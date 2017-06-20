package org.sireum.awas.reachability

import org.sireum.awas.fptc.{FlowGraph, FlowNode}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._


class ErrorReachabilityImpl[Node](graph: FlowGraph[FlowNode]) extends
  PortReachabilityImpl(graph) with ErrorReachability[FlowNode]
  with PortReachability[FlowNode] with BasicReachability[FlowNode] {

  override def forwardErrorSetReach(errorRes: IMap[ResourceUri, Set[ResourceUri]]):
  IMap[ResourceUri, Set[ResourceUri]] = {
    var result = imapEmpty[ResourceUri, Set[ResourceUri]]
    errorRes.foldLeft(result) { case (a, (k, v)) => unionErrors(a, forwardErrorReach(k, v)) }
    result
  }

  override def forwardErrorReach(port: ResourceUri, errors: ISet[ResourceUri]):
  IMap[ResourceUri, Set[ResourceUri]] = {
    errorReach(port, errors, isForward = true)
  }

  override def backwardErrorSetReach(errorRes: IMap[ResourceUri, Set[ResourceUri]])
  : IMap[ResourceUri, Set[ResourceUri]] = {
    var result = imapEmpty[ResourceUri, Set[ResourceUri]]
    errorRes.foldLeft(result) { case (a, (k, v)) => unionErrors(a, backwardErrorReach(k, v)) }
    result
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
  IMap[ResourceUri, Set[ResourceUri]] = {
    errorReach(port, errors, isForward = false)
  }

  def errorReach(port: ResourceUri, errors: ISet[ResourceUri], isForward: Boolean):
  IMap[ResourceUri, Set[ResourceUri]] = {
    var workList = ilistEmpty[(ResourceUri, ResourceUri)]
    val result = mmapEmpty[ResourceUri, MSet[ResourceUri]]
    if (port.startsWith(H.PORT_TYPE) && graph.getNode(port).isDefined) {
      workList = workList ++ errors.map((port, _))
      while (workList.nonEmpty) {
        val current = workList.head
        if (addErrors(result, current._1, current._2)) {
          workList = workList ++ (if (isForward) getSuccessorError(current)
          else getPredecessorError(current))

        }
        workList = workList.tail
      }
    }
    result.map(v => (v._1, v._2.toSet)).toMap
  }

  def getPredecessorError(tuple: (ResourceUri, ResourceUri)): IList[(ResourceUri, ResourceUri)] = {
    var result = ilistEmpty[(ResourceUri, ResourceUri)]
    if (tuple._1.startsWith(H.PORT_TYPE)) {
      val node = graph.getNode(tuple._1)
      if (node.isDefined && tuple._1.startsWith(H.PORT_OUT_TYPE)) {
        result = result ++ node.get.errorBackward(tuple)
      } else if (node.isDefined) {
        //propagation case
        graph.getEdgeForPort(tuple._1).foreach {
          e =>
            e.sourcePort match {
              case Some(x) => result = result :+ (x, tuple._2)
              case _ =>
            }
        }
      }
    }
    result
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
  ISet[IMap[ResourceUri, Set[ResourceUri]]] = {
    source.toSet.flatMap { x: ((ResourceUri, ISet[ResourceUri])) =>
      target.toSet.flatMap { y: ((ResourceUri, ISet[ResourceUri])) =>
        errorPathReach(x._1, x._2, y._1, y._2)
      }
    }
  }

  def errorPathReach(sourcePort: ResourceUri, sourceErrors: ISet[ResourceUri],
                     targetPort: ResourceUri, targetErrors: ISet[ResourceUri]):
  ISet[IMap[ResourceUri, Set[ResourceUri]]] = {
    reachPath(sourcePort, targetPort).par.flatMap(pathErrorRefine(_, sourcePort,
      sourceErrors, targetPort, targetErrors)).seq
  }

  private def pathErrorRefine(path: ISet[ResourceUri],
                              sourcePort: ResourceUri,
                              sourceErrors: ISet[ResourceUri],
                              targetPort: ResourceUri,
                              targetErrors: ISet[ResourceUri]):
  ISet[IMap[ResourceUri, ISet[ResourceUri]]] = {
    var paths = isetEmpty[ISeq[(ResourceUri, ResourceUri)]]

    sourceErrors.foreach(e => paths = paths +
      (ilistEmpty[(ResourceUri, ResourceUri)] :+ (sourcePort, e)))
    var result = isetEmpty[IMap[ResourceUri, ISet[ResourceUri]]]

    while (paths.nonEmpty) {
      val current = paths.head
      paths = paths - current
      if (current.last._1 == targetPort) {
        result = result + current.map(x => (x._1, isetEmpty[ResourceUri] + x._2)).toMap
      } else {
        getSuccessorError(current.last).filter(x => path.contains(x._1))
          .foreach(x => paths = paths + (current :+ x))
      }
    }
    result
  }

  def getSuccessorError(tuple: (ResourceUri, ResourceUri)): IList[(ResourceUri, ResourceUri)] = {
    //only port can be associated with error to calculate successor error
    var result = ilistEmpty[(ResourceUri, ResourceUri)]
    if (tuple._1.startsWith(H.PORT_TYPE)) {
      val node = graph.getNode(tuple._1)
      if (node.isDefined && tuple._1.startsWith(H.PORT_IN_TYPE)) {
        result = result ++ node.get.errorForward(tuple)
      } else if (node.isDefined) {
        //propagation case
        graph.getEdgeForPort(tuple._1).foreach {
          e =>
            e.targetPort match {
              case Some(x) => result = result :+ (x, tuple._2)
              case _ =>
            }
        }
      }
    }
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