package org.sireum.awas.test.analysis
import java.nio.file.Paths

import org.sireum.awas.analysis.FaultImpactAnalysis
import org.sireum.awas.ast.Builder
import org.sireum.awas.fptc.FlowGraph
import org.sireum.awas.query.{QueryEval, QueryParser}
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.TestUtils.{extensor, makePath, writeResult}
import org.sireum.message.Reporter
import org.sireum.test.{EqualTest, TestDef, TestDefProvider, TestFramework}
import org.sireum.util.{AccumulatingTagReporter, FileResourceUri, ISeq, Uri}
import org.sireum.util.jvm.FileUtil._

class FIATestDefProvider(tf: TestFramework) extends TestDefProvider {
  val testDirs = Seq(
    makePath("..", "example", "Query"),
    makePath("..", "example", "sscate")
  )

  val resultsDir: Uri = toFilePath(fileUri(this.getClass, makePath("..", "results", "fia")))
  val expectedDir: Uri = toFilePath(fileUri(this.getClass, makePath("..", "expected", "fia")))

  val generateExpected = false

  override def testDefs: ISeq[TestDef] = {
    val files = testDirs.flatMap { d =>
      listFiles(fileUri(this.getClass, d), ".awas")
    }

    val filesEqual = files.filter { p =>
      true
    //       p.toLowerCase.contains("no")
    }

    filesEqual.toVector.map { x =>
      val inputFileName = filename(x)
      val fileWithOutExt = extensor(inputFileName).toString


      val outputFileName = fileWithOutExt + ".fia"

      writeResult(outputFileName,
        FiaResult(x, readFile(x)._1), expectedDir, resultsDir, generateExpected)

      EqualTest(fileWithOutExt,
        readFile(toUri(makePath(resultsDir, outputFileName)))._1,
        readFile(toUri(makePath(expectedDir, outputFileName)))._1)
    }
  }

  def FiaResult(modelfile: FileResourceUri, model: String): String = {
    import org.sireum.util.jvm.FileUtil._
    val basePath = Paths.get(fileUri(this.getClass, s".."))
    val relativeUri = basePath.relativize(Paths.get(modelfile))

    Builder(Some(relativeUri.toString), model) match {
      case None => ""
      case Some(m) => {
        "From Source \n\n" + new FaultImpactAnalysis().generateFIAQueries(m, true)+
          "\n\nFrom Sink \n\n" +  new FaultImpactAnalysis().generateFIAQueries(m, false)
//        new FaultImpactAnalysis().computeFIA(m)

      }

    }
  }
}
