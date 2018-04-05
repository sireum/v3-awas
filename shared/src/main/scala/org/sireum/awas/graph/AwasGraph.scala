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

import org.sireum.util._

import scala.util.control.Breaks._

trait AwasGraph[Node, Edge <: AwasEdge[Node]] {
  self =>

  //type

  private var scc: Seq[Set[Node]] = ilistEmpty[Set[Node]]

  private var cycles: Seq[Set[Node]] = ilistEmpty[Set[Node]]

  def nodes: Iterable[Node]

  def numOfNodes: Int

  def edges: Iterable[Edge]

  def numOfEdges: Int

  def hasNode(n: Node): Boolean

  def getNode(n: Node): Node

  def hasEdge(n1: Node, n2: Node): Boolean

  def getEdge(n1: Node, n2: Node): CSet[Edge]

  def getEdges(n: Node): CSet[Edge]

  def getIncomingEdges(node: Node): CSet[Edge]

  def getOutgoingEdges(node: Node): CSet[Edge]

  def getSuccessorNodes(node: Node): CSet[Node]

  def getPredecessorNodes(node: Node): CSet[Node]

  def getSCC: Seq[Set[Node]] = {
    if (scc.isEmpty) {
      var result = ilistEmpty[Set[Node]]
      var discoveryMap: IMap[Node, (Boolean, Boolean)] =
        nodes.map(n => (n, (false, false))).toMap

      def resetDiscoveryMap(): Unit = {
        discoveryMap = discoveryMap.map(x => (x._1, (false, false)))
      }

      def setDiscovered(node: Node): Unit = {
        val x = discoveryMap(node)
        discoveryMap = discoveryMap + (node -> (true, x._2))
      }

      def setBoth(node: Node): Unit = {
        val x = discoveryMap(node)
        discoveryMap = discoveryMap + (node -> (true, true))
      }

      def isAllMySuccDiscovered(node: Node): Boolean =
        getSuccessorNodes(node).map(discoveryMap).map(_._1).fold(false)((x, y) => x & y)

      def dfs(node: Node, isFirst: Boolean): List[Node] = {
        var result = ilistEmpty[Node]
        var stack = mstackEmpty[Node]
        stack.push(node)

        while (stack.nonEmpty) {
          val current = stack.pop()
          if (!discoveryMap(current)._1) {
            setDiscovered(current)
            if (!isFirst) {
              result = result :+ current
            }
            setBoth(current)
            stack.push(current)

            val nexts = if (isFirst) getSuccessorNodes(current) else getPredecessorNodes(current)
            nexts.foreach(n => if (!discoveryMap(n)._1) stack.push(n))

          } else if (discoveryMap(current)._2 && isFirst) {
            result = result.+:(current)
          }
        }
        result
      }

      var orderedNodes = ilistEmpty[Node]

      nodes.foreach { n =>
        if (!discoveryMap(n)._1)
          orderedNodes = orderedNodes ++ dfs(n, true)
      }
      resetDiscoveryMap()
      orderedNodes.foreach { n =>
        if (!discoveryMap(n)._1) {
          result = result :+ dfs(n, false).toSet
        }
      }
      scc = result
      scc
    } else {
      scc
    }
  }

  /**
    * Find all simple cycles of a directed graph using the Schwarcfiter and Lauer's algorithm.
    *
    * @return set of cycles
    */
  def getCycles: Seq[Set[Node]] = {
    val sccs = getSCC
    var loops = ilistEmpty[Set[Node]]
    var bSets = imapEmpty[Node, Set[Node]]
    var stack = ilistEmpty[Node]
    var marked = isetEmpty[Node]
    var removed = imapEmpty[Node, Set[Node]]
    var position = imapEmpty[Node, Integer]
    var reach = imapEmpty[Node, Boolean]


    def cycle(v: Node, tq: Integer): Boolean = {
      var q = tq
      var foundCycle = false;
      marked = marked + v
      stack = stack.push(v)
      val t = stack.size
      position = position + (v -> t)
      if (!reach(v)) {
        q = t
      }
      val avRemoved = removed.getOrElse(v, isetEmpty)
      getOutgoingEdges(v).foreach { e =>
        val wV = e.target
        if (!avRemoved.contains(wV)) {
          if (!marked.contains(wV)) {
            val gotCycle = cycle(wV, q)
            if (gotCycle) {
              foundCycle = true
            } else {
              noCycle(v, wV)
            }
          } else if (position(wV) <= q) {
            foundCycle = true;
            var cycle = isetEmpty[Node]
            val it = stack.reverseIterator
            var current = stack.last

            while (it.hasNext) {
              current = it.next()
              if (wV == current) break

            }
            cycle = cycle + wV
            while (it.hasNext) {
              current = it.next()
              cycle = cycle + current
              if (current == wV) break
            }
            loops = loops :+ cycle
          } else {
            noCycle(v, wV)
          }
        }
      }
      stack.pop()
      if (foundCycle) {
        unmark(v)
      }
      reach = reach + (v -> true)
      position = position + (v -> numOfNodes)
      foundCycle
    }

    def unmark(x: Node): Unit = {
      marked = marked - x
      bSets.getOrElse(x, isetEmpty).foreach { y =>
        removed = removed + (y -> (removed.getOrElse(y, isetEmpty) - x))
        if (marked.contains(y)) {
          unmark(y)
        }
      }
      bSets = bSets + (x -> isetEmpty)
    }

    def noCycle(x: Node, y: Node): Unit = {
      bSets = bSets + (y -> (bSets.getOrElse(y, isetEmpty) + x))
      removed = removed + (x -> (removed.getOrElse(x, isetEmpty) + y))
    }

    if (cycles.isEmpty) {
      val startNodes = sccs.map { scc =>
        var max = -1
        var startNode = scc.head
        scc.foreach { node =>
          val inDegree = getIncomingEdges(node).size
          if (inDegree > max) {
            max = inDegree
            startNode = node
          }
        }
        startNode
      }
      startNodes.foreach(cycle(_, 0))
      cycles = loops
      cycles
    } else {
      cycles
    }
  }
}

trait AwasGraphUpdate[Node, Edge <: AwasEdge[Node]] {
  self: AwasGraph[Node, Edge] =>

  def addNode(n: Node): Node

  def addEdge(from: Node, to: Node, data: Edge): Edge
}

trait AwasEdgeFactory[Node, Edge <: AwasEdge[Node]] {
  //type Edge = E

  def createEdge(owner: AwasGraph[Node, Edge],
                 source: Node, target: Node): Edge
}

trait AwasEdge[T] {
  def source: T

  def target: T
}