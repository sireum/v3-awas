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

package org.sireum.awas.reachability

import java.io.StringWriter
import java.util

import org.jgrapht.DirectedGraph
import org.jgrapht.ext.{ComponentAttributeProvider, DOTExporter, IntegerNameProvider, VertexNameProvider}
import org.jgrapht.graph.DefaultDirectedGraph
import org.sireum.awas.ast.Name
import org.sireum.awas.fptc._
import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper, TypeTable}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

object ReachabilityAnalysis {
  var nodePool = imapEmpty[ResourceUri,ReachNode]

  def apply(graph : FptcGraph[FptcNode], st : SymbolTable) : ReachGraph[ReachNode] = {
    val result = new RG()
    graph.nodes.foreach{n =>
      n.ports.foreach{p =>
        val node = result.addNode(new RN(p,n.uri, st))
        val tempt = n.getFptcPropagation(p)
        node.setProp(n.getFptcPropagation(p))
        nodePool += (p -> node)}

      if(n.uri.startsWith(SymbolTableHelper.COMPONENT_TYPE)) {
        nodePool += (n.uri+"/"+"source" -> result.addNode(new RN(n.uri+"/"+"source",n.uri, st)))
        nodePool += (n.uri+"/"+"sink" -> result.addNode(new RN(n.uri+"/"+"sink",n.uri, st)))
      }
    }

    graph.nodes.foreach{
    n =>
        if(n.uri.startsWith(SymbolTableHelper.CONNECTION_TYPE)){
          result.addEdge(nodePool(n.inPorts.head), nodePool(n.outPorts.head))
        } else {
          val cst = st.componentTable(n.uri)
          val td = st.compTypeDecl(n.uri).get
          val tt = st.typeTable(td)

          cst.flows.foreach{
            f =>
              val flow = cst.flow(f)
              if(flow.from.isEmpty) {
                val source = nodePool(n.uri+"/"+"source")
                source.setProp(toUri(flow.toE, tt).toSet)
                val t = Resource.getResource(flow.to.get).get.getUri
                result.addEdge(source, nodePool(Resource.getResource(flow.to.get).get.getUri))
              } else if(flow.to.isEmpty) {
                val sink = nodePool(n.uri+"/"+"sink")
                sink.setProp(toUri(flow.fromE, tt).toSet)
                result.addEdge(nodePool(Resource.getResource(flow.from.get).get.getUri),sink)
              } else {
                val source = Resource.getResource(flow.from.get).get.getUri
                val target = Resource.getResource(flow.to.get).get.getUri
                val edge = result.addEdge(nodePool(source), nodePool(target))
                edge.setFlow(Resource.getResource(flow).get.getUri)
              }

          }
        }
    }
    graph.edges.foreach{
      e =>
        val source = nodePool(e.sourcePort.get)
        val target = nodePool(e.targetPort.get)
        result.addEdge(source, target)
    }

    result
  }

  def toUri(n : ISeq[Name], tt:TypeTable) : ISeq[ResourceUri] = {
    var res = ivectorEmpty[ResourceUri]
    n.map(_.value.map(_.value).mkString("/")).foreach{
      e => val eUri = tt.enumElements.find(_.endsWith(e))
        res :+= eUri.get
    }
    res
  }


}


trait ReachGraph[Node] extends AwasGraph[Node] {
  def toDot(name : String) : String
}

class RG extends ReachGraph[ReachNode] {
  self: AwasGraph[ReachNode] =>
  override def toDot(name: String): String = {
    val de = new DOTExporter[ReachNode, Edge](new IntegerNameProvider[ReachNode],
      nodelabelProvier,
      null,attProvider,null)
    val sw = new StringWriter()
    de.exportGraph(graph, sw)
    sw.toString

  }

  protected val attProvider = new ComponentAttributeProvider[ReachNode]{
    override def getComponentAttributes(component: ReachNode): util.Map[String, String] = {
      import scala.collection.JavaConverters._
      val res = mlinkedMapEmpty[String, String]
      res("shape") = "record"
      if(component.isSliceed) {
        res("style") = "filled"
        res("color") = ".7 .3 1.0"
      }
      res.asJava
    }
  }

  protected val nodelabelProvier  = new VertexNameProvider[ReachNode] {
    override def getVertexName(vertex: ReachNode): String = {
      var result = ""
      result += vertex.uri.split("#").last
      result += "|{"+(vertex.getProp.map(_.split("#").last).mkString(", "))+"}|"
      result += "{"+vertex.getSlice.map(_.split("#").last).mkString(", ")+"}"
      result
    }
  }

  override protected val graph: DirectedGraph[ReachNode, Edge] = {
    new DefaultDirectedGraph[ReachNode, AwasEdge[ReachNode]](
      (source: ReachNode, target: ReachNode) => new ReachEdge(self, source, target)
    )
  }

  override def getNode(n: ReachNode): ReachNode = n

  override def addEdge(from: ReachNode, to: ReachNode): ReachEdge = {
    val edge = ReachEdge(self, from, to)
    graph.addEdge(from, to, edge)
    edge
  }
}

trait ReachNode {
  def uri : ResourceUri
  def compUri : ResourceUri
  def setProp(set : Set[ResourceUri])
  def getProp : Set[ResourceUri]
  def getSlice : Set[ResourceUri]
  def isSliceed : Boolean
  def setSliced
  def setSlice (r : ResourceUri)
  def errorExist(r : ResourceUri) : Boolean
}

final case class RN(uri : ResourceUri,compUri : ResourceUri, st : SymbolTable) extends ReachNode {
  var sliceResult = isetEmpty[ResourceUri]
  var propagation = isetEmpty[ResourceUri]
  var slices = false
  override def setProp(set: Set[ResourceUri]): Unit = this.propagation = this.propagation ++ set

  override def setSlice(r: ResourceUri): Unit = sliceResult += r

  override def errorExist(r: ResourceUri): Boolean = sliceResult.contains(r)

  override def getProp: Set[ResourceUri] = propagation

  override def getSlice: Set[ResourceUri] = sliceResult

  override def isSliceed: Boolean = slices

  override def setSliced: Unit = slices = true
}

final case class ReachEdge(owner : AwasGraph[ReachNode],
                          source : ReachNode,
                          target : ReachNode
                        ) extends AwasEdge[ReachNode]{
  private var flow : Option[ResourceUri] = None
  override def sourcePort: Option[ResourceUri] = Some(source.uri)

  override def targetPort: Option[ResourceUri] = Some(target.uri)

  def setFlow(resourceUri: ResourceUri) = {
    this.flow = Some(resourceUri)
  }

  def getFlow : Option[ResourceUri] = flow

}

