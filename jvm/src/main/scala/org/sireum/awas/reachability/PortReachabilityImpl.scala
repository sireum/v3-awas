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

import org.sireum.awas.collector.CollectorErrorHelper.{MISSING_CRITERIA, ReachAnalysisStage, errorMessageGen}
import org.sireum.awas.collector.{Collector, CollectorErrorHelper, FlowCollector, ResultType}
import org.sireum.awas.fptc.FlowNode.Edge
import org.sireum.awas.fptc.{FlowGraph, FlowNode}
import org.sireum.awas.query.{ConstraintExpr, ConstraintKind}
import org.sireum.awas.symbol.{SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

class PortReachabilityImpl[Node](st: SymbolTable, graph: FlowGraph[FlowNode]) extends
  BasicReachabilityImpl(st, graph) with PortReachability[FlowNode] {

  val H = SymbolTableHelper

  private var sccValidCache = imapEmpty[ISet[ResourceUri],
    (ISet[ResourceUri], Boolean)]

  override def forwardPortReach(criterion: FlowNode): Collector =
    forwardPortReachSet(criterion.ports.toSet)

  override def forwardPortReachSet(criterions: Set[ResourceUri]): Collector =
    portReach(criterions, isForward = true)

  override def forwardPortReach(criterion: ResourceUri): Collector =
    portReach(isetEmpty[ResourceUri] + criterion, isForward = true)

  def portReach(criteria: ISet[ResourceUri], isForward: Boolean): Collector = {
    var result = isetEmpty[ResourceUri]
    var worklist = ilistEmpty[ResourceUri]
    var resEdges = isetEmpty[Edge]
    var resError = isetEmpty[Tag]
    var resFlows = isetEmpty[ResourceUri]

    criteria.foreach { p =>
      if (H.isPort(p) && graph.getNode(p).isDefined) {
        worklist = worklist :+ p
      } else {
        resError += errorMessageGen(MISSING_CRITERIA,
          p,
          ReachAnalysisStage.Port)
      }
    }

    while (worklist.nonEmpty) {
        val current = worklist.head
      if (!result.contains(current)) {
        val temp = if (isForward) graph.getSuccessorPorts(current) else graph.getPredecessorPorts(current)
        worklist = worklist ++ temp.ports
        resEdges = resEdges ++ temp.edges
        resFlows = resFlows ++ temp.flows
        resError = resError ++ temp.errors
      }
        worklist = worklist.tail
        result += current
      }

    Collector(st, graph, result, resFlows, resEdges, isForward, criteria, resError)
  }

  override def backwardPortReach(criterion: FlowNode): Collector =
    backwardPortReachSet(criterion.ports.toSet)

  override def backwardPortReachSet(criterions: Set[ResourceUri]): Collector =
    portReach(criterions, isForward = false)

  override def backwardPortReach(criterion: ResourceUri): Collector =
    portReach(isetEmpty[ResourceUri] + criterion, isForward = false)

  override def forwardReachSet(criterion: Set[ResourceUri]): Collector = {
    criterion.foldLeft(Collector(st, graph))((c: Collector, r: ResourceUri) =>
      c union forwardReach(r))
  }

  override def forwardReach(criterion: ResourceUri): Collector = {
    if (isNode(criterion)) {
      val onode = graph.getNode(criterion)
      forwardReach(onode.get)
    } else {
      forwardPortReach(criterion)
    }
  }

  private def isNode(uri: ResourceUri): Boolean = {
    //someday we have to switch to the below comment code
    //graph.nodes.find(_.getUri == uri)
    if (uri.startsWith(H.COMPONENT_TYPE) ||
      uri.startsWith(H.CONNECTION_TYPE)) {
      true
    } else {
      false
    }
  }

  override def backwardReachSet(criterion: Set[ResourceUri]): Collector = {
    criterion.foldLeft(Collector(st, graph))((c: Collector, r: ResourceUri) =>
      c union backwardReach(r))
  }

  override def backwardReach(criterion: ResourceUri): Collector = {
    if (isNode(criterion)) {
      val onode = graph.getNode(criterion)
      backwardReach(onode.get)
    } else {
      backwardPortReach(criterion)
    }
  }

  def reachPathSet(source: Set[ResourceUri], target: Set[ResourceUri]): Collector = {
    val temp = source.foldLeft(Collector(st, graph))((c, n) => c union
      target.foldLeft(Collector(st, graph))((c2, n2) => c2 union reachPath(n, n2)))
    temp
  }



  override def reachPathSet(source: Set[ResourceUri],
                            target: Set[ResourceUri],
                            constraint: ConstraintExpr): Collector = {
    val temp = source.foldLeft(Collector(st, graph))((c, n) => c union
      target.foldLeft(Collector(st, graph))((c2, n2) => c2 union reachPathWith(n, n2, constraint)))
    temp
  }

  private def nodeReachPath(source: ResourceUri, target: ResourceUri, cExp : Option[ConstraintExpr]): Collector = {
    val snode = graph.getNode(source)
    val tnode = graph.getNode(target)
    if (snode.isDefined && tnode.isDefined) {
      if(cExp.isDefined) {
        reachPath(snode.get, tnode.get, cExp.get)
      } else {
        reachPath(snode.get, tnode.get)
      }
    } else {
      Collector(st, graph,
        isetEmpty[Tag] + errorMessageGen(CollectorErrorHelper.MISSING_NODE, source, ReachAnalysisStage.Node) +
          errorMessageGen(CollectorErrorHelper.MISSING_NODE, target, ReachAnalysisStage.Node))
    }
  }

  def reachPathWith(source: ResourceUri, target: ResourceUri, constraint: ConstraintExpr): Collector = {
    if (isNode(source) || isNode(target)) {
      nodeReachPath(source, target, Some(constraint))
    }
    else {
      val snode = graph.getNode(source)
      val tnode = graph.getNode(target)
      if (snode.isDefined && tnode.isDefined) {
        val scc = graph.getCycles.map(_.toSet)
        val sccPorts = scc.map(getPortsFromNodes)
        var pathPorts = isetEmpty[(ISet[Edge], ISet[ResourceUri])]
        if (snode.get != tnode.get) {
          graph.getAllPathsEdges(snode.get, tnode.get).foreach { it: Seq[Edge] =>
            val tempPorts = (it.flatMap(_.sourcePort) union it.flatMap(_.targetPort)).toSet
            val start = if (H.isInPort(source)) graph.getSuccessorPorts(source).ports else isetEmpty[ResourceUri] + source
            val end = if (H.isOutPort(target)) graph.getPredecessorPorts(target).ports else isetEmpty[ResourceUri] + target
            if (tempPorts.intersect(start).nonEmpty && tempPorts.intersect(end).nonEmpty) {
              pathPorts = pathPorts.+((it.toSet, tempPorts))
            }
          }
        } else {
          pathPorts = scc.filter(_.contains(snode.get)).map(getPortsFromNodes).filter(it =>
            it._2.contains(source) && it._2.contains(target))
        }
        val pathCyclePorts = pathPorts.map(it => (it, sccPorts.filter(_._2.intersect(it._2).nonEmpty)))
        var paths = getPaths(pathCyclePorts, source, target, Some(constraint))

        val pathCollectors = paths.toVector.map(it => Collector(st, graph, isetEmpty[FlowNode],
          it.ports, ResultType.Port, it.edges, it.flows,
          isetEmpty[ResourceUri] + source + target, isetEmpty[Tag]))

        val c = Collector(st, graph, pathCollectors, Some(ResultType.Port))
        c
      } else {
        Collector(st, graph,
          isetEmpty[Tag] + errorMessageGen(CollectorErrorHelper.MISSING_PORT, source, ReachAnalysisStage.Port) +
            errorMessageGen(CollectorErrorHelper.MISSING_PORT, target, ReachAnalysisStage.Port))
      }
    }

  }

  /**
    * This method computes paths from source port to target ports
    * There are two kinds of paths, simple and with cycle
    * a simple path, is the one without any cycles in it
    * a cycle path, is a simple path + all the cycles relevant to the simple path
    *
    * @param source port
    * @param target port
    * @return collector with set of paths
    */
  def reachPath(source: ResourceUri, target: ResourceUri): Collector = {
    if (isNode(source) || isNode(target)) {
      nodeReachPath(source, target, None)
    } else {
      val snode = graph.getNode(source)
      val tnode = graph.getNode(target)
      if (snode.isDefined && tnode.isDefined) {
        val scc = graph.getCycles.map(_.toSet)
        val sccPorts = scc.map(getPortsFromNodes)
        var pathPorts = isetEmpty[(ISet[Edge], ISet[ResourceUri])]
        if (snode.get != tnode.get) {
          graph.getAllPathsEdges(snode.get, tnode.get).foreach { it: Seq[Edge] =>
            val tempPorts = (it.flatMap(_.sourcePort) union it.flatMap(_.targetPort)).toSet
            val start = if (H.isInPort(source)) graph.getSuccessorPorts(source).ports else isetEmpty[ResourceUri] + source
            val end = if (H.isOutPort(target)) graph.getPredecessorPorts(target).ports else isetEmpty[ResourceUri] + target
            if (tempPorts.intersect(start).nonEmpty && tempPorts.intersect(end).nonEmpty) {
              pathPorts = pathPorts.+((it.toSet, tempPorts))
            }
          }
        } else {
          pathPorts = scc.filter(_.contains(snode.get)).map(getPortsFromNodes).filter(it =>
            it._2.contains(source) && it._2.contains(target))
        }
        val pathCyclePorts = pathPorts.map(it => (it, sccPorts.filter(_._2.intersect(it._2).nonEmpty)))
        var paths = getPaths(pathCyclePorts, source, target, None)

        val pathCollectors = paths.toVector.map(it => Collector(st, graph, isetEmpty[FlowNode],
          it.ports, ResultType.Port, it.edges, it.flows,
          isetEmpty[ResourceUri] + source + target, isetEmpty[Tag]))

        val c = Collector(st, graph, pathCollectors, Some(ResultType.Port))
        c
      } else {
        Collector(st, graph,
          isetEmpty[Tag] + errorMessageGen(CollectorErrorHelper.MISSING_PORT, source, ReachAnalysisStage.Port) +
            errorMessageGen(CollectorErrorHelper.MISSING_PORT, target, ReachAnalysisStage.Port))
      }
    }
  }

  private def getPaths(pathCyclePorts: ISet[((ISet[Edge], ISet[ResourceUri]),
    ISet[(ISet[Edge], ISet[ResourceUri])])],
                       source : ResourceUri,
                       target: ResourceUri,
                       constraint: Option[ConstraintExpr]): ISet[FlowCollector] = {


    var paths = isetEmpty[FlowCollector]
    pathCyclePorts.foreach { it =>
      var simplePathPorts: ISet[ResourceUri] = if (H.isInPort(source)) it._1._2 + source else it._1._2 - source
      simplePathPorts = if (H.isOutPort(target)) simplePathPorts + target else simplePathPorts - target
      val simplePathEdges = it._1._1
      val cycles = it._2
      val (simpleFlows, valid) = getPathEdgesFlowsValidity(simplePathPorts)
      simplePathPorts = simplePathPorts + source + target

      if (valid ) {
        val validCycles = cycles.filter(it => getPathEdgesFlowsValidity(it._2)._2)
        val simplePath = FlowCollector(simplePathPorts,
          simplePathEdges, simpleFlows, isetEmpty[Tag])
        if(constraint.isEmpty) {
          paths = paths + simplePath
          paths = paths + getComplexPath(simplePath, validCycles)
        } else {
          val con = constraint.get.simple.get
          constraint.get.kind match {
            case ConstraintKind.All => {
              if(con.getPorts.subsetOf(simplePath.ports)) {
                paths = paths + simplePath
                paths = paths + getComplexPath(simplePath, validCycles)
              } else {
                val tPath = getComplexPath(simplePath, validCycles)
                if(con.getPorts.subsetOf(tPath.ports)) {
                  paths = paths + tPath
                }
              }
            }
            case ConstraintKind.Some => {
              if(simplePath.ports.intersect(con.getPorts).nonEmpty) {
                paths = paths + simplePath
                paths = paths + getComplexPath(simplePath, validCycles)
              } else {
                val tPath = getComplexPath(simplePath, validCycles)
                if(tPath.ports.intersect(con.getPorts).nonEmpty) {
                  paths = paths + tPath
                }
              }
            }
            case ConstraintKind.None => {
              if(simplePath.ports.intersect(con.getPorts).isEmpty) {
                paths = paths + simplePath
                paths = paths + getComplexPath(simplePath,
                  validCycles.filter(_._2.intersect(con.getPorts).isEmpty))
              }
            }
          }
        }

      }
    }
    paths
  }

  private def getComplexPath(simplePath: FlowCollector,
                             validCycles: ISet[(ISet[Edge], ISet[ResourceUri])]):FlowCollector = {
    val ports = simplePath.ports ++
      validCycles.foldLeft(isetEmpty[ResourceUri])((c, n) => c union n._2)
    val edges = simplePath.edges ++ validCycles.foldLeft(isetEmpty[Edge])((c, n) => n._1 union c)
    val flows = simplePath.flows ++ validCycles.foldLeft(isetEmpty[ResourceUri])((c, n) =>
      getPathEdgesFlowsValidity(n._2)._1 union c)
     FlowCollector(ports, edges, flows, isetEmpty[Tag])
  }

  private def getPathEdgesFlowsValidity(ports: ISet[ResourceUri])
  : (ISet[ResourceUri], Boolean) = {
    if (sccValidCache.contains(ports)) {
      sccValidCache(ports)
    } else {
      val temp = getPathFlows(ports)
      sccValidCache = sccValidCache + (ports -> temp)
      temp
    }
  }

  private def getPortsFromNodes(nodes: ISet[FlowNode]): (ISet[Edge], ISet[ResourceUri]) = {
    val edges = nodes.flatMap(it =>
      graph.getOutgoingEdges(it).filter(it2 =>
        nodes.contains(it2.target)))
    (edges, edges.flatMap(_.sourcePort) union edges.flatMap(_.targetPort))
  }

  def getPathFlows(ports: ISet[ResourceUri]): (ISet[ResourceUri], Boolean) = {
    var compPortMap = imapEmpty[ResourceUri, ISet[ResourceUri]]
    var res = isetEmpty[ResourceUri]
    var goodness = true
    ports.foreach { p =>
      val node = graph.getNode(p)
      if (node.isDefined) {
        val nodeUri = node.get.getUri
        compPortMap = compPortMap +
          (nodeUri -> (compPortMap.getOrElse(nodeUri, isetEmpty[ResourceUri]) + p))
      }
    }

    compPortMap.foreach { v =>
      val inports = v._2.filter(H.isInPort)
      val outPorts = v._2.filter(H.isOutPort)

      val node = graph.getNode(v._1)
      if (node.isDefined) {
        val inFlows = if (node.get.isComponent)
          inports.flatMap(it => st.componentTable(v._1).getFlowsFromPort(it))
        else inports.flatMap(it => st.connectionTable(v._1).getFlowsFromPort(it))
        val outFlows = if (node.get.isComponent)
          outPorts.flatMap(it => st.componentTable(v._1).getFlowsFromPort(it))
        else outPorts.flatMap(it => st.connectionTable(v._1).getFlowsFromPort(it))
        val ans = inFlows intersect outFlows
        if (goodness &&
          inports.nonEmpty &&
          outPorts.nonEmpty &&
          ans.isEmpty &&
          node.get.isFlowDefined) {
          goodness = false
        }

        res = res ++ ans
      }
    }
    (res, goodness)
  }

  //  /**
  //    * Uses the node path to find port path
  //    *
  //    * @param path set of nodes
  //    * @return port path for a node path
  //    */
  //  private def getPathPorts(path: ISet[ResourceUri]): ISet[ResourceUri] = {
  //    var result = isetEmpty[ResourceUri]
  //    path.foreach { nuri =>
  //      val node = graph.getNode(nuri)
  //      if (node.isDefined) {
  //        node.get.inPorts.foreach { p =>
  //          if (path.intersect(graph.getPredecessorPorts(p).flatMap { f: ResourceUri =>
  //            graph.getNode(f)
  //          }.toSet[FlowNode].map(_.getUri)).nonEmpty) {
  //            result += p
  //          }
  //        }
  //        node.get.outPorts.foreach { p =>
  //          if (path.intersect(graph.getSuccessorPorts(p).flatMap { f: ResourceUri =>
  //            graph.getNode(f)
  //          }.toSet[FlowNode].map(_.getUri)).nonEmpty) {
  //            result += p
  //          }
  //        }
  //      }
  //    }
  //    result
  //  }

}

