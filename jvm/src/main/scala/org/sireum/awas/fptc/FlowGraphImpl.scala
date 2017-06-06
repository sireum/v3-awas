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

package org.sireum.awas.fptc

import java.io.StringWriter
import java.util

import org.jgrapht.DirectedGraph
import org.jgrapht.ext._
import org.jgrapht.graph.DefaultDirectedGraph
import org.sireum.awas.graph.AwasGraphUpdate
import org.sireum.awas.symbol.SymbolTableHelper
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

class FlowGraphImpl extends FlowGraph[FlowNode] with AwasGraphUpdate[FlowNode] {
  self =>

  type FEdge = FptcEdge[FlowNode]
  override val graph: DirectedGraph[FlowNode, FEdge] = {
    new DefaultDirectedGraph[FlowNode, FEdge](
      (source: FlowNode, target: FlowNode) => FlowEdgeImpl(self, source, target)
    )
  }
  val H = SymbolTableHelper

  protected val attProvider = new ComponentAttributeProvider[FlowNode] {
    override def getComponentAttributes(component: FlowNode): util.Map[String, String] = {
      import scala.collection.JavaConverters._
      val res = mlinkedMapEmpty[String, String]
      res("shape") = "record"
      res.asJava
    }
  }

  protected val nIdProvider = new StringComponentNameProvider[FlowNode] {
    override def getName(component: FlowNode): FileResourceUri = {
      component.getUri.split(H.ID_SEPARATOR).last
    }
  }

  protected val nlabelProvide = new StringComponentNameProvider[FlowNode] {
    override def getName(vertex: FlowNode): String = {
      var result = ""
      if (vertex.getUri.startsWith(SymbolTableHelper.COMPONENT_TYPE)) {
        result += "{In Port|" + vertex.inPorts.map {
          ip =>
            val pname = ip.split(H.ID_SEPARATOR).last
            "<" + pname + ">" + pname
        }.mkString("|") + "} |"
        result += SymbolTableHelper.COMPONENT_TYPE + "\n" + vertex.getUri.split(H.ID_SEPARATOR).last + "|"
        result += "{Out Port|" + vertex.outPorts.map {
          ip =>
            val pname = ip.split(H.ID_SEPARATOR).last
            "<" + pname + ">" + pname
        }.mkString("|") + "} "
      } else {
        result = SymbolTableHelper.CONNECTION_TYPE + "\n" + vertex.getUri.split(H.ID_SEPARATOR).last
      }
      result
    }
  }

  protected val eAttrProvider = new ComponentAttributeProvider[FEdge] {
    override def getComponentAttributes(component: FEdge): util.Map[String, String] = {
      import scala.collection.JavaConverters._
      val res = mlinkedMapEmpty[String, String]
      if (component.source.isComponent) {
        res("tailport") = component.sourcePort.get.split(H.ID_SEPARATOR).last
      }
      if (component.target.isComponent) {
        res("headport") = component.targetPort.get.split(H.ID_SEPARATOR).last
      }
      res.asJava
    }
  }
  var portEdgeMap: IMap[ResourceUri, ISet[FEdge]] = imapEmpty[ResourceUri, ISet[FEdge]]
  var portNodeMap: IMap[ResourceUri, FlowNode] = imapEmpty[ResourceUri, FlowNode]

  override def toDot: String = {
    val de = new DOTExporter[FlowNode, FEdge](nIdProvider,
      nlabelProvide, null,
      this.attProvider, eAttrProvider)
    val sw = new StringWriter()
    de.exportGraph(graph, sw)
    sw.toString
  }

  def addPortEdge(port: ResourceUri, edge: FEdge): Unit = {
    if (portEdgeMap.keySet.contains(port)) {
      portEdgeMap += port -> (portEdgeMap(port) + edge)
    } else {
      portEdgeMap += port -> (isetEmpty[FEdge] + edge)
    }
  }

  override def addEdge(from: FlowNode, to: FlowNode): FlowEdgeImpl = {
    val edge = FlowEdgeImpl(self, from, to)
    graph.addEdge(from, to, edge)
    edge
  }

  override def addNode(n: FlowNode): FlowNode = {
    n.ports.foreach { p => portNodeMap += (p -> n) }
    graph.addVertex(n)
    n
  }


  override def getNode(n: FlowNode): FlowNode = n

  override def getSuccessorPorts(port: ResourceUri): CSet[ResourceUri] = {
    var result = isetEmpty[ResourceUri]
    val node = getNode(port)
    if (node.isDefined) {
      if (port.startsWith(H.PORT_IN_TYPE)) {
        result = result ++ node.get.flowForward(port)
      } else {
        //outport: use edge to get the successor
        getEdgeForPort(port).foreach {
          e =>
            e.targetPort match {
              case Some(x) => result = result + x
              case _ =>
            }
        }
      }
    }
    result
  }

  def getNode(uri: ResourceUri): Option[FlowNode] = {
    if (uri.startsWith(H.COMPONENT_TYPE) ||
      uri.startsWith(H.CONNECTION_TYPE)) {
      FlowNode.getNode(uri)
    } else {
      portNodeMap.get(uri)
    }
  }

  override def getEdgeForPort(port: ResourceUri): Set[FEdge] =
    portEdgeMap.getOrElse(port, isetEmpty[FEdge])

  override def getPredecessorPorts(port: ResourceUri): CSet[ResourceUri] = {
    var result = isetEmpty[ResourceUri]
    val node = getNode(port)
    if (node.isDefined) {
      if (port.startsWith(H.PORT_IN_TYPE)) {
        getEdgeForPort(port).foreach { e =>
          e.sourcePort match {
            case Some(x) => result = result + x
            case _ =>
          }
        }
      } else {
        //outport: use edge to get the successor
        result = result ++ node.get.flowBackward(port)
      }
    }
    result
  }
}
