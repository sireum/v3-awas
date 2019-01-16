package org.sireum.awas
import facades.QuickView
import org.scalajs.dom.html.{Anchor, Div, Input}
import org.sireum.awas.witness.{JSON, RankDir, SvgGenConfig}
import org.scalajs.dom
import org.scalajs.dom.raw.MouseEvent
import org.sireum.{B, T}
import org.sireum.common.JSutil.$

object SettingsView {
  val key = "AWAS-WV-SETTINGS"

  var currentConfig: SvgGenConfig =
    if (getStoredSettings.isDefined)
      getStoredSettings.get
    else SvgGenConfig.defaultConfig

  def preProcess(settingsNode: Div): Unit = {
    val tdNode = $[Input](settingsNode, "#std")
    val lrNode = $[Input](settingsNode, "#slr")
    val conn = $[Input](settingsNode, "#sconn")
//    val ports = $[Input](settingsNode, "#vports")
    val flows = $[Input](settingsNode, "#vflows")
    val errors = $[Input](settingsNode, "#verrors")
    val bind = $[Input](settingsNode, "#vbind")
    val apply = $[Anchor](settingsNode, "#settings_apply")
    val cancel = $[Anchor](settingsNode, "#settings_cancel")

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

    if (currentConfig.rankDir == RankDir.TB) {
      tdNode.checked = true
    } else {
      lrNode.checked = true
    }

    if (currentConfig.simpleConn) conn.checked = true
//    if (currentConfig.viewVirtualPorts) ports.checked = true
    if (currentConfig.viewErrors) errors.checked = true
    if (currentConfig.viewFlows) flows.checked = true
    if (currentConfig.bindings) bind.checked = true

    apply.onclick = (_: MouseEvent) => {
      val config = SvgGenConfig(
        if (tdNode.checked) RankDir.TB else RankDir.LR,
        B(conn.checked),
        T,
        B(errors.checked),
        B(flows.checked),
        B(bind.checked)
      )

      if (currentConfig != config) {
        currentConfig = config
        setStoredSettings(currentConfig)
        Util.reDrawGraphs(currentConfig)
      }

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
