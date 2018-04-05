package org.sireum.awas.reachability

import org.sireum.awas.collector
import org.sireum.awas.collector.CollectorErrorHelper.{MISSING_CRITERIA, ReachAnalysisStage, errorMessageGen}
import org.sireum.awas.collector._
import org.sireum.awas.fptc.FlowNode.Edge
import org.sireum.awas.fptc.{FlowGraph, FlowNode}
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._


class ErrorReachabilityImpl[Node](st: SymbolTable, graph: FlowGraph[FlowNode, FlowNode.Edge]) extends
  PortReachabilityImpl(st, graph) with ErrorReachability[FlowNode]
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

    if (port.startsWith(H.PORT_TYPE) && graph.getNode(port).isDefined) {
      workList = workList ++ errors.map((port, _))
    } else {
      resError += errorMessageGen(MISSING_CRITERIA,
        port, ReachAnalysisStage.Port)
    }
      while (workList.nonEmpty) {
        val current = workList.head
        if (addErrors(result, current._1, current._2)) {
          val temp = (if (isForward) getSuccessorError(current)
          else getPredecessorError(current))
          workList = workList ++ temp.tuples
          resEdges = resEdges ++ temp.edges
          resFlows = resFlows ++ temp.flows
          resError = resError ++ temp.errors
        }
        workList = workList.tail
      }
    collector.Collector(st,
      isetEmpty + graph,
      result.map(v => (v._1, v._2.toSet)).toMap,
      resFlows, resEdges, isForward, isetEmpty[ResourceUri] + port, resError)
  }

  def getPredecessorError(tuple: (ResourceUri, ResourceUri)): FlowErrorNextCollector = {
    assert(tuple._1.startsWith(H.PORT_TYPE))
      val node = graph.getNode(tuple._1)
      if (node.isDefined && tuple._1.startsWith(H.PORT_OUT_TYPE)) {
        node.get.errorBackward(tuple)
      } else {
        var result = isetEmpty[(ResourceUri, ResourceUri)]
        var edges = isetEmpty[Edge]
        //propagation case
        graph.getEdgeForPort(tuple._1).foreach { e =>
          if (e.sourcePort.isDefined) {
            result = result + ((e.sourcePort.get, tuple._2))
            edges += e
            }
        }
        collector.FlowErrorNextCollector(result, edges, isetEmpty[ResourceUri], isetEmpty[Tag])
      }
  }

  def getSuccessorError(tuple: (ResourceUri, ResourceUri)): FlowErrorNextCollector = {
    //only port can be associated with error to calculate successor error
    var result = ilistEmpty[(ResourceUri, ResourceUri)]
    assert(tuple._1.startsWith(H.PORT_TYPE))
    val node = graph.getNode(tuple._1)
    if (node.isDefined && tuple._1.startsWith(H.PORT_IN_TYPE)) {
      node.get.errorForward(tuple)
    } else {
      var result = ilistEmpty[(ResourceUri, ResourceUri)]
      var edges = isetEmpty[Edge]
      //propagation case
      graph.getEdgeForPort(tuple._1).foreach { e =>
        if (e.targetPort.isDefined) {
          edges += e
          result = result :+ (e.targetPort.get, tuple._2)
        }
      }
      collector.FlowErrorNextCollector(result.toSet, edges, isetEmpty[ResourceUri], isetEmpty[Tag])
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
    val paths = reachPath(sourcePort, targetPort).getPaths.par.flatMap(it => pathErrorRefine(it, sourcePort,
      sourceErrors, targetPort, targetErrors)).toSet
    Collector(st, isetEmpty + graph, paths.toVector, Some(ResultType.Error))
  }


  //TODO: Rework this method, refactor, high cyclomatic complex
  private def pathErrorRefine(path: Collector,
                              sourcePort: ResourceUri,
                              sourceErrors: ISet[ResourceUri],
                              targetPort: ResourceUri,
                              targetErrors: ISet[ResourceUri]):
  ISet[Collector] = {
    var paths = isetEmpty[FlowErrorPathCollector]

    sourceErrors.foreach(e => paths = paths +
      collector.FlowErrorPathCollector(ilistEmpty[(ResourceUri, ResourceUri)] :+ (sourcePort, e),
        isetEmpty[Edge], isetEmpty[ResourceUri], isetEmpty[Tag]))

    var result = isetEmpty[Collector]

    var resPaths = isetEmpty[FlowErrorPathCollector]

    var resCycle = isetEmpty[FlowErrorPathCollector]

    while (paths.nonEmpty) {
      val current = paths.head
      paths = paths - current
      if (current.path.last._1 == targetPort) {
        resPaths = resPaths + current
      } else {
        val tNext = getSuccessorError(current.path.last)
        tNext.tuples.foreach { t =>
          if (path.getPorts.contains(t._1)) {
            if (current.path.toSet.contains(t)) {
              resCycle = resCycle + current
            } else {
              if (tNext.edges.nonEmpty) {
                paths = paths + FlowErrorPathCollector(current.path :+ t,
                  current.edges union graph.getEdges(current.path.last._1, t._1),
                  current.flows, current.errors union tNext.errors)
              } else {
                if (tNext.flows.size <= 1) {
                  paths = paths + FlowErrorPathCollector(current.path :+ t, current.edges,
                    current.flows ++ tNext.flows, current.errors union tNext.errors)
                } else { //more than one flow so, branch for each flow
                  val cnode = graph.getNode(current.path.last._1)
                  val nnode = graph.getNode(t._1)
                  if (cnode.isDefined && nnode.isDefined &&
                    cnode.get == nnode.get) {
                    var flows = isetEmpty[ResourceUri]
                    if (cnode.get.isComponent) {
                      flows ++= tNext.flows.filter(it => nnode.get.getFlows.get(it).isDefined &&
                        nnode.get.getFlows(it).toPortUri.isDefined &&
                        nnode.get.getFlows(it).toPortUri.get == t._1 &&
                        nnode.get.getFlows(it).toFaults.contains(t._2)
                      )
                    } else {
                      flows ++= tNext.flows.filter(it => nnode.get.getFlows.get(it).isDefined &&
                        nnode.get.getFlows(it).toFaults.contains(t._2))
                    }
                    paths = paths + FlowErrorPathCollector(current.path :+ t, current.edges,
                      current.flows ++ flows, current.errors union tNext.errors)
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
      tempPaths = tempPaths + FlowErrorNextCollector(it._1.path.toSet, it._1.edges, it._1.flows, it._1.errors)

      val cycles = unionPaths(it._1, it._2.foldLeft(collector.FlowErrorPathCollector(ivectorEmpty[(ResourceUri, ResourceUri)],
        isetEmpty[Edge], isetEmpty[ResourceUri], isetEmpty[Tag]))((c, n) => unionPaths(c, n)))

      tempPaths = tempPaths + FlowErrorNextCollector(cycles.path.toSet, cycles.edges, cycles.flows, cycles.errors)

    }

    result = result ++ tempPaths.map(it => collector.Collector(st, isetEmpty + graph, it.tuples.map(x => (x._1, isetEmpty[ResourceUri] + x._2)).toMap,
      ResultType.Error, it.edges, it.flows, isetEmpty[ResourceUri] + sourcePort + targetPort, it.errors))


    //    result = result + Collector(st, graph,
    //      current.path.map(x => (x._1, isetEmpty[ResourceUri] + x._2)).toMap,
    //      ResultType.Error, current.edges, current.flows,
    //      isetEmpty[ResourceUri] + sourcePort+targetPort, current.errors)

    result
  }

  def unionPaths(op1: FlowErrorPathCollector, op2: FlowErrorPathCollector): FlowErrorPathCollector = {
    FlowErrorPathCollector((op1.path.toSet ++ op2.path.toSet).toVector, op1.edges ++ op2.edges,
      op1.flows ++ op2.flows, op1.errors ++ op2.errors)
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