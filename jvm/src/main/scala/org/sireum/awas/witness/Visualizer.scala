package org.sireum.awas.witness

import java.io.File
import java.net.URL
import java.nio.file.{Files, Path, Paths}
import java.util.jar.JarFile

import org.sireum.awas.ast.Builder
import org.sireum.awas.collector.{Collector, ResultType}
import org.sireum.awas.fptc._
import org.sireum.awas.query.{QueryBuilder, QueryEval, QueryPPrinter, QueryStmt}
import org.sireum.awas.symbol.SymbolTable
import org.sireum.util._
import org.sireum.util.jvm.FileUtil
import org.sireum.util.jvm.FileUtil._

import scala.collection.JavaConverters._

object Visualizer {
  def main(args: Array[String]): Unit = {
    //    val modelFile = "awas/jvm/src/test/resources/org/sireum/awas/test/example/Query/abcEF.awas"
    //    val modelFile = "awas/jvm/src/test/resources/org/sireum/awas/test/example/bindings/spiral2.awas"
    //    val modelFile = "awas/jvm/src/test/resources/org/sireum/awas/test/example/bindings/spiral3.awas"
    //    val modelFile = "awas/jvm/src/test/resources/org/sireum/awas/test/example/bindings/spiral4.awas"
    //    val outputPath = "awas/js/target/scala-2.12/classes/min/graphVar.js"
    //    val queryFile = "awas/jvm/src/test/resources/org/sireum/awas/test/example/Query/abcEF.aq"
    //    val queryFile = "awas/jvm/src/test/resources/org/sireum/awas/test/example/bindings/spiral2.aq"
    //    val queryFile = "awas/jvm/src/test/resources/org/sireum/awas/test/example/bindings/spiral3.aq"
    //    val queryFile = "awas/jvm/src/test/resources/org/sireum/awas/test/example/bindings/spiral4.aq"
    //    val outputFolder = "/workspace-v3/stash/temp2/"

    val modelFile = args(0)
    val queryFile = args(1)
    val outputFolder = args(2)
    val sireumJarLoc = if (args.length == 4) args(3) else ""
    val resourceLoc = "/org/sireum/web"

    val graphVarPath = "/min/graphVar.js"

    val web = this.getClass.getResource(resourceLoc)

    if (Files.exists(Paths.get(outputFolder))) {
      FileUtil.delete(Paths.get(outputFolder))
    }

    def extractFromJar(jarFile: JarFile) = {
      jarFile.stream().iterator().asScala.foreach { je =>
        if (je.getName.startsWith("org/sireum/web")) {
          val in = jarFile.getInputStream(je)
          val fileName = outputFolder + "/" + je.getName.substring(resourceLoc.length)
          if (je.isDirectory) {
            Files.createDirectories(Paths.get(fileName))
          } else {
            Files.copy(in, Paths.get(fileName))
          }
        }
      }
    }

    def copyWebResources(sourceURL: URL, dstFile: File) = {
      println("entered extracter")
      println("input URL :" + sourceURL.toExternalForm)
      if (sourceURL.getProtocol == "jar") {
        //command line resource access
        val resource = sourceURL.toString.split('!')
        val jarPath = sourceURL.getFile
        val jarFile = new JarFile(toFilePath(jarPath.split('!')(0)))
        extractFromJar(jarFile)
      } else if (sourceURL.getProtocol == "bundleresource") {
        //eclipse plugin resource access
        val jarFile = new JarFile(sireumJarLoc)
        extractFromJar(jarFile)
      } else {
        //IDE resource access
        walkFileTree(Paths.get(web.getPath), test, false)
        //println("Dont come here")
      }
    }

    //    val web =toFilePath(fileUri(this.getClass, "/org/sireum/web"))
    def test(isDir: Boolean, path: Path) = {
      val basePath = Paths.get(web.getPath)
      val tempPath = path.toAbsolutePath
      val relPath = basePath.relativize(tempPath)

      Files.copy(tempPath, Paths.get(outputFolder + relPath.toString))
      //relPath.toString
    }

    println(this.getClass.getResource(resourceLoc))

    copyWebResources(this.getClass.getResource(resourceLoc), new File(outputFolder))

    // walkFileTree(Paths.get(web.getPath), test, false)


    val relativeUri = Paths.get(modelFile)
    var result = ""
    println(modelFile)

    Builder(Some(relativeUri.toString), readFile(FileUtil.toUri(modelFile))._1) match {
      case None =>
      case Some(m) =>
        implicit val reporter: AccumulatingTagReporter = new ConsoleTagReporter
        val st = SymbolTable(m)
        val graph = FlowGraph(m, st)
        val graphVar = "var graph = `" + SvgGenerator(graph.asInstanceOf[FlowGraph[FlowNode, FlowEdge[FlowNode]]
          with FlowGraphUpdate[FlowNode, FlowEdge[FlowNode]]]) + "`;\n"
        result = result + graphVar
        QueryBuilder(readFile(FileUtil.toUri(queryFile))._1) match {
          case None =>
          case Some(q) =>
            val qres = QueryEval(q, graph, st)
            val queryExp = q.queryStmt.flatMap(it => qexpToJSMap(it, qres))

            result = result + "var queryExp = {" + queryExp.mkString(", ") + "};\n"

            val queryRes = qresToJSMap(qres)
            //              "\"" + it._1 + "\":" + "[" + collector2String(it._2).map("\"" + _ + "\"").mkString(", ") + "]"

            result = result + "var queryRes = {" + queryRes.mkString(",") + "};\n"

            val queryCriteria = qresToJSMap_criteria(qres)

            result = result + "var queryCriteria = {" + queryCriteria.mkString(",") + "};"
        }
    }
    FileUtil.writeFile(FileUtil.toUri(outputFolder + graphVarPath), result)
  }

  private def qexpToJSMap(qStmt: QueryStmt, qRes: QueryEval.Result): ISeq[String] = {
    var res = ivectorEmpty[String]
    val qid = QueryPPrinter(qStmt.qName)
    val qexpr = QueryPPrinter(qStmt.qExpr)
    res = res :+ "\"" + qid + "\":" + "\"" + qexpr + "\""
    if (qRes(qid).getPaths.size > 1) {
      for (i <- qRes(qid).getPaths.indices) {
        res = res :+ "\"" + qid + ":Path " + (i + 1) + "\":" + "\"" + qexpr + "\""
      }
    }
    res
  }

  private def qresToJSMap(qRes: QueryEval.Result): ISeq[String] = {
    var res = ivectorEmpty[String]
    qRes.foreach { it =>
      res = res :+ "\"" + it._1 + "\":" + "[" + collector2String(it._2).map("\"" + _ + "\"").mkString(", ") + "]"
      if (it._2.getPaths.size > 1)
        for (i <- it._2.getPaths.indices) {
          res = res :+ "\"" + it._1 + ":Path " + (i + 1) + "\":" + "[" + collector2String(it._2.getPaths(i)).map("\"" + _ + "\"").mkString(", ") + "]"
        }
    }
    res
  }

  private def qresToJSMap_criteria(qRes: QueryEval.Result): ISeq[String] = {
    var res = ivectorEmpty[String]
    qRes.foreach { it =>
      res = res :+ "\"" + it._1 + "\":" + "[" + it._2.getCriteria.map("\"" + _ + "\"").mkString(", ") + "]"
      if (it._2.getPaths.size > 1)
        for (i <- it._2.getPaths.indices) {
          res = res :+ "\"" + it._1 + ":Path " + (i + 1) + "\":" + "[" + it._2.getPaths(i).getCriteria.map("\"" + _ + "\"").mkString(", ") + "]"
        }
    }
    res
  }

  private def collector2Criteria(collector: Collector): ISet[String] = {
    collector.getCriteria
  }

  private def collector2String(collector: Collector): ISet[String] = {
    var res = isetEmpty[String]
    collector.getResultType match {
      case Some(ResultType.Node) =>
        res ++= collector.getNodes.map(_.getUri)
      case Some(ResultType.Port) =>
        res ++= collector.getPorts ++ collector.getFlows
      case Some(ResultType.Error) =>
        res ++= collector.getPortErrors.flatMap(it => it._2.map(e => "Error" + ":" + it._1 + ":" + e) + it._1) ++ collector.getFlows
      case _ =>
    }
    res ++= collector.getEdges.map(it => "Edge+" + it.sourcePort.get + ":" + it.targetPort.get)
    res
  }
}
