package org.sireum.awas.analysis

import org.sireum.awas.ast.{BehaveExpr, Fault, FaultSet}
import org.sireum.awas.fptc.FlowNode.Edge
import org.sireum.awas.fptc.{FlowGraph, FlowNode, NodeType}
import org.sireum.awas.reachability.{ErrorReachability, ErrorReachabilityImpl}
import org.sireum.awas.symbol.{ComponentTable, Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.{imapEmpty, _}

object FPTCAnalysis {

  private val H = SymbolTableHelper
  private var fptcGraphResult = imapEmpty[ResourceUri, IMap[ResourceUri, FPTCNode]]

  def apply(graph: ResourceUri, st: SymbolTable): IMap[ResourceUri, FPTCNode] = {
    if (fptcGraphResult.get(graph).isDefined) {
      fptcGraphResult(graph)
    } else {
      fptcGraphResult = fptcGraphResult + (graph -> computeFptc(graph, st))
      fptcGraphResult(graph)
    }
  }

  def computeFptc(graphUri: ResourceUri, st: SymbolTable): IMap[ResourceUri, FPTCNode] = {
    var result = imapEmpty[ResourceUri, FPTCNode]

    if (FlowNode.getGraph(graphUri).isDefined) {
      val graph = FlowNode.getGraph(graphUri).get
      val compST = st.componentTable(graph.getUri)
      val nodes = FlowNode.getGraph(graphUri).get.nodes.toSet
      val iotas = computeIota(nodes, st)
      val er = ErrorReachability(st)
      var workList = iotas.toList
      result = computeExternal(nodes, st)

      while (workList.nonEmpty) {
        var curr = workList.head
        val res = propagate(curr, result, graph, er, st)
        result = result ++ res._2
        workList = workList ++ res._1
        workList = workList.tail
      }
    }
    result
  }

  def propagate(
    node: ResourceUri,
    res: IMap[ResourceUri, FPTCNode],
    graph: FlowGraph[FlowNode, Edge],
    er: ErrorReachability[FlowNode],
    st: SymbolTable
  ): (ISet[ResourceUri], IMap[ResourceUri, FPTCNode]) = {
    var result = isetEmpty[ResourceUri]
    val behaviorUris = if (H.getUriType(node) == H.COMPONENT_TYPE) st.componentTable(node).behaviors else isetEmpty
    val currFptcNode = res.getOrElse(node, FPTCNode(imapEmpty, isetEmpty, isetEmpty))
    val propagation = currFptcNode.propagations
    var updatedFptcNode = imapEmpty[ResourceUri, FPTCNode]

    if (!H.isPort(node)) {
      if (behaviorUris.nonEmpty) {
        behaviorUris.foreach { buri =>
          val behave = st.componentTable(node).behavior(buri)
          if (!currFptcNode.behaviors.contains(buri) &&
            checkBehaveSat(behave, propagation)) {
            if (behave.rhs.isDefined) {
              behave.rhs.get.tokens.foreach { t =>
                val portRes = Resource.getResource(t._1).get
                val errorUris = (t._2 match {
                  case f: Fault => isetEmpty + Resource.getResource(f)
                  case fs: FaultSet => isetEmpty ++ fs.value.map(Resource.getResource)
                }).flatten.map(_.toUri)
                val newFptcNode = currFptcNode.copy(
                  propagations = currFptcNode.propagations ++ (imapEmpty + (portRes.toUri -> errorUris))
                    .map(it => it._1 -> (it._2 ++ currFptcNode.propagations.getOrElse(it._1, isetEmpty))),
                  behaviors = currFptcNode.behaviors + buri
                )
                updatedFptcNode = updatedFptcNode + (node -> newFptcNode)
                if (currFptcNode.propagations != newFptcNode.propagations) {
                  val succError = errorUris.map(
                    e =>
                      er.getSuccessor(portRes.toUri, e)
                        .filter(
                          f =>
                            Resource.getParentUri(f._1).isDefined &&
                              graph.nodes.map(_.getUri).toSet.contains(Resource.getParentUri(f._1).get)
                      )
                  )
                  val succInfo = successorErrorProp(succError, node, res)
                  result = result ++ succInfo._1
                  updatedFptcNode = updatedFptcNode ++ succInfo._2
                }
              }
            } else {
              updatedFptcNode = updatedFptcNode + (node -> currFptcNode.copy(behaviors = currFptcNode.behaviors + buri))
            }
          }
        }
      } else {
        propagation.foreach { p =>
          if (H.isInPort(p._1)) {
            p._2.foreach { e =>
              val outError = er.getSuccessor(p._1, e)
              outError.foreach { oe =>
                val toProp = if (propagation.contains(oe._1)) {
                  (oe._2 diff propagation(oe._1)).map((oe._1, _))
                } else {
                  oe._2.map((oe._1, _))
                }
                if (toProp.nonEmpty) {
                  var outProp = propagation
                  toProp.foreach { tp => outProp = outProp + (tp._1 -> (outProp.getOrElse(tp._1, isetEmpty) + tp._2))
                  }
                  updatedFptcNode = updatedFptcNode + (node -> currFptcNode.copy(propagations = outProp))
                  val succError = toProp.map(it => er.getSuccessor(it._1, it._2))
                  val succInfo = successorErrorProp(succError, node, res)
                  result = result ++ succInfo._1
                  updatedFptcNode = updatedFptcNode ++ succInfo._2
                }
              }
            }
          }
        }
      }
    }
    (result, updatedFptcNode)
  }

  def successorErrorProp(
    succError: ISet[IMap[ResourceUri, ISet[ResourceUri]]],
    node: ResourceUri,
    res: IMap[ResourceUri, FPTCNode]
  ): (ISet[ResourceUri], IMap[ResourceUri, FPTCNode]) = {
    var updatedFptcNode = imapEmpty[ResourceUri, FPTCNode]
    var result = isetEmpty[ResourceUri]
    succError.foreach { pe =>
      val filteredPE = pe.filter { it =>
        Resource.getParentUri(it._1).isDefined &&
          FlowNode.getNode(Resource.getParentUri(it._1).get).isDefined &&
          FlowNode.getNode(Resource.getParentUri(it._1).get).get.getOwner == FlowNode.getNode(node).get.getOwner
      }
      val succNode = filteredPE.keySet.flatMap(it => Resource.getParentUri(it).map((_, it)))
      succNode.foreach { sn =>
        val succProp = res.getOrElse(sn._1, FPTCNode(imapEmpty, isetEmpty, isetEmpty)).propagations
        val updatedSuccProp = succProp ++
          (imapEmpty[ResourceUri, ISet[ResourceUri]] + (sn._2 -> filteredPE.getOrElse(sn._2, isetEmpty)))
            .map(it => it._1 -> (it._2 ++ succProp.getOrElse(it._1, isetEmpty)))
        if (updatedSuccProp != succProp) {
          result = result + sn._1
        }
        updatedFptcNode = updatedFptcNode + (sn._1 -> res
          .getOrElse(sn._1, FPTCNode(imapEmpty, isetEmpty, isetEmpty))
          .copy(propagations = updatedSuccProp))
      }
    }
    (result, updatedFptcNode)
  }

  /**
    * Checks if the behavior is applicable with the current state of the propagation
    *
    * @param behave : Behavior expression
    * @param prop : Propagations
    * @return : bool
    */
  def checkBehaveSat(behave: BehaveExpr, prop: Map[ResourceUri, ISet[ResourceUri]]): Boolean = {
    if (behave.lhs.isDefined) {
      behave.lhs.get.tokens.forall { t =>
        assert(Resource.getResource(t._1).isDefined, "port name must be valid")
        val portRes = Resource.getResource(t._1).get
        val errorUris = (t._2 match {
          case f: Fault => isetEmpty + Resource.getResource(f)
          case fs: FaultSet => isetEmpty ++ fs.value.map(Resource.getResource)
        }).flatten.map(_.toUri)
        if (prop.contains(portRes.toUri) && errorUris.nonEmpty) {
          errorUris.forall(e => prop(portRes.toUri).contains(e))
        } else {
          false
        }
      }
    } else {
      true
    }
  }

  def computeIota(nodes: ISet[FlowNode], st: SymbolTable): ISet[ResourceUri] = {
    var result = imapEmpty[ResourceUri, ResourceUri]

    nodes.filter { n =>
      if (n.getResourceType == NodeType.COMPONENT &&
        st.componentTable(n.getUri).behaviors.nonEmpty) {
        st.componentTable(n.getUri).behaviors.exists(b => st.componentTable(n.getUri).behavior(b).lhs.isDefined)
      } else {
        false
      }
    }.map(_.getUri)
  }

  def computeExternal(nodes: ISet[FlowNode], st: SymbolTable): IMap[ResourceUri, FPTCNode] = {
    var result = imapEmpty[ResourceUri, FPTCNode]
    val portNode = nodes.filter(it => it.getResourceType == NodeType.PORT && H.isInPort(it.getUri))
    portNode.foreach { pn =>
      result = result ++ successorErrorProp(
        isetEmpty + (imapEmpty + (pn.getUri -> pn.getPropagation(pn.getUri))),
        pn.getUri,
        result
      )._2
    }
    result
  }
}

case class FPTCNode(
  propagations: Map[ResourceUri, ISet[ResourceUri]],
  states: ISet[ResourceUri],
  behaviors: ISet[ResourceUri]
)
