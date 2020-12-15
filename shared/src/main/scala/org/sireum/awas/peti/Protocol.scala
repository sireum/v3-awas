package org.sireum.awas.peti

import org.sireum.awas.ast
import org.sireum.awas.collector.Collector
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.{IMap, ISet}
import upickle.default.{macroRW, ReadWriter => RW}

sealed trait Protocol

object Protocol {
  implicit def rw: RW[Protocol] = RW.merge(Ping.rw,
    Pong.rw, ReqModel.rw, AwasModel.rw, RequestHash.rw,
    AwasHash.rw, Query.rw, QueryResults.rw, QueryRow.rw,
    Msg.rw, Error.rw, FindDef.rw, FindDia.rw, Highlight.rw, Clear.rw)
}

case class Ping() extends Protocol

object Ping {
  implicit def rw: RW[Ping] = macroRW
}

case class Pong() extends Protocol

object Pong {
  implicit def rw: RW[Pong] = macroRW
}

case class ReqModel() extends Protocol

object ReqModel {
  implicit def rw: RW[ReqModel] = macroRW
}

case class AwasModel(model: ast.Model) extends Protocol

object AwasModel {
  implicit def rw: RW[AwasModel] = macroRW
}

case class RequestHash() extends Protocol

object RequestHash {
  implicit def rw: RW[RequestHash] = macroRW
}

case class AwasHash(hash: String) extends Protocol

object AwasHash {
  implicit def rw: RW[AwasHash] = macroRW
}

case class Query(hash : String, query: String) extends Protocol

object Query {
  implicit def rw: RW[Query] = macroRW
}

case class QueryResults(queryName : String, result : Collector) extends Protocol

object QueryResults {
  implicit def rw: RW[QueryResults] = macroRW
}

case class QueryRow(queryName: String, queryDesc: String, result: Collector) extends Protocol

object QueryRow {
  implicit def rw: RW[QueryRow] = macroRW
}

case class FindDef(hash: String, uris: Set[ResourceUri]) extends Protocol

object FindDef {
  implicit def rw: RW[FindDef] = macroRW
}

case class FindDia(hash: String, collector: Collector) extends Protocol

object FindDia {
  implicit def rw: RW[FindDia] = macroRW
}

case class Highlight(urisColor: IMap[String, String]) extends Protocol

object Highlight {
  implicit def rw: RW[Highlight] = macroRW
}

case class Clear(uris: ISet[String]) extends Protocol

object Clear {
  implicit def rw: RW[Clear] = macroRW
}

case class Msg(msg: String) extends Protocol

object Msg {
  implicit def rw: RW[Msg] = macroRW
}

case class Error(msg: String) extends Protocol

object Error {
  implicit def rw: RW[Error] = macroRW
}