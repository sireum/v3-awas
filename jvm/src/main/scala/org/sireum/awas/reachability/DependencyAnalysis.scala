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

///*
// Copyright (c) 2017, Robby, Kansas State University
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice, this
//    list of conditions and the following disclaimer.
// 2. Redistributions in binary form must reproduce the above copyright notice,
//    this list of conditions and the following disclaimer in the documentation
//    and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
// ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
// WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
// ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
// (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
// LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
// ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
// SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
// */
//
//package org.sireum.awas.reachability
//
//import org.sireum.awas.symbol.{SymbolTable, SymbolTableHelper}
//import org.sireum.awas.util.AwasUtil
//import org.sireum.util._
//
//object DependencyAnalysis {
//  val FORWARD = "forward"
//  val BACKWARD = "backward"
//  def apply(g : ReachGraph[ReachNode],
//            st : SymbolTable,
//            compName: String,
//            portName :String,
//            error : Option[String],
//            dir : String) : ReachGraph[ReachNode] = {
//    val comp = st.components.find(_.endsWith("#"+compName))
//    if(comp.isDefined) {
//      val port = st.componentTable(comp.get).ports.find(_.endsWith("#"+portName))
//      if(port.isDefined) {
//        val node = ReachabilityAnalysis.nodePool(port.get)
//        if(error.isDefined) {
//          val eUri = node.getProp.find(_.endsWith(error.get))
//          node.setSlice(eUri.get)
//        } else {
//          node.getProp.foreach(f => node.setSlice(f))
//        }
//        slice(node, g, dir, st)
//      }
//    }
//    g
//  }
//
//  def slice(node: ReachNode, g:ReachGraph[ReachNode], dir : String, st : SymbolTable) : ReachGraph[ReachNode] = {
//    if(dir == FORWARD) {
//      forwardSlice(node, g, st)
//    } else {
//      backwardSlice(node, g, st)
//    }
//  }
//
//  def forwardSlice(cnode: ReachNode, g:ReachGraph[ReachNode], st : SymbolTable) : ReachGraph[ReachNode] = {
//    var workList = ivectorEmpty[ReachNode] :+ cnode
//    while(workList.nonEmpty) {
//      val node = workList.head
//      if(node.compUri.startsWith(SymbolTableHelper.COMPONENT_TYPE)) {
//        val cst = st.componentTable(node.compUri)
//        val ttUri = st.compTypeDecl(node.compUri)
//        val tt = st.typeTable(ttUri.get)
//        val edges = g.getOutgoingEdges(node)
//        edges.foreach {
//          e =>
//            if (e.isInstanceOf[ReachEdge]) {
//              val edge = e.asInstanceOf[ReachEdge]
//              if (edge.getFlow.isDefined) {
//                val flowUri = edge.getFlow.get
//
//                st.compTypeDecl(node.compUri)
//                val flow = cst.flow(flowUri)
//                val fe = AwasUtil.toUri(flow.fromE, tt)
//
//                  val te = AwasUtil.toUri(flow.toE, tt)
//                  if (!te.toSet.subsetOf(edge.target.getSlice)) {
//                    te.foreach(f => edge.target.setSlice(f))
//                    edge.target.setSliced
//                    workList = workList :+ edge.target
//                  }
//
//              } else {
//                if (!edge.source.getSlice.subsetOf(edge.target.getSlice)) {
//                  edge.source.getSlice.foreach(f => edge.target.setSlice(f))
//                  edge.target.setSliced
//                  workList = workList :+ edge.target
//                }
//              }
//            }
//        }
//      } else {
//        g.getOutgoingEdges(node).foreach {
//          e =>
//            e match {
//              case edge: ReachEdge =>
//                if (!edge.source.getSlice.subsetOf(edge.target.getSlice)) {
//                  edge.source.getSlice.foreach(f => edge.target.setSlice(f))
//                  edge.target.setSliced
//                  workList = workList :+ edge.target
//                }
//              case _ =>
//            }
//        }
//      }
//      workList = workList.tail
//    }
//    g
//  }
//
//  def backwardSlice(cnode: ReachNode, g:ReachGraph[ReachNode], st : SymbolTable) : ReachGraph[ReachNode] = {
//    var workList = ivectorEmpty[ReachNode] :+ cnode
//    while(workList.nonEmpty) {
//      val node = workList.head
//      val cst = st.componentTable(node.compUri)
//      val ttUri = st.compTypeDecl(node.compUri)
//      val tt = st.typeTable(ttUri.get)
//      val edges = g.getOutgoingEdges(node)
//      edges.foreach{
//        e =>
//          if(e.isInstanceOf[ReachEdge]) {
//            val edge = e.asInstanceOf[ReachEdge]
//            if (edge.getFlow.isDefined) {
//              val flowUri = edge.getFlow.get
//
//              st.compTypeDecl(node.compUri)
//              val flow = cst.flow(flowUri)
//              val fe = AwasUtil.toUri(flow.fromE, tt)
//              if(node.getSlice.intersect(fe.toSet).nonEmpty) {
//                val te = AwasUtil.toUri(flow.toE,tt)
//                if(!edge.target.getSlice.subsetOf(te.toSet)) {
//                  te.foreach(f => edge.target.setSlice(f))
//                  edge.target.setSliced
//                  workList = workList :+ edge.target
//                }
//              }
//            } else {
//              if(!edge.source.getSlice.subsetOf(edge.target.getSlice)) {
//                edge.source.getSlice.foreach(f => edge.target.setSlice(f))
//                edge.target.setSliced
//                workList = workList :+ edge.target
//              }
//            }
//          }
//      }
//      workList.tail
//    }
//    g
//  }
//
//}
