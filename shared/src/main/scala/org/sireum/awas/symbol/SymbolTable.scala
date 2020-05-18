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
import upickle.default.{macroRW, ReadWriter => RW}

object SymbolTable {

  private var symbolTable: Option[SymbolTable] = None
  private var currModel: Option[Model] = None

  def apply(n: Model)(
    implicit reporter: AccumulatingTagReporter): SymbolTable = {
    if (symbolTable.isDefined && currModel.isDefined && currModel.get == n) {
      symbolTable.get
    } else {
      val st = buildSymbolTable(n)
      symbolTable = Some(st)
      currModel = Some(n)
      st
    }
  }

  def getTable: Option[SymbolTable] = symbolTable

  private def buildSymbolTable(m: Model)(
    implicit reporter: AccumulatingTagReporter): SymbolTable = {
    val stp = SymbolMiner(m, new STProducer())
    stp.toSymbolTable
  }

  //  implicit def rw: RW[SymbolTable] = RW.merge(STProducer.rw)

}

trait SymbolTable {
  def typeDecls: Iterable[ResourceUri]

  def typeDecl(tdUri: ResourceUri): TypeDecl

  def typeTable(tdUri: ResourceUri): TypeTable

  def typeAlias(typeUri: ResourceUri): ISet[ResourceUri]

  def stateMachines: Iterable[ResourceUri]

  def stateMachine(smUri: ResourceUri): StateMachineDecl

  def smTable(smuri: ResourceUri): StateMachineTable

  def system: ResourceUri

  def systemDecl: ComponentDecl

  def systemTable: Option[ComponentTable]

  def components: Iterable[ResourceUri]

  def componentTable(compUri: ResourceUri): ComponentTable

  def compStateMachine(compUri: ResourceUri): Option[ResourceUri]

  def constants: Iterable[ResourceUri]

  def constant(constUri: ResourceUri): ConstantDecl

  def uris(file: FileResourceUri): MSet[ResourceUri]

  def computeDeployment() : Unit

  def removeDeployments() : Unit

  def forwardDeployment(uri : ResourceUri) : ISet[ResourceUri]

  def backwardDeployment(uri : ResourceUri) : ISet[ResourceUri]

  def compTypeDecl(compUri: ResourceUri): Set[ResourceUri]

  def getUriFromSymbol(symbol: String): Option[ResourceUri]
}


sealed case class SymbolTableData
(declaredSymbols: MMap[FileResourceUri, MSet[ResourceUri]] = mmapEmpty,
 typeDeclTable: MLinkedMap[ResourceUri, TypeDecl] = mlinkedMapEmpty,
 typeTable: MMap[ResourceUri, TypeTable] = mmapEmpty,
 aliasTable: MMap[ResourceUri, MSet[ResourceUri]] = mmapEmpty,
 stateMachineDeclTable: MMap[ResourceUri, StateMachineDecl] = mmapEmpty,
 stateMachineTable: MMap[ResourceUri, StateMachineTable] = mmapEmpty,
 // systemUri : Option[(ResourceUri, ComponentDecl)] = None,
 // systemTable: Option[ComponentSymbolTable] = None,
 componentDeclTable: MMap[ResourceUri, ComponentDecl] = mmapEmpty,
 componentSymbolTable: MMap[ResourceUri, ComponentTable] = mmapEmpty,
 compSMTable: MMap[ResourceUri, ResourceUri] = mmapEmpty,
 compTypeTable: MMap[ResourceUri, MSet[ResourceUri]] = mmapEmpty,
 constTable: MMap[ResourceUri, ConstantDecl] = mmapEmpty,
 forwardDeployment : MMap[ResourceUri, ISet[ResourceUri]] = mmapEmpty,
 backwardDeployment : MMap[ResourceUri, ISet[ResourceUri]] = mmapEmpty,
 symbol2Uri: MMap[String, ResourceUri] = mmapEmpty
)


case class STProducer(var systemUri: Option[ResourceUri] = None,
                 var systemD: Option[ComponentDecl] = None) extends SymbolTable {
  //TODO: make sure, mutable collections are not exposed by the traits
  st =>
  val tables = SymbolTableData()

  val compSymbolTableMap: MMap[ResourceUri, ComponentTable] = mmapEmpty[ResourceUri, ComponentTable]

  //  val compMap: MMap[ResourceUri, ComponentTable] = mmapEmpty[ResourceUri, ComponentTable]

  val connMap: MMap[ResourceUri, ComponentTable] = mmapEmpty[ResourceUri, ComponentTable]

  val deployMap: MSet[(ResourceUri, ResourceUri)] = msetEmpty[(ResourceUri, ResourceUri)]

  val modelMap: MMap[ResourceUri, Model] = mmapEmpty[ResourceUri, Model]

  def stateMachineTableProducer(smUri: ResourceUri): SMT = {
    val smt = new SMT(smUri)
    assert(tables.stateMachineDeclTable.contains(smUri))

    tables.stateMachineTable(smUri) = smt
    smt
  }

  override def typeDecls: Iterable[ResourceUri] = tables.typeDeclTable.keys

  override def typeDecl(tdUri: ResourceUri): TypeDecl = tables.typeDeclTable(tdUri)

  override def system: ResourceUri = this.systemUri.get

  override def systemDecl: ComponentDecl = this.systemD.get

  override def stateMachines: Iterable[ResourceUri] = tables.stateMachineDeclTable.keys

  override def stateMachine(smUri: ResourceUri): StateMachineDecl = tables.stateMachineDeclTable(smUri)

  override def constants: Iterable[ResourceUri] = tables.constTable.keys

  override def constant(constUri: ResourceUri): ConstantDecl = tables.constTable(constUri)

  override def uris(file: FileResourceUri): MSet[ResourceUri] = tables.declaredSymbols(file)

  override def typeTable(tdUri: ResourceUri): TypeTable = {
    tables.typeTable(tdUri)
  }

  override def typeAlias(typeUri: ResourceUri): ISet[ResourceUri] =
    isetEmpty ++ tables.aliasTable.getOrElse(typeUri, isetEmpty)

  override def componentTable(compUri: ResourceUri): ComponentTable = {

    tables.componentSymbolTable(compUri)

  }

  override def systemTable: Option[ComponentTable] = {
    systemUri.map(tables.componentSymbolTable(_))
  }

  //  def componentSymbolTableProducer(curi: ResourceUri): ComponentTable = {
  //    assert(tables.componentDeclTable.contains(curi))
  //    compMap.getOrElseUpdate(curi, new CompST(curi))
  //  }

  override def compStateMachine(compUri: ResourceUri): Option[ResourceUri] = {
    tables.compSMTable.get(compUri)
  }

  override def smTable(smUri: ResourceUri): StateMachineTable = {
    tables.stateMachineTable(smUri)
  }

  def toSymbolTable: SymbolTable = this

  override def compTypeDecl(compUri: ResourceUri): Set[ResourceUri] = {
    tables.compTypeTable.getOrElse(compUri, isetEmpty[ResourceUri]).toSet
  }



  override def getUriFromSymbol(symbol: String): Option[ResourceUri] = {
    tables.symbol2Uri.get(symbol)
  }


  class TypeT(val typeDeclUri: ResourceUri) extends TypeTable {

    val tables = TypeTableData()

    override def uri: ResourceUri = typeDeclUri

    override def symbolTable: SymbolTable = st

    override def enumElements: Set[ResourceUri] = tables.enumTable(typeDeclUri).keySet.toSet

    override def enumElement(enumElemUri: ResourceUri): Id =
      tables.enumTable(typeDeclUri)(enumElemUri)

    override def latticeElements: Set[ResourceUri] = tables.latticeTable.keySet.toSet

    override def latticeElement(latticeElemUri: ResourceUri): Id =
      tables.latticeTable(latticeElemUri).id

    override def latticeParents(latticeElemUri: ResourceUri): ISet[ResourceUri] =
      tables.latticeTable(latticeElemUri).parents.toSet

    override def getUriFromSymbol(symbol: String): Option[ResourceUri] = {
//      println(symbol)
//      tables.symbol2Uri.foreach(it => println(it))
      tables.symbol2Uri.get(symbol)

    }

  }

  class SMT(val smDeclUri: ResourceUri) extends StateMachineTable {
    val tables = StateMachineData()

    override def symbolTable: SymbolTable = st

    override def states: Seq[ResourceUri] = tables.statesTable.keys.toSeq

    override def events: Iterable[ResourceUri] = tables.eventsTable.keys

    override def state(stateUri: ResourceUri): Node = tables.statesTable(stateUri)

    override def event(eventUri: ResourceUri): Node = tables.eventsTable(eventUri)

    override def getUriFromSymbol(symbol: String): Option[ResourceUri] = {
      tables.symbol2Uri.get(symbol)
    }
  }

  override def components: Iterable[ResourceUri] = {
    tables.componentDeclTable.keys
  }

  override def computeDeployment() : Unit = {
    tables.forwardDeployment ++= componentTable(this.system).deployments.groupBy(_._1).mapValues(_.map(_._2).toSet)
    tables.backwardDeployment ++= componentTable(this.system).deployments.map(x => (x._2, x._1)).groupBy(_._1).mapValues(_.map(_._2).toSet)
  }

  override def removeDeployments() : Unit = {
    tables.forwardDeployment --= tables.forwardDeployment.keySet
    tables.backwardDeployment --= tables.backwardDeployment.keySet
  }

  override def forwardDeployment(uri: ResourceUri): ISet[ResourceUri] = {
    if(tables.forwardDeployment.isEmpty) {
      tables.forwardDeployment ++= componentTable(this.system).deployments.groupBy(_._1).mapValues(_.map(_._2).toSet)
    }
    tables.forwardDeployment.getOrElse(uri, isetEmpty)
  }

  override def backwardDeployment(uri: ResourceUri): ISet[ResourceUri] = {
    if(tables.backwardDeployment.isEmpty) {
      tables.backwardDeployment ++= componentTable(this.system).deployments.map(x => (x._2, x._1)).groupBy(_._1).mapValues(_.map(_._2).toSet)
    }
      tables.backwardDeployment.getOrElse(uri, isetEmpty)
  }

}

object STProducer {
  implicit def rw: RW[STProducer] = macroRW
}

trait TypeTable {
  def uri: ResourceUri

  def symbolTable: SymbolTable

  def enumElements: Set[ResourceUri]

  def enumElement(enumElemUri: ResourceUri): Id

  def latticeElements: Set[ResourceUri]

  def latticeElement(latticeElemUri: ResourceUri): Id

  def latticeParents(latticeElemUri: ResourceUri): ISet[ResourceUri]

  def getUriFromSymbol(symbol: String): Option[ResourceUri]
}

sealed case class TypeTableData
(enumTable: MMap[ResourceUri, MMap[ResourceUri, Id]] = mmapEmpty,

 latticeTable: MMap[ResourceUri, LatticeTableData] = mmapEmpty,

 symbol2Uri: MMap[String, ResourceUri] = mmapEmpty)

sealed case class LatticeTableData
(id: Id, parents: MSet[ResourceUri])

trait StateMachineTable {
  def symbolTable: SymbolTable

  def states: Seq[ResourceUri]

  def state(stateUri: ResourceUri): Node

  def events: Iterable[ResourceUri]

  def event(eventUri: ResourceUri): Node

  def getUriFromSymbol(symbol: String): Option[ResourceUri]
}

sealed case class StateMachineData
(statesTable: MLinkedMap[ResourceUri, Node] = mlinkedMapEmpty,
 eventsTable: MMap[ResourceUri, Node] = mmapEmpty,
 symbol2Uri: MMap[String, ResourceUri] = mmapEmpty)
