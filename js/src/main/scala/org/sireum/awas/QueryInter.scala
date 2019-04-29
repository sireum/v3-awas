/*
 *
 *  Copyright (c) 2017, Hariharan Thiagarajan, Kansas State University
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package org.sireum.awas

import org.sireum.awas.collector.Collector
import org.sireum.awas.query.{QueryEval, QueryPPrinter, QueryParser}
import org.sireum.awas.symbol.SymbolTable
import org.sireum.message.{Position, Reporter}
import org.sireum.util._

class QueryInter(st: SymbolTable) {
  var result = ilinkedMapEmpty[String, Collector]
  var queries = ilinkedMapEmpty[String, String]
  private var reporter: Reporter = new Reporter(org.sireum.ISZ())
  val qe = new QueryEval(st)

  def evalCmd(cmd: String): (QueryEval.Result, Reporter) = {
    println(cmd)
    reporter = new Reporter(org.sireum.ISZ())
    try {
      QueryParser(cmd, reporter) match {
        case Some(m) => {
//          result.foreach(it => println(it._1))
          if(m.queryStmt.forall(qs => qs.qName.value == st.systemDecl.compName.value)) {
            reporter.error(org.sireum.None[Position],
              org.sireum.String("Parse Error"),
              org.sireum.String("Query name "+
                m.queryStmt.find(qs => qs.qName.value == st.systemDecl.compName.value).get.qName.value
                +" cannot be equal to system name"))
          } else {
            result = result ++ qe.eval(m, result)
            m.queryStmt.foreach(it => queries = queries + (QueryPPrinter(it.qName) -> QueryPPrinter(it.qExpr)))
          }
          (result, reporter)
        }
        case None => (result, reporter)
      }
    } catch {
      case e : Throwable => {
        reporter.error(org.sireum.None[Position], "Exception", e.getMessage)
        (result, reporter)
      }
    }
  }

  def evalQueryFile(queryIns: String): (QueryEval.Result, Reporter) = {
    reporter = new Reporter(org.sireum.ISZ())
    QueryParser(queryIns, reporter) match {
      case Some(m) => {
        result = result ++ qe.eval(m)
        m.queryStmt.foreach(it => queries = queries + (QueryPPrinter(it.qName) -> QueryPPrinter(it.qExpr)))
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

  def removeQueries(qName: String): Unit = {
    if (queries.contains(qName) && result.contains(qName)) {
      queries = queries - qName
      result = result - qName
    }
  }

  def getReporter: Reporter = { reporter }

}
