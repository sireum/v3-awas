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

  def on(event: String, callBack: js.Function): js.UndefOr[Nothing] = js.native
}

