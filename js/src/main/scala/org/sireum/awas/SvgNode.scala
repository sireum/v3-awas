package org.sireum.awas

import org.scalajs.dom.MouseEvent
import org.scalajs.dom.html.Anchor
import org.scalajs.dom.svg.{G, SVG}
import org.sireum.awas.Main.openGraphTab
import org.sireum.awas.SvgNodeType.SvgNodeType
import org.sireum.awas.fptc.FlowNode
import org.sireum.awas.symbol.{SymbolTable, SymbolTableHelper}

import scala.scalajs.js

object SvgNodeType extends Enumeration {
  type SvgNodeType = Value
  val Node, Port, Error, Flow, Edge = Value
}

trait SvgNode {
  def reset(): Unit

  def getUri: String

  def select(isCriteria: Boolean): Unit

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
  private var _errorCriteriaColor: String = "#008080"
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

  processNode()

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
    node.setAttribute("xlink:title", "Click to select")
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
      case SvgNodeType.Node => node.firstElementChild.setAttribute("fill", _nodeColor)
      case SvgNodeType.Port => node.firstElementChild.setAttribute("fill", _portColor)
      case SvgNodeType.Flow => node.firstElementChild.setAttribute("fill", _portColor)
      case SvgNodeType.Error => node.firstElementChild.setAttribute("fill", _errorColor)
      case SvgNodeType.Edge => {
        node.firstElementChild.nextElementSibling.setAttribute("fill", _edgeColor)
        node.firstElementChild.nextElementSibling.setAttribute("fill-opacity", ".2")
        node.firstElementChild.nextElementSibling.setAttribute("stroke-opacity", "0")
        node.firstElementChild.setAttribute("stroke", _edgeColor)
        node.firstElementChild.setAttribute("opacity", ".2")
      }
    }

  }

  override def select(isCriteria: Boolean): Unit = {
    if (isCriteria) {
      nodeType.get match {
        case SvgNodeType.Node => node.firstElementChild.setAttribute("fill", _nodeCriteriaColor)
        case SvgNodeType.Port => node.firstElementChild.setAttribute("fill", _portCriteriaColor)
        case SvgNodeType.Flow => node.firstElementChild.setAttribute("fill", _portCriteriaColor)
        case SvgNodeType.Error => node.firstElementChild.setAttribute("fill", _errorCriteriaColor)
        case SvgNodeType.Edge => {
          node.firstElementChild.nextElementSibling.setAttribute("fill", _edgeSelectionColor)
          node.firstElementChild.nextElementSibling.setAttribute("fill-opacity", ".8")
          node.firstElementChild.setAttribute("stroke", _edgeSelectionColor)
          node.firstElementChild.setAttribute("opacity", ".8")
        }
      }
    } else {
      nodeType.get match {
        case SvgNodeType.Node => node.firstElementChild.setAttribute("fill", _nodeSelectionColor)
        case SvgNodeType.Port => node.firstElementChild.setAttribute("fill", _portSelectionColor)
        case SvgNodeType.Flow => node.firstElementChild.setAttribute("fill", _portSelectionColor)
        case SvgNodeType.Error => node.firstElementChild.setAttribute("fill", _errorSelectionColor)
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
        }
      } else {
        nodeType = Some(SvgNodeType.Edge)
      }
      nodeType.get
    }
  }
}
