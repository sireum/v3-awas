package org.sireum.awas
import facades.{GraphViz, SvgPanZoom}
import org.scalajs.dom.Element
import org.scalajs.dom.html.{Anchor, Input}
import org.scalajs.dom.raw.Node
import org.scalajs.jquery.jQuery
import org.sireum.awas.Main._
import org.sireum.awas.fptc.{FlowEdge, FlowGraph, FlowGraphUpdate, FlowNode}
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.awas.witness.{RankDir, SvgGenConfig, SvgGenerator}
import org.sireum.common.JSutil.templateContent
import org.sireum.util.{IMap, ISet, ilistEmpty, imapEmpty, isetEmpty}
import scalatags.Text.all.raw

import scala.scalajs.js

object Util {
  private var svgs: IMap[ResourceUri, Node] = imapEmpty[String, Node]
  private var graphNodeMap: IMap[String, ISet[SvgNode]] = imapEmpty[String, ISet[SvgNode]]

//  def getAllSubGraphs(st: SymbolTable)
//  : IMap[ResourceUri, FlowGraph[FlowNode, FlowNode.Edge]] = {
//    if(graphs.isEmpty) {
//      graphs = FlowNode.getGraphs.map(it => it.getUri -> it).toMap
//    }
//    graphs
//  }

  def graph2Svg(graphUri: ResourceUri, viewConfig: SvgGenConfig = SettingsView.currentConfig, st: SymbolTable): Node = {
    val dotGraph = SvgGenerator(
      FlowNode
        .getGraph(graphUri)
        .get
        .asInstanceOf[FlowGraph[FlowNode, FlowNode.Edge] with FlowGraphUpdate[FlowNode, FlowEdge[FlowNode]]],
      viewConfig
    )
    //    println(dotGraph)
    val svgString = GraphViz.Viz(
      dotGraph,
      js.Dictionary(
        (
          "images",
          js.Array(js.Dictionary(("path", "min/images/sub-graph-icon.png"), ("width", "45px"), ("height", "25px")))
        )
      )
    )
    val result = templateContent(raw(svgString)).querySelectorAll("svg")(0)
    val nodes = processSvg(result.asInstanceOf[Element], st)
    graphNodeMap += (graphUri -> nodes)
    svgs = svgs + ((graphUri, result))
    result
  }

  def reDrawGraphs(svgGenConfig: SvgGenConfig): Unit = {
    svgs.keySet.foreach { gk =>
      val old_svg = svgs(gk)
      val new_svg = Util.graph2Svg(gk, svgGenConfig, st.get)
      old_svg.parentNode.replaceChild(new_svg, old_svg)
      resetOrientationRadio()
    }
    SvgPanZoom("svg").svgPanZoom(js.Dictionary())
  }

  def processSvg(svg: Element, st: SymbolTable): ISet[SvgNode] = {
    svg.setAttribute("id", "awasgraph")
    svg.setAttribute("height", "100%")
    svg.setAttribute("width", "100%")
    jQuery(svg).removeAttr("xmlns")
    jQuery(svg).find("title").each((e: Element) => e.parentNode.removeChild(e))
    val allLinks = org.scalajs.dom.ext.PimpedNodeList(svg.querySelectorAll("[*|href='templink']"))
    val svgNodes = allLinks.map(n => new SvgNodeImpl(n.asInstanceOf[Anchor], st))
    val allUri = svgNodes.map(_.getUri).toSet
    svgNodes.foreach { node =>
      uriNodeMap = uriNodeMap + (node.getUri ->
        (uriNodeMap.getOrElse(node.getUri, isetEmpty[SvgNode]) + node))
    }

    if ((allUri intersect selections.keySet).nonEmpty) {

      selections.filter(s => allUri.contains(s._1)).foreach(s => highlight(imapEmpty + (s._1 -> s._2._1), s._2._2))
    }
    svgNodes.toSet
  }

  def resetOrientationRadio(): Unit = {
    svgs.keySet.foreach { g =>
      Main.gl.get.root.getItemsById(g).foreach { ci =>
        if (SettingsView.currentConfig.rankDir == RankDir.TB) {
          ci.element(0).querySelector("#td").asInstanceOf[Input].checked = true
          ci.element(0).querySelector("#lr").asInstanceOf[Input].checked = false
        } else {
          ci.element(0).querySelector("#td").asInstanceOf[Input].checked = false
          ci.element(0).querySelector("#lr").asInstanceOf[Input].checked = true
        }

        val container = ci.container
        if (ci.config.get("componentState").isDefined) {
          val compState = ci.config.get("componentState").get.asInstanceOf[js.Dictionary[scalajs.js.Any]]
          if (SettingsView.currentConfig.rankDir == RankDir.TB) {
            compState.update("isTD", true)
          } else {
            compState.update("isTD", false)
          }
        }
      }
    }
  }

}
