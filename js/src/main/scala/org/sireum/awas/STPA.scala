package org.sireum.awas

import facades.{GraphQuery, Options, SVGPanZoom}
import org.scalajs.dom.{Event, document}
import org.scalajs.dom.html.{Anchor, Div, Table, TableCell, TableRow}
import org.scalajs.dom.raw.MouseEvent
import org.sireum.awas.Main.{H, clearAll, collectorToUris, highlight, st}
import org.sireum.awas.analysis.StateReachAnalysis
import org.sireum.awas.ast.{AwasSerializer, Id, Property, RecordInit, StringInit}
import org.sireum.awas.collector.Collector
import org.sireum.awas.fptc.{FlowGraph, NodeType}
import org.sireum.awas.reachability.{ErrorReachabilityImpl, PortReachabilityImpl}
import org.sireum.awas.report.StpaProperty
import org.sireum.awas.slang.Aadl2Awas
import org.sireum.awas.symbol.{SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.common.JSutil.{render, _}
import org.sireum.util.ConsoleTagReporter
import scalatags.Text.all.{td, _}
import org.sireum.util._

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("STPA")
object STPA {

  @JSExport
  def main(): Unit = {
    var model = if (GraphQuery.json.isDefined) {
      Aadl2Awas.apply(GraphQuery.json.get)
    } else if (GraphQuery.awas.isDefined) {
      AwasSerializer.unapply(GraphQuery.awas.get)
    } else {
      None
    }

    if (model.isDefined) {
      println("testing1")
      val reporter = new ConsoleTagReporter()
      st = Some(SymbolTable(model.get)(reporter))

      val systemGraph = FlowGraph(model.get, st.get, includeBindingEdges = false)

      val mainDiv = render[Div](Views.stpaMain())
      val body = mainDiv.querySelector("#body")
      println(body.innerHTML)
      body.appendChild(render[Div](getSystem(st.get)))
      body.appendChild(render[Div](getAccidentLevels(st.get)))
      body.appendChild(render[Div](getAccident(st.get)))
      body.appendChild(render[Div](getSystemConstraints(st.get)))
      body.appendChild(render[Div](getEnvCond(st.get)))
      body.appendChild(render[Div](getSystemHaz(st.get)))

      val asvg = Util.graph2Svg(systemGraph.getUri, SettingsView.currentConfig, st.get)
      val svgDiv = render[Div](div(height := "100%", div(cls := "tempSvg")))
      svgDiv.replaceChild(asvg, svgDiv.querySelector(".tempSvg"))
      new SVGPanZoom(asvg, Options(svgDiv))
      body.appendChild(svgDiv)
      body.appendChild(getCausalScenario(st.get, ""))




      document.onreadystatechange = (_: Event) => {
        document.body.appendChild(mainDiv)
      }
    }
  }

  def getSystem(st: SymbolTable): Frag = {
    val systemProps = st.componentTable(st.system).componentDecl.properties
    val sysProp = systemProps.find(prop => prop.id.value == StpaProperty.SYSTEM_PROP)
    println(sysProp)
    if (sysProp.isDefined && sysProp.get.value.isDefined) {
      val sysVal = sysProp.get.value.get.asInstanceOf[RecordInit]
      // we know system property val is a record type
      val sysName = sysVal.fields.get(Id(StpaProperty.SYSTEM_PROP_NAME))
      val sysDesc = sysVal.fields.get(Id(StpaProperty.SYSTEM_PROP_DESC))
      println("testing2")

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

  def getAccident(st: SymbolTable): Frag = {
    var accident = ilistEmpty[RecordInit]
    st.components.foreach { compUri =>
      val props = st.componentTable(compUri).componentDecl.properties
      if (props.nonEmpty) {
        val sh = props.filter(p => p.id.value == StpaProperty.SYSTEM_HAZARD)

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
        val sh = props.filter(p => p.id.value == StpaProperty.SYSTEM_HAZARD)

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
        val sh = props.filter(p => p.id.value == StpaProperty.SYSTEM_HAZARD)
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
        val sh = props.filter(p => p.id.value == StpaProperty.SYSTEM_HAZARD)

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

  def getSystemHaz(st: SymbolTable): Frag = {
    var systemHaz = ilistEmpty[RecordInit]
    var envCond = ilistEmpty[RecordInit]
    st.components.foreach { compUri =>
      val props = st.componentTable(compUri).componentDecl.properties
      if (props.nonEmpty) {
        val sh = props.filter(p => p.id.value == StpaProperty.SYSTEM_HAZARD)

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
        val sh = props.filter(p => p.id.value == StpaProperty.SYSTEM_HAZARD)

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
        val compRoles = props.filter(p => p.id.value == StpaProperty.COMPONENT_ROLES)
        compRoles.foreach { cr =>
          if (cr.value.isDefined) {

          }
        }

      }
    }

  }

  def pss(criteria: IMap[ResourceUri, ISet[ResourceUri]], st: SymbolTable): Collector = {
    clearAll(Main.selections.keySet)
    val res = StateReachAnalysis.getReachability(criteria, st)
    highlight(collectorToUris(res), "#78c0a8")
    res
  }

  def getCausalScenario(st: SymbolTable, cs: String): Div = {
    val H = SymbolTableHelper
    var systemHaz = imapEmpty[RecordInit, IMap[ResourceUri, ISet[ResourceUri]]]
    var envCond = ilistEmpty[RecordInit]
    st.components.foreach { compUri =>
      val props = st.componentTable(compUri).componentDecl.properties
      if (props.nonEmpty) {
        val sh = props.filter(p => p.id.value == StpaProperty.SYSTEM_HAZARD)
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
                println(H.findComponentUri(uri.get, st).get)
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
          resColl = Some(pss(sh._2, st))
        }

        val td1 = render[TableCell](td())
        td1.appendChild(ran)
        val tr1 = render[TableRow](tr())
        tr1.appendChild(td1)
        tr1
      }

      val comps = resColl.get.getNodes.filter(_.getResourceType == NodeType.COMPONENT)

      val tab = render[Table](table(cls := "table", thead(tr(
        th(p("ID")),
        th(p("Contributing Hazards")), for (c <- comps) th(p(H.uri2IdString(c.getUri)))
      ))))
      for (r <- tableRows) {
        tab.appendChild(r)
      }

      val temp = render[Div](div(cls := "content is-medium", h4("System Hazards")))
      temp.appendChild(tab)
      temp
    } else {
      render[Div](div())
    }

  }

  def mineSystemHazards(st: SymbolTable): Frag = {
    ???
  }
}
