package org.sireum.awas

import org.scalajs.dom
import org.scalajs.dom.html.{Button, Div}
import org.scalajs.dom.{Element, Event, MouseEvent}
import org.scalajs.dom.raw.{MessageEvent, WebSocket}
import org.sireum.awas.Main.st
import org.sireum.awas.ast.{AwasSerializer, Model}
import org.sireum.awas.flow.FlowGraph
import org.sireum.awas.peti._
import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.common.JSutil._
import org.sireum.crypto.SHA3
import org.sireum.util.{ConsoleTagReporter, IMap, ISet, isetEmpty}

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scalatags.Text.all.s

import scala.concurrent.{Await, Future, Promise}
import scala.scalajs.js
import scala.scalajs.js.typedarray.{Int32Array, Int8Array, Uint16Array, Uint8Array}
import scala.util.{Failure, Success}

object PetiConnHandler {
  private var serverConn = Promise[PetiConnHandler]()
  //  var model : Option[Model] = None
  private var connected = false
  private val H = SymbolTableHelper

  def connect(): Unit = {
    val server = new WebSocket(s"ws://localhost:8080/connect")
    server.onopen = { (event: Event) =>
      println("connection open")
      connected = true
      serverConn.success(new PetiConnHandler(server))
    }

    server.onerror = { (event: Event) =>
      println("Connection error")
    }

    server.onmessage = { (event: MessageEvent) =>
      serverConn.future.value match {
        case Some(Success(v)) => v.handler(event.data.toString)
        case Some(Failure(e)) => println("connection handler failed")
        case None => println("connection not complete")
      }
      // serverConn.future.onComplete(it => it.get.handler(event.data.toString))

    }

    server.onclose = { (event: Event) =>
      connected = false
      serverConn = Promise[PetiConnHandler]()
      println("connection closed")
    }
  }

  def getModel: Future[Option[Model]] = {
    serverConn.future.flatMap(it => it.getModel().map(it => Some(it)))
  }

  def reconnect(): Unit = {
    connect()
    getModel.map { it =>
      val mainBox: Element = $[Element]("#main-container")
      mainBox.removeChild($[Div](".lm_goldenlayout"))
      Main.loadGL(it)
    }
  }

  def apply(): Future[Option[Model]] = {
    connect()
    getModel
  }

  def isConnected: Boolean = {
    connected
  }

  def disconnect(): Unit = {
    connected = false

    serverConn.future.value match {
      case Some(Success(v)) => {
        v.disconnect()
        serverConn = Promise[PetiConnHandler]()
      }
      case Some(Failure(e)) => println("connection handler failed")
      case None => println("connection not complete")
    }
  }

  def isConnectionAlive: Boolean = {
    val handler = serverConn.future.value match {
      case Some(Success(v)) => v.isConnectionAlive()
      case Some(Failure(e)) => false
      case None => false
    }
    handler && connected
  }

  def highlight(uris: IMap[ResourceUri, Boolean], color: String): Unit = {
    val res = uris.map { uri =>
      if (H.uri2TypeString(uri._1) == H.ERROR_TYPE ||
        H.uri2TypeString(uri._1) == H.CONNECTION_TYPE) {
        (uri._1, "#ff0000")
      } else if (uri._2) {
        (uri._1, "#1878c0")
      } else {
        (uri._1, color)
      }
    }
    serverConn.future.value match {
      case Some(Success(v)) => v.highlight(res)
      case Some(Failure(e)) =>
      case None =>
    }
  }

  def clear(uris: ISet[ResourceUri]): Unit = {
    serverConn.future.value match {
      case Some(Success(v)) => v.clear(uris)
      case Some(Failure(e)) =>
      case None =>
    }
  }

  def findSource(uri: ResourceUri): Unit = {
    serverConn.future.value match {
      case Some(Success(v)) => v.findSource(uri)
      case Some(Failure(e)) =>
      case None =>
    }
  }
}

class PetiConnHandler(server : WebSocket) {

  //  dom.window.setInterval({() => {
  //    if(server.readyState ==1) {
  //      server.send(ProtocolSerializer(Ping()))
  //    }
  //  }}: js.Function0[Any], 600000)

  private var model = Promise[Model]()
  private var modelHash: Option[String] = None
  private var modelWritten = false

  def disconnect() = {
    server.close(1000, "Closed by user")
  }

  def isConnectionAlive(): Boolean = {
    server.readyState == 1
  }

  def highlight(urisColor: IMap[String, String]) = {
    server.send(ProtocolSerializer(Highlight(urisColor)))
  }

  def clear(uris: ISet[String]): Unit = {
    server.send(ProtocolSerializer(Clear(uris)))
  }

  def findSource(uri: String): Unit = {
    server.send(ProtocolSerializer(FindDef("", isetEmpty + uri)))
  }


  def handler(msg: String) = {
    val optProto = ProtocolSerializer.unapply(msg)
    if (optProto.isDefined) {
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
        case AwasHash(hash) => {

          model.future.value match {
            case Some(Success(m)) => {
              checkIfChanged(hash, m) onComplete {
                case Success(b) => {
                  if (b) {
                    val mainBox: Element = $[Element]("#main-container")
                    var dlg: Element = $[Element]("#updateMdlDlg")
                    if (js.typeOf(dlg) == "undefined") {
                      val msg = "Underlying model changed, would you like to update the graphs? <br> Clicking cancel will disconnect you from the server."
                      mainBox.appendChild(render(
                        Views.popUp("Update graphs",
                          msg,
                          "Update Graphs",
                          "Cancel")))
                      dlg = $[Div](mainBox, "#updateMdlDlg")
                    }
                    dlg.classList.add("is-active")
                    dlg.setAttribute("style", "display: block;")
                    $[Button](dlg, "#popUpOk").onclick = (_: MouseEvent) => {
                      $[Div](mainBox, "#loading").classList.add("is-active")
                      val newMdl: Future[Model] = getModel()
                      newMdl onComplete {
                        case Success(nMdl) => {
                          //                            val reporter = new ConsoleTagReporter()
                          //                            Main.st = Some(SymbolTable(nMdl)(reporter))
                          //                            FlowGraph(nMdl, st.get, includeBindingEdges = SettingsView.currentConfig.bindings)
                          val tt = $[Div](".lm_goldenlayout")
                          println(tt)
                          mainBox.removeChild(tt)
                          //                            Util.reDrawGraphs(SettingsView.currentConfig)
                          //                            $[Div](mainBox, "#loading").classList.remove("is-active")
                          Main.loadGL(Some(nMdl))
                          dlg.classList.remove("is-active")
                          dlg.setAttribute("style", "display: none;")
                        }
                        case Failure(mm) => println("failed to update model")
                      }

                    }
                    $[Button](dlg, "#popUpCancel").onclick = (_: MouseEvent) => {
                      dlg.classList.remove("is-active")
                      dlg.setAttribute("style", "display: none;")
                    }

                  }
                }
                case Failure(_) =>
              }
            }
            case Some(Failure(t)) => {
              println("model empty")
            }
            case None => {
              println("model empty")
            }
          }

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
    model = Promise[Model]()
    modelWritten = false
  }

  def isModelWritten: Boolean = {
    modelWritten
  }

  def checkIfChanged(newHash: String, oldMdl: Model): Future[Boolean] = {
    //val sha3 = SHA3.init512()
    //sha3.update(org.sireum.conversions.String.toU8is(AwasSerializer(oldMdl)))

    import scala.scalajs.js.JSConverters._
    import scala.scalajs.js.typedarray.{Int8Array, ArrayBuffer, TypedArray, int8Array2ByteArray}

    val x = org.scalajs.dom.crypto.crypto.subtle.digest(org.scalajs.dom.crypto.HashAlgorithm.`SHA-256`,
      Int8Array.from(AwasSerializer(oldMdl).getBytes().toJSArray))

    x.toFuture.map { it =>
      val oldHash = int8Array2ByteArray(it.asInstanceOf[Int8Array]).map(it => String.format("%02x", Byte.box(it) & 0xff)).mkString
      println(oldHash)
      oldHash != newHash
    }
    //    x.toFuture.onComplete {
    //      case Success(value) => {
    //        println("old hash: "+value.asInstanceOf[scala.scalajs.js.typedarray.ArrayBuffer].toLocaleString())
    //        oldHash = value.toString
    //      }
    //      case Failure(exception) =>
    //    }
    //    val oldhu8 = sha3.finalise()
    //    oldhash = oldhu8.elements.foldLeft("")((a, c) => a.appendedAll(c.string.value))
    //    oldHash != newHash
  }


}
