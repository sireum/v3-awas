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

package org.sireum.awas.ast

import org.sireum.util.{IVector, RewriteFunction}

object Rewriter {

  import org.sireum.util.Rewriter._

  val constructorMap: ConstructorMap = Map(
    ("Propagation", { es =>
      val Seq(id: Id, et: IVector[Name]) = es
      org.sireum.awas.ast.Propagation(id, et)
    }),
    ("Id", { es =>
      val Seq(value: String) = es
      org.sireum.awas.ast.Id(value)
    }),
    ("Name", { es =>
      val Seq(value: IVector[Id]) = es
      org.sireum.awas.ast.Name(value)
    }),
    ("ComponentDecl", { es =>
      val Seq(compName: Id,
      withSm: Node.Seq[Name],
      ports: Node.Seq[Port],
      propagations: Node.Seq[Propagation],
      flows: Node.Seq[Flow],
      transitions: Option[Transition],
      behaviour: Option[Behaviour],
      properties: Node.Seq[Property]) = es

      org.sireum.awas.ast.ComponentDecl(compName,
        withSm,
        ports,
        propagations,
        flows,
        transitions,
        behaviour,
        properties)
    }),
    ("Flow", { es =>
      val Seq(id: Id,
      from: Option[Id],
      fromE: Node.Seq[Name],
      to: Option[Id],
      toE: Node.Seq[Name]) = es
      org.sireum.awas.ast.Flow(id, from, fromE, to, toE)
    }),

    ("Model",{es =>
      val Seq(types: Node.Seq[TypeDecl],
      stateMachines: Node.Seq[StateMachineDecl],
      constants: Node.Seq[ConstantDecl],
      components: Node.Seq[ComponentDecl],
      connections: Node.Seq[ConnectionDecl]) = es
//      fileUriOpt : Option[FileResourceUri],
//      nodeLocMap: MIdMap[AnyRef, LocationInfo]//
      org.sireum.awas.ast.Model(types, stateMachines, constants, components, connections)
//      m.fileUriOpt = fileUriOpt
//      m.nodeLocMap = nodeLocMap
//      m
    })
  )

  def build[T](mode: TraversalMode.Type = TraversalMode.TOP_DOWN)(
    f: RewriteFunction) =
    org.sireum.util.Rewriter.build[T](constructorMap)(mode, f)
}
