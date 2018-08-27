/*
 * // #Sireum
 *
 *  Copyright (c) 2017, Hariharan Thiagarajan, Kansas State University
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 */

package org.sireum.awas.reachability

import org.sireum.awas.collector.Collector
import org.sireum.awas.fptc.FlowNode
import org.sireum.awas.query.ConstraintExpr
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

trait ErrorReachability[Node] extends PortReachability[Node] {
  def forwardErrorReach(port: ResourceUri, errors: ISet[ResourceUri]): Collector

  def backwardErrorReach(port: ResourceUri, errors: ISet[ResourceUri]): Collector

  def forwardErrorSetReach(errorRes: IMap[ResourceUri, Set[ResourceUri]]): Collector

  def backwardErrorSetReach(errorRes: IMap[ResourceUri, Set[ResourceUri]]): Collector

  def errorPathReach(sourcePort: ResourceUri, sourceErrors: ISet[ResourceUri],
                     targetPort: ResourceUri, targetErrors: ISet[ResourceUri]):
  Collector

  def errorPathReachMap(source: IMap[ResourceUri, ISet[ResourceUri]],
                        target: IMap[ResourceUri, ISet[ResourceUri]]): Collector

  def errorPathReachMapWith(source: IMap[ResourceUri, ISet[ResourceUri]],
                            target: IMap[ResourceUri, ISet[ResourceUri]],
                            constraint: ConstraintExpr): Collector

  def getPredecessor(currentPort: ResourceUri,
                     currentError: ResourceUri)
  : IMap[ResourceUri, ISet[ResourceUri]]


  def getSuccessor(currentPort: ResourceUri,
                   currentError: ResourceUri)
  : IMap[ResourceUri, ISet[ResourceUri]]
}

object ErrorReachability {
  def apply(st: SymbolTable): ErrorReachability[FlowNode] = {
    new ErrorReachabilityImpl[FlowNode](st)
  }
}
