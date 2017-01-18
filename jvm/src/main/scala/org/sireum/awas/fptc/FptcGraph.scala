
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

import java.io.StringWriter
import java.util

import org.jgrapht.DirectedGraph
import org.jgrapht.ext.{ComponentAttributeProvider, DOTExporter, IntegerNameProvider, VertexNameProvider}
import org.jgrapht.graph.DefaultDirectedGraph
import org.sireum.awas.ast.Model
import org.sireum.awas.graph.{AwasEdge, AwasGraph}
import org.sireum.awas.symbol.Resource._
import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper}
import org.sireum.util._

trait FptcGraph[Node] extends AwasGraph[Node] {
  def toDot(name : String) : String
}

object FptcGraph{
  val H = SymbolTableHelper
  def apply(m : Model, st : SymbolTable) : FptcGraph[FptcNode] = {
    val result = new Fg()

    FptcNode.newPool()

    st.components.foreach{
      comp => result.addNode(FptcNode.createNode(comp, st))
    }

    st.connections.foreach {
      conn =>
        val connNode = result.addNode(FptcNode.createNode(conn, st))
        val fromNode = toFptcNode(st.connection(conn).fromComp)
        val toNode = toFptcNode(st.connection(conn).toComp)

        if(fromNode.isDefined && toNode.isDefined) {
          val fedge = result.addEdge(fromNode.get, connNode)
          val fromPortUri = Resource.getResource(st.connection(conn).fromPort).get.toUri
          fromNode.get.addPortEdge(fromPortUri, fedge)
          connNode.addPortEdge(connNode.inPorts.head, fedge)

          val tedge = result.addEdge(connNode, toNode.get)
          val toPortUri = Resource.getResource(st.connection(conn).toPort).get.toUri
          toNode.get.addPortEdge(toPortUri, tedge)
          connNode.addPortEdge(connNode.outPorts.head, tedge)
        }
    }

    result
  }

  def toFptcNode(node : org.sireum.awas.ast.Node) : Option[FptcNode] = {
    val res = getResource(node)
    if(res.isDefined && (H.isComponent(res.get) || H.isConnection(res.get))) {
      FptcNode.getNode(res.get.toUri)
    } else {
      None
    }
  }
}

class Fg extends FptcGraph[FptcNode] {
  self =>
  override def addEdge(from: FptcNode, to: FptcNode) = {
    val edge = FptcEdge(self, from, to)
    graph.addEdge(from, to, edge)
    edge
  }
  override def toDot(name: String): String = {
    val de = new DOTExporter[FptcNode, Edge](new IntegerNameProvider[FptcNode],
      nlabelProvide,null,
        this.attProvider, null)
    val sw = new StringWriter()
    de.exportGraph(graph, sw)
    sw.toString
  }

  protected val attProvider = new ComponentAttributeProvider[FptcNode] {
    override def getComponentAttributes(component: FptcNode): util.Map[String, String] = {
      import scala.collection.JavaConverters._
      val res = mlinkedMapEmpty[String, String]
      res("shape") = "record"
      res.asJava
    }
  }

  protected val nlabelProvide = new VertexNameProvider[FptcNode] {
    override def getVertexName(vertex: FptcNode): String = {
      var result = ""
      if(vertex.uri.startsWith(SymbolTableHelper.COMPONENT_TYPE)) {
        result += "{In Port|"+vertex.inPorts.map{
          ip =>
          val pname=ip.split("#").last
            "<"+pname+">"+pname
        }.mkString("|") +"} |"
        result += SymbolTableHelper.COMPONENT_TYPE +"\n"+ vertex.uri.split("#").last + "|"
        result += "{Out Port\n"+vertex.outPorts.map(_.split("#").last).mkString("|")+"}"
      } else {

        result = SymbolTableHelper.CONNECTION_TYPE + "\n" + vertex.uri.split("#").last
      }
      result

    }
  }

  override protected val graph: DirectedGraph[FptcNode, Edge] = {
    new DefaultDirectedGraph[FptcNode, AwasEdge[FptcNode]](
      (source: FptcNode, target: FptcNode) => new FptcEdge(self, source, target)
    )
  }

  override def getNode(n: FptcNode) = n
}