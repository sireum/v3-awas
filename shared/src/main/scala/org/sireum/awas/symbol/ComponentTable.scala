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

  def flows: Iterable[ResourceUri]

  def flow(flowUri: ResourceUri): FlowTableData

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

sealed case class ComponentTableData
(declaredSymbols: MMap[FileResourceUri, MSet[ResourceUri]] = mmapEmpty,
 portTable: MMap[ResourceUri, Port] = mmapEmpty,
 propagationTable: MMap[ResourceUri, MSet[ResourceUri]] = mmapEmpty,
 flowTable: MMap[ResourceUri, FlowTableData] = mmapEmpty,
 flowPortRelation: MMap[ResourceUri, MSet[ResourceUri]] = mmapEmpty,
 portFlowRelation: MMap[ResourceUri, MSet[ResourceUri]] = mmapEmpty,
 subComponentsDecl: MMap[ResourceUri, ComponentDecl] = mmapEmpty,
 subComponentsTable: MMap[ResourceUri, ComponentTable] = mmapEmpty,
 types: MSet[ResourceUri] = msetEmpty,
 stateMachine: MSet[ResourceUri] = msetEmpty,
 connectionTable: MMap[ResourceUri, ConnectionDecl] = mmapEmpty,
 connectionSymbolTabel: MMap[ResourceUri, ConnSTProducer] = mmapEmpty,
 deploymentDeclTable: MMap[(ResourceUri, ResourceUri), DeploymentDecl] = mmapEmpty,
 symbol2Uri: MMap[String, ResourceUri] = mmapEmpty
)

class CompSTProducer(val compUri: ResourceUri,
                     val compDecl: ComponentDecl,
                     val st: SymbolTable,
                     val parent: Option[ResourceUri] = None)
  extends ComponentTable {

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

  override def flows: Iterable[ResourceUri] = tables.flowTable.keys

  override def flow(flowUri: ResourceUri): FlowTableData = tables.flowTable(flowUri)

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