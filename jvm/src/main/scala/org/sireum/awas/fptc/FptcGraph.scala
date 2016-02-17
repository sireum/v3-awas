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

import org.sireum.awas.ast.Model
import org.sireum.awas.graph.{AwasEdge, AwasGraph}
import org.sireum.awas.ast._
import org.sireum.util._
import scalax.collection._
import scalax.collection.io.dot._

trait FptcGraph[Node] extends AwasGraph[Node] {
  def toDot(name : String): String
  def computeInSet(node : FptcNode): Tuple
  def computeOutSet(node: FptcNode, t :Tuple): Tuple
  def propagate(node: FptcNode, out: Tuple): List[FptcNode]
}

object FptcGraph {

  type GEdge = AwasEdge[FptcNode]

  def apply(m: Model): FptcGraph[FptcNode] = {

    val result = new Fg()

    var compMap = imapEmpty[Name, Node]

    Visitor.build({
      case comp : ComponentDecl => {
        val elem = (comp.compName, comp)
        compMap = compMap.+(elem)
        val compNode = FptcNode.createNode(comp)
        result.addNode(compNode)
      }
        false
    })(m)

    Visitor.build({
      case conn : ConnectionDecl => {
        val fromCompNode = FptcNode createNode compMap(conn.fromComp)
        val toCompNode = FptcNode createNode compMap(conn.toComp)
        val connNode = FptcNode createNode conn
        result.addNode(connNode)
        result.addEdge(fromCompNode, connNode)
        result.addEdge(connNode, toCompNode)
      }
        false
    })(m)
    result
  }
}

class Fg extends FptcGraph[FptcNode] {
  val graph = mutable.Graph[FptcNode, AwasEdge]()

  def toDot(name : String): String = {
    val dotExporter = new Export(graph)

    val root = DotRootGraph(directed = true, id =
      Some(scalax.collection.io.dot.Id(name)))

    def edgeTransformer(innerEdge: Graph[FptcNode, AwasEdge]#EdgeT):
    Option[(DotGraph, DotEdgeStmt)] = {
      val  edge = innerEdge
      Some((root, DotEdgeStmt(NodeId(edge.head.value.toString),
        NodeId(edge.last.value.toString), Nil)))
    }

    def nodeTransformer(innerNode: Graph[FptcNode, AwasEdge]#NodeT):
    Option[(DotGraph, DotNodeStmt)] =
      Some((root,
        DotNodeStmt(NodeId(innerNode.value.toString), innerNode.value.getType match {
          case FptcNodeProperty.CONN_NODE => Nil
          case _ => List(DotAttr(io.dot.Id("shape"), io.dot.Id("box")))
        })))

    val dot = dotExporter.toDot(root,
      edgeTransformer,None,
      cNodeTransformer = Some(nodeTransformer),
      iNodeTransformer = Some(nodeTransformer),
      spacing = Spacing(Indent.TwoSpaces)
    )

    val dotSorted = {
      val lines = dot.linesWithSeparators.toList
      val mid = lines.tail.init
      s"${lines.head}${mid.sorted.mkString}${lines.last}"
    }
    dotSorted
  }

  def sortedInEdges(node : FptcNode): Vector[Edge] = {
    inEdges(node).toVector.sortBy(f => f._1.toString)
  }

  def sortedOutEdges(node : FptcNode): Vector[Edge] = {
    outEdges(node).toVector.sortBy(f => f._1.toString)
  }

  def computeInSet(node : FptcNode): Tuple = {
    val inedges = sortedInEdges(node)
    val temp = inedges.map{e => e.fault}
    val t = Tuple(temp)
    t
  }

  private def setOfTupleToTuple(tuples : Set[Tuple]) : Tuple = {
    // we know tuples computed from out nodes share the same length of tokens
    var result = ivectorEmpty[One]

    for(i <- tuples.head.tokens.indices) {
      var sets = isetEmpty[One]
      tuples.foreach { tuple =>
        sets = sets + tuple.tokens(i)
      }
      if(sets.size < 2) {
        result = result :+ sets.head
      } else {
        result = result :+ FaultSet(sets)
      }
    }
    Tuple(result)
  }

  private def check(f: (One, One)) : Boolean = {
    f match {
      case (_, v : Variable) => true
      case (_, w : Wildcard) => true
      case (x, y) => x == y
    }
  }

  private def matches(t : Tuple, pat : Tuple) : Boolean = {
    val tTokens = t.tokens
    val patTokens = pat.tokens
    if(tTokens.length == patTokens.length) {
      var result = true
      for(i <- tTokens.indices) {
        if(result) {
          result = check((tTokens(i), patTokens(i)))
        }
      }
      result
    } else {
      false
    }
  }

  def computeOutSet(node: FptcNode, t :Tuple): Tuple = {
    val behaviour : Option[IMap[Tuple, Tuple]] = node.behaviour
    if(behaviour.isDefined) {
      var tuples = isetEmpty[Tuple]

      behaviour.get.foreach{ v =>
        if(matches(t,v._1)) {
          var varValMap = imapEmpty[One, One]
          v._1.tokens.foreach{token:One =>
            token match {
              case vari : Variable => {
                varValMap = varValMap + (vari -> t.tokens(v._1.tokens.indexOf(token)))
              }
              case _ =>
            }
          }
          val newTokens = v._2.tokens.map{ t : One =>
            t match {
              case vari : Variable => varValMap(vari)
              case anythingelse => anythingelse
            }
          }
          tuples = tuples + Tuple(newTokens)
        }
      }
      if(tuples.nonEmpty) {
        setOfTupleToTuple(tuples)
      } else {
        t
      }
    } else {
      t
    }
  }

  def propagate(node: FptcNode, out: Tuple): List[FptcNode] = {
    val edgeseq = sortedOutEdges(node)
    var result = ilistEmpty[FptcNode]
    if(edgeseq.size == out.tokens.length) {
      for(i <- edgeseq.indices) {
        if(edgeseq(i).fault != out.tokens(i)) {
          val f = edgeseq(i).fault
          this.graph.get(edgeseq(i)).setFault(out.tokens(i))
          val g = edgeseq(i).fault
          result= result :+ edgeseq(i)._2
        }
      }
    }
    result
  }
}