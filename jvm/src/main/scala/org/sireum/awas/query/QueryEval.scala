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

import org.sireum.awas.fptc.{FlowGraph, FlowNode}
import org.sireum.awas.reachability.ErrorReachability
import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.{ISet, _}

object QueryEval {
  type Result = Map[String, QueryResult]

  def apply(m: Model, graph: FlowGraph[FlowNode], st: SymbolTable): Result = {
    new QueryEval(graph, st).eval(m)
  }
}

final class QueryEval(graph: FlowGraph[FlowNode], st: SymbolTable) {
  var result = ilinkedMapEmpty[String, QRes]

  var queries = ilinkedMapEmpty[String, QueryExpr]

  def eval(m: Model): QueryEval.Result = {
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

  def eval(qexp: QueryExpr): QRes = {
    qexp match {
      case binary: BinaryExpr => eval(binary)
      case primary: PrimaryExpr => eval(primary)
    }
  }

  def eval(bexp: BinaryExpr): QRes = {
    val lhs: QRes = eval(bexp.lhs)
    val rhs: QRes = eval(bexp.rhs)
    bexp.op match {
      case "union" => QueryResult.union(lhs, rhs)
      case "intersect" => QueryResult.intersect(lhs, rhs)
      case "-" => QueryResult.diff(lhs, rhs)
      case "->" => {
        val er = ErrorReachability(graph)
        if (lhs.unitRes.isEmpty && rhs.unitRes.nonEmpty) {
          backwardReach(rhs)
        } else if (rhs.unitRes.isEmpty && lhs.unitRes.nonEmpty) {
          forwardReach(lhs)
        } else if (lhs.unitRes.nonEmpty && rhs.unitRes.nonEmpty) {
          val lhsres = forwardReach(lhs)
          val rhsres = backwardReach(rhs)
          QueryResult.intersect(lhsres, rhsres)
        } else {
          QRes(isetEmpty[UnitResult])
        }
      }
    }
  }

  def backwardReach(criterion: QRes): QRes = {
    val er = ErrorReachability(graph)
    val minType = QueryResult.getMinType(criterion)
    if (minType <= QResMinType.PathUri) {
      val ac = QueryResult.convertToType(criterion, QResMinType.Uri)
      QRes(ac.unitRes.flatMap(it =>
        er.backwardReach(it.asInstanceOf[UriResult].value)).map(UriResult))
    } else {
      val ac = QueryResult.convertToType(criterion, QResMinType.Error)
      var res = imapEmpty[ResourceUri, ISet[ResourceUri]]
      ac.unitRes.foreach {
        it =>
          val e = it.asInstanceOf[ErrorResult]
          er.backwardErrorReach(e.port, e.errors).foreach {
            f => res += (f._1 -> (res.getOrElse(f._1, isetEmpty[ResourceUri]) ++ f._2))
          }
      }
      QRes(res.map(it => ErrorResult(it._1, it._2)).toSet)
    }
  }

  def forwardReach(criterion: QRes): QRes = {
    val er = ErrorReachability(graph)
    val minType = QueryResult.getMinType(criterion)
    if (minType <= QResMinType.PathUri) {
      val ac = QueryResult.convertToType(criterion, QResMinType.Uri)
      QRes(ac.unitRes.flatMap(it =>
        er.forwardReach(it.asInstanceOf[UriResult].value)).map(UriResult))
    } else {
      val ac = QueryResult.convertToType(criterion, QResMinType.Error)
      var res = imapEmpty[ResourceUri, ISet[ResourceUri]]
      ac.unitRes.foreach {
        it =>
          val e = it.asInstanceOf[ErrorResult]
          er.forwardErrorReach(e.port, e.errors).foreach {
            f => res += (f._1 -> (res.getOrElse(f._1, isetEmpty[ResourceUri]) ++ f._2))
          }
      }
      QRes(res.map(it => ErrorResult(it._1, it._2)).toSet)
    }
  }

  def eval(pexp: PrimaryExpr): QRes = {
    pexp match {
      case NodeNameError(nodeName, errorSet) => eval(nodeName, errorSet)

      case Paren(expr) => eval(expr)

      case NodeSet(sets) => sets.foldLeft(QRes(isetEmpty[UnitResult]))((a, b) =>
        QueryResult.union(a, eval(b)))

      case NodeEmpty() => QRes(isetEmpty[UnitResult])

      case QueryName(id) => result.getOrElse(id.value, QRes(isetEmpty[UnitResult]))
    }
  }

  def eval(nn: NodeName, errorSet: ISeq[ISeq[Id]]): QRes = {
    var res = isetEmpty[ResourceUri]
    if (errorSet.isEmpty) {
      val uri = getCompOrPortUri(nn)
      if (uri.isDefined) {
        res = res + uri.get
      }
      QRes(res.map(UriResult))
    } else {
      assert(nn.ids.length == 2)
      val uri = getCompOrPortUri(nn)
      if (uri.isDefined) {
        var errorUri = isetEmpty[ResourceUri]
        errorSet.foreach { f =>
          val x = SymbolTableHelper.getErrorUri(st, f.map(_.value).mkString("."))
          if (x.isDefined) {
            errorUri = errorUri + x.get
          }
        }
        QRes(isetEmpty[UnitResult] +
          ErrorResult(uri.get, errorUri))
      } else {
        QRes(isetEmpty[UnitResult])
      }
    }
  }

  def getCompOrPortUri(n: NodeName): Option[ResourceUri] = {
    if (n.ids.length == 2) {
      val compId = n.ids(0)
      val pid = n.ids(1)
      val comp = st.components.find(_.endsWith(compId.value))
      if (comp.isDefined) {
        val port = st.component(comp.get).ports.find(_.id.value == pid.value)
        if (port.isDefined && Resource.getResource(port.get).isDefined) {
          Some(Resource.getResource(port.get).get.toUri)
        } else {
          None
        }
      } else {
        None
      }
    } else {
      val compId = n.ids(0)
      st.components.find(_.endsWith(compId.value))
    }
  }
}