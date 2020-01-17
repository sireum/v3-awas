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

package org.sireum.awas.fptc

import org.sireum.awas.Stpa.{StpaEdge, StpaNode}
import org.sireum.awas.ast.{Id, RecordInit, StringInit}
import org.sireum.util._
import org.sireum.awas.collector.Collector
import org.sireum.awas.graph.AwasGraph
import org.sireum.awas.reachability.{ErrorReachability, PortReachability}
import org.sireum.awas.report.StpaProperty
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.AwasUtil.ResourceUri

trait StpaControlStructure extends AwasGraph[StpaNode, StpaEdge] {
  def controlStructureId : String
}

class StpaControlStructureImpl(csId : String) extends StpaControlStructure {
  override def controlStructureId: String = csId

  override def nodes: Iterable[StpaNode] = ???

  override def numOfNodes: Natural = ???

  override def edges: Iterable[StpaEdge] = ???

  override def numOfEdges: Natural = ???

  override def hasNode(n: StpaNode): Boolean = ???

  override def getNode(n: StpaNode): StpaNode = ???

  override def hasEdge(n1: StpaNode, n2: StpaNode): Boolean = ???

  override def getEdge(n1: StpaNode, n2: StpaNode): CSet[StpaEdge] = ???

  override def getEdges(n: StpaNode): CSet[StpaEdge] = ???

  override def getIncomingEdges(node: StpaNode): CSet[StpaEdge] = ???

  override def getOutgoingEdges(node: StpaNode): CSet[StpaEdge] = ???

  override def getSuccessorNodes(node: StpaNode): CSet[StpaNode] = ???

  override def getPredecessorNodes(node: StpaNode): CSet[StpaNode] = ???

  override def getSCC: Seq[Set[StpaNode]] = ???

  /**
    * Find all simple cycles of a directed graph using the Schwarcfiter and Lauer's algorithm.
    *
    * @return set of cycles
    */
  override def getCycles: Seq[Seq[StpaNode]] = ???
}

object StpaControlStructure {
  type Edge = FlowEdge[FlowNode]


  def apply(
    symbolTable: SymbolTable,
    systemGraph: FlowGraph[FlowNode, Edge],
    collector: Collector,
    controlStructId: String
  ): FlowGraph[FlowNode, Edge] = {
    val graphUri = STPAHelper.STPA_RESOURCE_URI+symbolTable.system
    val result = new FlowGraphImpl(graphUri, symbolTable)

    var uriRole = imapEmpty[ResourceUri, String]
    var uriNode = imapEmpty[ResourceUri, FlowNode]

    symbolTable.components.foreach { compUri =>
      val compRoles = symbolTable
        .componentTable(compUri)
        .componentDecl
        .properties
        .find(p => p.id.value == StpaProperty.COMPONENT_ROLES)

      compRoles.foreach { cr =>
        if (cr.value.isDefined) {
          val csid = cr.value
            .asInstanceOf[RecordInit]
            .fields
            .get(Id(StpaProperty.COMPONENT_ROLES_CL))
            .flatMap(
              _.asInstanceOf[RecordInit].fields
                .get(Id(StpaProperty.CONTROL_STRUCT_ID))
                .map(_.asInstanceOf[StringInit].value)
            )
          val compRole = cr.value
            .asInstanceOf[RecordInit]
            .fields
            .get(Id(StpaProperty.COMPONENT_ROLES_Role))
            .map(_.asInstanceOf[StringInit].value)
          if (csid.isDefined && csid.get == controlStructId) {
            if (compRole.isDefined) {
              uriRole = uriRole + (compUri -> compRole.get)
            }
          }
        }
      }
    }

    uriRole.keySet.foreach { u =>
      if (FlowNode.getNode(u).isDefined) {
        uriNode = uriNode + (u -> FlowNode.getNode(u).get)
      }
    }

    uriNode.foreach(un => if (collector.getNodes.contains(un._2)) result.addNode(un._2))

    var edges = isetEmpty[Edge]

    def getEdges(port: ResourceUri): ISet[Edge] = {
      var res = isetEmpty[Edge]
      val outPorts = uriNode.flatMap(_._2.outPorts)

      var worklist = ilistEmpty[IList[ResourceUri]]
      worklist = worklist :+ (ilistEmpty[ResourceUri] :+ port)

      while (worklist.nonEmpty) {
        val curPath = worklist.head
        val curPort = curPath.last
        val nextPorts = ErrorReachability(symbolTable).getPredecessor(curPort)
        val found = nextPorts.intersect(outPorts.toSet)

        if(found.nonEmpty) {
          found.map{op =>
            FlowEdgeFactory
          }
        }
      }
      res
    }

    val inPorts = uriNode.flatMap(_._2.inPorts)

    uriNode.foreach { n =>
      val preNode = ErrorReachability(symbolTable).getPredecessor(n._2).intersect(collector.getNodes)
      if (preNode.nonEmpty) {}

    }
    result
  }
}
