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
import org.sireum.awas.fptc.{FlowGraph, FlowNode}
import org.sireum.util._

class BasicReachabilityImpl(graph: FlowGraph[FlowNode]) extends BasicReachability[FlowNode] {
  /**
    * Returns the forward reachability/slice for the set of criterion
    *
    * @param criterion Set of graph nodes to which reachability is computed
    * @return a [[ISet]] of reached nodes or empty set, in case nothing to be reached
    */
  override def forwardReachSetNode(criterion: Set[FlowNode]): ISet[FlowNode] = {
    criterion.flatMap(forwardReach)
  }

  /**
    * Returns the forward reachability/slice of the criterion
    *
    * @param criterion Graph node from which reachability is computed
    * @return [[ISet]] of reached nodes or empty set, in case nothing to be reached
    */
  override def forwardReach(criterion: FlowNode): ISet[FlowNode] = {
    reach(criterion, isForward = true)
  }

  /**
    * Returns the transitive closure of the given node in the specified direction
    * @param criterion Starting point for the closure
    * @param isForward direction to compute closure
    * @return Set of reachable nodes
    */
  private def reach(criterion: FlowNode, isForward: Boolean): ISet[FlowNode] = {
    var result = isetEmpty[FlowNode]
    var workList = ilistEmpty[FlowNode]
    if (graph.hasNode(criterion)) {
      workList = workList :+ criterion
      while (workList.nonEmpty) {
        val current = workList.head
        if (!result.contains(current))
          workList = workList ++ (if (isForward) graph.getSuccessorNodes(current)
          else graph.getPredecessorNodes(current))
        workList = workList.tail
        result += current
      }
    }
    result
  }

  /**
    * Returns the backward reachability/slice for the set of criterion
    *
    * @param criterions Set of graph nodes to which reachability is computed
    * @return a [[ISet]] of reached nodes or empty set, in case nothing to be reached
    */
  override def backwardReachSetNode(criterions: Set[FlowNode]): ISet[FlowNode] = {
    criterions.flatMap(backwardReach)
  }

  /**
    * Returns the backward reachability/slice of the criterion
    *
    * @param criterion Graph node to which reachability is computed
    * @return a [[ISet]] of reached nodes or empty set, in case nothing to be reached
    */
  override def backwardReach(criterion: FlowNode): ISet[FlowNode] = {
    reach(criterion, isForward = false)
  }

  /**
    * Returns the set of paths from source to target
    * @param source Starting node of paths
    * @param target Ending node of paths
    * @return a [[ISet]] of Paths, each path consists of a set of nodes
    */
  def reachPath(source : FlowNode, target:FlowNode): ISet[Set[FlowNode]] = {
    import org.jgrapht.alg.shortestpath._

    import scala.collection.JavaConverters._
    val allGraphPath = new AllDirectedPaths[FlowNode, graph.Edge](graph.graph)
    allGraphPath.getAllPaths(source,target,true,null).asScala
      .toSet.map((it: GraphPath[FlowNode, graph.Edge]) => it.getVertexList.asScala.toSet)
  }
}