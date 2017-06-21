
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

import org.sireum.awas.ast.{Builder, Model}
import org.sireum.awas.graph.{AwasEdge, AwasGraph}
import org.sireum.awas.symbol.Resource._
import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.{AccumulatingTagReporter, CSet, ConsoleTagReporter, FileResourceUri}

trait FlowGraph[Node] extends AwasGraph[Node] {
  def toDot: String

  def getEdgeForPort(port: ResourceUri): Set[Edge]

  def getSuccessorPorts(port: ResourceUri): CSet[ResourceUri]

  def getPredecessorPorts(port: ResourceUri): CSet[ResourceUri]

  def getNode(port: ResourceUri): Option[Node]
}

trait FptcEdge[Node] extends AwasEdge[Node] {
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

    st.components.foreach{
      comp => result.addNode(FlowNode.createNode(comp, st))
    }

    st.connections.foreach {
      conn =>
        val connNode = result.addNode(FlowNode.createNode(conn, st))
        val fromNode = toFptcNode(st.connection(conn).fromComp)
        val toNode = toFptcNode(st.connection(conn).toComp)

        if(fromNode.isDefined && toNode.isDefined) {
          val fedge = result.addEdge(fromNode.get, connNode)
          val fromPortRes = Resource.getResource(st.connection(conn).fromPort)
          if(fromPortRes.isDefined) {
            val fromPortUri = fromPortRes.get.toUri
            result.addPortEdge(fromPortUri, fedge)
            result.addPortEdge(connNode.inPorts.head, fedge)
          }
          val tedge = result.addEdge(connNode, toNode.get)
          val toPortRes = Resource.getResource(st.connection(conn).toPort)
          if(toPortRes.isDefined) {
            val toPortUri = toPortRes.get.toUri
            result.addPortEdge(toPortUri, tedge)
            result.addPortEdge(connNode.outPorts.head, tedge)
          }
        }
    }
    result
  }

  private def toFptcNode(node: org.sireum.awas.ast.Node): Option[FlowNode] = {
    val res = getResource(node)
    if(res.isDefined && (H.isComponent(res.get) || H.isConnection(res.get))) {
      FlowNode.getNode(res.get.toUri)
    } else {
      None
    }
  }
}

