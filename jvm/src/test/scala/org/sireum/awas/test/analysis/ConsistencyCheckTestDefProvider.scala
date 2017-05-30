package org.sireum.awas.test.analysis

import java.nio.file.Paths

import org.sireum.awas.ast.Builder
import org.sireum.awas.sanitycheck.IntraComponentChecks
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.TestUtils.{extensor, makePath, writeResult}
import org.sireum.test.{EqualTest, TestDef, TestDefProvider, TestFramework}
import org.sireum.util.jvm.FileUtil._
import org.sireum.util.{AccumulatingTagReporter, ConsoleTagReporter, FileResourceUri, ISeq, Uri, imapEmpty}

final class ConsistencyCheckTestDefProvider(tf: TestFramework)
  extends TestDefProvider {
  val testDirs = Seq(makePath("..", "example", "Query"))

  val resultsDir: Uri = toFilePath(fileUri(this.getClass, makePath("..", "results", "query")))
  val expectedDir: Uri = toFilePath(fileUri(this.getClass, makePath("..", "expected", "query")))

  val generateExpected = true

  override def testDefs: ISeq[TestDef] = {
    val files = testDirs.flatMap { d =>
      listFiles(fileUri(this.getClass, d), "abcEF.awas")
    }
    val filesEqual = files.filter { p =>
      p.toLowerCase.contains("awas")
    }

    filesEqual.toVector.map { x =>
      val inputFileName = filename(x)
      println(inputFileName)
      val fileWithOutExt = extensor(inputFileName).toString

      val modelFile = x
      val queryFile = extensor(x) + ".aq"
      var inputs = imapEmpty[FileResourceUri, FileResourceUri]
      inputs = inputs + (modelFile -> queryFile)

      val outputFileName = fileWithOutExt + ".check"

      writeResult(outputFileName,
        consistencyCheck(x, readFile(x)._1), expectedDir, resultsDir, generateExpected)

      EqualTest(fileWithOutExt,
        readFile(toUri(makePath(resultsDir, outputFileName)))._1,
        readFile(toUri(makePath(expectedDir, outputFileName)))._1)
    }
  }

  def consistencyCheck(modelfile: FileResourceUri, model: String): String = {
    import org.sireum.util.jvm.FileUtil._
    val basePath = Paths.get(fileUri(this.getClass, s".."))
    val relativeUri = basePath.relativize(Paths.get(modelfile))

    Builder(Some(relativeUri.toString), model) match {
      case None => ""
      case Some(m) =>
        implicit val reporter: AccumulatingTagReporter = new ConsoleTagReporter
        val st = SymbolTable(m)
        IntraComponentChecks(m, st)
        var res = ""
        if (reporter.hasError) {
          res = reporter.tags.mkString("\n")
        }
        res
    }
  }
}
