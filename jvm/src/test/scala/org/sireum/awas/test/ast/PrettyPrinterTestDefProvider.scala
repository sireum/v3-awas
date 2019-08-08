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

import org.sireum.awas.ast.{Builder, PrettyPrinter}
import org.sireum.awas.util.TestUtils.makePath
import org.sireum.test._
import org.sireum.util._
import org.sireum.util.jvm.FileUtil._


final class PrettyPrinterTestDefProvider(tf: TestFramework)
  extends TestDefProvider {
  val testDirs = Seq(
    makePath("..", "example", "awas-lang")//,
//    makePath("..", "example", "heirarchial")

    //    ,s"../example/fptc"
  )

  override def testDefs: ISeq[TestDef] = {
    println()
    val files = testDirs.flatMap{d => listFiles(fileUri(this.getClass, d), "awas")
    }

    //equals test by excluding some
    val filesEqual = files.filterNot { p =>
      p.toLowerCase.contains("abcloop") ||
        p.toLowerCase.contains("pcashutoff.awas") ||
        p.toLowerCase.contains("behavior") ||
        p.toLowerCase.contains("transition") ||
        p.toLowerCase.contains("properties")
    }.filter(_.toLowerCase.contains("properties"))

    //conditional test by filter
    val filesConditional = files.filterNot { f =>
      f.toLowerCase.contains("abnested") ||
      filename(f).equals("pcashutoff.awas") ||
        f.toLowerCase.contains("behavior") ||
        f.toLowerCase.contains("transition") ||
        f.toLowerCase.contains("properties")
    }

    filesEqual.toVector.map { x =>
      println(x)
      EqualOptTest(filename(x), printAndParse(x, readFile(x)._1), readFile(x)._1)
    } ++ filesConditional.toVector.map {x =>
    ConditionTest(filename(x), Builder(some(x),printAndParse(x, readFile(x)._1).get).isDefined)}
  }

  def printAndParse(fileResourceUri: FileResourceUri, model: String): Option[String] = {
    Builder(Some(fileResourceUri), model) match {
      case None => None
      case Some(e) =>
        val result = PrettyPrinter(e)
        Some(result)
    }
  }
}