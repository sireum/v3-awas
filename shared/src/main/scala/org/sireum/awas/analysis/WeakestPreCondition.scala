package org.sireum.awas.analysis
import org.sireum.awas.collector.Collector
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.{IMap, ISet}

trait WeakestPreCondition {

  def backwardReach(states : ISet[IMap[ResourceUri, ISet[ResourceUri]]],
                    behaviors : ISet[ResourceUri])
  : ISet[Collector]

  def forwardReach(states : ISet[IMap[ResourceUri, ISet[ResourceUri]]],
                   behaviors : ISet[ResourceUri])
  : ISet[Collector]

}

object WeakestPreCondition {
  def apply(st : SymbolTable): WeakestPreCondition = {
    //    new WeakestPreConditionImpl(st)
    ???
  }
}
