package facades

import org.scalajs.jquery.JQuery

import scala.language.implicitConversions
import scala.scalajs.js

@js.native
trait Terminal extends JQuery {
  //def terminal(fn:js.Function, config:js.Dynamic): Terminal = js.native
  def terminal(fn: js.Function2[String, Terminal, Unit],
               option: js.Dictionary[Any]): Terminal = js.native

  def clear(): Nothing = js.native

  def echo(in: String): Nothing = js.native

  def echo(in: String, options: js.Dictionary[Any]): Nothing = js.native

  def scroll_to_bottom(): Nothing = js.native

  override def resize(): Nothing = js.native

  def resize(width: Int, height: Int): Nothing = js.native

}

object Terminal {
  def apply(tag: String): Terminal = org.scalajs.jquery.jQuery(tag)

  implicit def jq2terminal(jq: JQuery): Terminal = jq.asInstanceOf[Terminal]

}