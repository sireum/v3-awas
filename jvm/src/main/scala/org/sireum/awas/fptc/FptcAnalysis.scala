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
import org.sireum.awas.fptc.FptcUtilities.NTup
import org.sireum.awas.graph.AwasEdge
import org.sireum.util._
import scala.collection._
import scalax.collection.mutable.Graph
import org.sireum.util

object FptcAnalysis {

  def apply(g: FptcGraph[FptcNode]): FptcGraph[FptcNode] = {

    var worklist = util.ilistEmpty[FptcNode]

    worklist = worklist ++ g.nodes[FptcNode].map {
      n: (Graph[FptcNode, AwasEdge]#NodeT) => n.value
    }

    while (worklist.nonEmpty) {
      val node = worklist.head
      val t: ISet[IVector[Option[Fault]]] = computeInSet(g, node)
      if (t.nonEmpty) {
        val outTuple = computeOutSet(node, t)
        var res = isetEmpty[FptcNode]
        outTuple.foreach{t =>
          res = res ++ g.propagate(node, t)
        }
        if (res.nonEmpty) {
          worklist = worklist.tail ++ res.toList
        } else {
          worklist = worklist.tail
        }
      } else {
        worklist = worklist.tail
      }
    }
    g
  }

  def computeInSet(g: FptcGraph[FptcNode], node: FptcNode): ISet[NTup] = {
    val temp = g.sortedInEdges(node).map { e => g.getFault(e) }
    val ins = FptcUtilities.ListOfSet2SetOfList(temp)
    val res = ins.filterNot(node.inSetContains)
    res.foreach { in => node.addToInSet(in) }
    res
  }

  def computeOutSet(node: FptcNode, inTupSet: ISet[IVector[Option[Fault]]]): ISet[IVector[Option[Fault]]] = {
    val behaviour: IVector[(IVector[Option[Fault]]) => Option[Tuple]] = node.getTups
    //    var outSet = imapEmpty[Tuple, Tuple]
    if (inTupSet.isEmpty) return isetEmpty[IVector[Option[Fault]]]
    var result = isetEmpty[IVector[Option[Fault]]]
    inTupSet.foreach { in =>
      val matchedLhsSet = behaviour.flatMap(_ (in)).toSet
      if (matchedLhsSet.isEmpty) {
        result = result + in
        println("WARNING: incoming fault Tup: " + FptcUtilities.toString(in) +
          " lacks appropriate behaviour in node: "
          + node.toString)
      } else {
        val matchedLhs = getMostSpecific(matchedLhsSet)
        if (matchedLhs.isDefined) {
          val rhs = node.getBehaviourRhs(matchedLhs.get)
          result = result ++ FptcUtilities.ListOfSet2SetOfList(
            varLessRhs(in, matchedLhs.get, rhs.get))
        } else {
          println("WARNING: Unable to get the most specific in node: "
            + node.toString)
        }
      }
    }
    result = result.filterNot(node.outSetContains)
    result.foreach { out => node.addToOutSet(out) }
    result
  }

  def getMostSpecific(tups: ISet[Tuple]): Option[Tuple] = {
    val head = tups.head
    var res = isetEmpty[Tuple] + head
    var gwt = weight(head)
    tups.foreach { t =>
      if (weight(t) > gwt) {
        gwt = weight(t)
        res = isetEmpty[Tuple] + t
      } else if (weight(t) == gwt) {
        res = res + t
      }
    }
    if (res.size == 1) {
      Some(res.head)
    } else {
      None
    }
  }

  private def varLessRhs(in: IVector[Option[Fault]], lhs: Tuple, rhs: Tuple): IVector[ISet[Fault]] = {
    var store = imapEmpty[Variable, Option[Fault]]
    lhs.tokens.foreach {
      case v: Variable =>
        store = store + ((v, in(lhs.tokens.indexOf(v))))
      case _ =>
    }

    val newTokens: IVector[ISet[Fault]] = rhs.tokens.map {
      case v: Variable =>
        if(store(v).isDefined) isetEmpty[Fault] + store(v).get else isetEmpty[Fault]
      case f: Fault => isetEmpty[Fault] + f
      case fs: FaultSet => isetEmpty[Fault] ++ fs.value
      case nf : NoFailure => isetEmpty[Fault]
      case x: One => {
        println("Error, Rhs contains " + PrettyPrinter.print(x))
        isetEmpty[Fault]
      }
    }
    newTokens
  }

  def weight(t: Tuple): Integer = {
    var fs = 0
    var vw = 0
    var fnf = 0
    t.tokens.foreach {
      case v: Variable => vw = vw + 1
      case w: Wildcard => vw = vw + 1
      case f: Fault => fnf = fnf + 1
      case nf: NoFailure => fnf = fnf + 1
      case f: FaultSet => fs = fs + 1
    }
    (fs * 5) + (fnf * 10) + vw
  }

}