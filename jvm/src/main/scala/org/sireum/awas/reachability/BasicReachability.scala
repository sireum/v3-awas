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

import org.sireum.util.ISet

trait BasicReachability[Node] {
  /**
    * Returns the forward reachability/slice of the criterion
    *
    * @param criterion Graph node from which reachability is computed
    * @return [[ISet]] of reached nodes or empty set, in case nothing to be reached
    */
  def forwardReach(criterion : Node) : ISet[Node]

  /**
    * Returns the forward reachability/slice for the set of criterion
    * @param criterion Set of graph nodes to which reachability is computed
    * @return a [[ISet]] of reached nodes or empty set, in case nothing to be reached
    */
  def forwardReachSetNode(criterion: Set[Node]): ISet[Node]

  /**
    * Returns the backward reachability/slice of the criterion
    *
    * @param criterion Graph node to which reachability is computed
    * @return a [[ISet]] of reached nodes or empty set, in case nothing to be reached
    */
  def backwardReach(criterion : Node) : ISet[Node]

  /**
    * Returns the backward reachability/slice for the set of criterion
    * @param criterions Set of graph nodes to which reachability is computed
    * @return a [[ISet]] of reached nodes or empty set, in case nothing to be reached
    */
  def backwardReachSetNode(criterions: Set[Node]): ISet[Node]

  /**
    * Returns the set of paths from source to target
    * @param source Starting node of paths
    * @param target Ending node of paths
    * @return a [[ISet]] of Paths, each path consists of a set of nodes
    */
  def reachPath(source : Node, target:Node): ISet[Set[Node]]
}
