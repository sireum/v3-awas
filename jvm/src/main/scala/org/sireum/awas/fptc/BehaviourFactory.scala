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

package org.sireum.awas.fptc

import org.sireum.awas.ast._
import org.sireum.awas.fptc.FptcUtilities._

object BehaviourFactory {
  def apply(lhs: Tuple): ((NTup) => Option[Tuple]) = {
    (input: NTup) => {
      val inBehave = lhs.tokens.map { o =>
        funSelect(o).curried(lhs.tokens.indexOf(o))
      }
      if (inBehave.forall(b => b(input))) Some(lhs) else None
    }
  }

  def funSelect(in: One): ((Int, NTup) => Boolean) = {
    in match {
      case w: Wildcard => (_, _) => true
      case f: Fault => (a: Int, b : NTup) => b(a) == f
      case nf: NoFailure => (a: Int, b : NTup) => b(a) == nf
      case v: Variable => (_, _) => true
      case fs: FaultSet => (a: Int, b: NTup) => {
        val fault = getFault(b(a))
        if(fault.isDefined) fs.value.contains(fault.get) else false
      }
    }
  }
}