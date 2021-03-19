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

import org.sireum.awas.AliranAman.Lattice.{LEdge, LNode}
import org.sireum.awas.collector
import org.sireum.awas.collector.CollectorErrorHelper._
import org.sireum.awas.collector.{Collector, CollectorErrorHelper, FlowCollector, ResultType}
import org.sireum.awas.flow.FlowNode.Edge
import org.sireum.awas.flow.{FlowEdge, FlowGraph, FlowNode, NodeType}
import org.sireum.awas.graph.{AwasEdge, SlangGraphImpl}
import org.sireum.awas.query.{ConstraintExpr, ConstraintKind}
import org.sireum.awas.symbol.{Resource, SymbolTable}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.{ILinkedSet, ISet, _}

class PortReachabilityImpl[Node](st: SymbolTable)
  extends BasicReachabilityImpl(st) with PortReachability[FlowNode] {

  private var cache = imapEmpty[ResourceUri, Collector]
  private val useCache = true

  private var sccValidCache = imapEmpty[ISet[ResourceUri], (ISet[ResourceUri], Boolean)]

  override def forwardPortReach(criterion: FlowNode): Collector =
    forwardPortReachSet(criterion.ports.toSet)

  override def forwardPortReachSet(criterions: Set[ResourceUri]): Collector =
    portReach(criterions, isForward = true)

  override def forwardPortReach(criterion: ResourceUri): Collector =
    portReach(isetEmpty[ResourceUri] + criterion, isForward = true)

  def nextPorts(port: ResourceUri, climbDown: Boolean): ISet[FlowCollector] = {
    if (H.isInPort(port) && FlowNode.getNode(port).isDefined) {
      //two cases 1. part of two graphs or, 2. just one graph
      val portNode = FlowNode.getNode(port).get
      var succ = if (climbDown) isetEmpty + portNode.getOwner.getSuccessorPorts(port) else {
        if (st.componentTable(portNode.getOwner.getUri).getFlowsFromPort(port).isEmpty) {
          isetEmpty + portNode.getOwner.getSuccessorPorts(port)
        } else {
          isetEmpty[FlowCollector]
        }
      } //sub graph succ
      if (FlowNode.getNode(portNode.getOwner.getUri).isDefined) {
        succ = succ + FlowNode.getNode(portNode.getOwner.getUri).get.getOwner.getSuccessorPorts(port) //parent graph succ
      }
      succ
    } else if(H.isOutPort(port) && st.forwardDeployment(port).nonEmpty) {
      //bindings/deployment next port
      val succPorts = st.forwardDeployment(port)
      succPorts.flatMap { sp =>
        val nodeUri = Resource.getParentUri(sp)
        if(nodeUri.isDefined) {
          Some(FlowCollector(isetEmpty+FlowNode.getNode(nodeUri.get).get.getOwner,
            isetEmpty+sp, isetEmpty, isetEmpty, isetEmpty))
        } else {
          None
        }
      }
    } else {
      //there is no port node for this port
      //if this is a port then there must be a parent uri
      assert(Resource.getParentUri(port).isDefined)
      val nodeUri = Resource.getParentUri(port).get
      if (nodeUri == st.system) {
        //&& FlowNode.getNode(port).isDefined) {
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
    } else if(H.isInPort(port) && st.backwardDeployment(port).nonEmpty) {
      val predPorts = st.backwardDeployment(port)
      predPorts.flatMap { pp =>
        val nodeUri = Resource.getParentUri(pp)

        if(nodeUri.isDefined) { // && FlowNode.getNode(nodeUri.get).isDefined) {
          Some(FlowCollector(isetEmpty+FlowNode.getNode(nodeUri.get).get.getOwner,
            isetEmpty+pp, isetEmpty, isetEmpty, isetEmpty))
        } else {
          None
        }
      }
    } else {
      //there is no port node for this port
      //if this is a port then there must be a parent uri
      assert(Resource.getParentUri(port).isDefined)
      val nodeUri = Resource.getParentUri(port).get
      if (nodeUri == st.system && FlowNode.getNode(port).isDefined) {
        //if this port is a port of the system component, then there must be a port node
        isetEmpty + FlowNode.getNode(port).get.getOwner.getPredecessorPorts(port)
      } else {
        assert(FlowNode.getNode(nodeUri).isDefined)
        isetEmpty + FlowNode.getNode(nodeUri).get.getOwner.getPredecessorPorts(port)
      }
    }
  }

  def flowReach(flow: ResourceUri): Collector = {
    if (useCache && cache.contains(flow)) {
      cache(flow)
    } else {
      val res = if (Resource.getParentUri(flow).isDefined &&
        H.uri2TypeString(Resource.getParentUri(flow).get) == H.COMPONENT_TYPE) {
        val cst = st.componentTable(Resource.getParentUri(flow).get)
        if (FlowNode.getNode(cst.componentUri).isDefined &&
          FlowNode.getNode(cst.componentUri).get.getSubGraph.isDefined) {
          val forward = if (cst.flow(flow).fromPortUri.isDefined &&
            FlowNode.getNode(cst.flow(flow).fromPortUri.get).isDefined) {
            val graph = FlowNode.getNode(cst.flow(flow).fromPortUri.get).get.getOwner
            graph.forwardPortReach(cst.flow(flow).fromPortUri.get)
          } else {
            Collector.buildEmpty()
          }
          val backward = if (cst.flow(flow).toPortUri.isDefined &&
            FlowNode.getNode(cst.flow(flow).toPortUri.get).isDefined) {
            val graph = FlowNode.getNode(cst.flow(flow).toPortUri.get).get.getOwner
            graph.backwardPortReach(cst.flow(flow).fromPortUri.get)
          } else {
            Collector.buildEmpty()
          }
          val result = if (forward == Collector.buildEmpty()) {
            backward
          } else if (backward == Collector.buildEmpty()) {
            forward
          } else {
            forward.intersect(backward)
          }
          result
        } else {
          Collector.buildEmpty()
        }
      } else {
        Collector.buildEmpty()
      }
      cache = cache + (flow -> res)
      res
    }
  }

  def portReach(criteria: ISet[ResourceUri], isForward: Boolean): Collector = {
    var resPorts = isetEmpty[ResourceUri]
    var worklist = ilistEmpty[ResourceUri]
    var resGraphs = isetEmpty[Graph]
    var resEdges = isetEmpty[Edge]
    var resError = isetEmpty[Tag]
    var resFlows = isetEmpty[ResourceUri]

    criteria.foreach { p =>
      if (H.isPort(p)) {
        worklist = worklist :+ p
      } else {
        resError += errorMessageGen(MISSING_CRITERIA, H.uri2CanonicalName(p), ReachAnalysisStage.Port)
      }
    }

    val climbDown = st.hasDeployments()

    while (worklist.nonEmpty) {
      val current = worklist.head
      if (!resPorts.contains(current)) {
        val temp = if (isForward) nextPorts(current, climbDown) else previousPorts(current)
        worklist = worklist ++ temp.flatMap(_.ports)
        resEdges = resEdges ++ temp.flatMap(_.edges)
        resFlows = resFlows ++ temp.flatMap(_.flows)
        resError = resError ++ temp.flatMap(_.errors)
        resGraphs = resGraphs ++ temp.flatMap(_.graph)
      }
      worklist = worklist.tail
      resPorts += current
    }

    var result = Collector(resGraphs, resPorts, resFlows, resEdges, isForward, criteria, resError)
    var worklist2 = resFlows.toList
    while (worklist2.nonEmpty) {
      val cFlow = worklist2.head
      worklist2 = worklist2.tail
      val next = flowReach(cFlow)
      worklist2 = worklist2 ++ next.getFlows
      result = result.union(next)
    }
    result
  }

  override def backwardPortReach(criterion: FlowNode): Collector =
    backwardPortReachSet(criterion.ports.toSet)

  override def backwardPortReachSet(criterions: Set[ResourceUri]): Collector =
    portReach(criterions, isForward = false)

  override def backwardPortReach(criterion: ResourceUri): Collector =
    portReach(isetEmpty[ResourceUri] + criterion, isForward = false)

  override def forwardReachSet(criterion: Set[ResourceUri]): Collector = {
    val nodeCriterion = criterion.filter(isNode)
    val restCriterion = criterion -- nodeCriterion
    if (nodeCriterion.isEmpty) {
      forwardPortReachSet(restCriterion)
    } else {
      forwardReachSetNode(nodeCriterion.flatMap(FlowNode.getNode)) union forwardPortReachSet(restCriterion)
    }
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
    val nodeCriterion = criterion.filter(isNode)
    val restCriterion = criterion -- nodeCriterion
    if (nodeCriterion.isEmpty) {
      backwardPortReachSet(restCriterion)
    } else {
      backwardReachSetNode(nodeCriterion.flatMap(FlowNode.getNode)) union backwardPortReachSet(restCriterion)
    }
  }

  override def backwardReach(criterion: ResourceUri): Collector = {
    if (isNode(criterion)) {
      val onode = FlowNode.getNode(criterion)
      backwardReach(onode.get)
    } else {
      backwardPortReach(criterion)
    }
  }

  override def reachPathSet(source: Set[ResourceUri], target: Set[ResourceUri], isRefined: Boolean): Collector = {
    val temp = source.foldLeft(Collector(st))(
      (c, n) =>
        c union
        target.foldLeft(Collector(st))((c2, n2) => c2 union reachPath(n, n2, isRefined: Boolean))
    )
    temp
  }

  override def reachPathSet(
                             source: Set[ResourceUri],
                             target: Set[ResourceUri],
    constraint: ConstraintExpr,
    isRefined: Boolean
                           ): Collector = {
    val temp = source.foldLeft(Collector(st))(
      (c, n) =>
        c union
        target.foldLeft(Collector(st))((c2, n2) => c2 union reachPathWith(n, n2, constraint, isRefined))
    )
    temp
  }

  private def getNodeFromPUri(portUri: ResourceUri): Option[FlowNode] = {
    if (FlowNode.getNode(portUri).isDefined) {
      Some(FlowNode.getNode(portUri).get)
    } else if (H.isPort(portUri) && Resource.getParentUri(portUri).isDefined &&
      FlowNode.getNode(Resource.getParentUri(portUri).get).isDefined) {
      Some(FlowNode.getNode(Resource.getParentUri(portUri).get).get)
    } else {
      None
    }
  }

  private def nodeReachPath(source: ResourceUri, target: ResourceUri, cExp: Option[ConstraintExpr]): Collector = {
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
        isetEmpty[FlowGraph[FlowNode, Edge]],
        isetEmpty[Tag] + errorMessageGen(CollectorErrorHelper.MISSING_NODE, H.uri2CanonicalName(source),
          ReachAnalysisStage.Node
        ) +
          errorMessageGen(CollectorErrorHelper.MISSING_NODE, H.uri2CanonicalName(target), ReachAnalysisStage.Node)
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

//  def nodePathToPortPaths(path: Collector, srcPort: ResourceUri,
//                          dstPort: ResourceUri): ISet[Collector] = {
//
//    val snode = getNodesFromPort(srcPort)
//    val tnode = getNodesFromPort(dstPort)
//
//
//    val ports = if (path.getEdges.isEmpty &&
//      path.getNodes.intersect(snode).nonEmpty &&
//      path.getNodes.intersect(tnode).nonEmpty) {
//      path.getPorts
//    } else {
//      path.getEdges.flatMap(_.sourcePort) ++
//        path.getEdges.flatMap(_.targetPort) ++
//        (if (H.isInPort(srcPort)) isetEmpty + srcPort else isetEmpty) ++
//        (if (H.isOutPort(dstPort)) isetEmpty + dstPort else isetEmpty)
//    }
//    var nodePortsMap = imapEmpty[FlowNode, ISet[ResourceUri]]
//    if (ports.contains(srcPort) && ports.contains(dstPort)) {
//      ports.foreach { p =>
//        for (elem <- getNodesFromPort(p)) {
//          nodePortsMap += (elem -> (nodePortsMap.getOrElse(elem, isetEmpty[ResourceUri]) + p))
//        }
//      }
//      assert(nodePortsMap.forall(p => p._2.size <= 2), "Simple path has more than two ports per node")
//
//      //drop path case: path dropped only when there is a flow defined
//      //for the inPort and the out port is not in it.
//      val isValidPath = nodePortsMap.forall {
//        case (n, ps) =>
//          if (ps.size == 2 && n.isFlowDefined) {
//            val inPort = ps.filter(H.isInPort).head
//            if (ps.filter(H.isOutPort).isEmpty) {
//              print("test")
//            }
//            val outPort = ps.filter(H.isOutPort).head
//            val flows = n.getFlowsFromPort(inPort).map(it => n.getFlows(it))
//            if (flows.nonEmpty && flows.exists(f => f.toPortUri.isDefined &&
//              f.toPortUri.get == outPort)) {
//              true
//            } else if (inPort == dstPort && outPort == srcPort) {
//              //no need for a flow when src and dst are reached
//              true
//            } else {
//              false
//            }
//          } else {
//            true
//          }
//      }
//      //only varying part is the flows field.
//      if (isValidPath) {
//        //starting with no flows
//        var result = isetEmpty[Collector] + Collector(
//          st,
//          path.getGraphs,
//          isetEmpty[FlowNode],
//          ports.toSet,
//          ResultType.Port,
//          path.getEdges,
//          isetEmpty[ResourceUri],
//          isetEmpty[ResourceUri] + srcPort + dstPort,
//          isetEmpty[Tag]
//        )
//
//        nodePortsMap.foreach {
//          case (n, ps) =>
//            if (n.isFlowDefined && ps.size == 2 && n.isComponent) {
//              val inPort = ps.filter(H.isInPort).head
//              val outPort = ps.filter(H.isOutPort).head
//              val flows = n
//                .getFlowsFromPort(inPort)
//                .map(it => n.getFlows(it))
//                .filter(f => f.toPortUri.isDefined && f.toPortUri.get == outPort)
//              if (flows.nonEmpty) {
//                result = result.flatMap(
//                  r =>
//                    flows.map(
//                      f =>
//                        Collector(
//                          st,
//                          r.getGraphs,
//                          isetEmpty[FlowNode],
//                          r.getPorts,
//                          r.getResultType.get,
//                          r.getEdges,
//                          r.getFlows + f.flowUri,
//                          r.getCriteria,
//                          r.getErrors ++ r.getWarnings
//                        )
//                    )
//                )
//              }
//            }
//        }
//        result
//        //case 1 - flows not defined:
//      } else {
//        isetEmpty[Collector]
//      }
//    } else {
//      isetEmpty[Collector]
//    }
//  }

  def cyclePortsToCollector(ports: ISet[ResourceUri], edges: ISet[Edge]): ISet[Collector] = {
    var nodePortsMap = imapEmpty[FlowNode, ISet[ResourceUri]]

    ports.foreach { p =>
      for (elem <- getNodesFromPort(p)) {
        nodePortsMap += (elem -> (nodePortsMap.getOrElse(elem, isetEmpty[ResourceUri]) + p))
      }
    }
    //assert(nodePortsMap.forall(p => p._2.size <= 2), "cycle has more than two ports per node")

    //drop path case: path dropped only when there is a flow defined
    //for the inPort and the out port is not in it.
    //    val isValidPath = nodePortsMap.forall {
    //      case (n, ps) =>
    //        if (n.isFlowDefined) {
    //          val inPort = ps.filter(H.isInPort).head
    //          val outPort = ps.filter(H.isOutPort).head
    //          val flows = n.getFlowsFromPort(inPort).map(it => n.getFlows(it))
    //          if (flows.nonEmpty && flows.exists(f => f.toPortUri.isDefined && f.toPortUri.get == outPort)) {
    //            true
    //          } else {
    //            false
    //          }
    //        } else {
    //          true
    //        }
    //    }

    //    if (isValidPath) {
      var result = isetEmpty[Collector] + Collector(
        isetEmpty,
        isetEmpty[FlowNode],
        ports,
        ResultType.Port,
        edges,
        isetEmpty[ResourceUri],
        isetEmpty[ResourceUri], hasCycle = true,
        isetEmpty[Tag]
      )

      nodePortsMap.foreach {
        case (n, ps) =>
          if (n.isFlowDefined && n.isComponent) {
            val inPort = ps.filter(H.isInPort)
            val outPort = ps.filter(H.isOutPort)

            inPort.foreach { inp =>
              val flows = n
                .getFlowsFromPort(inp)
                .map(it => n.getFlows(it))
                .filter(f => f.toPortUri.isDefined && outPort.contains(f.toPortUri.get))
              if (flows.nonEmpty) {
                //more than one flow then create multiple paths
                result = result.flatMap(
                  r =>
                    flows.map(
                      f =>
                        Collector(
                          r.getGraphs,
                          isetEmpty[FlowNode],
                          r.getPorts,
                          r.getResultType.get,
                          r.getEdges,
                          r.getFlows + f.flowUri,
                          r.getCriteria, hasCycle = true,
                          r.getErrors ++ r.getWarnings
                        )
                    )
                )
              }
            }
          }
      }
      result
    //    } else {
    //      isetEmpty[Collector]
    //    }

  }

//  def computeSimplePaths(source: ResourceUri, target: ResourceUri): Collector = {
//    assert(H.isPort(source) && H.isPort(target))
//    val srcNodes = getNodesFromPort(source)
//    val targetNodes = getNodesFromPort(target)
//    val nodePaths = if (srcNodes != targetNodes) {
//      getSimpleNodePaths(srcNodes, targetNodes)
//    } else {
//      reachPathSet(srcNodes.map(_.getUri), targetNodes.map(_.getUri))
//    }
//    val paths: ILinkedSet[Collector] = nodePaths.getPaths.flatMap(p => nodePathToPortPaths(p, source, target))
//    Collector(st, nodePaths.getGraphs, paths, Some(ResultType.Port))
//  }

def computeSimplePaths(
  source: ResourceUri,
  target: ResourceUri,
  isRefined: Boolean
): ISet[ILinkedSet[ResourceUri]] = {
    assert(H.isPort(source) && H.isPort(target))
    val simpleReach = forwardPortReach(source) intersect backwardPortReach(target)

    val path: ILinkedSet[ResourceUri] = ilinkedSetEmpty[ResourceUri] + source

    var result = isetEmpty[ILinkedSet[ResourceUri]]

    var workList: IList[ILinkedSet[ResourceUri]] = ilistEmpty[ILinkedSet[ResourceUri]] :+ path

    while (workList.nonEmpty) {
      val currentPath: ILinkedSet[ResourceUri] = workList.head
      val currPort = currentPath.last
      if (currPort != target) {
        val nexts = nextPorts(currPort, true).foldLeft(
          FlowCollector(isetEmpty[Graph], isetEmpty[ResourceUri], isetEmpty[Edge], isetEmpty[ResourceUri],
            isetEmpty[Tag])
        )(_.union(_))

        nexts.ports.foreach { np =>
          if ((!currentPath.contains(np)) && simpleReach.getPorts.contains(np)) {
            if (isRefined) {
              val currParent = Resource.getParentUri(currPort)
              val nextParent = Resource.getParentUri(np)
              if (currParent.isDefined &&
                nextParent.isDefined &&
                currParent.get == nextParent.get) {
                if (FlowNode.getNode(currParent.get).isDefined &&
                  FlowNode.getNode(currParent.get).get.getSubGraph.isEmpty) {
                  workList = workList :+ (currentPath + np)
                }
              } else {
                workList = workList :+ (currentPath + np)
              }
            } else {
              workList = workList :+ (currentPath + np)
            }
          }
        }
      } else {
        result = result + currentPath
      }
      workList = workList.tail
    }
    result

}

def getSimplePath(paths: ISet[ILinkedSet[ResourceUri]], src: ResourceUri, dst: ResourceUri): ISet[Collector] = {
    var res = isetEmpty[Collector]
    paths.foreach { path =>
      var flows = isetEmpty[ResourceUri]
      var edges = isetEmpty[Edge]
      val pathList = path.toList
      for (i <- pathList.indices) {
        if (i != path.size - 1) {

          val port = pathList(i)
          val nextPort = pathList(i + 1)
          val next = nextPorts(pathList(i), true).foldLeft(
            FlowCollector(
              isetEmpty[Graph],
              isetEmpty[ResourceUri],
              isetEmpty[Edge],
              isetEmpty[ResourceUri],
              isetEmpty[Tag]
            )
          )(_.union(_))
          val cflows = getNodesFromPort(port).flatMap(_.getFlows).toMap
          flows = flows ++ next.flows.filter(
          f =>
            if (cflows.keySet.contains(f)) {
              cflows(f).toPortUri.isDefined && cflows(f).toPortUri.get == nextPort
            } else false
        )
          edges = edges ++ getNodesFromPort(port)
          .flatMap(it => it.getOwner.getOutgoingEdges(it)).filter(
              e =>
                e.targetPort.isDefined &&
                  e.sourcePort.isDefined &&
                  (e.targetPort.get == nextPort)
            )
        }
      }
      res = res + Collector(
        if (path.nonEmpty) findRelaventGraphs(getNodesFromPort(src), getNodesFromPort(dst)) else isetEmpty,
      isetEmpty[FlowNode],
        path,
        ResultType.Port,
        edges,
        flows,
        isetEmpty + src + dst, hasCycle = false,
      isetEmpty[Tag]
    )
    }
    res
  }
//  def getEdgesFromCycle(cycle: ISet[FlowNode], g: Graph): ISet[Edge] = {
//    cycle.flatMap(n => g.getOutgoingEdges(n).filter(e => cycle.contains(e.target)))
//  }

  private def cyclePortsEdges(cycle : Seq[ResourceUri]) : (ISet[Edge], ISet[ResourceUri]) = {
    var ports = cycle.toSet
    var edges = isetEmpty[FlowEdge[FlowNode]]
    ports.foreach{ p =>
      val parent = Resource.getParentUri(p)
      if(parent.isDefined && FlowNode.getNode(parent.get).isDefined) {
        val graph = FlowNode.getNode(parent.get).get.getOwner
        val es = graph.getEdgeForPort(p)
        es.foreach {e =>
          if(e.sourcePort.isDefined &&
            e.targetPort.isDefined &&
            ports.contains(e.sourcePort.get) &&
            ports.contains(e.targetPort.get)) {
            edges = edges + e
          }
        }
      }
      if(FlowNode.getNode(p).isDefined) {
        val graph = FlowNode.getNode(p).get.getOwner
        val es = graph.getEdgeForPort(p)
        es.foreach { e =>
          if(e.sourcePort.isDefined &&
            e.targetPort.isDefined &&
            ports.contains(e.sourcePort.get) &&
            ports.contains(e.targetPort.get)) {
            edges = edges + e
          }
        }
      }
    }
    (edges, ports)
  }

  private def computePortCycles(source: ResourceUri, target: ResourceUri) : Seq[Seq[ResourceUri]] = {
    class PEdge(src: PNode, dst: PNode) extends AwasEdge[PNode] {
      override def source: PNode = src
      override def target: PNode = dst
    }
    case class PNode(id: ResourceUri)
    val pGraph = new SlangGraphImpl[PNode, PEdge]()
    val initPorts = forwardPortReach(source).intersect(backwardPortReach(target)).getPorts
    val uriPNMap = initPorts.map(p => (p,pGraph.addNode(PNode(p)))).toMap
    var worklist = ilistEmpty :+ source
    while(worklist.nonEmpty) {
      val curr = worklist.head
      if(initPorts.contains(curr)) {
        val next = nextPorts(curr, true).flatMap(_.ports)
        next.intersect(initPorts).foreach{n =>
          if(uriPNMap.get(curr).isDefined && uriPNMap.get(n).isDefined) {
            val srcPN = uriPNMap(curr)
            val dstPN = uriPNMap(n)
            val pe = new PEdge(srcPN, dstPN)
            if(pGraph.getEdge(srcPN, dstPN).isEmpty) {
              pGraph.addEdge(srcPN, dstPN, pe)
              worklist = worklist :+ n
            }
          }
        }
      }
      worklist = worklist.tail
    }
    pGraph.getCycles.map(_.map(_.id))
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
  def reachPath(source: ResourceUri, target: ResourceUri, isRefined: Boolean): Collector = {
    if (!H.isPort(source) || !H.isPort(target)) {
      nodeReachPath(source, target, None)
    } else {
      val snodes = getNodesFromPort(source)
      val tnodes = getNodesFromPort(target)

      val graphs = findRelaventGraphs(snodes, tnodes)
      //val sNodePath = getSimpleNodePaths(snodes, tnodes)
      val cycleNodes = graphs.map(g => (g, g.getCycles)).toMap

      val cyclePorts = computePortCycles(source, target).map{ cyc =>
        cyclePortsEdges(cyc)
      }

//      val cyclePorts = cycleNodes.flatMap { gcyc =>
//        val g = gcyc._1
//        val cycs = gcyc._2.toSet
//        cycs.map(cyc => getPortsFromNodes(ilistEmpty ++ cyc, g))
//      }



      val simplePaths = getSimplePath(computeSimplePaths(source, target, isRefined), source, target)
      val pathCycles =
        simplePaths.map(p => (p, cyclePorts.filter(cp => (cp._2 intersect p.getPorts).nonEmpty))).toMap

      val filteredPathCycles = pathCycles.map(pc =>
        if (pc._2.nonEmpty) {
          val cols = pc._2.flatMap(cyc => cyclePortsToCollector(cyc._2, cyc._1))
          (pc._1, Some(Collector.buildPathWrapper(cols.flatMap(_.getGraphs).toSet, ilinkedSetEmpty ++ cols, None)))
        } else {
          (pc._1, None)
        }
      )

      val complexPath = filteredPathCycles
        .map(
          pc =>
            if (pc._2.isDefined) {
          pc._1.union(pc._2.get)
        } else {
          pc._1
          }
        ).toSet


      Collector(
        simplePaths.flatMap(_.getGraphs),
        ilinkedSetEmpty[Collector] ++ simplePaths ++ complexPath,
        Some(ResultType.Port)
      )
    }
  }

  def reachPathWith(
    source: ResourceUri,
    target: ResourceUri,
    constraint: ConstraintExpr,
    isRefined: Boolean
  ): Collector = {
    if (!H.isPort(source) || !H.isPort(target)) {
      nodeReachPath(source, target, Some(constraint))
    } else {
      val snodes = getNodesFromPort(source)
      val tnodes = getNodesFromPort(target)

      val graphs = findRelaventGraphs(snodes, tnodes)
      val cycleNodes = graphs.map(g => (g, g.getCycles)).toMap

      val cyclePorts = computePortCycles(source, target).map{ cyc =>
        cyclePortsEdges(cyc)
      }

//      val cyclePorts = cycleNodes.flatMap { gcyc =>
//        val g = gcyc._1
//        val cycs = gcyc._2.toSet
//        cycs.map(cyc => getPortsFromNodes(ilistEmpty ++ cyc, g))
//      }

      val simplePaths = getSimplePath(computeSimplePaths(source, target, isRefined), source, target)

      val simplePathNone = constraint.kind match {
        case ConstraintKind.None =>
          val filteredPaths =
            simplePaths.filter(f => f.getPorts.intersect(constraint.simple.get.getPorts).isEmpty)
          Collector(
            simplePaths.flatMap(_.getNodes.map(_.getOwner)),
            ilinkedSetEmpty[Collector] ++ filteredPaths,
            Some(ResultType.Port)
          )
        case _ =>
          Collector(
            simplePaths.flatMap(_.getNodes.map(_.getOwner)),
            ilinkedSetEmpty[Collector] ++ simplePaths,
            Some(ResultType.Port)
          )
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
                      case ConstraintKind.None => cp._2.intersect(constraint.simple.get.getPorts).isEmpty
                      case _ => true
                    })
                )
              )
          )
          .toMap

      val complexPath = pathCycles.flatMap(pc => {
        val cycles = pc._2.flatMap(cyc => cyclePortsToCollector(cyc._2, cyc._1))
        if (cycles.nonEmpty)
          Some(pc._1.union(cycles.foldLeft(Collector(st))((x, y) => x.union(y))))
        else
          None
      })

      val filteredPaths = (complexPath.toSet union simplePathNone.getPaths).filter(
        cp =>
          constraint.kind match {
            case ConstraintKind.All => constraint.simple.get.getPorts.forall(p => cp.getPorts.contains(p))
            case ConstraintKind.Some => constraint.simple.get.getPorts.intersect(cp.getPorts).nonEmpty
            case _ => true
          }
      )

      Collector(
        filteredPaths.flatMap(_.getNodes.map(_.getOwner)),
        ilinkedSetEmpty ++ filteredPaths.toSeq, Some(ResultType.Port))
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

  private def getPortsFromNodes(nodes: ISeq[FlowNode], g: Graph): (ISet[Edge], ISet[ResourceUri]) = {
    var edges = isetEmpty[Edge]
    var ports = isetEmpty[ResourceUri]

    for (i <- nodes.indices) {
      val iplus = if (i + 1 == nodes.size) 0 else i + 1
      g.getEdge(nodes(i), nodes(iplus)).foreach { e =>
        if (e.sourcePort.isDefined && e.targetPort.isDefined) {
          edges = edges + e
          ports = ports + e.sourcePort.get + e.targetPort.get
        }
      }
    //      val oe = g.getOutgoingEdges(nodes(i))
//      oe.foreach { it =>
//        if (i + 1 == nodes.size) {
//          if (it.target == nodes.head && it.sourcePort.isDefined && it.targetPort.isDefined) {
//
//            edges = edges + it
//          }
//        } else {
//          if (it.target == nodes(i + 1) && it.sourcePort.isDefined && it.targetPort.isDefined) {
//            edges = edges + it
//          }
//        }
//      }
    }
  //
  //    val p1 = edges.flatMap(_.sourcePort)
  //    val p2 = {
  //      edges.flatMap(_.targetPort).toSet
  //    }
    (edges, ports)
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
  override def getSuccessor(currentPort: ResourceUri): ISet[ResourceUri] = {
    nextPorts(currentPort, true).flatMap(_.ports)
  }

  override def getSuccDetailed(currentPort: ResourceUri): ISet[FlowCollector] = {
    nextPorts(currentPort, true)
  }

  override def getPredecessor(currentPort: ResourceUri): ISet[ResourceUri] = {
    previousPorts(currentPort).flatMap(_.ports)
  }

  override def getPredDetailed(currentPort: ResourceUri): ISet[FlowCollector] = {
    previousPorts(currentPort)
  }

  def reachSimplePath(source: ResourceUri, target: ResourceUri, isRefined: Boolean
  ): Collector = {
    if (!H.isPort(source) || !H.isPort(target)) {
      val snode = getNodeFromPUri(source)
      val tnode = getNodeFromPUri(target)
      if (snode.isDefined && tnode.isDefined) {
        reachSimplePath(snode.get, tnode.get)
      } else {
        collector.Collector(
          isetEmpty[FlowGraph[FlowNode, Edge]],
          isetEmpty[Tag] + errorMessageGen(
            CollectorErrorHelper.MISSING_NODE,
            H.uri2CanonicalName(source),
            ReachAnalysisStage.Node
          ) +
            errorMessageGen(CollectorErrorHelper.MISSING_NODE, H.uri2CanonicalName(target), ReachAnalysisStage.Node)
        )
      }
    } else {
      val simplePaths = getSimplePath(computeSimplePaths(source, target, isRefined), source, target)
      Collector(
        simplePaths.flatMap(_.getNodes.map(_.getOwner)),
        ilinkedSetEmpty[Collector] ++ simplePaths,
        Some(ResultType.Port))
    }
  }
  override def reachSimplePathSet(source: Set[ResourceUri], target: Set[ResourceUri], isRefined: Boolean
  ): Collector = {
    val temp = source.foldLeft(Collector(st))(
      (c, n) =>
        c union
        target.foldLeft(Collector(st))((c2, n2) => c2 union reachSimplePath(n, n2, isRefined))
    )
    temp
  }

  override def reachSimplePathSet(
    source: Set[ResourceUri],
    target: Set[ResourceUri],
    constraint: ConstraintExpr,
    isRefined: Boolean
  ): Collector = {
    val paths = reachSimplePathSet(source, target, isRefined).getPaths
    val filteredPaths = constraint.kind match {
       case ConstraintKind.All => paths.filter(it =>
       constraint.simple.get.getPorts.subsetOf(it.getPorts))
       case ConstraintKind.Some => paths.filter(it =>
         constraint.simple.get.getPorts.intersect(it.getPorts).nonEmpty)
       case ConstraintKind.None => paths.filter(it =>
       constraint.simple.get.getPorts.intersect(it.getPorts).isEmpty)
    }
    Collector(filteredPaths.flatMap(_.getGraphs),
      ilinkedSetEmpty[Collector] ++ filteredPaths,
      Some(ResultType.Port))
  }
}
