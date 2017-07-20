package facades

import org.scalajs.jquery.JQuery

import scala.language.implicitConversions
import scala.scalajs.js


@js.native
trait SvgPanZoom extends JQuery {
  def svgPanZoom(options: js.Dictionary[String] = js.native): this.type = js.native
}

object SvgPanZoom {
  def apply(tag: String): SvgPanZoom = org.scalajs.jquery.jQuery(tag)

  implicit def jq2svgPanZoom(jq: JQuery): SvgPanZoom = jq.asInstanceOf[SvgPanZoom]
}


