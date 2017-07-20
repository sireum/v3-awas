package facades

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobalScope

@js.native
@JSGlobalScope
object GraphQuery extends js.Object {
  val graph: String = js.native
  val queryExp: js.Dictionary[String] = js.native
  val queryRes: js.Dictionary[js.Array[String]] = js.native
}
