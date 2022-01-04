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

import org.scalajs.dom
import org.scalajs.dom.html.{Anchor, Button, Div, Input}
import org.scalajs.dom.raw.{Event, MouseEvent}
import org.sireum.awas.witness.{Errors, JSON, RankDir, SvgGenConfig}
import org.sireum.common.JSutil.$
import org.sireum.{B, F, T}
import scalatags.JsDom.styles.display
import scalatags.generic._

import scala.scalajs.js

object SettingsView {
  val key = "AWAS-WV-SETTINGS"

  var currentConfig: SvgGenConfig =
    if (getStoredSettings.isDefined)
      getStoredSettings.get
    else SvgGenConfig.defaultConfig

  def preProcess(settingsNode: Div): Unit = {
    val viewsTab = $[Anchor](settingsNode, "#views-tab")
    val connectionsTab = $[Anchor](settingsNode, "#conn-tab")
    val viewBody = $[Div](settingsNode, "#view-options")
    val connectionBody = $[Div](settingsNode, "#server-options")

    //view elements
    val tdNode = $[Input](settingsNode, "#std")
    val lrNode = $[Input](settingsNode, "#slr")
    val conn = $[Input](settingsNode, "#sconn")
    //    val ports = $[Input](settingsNode, "#vports")
    val flows = $[Input](settingsNode, "#vflows")
    //    val errors = $[Input](settingsNode, "#verrors")
    val eNone = $[Input](settingsNode, "#eNone")
    val eErrors = $[Input](settingsNode, "#eErrors")
    val eTypes = $[Input](settingsNode, "#eTypes")
    val bind = $[Input](settingsNode, "#vbind")

    //connection elements
    val connStatus = $[Input](settingsNode, "#connStatus")
    val connButton = $[Button](settingsNode, "#connDis")
    val highlightInstMdl = $[Input](settingsNode, "#hinstmdl")

    val apply = $[Anchor](settingsNode, "#settings_apply")
    val cancel = $[Anchor](settingsNode, "#settings_cancel")
    val lattice = $[Div](settingsNode, "#lattice")

    viewsTab.onclick = (_: MouseEvent) => {
      if (viewBody.style.display == display.none.v) {
        viewBody.setAttribute("style", "display: block;")
        connectionBody.setAttribute("style", "display: none;")
        connectionsTab.parentElement.classList.remove("is-active")
        viewsTab.parentElement.classList.add("is-active")
      }
    }

    connectionsTab.onclick = (_: MouseEvent) => {
      if (connectionBody.style.display == display.none.v) {
        connectionBody.setAttribute("style", "display: block;")
        viewBody.setAttribute("style", "display: none;")
        viewsTab.parentElement.classList.remove("is-active")
        connectionsTab.parentElement.classList.add("is-active")
      }
    }

    tdNode.onclick = (_: MouseEvent) => {
      if (tdNode.checked) {
        lrNode.checked = false
      }
    }

    lrNode.onclick = (_: MouseEvent) => {
      if (lrNode.checked) {
        tdNode.checked = false
      }
    }

    eNone.onclick = (_: MouseEvent) => {
      if (eNone.checked) {
        eErrors.checked = false
        eTypes.checked = false
        showOrHideLegend(lattice, false)
      }
    }

    eErrors.onclick = (_: MouseEvent) => {
      if (eErrors.checked) {
        eNone.checked = false
        eTypes.checked = false
        showOrHideLegend(lattice, false)
      }
    }

    eTypes.onclick = (_: MouseEvent) => {
      if (eTypes.checked) {
        eNone.checked = false
        eErrors.checked = false
        showOrHideLegend(lattice, true)

      }
    }

    if (currentConfig.rankDir == RankDir.TB) {
      tdNode.checked = true
    } else {
      lrNode.checked = true
    }

    if (currentConfig.simpleConn) conn.checked = true
//    if (currentConfig.viewVirtualPorts) ports.checked = true
    if (currentConfig.viewErrors == Errors.None) {
      eNone.checked = true
      showOrHideLegend(lattice, false)
    } else if (currentConfig.viewErrors == Errors.Errors) {
      showOrHideLegend(lattice, false)
      eErrors.checked = true
    } else {
      showOrHideLegend(lattice, true)
      eTypes.checked = true
    }


    dom.window.setInterval({ () => {
      if (PetiConnHandler.isConnectionAlive) {
        connStatus.innerText = "Connected"
        connButton.firstElementChild.innerText = "Disconnect"
        connButton.classList.remove("is-success")
        connButton.classList.add("is-danger")
      } else {
        connStatus.innerText = "Not connected"
        connButton.firstElementChild.innerText = "Connect"
        connButton.classList.remove("is-danger")
        connButton.classList.add("is-success")
      }
    }
    }: js.Function0[Any], 1000)

    connButton.onclick = (_: MouseEvent) => {
      if (connButton.firstElementChild.innerText == "Disconnect") {
        PetiConnHandler.disconnect()
      } else {
        PetiConnHandler.reconnect()
      }
    }

    if (currentConfig.highlightInstMdl) highlightInstMdl.checked = true


    if (currentConfig.viewFlows) flows.checked = true
    if (currentConfig.bindings) bind.checked = true

    apply.onclick = (_: MouseEvent) => {
      apply.classList.add("is-loading")
      scalajs.js.timers.setTimeout(0)({

        val config = SvgGenConfig(
          if (tdNode.checked) RankDir.TB else RankDir.LR,
          B(conn.checked),
          T,
          if (eNone.checked) Errors.None else if (eErrors.checked) Errors.Errors else Errors.Types,
          B(flows.checked),
          B(bind.checked),
          F,
          F,
          B(highlightInstMdl.checked)
        )

        if (currentConfig != config) {
          currentConfig = config
          //          if(currentConfig.viewErrors == Errors.Types) {
          //            SecViolations.secInfoFlow = Some(SecInfoFlowAnalysis())
          //          }
          setStoredSettings(currentConfig)
          Util.reDrawGraphs(currentConfig)
          Main.viewHideViolations()
        }
        apply.classList.remove("is-loading")
      })
    }
  }

  def showOrHideLegend(lattice: Div, toShow: Boolean): Unit = {
    if (toShow) {
      lattice.setAttribute("style", "display: block; text-align: center;")
      if (lattice.lastElementChild == null) {
        lattice.appendChild(SecViolations.apply().getLattice)
      } else {
        //        val l = SecViolations.apply().getLattice
        //        if (!lattice.lastElementChild.isEqualNode(l)) {
        //          lattice.appendChild(SecViolations.apply().getLattice)
        //        }
        lattice.removeChild(lattice.lastElementChild)
        lattice.setAttribute("style", "display: none;")
      }
    } else {
      lattice.removeChild(lattice.lastElementChild)
      lattice.setAttribute("style", "display: none;")
    }
  }

  def setStoredSettings(cfg: SvgGenConfig): Unit = {
    dom.window.localStorage.setItem(key, JSON.fromSvgGenConfig(cfg, T).value)
  }

  def getStoredSettings: Option[SvgGenConfig] = {
    val configJSON = dom.window.localStorage.getItem(key)
    if (configJSON != null) {
      val config = JSON.toSvgGenConfig(configJSON)
      if (config.leftOpt.nonEmpty) {
        Some(config.left)
      } else {
        None
      }
    } else {
      None
    }
  }
}
