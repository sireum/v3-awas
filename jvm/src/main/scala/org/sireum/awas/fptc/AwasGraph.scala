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

package org.sireum.awas.fptc

import org.jgrapht.DirectedGraph
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.CSet

trait AwasGraph[Node] {
  self =>
  type Edge = AwasEdge[Node]

  protected def graph : DirectedGraph[Node, Edge]

  def nodes: Iterable[Node] = {
    import scala.collection.JavaConverters._
    graph.vertexSet().asScala
  }

  def numofNodes : Int = graph.vertexSet().size()

  def edges : Iterable[Edge] = {
    import scala.collection.JavaConverters._
    graph.edgeSet().asScala
  }

  def numOfEdges : Int = graph.edgeSet().size()

  def hasNode(n : Node): Boolean = graph.containsVertex(n)

  def getNode(n : Node) : Node

  def hasEdge(n1 : Node, n2: Node) : Boolean= {
    graph.containsEdge(n1, n2)
  }

  def addNode(n : Node) : Node = {
    graph.addVertex(n)
    n
  }

  def addEdge (from : Node, to: Node) : Edge
//  = {
//    val edge = AwasEdge[Node](self, from, to)
//    graph.addEdge(from, to, edge)
//    edge
//  }

  def getEdge(n1 : Node, n2: Node) : CSet[Edge] = {
    import scala.collection.JavaConverters._
    graph.getAllEdges(n1, n2).asScala
  }

  def getIncomingEdges(node : Node) : Set[Edge] = {
    import scala.collection.JavaConverters._
    graph.incomingEdgesOf(node).asScala.toSet
  }

  def getOutgoingEdges(node : Node) : Set[Edge] = {
    import scala.collection.JavaConverters._
    graph.outgoingEdgesOf(node).asScala.toSet
  }

}

trait AwasEdge[Node] {
  def sourcePort : Option[ResourceUri]
  def targetPort : Option[ResourceUri]

}





