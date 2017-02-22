/*
 Copyright (c) 2017, Robby, Kansas State University
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice, this
    list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sireum.awas.fptc

import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.isetEmpty

class BasicNodeImpl(uri : ResourceUri, st : SymbolTable) extends BasicNode {

  val H = SymbolTableHelper

  var portList = isetEmpty[ResourceUri]

  if (isComponent) {
    val compST = st.componentTable(uri)
    portList ++= compST.ports.toSeq
  } else {
    val conDecl = st.connection(uri)

    val inPortUri = Resource(H.PORT_IN_VIRTUAL_TYPE,
      Resource.getResource(conDecl).get,
      conDecl.fromComp.value.map(_.value).mkString("/") + "/" + conDecl.fromPort.value,
      Some(true))

    val outPortUri = Resource(H.PORT_OUT_VIRTUAL_TYPE,
      Resource.getResource(conDecl).get,
      conDecl.toComp.value.map(_.value).mkString("/") + "/" + conDecl.toPort.value,
      Some(true))

    portList += inPortUri.toUri
    portList += outPortUri.toUri
  }

  override def getResourceType: String = if(uri.startsWith(H.COMPONENT_TYPE)) H.COMPONENT_TYPE
  else H.CONNECTION_TYPE

  override def getUri: ResourceUri = uri

  override def ports: Iterable[ResourceUri] = portList

  override def inPorts: Iterable[ResourceUri] = ports.filter(_.startsWith(H.PORT_IN_TYPE))

  override def outPorts: Iterable[ResourceUri] = ports.filter(_.startsWith(H.PORT_OUT_TYPE))


  override def getPropagation(port: ResourceUri): Set[ResourceUri] = {
    if(getResourceType == H.COMPONENT_TYPE) {
      val compST = st.componentTable(uri)
      compST.propagation(port)
    } else {
      isetEmpty[ResourceUri]
    }
  }

  override def isComponent: Boolean = uri.startsWith(H.COMPONENT_TYPE)
}
