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

package org.sireum.awas.test.ast

import java.nio.file.Paths

import org.sireum.awas.ast.Builder
import org.sireum.awas.symbol.SymbolTable
import org.sireum.awas.util.TestUtils._
import org.sireum.test.{EqualTest, TestDef, TestDefProvider, TestFramework}
import org.sireum.util.jvm.FileUtil.{fileUri, _}
import org.sireum.util.{AccumulatingTagReporter, ConsoleTagReporter, FileResourceUri, ISeq, TagPickling}

final class SymbolTableTestDefProvider(tf: TestFramework)
  extends TestDefProvider {
  val testDirs = Seq(makePath("..", "example", "awas-lang")
    //    ,s"../example/fptc"
  )
  val resultsDir: FileResourceUri = toFilePath(fileUri(this.getClass, makePath("..", "results", "st")))
  val expectedDir: FileResourceUri = toFilePath(fileUri(this.getClass, makePath("..", "expected", "st")))

  val generateExpected = false

  override def testDefs: ISeq[TestDef] = {
    val files = testDirs.flatMap { d =>
      listFiles(fileUri(this.getClass, d), "awas")
    }
    //filter files here
    val filesEqual = files.filter { p =>
//      p.toLowerCase.contains("isolette_model") ||
//        p.toLowerCase.contains("state_machine.awas")
      p.toLowerCase.contains("nothing")

    }

    filesEqual.toVector.map { x =>
      val inputFileName = filename(x)
      val fileWithOutExt = extensor(inputFileName).toString
      val outputFileName = fileWithOutExt + ".st"
      //
      writeResult(outputFileName,
        symbolTablePrinter(x, readFile(x)._1, fileWithOutExt).get,
        expectedDir, resultsDir, generateExpected)

      EqualTest(fileWithOutExt,
        readFile(toUri(makePath(resultsDir, outputFileName)))._1,
        readFile(toUri(makePath(expectedDir, outputFileName)))._1)
//      }
    }
  }


  def symbolTablePrinter(infileUri: FileResourceUri, model: String, name: String): Option[String] = {
    import org.sireum.util.jvm.FileUtil._
    val basePath = Paths.get(fileUri(this.getClass, s"../"))
    val relativeUri = basePath.relativize(Paths.get(infileUri))
    Builder(Some(relativeUri.toString), model) match {
      case None => None
      case Some(m) =>
        implicit val reporter: AccumulatingTagReporter = new ConsoleTagReporter
        val st = SymbolTable(m)
        if (!reporter.hasError) {
          var result = "#TYPES#\n"
          result += st.typeDecls.toVector.sorted.mkString("\n")
          result += "\n"
          result += "#TYPE TABLE#\n"
          st.typeDecls.toVector.sorted.foreach {
            td =>
              result += td + ":\n"
              result += st.typeTable(td).enumElements.toVector.sorted.mkString("\n")
          }
          result += "\n"
          result += "#STATE MACHINES#\n"
          result += st.stateMachines.toVector.sorted.mkString("\n")
          result += "\n"
          result += "#STATE MACHINE TABLE#\n"
          st.stateMachines.toVector.sorted.foreach {
            sm =>
              result += sm + ":\n"
              result += "#STATES#\n"
              result += st.smTable(sm).states.mkString("\n") + "\n"
              result += "#EVENTS#\n"
              result += st.smTable(sm).events.toVector.sorted.mkString("\n") + "\n"
          }
          result += "#COMPONENTS#\n"
          result += st.components.toVector.sorted.mkString("\n") + "\n"
          result += "#COMPONENT TABLE#\n"
          st.components.toVector.sorted.foreach {
            comp =>
              result += comp + ":\n"
              result += "#PORTS#\n"
              result += st.componentTable(comp).ports.toVector.sorted.mkString("\n") + "\n\n"
              result += "#PROPAGATION\n"
              st.componentTable(comp).ports.foreach {
                propP =>
                  result += propP + ":\n"
                  result += "#ERRORS#"
                  result += st.componentTable(comp).propagation(propP).toVector.sorted.mkString("\n") + "\n"
              }
          }
          result += "#CONNECTIONS#\n"
          //          result += st.connections.toVector.sorted.mkString("\n") + "\n"

          Some(result)
        } else {
          Some(reporter.tags.map(TagPickling.pickle).mkString("\n"))
        }
    }
  }


}
