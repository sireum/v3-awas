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

import org.sireum
import org.sireum.awas.ast._
import org.sireum.util
import org.sireum.util.IMap

trait FptcNode {
  def getType : String
  def toString : String
  def behaviour : Option[IMap[Tuple, Tuple]]
  def getInPorts: Node.Seq[Port]
  def getOutPorts: Node.Seq[Port]
  def addToInSet(in : Tuple): Unit
  def inSetContains(in : Tuple) : Boolean
  def addToOutSet(out : Tuple): Unit
  def outSetContains(out : Tuple): Boolean
  def getInSet: Set[Tuple]
  def getOutSet: Set[Tuple]
}

object FptcNode {
  private var nodepool = util.imapEmpty[Node,FptcNode]

  def createNode(awasNode : Node): FptcNode = {
    if(nodepool.contains(awasNode)) {
      nodepool(awasNode)
    } else {
      val node = new FN(awasNode, awasNode match {
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

final case class FN(node : Node, `type`: String) extends FptcNode {
   override def toString : String  = {
    node match {
      case n : ComponentDecl => PrettyPrinter.print(n.compName) +"::"+ `type`
      case n : ConnectionDecl => PrettyPrinter.print(n.connName) +"::"+ `type`
      case _ => "Error in node name"
    }
  }

  private var inSet = sireum.util.isetEmpty[Tuple]
  private var outSet = sireum.util.isetEmpty[Tuple]

  def behaviour : Option[IMap[Tuple, Tuple]] = {
    var result :Option[IMap[Tuple, Tuple]] = None

    val temp = node match {
      case comp : ComponentDecl => comp.behaviour
      case conn : ConnectionDecl => conn.behaviour
      case _ => None
    }

    if(temp.isDefined) {
      result = Some(temp.get.expr)
    }
    result
  }

  def getType = `type`

  def getInPorts: Node.Seq[Port] = {
    node match {
      case comp : ComponentDecl => {
        comp.ports.filter{p : Port => p.isIn}
      }
      case _ => Node.emptySeq[Port]
    }
  }

  def getOutPorts: Node.Seq[Port] = {
    node match {
      case comp : ComponentDecl => {
        comp.ports.filter{p : Port => !p.isIn}
      }
      case _ => Node.emptySeq[Port]
    }
  }

  def addToInSet(in : Tuple): Unit = {
    inSet = inSet + in
  }

  def inSetContains(in : Tuple) : Boolean = {
    inSet.contains(in)
  }

  def addToOutSet(out : Tuple): Unit = {
    outSet = outSet + out
  }

  def outSetContains(out : Tuple): Boolean = {
    outSet.contains(out)
  }

  def getInSet: Set[Tuple] = inSet

  def getOutSet: Set[Tuple] = outSet
}

object FptcNodeProperty {
  val COMP_NODE = "Component"
  val CONN_NODE = "Connection"
  val ERROR_NODE = "Node type unknow"
}