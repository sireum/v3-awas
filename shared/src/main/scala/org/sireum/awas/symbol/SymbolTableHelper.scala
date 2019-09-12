/*
 *
 *  Copyright (c) 2017, Hariharan Thiagarajan, Kansas State University
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package org.sireum.awas.symbol

import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

object SymbolTableHelper {


  //TODO: In future if we are supporting models defined in more than one file
  //      use the below defined map to record the dependency between files
  type DependencyMap = MMap[FileResourceUri, MSet[FileResourceUri]]

  //uri seperators
  val ID_SEPARATOR = "#"
  val TYPE_SEPARATOR = ":"
  val ANCESTOR_SEPARATOR = "$"

  //dummy parent uri
  val HEAD = "head"

  //connection port ID
  val INPUT_CONN_PORT_ID = "in"
  val OUTPUT_CONN_PORT_ID = "out"

  //connection default flow ID
  val VIRTUAL_CONN_FLOW_ID = "&default_flow"

  //canonical bind port ID
  val INPUT_BIND_PORT_ID = "BIND_IN"
  val OUTPUT_BIND_PORT_ID = "BIND_OUT"

  val MODEL_TYPE = "model"

  val TYPE_TYPE = "type"
  val ALIAS_TYPE = "alias"
  val LATTICE_TYPE = "lattice"
  val ENUM_TYPE = "enum"
  val RECORD_TYPE = "record"
  val ERROR_TYPE = "error"
  val STATE_MACHINE_TYPE = "state-machine"
  val EVENT_TYPE = "event"
  val STATE_TYPE = "state"
  val COMPONENT_TYPE = "component"
  val PORT_IN_TYPE = "port-in"
  val PORT_OUT_TYPE = "port-out"
  val PORT_TYPE = "port"
  val VIRTUAL_PORT_TYPE = "virtual"
  val PORT_OUT_VIRTUAL_TYPE = "port-out-virtual"
  val PORT_IN_VIRTUAL_TYPE = "port-in-virtual"
  val BIND_PORT_TYPE = "bind"
  val PORT_IN_BIND_TYPE = "port-in-bind"
  val PORT_OUT_BIND_TYPE = "port-out-bind"
  val FLOW_TYPE = "flow"
  val BEHAVIOR_TYPE = "behavior"
  val TRANSITION_TYPE = "transition"
  val CONNECTION_TYPE = "connection"

  def isType(r: Resource): Boolean = r.uriType == TYPE_TYPE

  def isAlias(r: Resource): Boolean = r.uriType == ALIAS_TYPE

  def isLattice(r: Resource): Boolean = r.uriType == LATTICE_TYPE

  def isEnum(r: Resource): Boolean = r.uriType == ENUM_TYPE

  def isRecord(r: Resource): Boolean = r.uriType == RECORD_TYPE

  def isError(r: Resource): Boolean = r.uriType == ERROR_TYPE

  def isStateMachine(r: Resource): Boolean = r.uriType == STATE_MACHINE_TYPE

  def isEvent(r: Resource): Boolean = r.uriType == EVENT_TYPE

  def isState(r: Resource): Boolean = r.uriType == STATE_TYPE

  def isComponent(r: Resource): Boolean = r.uriType == COMPONENT_TYPE

  def isInPort(r: Resource): Boolean = r.uriType.startsWith(PORT_IN_TYPE)

  def isOutPort(r: Resource): Boolean = r.uriType.startsWith(PORT_OUT_TYPE)

  def isPort(r: Resource): Boolean = r.uriType.startsWith(PORT_TYPE)

  def isVirtual(r: Resource): Boolean = r.uriType.endsWith(VIRTUAL_PORT_TYPE)

  def isFlow(r: Resource): Boolean = r.uriType == FLOW_TYPE

  def isConnection(r: Resource): Boolean = r.uriType == CONNECTION_TYPE

  def isPort(r: ResourceUri): Boolean = {
    val t = Resource.getDefResource(r)
    t.isDefined && isPort(t.get) && r.startsWith(PORT_TYPE)
  }

  def isInPort(r: ResourceUri): Boolean = {
    val t = Resource.getDefResource(r)
    t.isDefined && isInPort(t.get)
  }

  def isOutPort(r: ResourceUri): Boolean = {
    val t = Resource.getDefResource(r)
    t.isDefined && isOutPort(t.get)
  }

  def isFlow(uri: ResourceUri): Boolean = {
    val t = Resource.getDefResource(uri)
    t.isDefined && isFlow(t.get)
  }

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

  def getUriType(uri: ResourceUri): String = {
    uri.split(TYPE_SEPARATOR).head
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

  def getErrorUri(st: SymbolTable, errorName: String): Option[ResourceUri] = {
    val cmlist = errorName.split('.')
    if (cmlist.length != 2) {
      None
    } else {
      val ttUri = st.typeDecls.find(_.endsWith(ID_SEPARATOR + cmlist.head))
      //     st.typeTable(ttUri.get).enumElements.foreach(println(_))
      if (ttUri.isDefined) {
        //        st.typeTable(ttUri.get).enumElements.foreach{
        //          f => if(f.endsWith(ID_SEPARATOR +cmlist.last)) {
        //            return Some(f)
        //          }
        //        }
        st.typeTable(ttUri.get).enumElements.find(_.endsWith(ID_SEPARATOR + cmlist.last))
      } else {
        None
      }
    }
  }

  def getCompId(st: SymbolTable, compUri: ResourceUri): Option[String] = {
    if (st.components.toSet.contains(compUri)) {
      val compRes = Resource.getResource(st.componentTable(compUri).componentDecl)
      Some(compRes.get.uri)
    } else {
      None
    }
  }

  //TODO: Add connection virtual port to st during symbol mining
  def getPortId(st: SymbolTable, elemUri: ResourceUri, portUri: ResourceUri)
  : Option[String] = {
    if (elemUri.startsWith(CONNECTION_TYPE)) {
      Some(portUri.split(ID_SEPARATOR).last)
    } else {
      val cst = st.componentTable(elemUri)
      if (cst.port(portUri).isDefined) {
        Some(Resource.getResource(cst.port(portUri).get).get.uri)
      } else {
        None
      }
    }
  }

  //returns empty string if there is no id_seperator found
  def uri2IdString(uri: ResourceUri): String = {
    val uriArray = uri.split(ID_SEPARATOR)
    if (uriArray.nonEmpty) {
      uriArray.last
    } else ""
  }

  def uri2TypeString(uri: ResourceUri): String = {
    val uriArray = uri.split(TYPE_SEPARATOR)
    if (uriArray.nonEmpty) {
      uriArray.head
    } else ""
  }

  def findUri(name: IVector[String], st: SymbolTable): Option[ResourceUri] = {
    var interUri = st.getUriFromSymbol(name.head)
    if (name.size <= 1) {
      interUri
    } else {
      name.tail.foreach { id =>
        if (interUri.isDefined &&
          (interUri.get.startsWith(COMPONENT_TYPE) ||
            interUri.get.startsWith(CONNECTION_TYPE))) {
          if (interUri.get.startsWith(COMPONENT_TYPE)) {
            interUri = st.componentTable(interUri.get).getUriFromSymbol(id)
          } else {
            val parentComp = Resource.getParentUri(interUri.get)
            if (parentComp.isDefined) {
              interUri = st.componentTable(parentComp.get).connectionTable(interUri.get).getUriFromSymbol(id)
            } else {
              interUri = None
            }
          }
        } else if (interUri.isDefined && getUriType(interUri.get) == ENUM_TYPE) {
          st.typeTable(interUri.get).getUriFromSymbol(name.last)
        } else {
          interUri = None
        }
      }
      interUri
    }
  }

  def getAllAncestors(uri: ResourceUri, st: SymbolTable): ISeq[ResourceUri] = {
    var res = ivectorEmpty[ResourceUri] :+ uri
    var curr = uri
    while ((curr != st.system) && Resource.getParentUri(uri).isDefined) {
      curr = Resource.getParentUri(curr).get
      res = res :+ curr
    }
    res.reverse
  }

  def uri2CanonicalName(uri: ResourceUri): String = {
    val resource = Resource.getDefResource(uri)
    if (resource.isDefined) {
      resource.get.toFullyQualifiedName
    } else {
      ""
    }
  }

  def findComponentUri(uri: ResourceUri, st: SymbolTable): Option[ResourceUri] = {
    val ans = getAllAncestors(uri, st)
    println(ans)
    if (ans.nonEmpty && ans.lastIndexWhere(it => getUriType(it) == COMPONENT_TYPE) > -1) {
      Some(ans(ans.lastIndexWhere(it => getUriType(it) == COMPONENT_TYPE)))
    } else {
      None
    }
  }

  def getComponentsLevelOrder(st: SymbolTable): ISeq[ResourceUri] = {
    var result = ilistEmpty[ResourceUri]
    var worklist = ilistEmpty[ResourceUri]
    worklist = worklist :+ st.system
    while (worklist.nonEmpty) {
      val current = worklist.head
      result = result :+ current
      worklist = worklist ++ st.componentTable(current).subComponents
      worklist = worklist.tail
    }
    result
  }

  def sortCompLevelOrder(st: SymbolTable, components: ISeq[ResourceUri])
  : ISeq[ResourceUri] = {
    var result = ilistEmpty[ResourceUri]
    if (components.forall(c => getUriType(c) == COMPONENT_TYPE)) {
      result = getComponentsLevelOrder(st).toList
      result.filter(c => components.contains(c))
    } else {
      result
    }
  }

}

