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

final case class FptcNodeImpl(uri : ResourceUri, st : SymbolTable)  extends
  BasicNodeImpl(uri, st) with FptcNode with FptcNodeUpdate {

  type Edge = FptcEdge[FptcNode]

  val uriType: Uri = if(uri.startsWith(H.CONNECTION_TYPE)) H.CONNECTION_TYPE else H.COMPONENT_TYPE

  val compST : Option[ComponentSymbolTable] = if(uriType == H.COMPONENT_TYPE) Some(st.componentTable(uri)) else None

  //initialized with no error
  var fptcPropagation: Map[ResourceUri, ISet[ResourceUri]] =
    ports.map(_ -> isetEmpty[ResourceUri]).toMap
  //adding initial state
  var fptcState: ISet[ResourceUri] = {
    if(uriType == H.COMPONENT_TYPE) {
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
    fptcPropagation += (port -> (etSet+error_type))
  }

  override def getFptcPropagation(port: ResourceUri): Set[ResourceUri] = fptcPropagation(port)

  override def flowForward(port: ResourceUri): Set[ResourceUri] = {
    var result = isetEmpty[ResourceUri]
    if (port.startsWith(H.PORT_IN_TYPE)) {
      if (isComponent) {
        val flows = compST.get.flows
        val pid = port.split("#").last
        flows.foreach { f =>
          if (compST.get.flow(f).from.isDefined &&
            compST.get.flow(f).to.isDefined) {
            if (compST.get.flow(f).from.get.value == pid) {
              result = result ++ outPorts.filter(_.split("#").last == compST.get.flow(f).to.get.value)
            }
          }
        }
      } else {
        result += outPorts.head
      }
    }
    result
  }

  override def flowBackward(port: ResourceUri): Set[ResourceUri] = {
    var result = isetEmpty[ResourceUri]
    if (port.startsWith(H.PORT_OUT_TYPE)) {
      if (isComponent) {
        val flows = compST.get.flows
        val pid = port.split("#").last
        flows.foreach { f =>
          if (compST.get.flow(f).from.isDefined &&
            compST.get.flow(f).to.isDefined) {
            if (compST.get.flow(f).to.get.value == pid) {
              result = result ++ inPorts.filter(_.split("#").last == compST.get.flow(f).from.get.value)
            }
          }
        }
      } else {
        result += inPorts.head
      }
    }
    result
  }
}

final case class FptcEdgeImpl(owner: AwasGraph[FptcNode],
                              source : FptcNode,
                              target: FptcNode) extends FptcEdge[FptcNode] {
  self: FptcEdge[FptcNode] =>
  //either source or target should be a connection
  val conn: FptcNode = if (source.getUri.startsWith(SymbolTableHelper.CONNECTION_TYPE))
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