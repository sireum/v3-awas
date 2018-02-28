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

import org.sireum.awas.query.FilterID.FilterID
import org.sireum.util.{IVector, ivector, ivectorEmpty}

object QueryNode {
  type Seq[T] = IVector[T]

  final def emptySeq[T]: IVector[T] = ivectorEmpty[T]

  final def seq[T](es: T*): IVector[T] = ivector(es: _*)

  final def seq[T](es: Iterable[T]): Vector[T] = es.toVector
}

sealed trait QueryNode extends Product

final case class Model(queryStmt: QueryNode.Seq[QueryStmt])
  extends QueryNode

final case class QueryStmt(qName: Id,
                           qExpr: QueryExpr) extends QueryNode

sealed trait QueryExpr extends QueryNode

final case class BinaryExpr(lhs: QueryExpr,
                            op: String,
                            rhs: QueryExpr) extends QueryExpr

sealed trait ReachExpr extends QueryExpr

final case class ForwardExpr(expr : QueryExpr) extends ReachExpr

final case class BackwardExpr(expr : QueryExpr) extends ReachExpr

final case class ChopExpr(source : QueryExpr, target: QueryExpr)
  extends ReachExpr

final case class PathExpr(source : QueryExpr,
                          target:QueryExpr,
                          withExpr : Option[WithExpr]) extends ReachExpr

sealed trait WithExpr extends QueryNode

final case class SimpleWith(op: String, expr: QueryExpr) extends WithExpr

//final case class RegExWith()
//--------------- regex---------------------
sealed trait RegExExpr extends WithExpr

final case class UnaryRegEx(op: String, expr : RegExExpr) extends RegExExpr

final case class BinaryRegEx(lhs : RegExExpr,
                             op : String,
                             rhs : RegExExpr) extends RegExExpr

trait PrimaryRegEx extends RegExExpr

final case class ParenRegEx(regExExpr: RegExExpr) extends PrimaryRegEx

final case class Anything() extends PrimaryRegEx

final case class IdRegEx(rId : NodeNameError) extends PrimaryRegEx
//--------------- regex---------------------

sealed trait PrimaryExpr extends QueryExpr

final case class FilterExpr(lhs: QueryExpr,
                            op: FilterID) extends QueryExpr

final case class NodeNameError(nodeName: NodeName,
                               errorSet: QueryNode.Seq[QueryNode.Seq[Id]]) extends PrimaryExpr

final case class Paren(expr: QueryExpr) extends PrimaryExpr

final case class NodeSet(sets: QueryNode.Seq[NodeNameError]) extends PrimaryExpr

final case class NodeEmpty() extends PrimaryExpr

final case class QueryName(id: Id) extends PrimaryExpr

final case class NodeName(ids: QueryNode.Seq[Id])

object FilterID extends Enumeration {
  type FilterID = Value
  val IN = Value("in-port")
  val OUT = Value("out-port")
  val SOURCE = Value("source")
  val SINK = Value("sink")
  val NODE = Value("node")
  val PORT = Value("port")
  val PORTERROR = Value("porterror")
  val ERROR = Value("error")
}

object Id {
  def apply(value: String): Id = {
    _Id(value.intern())
  }

  def unapply(id: Id): Option[String] = Some(id.value)
}

sealed trait Id extends QueryNode {
  def value: String
}

private final case class
_Id(value: String) extends Id {
  override def toString =
    s"Id($value)"
}