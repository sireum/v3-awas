package org.sireum.awas

import facades._
import org.scalajs.dom.html.{Anchor, Button, Div}
import org.scalajs.dom.raw.Node
import org.scalajs.dom.{raw => _, _}
import org.scalajs.jquery.jQuery
import org.sireum.common.JSutil._
import org.sireum.util.{imapEmpty, _}

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scalatags.Text
import scalatags.Text.all.{id, _}
import scalatags.Text.tags2.nav

@JSExportTopLevel("org.sireum.awas.Main")
object Main {

  var uriNodeMap: IMap[String, Node] = imapEmpty[String, Node]

  @JSExport
  def main(): Unit = {
    var svg = GraphViz.Viz(GraphQuery.graph)
    val mainDiv = render[Div](mainPage())

    val graphDiv = mainDiv.querySelector("#graph-view")
    val tempElem = templateContent(raw(svg)).querySelectorAll("svg")(0)
    processSvg(tempElem.asInstanceOf[Element])

    graphDiv.appendChild(tempElem)
    val qButton = mainDiv.querySelectorAll("#query-button")
    var res = imapEmpty[Node, String]
    for (i <- 0 until qButton.length) {
      qButton.item(i).firstChild.asInstanceOf[Anchor].onclick = (_: MouseEvent) => {
        val anch = qButton.item(i).childNodes.item(1).asInstanceOf[Anchor]
        clear(res)
        res = highlight(anch.getAttribute("id"))
      }
    }
    $[Button](mainDiv, "#clear-button").onclick = (_: MouseEvent) => clear(res)

    document.onreadystatechange = (_: Event) => {
      document.body.appendChild(mainDiv)
      SvgPanZoom("svg").svgPanZoom(js.Dictionary())
      TreeTable("#query-table").treetable(js.Dictionary.apply[js.Any](
        ("expandable", true),
        ("indenterTemplate", "<span style='display:run-in; vertical-align: middle' class=\"indenter\"></span>"),
        ("expanderTemplate", "<a href=\"#\" style='vertical-align: middle'><i class=\"fa fa-chevron-right\" aria-hidden=\"true\"></i></a>"),
        ("onNodeExpand", { (x: js.Any) => {
          val exp = x.asInstanceOf[TreeNode].expander(0)
          val oldChild = exp.firstElementChild
          val newChild = templateContent(
            raw("<i class=\"fa fa-chevron-down\" aria-hidden=\"true\"></i>")).asInstanceOf[Element]
          exp.appendChild(newChild)
          exp.removeChild(oldChild)
        }
        }: js.ThisFunction),
        ("onNodeCollapse", { (x: js.Any) => {
          val exp = x.asInstanceOf[TreeNode].expander(0)
          val oldChild = exp.firstElementChild
          val newChild = templateContent(
            raw("<i class=\"fa fa-chevron-right\" aria-hidden=\"true\"></i>")).asInstanceOf[Element]
          exp.appendChild(newChild)
          exp.removeChild(oldChild)
        }
        }: js.ThisFunction)
      ))
      computeHeight()
    }
    window.onresize = (_: UIEvent) => computeHeight()
  }


  def computeHeight(): Unit = {
    val totalHeight = window.innerHeight
    val headHeight = $[Div]("#header").clientHeight
    val footHeight = $[Div]("#footer").clientHeight
    val bodyHeight = totalHeight - (headHeight + footHeight + 23)
    val graphHeight = (bodyHeight / 2) - 75
    val queryHeight = bodyHeight / 2
    val graph = $[Div]("#graph-view")
    graph.style.height = s"${graphHeight}px"
    val query = $[Div]("#query-box")
    query.style.height = s"${queryHeight}px"
  }

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

      uriNodeMap = uriNodeMap + (tempUri -> allLinks.item(i))

      if (tempUri.startsWith("component") || tempUri.startsWith("connection")) {
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


  def highlight(queryName: String): IMap[Node, String] = {
    var res = imapEmpty[Node, String]
    val qres = GraphQuery.queryRes.toMap.get(queryName)
    val crit = GraphQuery.queryCriteria.toMap.get(queryName)
    if (qres.isDefined) {
      qres.get.toVector.foreach { ruri =>
        if (uriNodeMap.contains(ruri)) {
          if (ruri.startsWith("Edge")) {
            res += uriNodeMap(ruri) -> ruri
            uriNodeMap(ruri).asInstanceOf[Anchor].firstElementChild.setAttribute("stroke", "#ff0000")
            uriNodeMap(ruri).asInstanceOf[Anchor].firstElementChild.setAttribute("opacity", ".8")
            if (uriNodeMap(ruri).asInstanceOf[Anchor].childElementCount >= 2) {
              uriNodeMap(ruri).asInstanceOf[Anchor].children(1).setAttribute("stroke", "#ff0000")
              uriNodeMap(ruri).asInstanceOf[Anchor].children(1).setAttribute("fill", "#ff0000")
              uriNodeMap(ruri).asInstanceOf[Anchor].children(1).setAttribute("opacity", ".8")
            }
          } else if (ruri.startsWith("Error")) {
            res += uriNodeMap(ruri) -> ruri
            uriNodeMap(ruri).asInstanceOf[Anchor].firstElementChild.setAttribute("fill", "#ff9999")
          } else {
            res += uriNodeMap(ruri) -> ruri
            uriNodeMap(ruri).asInstanceOf[Anchor].firstElementChild.setAttribute("fill", "#b3daff")
          }
        }
      }
    }
    if (crit.isDefined) {
      crit.get.toVector.foreach { ruri =>
        if (uriNodeMap.contains(ruri)) {
          res += uriNodeMap(ruri) -> ruri
          uriNodeMap(ruri).asInstanceOf[Anchor].firstElementChild.setAttribute("fill", "#008080")
        }
      }
    }
    res
  }

  def mainPage(): Frag = {
    val temp: Seq[(String, String)] = GraphQuery.queryExp.toSeq
    div(id := "view", width := "100%",
      div(cls := "hero is-primary", id := "header",
        div(cls := "hero-body",
          div(cls := "container",
            h1(cls := "title", raw("&nbsp; Awas Witness Visualizer"))))),
      //body
      div(cls := "container",
        div(cls := "tile is-ancestor",
          div(cls := "tile is-parent is-vertical",
            div(cls := "tile is-child",
              div(id := "graph-box", cls := "box",
                h2(cls := "subtitle", "Dependence Graph"),
                figure(id := "graph-view", cls := "image ")
              )),
            div(cls := "tile is-child",
              div(id := "query-box", cls := "box", overflow := "scroll",
                nav(cls := "level", div(cls := "level-left",
                  div(cls := "level-item", h2(cls := "subtitle", "Queries"))),
                  div(cls := "level-right", div(cls := "level-item",
                    button(id := "clear-button", cls := "button", "Clear")))),
                queryTableBuild(temp)
              ))))),
      //footer
      div(id := "footer", cls := "nav", bottom := "0",
        div(cls := "nav-item",
          p(bottom := "10px", right := "10px", position := "absolute",
            span(color := "white",
              "SAnToS Laboratory, Kansas State University"))))
    )
  }

  private def queryTableBuild(temp: Seq[(String, String)]): Text.TypedTag[String] =
    table(id := "query-table", cls := "table is-striped is-narrow is-fullwidth",
      border := "0", borderSpacing := "0",
      col(width := "40%"), thead(th(cls := "is-5", "Name"), th("Expression")),
      SeqNode(temp.map {
        query =>
          tr(attr("data-tt-id") := "node" + query._1,
            if (query._1.contains(":")) attr("data-tt-parent-id") := "node" + query._1.split(":").head
            else attr("data-tt-parent-id") := "",
            td(verticalAlign := "middle", padding := "0", id := "query-button",
              a(textAlign := "left", padding := "0", justifyContent := "left", verticalAlign := "middle",
                id := query._1, style := "display:inline-flex; width:90%; ",
                if (temp.indexOf(query) % 2 == 1)
                  cls := "button is-light "
                else
                  cls := "button is-white ",
                span(query._1))),
            td(verticalAlign := "middle", span(query._2)))
      }))
}
