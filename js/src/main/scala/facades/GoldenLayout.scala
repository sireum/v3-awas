package facades

import org.scalajs.jquery.JQuery

import scala.scalajs.js
import scala.scalajs.js.UndefOr
import scala.scalajs.js.annotation.{JSGlobal, JSGlobalScope}


@js.native
@JSGlobal
class GoldenLayout(configuration: js.Dictionary[js.Any],
                   container: JQuery) extends js.Object {
  var isInitialised : Boolean = js.native
  var root : ContentItem = js.native
  def registerComponent(name : String, component: js.Function): Nothing = js.native
  def init(): Nothing = js.native
  def updateSize(width: UndefOr[Int], height: UndefOr[Int]) : Nothing = js.native
  def on(event : String, callBack: js.Function): Nothing = js.native
}

@js.native
trait ContentItem extends js.Object {
  def setSize(width : Int, height: Int) : Nothing = js.native
}

@js.native
trait Container extends js.Object {
  def getElement(): JQuery= js.native
}

