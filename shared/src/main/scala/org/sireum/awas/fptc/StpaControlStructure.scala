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

import org.sireum.awas.ast.{Id, RecordInit, StringInit}
import org.sireum.util._
import org.sireum.awas.collector.Collector
import org.sireum.awas.reachability.ErrorReachability
import org.sireum.awas.report.StpaProperty
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.AwasUtil.ResourceUri

class StpaControlStructure {}

object StpaControlStructure {
  type Edge = FlowEdge[FlowNode]

  def apply(
    symbolTable: SymbolTable,
    systemGraph: FlowGraph[FlowNode, Edge],
    collector: Collector,
    controlStructId: String
  ): FlowGraph[FlowNode, Edge] = {
    val result = new FlowGraphImpl(symbolTable.system, symbolTable)

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

    def getEdges(node: FlowNode): ISet[Edge] = {
      var res = isetEmpty[Edge]

      var worklist = ilistEmpty[IList[FlowNode]]
      worklist = worklist :+ (ilistEmpty :+ node)

      while (worklist.nonEmpty) {
        val curPath = worklist.head

        val curr = curPath.head

      }
      res
    }

    uriNode.foreach { n =>
      val preNode = ErrorReachability(symbolTable).getPredecessor(n._2).intersect(collector.getNodes)
      if (preNode.nonEmpty) {}

    }
    result
  }
}
