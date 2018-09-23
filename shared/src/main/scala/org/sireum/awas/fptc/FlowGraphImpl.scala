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

import org.sireum.awas.collector
import org.sireum.awas.collector.CollectorErrorHelper._
import org.sireum.awas.collector.{FlowCollector, FlowErrorNextCollector}
import org.sireum.awas.fptc.FlowNode.Edge
import org.sireum.awas.graph._
import org.sireum.awas.symbol.{SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._
import org.sireum.{$Slang, ISZ, ST, $internal}

class FlowGraphImpl(uri: ResourceUri, st: SymbolTable)
  extends FlowGraph[FlowNode, FlowEdge[FlowNode]] with FlowGraphUpdate[FlowNode, FlowEdge[FlowNode]] {
  self: FlowGraph[FlowNode, FlowEdge[FlowNode]] =>

  type EdgeT = FlowEdge[FlowNode]

  val ef = FlowEdgeFactory
  val useSlangGraph = true

  val superClass: AwasGraph[FlowNode, EdgeT] with AwasGraphUpdate[FlowNode, EdgeT] = //if (useSlangGraph) {
    new SlangGraphImpl[FlowNode, FlowEdge[FlowNode]]()
  //  } else {
  //    new JGraphTGraphImpl[FlowNode, FlowEdge[FlowNode]](ef)
  //  }
  val H = SymbolTableHelper
  private var nodeToDot = (n: FlowNode) => {
    st""" "${n.getUri}" [label="${getNodeDot(n)}", ${
      if (n.getResourceType == NodeType.PORT) {
        "rank=\"" + (if (H.isInPort(n.getUri)) "source" else "sink") + "\","
      } else ""
    } shape="record"] """
  }
  private var edgeToDot = (e: EdgeT) => {st""" ${getEdgeDot(e)} """}

  //  val graph: AwasGraph[FlowNode] = superClass.graph
  private var graphAttributes = ivectorEmpty[ST] :+ st"""rankdir=TB"""


  private var portEdgeMap: IMap[ResourceUri, ISet[EdgeT]] = imapEmpty[ResourceUri, ISet[EdgeT]]
  private var portNodeMap: IMap[ResourceUri, FlowNode] = imapEmpty[ResourceUri, FlowNode]
  private var edgePortsMap: IMap[EdgeT, (ResourceUri, ResourceUri)] = imapEmpty[EdgeT, (ResourceUri, ResourceUri)]

  //  def getAttributes(component: EdgeT): util.Map[String, Attribute] = {
  //    import scala.collection.JavaConverters._
  //    val res = mlinkedMapEmpty[String, Attribute]
  //    if (component.source.isComponent) {
  //      res("tailport") = new DefaultAttribute(component.sourcePort.get.split(H.ID_SEPARATOR).last, AttributeType.STRING)
  //    }
  //    if (component.target.isComponent) {
  //      res("headport") = new DefaultAttribute(component.targetPort.get.split(H.ID_SEPARATOR).last, AttributeType.STRING)
  //    }
  //    res.asJava
  //  }

  def addPortEdge(port: ResourceUri, edge: EdgeT): Unit = {
    portEdgeMap += port -> (portEdgeMap.getOrElse(port, isetEmpty[EdgeT]) + edge)
  }

  def addEdge(from: FlowNode, to: FlowNode): EdgeT = {
    val edge = FlowEdgeFactory.createEdge(self, from, to)
    addEdge(from, to, edge)
    edge
  }

  //  override def toDot: String = {
  //    val de = new DOTExporter[FlowNode, Edge](nIdProvider, nlabelProvide, null, this.attProvider, eAttrProvider)
  //    val sw = new StringWriter()
  //    de.exportGraph(graph, sw)
  //    sw.toString
  //  }

  // override def addEdge(from: FlowNode, to: FlowNode, data: Edge): Edge = ???
  // override def setEdgeAttrProvider(edgeAtt: ComponentAttributeProvider[Any]): Unit = ???
  override def addEdge(from: FlowNode, to: FlowNode, data: FlowEdge[FlowNode]):
  FlowEdge[FlowNode] = {
    val edge = ef.createEdge(this, from, to)
    superClass.addEdge(from, to, edge)
  }

  //  override def addEdge(from: FlowNode, to: FlowNode): Edge = {
  //    val edge = new FlowEdgeFactory().createEdge(self, from, to).asInstanceOf[Edge]
  //    superClass.addEdge(from, to, edge)
  //    edge
  //  }

  override def addNode(n: FlowNode): FlowNode = {
    n.ports.foreach { p =>
      portNodeMap += (p -> n)
    }
    superClass.addNode(n)
  }

  override def addEdgePortRelation(edge: FlowEdge[FlowNode], source: ResourceUri, target: ResourceUri): Unit = {
    assert(!edgePortsMap.contains(edge))
    edgePortsMap += (edge -> (source, target))
  }

  override def getNode(n: FlowNode): FlowNode = n

  override def getEdges(sourcePort: ResourceUri, targetPort: ResourceUri): ISet[EdgeT] = {
    var res = isetEmpty[EdgeT]
    edgePortsMap.foreach { v =>
      if (v._2 == (sourcePort, targetPort)) {
        res = res + v._1
      }
    }
    res
  }

  override def getSuccessorError(tuple: (ResourceUri, ResourceUri))
  : FlowErrorNextCollector = {
    val node = getNode(tuple._1)
    if (node.isDefined) {
      if (H.isInPort(tuple._1) && node.get.getResourceType != NodeType.PORT) {
        node.get.errorForward(tuple)
      } else {
        var result = ilistEmpty[(ResourceUri, ResourceUri)]
        var edges = isetEmpty[Edge]
        getEdgeForPort(tuple._1).foreach { e =>
          if (e.targetPort.isDefined) {
            edges += e
            val targetPropagations = e.target.getPropagation(e.targetPort.get)
            val errrorTypes = if (targetPropagations.contains(tuple._2)) {
              isetEmpty + tuple._2
            } else {
              st.typeAlias(tuple._2)
            }
            errrorTypes.foreach(t => {
              if (targetPropagations.contains(t)) {
                result = result :+ (e.targetPort.get, t)
              }
            })
          }
        }
        collector.FlowErrorNextCollector(result.toSet,
          edges, isetEmpty[ResourceUri], isetEmpty[Tag], isetEmpty + this)
      }
    } else {
      FlowErrorNextCollector(isetEmpty, isetEmpty, isetEmpty,
        isetEmpty + errorMessageGen(MISSING_NODE, tuple._1, ReachAnalysisStage.Port),
        isetEmpty + this)
    }
  }

  override def getPredecessorError(tuple: (ResourceUri, ResourceUri))
  : FlowErrorNextCollector = {
    val node = getNode(tuple._1)
    if (node.isDefined) {
      if (H.isOutPort(tuple._1) && node.get.getResourceType != NodeType.PORT) {
        node.get.errorBackward(tuple)
      } else {
        var result = ilistEmpty[(ResourceUri, ResourceUri)]
        var edges = isetEmpty[Edge]
        getEdgeForPort(tuple._1).foreach { e =>
          if (e.sourcePort.isDefined) {
            edges += e
            val sourcePropagation = e.source.getPropagation(e.sourcePort.get)
            val errorTypes = if (sourcePropagation.contains(tuple._2)) {
              isetEmpty + tuple._2
            } else {
              st.typeAlias(tuple._2)
            }
            errorTypes.foreach(t =>
              if (sourcePropagation.contains(t)) {
                result = result :+ (e.sourcePort.get, t)
              }
            )
          }
        }
        collector.FlowErrorNextCollector(result.toSet,
          edges, isetEmpty[ResourceUri], isetEmpty[Tag], isetEmpty + this)
      }
    } else {
      FlowErrorNextCollector(isetEmpty, isetEmpty, isetEmpty,
        isetEmpty + errorMessageGen(MISSING_NODE, tuple._1, ReachAnalysisStage.Port),
        isetEmpty + this)
    }
  }

  override def getSuccessorPorts(port: ResourceUri): FlowCollector = {
    val node = getNode(port)
    if (node.isDefined) {
      if (port.startsWith(H.PORT_IN_TYPE) &&
        (FlowNode.getNode(port).isEmpty ||
          !nodes.toSet.contains(FlowNode.getNode(port).get))) {
        node.get.flowForward(port)
      } else {
        //outport: use edge to get the successor
        var res = isetEmpty[ResourceUri]
        var edges = isetEmpty[EdgeT]

        getEdgeForPort(port).foreach { e =>
          e.targetPort match {
            case Some(x) => {
              res = res + x
              edges = edges + e
            }
            case _ =>
          }
        }
        collector.FlowCollector(isetEmpty + this, res, edges, isetEmpty[ResourceUri], isetEmpty[Tag])
      }
    } else {
      collector.FlowCollector(isetEmpty + this,
        isetEmpty[ResourceUri],
        isetEmpty[EdgeT],
        isetEmpty[ResourceUri],
        isetEmpty[Tag] + errorMessageGen(MISSING_NODE, port, ReachAnalysisStage.Port)
      )
    }
  }

  def getNode(uri: ResourceUri): Option[FlowNode] = {
    if (uri.startsWith(H.COMPONENT_TYPE) ||
      uri.startsWith(H.CONNECTION_TYPE) ||
      (FlowNode.getNode(uri).isDefined &&
        nodes.toSet.contains(FlowNode.getNode(uri).get))) {
      FlowNode.getNode(uri)
    } else {
      portNodeMap.get(uri)
    }
  }

  override def getEdgeForPort(port: ResourceUri): Set[EdgeT] =
    portEdgeMap.getOrElse(port, isetEmpty[EdgeT])

  override def getPredecessorPorts(port: ResourceUri): FlowCollector = {
    val node = getNode(port)
    if (node.isDefined) {

      if (port.startsWith(H.PORT_IN_TYPE) ||
        (FlowNode.getNode(port).isDefined &&
          nodes.toSet.contains(FlowNode.getNode(port).get))) {
        var res = isetEmpty[ResourceUri]
        var edges = isetEmpty[EdgeT]
        getEdgeForPort(port).foreach { e =>
          e.sourcePort match {
            case Some(x) => {
              res = res + x
              edges = edges + e
            }
            case _ =>
          }
        }
        collector.FlowCollector(isetEmpty + this, res, edges, isetEmpty[ResourceUri], isetEmpty[Tag])
      } else {
        //outport: use edge to get the successor
        node.get.flowBackward(port)
      }
    } else {
      collector.FlowCollector(isetEmpty + this,
        isetEmpty[ResourceUri],
        isetEmpty[EdgeT],
        isetEmpty[ResourceUri],
        isetEmpty[Tag] + errorMessageGen(MISSING_NODE, port, ReachAnalysisStage.Port)
      )
    }
  }

  override def getPortsFromEdge(edge: EdgeT): Option[(ResourceUri, ResourceUri)] = {
    edgePortsMap.get(edge)
  }

  //  override def getAllPathsNodes(source: FlowNode, sink: FlowNode): Set[Seq[FlowNode]] = {
  //    import scala.collection.JavaConverters._
  //    val allGraphPath = new AllDirectedPaths[FlowNode, Edge](graph)
  //    val pathNodes = allGraphPath.getAllPaths(source, sink, true, null).asScala.map(_.getVertexList.asScala.toSeq)
  //    pathNodes.toSet
  //  }
  //
  //  override def getAllPathsEdges(source: FlowNode, sink: FlowNode): Set[Seq[Edge]] = {
  //    import scala.collection.JavaConverters._
  //    val allGraphPath = new AllDirectedPaths[FlowNode, Edge](graph)
  //    val pathNodes = allGraphPath.getAllPaths(source, sink, true, null).asScala.map(_.getEdgeList.asScala.toSeq)
  //    pathNodes.toSet
  //  }

  override def numOfNodes: Natural = superClass.numOfNodes

  override def edges: Iterable[EdgeT] = superClass.edges

  override def numOfEdges: Natural = superClass.numOfEdges

  override def hasNode(n: FlowNode): Boolean = superClass.hasNode(n)

  override def hasEdge(n1: FlowNode, n2: FlowNode): Boolean = superClass.hasEdge(n1, n2)

  override def getEdge(n1: FlowNode, n2: FlowNode): CSet[FlowEdge[FlowNode]] =
    superClass.getEdge(n1, n2)

  override def getEdges(n: FlowNode): CSet[FlowEdge[FlowNode]] =
    superClass.getEdges(n)

  override def getIncomingEdges(node: FlowNode): CSet[FlowEdge[FlowNode]] =
    superClass.getIncomingEdges(node)

  override def getOutgoingEdges(node: FlowNode): CSet[FlowEdge[FlowNode]] =
    superClass.getOutgoingEdges(node)

  override def getSuccessorNodes(node: FlowNode): CSet[FlowNode] = {
    //    node.getResourceType match {
    //      case NodeType.PORT => {
    //        if(H.isOutPort(node.getUri) && FlowNode.getNode(uri).isDefined) {
    //          val parentGraph = FlowNode.getNode(uri).get.getOwner
    //            parentGraph.getEdgeForPort(node.getUri).
    //              flatMap(_.targetPort).flatMap(parentGraph.getNode)
    //        } else {
    //          superClass.getSuccessorNodes(node)
    //        }
    //      }
    //      case _=> superClass.getSuccessorNodes(node)
    //    }
    superClass.getSuccessorNodes(node)
  }

  override def getPredecessorNodes(node: FlowNode): CSet[FlowNode] = {
    //    node.getResourceType match {
    //      case NodeType.PORT => {
    //        if(H.isInPort(node.getUri) && FlowNode.getNode(uri).isDefined) {
    //          val parentGraph = FlowNode.getNode(uri).get.getOwner
    //          parentGraph.getEdgeForPort(node.getUri).
    //            flatMap(_.sourcePort).flatMap(parentGraph.getNode)
    //        } else {
    //          superClass.getPredecessorNodes(node)
    //        }
    //      }
    //      case _ => superClass.getPredecessorNodes(node)
    //    }
    superClass.getPredecessorNodes(node)
  }

  def getSCC: Seq[Set[FlowNode]] = {
    superClass.getSCC
  }

  def getCycles: Seq[Seq[FlowNode]] = {
    superClass.getCycles
  }

  override def toDot: String = {
    val nST: ISZ[ST] = ISZ((for (e <- this.nodes) yield st"""${nodeToDot(e)}""").toSeq: _*)
    val eST: ISZ[ST] = ISZ[ST]((for (e <- this.edges.toSeq) yield edgeToDot(e)): _*)
    val r =
      st"""digraph "${this.getUri}" {
          |
      |  ${(graphAttributes, "\n")}
          |
      |  ${(nST, "\n")}
          |
      |  ${(eST, "\n")}
          |
      |}"""
    return r.render.value
  }

  //  override def getAllPaths(source: FlowNode, sink: FlowNode): Set[Set[FlowNode]] = superClass.getAllPaths(source, sink)

  //  override def setNodeAttProvider(nodeAtt: ComponentAttributeProvider[FlowNode]): Unit = {
  //    this.attProvider = nodeAtt
  //  }
  //
  //  override def setNodeIdProvider(nodeId: StringComponentNameProvider[FlowNode]): Unit = {
  //    this.nIdProvider = nodeId
  //  }
  //
  //  override def setNodeLabelProvider(nodeLabel: StringComponentNameProvider[FlowNode]): Unit = {
  //    this.nlabelProvide = nodeLabel
  //  }

  override def getUri: ResourceUri = uri

  override def getInPortNodes: ISet[FlowNode] = {
    nodes
      .filter(
        n =>
          (n.getResourceType == NodeType.PORT) &&
            H.isInPort(n.getUri)
      )
      .toSet
  }

  override def nodes: Iterable[FlowNode] = {
    val nds = superClass.nodes
    nds
  }

  override def getOutPortNodes: ISet[FlowNode] = {
    nodes
      .filter(
        n =>
          (n.getResourceType == NodeType.PORT) &&
            H.isOutPort(n.getUri)
      )
      .toSet
  }

  override def setNodeToST(f: FlowNode => ST): Unit = nodeToDot = f

  override def setEdgeToST(f: FlowEdge[FlowNode] => ST): Unit = edgeToDot = f

  override def setGraphAttributes(attributes: ISeq[ST]): Unit = graphAttributes = attributes.toVector

  private def getNodeDot(vertex: FlowNode): String = {
    var result = ""
    vertex.getResourceType match {
      case NodeType.COMPONENT => result += compOrConnToDotNode(vertex)
      case NodeType.CONNECTION => result += compOrConnToDotNode(vertex)
      case NodeType.PORT => {
        result += (if (H.isInPort(vertex.getUri)) "{In Port|" else "{Out Port|")
        result += "<" + vertex.getUri.split(H.ID_SEPARATOR).last + ">" + vertex.getUri.split(H.ID_SEPARATOR).last + "}"
      }
    }
    result
  }

  private def getEdgeDot(edge: EdgeT): String = {
    assert(edge.sourcePort.isDefined && edge.targetPort.isDefined)
    var result = ""
    result += "\"" + edge.source.getUri + "\"" + " -> " + "\"" + edge.target.getUri + "\""
    result += " [" + "headport=" + edge.targetPort.get.split(H.ID_SEPARATOR).last +
      ", tailport=" + edge.sourcePort.get.split(H.ID_SEPARATOR).last + "]"
    result
  }

  private def compOrConnToDotNode(vertex: FlowNode): String = {
    var result = ""
    result += "{In Port|" + vertex.inPorts.map { ip =>
      val pname = ip.split(H.ID_SEPARATOR).last
      "<" + pname + ">" + pname
    }.mkString("|") + "} |"
    result += (if (vertex.getResourceType == NodeType.COMPONENT) SymbolTableHelper.COMPONENT_TYPE
    else SymbolTableHelper.CONNECTION_TYPE) + "\\n" + vertex.getUri.split(H.ID_SEPARATOR).last + "|"
    result += "{Out Port|" + vertex.outPorts.map { ip =>
      val pname = ip.split(H.ID_SEPARATOR).last
      "<" + pname + ">" + pname
    }.mkString("|") + "} "
    result
  }
}
