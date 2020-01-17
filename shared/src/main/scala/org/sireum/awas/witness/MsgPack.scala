// #Sireum
// @formatter:off

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

// This file is auto-generated from SvgGenConfig.scala

package org.sireum.awas.witness

import org.sireum._

object MsgPack {

  object Constants {

    val SvgGenConfig: Z = -32

  }

  object Writer {

    @record class Default(val writer: MessagePack.Writer.Impl) extends Writer

  }

  @msig trait Writer {

    def writer: MessagePack.Writer

    def writeSvgGenConfig(o: SvgGenConfig): Unit = {
      writer.writeZ(Constants.SvgGenConfig)
      writeRankDirType(o.rankDir)
      writer.writeB(o.simpleConn)
      writer.writeB(o.viewVirtualPorts)
      writeErrorsType(o.viewErrors)
      writer.writeB(o.viewFlows)
      writer.writeB(o.bindings)
      writer.writeB(o.behaviors)
      writer.writeB(o.states)
    }

    def writeRankDirType(o: RankDir.Type): Unit = {
      writer.writeZ(o.ordinal)
    }

    def writeErrorsType(o: Errors.Type): Unit = {
      writer.writeZ(o.ordinal)
    }

    def result: ISZ[U8] = {
      return writer.result
    }

  }

  object Reader {

    @record class Default(val reader: MessagePack.Reader.Impl) extends Reader {
      def errorOpt: Option[MessagePack.ErrorMsg] = {
        return reader.errorOpt
      }
    }

  }

  @msig trait Reader {

    def reader: MessagePack.Reader

    def readSvgGenConfig(): SvgGenConfig = {
      val r = readSvgGenConfigT(F)
      return r
    }

    def readSvgGenConfigT(typeParsed: B): SvgGenConfig = {
      if (!typeParsed) {
        reader.expectZ(Constants.SvgGenConfig)
      }
      val rankDir = readRankDirType()
      val simpleConn = reader.readB()
      val viewVirtualPorts = reader.readB()
      val viewErrors = readErrorsType()
      val viewFlows = reader.readB()
      val bindings = reader.readB()
      val behaviors = reader.readB()
      val states = reader.readB()
      return SvgGenConfig(rankDir, simpleConn, viewVirtualPorts, viewErrors, viewFlows, bindings, behaviors, states)
    }

    def readRankDirType(): RankDir.Type = {
      val r = reader.readZ()
      return RankDir.byOrdinal(r).get
    }

    def readErrorsType(): Errors.Type = {
      val r = reader.readZ()
      return Errors.byOrdinal(r).get
    }

  }

  def to[T](data: ISZ[U8], f: Reader => T): Either[T, MessagePack.ErrorMsg] = {
    val rd = Reader.Default(MessagePack.reader(data))
    rd.reader.init()
    val r = f(rd)
    rd.errorOpt match {
      case Some(e) => return Either.Right(e)
      case _ => return Either.Left(r)
    }
  }

  def fromSvgGenConfig(o: SvgGenConfig, pooling: B): ISZ[U8] = {
    val w = Writer.Default(MessagePack.writer(pooling))
    w.writeSvgGenConfig(o)
    return w.result
  }

  def toSvgGenConfig(data: ISZ[U8]): Either[SvgGenConfig, MessagePack.ErrorMsg] = {
    def fSvgGenConfig(reader: Reader): SvgGenConfig = {
      val r = reader.readSvgGenConfig()
      return r
    }
    val r = to(data, fSvgGenConfig _)
    return r
  }

}