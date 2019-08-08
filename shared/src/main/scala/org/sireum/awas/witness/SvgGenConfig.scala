// #Sireum

package org.sireum.awas.witness

import org.sireum._

@datatype class SvgGenConfig(
                              rankDir: RankDir.Type,
                              simpleConn: B,
                              //complexConn: B,
                              viewVirtualPorts: B,
                              viewErrors: B,
                              viewFlows: B,
                              bindings: B,
                              behaviors: B,
                              states: B
)

@enum object RankDir {
  'TB
  'LR
  'BT
  'RL
}

object SvgGenConfig {

  def defaultConfig: SvgGenConfig = {
    SvgGenConfig(RankDir.TB, F, T, T, T, F, F, F)
  }
}
