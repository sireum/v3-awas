package facades

import org.scalajs.dom.Blob

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobal, JSGlobalScope}

@js.native
@JSGlobalScope
object FileSaver extends js.Object {

  def saveAs(blob: Blob, fileName: String, disableBOM: Boolean = js.native): Unit = js.native
}

