/*
 Copyright (c) 2017, Robby, Kansas State University
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

import org.sireum.util.CSet

trait AwasGraph[Node] {
  self =>

  type Edge <: AwasEdge[Node]

  def nodes: Iterable[Node]

  def numOfNodes: Int

  def edges: Iterable[Edge]

  def numOfEdges: Int

  def hasNode(n: Node): Boolean

  def getNode(n : Node) : Node

  def hasEdge(n1: Node, n2: Node): Boolean

  def getCycles: Set[Seq[Node]]

  def getEdge(n1: Node, n2: Node): CSet[Edge]

  def getEdges(n: Node): CSet[Edge]

  def getIncomingEdges(node: Node): CSet[Edge]

  def getOutgoingEdges(node: Node): CSet[Edge]

  def getSuccessorNodes(node: Node): CSet[Node]

  def getPredecessorNodes(node: Node): CSet[Node]

  def getAllPaths(source: Node, sink: Node): Set[Set[Node]]
}

trait AwasGraphUpdate[Node] {
  self : AwasGraph[Node] =>

  def addNode(n: Node): Node

  def addEdge (from : Node, to: Node) : Edge
}

trait AwasEdgeFactory[Node, E] {
  type Edge = E

  def createEdge(owner: AwasGraph[Node],
                 source: Node, target: Node): Edge
}

trait AwasEdge[T] {
  def source: T

  def target: T
}