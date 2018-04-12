package org.sireum.awas

import facades._
import org.scalajs.dom.html.{Anchor, Div, Input}
import org.scalajs.dom.raw.Node
import org.scalajs.dom.{raw => _, _}
import org.scalajs.jquery.jQuery
import org.sireum.awas.fptc.{FlowEdge, FlowGraph, FlowGraphUpdate, FlowNode}
import org.sireum.awas.reachability.BasicReachabilityImpl
import org.sireum.awas.slang.Aadl2Awas
import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.awas.witness.SvgGenerator
import org.sireum.common.JSutil._
import org.sireum.util.{imapEmpty, _}
import scalatags.Text
import scalatags.Text.all.{id, _}
import scalatags.Text.tags2.nav

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}


@JSExportTopLevel("org.sireum.awas.Main")
object Main {
  val H = SymbolTableHelper
  var uriNodeMap: IMap[String, ISet[Node]] = imapEmpty[String, ISet[Node]]
  var selections: ISet[String] = isetEmpty[String]
  var gl: Option[GoldenLayout] = None

  def getInitLayout(title: String, inGraph: String): js.Dictionary[scalajs.js.Any] = js.Dictionary(
    ("settings", js.Dictionary(("showPopoutIcon", false),
      ("showCloseIcon", false))),
    (
      "content",
      js.Array(
        js.Dictionary(("type", "stack"),
          ("content",
            js.Array(
              js.Dictionary(
                ("title", title),
                ("type", "component"),
                ("componentName", "system"),
                ("id", inGraph),
                ("componentState", js.Dictionary[scalajs.js.Any](("graph", inGraph))),
                ("isClosable", false)
              )
            )
          )
        )
      )
    )
  )

  def childConfig(title: String, graph: ResourceUri): js.Dictionary[scalajs.js.Any] = js.Dictionary(
    ("title", title),
    ("type", "component"),
    ("componentName", "system"),
    ("id", graph),
    ("componentState", js.Dictionary(("graph", graph)))
  )


  def getAllSubGraphs(st: SymbolTable)
  : IMap[ResourceUri, FlowGraph[FlowNode, FlowNode.Edge]] = {
    var result = imapEmpty[ResourceUri, FlowGraph[FlowNode, FlowNode.Edge]]
    var workList = ilistEmpty[ResourceUri] ++ st.components

    while (workList.nonEmpty) {
      val current = workList.head
      if (st.componentTable(current).subComponents.nonEmpty &&
        FlowNode.getNode(current).isDefined) {
        result = result + (current -> FlowNode.getNode(current).get.getSubGraph.get)
      }
      workList = workList.tail
    }
    result
  }

  def graph2Svg(graph: FlowGraph[FlowNode, FlowNode.Edge], isLR: Boolean): Node = {
    val svgString = GraphViz.Viz(SvgGenerator(graph.asInstanceOf[FlowGraph[FlowNode, FlowNode.Edge]
      with FlowGraphUpdate[FlowNode, FlowEdge[FlowNode]]], isLR), js.Dictionary(("images", js.Array(
      js.Dictionary(("path", "min/images/sub-graph-icon.png"), ("width", "45px"), ("height", "25px"))
    ))))
    val result = templateContent(raw(svgString)).querySelectorAll("svg")(0)
    processSvg(result.asInstanceOf[Element])

    result
  }


  def uriToBreadCrumbs(uri: ResourceUri, st: SymbolTable): Frag = {
    val res = Resource.getDefResource(uri)
    if (res.isDefined) {
      val paths = (res.get.uriPaths :+ res.get.uri).tail.tail
      val ancestors = H.getAllAncestors(uri, st)
      div(cls := "container is-fluid ",
        nav(cls := "level", div(cls := "level-left",
          div(cls := "level-item", //tag("section")(cls:="section", //div(cls := "container",
            nav(cls := "breadcrumb has-arrow-separator is-medium",
              attr("aria-label") := "breadcrumbs", ul(
                for (path <- paths) yield {
                  li(a(href := "#", onclick := "openGraphTab(\"" + ancestors(paths.indexOf(path)) + "\")", path))
                }
              )))), div(cls := "level-right", div(cls := "level-item",
          div(cls := "control", label(cls := "radio", input(`type` := "radio", name := "TBLR", id := "td", " Top-Down ")),
            label(cls := "radio", input(`type` := "radio", name := "TBLR", id := "lr", " Left-Right ")))
        ))))
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
        gl.get.root.contentItems(0).addChild(childConfig(uri.split(H.ID_SEPARATOR).last, uri))
        val temp = selections

        SvgPanZoom("svg").svgPanZoom(js.Dictionary())

      }
    }
    false
  }

  //def openChildGraph()

  @JSExport
  def main(): Unit = {
    val model = Aadl2Awas.apply(GraphQuery.json)
    if (model.isDefined) {
      val reporter = new ConsoleTagReporter()
      val st = SymbolTable(model.get)(reporter)

      val systemGraph = FlowGraph(model.get, st)

      //val systemSvg = graph2Svg(systemGraph)

      val graphs = getAllSubGraphs(st) + (st.system -> systemGraph)

      //val subsvgs = subgraphs.map(x => (x._1, graph2Svg(x._2)))

      val mainDiv = render[Div](mainPage())

      val config = getInitLayout(st.systemDecl.compName.value, st.system)

      val mainBox: Element = mainDiv.querySelector("#main-container")

      gl = Some(new GoldenLayout(config, jQuery(mainBox)))

      var res = imapEmpty[Node, String]

      document.onreadystatechange = (_: Event) => {
        document.body.appendChild(mainDiv)
        if (document.readyState == "complete" && gl.isDefined) {
          val systemName = st.systemDecl.compName.value

          gl.get.registerComponent("system", { (container: Container, componentState: js.Dictionary[scalajs.js.Any]) => {
            if (componentState.get("graph").isDefined) {
              val uri = componentState.get("graph").get.asInstanceOf[ResourceUri]
              //val temp = componentState("graph").asInstanceOf[Node]
              //println(temp)
              val breadCrumbs = render[Div](uriToBreadCrumbs(uri, st))
              val asvg = graph2Svg(graphs(uri), false)
              container.getElement().append(breadCrumbs).append(asvg)
              SvgPanZoom("svg").svgPanZoom(js.Dictionary())
              $[Input](breadCrumbs, ".td").checked = true

              $[Input](breadCrumbs, ".td").onclick = (_: MouseEvent) => {
                //                if($[Input](breadCrumbs, ".td").checked == false) {
                $[Input](breadCrumbs, ".td").checked = true
                container.getElement().children("svg").first().replaceWith(
                  graph2Svg(graphs(uri), false))
                SvgPanZoom("svg").svgPanZoom(js.Dictionary())
                //                }
              }
              $[Input](breadCrumbs, ".lr").onclick = (_: MouseEvent) => {
                //                if($[Input](breadCrumbs, ".lr").checked == false) {
                $[Input](breadCrumbs, ".lr").checked = true
                container.getElement().children("svg").first().replaceWith(
                  graph2Svg(graphs(uri), true))
                SvgPanZoom("svg").svgPanZoom(js.Dictionary())
                //                }
              }
            }
          }
          }: js.Function)

          gl.get.init()

          SvgPanZoom("svg").svgPanZoom(js.Dictionary())
          // gl.root.contentItems(0).addChild(childConfig)

          gl.get.root.setTitle(systemName)

          computeHeight(gl.get)

          val qButton = mainDiv.querySelectorAll("#query-button")
          val clearButton = mainDiv.querySelector("#clear-button")
          val forwardButton = mainDiv.querySelector("#forward-button")
          val backwardButton = mainDiv.querySelector("#backward-button")
          forwardButton.asInstanceOf[Anchor].onclick = (_: MouseEvent) => {
            forwardButtonAction(selections, st)
          }

          backwardButton.asInstanceOf[Anchor].onclick = (_: MouseEvent) => {
            backwardButtonAction(selections, st)
          }

          clearButton.asInstanceOf[Anchor].onclick = (_: MouseEvent) => {
            clearAll(selections)
          }

          for (i <- 0 until qButton.length) {
            qButton.item(i).firstChild.asInstanceOf[Anchor].onclick = (_: MouseEvent) => {
              //println("clicked")
              val anch = qButton.item(i).childNodes.item(1).asInstanceOf[Anchor]
              //clear(res)
              //              res = highlight(anch.getAttribute("id"))
            }
          }
          //$[Button](mainDiv, "#clear-button").onclick = (_: MouseEvent) => clear(res)
        }
      }
      window.onresize = (_: UIEvent) => computeHeight(gl.get)
    } else {
      document.body.appendChild(render(
        p("Failed to load the model")))
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

//  def computeHeight(): Unit = {
//    val totalHeight = window.innerHeight
//    val headHeight = $[Div]("#header").clientHeight
//    val footHeight = $[Div]("#footer").clientHeight
//    val bodyHeight = totalHeight - (headHeight + footHeight + 23)
//    val graphHeight = (bodyHeight / 2) - 75
//    val queryHeight = bodyHeight / 2
//    val graph = $[Div]("#graph-view")
//    graph.style.height = s"${graphHeight}px"
//    val query = $[Div]("#query-box")
//    query.style.height = s"${queryHeight}px"
//  }

  //  def clear(res: IMap[Node, String]): Unit = {
  //    res.foreach { it =>
  //      if (it._2.startsWith("Edge")) {
  //        it._1.asInstanceOf[Element].firstElementChild.setAttribute("stroke", "#000000")
  //        it._1.asInstanceOf[Element].firstElementChild.setAttribute("opacity", ".2")
  //        if (it._1.asInstanceOf[Anchor].childElementCount >= 2) {
  //          it._1.asInstanceOf[Anchor].children(1).setAttribute("stroke", "#000000")
  //          it._1.asInstanceOf[Anchor].children(1).setAttribute("fill", "#000000")
  //          it._1.asInstanceOf[Anchor].children(1).setAttribute("opacity", ".2")
  //        }
  //
  //      } else if (it._2.startsWith("component") || it._2.startsWith("connection")) {
  //        it._1.asInstanceOf[Element].firstElementChild.setAttribute("fill", "#eeccff")
  //        //      } else if(it._2.startsWith("Error")) {
  //        //        it._1.asInstanceOf[Element].firstElementChild.setAttribute("fill", "#e6e6e6")
  //      } else {
  //        it._1.asInstanceOf[Element].firstElementChild.setAttribute("fill", "#ffffff")
  //      }
  //    }
  //  }

  def cellClicked(tempUri: String): Boolean = {
    if (selections.contains(tempUri)) {
      clear(tempUri)
    } else {
      highlight(isetEmpty[String] + tempUri, true)

    }
    false
  }

  def processSvg(svg: Element): Unit = {
    svg.setAttribute("id", "awasgraph")
    svg.setAttribute("height", "100%")
    svg.setAttribute("width", "100%")
    jQuery(svg).removeAttr("xmlns")
    jQuery(svg).find("title").each((e: Element) => e.parentNode.removeChild(e))

    val allLinks = svg.querySelectorAll("[*|href='templink']")
    for (i <- 0 until allLinks.length) {
      var tempUri = allLinks.item(i).asInstanceOf[Anchor].getAttribute("target")
      uriNodeMap = uriNodeMap + (tempUri ->
        uriNodeMap.getOrElse(tempUri, isetEmpty[Node]).+(allLinks.item(i)))

      allLinks.item(i).asInstanceOf[Anchor].onclick = (_: MouseEvent) => cellClicked(tempUri)
      val resUri = tempUri.split("[+]").last
      if (resUri.startsWith(H.COMPONENT_TYPE) &&
        FlowNode.getNode(resUri).isDefined &&
        FlowNode.getNode(resUri).get.getSubGraph.isDefined) {
        val subGraphUri = FlowNode.getNode(resUri).get.getSubGraph.get.getUri
        allLinks.item(i).asInstanceOf[Anchor].ondblclick = (_: MouseEvent) => openGraphTab(subGraphUri)
        allLinks(i).asInstanceOf[Anchor].setAttribute("xlink:title", "Click to select \n Double click to open sub-graph")
        allLinks(i).asInstanceOf[Anchor].firstElementChild.setAttribute("fill", "#eeccff")
      } else {
        if (tempUri.startsWith("Node+")) {
          allLinks(i).asInstanceOf[Anchor].setAttribute("xlink:title", "Click to select")
          allLinks(i).asInstanceOf[Anchor].firstElementChild.setAttribute("fill", "#eeccff")
        } else if (tempUri.startsWith("Edge")) {
          //        allLinks(i).asInstanceOf[Element].firstElementChild.setAttribute("stroke", "#a6a6a6")
          allLinks(i).asInstanceOf[Anchor].firstElementChild.setAttribute("opacity", ".2")
          if (allLinks(i).asInstanceOf[Element].childElementCount >= 2) {
            //          allLinks(i).asInstanceOf[Element].children(1).setAttribute("fill","#a6a6a6")
            //          allLinks(i).asInstanceOf[Element].children(1).setAttribute("stroke", "#a6a6a6")
            allLinks(i).asInstanceOf[Element].children(1).setAttribute("opacity", ".2")
          }
        } else {
          allLinks(i).asInstanceOf[Anchor].setAttribute("xlink:title", "Click to select")
        }
      }
    }
  }

  def clearAll(uris: ISet[String]): Boolean = {
    uris.foreach(clear)
    false
  }

  def forwardButtonAction(criteria: ISet[String], st: SymbolTable): Unit = {
    clearAll(criteria)
    if (criteria.nonEmpty) {
      val actualCriteria = criteria.flatMap { c =>
        if (c.startsWith("Node")) {
          FlowNode.getNode(c.split("[+]").last)
        } else if (H.isPort(c) || H.isFlow(c)) {
          None
          //          FlowNode.getNode(c)
        } else {
          //we dont care about edges
          None
        }
      }
      val basicReach = new BasicReachabilityImpl(st, actualCriteria.head.getOwner)
      val res = basicReach.forwardReachSetNode(actualCriteria)
      val edges = res.getEdges.map(e => "Edge+" + e.sourcePort.get + ":" + e.targetPort.get)
      val toHighlight = res.getNodes.map("Node+" + _.getUri) ++
        res.getNodes.map(_.getUri).filter(H.isPort) ++ edges
      highlight(toHighlight, false)
      highlight(criteria.filterNot(_.startsWith("Edge")), true)
      highlight(actualCriteria.map(_.getUri), true)
    }
  }

  def backwardButtonAction(criteria: ISet[String], st: SymbolTable): Unit = {
    clearAll(criteria)
    if (criteria.nonEmpty) {
      val actualCriteria = criteria.flatMap { c =>
        if (c.startsWith("Node")) {
          FlowNode.getNode(c.split("[+]").last)
        } else if (H.isPort(c) || H.isFlow(c)) {
          None
          //          FlowNode.getNode(c)
        } else {
          //we dont care about edges
          None
        }
      }
      val basicReach = new BasicReachabilityImpl(st, actualCriteria.head.getOwner)
      val res = basicReach.backwardReachSetNode(actualCriteria)
      val edges = res.getEdges.map(e => "Edge+" + e.sourcePort.get + ":" + e.targetPort.get)
      val toHighlight = res.getNodes.map("Node+" + _.getUri) ++
        res.getNodes.map(_.getUri).filter(H.isPort) ++ edges
      highlight(toHighlight, false)
      highlight(criteria.filterNot(_.startsWith("Edge")), true)
      highlight(actualCriteria.map(_.getUri), true)
    }
  }

  @JSExportTopLevel("clear")
  def clear(uri: String): Boolean = {
    if (uriNodeMap.get(uri).isDefined) {
      uriNodeMap(uri).map(_.asInstanceOf[Anchor]).foreach { anchor =>
        if (uri.startsWith("Node")) {
          val auri = uri.split("[+]").last
          if (H.isPort(auri) && selections.contains(auri)) {
            //            selections = selections - auri
            //            clear(auri)
            uriNodeMap(auri).map(_.asInstanceOf[Anchor]).foreach { an =>
              an.firstElementChild.setAttribute("fill", "#ffffff")
              selections = selections - auri
            }
          }
          anchor.firstElementChild.setAttribute("fill", "#eeccff")
        } else if (uri.startsWith("Edge")) {
          anchor.firstElementChild.setAttribute("stroke", "#000000")
          anchor.firstElementChild.setAttribute("opacity", ".2")
          if (anchor.childElementCount >= 2) {
            anchor.children(1).setAttribute("stroke", "#000000")
            anchor.children(1).setAttribute("fill", "#000000")
            anchor.children(1).setAttribute("opacity", ".2")
          }
        } else {
          if (H.isPort(uri) && FlowNode.getNode(uri).isDefined) {
            if (selections.contains("Node+" + uri)) {
              selections = selections - "Node+" + uri
              clear("Node+" + uri)
            }
          }
          anchor.firstElementChild.setAttribute("fill", "#ffffff")
        }
      }
    }
    selections = selections - uri
    false
  }

  def highlight(uris: ISet[String], isCriteria: Boolean): Boolean = {
    val color = if (isCriteria) "#008080" else "#b3daff"
    selections = selections ++ uris
    uris.foreach { uri =>
      val anchors = uriNodeMap.get(uri).map(_.map(_.asInstanceOf[Anchor]))
      if (anchors.isDefined) {
        if (uri.startsWith("Node")) {
          anchors.get.foreach(_.firstElementChild.setAttribute("fill", color))
          val puri = uri.split("[+]").last
          if (H.isPort(puri) && uriNodeMap.get(puri).isDefined) {
            uriNodeMap(puri).map(_.asInstanceOf[Anchor]).foreach(
              _.firstElementChild.setAttribute("fill", color))
            selections = selections + puri
          }
        } else if (uri.startsWith("Edge")) {
          anchors.get.foreach(_.firstElementChild.setAttribute("stroke", "#ff0000"))
          anchors.get.foreach(_.firstElementChild.setAttribute("opacity", ".8"))
          anchors.get.foreach { a =>
            if (a.childElementCount >= 2) {
              a.children(1).setAttribute("stroke", "#ff0000")
              a.children(1).setAttribute("fill", "#ff0000")
              a.children(1).setAttribute("opacity", ".8")
            }
          }
        } else if (uri.startsWith("Error")) {
          if (isCriteria) {
            anchors.get.foreach(_.firstElementChild.setAttribute("fill", color))
          } else {
            anchors.get.foreach(_.firstElementChild.setAttribute("fill", "#ff9999"))
          }
        } else {
          anchors.get.foreach(_.firstElementChild.setAttribute("fill", color))
          if (H.isPort(uri) &&
            FlowNode.getNode(uri).isDefined &&
            uriNodeMap.get("Node+" + uri).isDefined) {
            selections = selections + ("Node+" + uri)
            uriNodeMap("Node+" + uri).map(_.asInstanceOf[Anchor]).foreach(
              _.firstElementChild.setAttribute("fill", color))
          }
        }
      }
    }
    false
  }

  //  def highlight(queryName: String): IMap[Node, String] = {
  //    var res = imapEmpty[Node, String]
  //    val qres = GraphQuery.queryRes.toMap.get(queryName)
  //    val crit = GraphQuery.queryCriteria.toMap.get(queryName)
  //    if (qres.isDefined) {
  //      qres.get.toVector.foreach { ruri =>
  //        if (uriNodeMap.contains(ruri)) {
  //          if (ruri.startsWith("Edge")) {
  //            res += uriNodeMap(ruri) -> ruri
  //            uriNodeMap(ruri).asInstanceOf[Anchor].firstElementChild.setAttribute("stroke", "#ff0000")
  //            uriNodeMap(ruri).asInstanceOf[Anchor].firstElementChild.setAttribute("opacity", ".8")
  //            if (uriNodeMap(ruri).asInstanceOf[Anchor].childElementCount >= 2) {
  //              uriNodeMap(ruri).asInstanceOf[Anchor].children(1).setAttribute("stroke", "#ff0000")
  //              uriNodeMap(ruri).asInstanceOf[Anchor].children(1).setAttribute("fill", "#ff0000")
  //              uriNodeMap(ruri).asInstanceOf[Anchor].children(1).setAttribute("opacity", ".8")
  //            }
  //          } else if (ruri.startsWith("Error")) {
  //            res += uriNodeMap(ruri) -> ruri
  //            uriNodeMap(ruri).asInstanceOf[Anchor].firstElementChild.setAttribute("fill", "#ff9999")
  //          } else {
  //            res += uriNodeMap(ruri) -> ruri
  //            uriNodeMap(ruri).asInstanceOf[Anchor].firstElementChild.setAttribute("fill", "#b3daff")
  //          }
  //        }
  //      }
  //    }
  //    if (crit.isDefined) {
  //      crit.get.toVector.foreach { ruri =>
  //        if (uriNodeMap.contains(ruri)) {
  //          res += uriNodeMap(ruri) -> ruri
  //          uriNodeMap(ruri).asInstanceOf[Anchor].firstElementChild.setAttribute("fill", "#008080")
  //        }
  //      }
  //    }
  //    res
  //  }

  def mainPage(): Frag = {
    //    val temp: Seq[(String, String)] = GraphQuery.queryExp.toSeq
    div(
      id := "view",
      width := "100%",
      div(
        cls := "hero",
        div(cls := "hero-head is-large", id := "header", nav(cls := "navbar", div(cls := "container", div(cls := "navbar-brand", //backgroundColor:="primary",
          div(cls := "navbar-item", padding := "1%",
            h1(cls := "title is-2", color := "white", span("Awas Witness Visualizer"))),
          div(cls := "navbar-burger burger is-active", attr("data-target") := "nav-menu-buttons",
            span(),
            span(),
            span()
          )),
          div(cls := "navbar-menu is-active", id := "nav-menu-buttons",
            div(cls := "navbar-end",
              //  div(cls := "navbar-item", div(cls := "field is-grouped",
              div(cls := "navbar-item",
                p(cls := "control", a(cls := "button is-outlined", id := "forward-button",
                  span("Forward")
                  //                  span(cls := "icon is-medium has-text-primary",
                  //i(cls := "far fa-caret-square-right fa-2x", aria.hidden:="false"))
                ))),
              div(cls := "navbar-item",
                p(cls := "control", a(cls := "button is-outlined", id := "backward-button",
                  span("Backward")))),
              div(cls := "navbar-item",
                p(cls := "control", a(cls := "button is-outlined", id := "clear-button",
                  span("Clear")))),
              div(cls := "navbar-item",
                p(cls := "control", a(cls := "button is-outlined",
                  span("Awas Query"))))
              //                  span(cls := "icon is-medium has-text-primary",
              //                    i(cls:= "far fa-caret-square-left fa-2x", aria.hidden:="false"))))
            )) //)))
        ))),

      //body
        div(
          id := "body",
          cls := "hero-body", padding := "0%",
          // backgroundColor := "white",
          style := "display:inherit",
          div(id := "main-container", width := "100%", height := "100%")
        )),
//      div(cls := "container",
//        div(cls := "tile is-ancestor",
//          div(cls := "tile is-parent is-vertical",
//            div(cls := "tile is-child",
//              div(id := "graph-box", cls := "box",
//                h2(cls := "subtitle", "Dependence Graph"),
//                figure(id := "graph-view", cls := "image ")
//              )),
//            div(cls := "tile is-child",
//              div(id := "query-box", cls := "box", overflow := "scroll",
//                nav(cls := "level", div(cls := "level-left",
//                  div(cls := "level-item", h2(cls := "subtitle", "Queries"))),
//                  div(cls := "level-right", div(cls := "level-item",
//                    button(id := "clear-button", cls := "button", "Clear")))),
//                queryTableBuild(temp)
//              ))))),
      //footer
      nav(id := "footer", cls := "navbar", bottom := "0", style := "display:inherit",
        div(cls := "navbar-brand",
          p(bottom := "10px", right := "10px", position := "absolute",
            span(color := "white",
              "SAnToS Laboratory, Kansas State University"))))

    )
  }

  //  private def queryBox(): Frag = {
  //    val temp: Seq[(String, String)] = GraphQuery.queryExp.toSeq
  //    div(
  //      id := "query-box",
  //      cls := "box",
  //      overflow := "auto",
  //      nav(
  //        cls := "level",
  //        div(cls := "level-left", div(cls := "level-item", h2(cls := "subtitle", "Queries"))),
  //        div(cls := "level-right", div(cls := "level-item", button(id := "clear-button", cls := "button", "Clear")))
  //      ),
  //      queryTableBuild(temp)
  //    )
  //  }

  private def queryTableBuild(temp: Seq[(String, String)]): Text.TypedTag[String] =
    table(
      id := "query-table",
      cls := "table is-striped is-narrow is-fullwidth",
      border := "0",
      borderSpacing := "0",
      col(width := "40%"),
      thead(th(cls := "is-5", "Name"), th("Expression")),
      SeqNode(temp.map { query =>
        tr(
          attr("data-tt-id") := "node" + query._1,
          if (query._1.contains(":")) attr("data-tt-parent-id") := "node" + query._1.split(":").head
          else attr("data-tt-parent-id") := "",
          td(
            verticalAlign := "middle",
            padding := "0",
            id := "query-button",
            a(
              textAlign := "left",
              padding := "0",
              justifyContent := "left",
              verticalAlign := "middle",
              id := query._1,
              style := "display:inline-flex; width:90%; ",
              if (temp.indexOf(query) % 2 == 1)
                cls := "button is-light "
              else
                cls := "button is-white ",
              span(query._1)
            )
          ),
          td(verticalAlign := "middle", span(query._2))
        )
      })
    )
}
