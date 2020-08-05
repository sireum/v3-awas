package org.sireum.awas.benchmark

import java.util.Calendar

import scala.concurrent._
import scala.concurrent.duration.FiniteDuration

class TimerImpl extends Timer {

  var lastTime : Long = 0

  override def startTimer(): Unit = {
    lastTime = Calendar.getInstance().getTimeInMillis
  }

  override def endGetTime(): Long = {
    val res = Calendar.getInstance().getTimeInMillis - lastTime
    lastTime = 0
    res
  }

  override def createTimer(): Timer = new TimerImpl()
}

