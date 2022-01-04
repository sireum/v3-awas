package org.sireum.awas

import org.sireum.awas.benchmark.Timer
import org.sireum.awas.symbol.SymbolTable

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, CanAwait, ExecutionContext, Future, Promise, TimeoutException}
import scala.concurrent.duration._
import scala.concurrent.CanAwait
import scala.scalajs.js
import scala.scalajs.js.timers._
import scala.util.{Failure, Success}
import scala.async.Async.{async, await}

class JSTimerImpl extends Timer {
  var lastTime: Long = 0

  override def startTimer(): Unit = {
    lastTime = org.scalajs.dom.window.performance.now().toLong
  }

  override def endGetTime(): Long = {
    val res = org.scalajs.dom.window.performance.now().toLong - lastTime
    lastTime = 0
    res
  }

  override def createTimer(): Timer = new JSTimerImpl()

  override def runWithTimeout[T](timeoutMs: Long, st: SymbolTable, query: String)
                                (f: (SymbolTable, String, Timer) => T)
                                (implicit executor: ExecutionContext): Option[T] = {
    var res: Option[T] = Some(f(st, query, createTimer()))
    //    var foo = Future[T](f(st, query, createTimer()))
    //    foo.onComplete {
    //      case Success(s) => {
    //        res = Some(s)
    //        foo = Future.successful(s)
    //      }
    //      case Failure(f) => println(f.getMessage)
    //    }
    //    while(foo.isCompleted) {
    //      scalajs.js.timers.setTimeout(1000){
    //        res = foo.value.flatMap(_.toOption)
    //        println(res)
    //      }
    //    }
    //    res = foo.value.flatMap(_.toOption)
    //    val foo = timeoutFuture(Future(f(st, query, createTimer())), timeoutMs)(executor)
    //    foo.onComplete(it => return it.toOption)

    //    org.scalajs.dom.window.setTimeout({ () => {
    //      res = Some(f(st, query, createTimer()))
    //      println(res)
    //    }
    //    }: js.Function0[Any], 10000)
    //    scalajs.js.timers.setTimeout(0){
    //
    //      res = foo.value.map(_.get)
    //      println(res)
    //    }


    res
  }

  def timeoutFuture[T](f: Future[T], timeout: Long)(
    implicit ec: ExecutionContext): Future[T] = {
    val p = Promise[T]()
    val timeoutHandle = scalajs.js.timers.setTimeout(timeout.toDouble) {
      p.tryFailure(new TimeoutException)
    }
    f.onComplete { result =>
      p.tryComplete(result)
      clearTimeout(timeoutHandle)
    }
    p.future
  }


  //  private def timeoutFuture[T](f: Future[T], timeout: Long)(
  //    implicit ec: ExecutionContext): Future[T] = {
  //    val promise = Promise[T]()
  //
  //    f.onComplete {
  //      case Success(s) => promise.success(s)
  //      case Failure(f) => promise.failure(f)
  //    }
  //
  //    scalajs.js.timers.setTimeout(timeout.toDouble) {
  //      if (!f.isCompleted) {
  //        promise.failure(new TimeoutException("timeout"))
  //      }
  //    }
  //    promise.future
  //  }
}
