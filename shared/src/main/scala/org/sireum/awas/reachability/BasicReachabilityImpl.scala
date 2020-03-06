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

package org.sireum.awas.reachability

import org.sireum.awas.collector
import org.sireum.awas.collector._
import org.sireum.awas.flow.FlowNode._
import org.sireum.awas.flow.{FlowEdge, FlowGraph, FlowNode, NodeType}
import org.sireum.awas.query.{ConstraintExpr, ConstraintKind}
import org.sireum.awas.symbol.{SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

class BasicReachabilityImpl(st: SymbolTable)
  extends BasicReachability[FlowNode] {
  type Graph = FlowGraph[FlowNode, FlowEdge[FlowNode]]
  val H = SymbolTableHelper

  /**
    * Returns the forward reachability/slice for the set of criterion
    *
    * @param criterion Set of graph nodes to which reachability is computed
    * @return a [[ISet]] of reached nodes or empty set, in case nothing to be reached
    */
  override def forwardReachSetNode(criterion: Set[FlowNode]): Collector = {
    reach(criterion, isForward = true)
  }

  /**
    * Returns the forward reachability/slice of the criterion
    *
    * @param criterion Graph node from which reachability is computed
    * @return [[ISet]] of reached nodes or empty set, in case nothing to be reached
    */
  override def forwardReach(criterion: FlowNode): Collector = {
    reach(isetEmpty[FlowNode] + criterion, isForward = true)
  }

  private def nextNode(node: FlowNode): (ISet[FlowNode], ISet[Edge], ISet[Graph]) = {
    var resNodes = isetEmpty[FlowNode]
    var resEdge = isetEmpty[Edge]
    var resGraph = isetEmpty[Graph]
    val curGraph = node.getOwner
    if (node.getResourceType == NodeType.PORT &&
      H.isOutPort(node.getUri) &&
      FlowNode.getNode(curGraph.getUri).isDefined) {
      val parNode = FlowNode.getNode(curGraph.getUri).get
      val edges = parNode.getOwner.getOutgoingEdges(parNode).filter(e =>
        e.sourcePort.isDefined && e.sourcePort.get == node.getUri)

      resEdge = resEdge ++ edges
      resNodes = resNodes ++ edges.map(_.target)
      resGraph += parNode.getOwner
    } else {
      resNodes = resNodes ++ curGraph.getSuccessorNodes(node)
      resEdge = resEdge ++ curGraph.getOutgoingEdges(node)
      resGraph += curGraph
    }
    (resNodes, resEdge, resGraph)
  }

  private def previousNode(node: FlowNode): (ISet[FlowNode], ISet[Edge], ISet[Graph]) = {
    var resNodes = isetEmpty[FlowNode]
    var resEdge = isetEmpty[Edge]
    var resGraph = isetEmpty[Graph]
    val curGraph = node.getOwner
    if (node.getResourceType == NodeType.PORT &&
      H.isInPort(node.getUri) &&
      FlowNode.getNode(curGraph.getUri).isDefined) {
      val parNode = FlowNode.getNode(curGraph.getUri).get
      val edges = parNode.getOwner.getIncomingEdges(parNode).filter(e =>
        e.targetPort.isDefined && e.targetPort.get == node.getUri)
      resEdge = resEdge ++ edges
      resNodes = resNodes ++ edges.map(_.source)
      resGraph += parNode.getOwner
    } else {
      resNodes = resNodes ++ curGraph.getPredecessorNodes(node)
      resEdge = resEdge ++ curGraph.getIncomingEdges(node)
      resGraph += curGraph
    }
    (resNodes, resEdge, resGraph)
  }

  /**
    * Returns the transitive closure of the given node in the specified direction
    *
    * @param criteria  Starting point for the closure
    * @param isForward direction to compute closure
    * @return Set of reachable nodes
    */
  private def reach(criteria: ISet[FlowNode], isForward: Boolean)
  : Collector = {
    var result = isetEmpty[FlowNode]
    var workList = ilistEmpty[FlowNode] ++ criteria
    var resEdges = isetEmpty[Edge]
    var resError = isetEmpty[Tag]
    var resGraph = isetEmpty[FlowGraph[FlowNode, FlowNode.Edge]]

    while (workList.nonEmpty) {
      val curr = workList.head
      if (!result.contains(curr)) {
        val (n, e, g) = if (isForward) nextNode(curr) else previousNode(curr)
        result += curr
        resEdges = resEdges ++ e
        resGraph = resGraph ++ g
        n.foreach(nn => workList = workList :+ nn)
      }
      workList = workList.tail
    }
    val subGraphs = getAllSubGraphs(result)
    resEdges = resEdges ++ subGraphs.flatMap(_.edges)
    resGraph = resGraph ++ subGraphs
    result = result ++ subGraphs.flatMap(_.nodes)
    collector.Collector(resGraph, result, resEdges, isForward,
      criteria.map(_.getUri), resError)
  }

  private def getAllSubGraphs(given: ISet[FlowNode]): ISet[FlowGraph[FlowNode, FlowNode.Edge]] = {
    var result = isetEmpty[FlowGraph[FlowNode, FlowNode.Edge]]
    var workList = ilistEmpty[FlowGraph[FlowNode, FlowNode.Edge]]
    workList = workList ++ given.flatMap(_.getSubGraph)


    while (workList.nonEmpty) {
      val current = workList.head
      result = result + current
      workList = workList ++ current.nodes.flatMap(_.getSubGraph)
      workList = workList.tail
    }
    result
  }

  /**
    * Returns the backward reachability/slice for the set of criterion
    *
    * @param criterions Set of graph nodes to which reachability is computed
    * @return a [[ISet]] of reached nodes or empty set, in case nothing to be reached
    */
  override def backwardReachSetNode(criterions: Set[FlowNode]): Collector = {
    reach(criterions, isForward = false)
  }

  /**
    * Returns the backward reachability/slice of the criterion
    *
    * @param criterion Graph node to which reachability is computed
    * @return a [[ISet]] of reached nodes or empty set, in case nothing to be reached
    */
  override def backwardReach(criterion: FlowNode): Collector = {
    reach(isetEmpty[FlowNode] + criterion, isForward = false)
  }

  private def getAncestors(node: FlowNode): Seq[FlowGraph[FlowNode, Edge]] = {
    val systemuri = st.system
    var result = ilistEmpty[FlowGraph[FlowNode, Edge]]
    result = result.push(node.getOwner)
    while (result.head.getUri != systemuri) {
      assert(FlowNode.getNode(result.head.getUri).isDefined)
      result = result.push(FlowNode.getNode(result.head.getUri).get.getOwner)
    }
    result
  }

  protected def findRelaventGraphs(source: FlowNode,
                                   target: FlowNode)
  : ISet[Graph] = {
    val srcAn = getAncestors(source)
    val dstAn = getAncestors(target)

    val minSize = if (srcAn.size < dstAn.size) srcAn.size else dstAn.size
    var common = ilistEmpty[FlowGraph[FlowNode, Edge]]
    for (i <- 0 until minSize) {
      if (srcAn(i) == dstAn(i)) {
        common = common :+ srcAn(i)
      }
    }
    common = common.dropRight(1)
    val relaventGraph = srcAn.toSet ++ dstAn.toSet -- common.toSet

    relaventGraph ++ relaventGraph.flatMap(it => getAllSubGraphs(it.nodes.toSet))
  }

  case class NodeReachPath(graphs: ISet[Graph],
                           nodes: ISeq[FlowNode],
                           edges: ISeq[Edge])

  def simpleReachPath(source: FlowNode, target: FlowNode): Collector = {
    val relavent = findRelaventGraphs(source, target)

    var paths = ilistEmpty[NodeReachPath]
    var result = ilistEmpty[NodeReachPath]

    var workList = ilistEmpty[NodeReachPath]
    workList = workList :+ NodeReachPath(isetEmpty[Graph] + source.getOwner,
      ivectorEmpty[FlowNode] :+ source, ivectorEmpty[Edge])

    while (workList.nonEmpty) {
      val currPath = workList.head
      val currNode = currPath.nodes.last
      val currGraph = currNode.getOwner
      if (currNode != target) {
        if (currNode.getResourceType == NodeType.PORT &&
          H.isOutPort(currNode.getUri)) {
          if (FlowNode.getNode(currNode.getOwner.getUri).isDefined &&
            relavent.contains(FlowNode.getNode(currNode.getOwner.getUri).get.getOwner)) {
            val parentGraph = FlowNode.getNode(currNode.getOwner.getUri).get.getOwner
            parentGraph.getEdgeForPort(currNode.getUri).foreach { e =>
              if (!currPath.nodes.contains(e.target)) {
                workList = workList :+ NodeReachPath(currPath.graphs + parentGraph,
                  currPath.nodes :+ e.target, currPath.edges :+ e)
              }
            }
          }
        } else {
          if (relavent.map(_.getUri).contains(currNode.getUri)) {
            if (currPath.edges.nonEmpty &&
              currPath.edges.last.targetPort.isDefined) {
              val mayBeNext = FlowNode.getNode(currPath.edges.last.targetPort.get)
              if (mayBeNext.isDefined && !currPath.nodes.contains(mayBeNext.get)) {
                workList = workList :+ NodeReachPath(
                  currPath.graphs + mayBeNext.get.getOwner,
                  currPath.nodes :+ mayBeNext.get,
                  currPath.edges
                )
              }
            } else {
              val subGraph = relavent.find(_.getUri == currNode.getUri)
              if (subGraph.isDefined) {
                val nexts = subGraph.get.getInPortNodes
                workList = workList ++ nexts.flatMap(nn =>
                  if (!currPath.nodes.contains(nn)) {
                    Some(NodeReachPath(currPath.graphs + subGraph.get,
                      currPath.nodes :+ nn, currPath.edges))
                  } else None)
              }
            }
          } else {
            val nextNodes = currGraph.getSuccessorNodes(currNode)
            nextNodes.foreach { nn =>
              if (!currPath.nodes.contains(nn)) {
                workList = workList :+ NodeReachPath(currPath.graphs,
                  currPath.nodes :+ nn, currPath.edges ++ currGraph.getEdge(currNode, nn))
              }
            }
          }
        }
      } else {
        result = result :+ currPath
      }
      workList = workList.tail
    }

    val pathColls = result.map(r =>
      collector.Collector(
        graphs = r.graphs,
        nodes = r.nodes.toSet,
        ports = isetEmpty[ResourceUri],
        resType = ResultType.Node,
        edges = r.edges.toSet,
        flows = isetEmpty[ResourceUri],
        criteria = isetEmpty[ResourceUri] + source.getUri + target.getUri, false,
        error = isetEmpty[Tag]))

    val allGraphs = result.flatMap(_.graphs).toSet
    collector.Collector(allGraphs, ilinkedSetEmpty ++ pathColls, Some(ResultType.Node))
  }

  /**
    * Returns the set of paths from source to target
    *
    * @param source Starting node of paths
    * @param target Ending node of paths
    * @return a [[ISet]] of Paths, each path consists of a set of nodes
    *
    *
    *
    */
  def reachPath(source: FlowNode, target: FlowNode): Collector = {

    if (source != target) {

      val pathNodes = simpleReachPath(source, target)

      val scc = pathNodes.getGraphs.flatMap(_.getCycles).toSet

      val pathCycleMap = pathNodes.getPaths.map(x => (x,
        scc.flatMap(c =>
          if ((c intersect x.getNodes.toSeq).nonEmpty) c else isetEmpty[FlowNode]))).toMap

      val complexPaths: ISeq[Collector] = pathCycleMap.flatMap { it =>
        if (it._2.nonEmpty) {
          Some(collector.Collector(
            graphs = it._1.getGraphs,
            nodes = it._1.getNodes union it._2,
            ports = isetEmpty[ResourceUri],
            resType = ResultType.Node,
            edges = it._1.getEdges union getEdgesInPath(it._2),
            flows = isetEmpty[ResourceUri],
            criteria = isetEmpty[ResourceUri] + source.getUri + target.getUri, true,
            error = isetEmpty[Tag]))
        } else None
      }.toVector

      Collector(
        pathNodes.getGraphs,
        ilinkedSetEmpty ++ complexPaths ++ pathNodes.getPaths,
        Some(ResultType.Node))
    } else {
      val paths = source.getOwner.getCycles
        .filter(_.contains(source)).map {
        c =>
          collector.Collector(
            isetEmpty + source.getOwner,
            c.toSet,
            isetEmpty[ResourceUri],
            ResultType.Node,
            getEdgesInPath(c.toSet), isetEmpty[ResourceUri],
            isetEmpty[ResourceUri] + source.getUri + target.getUri, true,
            isetEmpty[Tag])
      }.toSet

      collector.Collector(isetEmpty + source.getOwner, ilinkedSetEmpty ++ paths, Some(ResultType.Node))
    }
  }

  def reachPath(source: FlowNode, target: FlowNode, constraint: ConstraintExpr): Collector = {
    val pathCollector = reachPath(source, target)
    val paths = constraint.kind match {
      case ConstraintKind.All => pathCollector.getPaths.filter(it =>
        constraint.simple.get.getNodes.subsetOf(it.getNodes))
      case ConstraintKind.Some => pathCollector.getPaths.filter(it =>
        constraint.simple.get.getNodes.intersect(it.getNodes).nonEmpty)
      case ConstraintKind.None => pathCollector.getPaths.filter(it =>
        constraint.simple.get.getNodes.intersect(it.getNodes).isEmpty)
    }
    Collector(paths.flatMap(_.getGraphs).toSet, ilinkedSetEmpty ++ paths.toSet, Some(ResultType.Node))
  }

  def getEdgesInPath(pathNodes: ISet[FlowNode]): Set[Edge] = {
    pathNodes.flatMap(it => it.getOwner.getOutgoingEdges(it)
      .filter(it2 => pathNodes.contains(it2.target)))
  }

  override def getSuccessor(current: FlowNode): ISet[FlowNode] = nextNode(current)._1

  override def getPredecessor(current: FlowNode): ISet[FlowNode] = previousNode(current)._1

  override def reachSimplePath(
    source: FlowNode,
    target: FlowNode
  ): Collector = {
    simpleReachPath(source, target)
  }
  override def reachSimplePath(
    source: FlowNode,
    target: FlowNode,
    constraint: ConstraintExpr
  ): Collector = {
    val pathCollector = reachSimplePath(source, target)
    val paths = constraint.kind match {
      case ConstraintKind.All => pathCollector.getPaths.filter(it =>
        constraint.simple.get.getNodes.subsetOf(it.getNodes))
      case ConstraintKind.Some => pathCollector.getPaths.filter(it =>
        constraint.simple.get.getNodes.intersect(it.getNodes).nonEmpty)
      case ConstraintKind.None => pathCollector.getPaths.filter(it =>
        constraint.simple.get.getNodes.intersect(it.getNodes).isEmpty)
    }
    Collector(paths.flatMap(_.getGraphs).toSet, ilinkedSetEmpty ++ paths.toSet, Some(ResultType.Node))
  }
}