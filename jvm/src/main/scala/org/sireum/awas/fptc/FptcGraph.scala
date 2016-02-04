/*
 Copyright (c) 2016, Robby, Kansas State University
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
import org.sireum.awas.ast._
import org.sireum.util._
import scalax.collection._
import scalax.collection.io.dot._

trait FptcGraph[Node] extends AwasGraph[Node] {
  def toDot(name : String): String
}

object FptcGraph {

  type GEdge = AwasEdge[FptcNode]

  def apply(m: Model): FptcGraph[FptcNode] = {

    val result = new Fg[FptcNode]()

    var compMap = imapEmpty[Name, Node]

    Visitor.build({
      case comp : ComponentDecl => {
        val elem = (comp.compName, comp)
        compMap = compMap.+(elem)
        val compNode = FptcNode.createNode(comp)
        result.addNode(compNode)
      }
        false
    })(m)

    Visitor.build({
      case conn : ConnectionDecl => {
        val fromCompNode = FptcNode createNode compMap(conn.fromComp)
        val toCompNode = FptcNode createNode compMap(conn.toComp)
        val connNode = FptcNode createNode conn
        result.addNode(connNode)
        result.addEdge(fromCompNode, connNode)
        result.addEdge(connNode, toCompNode)
      }
        false
    })(m)
    result
  }

}

class Fg[FptcNode] extends FptcGraph[FptcNode] {
  val graph = mutable.Graph[FptcNode, AwasEdge]()

  def toDot(name : String): String = {
    val dotExporter = new Export(graph)

    def buildRoot(name : String) = DotRootGraph(directed = true, id =
      Some(scalax.collection.io.dot.Id(name)))

    val root = buildRoot(name)

    def edgeTransformer(innerEdge: Graph[FptcNode, AwasEdge]#EdgeT):
    Option[(DotGraph, DotEdgeStmt)] = {
      val  edge = innerEdge
      Some((root, DotEdgeStmt(NodeId(edge.head.value.toString),
        NodeId(edge.last.value.toString), Nil)))
    }

    def nodeTransformer(innerNode: Graph[FptcNode, AwasEdge]#NodeT):
    Option[(DotGraph, DotNodeStmt)] =
      Some((root,
        DotNodeStmt(NodeId(innerNode.value.toString), Seq.empty[DotAttr])))

    dotExporter.toDot(dotRoot = buildRoot(name),
      edgeTransformer,None,
      cNodeTransformer = Some(nodeTransformer),
      iNodeTransformer = Some(nodeTransformer),
      spacing = Spacing(Indent.TwoSpaces)
    )
  }
}
