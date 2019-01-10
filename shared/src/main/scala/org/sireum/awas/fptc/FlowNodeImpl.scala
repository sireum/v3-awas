/*
 *
 *  Copyright (c) 2017, Hariharan Thiagarajan, Kansas State University
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package org.sireum.awas.fptc

import org.sireum.awas.collector
import org.sireum.awas.collector.CollectorErrorHelper._
import org.sireum.awas.collector.{FlowCollector, FlowErrorNextCollector}
import org.sireum.awas.graph.{AwasEdgeFactory, AwasGraph}
import org.sireum.awas.symbol.{FlowTableData, Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._
import upickle.default.{macroRW, ReadWriter => RW}

final case class FlowNodeImpl(uri: ResourceUri,
                              st: SymbolTable,
                              owner: FlowGraph[FlowNode, FlowEdge[FlowNode]])
                             (implicit reporter: AccumulatingTagReporter)
  extends BasicNodeImpl[FlowGraph[FlowNode, FlowEdge[FlowNode]]](uri, st, owner)
    with FlowNode with FptcNodeUpdate {

  self: FlowNode =>

  private type Edge = FlowEdge[FlowNode]

  //  override val H = SymbolTableHelper

  private val subGraph: Option[FlowGraph[FlowNode, Edge]] =
    if (getResourceType == NodeType.COMPONENT &&
      st.componentTable(uri).subComponents.nonEmpty) {
      Some(FlowGraph.buildGraph(st.componentTable(uri), st))
    } else {
      None
    }

  //initialized with no error
  private var fptcPropagation: Map[ResourceUri, ISet[ResourceUri]] =
    ports.map(_ -> isetEmpty[ResourceUri]).toMap

  //adding initial state
  private var fptcState: ISet[ResourceUri] = {
    if (getResourceType == NodeType.COMPONENT) {
      if (st.compStateMachine(uri).isDefined) {
        isetEmpty[ResourceUri] + st.smTable(st.compStateMachine(uri).get).states.head
      } else {
        isetEmpty[ResourceUri]
      }
    } else isetEmpty[ResourceUri]
  }

  override def getOwner: FlowGraph[FlowNode, FlowEdge[FlowNode]] = owner

  override def addFptcPropagation(port: ResourceUri, error_type: ResourceUri): Unit = {
    require(fptcPropagation.keySet.contains(port))
    var etSet = fptcPropagation(port)
    fptcPropagation += (port -> (etSet + error_type))
  }

  override def isFlowDefined: Boolean = {
    getResourceType match {
      case NodeType.COMPONENT => st.componentTable(uri).flows.nonEmpty
      case NodeType.CONNECTION => Resource.getParentUri(uri) match {
        case Some(puri) => st.componentTable(puri).connectionTable(uri).flows.nonEmpty
        case None => {
          assert(assertion = false, "Connection's parent not found")
          false
        }
      }
      case NodeType.PORT => false
    }
  }

  override def getFlows: IMap[ResourceUri, FlowTableData] = {
    getResourceType match {
      case NodeType.COMPONENT => st.componentTable(uri).flows.map(it =>
        (it, st.componentTable(uri).flow(it))).toMap
      case NodeType.CONNECTION => Resource.getParentUri(uri) match {
        case Some(puri) => {
          val connST = st.componentTable(puri).connectionTable(uri)
          connST.flows.map(it => (it, connST.flow(it))).toMap
        }
        case None => {
          assert(assertion = false, "Connection's parent not found")
          imapEmpty[ResourceUri, FlowTableData]
        }
      }
      case NodeType.PORT => imapEmpty[ResourceUri, FlowTableData]
    }
  }

  getResourceType match {
    case NodeType.COMPONENT =>
    case NodeType.CONNECTION => Resource.getParentUri(uri) match {
      case Some(puri) =>
      case None => {
        assert(assertion = false, "Connection's parent not found")
      }
    }
    case NodeType.PORT =>
  }

  def getPortsFromFlows(flowUri: ResourceUri): Set[ResourceUri] = {
    getResourceType match {
      case NodeType.COMPONENT => st.componentTable(uri).getPortsFromFlows(flowUri)
      case NodeType.CONNECTION => Resource.getParentUri(uri) match {
        case Some(puri) => st.componentTable(puri).connectionTable(uri).getPortsFromFlows(flowUri)
        case None => {
          assert(assertion = false, "Connection's parent not found")
          isetEmpty[ResourceUri]
        }
      }
      case NodeType.PORT => isetEmpty[ResourceUri]
    }
  }

  def getFlowsFromPort(portUri: ResourceUri): Set[ResourceUri] = {
    getResourceType match {
      case NodeType.COMPONENT => st.componentTable(uri).getFlowsFromPort(portUri)
      case NodeType.CONNECTION => Resource.getParentUri(uri) match {
        case Some(puri) => st.componentTable(puri).connectionTable(uri).getFlowsFromPort(portUri)
        case None => {
          assert(assertion = false, "Connection's parent not found")
          isetEmpty[ResourceUri]
        }
      }
      case NodeType.PORT => isetEmpty[ResourceUri]
    }
  }

  override def getFptcPropagation(port: ResourceUri): Set[ResourceUri] = fptcPropagation(port)

  override def flowBackward(port: ResourceUri): FlowCollector = {
    flowNext(port, isForward = false)
  }

  override def errorForward(tuple: (ResourceUri, ResourceUri)): FlowErrorNextCollector = {
    errorFlowNext(tuple, isForward = true)
  }

  override def errorBackward(tuple: (ResourceUri, ResourceUri)): FlowErrorNextCollector = {
    errorFlowNext(tuple, isForward = false)
  }

  override def flowForward(port: ResourceUri): FlowCollector = {
    flowNext(port, isForward = true)
  }

  private def flowNext(port: ResourceUri, isForward: Boolean): FlowCollector = {
    var result = isetEmpty[ResourceUri]
    var edges = isetEmpty[Edge]
    var flows = isetEmpty[ResourceUri]
    var errors = isetEmpty[Tag]

    var res = collector.FlowCollector(isetEmpty + this.getOwner, result, edges, flows, errors)

    if (!isFlowDefined) {
      //we know, if we are performing a forward analysis,
      // and calculating intra flow(this method), then given port is input
      if (isForward) result ++= outPorts else result ++= inPorts
      if (getResourceType == H.COMPONENT_TYPE) {
        errors = errors + warningMessageGen(FLOW_INFO_MISSING, H.uri2CanonicalName(uri), ReachAnalysisStage.Port)
      }

      collector.FlowCollector(isetEmpty + this.getOwner, result, edges, flows, errors)
    } else {
      getResourceType match {
        case NodeType.COMPONENT => {
          val compST = st.componentTable(uri)
          if (H.isPort(port) && compST.getFlowsFromPort(port).nonEmpty) {
            compST.getFlowsFromPort(port).foreach { f =>
              if (isForward && compST.flow(f).fromPortUri.isDefined) {
                flows = flows + f
                if (compST.flow(f).toPortUri.isDefined)
                  result = result + compST.flow(f).toPortUri.get
              } else if (!isForward && compST.flow(f).toPortUri.isDefined) {
                flows = flows + f
                if (compST.flow(f).fromPortUri.isDefined)
                  result = result + compST.flow(f).fromPortUri.get
              } else {
                errors = errors + errorMessageGen(INSUFFICIENT_FLOW_INFO, H.uri2CanonicalName(port), ReachAnalysisStage.Port)
              }
            }
          } else if (H.isPort(port) && compST.ports.toSet.contains(port)) {
            if (isForward) result ++= outPorts else result ++= inPorts
          } else {
            errors = errors + errorMessageGen(INSUFFICIENT_FLOW_INFO, H.uri2CanonicalName(port), ReachAnalysisStage.Port)
          }
        }
        case NodeType.CONNECTION => Resource.getParentUri(uri) match {
          case Some(puri) => {
            val connST = st.componentTable(puri).connectionTable(uri)
            if (H.isPort(port) && connST.getFlowsFromPort(port).nonEmpty) {
              connST.getFlowsFromPort(port).foreach { f =>
                if (isForward && connST.flow(f).fromPortUri.isDefined) {
                  flows = flows + f
                  if (connST.flow(f).toPortUri.isDefined)
                    result = result + connST.flow(f).toPortUri.get
                } else if (!isForward && connST.flow(f).toPortUri.isDefined) {
                  flows = flows + f
                  if (connST.flow(f).fromPortUri.isDefined)
                    result = result + connST.flow(f).fromPortUri.get
                } else {
                  errors = errors + errorMessageGen(INSUFFICIENT_FLOW_INFO, H.uri2CanonicalName(port), ReachAnalysisStage.Port)
                }
              }
            }
          }
          case None => assert(assertion = false, "Connection's parent not found")
        }
        case NodeType.PORT =>
      }
      collector.FlowCollector(isetEmpty + this.getOwner, result, edges, flows, errors)
    }
  }

  /**
    *
    * @param tuple     : (port, error token)
    * @param isForward : direction
    * @return : FlowErrorNextCollector
    */
  private def getCompFlowError(tuple: (ResourceUri, ResourceUri), isForward: Boolean): FlowErrorNextCollector = {
    //flows are defined, but may not be complete
    //in forward we care only the path and sink
    require(isComponent, "object state is not satisfying the requirment")
    var result = isetEmpty[(ResourceUri, ResourceUri)]
    var flows = isetEmpty[ResourceUri]
    var errors = isetEmpty[Tag]
    var found = false
    if (isFlowDefined) {
      val compST = st.componentTable(uri)
      compST.getFlowsFromPort(tuple._1).foreach { fUri => //takes cares of path and sink
        val flow = compST.flow(fUri)
        val source = if (isForward) flow.fromPortUri else flow.toPortUri
        val target = if (isForward) flow.toPortUri else flow.fromPortUri
        val sourceE = if (isForward) flow.fromFaults else flow.toFaults
        val targetE = if (isForward) flow.toFaults else flow.fromFaults
        if ((source.isDefined && source.get == tuple._1) &&
          (sourceE.contains(tuple._2) || sourceE.intersect(st.typeAlias(tuple._2)).nonEmpty)) {
          found = true
          flows += fUri
          if (target.isDefined) {
            result = result ++ targetE.flatMap { te =>
              val targetProp = getPropagation(target.get)
              if (targetProp.contains(te)) {
                isetEmpty[(ResourceUri, ResourceUri)] + ((target.get, te))
              } else {
                isetEmpty[(ResourceUri, ResourceUri)] ++
                  st.typeAlias(te).intersect(targetProp).map((target.get, _))
              }
            }
          }
        }
      }

      if (!found && compST.port(tuple._1).isDefined) { //if flow is not defined for this port and error
        val port = compST.port(tuple._1).get
        errors += errorMessageGen(
          INSUFFICIENT_FLOW_INFO_ERROR,
          H.uri2CanonicalName(tuple._1) + ", " + H.uri2CanonicalName(tuple._2),
          ReachAnalysisStage.FlowError
        )
        val tos = if (isForward) flowForward(tuple._1) else flowBackward(tuple._1)
        errors ++= tos.errors
        flows ++= tos.flows
        val tempPorts = tos.ports
        tempPorts.foreach { it =>
          result = result ++ compST.propagation(it).map((it, _))
        }
      }
    } else {
      result = result ++ outPorts.flatMap(it => getPropagation(it).map(e => (it, e))).toSet
      errors += warningMessageGen(FLOW_INFO_MISSING, H.uri2CanonicalName(uri), ReachAnalysisStage.FlowError)
    }
    collector.FlowErrorNextCollector(result, isetEmpty[Edge], flows, errors, isetEmpty + getOwner)
  }

  private def getConnFlowError(tuple: (ResourceUri, ResourceUri), isForward: Boolean): FlowErrorNextCollector = {
    var result = isetEmpty[(ResourceUri, ResourceUri)]
    var flows = isetEmpty[ResourceUri]
    var errors = isetEmpty[Tag]
    var found = false
    //require(!isComponent && connST.isDefined, "object state is not satisfying the requirment")
    val ports = if (isForward) outPorts else inPorts
    if (isFlowDefined) {
      val connST = Resource.getParentUri(uri) match {
        case Some(puri) => Some(st.componentTable(puri).connectionTable(uri))
        case None => None
      }
      connST.get.getFlowsFromPort(tuple._1).foreach { furi =>
        val flow = connST.get.flow(furi)
        val src = if (isForward) flow.fromPortUri else flow.toPortUri
        val target = if (isForward) flow.toPortUri else flow.fromPortUri
        val sourceE = if (isForward) flow.fromFaults else flow.toFaults
        val targetE = if (isForward) flow.toFaults else flow.fromFaults
        if (sourceE.nonEmpty &&
          (sourceE.contains(tuple._2) ||
            (sourceE.intersect(st.typeAlias(tuple._2)).nonEmpty))) {
          found = true
          flows += furi
          result = result ++ targetE.flatMap { e =>
            if (target.isDefined) {
              val tprop = getPropagation(target.get)
              if (tprop.contains(e)) {
                isetEmpty[(ResourceUri, ResourceUri)] + ((target.get, e))
              } else {
                isetEmpty[(ResourceUri, ResourceUri)] ++
                  st.typeAlias(e).intersect(tprop).map((target.get, _))
              }
            } else isetEmpty[(ResourceUri, ResourceUri)]
          }
        }
      }
      if (!found) {
        //propagate on the conservative step
        errors += errorMessageGen(
          INSUFFICIENT_FLOW_INFO_ERROR,
          H.uri2CanonicalName(tuple._1) + ", " + H.uri2CanonicalName(tuple._2),
          ReachAnalysisStage.FlowError
        )
        result ++= ports.map(op => (op, tuple._2))
      }
    } else {
      errors += warningMessageGen(FLOW_INFO_MISSING, H.uri2CanonicalName(uri), ReachAnalysisStage.FlowError)
      result ++= ports.map(op => (op, tuple._2))
    }
    collector.FlowErrorNextCollector(result, isetEmpty[Edge], flows, errors, isetEmpty + getOwner)
  }

  private def errorFlowNext(tuple: (ResourceUri, ResourceUri), isForward: Boolean): FlowErrorNextCollector = {
    getResourceType match {
      case NodeType.COMPONENT => getCompFlowError(tuple, isForward)
      case NodeType.CONNECTION => getConnFlowError(tuple, isForward)
      case NodeType.PORT => {
        assert(false, "Intra flow should not be invoked on port node")
        collector.FlowErrorNextCollector(isetEmpty[(ResourceUri, ResourceUri)], isetEmpty, isetEmpty, isetEmpty, isetEmpty)
      }
    }
  }

  override def getSubGraph: Option[FlowGraph[FlowNode, Edge]] = {
    subGraph
  }
}

object FlowEdgeFactory extends AwasEdgeFactory[FlowNode, FlowEdge[FlowNode]] {

  def createFlowEdge(owner: FlowGraph[FlowNode, FlowEdge[FlowNode]],
                     source: FlowNode, target: FlowNode): FlowEdge[FlowNode] = {
    FlowEdgeImpl(owner.getUri, source.getUri, target.getUri)
  }

  case class FlowEdgeImpl(
                           owner: ResourceUri,
                           sourceNodeUri: ResourceUri,
                           targetNodeUri: ResourceUri
                         ) extends FlowEdge[FlowNode] {
    self: FlowEdge[FlowNode] =>

    //either source or target should be a connection
  //    val conn: FlowNode =
    //      if (source.getUri.startsWith(SymbolTableHelper.CONNECTION_TYPE))
    //        source
    //      else target
    //
    //    val isSourceConn: Boolean = conn == source

    override def sourcePort: Option[ResourceUri] = {
      FlowGraph.graphs(owner).getPortsFromEdge(this) match {
        case Some(x) => Some(x._1)
        case None => None
      }
    }

    override def targetPort: Option[ResourceUri] = {
      FlowGraph.graphs(owner).getPortsFromEdge(this) match {
        case Some(x) => Some(x._2)
        case None => None
      }
    }
    override def source: FlowNode = {
      val source = FlowNode.getNode(sourceNodeUri)
      assert(source.isDefined, "if edge exists, then source and target nodes are defined")
      source.get
    }
    override def target: FlowNode = {
      val target = FlowNode.getNode(targetNodeUri)
      assert(target.isDefined, "if edge exists, then source and target nodes are defined")
      target.get
    }
  }

  object FlowEdgeImpl {
    implicit val rw: RW[FlowEdgeImpl] = macroRW
  }

  override def createEdge(owner: AwasGraph[FlowNode, FlowEdge[FlowNode]],
                          source: FlowNode, target: FlowNode)
  : FlowEdge[FlowNode] = {
    createFlowEdge(owner.asInstanceOf[FlowGraph[FlowNode, FlowEdge[FlowNode]]], source, target)
  }
}
