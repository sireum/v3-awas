package org.sireum.awas

import org.scalajs.dom
import org.scalajs.dom.Event
import org.scalajs.dom.raw.{MessageEvent, WebSocket}
import org.sireum.awas.ast.{AwasSerializer, Model}
import org.sireum.awas.peti._

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scalatags.Text.all.s

import scala.concurrent.{Future, Promise}
import scala.scalajs.js
import scala.util.{Failure, Success}

object PetiConnHandler {
  val serverConn  = Promise[PetiConnHandler]()
//  var model : Option[Model] = None
  def connect = {
    val server = new WebSocket(s"ws://localhost:8080/connect")
    server.onopen = { (event: Event) =>
      println("connection open")
      serverConn.success(new PetiConnHandler(server))
    }

    server.onerror = { (event: Event) =>
      println("Connection error")
    }

    server.onmessage = { (event: MessageEvent) =>
      if(serverConn.isCompleted) {
        serverConn.future.onComplete(it => it.get.handler(event.data.toString))
      }
    }

    server.onclose = { (event: Event) =>
     // serverConn = Future.never
      println("connection closed")
    }
  }

  def apply() : Future[Option[Model]] = {
    connect
    val p = Promise[Option[Model]]()
     serverConn.future.foreach(it => {
       val temp = it.getModel()
       temp.onComplete{
        case Success(mdl) => {
          println("model success")
          p.success(Some(mdl))
        }
        case Failure(t) => {
          println("model failure")
          p.failure(t)
        }
      }
    })
    p.future
  }

}

class PetiConnHandler(server : WebSocket) {

  dom.window.setInterval({() => {
    if(server.readyState ==1) {
      server.send(ProtocolSerializer(Ping()))
    }
  }}: js.Function0[Any], 60000)

  private var model  = Promise[Model]()
  private var modelHash : Option[String] = None
  private var modelWritten = false


  def handler(msg: String) = {
    val optProto = ProtocolSerializer.unapply(msg)
    if(optProto.isDefined) {
      optProto.get match {
        case Ping() => server.send(ProtocolSerializer(Pong()))
        case Pong() => println("received a pong")
        //case RequestHash() => AwasHash("nothing for now")
        case ReqModel() => println("received a reqmodel")
        case AwasModel(mdl) => {
          println("receiving mdl")
          model.success(mdl)
          modelWritten = true
        }
        case _ => println("something else")
      }
    } else {
      server.send(ProtocolSerializer(Error("Unknown command")))
    }

  }

  def getModel(): Future[Model] = {
    unsetModel

    server.send(ProtocolSerializer(ReqModel()))
    println("requested model")
    model.future
  }

  def unsetModel = {
    modelWritten = false
  }

  def isModelWritten : Boolean = {
    modelWritten
  }
  


}
