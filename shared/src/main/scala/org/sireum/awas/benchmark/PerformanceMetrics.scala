package org.sireum.awas.benchmark

import org.sireum.awas.query.{QueryEval, QueryParser}
import org.sireum.awas.symbol.SymbolTable
import org.sireum.message.{Reporter, ReporterImpl}

import scala.collection.parallel.immutable.ParVector
import scala.concurrent.{Await, ExecutionContext, Future, duration}
import scala.concurrent.duration._
import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.util.{Failure, Success}
import scala.language.postfixOps

object PerformanceMetrics {

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  def apply(st: SymbolTable, count: Int, timer: Timer): String = {

    var res = "#graphs, #height, #components, #connections, #ports, #nodes, #edges, #flows" + "\n"

    res = res + ModelStatInfo(st).graphs + ", "
    res = res + ModelStatInfo(st).height + ", "
    res = res + ModelStatInfo(st).components + ", "
    res = res + ModelStatInfo(st).connections + ", "
    res = res + ModelStatInfo(st).ports + ", "
    res = res + ModelStatInfo(st).nodes + ", "
    res = res + ModelStatInfo(st).edges + ", "
    res = res + ModelStatInfo(st).flows + "\n"

    val queries = GenQueries(st, count)

    res = res + queries.mkString("\n")

    val x = queries.map { q =>
      var exes = ""
      val v = Vector.range(0, 10)
      val t = v.flatMap { k =>
        // val m = TimeoutFuture[String](FiniteDuration(60, duration.MILLISECONDS), st, q, timer) (queryEval)
        val r = runWithTimeout(100, st, q, timer.createTimer())(queryEval)

        //        var r = ""
        //        m.onComplete {
        //          case Success(value) => r = r + value
        //          case Failure(e) => r = r + "timeout"
        //        }
        //        println(r)
        r
      }.seq.mkString(", ")

      q.split("=").head + " = " + t
    }
    res = res + x.mkString("\n")
    res
  }

  def queryEval(st: SymbolTable, query: String, timer: Timer): String = {
    implicit val reporter: Reporter = new ReporterImpl(org.sireum.ISZ())
    QueryParser(query, reporter) match {
      case None => {
        reporter.printMessages()
        "parse Error"
      }
      case Some(q) =>
        timer.startTimer()
        val res = QueryEval(q, st)
        var result = timer.endGetTime()
        if (res.nonEmpty) result.toString else "Exe Error"
    }
  }

  def runWithTimeout[T](timeoutMs: Long, st: SymbolTable, query: String, timer: Timer)(
    f: (SymbolTable, String, Timer) => T
  ): Option[T] = {
    try {
      Some(Await.result(Future(f(st, query, timer)), timeoutMs milliseconds))
    } catch {
      case e: Exception => None
    }
  }
}
