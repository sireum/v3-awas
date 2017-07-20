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

import org.sireum.util._

object Rewriter {

  import org.sireum.util.Rewriter._

  val constructorMap: ConstructorMap = Map(
    ("Propagation", { es =>
      val Seq(id: Id, et: IVector[_]) = es
      org.sireum.awas.ast.Propagation(id, cast(et))
    }),
    ("Id", { es =>
      val Seq(value: String) = es
      org.sireum.awas.ast.Id(value)
    }),
    ("Name", { es =>
      val Seq(value: IVector[_]) = es
      org.sireum.awas.ast.Name(cast(value))
    }),
    ("ComponentDecl", { es =>
      val Seq(compName: Id,
      withSm: Node.Seq[_],
      ports: Node.Seq[_],
      propagations: Node.Seq[_],
      flows: Node.Seq[_],
      transitions: Option[_],
      behaviour: Option[_],
      properties: Node.Seq[_]) = es

      org.sireum.awas.ast.ComponentDecl(
        compName,
        cast(withSm),
        cast(ports),
        cast(propagations),
        cast(flows),
        cast(transitions),
        cast(behaviour),
        cast(properties))
    }),
    ("Flow", { es =>
      val Seq(id: Id,
      from: Option[_],
      fromE: Node.Seq[_],
      to: Option[_],
      toE: Node.Seq[_]) = es
      org.sireum.awas.ast.Flow(id, cast(from), cast(fromE), cast(to), cast(toE))
    }),

    ("Model",{es =>
      val Seq(types: Node.Seq[_],
      stateMachines: Node.Seq[_],
      constants: Node.Seq[_],
      components: Node.Seq[_],
      connections: Node.Seq[_],
      deployments: Node.Seq[_]) = es
      org.sireum.awas.ast.Model(
        cast(types),
        cast(stateMachines),
        cast(constants),
        cast(components),
        cast(connections),
        cast(deployments))
    })
  )

  def build[T](mode: TraversalMode.Type = TraversalMode.TOP_DOWN)(
    f: RewriteFunction): (T) => T =
    org.sireum.util.Rewriter.build[T](constructorMap)(mode, f)
}
