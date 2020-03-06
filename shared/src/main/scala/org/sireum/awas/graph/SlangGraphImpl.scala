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

package org.sireum.awas.graph

import org.sireum.Graph
import org.sireum.ops.GraphOps
import org.sireum.util.{CSet, isetEmpty, ilistEmpty}


class SlangGraphImpl[Node, Edge <: AwasEdge[Node]]
//(ef: AwasEdgeFactory[Node, EdgeT])
  extends AwasGraph[Node, Edge] with AwasGraphUpdate[Node, Edge] {
  self: AwasGraph[Node, Edge] =>
  //override type Edge = EdgeT

  def getEdgeData(e: Graph.Edge[Node, Edge]): Option[Edge] = {
    e match {
      case Graph.Edge.Data(s, d, ed) => Some(ed)
      case _ => None
    }
  }

  var graph: Graph[Node, Edge] = Graph.empty[Node, Edge]

  private var cycles: Option[Seq[Seq[Node]]] = None

  override def nodes: Iterable[Node] = graph.nodes.keys.elements

  override def numOfNodes: Int = graph.numOfNodes.toInt

  override def edges: Iterable[Edge] = graph.allEdges.elements.flatMap(getEdgeData).toSet

  override def numOfEdges: Int = edges.size

  override def hasNode(n: Node): Boolean = graph.nodes.keys.elements.contains(n)

  override def getNode(n: Node): Node = n

  override def hasEdge(n1: Node, n2: Node): Boolean = graph.edges(n1, n2).nonEmpty

  //  override def getCycles: Set[Seq[Node]] = {
  //    //Todo: Complete it
  //    isetEmpty[Seq[Node]]
  //  }

  override def getEdge(n1: Node, n2: Node): CSet[Edge] =
    graph.edges(n1, n2).elements.flatMap(getEdgeData).toSet

  override def getEdges(n: Node): CSet[Edge] = getIncomingEdges(n) union getOutgoingEdges(n)

  override def getIncomingEdges(node: Node): CSet[Edge] = {
    if (graph.nodes.get(node).nonEmpty) {
      val inEdges = graph.incomingEdges.get(graph.nodes.get(node).get).map { x =>
        x.elements.elements.flatMap(t => getEdgeData(t.toEdge(graph.nodes.keys)))
      }
      if (inEdges.nonEmpty) {
        inEdges.get.toSet
      } else {
        isetEmpty[Edge]
      }
    } else {
      isetEmpty[Edge]
    }
  }

  override def getOutgoingEdges(node: Node): CSet[Edge] = {
    if (graph.nodes.get(node).nonEmpty) {
      val outEdges = graph.outgoingEdges.get(graph.nodes.get(node).get).map { x =>
        x.elements.elements.flatMap(t => getEdgeData(t.toEdge(graph.nodes.keys)))
      }
      if (outEdges.nonEmpty) {
        outEdges.get.toSet
      } else {
        isetEmpty[Edge]
      }
    } else {
      isetEmpty[Edge]
    }
  }

  override def getSuccessorNodes(node: Node): CSet[Node] = {
    if (graph.nodes.get(node).nonEmpty) {
      val t = graph.outgoingEdges.get(graph.nodes.get(node).get).map(
        _.elements.elements.map(t => graph.nodesInverse(t.dest)))
      if (t.nonEmpty) t.get.toSet else isetEmpty[Node]
    } else {
      isetEmpty[Node]
    }
  }

  override def getPredecessorNodes(node: Node): CSet[Node] = {
    if (graph.nodes.get(node).nonEmpty) {
      val t = graph.incomingEdges.get(graph.nodes.get(node).get).map(
        _.elements.elements.map(t => graph.nodesInverse(t.source)))
      if (t.nonEmpty) t.get.toSet else isetEmpty[Node]
    } else {
      isetEmpty[Node]
    }
  }

  override def addNode(n: Node): Node = {
    graph = graph * n
    n
  }

  //  override def addEdge(from: Node, to: Node, data: Edge): Edge = {
  //    val edge : Edge = SlangGraphAwasEdgeImpl[Node](from, to).asInstanceOf[Edge]
  //    graph = graph.addDataEdge(edge, from, to)
  //    edge
  //  }
  override def addEdge(from: Node, to: Node, data: Edge): Edge = {
    graph = graph.addDataEdge(data, from, to)
    data
  }

  override def getSCC: Seq[Set[Node]] = GraphOps(graph).getSCC.elements.map(_.elements.elements.toSet)
  /**
    * Find all simple cycles of a directed graph using the Schwarcfiter and Lauer's algorithm.
    *
    * @return set of cycles
    */
  override def getCycles: Seq[Seq[Node]] = {
    if (cycles.isEmpty) {
      cycles = Some(GraphOps(graph).getCycles.elements.map(_.elements))
    }
    cycles.getOrElse(ilistEmpty)
  }

  override def forwardReach(criteria: Set[Node]): CSet[Node] = {
    GraphOps(graph).forwardReach(org.sireum.ISZ(criteria.toSeq: _ *)).elements.toSet
  }

  override def backwardReach(criteria: Set[Node]): CSet[Node] = {
    GraphOps(graph).backwardReach(org.sireum.ISZ(criteria.toSeq: _ *)).elements.toSet
  }

  override def reComputeCycles(): Unit = {
    cycles = Some(GraphOps(graph).getCycles.elements.map(_.elements))
  }

  override def removeEdge(from: Node, to: Node): Unit = {
    graph = graph -- graph.edges(from, to)
  }
}

//case class SlangGraphAwasEdgeImpl[Node](src: Node, snk: Node) extends AwasEdge[Node] {
//  this: AwasEdge[Node] =>
//  override def source: Node = src
//
//  override def target: Node = snk
//}