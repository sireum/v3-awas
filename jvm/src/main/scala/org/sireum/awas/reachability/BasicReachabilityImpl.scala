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

package org.sireum.awas.reachability

import org.jgrapht.GraphPath
import org.sireum.awas.collector.CollectorErrorHelper._
import org.sireum.awas.collector._
import org.sireum.awas.fptc.FlowNode.Edge
import org.sireum.awas.fptc.{FlowGraph, FlowNode}
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

class BasicReachabilityImpl(st: SymbolTable, graph: FlowGraph[FlowNode]) extends BasicReachability[FlowNode] {
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

  /**
    * Returns the transitive closure of the given node in the specified direction
    *
    * @param criteria  Starting point for the closure
    * @param isForward direction to compute closure
    * @return Set of reachable nodes
    */
  private def reach(criteria: ISet[FlowNode], isForward: Boolean): Collector = {
    var result = isetEmpty[FlowNode]
    var workList = ilistEmpty[FlowNode]
    var resEdges = isetEmpty[Edge]
    var resError = isetEmpty[Tag]
    criteria.foreach { c =>
      if (graph.hasNode(c)) {
        workList = workList :+ c
      } else {
        resError += errorMessageGen(MISSING_CRITERIA,
          c.getUri,
          ReachAnalysisStage.Node)
      }
    }
    while (workList.nonEmpty) {
      val current = workList.head
      if (!result.contains(current)) {
        val next = if (isForward) graph.getSuccessorNodes(current)
        else graph.getPredecessorNodes(current)
        workList = workList ++ next
        result += current
        resEdges ++= next.flatMap(it => if (isForward) graph.getEdge(current, it)
        else graph.getEdge(it, current))
      }
      workList = workList.tail
    }

    Collector(st, graph, result, resEdges, isForward, criteria.map(_.getUri), resError)
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

  def manOf[T: Manifest](t: T): Manifest[T] = manifest[T]
  /**
    * Returns the set of paths from source to target
    *
    * @param source Starting node of paths
    * @param target Ending node of paths
    * @return a [[ISet]] of Paths, each path consists of a set of nodes
    */
  def reachPath(source: FlowNode, target: FlowNode): Collector = {
    import org.jgrapht.alg.shortestpath._

    import scala.collection.JavaConverters._
    if (source != target) {
      val allGraphPath = new AllDirectedPaths[FlowNode, graph.Edge](graph.graph)
      val scc = graph.getCycles.map(_.toSet).toSet

      val pathNodes = allGraphPath.getAllPaths(source, target, true, null).
        asScala.map(_.getVertexList.asScala.toSet)

      val temp = pathNodes.map(it => (it, scc.filter(_.intersect(it).nonEmpty).filterNot(it2 => it.subsetOf(it2))
        .foldLeft(isetEmpty[FlowNode])((c, n) => c.union(n)).union(it))).toMap.values.toSet

      val paths = temp.map(it2 =>
        Collector(st,
          graph,
          it2,
          isetEmpty[ResourceUri],
          ResultType.Node,
          getEdgesInPath(it2), isetEmpty[ResourceUri],
          isetEmpty[ResourceUri] + source.getUri + target.getUri,
          isetEmpty[Tag]
        ))
      Collector(st, graph, paths.toVector, Some(ResultType.Node))
    } else {
      val paths = graph.getCycles.filter(_.contains(source)).map {
        c =>
          Collector(st,
            graph,
            c.toSet,
            isetEmpty[ResourceUri],
            ResultType.Node,
            getEdgesInPath(c.toSet), isetEmpty[ResourceUri],
            isetEmpty[ResourceUri] + source.getUri + target.getUri,
            isetEmpty[Tag])
      }
      Collector(st, graph, paths.toVector, Some(ResultType.Node))
    }
  }

  def getEdgesInPath(pathNodes: ISet[FlowNode]): Set[Edge] = {
    pathNodes.flatMap(it => graph.getOutgoingEdges(it).filter(it2 => pathNodes.contains(it2.target)))
  }
}