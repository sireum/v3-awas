/*
 *
 *  Copyright (c) 2017, Hariharan Thiagarajan, Kansas State University
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package facades

import org.scalajs.dom.{Element, Node}

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobal, ScalaJSDefined}

//
//import org.scalajs.dom.{Blob, Node}
//import org.scalajs.jquery.JQuery
//
//import scala.language.implicitConversions
//import scala.runtime.java8.JFunction1$mcDD$sp
//import scala.scalajs.js
//import scala.scalajs.js.annotation.JSGlobalScope
//import js.JSConverters._

//@js.native
//trait SvgPanZoom extends JQuery {
//  def svgPanZoom(options: js.Dictionary[String] = js.native): this.type = js.native
//}
//
//object SvgPanZoom {
//  def apply(tag: String): SvgPanZoom = org.scalajs.jquery.jQuery(tag)
//
//  implicit def jq2svgPanZoom(jq: JQuery): SvgPanZoom = jq.asInstanceOf[SvgPanZoom]
//}
//
//
//object SvgHelper {
//  val opt  : js.Dictionary[js.Any] = js.Dictionary(
//    ("viewportSelector", ".svg-pan-zoom_viewport'"),
//    ("panEnabled", true),
//    ("controlIconsEnabled", true),
//    ("zoomEnabled", true),
//    ("dblClickZoomEnabled", false),
//    ("mouseWheelZoomEnabled", true),
//    ("preventMouseEventsDefault", true),
//    ("zoomScaleSensitivity", 0.5),
//    ("minZoom", 0.5),
//    ("maxZoom", 10),
//    ("fit", 10),
//    ("contain", false),
//    ("center", true),
//    ("refreshRate", "auto")
//
//  )
//}
//
//@js.native
//@JSGlobalScope


@js.native
@JSGlobal
class SVGPanZoom(svg: Element, options: Options) extends js.Object {
  def zoomIn(): Unit = js.native
}

object Options {

  def apply(eventMagnet: Element): Options = {
    js.Dynamic.literal(eventMagnet = eventMagnet).asInstanceOf[Options]
  }
}

@js.native
trait Options extends js.Object {
  var initialViewBox: js.Object = js.native
  var animationTime: Int = js.native
  var limits: String = js.native
  var eventMagnet: Element = js.native
  var zoom: js.Dynamic = js.native
  var pan: js.Dynamic = js.native
}