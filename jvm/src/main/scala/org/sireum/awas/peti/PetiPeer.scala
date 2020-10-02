package org.sireum.awas.peti

import akka.NotUsed
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.scaladsl.SourceQueueWithComplete
import akka.stream.scaladsl.MergeHub
import akka.stream.{Materializer, OverflowStrategy}
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}

import scala.concurrent.ExecutionContext

trait AbstractPeer {
  implicit val executionContext: ExecutionContext
  // implicit val materializer: Materializer

  val handlerFlow: Flow[Protocol, Protocol, NotUsed]
}

trait IndependentPeer extends AbstractPeer {
  val messageSource: Source[Protocol, _]
  val messageSink: Sink[Protocol, _]

  override lazy val handlerFlow = Flow.fromSinkAndSource(messageSink, messageSource)
}

trait PetiPeer extends IndependentPeer {
  override val messageSource: Source[Protocol, SourceQueueWithComplete[Protocol]] = {
    var queue = Source.queue[Protocol](100, OverflowStrategy.dropNew).prepend(Source.single(Ping()))
//    SourceQueueWithComplete.QueueOps(queue.)
    //Source.single(TextMessage("sent from server"))
    //queue = queue.prepend(Source.single(TextMessage("tail")))
    queue
  }

  override val messageSink: Sink[Protocol, NotUsed] = {
    Flow[Protocol].map {
      //case t: TextMessage => println("temp " + t.getStrictText)
      case _ => println("getting nonsense")
    }.to(Sink.ignore)
  }
}
