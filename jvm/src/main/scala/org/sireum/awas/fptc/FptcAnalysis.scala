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
import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper}
import org.sireum.util._

object FptcAnalysis {
  val H = SymbolTableHelper
  def apply(graph: FptcGraph[FptcNode], st: SymbolTable) : FptcGraph[FptcNode] = {
    var workList = ilistEmpty[FptcNode]

    workList = workList ++ graph.nodes
    while(workList.nonEmpty) {
      workList = workList.tail ++ evaluateNode(workList.head, st)
    }

    graph
  }

  def evaluateNode(node : FptcNode, st : SymbolTable) : Set[FptcNode] = {
    var res = isetEmpty[FptcNode]

    val nodeType = if(node.getUri.startsWith(H.CONNECTION_TYPE)) H.CONNECTION_TYPE else H.COMPONENT_TYPE

    if(nodeType == H.CONNECTION_TYPE) {
      //connection should have only one in port and one out port
      node.getFptcPropagation(node.inPorts.head).foreach {
        errorUri =>
          val temp = node.outPorts.head
//          node.addFptcPropagation(node.outPorts.head, errorUri)
      }

      //propagatation
      //Connection connects to exactly 2 edges, one for in and one for out
      val errors = node.getFptcPropagation(node.outPorts.head)
      val edge = node.getEdge(node.outPorts.head).head
      val targetPort = edge.targetPort
      val targetNode = edge.target
      assert(targetPort.isDefined)
      val targetErrors = targetNode.getPropagation(targetPort.get)
      if(!errors.subsetOf(targetErrors)) {
//        errors.foreach(targetNode.addFptcPropagation(targetPort.get, _))
        res += targetNode
      }
    } else {
      //component node, there are only two kinds of node
      val compDecl = st.component(node.getUri)

      if(compDecl.behaviour.isDefined) {
        val behavior = compDecl.behaviour.get
        behavior.exprs.foreach{
          expr => if(expr.lhs.isDefined) {
            if(checkLhs(node,expr.lhs.get.tokens)) {
              if(expr.rhs.isDefined)
                computeOuts(node, expr.rhs.get.tokens)
              res ++= computePropagate(node)
            }
          }else {
            if(expr.rhs.isDefined)
              computeOuts(node, expr.rhs.get.tokens)
            res ++= computePropagate(node)
          }
        }
      }
    }
    res
  }

  def checkLhs(node : FptcNode, tokens : ILinkedMap[Id, One]) : Boolean = {
    tokens.keySet.forall{
      t =>
        val pUri = Resource.getResource(t).get.toUri
        tokens(t) match {
          case f : Fault => node.getFptcPropagation(pUri).contains(Resource.getResource(f.enum).get.toUri)
          case fs : FaultSet => {
            fs.value.map(f => Resource.getResource(f.enum).get.toUri).subsetOf(node.getFptcPropagation(pUri))
          }
        }
    }
  }

  def computeOuts(node : FptcNode, tokens : ILinkedMap[Id, One]) : Unit = {
    tokens.foreach{
      t =>
        val pUri = Resource.getResource(t._1).get.toUri
//        t._2 match {
//          case f : Fault => node.addFptcPropagation(pUri, Resource.getResource(f.enum).get.toUri)
//          case fs : FaultSet => {
//            fs.value.map(f => Resource.getResource(f).get.toUri).foreach(node.addFptcPropagation(pUri, _))
//          }
//        }
    }
  }

  def computePropagate(node: FptcNode) : Set[FptcNode] = {
    var res = isetEmpty[FptcNode]
    node.outPorts.foreach{
      op =>
        val sErrors = node.getFptcPropagation(op)
        node.getEdge(op).foreach{
          e =>
            val tErrors = e.target.getFptcPropagation(e.targetPort.get)
            if(!sErrors.subsetOf(tErrors)) {
//              sErrors.foreach(f => e.target.addFptcPropagation(e.targetPort.get, f))
              res += e.target
            }
        }
    }
    res
  }
}