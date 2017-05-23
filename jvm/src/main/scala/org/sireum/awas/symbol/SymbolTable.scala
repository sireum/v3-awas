/*
 Copyright (c) 2016, Robby, Kansas State University
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

import org.sireum.awas.ast._
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

object SymbolTable {

  def apply(n: Model)(
    implicit reporter: AccumulatingTagReporter): SymbolTable =
    buildSymbolTable(n)

  def buildSymbolTable(m: Model)(
    implicit reporter: AccumulatingTagReporter): SymbolTable = {
    val stp = SymbolMiner(m, new STProducer())
    stp.toSymbolTable
  }
}

trait SymbolTable {
  def typeDecls: Iterable[ResourceUri]

  def typeDecl(tdUri: ResourceUri): TypeDecl

  def typeTable(tdUri: ResourceUri): TypeTable

  def stateMachines: Iterable[ResourceUri]

  def stateMachine(smUri: ResourceUri): StateMachineDecl

  def smTable(smuri : ResourceUri) : StateMachineTable

  def components: Iterable[ResourceUri]

  def component(componentUri: ResourceUri): ComponentDecl

  def componentTable(compUri: ResourceUri): ComponentSymbolTable

  def connections: Iterable[ResourceUri]

  def connection(connectionUri: ResourceUri): ConnectionDecl

  def compStateMachine(compUri: ResourceUri): Option[ResourceUri]

  def constants: Iterable[ResourceUri]

  def constant(constUri: ResourceUri): ConstantDecl

  def uris(file: FileResourceUri): MSet[ResourceUri]

  def compTypeDecl(compUri: ResourceUri): Option[ResourceUri]
}

sealed case class SymbolTableData
(declaredSymbols: MMap[FileResourceUri, MSet[ResourceUri]] = mmapEmpty,
 typeDeclTable: MMap[ResourceUri, TypeDecl] = mmapEmpty,
 typeTable: MMap[ResourceUri, TypeTable] = mmapEmpty,
 stateMachineDeclTable: MMap[ResourceUri, StateMachineDecl] = mmapEmpty,
 stateMachineTable: MMap[ResourceUri, StateMachineTable] = mmapEmpty,
 componentDeclTable: MMap[ResourceUri, ComponentDecl] = mmapEmpty,
 componentSymbolTable: MMap[ResourceUri, ComponentSymbolTable] = mmapEmpty,
 connectionTable: MMap[ResourceUri, ConnectionDecl] = mmapEmpty,
 compSMTable: MMap[ResourceUri, ResourceUri] = mmapEmpty,
 compTypeTable: MMap[ResourceUri, ResourceUri] = mmapEmpty,
 constTable: MMap[ResourceUri, ConstantDecl] = mmapEmpty
)

class STProducer extends SymbolTable {
  //TODO: make sure, mutable collections are not exposed by the traits
  st =>
  val tables = SymbolTableData()

  val compSymbolTableMap = mmapEmpty[ResourceUri, CompST]

  val compMap = mmapEmpty[ResourceUri, CompST]

  val modelMap = mmapEmpty[ResourceUri, Model]

  def stateMachineTableProducer(smUri : ResourceUri) : SMT = {
    val smt = new SMT(smUri)
    assert(tables.stateMachineDeclTable.contains(smUri))

    tables.stateMachineTable(smUri) = smt
    smt
  }

  override def typeDecls: Iterable[ResourceUri] = tables.typeDeclTable.keys

  override def typeDecl(tdUri: ResourceUri): TypeDecl = tables.typeDeclTable(tdUri)

  override def components: Iterable[ResourceUri] = tables.componentDeclTable.keys

  override def component(componentUri: ResourceUri): ComponentDecl = tables.componentDeclTable(componentUri)

  override def connections: Iterable[ResourceUri] = tables.connectionTable.keys

  override def connection(connectionUri: ResourceUri): ConnectionDecl = tables.connectionTable(connectionUri)

  override def stateMachines: Iterable[ResourceUri] = tables.stateMachineDeclTable.keys

  override def stateMachine(smUri: ResourceUri): StateMachineDecl = tables.stateMachineDeclTable(smUri)

  override def constants: Iterable[ResourceUri] = tables.constTable.keys

  override def constant(constUri: ResourceUri): ConstantDecl = tables.constTable(constUri)

  override def uris(file: FileResourceUri): MSet[ResourceUri] = tables.declaredSymbols(file)

  override def typeTable(tdUri: ResourceUri): TypeTable = {
    tables.typeTable(tdUri)
  }

  override def componentTable(compUri: ResourceUri): ComponentSymbolTable = {
    val cST = componentSymbolTableProducer(compUri)
    tables.componentSymbolTable(compUri) = cST
    cST
  }

  def componentSymbolTableProducer(curi: ResourceUri): CompST = {
    assert(tables.componentDeclTable.contains(curi))
    compMap.getOrElseUpdate(curi, new CompST(curi))
  }

  override def compStateMachine(compUri: ResourceUri): Option[ResourceUri] = {
    tables.compSMTable.get(compUri)
  }

  override def smTable(smUri: ResourceUri): StateMachineTable = {
    tables.stateMachineTable(smUri)
  }

  def toSymbolTable: SymbolTable = this

  override def compTypeDecl(compUri: ResourceUri): Option[ResourceUri] = {
    tables.compTypeTable.get(compUri)
  }

  class TypeT(val typeDeclUri: ResourceUri) extends TypeTable {

    val tables = TypeTableData()

    override def uri = typeDeclUri

    override def symbolTable: SymbolTable = st

    override def enumElements: Set[ResourceUri] = tables.enumTable(typeDeclUri).keySet.toSet

    override def enumElement(enumElemUri: ResourceUri) =
      tables.enumTable(typeDeclUri)(enumElemUri)

  }

  class SMT(val smDeclUri: ResourceUri) extends StateMachineTable {
    val tables = StateMachineData()
    override def symbolTable: SymbolTable = st

    override def states: Seq[ResourceUri] = tables.statesTable.keys.toSeq

    override def events: Iterable[ResourceUri] = tables.eventsTable.keys

    override def state(stateUri: ResourceUri): Node = tables.statesTable(stateUri)

    override def event(eventUri: ResourceUri): Node = tables.eventsTable(eventUri)
  }

  class CompST(val compUri: ResourceUri) extends ComponentSymbolTable {
    val tables = ComponentTableData()

    val typeDefMap = mmapEmpty[ResourceUri, ResourceUri]

    override def symbolTable: SymbolTable = st

    override def component: ComponentDecl = st.tables.componentDeclTable(compUri)

    override def stateMachine: StateMachineDecl = {
      st.tables.stateMachineDeclTable(st.tables.compSMTable(compUri))
    }

    override def ports: Iterable[ResourceUri] = tables.portTable.keys

    override def port(portUri: ResourceUri): Option[Port] =
      Option(tables.portTable.getOrElse(portUri, null))


    override def propagation(portUri: ResourceUri): Set[ResourceUri] = {
      if(tables.propagationTable.contains(portUri))
        tables.propagationTable(portUri).toSet
      else
        isetEmpty
    }

    override def flows: Iterable[ResourceUri] = tables.flowTable.keys

    override def flow(flowUri: ResourceUri): Flow = tables.flowTable(flowUri)

    override def flowRelate(portUri: ResourceUri): Set[ResourceUri] = {
      tables.flowPortRelation.getOrElse(portUri, isetEmpty).toSet
    }
  }
}

trait ComponentSymbolTable {
  def symbolTable: SymbolTable

  def component: ComponentDecl

  def stateMachine: StateMachineDecl

  def ports: Iterable[ResourceUri]

  def port(portUri: ResourceUri): Option[Port]

  def propagation(portUri: ResourceUri): Set[ResourceUri]

  def flows: Iterable[ResourceUri]

  def flow(flowUri: ResourceUri): Flow

  def flowRelate(portUri: ResourceUri): Set[ResourceUri]
}

sealed case class ComponentTableData
(declaredSymbols: MMap[FileResourceUri, MSet[ResourceUri]] = mmapEmpty,
 portTable: MMap[ResourceUri, Port] = mmapEmpty,
 propagationTable: MMap[ResourceUri, MSet[ResourceUri]] = mmapEmpty,
 flowTable: MMap[ResourceUri, Flow] = mmapEmpty,
 flowPortRelation: MMap[ResourceUri, MSet[ResourceUri]] = mmapEmpty
)

trait TypeTable {
  def uri : ResourceUri

  def symbolTable: SymbolTable

  def enumElements: Set[ResourceUri]

  def enumElement(enumElemUri : ResourceUri) : Id
}

sealed case class TypeTableData
(enumTable: MMap[ResourceUri, MMap[ResourceUri, Id]] = mmapEmpty)

trait StateMachineTable {
  def symbolTable: SymbolTable

  def states: Seq[ResourceUri]

  def state(stateUri : ResourceUri) : Node

  def events: Iterable[ResourceUri]

  def event(eventUri : ResourceUri) : Node
}

sealed case class StateMachineData
(statesTable: MLinkedMap[ResourceUri, Node] = mlinkedMapEmpty,
 eventsTable: MMap[ResourceUri, Node] = mmapEmpty
)