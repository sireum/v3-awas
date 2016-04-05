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

import org.sireum.awas.ast.{NoFailure, PrettyPrinter, Fault}
import org.sireum.util._

object FptcUtilities {
  //From Miles Sabin's blog: Unboxed union types via Curry-Howard isomorphism
  type ¬[A] = A => Nothing
  type ¬¬[A] = ¬[¬[A]]
  type ∨[T, U] = ¬[¬[T] with ¬[U]]
  type |∨|[T, U] = { type λ[X] = ¬¬[X] <:< (T ∨ U) }
  type FaultToken = Fault |∨| NoFailure

  type Tup = IVector[ISet[FaultToken]]
  type NTup = IVector[FaultToken]


  def isListOfSingletons(input : Tup) : Boolean = {
    input.forall(s => s.size <= 1)
  }

  def listOfSingletonToList(input : Tup) : NTup = {
    input.map{s => s.head}
  }

  def ListOfSet2SetOfList(input : Tup) : ISet[NTup]={
    if(isListOfSingletons(input)) {
      isetEmpty[NTup] + listOfSingletonToList(input)
    } else {
      var workList = ivectorEmpty[Tup]
      workList = workList.+:(input)
      var result = isetEmpty[NTup]
      while(workList.nonEmpty) {
        val cur : Tup = workList.head
        if(isListOfSingletons(cur)) {
          result = result + listOfSingletonToList(cur)
        } else {
          val setIndex = cur.indexWhere(s => s.size > 1)
          cur(setIndex).foreach{e =>
            var tempTup : Tup = ivectorEmpty[ISet[FaultToken]]
            tempTup = cur.slice(0, setIndex)
            tempTup = tempTup :+ (isetEmpty[FaultToken] + e)
            if (setIndex < cur.length - 1)
              tempTup = tempTup ++ cur.slice(setIndex + 1, cur.length)
            workList = workList.:+(tempTup)
          }
        }
        workList = workList.tail
      }
      result
    }
  }

  def toString(in : FaultToken): String = {
    in match {
      case f : Fault => PrettyPrinter.print(f)
      case nf : NoFailure => PrettyPrinter.print(nf)
    }
  }

  def getFault(in : FaultToken) : Option[Fault] ={
    in match {
      case f : Fault => Some(f)
      case nf : NoFailure => None
    }
  }

  def toString(in : NTup) : String = {
    var res = "("
    if (in.length > 1) {
      res = res + toString(in.head)
      in.tail.foreach { f =>
        res = res + ", " + toString(f)
      }
      res = res + ")"
    } else {
      res = res + toString(in.head) + ")"
    }
    res
  }

}
