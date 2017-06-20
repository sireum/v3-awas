/*
 Copyright (c) 2017, Hariharan Thiagarajan, Robby, Kansas State University
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

package org.sireum.awas.codegen

import org.sireum.awas.ast._
import org.sireum.awas.symbol.SymbolTable
import org.sireum.util.Rewriter.TraversalMode
import org.sireum.util._

import scala.collection.immutable.ListMap

object ContextInSensitiveGen {
  var varNameCounter = 0

  def apply(m: Model, st: SymbolTable): Model = {
    varNameCounter = 0
    val nm = build(m, st)
    nm
  }

  def build(m: Model, st: SymbolTable): Model = {
    rewriteComponent(m)
  }

  def rewriteComponent(oldComp: Model): Model = {
    val newModel = org.sireum.awas.ast.Rewriter.build[Model](TraversalMode.TOP_DOWN) {
      case c: ComponentDecl =>
        var propMap = imapEmpty[Id, MSet[Name]]
        if (c.behaviour.isDefined) {
          val props = ListMap(mineBehaviorProp(c.behaviour.get).toSeq.sortBy(_._1.value): _*).map { f => Propagation(f._1, f._2) }
          val flows = mineBehaviorFlow(c.behaviour.get)
          ComponentDecl(c.compName,
            c.withSM, c.ports, props.toVector,
            flows, c.transitions, c.behaviour, c.properties)
        } else c
    }(oldComp)
    newModel.nodeLocMap = oldComp.nodeLocMap
    newModel.fileUriOpt = oldComp.fileUriOpt
    newModel
  }

  def mineBehaviorProp(behaviour: Behaviour): IMap[Id, IVector[Fault]] = {
    var res = imapEmpty[Id, MSet[Fault]]
    behaviour.exprs.foreach {
      expr => {
        if (expr.lhs.isDefined) {
          expr.lhs.get.tokens.foreach {
            f =>
              val (id, et) = mineTokens(f._1, f._2)
              if (res.keySet.contains(id))
                res(id) ++ et
              else
                res = res + (id -> et)
          }
        }
        if (expr.rhs.isDefined) {
          expr.rhs.get.tokens.foreach {
            f =>
              val (id, et) = mineTokens(f._1, f._2)
              if (res.keySet.contains(id))
                res(id) ++= et
              else
                res = res + (id -> et)
          }
        }
      }
    }
    res.map { f => f._1 -> f._2.toVector }
  }

  //TODO : Refactor this function - function for each case
  def mineBehaviorFlow(behaviour: Behaviour): IVector[Flow] = {
    var res = ivectorEmpty[Flow]
    behaviour.exprs.foreach {
      expr => {
        if (expr.lhs.isEmpty) {

          if (expr.rhs.isDefined) {
            expr.rhs.get.tokens.foreach {
              rt =>
                val rtinfo = mineTokens(rt._1, rt._2)
                res :+= Flow(buildNewId("flow"),
                  None,
                  ivectorEmpty[Fault],
                  Some(rtinfo._1), rtinfo._2.toVector)
            }
          }
        }
        if (expr.rhs.isEmpty) {

          if (expr.lhs.isDefined) {
            expr.lhs.get.tokens.foreach {
              lt =>
                val ltinfo = mineTokens(lt._1, lt._2)
                res :+= Flow(buildNewId("flow"),
                  Some(ltinfo._1), ltinfo._2.toVector,
                  None,
                  ivectorEmpty[Fault])
            }
          }

        }

        if(expr.lhs.isDefined && expr.rhs.isDefined) {
          expr.lhs.get.tokens.foreach{
            ltup =>
              val linfo = mineTokens(ltup._1, ltup._2)
              expr.rhs.get.tokens.foreach{
                rtup =>
                  val rinfo = mineTokens(rtup._1, rtup._2)
                  res :+= Flow(buildNewId("flow"),
                    Some(linfo._1),
                    linfo._2.toVector,
                    Some(rinfo._1),
                    rinfo._2.toVector
                  )
              }
          }
        }
      }
    }
    res.sortBy(_.id.value)
  }

  def mineTokens(id: Id, one: One): (Id, MSet[Fault]) = {
    val newId = buildId(id.value)
    val res = msetEmpty[Fault]
    one match {
      case fault: Fault =>
        res += Fault(buildName(fault.enum.value))
      case fs: FaultSet =>
        fs.value.foreach {
          fse => res += Fault(buildName(fse.enum.value))
        }
    }
    (newId, res)
  }

  def buildId(value: String): Id = {
    val id = Id(value)
    id
  }

  def buildName(ids: IVector[Id]): Name = {
    val name = Name(ids.map(_.value).map(buildId))
    name
  }

  def buildNewId(prefix: String): Id = {
    varNameCounter = varNameCounter + 1
    Id(prefix + "_" + varNameCounter.toString)
  }

}
