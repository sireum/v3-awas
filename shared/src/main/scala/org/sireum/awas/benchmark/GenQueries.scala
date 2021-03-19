package org.sireum.awas.benchmark

import org.sireum.awas.flow.{FlowGraph, FlowNode, NodeType}
import org.sireum.awas.symbol.{SymbolTable, SymbolTableHelper}
import org.sireum.util._

import scala.util.Random

object GenQueries {

  val H = SymbolTableHelper
  val r: Random = new scala.util.Random(314)


  def apply(st: SymbolTable, count: Int): IList[IList[String]] = {
    var res = ilistEmpty[IList[String]]
    res = res :+ genForwardNode(st, count)
    res = res :+ genBackwardNode(st, count)
    res = res :+ genSourceTargetNode(st, count)
    res = res :+ genSourceTargetNodePath(st, count)
    res = res :+ genForwardPort(st, count)
    res = res :+ genBackwardPort(st, count)
    res = res :+ genSourceTargetPort(st, count)
    res = res :+ genSourceTargetPortPath(st, count)
    res
  }

  def genForwardNode(st: SymbolTable, count: Int): IList[String] = {
    val queryName = "forward_node_"
    val queryPre = "reach forward "
    val nodes = FlowNode.getGraphs.flatMap(_.nodes).filter(_.getResourceType != NodeType.PORT).toList.sortBy(_.getUri)
    val size = nodes.size
    var res = isetEmpty[String]
    for (i <- 0 to count) {
      res = res + (queryName + i + " = " + queryPre + H.uri2CanonicalName(nodes(r.nextInt(size)).getUri))
    }
    res.toList
  }

  def genBackwardNode(st : SymbolTable, count : Int) : IList[String] = {
    val queryName = "backward_node_"
    val queryPre = "reach backward "
    val nodes = FlowNode.getGraphs.flatMap(_.nodes).filter(_.getResourceType != NodeType.PORT).toList.sortBy(_.getUri)
    val size = nodes.size
    var res = isetEmpty[String]
    for (i <- 0 to count) {
      res = res + (queryName + i + " = " + queryPre + H.uri2CanonicalName(nodes(r.nextInt(size)).getUri))
    }
    res.toList
  }

  def genSourceTargetNode(st : SymbolTable, count : Int) : IList[String] = {
    val queryName = "source_target_node_"
    val queryPre = "reach from "
    val nodes = FlowNode.getGraphs.flatMap(_.nodes).filter(_.getResourceType != NodeType.PORT).toList.sortBy(_.getUri)
    val size = nodes.size
    var res = isetEmpty[String]
    for (i <- 0 to count) {
      res = res + (queryName + i + " = " + queryPre + H.uri2CanonicalName(nodes(r.nextInt(size)).getUri) +
        " to "+ H.uri2CanonicalName(nodes(r.nextInt(size)).getUri))
    }
    res.toList
  }

  def genSourceTargetNodePath(st : SymbolTable, count : Int) : IList[String] = {
    val queryName = "path_node_"
    val queryPre = "reach paths from "
    val nodes = FlowNode.getGraphs.flatMap(_.nodes).filter(_.getResourceType != NodeType.PORT).toList.sortBy(_.getUri)
    val size = nodes.size
    var res = isetEmpty[String]
    for (i <- 0 to count) {
      res = res + (queryName + i + " = " + queryPre + H.uri2CanonicalName(nodes(r.nextInt(size)).getUri) +
        " to "+ H.uri2CanonicalName(nodes(r.nextInt(size)).getUri))
    }
    res.toList
  }

  def genForwardPort(st : SymbolTable, count : Int) : IList[String] = {
    val queryName = "forward_port_"
    val queryPre = "reach forward "
    val ports = FlowNode.getGraphs.flatMap(_.nodes).flatMap(_.ports).toList
    val size = ports.size
    var res = isetEmpty[String]
    for (i <- 0 to count) {
      res = res + (queryName + i + " = " + queryPre + H.uri2CanonicalName(ports(r.nextInt(size))))
    }
    res.toList
  }

  def genBackwardPort(st : SymbolTable, count : Int) : IList[String] = {
    val queryName = "backward_port_"
    val queryPre = "reach backward "
    val ports = FlowNode.getGraphs.flatMap(_.nodes).flatMap(_.ports).toList
    val size = ports.size
    var res = isetEmpty[String]
    for (i <- 0 to count) {
      res = res + (queryName + i + " = " + queryPre + H.uri2CanonicalName(ports(r.nextInt(size))))
    }
    res.toList
  }

  def genSourceTargetPort(st : SymbolTable, count : Int) : IList[String] = {
    val queryName = "source_target_port_"
    val queryPre = "reach from "
    val ports = FlowNode.getGraphs.flatMap(_.nodes).flatMap(_.ports).toList
    val size = ports.size
    var res = isetEmpty[String]
    for (i <- 0 to count) {
      res = res + (queryName + i + " = " + queryPre + H.uri2CanonicalName(ports(r.nextInt(size))) +
        " to "+ H.uri2CanonicalName(ports(r.nextInt(size))))
    }
    res.toList
  }

  def genSourceTargetPortPath(st : SymbolTable, count : Int) : IList[String] = {
    val queryName = "path_port_"
    val queryPre = "reach paths from "
    val ports = FlowNode.getGraphs.flatMap(_.nodes).flatMap(_.ports).toList
    val size = ports.size
    var res = isetEmpty[String]
    for (i <- 0 to count) {
      res = res + (queryName + i + " = " + queryPre + H.uri2CanonicalName(ports(r.nextInt(size))) +
        " to "+ H.uri2CanonicalName(ports(r.nextInt(size))))
    }
    res.toList
  }

}
