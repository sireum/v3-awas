/*
Copyright (c) 2015, Robby, Kansas State University
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

package org.sireum.awas.ast

import java.io.StringReader

import org.antlr.v4.runtime._
import org.sireum.awas.parser.antlr4.Antlr4AwasParser
import org.sireum.util._

object Builder {

  trait Reporter {
    def error(line: PosInteger,
              column: PosInteger,
              offset: Natural,
              message: String): Unit
  }

  object ConsoleReporter extends Reporter {
    override def error(line: PosInteger,
                       column: PosInteger,
                       offset: Natural,
                       message: String): Unit = {
      Console.err.println(s"[$line, $column] $message")
      Console.err.flush()
    }
  }

  def apply(input: String,
            maxErrors: Natural = 0,
            reporter: Reporter = ConsoleReporter): Option[Model] = {
    class ParsingEscape extends RuntimeException

    import org.sireum.awas.parser.antlr4.Antlr4AwasLexer

    val sr = new StringReader(input)
    val inputStream = new ANTLRInputStream(sr)
    val lexer = new Antlr4AwasLexer(inputStream)
    val tokens = new CommonTokenStream(lexer)
    val parser = new Antlr4AwasParser(tokens)
    parser.removeErrorListeners()
    var success = true
    parser.addErrorListener(new BaseErrorListener {
      var errors = 0

      override def syntaxError(recognizer: Recognizer[_, _],
                               offendingSymbol: Any,
                               line: PosInteger,
                               charPositionInLine: PosInteger,
                               msg: String,
                               e: RecognitionException): Unit = {
        success = false
        val token = offendingSymbol.asInstanceOf[Token]
        val start = token.getStartIndex
        reporter.error(line, charPositionInLine, start, msg + s" (token=${token.getType})")
        errors += 1
        if (maxErrors > 0 && errors >= maxErrors) {
          throw new ParsingEscape
        }
      }
    })
    try {
      parser.modelFile() // TODO
      if (success) Some(Model())
      else None
    } catch {
      case t: Throwable => None
    }
  }
}
