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

package org.sireum.awas.test.Query

import java.nio.file.Paths

import org.sireum.awas.ast.{AwasSerializer, Builder}
import org.sireum.awas.flow.FlowGraph
import org.sireum.awas.query.{QueryEval, QueryParser}
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.TestUtils._
import org.sireum.message.{Reporter, ReporterImpl}
import org.sireum.test.{EqualTest, TestDef, TestDefProvider, TestFramework}
import org.sireum.util._
import org.sireum.util.jvm.FileUtil._


final class QueryTestDefProvider(tf: TestFramework) extends TestDefProvider {
  val testDirs = Seq(
    makePath("..", "example", "Query")
    //    makePath("..", "example", "sscate")
  )

  val resultsDir: Uri = toFilePath(fileUri(this.getClass, makePath("..", "results", "query")))
  val expectedDir: Uri = toFilePath(fileUri(this.getClass, makePath("..", "expected", "query")))

  val generateExpected = false

  override def testDefs: ISeq[TestDef] = {
    val files = testDirs.flatMap { d =>
      listFiles(fileUri(this.getClass, d), ".awas")
    }

    val filesEqual = files.filter { p =>
      true
      //p.toLowerCase.contains("pca_pulseox")
    }

    filesEqual.toVector.map { x =>
      val inputFileName = filename(x)
      println(inputFileName)
      val fileWithOutExt = extensor(inputFileName).toString

      val queryFile = extensor(x) + ".aq"

      val outputFileName = fileWithOutExt + ".qres"

      writeResult(outputFileName,
        QueryResultPrinter(x, readFile(x)._1,
          readFile(queryFile)._1), expectedDir, resultsDir, generateExpected)

      EqualTest(fileWithOutExt,
        readFile(toUri(makePath(resultsDir, outputFileName)))._1,
        readFile(toUri(makePath(expectedDir, outputFileName)))._1)
    }
  }

  def QueryResultPrinter(modelfile: FileResourceUri, model: String,
                         query: String): String = {
    import org.sireum.util.jvm.FileUtil._
    val basePath = Paths.get(fileUri(this.getClass, s".."))
    val relativeUri = basePath.relativize(Paths.get(modelfile))

    Builder(Some(relativeUri.toString), model) match {
      case None => "Failed to build the model"
      case Some(m) =>

        implicit val reporter: Reporter = new ReporterImpl(org.sireum.ISZ())
        val st = SymbolTable(m)(new AccumulatingTagReporter())
        val graph = FlowGraph(m, st, true)
        QueryParser(query, reporter) match {
          case None => {
            reporter.printMessages()
            "Failed to parse the query:"
          }
          case Some(q) =>
            val res = QueryEval(q, st)
            var result = "" //AwasSerializer(m) + "\n\n"
            res.foreach { r =>
              result += r._1
              result += "\n -------------- \n"
              result += r._2.toString
              result += "\n -------------- \n"
            }
            if (result.nonEmpty) result else "Empty result"
        }
    }
  }
}
