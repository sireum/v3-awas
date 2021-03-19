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

package org.sireum.awas.flow


import org.sireum.awas.collector.{Collector, FlowCollector, FlowErrorNextCollector}
import org.sireum.awas.symbol.{FlowTableData, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

trait FlowNode extends BasicNode[FlowGraph[FlowNode, FlowNode.Edge]] {
  def getFptcPropagation(port : ResourceUri) : Set[ResourceUri]

  def flowForward(port: ResourceUri): FlowCollector

  def flowBackward(port: ResourceUri): FlowCollector

  def errorForward(tuple: (ResourceUri, ResourceUri)): FlowErrorNextCollector

  def errorBackward(tuple: (ResourceUri, ResourceUri)): FlowErrorNextCollector

  def isFlowDefined: Boolean

  // the following methods unions component and connection
  def getFlows: IMap[ResourceUri, FlowTableData]

  def getFlowReach(flow: ResourceUri): Option[Collector]

  def getPortsFromFlows(flowUri: ResourceUri): Set[ResourceUri]

  def getFlowsFromPort(portUri: ResourceUri): Set[ResourceUri]

  def getSubGraph: Option[FlowGraph[FlowNode, FlowNode.Edge]]

  def getOwner: FlowGraph[FlowNode, FlowNode.Edge]

}

trait FptcNodeUpdate {
  def addFptcPropagation(port: ResourceUri, error_type: ResourceUri): Unit

  def updateFlowCache(flow: ResourceUri, result: Collector): Unit
}

object FlowNode {
  val H = SymbolTableHelper
  type Edge = FlowEdge[FlowNode]
  private var nodepool = imapEmpty[ResourceUri, FlowNode]
  private var graphs = imapEmpty[ResourceUri, FlowGraph[FlowNode, Edge]]


  def createNode(uri: ResourceUri,
                 st: SymbolTable,
                 graph: FlowGraph[FlowNode, FlowEdge[FlowNode]])
  : FlowNode = {
    implicit val reporter: AccumulatingTagReporter = new ConsoleTagReporter
    if (nodepool.contains(uri)) {
      nodepool(uri)
    } else {
      val node = new FlowNodeImpl(uri, st, graph)
      nodepool += (uri -> node)
      graphs += (graph.getUri -> graph)
      node
    }
  }

  def getGraphs: ISet[FlowGraph[FlowNode, Edge]] = graphs.values.toSet

  def getGraph(uri: ResourceUri): Option[FlowGraph[FlowNode, Edge]] = graphs.get(uri)

  def getNode(uri: ResourceUri): Option[FlowNode] = {
    //    if(H.isFlow(uri) || H.isPort(uri)) {
    //      Resource.getParentUri(uri).map(nodepool)
    //    }
    nodepool.get(uri)
  }

  def newPool(): Unit = {
    nodepool = imapEmpty[ResourceUri, FlowNode]
    graphs = imapEmpty[ResourceUri, FlowGraph[FlowNode, Edge]]
  }
}

