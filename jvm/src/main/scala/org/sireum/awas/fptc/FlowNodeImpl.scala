/*
 Copyright (c) 2017, Robby, Kansas State University
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice, this
    list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sireum.awas.fptc

import org.sireum.awas.collector.CollectorErrorHelper._
import org.sireum.awas.collector.{FlowCollector, FlowErrorNextCollector}
import org.sireum.awas.graph.{AwasEdgeFactory, AwasGraph}
import org.sireum.awas.symbol._
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

final case class FlowNodeImpl(uri: ResourceUri, st: SymbolTable)(implicit reporter: AccumulatingTagReporter)
  extends BasicNodeImpl(uri, st) with FlowNode with FptcNodeUpdate {

  self: FlowNode =>

  type Edge = FlowEdge[FlowNode]

  val uriType: Uri = if (uri.startsWith(H.CONNECTION_TYPE)) H.CONNECTION_TYPE else H.COMPONENT_TYPE

  val compST: Option[ComponentSymbolTable] = if (uriType == H.COMPONENT_TYPE) Some(st.componentTable(uri)) else None

  val connST: Option[ConnectionSymbolTable] = if (uriType == H.CONNECTION_TYPE) Some(st.connectionTable(uri)) else None

  //initialized with no error
  var fptcPropagation: Map[ResourceUri, ISet[ResourceUri]] =
    ports.map(_ -> isetEmpty[ResourceUri]).toMap

  //adding initial state
  var fptcState: ISet[ResourceUri] = {
    if (uriType == H.COMPONENT_TYPE) {
      if (st.compStateMachine(uri).isDefined) {
        isetEmpty[ResourceUri] + st.smTable(st.compStateMachine(uri).get).states(0)
      } else {
        isetEmpty[ResourceUri]
      }
    } else isetEmpty[ResourceUri]
  }

  override def addFptcPropagation(port: ResourceUri, error_type: ResourceUri): Unit = {
    require(fptcPropagation.keySet.contains(port))
    var etSet = fptcPropagation(port)
    fptcPropagation += (port -> (etSet + error_type))
  }

  override def isFlowDefined: Boolean = {
    if (uriType == H.COMPONENT_TYPE) {
      st.componentTable(uri).flows.nonEmpty
    } else {
      st.connectionTable(uri).flows.nonEmpty
    }
  }

  override def getFlows: IMap[ResourceUri, FlowTableData] = {
    if (isComponent & compST.isDefined) {
      compST.get.flows.map(it => (it, compST.get.flow(it))).toMap
    } else {
      connST.get.flows.map(it => (it, connST.get.flow(it))).toMap
    }
  }

  def getPortsFromFlows(flowUri: ResourceUri): Set[ResourceUri] = {
    if (isComponent & compST.isDefined) {
      compST.get.getPortsFromFlows(flowUri)
    } else {
      connST.get.getPortsFromFlows(flowUri)
    }
  }

  def getFlowsFromPort(portUri: ResourceUri): Set[ResourceUri] = {
    if (isComponent & compST.isDefined) {
      compST.get.getFlowsFromPort(portUri)
    } else {
      connST.get.getFlowsFromPort(portUri)
    }
  }

  override def getFptcPropagation(port: ResourceUri): Set[ResourceUri] = fptcPropagation(port)

  private def getCompFlowError(tuple: (ResourceUri, ResourceUri), isForward: Boolean): FlowErrorNextCollector = {
    //flows are defined, but may not be complete
    //in forward we care only the path and sink
    require(isComponent && compST.isDefined, "object state is not satisfying the requirment")
    var result = isetEmpty[(ResourceUri, ResourceUri)]
    var flows = isetEmpty[ResourceUri]
    var errors = isetEmpty[Tag]
    var found = false
    if (isFlowDefined) {
      compST.get.getFlowsFromPort(tuple._1).foreach { fUri => //takes cares of path and sink
        val flow = compST.get.flow(fUri)
        val source = if (isForward) flow.fromPortUri else flow.toPortUri
        val target = if (isForward) flow.toPortUri else flow.fromPortUri
        val sourceE = if (isForward) flow.fromFaults else flow.toFaults
        val targetE = if (isForward) flow.toFaults else flow.fromFaults
        if ((source.isDefined && source.get == tuple._1) &&
          sourceE.contains(tuple._2)) {
          found = true
          flows += fUri
          if (target.isDefined) {
            result = result ++ targetE.map((target.get, _))
          }
        }
      }

      if (!found && compST.get.port(tuple._1).isDefined) { //if flow is not defined for this port and error
        val port = compST.get.port(tuple._1).get
        errors += errorMessageGen(
          INSUFFICIENT_FLOW_INFO_ERROR,
          tuple._1 + ", " + tuple._2,
          ReachAnalysisStage.FlowError
        )
        val tos = if (isForward) flowForward(tuple._1) else flowBackward(tuple._1)
        errors ++= tos.errors
        flows ++= tos.flows
        val tempPorts = tos.ports
        tempPorts.foreach { it =>
          result = result ++ compST.get.propagation(it).map((it, _))
        }
      }
    } else {
      result = result ++ outPorts.flatMap(it => getPropagation(it).map(e => (it, e))).toSet
      errors += warningMessageGen(FLOW_INFO_MISSING, uri, ReachAnalysisStage.FlowError)
    }
    FlowErrorNextCollector(result, isetEmpty[Edge], flows, errors)
  }

  private def getConnFlowError(tuple: (ResourceUri, ResourceUri), isForward: Boolean): FlowErrorNextCollector = {
    var result = isetEmpty[(ResourceUri, ResourceUri)]
    var flows = isetEmpty[ResourceUri]
    var errors = isetEmpty[Tag]
    var found = false
    require(!isComponent && connST.isDefined, "object state is not satisfying the requirment")
    val ports = if (isForward) outPorts else inPorts
    if (isFlowDefined) {
      connST.get.getFlowsFromPort(tuple._1).foreach { furi =>
        val flow = connST.get.flow(furi)
        val src = if (isForward) flow.fromPortUri else flow.toPortUri
        val target = if (isForward) flow.toPortUri else flow.fromPortUri
        val sourceE = if (isForward) flow.fromFaults else flow.toFaults
        val targetE = if (isForward) flow.toFaults else flow.fromFaults
        if (sourceE.nonEmpty &&
          sourceE.contains(tuple._2)) {
          found = true
          flows += furi
          result = result ++ targetE.flatMap(e => if (target.isDefined) Some((target.get, e)) else None)
        }
      }
      if (!found) {
        //propagate on the conservative step
        errors += errorMessageGen(
          INSUFFICIENT_FLOW_INFO_ERROR,
          tuple._1 + ", " + tuple._2,
          ReachAnalysisStage.FlowError
        )
        result ++= ports.map(op => (op, tuple._2))
      }
    } else {
      errors += warningMessageGen(FLOW_INFO_MISSING, uri, ReachAnalysisStage.FlowError)
      result ++= ports.map(op => (op, tuple._2))
    }
    FlowErrorNextCollector(result, isetEmpty[Edge], flows, errors)
  }

  private def errorFlowNext(tuple: (ResourceUri, ResourceUri), isForward: Boolean): FlowErrorNextCollector = {

    if (isComponent && compST.isDefined) {
      getCompFlowError(tuple, isForward)
    } else {
      getConnFlowError(tuple, isForward)
    }
  }

  override def flowForward(port: ResourceUri): FlowCollector = {
    flowNext(port, isForward = true)
  }

  private def flowNext(port: ResourceUri, isForward: Boolean): FlowCollector = {
    var result = isetEmpty[ResourceUri]
    var edges = isetEmpty[Edge]
    var flows = isetEmpty[ResourceUri]
    var errors = isetEmpty[Tag]

    var res = FlowCollector(result, edges, flows, errors)

    if (!isFlowDefined) {
      //we know, if we are performing a forward analysis,
      // and calculating intra flow(this method), then given port is input
      if (isForward) result ++= outPorts else result ++= inPorts
      errors = errors + warningMessageGen(FLOW_INFO_MISSING, uri, ReachAnalysisStage.Port)

      FlowCollector(result, edges, flows, errors)
    } else {
      if (isComponent && compST.isDefined && H.isPort(port) && compST.get.getFlowsFromPort(port).nonEmpty) {
        compST.get.getFlowsFromPort(port).foreach { f =>
          if (isForward && compST.get.flow(f).fromPortUri.isDefined) {
            flows = flows + f
            if (compST.get.flow(f).toPortUri.isDefined)
              result = result + compST.get.flow(f).toPortUri.get
          } else if (!isForward && compST.get.flow(f).toPortUri.isDefined) {
            flows = flows + f
            if (compST.get.flow(f).fromPortUri.isDefined)
              result = result + compST.get.flow(f).fromPortUri.get
          } else {
            errors = errors + errorMessageGen(INSUFFICIENT_FLOW_INFO, port, ReachAnalysisStage.Port)
          }
        }
      } else if (!isComponent && connST.isDefined && H.isPort(port)) {
        if (isForward) result ++= outPorts else result ++= inPorts
      } else {
        errors = errors + errorMessageGen(INSUFFICIENT_FLOW_INFO, port, ReachAnalysisStage.Port)
      }
      FlowCollector(result, edges, flows, errors)
    }
  }

  override def flowBackward(port: ResourceUri): FlowCollector = {
    flowNext(port, isForward = false)
  }

  override def errorForward(tuple: (ResourceUri, ResourceUri)): FlowErrorNextCollector = {
    errorFlowNext(tuple, isForward = true)
  }

  override def errorBackward(tuple: (ResourceUri, ResourceUri)): FlowErrorNextCollector = {
    errorFlowNext(tuple, isForward = false)
  }

}

class FlowEdgeFactory extends AwasEdgeFactory[FlowNode, FlowEdge[FlowNode]] {

  def createFlowEdge(owner: FlowGraph[FlowNode],
                     source: FlowNode, target: FlowNode): FlowEdge[FlowNode] = {
    FlowEdgeImpl(owner, source, target)
  }

  case class FlowEdgeImpl(
                           owner: FlowGraph[FlowNode],
                           override val source: FlowNode,
                           override val target: FlowNode
                         ) extends FlowEdge[FlowNode] {
    self: FlowEdge[FlowNode] =>

    //either source or target should be a connection
    val conn: FlowNode =
      if (source != null && source.getUri.startsWith(SymbolTableHelper.CONNECTION_TYPE))
        source
      else target

    val isSourceConn: Boolean = conn == source

    override def sourcePort: Option[ResourceUri] = {
      owner.getPortsFromEdge(this) match {
        case Some(x) => Some(x._1)
        case None => None
      }
    }

    override def targetPort: Option[ResourceUri] = {
      owner.getPortsFromEdge(this) match {
        case Some(x) => Some(x._2)
        case None => None
      }
    }
  }

  override type Edge = FlowEdge[FlowNode]

  override def createEdge(owner: AwasGraph[FlowNode],
                          source: FlowNode, target: FlowNode)
  : FlowEdge[FlowNode] = {
    createFlowEdge(owner.asInstanceOf[FlowGraph[FlowNode]], source, target)
  }
}
