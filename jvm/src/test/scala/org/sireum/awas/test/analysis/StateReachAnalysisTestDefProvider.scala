/*
 *
 * Copyright (c) 2020, Hariharan Thiagarajan, Kansas State University
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

import org.sireum.awas.analysis.StateReachAnalysis
import org.sireum.awas.ast.Builder
import org.sireum.awas.codegen.ContextInSensitiveGen
import org.sireum.awas.flow.FlowGraph
import org.sireum.awas.symbol.{Resource, SymbolTable, SymbolTableHelper}
import org.sireum.awas.util.AwasUtil.ResourceUri
import org.sireum.awas.util.TestUtils.extensor
import org.sireum.test.{EqualTest, TestDef, TestDefProvider, TestFramework}
import org.sireum.util._
import org.sireum.util.jvm.FileUtil.{fileUri, filename, listFiles, readFile, toFilePath, toUri, writeFile}

import scala.io.Source

final class StateReachAnalysisTestDefProvider(tf: TestFramework) extends TestDefProvider {
  val testDirs = Seq(s"../example/awas-lang", s"../example/fptc")
  val resultsDir = toFilePath(fileUri(this.getClass, s"../results/fptc"))
  val expectedDir = toFilePath(fileUri(this.getClass, s"../expected/fptc"))

  val generateExpected = false

  override def testDefs: ISeq[TestDef] = {
    val files = testDirs.flatMap { d =>
      listFiles(fileUri(this.getClass, d), "awas")
    }
    val filesEqual = files.filter { p =>
      //      p.toLowerCase.contains("pcashutoff") ||
      //        p.toLowerCase.contains("isolette") ||
      //        p.toLowerCase.contains("abcloop")  ||
      p.toLowerCase.contains("fptc_base")
    }

    filesEqual.toVector.map { x =>
      val inputFileName = filename(x)
      val fileWithOutExt = extensor(inputFileName).toString
      val queryFile = extensor(toFilePath(x)) + ".aq"
      val outputFileName = fileWithOutExt + ".st"
      writeResult(outputFileName, graphAnalysis(x, readFile(x)._1, queryFile))
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

  def graphAnalysis(infileUri: FileResourceUri, model: String, name: String): String = {
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
        val lines = Source.fromFile(name).getLines
        lines.foreach { line =>
          val state = line2state(line, st)
          val res = StateReachAnalysis.getReachability(state, st)
          //          val fg = FPTCAnalysis(graph.getUri, st)
          //          fg.toList.sortBy(_._1).map(it =>
          //            SymbolTableHelper.uri2IdString(it._1) + "\n" + fptcNodeWriter(it._2, st)).mkString("\n")
          result = result + "\n" + res.toString + "\n States : \n" + res.getModes.mkString("\n") + "\n Behaviors: \n" + res.getBehavior
            .mkString("\n")

        }
        result
    }
  }

  def line2state(text: String, st: SymbolTable): IMap[ResourceUri, ISet[ResourceUri]] = {
    val portName = text.split("\\[")
    val H = SymbolTableHelper
    val portUri = H.getPortUri(st, portName.head)
    val errors = text.split("\\[").tail.head.dropRight(1).split(",")
    val errorsUri = errors.map(H.getErrorUri(st, _).get)

    imapEmpty[ResourceUri, ISet[ResourceUri]] + (portUri.get -> errorsUri.toSet)
  }
}
