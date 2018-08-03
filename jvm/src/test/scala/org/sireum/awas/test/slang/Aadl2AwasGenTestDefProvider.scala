package org.sireum.awas.test.slang

import org.sireum.awas.ast.PrettyPrinter
import org.sireum.awas.fptc.{FlowEdge, FlowGraph, FlowGraphUpdate, FlowNode}
import org.sireum.awas.slang.Aadl2Awas
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.TestUtils._
import org.sireum.awas.witness.SvgGenerator
import org.sireum.test.{EqualTest, TestDef, TestDefProvider, TestFramework}
import org.sireum.util.jvm.FileUtil._
import org.sireum.util.{AccumulatingTagReporter, ConsoleTagReporter, FileResourceUri, ISeq, Uri}

class Aadl2AwasGenTestDefProvider(tf: TestFramework)
  extends TestDefProvider {
  val testDirs = Seq(makePath("..", "example", "aadl-json"))

  val resultsDir: Uri = toFilePath(fileUri(this.getClass, makePath("..", "results", "query")))
  val expectedDir: Uri = toFilePath(fileUri(this.getClass, makePath("..", "expected", "query")))

  val generateExpected = true

  val outputExtension = ".awas"

  override def testDefs: ISeq[TestDef] = {
    val files = testDirs.flatMap { d =>
      listFiles(fileUri(this.getClass, d), ".json")
    }

    //equals test by excluding some
    val filesEqual = files.filter { p =>
      p.toLowerCase.contains("secure")
    }

    filesEqual.toVector.map { x =>
      val inputFileName = filename(x)
      println(inputFileName)
      val fileWithOutExt = extensor(inputFileName).toString

      val outputFileName = fileWithOutExt + outputExtension

      writeResult(outputFileName,
        translateAndParse(x, readFile(x)._1), expectedDir, resultsDir, generateExpected)

      EqualTest(fileWithOutExt,
        readFile(toUri(makePath(resultsDir, outputFileName)))._1,
        readFile(toUri(makePath(expectedDir, outputFileName)))._1)
    }
  }

  def translateAndParse(fileResourceUri: FileResourceUri, model: String): String = {
    Aadl2Awas(model) match {
      case Some(x) => {
        //                implicit val reporter: AccumulatingTagReporter = new ConsoleTagReporter
        //                val st = SymbolTable(x)
        //                val graph = FlowGraph(x, st)
        //                SvgGenerator(graph.asInstanceOf[FlowGraph[FlowNode, FlowNode.Edge]
        //                  with FlowGraphUpdate[FlowNode, FlowEdge[FlowNode]]], false)
        PrettyPrinter(x)
      }
      case None => ""
    }
  }
}
