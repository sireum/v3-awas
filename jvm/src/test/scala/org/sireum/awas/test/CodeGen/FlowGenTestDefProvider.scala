package org.sireum.awas.test.CodeGen

import org.sireum.awas.ast.PrettyPrinter
import org.sireum.awas.flow.{FlowGraph, FlowInference}
import org.sireum.awas.slang.Aadl2Awas
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.TestUtils.{extensor, makePath, writeResult}
import org.sireum.test.{EqualTest, TestDef, TestDefProvider, TestFramework}
import org.sireum.util.{AccumulatingTagReporter, ConsoleTagReporter, FileResourceUri, ISeq, Uri}
import org.sireum.util.jvm.FileUtil.{fileUri, filename, listFiles, readFile, toFilePath, toUri}

class FlowGenTestDefProvider(tf: TestFramework) extends TestDefProvider {
  val testDirs = Seq(makePath("..", "example", "aadl-json"))

  val resultsDir: Uri = toFilePath(fileUri(this.getClass, makePath("..", "results", "slang")))
  val expectedDir: Uri = toFilePath(fileUri(this.getClass, makePath("..", "expected", "slang")))

  val generateExpected = true

  val outputExtension = ".flow"

  override def testDefs: ISeq[TestDef] = {
    val files = testDirs.flatMap { d => listFiles(fileUri(this.getClass, d), ".json")
    }

    //equals test by excluding some
    val filesEqual = files.filter { p =>
      //true
      p.toLowerCase.contains("latest-security")
    }

    filesEqual.toVector.map { x =>
      val inputFileName = filename(x)
      println(inputFileName)
      val fileWithOutExt = extensor(inputFileName).toString

      val outputFileName = fileWithOutExt + outputExtension

      writeResult(outputFileName, translateAndParse(x, readFile(x)._1), expectedDir, resultsDir, generateExpected)

      EqualTest(
        fileWithOutExt,
        readFile(toUri(makePath(resultsDir, outputFileName)))._1,
        readFile(toUri(makePath(expectedDir, outputFileName)))._1
      )
    }
  }

  def translateAndParse(fileResourceUri: FileResourceUri, model: String): String = {
    //    var x = AwasSerializer.unapply(model)
    Aadl2Awas(model) match {
      case Some(x) => {
        implicit val reporter: AccumulatingTagReporter = new ConsoleTagReporter
        val st = SymbolTable(x)
        val graph = FlowGraph(x, st, false)
        val res = FlowInference(st)
        val rr = res.foldLeft("")((c,v) =>  c +"\n"+v._1+"\n-------------\n"+(v._2.map(it => st.componentTable(v._1).flow(it).toString).mkString("\n"))+"\n")
        //                        SvgGenerator(st, graph.asInstanceOf[FlowGraph[FlowNode, FlowNode.Edge]
        //                          with FlowGraphUpdate[FlowNode, FlowEdge[FlowNode]]],SvgGenerator.viewConfig, None)
        //  val rr = AwasSerializer(x)
//        val rr = PrettyPrinter(x)
        //        FaultImpactAnalysis.generateFIAQueries(x, false) + "\n \n" + FaultImpactAnalysis.generateFIAQueries(x, true)
        rr
      }
      case None => "unable to translate"
    }
  }

}
