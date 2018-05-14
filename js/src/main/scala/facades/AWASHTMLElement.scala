package facades

import org.scalajs.dom.html.Element
import org.scalajs.dom.raw.HTMLElement

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@js.native
trait AWASHTMLElement extends Element {
  def dataset: js.Dictionary[String] = js.native
}