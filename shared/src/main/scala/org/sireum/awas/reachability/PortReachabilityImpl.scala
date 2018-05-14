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
import org.sireum.awas.collector.{Collector, CollectorErrorHelper, FlowCollector, ResultType}
import org.sireum.awas.fptc.FlowNode.Edge
import org.sireum.awas.fptc.{FlowEdge, FlowGraph, FlowNode, NodeType}
import org.sireum.awas.query.{ConstraintExpr, ConstraintKind}
import org.sireum.awas.symbol.{Resource, SymbolTable}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

class PortReachabilityImpl[Node](st: SymbolTable)
  extends BasicReachabilityImpl(st) with PortReachability[FlowNode] {

  private var sccValidCache = imapEmpty[ISet[ResourceUri], (ISet[ResourceUri], Boolean)]

  override def forwardPortReach(criterion: FlowNode): Collector =
    forwardPortReachSet(criterion.ports.toSet)

  override def forwardPortReachSet(criterions: Set[ResourceUri]): Collector =
    portReach(criterions, isForward = true)

  override def forwardPortReach(criterion: ResourceUri): Collector =
    portReach(isetEmpty[ResourceUri] + criterion, isForward = true)

  def nextPorts(port: ResourceUri): ISet[FlowCollector] = {
    if (H.isInPort(port) && FlowNode.getNode(port).isDefined) {
      //two cases 1. part of two graphs or, 2. just one graph
      val portNode = FlowNode.getNode(port).get
      var succ = isetEmpty + portNode.getOwner.getSuccessorPorts(port)
      if (FlowNode.getNode(portNode.getOwner.getUri).isDefined) {
        succ = succ + FlowNode.getNode(portNode.getOwner.getUri).get.getOwner.getSuccessorPorts(port)
      }
      succ
    } else {
      //there is no port node for this port
      //if this is a port then there must be a parent uri
      assert(Resource.getParentUri(port).isDefined)
      val nodeUri = Resource.getParentUri(port).get
      if (nodeUri == st.system) {
        //if this port is a port of the system component, then there must be a port node
        isetEmpty + FlowNode.getNode(port).get.getOwner.getSuccessorPorts(port)
      } else {
        assert(FlowNode.getNode(nodeUri).isDefined)
        isetEmpty + FlowNode.getNode(nodeUri).get.getOwner.getSuccessorPorts(port)
      }
    }
  }

  def previousPorts(port: ResourceUri): ISet[FlowCollector] = {
    if (Resource.getParentUri(port).isDefined) {
      Resource.getParentUri(port).get
    }
    if (H.isOutPort(port) && FlowNode.getNode(port).isDefined) {
      //two cases 1. part of 2 graphs or, 2. just one graph
      val portNode = FlowNode.getNode(port).get
      var pred = isetEmpty + portNode.getOwner.getPredecessorPorts(port)
      if (FlowNode.getNode(portNode.getOwner.getUri).isDefined) {
        pred = pred + FlowNode.getNode(portNode.getOwner.getUri).get.getOwner.getPredecessorPorts(port)
      }
      pred
    } else {
      //there is no port node for this port
      //if this is a port then there must be a parent uri
      assert(Resource.getParentUri(port).isDefined)
      val nodeUri = Resource.getParentUri(port).get
      if (nodeUri == st.system) {
        //if this port is a port of the system component, then there must be a port node
        isetEmpty + FlowNode.getNode(port).get.getOwner.getPredecessorPorts(port)
      } else {
        assert(FlowNode.getNode(nodeUri).isDefined)
        isetEmpty + FlowNode.getNode(nodeUri).get.getOwner.getPredecessorPorts(port)
      }
    }
  }

  def portReach(criteria: ISet[ResourceUri], isForward: Boolean): Collector = {
    var result = isetEmpty[ResourceUri]
    var worklist = ilistEmpty[ResourceUri]
    var resGraphs = isetEmpty[Graph]
    var resEdges = isetEmpty[Edge]
    var resError = isetEmpty[Tag]
    var resFlows = isetEmpty[ResourceUri]

    criteria.foreach { p =>
      if (H.isPort(p)) {
        worklist = worklist :+ p
      } else {
        resError += errorMessageGen(MISSING_CRITERIA, p, ReachAnalysisStage.Port)
      }
    }

    while (worklist.nonEmpty) {
      val current = worklist.head
      if (!result.contains(current)) {
        val temp = if (isForward) nextPorts(current) else previousPorts(current)
        worklist = worklist ++ temp.flatMap(_.ports)
        resEdges = resEdges ++ temp.flatMap(_.edges)
        resFlows = resFlows ++ temp.flatMap(_.flows)
        resError = resError ++ temp.flatMap(_.errors)
        resGraphs = resGraphs ++ temp.flatMap(_.graph)
      }
      worklist = worklist.tail
      result += current
    }

    Collector(st, resGraphs, result, resFlows, resEdges, isForward, criteria, resError)
  }

  override def backwardPortReach(criterion: FlowNode): Collector =
    backwardPortReachSet(criterion.ports.toSet)

  override def backwardPortReachSet(criterions: Set[ResourceUri]): Collector =
    portReach(criterions, isForward = false)

  override def backwardPortReach(criterion: ResourceUri): Collector =
    portReach(isetEmpty[ResourceUri] + criterion, isForward = false)

  override def forwardReachSet(criterion: Set[ResourceUri]): Collector = {
    criterion.foldLeft(Collector(st))((c: Collector, r: ResourceUri) => c union forwardReach(r))
  }

  override def forwardReach(criterion: ResourceUri): Collector = {
    if (isNode(criterion)) {
      val onode = FlowNode.getNode(criterion)
      forwardReach(onode.get)
    } else {
      forwardPortReach(criterion)
    }
  }

  private def isNode(uri: ResourceUri): Boolean = {
    //someday we have to switch to the below comment code
    //graph.nodes.find(_.getUri == uri)
    if (FlowNode.getNode(uri).isDefined && (FlowNode.getNode(uri).get.getResourceType != NodeType.PORT)) {
      true
    } else {
      false
    }
  }

  override def backwardReachSet(criterion: Set[ResourceUri]): Collector = {
    criterion.foldLeft(Collector(st))((c: Collector, r: ResourceUri) => c union backwardReach(r))
  }

  override def backwardReach(criterion: ResourceUri): Collector = {
    if (isNode(criterion)) {
      val onode = FlowNode.getNode(criterion)
      backwardReach(onode.get)
    } else {
      backwardPortReach(criterion)
    }
  }

  def reachPathSet(source: Set[ResourceUri], target: Set[ResourceUri]): Collector = {
    val temp = source.foldLeft(Collector(st))(
      (c, n) =>
        c union
          target.foldLeft(Collector(st))((c2, n2) => c2 union reachPath(n, n2))
    )
    temp
  }

  override def reachPathSet(
                             source: Set[ResourceUri],
                             target: Set[ResourceUri],
                             constraint: ConstraintExpr
                           ): Collector = {
    val temp = source.foldLeft(Collector(st))(
      (c, n) =>
        c union
          target.foldLeft(Collector(st))((c2, n2) => c2 union reachPathWith(n, n2, constraint))
    )
    temp
  }

  private def nodeReachPath(source: ResourceUri, target: ResourceUri, cExp: Option[ConstraintExpr]): Collector = {

    def getNodeFromPUri(portUri: ResourceUri): Option[FlowNode] = {
      if (FlowNode.getNode(portUri).isDefined) {
        Some(FlowNode.getNode(portUri).get)
      } else if (H.isPort(portUri) && Resource.getParentUri(portUri).isDefined &&
        FlowNode.getNode(Resource.getParentUri(portUri).get).isDefined) {
        Some(FlowNode.getNode(Resource.getParentUri(portUri).get).get)
      } else {
        None
      }
    }

    val snode = getNodeFromPUri(source)
    val tnode = getNodeFromPUri(target)
    if (snode.isDefined && tnode.isDefined) {
      if (cExp.isDefined) {
        reachPath(snode.get, tnode.get, cExp.get)
      } else {
        reachPath(snode.get, tnode.get)
      }
    } else {
      collector.Collector(
        st,
        isetEmpty[FlowGraph[FlowNode, Edge]],
        isetEmpty[Tag] + errorMessageGen(CollectorErrorHelper.MISSING_NODE, source, ReachAnalysisStage.Node) +
          errorMessageGen(CollectorErrorHelper.MISSING_NODE, target, ReachAnalysisStage.Node)
      )
    }
  }

  def getNodesFromPort(portUri: ResourceUri): ISet[FlowNode] = {
    //two possible graphs
    var res = isetEmpty[FlowNode]
    if (FlowNode.getNode(portUri).isDefined) {
      res = res + FlowNode.getNode(portUri).get
    }

    if (Resource.getParentUri(portUri).isDefined &&
      FlowNode.getNode(Resource.getParentUri(portUri).get).isDefined) {
      res = res + FlowNode.getNode(Resource.getParentUri(portUri).get).get
    }
    res
  }

  def findRelaventGraphs(sources: ISet[FlowNode], targets: ISet[FlowNode]): ISet[Graph] = {
    sources.flatMap(s => targets.flatMap(t => findRelaventGraphs(s, t)))
  }

  def getSimpleNodePaths(sources: ISet[FlowNode], targets: ISet[FlowNode]): Collector = {
    sources.flatMap(s => targets.map(t => simpleReachPath(s, t))).fold(Collector(st))(_.union(_))
  }

  def nodePathToPortPaths(path: Collector, srcPort: ResourceUri,
                          dstPort: ResourceUri): ISet[Collector] = {

    val ports = path.getEdges.flatMap(_.sourcePort) ++
      path.getEdges.flatMap(_.targetPort) ++
      (if (H.isInPort(srcPort)) isetEmpty + srcPort else isetEmpty) ++
      (if (H.isOutPort(dstPort)) isetEmpty + dstPort else isetEmpty)

    var nodePortsMap = imapEmpty[FlowNode, ISet[ResourceUri]]

    ports.foreach { p =>
      for (elem <- getNodesFromPort(p)) {
        nodePortsMap += (elem -> (nodePortsMap.getOrElse(elem, isetEmpty[ResourceUri]) + p))
      }
    }
    assert(nodePortsMap.forall(p => p._2.size <= 2), "Simple path has more than two ports per node")

    //drop path case: path dropped only when there is a flow defined
    //for the inPort and the out port is not in it.
    val isValidPath = nodePortsMap.forall {
      case (n, ps) =>
        if (ps.size == 2 && n.isFlowDefined) {
          val inPort = ps.filter(H.isInPort).head
          if (ps.filter(H.isOutPort).isEmpty) {
            print("test")
          }
          val outPort = ps.filter(H.isOutPort).head
          val flows = n.getFlowsFromPort(inPort).map(it => n.getFlows(it))
          if (flows.nonEmpty && flows.exists(f => f.toPortUri.isDefined &&
            f.toPortUri.get == outPort)) {
            true
          } else {
            false
          }
        } else {
          true
        }
    }
    //only varying part is the flows field.
    if (isValidPath) {
      //starting with no flows
      var result = isetEmpty[Collector] + Collector(
        st,
        path.getGraphs,
        isetEmpty[FlowNode],
        ports,
        ResultType.Port,
        path.getEdges,
        isetEmpty[ResourceUri],
        isetEmpty[ResourceUri] + srcPort + dstPort,
        isetEmpty[Tag]
      )

      nodePortsMap.foreach {
        case (n, ps) =>
          if (n.isFlowDefined && ps.size == 2) {
            val inPort = ps.filter(H.isInPort).head
            val outPort = ps.filter(H.isOutPort).head
            val flows = n
              .getFlowsFromPort(inPort)
              .map(it => n.getFlows(it))
              .filter(f => f.toPortUri.isDefined && f.toPortUri.get == outPort)
            if (flows.nonEmpty) {
              result = result.flatMap(
                r =>
                  flows.map(
                    f =>
                      Collector(
                        st,
                        r.getGraphs,
                        isetEmpty[FlowNode],
                        r.getPorts,
                        r.getResultType.get,
                        r.getEdges,
                        r.getFlows + f.flowUri,
                        r.getCriteria,
                        r.getErrors ++ r.getWarnings
                      )
                  )
              )
            }
          }
      }
      result
      //case 1 - flows not defined:
    } else {
      isetEmpty[Collector]
    }
  }

  def cyclePortsToCollector(ports: ISet[ResourceUri], edges: ISet[Edge]): ISet[Collector] = {
    var nodePortsMap = imapEmpty[FlowNode, ISet[ResourceUri]]

    ports.foreach { p =>
      for (elem <- getNodesFromPort(p)) {
        nodePortsMap += (elem -> (nodePortsMap.getOrElse(elem, isetEmpty[ResourceUri]) + p))
      }
    }
    assert(nodePortsMap.forall(p => p._2.size <= 2), "cycle has more than two ports per node")

    //drop path case: path dropped only when there is a flow defined
    //for the inPort and the out port is not in it.
    val isValidPath = nodePortsMap.forall {
      case (n, ps) =>
        if (ps.size == 2 && n.isFlowDefined) {
          val inPort = ps.filter(H.isInPort).head
          val outPort = ps.filter(H.isOutPort).head
          val flows = n.getFlowsFromPort(inPort).map(it => n.getFlows(it))
          if (flows.nonEmpty && flows.exists(f => f.toPortUri.isDefined && f.toPortUri.get == outPort)) {
            true
          } else {
            false
          }
        } else {
          true
        }
    }

    if (isValidPath) {
      var result = isetEmpty[Collector] + Collector(
        st,
        isetEmpty,
        isetEmpty[FlowNode],
        ports,
        ResultType.Port,
        edges,
        isetEmpty[ResourceUri],
        isetEmpty[ResourceUri],
        isetEmpty[Tag]
      )

      nodePortsMap.foreach {
        case (n, ps) =>
          if (n.isFlowDefined && ps.size == 2) {
            val inPort = ps.filter(H.isInPort).head
            val outPort = ps.filter(H.isOutPort).head
            val flows = n
              .getFlowsFromPort(inPort)
              .map(it => n.getFlows(it))
              .filter(f => f.toPortUri.isDefined && f.toPortUri.get == outPort)
            if (flows.nonEmpty) {
              result = result.flatMap(
                r =>
                  flows.map(
                    f =>
                      Collector(
                        st,
                        r.getGraphs,
                        isetEmpty[FlowNode],
                        r.getPorts,
                        r.getResultType.get,
                        r.getEdges,
                        r.getFlows + f.flowUri,
                        r.getCriteria,
                        r.getErrors ++ r.getWarnings
                      )
                  )
              )
            }
          }
      }
      result
    } else {
      isetEmpty
    }
  }

  def computeSimplePaths(source: ResourceUri, target: ResourceUri): Collector = {
    assert(H.isPort(source) && H.isPort(target))
    val srcNodes = getNodesFromPort(source)
    val targetNodes = getNodesFromPort(target)
    val nodePaths = getSimpleNodePaths(srcNodes, targetNodes)
    val paths = nodePaths.getPaths.flatMap(p => nodePathToPortPaths(p, source, target))
    Collector(st, nodePaths.getGraphs, paths, Some(ResultType.Port))
  }

  def getEdgesFromCycle(cycle: ISet[FlowNode], g: Graph): ISet[Edge] = {
    cycle.flatMap(n => g.getOutgoingEdges(n).filter(e => cycle.contains(e.target)))
  }

  /**
    * This method computes paths from source port to target ports
    * There are two kinds of paths, simple and with cycle
    * a simple path, is the one without any cycles in it
    * a cycle path, is a simple path + all the cycles relevant to this simple path
    *
    * @param source port
    * @param target port
    * @return collector with set of paths
    */
  def reachPath(source: ResourceUri, target: ResourceUri): Collector = {
    if (!H.isPort(source) || !H.isPort(target)) {
      nodeReachPath(source, target, None)
    } else {
      val snodes = getNodesFromPort(source)
      val tnodes = getNodesFromPort(target)

      val graphs = findRelaventGraphs(snodes, tnodes)
      val cycleNodes = graphs.map(g => (g, g.getCycles)).toMap
      val cyclePorts = cycleNodes.flatMap { gcyc =>
        val g = gcyc._1
        val cycs = gcyc._2.toSet
        cycs.map(cyc => getPortsFromNodes(cyc, g))
      }
      val simplePaths = computeSimplePaths(source, target)
      val pathCycles =
        simplePaths.getPaths.map(p => (p, cyclePorts.filter(cp => (cp._2 intersect p.getPorts).nonEmpty))).toMap

      val complexPath = pathCycles.map(
        pc => if (pc._2.nonEmpty) {
          pc._1.union(
            pc._2.flatMap(cyc => cyclePortsToCollector(cyc._2, cyc._1)).foldLeft(Collector(st))((x, y) => x.union(y))
          )
        } else {
          pc._1
        }
      )

      simplePaths union Collector(st, simplePaths.getGraphs, ilistEmpty ++ complexPath.toSeq, Some(ResultType.Port))
    }
  }

  def reachPathWith(source: ResourceUri, target: ResourceUri, constraint: ConstraintExpr): Collector = {
    if (!H.isPort(source) || !H.isPort(target)) {
      nodeReachPath(source, target, Some(constraint))
    } else {
      val snodes = getNodesFromPort(source)
      val tnodes = getNodesFromPort(target)

      val graphs = findRelaventGraphs(snodes, tnodes)
      val cycleNodes = graphs.map(g => (g, g.getCycles)).toMap
      val cyclePorts = cycleNodes.flatMap { gcyc =>
        val g = gcyc._1
        val cycs = gcyc._2.toSet
        cycs.map(cyc => getPortsFromNodes(cyc, g))
      }
      val simplePaths = computeSimplePaths(source, target)
      val simplePathNone = constraint.kind match {
        case ConstraintKind.None => {
          val filteredPaths =
            simplePaths.getPaths.filter(f => f.getPorts.intersect(constraint.simple.get.getPorts).nonEmpty)
          Collector(st, simplePaths.getGraphs, filteredPaths, Some(ResultType.Port))
        }
        case _ => simplePaths
      }

      val pathCycles =
        simplePathNone.getPaths
          .map(
            p =>
              (
                p,
                cyclePorts.filter(
                  cp =>
                    (cp._2 intersect p.getPorts).nonEmpty && (constraint.kind match {
                      case ConstraintKind.None => cp._2.intersect(constraint.simple.get.getPorts).nonEmpty
                      case _ => true
                    })
                )
              )
          )
          .toMap

      val complexPath = pathCycles.map(
        pc =>
          pc._1.union(
            pc._2.flatMap(cyc => cyclePortsToCollector(cyc._2, cyc._1)).foldLeft(Collector(st))((x, y) => x.union(y))
          )
      )

      val filteredPaths = (complexPath.toSet union simplePaths.getPaths.toSet).filter(
        cp =>
          constraint.kind match {
            case ConstraintKind.All => constraint.simple.get.getPorts.forall(p => cp.getPorts.contains(p))
            case ConstraintKind.Some => constraint.simple.get.getPorts.intersect(cp.getPorts).nonEmpty
            case _ => true
          }
      )
      Collector(st, simplePaths.getGraphs, ilistEmpty ++ filteredPaths.toSeq, Some(ResultType.Port))
    }
  }

  //  private def getPaths(pathCyclePorts: ISet[((ISet[Edge], ISet[ResourceUri]),
  //    ISet[(ISet[Edge], ISet[ResourceUri])])],
  //                       source : ResourceUri,
  //                       target: ResourceUri,
  //                       constraint: Option[ConstraintExpr]): ISet[FlowCollector] = {
  //
  //
  //    var paths = isetEmpty[FlowCollector]
  //    pathCyclePorts.foreach { it =>
  //      var simplePathPorts: ISet[ResourceUri] = if (H.isInPort(source)) it._1._2 + source else it._1._2 - source
  //      simplePathPorts = if (H.isOutPort(target)) simplePathPorts + target else simplePathPorts - target
  //      val simplePathEdges = it._1._1
  //      val cycles = it._2
  //      val (simpleFlows, valid) = getPathEdgesFlowsValidity(simplePathPorts)
  //      simplePathPorts = simplePathPorts + source + target
  //
  //      if (valid ) {
  //        val validCycles = cycles.filter(it => getPathEdgesFlowsValidity(it._2)._2)
  //        val simplePath = FlowCollector(simplePathPorts,
  //          simplePathEdges, simpleFlows, isetEmpty[Tag])
  //        if(constraint.isEmpty) {
  //          paths = paths + simplePath
  //          paths = paths + getComplexPath(simplePath, validCycles)
  //        } else {
  //          val con = constraint.get.simple.get
  //          constraint.get.kind match {
  //            case ConstraintKind.All => {
  //              if(con.getPorts.subsetOf(simplePath.ports)) {
  //                paths = paths + simplePath
  //                paths = paths + getComplexPath(simplePath, validCycles)
  //              } else {
  //                val tPath = getComplexPath(simplePath, validCycles)
  //                if(con.getPorts.subsetOf(tPath.ports)) {
  //                  paths = paths + tPath
  //                }
  //              }
  //            }
  //            case ConstraintKind.Some => {
  //              if(simplePath.ports.intersect(con.getPorts).nonEmpty) {
  //                paths = paths + simplePath
  //                paths = paths + getComplexPath(simplePath, validCycles)
  //              } else {
  //                val tPath = getComplexPath(simplePath, validCycles)
  //                if(tPath.ports.intersect(con.getPorts).nonEmpty) {
  //                  paths = paths + tPath
  //                }
  //              }
  //            }
  //            case ConstraintKind.None => {
  //              if(simplePath.ports.intersect(con.getPorts).isEmpty) {
  //                paths = paths + simplePath
  //                paths = paths + getComplexPath(simplePath,
  //                  validCycles.filter(_._2.intersect(con.getPorts).isEmpty))
  //              }
  //            }
  //          }
  //        }
  //
  //      }
  //    }
  //    paths
  //  }

  //  private def getComplexPath(simplePath: FlowCollector,
  //                             validCycles: ISet[(ISet[Edge], ISet[ResourceUri])]):FlowCollector = {
  //    val ports = simplePath.ports ++
  //      validCycles.foldLeft(isetEmpty[ResourceUri])((c, n) => c union n._2)
  //    val edges = simplePath.edges ++ validCycles.foldLeft(isetEmpty[Edge])((c, n) => n._1 union c)
  //    val flows = simplePath.flows ++ validCycles.foldLeft(isetEmpty[ResourceUri])((c, n) =>
  //      getPathEdgesFlowsValidity(n._2)._1 union c)
  //     FlowCollector(ports, edges, flows, isetEmpty[Tag])
  //  }

  //  private def getPathEdgesFlowsValidity(ports: ISet[ResourceUri])
  //  : (ISet[ResourceUri], Boolean) = {
  //    if (sccValidCache.contains(ports)) {
  //      sccValidCache(ports)
  //    } else {
  //      val temp = getPathFlows(ports)
  //      sccValidCache = sccValidCache + (ports -> temp)
  //      temp
  //    }
  //  }

  private def getPortsFromNodes(nodes: ISet[FlowNode], g: Graph): (ISet[Edge], ISet[ResourceUri]) = {
    val edges = nodes.flatMap(it => g.getOutgoingEdges(it).filter(it2 => nodes.contains(it2.target)))
    (edges, edges.flatMap(_.sourcePort) union edges.flatMap(_.targetPort))
  }

  //  def getPathFlows(ports: ISet[ResourceUri]): (ISet[ResourceUri], Boolean) = {
  //    var compPortMap = imapEmpty[ResourceUri, ISet[ResourceUri]]
  //    var res = isetEmpty[ResourceUri]
  //    var goodness = true
  //    ports.foreach { p =>
  //      val node = graph.getNode(p)
  //      if (node.isDefined) {
  //        val nodeUri = node.get.getUri
  //        compPortMap = compPortMap +
  //          (nodeUri -> (compPortMap.getOrElse(nodeUri, isetEmpty[ResourceUri]) + p))
  //      }
  //    }
  //
  //    compPortMap.foreach { v =>
  //      val inports = v._2.filter(H.isInPort)
  //      val outPorts = v._2.filter(H.isOutPort)
  //
  //      val node = graph.getNode(v._1)
  //      if (node.isDefined) {
  //        val inFlows = if (node.get.isComponent)
  //          inports.flatMap(it => st.componentTable(v._1).getFlowsFromPort(it))
  //        else inports.flatMap(it => st.connectionTable(v._1).getFlowsFromPort(it))
  //        val outFlows = if (node.get.isComponent)
  //          outPorts.flatMap(it => st.componentTable(v._1).getFlowsFromPort(it))
  //        else outPorts.flatMap(it => st.connectionTable(v._1).getFlowsFromPort(it))
  //        val ans = inFlows intersect outFlows
  //        if (goodness &&
  //          inports.nonEmpty &&
  //          outPorts.nonEmpty &&
  //          ans.isEmpty &&
  //          node.get.isFlowDefined) {
  //          goodness = false
  //        }
  //
  //        res = res ++ ans
  //      }
  //    }
  //    (res, goodness)
  //  }

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
