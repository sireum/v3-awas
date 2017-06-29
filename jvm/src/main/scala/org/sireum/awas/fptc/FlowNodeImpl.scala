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

import org.sireum.awas.symbol._
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

final case class FlowNodeImpl(uri: ResourceUri, st: SymbolTable)(implicit reporter: AccumulatingTagReporter) extends
  BasicNodeImpl(uri, st) with FlowNode with FptcNodeUpdate {

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
    }
    else isetEmpty[ResourceUri]
  }

  override def addFptcPropagation(port: ResourceUri, error_type: ResourceUri): Unit = {
    require(fptcPropagation.keySet.contains(port))
    var etSet = fptcPropagation(port)
    fptcPropagation += (port -> (etSet + error_type))
  }

  def isFlowDefined: Boolean = {
    if (uriType == H.COMPONENT_TYPE) {
      st.componentTable(uri).flows.nonEmpty
    } else {
      st.connectionTable(uri).flows.nonEmpty
    }
  }

  override def getFptcPropagation(port: ResourceUri): Set[ResourceUri] = fptcPropagation(port)

  private def getCompFlowError(tuple: (ResourceUri, ResourceUri),
                               isForward: Boolean): ISet[(ResourceUri, ResourceUri)] = {
    //flows are defined, but may not be complete
    //in forward we care only the path and sink
    require(isComponent && compST.isDefined, "object state is not satisfying the requirment")
    var result = isetEmpty[(ResourceUri, ResourceUri)]
    var found = false

    compST.get.flowRelate(tuple._1).foreach { fUri => //takes cares of path and sink
      val flow = compST.get.flow(fUri)
      val source = if (isForward) flow.from else flow.to
      val target = if (isForward) flow.to else flow.from
      val sourceE = if (isForward) flow.fromE else flow.toE
      val targetE = if (isForward) flow.toE else flow.fromE
      if (source.isDefined &&
        Resource.getResource(source.get).isDefined &&
        (Resource.getResource(source.get).get.toUri == tuple._1) &&
        sourceE.flatMap(Resource.getResource(_)).map(_.toUri).contains(tuple._2)) {
        found = true
        if (target.isDefined && Resource.getResource(target.get).isDefined) {
          result = result ++ targetE.flatMap(Resource.getResource(_)).
            map(_.toUri).map((Resource.getResource(target.get).get.toUri, _))
          }
      }
    }
    if (!found && compST.get.port(tuple._1).isDefined) { //if flow is not defined for this port and error
      val port = compST.get.port(tuple._1).get
      val tos = if (isForward) flowForward(tuple._1) else flowBackward(tuple._1)
      tos.foreach { it =>
        result = result ++ compST.get.propagation(it).map((it, _))
      }
      System.err.println("Missing flow definition for port and error :" + tuple._1 + " -> " + tuple._2)
    }
    result
  }

  private def getConnFlowError(tuple: (ResourceUri, ResourceUri),
                               isForward: Boolean): ISet[(ResourceUri, ResourceUri)] = {
    var result = isetEmpty[(ResourceUri, ResourceUri)]
    var found = false
    require(!isComponent && connST.isDefined, "object state is not satisfying the requirment")
    val ports = if (isForward) outPorts else inPorts
    connST.get.flowRelate(tuple._1).foreach { furi =>
      val flow = connST.get.flow(furi)
      val sourceE = if (isForward) flow.fromE else flow.toE
      val targetE = if (isForward) flow.toE else flow.fromE
      if (sourceE.nonEmpty &&
        sourceE.flatMap(Resource.getResource(_)).map(_.toUri).contains(tuple._2)) {
        found = true
        result = result ++ targetE.flatMap(Resource.getResource(_))
          .map(_.toUri).map(e => (ports.head, e))
        }
    }
    if (!found) {
      //propagate on the conservative step
      result += ((ports.head, tuple._2))
      System.err.println("Missing flow definition for port and error :" + tuple._1 + " -> " + tuple._2)
    }
    result
  }

  private def errorFlowNext(tuple: (ResourceUri, ResourceUri),
                            isForward: Boolean): ISet[(ResourceUri, ResourceUri)] = {
    var result = isetEmpty[(ResourceUri, ResourceUri)]
    if (!isFlowDefined) {
      val ports = if (isForward) outPorts else inPorts
      if (isComponent) {
        //if it is a component, use propagation to compute result
        ports.foreach { op =>
          result ++= compST.get.propagation(op).map((f: ResourceUri) => (op, f))
        }
        return result
      } else {
        //for connections without flows, just propagate the error
        result ++= ports.map(op => (op, tuple._2))
        return result
      }
    } else {
      //flows are defined, but may not be complete
      //in forward we care only the path and sink
      if (isComponent && compST.isDefined) {
        result = result ++ getCompFlowError(tuple, isForward)
      } else if (!isComponent && connST.isDefined) {
        result = result ++ getConnFlowError(tuple, isForward)
      } else {
        //node that's not a component and not a connection too? something is wrong
        assert(false)
      }
    }
    result
  }

  override def flowForward(port: ResourceUri): Set[ResourceUri] = {
    var result = isetEmpty[ResourceUri]
    if (isComponent && !isFlowDefined) {
      result ++= outPorts
    } else {
      if (port.startsWith(H.PORT_IN_TYPE) &&
        isComponent && H.getPortId(st, uri, port).isDefined) {
        compST.get.flowRelate(port).foreach { f =>
          if (compST.get.flow(f).from.isDefined &&
            compST.get.flow(f).to.isDefined) {
            result = result ++ outPorts.filter(H.getPortId(st, uri, _).get ==
              compST.get.flow(f).to.get.value)
          }
        }
      } else {
        result += outPorts.head
      }
    }
    result
  }

  override def errorForward(tuple: (ResourceUri, ResourceUri)): ISet[(ResourceUri, ResourceUri)] = {
    errorFlowNext(tuple, isForward = true)
  }

  override def errorBackward(tuple: (ResourceUri, ResourceUri)): ISet[(ResourceUri, ResourceUri)] = {
    errorFlowNext(tuple, isForward = false)
  }

  override def flowBackward(port: ResourceUri): Set[ResourceUri] = {
    var result = isetEmpty[ResourceUri]
    if (isComponent && !isFlowDefined) {
      result ++= inPorts
    } else {
      if (port.startsWith(H.PORT_OUT_TYPE) &&
        isComponent && H.getPortId(st, uri, port).isDefined) {
        compST.get.flowRelate(port).foreach { f =>
          if (compST.get.flow(f).from.isDefined &&
            compST.get.flow(f).to.isDefined) {
            result = result ++ inPorts.filter(H.getPortId(st, uri, _).get ==
              compST.get.flow(f).from.get.value)
          }
        }
      } else {
        result += inPorts.head
      }
    }
    result
  }

}

final case class FlowEdgeImpl(owner: FlowGraph[FlowNode],
                              source: FlowNode,
                              target: FlowNode) extends FlowEdge[FlowNode] {
  self: FlowEdge[FlowNode] =>
  //either source or target should be a connection
  val conn: FlowNode = if (source.getUri.startsWith(SymbolTableHelper.CONNECTION_TYPE))
    source else target

  val isSourceConn : Boolean = conn == source

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