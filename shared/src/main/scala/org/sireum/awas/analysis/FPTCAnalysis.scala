package org.sireum.awas.analysis

import org.sireum.awas.analysis.FPTCAnalysis.H
import org.sireum.awas.ast.{BehaveExpr, Fault, FaultSet, Id, One, PrettyPrinter, Tuple}
import org.sireum.awas.collector.{Collector, ResultType}
import org.sireum.awas.fptc.FlowNode.Edge
import org.sireum.awas.fptc.{FlowGraph, FlowNode, NodeType}
import org.sireum.awas.reachability.{ErrorReachability, ErrorReachabilityImpl}
import org.sireum.awas.symbol.{ComponentTable, Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.{IMap, ISet, imapEmpty, _}

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
      BehaviorConditionEval(behave.lhs.get, prop)
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


object StateReachAnalysis {

  private var fptc: Option[Collector] = None

  def fptcAnalysis(st: SymbolTable): Collector = {
    if (fptc.isDefined) {
      fptc.get
    } else {
      fptc = Some(new StateReachAnalysis(st).computeFptc())
      fptc.get
    }
  }

  def getReachability(state: IMap[ResourceUri, ISet[ResourceUri]], st: SymbolTable): Collector = {
    val sra = new StateReachAnalysis(st)
    fptcAnalysis(st)

    val backreach = sra.reachBackward(state, fptc)

    val ff = sra.reachForward(imapEmpty, Some(backreach))

    ff

  }
}


class StateReachAnalysis(st: SymbolTable) {

  val H = SymbolTableHelper
  val er = ErrorReachability(st)


  def getInitStore(initState: IMap[ResourceUri, ISet[ResourceUri]],
                   collector: Option[Collector]): IMap[ResourceUri, ISet[ResourceUri]] = {
    if (collector.isDefined) {

      intersectErrors(collector.get.getPortErrors, initState)
    }
    else
      initState
  }

  def reachForward(initState: IMap[ResourceUri, ISet[ResourceUri]],
                   collector: Option[Collector]): Collector = {

    var store = getInitStore(initState, collector)
    val nodes = if (collector.isDefined) collector.get.getNodes else FlowNode.getGraphs.flatMap(_.nodes)
    val initModes = addinitModes(st, nodes)
    store = store ++ initModes
    var workList = nodes.toList
    var res = Collector.buildMode(nodes.map(_.getOwner.getUri), initModes.values.flatten.toSet, isetEmpty[ResourceUri])
    while (workList.nonEmpty) {
      val currentNode = workList.head
      if (currentNode.getResourceType == NodeType.COMPONENT) {
        val cst = st.componentTable(currentNode.getUri)
        val behaviorUris = if (H.getUriType(currentNode.getUri) == H.COMPONENT_TYPE) {
          cst.behaviors.filter { x =>
            if (collector.isDefined) collector.get.getBehavior.contains(x) else true
          }
        } else {
          isetEmpty
        }
        val transitionUris = if (H.getUriType(currentNode.getUri) == H.COMPONENT_TYPE) cst.transitions.filter { x =>
          if (collector.isDefined) collector.get.getBehavior.contains(x) else true
        } else isetEmpty
        if (behaviorUris.nonEmpty) {
          //first compute state transitions
          cst.transitions.foreach { trans =>
            if (cst.transition(trans).propCond.isDefined &&
              BehaviorConditionEval(cst.transition(trans).propCond.get, store) &&
              Resource.getResource(cst.transition(trans).lhs.head).isDefined
            ) {
              if (store.get(currentNode.getUri).isDefined &&
                store(currentNode.getUri).contains(Resource.getResource(cst.transition(trans).lhs.head).get.toUri)) {
                val modes = if (Resource.getResource(cst.transition(trans).rhs.head).isDefined)
                  isetEmpty + Resource.getResource(cst.transition(trans).rhs.head).get.toUri
                else
                  isetEmpty[ResourceUri]
                store = store + (currentNode.getUri -> (store.getOrElse(currentNode.getUri, isetEmpty) ++ modes))
                res = res.union(Collector.buildMode(isetEmpty + currentNode.getOwner.getUri, modes, isetEmpty + trans))

              }
            }
          }
          //behavior based out prop

          behaviorUris.foreach { buri =>
            val behave = cst.behavior(buri)
            if (behave.lhs.isDefined) {

              if (BehaviorConditionEval(behave.lhs.get, store)) {
                if (behave.states.nonEmpty &&
                  Resource.getResource(behave.states.head).isDefined) {

                  if (store.get(currentNode.getUri).isDefined &&
                    store(currentNode.getUri).contains(Resource.getResource(behave.states.head).get.toUri)) {
                    res = res.union(Collector.buildMode(isetEmpty + currentNode.getOwner.getUri, isetEmpty, isetEmpty + buri))
                    if (behave.rhs.isDefined) {
                      val resP = propagation(behave.rhs.get, store)
                      workList = workList ++ (resP.getNodes - currentNode)
                      resP.getPortErrors.foreach { pe => store = store + (pe._1 -> (store.getOrElse(pe._1, isetEmpty) ++ pe._2)) }
                      res = res.union(resP)
                    }
                    //process rhs
                  }
                } else {
                  res = res.union(Collector.buildMode(isetEmpty + currentNode.getOwner.getUri, isetEmpty, isetEmpty + buri))
                  if (behave.rhs.isDefined) {
                    val resP = propagation(behave.rhs.get, store)
                    workList = workList ++ (resP.getNodes - currentNode)
                    resP.getPortErrors.foreach { pe => store = store + (pe._1 -> (store.getOrElse(pe._1, isetEmpty) ++ pe._2)) }
                    res = res.union(resP)
                  }
                  //process rhs
                }
              }
            } else {
              res = res.union(Collector.buildMode(isetEmpty + currentNode.getOwner.getUri, isetEmpty, isetEmpty + buri))
              if (behave.rhs.isDefined) {
                val resP = propagation(behave.rhs.get, store)
                workList = workList ++ (resP.getNodes - currentNode)
                resP.getPortErrors.foreach { pe => store = store + (pe._1 -> (store.getOrElse(pe._1, isetEmpty) ++ pe._2)) }
                res = res.union(resP)
              }
              //process rhs
            }
          }
        }
      } else {
        currentNode.inPorts.foreach { ip =>
          if (store.contains(ip)) {
            store.getOrElse(ip, isetEmpty).foreach { e =>
              val resP = er.getSuccessor(ip, e).map(x => successor(x._1, x._2, store)).foldLeft(Collector(st))((c, n) => c.union(n))
              workList = workList ++ (resP.getNodes - currentNode)
              resP.getPortErrors.foreach { pe => store = store + (pe._1 -> (store.getOrElse(pe._1, isetEmpty) ++ pe._2)) }
              res = res.union(resP)
            }
          }
        }
      }
      workList = workList.tail
    }
    res
  }

  def reachBackward(initState: IMap[ResourceUri, ISet[ResourceUri]],
                    collector: Option[Collector]): Collector = {
    var store = getInitStore(processInitforBack(initState), collector)

    val nodes = if (collector.isDefined) collector.get.getNodes else store.map(x =>
      er.backwardErrorReach(x._1, x._2)).foldLeft(Collector(st))((c, n) => c.union((n))).getNodes

    var workList = nodes.toList
    var res = Collector(st)
    while (workList.nonEmpty) {

      val currNode = workList.head
      if (currNode.getResourceType == NodeType.COMPONENT) {
        val cst = st.componentTable(currNode.getUri)
        val behaviorUris = if (H.getUriType(currNode.getUri) == H.COMPONENT_TYPE) cst.behaviors.filter { x =>
          if (collector.isDefined) collector.get.getBehavior.contains(x) else true
        } else isetEmpty
        val transitionUris = if (H.getUriType(currNode.getUri) == H.COMPONENT_TYPE) cst.transitions.filter { x =>
          if (collector.isDefined) collector.get.getBehavior.contains(x) else true
        } else isetEmpty
        if (transitionUris.nonEmpty) {

          cst.transitions.foreach { trans =>
            if (Resource.getResource(cst.transition(trans).rhs.head).isDefined &&
              store.get(currNode.getUri).isDefined &&
              store(currNode.getUri).contains(Resource.getResource(cst.transition(trans).rhs.head).get.toUri)) {
              res = res.union(Collector.buildMode(isetEmpty + currNode.getOwner.getUri, isetEmpty, isetEmpty + trans))
              val modes = if (Resource.getResource(cst.transition(trans).lhs.head).isDefined)
                isetEmpty + Resource.getResource(cst.transition(trans).lhs.head).get.toUri
              else
                isetEmpty[ResourceUri]
              store = store + (currNode.getUri -> (store.getOrElse(currNode.getUri, isetEmpty) ++ modes))
              if (cst.transition(trans).propCond.isDefined) {
                BehaviorConditionEval.getTuples(cst.transition(trans).propCond.get).foreach { tup =>
                  tup.tokens.foreach { token =>
                    val pe = token2Uris(token)
                    if (!store.contains(pe._1) || (pe._2 diff store(pe._1)).nonEmpty) {
                      store = store + (pe._1 -> (store.getOrElse(pe._1, isetEmpty) ++ pe._2))
                      val coll = predecessor(pe._1, pe._2, store)
                      workList = workList ++ coll.getNodes
                      res = res.union(coll)

                    }
                  }
                }
              }
            }
          }
        }

        if (behaviorUris.nonEmpty) {

          behaviorUris.foreach { buri =>
            val behave = cst.behavior(buri)


            if (behave.rhs.isDefined &&
              behave.rhs.get.tokens.exists(token => store.contains(token2Uris(token)._1) && (store(token2Uris(token)._1) intersect token2Uris(token)._2).nonEmpty)) {

              res = res.union(Collector.buildMode(isetEmpty + currNode.getOwner.getUri, isetEmpty, isetEmpty + buri))
              if (behave.states.nonEmpty &&
                Resource.getResource(behave.states.last).isDefined &&
                !store.getOrElse(currNode.getUri, isetEmpty).contains(Resource.getResource(behave.states.last).get.toUri)) {

                val mode = Resource.getResource(behave.states.last).get.toUri
                store = store + (currNode.getUri -> (store.getOrElse(currNode.getUri, isetEmpty) + mode))
                workList = workList :+ currNode
                res = res.union(Collector.buildMode(isetEmpty + currNode.getOwner.getUri, isetEmpty + mode, isetEmpty + buri))
              }
              if (behave.lhs.isDefined) {
                BehaviorConditionEval.getTuples(behave.lhs.get).foreach { tup =>
                  tup.tokens.foreach { token =>
                    val pe = token2Uris(token)
                    if (!store.contains(pe._1) || (pe._2 diff store(pe._1)).nonEmpty) {
                      store = store + (pe._1 -> (store.getOrElse(pe._1, isetEmpty) ++ pe._2))
                      val coll = predecessor(pe._1, pe._2, store)
                      coll.getPortErrors.foreach { spe =>
                        store = store + (spe._1 -> (store.getOrElse(spe._1, isetEmpty) ++ spe._2))
                      }
                      workList = workList ++ coll.getNodes
                      res = res.union(coll)
                    }
                  }
                }
              }
            }
          }
        }
      } else {
        currNode.outPorts.foreach { p =>
          store.getOrElse(p, isetEmpty).foreach { e =>
            val resP = er.getPredecessor(p, e).map(x => predecessor(x._1, x._2, store)).foldLeft(Collector(st))((c, n) => c.union(n))
            workList = workList ++ (resP.getNodes - currNode)
            resP.getPortErrors.foreach { pe => store = store + (pe._1 -> (store.getOrElse(pe._1, isetEmpty) ++ pe._2)) }
            res = res.union(resP)
          }
        }
      }
      workList = workList.tail

    }

    res
  }

  private def processInitforBack(init: IMap[ResourceUri, ISet[ResourceUri]]): IMap[ResourceUri, ISet[ResourceUri]] = {
    var res = init
    init.foreach { pe =>
      if (H.isInPort(pe._1)) {
        pe._2.foreach { e =>
          er.getPredecessor(pe._1, e).foreach(it => res = res + (it._1 -> (res.getOrElse(pe._1, isetEmpty) ++ it._2)))
        }
      }
    }
    res
  }

  private def token2Uris(t: (Id, One)): (ResourceUri, ISet[ResourceUri]) = {
    val portRes = Resource.getResource(t._1).get
    val errorUris = (t._2 match {
      case f: Fault => isetEmpty + Resource.getResource(f)
      case fs: FaultSet => isetEmpty ++ fs.value.map(Resource.getResource)
    }).flatten.map(_.toUri)
    portRes.toUri -> errorUris
  }


  def propagation(tuple: Tuple, store: IMap[ResourceUri, ISet[ResourceUri]])
  : Collector = {
    var resColl = isetEmpty[Collector]
    var resStore = store
    tuple.tokens.foreach { t =>
      val (portRes, errorUris) = token2Uris(t)
      resColl = resColl + successor(portRes, errorUris, store)

    }
    resColl.foldLeft(Collector(st))((c, n) => c.union(n))
  }

  def predecessor(portUri: ResourceUri, errorUris: ISet[ResourceUri],
                  store: IMap[ResourceUri, ISet[ResourceUri]])
  : Collector = {
    val pred = errorUris.flatMap(e => er.getPredDetailed(portUri, e))
    val portError: Map[ResourceUri, ISet[ResourceUri]] = pred.flatMap(_.tuples).filter(it =>
      !store.contains(it._1) || !store(it._1).contains(it._2))
      .groupBy(_._1).mapValues(_.map(_._2)) + (portUri -> errorUris)
    Collector(
      pred.flatMap(_.graph),
      portError,
      pred.flatMap(_.flows),
      pred.flatMap(_.edges),
      true,
      isetEmpty[ResourceUri],
      pred.flatMap(_.errors))
  }

  def successor(portUri: ResourceUri, errorUris: ISet[ResourceUri],
                store: IMap[ResourceUri, ISet[ResourceUri]])
  : Collector = {
    val succs = errorUris.flatMap(e => er.getSuccDetailed(portUri, e))
    val portError: Map[ResourceUri, ISet[ResourceUri]] = succs.flatMap(_.tuples).filter(it =>
      !store.contains(it._1) || !store(it._1).contains(it._2))
      .groupBy(_._1).mapValues(_.map(_._2)) + (portUri -> errorUris)

    Collector(
      succs.flatMap(_.graph),
      portError,
      succs.flatMap(_.flows),
      succs.flatMap(_.edges),
      true,
      isetEmpty[ResourceUri],
      succs.flatMap(_.errors))
  }

  def addinitModes(st: SymbolTable,
                   nodes: ISet[FlowNode])
  : IMap[ResourceUri, ISet[ResourceUri]] = {
    var result = imapEmpty[ResourceUri, ISet[ResourceUri]]
    nodes.filter(_.getResourceType == NodeType.COMPONENT) foreach { node =>
      val cst = st.componentTable(node.getUri)
      if (cst.stateMachine.isDefined &&
        Resource.getResource(st.stateMachine(cst.stateMachine.get).states.head).isDefined) {
        result = result + (node.getUri ->
          (result.getOrElse(node.getUri, isetEmpty) +
            Resource.getResource(st.stateMachine(cst.stateMachine.get).states.head).get.toUri))

      }
    }
    result
  }

  def computeFptc(): Collector = {
    // var store = computeIota()
    reachForward(imapEmpty, None)
  }

  def computeIota(): IMap[ResourceUri, ISet[ResourceUri]] = {
    var result = imapEmpty[ResourceUri, ISet[ResourceUri]]
    val inPorts = FlowNode.getGraph(st.system).get.nodes.filter(it =>
      it.getResourceType == NodeType.PORT && H.isInPort(it.getUri))

    inPorts.foreach { ip =>
      result = result + (ip.getUri -> ip.getPropagation(ip.getUri))
    }
    result
  }


  private def intersectErrors(op1: IMap[ResourceUri, ISet[ResourceUri]],
                              op2: IMap[ResourceUri, ISet[ResourceUri]]):
  IMap[ResourceUri, Set[ResourceUri]] = {
    var result = imapEmpty[ResourceUri, ISet[ResourceUri]]
    val ports = op1.keySet.intersect(op2.keySet)
    ports.foreach { p =>
      result = result + ((p, op1(p).intersect(op2(p))))
    }
    result
  }

}