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

package org.sireum.awas.AliranAman

import org.sireum.ST
import org.sireum.awas.AliranAman.Lattice.{LEdge, LNode}
import org.sireum.awas.collector.Collector
import org.sireum.awas.flow.{FlowNode, NodeType}
import org.sireum.awas.graph.{AwasEdge, SlangGraphImpl}
import org.sireum.awas.reachability.PortReachability
import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.ops.GraphOps
import org.sireum.util._
import org.sireum.{$Slang, $internal, ISZ, ST}

//plan :

object SecInfoFlowAnalysis {
  def apply(): SecInfoFlowAnalysis = new SecInfoFlowAnalysisImpl(SymbolTable.getTable.get)
}

trait SecInfoFlowAnalysis {
  def getProvidedSecType(): IMap[ResourceUri, ResourceUri]

  def getSecTypes(): IMap[ResourceUri, ResourceUri]

  def getViolations(): ISet[ResourceUri]

  def getLattice(): Lattice

  def getViolatingPaths(): ILinkedMap[String, Collector]
}

class SecInfoFlowAnalysisImpl(st: SymbolTable) extends SecInfoFlowAnalysis {

  private val pr = PortReachability(st)

  private val lattice = Lattice(st)

  private val H = SymbolTableHelper

  private var violatingPaths: Option[ILinkedMap[String, Collector]] = None

  private val providedTypes = FlowNode.getGraphs
    .flatMap(_.nodes)
    .filter(_.isComponent)
    .flatMap { node =>
      var res = ivectorEmpty[(ResourceUri, String)]
      val compSt = st.componentTable(node.getUri)
      node.ports.foreach { p =>
        if (compSt.security(p).isDefined) {
          res = res :+ (p, compSt.security(p).get)
        }
      }
      res
    }
    .toMap

  private val (secType, violations) = computeArus()

  def getLattice(): Lattice = {
    lattice
  }

  def getSecTypes(): IMap[ResourceUri, ResourceUri] = {
    secType
  }

  def getViolations(): ISet[ResourceUri] = {
    violations
  }

  def getProvidedSecType(): IMap[ResourceUri, ResourceUri] = {
    providedTypes
  }

  private def getDeclass(
    currPort: ResourceUri,
    nextPort: ResourceUri,
    flows: ISet[ResourceUri]
  ): Option[(Option[ResourceUri], ResourceUri)] = {
    var correctFlow: Option[ResourceUri] = None
    flows.foreach { flow =>
      if (correctFlow.isEmpty) {
        val compUri = H.findComponentUri(flow, st)
        if (compUri.isDefined) {
          val cst = st.componentTable(compUri.get)
          if (cst.getPortsFromFlows(flow).contains(nextPort)) {
            correctFlow = Some(flow)
          }
        }
      }
    }
    if (correctFlow.isDefined) {
      val cst = st.componentTable(H.findComponentUri(correctFlow.get, st).get)
      cst.declass(correctFlow.get)
    } else {
      None
    }
  }

  private def computeArus(): (IMap[ResourceUri, ResourceUri], ISet[ResourceUri]) = {
    var result = imapEmpty[ResourceUri, (String, String)]
    // var violations = isetEmpty[ResourceUri]
    val sysGraph = FlowNode.getGraph(st.system)
    val nodes = getStartingNodes(st.system)

    val ports = nodes.flatMap(_.inPorts)

    //step 1: From the set of provided, perform a forward fixpoint, such that
    //        LUB is computed at each meet(meaning low can flow to high)

    var workList1 = ivectorEmpty[(ResourceUri, ResourceUri)]
    workList1 = workList1 ++ providedTypes.toVector
    var res = imapEmpty[ResourceUri, ResourceUri] ++ providedTypes
    while (workList1.nonEmpty) {
      val curr = workList1.head

      val succ = pr.getSuccDetailed(curr._1)

      succ.foreach { sp =>
        sp.ports.foreach { p =>
          val declass = getDeclass(curr._1, p, sp.flows)
          if (res.contains(p)) {
            if (providedTypes.contains(p)) {
              // dont overwrite it
            } else {
              val t1 = if (declass.isDefined) {
                lattice.LUB(isetEmpty + res(p) + declass.get._2)
              } else {
                lattice.LUB(isetEmpty + res(p) + curr._2)
              }
              if (res(p) != t1) {
                res = res + (p -> t1)
                workList1 = workList1 :+ (p, t1)
              }
            }
          } else {
            val nt = if (declass.isDefined) { declass.get._2 } else curr._2
            res = res + (p -> nt)
            workList1 = workList1 :+ (p, nt)
          }
        }

      }
      workList1 = workList1.tail
    }

    //step 2: from the set of (all typevar - typevars with type defined),
    //        perform a backward reach until the defined are reached

    val rest = ports.diff(res.keySet)
    var seen = isetEmpty[ResourceUri]
    var workList2 = ivectorEmpty[ResourceUri] ++ rest

    var forwardFrom = isetEmpty[ResourceUri]
    while (workList2.nonEmpty) {
      val curr = workList2.head
      if (!seen.contains(curr)) {
        if (res.keySet.contains(curr)) {
          forwardFrom = forwardFrom + curr
          seen = seen + curr
        } else {
          val pre = pr.getSuccessor(curr)
          workList2 = workList2 ++ pre
          seen = seen + curr
        }
      }
      workList2 = workList2.tail
    }

    //step 3: From the defined perform a forward analysis with LUB on join
    //          until all the undefined vars are defined

    if (forwardFrom.nonEmpty) {
      var workList3 = ivectorEmpty[(ResourceUri, String)] ++ forwardFrom.map(it => (it, res(it)))

      while (workList3.nonEmpty) {
        val curr = workList3.head
        val succ = pr.getPredecessor(curr._1)

        succ.foreach { p =>
          if (rest.contains(p)) {
            if (res.contains(p)) {
              //p should not be in providedSecInfo

              val t1 = lattice.GLB(isetEmpty + res(p) + curr._2)
              if (res(p) != t1) {
                res = res + (p -> t1)
                workList3 = workList3 :+ (p, t1)
              }
            } else {
              res = res + (p -> curr._2)
              workList3 = workList3 :+ (p, curr._2)
            }
          }
        }
        workList3 = workList3.tail
      }
    }

    //step 4: compute violations

    var localViolations = isetEmpty[ResourceUri]

    providedTypes.keySet.foreach { p =>
      pr.getPredecessor(p).foreach { pre =>
        if (H.isInPort(p)) {
          if (!lattice.checkLessThan(res(pre), res(p))) {
            localViolations = localViolations + p
          }
        } else {
          if (Resource.getParentUri(p).isDefined
            && FlowNode.getNode(Resource.getParentUri(p).get).isDefined) {
            //currently security type can be attached only to component ports
            val node = FlowNode.getNode(Resource.getParentUri(p).get).get
            assert(node.isComponent, "only component ports are in provided")
            if (node.ports.toSet.contains(pre)) {
              val flows = node.getFlowsFromPort(p).intersect(node.getFlowsFromPort(pre))
              flows.foreach { f =>
                st.componentTable(node.getUri).declass(f).foreach { d =>
                  //not checking for valid declass
                  if (!lattice.checkLessThan(d._2, res(p))) {
                    localViolations = localViolations + p
                  }
                }
              }
            }
          }
        }
      }
    }

    (res, localViolations)
  }

  def getViolatingPaths(): ILinkedMap[String, Collector] = {
    if (violatingPaths.isDefined) {
      violatingPaths.get
    } else {
      var result = ilinkedMapEmpty[String, Collector]
      violations.foreach { v =>
        var sources = isetEmpty[ResourceUri]
        var workList4 = ilistEmpty[ResourceUri]
        var seen2 = isetEmpty[ResourceUri]
        workList4 = workList4 ++ pr.getPredecessor(v)
        while (workList4.nonEmpty) {
          val curr = workList4.head
          if (providedTypes.keySet.contains(curr)) {
            sources = sources + curr
          } else {
            if (!seen2.contains(curr)) {
              workList4 = workList4 ++ pr.getPredecessor(curr)
            }
          }
          workList4 = workList4.tail
        }

        result = result ++ sources.flatMap { it =>
          val coll = pr.reachSimplePath(it, v, false)
          var fiflag = true
          coll.getPaths.foreach { col =>
            var ports = ilinkedSetEmpty[ResourceUri]
            var start = it
            ports = ports + start
            while (col.getNextPort(start).nonEmpty) {
              val x = col.getNextPort(start)
              if (col.getNextPort(start).size > 1) {
                println(x)
              }
              assert(col.getNextPort(start).size <= 1)
              ports = ports + col.getNextPort(start).head
              start = col.getNextPort(start).head
            }
            val types: IList[String] = ports.map(secType(_)).toList
            val flag = types.foldLeft(true) { (r, c) =>
              if (types.indexOf(c) < types.size - 1) {
                r && lattice.checkLessThan(c, types(types.indexOf(c) + 1))
              } else r
            }
            if (!flag) {
              fiflag = false
            }
          }

          if (!fiflag) {
            Some((H.uri2CanonicalName(it) + "->" + H.uri2CanonicalName(v), coll))
          } else {
            None
          }
        }.toMap
      }

      violatingPaths = Some(result)
      violatingPaths.get
    }
  }

  def getStartingNodes(system: ResourceUri): ISet[FlowNode] = {
    var result = isetEmpty[FlowNode]
    val inPortNodes = FlowNode.getGraph(system).get.getInPortNodes
    if (inPortNodes.nonEmpty) {
      result = inPortNodes
    } else {
      val graph = FlowNode.getGraph(system).get
      val minPortsize = graph.getIncomingEdges(graph.nodes.minBy(it => graph.getIncomingEdges(it).size)).size
      result = graph.nodes.filter(it => graph.getIncomingEdges(it).size == minPortsize).toSet
    }
    result
  }

}

object Lattice {

  val TOP = "$T$"
  val BOT = "$B$"

  class LEdge(src: LNode, dst: LNode) extends AwasEdge[LNode] {
    override def source: LNode = src

    override def target: LNode = dst
  }

  case class LNode(id: String)

  def apply(st: SymbolTable): Lattice = {
    val lattice = new LatticeImpl(st)
    st.typeDecls.foreach { typeUri =>
      val elems = st.typeTable(typeUri).latticeElements
      elems.foreach { e =>
        lattice.addNode(e)
        st.typeTable(typeUri).latticeParents(e).foreach { p =>
          lattice.addEdge(p, e)
        }
      }
    }
    val t = lattice.addNode(TOP)
    val b = lattice.addNode(BOT)

    val leaf = lattice.nodes.filter(it => lattice.incommingEdges(it).isEmpty && it != t)
    leaf.foreach(l => lattice.addEdge(b.id, l.id))
    val roots = lattice.nodes.filter(it => lattice.outgoingEdges(it).isEmpty && it != b)
    roots.foreach(r => lattice.addEdge(r.id, t.id))
    lattice
  }
}

trait Lattice {
  def nodes: ISet[LNode]
  def incommingEdges(node: LNode): ISet[LEdge]
  def outgoingEdges(node: LNode): ISet[LEdge]
  def getParents(nodeId: String): ISet[String]
  def getChildren(nodeId: String): ISet[String]
  def computeAllParent(id: String): IList[String]
  def computeAllChildren(id: String): IList[String]
  def LUB(nodes: ISet[String]): String
  def GLB(nodes: ISet[String]): String
  def checkLessThan(type1: String, type2: String): Boolean

  def getDot: String
}

trait LatticeUpdate {
  def addNode(id: String): LNode

  def addEdge(from: String, to: String): Unit
}

class LatticeImpl(st: SymbolTable) extends Lattice with LatticeUpdate {

  private var idNodeMap = imapEmpty[String, LNode]

  private var nodePrentsMap = imapEmpty[String, IList[String]]

  private var nodeChildrenMap = imapEmpty[String, IList[String]]

  val graph = new SlangGraphImpl[LNode, LEdge]()

  private var graphAttributes = ivectorEmpty[ST] :+ st"""rankdir=BT"""

  private var toDot: () => String = () => {
    val nST: ISZ[ST] = ISZ((for (e <- graph.nodes) yield st"""${nodeToDot(e)}""").toSeq.sortWith((lt1, lt2) =>
      lt1.render < lt2.render): _*)
    val eST: ISZ[ST] =
      ISZ[ST]((for (e <- graph.edges.toSeq) yield st""" "${e.source.id}" -> "${e.target.id}" """).sortWith((e1, e2) => e1.render < e2.render): _*)

    val r =
      st"""digraph "Lattice" {
          |
      |  ${(graphAttributes, "\n")}
          |
      |  ${(nST, "\n")}
          |
      |  ${(eST, "\n")}
          |
      |}"""
    r.render.value
  }

  private var nodeToDot = (n: LNode) => {
    st""" "${n.id}" [label="${
      if (n.id != Lattice.TOP || n.id != Lattice.BOT) {
        SymbolTableHelper.uri2IdString(n.id)
      } else n.id
    }" ${(if (n.id == Lattice.TOP) "rank=max" else if (n.id == Lattice.BOT) "rank=min" else "")} shape="box"] """
  }


  override def getDot: String = toDot()

  def nodes: ISet[LNode] = {
    graph.nodes.toSet
  }

  def addNode(id: String): LNode = {
    val n = if (idNodeMap.contains(id)) {
      idNodeMap(id)
    } else {
      val t = new LNode(id)
      idNodeMap = idNodeMap + (id -> t)
      t
    }
    graph.addNode(n)
    n
  }

  def addEdge(from: String, to: String) = {
    assert(idNodeMap.contains(from) && idNodeMap.contains(to))
    if (from != to) {
      graph.addEdge(idNodeMap(from), idNodeMap(to), new LEdge(idNodeMap(from), idNodeMap(to)))
    }
  }

  def incommingEdges(node: LNode): ISet[LEdge] = {
    graph.getIncomingEdges(node).toSet
  }

  def outgoingEdges(node: LNode): ISet[LEdge] = {
    graph.getOutgoingEdges(node).toSet
  }

  def getParents(nodeId: String): ISet[String] = {
    if (nodeId == Lattice.TOP) {
      isetEmpty
    } else {
      if (idNodeMap.contains(nodeId)) {
        graph.getSuccessorNodes(idNodeMap(nodeId)).map(_.id).toSet
      } else {
        assert(false, "all types must be in the map")
        isetEmpty
      }
    }
  }

  def getChildren(nodeId: String): ISet[String] = {
    if (nodeId == Lattice.BOT) {
      isetEmpty
    } else {
      if (idNodeMap.contains(nodeId)) {
        graph.getPredecessorNodes(idNodeMap(nodeId)).map(_.id).toSet
      } else {
        assert(false, "all types must be in the map")
        isetEmpty
      }
    }
  }

  def LUB(nodes: ISet[String], check: Boolean): Option[String] = {
    val parents = nodes.map(computeAllParent)
    if (check) {
      var common = parents.foldLeft(nodes)((r, c) => r.intersect(c.toSet))
      for (b1 <- common) {
        for (b2 <- common if b1 != b2) {
          if (computeAllParent(b1).contains(b2)) {
            common = common - b2
          }
        }
      }
      if (common.size == 1) {
        Some(common.head)
      } else {
        None
      }
    } else {
      val any = parents.head
      val rest = (parents - any).map(_.toSet)
      var res = Lattice.TOP
      var found = false
      for (it <- any) {
        if (!found && rest.forall(temp => temp.contains(it))) {
          found = true
          res = it
        }
      }
      Some(res)
    }
  }

  def reduce(edges: ISet[LEdge]): Unit = {
    edges.foreach { edge =>
      val src = edge.source
      val tgt = edge.target
      val otherSucc = graph.getSuccessorNodes(src) - tgt
      if (graph.forwardReach(otherSucc.toSet).contains(tgt)) {
        graph.removeEdge(src, tgt)
      }
    }
  }

  def computeAllParent(id: String): IList[String] = {
    if (nodePrentsMap.contains(id)) {
      nodePrentsMap(id)
    } else {
      var res = ilistEmpty[LNode]
      if (idNodeMap.contains(id)) {
        var worklist = ilistEmpty[LNode]
        worklist = worklist :+ idNodeMap(id)
        while (worklist.nonEmpty) {
          val curr = worklist.head
          res = res :+ curr
          if (curr.id != Lattice.TOP) {
            worklist = worklist ++ graph.getSuccessorNodes(curr)
          }
          worklist = worklist.tail
        }
      }
      nodePrentsMap = nodePrentsMap + (id -> res.map(_.id))
      res.map(_.id)
    }
  }

  def GLB(nodes: ISet[String]): String = {
    val parents = nodes.map(computeAllParent)
    val any = parents.head
    val rest = (parents - any).map(_.toSet)
    var res = Lattice.BOT
    var found = false
    for (it <- any) {
      if (!found && rest.forall(temp => temp.contains(it))) {
        found = true
        res = it
      }
    }
    res
  }

  def computeAllChildren(id: String): IList[String] = {
    if (nodeChildrenMap.contains(id)) {
      nodeChildrenMap(id)
    } else {
      var res = ilistEmpty[LNode]
      if (idNodeMap.contains(id)) {
        var worklist = ilistEmpty[LNode]
        worklist = worklist :+ idNodeMap(id)
        while (worklist.nonEmpty) {
          val curr = worklist.head
          res = res :+ curr
          if (curr.id != Lattice.BOT) {
            worklist = worklist ++ graph.getPredecessorNodes(curr)
          }
          worklist = worklist.tail
        }
      }
      nodeChildrenMap = nodeChildrenMap + (id -> res.map(_.id))
      res.map(_.id)
    }
  }

  def checkLessThan(type1: String, type2: String): Boolean = {
    computeAllParent(type1).contains(type2) || type1 == type2
  }

  override def LUB(nodes: ISet[String]): String = LUB(nodes, check = false).get
}
