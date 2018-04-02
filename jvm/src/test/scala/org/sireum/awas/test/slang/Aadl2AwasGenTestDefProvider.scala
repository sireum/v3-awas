package org.sireum.awas.test.slang

import org.sireum.awas.ast.PrettyPrinter
import org.sireum.awas.slang.Aadl2Awas
import org.sireum.awas.util.TestUtils._
import org.sireum.test.{EqualTest, TestDef, TestDefProvider, TestFramework}
import org.sireum.util.jvm.FileUtil._
import org.sireum.util.{FileResourceUri, ISeq, Uri}

class Aadl2AwasGenTestDefProvider(tf: TestFramework)
  extends TestDefProvider {
  val testDirs = Seq(makePath("..", "example", "aadl-json"))

  val resultsDir: Uri = toFilePath(fileUri(this.getClass, makePath("..", "results", "codegen")))
  val expectedDir: Uri = toFilePath(fileUri(this.getClass, makePath("..", "expected", "codegen")))

  val generateExpected = true

  val outputExtension = ".awas"

  override def testDefs: ISeq[TestDef] = {
    println("test")
    val files = testDirs.flatMap { d =>
      listFiles(fileUri(this.getClass, d), ".json")
    }
    println(files)

    //equals test by excluding some
    val filesEqual = files // not excluding any

    filesEqual.toVector.map { x =>
      println("test 2")
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
      case Some(x) => PrettyPrinter(x)
      case None => ""
    }
  }
}
