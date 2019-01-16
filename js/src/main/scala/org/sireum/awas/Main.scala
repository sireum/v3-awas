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

import facades._
import org.scalajs.dom
import org.scalajs.dom.ext._
import org.scalajs.dom.html.{Anchor, Div, Input, Label, Table}
import org.scalajs.dom.raw.Node
import org.scalajs.dom.svg.SVG
import org.scalajs.dom.{raw => _, _}
import org.scalajs.jquery.jQuery
import org.sireum.awas.Notification.Kind
import org.sireum.awas.analysis.FaultImpactAnalysis
import org.sireum.awas.ast.AwasSerializer
import org.sireum.awas.collector.{Collector, ResultType}
import org.sireum.awas.fptc._
import org.sireum.awas.reachability.{ErrorReachabilityImpl, PortReachabilityImpl}
import org.sireum.awas.slang.Aadl2Awas
import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.awas.witness.{RankDir, SvgGenConfig, SvgGenerator}
import org.sireum.common.JSutil.{render, _}
import org.sireum.message.Message
import org.sireum.ops.ISZOps
import org.sireum.util.{imapEmpty, _}
import scalatags.Text
import scalatags.Text.all.{div, id, label, _}
import scalatags.Text.tags2.nav

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.scalajs.js.timers.SetTimeoutHandle

@JSExportTopLevel("Main")
object Main {
  val H = SymbolTableHelper

  var uriNodeMap: IMap[String, ISet[SvgNode]] = imapEmpty[String, ISet[SvgNode]]
  var selections: IMap[String, (Boolean, String)] = imapEmpty[String, (Boolean, String)]
  var selectedUris: ISet[ResourceUri] = isetEmpty[ResourceUri]
  var clickCounter = 0
  var timer: Option[SetTimeoutHandle] = None
  var gl: Option[GoldenLayout] = None
  var qI: Option[QueryInter] = None
  var st: Option[SymbolTable] = None
  var terminal: Option[Terminal] = None
  var cliRegistered = false

  def uriToBreadCrumbs(uri: ResourceUri, st: SymbolTable): Frag = {
    val res = Resource.getDefResource(uri)
    if (res.isDefined) {
      val paths = (res.get.uriPaths :+ res.get.uri).tail.tail
      val ancestors = H.getAllAncestors(uri, st)
      div(
        cls := "container is-fluid ",
        nav(
          cls := "level",
          div(
            cls := "level-left",
            div(
              cls := "level-item", //tag("section")(cls:="section", //div(cls := "container",
              nav(
                cls := "breadcrumb has-arrow-separator is-medium",
                attr("aria-label") := "breadcrumbs",
                ul(for (path <- paths) yield {
                  li(a(href := "#", onclick := "openGraphTab(\"" + ancestors(paths.indexOf(path)) + "\")", path))
                })
              )
            )
          ),
          div(
            cls := "level-right",
            div(
              cls := "level-item",
              div(
                cls := "control",
                label(cls := "radio", input(`type` := "radio", name := uri, id := "td", " Top-Down ")),
                label(cls := "radio", input(`type` := "radio", name := uri, id := "lr", " Left-Right "))
              )
            )
          )
        )
      )
      //)//)
    } else div()
  }

  @JSExportTopLevel("openGraphTab")
  def openGraphTab(uri: ResourceUri): Boolean = {
    if (gl.isDefined) {
      if (gl.get.root.getItemsById(uri).nonEmpty) {
        val ci = gl.get.root.getItemsById(uri).head
        val stacks = gl.get.root.getItemsByType("stack")
        val si = stacks.find(si => si.contentItems.contains(ci))
        if (si.isDefined) si.get.setActiveContentItem(ci)

        //gl.get.root.contentItems(0).setActiveContentItem(ci)
      } else {
        gl.get.root.contentItems(0).addChild(Views.childConfig(uri.split(H.ID_SEPARATOR).last, uri))
        val temp = selections

        SvgPanZoom("svg").svgPanZoom(js.Dictionary())

      }
    }
    false
  }

  //def openChildGraph()

  @JSExport
  def main(): Unit = {
    //var model = Aadl2Awas.apply(GraphQuery.json)

    var model = if (GraphQuery.json.isDefined) {
      Aadl2Awas.apply(GraphQuery.json.get)
    } else if (GraphQuery.awas.isDefined) {
      AwasSerializer.unapply(GraphQuery.awas.get)
    } else {
      None
    }

    //if(model.isEmpty) {
    // var model = AwasSerializer.unapply(GraphQuery.awas)
    //}
    val xx = GraphQuery.queries

    if (model.isDefined) {
      val reporter = new ConsoleTagReporter()
      st = Some(SymbolTable(model.get)(reporter))

      val systemGraph = FlowGraph(model.get, st.get)

      //val systemSvg = graph2Svg(systemGraph)

      //val subsvgs = subgraphs.map(x => (x._1, graph2Svg(x._2)))

      val mainDiv = render[Div](Views.mainPage())

      val config = Views.getInitLayout(st.get.systemDecl.compName.value, st.get.system)

      val mainBox: Element = mainDiv.querySelector("#main-container")

      val body = mainDiv.querySelector("#body")
      val settingsView = render[Div](Views.quickView())
      SettingsView.preProcess(settingsView)
      body.insertBefore(settingsView, mainBox)

      gl = Some(new GoldenLayout(config, jQuery(mainBox)))

      var res = imapEmpty[Node, String]

      document.onreadystatechange = (_: Event) => {
        document.body.appendChild(mainDiv)
        if (document.readyState == "complete" && gl.isDefined) {
          val systemName = st.get.systemDecl.compName.value

          gl.get.registerComponent("system", { (container: Container, componentState: js.Dictionary[scalajs.js.Any]) => {
            if (componentState.get("graph").isDefined) {
              val uri = componentState.get("graph").get.asInstanceOf[ResourceUri]
              //val temp = componentState("graph").asInstanceOf[Node]
              //println(temp)
              val breadCrumbs = render[Div](uriToBreadCrumbs(uri, st.get))
              $[Input](breadCrumbs, "#td").onclick = (_: MouseEvent) => toTopDown(uri)
              //              {
            //                println(componentState.get("isTD").get)
            //                if (componentState.get("isTD").isDefined &&
            //                  !componentState.get("isTD").get.asInstanceOf[Boolean]) {
            //                  //                  $[Input](breadCrumbs, ".td").checked = true
            //                  container.getElement().children("svg").first().replaceWith(
            //                    Util.graph2Svg(uri, SvgGenConfig(RankDir.TB,
            //                      SettingsView.currentConfig.simpleConn,
            //                      SettingsView.currentConfig.viewVirtualPorts, SettingsView.currentConfig.viewErrors,
            //                      SettingsView.currentConfig.viewFlows, SettingsView.currentConfig.bindings), st.get))
            //                  SvgPanZoom("svg").svgPanZoom(js.Dictionary())
            //                  componentState.update("isTD", true)
            //                }
            //              }
              $[Input](breadCrumbs, "#lr").onclick = (_: MouseEvent) => toLeftRight(uri)
            //              {
            //                if (componentState.get("isTD").isDefined &&
            //                  componentState.get("isTD").get.asInstanceOf[Boolean]) {
            //                  //                  $[Input](breadCrumbs, ".lr").checked = true
            //                  container.getElement().children("svg").first().replaceWith(
            //                    Util.graph2Svg(uri, SvgGenConfig(RankDir.LR,
            //                      SettingsView.currentConfig.simpleConn,
            //                      SettingsView.currentConfig.viewVirtualPorts, SettingsView.currentConfig.viewErrors,
            //                      SettingsView.currentConfig.viewFlows, SettingsView.currentConfig.bindings), st.get))
            //                  SvgPanZoom("svg").svgPanZoom(js.Dictionary())
            //                  componentState.update("isTD", false)
            //                }
            //              }

              if (SettingsView.currentConfig.rankDir == RankDir.TB) {
                $[Input](breadCrumbs, "#td").checked = true
                $[Input](breadCrumbs, "#lr").checked = false
              } else {
                $[Input](breadCrumbs, "#lr").checked = true
                $[Input](breadCrumbs, "#td").checked = false
              }

              val asvg = Util.graph2Svg(uri, SettingsView.currentConfig, st.get)

              container.getElement().append(breadCrumbs).append(asvg)
              SvgPanZoom("svg").svgPanZoom(js.Dictionary())

              }
            container
          }

          }: js.Function)
          gl.get.init()

          SvgPanZoom("svg").svgPanZoom(js.Dictionary())
          // gl.root.contentItems(0).addChild(childConfig)

          gl.get.root.setTitle(systemName)

          computeHeight(gl.get)

          val queryButton = mainDiv.querySelector("#query-button")
          val clearButton = mainDiv.querySelector("#clear-button")
          val forwardButton = mainDiv.querySelector("#forward-button")
          val backwardButton = mainDiv.querySelector("#backward-button")
          queryButton.asInstanceOf[Anchor].onclick = (_: MouseEvent) => {
            openQueryCli(st.get, systemGraph)
          }

          clearButton.asInstanceOf[Anchor].onclick = (_: MouseEvent) => {
            clearAll(selections.keySet)
          }

          forwardButton.asInstanceOf[Anchor].onclick = (_: MouseEvent) => {
            forwardButtonAction(selections.keySet, st.get)
          }

          backwardButton.asInstanceOf[Anchor].onclick = (_: MouseEvent) => {
            backwardButtonAction(selections.keySet, st.get)
          }

          val burger = mainDiv.querySelector(".burger")
          burger.addEventListener("click", { (_: MouseEvent) => {
            val target = burger.asInstanceOf[AWASHTMLElement].dataset("target")
            val targetElem = mainDiv.querySelector("#" + target)
            burger.classList.toggle("is-active")
            targetElem.classList.toggle("is-active")
            computeHeight(gl.get)
          }
          }: js.Function1[MouseEvent, _])

          //          for (i <- 0 until qButton.length) {
          //            qButton.item(i).firstChild.asInstanceOf[Anchor].onclick = (_: MouseEvent) => {
          //              //println("clicked")
          //              val anch = qButton.item(i).childNodes.item(1).asInstanceOf[Anchor]
          //              //clear(res)
          //              //              res = highlight(anch.getAttribute("id"))
          //            }
          //          }
          //$[Button](mainDiv, "#clear-button").onclick = (_: MouseEvent) => clear(res)

          if (!js.isUndefined(GraphQuery.queries)) {

            openQueryCli(st.get, systemGraph)
            qI.get.evalQueryFile(GraphQuery.queries.toString)
            updateTable(qI.get.getQueries, qI.get.getResults)
          }
          QuickView.quickView()
        }
      }
      window.onresize = (_: UIEvent) => computeHeight(gl.get)
    } else {
      document.body.appendChild(render(p("Failed to load the model")))
    }

  }

  def toTopDown(graph: ResourceUri): Unit = {
    val ci = gl.get.root.getItemsById(graph).head
    if (ci.config.get("componentState").isDefined) {
      val compState = ci.config.get("componentState").get.asInstanceOf[js.Dictionary[scalajs.js.Any]]

      if (compState.get("isTD").isDefined &&
        !compState.get("isTD").get.asInstanceOf[Boolean]) {
        //                  $[Input](breadCrumbs, ".td").checked = true
        jQuery(ci.element.head.querySelector("svg")).replaceWith(
          Util.graph2Svg(
            graph,
            SvgGenConfig(
              RankDir.TB,
              SettingsView.currentConfig.simpleConn,
              SettingsView.currentConfig.viewVirtualPorts,
              SettingsView.currentConfig.viewErrors,
              SettingsView.currentConfig.viewFlows,
              SettingsView.currentConfig.bindings
            ),
            st.get
          )
        )
        SvgPanZoom("svg").svgPanZoom(js.Dictionary())
        compState.update("isTD", true)
      }
    }

  }

  def toLeftRight(graph: ResourceUri): Unit = {
    val ci = gl.get.root.getItemsById(graph).head
    if (ci.config.get("componentState").isDefined) {
      val compState = ci.config.get("componentState").get.asInstanceOf[js.Dictionary[scalajs.js.Any]]
      if (compState.get("isTD").isDefined &&
        compState.get("isTD").get.asInstanceOf[Boolean]) {
        //                  $[Input](breadCrumbs, ".lr").checked = true
        jQuery(ci.element.head.querySelector("svg")).replaceWith(
          Util.graph2Svg(
            graph,
            SvgGenConfig(
              RankDir.LR,
              SettingsView.currentConfig.simpleConn,
              SettingsView.currentConfig.viewVirtualPorts,
              SettingsView.currentConfig.viewErrors,
              SettingsView.currentConfig.viewFlows,
              SettingsView.currentConfig.bindings
            ),
            st.get
          )
        )
        SvgPanZoom("svg").svgPanZoom(js.Dictionary())
        compState.update("isTD", false)
      }
    }
  }

  def computeHeight(gl: GoldenLayout): Unit = {
    val totalHeight = window.innerHeight
    val headHeight = $[Div]("#header").clientHeight
    val footHeight = $[Div]("#footer").clientHeight
    val bodyHeight = totalHeight - (headHeight + footHeight)
//    val graphHeight = (bodyHeight / 2) - 75
//    val queryHeight = bodyHeight / 2
    val graph = $[Div]("#body")
    graph.style.height = s"${bodyHeight}px"
//    val query = $[Div]("#query-box")
//    query.style.height = s"${queryHeight}px"
    gl.updateSize(scalajs.js.undefined, scalajs.js.undefined)
  }

  def getPortErrorFromFlow(
    flowUri: ResourceUri,
    node: FlowNode,
    isFrom: Boolean,
    st: SymbolTable
  ): ISet[ResourceUri] = {
    val ft = node.getFlows(flowUri)

    val portUri = if (isFrom) ft.fromPortUri else ft.toPortUri
    val errors = if (isFrom) ft.fromFaults else ft.toFaults

    if (portUri.isDefined && errors.nonEmpty) {
      val prop = node.getPropagation(portUri.get)
      errors.flatMap { ff =>
        if (prop.contains(ff)) {
          isetEmpty[ResourceUri] + ("Error:" + portUri.get + ":" + ff)
        } else {
          st.typeAlias(ff).intersect(prop).map((portUri.get, _)).map(pe => "Error:" + pe._1 + ":" + pe._2)
        }
      }
    } else if (portUri.isDefined) {
      isetEmpty + portUri.get
    } else {
      isetEmpty[ResourceUri]
    }
  }

  def cellClicked(tempUri: String, st: SymbolTable): Boolean = {
    clickCounter = clickCounter + 1
    if (clickCounter == 1) {
      timer = Some(scalajs.js.timers.setTimeout(200) {
        if (selections.keySet.contains(tempUri)) {
          clear(tempUri)
        } else {
          highlight(imapEmpty + (tempUri -> true), "#1878c0")
          if (H.isFlow(tempUri)) {
            val reso = Resource.getParentUri(tempUri)
            if (reso.isDefined && FlowNode.getNode(reso.get).isDefined) {
              val toHi = getPortErrorFromFlow(tempUri, FlowNode.getNode(reso.get).get, true, st) ++
                getPortErrorFromFlow(tempUri, FlowNode.getNode(reso.get).get, isFrom = false, st)
              toHi.foreach(t => highlight(imapEmpty + (t -> true), "#1878c0"))
            }
          }

          if (tempUri.startsWith("Edge")) {
            val ports = tempUri.split("[+]")
            if (ports.length == 3) {
              highlight(imapEmpty + (ports(1) -> true) + (ports(2) -> true), "#1878c0")
            }
          }

          if (!SettingsView.currentConfig.simpleConn &&
            tempUri.startsWith(H.CONNECTION_TYPE) &&
            FlowNode.getNode(tempUri).isDefined) {
            val connNode = FlowNode.getNode(tempUri).get

            val inEdges = connNode.getOwner.getIncomingEdges(connNode)
            val outEdges = connNode.getOwner.getOutgoingEdges(connNode)

            val res = inEdges.flatMap(_.sourcePort) ++ outEdges.flatMap(_.targetPort)
            highlight(imapEmpty ++ res.map((_, true)).toMap, "#1878c0")
          }

        }
        clickCounter = 0
      })
    } else {
      if (timer.isDefined) {
        scalajs.js.timers.clearTimeout(timer.get)
        //do dbl click
        if (tempUri.startsWith(H.COMPONENT_TYPE) &&
          FlowNode.getNode(tempUri).isDefined &&
          FlowNode.getNode(tempUri).get.getSubGraph.isDefined) {
          val subGraphUri = FlowNode.getNode(tempUri).get.getSubGraph.get.getUri
          openGraphTab(subGraphUri)
        }
        clickCounter = 0
      }
    }
    false
  }

  @JSExportTopLevel("clear")
  def clear(uri: String): Boolean = {
    //we dont care whether the selection is criteria or not
    //we are just resetting them and removing from selection
    if (uriNodeMap.keySet.contains(uri)) {
      uriNodeMap(uri).foreach(_.reset())
    }

    if (selections.contains(uri)) {
      selections = selections - uri
    }
    false
  }

  def clearAll(uris: ISet[String]): Boolean = {
    uris.foreach(clear)
    false
  }

  def collectorToUris(col: Collector): IMap[String, Boolean] = {
    var res = imapEmpty[String, Boolean]
    if (col.getResultType.isDefined) {
      val edges = col.getEdges.map(e => "Edge+" + e.sourcePort.get + "+" + e.targetPort.get)
      val ans = col.getResultType.get match {
        case ResultType.Node => col.getNodes.map(_.getUri)
        case ResultType.Port => {
          var connUri = isetEmpty[ResourceUri]
          if (!SettingsView.currentConfig.simpleConn) {
            col.getPorts.foreach { p =>
              val nodeUri = Resource.getParentUri(p)
              if (nodeUri.isDefined &&
                H.getUriType(nodeUri.get) == H.CONNECTION_TYPE &&
                FlowNode.getNode(nodeUri.get).isDefined) {
                  val node = FlowNode.getNode(nodeUri.get).get
                val g = node.getOwner
                if (g.getIncomingEdges(node) . size == 1 &&
                  g.getOutgoingEdges(node).size == 1) {
                  connUri = connUri + node.getUri
                }
              }
            }
          }
          col.getPorts ++ col.getFlows ++ connUri
        }
        case ResultType.Error => {
          var connUri = isetEmpty[ResourceUri]
          if (!SettingsView.currentConfig.simpleConn) {
            col.getPortErrors.foreach { pe =>
              val nodeUri = Resource.getParentUri(pe._1)
              if (nodeUri.isDefined &&
                H.getUriType(nodeUri.get) == H.CONNECTION_TYPE &&
                FlowNode.getNode(nodeUri.get).isDefined) {
                val node = FlowNode.getNode(nodeUri.get).get
                val g = node.getOwner
                if (g.getIncomingEdges(node).size == 1 &&
                  g.getOutgoingEdges(node).size == 1) {
                  connUri = connUri + node.getUri
                }
              }
            }
          }
          col.getPortErrors.flatMap(pe => isetEmpty + pe._1 ++ pe._2.map(e => "Error:" + pe._1 + ":" + e)) ++
          col.getFlows ++ connUri
        }
      }
      res = res ++ (edges ++ ans).map(s => (s, false)).toMap
      res = res ++ col.getCriteria.map(c => (c, true)).toMap
    }
    res
  }

  def forwardButtonAction(criteria: ISet[String], st: SymbolTable): Unit = {
    clearAll(criteria)
    if (criteria.nonEmpty) {
      val errorCriteria = criteria.filter(c => c.startsWith("Error"))
      val edgeOrFlowCriteria = criteria.filter(c => c.startsWith("Edge") || c.startsWith(H.FLOW_TYPE))
      val restCriteria = (criteria -- errorCriteria) -- edgeOrFlowCriteria
      errorCriteria.foreach { c =>
        val pe = c.substring(6)
        val ei = pe.indexOf(":error")
        val port = pe.subSequence(0, ei).toString
        val error = pe.subSequence(ei + 1, pe.size).toString
        highlight(collectorToUris(new ErrorReachabilityImpl(st).forwardErrorReach(port, isetEmpty + error)), "#78c0a8")
      }
      highlight(collectorToUris(new PortReachabilityImpl(st).forwardReachSet(restCriteria)), "#78c0a8")
    }
  }

  def backwardButtonAction(criteria: ISet[String], st: SymbolTable): Unit = {
    clearAll(criteria)
    if (criteria.nonEmpty) {
      val errorCriteria = criteria.filter(c => c.startsWith("Error"))
      val edgeOrFlowCriteria = criteria.filter(c => c.startsWith("Edge") || c.startsWith(H.FLOW_TYPE))
      val restCriteria = (criteria -- errorCriteria) -- edgeOrFlowCriteria
      errorCriteria.foreach { c =>
        val pe = c.substring(6)
        val ei = pe.indexOf(":error")
        val port = pe.subSequence(0, ei).toString
        val error = pe.subSequence(ei + 1, pe.size).toString
        highlight(collectorToUris(new ErrorReachabilityImpl(st).backwardErrorReach(port, isetEmpty + error)), "#78c0a8")
      }
      highlight(collectorToUris(new PortReachabilityImpl(st).backwardReachSet(restCriteria)), "#78c0a8")
    }

  }

  def highlight(uris: IMap[String, Boolean], color: String): Boolean = {

    selections = selections ++ uris.map(it => (it._1, (it._2, color)))
    uris.foreach(u => if (uriNodeMap.keySet.contains(u._1)) uriNodeMap(u._1).foreach(_.select(u._2, color)))
    false
  }

  def updateTable(entries: ILinkedMap[String, String], results: ILinkedMap[String, Collector]): Unit = {
    def entryToFrag(entry: (String, String)): ISeq[Node] = {
      var res = ilistEmpty[Node]
      res = res :+ render[Node](
        tr(
          attr("data-tt-id") := "node" + entry._1,
          attr("data-tt-parent-id") := "",
          td(
            verticalAlign := "middle",
            padding := "0",
            div(
              display := "flex",
              justifyContent := "center",
              verticalAlign := "middle",
              style := "display:inline-flex;width:80%;",
              label(`class` := "checkbox", input(`type` := "checkbox", `class` := "select-query", id := "select:" + entry._1)
              )
            )
          ),
          td( //display.`inline-table`,
            verticalAlign := "middle",
            padding := "0", //flexWrap.nowrap,
            //cls := "columns",
            div(
              id := "color-" + entry._1,
              cls := "query-color-picker colorPicker",
              a(cls := "color", div(cls := "colorInner")),
              div(cls := "track", style := "background-image: min/images/text-color.png; display:none;"),
              ul(cls := "dropdown"),
              input(`type` := "hidden", cls := "colorInput")
            )
          ),
          td( //display.`inline-table`,
            verticalAlign := "middle",
            padding := "0", //flexWrap.nowrap,
            //cls := "columns",
            a(
              textAlign := "left",
              padding := "2",
              justifyContent := "left",
              verticalAlign := "middle",
              id := entry._1,
              style := "width:100%",
//            style := "display:inline-flex;width:88%;",
              //            display.`table-cell`,
              //            width.auto,
              cls := "query-table-button button is-white",
              span(entry._1)
            )
          ),
          td(verticalAlign := "middle", span(entry._2))
        )
      )
      if (results(entry._1).getPaths.size > 1) {
        for (i <- results(entry._1).getPaths.toIndexedSeq.indices) {
          res = res :+ render[Node](
            tr(
              attr("data-tt-id") := "node" + entry._1 + ":" + i,
              attr("data-tt-parent-id") := "node" + entry._1,
              td(div(textAlign := "right")),
              td( //display.`inline-table`,
                verticalAlign := "middle",
                padding := "0", //flexWrap.nowrap,
                //cls := "columns",
                div(
                  id := "color-" + entry._1 + "-" + i,
                  cls := "query-color-picker colorPicker",
                  a(cls := "color", div(cls := "colorInner")),
                  div(cls := "track", style := "background-image: min/images/text-color.png; display:none;"),
                  ul(cls := "dropdown"),
                  input(`type` := "hidden", cls := "colorInput")
                )
              ),
              td(
                verticalAlign := "middle",
                paddingLeft := "10",
                //flexWrap.nowrap,
                //cls := "columns is-mobile",
                a(
                  textAlign := "left",
                  padding := "2",
                  justifyContent := "left",
                  verticalAlign := "middle",
                  id := entry._1 + ":" + i,
                  style := "width:100%",
//                style := "display:inline-flex;width:80%;",
                  cls := "query-table-button button is-white",
                  span(entry._1 + "(path " + (i + 1) + ")")
                )
              ),
              td(verticalAlign := "middle", span(entry._2))
            )
          )
        }
      }
      res
    }

    val rows = entries.flatMap(e => entryToFrag(e))
    val table = $[Table]("#query-table")
    val oldNodes = table.childNodes
    while (oldNodes.length > 2) {
      table.removeChild(oldNodes(oldNodes.length - 1))
    }
    rows.foreach(r => table.appendChild(r))
    treeTableAdapter()
    RandomColor.reset()
    val colorPickers = PimpedNodeList(table.querySelectorAll(".query-color-picker"))
    var queryColors = imapEmpty[String, String]
    colorPickers.foreach { cp =>

      val te = $[Div]("#" + cp.asInstanceOf[Div].id)

      val tcp = TinyColorPicker("#" + cp.asInstanceOf[Div].id)
      tcp.tinycolorpicker(js.Dictionary(("colors", js.Array("#f07830")), ("backgroundUrl", "min/images/text-color.png")));
      val cpp = tcp.data("plugin_tinycolorpicker").asInstanceOf[TinyColorPicker]
      tcp.bind("change", { () => {
        val qid = cp.asInstanceOf[Div].id.split("r-").last.replace("-", ":")
        queryColors = queryColors + (qid -> cpp.colorHex)
      }
      }: js.Function)
      cpp.setColor(RandomColor())
      val qid = cp.asInstanceOf[Div].id.split("r-").last.replace("-", ":")
      queryColors = queryColors + (qid -> cpp.colorHex)
    }


    val buttons = PimpedNodeList(table.querySelectorAll(".query-table-button"))
    def notifyErrors(id: String, coll: Collector): Unit = {
      if (coll.getGraphs.isEmpty) {
        Notification.notify(Notification.Kind.Info, "The result is empty.")
      }
      if (coll.getErrors.nonEmpty) {
        val resErrMsg = coll.getErrors.flatMap {
          case m: ErrorMessage => Some(value = m.message)
          case _ => None
        }
        resErrMsg.foreach(Notification.notify(Notification.Kind.Error, _))
        if (terminal.isDefined) {
          terminal.get.echo("[[;red;]" + "Error(s) in " + id + "]")
          resErrMsg.foreach(it => terminal.get.echo("[[;red;]" + it + "]"))
        }
      }
      if (coll.getWarnings.nonEmpty) {
        val resWarnMsg = coll.getWarnings.flatMap {
          case m: WarningMessage => Some(value = m.message)
          case _ => None
        }
        resWarnMsg.foreach(Notification.notify(Notification.Kind.Warning, _))
        if (terminal.isDefined) {
          terminal.get.echo("[[;orange;]" + "Warning(s) in " + id + "]")
          resWarnMsg.foreach(it => terminal.get.echo("[[;orange;]" + it + "]"))
        }
      }
    }
    buttons.foreach { b =>
      val id = b.asInstanceOf[Anchor].getAttribute("id")
      b.asInstanceOf[Anchor].onclick = (_: MouseEvent) => {
//        b.asInstanceOf[Anchor].
if (id.contains(":")) {
  val resColl = results(id.split(":").head)
  val res = resColl.getPaths.toIndexedSeq(id.split(":").last.toInt)
  notifyErrors(id, res)
  highlight(collectorToUris(res), queryColors(id))
} else {
  val res = results(id)
  notifyErrors(id, res)
  highlight(collectorToUris(res), queryColors(id))
        }
      }
    }
  }

  def treeTableAdapter(): TreeTable = {
    //    if($[Table]("#query-table").hasAttribute("class")) {
    //      val attrs = $[Table]("#query-table").getAttribute("class").split(" ").toSet
    //      if(attrs.contains("treetable")) {
    //        $[Table]("#query-table").setAttribute("class", (attrs - "treetable").mkString(" "))
    //      }
    //    }
    TreeTable("#query-table").treetable(
      js.Dictionary.apply[js.Any](
        ("expandable", true),
        (
          "indenterTemplate",
          "<span style='display:run-in; vertical-align: middle; width:15%' class=\"indenter\"></span>"
        ),
        (
          "expanderTemplate",
          "<a href=\"#\" style='vertical-align: middle; display:inline-block; width:3%;'><i class=\"fa fa-chevron-right\" aria-hidden=\"true\"></i></a>"
        ),
        ("onNodeExpand", { (x: js.Any) => {
          val exp = x.asInstanceOf[TreeNode].expander(0)
          val oldChild = exp.firstElementChild
          val newChild =
            templateContent(raw("<i class=\"fa fa-chevron-down\" aria-hidden=\"true\"></i>")).asInstanceOf[Element]
          exp.appendChild(newChild)
          exp.removeChild(oldChild)
        }
        }: js.ThisFunction),
        ("onNodeCollapse", { (x: js.Any) => {
          val exp = x.asInstanceOf[TreeNode].expander(0)
          val oldChild = exp.firstElementChild
          val newChild =
            templateContent(raw("<i class=\"fa fa-chevron-right\" aria-hidden=\"true\"></i>")).asInstanceOf[Element]
          exp.appendChild(newChild)
          exp.removeChild(oldChild)
        }
        }: js.ThisFunction)
      ),
      true
    )
  }

  def openCliTab(st: SymbolTable): Boolean = {
    if (gl.isDefined) {
      if (qI.isEmpty) {
        qI = Some(new QueryInter(st))
      }
      //gl.get.root.contentItems(0).addChild(cliConfig())
      terminal = Some(
        Terminal("#term1").terminal({
          (cmd: String, term: Terminal) => {

            val res = Future(qI.get.evalCmd(cmd))

            res.onComplete(
              r =>
                if (r.isSuccess) {
                  if (r.get._2.messages.isEmpty) {
                    if (r.get._1.last._2.hasErrors) {
                      term.error("Errors :" + r.get._1.last._2.getErrors.map(x => x.asInstanceOf[MessageTag].message).mkString("\n"))
                    } else {
                      term.echo("Computed: " + r.get._1.last._1)
                      clearAll(selections.keySet)
                      highlight(collectorToUris(r.get._1.last._2), RandomColor())
                      term.echo(
                        "Results found in graph(s): {" +
                          r.get._1.last._2.getGraphs.map(_.split("\\$\\$AWAS").last).mkString(", ") + "}"
                      )
                      updateTable(qI.get.getQueries, qI.get.getResults)

                    }
                  } else {
                    term.echo(
                      "Parse Error :" + ISZOps(r.get._2.messages)
                        .foldLeft[String]((r: String, m: Message) => m.text.value + "\n" + r, "")
                    )
                  }
                } else {}
            )
          }
        }: js.Function2[String, Terminal, Unit], js.Dictionary("greetings" -> "Awas Query Command Line Interface", "name" -> "AwasCli",
          "prompt" -> "> ",
          "scrollOnEcho" -> true,
          "onAfterRedraw" -> ({ (t: Terminal) => {
            t.find("textarea").blur().focus()
          }
        }: js.ThisFunction0[Terminal, Unit])))
      )
      terminal.get.resize()
      val x = $[Div](".terminal-output")
      x.setAttribute("overflow", "auto")
    }
    false
  }

  private def openQueryCli(st: SymbolTable, graph: FlowGraph[FlowNode, FlowEdge[FlowNode]]): Boolean = {
    var queryLayout: Option[GoldenLayout] = None
    if (gl.isDefined) {

      if (gl.get.root.getComponentsByName("cli").nonEmpty) {
        val ci = gl.get.root.getItemsById("cli").head
        val stacks = gl.get.root.getItemsByType("stack")
        val si = stacks.find(si => si.contentItems.contains(ci))
        if (si.isDefined) si.get.setActiveContentItem(ci)
      } else {
        if (!cliRegistered) {
          gl.get.registerComponent("cli", {
            (container: Container, componentState: js.Dictionary[scalajs.js.Any]) => {
              val termDom = //style := "display:block;width:100%;height:100%",
                div(id := "QueryCli", style := "display:block;width:100%;height:100%").render
              container.getElement().append(termDom)
              container.on("resize", { () => {
                  if (queryLayout.isDefined) {
                    queryLayout.get.updateSize(scalajs.js.undefined, scalajs.js.undefined)
                  }
              }
                container.on("close", { () => {
                  if (queryLayout.isDefined) {
                    queryLayout.get.destroy()
                  }
                  container.close()
                }
                })

              }: js.Function
              )

            }
          }: js.Function)
          cliRegistered = true
        }

        gl.get.root.contentItems(0).addChild(Views.cliConfig())
        val queryCli: Div = $[Div]("#QueryCli")

        queryLayout = Some(new GoldenLayout(Views.queryCliConfig, jQuery(queryCli)))
        val qBox = render[Div](Views.queryBox())

        $[Input](qBox, "#select-all").onclick = (_: MouseEvent) => {
          if ($[Input](qBox, "#select-all").checked) {
            qBox.querySelectorAll(".select-query").foreach(_.asInstanceOf[Input].checked = true)
          } else {
            qBox.querySelectorAll(".select-query").foreach(_.asInstanceOf[Input].checked = false)
          }
        }

        queryLayout.get.registerComponent("query_table", {
          (container: Container, componentState: js.Dictionary[scalajs.js.Any]) => {
            container.getElement().append(qBox)
          }
        }: js.Function)
        queryLayout.get.registerComponent(
          "query_cli", { (container: Container, componentState: js.Dictionary[scalajs.js.Any]) => {
            container
              .getElement()
              .append(span(overflow := "auto", style := "display:block;width:100%;height:100%", div(id := "term1")).render)
          }
          }: js.Function
        )
        queryLayout.get.init()
        openCliTab(st: SymbolTable)

        //treeTableAdapter()
        val ci = gl.get.root.getItemsById("cli").head
        ci.element.asInstanceOf[Container]

        val inputImport = qBox.querySelector("#import-queries")
        inputImport.asInstanceOf[Input].onchange = (e: Event) => {

          val files = inputImport.asInstanceOf[Input].files
          val reader = new dom.FileReader()
          var queries = ""

          reader.onload = (_: UIEvent) => {
            queries = reader.result.asInstanceOf[String]
            qI.get.evalQueryFile(queries)
            updateTable(qI.get.getQueries, qI.get.getResults)
            if (qI.get.getReporter.messages.nonEmpty) {
              println(qI.get.getReporter.messages.elements.mkString("\n"))
            }
          }
          reader.readAsText(files(0))
          reader.onerror = (_: Event) => {
            println("Import error")
          }
        }

        val inputExport = qBox.querySelector("#export-queries")
        inputExport.asInstanceOf[Input].onclick = (_: MouseEvent) => {

          val selectedQueries = qBox
            .querySelectorAll(".select-query")
            .filter(_.asInstanceOf[Input].checked)
            .flatMap { sq => sq.attributes.get("id")
            }
            .map(_.value.split(":").last)
            .toSet
          if (selectedQueries.isEmpty) {
            Notification.notify(Kind.Error, "Select queries to export")
          } else {
            val text =
              qI.get.getQueries.filter(it => selectedQueries.contains(it._1)).map(q => q._1 + " = " + q._2)
                .mkString("\n")
            val filename = st.systemDecl.compName.value + ".aq"
            val blob = new Blob(js.Array(text), BlobPropertyBag("text/plain;charset=utf-8"))
            FileSaver.saveAs(blob, filename)
          }
        }

        val inputFia = qBox.querySelector("#gen-queries")
        inputFia.asInstanceOf[Input].onclick = (_: MouseEvent) => {
          val magic = inputFia.parentNode.asInstanceOf[Label].querySelector(".file-icon").firstChild
          inputFia.parentNode
            .asInstanceOf[Label]
            .querySelector(".file-icon")
            .replaceChild(render[Node](i(cls := "fas fa-spinner")), magic)
          qI.get.evalQueryFile(new FaultImpactAnalysis().generateFIAQueries(st, graph, true))
          updateTable(qI.get.getQueries, qI.get.getResults)
          val doing = inputFia.parentNode.asInstanceOf[Label].querySelector(".file-icon").firstChild
          inputFia.parentNode.asInstanceOf[Label].querySelector(".file-icon").replaceChild(magic, doing)
          Notification.notify(Notification.Kind.Success, "Generated FIA queries")
//          updateTable(qI.get.getQueries, qI.get.getResults)
        }

        val inputRemove = qBox.querySelector("#remove-queries")
        inputRemove.asInstanceOf[Input].onclick = (_: MouseEvent) => {
          val selectedQueries = qBox.querySelectorAll(".select-query").filter(_.asInstanceOf[Input].checked)
          if (selectedQueries.isEmpty) {
            Notification.notify(Kind.Error, "Select queries to remove")
          } else {
            selectedQueries.foreach { q =>
              if (q.attributes.contains("id") && qI.isDefined) {
                qI.get.removeQueries(q.attributes.get("id").get.value.split(":").last)
              }
            }
            updateTable(qI.get.getQueries, qI.get.getResults)
          }
        }
      }
    }
    false
  }

}

object RandomColor extends Function0[String] {
  var i = 0

  val colors = Seq(
    "#f07830",
    "#c0d830",
    "#184890",
    "#303090",
    "#189048",
    "#f0f018",
    "#1878c0",
    "#903090",
    "#c01830",
    "#a81860",
    "#d8a818",
    "#f06060",
    "#60c048",
    "#f03018",
    "#603090",
    "#78a848",
    "#6048a8",
    "#48a848",
    "#d83030",
    "#f0f0a8",
    "#f0f078",
    "#48a8d8",
    "#18a8c0",
    "#c04830",
    "#ffd8a8",
    "#4860a8",
    "#f0c0a8",
    "#ffd878",
    "#f01830",
    "#78c0d8",
    "#f0c078",
    "#f0a8c0",
    "#d878a8",
    "#ffc000",
    "#90d8f0",
    "#ffd830",
    "#30c078",
    "#f01860",
    "#a890c0",
    "#60c090",
    "#4890c0",
    "#c0d878",
    "#90a8d8",
    "#f07890",
    "#f04878",
    "#7890c0",
    "#9060a8",
    "#6078c0",
    "#78c0a8"
  )

  def apply(): String = {
    if (i == colors.size) {
      i = 0
    }
    val res = colors(i)
    i = i + 1
    res
  }

  def reset() = {
    i = 0
  }
}
