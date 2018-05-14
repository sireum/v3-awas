package org.sireum.awas.reachability

import org.sireum.awas.collector
import org.sireum.awas.collector.CollectorErrorHelper.{MISSING_CRITERIA, ReachAnalysisStage, errorMessageGen}
import org.sireum.awas.collector._
import org.sireum.awas.fptc.FlowNode.Edge
import org.sireum.awas.fptc.{FlowGraph, FlowNode}
import org.sireum.awas.symbol.{Resource, SymbolTable}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._


class ErrorReachabilityImpl[Node](st: SymbolTable) extends
  PortReachabilityImpl(st) with ErrorReachability[FlowNode]
  with PortReachability[FlowNode] with BasicReachability[FlowNode] {

  override def forwardErrorSetReach(errorRes: IMap[ResourceUri, Set[ResourceUri]])
  : Collector = {
    errorRes.foldLeft(Collector(st))((c, n) => c union forwardErrorReach(n._1, n._2))
  }

  override def forwardErrorReach(port: ResourceUri, errors: ISet[ResourceUri]):
  Collector = {
    errorReach(port, errors, isForward = true)
  }

  override def backwardErrorSetReach(errorRes: IMap[ResourceUri, Set[ResourceUri]])
  : Collector = {
    errorRes.foldLeft(Collector(st))((c, n) => c union backwardErrorReach(n._1, n._2))
  }

  def unionErrors(op1: IMap[ResourceUri, ISet[ResourceUri]],
                  op2: IMap[ResourceUri, ISet[ResourceUri]]):
  IMap[ResourceUri, Set[ResourceUri]] = {
    var result = imapEmpty[ResourceUri, ISet[ResourceUri]]
    val ports = op1.keySet ++ op2.keySet
    ports.foreach { p =>
      result = result + ((p, op1.getOrElse(p, isetEmpty[ResourceUri]) ++
        op2.getOrElse(p, isetEmpty[ResourceUri])))
    }
    result
  }

  override def backwardErrorReach(port: ResourceUri, errors: ISet[ResourceUri]):
  Collector = {
    errorReach(port, errors, isForward = false)
  }

  def errorReach(port: ResourceUri,
                 errors: ISet[ResourceUri],
                 isForward: Boolean): Collector = {

    var workList = ilistEmpty[(ResourceUri, ResourceUri)]
    val result = mmapEmpty[ResourceUri, MSet[ResourceUri]]
    var resEdges = isetEmpty[Edge]
    var resError = isetEmpty[Tag]
    var resFlows = isetEmpty[ResourceUri]
    var resGraph = isetEmpty[Graph]

    if (port.startsWith(H.PORT_TYPE)) {
      workList = workList ++ errors.map((port, _))
    } else {
      resError += errorMessageGen(MISSING_CRITERIA,
        port, ReachAnalysisStage.Port)
    }
      while (workList.nonEmpty) {
        val current = workList.head
        if (addErrors(result, current._1, current._2)) {
          val temp = (if (isForward) nextError(current)
          else previousError(current)).foldLeft(FlowErrorNextCollector(
            isetEmpty, isetEmpty, isetEmpty, isetEmpty, isetEmpty))(_.union(_))
          workList = workList ++ temp.tuples
          resEdges = resEdges ++ temp.edges
          resFlows = resFlows ++ temp.flows
          resError = resError ++ temp.errors
          resGraph = resGraph ++ temp.graph
        }
        workList = workList.tail
      }
    collector.Collector(st,
      resGraph,
      result.map(v => (v._1, v._2.toSet)).toMap,
      resFlows, resEdges, isForward, isetEmpty[ResourceUri] + port, resError)
  }

  def previousError(tuple: (ResourceUri, ResourceUri)): ISet[FlowErrorNextCollector] = {
    var result = ilistEmpty[(ResourceUri, ResourceUri)]
    assert(H.isPort(tuple._1))
    if (H.isOutPort(tuple._1) && FlowNode.getNode(tuple._1).isDefined) {
      val portNode = FlowNode.getNode(tuple._1).get
      var succ = isetEmpty + portNode.getOwner.getPredecessorError(tuple)
      if (FlowNode.getNode(portNode.getOwner.getUri).isDefined) {
        succ = succ + FlowNode.getNode(portNode.getOwner.getUri)
          .get.getOwner.getPredecessorError(tuple)
      }
      succ
    } else {
      //there is no port node for this port
      //if this is a port then there must be a parent uri
      assert(Resource.getParentUri(tuple._1).isDefined)
      val nodeUri = Resource.getParentUri(tuple._1).get
      if (nodeUri == st.system) {
        //if this port is a port of the system component, then there must be a port node
        isetEmpty + FlowNode.getNode(tuple._1).get.getOwner.getPredecessorError(tuple)
      } else {
        assert(FlowNode.getNode(nodeUri).isDefined)
        isetEmpty + FlowNode.getNode(nodeUri).get.getOwner.getPredecessorError(tuple)
      }
    }
  }

  def nextError(tuple: (ResourceUri, ResourceUri)): ISet[FlowErrorNextCollector] = {
    //only port can be associated with error to calculate successor error
    var result = ilistEmpty[(ResourceUri, ResourceUri)]
    assert(H.isPort(tuple._1))
    if (H.isInPort(tuple._1) && FlowNode.getNode(tuple._1).isDefined) {
      val portNode = FlowNode.getNode(tuple._1).get
      var succ = isetEmpty + portNode.getOwner.getSuccessorError(tuple)
      if (FlowNode.getNode(portNode.getOwner.getUri).isDefined) {
        succ = succ + FlowNode.getNode(portNode.getOwner.getUri)
          .get.getOwner.getSuccessorError(tuple)
      }
      succ
    } else {
      //there is no port node for this port
      //if this is a port then there must be a parent uri
      assert(Resource.getParentUri(tuple._1).isDefined)
      val nodeUri = Resource.getParentUri(tuple._1).get
      if (nodeUri == st.system) {
        //if this port is a port of the system component, then there must be a port node
        isetEmpty + FlowNode.getNode(tuple._1).get.getOwner.getSuccessorError(tuple)
      } else {
        assert(FlowNode.getNode(nodeUri).isDefined)
        isetEmpty + FlowNode.getNode(nodeUri).get.getOwner.getSuccessorError(tuple)
      }
    }
  }

  /**
    * This methods adds the errors in the res as side effect
    * and the successful addition is informed by the return
    *
    * @param res
    * @param port
    * @param error
    * @return
    */
  def addErrors(res: MMap[ResourceUri, MSet[ResourceUri]],
                port: ResourceUri,
                error: ResourceUri): Boolean = {
    var result = false
    val errors = res.getOrElseUpdate(port, msetEmpty[ResourceUri])
    if (!errors.contains(error)) {
      res(port) = res(port) + error
      result = true
    }
    result
  }

  def errorPathReachMap(source: IMap[ResourceUri, ISet[ResourceUri]],
                        target: IMap[ResourceUri, ISet[ResourceUri]]):
  Collector = {

    source.toSet.foldLeft(Collector(st))((c, n) => c union
      target.toSet.foldLeft(Collector(st))((c2, n2) => c2 union
        errorPathReach(n._1, n._2, n2._1, n2._2)))
  }

  def errorPathReach(sourcePort: ResourceUri, sourceErrors: ISet[ResourceUri],
                     targetPort: ResourceUri, targetErrors: ISet[ResourceUri]):
  Collector = {
    val paths = reachPath(sourcePort, targetPort).getPaths.flatMap(it => pathErrorRefine(it, sourcePort,
      sourceErrors, targetPort, targetErrors)).toSet
    Collector(st,
      paths.map(_.getGraphs).fold(isetEmpty[Graph])((s, t) => s ++ t),
      paths.toVector, Some(ResultType.Error))
  }


  //TODO: Rework this method, refactor, high cyclomatic complex
  private def pathErrorRefine(path: Collector,
                              sourcePort: ResourceUri,
                              sourceErrors: ISet[ResourceUri],
                              targetPort: ResourceUri,
                              targetErrors: ISet[ResourceUri]):
  ISet[Collector] = {
    val snodes = getNodesFromPort(sourcePort)
    val tnodes = getNodesFromPort(targetPort)

    val graphs = findRelaventGraphs(snodes, tnodes)

    var paths = isetEmpty[FlowErrorPathCollector]

    sourceErrors.foreach(e => paths = paths +
      collector.FlowErrorPathCollector(ilistEmpty[(ResourceUri, ResourceUri)] :+ (sourcePort, e),
        isetEmpty[Edge], isetEmpty[ResourceUri], isetEmpty[Tag], isetEmpty ++ snodes.map(_.getOwner)))

    var result = isetEmpty[Collector]

    var resPaths = isetEmpty[FlowErrorPathCollector]

    var resCycle = isetEmpty[FlowErrorPathCollector]


    while (paths.nonEmpty) {
      val current = paths.head
      paths = paths - current
      if (current.path.last._1 == targetPort) {
        resPaths = resPaths + current
      } else {
        val tNext = nextError(current.path.last)
        tNext.foreach { tn =>
          tn.tuples.foreach { t =>
            if (path.getPorts.contains(t._1)) {
              if (current.path.toSet.contains(t)) {
                resCycle = resCycle + current
              } else {
                if (tn.edges.nonEmpty) {
                  paths = paths + FlowErrorPathCollector(current.path :+ t,
                    current.edges union tn.graph.head.getEdges(current.path.last._1, t._1),
                    current.flows, current.errors union tn.errors, tn.graph)
                } else {
                  if (tn.flows.size <= 1) {
                    paths = paths + FlowErrorPathCollector(current.path :+ t, current.edges,
                      current.flows ++ tn.flows, current.errors union tn.errors, tn.graph)
                  } else { //more than one flow so, branch for each flow

                    val resFlows = tn.flows.filter { it =>
                      val flows = getNodesFromPort(t._1).flatMap(_.getFlows).toMap
                      flows.get(it).isDefined && flows(it).toPortUri.isDefined &&
                        flows(it).toPortUri.get == t._1 && flows(it).toFaults.contains(t._2)
                      //.toMap(it)
                    }
                    paths = paths + FlowErrorPathCollector(current.path :+ t, current.edges,
                      current.flows ++ resFlows, current.errors union tn.errors, current.graphs ++ tn.graph)

                    //                    val cnode = graph.getNode(current.path.last._1)
                    //                    val nnode = graph.getNode(t._1)
                    //                    if (cnode.isDefined && nnode.isDefined &&
                    //                      cnode.get == nnode.get) {
                    //                      var flows = isetEmpty[ResourceUri]
                    //                      if (cnode.get.isComponent) {
                    //                        flows ++= tNext.flows.filter(it => nnode.get.getFlows.get(it).isDefined &&
                    //                          nnode.get.getFlows(it).toPortUri.isDefined &&
                    //                          nnode.get.getFlows(it).toPortUri.get == t._1 &&
                    //                          nnode.get.getFlows(it).toFaults.contains(t._2)
                    //                        )
                    //                      } else {
                    //                        flows ++= tNext.flows.filter(it => nnode.get.getFlows.get(it).isDefined &&
                    //                          nnode.get.getFlows(it).toFaults.contains(t._2))
                    //                      }
                    //                      paths = paths + FlowErrorPathCollector(current.path :+ t, current.edges,
                    //                        current.flows ++ flows, current.errors union tNext.errors)
                    //                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    val pathCycle = resPaths.map(it => (it, resCycle.filter(it2 => it2.path.toSet.intersect(it.path.toSet).nonEmpty)))

    var tempPaths = isetEmpty[FlowErrorNextCollector]

    pathCycle.foreach { it =>
      tempPaths = tempPaths + FlowErrorNextCollector(it._1.path.toSet,
        it._1.edges, it._1.flows, it._1.errors, it._1.graphs)
      if (it._2.nonEmpty) {
        val cycles = it._1.union(it._2.foldLeft(collector.FlowErrorPathCollector(ivectorEmpty[(ResourceUri, ResourceUri)],
          isetEmpty[Edge], isetEmpty[ResourceUri], isetEmpty[Tag], isetEmpty))(_.union(_)))

        tempPaths = tempPaths + FlowErrorNextCollector(cycles.path.toSet, cycles.edges, cycles.flows, cycles.errors, cycles.graphs)
      }
    }

    result = result ++ tempPaths.map(it => collector.Collector(st, it.graph, it.tuples.map(x => (x._1, isetEmpty[ResourceUri] + x._2)).toMap,
      ResultType.Error, it.edges, it.flows, isetEmpty[ResourceUri] + sourcePort + targetPort, it.errors))


    //    result = result + Collector(st, graph,
    //      current.path.map(x => (x._1, isetEmpty[ResourceUri] + x._2)).toMap,
    //      ResultType.Error, current.edges, current.flows,
    //      isetEmpty[ResourceUri] + sourcePort+targetPort, current.errors)

    result
  }


  def intersectErrors(op1: IMap[ResourceUri, ISet[ResourceUri]],
                      op2: IMap[ResourceUri, ISet[ResourceUri]]):
  IMap[ResourceUri, Set[ResourceUri]] = {
    var result = imapEmpty[ResourceUri, ISet[ResourceUri]]
    val ports = op1.keySet.intersect(op2.keySet)
    ports.foreach { p =>
      result = result + ((p, op1(p).intersect(op2(p))))
    }
    result
  }

}