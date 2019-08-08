/*
 *
 *  Copyright (c) 2017, Hariharan Thiagarajan, Kansas State University
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package org.sireum.awas.query

import org.sireum.awas.collector
import org.sireum.awas.collector.CollectorErrorHelper.{ReachAnalysisStage, _}
import org.sireum.awas.collector.{Collector, CollectorImpl, Operator, ResultType}
import org.sireum.awas.fptc.FlowNode.Edge
import org.sireum.awas.fptc.{FlowGraph, FlowNode}
import org.sireum.awas.query.ConstraintKind.ConstraintKind
import org.sireum.awas.reachability.ErrorReachability
import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.{ISet, _}

object QueryEval {
  type Result = ILinkedMap[String, Collector]

  def apply(m: Model, st: SymbolTable): Result = {
    new QueryEval(st).eval(m)
  }

  def apply(st : SymbolTable, qExpr : QueryExpr) : Collector = {
    new QueryEval(st).eval(qExpr)
  }

//  def apply(qs: QueryStmt, env: ILinkedMap[String, Collector],
//            st: SymbolTable): Result = {
//    new QueryEval(st).eval(qs, env)
//  }
}

object ConstraintKind extends Enumeration {
  type ConstraintKind = Value
  val All, Some, None, Regex = Value
}

//Data structure to store the eval of a WithExpr, can be simple or regex,
//in case of regex, we need automaton, and for simple we need collector and type
case class ConstraintExpr(kind : ConstraintKind,
                          simple: Option[Collector],
                          regex: Option[Anything])

final class QueryEval(st: SymbolTable) {
  type Graph = FlowGraph[FlowNode, FlowNode.Edge]

  val H = SymbolTableHelper

  var result: ILinkedMap[String, Collector] = ilinkedMapEmpty[String, Collector]

  var queries: ILinkedMap[String, QueryExpr] = ilinkedMapEmpty[String, QueryExpr]

  def eval(m: Model): QueryEval.Result = {
    eval(m, ilinkedMapEmpty[String, Collector])
  }

  def eval(m : Model, env: ILinkedMap[String, Collector]): QueryEval.Result = {
    result = env
    m.queryStmt.foreach {
      q =>
        if (q.qName.value != st.systemDecl.compName.value) {
          queries += (q.qName.value -> q.qExpr)
        } else {
          result = result + (q.qName.value -> Collector(isetEmpty,
            isetEmpty[Tag] + errorMessageGen("Query name cannot be equal to system name",
              QueryPPrinter(q.qName), ReachAnalysisStage.Query)) )
        }
    }
    queries.foreach {
      q => result = result + (q._1 -> eval(q._2))
    }
    result
  }

//  def eval(queryStmt: QueryStmt, env: ILinkedMap[String, Collector]): QueryEval.Result = {
//    result = env
//    result = result + (queryStmt.qName.value -> eval(queryStmt.qExpr))
//    result
//  }

  def eval(qexp: QueryExpr): Collector = {
    qexp match {
      case binary: BinaryExpr => eval(binary)
      case primary: PrimaryExpr => eval(primary)
      case filter: FilterExpr => eval(filter)
      case reach: ReachExpr => eval(reach)
    }
  }

  def eval(fexp: FilterExpr): Collector = {
    val lhs: Collector = eval(fexp.lhs)
    fexp.op match {
      case FilterID.NODE => {
        collector.Collector(lhs.getGraphs, Some(ResultType.Node), lhs.getEdges, lhs.getOperator,
          lhs.getCriteria, lhs.getNodes.map(_.getUri), isetEmpty[ResourceUri],
          imapEmpty[ResourceUri, ISet[ResourceUri]], isetEmpty[ResourceUri], isetEmpty[ResourceUri],
          isetEmpty[ResourceUri], isetEmpty[ResourceUri], ilinkedSetEmpty[Collector], lhs.hasCycles, lhs.getErrors ++ lhs.getWarnings)
      }
      case FilterID.PORT => {
        collector.Collector(lhs.getGraphs, Some(ResultType.Port), lhs.getEdges, lhs.getOperator,
          lhs.getCriteria, isetEmpty[ResourceUri], lhs.getPorts,
          imapEmpty[ResourceUri, ISet[ResourceUri]], isetEmpty[ResourceUri], isetEmpty[ResourceUri],
          isetEmpty[ResourceUri], isetEmpty[ResourceUri], ilinkedSetEmpty[Collector], lhs.hasCycles, lhs.getErrors ++ lhs.getWarnings)
      }
      case FilterID.IN => {
        collector.Collector(lhs.getGraphs, Some(ResultType.Port), lhs.getEdges, lhs.getOperator,
          lhs.getCriteria, isetEmpty[ResourceUri], lhs.getPorts.filter(H.isInPort),
          imapEmpty[ResourceUri, ISet[ResourceUri]], isetEmpty[ResourceUri], isetEmpty[ResourceUri],
          isetEmpty[ResourceUri], isetEmpty[ResourceUri], ilinkedSetEmpty[Collector], lhs.hasCycles, lhs.getErrors ++ lhs.getWarnings)
      }
      case FilterID.OUT => {
        collector.Collector(lhs.getGraphs, Some(ResultType.Port), lhs.getEdges, lhs.getOperator,
          lhs.getCriteria, isetEmpty[ResourceUri], lhs.getPorts.filter(H.isOutPort),
          imapEmpty[ResourceUri, ISet[ResourceUri]], isetEmpty[ResourceUri], isetEmpty[ResourceUri],
          isetEmpty[ResourceUri], isetEmpty[ResourceUri], ilinkedSetEmpty[Collector], lhs.hasCycles, lhs.getErrors ++ lhs.getWarnings)
      }
      case FilterID.PORTERROR => {
        collector.Collector(lhs.getGraphs, Some(ResultType.Error), lhs.getEdges, lhs.getOperator,
          lhs.getCriteria, isetEmpty[ResourceUri], isetEmpty[ResourceUri],
          lhs.getPortErrors, isetEmpty[ResourceUri], isetEmpty[ResourceUri],
          isetEmpty[ResourceUri], isetEmpty[ResourceUri], ilinkedSetEmpty[Collector], lhs.hasCycles, lhs.getErrors ++ lhs.getWarnings)
      }

      case FilterID.SOURCE => {
        filterSource(lhs)
      }

      case FilterID.SINK => {
        filterSink(lhs)
      }
      case _ => lhs
    }
  }

  def filterSource(c: Collector): Collector = {
    var flows = isetEmpty[ResourceUri]
    var portE = imapEmpty[ResourceUri, ISet[ResourceUri]]
    c.getFlows.foreach { f =>
      //parent of a flow should be a node
      val node = FlowNode.getNode(Resource.getParentUri(f).get).get //c.getGraphs.getNode(Resource.getParentUri(f).get).get
      val flow = node.getFlows.get(f)
      if (flow.isDefined && flow.get.fromPortUri.isEmpty && flow.get.fromFaults.isEmpty) {
        flows = flows + f
        val ports = node.getPortsFromFlows(f)
        if (ports.size == 1) {
          val port = ports.toSeq.head
          val errors = flow.get.toFaults
          portE += (port -> errors.intersect(c.getPortErrors(port)))
        }
      }
    }
    collector.Collector(c.getGraphs, Some(ResultType.Error), isetEmpty[Edge], c.getOperator,
      c.getCriteria, isetEmpty[ResourceUri], isetEmpty[ResourceUri], portE, flows,
      isetEmpty[ResourceUri], isetEmpty[ResourceUri], isetEmpty[ResourceUri],
      ilinkedSetEmpty[Collector], false, c.getErrors ++ c.getWarnings)
  }

  def filterSink(c: Collector): Collector = {
    var flows = isetEmpty[ResourceUri]
    var portE = imapEmpty[ResourceUri, ISet[ResourceUri]]
    c.getFlows.foreach { f =>
      //parent of a flow should be a node
      val node = FlowNode.getNode(Resource.getParentUri(f).get).get
      //c.getGraph.getNode(Resource.getParentUri(f).get).get
      val flow = node.getFlows.get(f)
      if (flow.isDefined && flow.get.toPortUri.isEmpty && flow.get.toFaults.isEmpty) {
        flows = flows + f
        val ports = node.getPortsFromFlows(f)
        if (ports.size == 1) {
          val port = ports.toSeq.head
          val errors = flow.get.fromFaults
          portE += (port -> errors.intersect(c.getPortErrors(port)))
        }
      }
    }
    collector.Collector(c.getGraphs, Some(ResultType.Error), isetEmpty[Edge], c.getOperator,
      c.getCriteria, isetEmpty[ResourceUri], isetEmpty[ResourceUri], portE, flows,
      isetEmpty[ResourceUri], isetEmpty[ResourceUri], isetEmpty[ResourceUri],
      ilinkedSetEmpty[Collector], false, c.getErrors ++ c.getWarnings)
  }

  def eval(rexp: ReachExpr): Collector = {
    rexp match {
      case fexp: ForwardExpr => forwardReach(eval(fexp.expr))
      case bexp: BackwardExpr => backwardReach(eval(bexp.expr))
      case cexp: ChopExpr =>
        val sourceRes = forwardReach(eval(cexp.source))
        val targetRes = backwardReach(eval(cexp.target))
        sourceRes intersect targetRes
      case pexp: PathExpr => {
        val source = eval(pexp.source)
        val target = eval(pexp.target)
        val constrain = pexp.withExpr
        val isSimple = pexp.isSimple
        val isRefined = pexp.isRefined
        if (constrain.isDefined) {
          val cExp = eval(constrain.get)
          if (isSimple) {
            simplePathReach(source, target, Some(cExp), isRefined)
          } else {
            pathReach(source, target, Some(cExp), isRefined)
          }
        } else {
          if (isSimple) {
            simplePathReach(source, target, None, isRefined)
          } else {
            pathReach(source, target, None, isRefined)
          }
        }
      }
//        case spexp: SimplePathExpr => {
//          val source = eval(spexp.source)
//          val target = eval(spexp.target)
//          val constrain = spexp.withExpr
//          if(constrain.isDefined) {
//            val cExp = eval(constrain.get)
//            simplePathReach(source, target, Some(cExp))
//          } else {
//            pathReach(source, target, None)
//          }
//        }
    }

  }

  def eval(wexp: WithExpr):ConstraintExpr = {
    wexp match {
      case se : SimpleWith =>
        ConstraintExpr(
          se.op match {
            case "all" => ConstraintKind.All
            case "some" => ConstraintKind.Some
            case "none" => ConstraintKind.None
          }, Some(eval(se.expr)), None)
      case re : RegExExpr =>
        ConstraintExpr(ConstraintKind.Regex, None, None)
    }
  }


  def eval(bexp: BinaryExpr): Collector = {
    val lhs: Collector = eval(bexp.lhs)
    val rhs: Collector = eval(bexp.rhs)
    bexp.op.trim match {
      case "union" => lhs union rhs
      case "intersect" => lhs intersect rhs
      case "-" => lhs diff rhs
    }
  }

  def backwardReach(criterion: Collector): Collector = {
    reach(criterion, isForward = false)
  }

  def simplePathReach(
    source: Collector,
    target: Collector,
    constraint: Option[ConstraintExpr],
    isRefined: Boolean
  ): Collector = {
    if (source.hasErrors || target.hasErrors) {
      source.union(target)
    } else {
      val er = ErrorReachability(st)
      val resType = if (source.getResultType.isDefined && target.getResultType.isDefined) {
        if (source.getResultType.get < target.getResultType.get) source.getResultType else target.getResultType
      } else if (source.getResultType.isDefined) {
        source.getResultType
      } else {
        target.getResultType
      }

      val res = resType match {
        case Some(ResultType.Node) => {
          if (constraint.isDefined) {
            er.reachSimplePathSet(source.getNodes.map(_.getUri), target.getNodes.map(_.getUri), constraint.get, isRefined)
          } else {
            er.reachSimplePathSet(source.getNodes.map(_.getUri), target.getNodes.map(_.getUri), isRefined)
          }
        }
        case Some(ResultType.Port) => {
          if (constraint.isDefined) {
            er.reachSimplePathSet(source.getPorts, target.getPorts, constraint.get, isRefined)
          } else {
            er.reachSimplePathSet(source.getPorts, target.getPorts, isRefined)
          }
        }
        case Some(ResultType.Error) => {
          if (constraint.isDefined) {
            er.errorSimplePathReachMapWith(source.getPortErrors, target.getPortErrors, constraint.get, isRefined)
          } else {
            er.errorSimplePathReachMap(source.getPortErrors, target.getPortErrors, isRefined)
          }
        }
        case _ => Collector(isetEmpty, source.getErrors ++ target.getErrors +
          errorMessageGen(TYPE_UNKNOWN, "", ReachAnalysisStage.Query)
          )
      }
      res
//      if(isRefined) {
//        refinedPathFilter(res)
//      } else {
//        res
//      }
    }
  }

  def refinedPathFilter(input: Collector): Collector = {
    val paths = input.getPaths
    val filteredPaths = paths.filter { p =>
      p.getFlows.forall { f =>
        val nuri = Resource.getParentUri(f)
        if (nuri.isDefined && FlowNode.getNode(nuri.get)
            .isDefined && FlowNode.getNode(nuri.get).get.getSubGraph.isDefined) {
          false
        } else {
          true
        }
      }
    }

    Collector(
      filteredPaths.foldLeft(Collector(st))(_.union(_)).getGraphs,
      ilinkedSetEmpty[Collector] ++ filteredPaths,
      input.getResultType
    )
  }

  def pathReach(
    source: Collector,
    target: Collector,
    constraint: Option[ConstraintExpr],
    isRefined: Boolean
  ): Collector = {
    if (source.hasErrors || target.hasErrors) {
      source.union(target)
    } else {
      val er = ErrorReachability(st)
      val resType = if (source.getResultType.isDefined && target.getResultType.isDefined) {
        if (source.getResultType.get < target.getResultType.get) source.getResultType else target.getResultType
      } else if (source.getResultType.isDefined) {
        source.getResultType
      } else {
        target.getResultType
      }

      val res = resType match {
        case Some(ResultType.Node) => {
          if (constraint.isDefined) {
            er.reachPathSet(source.getNodes.map(_.getUri), target.getNodes.map(_.getUri), constraint.get, isRefined)
          } else {
            er.reachPathSet(source.getNodes.map(_.getUri), target.getNodes.map(_.getUri), isRefined)
          }
        }
        case Some(ResultType.Port) => {
          if (constraint.isDefined) {
            er.reachPathSet(source.getPorts, target.getPorts, constraint.get, isRefined)
          } else {
            er.reachPathSet(source.getPorts, target.getPorts, isRefined)
          }
        }
        case Some(ResultType.Error) => {
          if (constraint.isDefined) {
            er.errorPathReachMapWith(source.getPortErrors, target.getPortErrors, constraint.get, isRefined)
          } else {
            er.errorPathReachMap(source.getPortErrors, target.getPortErrors, isRefined)
          }
        }
        case _ => Collector(isetEmpty, source.getErrors ++ target.getErrors +
          errorMessageGen(TYPE_UNKNOWN, "", ReachAnalysisStage.Query)
          )
      }
      res
      //      if(isRefined) {
      //        refinedPathFilter(res)
    //      } else {
    //        res
//      }
    }

    //    val minSType = QueryResult.getMinType(source)
    //    val minTType = QueryResult.getMinType(target)
    //    val minType = if (minSType <= minTType) minSType else minTType
    //    if (minType <= QResMinType.PathUri) {
    //      val as = QueryResult.convertToType(source, QResMinType.Uri)
    //      val at = QueryResult.convertToType(target, QResMinType.Uri)
    //      QRes(er.reachPathSet(as.toPorts, at.toPorts).map(PathResult))
    //    } else {
    //      val as = QueryResult.convertToType(source, QResMinType.Error)
    //      val at = QueryResult.convertToType(target, QResMinType.Error)
    //      val src = as.unitRes.collect { case er: ErrorResult => er.port -> er.errors }.toMap
    //      val sink = at.unitRes.collect { case er: ErrorResult => er.port -> er.errors }.toMap
    //      QRes(er.errorPathReachMap(src, sink).map(ErrorPathResult))
    //    }
  }

  def forwardReach(criterion: Collector): Collector = {
    reach(criterion, isForward = true)
  }

  def reach(criterion: Collector, isForward: Boolean): Collector = {
    val er = ErrorReachability(st)
    if (criterion.hasErrors) {
      criterion
    } else {
      if (criterion.getResultType.isDefined) {
        val resType = criterion.getResultType.get
        resType match {
          case ResultType.Node => {
            if (isForward)
              er.forwardReachSetNode(criterion.getNodes)
            else
              er.backwardReachSetNode(criterion.getNodes)
          }
          case ResultType.Port => {
            if (isForward)
              er.forwardPortReachSet(criterion.getPorts)
            else
              er.backwardPortReachSet(criterion.getPorts)
          }
          case ResultType.Error => {
            if (isForward)
              er.forwardErrorSetReach(criterion.getPortErrors)
            else
              er.backwardErrorSetReach(criterion.getPortErrors)
          }
        }
      } else {
        Collector(isetEmpty, criterion.getErrors +
          errorMessageGen(TYPE_UNKNOWN, "", ReachAnalysisStage.Query))
      }
    }

    //    val minType = QueryResult.getMinType(criterion)
    //    if (minType <= QResMinType.PathUri) {
    //      val ac = QueryResult.convertToType(criterion, QResMinType.Uri)
    //      QRes(ac.unitRes.flatMap(it =>
    //        er.forwardReach(it.asInstanceOf[UriResult].value)).map(UriResult))
    //    } else {
    //      val ac = QueryResult.convertToType(criterion, QResMinType.Error)
    //      var res = imapEmpty[ResourceUri, ISet[ResourceUri]]
    //      ac.unitRes.foreach {
    //        it =>
    //          val e = it.asInstanceOf[ErrorResult]
    //          er.forwardErrorReach(e.port, e.errors).foreach {
    //            f => res += (f._1 -> (res.getOrElse(f._1, isetEmpty[ResourceUri]) ++ f._2))
    //          }
    //      }
    //      QRes(res.map(it => ErrorResult(it._1, it._2)).toSet)
    //    }
  }

  def eval(pexp: PrimaryExpr): Collector = {
    pexp match {
      case NodeNameError(nodeName, errorSet) => eval(nodeName, errorSet)

      case Paren(expr) => eval(expr)

      case NodeSet(sets) => sets.foldLeft(Collector(st))((a, b) => a union eval(b))

      case NodeEmpty() => Collector(st)

      case QueryName(id) => {
        if(id.value == st.systemDecl.compName.value) {
          eval(NodeName(QueryNode.emptySeq.+:(id)))
        } else {
          result.getOrElse(
            id.value,
            Collector(isetEmpty, isetEmpty[Tag] + errorMessageGen(MISSING_RESULT, id.value, ReachAnalysisStage.Query))
          )
        }
      }

      case nn: NodeName => eval(nn, ivectorEmpty[ISeq[Id]])
    }
  }

  def eval(nn: NodeName, errorSet: ISeq[ISeq[Id]]): Collector = {
    var res = isetEmpty[ResourceUri]
    if (errorSet.isEmpty) {
      getCompOrPortUri(nn)
    } else {
      val uris = getCompOrPortUri(nn)
      val errorUri = errorSet.flatMap(es =>
        SymbolTableHelper.getErrorUri(st, es.map(_.value).mkString("."))).toSet

      collector.Collector(
        isetEmpty[FlowGraph[FlowNode, Edge]],
        Some(ResultType.Error),
        Some(Operator.ID),
        uris.getPorts,
        isetEmpty[ResourceUri],
        isetEmpty[ResourceUri],
        uris.getPorts.map((_, errorUri)).toMap)
    }
  }

  def getCompOrPortUri(n: NodeName): Collector = {
    val nId = n.ids.map(_.value)
    val uri = H.findUri(nId, st)
    if (uri.isDefined) {
      if ((uri.get.startsWith(H.COMPONENT_TYPE) ||
        uri.get.startsWith(H.CONNECTION_TYPE)) &&
        FlowNode.getNode(uri.get).isDefined) {
        assert(FlowNode.getNode(uri.get).isDefined)
        collector.Collector(
          isetEmpty + FlowNode.getNode(uri.get).get.getOwner,
          Some(ResultType.Node),
          Some(Operator.ID), isetEmpty[ResourceUri] + uri.get,
          isetEmpty[ResourceUri] + uri.get, isetEmpty[ResourceUri],
          imapEmpty[ResourceUri, ISet[ResourceUri]])
      } else if (H.isPort(uri.get)) {
        var gra = isetEmpty[Graph]
        if (FlowNode.getNode(uri.get).isDefined) {
          gra += FlowNode.getNode(uri.get).get.getOwner
        }
        val nodeUri = Resource.getParentUri(uri.get).get
        if (FlowNode.getNode(nodeUri).isDefined) {
          gra += FlowNode.getNode(nodeUri).get.getOwner
        }
        collector.Collector(
          isetEmpty ++ gra,
          Some(ResultType.Port),
          Some(Operator.ID), isetEmpty[ResourceUri] + uri.get,
          isetEmpty[ResourceUri], isetEmpty[ResourceUri] + uri.get,
          imapEmpty[ResourceUri, ISet[ResourceUri]])
      } else if (H.isFlow(uri.get)) {
        val nodeUri = Resource.getParentUri(uri.get).get
        val gra = FlowNode.getNode(nodeUri).get.getOwner
        collector.Collector(
          isetEmpty + gra,
          Some(ResultType.Port),
          Some(Operator.ID), isetEmpty[ResourceUri] + uri.get,
          isetEmpty[ResourceUri], isetEmpty[ResourceUri] ++ FlowNode.getNode(nodeUri).get.getPortsFromFlows(uri.get),
          imapEmpty[ResourceUri, ISet[ResourceUri]])
      } else if (uri.get.startsWith(H.COMPONENT_TYPE) && uri.get == st.system) {
        collector.Collector(
          FlowNode.getGraphs,
          FlowNode.getGraphs.flatMap(_.nodes),
          isetEmpty[ResourceUri],
          ResultType.Node,
          FlowNode.getGraphs.flatMap(_.edges),
          isetEmpty[ResourceUri],
          isetEmpty[ResourceUri] + uri.get, false,
          isetEmpty[Tag])
      } else {
        Collector(isetEmpty,
          isetEmpty[Tag] + errorMessageGen(MISSING_CRITERIA,
            QueryPPrinter(n), ReachAnalysisStage.Query))
      }

    } else {
      Collector(isetEmpty,
        isetEmpty[Tag] + errorMessageGen(MISSING_CRITERIA,
          QueryPPrinter(n), ReachAnalysisStage.Query))
    }
  }
}