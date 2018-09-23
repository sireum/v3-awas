package org.sireum.awas.analysis
import org.sireum.awas.ast.Model
import org.sireum.awas.collector.Collector
import org.sireum.awas.fptc.{FlowGraph, FlowNode}
import org.sireum.awas.query.{PathExpr, QueryEval, QueryParser, SimplePathExpr}
import org.sireum.awas.reachability.ErrorReachability
import org.sireum.awas.symbol.{FlowTableData, Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.awas.witness.SvgGenerator.Edge
import org.sireum.message.Reporter
import org.sireum.util.{ISet, _}

//trait FaultImpactAnalysis {
//  def computeFIA : FIAResult
//}
//
//case class FIAResult (sources : IList[(ResourceUri, ResourceUri)],
//                      rows : IList[FIARow]
//
//                     )
//
//case class FIARow(source : (ResourceUri, ResourceUri),
//                  path : Collector,
//                  resPortError : IList[(ResourceUri, ResourceUri)]) {
//  val H = SymbolTableHelper
//
//  var row = ivectorEmpty[String]
//
//  def computeRow = {
//    val head =resPortError.head
//
//    val initalComp = Resource.getParentUri(head._1)
//
//    if(initalComp.isDefined) {
//      H.getCompId(path.getSymbolTable, initalComp.get)
//
//
//
//    }
//  }
//
//  def pathLength : Int = {
//    resPortError.size
//  }



class FaultImpactAnalysis {
  val H = SymbolTableHelper

  def generateFIAQueries(model : Model, isSource : Boolean): String = {

    implicit val reporter: AccumulatingTagReporter = new ConsoleTagReporter
    val st = SymbolTable(model)
    val graph = FlowGraph(model, st)

//    if(reporter.hasError) {
//      throw new Exception(reporter.tags.map(TagPickling.pickle).mkString("\n"));
//    }

    generateFIAQueries(st, graph, isSource)
  }

  def generateFIAQueries(st : SymbolTable,
                         graph : FlowGraph[FlowNode, Edge],
                         isSource : Boolean): String = {
    val er = ErrorReachability(st)

    if(isSource) {
      genSourceFIAQueries(graph, er)
    } else {
      genSinkFIAQueries(graph, er)
    }
  }

  def computeFIA(model : Model): String = {
    var result_in = ilistEmpty[IList[String]]
    var result_ex = ilistEmpty[IList[String]]
    implicit val reporter: AccumulatingTagReporter = new ConsoleTagReporter
    val st = SymbolTable(model)
    val graph = FlowGraph(model, st)
    val queries = generateFIAQueries(st, graph, isSource = true).split("\n")

    queries.foreach{ qry =>
      implicit val reporter: Reporter = new Reporter(org.sireum.ISZ())
      QueryParser(qry, reporter) match {
        case None => ""
        case Some(q) => {
          q.queryStmt.last.qExpr match {
            case pe : SimplePathExpr => {
              val source = QueryEval(st , pe.source)
              val target = QueryEval(st, pe.target)
              val qres = QueryEval(q, st).values
              if(qry.contains("__Internal")) {
                qres
                  .flatMap(_.getPaths)
                  .foreach(q => result_in = result_in :+ printFIARow(st, source, target, q, qry.contains("__Internal")))
              } else {
                qres
                  .flatMap(_.getPaths)
                  .foreach(q => result_ex = result_ex :+ printFIARow(st, source, target, q, qry.contains("__Internal")))
              }
            }
            case _ =>
          }
        }
      }
    }

    val y = result_in.map(in => in.mkString(",")).mkString("\n")
    val z = result_ex.map(in => in.mkString(",")).mkString("\n")
    "Fault Impact of System Internal Error Sources\n\n"+FIAHeader(result_in, true)+
      "\n"+y+"\n\nFault Impact of System External Error Sources \n\n"+
      FIAHeader(result_ex, false) + "\n"+z
  }

  def FIAHeader(table : IList[IList[String]], isInternal : Boolean) : String = {
    var result = ilistEmpty[String]
    if(isInternal) {
      result = result :+ "Component" :+ " Initial Failure Mode"
    } else {
      result =result :+ "Root System" :+ "External Error Source"
    }
    if(table.nonEmpty) {
    val max = (table.map(_.size).max) / 2
    for (i <- 0 to max) {
      result = result :+ (i.toString + " Level Effect") :+ "Failure Mode"
    }

      result.mkString(",")
    }else {""}
  }

  def printFIARow(st : SymbolTable, source : Collector, target : Collector,
                  resColl : Collector, isInternal : Boolean) : IList[String] = {
    var result = ilistEmpty[String]

      val nodeUri = Resource.getParentUri(source.getPortErrors.keySet.head).get

      val srcPort = source.getPortErrors.keySet.head
      val srcErr = source.getPortErrors(srcPort).head
    if(isInternal) {
      result = result :+ getIdFromURI(nodeUri)
      result = result :+ "{" + srcErr.split(H.ID_SEPARATOR).last + "}"
    }else {
      result = result :+ H.getCompId(st, nodeUri).get
      result = result :+ getIdFromURI(srcPort)+" {"+getIdFromURI(srcErr)+"}"
    }
      var curr = (srcPort, srcErr)
      var next = resColl.getNextPortError(srcPort, srcErr)

      while(next.nonEmpty) {
        val nport = next.keySet.head
        val nerr = next(nport).head

        next = imapEmpty
        if(curr != (nport, nerr)) {
          val node = Resource.getParentUri(nport)
          if (node.isDefined) {
            if (H.getUriType(node.get) == H.COMPONENT_TYPE) {
              result = result :+ "{" + getIdFromURI(curr._2) + "} " + getIdFromURI(curr._1) + " -> " +
                getIdFromURI(node.get) + ":" + getIdFromURI(nport)

              result = result :+ getIdFromURI(node.get) + " {" + getIdFromURI(nerr) + "}"

              val tempCur = resColl.getNextPortError(nport, nerr)
              if(tempCur.nonEmpty) {
                curr = (tempCur.keySet.head, tempCur(tempCur.keySet.head).head)

                next = resColl.getNextPortError(curr._1, curr._2)
              }else {
                next = imapEmpty
              }
            } else {
              next = resColl.getNextPortError(nport, nerr)
            }
          }
        }
      }

    result
  }

  def getIdFromURI(uri : ResourceUri) : String = {
    uri.split(H.ID_SEPARATOR).last
  }

  def genSourceFIAQueries(graph : FlowGraph[FlowNode, Edge], er : ErrorReachability[FlowNode]) : String = {
    var result = ilistEmpty[String]
    val nodes = FlowNode.getGraphs.flatMap(_.nodes)
    val sources = nodes.flatMap(_.getFlows.toList).filter(f =>
      f._2.fromPortUri.isEmpty && f._2.fromFaults.isEmpty)
    val srcPE = sources.flatMap(f => flowTable2PortError(f._2))

    srcPE.foreach{spe =>
      val resCollector = er.forwardErrorReach(spe._1, isetEmpty + spe._2)
      var npes = ilistEmpty[(ResourceUri, ResourceUri)]
      npes ++= flows2PortError(resCollector, isSource = false)
      npes ++= collector2PortError(resCollector, isSource = false)
      var i = 0
      npes.foreach { npe =>
        i=i+1
        result = result :+ buildQuery(
          portError2name(spe._1, spe._2) +"__Internal"+ (if(npes.size>1) {"__"+i.toString} else {""}),
          portError2Criteria(spe._1, spe._2),
          portError2Criteria(npe._1, npe._2)
        )
      }
    }

    val nodePE = graph.getInPortNodes.flatMap(n => n.getPropagation(n.getUri).map((n.getUri, _)))
    nodePE.foreach { nspe =>
      val resCollector = er.forwardErrorReach(nspe._1, isetEmpty + nspe._2)
      var npes = ilistEmpty[(ResourceUri, ResourceUri)]
      npes ++= flows2PortError(resCollector, isSource = false)
      npes ++= collector2PortError(resCollector, isSource = false)
      var i = 0
      npes.foreach { npe =>
        i = i + 1
        result = result :+ buildQuery(
          portError2name(nspe._1, nspe._2) + "__External" + (if (npes.size > 1) { "__" + i.toString } else { "" }),
          portError2Criteria(nspe._1, nspe._2),
          portError2Criteria(npe._1, npe._2)
        )
      }
    }
    result.sorted.mkString("\n")
  }

  def genSinkFIAQueries(graph : FlowGraph[FlowNode, Edge], er : ErrorReachability[FlowNode]) : String = {
    var result = ilistEmpty[String]
    val nodes = FlowNode.getGraphs.flatMap(_.nodes)
    val sinks = nodes.flatMap(_.getFlows.toList).filter(f =>
      f._2.toPortUri.isEmpty && f._2.toFaults.isEmpty)
    val srcPE = sinks.flatMap(f => flowTable2PortError(f._2))

    srcPE.foreach{spe =>
      val resCollector = er.backwardErrorReach(spe._1, isetEmpty + spe._2)
      var npes = ilistEmpty[(ResourceUri, ResourceUri)]
      npes ++= flows2PortError(resCollector, isSource = true)
      npes ++= collector2PortError(resCollector, isSource = true)
      var i = 0
      npes.foreach { npe =>
        i=i+1
        result = result :+ buildQuery(
          portError2name(spe._1, spe._2) +"__Internal"+ (if(npes.size>1) {"__"+i.toString} else {""}),
          portError2Criteria(npe._1, npe._2), portError2Criteria(spe._1, spe._2)
        )
      }
    }

    val nodePE = graph.getOutPortNodes.flatMap(n => n.getPropagation(n.getUri).map((n.getUri, _)))
    nodePE.foreach { nspe =>
      val resCollector = er.backwardErrorReach(nspe._1, isetEmpty + nspe._2)
      var npes = ilistEmpty[(ResourceUri, ResourceUri)]
      npes ++= flows2PortError(resCollector, isSource = true)
      npes ++= collector2PortError(resCollector, isSource = true)
      var i = 0
      npes.foreach { npe =>
        i = i + 1
        result = result :+ buildQuery(
          portError2name(nspe._1, nspe._2) + "__External" + (if (npes.size > 1) { "__" + i.toString } else { "" }),
          portError2Criteria(npe._1, npe._2), portError2Criteria(nspe._1, nspe._2)
        )
      }
    }
    result.sorted.mkString("\n")
  }

  private def collector2PortError(collector : Collector, isSource : Boolean)
  :ISet[(ResourceUri, ResourceUri)] = {
    val ports = collector.getPortErrors.keySet.filter(p =>
      if(isSource){H.isInPort(p)} else {H.isOutPort(p)}) -- collector.getEdges.flatMap(e =>
      if(isSource) {e.targetPort} else {e.sourcePort})
    collector.getPortErrors.filter(pe => ports.contains(pe._1)).flatMap(pe => pe._2.map((pe._1, _))).toSet
  }

  private def flowTable2PortError(flow : FlowTableData) : ISet[(ResourceUri, ResourceUri)] = {
    val port = if(flow.fromPortUri.isDefined) flow.fromPortUri.get else flow.toPortUri.get
    val errors = if(flow.fromPortUri.isDefined) flow.fromFaults else flow.toFaults
    errors.map(e => (port, e))
  }

  private def flows2PortError(collector : Collector, isSource : Boolean)
  : ISet[(ResourceUri, ResourceUri)] = {
    val flows = collector.getFlows
    var res = isetEmpty[(ResourceUri, ResourceUri)]
    flows.foreach { f =>
      val node = FlowNode.getNode(Resource.getParentUri(f).get).get
      val flow = node.getFlows.get(f)
      if(flow.isDefined) {
        if(isSource) {
          if(flow.get.fromPortUri.isEmpty && flow.get.fromFaults.isEmpty) {
            res ++= flowTable2PortError(flow.get).filter(pe =>
              collector.getPortErrors.contains(pe._1) && collector.getPortErrors(pe._1).contains(pe._2))
          }
        } else {
          if(flow.get.toPortUri.isEmpty && flow.get.toFaults.isEmpty) {
            res ++= flowTable2PortError(flow.get).filter(pe =>
              collector.getPortErrors.contains(pe._1) && collector.getPortErrors(pe._1).contains(pe._2))
          }
        }
      }
    }
    res
  }

  private def portError2name(port : ResourceUri, error : ResourceUri):String = {
    val pR = Resource.getDefResource(port)
    val eR = Resource.getDefResource(error)

    if(pR.isDefined && eR.isDefined) {
      val portName = (pR.get.uriPaths.tail.tail.tail:+pR.get.uri).mkString("_")
      val errorName = eR.get.uriPaths.tail.tail.tail.mkString("_")+"_"+eR.get.uri
      portName+"__"+errorName
    } else {""}
  }

  private def portError2Criteria(port : ResourceUri, error : ResourceUri): String = {
    val pR = Resource.getDefResource(port)
    val eR = Resource.getDefResource(error)

    if (pR.isDefined && eR.isDefined) {
      (pR.get.uriPaths.tail.tail :+ pR.get.uri).mkString(".")+"{"+(eR.get.uriPaths.tail.tail.tail :+ eR.get.uri).mkString(".")+"}"
    } else {
      assert(false, "should never reach here")
      ""
    }
  }

  private def buildQuery(qName : String, src : String, dst : String):String = {
    qName + " = " + "reach simple paths from " + src + " to " + dst
  }

}