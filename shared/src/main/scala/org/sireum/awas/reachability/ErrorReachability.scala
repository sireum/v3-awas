package org.sireum.awas.reachability

import org.sireum.awas.collector.Collector
import org.sireum.awas.fptc.{FlowGraph, FlowNode}
import org.sireum.awas.query.ConstraintExpr
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

trait ErrorReachability[Node] extends PortReachability[Node] {
  def forwardErrorReach(port: ResourceUri, errors: ISet[ResourceUri]): Collector

  def backwardErrorReach(port: ResourceUri, errors: ISet[ResourceUri]): Collector

  def forwardErrorSetReach(errorRes: IMap[ResourceUri, Set[ResourceUri]]): Collector

  def backwardErrorSetReach(errorRes: IMap[ResourceUri, Set[ResourceUri]]): Collector

  def errorPathReach(sourcePort: ResourceUri, sourceErrors: ISet[ResourceUri],
                     targetPort: ResourceUri, targetErrors: ISet[ResourceUri]):
  Collector

  def errorPathReachMap(source: IMap[ResourceUri, ISet[ResourceUri]],
                        target: IMap[ResourceUri, ISet[ResourceUri]]): Collector

  def errorPathReachMapWith(source: IMap[ResourceUri, ISet[ResourceUri]],
                            target: IMap[ResourceUri, ISet[ResourceUri]],
                            constraint: ConstraintExpr): Collector

  def getPredecessor(currentPort: ResourceUri,
                     currentError: ResourceUri)
  : IMap[ResourceUri, ISet[ResourceUri]]


  def getSuccessor(currentPort: ResourceUri,
                   currentError: ResourceUri)
  : IMap[ResourceUri, ISet[ResourceUri]]
}

object ErrorReachability {
  def apply(st: SymbolTable): ErrorReachability[FlowNode] = {
    new ErrorReachabilityImpl[FlowNode](st)
  }
}
