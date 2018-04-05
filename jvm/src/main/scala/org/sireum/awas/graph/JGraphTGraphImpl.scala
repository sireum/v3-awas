package org.sireum.awas.graph

import org.jgrapht.Graph
import org.jgrapht.alg.shortestpath.AllDirectedPaths
import org.jgrapht.graph.DefaultDirectedGraph
import org.sireum.util.CSet

class JGraphTGraphImpl[Node, Edge <: AwasEdge[Node]]
(ef: AwasEdgeFactory[Node, Edge])
  extends AwasGraph[Node, Edge] with AwasGraphUpdate[Node, Edge] {
  self: AwasGraph[Node, Edge] =>
  // override type Edge = EdgeT

  val graph: Graph[Node, Edge] = {
    new DefaultDirectedGraph[Node, Edge](
      (source: Node, target: Node) => ef.createEdge(self, source, target)
    )
  }

  def nodes: Iterable[Node] = {
    import scala.collection.JavaConverters._
    val res = graph.vertexSet().asScala
    res
  }

  def numOfNodes: Int = graph.vertexSet().size()

  def edges: Iterable[Edge] = {
    import scala.collection.JavaConverters._
    graph.edgeSet().asScala
  }

  def numOfEdges: Int = graph.edgeSet().size()

  def hasNode(n: Node): Boolean = graph.containsVertex(n)

  //  def getNode(n : Node) : Node = graph.get

  def hasEdge(n1: Node, n2: Node): Boolean = {
    graph.containsEdge(n1, n2)
  }

  //  def getCycles: Set[Seq[Node]] = {
  //    import org.jgrapht.alg.cycle._
  //
  //    import scala.collection.JavaConverters._
  //    new SzwarcfiterLauerSimpleCycles[Node, Edge](graph)
  //      .findSimpleCycles().asScala.toSet.map((it: java.util.List[Node]) => it.asScala)
  //  }

  def getEdge(n1: Node, n2: Node): CSet[Edge] = {
    import scala.collection.JavaConverters._
    graph.getAllEdges(n1, n2).asScala
  }

  def getEdges(n: Node): CSet[Edge] = {
    import scala.collection.JavaConverters._
    graph.edgesOf(n).asScala
  }

  def getIncomingEdges(node: Node): CSet[Edge] = {
    import scala.collection.JavaConverters._
    graph.incomingEdgesOf(node).asScala
  }

  def getOutgoingEdges(node: Node): CSet[Edge] = {
    import scala.collection.JavaConverters._
    graph.outgoingEdgesOf(node).asScala
  }

  def getSuccessorNodes(node: Node): CSet[Node] = {
    import scala.collection.JavaConverters._
    graph.outgoingEdgesOf(node).asScala.map(x => x.target)
  }

  def getPredecessorNodes(node: Node): CSet[Node] = {
    import scala.collection.JavaConverters._
    graph.incomingEdgesOf(node).asScala.map(_.source)
  }

  def getAllPaths(source: Node, sink: Node):
  Set[Set[Node]] = {
    import scala.collection.JavaConverters._
    val allGraphPath = new AllDirectedPaths[Node, Edge](graph)
    val pathNodes = allGraphPath.getAllPaths(source, sink, true, null).
      asScala.map(_.getVertexList.asScala.toSet)
    pathNodes.toSet
  }

  override def getNode(n: Node): Node = n

  override def addNode(n: Node): Node = {
    graph.addVertex(n)
    n
  }

  override def addEdge(from: Node, to: Node, data: Edge): Edge = {
    graph.addEdge(from, to, data)
    data
  }
}

//object JGraphTGraphImpl {
//
//}
//
//case class JGraphTAwasEdgeImpl[Node](src: Node, snk: Node) extends AwasEdge[Node] {
//  self: AwasEdge[Node] =>
//  override def source: Node = src
//
//  override def target: Node = snk
//}

