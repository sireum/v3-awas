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

import org.jgrapht.Graph
import org.jgrapht.alg.shortestpath.AllDirectedPaths
import org.jgrapht.io._
import org.sireum.awas.collector.CollectorErrorHelper._
import org.sireum.awas.collector.FlowCollector
import org.sireum.awas.graph.JGraphTAwasGraphImpl
import org.sireum.awas.symbol.SymbolTableHelper
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

class FlowGraphImpl extends FlowGraph[FlowNode] with FlowGraphUpdate[FlowNode] {
  self: FlowGraph[FlowNode] =>

  val ef = new FlowEdgeFactory()

  val superClass = new JGraphTAwasGraphImpl[FlowNode, FlowEdge[FlowNode]](ef)
  override type Edge = FlowEdge[FlowNode]
  val graph: Graph[FlowNode, FlowEdge[FlowNode]] = superClass.graph

  val H = SymbolTableHelper

  private var attProvider = new ComponentAttributeProvider[FlowNode] {
    override def getComponentAttributes(component: FlowNode): util.Map[String, Attribute] = {
      import scala.collection.JavaConverters._
      val res = mlinkedMapEmpty[String, Attribute]
      res("shape") = new DefaultAttribute("record", AttributeType.STRING)
      res.asJava
    }
  }

  private var nIdProvider = new StringComponentNameProvider[FlowNode] {
    override def getName(component: FlowNode): FileResourceUri = {
      component.getUri.split(H.ID_SEPARATOR).last
    }
  }

  private var nlabelProvide = new StringComponentNameProvider[FlowNode] {
    override def getName(vertex: FlowNode): String = {
      var result = ""
      if (vertex.getUri.startsWith(SymbolTableHelper.COMPONENT_TYPE)) {
        result += "{In Port|" + vertex.inPorts.map { ip =>
          val pname = ip.split(H.ID_SEPARATOR).last
          "<" + pname + ">" + pname
        }.mkString("|") + "} |"
        result += SymbolTableHelper.COMPONENT_TYPE + "\n" + vertex.getUri.split(H.ID_SEPARATOR).last + "|"
        result += "{Out Port|" + vertex.outPorts.map { ip =>
          val pname = ip.split(H.ID_SEPARATOR).last
          "<" + pname + ">" + pname
        }.mkString("|") + "} "
      } else {
        result = SymbolTableHelper.CONNECTION_TYPE + "\n" + vertex.getUri.split(H.ID_SEPARATOR).last
      }
      result
    }
  }

  private var eAttrProvider = new ComponentAttributeProvider[Edge] {
    override def getComponentAttributes(component: Edge): util.Map[String, Attribute] = {
      import scala.collection.JavaConverters._
      val res = mlinkedMapEmpty[String, Attribute]
      if (component.source.isComponent) {
        res("tailport") =
          new DefaultAttribute(component.sourcePort.get.split(H.ID_SEPARATOR).last, AttributeType.STRING)
      }
      if (component.target.isComponent) {
        res("headport") =
          new DefaultAttribute(component.targetPort.get.split(H.ID_SEPARATOR).last, AttributeType.STRING)
      }
      res.asJava
    }
  }

  private var portEdgeMap: IMap[ResourceUri, ISet[Edge]] = imapEmpty[ResourceUri, ISet[Edge]]
  private var portNodeMap: IMap[ResourceUri, FlowNode] = imapEmpty[ResourceUri, FlowNode]
  private var edgePortsMap: IMap[Edge, (ResourceUri, ResourceUri)] = imapEmpty[Edge, (ResourceUri, ResourceUri)]

  override def toDot: String = {
    val de = new DOTExporter[FlowNode, Edge](nIdProvider, nlabelProvide, null, this.attProvider, eAttrProvider)
    val sw = new StringWriter()
    de.exportGraph(graph, sw)
    sw.toString
  }

  def addPortEdge(port: ResourceUri, edge: Edge): Unit = {
    portEdgeMap += port -> (portEdgeMap.getOrElse(port, isetEmpty[Edge]) + edge)
  }

  override def addEdge(from: FlowNode, to: FlowNode): Edge = {
    val edge = new FlowEdgeFactory().createEdge(self, from, to)
    graph.addEdge(from, to, edge)
    edge
  }

  override def addNode(n: FlowNode): FlowNode = {
    n.ports.foreach { p =>
      portNodeMap += (p -> n)
    }
    graph.addVertex(n)
    n
  }

  override def addEdgePortRelation(edge: FlowEdge[FlowNode], source: ResourceUri, target: ResourceUri): Unit = {
    assert(!edgePortsMap.contains(edge))
    edgePortsMap += (edge -> (source, target))
  }

  override def getNode(n: FlowNode): FlowNode = n

  def getNode(uri: ResourceUri): Option[FlowNode] = {
    if (uri.startsWith(H.COMPONENT_TYPE) ||
      uri.startsWith(H.CONNECTION_TYPE)) {
      FlowNode.getNode(uri)
    } else {
      portNodeMap.get(uri)
    }
  }

  override def getEdgeForPort(port: ResourceUri): Set[Edge] =
    portEdgeMap.getOrElse(port, isetEmpty[Edge])

  override def getEdges(sourcePort: ResourceUri, targetPort: ResourceUri): ISet[Edge] = {
    var res = isetEmpty[Edge]
    edgePortsMap.foreach { v =>
      if (v._2 == (sourcePort, targetPort)) {
        res = res + v._1
      }
    }
    res
  }

  override def getSuccessorPorts(port: ResourceUri): FlowCollector = {
    val node = getNode(port)
    if (node.isDefined) {
      if (port.startsWith(H.PORT_IN_TYPE)) {
        node.get.flowForward(port)
      } else {
        //outport: use edge to get the successor
        var res = isetEmpty[ResourceUri]
        var edges = isetEmpty[Edge]

        getEdgeForPort(port).foreach { e =>
          e.targetPort match {
            case Some(x) => {
              res = res + x
              edges = edges + e
            }
            case _ =>
          }
        }
        FlowCollector(res, edges, isetEmpty[ResourceUri], isetEmpty[Tag])
      }
    } else {
      FlowCollector(
        isetEmpty[ResourceUri],
        isetEmpty[Edge],
        isetEmpty[ResourceUri],
        isetEmpty[Tag] + errorMessageGen(MISSING_NODE, port, ReachAnalysisStage.Port)
      )
    }
  }

  override def getPredecessorPorts(port: ResourceUri): FlowCollector = {
    val node = getNode(port)
    if (node.isDefined) {

      if (port.startsWith(H.PORT_IN_TYPE)) {
        var res = isetEmpty[ResourceUri]
        var edges = isetEmpty[Edge]
        getEdgeForPort(port).foreach { e =>
          e.sourcePort match {
            case Some(x) => {
              res = res + x
              edges = edges + e
            }
            case _ =>
          }
        }
        FlowCollector(res, edges, isetEmpty[ResourceUri], isetEmpty[Tag])
      } else {
        //outport: use edge to get the successor
        node.get.flowBackward(port)
      }
    } else {
      FlowCollector(
        isetEmpty[ResourceUri],
        isetEmpty[Edge],
        isetEmpty[ResourceUri],
        isetEmpty[Tag] + errorMessageGen(MISSING_NODE, port, ReachAnalysisStage.Port)
      )
    }
  }

  override def getAllPathsNodes(source: FlowNode, sink: FlowNode): Set[Seq[FlowNode]] = {
    import scala.collection.JavaConverters._
    val allGraphPath = new AllDirectedPaths[FlowNode, Edge](graph)
    val pathNodes = allGraphPath.getAllPaths(source, sink, true, null).asScala.map(_.getVertexList.asScala.toSeq)
    pathNodes.toSet
  }

  override def getAllPathsEdges(source: FlowNode, sink: FlowNode): Set[Seq[Edge]] = {
    import scala.collection.JavaConverters._
    val allGraphPath = new AllDirectedPaths[FlowNode, Edge](graph)
    val pathNodes = allGraphPath.getAllPaths(source, sink, true, null).asScala.map(_.getEdgeList.asScala.toSeq)
    pathNodes.toSet
  }

  override def getPortsFromEdge(edge: Edge): Option[(ResourceUri, ResourceUri)] = {
    edgePortsMap.get(edge)
  }

  override def nodes: Iterable[FlowNode] = {
    val nds = superClass.nodes
    nds
  }

  override def numOfNodes: Natural = superClass.numOfNodes

  override def edges: Iterable[Edge] = superClass.edges.map(_.asInstanceOf[Edge])

  override def numOfEdges: Natural = superClass.numOfEdges

  override def hasNode(n: FlowNode): Boolean = superClass.hasNode(n)

  override def hasEdge(n1: FlowNode, n2: FlowNode): Boolean = superClass.hasEdge(n1, n2)

  override def getCycles: Set[Seq[FlowNode]] = superClass.getCycles

  override def getEdge(n1: FlowNode, n2: FlowNode): CSet[FlowEdge[FlowNode]] =
    superClass.getEdge(n1, n2)

  override def getEdges(n: FlowNode): CSet[FlowEdge[FlowNode]] =
    superClass.getEdges(n)

  override def getIncomingEdges(node: FlowNode): CSet[FlowEdge[FlowNode]] =
    superClass.getIncomingEdges(node)

  override def getOutgoingEdges(node: FlowNode): CSet[FlowEdge[FlowNode]] =
    superClass.getOutgoingEdges(node)

  override def getSuccessorNodes(node: FlowNode): CSet[FlowNode] =
    superClass.getSuccessorNodes(node)

  override def getPredecessorNodes(node: FlowNode): CSet[FlowNode] =
    superClass.getPredecessorNodes(node)

  override def getAllPaths(source: FlowNode, sink: FlowNode): Set[Set[FlowNode]] = superClass.getAllPaths(source, sink)

  override def setNodeAttProvider(nodeAtt: ComponentAttributeProvider[FlowNode]): Unit = {
    this.attProvider = nodeAtt
  }

  override def setNodeIdProvider(nodeId: StringComponentNameProvider[FlowNode]): Unit = {
    this.nIdProvider = nodeId
  }

  override def setNodeLabelProvider(nodeLabel: StringComponentNameProvider[FlowNode]): Unit = {
    this.nlabelProvide = nodeLabel
  }

  override def setEdgeAttrProvider(edgeAtt: ComponentAttributeProvider[FlowEdge[FlowNode]]): Unit = {
    this.eAttrProvider = edgeAtt
  }
}
