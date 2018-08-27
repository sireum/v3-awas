package org.sireum.awas

import org.sireum.awas.collector.Collector
import org.sireum.awas.query.{QueryEval, QueryPPrinter, QueryParser}
import org.sireum.awas.symbol.SymbolTable
import org.sireum.message.Reporter
import org.sireum.util._

class QueryInter(st: SymbolTable) {
  var result = ilinkedMapEmpty[String, Collector]
  var queries = ilinkedMapEmpty[String, String]
  private var reporter: Reporter = new Reporter(org.sireum.ISZ())
  val qe = new QueryEval(st)

  def evalCmd(cmd: String): (QueryEval.Result, Reporter) = {
    reporter = new Reporter(org.sireum.ISZ())
    QueryParser(cmd, reporter) match {
      case Some(m) => {
        result = qe.eval(m.queryStmt.head, result)
        m.queryStmt.foreach(it =>
          queries = queries + (QueryPPrinter(it.qName) -> QueryPPrinter(it.qExpr)))
        (result, reporter)
      }
      case None => (result, reporter)
    }
  }

  def evalQueryFile(queryIns: String): (QueryEval.Result, Reporter) = {
    reporter = new Reporter(org.sireum.ISZ())
    QueryParser(queryIns, reporter) match {
      case Some(m) => {
        result = result ++ qe.eval(m)
        m.queryStmt.foreach(it =>
          queries = queries + (QueryPPrinter(it.qName) -> QueryPPrinter(it.qExpr)))
        (result, reporter)
      }
      case None => (result, reporter)
    }
  }

  def getQueries: ILinkedMap[String, String] = {
    queries
  }

  def getResults: ILinkedMap[String, Collector] = {
    result
  }

  def getReporter: Reporter = { reporter }

}
