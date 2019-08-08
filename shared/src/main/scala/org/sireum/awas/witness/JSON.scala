// #Sireum
// @formatter:off

/*
 Copyright (c) 2019, Robby, Kansas State University
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

// This file is auto-generated from SvgGenConfig.scala

package org.sireum.awas.witness

import org.sireum._
import org.sireum.Json.Printer._

object JSON {

  object Printer {

    @pure def printSvgGenConfig(o: SvgGenConfig): ST = {
      return printObject(ISZ(
        ("type", st""""SvgGenConfig""""),
        ("rankDir", printRankDirType(o.rankDir)),
        ("simpleConn", printB(o.simpleConn)),
        ("viewVirtualPorts", printB(o.viewVirtualPorts)),
        ("viewErrors", printB(o.viewErrors)),
        ("viewFlows", printB(o.viewFlows)),
        ("bindings", printB(o.bindings)),
        ("behaviors", printB(o.behaviors)),
        ("states", printB(o.states))
      ))
    }

    @pure def printRankDirType(o: RankDir.Type): ST = {
      val value: String = o match {
        case RankDir.TB => "TB"
        case RankDir.LR => "LR"
        case RankDir.BT => "BT"
        case RankDir.RL => "RL"
      }
      return printObject(ISZ(
        ("type", printString("RankDir")),
        ("value", printString(value))
      ))
    }

  }

  @record class Parser(input: String) {
    val parser: Json.Parser = Json.Parser.create(input)

    def errorOpt: Option[Json.ErrorMsg] = {
      return parser.errorOpt
    }

    def parseSvgGenConfig(): SvgGenConfig = {
      val r = parseSvgGenConfigT(F)
      return r
    }

    def parseSvgGenConfigT(typeParsed: B): SvgGenConfig = {
      if (!typeParsed) {
        parser.parseObjectType("SvgGenConfig")
      }
      parser.parseObjectKey("rankDir")
      val rankDir = parseRankDirType()
      parser.parseObjectNext()
      parser.parseObjectKey("simpleConn")
      val simpleConn = parser.parseB()
      parser.parseObjectNext()
      parser.parseObjectKey("viewVirtualPorts")
      val viewVirtualPorts = parser.parseB()
      parser.parseObjectNext()
      parser.parseObjectKey("viewErrors")
      val viewErrors = parser.parseB()
      parser.parseObjectNext()
      parser.parseObjectKey("viewFlows")
      val viewFlows = parser.parseB()
      parser.parseObjectNext()
      parser.parseObjectKey("bindings")
      val bindings = parser.parseB()
      parser.parseObjectNext()
      parser.parseObjectKey("behaviors")
      val behaviors = parser.parseB()
      parser.parseObjectNext()
      parser.parseObjectKey("states")
      val states = parser.parseB()
      parser.parseObjectNext()
      return SvgGenConfig(rankDir, simpleConn, viewVirtualPorts, viewErrors, viewFlows, bindings, behaviors, states)
    }

    def parseRankDirType(): RankDir.Type = {
      val r = parseRankDirT(F)
      return r
    }

    def parseRankDirT(typeParsed: B): RankDir.Type = {
      if (!typeParsed) {
        parser.parseObjectType("RankDir")
      }
      parser.parseObjectKey("value")
      var i = parser.offset
      val s = parser.parseString()
      parser.parseObjectNext()
      RankDir.byName(s) match {
        case Some(r) => return r
        case _ =>
          parser.parseException(i, s"Invalid element name '$s' for RankDir.")
          return RankDir.byOrdinal(0).get
      }
    }

    def eof(): B = {
      val r = parser.eof()
      return r
    }

  }

  def to[T](s: String, f: Parser => T): Either[T, Json.ErrorMsg] = {
    val parser = Parser(s)
    val r = f(parser)
    parser.eof()
    parser.errorOpt match {
      case Some(e) => return Either.Right(e)
      case _ => return Either.Left(r)
    }
  }

  def fromSvgGenConfig(o: SvgGenConfig, isCompact: B): String = {
    val st = Printer.printSvgGenConfig(o)
    if (isCompact) {
      return st.renderCompact
    } else {
      return st.render
    }
  }

  def toSvgGenConfig(s: String): Either[SvgGenConfig, Json.ErrorMsg] = {
    def fSvgGenConfig(parser: Parser): SvgGenConfig = {
      val r = parser.parseSvgGenConfig()
      return r
    }
    val r = to(s, fSvgGenConfig _)
    return r
  }

}