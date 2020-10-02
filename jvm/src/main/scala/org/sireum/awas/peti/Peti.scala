package org.sireum.awas.peti

import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

import akka.NotUsed
import akka.actor.{ActorSystem, Terminated}
import akka.http.javadsl.model.ws.WebSocket
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.stream.scaladsl._
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.settings.ServerSettings
import akka.stream.ActorMaterializer
import akka.util.ByteString
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.directives.WebSocketDirectives
import org.sireum.awas.ast.{AwasSerializer, Model}
import org.sireum.awas.collector.Collector
import org.sireum.awas.peti.PetiApp.getModel
import org.sireum.awas.util.AwasUtil.ResourceUri
import upickle.default._

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import scala.concurrent.duration.Duration
import scala.util._

object PetiApp extends App with PetiPeer with Peti {
  startServer()
  override def getModel(): Option[Model] = {
    val x =
      "{\"$type\":\"org.sireum.awas.ast.Model\",\"types\":[{\"$type\":\"org.sireum.awas.ast.EnumDecl\",\"name\":{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},\"superEnums\":[],\"elements\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ServiceError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ItemOmission\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ServiceOmission\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"SequenceOmission\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"TransientServiceOmission\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"LateServiceStart\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"EarlyServiceTermination\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"BoundedOmissionInterval\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ItemCommission\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ServiceCommission\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"SequenceCommission\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"EarlyServiceStart\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"LateServiceTermination\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ItemTimingError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"EarlyDelivery\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"LateDelivery\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"SequenceTimingError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"HighRate\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"LowRate\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"RateJitter\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ServiceTimingError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"DelayedService\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"EarlyService\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"TimingError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"RateError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"EarlyData\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"LateData\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ServiceTimeShift\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ItemValueError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"UndetectableValueError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"DetectableValueError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"OutOfRange\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"BelowRange\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"AboveRange\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"OutOfBounds\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"SequenceValueError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"BoundedValueChange\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"StuckValue\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"OutOfOrder\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ServiceValueError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"OutOfCalibration\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ValueError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"IncorrectValue\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ValueCorruption\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"BadValue\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"SequenceError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"SubtleValueError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"BenignValueError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"SubtleValueCorruption\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ReplicationError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"AsymmetricReplicatesError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"AsymmetricValue\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"AsymmetricApproximateValue\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"AsymmetricExactValue\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"AsymmetricTiming\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"AsymmetricOmission\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"AsymmetricItemOmission\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"AsymmetricServiceOmission\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"SymmetricReplicatesError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"SymmetricValue\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"SymmetricApproximateValue\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"SymmetricExactValue\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"SymmetricTiming\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"SymmetricOmission\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"SymmetricItemOmission\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"SymmetricServiceOmission\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"InconsistentValue\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"InconsistentTiming\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"InconsistentOmission\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"InconsistentItemOmission\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"InconsistentServiceOmission\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"AsymmetricTransmissive\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ConcurrencyError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"RaceCondition\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ReadWriteRace\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"WriteWriteRace\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"MutExError\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Deadlock\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Starvation\"}]}],\"stateMachines\":[{\"$type\":\"org.sireum.awas.ast.StateMachineDecl\",\"smName\":{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary.FailStop\"},\"states\":[{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"FailStop\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Operational\"}]},{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"FailStop\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"FailStop\"}]}],\"events\":[{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"FailStop\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Failure\"}]}]},{\"$type\":\"org.sireum.awas.ast.StateMachineDecl\",\"smName\":{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary.DegradedFailStop\"},\"states\":[{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"DegradedFailStop\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Operational\"}]},{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"DegradedFailStop\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Degraded\"}]},{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"DegradedFailStop\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"FailStop\"}]}],\"events\":[{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"DegradedFailStop\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Failure\"}]}]},{\"$type\":\"org.sireum.awas.ast.StateMachineDecl\",\"smName\":{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary.FailAndRecover\"},\"states\":[{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"FailAndRecover\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Operational\"}]},{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"FailAndRecover\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Failed\"}]}],\"events\":[{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"FailAndRecover\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Failure\"}]},{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"FailAndRecover\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Recovery\"}]}]},{\"$type\":\"org.sireum.awas.ast.StateMachineDecl\",\"smName\":{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary.DegradedRecovery\"},\"states\":[{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"DegradedRecovery\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Operational\"}]},{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"DegradedRecovery\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Degraded\"}]},{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"DegradedRecovery\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"FailStop\"}]}],\"events\":[{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"DegradedRecovery\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Failure\"}]},{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"DegradedRecovery\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Recovery\"}]}]},{\"$type\":\"org.sireum.awas.ast.StateMachineDecl\",\"smName\":{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary.PermanentTransientFailure\"},\"states\":[{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"PermanentTransientFailure\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Operational\"}]},{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"PermanentTransientFailure\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"FailedTransient\"}]},{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"PermanentTransientFailure\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"FailedPermanent\"}]}],\"events\":[{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"PermanentTransientFailure\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Failure\"}]},{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"PermanentTransientFailure\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Recovery\"}]}]},{\"$type\":\"org.sireum.awas.ast.StateMachineDecl\",\"smName\":{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary.FailRecoveryFailure\"},\"states\":[{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"FailRecoveryFailure\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Operational\"}]},{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"FailRecoveryFailure\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Failed\"}]},{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"FailRecoveryFailure\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"FailStop\"}]}],\"events\":[{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"FailRecoveryFailure\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Failure\"}]},{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"FailRecoveryFailure\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"Recovery\"}]}]}],\"constants\":[],\"system\":[{\"$type\":\"org.sireum.awas.ast.ComponentDecl\",\"compName\":{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"top_i_Instance\"},\"withSM\":[{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"}]}],\"ports\":[{\"$type\":\"org.sireum.awas.ast.Port\",\"isIn\":true,\"id\":{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"a\"},\"name\":[]},{\"$type\":\"org.sireum.awas.ast.Port\",\"isIn\":false,\"id\":{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"b\"},\"name\":[]}],\"propagations\":[{\"$type\":\"org.sireum.awas.ast.Propagation\",\"id\":{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"a\"},\"errorTypes\":[{\"$type\":\"org.sireum.awas.ast.Fault\",\"enum\":{\"$type\":\"org.sireum.awas.ast.Name\",\"value\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ErrorLibrary\"},{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"ServiceError\"}]}}]}],\"security\":[],\"flows\":[{\"$type\":\"org.sireum.awas.ast.Flow\",\"id\":{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"af\"},\"from\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"a\"}],\"fromE\":[],\"to\":[{\"$type\":\"org.sireum.awas.ast._Id\",\"value\":\"b\"}],\"toE\":[]}],\"declass\":[],\"transitions\":[],\"behaviour\":[],\"subComp\":[],\"connections\":[],\"deployment\":[],\"properties\":[]}]}"
    AwasSerializer.unapply(x)
  }

  override def handlePing(): Option[Protocol] = {
    Some(Pong())
  }

  override def handleModelReq(): Option[Protocol] = {
    getModel().map(it => AwasModel(it))
  }

}

class PetiImpl extends Peti {
  override def getModel(): Option[Model] = None
}

trait Peti extends PetiPeer with PetiLocalEndPoint with WebSocketDirectives {
  private implicit val system: ActorSystem = ActorSystem("websocket-server")
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  //implicit val materializer = ActorMaterializer()


  private val route = get {
    path("connect") {
      handleWebSocketMessages(petiFlow(handlerFlow))
    }
  }

  def startServer(): Future[Http.ServerBinding] = {
    val binding: Future[Http.ServerBinding] = Http().newServerAt(host, port)
      .bind(route).map(_.addToCoordinatedShutdown(Duration(10, TimeUnit.SECONDS)))
    binding.onComplete {
      case Success(binding) =>
        val localAddress = binding.localAddress
        println(s"Server is listening on ${localAddress.getHostName}:${localAddress.getPort}")
      case Failure(e) =>
        println(s"Binding failed with ${e.getMessage}")
        system.terminate()
    }
    binding
  }

  def shutdownServer(): Future[Terminated] = {
    system.terminate()
  }

  private def serializeMsg(msg : Option[Protocol]) : TextMessage = {
    TextMessage.Strict(ProtocolSerializer( msg match {
      case Some(x) => x
      case None => Error("Handler failed")
    }))
  }

  private def petiFlow(inFlow: Flow[Protocol, Protocol, NotUsed]): Flow[Message, Message, Any] = {
    Flow[Message].collect {
      case TextMessage.Strict(msg) => {
        read[Protocol](msg)
      }
      case _ => println("again nonsense")
    }.map {
      case Ping() => serializeMsg(handlePing())
      case ReqModel() => serializeMsg(handleModelReq())

      case _ => {
        println("not getting it")
        TextMessage.Strict("unknown")
      }
    }
  }





  def getModel() : Option[Model]



  def setPort(portVal : Int): Unit = {
    port = portVal
  }

//-- interface

  protected def handlePing() : Option[Protocol] = {
    None
  }

  protected def handlePong(): Option[Protocol]= {
    None
  }

  protected def handleModelReq(): Option[Protocol] = {
    getModel().map(it => AwasModel(it))
  }

  protected def handleModel(model : Model): Option[Protocol]  = {
    //client cannot send model, do not implement
    None
  }

  protected def handleHashReq(): Option[Protocol] = {
    None
  }

  protected def handleHash(hash: String): Option[Protocol] = {
    None
  }

  protected def handleQuery(hash : String, query : String): Option[Protocol] = {
    None
  }

  protected def handleQueryRes(queryName: String, resulr : Collector): Option[Protocol] = {
    //client cannot send res, do not implement
    None
  }

  protected def handleQueryRow(queryName: String,
                     queryDesc: String,
                     result: Collector): Option[Protocol] = {
    //client cannot send row, do not implement
    None
  }

  protected def handleError(msg : String): Option[Protocol] = {
    None
  }

  //eclipse specific
  protected def handleFindDef(hash : String, uris: Set[ResourceUri]): Option[Protocol] = {
    None
  }

  protected def handleFindDia(hash: String, collector: Collector): Option[Protocol] = {
    None
  }
}

