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
import org.sireum.awas.util.TestUtils._
import org.sireum.test._
import org.sireum.util.jvm.FileUtil._
import org.sireum.util.{AccumulatingTagReporter, ConsoleTagReporter, FileResourceUri, ISeq}


final class CInSensitiveTestDefProvider(tf: TestFramework)
  extends TestDefProvider {

  val testDirs = Seq(
    makePath("..", "example", "awas-lang"),
    makePath("..", "example", "fptc")
  )
  val resultsDir: FileResourceUri = toFilePath(fileUri(this.getClass, makePath("..", "results", "codegen")))
  val expectedDir: FileResourceUri = toFilePath(fileUri(this.getClass, makePath("..", "expected", "codegen")))


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

      ConditionTest(fileWithOutExt, Builder(Some(x), modelPrinter(x, readFile(x)._1).get).isDefined)
    }
  }


  def modelPrinter(fileUri: FileResourceUri, model: String): Option[String] = {
    Builder(Some(fileUri), model) match {
      case None => None
      case Some(m) =>
        implicit val reporter: AccumulatingTagReporter = new ConsoleTagReporter
        val st = SymbolTable(m)
        Some(PrettyPrinter(ContextInSensitiveGen(m, st)))
    }
  }
}
