package org.sireum.awas.graph

import org.sireum.Graph
import org.sireum.util.CSet

class SlangGraphImpl[Node, EdgeT <: AwasEdge[Node]]
(ef: AwasEdgeFactory[Node, EdgeT]) extends AwasGraph[Node] {

  override type Edge = EdgeT

  val graph: Graph[Node, Edge] = Graph.empty[Node, Edge]

  override def nodes: Iterable[Node] = graph.nodes.keys.elements

  override def numOfNodes: Int = graph.numOfNodes.toInt

  override def edges: Iterable[Edge] = ???

  override def numOfEdges: Int = edges.size

  override def hasNode(n: Node): Boolean = graph.nodes.keys.elements.contains(n)

  override def getNode(n: Node): Node = n

  override def hasEdge(n1: Node, n2: Node): Boolean = graph.edges(n1, n2).nonEmpty

  override def getCycles: Set[Seq[Node]] = ???

  override def getEdge(n1: Node, n2: Node): CSet[Edge] = ???

  override def getEdges(n: Node): CSet[Edge] = ???

  override def getIncomingEdges(node: Node): CSet[Edge] = ???

  override def getOutgoingEdges(node: Node): CSet[Edge] = ???

  override def getSuccessorNodes(node: Node): CSet[Node] = ???

  override def getPredecessorNodes(node: Node): CSet[Node] = ???

  override def getAllPaths(source: Node, sink: Node): Set[Set[Node]] = ???
}
