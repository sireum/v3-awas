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
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.awas.witness.RankDir
import scalatags.Text
import scalatags.Text.all._
import scalatags.Text.tags2.nav

import scala.scalajs.js

object Views {

  def getInitLayout(title: String, inGraph: String): js.Dictionary[scalajs.js.Any] = js.Dictionary(
    ("settings", js.Dictionary(("showPopoutIcon", false), ("showCloseIcon", false))),
    (
      "content",
      js.Array(
        js.Dictionary(
          ("type", "stack"),
          (
            "content",
            js.Array(
              js.Dictionary(
                ("title", title),
                ("type", "component"),
                ("componentName", "system"),
                ("id", inGraph),
                (
                  "componentState",
                  js.Dictionary[scalajs.js.Any](
                    ("graph", inGraph),
                    ("isTD", SettingsView.currentConfig.rankDir == RankDir.TB)
                  )
                ),
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
    ("componentState", js.Dictionary(("graph", graph), ("isTD", SettingsView.currentConfig.rankDir == RankDir.TB)))
  )

  def cliConfig(): js.Dictionary[scalajs.js.Any] = js.Dictionary(
    ("title", "Awas Query"),
    ("type", "component"),
    ("componentName", "cli"),
    ("id", "cli"),
    ("componentState", js.Dictionary(("name", "awascli")))
  )

  def violationsConfig(): js.Dictionary[scalajs.js.Any] = js.Dictionary(
    ("title", "Violations"),
    ("type", "component"),
    ("componentName", "violation"),
    ("id", "violation")
  )

  def queryCliConfig = js.Dictionary(
    (
      "settings",
      js.Dictionary( //("hasHeaders", false),
        //("reorderEnabled", false),
        ("showPopoutIcon", false),
        ("showMaximiseIcon", false),
        ("showCloseIcon", false)
      )
    ),
    (
      "content",
      js.Array(
        js.Dictionary(
          ("type", "column"),
          (
            "content",
            js.Array(
              js.Dictionary(
                ("title", "Table"),
                ("type", "component"),
                ("componentName", "query_table"),
                ("id", "qtable"),
                ("componentState", js.Dictionary()),
                ("isClosable", false)
              ),
              js.Dictionary(
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


  def mainPage(): Frag = {
    //    val temp: Seq[(String, String)] = GraphQuery.queryExp.toSeq
    div(
      id := "view",
      width := "100%",
      div(
        cls := "hero",
        div(
          cls := "hero-head is-large",
          id := "header",
          nav(
            cls := "navbar",
            //div(cls := "container",
            div(
              cls := "navbar-brand", //backgroundColor:="primary",
              div(
                cls := "navbar-item",
                padding := "1%",
                h1(cls := "title is-2", color := "white", span(whiteSpace := "nowrap", "Awas Witness Visualizer"))
              ),
              div(
                cls := "navbar-burger burger",
                attr("data-target") := "nav-menu-buttons",
                span(aria.hidden := "true"),
                span(aria.hidden := "true"),
                span(aria.hidden := "true")
              )
            ),
            div(
              cls := "navbar-menu",
              id := "nav-menu-buttons",
              div(cls := "navbar-start"),
              div(
                cls := "navbar-end",
                div(
                  cls := "navbar-item",
                  div(
                    cls := "field is-grouped",
                    p(cls := "control", a(cls := "button is-outlined", id := "sec-violation-button", span("Violations"))),
                    p(cls := "control", a(cls := "button is-outlined", id := "forward-button", span("Forward"))),
                    p(cls := "control", a(cls := "button is-outlined", id := "backward-button", span("Backward"))),
                    p(cls := "control", a(cls := "button is-outlined", id := "clear-button", span("Clear"))),
                    p(cls := "control", a(cls := "button is-outlined", id := "query-button", span("Awas Query"))),
                    p(
                      cls := "control",
                      verticalAlign := "center",
                      display.flex,
                      alignItems.center,
                      a(
                        cls := "icon is-medium has-text-white",
                        id := "settings-button",
                        attr("data-show") := "quickview",
                        attr("data-target") := "quickviewDefault",
                        i(cls := "fas fa-cog fa-2x", aria.hidden := "true")
                      )
                    )
                  )
                )
              )
            ) //)))
            //)
          )
        )
      ),
      //body
      div(
        id := "body",
        cls := "hero-body",
        padding := "0%",
        // backgroundColor := "white",
        style := "display:inherit", //quickView(),
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
      nav(
        id := "footer",
        cls := "navbar",
        bottom := "0",
        style := "display:inherit",
        div(
          cls := "navbar-brand",
          p(
            bottom := "10px",
            right := "10px",
            position := "absolute",
            span(color := "white", "SAnToS Laboratory, Kansas State University")
          )
        )
      )
    )
  }

  def stpaMain(): Frag = {
    div(
      id := "stpa",
      cls := "container",
      tag("section")(cls := "hero is-light is-fullheight",
        div(cls := "hero-head", id := "header",
          nav(cls := "level",
            div(cls := "level-item has-text-centered",
              paddingTop := "2%",
              h1(
                cls := "title is-1",
                id := "title",
                textAlign := "center",
                "STPA Report"
              )
            )

          )
        ),
        hr(cls := "hr", backgroundColor := "black"),
        div(cls := "hero-body", alignItems := "flex-start",
          div(id := "body")
        ),
        hr(cls := "hr", backgroundColor := "black"),
        div(cls := "hero-foot",
          nav(id := "foot", cls := "level", p(
            cls := "level-item has-text-centered",
            span("SAnToS Laboratory, Kansas State University")
          )
          )
        )
      )

    )
  }

  def queryBox(): Frag = {
    div(
      id := "query-box",
      cls := "box",
      overflow := "auto",
      style := "display:block;width:100%;height:100%",
      nav(
        cls := "level",
        div(cls := "level-left", div(cls := "level-item", h2(cls := "subtitle", "Queries"))),
        div(
          cls := "level-right",
          div(
            cls := "level-item",
            div(
              cls := "file",
              label(
                cls := "file-label",
                input(
                  cls := "file-input",
                  `type` := "file",
                  id := "import-queries",
                  name := "import",
                  span(
                    cls := "file-cta",
                    span(cls := "file-icon", i(cls := "fas fa-upload")),
                    span(cls := "file-label", "Import")
                  )
                )
              )
            )
          ),
          //            button(id := "import-queries", cls := "button", "Import")),
          div(
            cls := "level-item",
            div(
              cls := "file",
              label(
                cls := "file-label",
                input(
                  cls := "file-input",
                  `type` := "button",
                  id := "export-queries",
                  name := "import",
                  span(
                    cls := "file-cta",
                    span(cls := "file-icon", i(cls := "fas fa-download")),
                    span(cls := "file-label", "Export")
                  )
                )
              )
            )
          ),
          div(
            cls := "level-item",
            div(
              cls := "file",
              label(
                cls := "file-label",
                input(
                  cls := "file-input",
                  `type` := "button",
                  id := "gen-queries",
                  name := "gen",
                  span(
                    cls := "file-cta",
                    span(cls := "file-icon", i(cls := "fas fa-magic")),
                    span(cls := "file-label", "Generate FIA")
                  )
                )
              )
            )
          ),
          div(
            cls := "level-item",
            div(
              cls := "file",
              label(
                cls := "file-label",
                input(
                  cls := "file-input",
                  `type` := "button",
                  id := "remove-queries",
                  name := "remove",
                  span(
                    cls := "file-cta",
                    span(cls := "file-icon", i(cls := "fas fa-minus-square")),
                    span(cls := "file-label", "Remove")
                  )
                )
              )
            )
          )
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
      col(width := "5%"),
      thead(
        th(div(textAlign := "center", label(`class` := "checkbox", input(`type` := "checkbox", id := "select-all")))),
        th(cls := "is-5", textAlign := "center", "Color"),
        th(cls := "is-5", "Name"),
        th("Expression")
      )
    )

  def violationsTableBuild(): Text.TypedTag[String] =
    table(
      id := "violations-table",
      cls := "table is-striped is-narrow is-fullwidth",
      border := "0",
      borderSpacing := "0",
      col(width := "10%"),
      thead(
        th(cls := "is-5", "Name"),
        th("Expression")
      )
    )

  def quickView(): Frag = div(
      id := "quickviewDefault",
      cls := "quickview",
      header(
        cls := "quickview-header is-primary",
        p(cls := "title", b("View Options"))
        //span(cls := "delete", attr("data-dismiss") := "quickview")
      ),
      div(
        cls := "quickview-body",
        div(
          cls := "quickview-block",
          div(
            cls := "section", //div(cls:="notification is-white",
            div(
              cls := "field",
              label(cls := "label", "Orientation"),
              div(
                cls := "control",
                label(cls := "radio", input(`type` := "radio", id := "std", " Top-Down ")),
                label(cls := "radio", input(`type` := "radio", id := "slr", " Left-Right "))
              )
            ),
            div(
              cls := "field",
              label(cls := "label", "View simple connections"),
              div(cls := "control", label(cls := "checkbox", input(`type` := "checkbox", id := "sconn")))
            ),
//            div(
//              cls := "field",
//              label(cls := "label", "View ports"),
//              div(cls := "control", label(cls := "checkbox", input(`type` := "checkbox", id := "vports")))
//            ),
            div(
              cls := "field",
              label(cls := "label", "View flows"),
              div(cls := "control", label(cls := "checkbox", input(`type` := "checkbox", id := "vflows")))
            ),
            div(
              cls := "field",
              label(cls := "label", "View errors"),
              div(
                cls := "control",
                label(cls := "radio", input(`type` := "radio", id := "eNone", " None ")),
                label(cls := "radio", input(`type` := "radio", id := "eErrors", " EMv2 Errors ")),
                label(cls := "radio", input(`type` := "radio", id := "eTypes", " Security Types "))
              )
            ),
            div(
              cls := "field",
              label(cls := "label", "View binding edges"),
              div(cls := "control", label(cls := "checkbox", input(`type` := "checkbox", id := "vbind")))
            ),
            div(
              cls := "field is-grouped is-grouped-centered",
              p(
                cls := "control",
                a(cls := "button is-primary", id := "settings_apply", attr("data-dismiss") := "quickview", "Apply")
              ),
              p(
                cls := "control",
                a(cls := "button is-light", id := "settings_cancel", attr("data-dismiss") := "quickview", "Cancel")
              )
            ),
            div(id := "lattice", display.none,
              div(id := "lattice-title", cls := "is-divider", attr("data-content") := "Lattice"),
            )
          )
        )
      ),
      footer(cls := "quickview-footer")
    )
}
