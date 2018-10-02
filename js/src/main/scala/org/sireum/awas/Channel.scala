package org.sireum.awas

import scala.concurrent.{Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global

class Channel[T](init: (T => Unit) => Unit) {
  init(update)
  private[this] var value: Promise[T] = null

  def apply(): Future[T] = {
    value = Promise[T]()
    value.future
  }

  def update(t: T): Unit = {
    if (value != null && !value.isCompleted) value.success(t)
  }

  def |(other: Channel[T]): Future[T] = {
    val p = Promise[T]()
    for {
      f <- Seq(other(), this())
      t <- f
    } p.trySuccess(t)
    p.future
  }
}
