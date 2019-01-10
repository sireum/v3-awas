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

package org.sireum.awas

import org.scalajs.dom.MouseEvent
import org.scalajs.dom.html.Anchor
import org.sireum.awas.SvgNodeType.SvgNodeType
import org.sireum.awas.fptc.FlowNode
import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper}
//TODO
// convert this to SvgNode factory and SvgNode as a case class
// add the following methods in the case class
// 1. getUri
// 2. select(color)
// 3. pop - pops the color in the top
// 4. clear - removes all the color
// 5. getType

object SvgNodeType extends Enumeration {
  type SvgNodeType = Value
  val Node, Port, Error, Flow, Edge = Value
}

trait SvgNode {
  def reset(): Unit

  def getUri: String

  def select(isCriteria: Boolean, color: String): Unit

  def nodeColor: String

  def portColor: String

  def errorColor: String

  def nodeSelectionColor: String

  def portSelectionColor: String

  def errorSelectionColor: String

  def nodeCriteriaColor: String

  def portCriteriaColor: String

  def errorCriteriaColor: String

  def getNodeType: SvgNodeType.SvgNodeType
}

trait SvgNodeUpdateColors {
  def nodeColor_=(value: String): Unit

  def portColor_=(value: String): Unit

  def errorColor_=(value: String): Unit

  def nodeSelectionColor_=(value: String): Unit

  def portSelectionColor_=(value: String): Unit

  def errorSelectionColor_=(value: String): Unit

  def nodeCriteriaColor_=(value: String): Unit

  def portCriteriaColor_=(value: String): Unit

  def errorCriteriaColor_=(value: String): Unit
}

class SvgNodeImpl(node: Anchor, st: SymbolTable) extends SvgNode with SvgNodeUpdateColors {
  this: SvgNode =>
  val H = SymbolTableHelper
  private var nodeType: Option[SvgNodeType] = None
  private var criteria: Boolean = false
  private var uri: Option[String] = None

  private var _nodeColor: String = "#eeccff"
  private var _portColor: String = "#ffffff"
  private var _errorColor: String = "#ffffff"
  private var _nodeSelectionColor: String = "#b3daff"
  private var _portSelectionColor: String = "#b3daff"
  private var _errorSelectionColor: String = "#ff9999"
  private var _nodeCriteriaColor: String = "#008080"
  private var _portCriteriaColor: String = "#008080"
  private var _errorCriteriaColor: String = "#ff0000"
  private var _edgeColor: String = "#000000"
  private var _edgeSelectionColor: String = "#ff0000"

  processNode()

  private def edgeSelectionColor: String = _edgeSelectionColor

  private def edgeSelectionColor_=(value: String): Unit = {
    _edgeSelectionColor = value
  }

  private def edgeColor: String = _edgeColor

  private def edgeColor_=(value: String): Unit = {
    _edgeColor = value
  }

  def nodeColor: String = _nodeColor

  def nodeColor_=(value: String): Unit = {
    _nodeColor = value
  }

  def portColor: String = _portColor

  def portColor_=(value: String): Unit = {
    _portColor = value
  }

  def errorColor: String = _errorColor

  def errorColor_=(value: String): Unit = {
    _errorColor = value
  }

  def nodeSelectionColor: String = _nodeSelectionColor

  def nodeSelectionColor_=(value: String): Unit = {
    _nodeSelectionColor = value
  }

  def portSelectionColor: String = _portSelectionColor

  def portSelectionColor_=(value: String): Unit = {
    _portSelectionColor = value
  }

  def errorSelectionColor: String = _errorSelectionColor

  def errorSelectionColor_=(value: String): Unit = {
    _errorSelectionColor = value
  }

  def nodeCriteriaColor: String = _nodeCriteriaColor

  def nodeCriteriaColor_=(value: String): Unit = {
    _nodeCriteriaColor = value
  }

  def portCriteriaColor: String = _portCriteriaColor

  def portCriteriaColor_=(value: String): Unit = {
    _portCriteriaColor = value
  }

  def errorCriteriaColor: String = _errorCriteriaColor

  def errorCriteriaColor_=(value: String): Unit = {
    _errorCriteriaColor = value
  }

  private def processNode(): Unit = {
    getNodeType
    node.onclick = (_: MouseEvent) => Main.cellClicked(getUri, st)
    //    node.addEventListener("click touchstart", {(_: MouseEvent) =>
    //      Main.cellClicked(getUri)
    //    }:js.Function1[MouseEvent, _])
    if (!SettingsView.currentConfig.simpleConn &&
      getUri.startsWith(H.CONNECTION_TYPE) &&
      FlowNode.getNode(getUri).isDefined &&
      FlowNode.getNode(getUri).get.getOwner.getIncomingEdges(FlowNode.getNode(getUri).get).size == 1 &&
      FlowNode.getNode(getUri).get.getOwner.getIncomingEdges(FlowNode.getNode(getUri).get).size == 1) {
        node.setAttribute("xlink:title", "Connection: " + getUri.split(H.ID_SEPARATOR).last)
    } else {
      node.setAttribute("xlink:title", "Click to select")
    }

    if (getUri.startsWith(H.COMPONENT_TYPE) &&
      FlowNode.getNode(getUri).isDefined &&
      FlowNode.getNode(getUri).get.getSubGraph.isDefined) {
      node.setAttribute(
        "xlink:title",
        "Click to select " +
          "\n Double click to open sub-graph"
      )
    }
    reset()
  }

  override def reset(): Unit = {
    if (nodeType.isEmpty) {
      getNodeType
    }
    nodeType.get match {
      case SvgNodeType.Node => {
        node.firstElementChild.setAttribute("fill", _nodeColor)
        node.firstElementChild.setAttribute("fill-opacity", "1")
      }
      case SvgNodeType.Port => {
        node.firstElementChild.setAttribute("fill", _portColor)
        node.firstElementChild.setAttribute("fill-opacity", "1")
      }
      case SvgNodeType.Flow => {
        node.firstElementChild.setAttribute("fill", _portColor)
        node.firstElementChild.setAttribute("fill-opacity", "1")
      }
      case SvgNodeType.Error => {
        node.firstElementChild.setAttribute("fill", _errorColor)
        node.firstElementChild.setAttribute("fill-opacity", "1")
      }
      case SvgNodeType.Edge => {
        node.firstElementChild.nextElementSibling.setAttribute("fill", _edgeColor)
        node.firstElementChild.nextElementSibling.setAttribute("fill-opacity", ".2")
        node.firstElementChild.nextElementSibling.setAttribute("stroke-opacity", "0")
        node.firstElementChild.setAttribute("stroke", _edgeColor)
        node.firstElementChild.setAttribute("opacity", ".2")
      }
    }
  }

  override def select(isCriteria: Boolean, color: String): Unit = {
    if (isCriteria) {
      nodeType.get match {
        case SvgNodeType.Node => {
          node.firstElementChild.setAttribute("fill", "#1878c0")
          node.firstElementChild.setAttribute("fill-opacity", ".8")
        }
        case SvgNodeType.Port => {
          node.firstElementChild.setAttribute("fill", "#1878c0")
          node.firstElementChild.setAttribute("fill-opacity", ".8")
        }
        case SvgNodeType.Flow => {
          node.firstElementChild.setAttribute("fill", "#1878c0")
          node.firstElementChild.setAttribute("fill-opacity", ".8")
        }
        case SvgNodeType.Error => {
          node.firstElementChild.setAttribute("fill", _errorCriteriaColor)
          node.firstElementChild.setAttribute("fill-opacity", ".6")
        }
        case SvgNodeType.Edge => {
          node.firstElementChild.nextElementSibling.setAttribute("fill", _edgeSelectionColor)
          node.firstElementChild.nextElementSibling.setAttribute("fill-opacity", ".8")
          node.firstElementChild.setAttribute("stroke", _edgeSelectionColor)
          node.firstElementChild.setAttribute("opacity", ".8")
        }
      }
    } else {
      nodeType.get match {
        case SvgNodeType.Node => {
          node.firstElementChild.setAttribute("fill", color)
          node.firstElementChild.setAttribute("fill-opacity", ".8")
        }
        case SvgNodeType.Port => {
          node.firstElementChild.setAttribute("fill", color)
          node.firstElementChild.setAttribute("fill-opacity", ".6")
        }
        case SvgNodeType.Flow => {
          if (Resource.getParentUri(getUri).isDefined &&
            FlowNode.getNode(Resource.getParentUri(getUri).get).isDefined) {
            val flow = FlowNode.getNode(Resource.getParentUri(getUri).get).get.getFlows(getUri)
            if ((flow.fromPortUri.isEmpty && flow.toPortUri.nonEmpty) || (flow.fromPortUri.nonEmpty && flow.toPortUri.isEmpty)) {
              node.firstElementChild.setAttribute("fill", color)
              node.firstElementChild.setAttribute("fill-opacity", "1.5")
            } else {
              node.firstElementChild.setAttribute("fill", color)
              node.firstElementChild.setAttribute("fill-opacity", ".6")
            }
          } else {
            node.firstElementChild.setAttribute("fill", color)
            node.firstElementChild.setAttribute("fill-opacity", ".6")
          }
        }
        case SvgNodeType.Error => {
          node.firstElementChild.setAttribute("fill", _errorCriteriaColor)
          node.firstElementChild.setAttribute("fill-opacity", ".6")
        }
        case SvgNodeType.Edge => {
          node.firstElementChild.nextElementSibling.setAttribute("fill", _edgeSelectionColor)
          node.firstElementChild.nextElementSibling.setAttribute("fill-opacity", ".8")
          node.firstElementChild.setAttribute("stroke", _edgeSelectionColor)
          node.firstElementChild.setAttribute("opacity", ".8")
        }
      }
    }
  }

  override def getUri: String = {
    if (uri.isDefined) {
      uri.get
    } else {
      uri = Some(node.getAttribute("target"))
      uri.get
    }
  }

  override def getNodeType: SvgNodeType = {
    if (nodeType.isDefined) {
      nodeType.get
    } else {
      if (node.hasAttribute("xlink:title")) {
        val title = node.getAttribute("xlink:title")

        if (title == "node") {
          nodeType = Some(SvgNodeType.Node)
        } else if (title == "port") {
          nodeType = Some(SvgNodeType.Port)
        } else if (title == "flow") {
          nodeType = Some(SvgNodeType.Flow)
        } else if (title == "error") {
          nodeType = Some(SvgNodeType.Error)
        } else {
          nodeType = Some(SvgNodeType.Edge)
        }
      } else {
        nodeType = Some(SvgNodeType.Edge)
      }
      nodeType.get
    }
  }
}
