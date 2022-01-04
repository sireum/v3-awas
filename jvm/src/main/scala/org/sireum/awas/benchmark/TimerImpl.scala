package org.sireum.awas.benchmark

import java.util.Calendar

import org.sireum.awas.symbol.SymbolTable

import scala.concurrent._
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._

class TimerImpl extends Timer {
  //implicit value scala.language.postfixOps
  var lastTime: Long = 0

  override def startTimer(): Unit = {
    lastTime = System.nanoTime() //Calendar.getInstance().getTimeInMillis
  }

  override def endGetTime(): Long = {
    // val res = Calendar.getInstance().getTimeInMillis - lastTime
    val res = System.nanoTime() - lastTime

    lastTime = 0
    (res / 1000).toLong
  }

  override def createTimer(): Timer = new TimerImpl()

  override def runWithTimeout[T](timeoutMs: Long, st: SymbolTable, query: String)
                                (f: (SymbolTable, String, Timer) => T)
                                (implicit executor: ExecutionContext): Option[T] = {
    try {
      Some(Await.result(Future(f(st, query, this))(executor), Duration(timeoutMs, "millisecond")))
    } catch {
      case e: Exception => None
    }
  }
}

