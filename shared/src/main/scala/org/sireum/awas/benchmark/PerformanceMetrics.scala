package org.sireum.awas.benchmark

import org.sireum.awas.query.{QueryEval, QueryParser}
import org.sireum.awas.symbol.SymbolTable
import org.sireum.message.{Reporter, ReporterImpl}
import org.sireum.util._

import scala.collection.immutable.ListMap
import scala.concurrent.{Await, ExecutionContext, Future, duration}
import scala.concurrent.duration._
import scala.util.{Failure, Random, Success}
import scala.language.postfixOps

object PerformanceMetrics {

  //scalajs.js//JSExecutionContext.Implicits.runNow//scala.concurrent.ExecutionContext.Implicits.global

  def apply(st: SymbolTable, count: Int, times: Int, timer: Timer, executionContext: ExecutionContext): String = {
    implicit var ec: ExecutionContext = executionContext

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

    res = res + queries.map(m => m.mkString("\n")).mkString("\n")

    val queRes = queries.map { seg =>
      var segRes = seg.map(q => (q.split("=").head, ilistEmpty[String])).to(ListMap)
      val v = Vector.range(0, times)
      val t = v.foreach { k =>
        QueryEval.setup(st)
        Random.shuffle(seg).foreach { q =>
          val qname = q.split("=").head
          val r = if (segRes(qname).isEmpty || segRes(qname).last != "timeout") {
            timer.createTimer().runWithTimeout(10000, st, q)(queryEval)
          } else {
            None
          }
          segRes = segRes + (qname -> segRes(qname).:+(r.getOrElse("timeout")))
        }
        QueryEval.clear()
      }
      segRes.foreach { sr =>
        if (sr._2.contains("timeout")) {
          segRes = segRes + (sr._1 -> sr._2.:+("timeout"))
        } else {
          val avg = (sr._2.map(it => it.toInt).sum) / sr._2.length
          segRes = segRes + (sr._1 -> sr._2.:+(avg.toString))
        }
      }
      var tsum = 0
      var tsize = 0
      segRes.foreach { it =>
        if (!it._2.contains("timeout")) {
          tsum = tsum + it._2.last.toInt
          tsize = tsize + 1
        }
      }
      val tavg = if (tsize > 0) tsum / tsize else 0
      segRes = segRes + ("total = " -> ilistEmpty[String].:+(tavg.toString))
      segRes
    }

    res + "\n" + printer(queRes)

    //    val x = queries.map { q =>
    //      var exes = ""
    //      val v = Vector.range(0, 10)
    //      val t = v.flatMap { k =>
    //        // val m = TimeoutFuture[String](FiniteDuration(60, duration.MILLISECONDS), st, q, timer) (queryEval)
    //        val r = runWithTimeout(100, st, q, timer.createTimer())(queryEval)
    //
    //        //        var r = ""
    //        //        m.onComplete {
    //        //          case Success(value) => r = r + value
    //        //          case Failure(e) => r = r + "timeout"
    //        //        }
    //        //        println(r)
    //        r
    //      }.mkString(", ")
    //
    //      //q.split("=").head +
    //        " = " + t
    //    }
    //    res = res + x.mkString("\n")
    //    res
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

  //  def runWithTimeout[T](timeoutMs: Long, st: SymbolTable, query: String, timer: Timer, executionContext: ExecutionContext)(
  //    f: (SymbolTable, String, Timer) => T
  //  ): Option[T] = {
  //    try {
  //      Some(Await.result(Future(f(st, query, timer))(executionContext), timeoutMs milliseconds))
  //    } catch {
  //      case e: Exception => None
  //    }
  //  }

  def printer(qres: IList[ILinkedMap[String, IList[String]]]): String = {
    qres.map { qr => qr.map { q => q._1 + " = " + q._2.mkString(", ") }.mkString("\n") }.mkString("\n\n")
  }
}
