package org.sireum.awas.peti

import upickle.default._

object ProtocolSerializer {
  def apply(protocol : Protocol) : String = {
    write(protocol)
  }

  def unapply(arg: String): Option[Protocol] = Some(read[Protocol](arg))
}
