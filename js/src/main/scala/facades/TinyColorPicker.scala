package facades

import org.scalajs.jquery.JQuery

import scala.language.implicitConversions
import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@js.native
trait TinyColorPicker extends JQuery {
  def setColor(color: String): Unit = js.native

  def tinycolorpicker(options: js.Dictionary[js.Any]): js.Any = js.native

  var colorHex: String

}

object TinyColorPicker {
  def apply(tag: String): TinyColorPicker = org.scalajs.jquery.jQuery(tag)

  implicit def jq2tinyColorPicker(jq: JQuery): TinyColorPicker = jq.asInstanceOf[TinyColorPicker]
}
