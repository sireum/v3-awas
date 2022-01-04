package org.sireum.awas.benchmark

import org.sireum.awas.symbol.SymbolTable

import scala.concurrent.{Await, ExecutionContext, Future, TimeoutException}
import scala.concurrent.duration.FiniteDuration

trait Timer {

  def startTimer(): Unit

  def endGetTime(): Long

  def createTimer(): Timer

  def runWithTimeout[T](timeoutMs: Long, st: SymbolTable, query: String)(
    f: (SymbolTable, String, Timer) => T
  )(implicit executor: ExecutionContext): Option[T]
}

//object TimeoutFuture {
//  def apply[A](timeout: FiniteDuration, st : SymbolTable, query : String, timer: Timer)(block: (SymbolTable, String, Timer)=> A)(implicit executor: ExecutionContext): Future[A] =
//    try {
//      Future { Await.result(Future { block(st, query, timer) }, timeout) }
//    } catch {
//      case _: TimeoutException => Future.failed(new TimeoutException(s"Timed out after ${timeout.toString}"))
//    }
//}