package org.sireum.awas.peti

trait PetiEndPoint {
  val host: String
  var port: Int
}

trait PetiLocalEndPoint extends PetiEndPoint {
  //self: PetiEndPoint =>
  override val host: String = "localhost"
  override var port: Int = 8080
}
