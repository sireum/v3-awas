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

package org.sireum.awas.test.slang

import org.sireum.awas.ast.PrettyPrinter
import org.sireum.awas.slang.Aadl2Awas
import org.sireum.awas.util.TestUtils._
import org.sireum.test.{EqualTest, TestDef, TestDefProvider, TestFramework}
import org.sireum.util.jvm.FileUtil._
import org.sireum.util.{FileResourceUri, ISeq, Uri}

class Aadl2AwasGenTestDefProvider(tf: TestFramework) extends TestDefProvider {
  val testDirs = Seq(makePath("..", "example", "aadl-json"))

  val resultsDir: Uri = toFilePath(fileUri(this.getClass, makePath("..", "results", "slang")))
  val expectedDir: Uri = toFilePath(fileUri(this.getClass, makePath("..", "expected", "slang")))

  val generateExpected = false

  val outputExtension = ".awas"

  override def testDefs: ISeq[TestDef] = {
    val files = testDirs.flatMap { d => listFiles(fileUri(this.getClass, d), ".json")
    }

    //equals test by excluding some
    val filesEqual = files.filter { p =>
      true
//          p.toLowerCase.contains("root")
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
    Aadl2Awas(model) match {
      case Some(x) => {
//                        implicit val reporter: AccumulatingTagReporter = new ConsoleTagReporter
//                        val st = SymbolTable(x)
//                        val graph = FlowGraph(x, st)
//                        SvgGenerator(graph.asInstanceOf[FlowGraph[FlowNode, FlowNode.Edge]
//                          with FlowGraphUpdate[FlowNode, FlowEdge[FlowNode]]], false)
//        AwasSerializer(x)
        PrettyPrinter(x)
//        FaultImpactAnalysis.generateFIAQueries(x, false) + "\n \n" + FaultImpactAnalysis.generateFIAQueries(x, true)
      }
      case None => ""
    }
  }
}
