//package org.sireum.awas.analysis
//import org.sireum.awas.ast._
//import org.sireum.awas.collector.{BehaviorCollector, Collector}
//import org.sireum.awas.fptc.{FlowGraph, FlowNode}
//import org.sireum.awas.fptc.FlowNode.Edge
//import org.sireum.awas.reachability.ErrorReachability
//import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper}
//import org.sireum.awas.util.AwasUtil.ResourceUri
//import org.sireum.util._
//
//class WeakestPreConditionImpl(st : SymbolTable) extends WeakestPreCondition {
//
//  val H = SymbolTableHelper
//
//  val er = ErrorReachability(st)
//
//  override def backwardReach(
//    states: ISet[IMap[
//      ResourceUri,
//      ISet[ResourceUri]
//    ]],
//    behaviors : ISet[ResourceUri]
//  ): ISet[Collector] = ???
//
//  override def forwardReach(
//    states: ISet[IMap[
//      ResourceUri,
//      ISet[ResourceUri]
//    ]],
//    behaviors : ISet[ResourceUri]
//  ): ISet[Collector] = ???
//
//  private def reach(state: IMap[ResourceUri,
//    ISet[ResourceUri]],
//                    isForward : Boolean,
//                   ): Collector = {
//    val transState = translateState(state, isForward)
//    val graphs = graphPort(transState)
//
//
//    ???
//
//
//  }
//
//  private def reachGraph(graph : ResourceUri,
//                         gState : IMap[ResourceUri, ISet[ResourceUri]],
//                         isForward : Boolean,
//                         behavior : ISet[ResourceUri]): Collector = {
//
//    ???
//
//  }
//
//
//  private def propagate(graph : ResourceUri,
//                        gState : IMap[ResourceUri, ISet[ResourceUri]],
//                        isForward : Boolean,
//                        behavior : ISet[ResourceUri]
//                       ) = {
//
//  }
//
//
////  private def successor(node: ResourceUri,
////                        res : IMap[ResourceUri, FPTCNode],
////                        graph: FlowGraph[FlowNode, Edge],
////                        er: ErrorReachability[FlowNode],
////                        behaviors : ISet[ResourceUri],
////                        st: SymbolTable)
////  : (ISet[ResourceUri], IMap[ResourceUri, FPTCNode]) = {
////    var result = isetEmpty[ResourceUri]
////    var updatedCollector = imapEmpty[ResourceUri, FPTCNode]
////
////    val behaviorUris = if (H.getUriType(node) == H.COMPONENT_TYPE) st.componentTable(node).behaviors else isetEmpty
////    val currFptcNode = res.getOrElse(node, FPTCNode(imapEmpty, isetEmpty, isetEmpty))
////
////    val propagation = currFptcNode.propagations
////
////    if (!H.isPort(node)) {
////      if (behaviorUris.nonEmpty) {
////        behaviorUris.foreach { buri =>
////          val behave = st.componentTable(node).behavior(buri)
////          if (!currFptcNode.behaviors.contains(buri) &&
////            behaviors.contains(buri)) {
////
////            if(checkBehavePrecise(behave, propagation, true)) {
////              if (behave.rhs.isDefined) {
////                behave.rhs.get.tokens.foreach { t =>
////                  val portRes = Resource.getResource(t._1).get
////                  val errorUris = (t._2 match {
////                    case f: Fault => isetEmpty + Resource.getResource(f)
////                    case fs: FaultSet => isetEmpty ++ fs.value.map(Resource.getResource)
////                  }).flatten.map(_.toUri)
////                  val newFptcNode = currFptcNode.copy(
////                    propagations = currFptcNode.propagations ++ (imapEmpty + (portRes.toUri -> errorUris))
////                      .map(it => it._1 -> (it._2 ++ currFptcNode.propagations.getOrElse(it._1, isetEmpty))),
////                    behaviors = currFptcNode.behaviors + buri
////                  )
////                  updatedCollector = updatedCollector + (node -> newFptcNode)
////                  if (currFptcNode.propagations != newFptcNode.propagations) {
////                    val succError = errorUris.map(
////                      e =>
////                        er.getSuccessor(portRes.toUri, e)
////                          .filter(
////                            f =>
////                              Resource.getParentUri(f._1).isDefined &&
////                                graph.nodes.map(_.getUri).toSet.contains(Resource.getParentUri(f._1).get)
////                          )
////                    )
////                    val succInfo = interErrorProp(succError, node, res)
////                    result = result ++ succInfo._1
////                    updatedCollector = updatedCollector ++ succInfo._2
////                  }
////                }
////              } else {
////                updatedCollector = updatedCollector + (node -> currFptcNode.copy(behaviors = currFptcNode.behaviors + buri))
////              }
////            }
////
////
////          }
////        }
////      }
////    }
////  }
//
//
//
//  def intraErrorProp(graph: FlowGraph[FlowNode, Edge],
//                     res : IMap[ResourceUri, FPTCNode],
//                     node: ResourceUri,
//                     buri: ResourceUri,
//                     behave : BehaveExpr,
//                     currFptcNode : FPTCNode,
//                     isForward : Boolean)
//  :(ISet[ResourceUri], IMap[ResourceUri, FPTCNode]) = {
//    var updatedCollector = imapEmpty[ResourceUri, FPTCNode]
//    var result = isetEmpty[ResourceUri]
//
//    if ((isForward && behave.rhs.isDefined) ||
//      (!isForward && behave.lhs.isDefined)) {
//      val tokens = if(isForward) behave.rhs.get.tokens else behave.lhs.get.tokens
//
//      tokens.foreach { t =>
//        val (portUri, errorUris) = tokenToUris(t)
//
//        val newFptcNode = currFptcNode.copy(
//          propagations = currFptcNode.propagations ++ (imapEmpty + (portUri -> errorUris))
//            .map(it => it._1 -> (it._2 ++ currFptcNode.propagations.getOrElse(it._1, isetEmpty))),
//          behaviors = currFptcNode.behaviors + buri
//        )
//
//        updatedCollector = updatedCollector + (node -> newFptcNode)
//        if (currFptcNode.propagations != newFptcNode.propagations) {
//          val succError = errorUris.map(
//            e =>
//              (if(isForward)er.getSuccessor(portUri, e)else er.getPredecessor(portUri, e)).filter(f =>
//                Resource.getParentUri(f._1).isDefined &&
//                  graph.nodes.map(_.getUri).toSet.contains(Resource.getParentUri(f._1).get)
//                )
//          )
//          val succInfo = interErrorProp(succError, node, res)
//          result = result ++ succInfo._1
//          updatedCollector = updatedCollector ++ succInfo._2
//        }
//      }
//    } else {
//      updatedCollector = updatedCollector + (node -> currFptcNode.copy(behaviors = currFptcNode.behaviors + buri))
//    }
//    (result, updatedCollector)
//  }
//
//  def intraFlowProp(node: ResourceUri,
//                    currFptcNode : FPTCNode,
//                    res : IMap[ResourceUri, FPTCNode],
//                    isForward: Boolean)
//  :(ISet[ResourceUri], IMap[ResourceUri, FPTCNode]) = {
//    val propagation = currFptcNode.propagations
//    var updatedCollector = imapEmpty[ResourceUri, FPTCNode]
//    var result = isetEmpty[ResourceUri]
//    propagation.foreach { p =>
//      if ((isForward && H.isInPort(p._1)) || (!isForward && H.isOutPort(p._1))) {
//        p._2.foreach { e =>
//          val outError = if(isForward) er.getSuccessor(p._1, e) else er.getPredecessor(p._1, e)
//          outError.foreach { oe =>
//            val toProp = if (propagation.contains(oe._1)) {
//              (oe._2 diff propagation(oe._1)).map((oe._1, _))
//            } else {
//              oe._2.map((oe._1, _))
//            }
//            if (toProp.nonEmpty) {
//              var outProp = propagation
//              toProp.foreach { tp => outProp = outProp + (tp._1 -> (outProp.getOrElse(tp._1, isetEmpty) + tp._2))
//              }
//              updatedCollector = updatedCollector + (node -> currFptcNode.copy(propagations = outProp))
//              val succError = toProp.map(it => if(isForward) er.getSuccessor(it._1, it._2) else er.getPredecessor(it._1, it._2))
//              val succInfo = interErrorProp(succError, node, res)
//              result = result ++ succInfo._1
//              updatedCollector = updatedCollector ++ succInfo._2
//            }
//          }
//        }
//      }
//    }
//    (result, updatedCollector)
//  }
//
//
//  /**
//    * propagates the new states to the successor or predecessor component
//    * @param succError
//    * @param node
//    * @param res
//    * @return
//    */
//  def interErrorProp(succError: ISet[IMap[ResourceUri, ISet[ResourceUri]]],
//                     node: ResourceUri,
//                     res: IMap[ResourceUri, FPTCNode]
//                    ): (ISet[ResourceUri], IMap[ResourceUri, FPTCNode]) = {
//    var updatedFptcNode = imapEmpty[ResourceUri, FPTCNode]
//    var result = isetEmpty[ResourceUri]
//    succError.foreach { pe =>
//      val filteredPE = pe.filter { it =>
//        Resource.getParentUri(it._1).isDefined &&
//          FlowNode.getNode(Resource.getParentUri(it._1).get).isDefined &&
//          FlowNode.getNode(Resource.getParentUri(it._1).get).get.getOwner == FlowNode.getNode(node).get.getOwner
//      }
//      val succNode = filteredPE.keySet.flatMap(it => Resource.getParentUri(it).map((_, it)))
//      succNode.foreach { sn =>
//        val succProp = res.getOrElse(sn._1, FPTCNode(imapEmpty, isetEmpty, isetEmpty)).propagations
//        val updatedSuccProp = succProp ++
//          (imapEmpty[ResourceUri, ISet[ResourceUri]] + (sn._2 -> filteredPE.getOrElse(sn._2, isetEmpty)))
//            .map(it => it._1 -> (it._2 ++ succProp.getOrElse(it._1, isetEmpty)))
//        if (updatedSuccProp != succProp) {
//          result = result + sn._1
//        }
//        updatedFptcNode = updatedFptcNode + (sn._1 -> res
//          .getOrElse(sn._1, FPTCNode(imapEmpty, isetEmpty, isetEmpty))
//          .copy(propagations = updatedSuccProp))
//      }
//    }
//    (result, updatedFptcNode)
//  }
//
//  /**
//    * remove the precicely matched errors from the state to
//    * check if there is any remaining can be weakly matched
//    * @param behave
//    * @param prop
//    * @param isForward
//    * @return
//    */
//  private def removePreciseMatch(behave: BehaveExpr,
//                                 prop: Map[ResourceUri, ISet[ResourceUri]],
//                                 isForward: Boolean)
//  :Map[ResourceUri, ISet[ResourceUri]] = {
//    var result = prop
//    val tokens = if(isForward) behave.lhs.map(_.tokens).getOrElse(ilistEmpty) else
//      behave.rhs.map(_.tokens).getOrElse(ilistEmpty)
//
//    if(tokens.nonEmpty) {
//      tokens.foreach { t =>
//        val (portUri ,errorUris) = tokenToUris(t)
//        result = if(result(portUri).nonEmpty && (result(portUri) -- errorUris).nonEmpty) {
//          result + (portUri -> (result(portUri) -- errorUris))
//        } else {
//          result - portUri
//        }
//      }
//    }
//    result
//  }
//
//  /**
//    * Converts the AST node token to port,errors pair
//    * @param t
//    * @return
//    */
//  private def tokenToUris(t : (Id, One)) : (ResourceUri, ISet[ResourceUri]) = {
//    val portUri = Resource.getResource(t._1).get.toUri
//    val errorUris = (t._2 match {
//      case f: Fault => isetEmpty + Resource.getResource(f)
//      case fs: FaultSet => isetEmpty ++ fs.value.map(Resource.getResource)
//    }).flatten.map(_.toUri)
//    (portUri, errorUris)
//  }
//
//
//  /**
//    * Checks if the given behavior is possible to produce the given state (precisely)
//    * @param behave
//    * @param prop
//    * @param isForward
//    * @return
//    */
//  def checkBehavePrecise(behave: BehaveExpr,
//                     prop: Map[ResourceUri, ISet[ResourceUri]],
//                     isForward: Boolean): Boolean = {
//    var tokens = ilistEmpty[(Id, One)]
//    tokens = if (isForward) behave.lhs.map(_.tokens).getOrElse(ilistEmpty) else
//      behave.rhs.map(_.tokens).getOrElse(ilistEmpty)
//    if(tokens.nonEmpty){
//      tokens.forall { t =>
//        assert(Resource.getResource(t._1).isDefined, "port name must be valid")
//        val (portUri ,errorUris) = tokenToUris(t)
//        if (prop.contains(portUri) && errorUris.nonEmpty) {
//          errorUris.forall(e => prop(portUri).contains(e))
//        } else {
//          false
//        }
//      }
//    } else {
//      false
//    }
//  }
//
//  /**
//    * Checks if the given behavior is possible to produce the given state (weakly)
//    * @param behave
//    * @param prop
//    * @param isForward
//    * @return
//    */
//  def checkBehaveWeak(behave: BehaveExpr,
//                         prop: Map[ResourceUri, ISet[ResourceUri]],
//                         isForward: Boolean): Boolean = {
//    var tokens = ilistEmpty[(Id, One)]
//    tokens = if (isForward) behave.lhs.map(_.tokens).getOrElse(ilistEmpty) else
//      behave.rhs.map(_.tokens).getOrElse(ilistEmpty)
//    if(tokens.nonEmpty){
//      tokens.exists { t =>
//        assert(Resource.getResource(t._1).isDefined, "port name must be valid")
//        val (portUri ,errorUris) = tokenToUris(t)
//        if (prop.contains(portUri) && errorUris.nonEmpty) {
//          errorUris.exists(e => prop(portUri).contains(e))
//        } else {
//          false
//        }
//      }
//    } else {
//      false
//    }
//  }
//
//
//
//
//
//
//  /**
//    * classifies the ports in states based on the graph, always chooses the parent
//    * @param state
//    * @return @Map[graph, @Set[ports] ]
//    */
//  private def graphPort(state: IMap[ResourceUri, ISet[ResourceUri]]): IMap[ResourceUri, ISet[ResourceUri]] = {
//    var result = imapEmpty[ResourceUri, ISet[ResourceUri]]
//    state.keySet.foreach { p =>
//      if (Resource.getParentUri(p).isDefined) {
//        val nodeUri = Resource.getParentUri(p).get
//        if (FlowNode.getNode(nodeUri).isDefined) {
//          val node = FlowNode.getNode(nodeUri).get
//          result = result +
//            (node.getOwner.getUri -> (result.getOrElse(node.getOwner.getUri, isetEmpty) + p))
//        }
//      }
//    }
//    result
//  }
//
//  /**
//    * If the port is a port node on the root system, propagate the errors to the succ or pred node.
//    * @param state
//    * @param isForward
//    * @return
//    */
//  def translateState(state: IMap[ResourceUri, ISet[ResourceUri]],
//                     isForward : Boolean): IMap[ResourceUri, ISet[ResourceUri]] = {
//    val filteredStates = if(isForward) {state.filter (pe =>
//      H.isInPort(pe._1) &&
//      Resource.getParentUri(pe._1).isDefined &&
//        Resource.getParentUri(pe._1).get == st.system)
//    } else {
//      state.filter(pe =>
//        H.isOutPort(pe._1) &&
//          Resource.getParentUri(pe._1).isDefined &&
//          Resource.getParentUri(pe._1).get == st.system)
//    }
//    var result = state -- filteredStates.keySet
//    filteredStates.keySet.foreach{pe =>
//      if(isForward) {
//        state(pe).foreach{e =>
//          val transPe = er.getSuccessor(pe, e)
//          transPe.foreach {tpe =>
//            result = result + (tpe._1 -> (result.getOrElse(tpe._1, isetEmpty) ++ tpe._2))
//          }
//        }
//      } else {
//        state(pe).foreach{e =>
//          val transPe = er.getPredecessor(pe, e)
//          transPe.foreach {tpe =>
//            result = result + (tpe._1 -> (result.getOrElse(tpe._1, isetEmpty) ++ tpe._2))
//          }
//        }
//      }
//    }
//    result
//  }
//
//
//}
