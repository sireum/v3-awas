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
  def sortedInEdges(node : FptcNode): Vector[Edge]
  def propagate(node: FptcNode, out: IVector[Option[Fault]]): ISet[FptcNode]
  def getFault(e : AwasEdge[FptcNode]) : ISet[Fault]
}

object FptcGraph {

  type GEdge = AwasEdge[FptcNode]

  def apply(m: Model): FptcGraph[FptcNode] = {

    val result = new Fg()

    FptcNode.newPool()

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
        val e1 = result.addEdge(fromCompNode, connNode)
        addPortInfo(fromCompNode, conn, e1, isInPort = false)
        val e2 = result.addEdge(connNode, toCompNode)
        addPortInfo(toCompNode, conn, e2, isInPort = true)
      }
        false
    })(m)
    result
  }

  def addPortInfo(node : FptcNode, conn : ConnectionDecl,
                  edge : AwasEdge[FptcNode], isInPort: Boolean) = {
    assert(node.getType == FptcNodeProperty.COMP_NODE,
      "Component node expected")
    val ports = if(isInPort) node.getCompInPorts else node.getCompOutPorts
    val portId = if(isInPort) conn.toPort else conn.fromPort
    val port = ports.find(p => p.id.equals(portId))
    if(port.isDefined) {
      node.addPortEdgeInfo(port.get, edge)
    }
  }
}

class Fg extends FptcGraph[FptcNode] {
  val graph = mutable.Graph[FptcNode, AwasEdge]()

//  private def getGraphEdge(e : AwasEdge[FptcNode]) = {
//    this.graph.get(e).toOuter
//  }

  def sortedInEdges(node : FptcNode): Vector[Edge] = {
    if(node.getType == FptcNodeProperty.COMP_NODE)
      node.getCompInPorts.map{e =>
        getOuterEdge(node.getPortEdgeInfo(e).edge)
      }
    else {
      assert(this.inEdges(node).size == 1)
      inEdges(node).toVector
    }
  }

  def sortedOutEdges(node : FptcNode): Vector[Edge] = {
    if(node.getType == FptcNodeProperty.COMP_NODE)
      node.getCompOutPorts.map{e =>
        getOuterEdge(node.getPortEdgeInfo(e).edge)
      }
    else {
      assert(this.outEdges(node).size == 1)
      outEdges(node).toVector
    }
  }

  def getFault(e : AwasEdge[FptcNode]) : ISet[Fault] = {
    val temp = this.graph.get(e).fault
    this.graph.get(e).fault = isetEmpty[Fault]
    temp
  }

  def propagate(node: FptcNode, out: IVector[Option[Fault]]): ISet[FptcNode] = {
    val edgeseq = sortedOutEdges(node)
    var result = isetEmpty[FptcNode]
    if(edgeseq.size == out.length) {
      for(i <- edgeseq.indices) {
        if(out(i).isDefined) {
          this.graph.get(edgeseq(i)).setFault(out(i).get)
          result = result + edgeseq(i)._2
        }
      }
    }
    result
  }

  //TODO: Someday move this into more abstract class of Node
  def toDot(name : String): String = {
    val dotExporter = new Export(graph)

    val root = DotRootGraph(directed = true, id =
      Some(scalax.collection.io.dot.Id(name)))

    def edgeTransformer(innerEdge: Graph[FptcNode, AwasEdge]#EdgeT):
    Option[(DotGraph, DotEdgeStmt)] = {
      val  edge = innerEdge
      Some((root, DotEdgeStmt(NodeId(edge.head.value.toString),
        NodeId(edge.last.value.toString), Nil)))
    }

    def nodeTransformer(innerNode: Graph[FptcNode, AwasEdge]#NodeT):
    Option[(DotGraph, DotNodeStmt)] =
      Some((root,
        DotNodeStmt(NodeId(innerNode.value.toString), innerNode.value.getType match {
          case FptcNodeProperty.CONN_NODE => Nil
          case _ => List(DotAttr(io.dot.Id("shape"), io.dot.Id("box")))
        })))

    val dot = dotExporter.toDot(root,
      edgeTransformer,None,
      cNodeTransformer = Some(nodeTransformer),
      iNodeTransformer = Some(nodeTransformer),
      spacing = Spacing(Indent.TwoSpaces)
    )

    val dotSorted = {
      val lines = dot.linesWithSeparators.toList
      val mid = lines.tail.init
      s"${lines.head}${mid.sorted.mkString}${lines.last}"
    }
    dotSorted
  }
}