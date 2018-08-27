/*
 * // #Sireum
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
 *
 */

package org.sireum.awas.reachability

import org.sireum.awas.collector
import org.sireum.awas.collector.CollectorErrorHelper.{MISSING_CRITERIA, ReachAnalysisStage, errorMessageGen}
import org.sireum.awas.collector._
import org.sireum.awas.fptc.FlowNode
import org.sireum.awas.fptc.FlowNode.Edge
import org.sireum.awas.query.{ConstraintExpr, ConstraintKind}
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
      sourceErrors, targetPort, targetErrors, None)).toSet
    Collector(st,
      paths.map(_.getGraphs).fold(isetEmpty[Graph])((s, t) => s ++ t),
      ilinkedSetEmpty ++ paths.toVector, Some(ResultType.Error))
  }

  def errorPathReachMapWith(source: IMap[ResourceUri, ISet[ResourceUri]],
                            target: IMap[ResourceUri, ISet[ResourceUri]],
                            constraint: ConstraintExpr): Collector = {
    var reformatedArgs = isetEmpty[(ResourceUri, ISet[ResourceUri], ResourceUri, ISet[ResourceUri], ConstraintExpr)]

    source.foreach { s => target.foreach { t => reformatedArgs += ((s._1, s._2, t._1, t._2, constraint)) } }

    val pathsConst = reformatedArgs.flatMap(e => reachPath(e._1, e._3).getPaths.flatMap(it =>
      pathErrorRefine(it, e._1, e._2, e._3, e._4, Some(e._5))
    ))
    Collector(st,
      pathsConst.map(_.getGraphs).fold(isetEmpty[Graph])((s, t) => s ++ t),
      ilinkedSetEmpty ++ pathsConst.toVector, Some(ResultType.Error))
  }

  def checkPathForAllSome(path: FlowErrorPathCollector, constraint: ConstraintExpr): Boolean = {
    constraint.kind match {
      case ConstraintKind.All => constraint.simple.get.getPortErrors.forall(pe =>
        pe._2.map((pe._1, _)).subsetOf(path.path.toSet))
      case ConstraintKind.Some => constraint.simple.get.getPortErrors.flatMap(pe =>
        pe._2.map((pe._1, _))).toSet.intersect(path.path.toSet).nonEmpty
      case _ => true
    }
  }

  def checkPathForNone(path: FlowErrorPathCollector, constraint: ConstraintExpr): Boolean = {
    constraint.kind match {
      case ConstraintKind.None => constraint.simple.get.getPortErrors.flatMap(pe =>
        pe._2.map((pe._1, _))).toSet.intersect(path.path.toSet).isEmpty
      case _ => true
    }
  }



  //TODO: Rework this method, refactor, high cyclomatic complex
  /**
    * Refined a path computed only with ports into ports and errors
    *
    * @param path         : port path computed using portReachabilityImpl
    * @param sourcePort   : source port
    * @param sourceErrors : source error
    * @param targetPort   : target port
    * @param targetErrors : target error
    * @return : set of paths as collectors
    */
  private def pathErrorRefine(path: Collector,
                              sourcePort: ResourceUri,
                              sourceErrors: ISet[ResourceUri],
                              targetPort: ResourceUri,
                              targetErrors: ISet[ResourceUri],
                              constraint: Option[ConstraintExpr]):
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
      if (constraint.isDefined) {
        if (checkPathForNone(it._1, constraint.get) && checkPathForAllSome(it._1, constraint.get)) {
          tempPaths = tempPaths + FlowErrorNextCollector(it._1.path.toSet,
            it._1.edges, it._1.flows, it._1.errors, it._1.graphs)
        }
      } else {
        tempPaths = tempPaths + FlowErrorNextCollector(it._1.path.toSet,
          it._1.edges, it._1.flows, it._1.errors, it._1.graphs)
      }
      if (it._2.nonEmpty) {
        if (constraint.isDefined) {
          if (checkPathForNone(it._1, constraint.get)) {
            val filtered = it._2.filter(checkPathForNone(_, constraint.get))
            if (filtered.nonEmpty) {
              val cycles = it._1.union(filtered.foldLeft(
                collector.FlowErrorPathCollector(ivectorEmpty[(ResourceUri, ResourceUri)],
                  isetEmpty[Edge], isetEmpty[ResourceUri], isetEmpty[Tag], isetEmpty))(_.union(_)))
              if (checkPathForAllSome(cycles, constraint.get)) {
                tempPaths = tempPaths +
                  FlowErrorNextCollector(cycles.path.toSet, cycles.edges,
                    cycles.flows, cycles.errors, cycles.graphs)
              }
            }
          }
        } else {

          val cycles = it._1.union(it._2.foldLeft(
            collector.FlowErrorPathCollector(ivectorEmpty[(ResourceUri, ResourceUri)],
              isetEmpty[Edge], isetEmpty[ResourceUri], isetEmpty[Tag], isetEmpty))(_.union(_)))

          tempPaths = tempPaths + FlowErrorNextCollector(cycles.path.toSet, cycles.edges,
            cycles.flows, cycles.errors, cycles.graphs)
        }
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

  override def getPredecessor(currentPort: ResourceUri,
                              currentError: ResourceUri)
  : IMap[ResourceUri, ISet[ResourceUri]] = {
    previousError((currentPort, currentError)).flatMap(_.tuples)
      .groupBy(_._1).mapValues(_.map(_._2))
  }

  override def getSuccessor(currentPort: ResourceUri,
                            currentError: ResourceUri)
  : IMap[ResourceUri, ISet[ResourceUri]] = {
    nextError((currentPort, currentError)).flatMap(_.tuples)
      .groupBy(_._1).mapValues(_.map(_._2))
  }
}