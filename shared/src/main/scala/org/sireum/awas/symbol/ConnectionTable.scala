package org.sireum.awas.symbol

import org.sireum.awas.ast.ConnectionDecl
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.{MMap, MSet, isetEmpty, mmapEmpty, msetEmpty}

trait ConnectionTable {
  def symbolTable: SymbolTable

  def connection: ConnectionDecl

  def connectionUri: ResourceUri

  def parentUri: ResourceUri

  def ports: Iterable[ResourceUri]

  def propagation(portUri: ResourceUri): Set[ResourceUri]

  def flows: Iterable[ResourceUri]

  def flow(flowUri: ResourceUri): FlowTableData

  def getFlowsFromPort(portUri: ResourceUri): Set[ResourceUri]

  def getPortsFromFlows(flowUri: ResourceUri): Set[ResourceUri]

  def getUriFromSymbol(symbol: String): Option[ResourceUri]

  def fromCompUri: Option[ResourceUri]

  def toCompUri: Option[ResourceUri]

  def fromPort: Option[ResourceUri]

  def toPort: Option[ResourceUri]
}

sealed case class ConnectionTableData
(portTable: MSet[ResourceUri] = msetEmpty,
 propagationTable: MMap[ResourceUri, MSet[ResourceUri]] = mmapEmpty,
 flowTable: MMap[ResourceUri, FlowTableData] = mmapEmpty,
 flowPortRelation: MMap[ResourceUri, MSet[ResourceUri]] = mmapEmpty,
 portFlowRelation: MMap[ResourceUri, MSet[ResourceUri]] = mmapEmpty,
 symbol2Uri: MMap[String, ResourceUri] = mmapEmpty
)

class ConnSTProducer(val connUri: ResourceUri,
                     val connDecl: ConnectionDecl,
                     val st: SymbolTable,
                     val parent: ResourceUri) extends ConnectionTable {
  val tables = ConnectionTableData()

  override def fromCompUri: Option[ResourceUri] = connDecl.fromComp match {
    case Some(comp) => Resource.getResource(comp).map(_.toUri)
    case None => None
  }

  override def toCompUri: Option[ResourceUri] = connDecl.toComp match {
    case Some(comp) => Resource.getResource(comp).map(_.toUri)
    case None => None
  }

  override def fromPort: Option[ResourceUri] = Resource.getResource(connDecl.fromPort).map(_.toUri)

  override def toPort: Option[ResourceUri] = Resource.getResource(connDecl.toPort).map(_.toUri)

  override def symbolTable: SymbolTable = st

  override def connection: ConnectionDecl = connDecl

  override def parentUri: ResourceUri = this.parent

  override def ports: Iterable[ResourceUri] = tables.portTable

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

  override def connectionUri: ResourceUri = connUri
}