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

package org.sireum.awas.ast

import org.sireum.util._
import upickle.default.{macroRW, ReadWriter => RW}

object Node {
  type Seq[T] = IVector[T]

  final def emptySeq[T]: IVector[T] = ivectorEmpty[T]

  final def seq[T](es: T*): IVector[T] = ivector(es: _*)

  final def seq[T](es: Iterable[T]): Vector[T] = es.toVector
}

sealed trait Node extends Product {
  var auriFrag: Option[String] = None
}

sealed trait UnitNode extends Node {
  var fileUriOpt: Option[FileResourceUri] = None
  var nodeLocMap: MIdMap[AnyRef, LocationInfo] = midmapEmpty
}

sealed trait TypeDecl extends Node

object TypeDecl {
  implicit def rw: RW[TypeDecl] = RW.merge(AliasDecl.rw, EnumDecl.rw, RecordDecl.rw, LatticeDecl.rw)
}

final case class Model(
  types: Node.Seq[TypeDecl],
  stateMachines: Node.Seq[StateMachineDecl],
  constants: Node.Seq[ConstantDecl],
  system: Option[ComponentDecl]
) extends UnitNode {

//  var sourceURI = Option[FileResourceUri]
}

object Model {
  implicit def rw: RW[Model] = macroRW
}

final case class StateMachineDecl(smName: Id, states: Node.Seq[Name], events: Node.Seq[Name]) extends Node

object StateMachineDecl {
  implicit def rw: RW[StateMachineDecl] = macroRW
}

final case class ConstantDecl(name: Name, constType: Type, init: Init) extends Node

object ConstantDecl {
  implicit def rw: RW[ConstantDecl] = macroRW
}

final case class ComponentDecl(
  compName: Id,
  withSM: Node.Seq[Name],
  ports: Node.Seq[Port],
  propagations: Node.Seq[Propagation],
  security: Node.Seq[Security],
  flows: Node.Seq[Flow],
  declass: Node.Seq[Declass],
  transitions: Option[Transition],
  behaviour: Option[Behaviour],
  subComp: Node.Seq[ComponentDecl],
  connections: Node.Seq[ConnectionDecl],
  deployment: Node.Seq[DeploymentDecl],
  properties: Node.Seq[Property]
) extends Node

object ComponentDecl {
  implicit def rw: RW[ComponentDecl] = macroRW
}

final case class ConnectionDecl(
  connName: Id,
  fromComp: Option[Name],
  fromPort: Id,
  isAccess: Boolean,
  toComp: Option[Name],
  toPort: Id,
  connFlow: Node.Seq[CFlow],
  behaviour: Option[Behaviour],
  properties: Node.Seq[Property]
) extends Node

object ConnectionDecl {
  implicit def rw: RW[ConnectionDecl] = macroRW
}

final case class DeploymentDecl(fromNode: Name, fromPort: Option[Id], toNode: Name, toPort: Option[Id]) extends Node

object DeploymentDecl {
  implicit def rw: RW[DeploymentDecl] = macroRW
}

final case class Port(isIn: Boolean, id: Id, name: Option[Name]) extends Node

object Port {
  implicit def rw: RW[Port] = macroRW
}

final case class Propagation(id: Id, errorTypes: Node.Seq[Fault]) extends Node

object Propagation {
  implicit def rw: RW[Propagation] = macroRW
}

final case class Security(id: Id, domain: Id) extends Node

object Security {
  implicit def rw: RW[Security] = macroRW
}

final case class Flow(id: Id, from: Option[Id], fromE: Node.Seq[Fault], to: Option[Id], toE: Node.Seq[Fault])
    extends Node

object Flow {
  implicit def rw: RW[Flow] = macroRW
}

final case class CFlow(
  id: Id,
  fromE: Node.Seq[Fault] = Node.emptySeq[Fault],
  toE: Node.Seq[Fault] = Node.emptySeq[Fault]
) extends Node

object CFlow {
  implicit def rw: RW[CFlow] = macroRW
}

final case class Declass(fid: Id, fromDomain: Option[Id], toDomain: Id) extends Node

object Declass {
  implicit def rw: RW[Declass] = macroRW
}

final case class Property(
  id: Name,
  //                          propType: Type,
  value: Option[Init],
  appliesTo: Map[String, ISet[String]]
) extends Node

object Property {
  implicit def rw: RW[Property] = macroRW
}

final case class AliasDecl(typeName: Name, aliasName: BasicType) extends TypeDecl

object AliasDecl {
  implicit def rw: RW[AliasDecl] = macroRW
}

final case class EnumDecl(name: Id, superEnums: Node.Seq[Name], elements: Node.Seq[Id]) extends TypeDecl

object EnumDecl {
  implicit def rw: RW[EnumDecl] = macroRW
}

final case class LatticeDecl(name: Id, superLattice: Node.Seq[Name]) extends TypeDecl

object LatticeDecl {
  implicit def rw: RW[LatticeDecl] = macroRW
}

final case class RecordDecl(name: Id, fields: Node.Seq[FieldDecl]) extends TypeDecl

object RecordDecl {
  implicit def rw: RW[RecordDecl] = macroRW
}

final case class FieldDecl(id: Id, fieldType: Type) extends Node

object FieldDecl {
  implicit def rw: RW[FieldDecl] = macroRW
}

object Id {

  def apply(value: String): Id = {
    _Id(value.intern())
  }

  def unapply(id: Id): Option[String] = Some(id.value)

  implicit def rw: RW[Id] = macroRW
}

sealed trait Id extends Node {
  def value: String
}

final case class _Id(value: String) extends Id {
  override def toString =
    s"Id($value)"
}

object _Id {
  implicit def rw: RW[_Id] = macroRW
}

final case class Name(value: Node.Seq[Id]) extends Node

object Name {
  implicit def rw: RW[Name] = macroRW
}

final case class Virtual(name: Id) extends Node

object Virtual {
  implicit def rw: RW[Virtual] = macroRW
}

//-----------------------Behaviour---------------------------//

final case class Behaviour(exprs: Node.Seq[BehaveExpr]) extends Node

object Behaviour {
  implicit def rw: RW[Behaviour] = macroRW
}

final case class Transition(exprs: Node.Seq[TransExpr]) extends Node

object Transition {
  implicit def rw: RW[Transition] = macroRW
}

final case class TransExpr(id: Id, lhs: Node.Seq[Id], rhs: Node.Seq[Id], propCond: Option[ConditionTuple]) extends Node

object TransExpr {
  implicit def rw: RW[TransExpr] = macroRW
}

final case class BehaveExpr(id: Id, lhs: Option[ConditionTuple], rhs: Option[Tuple], states: Node.Seq[Id]) extends Node

object BehaveExpr {
  implicit def rw: RW[BehaveExpr] = macroRW
}

sealed trait ConditionTuple extends Node

object ConditionTuple {
  implicit def rw: RW[ConditionTuple] = RW.merge(And.rw, Or.rw, OrLess.rw, OrMore.rw, All.rw, PrimaryCondition.rw)
}

final case class And(lhs: ConditionTuple, rhs: ConditionTuple) extends ConditionTuple

object And {
  implicit def rw: RW[And] = macroRW
}

final case class Or(lhs: ConditionTuple, rhs: ConditionTuple) extends ConditionTuple

object Or {
  implicit def rw: RW[Or] = macroRW
}

final case class OrMore(value: Int, conds: Node.Seq[ConditionTuple]) extends ConditionTuple

object OrMore {
  implicit def rw: RW[OrMore] = macroRW
}

final case class OrLess(value: Int, conds: Node.Seq[ConditionTuple]) extends ConditionTuple

object OrLess {
  implicit def rw: RW[OrLess] = macroRW
}

final case class All(conds: Node.Seq[ConditionTuple]) extends ConditionTuple

object All {
  implicit def rw: RW[All] = macroRW
}

sealed trait PrimaryCondition extends ConditionTuple

object PrimaryCondition {
  implicit def rw: RW[PrimaryCondition] = RW.merge(EventRef.rw, Tuple.rw)
}

final case class EventRef(event: Node.Seq[Id]) extends PrimaryCondition

object EventRef {
  implicit def rw: RW[EventRef] = macroRW
}

final case class Tuple(tokens: IList[(Id, One)]) extends PrimaryCondition

object Tuple {
  implicit def rw: RW[Tuple] = macroRW
}

sealed trait One extends Node

object One {
  implicit def rw: RW[One] = RW.merge(Fault.rw, FaultSet.rw)
}

//final case class NoFailure() extends One

//final case class Wildcard() extends One

//final case class Variable(id : Id) extends One

final case class Fault(enum: Name) extends One

object Fault {
  implicit def rw: RW[Fault] = macroRW
}

final case class FaultSet(value: ILinkedSet[Fault]) extends One

object FaultSet {
  implicit def rw: RW[FaultSet] = macroRW
}

//-----------------------Init---------------------------//
sealed trait Init extends Node

object Init {
  implicit def rw: RW[Init] = RW.merge(
    BooleanInit.rw,
    IntegerInit.rw,
    RealInit.rw,
    StringInit.rw,
    RecordInit.rw,
    NameRefInit.rw,
    NoneInit.rw,
    SomeInit.rw,
    SeqInit.rw,
    SetInit.rw,
    MapInit.rw
  )
}

final case class BooleanInit(value: Boolean) extends Init

object BooleanInit {
  implicit def rw: RW[BooleanInit] = macroRW
}

final case class IntegerInit(value: Integer) extends Init

object IntegerInit {
  implicit def rw: RW[IntegerInit] = macroRW
}

final case class RealInit(value: Double) extends Init

object RealInit {
  implicit def rw: RW[RealInit] = macroRW
}

final case class StringInit(value: String) extends Init

object StringInit {
  implicit def rw: RW[StringInit] = macroRW
}

final case class RecordInit(name: Name, fields: IMap[Id, Init]) extends Init

object RecordInit {
  implicit def rw: RW[RecordInit] = macroRW
}

final case class NameRefInit(name: Name, ref: Option[Id]) extends Init

object NameRefInit {
  implicit def rw: RW[NameRefInit] = macroRW
}

final case class NoneInit(typeInit: Type) extends Init

object NoneInit {
  implicit def rw: RW[NoneInit] = macroRW
}

final case class SomeInit(typeInit: Type, value: Init) extends Init

object SomeInit {
  implicit def rw: RW[SomeInit] = macroRW
}

final case class SetInit(typeInit: Type, value: ISet[Init]) extends Init

object SetInit {
  implicit def rw: RW[SetInit] = macroRW
}

final case class SeqInit(typeInit: Type, value: Node.Seq[Init]) extends Init

object SeqInit {
  implicit def rw: RW[SeqInit] = macroRW
}

final case class MapInit(keyType: Type, valueType: Type, value: IMap[Init, Init]) extends Init

object MapInit {
  implicit def rw: RW[MapInit] = macroRW
}

//-----------------------Types---------------------------//
sealed trait Type extends Node

object Type {
  implicit def rw: RW[Type] = RW.merge(CompoundType.rw, BasicType.rw)
}

sealed trait CompoundType extends Type

object CompoundType {
  implicit def rw: RW[CompoundType] = RW.merge(OptionTypeDecl.rw, SetTypeDecl.rw, SeqTypeDecl.rw, MapTypeDecl.rw)
}

sealed trait BasicType extends Type

object BasicType {
  implicit def rw: RW[BasicType] = RW.merge(
    BooleanTypeDecl.rw,
    IntegerTypeDecl.rw,
    RealTypeDecl.rw,
    ComponentTypeDecl.rw,
    PortTypeDecl.rw,
    NamedTypeDecl.rw
  )
}

final case class OptionTypeDecl(typeVal: Type) extends CompoundType

object OptionTypeDecl {
  implicit def rw: RW[OptionTypeDecl] = macroRW
}

final case class SetTypeDecl(typeVal: Type) extends CompoundType

object SetTypeDecl {
  implicit def rw: RW[SetTypeDecl] = macroRW
}

final case class SeqTypeDecl(typeVal: Type) extends CompoundType

object SeqTypeDecl {
  implicit def rw: RW[SeqTypeDecl] = macroRW
}

final case class MapTypeDecl(keyType: Type, valueType: Type) extends CompoundType

object MapTypeDecl {
  implicit def rw: RW[MapTypeDecl] = macroRW
}

final case class BooleanTypeDecl() extends BasicType

object BooleanTypeDecl {
  implicit def rw: RW[BooleanTypeDecl] = macroRW
}

final case class IntegerTypeDecl(value: Option[(IntTypeDisc, IntTypeDisc)]) extends BasicType

object IntegerTypeDecl {
  implicit def rw: RW[IntegerTypeDecl] = macroRW
}

sealed trait IntTypeDisc extends Node

object IntTypeDisc {
  implicit def rw: RW[IntTypeDisc] = RW.merge(IntLit.rw, NamedIntType.rw, ArbitrartyIntType.rw)
}

final case class IntLit(value: Integer) extends IntTypeDisc

object IntLit {
  implicit def rw: RW[IntLit] = macroRW
}

final case class NamedIntType(value: Name) extends IntTypeDisc

object NamedIntType {
  implicit def rw: RW[NamedIntType] = macroRW
}

final case class ArbitrartyIntType() extends IntTypeDisc

object ArbitrartyIntType {
  implicit def rw: RW[ArbitrartyIntType] = macroRW
}

final case class RealTypeDecl() extends BasicType

object RealTypeDecl {
  implicit def rw: RW[RealTypeDecl] = macroRW
}

final case class StringTypeDecl() extends BasicType

object StringTypeDecl {
  implicit def rw: RW[StringTypeDecl] = macroRW
}

final case class ComponentTypeDecl() extends BasicType

object ComponentTypeDecl {
  implicit def rw: RW[ComponentTypeDecl] = macroRW
}

final case class PortTypeDecl() extends BasicType

object PortTypeDecl {
  implicit def rw: RW[PortTypeDecl] = macroRW
}

final case class NamedTypeDecl(value: Name) extends BasicType

object NamedTypeDecl {
  implicit def rw: RW[NamedTypeDecl] = macroRW
}
