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

package org.sireum.awas.collector


import org.sireum.awas.collector.Operator.Operator
import org.sireum.awas.collector.ResultType.ResultType
import org.sireum.awas.fptc.FlowNode._
import org.sireum.awas.fptc.{FlowEdge, FlowGraph, FlowNode}
import org.sireum.awas.reachability.ErrorReachability
import org.sireum.awas.symbol.{Resource, SymbolTable}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.{IMap, ISeq, ISet, Tag, _}
import ujson.Js
import upickle.default.{macroRW, ReadWriter => RW}

//In future, even graph and symboltable may become maps where each graph points to a Collector
class CollectorImpl(
  symbolTable: SymbolTable,
                         graphs: ISet[ResourceUri] = isetEmpty[ResourceUri],
                         resType: Option[ResultType] = None,
                         var resEdges: ISet[FlowEdge[FlowNode]] = isetEmpty[Edge],
                         operator: Option[Operator] = None,
                         var criteria: ISet[ResourceUri] = isetEmpty[ResourceUri],
                         var resNodes: ISet[ResourceUri] = isetEmpty[ResourceUri],
                         var resPorts: ISet[ResourceUri] = isetEmpty[ResourceUri],
                         var resPortError: IMap[ResourceUri, ISet[ResourceUri]] = imapEmpty[ResourceUri, ISet[ResourceUri]],
                         var resFlows: ISet[ResourceUri] = isetEmpty[ResourceUri],
                         var resMode: ISet[ResourceUri] = isetEmpty[ResourceUri],
                         var resBehav: ISet[ResourceUri] = isetEmpty[ResourceUri],
                         var resEvents: ISet[ResourceUri] = isetEmpty[ResourceUri],
                         resPaths: ILinkedSet[Collector] = ilinkedSetEmpty[Collector],
                         containsCycle : Boolean = false,
                         var error: ISet[Tag] = isetEmpty[Tag]
                        ) extends Collector with Serializable {

  private def hasPath: Boolean = {
    resPaths.nonEmpty
  }

  override def toString: String = {
    var res = ""
    if (hasErrors) {
      res = res + "Error: \n" + getErrors.foldLeft("")((c, n) =>
        n match {
          case m: ErrorMessage => c + "\n" + m.message
          case _ => c + "\n" + ""
        }) + "\n"
    } else {
      if (getWarnings.nonEmpty)
        res = res + "Warning: \n" + getWarnings.foldLeft("")((c, n) =>
          n match {
            case m: WarningMessage => c + "\n" + m.message
            case _ => c + "\n" + ""
          }) + "\n"

      if (getResultType.isDefined) {
        getResultType.get match {
          case ResultType.Node => res = res + "{ " +
            getNodes.map(_.getUri).toSeq.sorted.mkString(", \n") + " } \n"
          case ResultType.Port => res = res + "{ " +
            getPorts.toSeq.sorted.mkString(", \n") + " } \n"
          case ResultType.Error => res = res + "{ " +
            getPortErrors.toSeq.map(k => k._1 + "[ " +
              k._2.toSeq.sorted.mkString(", ") + " ]").sorted.mkString("\n") + " } \n"
          case _ =>
        }
      }
    }
    res
  }

  override def getNodes: ISet[FlowNode] = {
    resType match {
      case Some(ResultType.Node) =>
        if (hasPath && resNodes.isEmpty) {
          resNodes = resPaths.flatMap(_.getNodes).map(_.getUri)
        }
        resNodes.flatMap(it => FlowNode.getNode(it))
      case Some(ResultType.Port) =>
        if (hasPath && resPorts.isEmpty) {
          resPorts = resPaths.flatMap(_.getPorts)
        }

        val nodes = resPorts.flatMap(FlowNode.getNode) ++
          resPorts.flatMap(Resource.getParentUri).flatMap(FlowNode.getNode)
        resNodes = nodes.map(_.getUri)
        nodes
//        graphs.flatMap(g => resPorts.flatMap(p => FlowGraph.graphs(g).getNode(p)))
      case Some(ResultType.Error) =>
        if (hasPath && resPortError.isEmpty) {
          resPortError = resPaths.foldLeft(imapEmpty[ResourceUri, ISet[ResourceUri]])((c, n) =>
            portErrorsUnion(c, n.getPortErrors))
        }
        val y = graphs.flatMap(g => resPortError.keySet.flatMap(p => FlowNode.getGraph(g).flatMap(_.getNode(p))))
        y
//      case Some(ResultType.Flow) =>
//        if(hasPath && resFlows.isEmpty) {
//          resFlows = resPaths.foldLeft(isetEmpty[ResourceUri])((c , n) =>
//          c union n.getFlows)
//        }
//        resFlows.flatMap(it => graph.get)
      case _ =>
        graphs.flatMap(g => resNodes.flatMap(n => FlowNode.getGraph(g).flatMap(_.getNode(n))))
    }
  }

  override def getEdges: ISet[FlowEdge[FlowNode]] = {
    if (hasPath && resEdges.isEmpty)
      resEdges = resPaths.foldLeft(isetEmpty[Edge])((c, n) => c union n.getEdges)
    resEdges
  }

  override def getSymbolTable: SymbolTable = symbolTable

  override def getGraphs: ISet[FlowGraph[FlowNode, FlowNode.Edge]] = graphs.flatMap(FlowNode.getGraph)

  override def getResultType: Option[ResultType] = resType

  override def getOperator: Option[Operator] = operator

  override def getCriteria: ISet[ResourceUri] = if (criteria.isEmpty && resPaths.nonEmpty) {
    criteria = resPaths.foldLeft(isetEmpty[ResourceUri])((c, n) => c union n.getCriteria)
    criteria
  } else {
    criteria
  }

  override def getPorts: ISet[ResourceUri] = {
    if (resType.isDefined && (resType.get != ResultType.Port)) {
      resType.get match {
        case ResultType.Node => {
          if (hasPath && resNodes.isEmpty)
            resNodes = resPaths.foldLeft(isetEmpty[ResourceUri])((c, n) =>
              c union n.getNodes.map(_.getUri))
          resPorts = graphs.flatMap(g => resNodes.flatMap(n => FlowNode.getGraph(g).flatMap(_.getNode(n)))).flatMap(_.ports)
        }
        case ResultType.Error => {
          if (hasPath && resPortError.isEmpty)
            resPortError = resPaths.foldLeft(imapEmpty[ResourceUri, ISet[ResourceUri]])((c, n) =>
              portErrorsUnion(c, n.getPortErrors))
          resPorts = resPortError.keySet
        }
        case _ =>
      }
      resPorts
    } else {
      if (hasPath && resPorts.isEmpty) {
        resPorts = resPaths.foldLeft(isetEmpty[ResourceUri])((c, n) =>
          c union n.getPorts)
      }
      resPorts
    }
  }

  override def getPortErrors: IMap[ResourceUri, ISet[ResourceUri]] = {
    resType match {
      case Some(ResultType.Node) => {
        if (hasPath && resNodes.isEmpty) {
          resNodes = resPaths.flatMap(_.getNodes).map(_.getUri)
        }
        val resPortError = graphs.flatMap(g => resNodes.flatMap(n =>
          if (FlowNode.getGraph(g).flatMap(_.getNode(n)).isDefined)
            FlowNode.getGraph(g).flatMap(_.getNode(n)).get.ports.map(p => (p, FlowNode.getGraph(g).flatMap(_.getNode(n)).get.getPropagation(p)))
          else imapEmpty[ResourceUri, ISet[ResourceUri]]
        ))
        resPortError.toMap
      }
      case Some(ResultType.Port) => {
        if (hasPath && resPortError.isEmpty) {
          resPorts = resPaths.foldLeft(isetEmpty[ResourceUri])((c, n) =>
            c union n.getPorts)
        }
        resPortError = graphs.flatMap(
            g =>
              resPorts.map(
                p =>
                  (
                    p,
                    if (FlowNode.getGraph(g).flatMap(_.getNode(p)).isDefined) FlowNode.getGraph(g).flatMap(_.getNode(p)).get.getPropagation(p)
          else
            isetEmpty[ResourceUri]
        ))).toMap

        resPortError
      }
      case Some(ResultType.Error) => {
        if (hasPath && resPortError.isEmpty)
          resPortError = resPaths.foldLeft(imapEmpty[ResourceUri, ISet[ResourceUri]])((c, n) =>
            portErrorsUnion(c, n.getPortErrors))
        resPortError
      }
      case _ => resPortError
    }
  }

  override def getFlows: ISet[ResourceUri] = {
    if (hasPath && resFlows.isEmpty) {
      resFlows = resPaths.foldLeft(isetEmpty[ResourceUri])((c, n) => c union n.getFlows)
    }
    resFlows
  }

  override def getBehavior: ISet[ResourceUri] = {
    if (hasPath && resBehav.isEmpty) {
      resBehav = resPaths.foldLeft(isetEmpty[ResourceUri])((c, n) => c union n.getBehavior)
    }
    resBehav
  }

  override def getModes: ISet[ResourceUri] = {
    if (hasPath && resMode.isEmpty) {
      resMode = resPaths.foldLeft(isetEmpty[ResourceUri])((c, n) => c union n.getModes)
    }
    resMode
  }

  override def getEvents: ISet[ResourceUri] = {
    if (hasPath && resEvents.isEmpty) {
      resEvents = resPaths.foldLeft(isetEmpty[ResourceUri])((c, n) => c union n.getEvents)
    }
    resEvents
  }

  override def getErrors: ISet[Tag] = {
    if (hasPath && error.isEmpty) {
      error = resPaths.foldLeft(isetEmpty[Tag])((c, n) => c union n.getErrors)
    }
    error.filter(_.isInstanceOf[ErrorTag])
  }

  override def getWarnings: ISet[Tag] = {
    if (hasPath && error.isEmpty) {
      error = resPaths.foldLeft(isetEmpty[Tag])((c, n) => c union n.getErrors)
    }
    error.filter(_.isInstanceOf[WarningTag])
  }

  override def hasErrors: Boolean = getErrors.nonEmpty

  override def getPaths: ILinkedSet[Collector] = resPaths

  def union(c: Collector): Collector = {
    if (c.getGraphs.isEmpty) {
      this
    } else if (symbolTable == c.getSymbolTable) {
      val resT = if (resType.isDefined && c.getResultType.isDefined) {
        if (resType.get < c.getResultType.get) resType else c.getResultType
      } else if (resType.isDefined) {
        resType
      } else {
        c.getResultType
      }
      Collector(
        symbolTable,
        getGraphs ++ c.getGraphs,
        resT,
        getEdges union c.getEdges,
        Some(Operator.Union),
        criteria union c.getCriteria,
        (getNodes union c.getNodes).map(_.getUri),
        if (resT.isDefined && resT.get >= ResultType.Port) getPorts union c.getPorts else isetEmpty,
        if (resT.isDefined && resT.get >= ResultType.Error) portErrorsUnion(getPortErrors, c.getPortErrors) else imapEmpty,
        if(resT.isDefined && resT.get >= ResultType.Port)getFlows union c.getFlows else isetEmpty,
        getModes union c.getModes,
        getBehavior union c.getBehavior,
        getEvents union c.getEvents,
        ilinkedSetEmpty ++ getPaths union c.getPaths,
        c.hasCycles || hasCycles,
        getErrors ++ getWarnings union c.getErrors ++ c.getWarnings)
    } else {
      //future allow collector to collect across systems
      Collector(
        symbolTable,
        getGraphs,
        isetEmpty [Tag] +
        CollectorErrorHelper.errorMessageGen(
          CollectorErrorHelper.GRAPH_INCONSISTENT,
          "", CollectorErrorHelper.ReachAnalysisStage.Other)
      )
    }
  }

  def diff(c: Collector): Collector = {
    if (symbolTable == c.getSymbolTable) {
      val resT = if (resType.isDefined && c.getResultType.isDefined) {
        if (resType.get < c.getResultType.get) resType else c.getResultType
      } else if (resType.isDefined) {
        resType
      } else {
        c.getResultType
      }
      Collector(
        symbolTable,
        getGraphs diff c.getGraphs,
        resT,
        getEdges diff c.getEdges,
        Some(Operator.Difference),
        criteria union c.getCriteria,
        (getNodes diff c.getNodes).map(_.getUri),
        if (resT.isDefined && resT.get >= ResultType.Port) getPorts diff c.getPorts else isetEmpty,
        if (resT.isDefined && resT.get >= ResultType.Error) diffErrors(getPortErrors, c.getPortErrors) else imapEmpty,
        if (resT.isDefined && resT.get >= ResultType.Port) getFlows diff c.getFlows else isetEmpty,
        getModes diff c.getModes,
        getBehavior diff c.getBehavior,
        getEvents diff c.getEvents,
        getPaths diff c.getPaths,
        hasCycles,
        getErrors ++ getWarnings union c.getErrors ++ c.getWarnings)
    } else {
      //future allow collector to collect across systems
      Collector(
        symbolTable,
        getGraphs,
        isetEmpty[Tag] +
        CollectorErrorHelper.errorMessageGen(
          CollectorErrorHelper.GRAPH_INCONSISTENT, "", CollectorErrorHelper.ReachAnalysisStage.Other)
      )
    }
  }

  def intersect(c: Collector): Collector = {
    if (symbolTable == c.getSymbolTable) {
      val resT = if (resType.isDefined && c.getResultType.isDefined) {
        if (resType.get < c.getResultType.get) resType else c.getResultType
      } else if (resType.isDefined) {
        resType
      } else {
        c.getResultType
      }
      Collector(
        symbolTable,
        getGraphs intersect c.getGraphs,
        resT,
        getEdges intersect c.getEdges,
        Some(Operator.Intersection),
        criteria union c.getCriteria,
        (getNodes intersect c.getNodes).map(_.getUri),
        if (resT.isDefined && resT.get >= ResultType.Port) getPorts intersect c.getPorts else isetEmpty,
        if (resT.isDefined && resT.get >= ResultType.Error) intersectErrors(getPortErrors, c.getPortErrors) else imapEmpty,
        if (resT.isDefined && resT.get >= ResultType.Port) getFlows intersect c.getFlows else isetEmpty,
        getModes intersect c.getModes,
        getBehavior intersect c.getBehavior,
        getEvents intersect c.getEvents,
        getPaths intersect c.getPaths,
        hasCycles && c.hasCycles,
        getErrors ++ getWarnings union c.getErrors ++ c.getWarnings)
    } else {
      //future allow collector to collect across systems
      Collector(
        symbolTable,
        getGraphs,
        isetEmpty[Tag] +
        CollectorErrorHelper.errorMessageGen(
          CollectorErrorHelper.GRAPH_INCONSISTENT,
          "", CollectorErrorHelper.ReachAnalysisStage.Other))
    }
  }

  private def portErrorsUnion(p1: IMap[ResourceUri, ISet[ResourceUri]],
                              p2: IMap[ResourceUri, ISet[ResourceUri]])
  : IMap[ResourceUri, ISet[ResourceUri]] = {
    var res = imapEmpty[ResourceUri, ISet[ResourceUri]]
    res = p1
    p2.foreach { it =>
      if (res.contains(it._1)) {
        res += (it._1 -> (res(it._1) union it._2))
      } else {
        res += it
      }
    }
    res
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

  private def diffErrors(op1: IMap[ResourceUri, ISet[ResourceUri]],
                         op2: IMap[ResourceUri, ISet[ResourceUri]]):
  IMap[ResourceUri, Set[ResourceUri]] = {
    var result = imapEmpty[ResourceUri, ISet[ResourceUri]]
    val ports = op1.keySet.diff(op2.keySet)
    ports.foreach { p =>
      result = result + ((p, op1(p).intersect(op2.getOrElse(p, isetEmpty[ResourceUri]))))
    }
    result
  }

  override def getNextNode(currentNode: FlowNode): ISet[FlowNode] = {
    var res = isetEmpty[FlowNode]
    ErrorReachability(symbolTable).getSuccessor(currentNode).intersect(getNodes)
  }

  override def getNextPort(currentPort: ResourceUri): ISet[ResourceUri] = {
    ErrorReachability(symbolTable).getSuccessor(currentPort).intersect(getPorts)
  }

  override def getNextPortError(currentPort: ResourceUri,
                                currentError: ResourceUri)
  : IMap[ResourceUri, ISet[ResourceUri]] = {
    intersectErrors(ErrorReachability(symbolTable).getSuccessor(currentPort, currentError),
      getPortErrors)
  }
  override def hasCycles: Boolean = containsCycle
}



//object Tag {
//
//}

object ResultType extends Enumeration {
  type ResultType = Value
  val Node, Port, Error, Flow = Value

  implicit val rw: RW[ResultType] = upickle.default.readwriter[Int].bimap[ResultType](resultType => resultType.id, id => {
//      val Array(i, s) = str.split(" ", 2)
    ResultType(id)
  })
}

object Operator extends Enumeration {
  type Operator = Value
  val Forward, Backward, Path, Union, Intersection, Difference, ID = Value

  implicit val rw: RW[Operator] = upickle.default.readwriter[Int].bimap[Operator](operator => operator.id, id => {
    //      val Array(i, s) = str.split(" ", 2)
    Operator(id)
  })
}

case class FlowCollector(graph: ISet[FlowGraph[FlowNode, Edge]],
                         ports: ISet[ResourceUri],
                         //  isInterNodeEdge : Boolean,
                         edges: ISet[Edge],
                         flows: ISet[ResourceUri],
                         errors: ISet[Tag]) {
  def union(fc: FlowCollector): FlowCollector = {
    FlowCollector(graph ++ fc.graph,
      ports ++ fc.ports,
      edges ++ fc.edges,
      flows ++ fc.flows,
      errors ++ fc.errors)
  }
}

case class FlowErrorNextCollector(tuples: ISet[(ResourceUri, ResourceUri)],
                                  edges: ISet[Edge],
                                  flows: ISet[ResourceUri],
                                  errors: ISet[Tag],
                                  graph: ISet[FlowGraph[FlowNode, Edge]]) {
  def union(fenc: FlowErrorNextCollector): FlowErrorNextCollector = {
    FlowErrorNextCollector(tuples ++ fenc.tuples,
      edges ++ fenc.edges,
      flows ++ fenc.flows,
      errors ++ fenc.errors,
      graph ++ fenc.graph
    )
  }
}

case class FlowErrorPathCollector(path: ISeq[(ResourceUri, ResourceUri)],
                                  edges: ISet[Edge],
                                  flows: ISet[ResourceUri],
                                  errors: ISet[Tag],
                                  graphs: ISet[FlowGraph[FlowNode, Edge]]) {
  def union(fepc: FlowErrorPathCollector): FlowErrorPathCollector = {
    FlowErrorPathCollector((path.toSet ++ fepc.path.toSet).toVector, edges ++ fepc.edges,
      flows ++ fepc.flows, errors ++ fepc.errors, graphs ++ fepc.graphs)
  }
}


