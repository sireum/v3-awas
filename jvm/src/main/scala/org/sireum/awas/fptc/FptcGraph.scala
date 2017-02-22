
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

import org.sireum.awas.ast.Model
import org.sireum.awas.graph.{AwasEdge, AwasGraph}
import org.sireum.awas.symbol.Resource._
import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.CSet

trait FptcGraph[Node] extends AwasGraph[Node] {
  def toDot(name : String) : String

  def getEdgeForPort(port: ResourceUri): Set[Edge]

  def getSuccessorPorts(port: ResourceUri): CSet[ResourceUri]

  def getPredecessorPorts(port: ResourceUri): CSet[ResourceUri]

  def getNode(port: ResourceUri): Option[Node]
}

trait FptcEdge[Node] extends AwasEdge[Node] {
  def sourcePort: Option[ResourceUri]

  def targetPort: Option[ResourceUri]
}

object FptcGraph{
  val H = SymbolTableHelper
  def apply(m : Model, st : SymbolTable) : FptcGraph[FptcNode] = {
    val result = new FptcGraphImpl()

    FptcNode.newPool()

    st.components.foreach{
      comp => result.addNode(FptcNode.createNode(comp, st))
    }

    st.connections.foreach {
      conn =>
        val connNode = result.addNode(FptcNode.createNode(conn, st))
        val fromNode = toFptcNode(st.connection(conn).fromComp)
        val toNode = toFptcNode(st.connection(conn).toComp)

        if(fromNode.isDefined && toNode.isDefined) {
          val fedge = result.addEdge(fromNode.get, connNode)
          val fromPortUri = Resource.getResource(st.connection(conn).fromPort).get.toUri
          result.addPortEdge(fromPortUri, fedge)
          result.addPortEdge(connNode.inPorts.head, fedge)

          val tedge = result.addEdge(connNode, toNode.get)
          val toPortUri = Resource.getResource(st.connection(conn).toPort).get.toUri
          result.addPortEdge(toPortUri, tedge)
          result.addPortEdge(connNode.outPorts.head, tedge)
        }
    }
    result
  }

  def toFptcNode(node : org.sireum.awas.ast.Node) : Option[FptcNode] = {
    val res = getResource(node)
    if(res.isDefined && (H.isComponent(res.get) || H.isConnection(res.get))) {
      FptcNode.getNode(res.get.toUri)
    } else {
      None
    }
  }
}

