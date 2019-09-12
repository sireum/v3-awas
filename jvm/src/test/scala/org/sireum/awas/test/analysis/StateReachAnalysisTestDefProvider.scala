package org.sireum.awas.test.analysis

import java.nio.file.Paths

import org.sireum.awas.analysis.StateReachAnalysis
import org.sireum.awas.ast.Builder
import org.sireum.awas.codegen.ContextInSensitiveGen
import org.sireum.awas.fptc.FlowGraph
import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.awas.util.TestUtils.extensor
import org.sireum.test.{EqualTest, TestDef, TestDefProvider, TestFramework}
import org.sireum.util._
import org.sireum.util.jvm.FileUtil.{fileUri, filename, listFiles, readFile, toFilePath, toUri, writeFile}

import scala.io.Source

final class StateReachAnalysisTestDefProvider(tf: TestFramework) extends TestDefProvider {
  val testDirs = Seq(s"../example/awas-lang", s"../example/fptc")
  val resultsDir = toFilePath(fileUri(this.getClass, s"../results/fptc"))
  val expectedDir = toFilePath(fileUri(this.getClass, s"../expected/fptc"))

  val generateExpected = true

  override def testDefs: ISeq[TestDef] = {
    val files = testDirs.flatMap { d =>
      listFiles(fileUri(this.getClass, d), "awas")
    }
    val filesEqual = files.filter { p =>
      //      p.toLowerCase.contains("pcashutoff") ||
      //        p.toLowerCase.contains("isolette") ||
      //        p.toLowerCase.contains("abcloop")  ||
      p.toLowerCase.contains("fptc_base")
    }

    filesEqual.toVector.map { x =>
      val inputFileName = filename(x)
      val fileWithOutExt = extensor(inputFileName).toString
      val queryFile = extensor(toFilePath(x)) + ".aq"
      val outputFileName = fileWithOutExt + ".st"
      writeResult(outputFileName, graphAnalysis(x, readFile(x)._1, queryFile))
      val result = readFile(toUri(resultsDir + "/" + outputFileName))._1
      EqualTest(filename(x), result, readFile(toUri(expectedDir + "/" + outputFileName))._1)
    }
  }

  def writeResult(fileName: String, content: String) = {
    if (generateExpected) {
      val expPath = expectedDir + "/" + fileName
      writeFile(toUri(expPath), content)
    }
    val resPath = resultsDir + "/" + fileName
    writeFile(toUri(resPath), content)
  }

  def graphAnalysis(infileUri: FileResourceUri, model: String, name: String): String = {
    import org.sireum.util.jvm.FileUtil._
    val basePath = Paths.get(fileUri(this.getClass, s"../"))
    val relativeUri = basePath.relativize(Paths.get(infileUri))
    var result = ""
    Builder(Some(relativeUri.toString), model) match {
      case None => ""
      case Some(m) =>
        implicit val reporter: AccumulatingTagReporter = new ConsoleTagReporter
        var st = SymbolTable(m)
//        val updatedModel = ContextInSensitiveGen(m, st)
//        Resource.reset
//        st = SymbolTable(updatedModel)
val graph = FlowGraph(m, st, false)
        val lines = Source.fromFile(name).getLines
        lines.foreach { line =>
          val state = line2state(line, st)
          val res = StateReachAnalysis.getReachability(state, st)
          //          val fg = FPTCAnalysis(graph.getUri, st)
          //          fg.toList.sortBy(_._1).map(it =>
          //            SymbolTableHelper.uri2IdString(it._1) + "\n" + fptcNodeWriter(it._2, st)).mkString("\n")
          result = result + "\n" + res.toString + "\n States : \n" + res.getModes.mkString("\n") + "\n Behaviors: \n" + res.getBehavior
            .mkString("\n")

        }
        result
    }
  }

  def line2state(text: String, st: SymbolTable): IMap[ResourceUri, ISet[ResourceUri]] = {
    val portName = text.split("\\[")
    val H = SymbolTableHelper
    val portUri = H.getPortUri(st, portName.head)
    val errors = text.split("\\[").tail.head.dropRight(1).split(",")
    val errorsUri = errors.map(H.getErrorUri(st, _).get)

    imapEmpty[ResourceUri, ISet[ResourceUri]] + (portUri.get -> errorsUri.toSet)
  }
}
