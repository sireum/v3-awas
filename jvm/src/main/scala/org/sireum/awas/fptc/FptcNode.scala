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

import org.sireum.awas.ast.{PrettyPrinter, ConnectionDecl, ComponentDecl, Node}
import org.sireum.util

object FptcNode {
  private var nodepool = util.imapEmpty[Node,FptcNode]

  def createNode(awasNode : Node): FptcNode = {
    if(nodepool.contains(awasNode)) {
      nodepool(awasNode)
    } else {
      val node = new FptcNode(awasNode, awasNode match {
        case awasNode: ComponentDecl => FptcNodeProperty.COMP_NODE
        case awasNode: ConnectionDecl => FptcNodeProperty.CONN_NODE
        case _ => FptcNodeProperty.ERROR_NODE
      })
      val elem = (awasNode, node)
      nodepool = nodepool + elem
      node
    }
  }
}

final case class FptcNode(node : Node, `type`: String) {
   override def toString() : String  = {

    node match {
      case n : ComponentDecl => PrettyPrinter.print(n.compName) +"\n"+ `type`
      case n : ConnectionDecl => PrettyPrinter.print(n.connName) +"\n"+ `type`
      case _ => "Error in node name"
    }
  }
}

object FptcNodeProperty {
  val COMP_NODE = "Component Node"
  val CONN_NODE = "Connection Node"
  val ERROR_NODE = "Node type unknow"
}