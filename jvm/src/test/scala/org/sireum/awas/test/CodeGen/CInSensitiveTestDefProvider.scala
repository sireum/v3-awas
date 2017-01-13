/*
 Copyright (c) 2017, Hariharan Thiagarajan, Robby, Kansas State University
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice, this
    list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sireum.awas.test.CodeGen

import org.sireum.awas.ast.{Builder, PrettyPrinter}
import org.sireum.awas.codegen.ContextInSensitiveGen
import org.sireum.awas.symbol.SymbolTable
import org.sireum.test.{EqualTest, TestDef, TestDefProvider, TestFramework}
import org.sireum.util.jvm.FileUtil._
import org.sireum.util.{AccumulatingTagReporter, ConsoleTagReporter, FileResourceUri, ISeq}


final class CInSensitiveTestDefProvider(tf: TestFramework)
  extends TestDefProvider {

  val testDirs = Seq(s"../example/awas-lang"
        ,s"../example/fptc"
  )
  val resultsDir = toFilePath(fileUri(this.getClass, s"../results/codegen"))
  val expectedDir = toFilePath(fileUri(this.getClass, s"../expected/codegen"))

  val generateExpected = true

  override def testDefs: ISeq[TestDef] = {
    val files = testDirs.flatMap { d =>
      listFiles(fileUri(this.getClass, d), "awas")
    }
    //filter files here
    val filesEqual = files.filter { p =>
      p.toLowerCase.contains("fptc_base")
    }

    filesEqual.toVector.map { x =>
      val inputFileName = filename(x)
      val fileWithOutExt = extensor(inputFileName).toString
      val outputFileName = fileWithOutExt + ".cis"
      writeResult(outputFileName, modelPrinter(x, readFile(x)._1, fileWithOutExt).get)
      val result = readFile(toUri(resultsDir + "/" + outputFileName))._1
      EqualTest(filename(x), result,
        readFile(toUri(expectedDir + "/" + outputFileName))._1)
    }
  }

  def extensor(orig: String) = (orig.split('.') match {
    case xs@Array(x) => xs
    case y => y.init
  }).mkString

  def writeResult(fileName: String, content: String) = {
    if (generateExpected) {
      val expPath = expectedDir + "/" + fileName
      writeFile(toUri(expPath), content)
    }
    val resPath = resultsDir + "/" + fileName
    writeFile(toUri(resPath), content)
  }

  def modelPrinter(fileUri: FileResourceUri, model: String, name: String): Option[String] = {
    Builder(Some(fileUri), model) match {
      case None => None
      case Some(m) =>
        implicit val reporter: AccumulatingTagReporter = new ConsoleTagReporter
        val st = SymbolTable(m)
        Some(PrettyPrinter(ContextInSensitiveGen(m, st)))
    }
  }
}
