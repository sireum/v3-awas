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

import org.sireum.awas.ast._
import org.sireum.awas.graph.AwasEdge
import org.sireum.util._
import org.sireum.util.IMap

trait FptcNode {
  def getType : String
  def toString : String
  def getTups : IVector[((Tuple) => Option[Tuple])]
  def getBehaviourRhs(lhs : Tuple) : Option[Tuple]
  def getCompInPorts: Node.Seq[Port]
  def getCompOutPorts: Node.Seq[Port]
  def addToInSet(in : Tuple): Unit
  def inSetContains(in : Tuple) : Boolean
  def addToOutSet(out : Tuple): Unit
  def outSetContains(out : Tuple): Boolean
  def getInSet: Set[Tuple]
  def getOutSet: Set[Tuple]
  def addPortEdgeInfo(port : Port, edge : AwasEdge[FptcNode])
  def getPortEdgeInfo : IMap[Port, AwasEdge[FptcNode]]
}

object FptcNode {
  private var nodepool = imapEmpty[Node,FptcNode]

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

  def newPool(): Unit ={
    nodepool = imapEmpty[Node,FptcNode]
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

  private var inSet = isetEmpty[Tuple]
  private var outSet = isetEmpty[Tuple]
  private var behaviour = imapEmpty[Tuple, Tuple]
  private val selectTup = buildMatcher
  private var portEdgeMap = imapEmpty[Port, AwasEdge[FptcNode]]

  def getTups : IVector[((Tuple) => Option[Tuple])] = selectTup

  private def buildMatcher: IVector[((Tuple) => Option[Tuple])] = {

    var result : IMap[Tuple, Tuple] = imapEmpty[Tuple, Tuple]

    val temp = node match {
      case comp : ComponentDecl => comp.behaviour
      case conn : ConnectionDecl => conn.behaviour
      case _ => None
    }

    if(temp.isDefined) {
      result = temp.get.expr
    }

    behaviour = result

    result.keySet.map{k => BehaviourFactory(k)}.toVector
  }

  def getBehaviourRhs(lhs : Tuple) : Option[Tuple] = {
    behaviour.lift(lhs)
  }

  def getType = `type`

  def getCompInPorts: Node.Seq[Port] = {
    node match {
      case comp : ComponentDecl =>
        comp.ports.filter{p : Port => p.isIn}
      case _ => Node.emptySeq[Port]
    }
  }

  def getCompOutPorts: Node.Seq[Port] = {
    node match {
      case comp : ComponentDecl =>
        comp.ports.filter{p : Port => !p.isIn}
      case _ => Node.emptySeq[Port]
    }
  }

  def getConnFromPortName: Id = {
    assert(this.getType == FptcNodeProperty.CONN_NODE,
      "Node is not Connection to get from port")
    node.asInstanceOf[ConnectionDecl].fromPort
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

  def addPortEdgeInfo(port : Port, edge : AwasEdge[FptcNode]) = {
    portEdgeMap = portEdgeMap + ((port, edge))
  }

  def getPortEdgeInfo : IMap[Port, AwasEdge[FptcNode]] = this.portEdgeMap

}

object FptcNodeProperty {
  val COMP_NODE = "Component"
  val CONN_NODE = "Connection"
  val ERROR_NODE = "Node type unknown"
}