package org.sireum.awas.test.Query

import java.nio.file.Paths

import org.sireum.awas.ast.AwasSerializer
import org.sireum.awas.benchmark.{GenQueries, PerformanceMetrics, TimerImpl}
import org.sireum.awas.flow.FlowGraph
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.TestUtils.{extensor, makePath, writeResult}
import org.sireum.message.{Reporter, ReporterImpl}
import org.sireum.test.{EqualTest, TestDef, TestDefProvider, TestFramework}
import org.sireum.util.{AccumulatingTagReporter, FileResourceUri, ISeq, Uri}
import org.sireum.util.jvm.FileUtil.{fileUri, filename, listFiles, readFile, toFilePath, toUri}

class GenQueryTestDefProvider(tf: TestFramework) extends TestDefProvider {
  val testDirs = Seq(
    makePath("..", "example", "benchmark")
    //    makePath("..", "example", "sscate")
  )

  val resultsDir: Uri = toFilePath(fileUri(this.getClass, makePath("..", "results", "benchmark")))
  val expectedDir: Uri = toFilePath(fileUri(this.getClass, makePath("..", "expected", "benchmark")))

  val generateExpected = false


  override def testDefs: ISeq[TestDef] = {
    val files = testDirs.flatMap { d =>
      listFiles(fileUri(this.getClass, d), ".json")
    }

    val filesEqual = files.filter { p =>
      true
      //          p.toLowerCase.contains("pca_pulseox")
    }

    filesEqual.toVector.map { x =>
      val inputFileName = filename(x)
      println(inputFileName)
      val fileWithOutExt = extensor(inputFileName).toString
      val outputFileName = fileWithOutExt + ".aq"

      writeResult(outputFileName,
        QueryResultPrinter(x, readFile(x)._1), expectedDir, resultsDir, generateExpected)

      EqualTest(fileWithOutExt,
        readFile(toUri(makePath(resultsDir, outputFileName)))._1,
        readFile(toUri(makePath(expectedDir, outputFileName)))._1)
    }
  }

  def QueryResultPrinter(modelfile: FileResourceUri, model: String): String = {
    import org.sireum.util.jvm.FileUtil._
    val basePath = Paths.get(fileUri(this.getClass, s".."))
    val relativeUri = basePath.relativize(Paths.get(modelfile))

    // Aadl2Awas(model) match {
    AwasSerializer.unapply(model) match {
      case None => "Failed to build the model"
      case Some(m) =>
        implicit val reporter: Reporter = new ReporterImpl(org.sireum.ISZ())
        val st = SymbolTable(m)(new AccumulatingTagReporter())
        val graph = FlowGraph(m, st, true)
        GenQueries(st, 29).map(m => m.mkString("\n")).mkString("\n")
      //PerformanceMetrics(st, 29, 10, new TimerImpl())
    }
  }
}
