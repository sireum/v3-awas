package facades

import org.scalajs.dom.Element
import org.scalajs.jquery.JQuery

import scala.scalajs.js
import scala.scalajs.js.UndefOr
import scala.scalajs.js.annotation.JSGlobal


@js.native
@JSGlobal
class GoldenLayout(configuration: js.Dictionary[js.Any],
                   container: JQuery) extends js.Object {
  var isInitialised : Boolean = js.native
  var root : ContentItem = js.native

  def selectItem(contentItem: ContentItem): Nothing = js.native
  def registerComponent(name : String, component: js.Function): Nothing = js.native
  def init(): Nothing = js.native
  def updateSize(width: UndefOr[Int], height: UndefOr[Int]) : Nothing = js.native
  def on(event : String, callBack: js.Function): Nothing = js.native

  def toConfig(): scalajs.js.Dictionary[js.Any] = js.native
  def getComponent(name: String): js.Function2[Container, js.Dictionary[Object], Nothing] = js.native
}

@js.native
trait ContentItem extends js.Object {
  var id: String = js.native
  //  var id : js.Array[String] = js.native
  var element: Element = js.native
  var contentItems: js.Array[ContentItem] = js.native

  def select: Nothing = js.native

  def deselect: Nothing = js.native

  def getItemsByType(`type`: String): js.Array[ContentItem] = js.native

  def getItemsById(id: String): js.Array[ContentItem] = js.native

  def setActiveContentItem(contentItem: ContentItem): js.Dynamic = js.native

  def setSize(width : Int, height: Int) : Nothing = js.native

  def setTitle(title: String): Nothing = js.native

  def addChild(itemOrItemConfig: js.Dictionary[js.Any]): Nothing = js.native

  def addChild(itemOrItemConfig: js.Dictionary[js.Any], index: Int): Nothing = js.native
}

//@js.native
//class Component(container : Container, state : js.Any) extends js.Object {
//  def getContainer : Container = {
//    container
//  }
//}

@js.native
trait Container extends js.Object {
  def getElement(): JQuery= js.native
}

