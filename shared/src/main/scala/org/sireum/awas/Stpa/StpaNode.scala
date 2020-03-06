/*
 *
 * Copyright (c) 2019, Robby, Kansas State University
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sireum.awas.Stpa

import org.sireum.awas.flow.NodeType.NodeType
import org.sireum.awas.flow.{BasicNode, BasicNodeImpl, FlowNode, StpaControlStructure}
import org.sireum.awas.graph.AwasEdge
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

trait StpaNode extends BasicNode[StpaControlStructure] {
  def getFlowNode: FlowNode

  def getRole: String
}

class StpaBasicNode(uri: ResourceUri, role: String, st: SymbolTable, owner: StpaControlStructure)
    extends BasicNodeImpl[StpaControlStructure](uri, st, owner) with StpaNode {

  self: StpaNode =>

  override def getResourceType: NodeType = super.getResourceType

  override def getUri: ResourceUri = StpaNode.STPA_NODE + StpaNode.SEPERATOR + uri

  override def ports: Iterable[ResourceUri] = super.ports

  override def inPorts: Iterable[ResourceUri] = super.inPorts

  override def outPorts: Iterable[ResourceUri] = super.outPorts

  override def getPropagation(port: ResourceUri): Set[ResourceUri] = super.getPropagation(port)

  override def getOwner: StpaControlStructure = owner

  override def isComponent: Boolean = super.isComponent

  override def getFlowNode: FlowNode = FlowNode.getNode(uri).get

  override def getRole: String = role
}

object StpaNode {

  def apply(flowNode: FlowNode, role: String, owner: StpaControlStructure): StpaNode = {
    createNode(flowNode, role, owner)
  }

  private var nodePool = imapEmpty[ResourceUri, StpaNode]

  val STPA_NODE = "StpaNode"

  val SEPERATOR = "--"

  def createNode(flowNode: FlowNode, role: String, owner: StpaControlStructure): StpaNode = {
    if (nodePool.get(STPA_NODE + SEPERATOR + flowNode.getUri).isDefined) {
      nodePool(STPA_NODE + SEPERATOR + flowNode.getUri)
    } else {
      val node: StpaNode = StpaNode(flowNode, role, owner)
      nodePool = nodePool + (node.getUri -> node)
      node
    }
  }

}

trait StpaEdge extends AwasEdge[StpaNode] {
  def sourcePort: ResourceUri
  def targetPort: ResourceUri
}

class StpaEdgeFactory(
  owner: StpaControlStructure,
  sourceNode: StpaNode,
  targetNode: StpaNode,
  sourcePortUri: ResourceUri,
  targetPortUri: ResourceUri
) extends StpaEdge {
  StpaEdge =>

  override def source: StpaNode = sourceNode

  override def target: StpaNode = targetNode

  override def sourcePort: ResourceUri = sourcePortUri

  override def targetPort: ResourceUri = targetPortUri
}
