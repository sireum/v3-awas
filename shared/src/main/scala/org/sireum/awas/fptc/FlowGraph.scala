/*
 *
 *  Copyright (c) 2017, Hariharan Thiagarajan, Kansas State University
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package org.sireum.awas.fptc

import org.sireum.ST
import org.sireum.awas.ast.Model
import org.sireum.awas.collector.{FlowCollector, FlowErrorNextCollector}
import org.sireum.awas.graph.{AwasEdge, AwasGraph, AwasGraphUpdate}
import org.sireum.awas.slang.Aadl2Awas
import org.sireum.awas.symbol.Resource._
import org.sireum.awas.symbol.{ComponentTable, Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._
import upickle.default.{macroRW, ReadWriter => RW}


trait FlowGraph[Node, Edge <: AwasEdge[Node]] extends AwasGraph[Node, Edge] {
  // type Edge = FlowEdge[Node]

  def getDot: String

  def getEdgeForPort(port: ResourceUri): Set[Edge]

  def getEdges(sourcePort: ResourceUri, targetPort: ResourceUri): Set[Edge]

  def getSuccessorPorts(port: ResourceUri): FlowCollector

  def getPredecessorPorts(port: ResourceUri): FlowCollector

  def getSuccessorError(tuple: (ResourceUri, ResourceUri)): FlowErrorNextCollector

  def getPredecessorError(tuple: (ResourceUri, ResourceUri)): FlowErrorNextCollector

  def getNode(port: ResourceUri): Option[Node]

  def getPortsFromEdge(edge: Edge): Option[(ResourceUri, ResourceUri)]

  def getUri: ResourceUri

  def getInPortNodes: ISet[FlowNode]

  def getOutPortNodes: ISet[FlowNode]

  //  def getAllPathsNodes(source: FlowNode, sink: FlowNode): Set[Seq[FlowNode]]
  //
  //  def getAllPathsEdges(source: FlowNode, sink: FlowNode): Set[Seq[Edge]]

}


trait FlowGraphUpdate[Node, Edge <: AwasEdge[Node]] extends AwasGraphUpdate[Node, Edge] {
  self: FlowGraph[Node, Edge] =>

  def addEdgePortRelation(edge: Edge, source: ResourceUri, target: ResourceUri): Unit

  def addPortEdge(port: ResourceUri, edge: Edge): Unit

  def setNodeToST(f: Node => ST): Unit

  def setEdgeToST(f: Edge => ST): Unit

  def setGraphAttributes(attributes: ISeq[ST]): Unit

  def setDot(updatedToDot: () => String): Unit

  //  def setNodeAttProvider(nodeAtt: ComponentAttributeProvider[FlowNode])
  //
  //  def setNodeIdProvider(nodeId: StringComponentNameProvider[FlowNode])
  //
  //  def setNodeLabelProvider(nodeLabel: StringComponentNameProvider[FlowNode])
  //
  //  def setEdgeAttrProvider(edgeAtt: ComponentAttributeProvider[Edge])
}

sealed trait FlowEdge[Node] extends AwasEdge[Node] {
  def sourcePort: Option[ResourceUri]

  def targetPort: Option[ResourceUri]
}

object FlowEdge {
  implicit def rw: RW[FlowEdge[FlowNode]] = RW.merge(
    FlowEdgeImpl.rw
  )
}

case class FlowEdgeImpl(
                         owner: ResourceUri,
                         sourceNodeUri: ResourceUri,
                         targetNodeUri: ResourceUri
                       ) extends FlowEdge[FlowNode] {
  //    self: FlowEdge[FlowNode] =>

  //either source or target should be a connection
  //    val conn: FlowNode =
  //      if (source.getUri.startsWith(SymbolTableHelper.CONNECTION_TYPE))
  //        source
  //      else target
  //
  //    val isSourceConn: Boolean = conn == source

  override def sourcePort: Option[ResourceUri] = {
    FlowNode.getGraph(owner).flatMap(_.getPortsFromEdge(this)) match {
      case Some(x) => Some(x._1)
      case None => None
    }
  }

  override def targetPort: Option[ResourceUri] = {
    FlowNode.getGraph(owner).flatMap(_.getPortsFromEdge(this)) match {
      case Some(x) => Some(x._2)
      case None => None
    }
  }

  override def source: FlowNode = {
    val source = FlowNode.getNode(sourceNodeUri)
    assert(source.isDefined, "if edge exists, then source and target nodes are defined")
    source.get
  }

  override def target: FlowNode = {
    val target = FlowNode.getNode(targetNodeUri)
    assert(target.isDefined, "if edge exists, then source and target nodes are defined")
    target.get
  }
}

object FlowEdgeImpl {
  implicit def rw: RW[FlowEdgeImpl] = macroRW
}



/**
  * Factory Methods to build graph
  */
object FlowGraph {
  val H = SymbolTableHelper

  private var edgesRemoved = imapEmpty[ResourceUri, IMap[FlowNode.Edge, (ResourceUri, ResourceUri)]]


  //  def apply(modelFile: FileResourceUri): Option[FlowGraph[FlowNode, FlowEdge[FlowNode]]] = {
  //    import org.sireum.util.jvm.FileUtil._
  //    val basePath = Paths.get(fileUri(this.getClass, s".."))
  //    val relativeUri = basePath.relativize(Paths.get(modelFile))
  //    val modelOpt = Builder(Some(relativeUri.toString), readFile(modelFile)._1)
  //    if (modelOpt.isDefined) {
  //      Some(apply(modelOpt.get))
  //    } else {
  //      None
  //    }
  //  }


  def apply(m: Model, includeBindingEdges: Boolean): FlowGraph[FlowNode, FlowEdge[FlowNode]] = {
    implicit val reporter: AccumulatingTagReporter = new ConsoleTagReporter
    val st = SymbolTable(m)
    apply(m, st, includeBindingEdges)
  }

  def buildGraph(cst: ComponentTable, st: SymbolTable)
  : FlowGraph[FlowNode, FlowEdge[FlowNode]] = {
    val result = new FlowGraphImpl(cst.componentUri, st)
    var portUris = isetEmpty[ResourceUri]
    cst.ports.foreach(p => result.addNode(FlowNode.createNode(p, st, result)))
    cst.subComponents.foreach(sc => result.addNode(FlowNode.createNode(sc, st, result)))

    cst.connections.foreach { conn =>
      val connST = cst.connectionTable(conn)
      val connNode = result.addNode(FlowNode.createNode(conn, st, result))
      assert(connST.fromPort.isDefined)

      val fromNode = if (connST.fromCompUri.isDefined && connST.fromCompUri.get != cst.componentUri) {
        FlowNode.getNode(connST.fromCompUri.get)
      } else {
        FlowNode.getNode(connST.fromPort.get)
      }

      val toNode = if (connST.toCompUri.isDefined && connST.toCompUri.get != cst.componentUri) {
        FlowNode.getNode(connST.toCompUri.get)
      } else {
        FlowNode.getNode(connST.toPort.get)
      }

      if (fromNode.isDefined && toNode.isDefined) {

        val fedge = result.addEdge(fromNode.get, connNode)

        if (connST.fromPort.isDefined) {
          result.addPortEdge(connST.fromPort.get, fedge)
          result.addPortEdge(connNode.inPorts.find(
            _.startsWith(H.PORT_IN_VIRTUAL_TYPE)).get, fedge)
          result.addEdgePortRelation(fedge,
            connST.fromPort.get, connNode.inPorts.find(
              _.startsWith(H.PORT_IN_VIRTUAL_TYPE)).get)
        }

        val tedge = result.addEdge(connNode, toNode.get)
        if (connST.toPort.isDefined) {
          result.addPortEdge(connST.toPort.get, tedge)
          result.addPortEdge(connNode.outPorts.find(
            _.startsWith(H.PORT_OUT_VIRTUAL_TYPE)).get, tedge)
          result.addEdgePortRelation(tedge, connNode.outPorts.find(
            _.startsWith(H.PORT_OUT_VIRTUAL_TYPE)).get, connST.toPort.get)
        }
      }
    }

    cst.deployments.foreach { dep =>
      val fromUri = dep._1
      val toUri = dep._2

      val fromPortUri = if (H.isPort(dep._1)) {
        dep._1
      } else {
        //must be a connection
        assert(H.getUriType(dep._1) == H.CONNECTION_TYPE, "Deployment uri is not port or connection")
        cst.connectionTable(dep._1).ports.filter(_.startsWith(H.PORT_OUT_BIND_TYPE)).head
      }

      val toPortUri = if (H.isPort(dep._2)) {
        dep._2
      } else {
        assert(H.getUriType(dep._2) == H.CONNECTION_TYPE, "Deployment uri is not port or connection")
        cst.connectionTable(dep._2).ports.filter(_.startsWith(H.PORT_IN_BIND_TYPE)).head
      }

      val fromNodeUri = Resource.getParentUri(fromPortUri)
      val toNodeUri = Resource.getParentUri(toPortUri)


      if (fromNodeUri.isDefined && toNodeUri.isDefined) {
        val fromNode = FlowNode.getNode(fromNodeUri.get)
        val toNode = FlowNode.getNode(toNodeUri.get)
        if (fromNode.isDefined && toNode.isDefined) {
          val edge1 = result.addEdge(fromNode.get, toNode.get)

          result.addPortEdge(fromPortUri, edge1)
          result.addPortEdge(toPortUri, edge1)
          result.addEdgePortRelation(edge1, fromPortUri, toPortUri)
        }
      }
    }
    result
  }

  def apply(m: Model, st: SymbolTable, includeBindingEdges: Boolean): FlowGraph[FlowNode, FlowEdge[FlowNode]] = {
    FlowNode.newPool()
    edgesRemoved = imapEmpty[ResourceUri, IMap[FlowNode.Edge, (ResourceUri, ResourceUri)]]
    val systemST = st.componentTable(st.system)
    val result = buildGraph(systemST, st)
    if (includeBindingEdges) {
      //TODO: safly type casting here as it is inside FlowGraph, but have to rework to avoid this casting
      FlowNode.getGraphs.foreach(it => addBindings(it.asInstanceOf[FlowGraph[FlowNode, FlowNode.Edge] with FlowGraphUpdate[FlowNode, FlowNode.Edge]]))
    } else {
      FlowNode.getGraphs.foreach(it => removeBindings(it.asInstanceOf[FlowGraph[FlowNode, FlowNode.Edge] with FlowGraphUpdate[FlowNode, FlowNode.Edge]]))
    }
    FlowNode.getGraph(st.system).get
  }

  private def toFptcNode(node: org.sireum.awas.ast.Node): Option[FlowNode] = {
    val res = getResource(node)
    if (res.isDefined && (H.isComponent(res.get) || H.isConnection(res.get))) {
      FlowNode.getNode(res.get.toUri)
    } else {
      None
    }
  }

  def removeBindings(graph: FlowGraph[FlowNode, FlowNode.Edge] with FlowGraphUpdate[FlowNode, FlowNode.Edge]): Unit = {

    if (!edgesRemoved.contains(graph.getUri)) {
      val ports = graph.nodes.flatMap(_.ports)

      val bindPorts = ports.filter(
        it =>
          H.getUriType(it).endsWith(H.BIND_PORT_TYPE) ||
            it.split(H.ID_SEPARATOR).last == Aadl2Awas.PROCESSOR_IN ||
            it.split(H.ID_SEPARATOR).last == Aadl2Awas.PROCESSOR_OUT
      )

      val bindEdges = bindPorts.flatMap(graph.getEdgeForPort)
      val edgePorts = bindEdges.map(it => it -> (it.sourcePort.get, it.targetPort.get)).toMap

      edgesRemoved = edgesRemoved +
        (graph.getUri -> (edgesRemoved.getOrElse(graph.getUri, imapEmpty[FlowNode.Edge, (ResourceUri, ResourceUri)]) ++ edgePorts))
      bindEdges.foreach(it => graph.removeEdge(it.source, it.target))
      graph.reComputeCycles()
    }

  }

  def addBindings(graph: FlowGraph[FlowNode, FlowNode.Edge] with FlowGraphUpdate[FlowNode, FlowNode.Edge]): Unit = {

    if (edgesRemoved.keySet.contains(graph.getUri)) {
      val edgePorts = edgesRemoved(graph.getUri)
      edgePorts.keySet.foreach { e =>
        graph.addEdge(e.source, e.target, e)
        graph.addEdgePortRelation(e, edgePorts(e)._1, edgePorts(e)._2)
        graph.addPortEdge(edgePorts(e)._1, e)
        graph.addPortEdge(edgePorts(e)._2, e)
      }
      edgesRemoved = edgesRemoved - graph.getUri
      graph.reComputeCycles()
    }
  }

}
