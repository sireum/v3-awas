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

import org.sireum
import org.sireum.awas.ast._
import org.sireum.util._

object BehaviourFactory {
  def apply(k: Tuple, v: Tuple): ((Tuple) => Option[Tuple]) = {
    new ((Tuple) => Option[Tuple]) {
      override def apply(input: Tuple): Option[Tuple] = {
        val lhsTokens = k.tokens
        val rhsTokens = v.tokens
        val inTokens = input.tokens
        val store = sireum.util.mmapEmpty[Variable, One]
        if (inTokens.length != lhsTokens.length) {
          return None
        }
        var flag = true
        for (i <- lhsTokens.indices) {
          if (!(flag && checker(lhsTokens(i), inTokens(i), store))) {
            flag = false
          }
        }
        if (flag) {
          Some(varLessRhs(rhsTokens, store))
        } else {
          None
        }
      }

      private def checker(lhs: One, in: One, store: MMap[Variable, One]): Boolean = {
        lhs match {
          case w: Wildcard => true
          case f: Fault => f == in
          case nf: NoFailure => nf == in
          case v: Variable => {
            store(v) = in
            true
          }
          case fs : FaultSet => {
            assert(false, "Fault set not allowed")
            false
          }
          case _ => false
        }
      }

      private def varLessRhs(tokens: Node.Seq[One], store: MMap[Variable, One]): Tuple = {
        val newTokens: Node.Seq[One] = tokens.map {
          case v: Variable => store(v)
          case x: One => x
        }
        Tuple(newTokens)
      }
    }
  }
}