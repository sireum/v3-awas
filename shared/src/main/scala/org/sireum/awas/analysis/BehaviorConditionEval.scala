package org.sireum.awas.analysis

import org.sireum.awas.ast.{
  All,
  And,
  ConditionTuple,
  EventRef,
  Fault,
  FaultSet,
  Or,
  OrLess,
  OrMore,
  PrimaryCondition,
  Tuple
}
import org.sireum.awas.symbol.{Resource, SymbolTable}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.{IMap, ISet, isetEmpty}

class BehaviorConditionEval(store: IMap[ResourceUri, ISet[ResourceUri]]) {

  def eval(condition: ConditionTuple): Boolean = {
    condition match {
      case and: And => eval(and)
      case or: Or => eval(or)
      case ormore: OrMore => eval(ormore)
      case orless: OrLess => eval(orless)
      case all: All => eval(all)
      case primary: PrimaryCondition => eval(primary)
    }
  }

  private def eval(and: And): Boolean = {
    eval(and.lhs) && eval(and.rhs)
  }

  private def eval(or: Or): Boolean = {
    eval(or.lhs) || eval(or.rhs)
  }

  private def eval(orMore: OrMore): Boolean = {
    val count = orMore.conds.foldLeft(0)((r, c) => if (eval(c)) r + 1 else r)
    count >= orMore.value
  }

  private def eval(orLess: OrLess): Boolean = {
    val count = orLess.conds.foldLeft(0)((r, c) => if (eval(c)) r + 1 else r)
    count <= orLess.value
  }

  private def eval(all: All): Boolean = {
    all.conds.forall(eval)
  }

  private def eval(primary: PrimaryCondition): Boolean = {
    primary match {
      case ef: EventRef => true
      case tup: Tuple => {
        tup.tokens.exists { t =>
          assert(Resource.getResource(t._1).isDefined, "port name must be valid :" + t._1.value)
          val portRes = Resource.getResource(t._1).get
          val errorUris = (t._2 match {
            case f: Fault => isetEmpty + Resource.getResource(f)
            case fs: FaultSet => isetEmpty ++ fs.value.map(Resource.getResource)
          }).flatten.map(_.toUri)
          if (store.contains(portRes.toUri) && errorUris.nonEmpty) {
            errorUris.exists(e => store(portRes.toUri).contains(e))
          } else {
            false
          }
        }
      }
    }
  }

}

object BehaviorConditionEval {

  def apply(condition: ConditionTuple, store: IMap[ResourceUri, ISet[ResourceUri]]): Boolean = {
    new BehaviorConditionEval(store).eval(condition)
  }

  def getTuples(condition: ConditionTuple): ISet[Tuple] = {
    val x = condition match {
      case and: And => getTuples(and.lhs) ++ getTuples(and.rhs)
      case or: Or => getTuples(or.lhs) ++ getTuples(or.rhs)
      case orMore: OrMore => orMore.conds.flatMap(getTuples).toSet
      case orLess: OrLess => orLess.conds.flatMap(getTuples).toSet
      case all: All => all.conds.flatMap(getTuples).toSet
      case primary: PrimaryCondition => {
        primary match {
          case ef: EventRef => isetEmpty[Tuple]
          case tup: Tuple => isetEmpty[Tuple] + tup
        }
      }
    }
//    println(x)
    x
  }
}
