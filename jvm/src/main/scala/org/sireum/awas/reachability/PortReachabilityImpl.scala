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
    if (criterion.startsWith(H.COMPONENT_TYPE) ||
      criterion.startsWith(H.CONNECTION_TYPE)) {
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

  override def backwardReachSet(criterion: Set[ResourceUri]): ISet[ResourceUri] = {
    criterion.flatMap(backwardReach)
  }

  override def backwardReach(criterion: ResourceUri): ISet[ResourceUri] = {
    if (criterion.startsWith(H.COMPONENT_TYPE) ||
      criterion.startsWith(H.CONNECTION_TYPE)) {
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
}
