/*
 Copyright (c) 2016, Robby, Kansas State University
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

package org.sireum.awas.test.fptc

import org.sireum.awas.ast.Builder
import org.sireum.awas.fptc.FptcGraph
import org.sireum.test._
import org.sireum.util._
import org.sireum.util.jvm.FileUtil._


final class FptcGraphTestDefProvider(tf: TestFramework)
extends TestDefProvider {

  val testcaseDir = "../../awas/jvm/src/test/resources/org/sireum/awas/test/example"
  val resultsDir = "../../awas/jvm/src/test/resources/org/sireum/awas/test/results/dot"
  val expectedDir = "../../awas/jvm/src/test/resources/org/sireum/awas/test/expected/dot"

  val generateExpected = true

  override def testDefs: ISeq[TestDef] = {
    val files = listFiles(toUri(testcaseDir), "awas")

    val filesEqual = files.filter { p =>
      p.toLowerCase.contains("pcashutoff") ||
        p.toLowerCase.contains("isolette") ||
        p.toLowerCase.contains("abcloop")
    }

    filesEqual.toVector.map { x =>
      val inputFileName = filename(x)
      val fileWithOutExt = extensor(inputFileName).toString
      val outputFileName = fileWithOutExt + ".dot"
      writeResult(outputFileName, dotGraphPrinter(readFile(x)._1, fileWithOutExt).get)

      val result = readFile(toUri(resultsDir+"/"+outputFileName))._1
      EqualTest(filename(x), result ,
        readFile(toUri(expectedDir+"/"+outputFileName))._1)
    }

  }

  def extensor(orig: String) = (orig.split('.') match {
    case xs @ Array(x) => xs
    case y => y.init
  }).mkString


  def writeResult(fileName : String, content : String) ={
    if(generateExpected) {
      val expPath = expectedDir + "/" + fileName
      writeFile(toUri(expPath), content)
    }

    val resPath = resultsDir + "/" + fileName
    writeFile(toUri(resPath), content)
  }



  def dotGraphPrinter(model: String, name : String): Option[String] ={
    Builder(model) match {
      case None => None
      case Some(m) =>
        val graph = FptcGraph(m)
        Some(graph.toDot(name))
    }
  }
}
