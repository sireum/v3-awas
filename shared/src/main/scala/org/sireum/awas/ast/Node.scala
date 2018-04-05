/*
Copyright (c) 2015, Robby, Kansas State University
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

package org.sireum.awas.ast

import org.sireum.util._

object Node {
  type Seq[T] = IVector[T]

  final def emptySeq[T]: IVector[T] = ivectorEmpty[T]

  final def seq[T](es: T*): IVector[T] = ivector(es: _*)

  final def seq[T](es: Iterable[T]): Vector[T] = es.toVector
}

sealed trait Node extends Product

sealed trait UnitNode extends Node {
  var fileUriOpt: Option[FileResourceUri] = None
  var nodeLocMap: MIdMap[AnyRef, LocationInfo] = midmapEmpty
}

sealed trait TypeDecl extends Node

final case class Model(types: Node.Seq[TypeDecl],
                       stateMachines: Node.Seq[StateMachineDecl],
                       constants: Node.Seq[ConstantDecl],
                       system: Option[ComponentDecl]) extends UnitNode {

//  var sourceURI = Option[FileResourceUri]
}

final case class StateMachineDecl(smName: Id,
                                  states: Node.Seq[Id],
                                  events: Node.Seq[Id]) extends Node

final case class ConstantDecl(name: Name, constType: Type, init: Init) extends Node

final case class ComponentDecl(compName: Id,
                               withSM: Node.Seq[Name],
                               ports: Node.Seq[Port],
                               propagations : Node.Seq[Propagation],
                               flows: Node.Seq[Flow],
                               transitions: Option[Transition],
                               behaviour: Option[Behaviour],
                               subComp: Node.Seq[ComponentDecl],
                               connections: Node.Seq[ConnectionDecl],
                               deployment: Node.Seq[DeploymentDecl],
                               properties: Node.Seq[Property]) extends Node

final case class ConnectionDecl(connName: Id,
                                fromComp: Option[Name],
                                fromPort: Id,
                                isAccess: Boolean,
                                toComp: Option[Name],
                                toPort: Id,
                                connFlow: Node.Seq[CFlow],
                                behaviour: Option[Behaviour],
                                properties:Node.Seq[Property]) extends Node

final case class DeploymentDecl(fromNode: Name,
                                toNode: Name) extends Node

final case class Port(isIn : Boolean, id : Id, name: Option[Name]) extends Node

final case class Propagation(id: Id, errorTypes: Node.Seq[Fault]) extends Node

final case class Flow(id: Id,
                      from: Option[Id],
                      fromE: Node.Seq[Fault],
                      to:Option[Id],
                      toE: Node.Seq[Fault]) extends Node

final case class CFlow(id: Id,
                       fromE: Node.Seq[Fault] = Node.emptySeq[Fault],
                       toE: Node.Seq[Fault] = Node.emptySeq[Fault]) extends Node

final case class Property(id: Id, propType: Type, value: Option[Init]) extends Node

final case class AliasDecl(name: Id, typeName: Type) extends TypeDecl

final case class EnumDecl(name: Id,
                          superEnums: Node.Seq[Name],
                          elements: Node.Seq[Id]) extends TypeDecl

final case class LatticeDecl(name: Id,
                             superLattice: Node.Seq[Name]) extends TypeDecl

final case class RecordDecl(name: Id, fields: Node.Seq[FieldDecl]) extends TypeDecl

final case class FieldDecl(id : Id, fieldType: Type) extends Node

object Id {
  def apply(value: String): Id = {
    _Id(value.intern())
  }

  def unapply(id: Id): Option[String] = Some(id.value)
}

sealed trait Id extends Node {
  def value: String
}

private final case class
_Id(value: String) extends Id {
  override def toString =
    s"Id($value)"
}

final case class Name(value: Node.Seq[Id]) extends Node

final case class Virtual(name : Id) extends Node

//-----------------------Behaviour---------------------------//

final case class Behaviour(exprs : Node.Seq[Expression]) extends Node

final case class Transition(exprs : Node.Seq[TransExpr]) extends Node

final case class TransExpr(lhs: Node.Seq[Id],
                            rhs: Node.Seq[Id],
                            propCond: Option[Tuple],
                            trigger: Node.Seq[Id]) extends Node

final case class Expression(lhs: Option[Tuple], rhs: Option[Tuple], states: Node.Seq[Id])

final case class Tuple(tokens : ILinkedMap[Id, One]) extends Node

sealed trait One extends Node

//final case class NoFailure() extends One

//final case class Wildcard() extends One

//final case class Variable(id : Id) extends One

final case class Fault(enum : Name) extends One

final case class FaultSet(value : ILinkedSet[Fault]) extends One

//-----------------------Init---------------------------//
sealed trait Init extends Node

final case class BooleanInit(value: Boolean) extends Init

final case class IntegerInit(value: Integer) extends Init

final case class RealInit(value: Double) extends Init

final case class StringInit(value: String) extends Init

final case class RecordInit(name: Name, fields: IMap[Id, Init]) extends Init

final case class NameRefInit(name: Name, ref: Option[Id]) extends Init

final case class NoneInit(typeInit: Type) extends Init

final case class SomeInit(typeInit: Type, value: Init) extends Init

final case class SetInit(typeInit: Type, value: ISet[Init]) extends Init

final case class SeqInit(typeInit: Type, value: Node.Seq[Init]) extends Init

final case class MapInit(keyType: Type,
                         valueType: Type,
                         value: IMap[Init, Init]) extends Init

//-----------------------Types---------------------------//
sealed trait Type extends Node

sealed trait CompoundType extends Type

sealed trait BasicType extends Type

final case class OptionTypeDecl(typeVal : Type) extends CompoundType

final case class SetTypeDecl(typeVal : Type) extends CompoundType

final case class SeqTypeDecl(typeVal : Type) extends CompoundType

final case class MapTypeDecl(keyType: Type,
                             valueType: Type) extends CompoundType

final case class BooleanTypeDecl() extends BasicType

final case class IntegerTypeDecl(value: Option[(IntTypeDisc, IntTypeDisc)])
  extends BasicType

sealed trait IntTypeDisc extends Node

final case class IntLit(value: Integer) extends IntTypeDisc

final case class NamedIntType(value: Name) extends IntTypeDisc

final case class ArbitrartyIntType() extends IntTypeDisc

final case class RealTypeDecl() extends BasicType

final case class StringTypeDecl() extends BasicType

final case class ComponentTypeDecl() extends BasicType

final case class PortTypeDecl() extends BasicType

final case class NamedTypeDecl(value: Name) extends BasicType