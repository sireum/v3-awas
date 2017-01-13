
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
          val fromPortUri = Resource.getResourceUri(Resource.getResource(st.connection(conn).fromPort).get)
          fromNode.get.addPortEdge(fromPortUri, fedge)
          connNode.addPortEdge(connNode.inPorts.head, fedge)

          val tedge = result.addEdge(connNode, toNode.get)
          val toPortUri = Resource.getResourceUri(Resource.getResource(st.connection(conn).toPort).get)
          toNode.get.addPortEdge(toPortUri, tedge)
          connNode.addPortEdge(connNode.outPorts.head, tedge)
        }
    }

    result
  }

  def toFptcNode(node : org.sireum.awas.ast.Node) : Option[FptcNode] = {
    val res = getResource(node)
    if(res.isDefined && (H.isComponent(res.get) || H.isConnection(res.get))) {
      FptcNode.getNode(getResourceUri(res.get))
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


//
//trait FptcGraph[Node] extends AwasGraph[Node] {
//  def toDot(name : String): String
//  def sortedInEdges(node : FptcNode): Vector[Edge]
//  def propagate(node: FptcNode, out: IVector[Option[Fault]]): ISet[FptcNode]
//  def getFault(e : AwasEdge[FptcNode]) : ISet[Option[Fault]]
//}
//
//object FptcGraph {
//
//  type GEdge = AwasEdge[FptcNode]
//
//  def apply(m: Model): FptcGraph[FptcNode] = {
//
//    val result = new Fg()
//
//    FptcNode.newPool()
//
//    var compMap = imapEmpty[Name, Node]
//
//    Visitor.build({
//      case comp : ComponentDecl => {
//        val elem = (comp.compName, comp)
//        compMap = compMap.+(elem)
//        val compNode = FptcNode.createNode(comp)
//        result.addNode(compNode)
//      }
//        false
//    })(m)
//
//    Visitor.build({
//      case conn : ConnectionDecl => {
//        val fromCompNode = FptcNode createNode compMap(conn.fromComp)
//        val toCompNode = FptcNode createNode compMap(conn.toComp)
//        val connNode = FptcNode createNode conn
//        result.addNode(connNode)
//        val e1 = result.addEdge(fromCompNode, connNode)
//        addPortInfo(fromCompNode, conn, e1, isInPort = false)
//        val e2 = result.addEdge(connNode, toCompNode)
//        addPortInfo(toCompNode, conn, e2, isInPort = true)
//      }
//        false
//    })(m)
//    result
//  }
//
//  def addPortInfo(node : FptcNode, conn : ConnectionDecl,
//                  edge : AwasEdge[FptcNode], isInPort: Boolean) = {
//    assert(node.getType == FptcNodeProperty.COMP_NODE,
//      "Component node expected")
//    val ports = if(isInPort) node.getCompInPorts else node.getCompOutPorts
//    val portId = if(isInPort) conn.toPort else conn.fromPort
//    val port = ports.find(p => p.id.equals(portId))
//    if(port.isDefined) {
//      node.addPortEdgeInfo(port.get, edge)
//    }
//  }
//}
//
//class Fg extends FptcGraph[FptcNode] {
//  val graph = mutable.Graph[FptcNode, AwasEdge]()
//
////  private def getGraphEdge(e : AwasEdge[FptcNode]) = {
////    this.graph.get(e).toOuter
////  }
//
//  def sortedInEdges(node : FptcNode): Vector[Edge] = {
//    if(node.getType == FptcNodeProperty.COMP_NODE)
//      node.getCompInPorts.map{e =>
//        getOuterEdge(node.getPortEdgeInfo(e).edge)
//      }
//    else {
//      assert(this.inEdges(node).size == 1)
//      inEdges(node).toVector
//    }
//  }
//
//  def sortedOutEdges(node : FptcNode): Vector[Edge] = {
//    if(node.getType == FptcNodeProperty.COMP_NODE)
//      node.getCompOutPorts.map{e =>
//        getOuterEdge(node.getPortEdgeInfo(e).edge)
//      }
//    else {
//      assert(this.outEdges(node).size == 1)
//      outEdges(node).toVector
//    }
//  }
//
//  def getFault(e : AwasEdge[FptcNode]) : ISet[Option[Fault]] = {
//    this.graph.get(e).fault
//  }
//
//  def propagate(node: FptcNode, out: IVector[Option[Fault]]): ISet[FptcNode] = {
//    val edgeseq = sortedOutEdges(node)
//    var result = isetEmpty[FptcNode]
//    if(edgeseq.size == out.length) {
//      for(i <- edgeseq.indices) {
//        if(out(i).isDefined) {
//          this.graph.get(edgeseq(i)).setFault(out(i))
//          result = result + edgeseq(i)._2
//        }
//      }
//    }
//    result
//  }
//
//  //TODO: Someday move this into more abstract class of Node
//  def toDot(name : String): String = {
//    val dotExporter = new Export(graph)
//
//    val root = DotRootGraph(directed = true, id =
//      Some(scalax.collection.io.dot.Id(name)))
//
//    def edgeTransformer(innerEdge: Graph[FptcNode, AwasEdge]#EdgeT):
//    Option[(DotGraph, DotEdgeStmt)] = {
//      val  edge = innerEdge
//      Some((root, DotEdgeStmt(NodeId(edge.head.value.toString),
//        NodeId(edge.last.value.toString), Nil)))
//    }
//
//    def nodeTransformer(innerNode: Graph[FptcNode, AwasEdge]#NodeT):
//    Option[(DotGraph, DotNodeStmt)] =
//      Some((root,
//        DotNodeStmt(NodeId(innerNode.value.toString), innerNode.value.getType match {
//          case FptcNodeProperty.CONN_NODE => Nil
//          case _ => List(DotAttr(io.dot.Id("shape"), io.dot.Id("box")))
//        })))
//
//    val dot = dotExporter.toDot(root,
//      edgeTransformer,None,
//      cNodeTransformer = Some(nodeTransformer),
//      iNodeTransformer = Some(nodeTransformer),
//      spacing = Spacing(Indent.TwoSpaces)
//    )
//
//    val dotSorted = {
//      val lines = dot.linesWithSeparators.toList
//      val mid = lines.tail.init
//      s"${lines.head}${mid.sorted.mkString}${lines.last}"
//    }
//    dotSorted
//  }
//}