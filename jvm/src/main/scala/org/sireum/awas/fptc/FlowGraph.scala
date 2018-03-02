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

import java.nio.file.Paths

import org.jgrapht.io.{ComponentAttributeProvider, StringComponentNameProvider}
import org.sireum.awas.ast.{Builder, Model}
import org.sireum.awas.collector.FlowCollector
import org.sireum.awas.graph.{AwasEdge, AwasGraph, AwasGraphUpdate}
import org.sireum.awas.symbol.Resource._
import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.{AccumulatingTagReporter, ConsoleTagReporter, FileResourceUri}

trait FlowGraph[Node] extends AwasGraph[Node] {
  type Edge = FlowEdge[Node]

  def toDot: String

  def getEdgeForPort(port: ResourceUri): Set[Edge]

  def getEdges(sourcePort: ResourceUri, targetPort: ResourceUri): Set[Edge]

  def getSuccessorPorts(port: ResourceUri): FlowCollector

  def getPredecessorPorts(port: ResourceUri): FlowCollector

  def getNode(port: ResourceUri): Option[Node]

  def getPortsFromEdge(edge: Edge): Option[(ResourceUri, ResourceUri)]

  def getAllPathsNodes(source: FlowNode, sink: FlowNode): Set[Seq[FlowNode]]

  def getAllPathsEdges(source: FlowNode, sink: FlowNode): Set[Seq[Edge]]

}


trait FlowGraphUpdate[Node] extends AwasGraphUpdate[Node] {
  self: FlowGraph[Node] =>

  def addEdgePortRelation(edge: FlowEdge[Node], source: ResourceUri, target: ResourceUri): Unit

  def setNodeAttProvider(nodeAtt: ComponentAttributeProvider[FlowNode])

  def setNodeIdProvider(nodeId: StringComponentNameProvider[FlowNode])

  def setNodeLabelProvider(nodeLabel: StringComponentNameProvider[FlowNode])

  def setEdgeAttrProvider(edgeAtt: ComponentAttributeProvider[Edge])
}

trait FlowEdge[Node] extends AwasEdge[Node] {
  def sourcePort: Option[ResourceUri]

  def targetPort: Option[ResourceUri]
}

/**
  * Factory Methods to build graph
  */
object FlowGraph {
  val H = SymbolTableHelper

  def apply(modelFile: FileResourceUri): Option[FlowGraph[FlowNode]] = {
    import org.sireum.util.jvm.FileUtil._
    val basePath = Paths.get(fileUri(this.getClass, s".."))
    val relativeUri = basePath.relativize(Paths.get(modelFile))
    val modelOpt = Builder(Some(relativeUri.toString), readFile(modelFile)._1)
    if (modelOpt.isDefined) {
      Some(apply(modelOpt.get))
    } else {
      None
    }
  }

  def apply(m: Model): FlowGraph[FlowNode] = {
    implicit val reporter: AccumulatingTagReporter = new ConsoleTagReporter
    val st = SymbolTable(m)
    apply(m, st)
  }

  def apply(m: Model, st: SymbolTable): FlowGraph[FlowNode] = {
    val result = new FlowGraphImpl()

    FlowNode.newPool()

    st.components.foreach { comp =>
      result.addNode(FlowNode.createNode(comp, st))
    }

    st.connections.foreach { conn =>
      val connNode = result.addNode(FlowNode.createNode(conn, st))
      val fromNode = toFptcNode(st.connection(conn).fromComp)
      val toNode = toFptcNode(st.connection(conn).toComp)

      if (fromNode.isDefined && toNode.isDefined) {
        val fedge = result.addEdge(fromNode.get, connNode)
        val fromPortRes = Resource.getResource(st.connection(conn).fromPort)
        if (fromPortRes.isDefined) {
          val fromPortUri = fromPortRes.get.toUri
          result.addPortEdge(fromPortUri, fedge)
          result.addPortEdge(connNode.inPorts.find(_.startsWith(H.PORT_IN_VIRTUAL_TYPE)).get, fedge)
          result
            .addEdgePortRelation(fedge, fromPortUri, connNode.inPorts.find(_.startsWith(H.PORT_IN_VIRTUAL_TYPE)).get)
        }
        val tedge = result.addEdge(connNode, toNode.get)
        val toPortRes = Resource.getResource(st.connection(conn).toPort)
        if (toPortRes.isDefined) {
          val toPortUri = toPortRes.get.toUri
          result.addPortEdge(toPortUri, tedge)
          result.addPortEdge(connNode.outPorts.find(_.startsWith(H.PORT_OUT_VIRTUAL_TYPE)).get, tedge)
          result
            .addEdgePortRelation(tedge, connNode.outPorts.find(_.startsWith(H.PORT_OUT_VIRTUAL_TYPE)).get, toPortUri)
        }
      }
    }

    st.deployments.foreach { dep =>
      val fromNode = FlowNode.getNode(dep._1)
      val toNode = FlowNode.getNode(dep._2)
      if (fromNode.isDefined && toNode.isDefined) {
        val edge1 = result.addEdge(fromNode.get, toNode.get)
        val frompUri1 = fromNode.get.outPorts.find(_.startsWith(H.PORT_OUT_BIND_TYPE)).get
        val topUri1 = toNode.get.inPorts.find(_.startsWith(H.PORT_IN_BIND_TYPE)).get
        result.addPortEdge(frompUri1, edge1)
        result.addPortEdge(topUri1, edge1)
        result.addEdgePortRelation(edge1, frompUri1, topUri1)

        val edge2 = result.addEdge(toNode.get, fromNode.get)
        val frompUri2 = toNode.get.outPorts.find(_.startsWith(H.PORT_OUT_BIND_TYPE)).get
        val topUri2 = fromNode.get.inPorts.find(_.startsWith(H.PORT_IN_BIND_TYPE)).get

        result.addPortEdge(frompUri2, edge2)
        result.addPortEdge(topUri2, edge2)
        result.addEdgePortRelation(edge2, frompUri2, topUri2)
      }
    }
    result
  }

  private def toFptcNode(node: org.sireum.awas.ast.Node): Option[FlowNode] = {
    val res = getResource(node)
    if (res.isDefined && (H.isComponent(res.get) || H.isConnection(res.get))) {
      FlowNode.getNode(res.get.toUri)
    } else {
      None
    }
  }
}
