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

package org.sireum.awas.witness

import java.io._
import java.nio.file.{Files, Paths}
import java.util.zip.ZipInputStream

import org.sireum.util.jvm.FileUtil

object Visualizer {

  /**
    * @param modelJson : serialized AWAS model in JSON
    * @param outputDirLoc : output dir path
    * @param queries : optional query file
    */
  @throws[IOException]
  def apply(modelJson : String,
            outputDirLoc : String,
            queries : String): Unit = {

    val resourceLoc = "/org/sireum/awas/AADLBridge/awas-web.zip"

    val zipFolderName = "awas-web/"

    val graphVarPath = "/min/graphVar.js"

    def extractFromZip(zis : ZipInputStream): Unit = {
      LazyList.continually(zis.getNextEntry).takeWhile(_ != null).foreach { file =>
        val fileName = outputDirLoc + "/" + file.getName.substring(zipFolderName.length)
        if(file.isDirectory) {
          Files.createDirectories(Paths.get(fileName))
        } else {
          val fout = new FileOutputStream(fileName)
          val buffer = new Array[Byte](1024)
          LazyList.continually(zis.read(buffer)).takeWhile(_ != -1).foreach(fout.write(buffer, 0, _))
        }
      }
    }

    val res = this.getClass.getResource(resourceLoc)
    extractFromZip(new ZipInputStream(res.openStream()))
    var result = ""
    //println(modelFile)
    var graphVar = "var awas = `" + modelJson + "`;\n"
    if(queries.nonEmpty) {graphVar = graphVar + "var queries = `" + queries + "`;\n" }
    result = result + graphVar

    FileUtil.writeFile(FileUtil.toUri(outputDirLoc + graphVarPath), result)
  }
}
