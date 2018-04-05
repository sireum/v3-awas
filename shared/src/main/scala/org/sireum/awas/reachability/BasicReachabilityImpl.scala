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

import org.sireum.awas.collector
import org.sireum.awas.collector.CollectorErrorHelper._
import org.sireum.awas.collector._
import org.sireum.awas.fptc.FlowNode._
import org.sireum.awas.fptc.{FlowEdge, FlowGraph, FlowNode, NodeType}
import org.sireum.awas.query.{ConstraintExpr, ConstraintKind}
import org.sireum.awas.symbol.{SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

class BasicReachabilityImpl(st: SymbolTable, graph: FlowGraph[FlowNode, FlowEdge[FlowNode]])
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
    val graphNodes = criteria.map(n => (n.getOwner, n)).groupBy(_._1).mapValues(_.map(_._2))
    if (graphNodes.size > 1) {
      graphNodes.map { ent =>
        new BasicReachabilityImpl(st, ent._1).reach(ent._2, isForward)
      }.fold(Collector.buildEmpty(st))((x, y) => x.union(y))
    } else {
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
          workList = workList ++ next ++ next.flatMap(getAllSubNode)
          result += current
          resEdges ++= next.flatMap(it => if (isForward) graph.getEdge(current, it)
          else graph.getEdge(it, current))
        }
        workList = workList.tail
      }

      val portNodes = if (isForward)
        result.filter(_.getResourceType == NodeType.PORT).filter(it => H.isOutPort(it.getUri))
      else
        result.filter(_.getResourceType == NodeType.PORT).filter(it => H.isInPort(it.getUri))
      val parenNodes = if (portNodes.nonEmpty && FlowNode.getNode(graph.getUri).isDefined) {
        portNodes.flatMap(x => FlowNode.getNode(graph.getUri).get.getOwner.getEdgeForPort(x.getUri)).
          map(it => if (isForward) it.target else it.source)
      } else {
        isetEmpty[FlowNode]
      }

      if (parenNodes.nonEmpty) {
        collector.Collector(st, isetEmpty + graph, result + FlowNode.getNode(graph.getUri).get,
          resEdges, isForward, criteria.map(_.getUri), resError).union(
          new BasicReachabilityImpl(st, FlowNode.getNode(graph.getUri).get.getOwner)
            .reach(parenNodes, isForward))
      } else {
        collector.Collector(st, isetEmpty + graph, result, resEdges, isForward,
          criteria.map(_.getUri), resError)
      }
    }
  }


  private def getAllSubNode(given: FlowNode): ISet[FlowNode] = {
    var result = isetEmpty[FlowNode]
    if (given.getSubGraph.isDefined) {
      var workList: Seq[FlowNode] = given.getSubGraph.get.nodes.map(x =>
        x.asInstanceOf[FlowNode]).toSeq
      while (workList.nonEmpty) {
        //no need to check seen, due to lack of cycle, in fact it is a tree
        val current = workList.head
        result = result + current
        if (current.getSubGraph.isDefined) {
          workList = workList ++ current.getSubGraph.get.nodes.map(_.asInstanceOf[FlowNode])
        }
        workList = workList.tail
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
    result.push(node.getOwner)
    while (result.head.getUri != systemuri) {
      assert(FlowNode.getNode(result.head.getUri).isDefined)
      result = result.push(FlowNode.getNode(result.head.getUri).get.getOwner)
    }
    result
  }

  private def findRelaventGraphs(src: Seq[FlowGraph[FlowNode, Edge]],
                                 dst: Seq[FlowGraph[FlowNode, Edge]]) = {
    val minSize = if (src.size < dst.size) src.size else dst.size
    var common = ilistEmpty[FlowGraph[FlowNode, Edge]]
    for (i <- 0 to minSize) {
      if (src(i) == dst(i)) {
        common = common :+ src(i)
      }
    }
    common = common.dropRight(1)
    src.toSet ++ dst.toSet -- common.toSet
  }

  case class NodeReachPath(graphs: ISet[Graph],
                           nodes: ISeq[FlowNode],
                           edges: ISeq[Edge])

  def simpleReachPath(source: FlowNode, target: FlowNode): Collector = {
    val srcAn = getAncestors(source)
    val dstAn = getAncestors(target)
    val relavent = findRelaventGraphs(srcAn, dstAn)

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
      collector.Collector(st,
        graphs = r.graphs.toSet,
        nodes = r.nodes.toSet,
        ports = isetEmpty[ResourceUri],
        resType = ResultType.Node,
        edges = r.edges.toSet,
        flows = isetEmpty[ResourceUri],
        criteria = isetEmpty[ResourceUri] + source.getUri + target.getUri,
        error = isetEmpty[Tag]))

    val allGraphs = result.flatMap(_.graphs).toSet
    collector.Collector(st, allGraphs, pathColls, Some(ResultType.Node))
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
          if ((c intersect x.getNodes).nonEmpty) c else isetEmpty[FlowNode]))).toMap

      val complexPaths: ISeq[Collector] = pathCycleMap.map { it =>
        collector.Collector(st,
          graphs = it._1.getGraphs,
          nodes = it._1.getNodes union it._2,
          ports = isetEmpty[ResourceUri],
          resType = ResultType.Node,
          edges = it._1.getEdges union getEdgesInPath(it._2),
          flows = isetEmpty[ResourceUri],
          criteria = isetEmpty[ResourceUri] + source.getUri + target.getUri,
          error = isetEmpty[Tag])
      }.toVector

      Collector(st,
        pathNodes.getGraphs,
        complexPaths ++ pathNodes.getPaths,
        Some(ResultType.Node))
    } else {
      val paths = source.getOwner.getCycles
        .filter(_.contains(source)).map {
        c =>
          collector.Collector(st,
            isetEmpty + source.getOwner,
            c.toSet,
            isetEmpty[ResourceUri],
            ResultType.Node,
            getEdgesInPath(c.toSet), isetEmpty[ResourceUri],
            isetEmpty[ResourceUri] + source.getUri + target.getUri,
            isetEmpty[Tag])
      }.toVector

      collector.Collector(st, isetEmpty + source.getOwner, paths, Some(ResultType.Node))
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
    Collector(st, paths.flatMap(_.getGraphs).toSet, paths.toVector, Some(ResultType.Node))
  }

  def getEdgesInPath(pathNodes: ISet[FlowNode]): Set[Edge] = {
    pathNodes.flatMap(it => it.getOwner.getOutgoingEdges(it)
      .filter(it2 => pathNodes.contains(it2.target)))
  }
}