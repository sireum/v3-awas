package facades

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobalScope

@js.native
@JSGlobalScope
object GraphViz extends js.Object {
  def Viz(src: String): String = js.native

  def Viz(src: String, options: js.Dictionary[Any]): String = js.native
}
