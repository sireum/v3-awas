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

import org.sireum.awas.ast.{All, And, ConditionTuple, EventRef, Fault, FaultSet, Or, OrLess, OrMore, PrimaryCondition, Tuple}
import org.sireum.awas.flow.FlowInference.H
import org.sireum.awas.reachability.PortReachability
import org.sireum.awas.symbol.{ComponentTableUpdate, Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util._

object FlowInference {

  val H = SymbolTableHelper



  //This is a symbol table mutating analysis,
  // must be executed after the flow graph computation
  def apply(symbolTable: SymbolTable): IMap[ResourceUri, ISet[ResourceUri]] = {
    new FlowInferenceImpl().inferFlows(symbolTable)
  }

}

class FlowInferenceImpl {

  private var flowid = 0

  val COMPUTED_FLOW_NAME = "computed-flow-"

  val H = SymbolTableHelper

  def inferFlows(symbolTable: SymbolTable): IMap[ResourceUri, ISet[ResourceUri]] = {
    val pr = PortReachability(symbolTable)

    H.getComponentsLevelOrder(symbolTable).reverse.map { comp =>
      (comp, inferComponentFlows(comp, pr, symbolTable))
    }.toMap
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

  private def addFlows(compUri: ResourceUri,
                       flows : ISet[SimpleErrorFlow],
                       st: SymbolTable)
  : ISet[ResourceUri] = {
    var res = isetEmpty[ResourceUri]
    if (Resource.hasResourceInfo(st.componentTable(compUri).componentDecl)) {
      val cst = st.componentTable(compUri)
      val cstUpdate = cst.asInstanceOf[ComponentTableUpdate]
      val compResource = Resource.getResource(cst.componentDecl).get
      flows.foreach{ flow =>
        if(flow.from.isDefined) {
          val from = flow.from.get._1
          val exFlows = cst.getFlowsFromPort(from)
          if(flow.to.isDefined) {
            val to = flow.to.get._1
            if(exFlows.intersect(cst.getFlowsFromPort(to)).isEmpty) {
              //flow not found for these two ports
              val fr = Resource(H.VIRTUAL_FLOW_TYPE, compResource, buildNewFlowId(), isDef = Some(true))
              cstUpdate.addFlow(fr.toUri, Some(from), Some(to), isetEmpty, isetEmpty)
              res = res + fr.toUri
            }
          } else {
            //look for sink
            if(!exFlows.exists(it => cst.flow(it).toPortUri.isEmpty)) {
              val fr = Resource(H.VIRTUAL_FLOW_TYPE, compResource, buildNewFlowId(), isDef = Some(true))
              cstUpdate.addFlow(fr.toUri, Some(from), None, isetEmpty, isetEmpty)
              res = res + fr.toUri
            }
          }
        } else {
          if(flow.to.isDefined) {
            //look for source
            if(! cst.getFlowsFromPort(flow.to.get._1).exists(it => cst.flow(it).fromPortUri.isEmpty)){
              val fr = Resource(H.VIRTUAL_FLOW_TYPE, compResource, buildNewFlowId(), isDef = Some(true))
              cstUpdate.addFlow(fr.toUri, None, Some(flow.to.get._1), isetEmpty, isetEmpty)
              res = res + fr.toUri
            }
          }
        }
      }
    }
    res
  }

  private def intraComponentFlowInfer(compUri: ResourceUri,
                                      st: SymbolTable)
  : ISet[SimpleErrorFlow] = {
    val ct = st.componentTable(compUri)
    var res = isetEmpty[SimpleErrorFlow]
    if(ct.behaviors.nonEmpty) {
      ct.behaviors.foreach{behave =>
        val lhs = if(ct.behavior(behave).lhs.isDefined) {
          getTuples(ct.behavior(behave).lhs.get).flatMap(tuple2Uris)
        } else isetEmpty

        val rhs = if(ct.behavior(behave).rhs.isDefined) {
          tuple2Uris(ct.behavior(behave).rhs.get)
        } else isetEmpty

        if(lhs.isEmpty && rhs.isEmpty) {
          isetEmpty
        } else if(rhs.isEmpty) {
          res = res ++ lhs.map{pe => SimpleErrorFlow(Some(pe), None)}
        } else if(lhs.isEmpty) {
          res = res ++ rhs.map(pe => SimpleErrorFlow(None, Some(pe)))
        } else {
          lhs.foreach {pe1 =>
            rhs.foreach{pe2 =>
              res = res + SimpleErrorFlow(Some(pe1), Some(pe2))
            }
          }
        }
      }
      res
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

  private def tuple2Uris(tup : Tuple): ISet[(ResourceUri, ResourceUri)] = {
    val tokens = tup.tokens
    tokens.flatMap{ t =>
      val portRes = Resource.getResource(t._1).get
      val errorUris = (t._2 match {
        case f: Fault => isetEmpty + Resource.getResource(f)
        case fs: FaultSet => isetEmpty ++ fs.value.map(Resource.getResource)
      }).flatten.map(_.toUri).map(e => (portRes.toUri, e))
      errorUris
    }.toSet
  }

  private def inferComponentFlows(
    componentUri: ResourceUri,
    portReach: PortReachability[FlowNode],
    st: SymbolTable
  ): ISet[ResourceUri] = {
    var res = isetEmpty[ResourceUri]
    if (Resource.hasResourceInfo(st.componentTable(componentUri).componentDecl)) {
      val compTable = st.componentTable(componentUri)
      val compTableUpdate = st.componentTable(componentUri).asInstanceOf[ComponentTableUpdate]
      val compResource = Resource.getResource(compTable.componentDecl).get
      res = res ++ addFlows(componentUri, intraComponentFlowInfer(componentUri, st), st)
      val inports = compTable.ports.filter(H.isInPort)
      val outPorts = compTable.ports.filter(H.isOutPort)
      if (compTable.subComponents.nonEmpty) {
        val inWithOutFlows = inports.filter(it => compTable.getFlowsFromPort(it).isEmpty)
        inWithOutFlows.foreach{ ip =>
          val outs = minePortRelationSubSys(ip, compTable.componentUri,st)
          outs.foreach{op =>
            val fr = Resource(H.VIRTUAL_FLOW_TYPE, compResource, buildNewFlowId(), isDef = Some(true))
            compTableUpdate.addFlow(fr.toUri, Some(ip), Some(op), isetEmpty, isetEmpty)
            res = res + fr.toUri
          }
        }
      } else {

        if (compTable.flows.isEmpty) {
          //leaf comp and no flows defined
          //from everything to everything
//          println(H.uri2CanonicalName(componentUri))

          inports.foreach { ip =>
            outPorts.foreach { op =>
              val fr = Resource(H.VIRTUAL_FLOW_TYPE, compResource, buildNewFlowId(), isDef = Some(true))
              compTableUpdate.addFlow(fr.toUri, Some(ip), Some(op), isetEmpty, isetEmpty)
              res = res + fr.toUri
            }
          }

        } else {
          val inWithOutFlows = inports.filter(it => compTable.getFlowsFromPort(it).isEmpty)
          inWithOutFlows.foreach { ip =>
            outPorts.foreach { op =>
              val fr = Resource(H.VIRTUAL_FLOW_TYPE, compResource, buildNewFlowId(), isDef = Some(true))
              compTableUpdate.addFlow(fr.toUri, Some(ip), Some(op), isetEmpty, isetEmpty)
              res = res + fr.toUri
            }
          }
        }
      }
    }
    res
  }

  private def buildNewFlowId(): String = {
    flowid = flowid + 1
    COMPUTED_FLOW_NAME + flowid
  }

  private def minePortRelationSubSys(portUri: ResourceUri,
                                graphUri: ResourceUri,
                                st: SymbolTable)
  : ISet[ResourceUri] = {
    val graph = FlowNode.getGraph(graphUri)
    val H = SymbolTableHelper
    var res = isetEmpty[ResourceUri]

    if(graph.isDefined && graph.get.getInPortNodes.flatMap(_.ports).contains(portUri)) {
      val ports = graph.get.nodes.flatMap(_.ports).toSet
      var worklist = ilistEmpty[ResourceUri] :+ portUri

      while(worklist.nonEmpty) {
        val curr = worklist.head
        if(!res.contains(curr)) {
          res = res + curr
          val succ = graph.get.getSuccessorPorts(curr).ports
          worklist= worklist ++ succ.intersect(ports)
        }
        worklist = worklist.tail
      }
      res = res.intersect(graph.get.getOutPortNodes.flatMap(_.ports))
    }
    res
  }

}

case class SimpleErrorFlow(from :Option[(ResourceUri, ResourceUri)],
                            to: Option[(ResourceUri, ResourceUri)])
