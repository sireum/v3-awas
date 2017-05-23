package org.sireum.awas.reachability

import org.sireum.awas.fptc.{FlowGraph, FlowNode}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.{IMap, ISet}

trait ErrorReachability[Node] extends PortReachability[Node] {
  def forwardErrorReach(port: ResourceUri, errors: ISet[ResourceUri]): Map[ResourceUri, Set[ResourceUri]]

  def backwardErrorReach(port: ResourceUri, errors: ISet[ResourceUri]): Map[ResourceUri, Set[ResourceUri]]

  def forwardErrorSetReach(errorRes: IMap[ResourceUri, Set[ResourceUri]]): Map[ResourceUri, Set[ResourceUri]]

  def backwardErrorSetReach(errorRes: IMap[ResourceUri, Set[ResourceUri]]): Map[ResourceUri, Set[ResourceUri]]
}

object ErrorReachability {
  def apply(graph: FlowGraph[FlowNode]): ErrorReachability[FlowNode] = {
    new ErrorReachabilityImpl[FlowNode](graph)
  }
}
