package org.sireum.awas

import facades._
import org.scalajs.dom
import org.scalajs.dom.ext._
import org.scalajs.dom.html.{Anchor, Div, Input, Table}
import org.scalajs.dom.raw.Node
import org.scalajs.dom.{raw => _, _}
import org.scalajs.jquery.jQuery
import org.sireum.awas.collector.{Collector, ResultType}
import org.sireum.awas.fptc.{FlowEdge, FlowGraph, FlowGraphUpdate, FlowNode}
import org.sireum.awas.reachability.{ErrorReachabilityImpl, PortReachabilityImpl}
import org.sireum.awas.slang.Aadl2Awas
import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.awas.witness.SvgGenerator
import org.sireum.common.JSutil._
import org.sireum.message.Message
import org.sireum.ops.ISZOps
import org.sireum.util.{imapEmpty, _}
import scalatags.Text
import scalatags.Text.all.{id, _}
import scalatags.Text.tags2.nav

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.scalajs.js.timers.SetTimeoutHandle


@JSExportTopLevel("org.sireum.awas.Main")
object Main {
  val H = SymbolTableHelper
  var uriNodeMap: IMap[String, ISet[SvgNode]] = imapEmpty[String, ISet[SvgNode]]
  var graphNodeMap: IMap[String, ISet[SvgNode]] = imapEmpty[String, ISet[SvgNode]]
  var selections: IMap[String, Boolean] = imapEmpty[String, Boolean]
  var clickCounter = 0
  var timer: Option[SetTimeoutHandle] = None
  var gl: Option[GoldenLayout] = None
  var qI: Option[QueryInter] = None

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
                ("componentState", js.Dictionary[scalajs.js.Any](("graph", inGraph), ("isTD", true))),
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
    ("componentState", js.Dictionary(("graph", graph), ("isTD", true)))
  )

  def cliConfig(): js.Dictionary[scalajs.js.Any] = js.Dictionary(
    ("title", "Awas Query"),
    ("type", "component"),
    ("componentName", "cli"),
    ("id", "cli"),
    ("componentState", js.Dictionary(("name", "awascli")))
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

  def graph2Svg(graph: FlowGraph[FlowNode, FlowNode.Edge], isLR: Boolean, st: SymbolTable): Node = {
    val dotGraph = SvgGenerator(graph.asInstanceOf[FlowGraph[FlowNode, FlowNode.Edge]
      with FlowGraphUpdate[FlowNode, FlowEdge[FlowNode]]], isLR)
    val svgString = GraphViz.Viz(dotGraph, js.Dictionary(("images", js.Array(
      js.Dictionary(("path", "min/images/sub-graph-icon.png"), ("width", "45px"), ("height", "25px"))
    ))))
    val result = templateContent(raw(svgString)).querySelectorAll("svg")(0)
    val nodes = processSvg(result.asInstanceOf[Element], st)
    graphNodeMap += (graph.getUri -> nodes)
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
          div(cls := "control", label(cls := "radio", input(`type` := "radio", name := uri, id := "td", " Top-Down ")),
            label(cls := "radio", input(`type` := "radio", name := uri, id := "lr", " Left-Right ")))
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
              val asvg = graph2Svg(graphs(uri), false, st)
              container.getElement().append(breadCrumbs).append(asvg)
              SvgPanZoom("svg").svgPanZoom(js.Dictionary())
              $[Input](breadCrumbs, ".td").checked = true

              $[Input](breadCrumbs, ".td").onclick = (_: MouseEvent) => {
                if (componentState.get("isTD").isDefined &&
                  !componentState.get("isTD").get.asInstanceOf[Boolean]) {
                  //                  $[Input](breadCrumbs, ".td").checked = true
                  container.getElement().children("svg").first().replaceWith(
                    graph2Svg(graphs(uri), false, st))
                  SvgPanZoom("svg").svgPanZoom(js.Dictionary())
                  componentState.update("isTD", true)
                }
              }
              $[Input](breadCrumbs, ".lr").onclick = (_: MouseEvent) => {
                if (componentState.get("isTD").isDefined &&
                  componentState.get("isTD").get.asInstanceOf[Boolean]) {
                  //                  $[Input](breadCrumbs, ".lr").checked = true
                  container.getElement().children("svg").first().replaceWith(
                    graph2Svg(graphs(uri), true, st))
                  SvgPanZoom("svg").svgPanZoom(js.Dictionary())
                  componentState.update("isTD", false)
                }
              }
            }
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
            openQueryCli(st)
          }

          clearButton.asInstanceOf[Anchor].onclick = (_: MouseEvent) => {
            clearAll(selections.keySet)
          }

          forwardButton.asInstanceOf[Anchor].onclick = (_: MouseEvent) => {
            forwardButtonAction(selections.keySet, st)
          }

          backwardButton.asInstanceOf[Anchor].onclick = (_: MouseEvent) => {
            backwardButtonAction(selections.keySet, st)
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

  def getPortErrorFromFlow(flowUri: ResourceUri,
                           node: FlowNode, isFrom: Boolean,
                           st: SymbolTable): ISet[ResourceUri] = {
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
          highlight(imapEmpty + (tempUri -> true))
          if (H.isFlow(tempUri)) {
            val reso = Resource.getParentUri(tempUri)
            if (reso.isDefined && FlowNode.getNode(reso.get).isDefined) {
              val toHi = getPortErrorFromFlow(tempUri, FlowNode.getNode(reso.get).get, true, st) ++
                getPortErrorFromFlow(tempUri, FlowNode.getNode(reso.get).get, isFrom = false, st)
              toHi.foreach(t => highlight(imapEmpty + (t -> true)))
            }
          }

          if (tempUri.startsWith("Edge")) {
            val ports = tempUri.split("[+]")
            if (ports.length == 3) {
              highlight(imapEmpty + (ports(1) -> true) + (ports(2) -> true))
            }
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


  def processSvg(svg: Element, st: SymbolTable): ISet[SvgNode] = {
    svg.setAttribute("id", "awasgraph")
    svg.setAttribute("height", "100%")
    svg.setAttribute("width", "100%")
    jQuery(svg).removeAttr("xmlns")
    jQuery(svg).find("title").each((e: Element) => e.parentNode.removeChild(e))
    val allLinks = org.scalajs.dom.ext.PimpedNodeList(
      svg.querySelectorAll("[*|href='templink']"))
    val svgNodes = allLinks.map(n => new SvgNodeImpl(n.asInstanceOf[Anchor], st))
    val allUri = svgNodes.map(_.getUri).toSet
    svgNodes.foreach { node =>
      uriNodeMap = uriNodeMap + (node.getUri ->
        (uriNodeMap.getOrElse(node.getUri, isetEmpty[SvgNode]) + node))
    }

    if ((allUri intersect selections.keySet).nonEmpty) {

      highlight(selections.filter(s => allUri.contains(s._1)))
    }
    svgNodes.toSet
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
        case ResultType.Port => col.getPorts ++ col.getFlows
        case ResultType.Error => col.getPortErrors.flatMap(pe =>
          isetEmpty + pe._1 ++ pe._2.map(e => "Error:" + pe._1 + ":" + e)) ++
          col.getFlows
      }
      res = res ++ (edges ++ ans).map(s => (s, false)).toMap
      res = res ++ col.getCriteria.map(c => (c, true)).toMap
    }
    res
  }

  def forwardButtonAction(criteria: ISet[String], st: SymbolTable): Unit = {
    clearAll(criteria)
    if (criteria.nonEmpty) {
      val res = criteria.flatMap { c =>
        if (c.startsWith("Error")) {
          val pe = c.substring(6)
          val ei = pe.indexOf(":error")
          val port = pe.subSequence(0, ei).toString
          val error = pe.subSequence(ei + 1, pe.size).toString
          Some(new ErrorReachabilityImpl(st).
            forwardErrorReach(port, isetEmpty + error))
        } else {
          if (c.startsWith(H.FLOW_TYPE) || c.startsWith("Edge")) {
            None
          } else {
            Some(new PortReachabilityImpl(st).forwardReach(c))
          }
        }
      }
      res.foreach(r => highlight(collectorToUris(r)))
      // val resColl = res.foldLeft(Collector(st))(_.union(_))
      //highlight(collectorToUris())
    }
  }

  def backwardButtonAction(criteria: ISet[String], st: SymbolTable): Unit = {
    clearAll(criteria)
    if (criteria.nonEmpty) {
      val res = criteria.flatMap { c =>
        if (c.startsWith("Error")) {
          val pe = c.substring(6)
          val ei = pe.indexOf(":error")
          val port = pe.subSequence(0, ei).toString
          val error = pe.subSequence(ei + 1, pe.size).toString
          Some(new ErrorReachabilityImpl(st).
            backwardErrorReach(port, isetEmpty + error))
        } else {
          Some(new PortReachabilityImpl(st).backwardReach(c))
        }
      }
      res.foreach(r => highlight(collectorToUris(r)))
      //highlight(collectorToUris(res.foldLeft(Collector(st))(_.union(_))))
    }

  }

  def highlight(uris: IMap[String, Boolean]): Boolean = {
    selections = selections ++ uris
    uris.foreach(u => if (uriNodeMap.keySet.contains(u._1)) uriNodeMap(u._1).foreach(_.select(u._2)))
    false
  }

  def mainPage(): Frag = {
    //    val temp: Seq[(String, String)] = GraphQuery.queryExp.toSeq
    div(
      id := "view",
      width := "100%",
      div(
        cls := "hero",
        div(cls := "hero-head is-large", id := "header", nav(cls := "navbar",
          //div(cls := "container",
          div(cls := "navbar-brand", //backgroundColor:="primary",
            div(cls := "navbar-item", padding := "1%",
              h1(cls := "title is-2",
                color := "white", span(whiteSpace := "nowrap", "Awas Witness Visualizer"))),
            div(cls := "navbar-burger burger", attr("data-target") := "nav-menu-buttons",
              span(aria.hidden := "true"),
              span(aria.hidden := "true"),
              span(aria.hidden := "true")
          )),
          div(cls := "navbar-menu", id := "nav-menu-buttons",
            div(cls := "navbar-start"),
            div(cls := "navbar-end",
              div(cls := "navbar-item",
                div(cls := "field is-grouped",
                  p(cls := "control", a(cls := "button is-outlined", id := "forward-button",
                    span("Forward"))),
                  p(cls := "control", a(cls := "button is-outlined", id := "backward-button",
                    span("Backward"))),
                  p(cls := "control", a(cls := "button is-outlined", id := "clear-button",
                    span("Clear"))),
                  p(cls := "control", a(cls := "button is-outlined", id := "query-button",
                  span("Awas Query"))))
              ))) //)))
          //)
        ))),

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
      nav(id := "footer", cls := "navbar", bottom := "0", style := "display:inherit",
        div(cls := "navbar-brand",
          p(bottom := "10px", right := "10px", position := "absolute",
            span(color := "white",
              "SAnToS Laboratory, Kansas State University"))))

    )
  }

  def updateTable(entries: ILinkedMap[String, String],
                  results: ILinkedMap[String, Collector]): Unit = {
    def entryToFrag(entry: (String, String)): ISeq[Node] = {
      var res = ilistEmpty[Node]
      res = res :+ render[Node](tr(
        attr("data-tt-id") := "node" + entry._1,
        attr("data-tt-parent-id") := "",
        td(//display.`inline-table`,
          verticalAlign := "middle",
          padding := "0", //flexWrap.nowrap,
          //cls := "columns",

          a(textAlign := "left",
            padding := "0",
            justifyContent := "left",
            verticalAlign := "middle",
            id := entry._1,
            style := "display:inline-flex;width:88%;",
            //            display.`table-cell`,
            //            width.auto,
            cls := "query-table-button button is-white",
            span(entry._1)
          )
        ),
        td(verticalAlign := "middle", span(entry._2))
      ))
      if (results(entry._1).getPaths.size > 0) {
        for (i <- results(entry._1).getPaths.toIndexedSeq.indices) {
          res = res :+ render[Node](tr(
            attr("data-tt-id") := "node" + entry._1 + ":" + i,
            attr("data-tt-parent-id") := "node" + entry._1,
            td(
              verticalAlign := "middle",
              padding := "0",
              //flexWrap.nowrap,
              //cls := "columns is-mobile",
              a(
                textAlign := "left",
                padding := "0",
                justifyContent := "left",
                verticalAlign := "middle",
                id := entry._1 + ":" + i,
                style := "display:inline-flex;width:80%;",
                cls := "query-table-button button is-white",
                span(entry._1 + "(path " + (i + 1) + ")")
              )
            ), td(verticalAlign := "middle", span(entry._2))
          ))
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
    val buttons = PimpedNodeList(table.querySelectorAll(".query-table-button"))
    buttons.foreach { b =>
      val id = b.asInstanceOf[Anchor].getAttribute("id")
      b.asInstanceOf[Anchor].onclick = (_: MouseEvent) => {
        if (id.contains(":")) {
          highlight(collectorToUris(results(id.split(":").head)
            .getPaths.toIndexedSeq(id.split(":").last.toInt)))
        } else {
          highlight(collectorToUris(results(id)))
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
    TreeTable("#query-table").treetable(js.Dictionary.apply[js.Any](
      ("expandable", true),
      ("indenterTemplate", "<span style='display:run-in; vertical-align: middle; width:11%' class=\"indenter\"></span>"),
      ("expanderTemplate", "<a href=\"#\" style='vertical-align: middle; display:inline-block; width:3%;'><i class=\"fa fa-chevron-right\" aria-hidden=\"true\"></i></a>"),
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
    ), true)
  }

  def openCliTab(st: SymbolTable): Boolean = {
    if (gl.isDefined) {
      if (qI.isEmpty) {
        qI = Some(new QueryInter(st))
      }
      //gl.get.root.contentItems(0).addChild(cliConfig())
      Terminal("#term1").terminal({ (cmd: String, term: Terminal) => {

        val res = Future(qI.get.evalCmd(cmd))

        res.onComplete(r =>
          if (r.isSuccess) {
            if (r.get._2.messages.isEmpty) {
              if (r.get._1.last._2.hasErrors) {
                term.echo("Errors :" + r.get._1.last._2.getErrors.map(x =>
              x.asInstanceOf[MessageTag].message).mkString("\n"))
          } else {
                term.echo("Computed: " + r.get._1.last._1)
            clearAll(selections.keySet)
                highlight(collectorToUris(r.get._1.last._2))
            term.echo("Results found in graph(s): {" +
              r.get._1.last._2.getGraphs.map(_.getUri).map(_.split("\\$\\$AWAS").last).mkString(", ") + "}")
            updateTable(qI.get.getQueries, qI.get.getResults)

          }
        } else {
              term.echo("Parse Error :" + ISZOps(r.get._2.messages).
            foldLeft[String]((r: String, m: Message) => m.text.value + "\n" + r, ""))
            }
          } else {})
      }
      }: js.Function2[String, Terminal, Unit], js.Dictionary(
        "greetings" -> "Awas Query Command Line Interface",
        "name" -> "AwasCli",
        "prompt" -> "> ",
        "scrollOnEcho" -> true,
        "onAfterRedraw" -> ({ (t: Terminal) => {
          t.find("textarea").blur().focus()
        }
        }: js.ThisFunction0[Terminal, Unit])
      )).resize()
      val x = $[Div](".terminal-output")
      x.setAttribute("overflow", "auto")
    }
    false
  }

  private def openQueryCli(st: SymbolTable): Boolean = {
    var queryLayout: Option[GoldenLayout] = None
    if (gl.isDefined) {
      if (gl.get.root.getItemsById("cli").nonEmpty) {
        val ci = gl.get.root.getItemsById("cli").head
        val stacks = gl.get.root.getItemsByType("stack")
        val si = stacks.find(si => si.contentItems.contains(ci))
        if (si.isDefined) si.get.setActiveContentItem(ci)
      } else {
        gl.get.registerComponent("cli", { (container: Container,
                                           componentState: js.Dictionary[scalajs.js.Any]) => {
          val termDom = //style := "display:block;width:100%;height:100%",
            div(id := "QueryCli", style := "display:block;width:100%;height:100%").render
          container.getElement().append(termDom)
          container.on("resize", { () => {
            if (queryLayout.isDefined) {
              queryLayout.get.updateSize(scalajs.js.undefined, scalajs.js.undefined)
            }
          }
          }: js.Function)

        }
        }: js.Function)
        gl.get.root.contentItems(0).addChild(cliConfig())
        val queryCli: Div = $[Div]("#QueryCli")
        val queryCliConfig = js.Dictionary(
          ("settings", js.Dictionary(//("hasHeaders", false),
            //("reorderEnabled", false),
            ("showPopoutIcon", false),
            ("showMaximiseIcon", false),
            ("showCloseIcon", false))),
          (
            "content",
            js.Array(
              js.Dictionary(("type", "column"),
                ("content",
                  js.Array(
                    js.Dictionary(
                      ("title", "Table"),
                      ("type", "component"),
                      ("componentName", "query_table"),
                      ("id", "qtable"),
                      ("componentState", js.Dictionary()),
                      ("isClosable", false)
                    ), js.Dictionary(
                      ("title", "CLI"),
                      ("type", "component"),
                      ("componentName", "query_cli"),
                      ("id", "qcli"),
                      ("componentState", js.Dictionary()),
                      ("isClosable", false)
                    )
                  )
                )
              )
            )
          )
        )

        queryLayout = Some(new GoldenLayout(queryCliConfig, jQuery(queryCli)))
        val qBox = render[Div](queryBox())

        queryLayout.get.registerComponent("query_table", { (container: Container, componentState: js.Dictionary[scalajs.js.Any]) => {
          container.getElement().append(qBox)
        }
        }: js.Function)
        queryLayout.get.registerComponent("query_cli", { (container: Container, componentState: js.Dictionary[scalajs.js.Any]) => {
          container.getElement().append(span(overflow := "auto", style := "display:block;width:100%;height:100%",
            div(id := "term1")).render)
        }
        }: js.Function)
        queryLayout.get.init()
        openCliTab(st: SymbolTable)

        //treeTableAdapter()
        val ci = gl.get.root.getItemsById("cli").head
        ci.element.asInstanceOf[Container]


        val inputImport = qBox.querySelector("#import-queries")
        inputImport.asInstanceOf[Input].onchange = (_: Event) => {
          val files = inputImport.asInstanceOf[Input].files
          val reader = new dom.FileReader()
          var queries = ""

          reader.onload = (_: UIEvent) => {

            queries = reader.result.asInstanceOf[String]
//            println(queries)
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
//          println("exporter")
          val text = qI.get.getQueries.map(q => q._1 + " = " + q._2).mkString("\n")
          val filename = st.systemDecl.compName.value + ".aq"
          val blob = new Blob(js.Array(text), BlobPropertyBag("text/plain;charset=utf-8"))
          FileSaver.saveAs(blob, filename)
        }
      }
    }
    false
  }

  private def queryBox(): Frag = {
    div(
      id := "query-box",
      cls := "box",
      overflow := "auto",
      style := "display:block;width:100%;height:100%",
      nav(
        cls := "level",
        div(cls := "level-left", div(cls := "level-item", h2(cls := "subtitle", "Queries"))),
        div(cls := "level-right",
          div(cls := "level-item", div(cls := "file",
            label(cls := "file-label", input(cls := "file-input", `type` := "file", id := "import-queries",
              name := "import", span(cls := "file-cta", span(cls := "file-icon",
                i(cls := "fas fa-upload")), span(cls := "file-label", "Import")))))),


          //            button(id := "import-queries", cls := "button", "Import")),
          div(cls := "level-item", div(cls := "file",
            label(cls := "file-label", input(cls := "file-input", `type` := "button", id := "export-queries",
              name := "import", span(cls := "file-cta", span(cls := "file-icon",
                i(cls := "fas fa-download")), span(cls := "file-label", "Export"))))))



          //            button(id := "export-queries", cls := "button", "Export"))
        )
      ),
      queryTableBuild()
    )
  }

  private def queryTableBuild(): Text.TypedTag[String] =
    table(
      id := "query-table",
      cls := "table is-striped is-narrow is-fullwidth",
      border := "0",
      borderSpacing := "0",
      col(width := "40%"),
      thead(th(cls := "is-5", "Name"), th("Expression"))
      //      SeqNode(temp.map { query =>
      //        tr(
      //          attr("data-tt-id") := "node" + query._1,
      //          if (query._1.contains(":")) attr("data-tt-parent-id") := "node" + query._1.split(":").head
      //          else attr("data-tt-parent-id") := "",
      //          td(
      //            verticalAlign := "middle",
      //            padding := "0",
      //        //    id := "query-button",
      //            a(
      //              textAlign := "left",
      //              padding := "0",
      //              justifyContent := "left",
      //              verticalAlign := "middle",
      //              id := query._1,
      //              style := "display:inline-flex; width:90%; ",
      //              if (temp.indexOf(query) % 2 == 1)
      //                cls := "button is-light "
      //              else
      //                cls := "button is-white ",
      //              span(query._1)
      //            )
      //          ),
      //          td(verticalAlign := "middle", span(query._2))
      //        )
      //      })
    )
}
