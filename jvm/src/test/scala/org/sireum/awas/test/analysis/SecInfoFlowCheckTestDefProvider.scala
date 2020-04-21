/*
 *
 * Copyright (c) 2019, Robby, Kansas State University
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sireum.awas.test.analysis

import java.nio.file.Paths

import org.sireum.awas.AliranAman.SecInfoFlowAnalysis
import org.sireum.awas.ast.{AwasSerializer, Builder}
import org.sireum.awas.flow.FlowGraph
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.TestUtils.extensor
import org.sireum.test.{EqualTest, TestDef, TestDefProvider, TestFramework}
import org.sireum.util.jvm.FileUtil._
import org.sireum.util.{AccumulatingTagReporter, ConsoleTagReporter, FileResourceUri, ISeq}

class SecInfoFlowCheckTestDefProvider(tf: TestFramework) extends TestDefProvider {
  val testDirs = Seq(s"../example/SecInfoFlow")
  val resultsDir = toFilePath(fileUri(this.getClass, s"../results/SecInfoFlow"))
  val expectedDir = toFilePath(fileUri(this.getClass, s"../expected/SecInfoFlow"))

  val generateExpected = true

  override def testDefs: ISeq[TestDef] = {
    val files = testDirs.flatMap { d =>
      listFiles(fileUri(this.getClass, d), "awas")
    }
    val filesEqual = files.filter { p =>
      //      p.toLowerCase.contains("pcashutoff") ||
      //        p.toLowerCase.contains("isolette") ||
      //        p.toLowerCase.contains("abcloop")  ||
            p.toLowerCase.contains("case_sw")
//      true
    }

    filesEqual.toVector.map { x =>
      val inputFileName = filename(x)
      val fileWithOutExt = extensor(inputFileName).toString

      val outputFileName = fileWithOutExt + ".sif"
      writeResult(outputFileName, graphAnalysis(x, readFile(x)._1))
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

  def graphAnalysis(infileUri: FileResourceUri, model: String): String = {
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
        val r = SecInfoFlowAnalysis()

        r.getSecTypes().mkString("\n") + "\n ---- \n" +
          r.getViolations().mkString("\n") + "\n ---- \n" +
          "\n ---- \n" + r.getViolatingPaths()

    }
  }

}
