/*
 *
 * Copyright (c) 2019, Robby, Kansas State University
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

import facades._
import org.scalajs.dom.html.{Anchor, Div, Table, TableDataCell, TableRow}
import org.scalajs.dom.raw.{SVGAElement, SVGElement, SVGPolygonElement}
import org.scalajs.dom.svg.Defs
import org.scalajs.dom.{raw => _, _}
import org.sireum.awas.AliranAman.SecInfoFlowAnalysis
import org.sireum.awas.Main.{collectorToUris, gl, highlight}
import org.sireum.awas.Notification.Kind
import org.sireum.awas.symbol.{SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.common.JSutil
import org.sireum.common.JSutil.{$, render, templateContent}
import org.sireum.util._
import scalatags.Text.all._

import scala.scalajs.js

object SecViolations {

  var violationsRegistered = false

  var secInfoFlow: Option[SecViolations] = None

  def openWindow(st: SymbolTable): Boolean = {

    if (gl.isDefined && apply().getSecInfoFlow.getViolations().nonEmpty) {

      if (gl.get.root.getComponentsByName("violation").nonEmpty) {
        val vi = gl.get.root.getItemsById("violation").head
        val stacks = gl.get.root.getItemsByType("stack")
        val si = stacks.find(si => si.contentItems.contains(vi))
        if (si.isDefined) si.get.setActiveContentItem(vi)
      } else {
        if (!violationsRegistered) {
          gl.get.registerComponent("violation", {
            (container: Container, componentState: js.Dictionary[scalajs.js.Any]) =>
              {
                val termDom = div(id := "violations-window", style := "display:block;width:100%;height:100%").render
                container.getElement().append(termDom)
              }
          }: js.Function)
          violationsRegistered = true
        }
        gl.get.root.contentItems(0).addChild(Views.violationsConfig())
        val violationsWin: Div = $[Div]("#violations-window")
        violationsWin.appendChild(render[Table](Views.violationsTableBuild()))
        val violationTable: Table = $[Table](violationsWin, "#violations-table")
        var violation_num = 1
        apply().getSecInfoFlow.getViolatingPaths().keySet.toList.sorted.foreach { vp =>
          val violationButton = render[Anchor](
            a(
              textAlign := "left",
              padding := "2",
              justifyContent := "left",
              verticalAlign := "middle",
              id := "Violation-" + violation_num,
              style := "width:100%",
              cls := "button is-white",
              span("Violation-" + violation_num)
            )
          )
          violationButton.onclick = (_: MouseEvent) => {
            highlight(collectorToUris(apply().getSecInfoFlow.getViolatingPaths()(vp)), "#78c0a8")
          }

          val td1 = render[TableDataCell](td(verticalAlign := "middle", paddingLeft := "10"))

          td1.appendChild(violationButton)

          val td2 = render[TableDataCell](td(verticalAlign := "middle", span(vp)))

          val tr = JSutil.create[TableRow]("tr")
          tr.appendChild(td1)
          tr.appendChild(td2)
          violationTable.appendChild(tr)
          violation_num = violation_num + 1
        }
      }
    } else {
      Notification.notify(Kind.Info, "No Secure information leak found")
    }
    false
  }

  def apply(): SecViolations = {
    if (secInfoFlow.isEmpty) {
      secInfoFlow = Some(new SecViolationsImpl())
    }
    secInfoFlow.get
  }

}

trait SecViolations {
  def getSecInfoFlow: SecInfoFlowAnalysis

  def getTypeColor(graphUri: ResourceUri): IMap[ResourceUri, String]

  def getProvidedColor: IMap[ResourceUri, String]

  def getViolationColor(graphUri: ResourceUri): IMap[ResourceUri, String]

  def getColorDefs(graphUri: ResourceUri): ISet[Defs]

  def getLattice: SVGElement
}

class SecViolationsImpl() extends SecViolations {

  private var latticesvg: Option[SVGElement] = None

  private val secInfoFlow: SecInfoFlowAnalysis = SecInfoFlowAnalysis()

  private val typeColor: IMap[ResourceUri, String] = secInfoFlow
    .getSecTypes()
    .values
    .toSet
    .map { it: ResourceUri =>
      (it, RandomColor())
    }
    .toMap

  private var gradColorDefs = isetEmpty[Defs]

  private val inferedColor: IMap[ResourceUri, String] = secInfoFlow
    .getSecTypes()
    .values
    .toSet
    .map { it: ResourceUri =>
      val (cid, defs) = RandomColor.stripeColor((isetEmpty[String] + typeColor(it)) + "#ffffff")
      gradColorDefs = gradColorDefs + defs
      (it, cid)
    }
    .toMap

  private val providedColor: IMap[ResourceUri, String] = secInfoFlow
    .getProvidedSecType()
    .values
    .toSet
    .map { pc: ResourceUri => (pc, typeColor(pc)) }
    .toMap

  private val violationColor: IMap[ResourceUri, String] = secInfoFlow
    .getViolations()
    .map { vp =>
      val pc = secInfoFlow.getSecTypes()(vp)
      val (cid, defs) = RandomColor.colorViolation("#cc0000", typeColor(pc))
      gradColorDefs = gradColorDefs + defs
      (pc, cid)
    }
    .toMap

  def getSecInfoFlow: SecInfoFlowAnalysis = {
    secInfoFlow
  }

  def getTypeColor(graphUri: ResourceUri): IMap[ResourceUri, String] = {
    inferedColor.map(k => (k._1, "url(#" + SymbolTableHelper.uri2CanonicalName(graphUri) + "." + k._2 + ")"))
  }

  def getProvidedColor: IMap[ResourceUri, String] = {
    providedColor
  }

  def getViolationColor(graphUri: ResourceUri): IMap[ResourceUri, String] = {
    violationColor.map(k => (k._1, "url(#" + SymbolTableHelper.uri2CanonicalName(graphUri) + "." + k._2 + ")"))
  }

  def getColorDefs(graphUri: ResourceUri): ISet[Defs] = {
    gradColorDefs.map { od =>
      val defs = od.cloneNode(true).asInstanceOf[Defs]
      val id = defs.firstElementChild.getAttribute("id")
      defs.firstElementChild.setAttribute("id", SymbolTableHelper.uri2CanonicalName(graphUri) + "." + id)
      defs
    }
  }

  def getLattice: SVGElement = {
    if (latticesvg.isDefined) {
      latticesvg.get
    } else {
      val g = secInfoFlow.getLattice().getDot
      println(g)
      val svg = templateContent(raw(GraphViz.Viz(g)))
        .querySelectorAll("svg")(0).asInstanceOf[SVGElement]

      //getColorDefs("lattice").foreach(defs => svg.appendChild(defs))

      val nodes = org.scalajs.dom.ext.PimpedNodeList(svg.querySelectorAll(".node"))
      nodes.foreach { n =>
        val typeUri = n.asInstanceOf[SVGElement].firstElementChild.textContent

        val p = n.asInstanceOf[SVGElement].getElementsByTagName("polygon").item(0)
        if (typeColor.contains(typeUri)) {
          p.asInstanceOf[SVGPolygonElement].setAttribute("fill", typeColor(typeUri))
          p.asInstanceOf[SVGPolygonElement].setAttribute("fill-opacity", ".8")
        }
      }
      latticesvg = Some(svg)
      latticesvg.get
    }
  }

}
