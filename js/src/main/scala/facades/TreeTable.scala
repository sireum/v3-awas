package facades

import org.scalajs.dom.Element
import org.scalajs.jquery.JQuery

import scala.language.implicitConversions
import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@js.native
trait TreeTable extends JQuery {
  def treetable(options: js.Dictionary[js.Any] = js.native): this.type = js.native

  val Node: TreeNode = js.native
}

@js.native
trait TreeNode extends js.Object {
  def expander: js.Array[Element] = js.native
}


object TreeTable {
  def apply(tag: String): TreeTable = {
    val x = org.scalajs.jquery.jQuery(tag)
    println("table : " + x)
    x
  }

  implicit def jq2treeTable(jq: JQuery): TreeTable = jq.asInstanceOf[TreeTable]
}
