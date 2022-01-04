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

import facades.{Container, FileSaver, GLUtil, GoldenLayout, Terminal}
import org.scalajs.dom
import org.scalajs.dom.ext._
import org.scalajs.dom.html.{Div, Input, Label}
import org.scalajs.dom.raw.Node
import org.scalajs.dom.{Blob, BlobPropertyBag, Event, MouseEvent, UIEvent, raw => _}
import org.scalajs.jquery.jQuery
import org.sireum.awas.Main._
import org.sireum.awas.Notification.Kind
import org.sireum.awas.analysis.FaultImpactAnalysis
import org.sireum.awas.flow.{FlowEdge, FlowGraph, FlowNode}
import org.sireum.awas.query.QueryInter
import org.sireum.awas.symbol.SymbolTable
import org.sireum.common.JSutil.{$, render}
import org.sireum.message.Message
import org.sireum.ops.ISZOps
import org.sireum.util.MessageTag
import scalatags.Text.all._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js

object QueryCli {

  var cliRegistered = false

  def openQueryCli(st: SymbolTable, graph: FlowGraph[FlowNode, FlowEdge[FlowNode]]): Boolean = {
    var queryLayout: Option[GoldenLayout] = None
    if (gl.isDefined) {

      if (gl.get.root.getComponentsByName("cli").nonEmpty) {
        val ci = gl.get.root.getItemsById("cli").head
        val stacks = gl.get.root.getItemsByType("stack")
        val si = stacks.find(si => si.contentItems.contains(ci))
        if (si.isDefined) si.get.setActiveContentItem(ci)
      } else {
        if (!cliRegistered) {
          def qcli(container: Container, componentState: js.Dictionary[scalajs.js.Any]): js.Any = {
            val termDom = //style := "display:block;width:100%;height:100%",
              div(id := "QueryCli", style := "display:block;width:100%;height:100%").render
            container.getElement().append(termDom)
            container.on(
              "resize", { () =>
                {
                  if (queryLayout.isDefined) {
                    queryLayout.get.updateSize()
                  }
                }
                container.on("close", { () =>
                  {
                    if (queryLayout.isDefined) {
                      queryLayout.get.destroy()
                    }
                    container.close()
                  }
                })

              }: js.Function
            )

          }
          gl.get.registerComponent("cli", GLUtil.componentFactory(qcli))
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

        def qtab(container: Container, componentState: js.Dictionary[scalajs.js.Any]): js.Any = {
          container.getElement().append(qBox)
        }

        queryLayout.get.registerComponent("query_table", GLUtil.componentFactory(qtab))

        def qcli(container: Container, componentState: js.Dictionary[scalajs.js.Any]) = {
          container
            .getElement()
            .append(
              span(overflow := "auto", style := "display:block;width:100%;height:100%", div(id := "term1")).render
            )
        }
        queryLayout.get.registerComponent("query_cli", GLUtil.componentFactory(qcli))
        queryLayout.get.init()
        openCliTab(st: SymbolTable)

        //treeTableAdapter()
//        val ci = gl.get.root.getItemsById("cli").head
//        ci.element.asInstanceOf[Container]

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
            .flatMap { sq =>
              sq.attributes.get("id")
            }
            .map(_.value.split(":").last)
            .toSet
          if (selectedQueries.isEmpty) {
            Notification.notify(Kind.Error, "Select queries to export")
          } else {
            val text =
              qI.get.getQueries
                .filter(it => selectedQueries.contains(it._1))
                .map(q => q._1 + " = " + q._2)
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

  def openCliTab(st: SymbolTable): Boolean = {
    if (gl.isDefined) {
      if (qI.isEmpty) {
        qI = Some(new QueryInter(st))
      }
      terminal = Some(
        Terminal("#term1").terminal({
          (cmd: String, term: Terminal) =>
            {
              val temp = qI.get.evalCmd(cmd)
              temp._2.printMessages()
              temp._1.values.foreach(c => println(c.getErrors.mkString("\n")))
              val res = Future(qI.get.evalCmd(cmd))

              res.onComplete(
                r =>
                  if (r.isSuccess) {
                    if (r.get._2.messages.isEmpty) {
                      if (r.get._1.last._2.hasErrors) {
                        term.error(
                          "Errors :" + r.get._1.last._2.getErrors
                            .map(x => x.asInstanceOf[MessageTag].message)
                            .mkString("\n")
                        )
                      } else {
                        term.echo("Computed: " + r.get._1.last._1)
                        clearAll(selections.keySet)
                        highlight(collectorToUris(r.get._1.last._2), RandomColor())
                        term.echo(
                          "Results found in graph(s): {" +
                            r.get._1.last._2.getGraphs
                              .map(_.getUri)
                              .map(_.split("\\$\\$AWAS").last.tail.replace("#", ".").replace("$", "."))
                              .mkString(", ") + "}"
                        )
                        updateTable(qI.get.getQueries, qI.get.getResults)

                      }
                    } else {
                      term.error(
                        "Parse Error :" + ISZOps(r.get._2.messages)
                          .foldLeft[String]((r: String, m: Message) => m.text.value + "\n" + r, "")
                      )
                    }
                  } else {}
              )
            }
        }: js.Function2[String, Terminal, Unit], js.Dictionary("greetings" -> "Awas Query Command Line Interface", "name" -> "AwasCli", "prompt" -> "> ", "scrollOnEcho" -> true, "onAfterRedraw" -> ({
          (t: Terminal) =>
            {
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
}
