/*
 Copyright (c) 2017, Hariharan Thiagarajan, Robby, Kansas State University
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

package org.sireum.awas.symbol

import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

object SymbolTableHelper {

  //TODO: In future if we are supporting models defined in more than one file
  //      use the below defined map to record the dependency between files
  type DependencyMap = MMap[FileResourceUri, MSet[FileResourceUri]]

  //uri id seperator
  val ID_SEPARATOR = "#"

  //dummy parent uri
  val HEAD = "head"

  val MODEL_TYPE = "model"

  val TYPE_TYPE = "type"

  def isType(r: Resource): Boolean = r.uriType == TYPE_TYPE

  val ALIAS_TYPE = "alias"

  def isAlias(r: Resource): Boolean = r.uriType == ALIAS_TYPE

  val LATTICE_TYPE = "lattice"

  def isLattice(r: Resource): Boolean = r.uriType == LATTICE_TYPE

  val ENUM_TYPE = "enum"

  def isEnum(r: Resource): Boolean = r.uriType == ENUM_TYPE

  val RECORD_TYPE = "record"

  def isRecord(r: Resource): Boolean = r.uriType == RECORD_TYPE

  val ERROR_TYPE = "error"

  def isError(r: Resource): Boolean = r.uriType == ERROR_TYPE

  val STATE_MACHINE_TYPE = "state-machine"

  def isStateMachine(r: Resource): Boolean = r.uriType == STATE_MACHINE_TYPE

  val EVENT_TYPE = "event"

  def isEvent(r: Resource): Boolean = r.uriType == EVENT_TYPE

  val STATE_TYPE = "state"

  def isState(r: Resource): Boolean = r.uriType == STATE_TYPE

  val COMPONENT_TYPE = "component"

  def isComponent(r: Resource): Boolean = r.uriType == COMPONENT_TYPE

  val PORT_IN_TYPE = "port-in"

  def isInPort(r: Resource): Boolean = r.uriType.startsWith(PORT_IN_TYPE)

  val PORT_OUT_TYPE = "port-out"

  def isOutPort(r: Resource): Boolean = r.uriType.startsWith(PORT_OUT_TYPE)

  val PORT_TYPE = "port"

  def isPort(r: Resource): Boolean = r.uriType.startsWith(PORT_TYPE)

  val VIRTUAL_PORT_TYPE = "virtual"

  val PORT_OUT_VIRTUAL_TYPE = "port-out-virtual"

  val PORT_IN_VIRTUAL_TYPE = "port-in-virtual"

  def isVirtual(r: Resource): Boolean = r.uriType.endsWith(VIRTUAL_PORT_TYPE)

  val FLOW_TYPE = "flow"

  def isFlow(r: Resource): Boolean = r.uriType == FLOW_TYPE

  val CONNECTION_TYPE = "connection"

  def isConnection(r: Resource): Boolean = r.uriType == CONNECTION_TYPE

  //dot separated canonical resource name, TODO: rewrite later with the model name
  def getUriFromString(st: SymbolTable, completeName: String): Option[ResourceUri] = {
    val cmlist = completeName.split('.')
    if (cmlist.nonEmpty && cmlist.length == 1) {
      st.components.find(_.endsWith(ID_SEPARATOR + cmlist.last))
    } else if (cmlist.length > 1) {
      getPortUri(st, completeName)
    } else {
      None
    }
  }

  def getPortUri(st: SymbolTable, completeName: String): Option[ResourceUri] = {
    val cmlist = completeName.split('.')
    if (cmlist.length >= 2) {
      val comp = st.components.find(_.endsWith(ID_SEPARATOR + cmlist(cmlist.length - 2)))
      if (comp.isDefined)
        st.componentTable(comp.get).ports.find(_.endsWith(ID_SEPARATOR + cmlist.last))
      else
        None
    } else {
      None
    }
  }
}

