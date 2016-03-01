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

package org.sireum.awas.graph

import org.sireum.awas.ast.{Fault, FaultSet, NoFailure, One}
import org.sireum.awas.fptc.FptcNode
import org.sireum.util._

import scalax.collection.mutable.Graph
import scalax.collection.GraphEdge._
import scalax.collection.GraphPredef._

/**
  * Created by hariharan on 1/27/16.
  */
trait AwasGraph[Node] {
  type Edge = AwasEdge[Node]

  def numOfNodes : Int = graph.nodes.size

  def nodes[Node]  = graph.nodes

  def edges = graph.edges

//  def hasEdge(n1 : Node, n2: Node) : Boolean = {
//    val edge  = new AwasEdge[Node]((n1,n2),_)
//    graph.exists(graph.having(node = _ == edge))
//  }

  def numOfEdges : Int = graph.edges.size

  def hasNode(n : Node): Boolean = graph.find(n).isDefined

  def getNode(n : Node)  = {
    graph.get(n)
  }

  def addNode (node : Node) : Node ={
    graph.add(node)
    node
  }

  def getEdge (edge : Edge) = {
    graph.get(edge)
  }



  def addEdge (from : Node, to: Node) = {
    val nodes : Product2[Node, Node]= (from, to)
    val edge = new AwasEdge[Node](nodes, Some(NoFailure()))
    graph.add(edge)
    edge
  }

  protected def graph : Graph[Node, AwasEdge]

  def predecessor(node : Node) : Set[Node] = {
    graph.get(node).diPredecessors.map(t => t.value)
  }

  def successor(node : Node) : Set[Node] = {
    graph.get(node).diSuccessors.map(t => t.value)
  }

  def inEdges(node : Node) : Set[Edge] = {
    graph.get(node).incoming.map(t => t.toOuter)
  }

  def outEdges(node : Node) : Set[Edge] = {
    graph.get(node).outgoing.map(t => t.toOuter)
  }

}

class AwasEdge[Node](nodes: Product, var fault: Option[One])
  extends DiEdge[Node](nodes)
    with EdgeCopy[AwasEdge]
    with OuterEdge[Node, AwasEdge] {


  //  var fault : One = NoFailure()
  //  def getFault:One = fault
  //
  def setFault(f: One) = {
    if (this.fault.isDefined) {
      this.fault.get match {
        case fs: FaultSet =>
          val oneSet = fs.value + f
          this.fault = Some(FaultSet(oneSet))
        case tf: One =>
          val oneSet = isetEmpty[One] + tf + f
          this.fault = Some(FaultSet(oneSet))
      }
    } else {
      this.fault = Some(f)
    }
  }

  override def toString() = {
    nodes match {
      case np : (Node, Node) => np._1.toString + " ~> " + np._2.toString + "<<"+this.fault+">>"
    }
  }

//  def getFault(): Option[One] = {
//    val temp = this.fault
//    this.edge.fault = None
////    println(toString())
//    temp
//  }

  override def copy[NodeNode](newNodes: Product) =
    new AwasEdge[NodeNode](newNodes, fault)


}

object AwasEdge {
  val ~> = AwasEdge

  def apply[Node](from: Node, to: Node, fault : Option[One]):AwasEdge[Node] = new AwasEdge[Node](NodeProduct(from, to), fault)

//  protected[Collection]
//  def from [Node](nodes : Product) = new AwasEdge[Node](nodes)

//  def apply[Node](nodes: Product2[Node, Node]):AwasEdge[Node] = new AwasEdge[Node](nodes)

  def unapply[Node](e: AwasEdge[Node]):Option[(Node, Node, Option[One])] = if (e eq null) None else Some((e.source, e.target, e.fault))


}


