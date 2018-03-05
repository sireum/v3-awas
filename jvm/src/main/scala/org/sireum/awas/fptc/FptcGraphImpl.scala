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
//package org.sireum.awas.fptc
//
//import java.io.StringWriter
//import java.util
//
//import org.jgrapht.DirectedGraph
//import org.jgrapht.ext._
//import org.jgrapht.graph.DefaultDirectedGraph
//import org.sireum.awas.graph.AwasGraphUpdate
//import org.sireum.awas.symbol.SymbolTableHelper
//import org.sireum.awas.util.AwasUtil.ResourceUri
//import org.sireum.util._
//
//class FptcGraphImpl extends FptcGraph[FptcNode] with AwasGraphUpdate[FptcNode] {
//  self =>
//
//  type FEdge = FptcEdge[FptcNode]
//
//  var portEdgeMap = imapEmpty[ResourceUri, ISet[FEdge]]
//
//  var portNodeMap = imapEmpty[ResourceUri, FptcNode]
//
//  val H = SymbolTableHelper
//
//  def addPortEdge(port: ResourceUri, edge: FEdge): Unit = {
//    if (portEdgeMap.keySet.contains(port)) {
//      portEdgeMap += port -> (portEdgeMap(port) + edge)
//    } else {
//      portEdgeMap += port -> (isetEmpty[FEdge] + edge)
//    }
//  }
//
//  override def addEdge(from: FptcNode, to: FptcNode) = {
//    val edge = FptcEdgeImpl(self, from, to)
//    graph.addEdge(from, to, edge)
//    edge
//  }
//
//  override def addNode(n: FptcNode): FptcNode = {
//    n.ports.foreach { p => portNodeMap += (p -> n) }
//    graph.addVertex(n)
//    n
//  }
//
//  override def toDot(name: String): String = {
//    val de = new DOTExporter[FptcNode, FEdge](new IntegerNameProvider[FptcNode],
//      nlabelProvide, null,
//      this.attProvider, null)
//    val sw = new StringWriter()
//    de.exportGraph(graph, sw)
//    sw.toString
//  }
//
//  protected val attProvider = new ComponentAttributeProvider[FptcNode] {
//    override def getComponentAttributes(component: FptcNode): util.Map[String, String] = {
//      import scala.collection.JavaConverters._
//      val res = mlinkedMapEmpty[String, String]
//      res("shape") = "record"
//      res.asJava
//    }
//  }
//
//  protected val nlabelProvide = new VertexNameProvider[FptcNode] {
//    override def getVertexName(vertex: FptcNode): String = {
//      var result = ""
//      if (vertex.getUri.startsWith(SymbolTableHelper.COMPONENT_TYPE)) {
//        result += "{In Port|" + vertex.inPorts.map {
//          ip =>
//            val pname = ip.split("#").last
//            "<" + pname + ">" + pname
//        }.mkString("|") + "} |"
//        result += SymbolTableHelper.COMPONENT_TYPE + "\n" + vertex.getUri.split("#").last + "|"
//        result += "{Out Port|" + vertex.outPorts.map(_.split("#").last).mkString("|") + "}"
//      } else {
//
//        result = SymbolTableHelper.CONNECTION_TYPE + "\n" + vertex.getUri.split("#").last
//      }
//      result
//    }
//  }
//
//  override protected val graph: DirectedGraph[FptcNode, FEdge] = {
//    new DefaultDirectedGraph[FptcNode, FEdge](
//      (source: FptcNode, target: FptcNode) => FptcEdgeImpl(self, source, target)
//    )
//  }
//
//  override def getNode(n: FptcNode) = n
//
//  def getNode(uri: ResourceUri): Option[FptcNode] = {
//    if (uri.startsWith(H.COMPONENT_TYPE) ||
//      uri.startsWith(H.CONNECTION_TYPE)) {
//      FptcNode.getNode(uri)
//    } else {
//      portNodeMap.get(uri)
//    }
//  }
//
//  override def getEdgeForPort(port: ResourceUri): Set[FEdge] =
//    portEdgeMap.getOrElse(port, isetEmpty[FEdge])
//
//  override def getSuccessorPorts(port: ResourceUri): CSet[ResourceUri] = {
//    var result = isetEmpty[ResourceUri]
//    val node = getNode(port)
//    if (node.isDefined) {
//      if (port.startsWith(H.PORT_IN_TYPE)) {
//        result = result ++ node.get.flowForward(port)
//      } else {
//        //outport: use edge to get the successor
//        getEdgeForPort(port).foreach {
//          e =>
//            e.targetPort match {
//              case Some(x) => result = result + x
//              case none =>
//            }
//        }
//      }
//    }
//    result
//  }
//
//  override def getPredecessorPorts(port: ResourceUri): CSet[ResourceUri] = {
//    var result = isetEmpty[ResourceUri]
//    val node = getNode(port)
//    if (node.isDefined) {
//      if (port.startsWith(H.PORT_IN_TYPE)) {
//        getEdgeForPort(port).foreach { e =>
//          e.sourcePort match {
//            case Some(x) => result = result + x
//            case none =>
//          }
//        }
//      } else {
//        result = result ++ node.get.flowBackward(port)
//      }
//    }
//    result
//  }
//}
