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

package org.sireum.awas.reachability

import org.sireum.awas.fptc.{FlowGraph, FlowNode}
import org.sireum.awas.symbol.SymbolTableHelper
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

class PortReachabilityImpl[Node](graph: FlowGraph[FlowNode]) extends
  BasicReachabilityImpl(graph) with PortReachability[FlowNode] {

  val H = SymbolTableHelper

  override def forwardPortReach(criterion: FlowNode): ISet[ResourceUri] =
    forwardPortReachSet(criterion.outPorts.toSet)

  override def forwardPortReachSet(criterions: Set[ResourceUri]): ISet[ResourceUri] =
    criterions.flatMap(forwardPortReach)

  override def forwardPortReach(criterion: ResourceUri): ISet[ResourceUri] =
    portReach(criterion, isForward = true)

  def portReach(criterion: ResourceUri, isForward: Boolean): ISet[ResourceUri] = {
    var result = isetEmpty[ResourceUri]
    var worklist = ilistEmpty[ResourceUri]
    if (criterion.startsWith(H.PORT_TYPE) && graph.getNode(criterion).isDefined) {
      worklist = worklist :+ criterion
      while (worklist.nonEmpty) {
        val current = worklist.head
        if (!result.contains(current))
          worklist = worklist ++ (if (isForward) graph.getSuccessorPorts(current)
          else graph.getPredecessorPorts(current))
        worklist = worklist.tail
        result += current
      }
    }
    result
  }

  override def backwardPortReach(criterion: FlowNode): ISet[ResourceUri] =
    backwardPortReachSet(criterion.inPorts.toSet)

  override def backwardPortReachSet(criterions: Set[ResourceUri]): ISet[ResourceUri] =
    criterions.flatMap(backwardPortReach)

  override def backwardPortReach(criterion: ResourceUri): ISet[ResourceUri] =
    portReach(criterion, isForward = false)

  override def forwardReachSet(criterion: Set[ResourceUri]): ISet[ResourceUri] = {
    criterion.flatMap(forwardReach)
  }

  override def forwardReach(criterion: ResourceUri): ISet[ResourceUri] = {
    if (isNode(criterion)) {
      val onode = graph.getNode(criterion)
      if (onode.isDefined) {
        forwardReach(onode.get).map(_.getUri)
      } else {
        isetEmpty[ResourceUri]
      }
    } else {
      forwardPortReach(criterion)
    }
  }

  private def isNode(uri: ResourceUri): Boolean = {
    if (uri.startsWith(H.COMPONENT_TYPE) ||
      uri.startsWith(H.CONNECTION_TYPE)) {
      true
    } else {
      false
    }
  }

  override def backwardReachSet(criterion: Set[ResourceUri]): ISet[ResourceUri] = {
    criterion.flatMap(backwardReach)
  }

  override def backwardReach(criterion: ResourceUri): ISet[ResourceUri] = {
    if (isNode(criterion)) {
      val onode = graph.getNode(criterion)
      if (onode.isDefined) {
        backwardReach(onode.get).map(_.getUri)
      } else {
        isetEmpty[ResourceUri]
      }
    } else {
      backwardPortReach(criterion)
    }
  }

  def reachPathSet(source: Set[ResourceUri], target: Set[ResourceUri]): ISet[Set[ResourceUri]] = {
    source.flatMap(x => target.flatMap(y => reachPath(x, y)))
  }

  def reachPath(source: ResourceUri, target: ResourceUri): ISet[Set[ResourceUri]] = {
    if (isNode(source) || isNode(target)) {
      val snode = graph.getNode(source)
      val tnode = graph.getNode(target)
      if (snode.isDefined && tnode.isDefined) {
        reachPath(snode.get, tnode.get).map(p => p.map(_.getUri))
      } else {
        isetEmpty[ISet[ResourceUri]]
      }
    } else {
      val snode = graph.getNode(source)
      val tnode = graph.getNode(target)
      if (snode.isDefined && tnode.isDefined) {
        val nodePath = reachPath(snode.get, tnode.get).map(p => p.map(_.getUri))
        nodePath.map(getPathPorts)
      } else {
        isetEmpty[ISet[ResourceUri]]
      }
    }
  }

  /**
    * Uses the node path to find port path
    *
    * @param path set of nodes
    * @return port path for a node path
    */
  private def getPathPorts(path: ISet[ResourceUri]): ISet[ResourceUri] = {
    var result = isetEmpty[ResourceUri]
    path.foreach { nuri =>
      val node = graph.getNode(nuri)
      if (node.isDefined) {
        node.get.inPorts.foreach { p =>
          if (path.intersect(graph.getPredecessorPorts(p).flatMap { f: ResourceUri =>
            graph.getNode(f)
          }.toSet[FlowNode].map(_.getUri)).nonEmpty) {
            result += p
          }
        }
        node.get.outPorts.foreach { p =>
          if (path.intersect(graph.getSuccessorPorts(p).flatMap { f: ResourceUri =>
            graph.getNode(f)
          }.toSet[FlowNode].map(_.getUri)).nonEmpty) {
            result += p
          }
        }
      }
    }
    result
  }

}

