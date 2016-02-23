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

import org.sireum.awas.ast.{FaultSet, One, Tuple}
import org.sireum.awas.graph.AwasEdge
import org.sireum.util._
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
      val t: ISet[Tuple] = computeInSet(g, node)
      if (t.nonEmpty) {
        val outTuple = computeOutSet(node, t)
        if (outTuple.isDefined) {
          worklist = worklist.tail ++ g.propagate(node, outTuple.get)
        } else {
          worklist = worklist.tail
        }
      } else {
        worklist = worklist.tail
      }
    }
    g
  }

  def computeInSet(g: FptcGraph[FptcNode], node: FptcNode): ISet[Tuple] = {
    val inedges = g.sortedInEdges(node)
    val temp = inedges.map { e => e.fault }
    val t = Tuple(temp)
    val res = flatTuple(t) diff node.getInSet
    res.foreach(node.addToInSet)
    res
  }

  def computeOutSet(node: FptcNode, inTupSet: ISet[Tuple]): Option[Tuple] = {
    val behaviour: IVector[(Tuple) => Option[Tuple]] = node.behaviour
    var outSet = isetEmpty[Option[Tuple]]
    inTupSet.foreach { in =>
      outSet = outSet ++ behaviour.par.map(_ (in))
    }
    var out = outSet.flatten.flatMap(flatTuple)
    if (out.nonEmpty) {
      out = out diff node.getOutSet
    } else {
      out = inTupSet
    }
    out.foreach(node.addToOutSet)
    setOfTupleToTuple(out)
  }

  private def setOfTupleToTuple(tuples: Set[Tuple]): Option[Tuple] = {
    // we know tuples computed from out nodes share the same length of tokens
    if (tuples.nonEmpty) {
      var result = ivectorEmpty[One]
      for (i <- tuples.head.tokens.indices) {
        var sets = isetEmpty[One]
        tuples.foreach { tuple =>
          sets = sets + tuple.tokens(i)
        }
        if (sets.size < 2) {
          result = result :+ sets.head
        } else {
          result = result :+ FaultSet(sets)
        }
      }
      Some(Tuple(result))
    } else {
      None
    }
  }

  def findFaultSet(in: IVector[One]): Int = {
    in.indexWhere(x => x match {
      case x: FaultSet => true
      case _ => false
    })
  }

  def flatTuple(in: Tuple): ISet[Tuple] = {
    val inSeq = in.tokens
    var result = ilistEmpty[IVector[One]]
    var worklist = ilistEmpty[IVector[One]]
    worklist = worklist :+ inSeq
    while (worklist.nonEmpty) {
      val currTup = worklist.head
      val where = findFaultSet(currTup)
      var tempList = ilistEmpty[IVector[One]]
      if (where != -1) {
        val fs = currTup(where).asInstanceOf[FaultSet]
        fs.value.foreach { e =>
          var tempTup = ivectorEmpty[One]
          tempTup = currTup.slice(0, where)
          tempTup = tempTup :+ e
          if (where < currTup.length - 1)
            tempTup = tempTup ++ currTup.slice(where + 1, currTup.length)
          tempList = tempList :+ tempTup
        }
      } else {
        result = result :+ currTup
      }
      worklist = worklist.tail ++ tempList
    }
    result.map(Tuple).toSet[Tuple]
  }
}