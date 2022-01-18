/*
 *
 * Copyright (c) 2020, Hariharan Thiagarajan, Kansas State University
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sireum.awas
import facades.{GraphViz, SvgPanZoom}
import org.scalajs.dom.Element
import org.scalajs.dom.html.{Anchor, Input}
import org.scalajs.dom.raw.SVGElement
import org.scalajs.jquery.jQuery
import org.sireum.awas.Main._
import org.sireum.awas.flow.{FlowEdge, FlowGraph, FlowGraphUpdate, FlowNode}
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.awas.witness.{Errors, RankDir, SvgGenConfig, SvgGenerator}
import org.sireum.common.JSutil.{$$, templateContent}
import org.sireum.util.{IMap, ISet, imapEmpty, isetEmpty}
import scalatags.Text.all.raw

import scala.scalajs.js

object Util {
  private var svgs: IMap[ResourceUri, SVGElement] = imapEmpty[String, SVGElement]
  private var graphNodeMap: IMap[String, ISet[SvgNode]] = imapEmpty[String, ISet[SvgNode]]

//  def getAllSubGraphs(st: SymbolTable)
//  : IMap[ResourceUri, FlowGraph[FlowNode, FlowNode.Edge]] = {
//    if(graphs.isEmpty) {
//      graphs = FlowNode.getGraphs.map(it => it.getUri -> it).toMap
//    }
//    graphs
//  }

  def graph2Svg(
    graphUri: ResourceUri,
    viewConfig: SvgGenConfig = SettingsView.currentConfig,
    st: SymbolTable
  ): SVGElement = {
    val dotGraph = SvgGenerator(st: SymbolTable,
      FlowNode
        .getGraph(graphUri)
        .get
        .asInstanceOf[FlowGraph[FlowNode, FlowNode.Edge] with FlowGraphUpdate[FlowNode, FlowEdge[FlowNode]]],
      viewConfig, None
    )
    //    println(dotGraph)
    val svgString = GraphViz.Viz(
      dotGraph,
      js.Dictionary(
        (
          "images",
          js.Array(js.Dictionary(("path", "min/images/sub-graph-icon.png"), ("width", "45px"), ("height", "25px"))))
      )
    )
    var result = templateContent(raw(svgString)).querySelectorAll("svg")(0).asInstanceOf[SVGElement]

    if (viewConfig.viewErrors == Errors.Types) {
      SecViolations().getColorDefs(graphUri).foreach(result.appendChild(_))
      //      result = templateContent(raw(result.outerHTML)).querySelectorAll("svg")(0).asInstanceOf[SVGElement]
    }

    val nodes = processSvg(result, st)
    graphNodeMap += (graphUri -> nodes)
    svgs = svgs + ((graphUri, result))

    if (viewConfig.viewErrors == Errors.Types) {
      val errorNodes = nodes.filter(_.getNodeType == SvgNodeType.Error)
      val types = errorNodes.map(_.getUri.split(SvgGenerator.URI_GLUE).last).toList.sorted


      errorNodes.foreach { en =>
        val typeUri = en.getUri.split(SvgGenerator.URI_GLUE).last
        val pUri = en.getUri.split(SvgGenerator.URI_GLUE)(1)
        var color = if (SecViolations().getSecInfoFlow.getViolations().contains(pUri)) {
          SecViolations().getViolationColor(graphUri)(typeUri)
        } else if (SecViolations().getSecInfoFlow.getProvidedSecType().keySet.contains(pUri)) {
          SecViolations().getProvidedColor(typeUri)
        } else {
          SecViolations().getTypeColor(graphUri)(typeUri)
        }

        // val pNode = uriNodeMap(pUri).map(_.asInstanceOf[SvgNodeUpdateColors])
        val tNode = uriNodeMap(en.getUri).intersect(nodes).map(_.asInstanceOf[SvgNodeUpdateColors])

        //pNode.foreach(_.portColor_=(color))

        tNode.foreach(_.errorColor_=(color))
        uriNodeMap(pUri).foreach(_.reset())
        uriNodeMap(en.getUri).foreach(_.reset())
      }
    }

    //SvgPanZoom.svgPanZoom(result)

    result
  }


  def reDrawGraphs(svgGenConfig: SvgGenConfig): Unit = {
    if (gl.isDefined) {
      gl.get
    }
    svgs.keySet.foreach { gk =>
      val old_svg = svgs(gk)
      val new_svg = Util.graph2Svg(gk, svgGenConfig, st.get)
      old_svg.parentNode.replaceChild(new_svg, old_svg)
      $$[SVGElement]("svg").foreach(svg => new SvgPanZoom(svg).enableControlIcons().disableDblClickZoom().setMaxZoom(200))
      resetOrientationRadio()
    }

    //SvgPanZoom.svgPanZoom("svg")
  }

  def processSvg(svg: SVGElement, st: SymbolTable): ISet[SvgNode] = {
    svg.setAttribute("id", "awasgraph")
    svg.setAttribute("height", "100%")
    svg.setAttribute("width", "100%")
  //    svg.setAttribute("viewBox", "0 0 100 100")
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
