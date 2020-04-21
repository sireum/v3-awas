/*
 *
 * Copyright (c) 2020, Hariharan Thiagarajan, Kansas State University
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

package org.sireum.awas.flow

import org.sireum.awas.ast.{All, And, ConditionTuple, EventRef, Or, OrLess, OrMore, PrimaryCondition, Tuple}
import org.sireum.awas.reachability.PortReachability
import org.sireum.awas.symbol.{ComponentTableUpdate, Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

object FlowInference {

  val COMPUTED_FLOW_NAME = "computed-flow-"

  val H = SymbolTableHelper

  private var flowid = 0

  //This is a symbol table mutating analysis,
  // must be executed after the flow graph computation
  def apply(): Unit = {
    val stOpt = SymbolTable.getTable
    if (stOpt.isDefined) {
      val st = stOpt.get
      val pr = PortReachability(st)

      H.getComponentsLevelOrder(st).reverse.foreach { comp =>
        inferComponentFlows(comp, pr, st)
      }
    }
  }

  def getLeafComponents(): ISet[ResourceUri] = {
    val stOpt = SymbolTable.getTable
    if (stOpt.isDefined) {
      stOpt.get.components.filter {curi =>
        stOpt.get.componentTable(curi).subComponents.isEmpty
      }.toSet
    } else {
      isetEmpty
    }
  }

  private def intraComponentFlowInfer(compUri: ResourceUri,
                                      st: SymbolTable)
  : ISet[ResourceUri] = {
    val ct = st.componentTable(compUri)
    if(ct.behaviors.nonEmpty) {
      isetEmpty
    } else {
      isetEmpty
    }

  }

  private def getTuples(condition: ConditionTuple): ISet[Tuple] = {
    condition match {
      case and: And => getTuples(and.lhs) ++ getTuples(and.rhs)
      case or: Or => getTuples(or.lhs) ++ getTuples(or.rhs)
      case orMore: OrMore => orMore.conds.flatMap(getTuples).toSet
      case orLess: OrLess => orLess.conds.flatMap(getTuples).toSet
      case all: All => all.conds.flatMap(getTuples).toSet
      case primary: PrimaryCondition => {
        primary match {
          case ef: EventRef => isetEmpty[Tuple]
          case tup: Tuple => isetEmpty[Tuple] + tup
        }
      }
    }
  }

  private def inferComponentFlows(
    componentUri: ResourceUri,
    portReach: PortReachability[FlowNode],
    st: SymbolTable
  ): Unit = {
    if (Resource.hasResourceInfo(st.componentTable(componentUri).componentDecl)) {
      val compTable = st.componentTable(componentUri)
      val compTableUpdate = st.componentTable(componentUri).asInstanceOf[ComponentTableUpdate]
      val compResource = Resource.getResource(compTable.componentDecl).get
      if (compTable.subComponents.nonEmpty) {} else {
        val inports = compTable.ports.filter(H.isInPort)
        val outPorts = compTable.ports.filter(H.isOutPort)

        if (compTable.flows.isEmpty) {} else {
          val inWithOutFlows = inports.filter(it => compTable.getFlowsFromPort(it).isEmpty)
          inWithOutFlows.foreach { ip =>
            outPorts.foreach { op =>
              val fr = Resource(H.VIRTUAL_FLOW_TYPE, compResource, buildNewFlowId(), isDef = Some(true))
              val ftd = compTableUpdate.addFlow(fr.toUri, Some(ip), Some(op), isetEmpty, isetEmpty)

            }
          }
        }
      }
    }
  }

  private def buildNewFlowId(): String = {
    flowid = flowid + 1
    COMPUTED_FLOW_NAME + flowid
  }

}
