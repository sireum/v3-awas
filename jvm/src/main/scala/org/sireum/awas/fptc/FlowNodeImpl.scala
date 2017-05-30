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

import org.sireum.awas.graph.AwasGraph
import org.sireum.awas.symbol._
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

final case class FlowNodeImpl(uri: ResourceUri, st: SymbolTable)(implicit reporter: AccumulatingTagReporter) extends
  BasicNodeImpl(uri, st) with FlowNode with FptcNodeUpdate {

  type Edge = FptcEdge[FlowNode]

  val uriType: Uri = if (uri.startsWith(H.CONNECTION_TYPE)) H.CONNECTION_TYPE else H.COMPONENT_TYPE

  val compST: Option[ComponentSymbolTable] = if (uriType == H.COMPONENT_TYPE) Some(st.componentTable(uri)) else None

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

  override def getFptcPropagation(port: ResourceUri): Set[ResourceUri] = fptcPropagation(port)

  override def errorForward(tuple: (ResourceUri, ResourceUri)): ISet[(ResourceUri, ResourceUri)] = {
    var result = isetEmpty[(ResourceUri, ResourceUri)]

    if (isComponent && !H.isFlowDefined(compST.get)) {
      outPorts.foreach { op =>
        result ++= compST.get.propagation(op).map((f: ResourceUri) => (op, f))
      }
    } else {
      if (tuple._1.startsWith(H.PORT_IN_TYPE) &&
        isComponent && H.getPortId(st, uri, tuple._1).isDefined) {
        var found = false
        compST.get.flowRelate(tuple._1).foreach { f =>
          // this looks for path
          if (compST.get.flow(f).from.isDefined &&
            compST.get.flow(f).to.isDefined) {
            val fromE = compST.get.flow(f).fromE.flatMap(Resource.getResource(_)).map(_.toUri)

            if (fromE.contains(tuple._2)) {
              val toUri = Resource.getResource(compST.get.flow(f).to.get)
              if (toUri.isDefined) {
                found = true
                result = result ++ compST.get.flow(f).toE.flatMap(
                  Resource.getResource(_)).map(_.toUri).map((toUri.get.toUri, _))
              }
            }
          }
          //looks for sink
          if (compST.get.flow(f).from.isDefined &&
            compST.get.flow(f).to.isEmpty) {
            val fromE = compST.get.flow(f).fromE.flatMap(Resource.getResource(_)).map(_.toUri)
            if (fromE.contains(tuple._2)) {
              found = true
            }
          }
        }
        if (!found) {
          if (compST.get.port(tuple._1).isDefined) {
            val port = compST.get.port(tuple._1).get
            val tos = flowForward(tuple._1)
            tos.foreach { it =>
              result = result ++ compST.get.propagation(it).map((it, _))
            }
            System.err.println("Flow missing the error :" + tuple._1 + " -> " + tuple._2)
          }
        }
      } else {
        result += ((outPorts.head, tuple._2))
      }
    }
    result
  }

  override def flowForward(port: ResourceUri): Set[ResourceUri] = {
    var result = isetEmpty[ResourceUri]
    if (isComponent && !H.isFlowDefined(compST.get)) {
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

  override def errorBackward(tuple: (ResourceUri, ResourceUri)): ISet[(ResourceUri, ResourceUri)] = {
    var result = isetEmpty[(ResourceUri, ResourceUri)]

    if (isComponent && !H.isFlowDefined(compST.get)) {
      inPorts.foreach { op =>
        result ++= compST.get.propagation(op).map((f: ResourceUri) => (op, f))
      }
    } else {
      if (tuple._1.startsWith(H.PORT_OUT_TYPE) &&
        isComponent && H.getPortId(st, uri, tuple._1).isDefined) {
        var found = false
        compST.get.flowRelate(tuple._1).foreach { f =>
          // this looks for path
          if (compST.get.flow(f).from.isDefined &&
            compST.get.flow(f).to.isDefined) {
            val toE = compST.get.flow(f).toE.flatMap(Resource.getResource(_)).map(_.toUri)
            if (toE.contains(tuple._2)) {
              val fromUri = Resource.getResource(compST.get.flow(f).from.get)
              if (fromUri.isDefined) {
                result ++= compST.get.flow(f).fromE.flatMap(
                  Resource.getResource(_)).map(_.toUri).map((fromUri.get.toUri, _))
                found = true
              }
            }
          }
          //looks for sink
          if (compST.get.flow(f).to.isDefined &&
            compST.get.flow(f).from.isEmpty) {
            val toE = compST.get.flow(f).toE.flatMap(Resource.getResource(_)).map(_.toUri)
            if (toE.contains(tuple._2)) {
              found = true
            }
          }

        }

        if (!found) {
          if (compST.get.port(tuple._1).isDefined) {
            val port = compST.get.port(tuple._1).get
            val tos = flowBackward(tuple._1)
            tos.foreach { it =>
              result = result ++ compST.get.propagation(it).map((it, _))
            }
            System.err.println("Flow missing the error :" + tuple._1 + " -> " + tuple._2)
          }

        }


      } else {
        result += ((inPorts.head, tuple._2))
      }
    }
    result
  }

  override def flowBackward(port: ResourceUri): Set[ResourceUri] = {
    var result = isetEmpty[ResourceUri]
    if (isComponent && !H.isFlowDefined(compST.get)) {
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

final case class FlowEdgeImpl(owner: AwasGraph[FlowNode],
                              source: FlowNode,
                              target: FlowNode) extends FptcEdge[FlowNode] {
  self: FptcEdge[FlowNode] =>
  //either source or target should be a connection
  val conn: FlowNode = if (source.getUri.startsWith(SymbolTableHelper.CONNECTION_TYPE))
    source else target

  val isSourceConn : Boolean = conn == source

  override def sourcePort: Option[ResourceUri] = {
    if(isSourceConn) {
      Some(conn.outPorts.head)
    } else {
      val pname = conn.inPorts.head.split("/").last
      source.outPorts.find(_.endsWith(pname))
    }
  }

  override def targetPort: Option[ResourceUri] = {
    if(isSourceConn) {
      val pname = conn.outPorts.head.split("/").last
      target.inPorts.find(_.endsWith(pname))
    } else {
      Some(conn.inPorts.head)
    }
  }
}