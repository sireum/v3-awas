package org.sireum.awas

import facades.{GraphQuery, GraphViz, SvgPanZoom}
import org.scalajs.dom.html.{Anchor, Button, Div}
import org.scalajs.dom.raw.Node
import org.scalajs.dom.{raw => _, _}
import org.scalajs.jquery.jQuery
import org.sireum.common.JSutil._
import org.sireum.util._

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scalatags.Text.all._
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
    var res = isetEmpty[Node]
    println(qButton.length)
    for (i <- 0 until qButton.length) {
      qButton.item(i).firstChild.asInstanceOf[Anchor].onclick = (_: MouseEvent) => {
        val anch = qButton.item(i).firstChild.asInstanceOf[Anchor]
        println("clicked : \n" + anch.innerHTML)
        clear(res)
        res = highlight(anch.getAttribute("id"))
      }
    }
    $[Button](mainDiv, "#clear-button").onclick = (_: MouseEvent) => clear(res)
    document.onreadystatechange = (_: Event) => {
      document.body.appendChild(mainDiv)
      SvgPanZoom("svg").svgPanZoom(js.Dictionary())
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

  def clear(res: ISet[Node]): Unit = {
    res.foreach(_.asInstanceOf[Element].firstElementChild.setAttribute("fill", "#ffffff"))
  }

  def processSvg(svg: Element): Unit = {
    svg.setAttribute("id", "awasgraph")
    svg.setAttribute("height", "100%")
    svg.setAttribute("width", "100%")
    jQuery(svg).removeAttr("xmlns")
    //println(jQuery("[*|href]", svg).get(0))
    println($$[Anchor]("[*|href='templink']").nonEmpty)
    val allLinks = svg.querySelectorAll("[*|href='templink']")
    for (i <- 0 until allLinks.length) {
      allLinks.item(i).asInstanceOf[Anchor].onclick = (_: MouseEvent) => false
      uriNodeMap = uriNodeMap + (allLinks.item(i).asInstanceOf[Anchor].getAttribute("target") -> allLinks.item(i))
    }
  }

  def highlight(queryName: String): ISet[Node] = {
    var res = isetEmpty[Node]
    val qres = GraphQuery.queryRes.toMap.get(queryName)
    if (qres.isDefined) {
      qres.get.toVector.foreach { ruri =>
        if (uriNodeMap.contains(ruri)) {
          uriNodeMap(ruri).asInstanceOf[Anchor].firstElementChild.setAttribute("fill", "#ffd1b3")
          res += uriNodeMap(ruri)
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
                  div(cls := "level-right", div(cls := "level-item", button(id := "clear-button", cls := "button", "Clear")))),
                table(cls := "table is-striped is-narrow is-fullwidth", border := "0", borderSpacing := "0", id := "qtable",
                  col(width := "40%"), thead(th(cls := "is-5", "Name"), th("Expression")),
                  SeqNode(temp.map {
                    query =>
                      tr(
                        td(verticalAlign := "middle", padding := "0", id := "query-button",
                          a(textAlign := "left", padding := "0", justifyContent := "left",
                            id := query._1,
                            if (temp.indexOf(query) % 2 == 1)
                              cls := "button is-light is-fullwidth"
                            else
                              cls := "button is-white is-fullwidth",
                            span(paddingLeft := "20px", query._1))),
                        td(verticalAlign := "middle", span(query._2)))
                  })
                )
              ))))),
      //footer
      div(id := "footer", cls := "nav", bottom := "0",
        div(cls := "nav-item",
          p(bottom := "10px", right := "10px", position := "absolute",
            span(color := "white",
              "SAnToS Laboratory, Kansas State University"))))
    )
  }
}
