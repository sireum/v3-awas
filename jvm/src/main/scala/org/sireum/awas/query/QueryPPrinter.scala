package org.sireum.awas.query


import org.sireum.util._
import org.stringtemplate.v4.{ST, STGroup, STGroupFile}

import scala.collection.JavaConverters._

object QueryPPrinter {
  private val stg: STGroup = new STGroupFile(getClass.getResource("../../aq/parser/QueryPrettyPrinter.stg"), "UTF-8", '$', '$')

  def apply(n: QueryNode): String = {
    var result = ""
    Visitor.build({
      case id: Id =>
        result = new QueryPPrinter(stg).print(id).render()
        false
      case qe: QueryExpr =>
        result = new QueryPPrinter(stg).print(qe).render()
        false
    })(n)
    result
  }
}

final class QueryPPrinter(stg: STGroup) {
  def print(id: Id): ST = {
    stg.getInstanceOf("qName").add("id", id.value)
  }

  def print(qe: QueryExpr): ST = {
    qe match {
      case be: BinaryExpr => print(be)
      case pe: PrimaryExpr => print(pe)
    }
  }

  def print(be: BinaryExpr): ST = {
    val temp = stg.getInstanceOf("binaryExpr")
    temp.add("lhs", print(be.lhs))
    temp.add("op", be.op)
    temp.add("rhs", print(be.rhs))
    temp
  }

  def print(pe: PrimaryExpr): ST = {
    pe match {
      case p: Paren => print(p)
      case nne: NodeNameError => print(nne)
      case ns: NodeSet => print(ns)
      case ne: NodeEmpty => print(ne)
      case qres: QueryName => print(qres)
    }
  }

  def print(p: Paren): ST = {
    stg.getInstanceOf("parenExpr").add("expr", print(p.expr))
  }

  def print(nne: NodeNameError): ST = {
    val temp = stg.getInstanceOf("nnError")
    temp.add("nn", print(nne.nodeName))
    if (nne.errorSet.nonEmpty)
      temp.add("errorSet", nne.errorSet.map(print).asJava)
    temp
  }

  def print(ns: NodeSet): ST = {
    stg.getInstanceOf("nSet").add("ns", ns.sets.map(print).asJava)
  }

  def print(ne: NodeEmpty): ST = {
    stg.getInstanceOf("empty")
  }

  def print(qn: QueryName): ST = {
    stg.getInstanceOf("qRes").add("id", print(qn.id))
  }

  def print(errorId: QueryNode.Seq[Id]): ST = {
    stg.getInstanceOf("errorId").add("ids", errorId.map(print).asJava)
  }

  def print(nn: NodeName): ST = {
    val temp = stg.getInstanceOf("nodeName")
    temp.add("id", nn.ids.map(print).asJava)
    if (nn.filter.isDefined)
      temp.add("filter", nn.filter.get.toString)
    temp
  }

}