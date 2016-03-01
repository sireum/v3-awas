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
    val temp = inedges.map { e => g.getFault(e)}
    if(temp.flatten.length == inedges.length) {
      val t = Tuple(temp.flatten)
      val res = flatTuple(t) diff node.getInSet
      res.foreach(node.addToInSet)
      res
    } else {
      isetEmpty[Tuple]
    }
  }

//TODO: rewrite it, pick only the tuples that are not already in outset
  def computeOutSet(node: FptcNode, inTupSet: ISet[Tuple]): Option[Tuple] = {
    val behaviour: IVector[(Tuple) => Option[Tuple]] = node.getTups
    var outSet = imapEmpty[Tuple, Tuple]
    if(inTupSet.isEmpty) {
      return None
    }
    inTupSet.foreach { in =>
       behaviour.flatMap(_ (in)).foreach{ ml =>
         outSet = outSet+((ml, in))
       }
    }
    val tups = outSet.keySet
    if(tups.nonEmpty) {
      val tup = getMostSpecific(tups)
      if(tup.isDefined) {
        val out = node.getBehaviourRhs(tup.get)
        if(out.isDefined) {
          val res = varLessRhs(outSet(tup.get), tup.get, out.get)
          flatTuple(res).foreach(node.addToOutSet)
          Some(res)
        }
        else
          None
      } else {
        println("WARNING: Unable to get the most specific in node: "
          +node.toString)
        None
      }
    } else {
      println("WARNING: incoming fault: "+
        inTupSet.foldLeft(""){(a,b) => PrettyPrinter.print(b)+" "}+
        " lacks appropriate behaviour in node: "
        +node.toString)
      val out = inTupSet
      out.foreach(node.addToOutSet)
      setOfTupleToTuple(out)
    }
  }

  def getMostSpecific(tups:ISet[Tuple]): Option[Tuple] = {
    val head = tups.head
    var res = isetEmpty[Tuple] + head
    var gwt = weight(head)
    tups.foreach{ t =>
      if(weight(t) > gwt){
        gwt = weight(t)
        res = isetEmpty[Tuple] + t
      } else if(weight(t) == gwt) {
        res = res + t
      }
    }
    if(res.size == 1) {
      Some(res.head)
    } else {
      None
    }
  }

  private def varLessRhs(in : Tuple, lhs : Tuple, rhs : Tuple): Tuple = {
    var store = imapEmpty[Variable, One]
    lhs.tokens.foreach {
      case v: Variable =>
      store = store + ((v, in.tokens(lhs.tokens.indexOf(v))))
      case _ =>
    }

    val newTokens: Node.Seq[One] = rhs.tokens.map {
      case v: Variable => store(v)
      case x: One => x
    }

    Tuple(newTokens)
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