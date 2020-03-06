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

import org.sireum.awas.ast._
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

trait ComponentTable {
  def symbolTable: SymbolTable

  def parentUri: Option[ResourceUri]

  def componentUri: ResourceUri

  def componentDecl: ComponentDecl

  def stateMachine: Option[ResourceUri]

  def types: Iterable[ResourceUri]

  def ports: Iterable[ResourceUri]

  def port(portUri: ResourceUri): Option[Port]

  def propagation(portUri: ResourceUri): Set[ResourceUri]

  def security(portUri: ResourceUri): Option[ResourceUri]

  def flows: Iterable[ResourceUri]

  def flow(flowUri: ResourceUri): FlowTableData

  def declass(flowUri: ResourceUri): Option[(Option[ResourceUri], ResourceUri)]

  def behaviors: Iterable[ResourceUri]

  def behavior(behaviorUri: ResourceUri): BehaveExpr

  def transitions: Iterable[ResourceUri]

  def transition(transUri: ResourceUri): TransExpr

  def getFlowsFromPort(portUri: ResourceUri): Set[ResourceUri]

  def getPortsFromFlows(flowUri: ResourceUri): Set[ResourceUri]

  def subComponents: Iterable[ResourceUri]

  def subComponent(componentUri: ResourceUri): ComponentDecl

  def connections: Iterable[ResourceUri]

  def connection(connectionUri: ResourceUri): ConnectionDecl

  def deployments: Iterable[(ResourceUri, ResourceUri)]

  def deployment(nodePairUri: (ResourceUri, ResourceUri)): DeploymentDecl

  def connectionTable(connUri: ResourceUri): ConnectionTable

  def getUriFromSymbol(symbol: String): Option[ResourceUri]
}

trait ComponentTableUpdate {

  def addFlow(flowUri: ResourceUri,
              fromPortUri: Option[ResourceUri],
              toPortUri: Option[ResourceUri],
              fromFaults: Set[ResourceUri],
              toFaults: Set[ResourceUri]): Unit

}



sealed case class ComponentTableData
(declaredSymbols: MMap[FileResourceUri, MSet[ResourceUri]] = mmapEmpty,
 portTable: MMap[ResourceUri, Port] = mmapEmpty,
 propagationTable: MMap[ResourceUri, MSet[ResourceUri]] = mmapEmpty,
 securityTable: MMap[ResourceUri, ResourceUri] = mmapEmpty,
 flowTable: MMap[ResourceUri, FlowTableData] = mmapEmpty,
 declass: MMap[ResourceUri, (Option[ResourceUri], ResourceUri)] = mmapEmpty,
 flowPortRelation: MMap[ResourceUri, MSet[ResourceUri]] = mmapEmpty,
 portFlowRelation: MMap[ResourceUri, MSet[ResourceUri]] = mmapEmpty,
 behaviorTable: MMap[ResourceUri, BehaveExpr] = mmapEmpty,
 transitionTable: MMap[ResourceUri, TransExpr] = mmapEmpty,
 subComponentsDecl: MMap[ResourceUri, ComponentDecl] = mmapEmpty,
 subComponentsTable: MMap[ResourceUri, ComponentTable] = mmapEmpty,
 types: MSet[ResourceUri] = msetEmpty,
 stateMachine: MSet[ResourceUri] = msetEmpty,
 connectionTable: MMap[ResourceUri, ConnectionDecl] = mmapEmpty,
 connectionSymbolTabel: MMap[ResourceUri, ConnSTProducer] = mmapEmpty,
 deploymentDeclTable: MMap[(ResourceUri, ResourceUri), DeploymentDecl] = mmapEmpty,
 propertyDecl: MMap[ResourceUri, Property] = mmapEmpty,
 symbol2Uri: MMap[String, ResourceUri] = mmapEmpty
)

class CompSTProducer(val compUri: ResourceUri,
                     val compDecl: ComponentDecl,
                     val st: SymbolTable,
                     val parent: Option[ResourceUri] = None)
  extends ComponentTable with ComponentTableUpdate {

  val tables = ComponentTableData()

  val typeDefMap: MMap[ResourceUri, ResourceUri] = mmapEmpty[ResourceUri, ResourceUri]

  val connMap: MMap[ResourceUri, ConnectionTable] = mmapEmpty[ResourceUri, ConnectionTable]

  override def symbolTable: SymbolTable = st

  override def parentUri: Option[ResourceUri] = this.parent

  override def componentUri: ResourceUri = compUri

  override def componentDecl: ComponentDecl = this.compDecl

  override def stateMachine: Option[ResourceUri] = {
    if (tables.stateMachine.nonEmpty) {
      assert(tables.stateMachine.size == 1) //no more than one state machine can be associated with a comp
      Some(tables.stateMachine.last)
    } else None
  }

  def types: Iterable[ResourceUri] = tables.types

  override def ports: Iterable[ResourceUri] = tables.portTable.keys

  override def port(portUri: ResourceUri): Option[Port] =
    Option(tables.portTable.getOrElse(portUri, null))

  override def propagation(portUri: ResourceUri): Set[ResourceUri] = {
    if (tables.propagationTable.contains(portUri))
      tables.propagationTable(portUri).toSet
    else
      isetEmpty
  }

  override def security(portUri: ResourceUri): Option[ResourceUri] = {
    if (tables.securityTable.contains(portUri))
      Some(tables.securityTable(portUri))
    else None
  }

  override def flows: Iterable[ResourceUri] = tables.flowTable.keys

  override def flow(flowUri: ResourceUri): FlowTableData = tables.flowTable(flowUri)

  override def declass(flowUri: ResourceUri): Option[(Option[ResourceUri], ResourceUri)] = tables.declass.get(flowUri)

  override def getFlowsFromPort(portUri: ResourceUri): Set[ResourceUri] = {
    tables.flowPortRelation.getOrElse(portUri, isetEmpty).toSet
  }

  override def getPortsFromFlows(flowUri: ResourceUri): Set[ResourceUri] = {
    tables.portFlowRelation.getOrElse(flowUri, isetEmpty).toSet
  }

  override def getUriFromSymbol(symbol: String): Option[ResourceUri] = {
    tables.symbol2Uri.get(symbol)
  }

  override def subComponents: Iterable[ResourceUri] = tables.subComponentsDecl.keys

  override def subComponent(componentUri: ResourceUri): ComponentDecl = tables.subComponentsDecl(componentUri)

  override def connections: Iterable[ResourceUri] = tables.connectionTable.keys

  override def connection(connectionUri: ResourceUri): ConnectionDecl = tables.connectionTable(connectionUri)

  override def deployments: Iterable[(ResourceUri, ResourceUri)] = tables.deploymentDeclTable.keys

  override def deployment(nodePairUri: (ResourceUri, ResourceUri)): DeploymentDecl = tables.deploymentDeclTable(nodePairUri)

  def connectionTable(connUri: ResourceUri): ConnectionTable = {
    tables.connectionSymbolTabel(connUri)
  }

  //  def connectionSymbolTableProducer(curi: ResourceUri): ConnST = {
  //    assert(tables.connectionTable.keySet.contains(curi))
  //    connMap.getOrElseUpdate(curi, new ConnST(curi))
  //  }
  override def behaviors: Iterable[ResourceUri] = tables.behaviorTable.keys

  override def behavior(behaviorUri: ResourceUri): BehaveExpr = tables.behaviorTable(behaviorUri)

  override def transitions: Iterable[ResourceUri] = tables.transitionTable.keys

  override def transition(transUri: ResourceUri): TransExpr = tables.transitionTable(transUri)

  override def addFlow(flowUri: ResourceUri,
                       fromPortUri: Option[ResourceUri],
                       toPortUri: Option[ResourceUri],
                       fromFaults: Set[ResourceUri],
                       toFaults: Set[ResourceUri]): Unit = {
    if (!flows.toSet.contains(flowUri)) {
      val fdt = FlowTableData(flowUri, fromPortUri, toPortUri, fromFaults, toFaults)
      tables.flowTable + (flowUri -> fdt)

      if (fromPortUri.isDefined) {
        tables.portFlowRelation + (flowUri -> fromPortUri.get)
        tables.flowPortRelation + (fromPortUri.get -> flowUri)
      }

      if (toPortUri.isDefined) {
        tables.portFlowRelation + (flowUri -> toPortUri.get)
        tables.flowPortRelation + (toPortUri.get -> flowUri)
      }
    }
  }
}

sealed case class FlowTableData
(flowUri: ResourceUri,
 fromPortUri: Option[ResourceUri],
 toPortUri: Option[ResourceUri],
 fromFaults: Set[ResourceUri],
 toFaults: Set[ResourceUri]
) {
  override def toString: String = {
    val H = SymbolTableHelper
    var result = ""
    if (fromPortUri.isDefined) {
      result += H.uri2IdString(fromPortUri.get)
    }
    if (fromFaults.nonEmpty) {
      result += "{"
      result += fromFaults.map(H.uri2IdString).mkString(", ")
      result += "}"
    }

    if (fromPortUri.isEmpty && fromFaults.isEmpty) result += "*"

    result += "->"

    if (toPortUri.isDefined) {
      result += H.uri2IdString(toPortUri.get)
    }
    if (toFaults.nonEmpty) {
      result += "{"
      result += toFaults.map(H.uri2IdString).mkString(", ")
      result += "}"
    }

    if (toPortUri.isEmpty && toFaults.isEmpty) result += "*"

    result
  }

}