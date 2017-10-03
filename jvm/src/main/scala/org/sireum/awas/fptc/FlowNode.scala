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


import org.sireum.awas.ast.Node
import org.sireum.awas.collector.{FlowErrorNextCollector, FlowCollector}
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

trait FlowNode extends BasicNode {
  def getFptcPropagation(port : ResourceUri) : Set[ResourceUri]

  def flowForward(port: ResourceUri): FlowCollector

  def flowBackward(port: ResourceUri): FlowCollector

  def errorForward(tuple: (ResourceUri, ResourceUri)): FlowErrorNextCollector

  def errorBackward(tuple: (ResourceUri, ResourceUri)): FlowErrorNextCollector

  def isFlowDefined: Boolean

  def getFlows: IMap[ResourceUri, Node]
}

trait FptcNodeUpdate {
  def addFptcPropagation(port: ResourceUri, error_type: ResourceUri)
}

object FlowNode {
  type Edge = FlowEdge[FlowNode]
  private var nodepool = imapEmpty[ResourceUri, FlowNode]

  def createNode(uri: ResourceUri, st: SymbolTable): FlowNode = {
    implicit val reporter: AccumulatingTagReporter = new ConsoleTagReporter
    if (nodepool.contains(uri)) {
      nodepool(uri)
    } else {
      val node = new FlowNodeImpl(uri, st)
      nodepool += (uri -> node)
      node
    }
  }

  def getNode(uri: ResourceUri): Option[FlowNode] = nodepool.get(uri)

  def newPool(): Unit = {
    nodepool = imapEmpty[ResourceUri, FlowNode]
  }
}

