/*
 Copyright (c) 2017, Robby, Kansas State University
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

package org.sireum.awas.query

import org.sireum.awas.fptc.{FptcGraph, FptcNode}
import org.sireum.awas.reachability.PortReachability
import org.sireum.awas.symbol.{Resource, SymbolTable}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.{ISet, _}

object QueryEval {
  type QueryResult = Map[String, Set[ResourceUri]]

  def apply(m: Model, graph: FptcGraph[FptcNode], st: SymbolTable): QueryResult = {
    new QueryEval(graph, st).eval(m)
  }
}

final class QueryEval(graph: FptcGraph[FptcNode], st: SymbolTable) {
  var result = ilinkedMapEmpty[String, Set[ResourceUri]]

  var queries = ilinkedMapEmpty[String, QueryExpr]

  def eval(m: Model): QueryEval.QueryResult = {
    m.queryStmt.foreach {
      q =>
        if (!queries.keySet.contains(q.qName.value)) {
          queries += (q.qName.value -> q.qExpr)
        }
    }
    queries.foreach {
      q => result = result + (q._1 -> eval(q._2))
    }
    result
  }

  def eval(qexp: QueryExpr): Set[ResourceUri] = {
    qexp match {
      case binary: BinaryExpr => eval(binary)
      case primary: PrimaryExpr => eval(primary)
    }
  }

  def eval(bexp: BinaryExpr): Set[ResourceUri] = {
    val lhs: ISet[ResourceUri] = eval(bexp.lhs)
    val rhs: ISet[ResourceUri] = eval(bexp.rhs)
    bexp.op match {
      case "union" => lhs.union(rhs)
      case "intersect" => lhs.intersect(rhs)
      case "-" => lhs.diff(rhs)
      case "->" => {
        var res = isetEmpty[ResourceUri]
        val pr = PortReachability(graph)
        if (lhs.isEmpty && rhs.nonEmpty) {
          res = pr.backwardReachSet(rhs)
        } else if (rhs.isEmpty && lhs.nonEmpty) {
          res = pr.forwardReachSet(lhs)
        } else if (!lhs.isEmpty && !rhs.isEmpty) {
          val lhsres = pr.forwardReachSet(lhs)
          val rhsres = pr.backwardReachSet(rhs)
          res = lhsres.intersect(rhsres)
        } else {
          res = isetEmpty[ResourceUri]
        }
        res
      }
      case "<-" => {
        var res = isetEmpty[ResourceUri]
        val pr = PortReachability(graph)
        if (lhs.isEmpty && rhs.nonEmpty) {
          res = pr.forwardReachSet(rhs)
        } else if (rhs.isEmpty && lhs.nonEmpty) {
          res = pr.backwardReachSet(lhs)
        } else if (!lhs.isEmpty && !rhs.isEmpty) {
          val rhsres = pr.forwardReachSet(rhs)
          val lhsres = pr.backwardReachSet(lhs)
          res = lhsres.intersect(rhsres)
        }
        res
      }
    }
  }

  def eval(pexp: PrimaryExpr): ISet[ResourceUri] = {
    pexp match {
      case NodeNameError(nodeName, errorSet) => eval(nodeName)

      case Paren(expr) => eval(expr)

      case NodeSet(sets) => sets.flatMap(eval).toSet

      case NodeEmpty() => isetEmpty[ResourceUri]

      case QueryName(id) => result.getOrElse(id.value, isetEmpty[ResourceUri])
    }
  }

  def eval(nn: NodeName): ISet[ResourceUri] = {
    var res = isetEmpty[ResourceUri]
    if (nn.ids.length == 2) {
      val compId = nn.ids(0)
      val pid = nn.ids(1)
      val comp = st.components.find(_.endsWith(compId.value))
      if (comp.isDefined) {
        val port = st.component(comp.get).ports.find(_.id.value == pid.value)
        if (port.isDefined && Resource.getResource(port.get).isDefined) {
          res = res + Resource.getResource(port.get).get.toUri
        }
      }
    }
    if (nn.ids.length == 1) {
      val compId = nn.ids(0)
      val comp = st.components.find(_.endsWith(compId.value))
      if (comp.isDefined) {
        res += comp.get
      }
    }
    res
  }
}