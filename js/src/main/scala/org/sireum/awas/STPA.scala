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

import facades.{GoldenLayout, GraphQuery, SvgPanZoom}
import org.scalajs.dom.{Element, Event, document}
import org.scalajs.dom.html.{Anchor, Div, Table, TableCell, TableRow}
import org.scalajs.dom.raw.{MouseEvent, MutationObserver, MutationObserverInit, MutationRecord, SVGElement}
import org.scalajs.jquery.jQuery
import org.sireum.awas.Main.{H, buildGraphWindow, clearAll, collectorToUris, gl, highlight, st}
import org.sireum.awas.STPA.pss
import org.sireum.awas.analysis.StateReachAnalysis
import org.sireum.awas.ast.{AwasSerializer, Id, Node, Property, RecordInit, SeqInit, StringInit}
import org.sireum.awas.collector.Collector
import org.sireum.awas.flow.{FlowGraph, FlowNode, NodeType}
import org.sireum.awas.reachability.{ErrorReachability, ErrorReachabilityImpl, PortReachabilityImpl}
import org.sireum.awas.report.{ISO14971Property, StpaProperty}
import org.sireum.awas.slang.Aadl2Awas
import org.sireum.awas.symbol.{SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.awas.witness.SvgGenConfig
import org.sireum.common.JSutil.{render, _}
import org.sireum.util.ConsoleTagReporter
import scalatags.Text.all.{td, _}
import org.sireum.util._

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("STPA")
object STPA {

  val isSTPA = false
  val isISO14971 = true

  var selected: Option[Element] = None

  val severity: IMap[String, Int] = Map(
    ("Catastrophic", 1),
    ("High", 2),
    ("Medium", 3),
    ("Low", 4),
    ("Negligible", 5),
    ("Critical", 2),
    ("Serious", 3),
    ("Minor", 4),
    ("NoEffect", 5)
  )

  @JSExport
  def main(): Unit = {
    var model = if (js.typeOf(js.Dynamic.global.json) != "undefined") {
      Aadl2Awas.apply(GraphQuery.json.get)
    } else if (js.typeOf(js.Dynamic.global.awas) != "undefined") {
      AwasSerializer.unapply(GraphQuery.awas.get)
    } else {
      None
    }

    selected = None
    if (model.isDefined) {

      val reporter = new ConsoleTagReporter()
      st = Some(SymbolTable(model.get)(reporter))
      Main.st = st
      SettingsView.setStoredSettings(SvgGenConfig.defaultErrorConfig)
      val systemGraph = FlowGraph(model.get, st.get, includeBindingEdges = false)

      val mainDiv = if (isSTPA) render[Div](Views.stpaMain()) else render[Div](Views.iso14971Main())
      val body = mainDiv.querySelector("#body")


      println("-------- Stat Info-------------")
      println("Number of subsystem: " + FlowNode.getGraphs.size)
      println("Number of components: " + FlowNode.getGraphs.flatMap(_.nodes.filter(_.getResourceType == NodeType.COMPONENT)).size)
      println("Number of connections: " + FlowNode.getGraphs.flatMap(_.nodes.filter(_.getResourceType == NodeType.CONNECTION)).size)
      println("Number of Edges: " + FlowNode.getGraphs.flatMap(_.edges).size)
      println("Number of Flows: " + FlowNode.getGraphs.flatMap(it => st.get.componentTable(it.getUri).flows).size)

      if(isSTPA) {
        body.appendChild(render[Div](getSystem(st.get)))
        body.appendChild(render[Div](getAccidentLevels(st.get)))
        body.appendChild(render[Div](getAccident(st.get)))
        body.appendChild(render[Div](getSystemConstraints(st.get)))
        body.appendChild(render[Div](getEnvCond(st.get)))
        body.appendChild(render[Div](getAllHaz(st.get)))
        body.appendChild(render[Div](getSystemHaz(st.get)))

        val asvg = Util.graph2Svg(systemGraph.getUri, SvgGenConfig.defaultConfig, st.get)
        val svgDiv = render[Div](div(height := "100%", div(cls := "tempSvg")))
        svgDiv.replaceChild(asvg, svgDiv.querySelector(".tempSvg"))
        body.appendChild(svgDiv)
        $$[SVGElement]("svg").foreach(svg => new SvgPanZoom(svg).enableControlIcons().disableDblClickZoom().setMaxZoom(200))
        body.appendChild(getCausalScenario(st.get, ""))
      } else if(isISO14971) {
        val hs = getAllHazSituations(st.get)
        val causes = getAllISO14971Causes(st.get)
        body.appendChild(render[Div](getSystem14971(st.get)))
        body.appendChild(render[Div](getISO14971Harm(st.get, hs)))
        body.appendChild(render[Div](getISO14971ContribFact(st.get, hs)))
        body.appendChild(render[Div](getISO14971Causes(st.get, causes.values.toList)))
        body.appendChild(render[Div](getISO14971Haz(st.get)))
        body.appendChild(render[Div](getISO14971HazSituations(st.get, hs)))
        val graphDiv = render[Div](div(width := "100%", height := "500px"))

        val config = Views.getInitLayout(st.get.systemDecl.compName.value, st.get.system, canPop = true)
        SettingsView.setStoredSettings(SvgGenConfig.defaultErrorConfig)
        Main.gl = Some(new GoldenLayout(config, jQuery(graphDiv)))
        Main.gl.get.updateSize()
        body.appendChild(graphDiv)


        val haz = getAllISO14971Hazards(st.get).map(it => (it._1, it._2._2))
        val caz = getAllISO14971Causes(st.get).map(it => (it._1, it._2._2))

        var hz_hs = imapEmpty[String, ISet[String]]
        var hs_hrm = imapEmpty[String, ISet[String]]
        var hs_cf = imapEmpty[String, ISet[String]]

        val hsrec: ISet[RecordInit] = hs.flatMap(_._2).flatMap { hsProp =>
          if (hsProp.value.isDefined && hsProp.value.get.isInstanceOf[SeqInit]) {
            hsProp.value.get.asInstanceOf[SeqInit].value.map(_.asInstanceOf[RecordInit]).toSet
          }
          else if (hsProp.value.isDefined && hsProp.value.get.isInstanceOf[RecordInit]) {
            isetEmpty + hsProp.value.get.asInstanceOf[RecordInit]
          }
          else {
            isetEmpty[RecordInit]
          }
        }.toSet

        hsrec.foreach { h =>
          val hsid = h.fields(Id(ISO14971Property.HAZ_SITUATION_ID)).asInstanceOf[StringInit].value
          if (h.fields.get(Id(ISO14971Property.HAZ_SITUATION_HAZARD)).isDefined) {
            val hazid = h.fields(Id(ISO14971Property.HAZ_SITUATION_HAZARD))
              .asInstanceOf[RecordInit].fields(Id(ISO14971Property.HAZARD_ID)).asInstanceOf[StringInit].value
            hz_hs = hz_hs + (hazid -> (hz_hs.getOrElse(hazid, isetEmpty) + hsid))
          }
          if (h.fields.get(Id(ISO14971Property.HAZ_SITUATION_PATHS)).isDefined) {
            val pathProp = h.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS))
            if (pathProp.isInstanceOf[SeqInit]) {
              pathProp.asInstanceOf[SeqInit].value.foreach { path =>
                val hrmid = path.asInstanceOf[RecordInit].fields(Id(ISO14971Property.HAZ_SITUATION_HARM))
                  .asInstanceOf[RecordInit].fields(Id(ISO14971Property.HARM_ID)).asInstanceOf[StringInit].value
                hs_hrm = hs_hrm + (hsid -> (hs_hrm.getOrElse(hsid, isetEmpty) + hrmid))
              }
            } else {
              val hrmid = pathProp.asInstanceOf[RecordInit].fields(Id(ISO14971Property.HAZ_SITUATION_HARM))
                .asInstanceOf[RecordInit].fields(Id(ISO14971Property.HARM_ID)).asInstanceOf[StringInit].value
              hs_hrm = hs_hrm + (hsid -> (hs_hrm.getOrElse(hsid, isetEmpty) + hrmid))
            }
          }

          if (h.fields.get(Id(ISO14971Property.HAZ_SITUATION_PATHS)).isDefined) {
            val pathProp = h.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS))
            val paths = if (pathProp.isInstanceOf[SeqInit]) {
              pathProp.asInstanceOf[SeqInit].value.map(_.asInstanceOf[RecordInit])
            } else {
              Node.emptySeq :+ pathProp.asInstanceOf[RecordInit]
            }

            val cf = paths.flatMap(_.fields.get(Id(ISO14971Property.HAZ_SITUATION_CF)))
            if (cf.nonEmpty) {
              cf.foreach {
                case si: SeqInit => {
                  si.value.filter(_.isInstanceOf[RecordInit]).map(_.asInstanceOf[RecordInit].fields(Id(ISO14971Property.CONTRIB_ID)).asInstanceOf[StringInit].value).foreach { cfid =>
                    hs_cf = hs_cf + (hsid -> (hs_cf.getOrElse(hsid, isetEmpty) + cfid))
                  }
                }
                case it: RecordInit => {
                  val cfid = it.asInstanceOf[RecordInit].fields(Id(ISO14971Property.CONTRIB_ID)).asInstanceOf[StringInit].value
                  hs_cf = hs_cf + (hsid -> (hs_cf.getOrElse(hsid, isetEmpty) + cfid))
                }
                case _ =>
              }
            }
          }
        }
        body.appendChild(topDownAnalysis(st.get, haz, caz, hz_hs, hs_hrm, hs_cf))
        body.appendChild(bottomUpAnalysis(st.get, haz, caz, hz_hs, hs_hrm, hs_cf))


      }

      document.onreadystatechange = (_: Event) => {
        if (document.readyState == "complete") {
          SettingsView.setStoredSettings(SvgGenConfig.defaultErrorConfig)
          document.body.appendChild(mainDiv)
          buildGraphWindow(true)


          val mo = new MutationObserver({ (e: js.Array[MutationRecord], _: MutationObserver) => {
            if (!e.head.target.hasChildNodes() && e.head.target.parentNode.isInstanceOf[Div]) {
              e.head.target.parentNode.asInstanceOf[Div].style = "display: none;"
            }
            if (e.head.addedNodes.length != 0 && e.head.target.parentNode.isInstanceOf[Div]) {
              e.head.target.parentNode.asInstanceOf[Div].style = "display: block;"
            }

          }
          }: js.Function2[js.Array[MutationRecord], MutationObserver, _])

          mo.observe(Main.gl.get.root.element.head, MutationObserverInit(childList = true))
        }
      }
    }
  }

  def select(elem: Element) = {
    if (selected.isDefined && selected.get == elem) {
      clearAll(Main.selections.keySet)
      gl.get.eventHub.emit("clearall")
      elem.setAttribute("style", "background-color:#FFFFFF")
      selected = None
    } else {
      if (selected.nonEmpty) {
        selected.get.setAttribute("style", "background-color:#FFFFFF")
        //clearAll(Main.selections.keySet)
      }
      selected = Some(elem)
      elem.setAttribute("style", "background-color:#1878c0")
    }
  }

  def getSystem(st: SymbolTable): Frag = {
    val systemProps = st.componentTable(st.system).componentDecl.properties
    val sysProp = systemProps.find(prop => prop.id.value.last.value == StpaProperty.SYSTEM_PROP)
    if (sysProp.isDefined && sysProp.get.value.isDefined) {
      val sysVal = sysProp.get.value.get.asInstanceOf[RecordInit]
      // we know system property val is a record type
      val sysName = sysVal.fields.get(Id(StpaProperty.SYSTEM_PROP_NAME))
      val sysDesc = sysVal.fields.get(Id(StpaProperty.SYSTEM_PROP_DESC))


      val sname = if (sysName.isDefined) {
        div(h3(sysName.get.asInstanceOf[StringInit].value))
      } else {
        div()
      }

      val sdesc = if (sysDesc.isDefined) {
        div(sysDesc.get.asInstanceOf[StringInit].value)
      } else {
        div()
      }
      div(cls := "content is-medium", sname, sdesc)
    } else {
      div()
    }
  }

  def getSystem14971(st: SymbolTable): Frag = {
    val systemProps = st.componentTable(st.system).componentDecl.properties
    val sysProp = systemProps.find(prop => prop.id.value.last.value == ISO14971Property.SYSTEM_PROP)
    if (sysProp.isDefined && sysProp.get.value.isDefined) {
      val sysVal = sysProp.get.value.get.asInstanceOf[RecordInit]
      // we know system property val is a record type
      val sysName = sysVal.fields.get(Id(ISO14971Property.SYSTEM_PROP_NAME))
      val sysDesc = sysVal.fields.get(Id(ISO14971Property.SYSTEM_PROP_DESC))
      val sysInten = sysVal.fields.get(Id(ISO14971Property.SYSTEM_PROP_INTENDED))

      val sname = if (sysName.isDefined) {
        div(h3(sysName.get.asInstanceOf[StringInit].value))
      } else {
        div()
      }

      val sdesc = if (sysDesc.isDefined) {

        div(p(sysDesc.get.asInstanceOf[StringInit].value))
      } else {
        div()
      }

      val sintended = if (sysInten.isDefined) {

        div(br, h5("Intended Use"), p(sysInten.get.asInstanceOf[StringInit].value))
      } else {
        div()
      }

      div(cls := "content is-medium", sname, sdesc, sintended)
    } else {
      div()
    }
  }

  def getISO14971Harm(st: SymbolTable, hs: IMap[ResourceUri, ISet[Property]]): Frag = {
    var harms = ilinkedSetEmpty[RecordInit]
    //val hs = getAllHazSituations(st)
    hs.foreach { h =>
      h._2.foreach { prop =>
        if (prop.value.isDefined && prop.value.get.isInstanceOf[RecordInit]) {
          val recProp = prop.value.get.asInstanceOf[RecordInit]
          if (recProp.fields.get(Id(ISO14971Property.HAZ_SITUATION_PATHS)).isDefined) {
            val paths = if (recProp.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS)).isInstanceOf[SeqInit]) {
              recProp.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS)).asInstanceOf[SeqInit].value
            } else {
              Node.emptySeq :+ recProp.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS))
            }
            val harm = paths.flatMap(_.asInstanceOf[RecordInit].fields.get(Id(ISO14971Property.HAZ_SITUATION_HARM)))
            if (harm.nonEmpty) {
              harms = harms ++ harm.map(_.asInstanceOf[RecordInit])
            }
          }
        } else if (prop.value.isDefined && prop.value.get.isInstanceOf[SeqInit]) {
          prop.value.get.asInstanceOf[SeqInit].value.foreach { hsp =>
            val recProp = hsp.asInstanceOf[RecordInit]
            if (recProp.fields.get(Id(ISO14971Property.HAZ_SITUATION_PATHS)).isDefined) {
              val paths = if (recProp.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS)).isInstanceOf[SeqInit]) {
                recProp.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS)).asInstanceOf[SeqInit].value
              } else {
                Node.emptySeq :+ recProp.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS))
              }
              val harm = paths.flatMap(_.asInstanceOf[RecordInit].fields.get(Id(ISO14971Property.HAZ_SITUATION_HARM)))
              if (harm.nonEmpty) {
                harms = harms ++ harm.map(_.asInstanceOf[RecordInit])
              }
            }
          }
        }
      }
    }

    if (harms.nonEmpty) {
      val tableRows = harms.map { harm =>
        tr(
          id := harm.fields(Id(ISO14971Property.HARM_ID)).asInstanceOf[StringInit].value,
          td(harm.fields(Id(ISO14971Property.HARM_ID)).asInstanceOf[StringInit].value),
          td(harm.fields(Id(ISO14971Property.HARM_DESC)).asInstanceOf[StringInit].value),
          td(harm.fields(Id(ISO14971Property.HARM_SEVERITY)).asInstanceOf[StringInit].value)
        )
      }.toList
      val tab = table(
        cls := "table",
        thead(tr(th(p("ID")), th(p("Description")), th(p("Severity")))),
        tbody(
          for (row <- tableRows)
            yield row
        )
      )
      div(cls := "content is-medium", h3("Harms"), tab)
    } else {
      div()
    }
  }

  def getISO14971ContribFact(st: SymbolTable, hs: IMap[ResourceUri, ISet[Property]]): Frag = {
    var facts = ilinkedSetEmpty[RecordInit]
    val prop = hs.flatMap(_._2)
    prop.foreach { p =>
      if (p.value.isDefined && p.value.get.isInstanceOf[RecordInit]) {
        val hs = p.value.get.asInstanceOf[RecordInit]
        if (hs.fields.get(Id(ISO14971Property.HAZ_SITUATION_PATHS)).isDefined) {
          val paths = if (hs.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS)).isInstanceOf[SeqInit]) {
            hs.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS)).asInstanceOf[SeqInit].value
          } else {
            Node.emptySeq :+ hs.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS))
          }

          val cf = paths.flatMap(_.asInstanceOf[RecordInit].fields.get(Id(ISO14971Property.HAZ_SITUATION_CF)))
          if (cf.nonEmpty) {
            facts = facts ++ cf.flatMap { it =>
              it match {
                case si: SeqInit => si.value.map(_.asInstanceOf[RecordInit])
                case _ => Node.emptySeq :+ it.asInstanceOf[RecordInit]
              }
            }
          }
        }

      } else if (p.value.isDefined && p.value.get.isInstanceOf[SeqInit]) {
        p.value.get.asInstanceOf[SeqInit].value.foreach { hsp =>
          val hs = hsp.asInstanceOf[RecordInit]
          if (hs.fields.get(Id(ISO14971Property.HAZ_SITUATION_PATHS)).isDefined) {
            val paths = if (hs.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS)).isInstanceOf[SeqInit]) {
              hs.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS)).asInstanceOf[SeqInit].value
            } else {
              Node.emptySeq :+ hs.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS))
            }

            val cf = paths.flatMap(_.asInstanceOf[RecordInit].fields.get(Id(ISO14971Property.HAZ_SITUATION_CF)))
            if (cf.nonEmpty) {
              facts = facts ++ cf.flatMap { it =>
                it match {
                  case si: SeqInit => si.value.filter(_.isInstanceOf[RecordInit]).map(_.asInstanceOf[RecordInit])
                  case _ => if (it.isInstanceOf[RecordInit]) Node.emptySeq :+ it.asInstanceOf[RecordInit] else Node.emptySeq
                }
              }
            }
          }
        }
      }

    }
    // facts = facts.sortBy(_.fields(Id(ISO14971Property.CONTRIB_ID)).asInstanceOf[StringInit].value)
    if (facts.nonEmpty) {
      val tableRows = facts.map { cf =>
        tr(
          id := cf.fields(Id(ISO14971Property.CONTRIB_ID)).asInstanceOf[StringInit].value,
          td(cf.fields(Id(ISO14971Property.CONTRIB_ID)).asInstanceOf[StringInit].value),
          td(cf.fields(Id(ISO14971Property.CONTRIB_DESC)).asInstanceOf[StringInit].value)
        )

      }.toList
      val tab = table(
        cls := "table",
        thead(tr(th(p("ID")), th(p("Description")))),
        tbody(
          for (row <- tableRows)
            yield row
        )
      )
      div(cls := "content is-medium", h3("Contributing Factors"), tab)
    } else {
      div()
    }
  }

  def getISO14971Haz(st: SymbolTable): Frag = {
    var hazards = ilistEmpty[(RecordInit, IMap[ResourceUri, ISet[ResourceUri]])]
    st.components.foreach { compUri =>
      val props = st.componentTable(compUri).componentDecl.properties
      if (props.nonEmpty) {
        val haz = props.filter(p => p.id.value.last.value == ISO14971Property.HAZARDS)
        haz.foreach(h => hazards = hazards :+ (h.value.get.asInstanceOf[RecordInit], h.appliesTo))
      }
    }

    if (hazards.nonEmpty) {
      val tableRows = hazards.sortBy(it => it._1.fields(Id(ISO14971Property.HAZARD_ID)).asInstanceOf[StringInit].value).map { hs =>
        hs._1.fields(Id(ISO14971Property.HAZARD_DESC))
        tr(
          id := hs._1.fields(Id(ISO14971Property.HAZARD_ID)).asInstanceOf[StringInit].value,
          td(hs._1.fields(Id(ISO14971Property.HAZARD_ID)).asInstanceOf[StringInit].value),
          td(hs._1.fields(Id(ISO14971Property.HAZARD_DESC)).asInstanceOf[StringInit].value),
          td(resUri2String(hs._2))
        )
      }

      val tab = table(
        cls := "table", style := "table-layout:fixed;word-wrap:break-word;",
        thead(tr(th(p("ID")), th(p("Description")), th(style := "width: 45%;", p("Applied at")))),
        tbody(
          for (row <- tableRows)
            yield row
        )
      )

      div(cls := "content is-medium", h3("Hazards"), tab)
    } else {
      div()
    }
  }


  def getISO14971Causes(st: SymbolTable, causes: IList[(RecordInit, IMap[ResourceUri, ISet[ResourceUri]])]): Frag = {
    if (causes.nonEmpty) {
      val tableRows = causes.map { c =>
        tr(
          id := c._1.fields(Id(ISO14971Property.CAUSE_ID)).asInstanceOf[StringInit].value,
          td(c._1.fields(Id(ISO14971Property.CAUSE_ID)).asInstanceOf[StringInit].value),
          td(c._1.fields(Id(ISO14971Property.CAUSE_DESC)).asInstanceOf[StringInit].value),
          td(c._1.fields(Id(ISO14971Property.CAUSE_PROBABILITY)).asInstanceOf[StringInit].value),
          td(resUri2String(c._2))
        )
      }
      val tab = table(
        cls := "table", style := "table-layout:fixed;word-wrap:break-word;",
        thead(tr(th(p("ID")), th(p("Description")), th(p("Probability")), th(style := "width: 45%;", p("Applied at")))),
        tbody(
          for (row <- tableRows)
            yield row
        )
      )
      div(cls := "content is-medium", h3("Causes"), tab)
    } else {
      div()
    }
  }

  def getISO14971HazSituations(st: SymbolTable, hs: IMap[ResourceUri, ISet[Property]]): Frag = {

    val prop = hs.flatMap(_._2)

    var hazSitu = ilistEmpty[RecordInit]
    prop.foreach { p =>
      if (p.value.isDefined && p.value.get.isInstanceOf[RecordInit]) {
        val hs = p.value.get.asInstanceOf[RecordInit]
        hazSitu = hazSitu :+ hs
      } else if (p.value.isDefined && p.value.get.isInstanceOf[SeqInit]) {
        p.value.get.asInstanceOf[SeqInit].value.foreach { hsp =>
          val hs = hsp.asInstanceOf[RecordInit]
          hazSitu = hazSitu :+ hs
        }
      }
    }

    hazSitu = hazSitu.sortBy(_.fields(Id(ISO14971Property.HAZ_SITUATION_ID)).asInstanceOf[StringInit].value)

    if (hazSitu.nonEmpty) {
      val tableRows = hazSitu.map { hs =>
        tr(
          id := hs.fields(Id(ISO14971Property.HAZ_SITUATION_ID)).asInstanceOf[StringInit].value,
          td(hs.fields(Id(ISO14971Property.HAZ_SITUATION_ID)).asInstanceOf[StringInit].value),
          td(if (hs.fields.get(Id(ISO14971Property.HAZ_SITUATION_DESC)).isDefined)
            hs.fields(Id(ISO14971Property.HAZ_SITUATION_DESC)).asInstanceOf[StringInit].value else div()),
          td(if (hs.fields.get(Id(ISO14971Property.HAZ_SITUATION_HAZARD)).isDefined) {
            val hazid = hs.fields(Id(ISO14971Property.HAZ_SITUATION_HAZARD)).asInstanceOf[RecordInit].fields(
              Id(ISO14971Property.HAZARD_ID)).asInstanceOf[StringInit].value
            a(href := "#" + hazid, hazid)
          } else div()),
          td(
            if (hs.fields.get(Id(ISO14971Property.HAZ_SITUATION_SEVERITY)).isDefined) {
              hs.fields(Id(ISO14971Property.HAZ_SITUATION_SEVERITY)).asInstanceOf[StringInit].value
            } else if (hs.fields.get(Id(ISO14971Property.HAZ_SITUATION_PATHS)).isDefined) {
              val paths = if (hs.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS)).isInstanceOf[SeqInit]) {
                hs.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS)).asInstanceOf[SeqInit].value
              } else {
                Node.emptySeq :+ hs.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS))
              }
              p(getWorstSeverity(paths.flatMap { p =>
                if (p.isInstanceOf[RecordInit] && p.asInstanceOf[RecordInit].fields.get(Id(ISO14971Property.HAZ_SITUATION_HARM)).isDefined) {
                  Some(p.asInstanceOf[RecordInit].fields(Id(ISO14971Property.HAZ_SITUATION_HARM)).asInstanceOf[RecordInit].fields(Id(ISO14971Property.HARM_SEVERITY)).asInstanceOf[StringInit].value)
                } else {
                  None
                }
              }.toList))
            } else {
              div()
            }),
          td(padding := "0px", if (hs.fields.get(Id(ISO14971Property.HAZ_SITUATION_PATHS)).isDefined) {
            val paths = if (hs.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS)).isInstanceOf[SeqInit]) {
              hs.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS)).asInstanceOf[SeqInit].value
            } else {
              Node.emptySeq :+ hs.fields(Id(ISO14971Property.HAZ_SITUATION_PATHS))
            }
            table(style := "table-layout: auto;width:100%", for (elem <- paths) yield {
              tr(td(style := "width:20%",
                if (elem.isInstanceOf[RecordInit] && elem.asInstanceOf[RecordInit].fields.get(Id(ISO14971Property.HAZ_SITUATION_HARM)).isDefined) {
                  val harmid = elem.asInstanceOf[RecordInit].fields(Id(ISO14971Property.HAZ_SITUATION_HARM)).asInstanceOf[RecordInit].fields(Id(ISO14971Property.HARM_ID)).asInstanceOf[StringInit].value
                  a(href := "#" + harmid, harmid)
                } else {
                  div()
                }
              ), td(style := "width:40%",
                if (elem.isInstanceOf[RecordInit] && elem.asInstanceOf[RecordInit].fields.get(Id(ISO14971Property.HAZ_SITUATION_CF)).isDefined) {
                  if (elem.asInstanceOf[RecordInit].fields(Id(ISO14971Property.HAZ_SITUATION_CF)).isInstanceOf[RecordInit]) {
                    val cfid = elem.asInstanceOf[RecordInit].fields(Id(ISO14971Property.HAZ_SITUATION_CF)).asInstanceOf[RecordInit].fields(Id(ISO14971Property.CONTRIB_ID)).asInstanceOf[StringInit].value
                    a(href := "#" + cfid, cfid)
                  } else if (elem.asInstanceOf[RecordInit].fields(Id(ISO14971Property.HAZ_SITUATION_CF)).isInstanceOf[SeqInit]) {
                    val cfs = elem.asInstanceOf[RecordInit].fields(Id(ISO14971Property.HAZ_SITUATION_CF)).asInstanceOf[SeqInit].value
                    div(for (cf <- cfs) yield {
                      val cfid = cf.asInstanceOf[RecordInit].fields(Id(ISO14971Property.CONTRIB_ID)).asInstanceOf[StringInit].value
                      a(href := "#" + cfid, cfid + " ")
                    })
                  } else {
                    div()
                  }
                } else {
                  div()
                }
              ), td(style := "width:40%;",
                if (elem.isInstanceOf[RecordInit] && elem.asInstanceOf[RecordInit].fields.get(Id(ISO14971Property.HAZ_SITUATION_PROB_TRAN)).isDefined) {
                  elem.asInstanceOf[RecordInit].fields(Id(ISO14971Property.HAZ_SITUATION_PROB_TRAN)).asInstanceOf[StringInit].value
                } else {
                  div()
                }
              ))
            })
          } else div()),
          td(if (hs.fields.get(Id(ISO14971Property.HAZ_SITUATION_RISK)).isDefined)
            hs.fields(Id(ISO14971Property.HAZ_SITUATION_RISK)).asInstanceOf[StringInit].value else div()),
          td(if (hs.fields.get(Id(ISO14971Property.HAZ_SITUATION_PROB)).isDefined)
            hs.fields(Id(ISO14971Property.HAZ_SITUATION_PROB)).asInstanceOf[StringInit].value else div())
        )
      }
      val tab = table(
        cls := "table",
        thead(tr(th(p("ID")), th(p("Description")), th(p("Hazard")), th(p("Severity")),
          th(padding := "0px", table(style := "table-layout:auto;width:100%", th(style := "width:20%", p("Harm")), th(style := "width:40%", p("Contributing Factor")),
            th(style := "width:40%", p("Probability of Transition"))), th(p("Risk")), th(p("Probabilty"))))),
        tbody(
          for (row <- tableRows)
            yield row
        )
      )
      div(cls := "content is-medium", h3("Hazardous Situations"), tab)

    } else {
      div()
    }

  }

  def topDownAnalysis(st: SymbolTable,
                      hazards: IMap[String, IMap[ResourceUri, ISet[ResourceUri]]],
                      causes: IMap[String, IMap[ResourceUri, ISet[ResourceUri]]],
                      haz_HazSitu: IMap[String, ISet[String]],
                      hazSitu_Harms: IMap[String, ISet[String]],
                      hazSitu_CF: IMap[String, ISet[String]]
                     ): Div = {
    var haz_Cause = imapEmpty[String, ISet[String]]

    hazards.foreach { haz =>
      if (haz_HazSitu.keySet.contains(haz._1) && haz._2.nonEmpty) {

        val coll = ps(haz._2, st, false)

        causes.foreach { cau =>
          if (intersectErrors(coll.getPortErrors, cau._2).nonEmpty) {
            haz_Cause = haz_Cause + (haz._1 -> (haz_Cause.getOrElse(haz._1, isetEmpty) + cau._1))
          }
        }
      }
    }

    val tableRows = haz_Cause.map { h_c =>

      val row = render[TableRow](tr(
        td(a(href := "#" + h_c._1, h_c._1)),
        td(for (elem <- haz_HazSitu(h_c._1).flatMap(it => hazSitu_CF.getOrElse(it, isetEmpty)).toList) yield {
          div(a(href := "#" + elem, elem), p(" "))
        }),
        td(for (elem <- h_c._2.toList) yield {
          div(a(href := "#" + elem, elem), p(" "))
        }),
        td(for (elem <- haz_HazSitu(h_c._1).toList) yield {
          div(a(href := "#" + elem, elem), p(" "))
        }),
        td(for (elem <- haz_HazSitu(h_c._1).flatMap(it => hazSitu_Harms.getOrElse(it, isetEmpty)).toList) yield {
          div(a(href := "#" + elem, elem), p(" "))
        })
      ))

      row.onclick = (_: MouseEvent) => {
        //        if(!selected.contains(row)) {
        val src = hazards(h_c._1)
        val snk = haz_Cause(h_c._1).foldLeft(imapEmpty[ResourceUri, ISet[ResourceUri]])((c, n) => unionErrors(c, causes(n)))

        Some(pss(hazards(h_c._1), snk, st, true))
        select(row)
        //        }
      }
      row
    }


    val tab = table(
      cls := "table",
      thead(tr(th(p("Hazards")), th(p("Contributing Factors")), th(p("Initiating Causes")), th(p("Hazardous Situation")), th(p("Harm"))))
    )

    val tab2 = render[Table](tab)
    tableRows.foreach(it => tab2.appendChild(it))

    val res = if (tableRows.nonEmpty) {
      render[Div](div(cls := "content is-medium", h3("Top Down Analysis"), small(span("Click the table rows to view the causal path"))))
    } else {
      render[Div](div())
    }
    if (tableRows.nonEmpty) {
      res.appendChild(tab2)
    }

    res
  }

  def bottomUpAnalysis(st: SymbolTable,
                       hazards: IMap[String, IMap[ResourceUri, ISet[ResourceUri]]],
                       causes: IMap[String, IMap[ResourceUri, ISet[ResourceUri]]],
                       haz_HazSitu: IMap[String, ISet[String]],
                       hazSitu_Harms: IMap[String, ISet[String]],
                       hazSitu_CF: IMap[String, ISet[String]]
                      ): Div = {
    var cause_haz = imapEmpty[String, ISet[String]]

    causes.foreach { cuz =>

      val coll = ErrorReachability(st).forwardErrorSetReach(cuz._2)

      hazards.foreach { haz =>
        if (intersectErrors(coll.getPortErrors, haz._2).nonEmpty) {
          if (haz_HazSitu.keySet.contains(haz._1) && haz._2.nonEmpty) {
            cause_haz = cause_haz + (cuz._1 -> (cause_haz.getOrElse(cuz._1, isetEmpty) + haz._1))
          }
        }
      }
    }

    val tableRows = cause_haz.map { c_h =>

      val row = render[TableRow](tr(
        td(a(href := "#" + c_h._1, c_h._1)),
        td(for (elem <- c_h._2.toList) yield {
          div(a(href := "#" + elem, elem), p(" "))
        }),
        td(for (elem <- c_h._2.flatMap(it => haz_HazSitu(it)).toList) yield {
          div(a(href := "#" + elem, elem), p(" "))
        }),
        td(for (elem <- c_h._2.flatMap(it => haz_HazSitu(it).flatMap(it2 => hazSitu_CF.getOrElse(it2, isetEmpty))).toList) yield {
          div(a(href := "#" + elem, elem), p(" "))
        }),
        td(for (elem <- c_h._2.flatMap(it => haz_HazSitu(it).flatMap(it2 => hazSitu_Harms.getOrElse(it2, isetEmpty))).toList) yield {
          div(a(href := "#" + elem, elem), p(" "))
        })
      ))

      row.onclick = (_: MouseEvent) => {
        //        if(!selected.contains(row)) {
        val src = causes(c_h._1)
        val snk = cause_haz(c_h._1).foldLeft(imapEmpty[ResourceUri, ISet[ResourceUri]])((c, n) => unionErrors(c, hazards(n)))

        Some(pss(snk, causes(c_h._1), st, true))
        select(row)
        //        }


      }
      row
    }


    val tab = table(
      cls := "table",
      thead(tr(th(p("Cause")), th(p("Hazards")), th(p("Hazardous Situation")), th(p("Contributing Factors")), th(p("Harm"))))
    )

    val tab2 = render[Table](tab)
    tableRows.foreach(it => tab2.appendChild(it))

    val res = if (tableRows.nonEmpty) {
      render[Div](div(cls := "content is-medium", h3("Bottom Up Analysis"), small(span("Click the table rows to view the causal path"))))
    } else {
      render[Div](div())
    }
    if (tableRows.nonEmpty) {
      res.appendChild(tab2)
    }

    res
  }


  def getAccident(st: SymbolTable): Frag = {
    var accident = ilistEmpty[RecordInit]
    st.components.foreach { compUri =>
      val props = st.componentTable(compUri).componentDecl.properties
      if (props.nonEmpty) {
        val sh = props.filter(p => p.id.value.last.value == StpaProperty.SYSTEM_HAZARD)

        sh.foreach { s =>
          if (s.value.isDefined) {
            val contrib = s.value.get.asInstanceOf[RecordInit].fields.get(Id(StpaProperty.SYSTEM_HAZARD_CONTRIB))
            if (contrib.isDefined) {
              val acc = contrib.get.asInstanceOf[RecordInit].fields.get(Id(StpaProperty.SYSTEM_HAZARD_ACCIDENT))
              if (acc.isDefined) {
                accident = accident :+ acc.get.asInstanceOf[RecordInit]
              }
            }
          }
        }
      }
    }
    if (accident.nonEmpty) {
      val tableRows = accident.map { acci =>
        tr(
          id := acci.fields(Id(StpaProperty.ACCIDENT_ID)).asInstanceOf[StringInit].value,
          td(acci.fields(Id(StpaProperty.ACCIDENT_ID)).asInstanceOf[StringInit].value),
          td(acci.fields(Id(StpaProperty.ACCIDENT_DESC)).asInstanceOf[StringInit].value),
          td(
            a(
              href := "#" +
                acci
                  .fields(Id(StpaProperty.ACCIDENT_SEVERITY))
                  .asInstanceOf[RecordInit]
                  .fields(Id(StpaProperty.ACCIDENT_LEVEL_ID))
                  .asInstanceOf[StringInit]
                  .value + "_" +
                acci
                  .fields(Id(StpaProperty.ACCIDENT_SEVERITY))
                  .asInstanceOf[RecordInit]
                  .fields(Id(StpaProperty.ACCIDENT_LEVEL_SEC))
                  .asInstanceOf[StringInit]
                  .value,
              acci
                .fields(Id(StpaProperty.ACCIDENT_SEVERITY))
                .asInstanceOf[RecordInit]
                .fields(Id(StpaProperty.ACCIDENT_LEVEL_ID))
                .asInstanceOf[StringInit]
                .value + "_" +
                acci
                  .fields(Id(StpaProperty.ACCIDENT_SEVERITY))
                  .asInstanceOf[RecordInit]
                  .fields(Id(StpaProperty.ACCIDENT_LEVEL_SEC))
                  .asInstanceOf[StringInit]
                  .value
            )
          )
        )
      }
      val tab = table(
        cls := "table",
        thead(tr(th(p("ID")), th(p("Description")), th(p("Severity")))),
        tbody(
          for (row <- tableRows)
            yield row
        )
      )

      div(cls := "content is-medium", h3("Accidents"), tab)
    } else {
      div()
    }
  }

  def getAccidentLevels(st: SymbolTable): Frag = {
    var accidentlevels = ilistEmpty[RecordInit]

    st.components.foreach { compUri =>
      val props = st.componentTable(compUri).componentDecl.properties
      if (props.nonEmpty) {
        val sh = props.filter(p => p.id.value.last.value == StpaProperty.SYSTEM_HAZARD)

        sh.foreach { s =>
          if (s.value.isDefined) {
            val contrib = s.value.get.asInstanceOf[RecordInit].fields.get(Id(StpaProperty.SYSTEM_HAZARD_CONTRIB))
            if (contrib.isDefined) {
              val accident = contrib.get.asInstanceOf[RecordInit].fields.get(Id(StpaProperty.SYSTEM_HAZARD_ACCIDENT))
              if (accident.isDefined) {
                val al = accident.get.asInstanceOf[RecordInit].fields.get(Id(StpaProperty.ACCIDENT_SEVERITY))
                if (al.isDefined) {
                  accidentlevels = accidentlevels :+ al.get.asInstanceOf[RecordInit]
                }
              }
            }
          }
        }
      }
    }

    if (accidentlevels.nonEmpty) {
      val sorted = accidentlevels.sortBy(
        al =>
          al.fields(Id(StpaProperty.ACCIDENT_LEVEL_LEVEL))
            .asInstanceOf[RecordInit]
            .fields(Id(Aadl2Awas.UNIT_PROP_VAL))
            .asInstanceOf[StringInit]
            .value
            .toInt
      )
      val tableRows = sorted.map { al =>
        tr(
          id := al.fields(Id(StpaProperty.ACCIDENT_LEVEL_ID)).asInstanceOf[StringInit].value +
            "_" + al.fields(Id(StpaProperty.ACCIDENT_LEVEL_SEC)).asInstanceOf[StringInit].value,
          td(al.fields(Id(StpaProperty.ACCIDENT_LEVEL_ID)).asInstanceOf[StringInit].value),
          td(al.fields(Id(StpaProperty.ACCIDENT_LEVEL_SEC)).asInstanceOf[StringInit].value),
          td(
            al.fields(Id(StpaProperty.ACCIDENT_LEVEL_LEVEL))
              .asInstanceOf[RecordInit]
              .fields(Id(Aadl2Awas.UNIT_PROP_VAL))
              .asInstanceOf[StringInit]
              .value
          ),
          td(al.fields(Id(StpaProperty.ACCIDENT_LEVEL_DESC)).asInstanceOf[StringInit].value)
        )
      }

      val tab = table(
        cls := "table",
        thead(tr(th(p("ID")), th(p("Sub-Section")), th(p("Level")), th(p("Potential Consequence")))),
        tbody(
          for (row <- tableRows)
            yield row
        )
      )

      div(cls := "content is-medium", h3("Accident Levels"), tab)
    } else {
      div()
    }
  }

  def getSystemConstraints(st: SymbolTable): Frag = {
    var safetyConstraints = ilistEmpty[RecordInit]
    st.components.foreach { compUri =>
      val props = st.componentTable(compUri).componentDecl.properties
      if (props.nonEmpty) {
        val sh = props.filter(p => p.id.value.last.value == StpaProperty.SYSTEM_HAZARD)
        sh.foreach { s =>
          if (s.value.isDefined) {
            val constraint = s.value.get.asInstanceOf[RecordInit].fields.get(Id(StpaProperty.SYSTEM_HAZARD_CONSTRAINT))
            if (constraint.isDefined) {
              safetyConstraints = safetyConstraints :+ constraint.get.asInstanceOf[RecordInit]
            }
          }
        }
      }
    }

    if (safetyConstraints.nonEmpty) {
      val tableRows = safetyConstraints.map { sc =>
        val scid = sc.fields(Id(StpaProperty.SAFETY_CONSTRAINT_ID)).asInstanceOf[StringInit].value
        tr(
          id := scid,
          td(scid),
          td(sc.fields(Id(StpaProperty.SAFETY_CONSTRAINT_CONSTRAINT)).asInstanceOf[StringInit].value)
        )
      }
      val tab = table(
        cls := "table",
        thead(tr(th(p("ID")), th(p("Constraint")))),
        tbody(
          for (row <- tableRows)
            yield row
        )
      )
      div(cls := "content is-medium", h3("Safety Constraints"), tab)
    } else {
      div()
    }
  }

  def getEnvCond(st: SymbolTable): Frag = {
    var envCond = ilistEmpty[RecordInit]
    st.components.foreach { compUri =>
      val props = st.componentTable(compUri).componentDecl.properties
      if (props.nonEmpty) {
        val sh = props.filter(p => p.id.value.last.value == StpaProperty.SYSTEM_HAZARD)

        sh.foreach { s =>
          if (s.value.isDefined) {
            val contrib = s.value.get.asInstanceOf[RecordInit].fields.get(Id(StpaProperty.SYSTEM_HAZARD_CONTRIB))
            if (contrib.isDefined) {
              val env = contrib.get.asInstanceOf[RecordInit].fields.get(Id(StpaProperty.SYSTEM_HAZARD_ENV))
              if (env.isDefined) {
                envCond = envCond :+ env.get.asInstanceOf[RecordInit]
              }
            }
          }
        }
      }
    }

    if (envCond.nonEmpty) {
      val tableRows = envCond.map { ev =>
        val scid = ev.fields(Id(StpaProperty.ENV_COND_ID)).asInstanceOf[StringInit].value
        tr(id := scid, td(scid), td(ev.fields(Id(StpaProperty.ENV_COND_DESC)).asInstanceOf[StringInit].value))
      }
      val tab = table(
        cls := "table",
        thead(tr(th(p("ID")), th(p("Constraint")))),
        tbody(
          for (row <- tableRows)
            yield row
        )
      )
      div(cls := "content is-medium", h3("Environmental Conditions"), tab)
    } else {
      div()
    }
  }

  def getAllHaz(st: SymbolTable) : Frag = {
    var hazProp = ilistEmpty[Property]

    st.components.foreach { compUri =>
      val props = st.componentTable(compUri).componentDecl.properties
      if (props.nonEmpty) {
        val sh = props.filter(p => p.id.value.last.value == StpaProperty.HAZARDS)
        sh.foreach {haz =>
          hazProp = hazProp :+ haz
        }
      }
    }

    val hazards = hazProp.map(_.value.get.asInstanceOf[RecordInit])


    if(hazards.nonEmpty) {
      val tableRows = hazards.sortBy(_.fields(Id(StpaProperty.HAZARD_ID)).asInstanceOf[StringInit].value).map{ hs =>
        val vcs = ilistEmpty[String]
        val violatingConst = hs.fields.get(Id(StpaProperty.HAZARD_VC))
        if(violatingConst.nonEmpty) {
          val vc = violatingConst.get.asInstanceOf[RecordInit]
          vc.fields(Id(StpaProperty.SAFETY_CONSTRAINT_ID))
        }
        tr(
          id := hs.fields(Id(StpaProperty.HAZARD_ID)).asInstanceOf[StringInit].value,
          td(hs.fields(Id(StpaProperty.HAZARD_ID)).asInstanceOf[StringInit].value),
          td(hs.fields(Id(StpaProperty.HAZARD_DESC)).asInstanceOf[StringInit].value)

        )

      }

      val tab = table(
        cls := "table",
        thead(tr(th(p("ID")), th(p("Description")))),
        tbody(
          for (row <- tableRows)
            yield row
        )
      )

      div(cls := "content is-medium", h3("Hazards"), tab)
    } else {
      div()
    }
  }


  def getSystemHaz(st: SymbolTable): Frag = {
    var systemHaz = ilistEmpty[RecordInit]
    var envCond = ilistEmpty[RecordInit]
    st.components.foreach { compUri =>
      val props = st.componentTable(compUri).componentDecl.properties
      if (props.nonEmpty) {
        val sh = props.filter(p => p.id.value.last.value == StpaProperty.SYSTEM_HAZARD)

        sh.foreach { s =>
          if (s.value.isDefined) {
            systemHaz = systemHaz :+ s.value.get.asInstanceOf[RecordInit]
          }
        }
      }
    }

    if (systemHaz.nonEmpty) {
      val tableRows = systemHaz.map { sh =>
        val shid = sh.fields(Id(StpaProperty.SYSTEM_HAZARD_ID)).asInstanceOf[StringInit].value
        val env = sh
          .fields(Id(StpaProperty.SYSTEM_HAZARD_CONTRIB))
          .asInstanceOf[RecordInit]
          .fields(Id(StpaProperty.SYSTEM_HAZARD_ENV))
          .asInstanceOf[RecordInit]
          .fields(Id(StpaProperty.ENV_COND_ID))
          .asInstanceOf[StringInit]
          .value
        val acc = sh
          .fields(Id(StpaProperty.SYSTEM_HAZARD_CONTRIB))
          .asInstanceOf[RecordInit]
          .fields(Id(StpaProperty.SYSTEM_HAZARD_ACCIDENT))
          .asInstanceOf[RecordInit]
          .fields(Id(StpaProperty.ACCIDENT_ID))
          .asInstanceOf[StringInit]
          .value
        val vc = sh.fields(Id(StpaProperty.SYSTEM_HAZARD_CONSTRAINT)).asInstanceOf[RecordInit].fields.map { constr =>
          val v = constr._2.asInstanceOf[StringInit].value
          td(a(href := "#" + v, v))
        }

        tr(
          id := shid,
          td(shid),
          td(sh.fields(Id(StpaProperty.SYSTEM_HAZARD_DESC)).asInstanceOf[StringInit].value),
          td(
            table(
              cls := "table",
              tbody(
                for (v <- vc.toList)
                  yield tr(v)
              )
            )
          ),
          td(a(href := "#" + env, env)),
          td(a(href := "#" + acc, acc))
        )
      }
      val tab = table(
        cls := "table",
        thead(
          tr(
            th(p("ID")),
            th(p("Description")),
            th(p("Violating Constraints")),
            th(p("Environment Condition")),
            th(p("Accident"))
          )
        ),
        tbody(
          for (row <- tableRows)
            yield row
        )
      )
      div(cls := "content is-medium", h3("System Hazards"), tab)

    } else {
      div()
    }

  }

  def getControlStructure(st: SymbolTable): Frag = {
    var controlStructs = imapEmpty[RecordInit, ISet[RecordInit]]
    st.components.foreach { compUri =>
      val props = st.componentTable(compUri).componentDecl.properties
      if (props.nonEmpty) {
        val sh = props.filter(p => p.id.value.last.value == StpaProperty.SYSTEM_HAZARD)

        sh.foreach { s =>
          if (s.value.isDefined) {
            val cl = s.value.get.asInstanceOf[RecordInit].fields.get(Id(StpaProperty.SYSTEM_HAZARD_CTRL))
            //            controlStructs = controlStructs.+(cl.get -> isetEmpty[RecordInit])
          }
        }
      }
    }

    st.components.foreach { compUri =>
      val props = st.componentTable(compUri).componentDecl.properties
      if (props.nonEmpty) {
        val compRoles = props.filter(p => p.id.value.last.value == StpaProperty.COMPONENT_ROLES)
        compRoles.foreach { cr =>
          if (cr.value.isDefined) {

          }
        }

      }
    }

  }

  def ps(haz: IMap[ResourceUri, ISet[ResourceUri]], st: SymbolTable, high: Boolean): Collector = {
    //    clearAll(Main.selections.keySet)
    val res = ErrorReachability(st).backwardErrorSetReach(haz) //StateReachAnalysis.getReachability(criteria, st)
    //    if(high) {
    //      highlight(collectorToUris(res), "#78c0a8")
    //    }
    res
  }


  def pss(haz: IMap[ResourceUri, ISet[ResourceUri]], cause: IMap[ResourceUri, ISet[ResourceUri]], st: SymbolTable, high: Boolean): Collector = {
    clearAll(Main.selections.keySet)
    gl.get.eventHub.emit("clearall")
    val res = ErrorReachability(st).backwardErrorSetReach(haz).intersect(ErrorReachability(st).forwardErrorSetReach(cause)) //StateReachAnalysis.getReachability(criteria, st)
    if (high) {
      gl.get.eventHub.emit("highlight", collectorToUris(res).asInstanceOf[scala.scalajs.js.Object], "#78c0a8".asInstanceOf[scala.scalajs.js.Object])
      highlight(collectorToUris(res), "#78c0a8")
    }
    res
  }

  def getCausalScenario(st: SymbolTable, cs: String): Div = {
    val H = SymbolTableHelper
    var systemHaz = imapEmpty[RecordInit, IMap[ResourceUri, ISet[ResourceUri]]]
    var envCond = ilistEmpty[RecordInit]
    st.components.foreach { compUri =>
      val props = st.componentTable(compUri).componentDecl.properties
      if (props.nonEmpty) {
        val sh = props.filter(p => p.id.value.last.value == StpaProperty.SYSTEM_HAZARD)
        sh.foreach { s =>
          if (s.value.isDefined) {
            val rlist = s.appliesTo.toList.flatMap { s =>
              val uri = H.findUri(s._1.split('.').toVector, st)
              var res = ilistEmpty[(ResourceUri, ISet[ResourceUri])]
              if (uri.isDefined && H.isPort(uri.get) && s._2.nonEmpty) {
                res = res :+ (uri.get -> s._2.flatMap(it => H.findUri(it.split('.').toVector, st)))
              } else if (uri.isDefined && H.isPort(uri.get)) {
                assert(H.findComponentUri(uri.get, st).isDefined)
                res = res :+ (uri.get -> st.componentTable(H.findComponentUri(uri.get, st).get).propagation(uri.get))
              } else if (uri.isDefined && H.isFlow(uri.get)) {
                assert(H.findComponentUri(uri.get, st).isDefined)
                //
                val cst = st.componentTable(H.findComponentUri(uri.get, st).get)
                val ft = cst.flow(uri.get)
                if (ft.fromPortUri.isDefined) {
                  res = res :+ (ft.fromPortUri.get -> ft.fromFaults)
                }
                if (ft.toPortUri.isDefined) {
                  res = res :+ (ft.toPortUri.get -> ft.toFaults)
                }
              } else {
                assert(false, "handle other component elements such as behavior transitions and states")
              }
              res
            }
            systemHaz = systemHaz + (s.value.get.asInstanceOf[RecordInit] -> rlist.toMap)
          }
        }
      }
    }

//    systemHaz.map { sh =>
//
//    }

    if (systemHaz.nonEmpty) {
      var resColl: Option[Collector] = None
      val tableRows = systemHaz.map { sh =>
        val shid = sh._1.fields(Id(StpaProperty.SYSTEM_HAZARD_ID)).asInstanceOf[StringInit].value
        val an = a(id := "shid", shid)
        val ran = render[Anchor](an)
        ran.onclick = (_: MouseEvent) => {
          Some(ps(sh._2, st, true))
        }
        resColl = Some(StateReachAnalysis.getReachability(sh._2, st))
        val comps = resColl.get.getNodes.filter(_.getResourceType == NodeType.COMPONENT)
        var hazards = ilistEmpty[Property]
        comps.foreach {comUri =>
          val props = st.componentTable(comUri.getUri).componentDecl.properties
          if (props.nonEmpty) {
            val sh = props.filter(p => p.id.value.last.value == StpaProperty.HAZARDS)
            sh.foreach {haz =>
              hazards = hazards :+ haz
            }
          }
        }

        val causalHaz = hazards.flatMap{hs =>
          val hid = hs.value.get.asInstanceOf[RecordInit].fields(Id(StpaProperty.HAZARD_ID)).asInstanceOf[StringInit].value
          val x = hs.appliesTo.toList.flatMap{hap =>
            val uri = H.findUri(hap._1.split('.').toVector, st)
            var res = ilistEmpty[(ResourceUri, ISet[ResourceUri])]
            if (uri.isDefined && H.isPort(uri.get) && hap._2.nonEmpty) {
              val pe =  uri.get -> hap._2.flatMap(it => H.findUri(it.split('.').toVector, st))
              if(resColl.get.getPortErrors.contains(pe._1) &&
                resColl.get.getPortErrors(pe._1).intersect(pe._2).nonEmpty) {
                res = res :+ pe
              }
            }
            res
          }
          if(x.nonEmpty) {
            Some((hid, x))
          } else {
            None
          }
        }

        val td1 = render[TableCell](td())
        td1.appendChild(ran)



        val tr1 = render[TableRow](tr())
        tr1.appendChild(td1)

        val td2 = render[TableCell](td())
        val anchs = causalHaz.map{cz =>
          render[Anchor](a(href := "#" + cz._1, cz._1))
        }.sortBy(_.id)

        td2.appendChild(anchs.head)
        anchs.tail.foldLeft(td2){(c, v) =>
          c.appendChild(render(span(", ")))
          c.appendChild(v)
          c
        }

        tr1.appendChild(td2)

        tr1
      }

      val comps = resColl.get.getNodes.filter(_.getResourceType == NodeType.COMPONENT)

      val tab = render[Table](table(cls := "table", thead(tr(
        th(p("ID")),
        th(p("Contributing Hazards")) //, for (c <- comps) th(p(H.uri2IdString(c.getUri)))
      ))))
      for (r <- tableRows) {
        tab.appendChild(r)
      }

      val temp = render[Div](div(cls := "content is-medium", h4("Hazardous Situations")))
      temp.appendChild(tab)
      temp
    } else {
      render[Div](div())
    }

  }

  def mineSystemHazards(st: SymbolTable): Frag = {
    ???
  }


  private def getAllHazSituations(st: SymbolTable): IMap[ResourceUri, ISet[Property]] = {
    var res = imapEmpty[ResourceUri, ISet[Property]]
    st.components.foreach { compUri =>
      val props = st.componentTable(compUri).componentDecl.properties
      if (props.nonEmpty) {
        res = res + (compUri -> props.filter(p => p.id.value.last.value == ISO14971Property.HAZ_SITUATIONS).toSet)
      }
    }
    res
  }

  private def getAllISO14971Hazards(st: SymbolTable)
  : IMap[String, (RecordInit, IMap[ResourceUri, ISet[ResourceUri]])] = {
    var res = imapEmpty[String, (RecordInit, IMap[ResourceUri, ISet[ResourceUri]])]
    st.components.foreach { compUri =>
      val props = st.componentTable(compUri).componentDecl.properties.filter(p => p.id.value.last.value == ISO14971Property.HAZARDS)

      if (props.nonEmpty) {
        props.foreach { prop =>
          val haz = prop.value.get.asInstanceOf[RecordInit]
          val hid = haz.fields(Id(ISO14971Property.HAZARD_ID)).asInstanceOf[StringInit].value
          val errors = prop.appliesTo.toList.flatMap { hap =>
            val uri = H.findUri(hap._1.split('.').toVector, st)
            if (uri.isDefined) {
              Some((uri.get, hap._2.flatMap(it => H.findUri(it.split('.').toVector, st))))
            } else {
              None
            }
          }.toMap
          if (res.keySet.contains(hid)) {
            assert(false, hid + " : Hazard already defined")
            println(hid + " : Hazard already defined")
          } else {
            res = res + (hid -> (haz, errors))
          }
        }
      }
    }
    res

  }

  private def getAllISO14971Causes(st: SymbolTable)
  : IMap[String, (RecordInit, IMap[ResourceUri, ISet[ResourceUri]])] = {
    var res = imapEmpty[String, (RecordInit, IMap[ResourceUri, ISet[ResourceUri]])]
    st.components.foreach { compUri =>
      val props = st.componentTable(compUri).componentDecl.properties.filter(p => p.id.value.last.value == ISO14971Property.CAUSES)

      if (props.nonEmpty) {
        props.foreach { prop =>
          val haz = prop.value.get.asInstanceOf[RecordInit]
          val hid = haz.fields(Id(ISO14971Property.CAUSE_ID)).asInstanceOf[StringInit].value
          val errors = prop.appliesTo.toList.flatMap { hap =>
            val uri = H.findUri(hap._1.split('.').toVector, st)
            if (uri.isDefined) {
              Some((uri.get, hap._2.flatMap(it => H.findUri(it.split('.').toVector, st))))
            } else {
              None
            }
          }.toMap
          if (res.keySet.contains(hid)) {
            res = res + (hid -> (haz, unionErrors(res(hid)._2, errors)))
          } else {
            res = res + (hid -> (haz, errors))
          }
        }
      }
    }
    res
  }

  private def intersectErrors(op1: IMap[ResourceUri, ISet[ResourceUri]],
                              op2: IMap[ResourceUri, ISet[ResourceUri]]):
  IMap[ResourceUri, Set[ResourceUri]] = {
    var result = imapEmpty[ResourceUri, ISet[ResourceUri]]
    val ports = op1.keySet.intersect(op2.keySet)
    ports.foreach { p =>
      result = result + ((p, op1(p).intersect(op2(p))))
    }
    result.filter(_._2.nonEmpty)
  }

  private def unionErrors(op1: IMap[ResourceUri, ISet[ResourceUri]],
                          op2: IMap[ResourceUri, ISet[ResourceUri]]):
  IMap[ResourceUri, Set[ResourceUri]] = {
    var result = imapEmpty[ResourceUri, ISet[ResourceUri]]
    val ports = op1.keySet ++ op2.keySet
    ports.foreach { p =>
      result = result + ((p, op1.getOrElse(p, isetEmpty[ResourceUri]) ++
        op2.getOrElse(p, isetEmpty[ResourceUri])))
    }
    result
  }

  private def getWorstSeverity(severities: IList[String]): String = {
    var worst = severities.head
    severities.foreach { s =>
      if (severity(s) < severity(worst)) {
        worst = s
      }
    }
    worst
  }

  private def resUri2String(uri: IMap[ResourceUri, ISet[ResourceUri]]): String = {
    var res = ilistEmpty[String]
    uri.foreach { pe =>
      res = res :+ (if (H.uri2CanonicalName(pe._1) == "") pe._1 else H.uri2CanonicalName(pe._1)) + (if (pe._2.nonEmpty) {
        "{" + pe._2.map(it => if (H.uri2CanonicalName(it) == "") it else H.uri2CanonicalName(it)).mkString(", ") + "}"
      } else "")
    }
    res.mkString(" ")
  }



}
