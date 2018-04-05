package org.sireum.awas

import facades._
import org.scalajs.dom.html.{Anchor, Button, Div}
import org.scalajs.dom.raw.Node
import org.scalajs.dom.{raw => _, _}
import org.scalajs.jquery.jQuery
import org.sireum.awas.fptc.{FlowEdge, FlowGraph, FlowGraphUpdate, FlowNode}
import org.sireum.awas.slang.Aadl2Awas
import org.sireum.awas.symbol.{Resource, SymbolTable}
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

  var uriNodeMap: IMap[String, ISet[Node]] = imapEmpty[String, ISet[Node]]

  def getInitLayout(title: String, inGraph: String): js.Dictionary[scalajs.js.Any] = js.Dictionary(
    ("settings", js.Dictionary(("showPopoutIcon", false), ("showCloseIcon", false))),
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
                ("componentState", js.Dictionary[scalajs.js.Any](("graph", inGraph))),
                ("isClosable", false)
              )
            )
          )
        )
      )
    )
  )

  def childConfig(title: String, graph: Node): js.Dictionary[scalajs.js.Any] = js.Dictionary(
    ("title", title),
    ("type", "component"),
    ("componentName", "system"),
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

  def graph2Svg(graph: FlowGraph[FlowNode, FlowNode.Edge]): Node = {
    val svgString = GraphViz.Viz(SvgGenerator(graph.asInstanceOf[FlowGraph[FlowNode, FlowNode.Edge]
      with FlowGraphUpdate[FlowNode, FlowEdge[FlowNode]]]))
    val result = templateContent(raw(svgString)).querySelectorAll("svg")(0)
    processSvg(result.asInstanceOf[Element])
    result
  }

  def uriToBreadCrumbs(uri: ResourceUri): Frag = {
    val res = Resource.getDefResource(uri)
    if (res.isDefined) {
      val paths = res.get.uriPaths :+ res.get.uri
      div(nav(cls := "breadcrumb", attr("aria-label") := "breadcrumbs", ul(
        for (path <- paths) yield li(path)
      )))
    } else div()
  }

  //def openChildGraph()

  @JSExport
  def main(): Unit = {
    val model = Aadl2Awas.apply(GraphQuery.json)
    if (model.isDefined) {
      val reporter = new ConsoleTagReporter()
      val st = SymbolTable(model.get)(reporter)

      val systemGraph = FlowGraph(model.get, st)

      val systemSvg = graph2Svg(systemGraph)

      val subgraphs = getAllSubGraphs(st)

      //val subsvgs = subgraphs.map(x => (x._1, graph2Svg(x._2)))

      val mainDiv = render[Div](mainPage())

      val config = getInitLayout(st.systemDecl.compName.value, "")

      val mainBox: Element = mainDiv.querySelector("#main-container")

      val gl = new GoldenLayout(config, jQuery(mainBox))

      var res = imapEmpty[Node, String]

      document.onreadystatechange = (_: Event) => {
        document.body.appendChild(mainDiv)
        if (document.readyState == "complete") {
          val systemName = st.systemDecl.compName.value

          gl.registerComponent("system", { (container: Container, componentState: js.Dictionary[scalajs.js.Any]) => {
            if (componentState.get("graph").isDefined) {
              //val temp = componentState("graph").asInstanceOf[Node]
              //println(temp)
              container.getElement().append(render[Div](uriToBreadCrumbs(st.system))).append(systemSvg) //componentState("graph").asInstanceOf[Node])
            }
          }
          }: js.Function)

          gl.init()
          val x = gl.getComponent("system")

          // gl.root.contentItems(0).addChild(childConfig)

          println(gl.root)

          gl.root.setTitle(systemName)

          computeHeight(gl)

          SvgPanZoom("svg").svgPanZoom(js.Dictionary())

          val qButton = mainDiv.querySelectorAll("#query-button")

          for (i <- 0 until qButton.length) {
            qButton.item(i).firstChild.asInstanceOf[Anchor].onclick = (_: MouseEvent) => {
              //println("clicked")
              val anch = qButton.item(i).childNodes.item(1).asInstanceOf[Anchor]
              clear(res)
              //              res = highlight(anch.getAttribute("id"))
            }
          }
          $[Button](mainDiv, "#clear-button").onclick = (_: MouseEvent) => clear(res)
        }
      }
      window.onresize = (_: UIEvent) => computeHeight(gl)
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

  def clear(res: IMap[Node, String]): Unit = {
    res.foreach { it =>
      if (it._2.startsWith("Edge")) {
        it._1.asInstanceOf[Element].firstElementChild.setAttribute("stroke", "#000000")
        it._1.asInstanceOf[Element].firstElementChild.setAttribute("opacity", ".2")
        if (it._1.asInstanceOf[Anchor].childElementCount >= 2) {
          it._1.asInstanceOf[Anchor].children(1).setAttribute("stroke", "#000000")
          it._1.asInstanceOf[Anchor].children(1).setAttribute("fill", "#000000")
          it._1.asInstanceOf[Anchor].children(1).setAttribute("opacity", ".2")
        }

      } else if (it._2.startsWith("component") || it._2.startsWith("connection")) {
        it._1.asInstanceOf[Element].firstElementChild.setAttribute("fill", "#eeccff")
        //      } else if(it._2.startsWith("Error")) {
        //        it._1.asInstanceOf[Element].firstElementChild.setAttribute("fill", "#e6e6e6")
      } else {
        it._1.asInstanceOf[Element].firstElementChild.setAttribute("fill", "#ffffff")
      }
    }
  }

  def processSvg(svg: Element): Unit = {
    svg.setAttribute("id", "awasgraph")
    svg.setAttribute("height", "100%")
    svg.setAttribute("width", "100%")
    jQuery(svg).removeAttr("xmlns")
    val allLinks = svg.querySelectorAll("[*|href='templink']")
    for (i <- 0 until allLinks.length) {
      allLinks.item(i).asInstanceOf[Anchor].onclick = (_: MouseEvent) => false
      var tempUri = allLinks.item(i).asInstanceOf[Anchor].getAttribute("target")

      uriNodeMap = uriNodeMap + (tempUri ->
        uriNodeMap.getOrElse(tempUri, isetEmpty[Node]).+(allLinks.item(i)))

      if (tempUri.startsWith("Node+")) {
        allLinks(i).asInstanceOf[Anchor].firstElementChild.setAttribute("fill", "#eeccff")
      }


      //      if(tempUri.startsWith("Error")) {
      //        allLinks(i).asInstanceOf[Anchor].firstElementChild.setAttribute("fill", "#e6e6e6")
      //      }

      if (tempUri.startsWith("Edge")) {
        //        allLinks(i).asInstanceOf[Element].firstElementChild.setAttribute("stroke", "#a6a6a6")
        allLinks(i).asInstanceOf[Anchor].firstElementChild.setAttribute("opacity", ".2")
        if (allLinks(i).asInstanceOf[Element].childElementCount >= 2) {
          //          allLinks(i).asInstanceOf[Element].children(1).setAttribute("fill","#a6a6a6")
          //          allLinks(i).asInstanceOf[Element].children(1).setAttribute("stroke", "#a6a6a6")
          allLinks(i).asInstanceOf[Element].children(1).setAttribute("opacity", ".2")
        }
      }
    }
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
        div(cls := "hero-head is-large", id := "header", nav(cls := "navbar",
          div(cls := "navbar-brand",
            div(cls := "container", padding := "1%",
              h1(cls := "title is-2", color := "white", raw("&nbsp; Awas Witness Visualizer")))))),

      //body
        div(
          id := "body",
          cls := "hero-body", padding := "0%",
          // backgroundColor := "white",
          style := "display:inherit",
          div(id := "main-container", width := "100%", height := "100%")
        ),
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
        div(
          id := "footer", padding := "0%",
          cls := "hero-foot",
          bottom := "0",
          style := "display:inherit",
          div(cls := "container", backgroundColor := "#8e44ad",
            p(
              bottom := "10px",
              right := "10px",
              position := "absolute",
              span(color := "white", "SAnToS Laboratory, Kansas State University")
            )
          )
        )
      )
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
