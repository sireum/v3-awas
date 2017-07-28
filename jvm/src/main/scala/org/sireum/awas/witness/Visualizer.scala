package org.sireum.awas.witness

import java.nio.file.Paths

import org.sireum.awas.ast.Builder
import org.sireum.awas.fptc.FlowGraph
import org.sireum.awas.query.{QRes, QueryBuilder, QueryEval, QueryPPrinter}
import org.sireum.awas.symbol.SymbolTable
import org.sireum.util._
import org.sireum.util.jvm.FileUtil
import org.sireum.util.jvm.FileUtil.{fileUri, readFile}

object Visualizer {
  def main(args: Array[String]): Unit = {
    val modelFile = "awas/jvm/src/test/resources/org/sireum/awas/test/example/Query/abcEF.awas"
    val outputPath = "awas/js/target/scala-2.12/classes/min/graphVar.js"
    val queryFile = "awas/jvm/src/test/resources/org/sireum/awas/test/example/Query/abcEF.aq"
    val basePath = Paths.get(fileUri(this.getClass, s".."))
    val relativeUri = basePath.relativize(Paths.get(modelFile))
    var result = ""

    Builder(Some(relativeUri.toString), readFile(FileUtil.toUri(modelFile))._1) match {
      case None =>
      case Some(m) =>
        implicit val reporter: AccumulatingTagReporter = new ConsoleTagReporter
        val st = SymbolTable(m)
        val graph = FlowGraph(m, st)
        val graphVar = "var graph = `" + SvgGenerator(graph) + "`;\n"
        result = result + graphVar
        QueryBuilder(readFile(FileUtil.toUri(queryFile))._1) match {
          case None =>
          case Some(q) =>
            val queryExp = q.queryStmt.map(it =>
              (QueryPPrinter(it.qName), QueryPPrinter(it.qExpr))).foldLeft(ilistEmpty[String]) {
              (r: IList[String], p: (String, String)) =>
                r :+ "\"" + p._1 + "\":" + "\"" + p._2 + "\""
            }
            result = result + "var queryExp = {" + queryExp.mkString(", ") + "};\n"
            val queryRes = QueryEval(q, graph, st).map { it =>
              "\"" + it._1 + "\":" + "[" + it._2.asInstanceOf[QRes].toPorts.map("\"" + _ + "\"").mkString(", ") + "]"
            }
            result = result + "var queryRes = {" + queryRes.mkString(",") + "};"
        }
    }

    FileUtil.writeFile(FileUtil.toUri(outputPath), result)

  }
}
