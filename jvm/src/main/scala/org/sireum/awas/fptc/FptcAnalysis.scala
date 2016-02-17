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

package org.sireum.awas.fptc

import org.sireum.awas.ast.Tuple
import org.sireum.awas.graph.AwasEdge
import scalax.collection.mutable.Graph
import org.sireum.util

object FptcAnalysis {

  def apply(g: FptcGraph[FptcNode]): FptcGraph[FptcNode] = {

    var worklist = util.ilistEmpty[FptcNode]

    worklist = worklist ++ g.nodes[FptcNode].map{
      n : (Graph[FptcNode, AwasEdge]#NodeT) => n.value}

    while(worklist.nonEmpty) {
     val node = worklist.head
     val t : Tuple = g.computeInSet(node)
     if(!node.inSetContains(t)) {
       node.addToInSet(t)
       val outTuple = g.computeOutSet(node, t)
       if(!node.outSetContains(outTuple)) {
         node.addToOutSet(outTuple)
         worklist = worklist.tail ++ g.propagate(node, outTuple)
       } else {
         worklist = worklist.tail
       }
     } else {
       worklist = worklist.tail
     }
    }
    g
  }
}
