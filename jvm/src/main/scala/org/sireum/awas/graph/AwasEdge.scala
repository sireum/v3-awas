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

import org.sireum.awas.fptc.FptcUtilities.FaultToken
import org.sireum.util._

import scalax.collection.GraphEdge.{NodeProduct, EdgeCopy, DiEdge}
import scalax.collection.GraphPredef.OuterEdge

final class AwasEdge[Node](override val nodes: Product, var fault: ISet[FaultToken])
  extends DiEdge[Node](nodes)
    with EdgeCopy[AwasEdge]
    with OuterEdge[Node, AwasEdge] {

  def setFault(f: FaultToken) = {
    fault = fault + f
  }

  override def toString() = {
    nodes match {
      case np : (Node, Node) => np._1.toString + " ~> " + np._2.toString + "<<"+this.fault+">>"
    }
  }

  override def copy[NodeNode](newNodes: Product) =
    new AwasEdge[NodeNode](newNodes, fault)

}

object AwasEdge {
  val ~> = AwasEdge

  def apply[Node](from: Node, to: Node, fault : ISet[FaultToken]):AwasEdge[Node] =
    new AwasEdge[Node](NodeProduct(from, to), fault)

  def unapply[Node](e: AwasEdge[Node]):Option[(Node, Node, ISet[FaultToken])] =
    if (e eq null) None else Some((e.source, e.target, e.fault))
}
