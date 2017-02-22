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

import org.sireum.awas.fptc.{FptcGraph, FptcNode}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.ISet


trait PortReachability[Node] extends BasicReachability[Node]{
  def forwardPortReach(criterion: ResourceUri): ISet[ResourceUri]

  def backwardPortReach(criterion: ResourceUri): ISet[ResourceUri]

  def forwardPortReachSet(criterions: Set[ResourceUri]): ISet[ResourceUri]

  def backwardPortReachSet(criterions: Set[ResourceUri]): ISet[ResourceUri]

  def forwardPortReach(criterion: FptcNode): ISet[ResourceUri]

  def backwardPortReach(criterion: FptcNode): ISet[ResourceUri]

  def forwardReach(criterion: ResourceUri): ISet[ResourceUri]

  def backwardReach(criterion: ResourceUri): ISet[ResourceUri]

  def forwardReachSet(criterion: Set[ResourceUri]): ISet[ResourceUri]

  def backwardReachSet(criterion: Set[ResourceUri]): ISet[ResourceUri]
}

object PortReachability {
  def apply(graph: FptcGraph[FptcNode]): PortReachability[FptcNode] = {
    new PortReachabilityImpl[FptcNode](graph)
  }
}