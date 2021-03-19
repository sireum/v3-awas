package org.sireum.awas.benchmark

import org.sireum.awas.ast.Model
import org.sireum.awas.flow.{FlowGraph, FlowNode, NodeType}
import org.sireum.awas.symbol.{ComponentTable, SymbolTable, SymbolTableHelper}


object ModelStatInfo {

  def apply(st: SymbolTable): ModelInfo = {
    val graphs = FlowNode.getGraphs.size
    val height = computeHeight(st)
    val comps = FlowNode.getGraphs.flatMap(_.nodes).count(_.isComponent)
    val conns = FlowNode.getGraphs.flatMap(_.nodes).count(_.getResourceType == NodeType.CONNECTION)
    val ports = FlowNode.getGraphs.flatMap(_.nodes).flatMap(_.ports).size
    val nodes = FlowNode.getGraphs.flatMap(_.nodes).size
    val edges = FlowNode.getGraphs.flatMap(_.edges).size
    val flows = FlowNode.getGraphs.flatMap(_.nodes).flatMap(_.getFlows).size

    ModelInfo(graphs, height, comps, conns, ports, nodes, edges, flows)
  }

  def computeHeight(st: SymbolTable): Int = {
    height(st, st.componentTable(st.system), 0)
  }

  def height(st: SymbolTable, cst: ComponentTable, h: Int): Int = {
    if (cst.subComponents.nonEmpty) {
      cst.subComponents.map(c => height(st, st.componentTable(c), h + 1)).max
    } else {
      h
    }
  }

}

case class ModelInfo(graphs : Int,
                     height: Int,
                     components: Int,
                     connections: Int,
                     ports: Int,
                     nodes: Int,
                     edges: Int,
                     flows: Int)
