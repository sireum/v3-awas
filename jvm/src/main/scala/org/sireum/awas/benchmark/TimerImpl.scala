package org.sireum.awas.benchmark

import java.util.Calendar

import scala.concurrent._
import scala.concurrent.duration.FiniteDuration

class TimerImpl extends Timer {

  var lastTime : Long = 0

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
}

