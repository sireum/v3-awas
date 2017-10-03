package org.sireum.awas.collector

import org.sireum.awas.collector.Operator.Operator
import org.sireum.awas.collector.ResultType.ResultType
import org.sireum.awas.fptc.FlowNode.Edge
import org.sireum.awas.fptc.{FlowEdge, FlowGraph, FlowNode}
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.util.{IMap, _}

//In future, even graph and symboltable may become maps where each graph points to a Collector
case class CollectorImpl(symbolTable: SymbolTable,
                         graph: FlowGraph[FlowNode],
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
                         resPaths: ISeq[Collector] = ivectorEmpty[Collector],
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
          resNodes = resPaths.foldLeft(isetEmpty[ResourceUri])((c, n) =>
            c union n.getNodes.map(_.getUri))
        }
        resNodes.flatMap(it => graph.getNode(it))
      case Some(ResultType.Port) =>
        if (hasPath && resPorts.isEmpty) {
          resPorts = resPaths.foldLeft(isetEmpty[ResourceUri])((c, n) =>
            c union n.getPorts)
        }
        resPorts.flatMap(it => graph.getNode(it))
      case Some(ResultType.Error) =>
        if (hasPath && resPortError.isEmpty) {
          resPortError = resPaths.foldLeft(imapEmpty[ResourceUri, ISet[ResourceUri]])((c, n) =>
            portErrorsUnion(c, n.getPortErrors))
        }
        resPortError.keySet.flatMap(it => graph.getNode(it))
      case _ =>
        resNodes.flatMap(it => graph.getNode(it))
    }
  }

  override def getEdges: ISet[FlowEdge[FlowNode]] = {
    if (hasPath && resEdges.isEmpty)
      resEdges = resPaths.foldLeft(isetEmpty[Edge])((c, n) => c union n.getEdges)
    resEdges
  }

  override def getSymbolTable: SymbolTable = symbolTable

  override def getGraph: FlowGraph[FlowNode] = graph

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
          resPorts = resNodes.flatMap(graph.getNode).flatMap(_.ports)
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
          resNodes = resPaths.foldLeft(isetEmpty[ResourceUri])((c, n) =>
            c union n.getNodes.map(_.getUri))
        }
        resPortError = resNodes.flatMap(graph.getNode).flatMap(_.ports).map(it =>
          (it, if (graph.getNode(it).isDefined) graph.getNode(it).get.getPropagation(it) else
            isetEmpty[ResourceUri])).toMap
        resPortError
      }
      case Some(ResultType.Port) => {
        if (hasPath && resPortError.isEmpty) {
          resPorts = resPaths.foldLeft(isetEmpty[ResourceUri])((c, n) =>
            c union n.getPorts)
        }
        resPortError = resPorts.map(it => (it,
          if (graph.getNode(it).isDefined) graph.getNode(it).get.getPropagation(it) else
            isetEmpty[ResourceUri])).toMap
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

  override def getPaths: ISeq[Collector] = resPaths

  def union(c: Collector): Collector = {
    if (symbolTable == c.getSymbolTable && graph == c.getGraph) {
      Collector(
        symbolTable,
        graph,
        if (resType.isDefined && c.getResultType.isDefined) {
          if (resType.get < c.getResultType.get) resType else c.getResultType
        } else if (resType.isDefined) {
          resType
        } else {
          c.getResultType
        }
        ,
        getEdges union c.getEdges,
        Some(Operator.Union),
        criteria union c.getCriteria,
        (getNodes union c.getNodes).map(_.getUri),
        getPorts union c.getPorts,
        portErrorsUnion(getPortErrors, c.getPortErrors),
        getFlows union c.getFlows,
        getModes union c.getModes,
        getBehavior union c.getBehavior,
        getEvents union c.getEvents,
        getPaths union c.getPaths,
        getErrors ++ getWarnings union c.getErrors ++ c.getWarnings)
    } else {
      //future allow collector to collect across systems
      Collector(symbolTable, graph, isetEmpty[Tag] +
        CollectorErrorHelper.errorMessageGen(
          CollectorErrorHelper.GRAPH_INCONSISTENT,
          "", CollectorErrorHelper.ReachAnalysisStage.Other))
    }
  }

  def diff(c: Collector): Collector = {
    if (symbolTable == c.getSymbolTable && graph == c.getGraph) {
      Collector(
        symbolTable,
        graph,
        if (resType.isDefined && c.getResultType.isDefined) {
          if (resType.get < c.getResultType.get) resType else c.getResultType
        } else if (resType.isDefined) {
          resType
        } else {
          c.getResultType
        },
        getEdges diff c.getEdges,
        Some(Operator.Difference),
        criteria union c.getCriteria,
        (getNodes diff c.getNodes).map(_.getUri),
        getPorts diff c.getPorts,
        diffErrors(getPortErrors, c.getPortErrors),
        getFlows diff c.getFlows,
        getModes diff c.getModes,
        getBehavior diff c.getBehavior,
        getEvents diff c.getEvents,
        getPaths diff c.getPaths,
        getErrors ++ getWarnings union c.getErrors ++ c.getWarnings)
    } else {
      //future allow collector to collect across systems
      Collector(symbolTable, graph, isetEmpty[Tag] +
        CollectorErrorHelper.errorMessageGen(
          CollectorErrorHelper.GRAPH_INCONSISTENT,
          "", CollectorErrorHelper.ReachAnalysisStage.Other))
    }
  }

  def intersect(c: Collector): Collector = {
    if (symbolTable == c.getSymbolTable && graph == c.getGraph) {
      Collector(
        symbolTable,
        graph,
        if (resType.isDefined && c.getResultType.isDefined) {
          if (resType.get < c.getResultType.get) resType else c.getResultType
        } else if (resType.isDefined) {
          resType
        } else {
          c.getResultType
        },
        getEdges intersect c.getEdges,
        Some(Operator.Intersection),
        criteria union c.getCriteria,
        (getNodes intersect c.getNodes).map(_.getUri),
        getPorts intersect c.getPorts,
        intersectErrors(getPortErrors, c.getPortErrors),
        getFlows intersect c.getFlows,
        getModes intersect c.getModes,
        getBehavior intersect c.getBehavior,
        getEvents intersect c.getEvents,
        getPaths intersect c.getPaths,
        getErrors ++ getWarnings union c.getErrors ++ c.getWarnings)

    } else {
      //future allow collector to collect across systems
      Collector(symbolTable, graph, isetEmpty[Tag] +
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

}

case class FlowCollector(ports: ISet[ResourceUri],
                         //  isInterNodeEdge : Boolean,
                         edges: ISet[Edge],
                         flows: ISet[ResourceUri],
                         errors: ISet[Tag])

case class FlowErrorNextCollector(tuples: ISet[(ResourceUri, ResourceUri)],
                                  edges: ISet[Edge],
                                  flows: ISet[ResourceUri],
                                  errors: ISet[Tag])

case class FlowErrorPathCollector(path: ISeq[(ResourceUri, ResourceUri)],
                                  edges: ISet[Edge],
                                  flows: ISet[ResourceUri],
                                  errors: ISet[Tag])

