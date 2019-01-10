/*
 *
 *  Copyright (c) 2017, Hariharan Thiagarajan, Kansas State University
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package org.sireum.awas.test.analysis

import java.nio.file.Paths

import org.sireum.awas.ast.Builder
import org.sireum.awas.codegen.ContextInSensitiveGen
import org.sireum.awas.fptc.FlowGraph
import org.sireum.awas.symbol.{Resource, SymbolTable}
import org.sireum.awas.util.TestUtils
import org.sireum.awas.util.TestUtils._
import org.sireum.test._
import org.sireum.util._
import org.sireum.util.jvm.FileUtil.{readFile, _}


final class FptcGraphTestDefProvider(tf: TestFramework)
extends TestDefProvider {

  val testDirs = Seq(
    //makePath("..", "example", "awas-lang"),
    //makePath("..", "example", "fptc"),
    makePath("..", "example", "Query"),
    //    makePath("..", "example", "sscate"),
    //makePath("..", "example", "hierarchial"),
    makePath("..", "example", "bindings")
  )
  val resultsDir: Uri = toFilePath(fileUri(this.getClass, makePath("..", "results", "dot")))
  val expectedDir: Uri = toFilePath(fileUri(this.getClass, makePath("..", "expected", "dot")))

  val generateExpected = false

  override def testDefs: ISeq[TestDef] = {
    val files = testDirs.flatMap { d =>
      listFiles(fileUri(this.getClass, d), "awas")
    }

    val filesEqual = files.filter { p =>
      true
    //      p.toLowerCase.contains("impl3")
    }

    filesEqual.toVector.map { x =>
      val inputFileName = filename(x)
      print(inputFileName)
      val fileWithOutExt = TestUtils.extensor(inputFileName).toString
      val outputFileName = fileWithOutExt + ".dot"
      val res = dotGraphPrinter(x, readFile(x)._1)

      TestUtils.writeResult(outputFileName,
        if (res.isDefined) res.get else "Result not found",
        expectedDir, resultsDir, generateExpected)

      EqualTest(fileWithOutExt,
        readFile(toUri(makePath(resultsDir, outputFileName)))._1,
        readFile(toUri(makePath(expectedDir, outputFileName)))._1)
    }
  }

  def dotGraphPrinter(infileUri: FileResourceUri, model: String): Option[String] = {
    import org.sireum.util.jvm.FileUtil._
    val basePath = Paths.get(fileUri(this.getClass, s".."))
    val relativeUri = basePath.relativize(Paths.get(infileUri))
    Builder(Some(relativeUri.toString), model) match {
      case None => None
      case Some(m) =>
        implicit val reporter: AccumulatingTagReporter = new ConsoleTagReporter
        var st = SymbolTable(m)
        val updatedModel = ContextInSensitiveGen(m, st)
        Resource.reset()
        st = SymbolTable(updatedModel)
        val graph = FlowGraph(m, st)
        Some(graph.getDot)
    }
  }
}
