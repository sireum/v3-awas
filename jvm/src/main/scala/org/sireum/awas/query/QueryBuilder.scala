/*
 Copyright (c) 2017, Robby, Kansas State University
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

package org.sireum.awas.query

import java.io.StringReader

import org.antlr.v4.runtime._
import org.antlr.v4.runtime.tree.TerminalNode
import org.sireum.aq.parser.Antlr4AQParser._
import org.sireum.aq.parser.{Antlr4AQLexer, Antlr4AQParser}
import org.sireum.util._

final class QueryBuilder private() {

  def build(ctx: ModelContext): Model = {
    val r = Model(ctx.queryStmt().map(build))
    r
  }

  def build(ctx: QueryStmtContext): QueryStmt = {
    QueryStmt(buildId(ctx.id),
      build(ctx.expr()))
  }

  def build(ctx: ExprContext): QueryExpr = {
    ctx match {
      case ctx: PrimaryExprContext => build(ctx.pexpr)
      case ctx: BinaryContext => build(ctx)
    }
  }

  def build(ctx: PexprContext): PrimaryExpr = {
    ctx match {
      case ctx: ParenContext => build(ctx)
      case ctx: NodeSetContext => build(ctx)
      case ctx: EmptyContext => build(ctx)
      case ctx: QueryResContext => build(ctx)
      case ctx: NodeNContext => build(ctx.nodeNameError())
    }
  }

  def build(ctx: BinaryContext): BinaryExpr = {
    BinaryExpr(build(ctx.l), ctx.op.getText, build(ctx.r))
  }

  def build(ctx: ParenContext): Paren = {
    Paren(build(ctx.expr()))
  }

  def build(ctx: NodeSetContext): NodeSet = {
    NodeSet(ctx.nodeNameError().map(build))
  }

  def build(ctx: EmptyContext): NodeEmpty = {
    NodeEmpty()
  }

  def build(ctx: QueryResContext): QueryName = {
    QueryName(buildId(ctx.id))
  }

  def build(ctx: NodeNameErrorContext): NodeNameError = {
    NodeNameError(build(ctx.nodeName()), ctx.ids.map(buildId))
  }

  def build(ctx: NodeNameContext): NodeName = {
    NodeName(ctx.ids.map(buildId), if (ctx.f != null) Some(ctx.f.getText) else None)
  }

  def buildId(t: Token): Id = {
    Id(t.getText.intern())
  }

  import scala.collection.JavaConverters._

  @inline
  private implicit def toNodeSeq[T](ns: java.lang.Iterable[T]): QueryNode.Seq[T] =
    QueryNode.seq[T](ns.asScala)

  @inline
  private implicit def toToken(n: TerminalNode): Token = n.getSymbol

  @inline
  private implicit def toLinkedSet[T](n: Vector[T]): ILinkedSet[T] = {
    var res = ilinkedSetEmpty[T]
    n.foreach(x => res = res + x)
    res
  }
}

object QueryBuilder {

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
      //Console.err.println(s"[$line, $column] $message")
      Console.err.println("[" + line + ", " + column + "] " + message)
      Console.err.flush()
    }
  }

  def apply(input: String,
            maxErrors: Natural = 0,
            reporter: Reporter = ConsoleReporter): Option[Model] = {
    class ParsingEscape extends RuntimeException

    val sr = new StringReader(input)
    val inputStream = new ANTLRInputStream(sr)
    val lexer = new Antlr4AQLexer(inputStream)
    val tokens = new CommonTokenStream(lexer)
    val parser = new Antlr4AQParser(tokens)
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
        reporter.error(line, charPositionInLine, start, msg + " (token=" + token.getType + ")")
        errors += 1
        if (maxErrors > 0 && errors >= maxErrors) {
          throw new ParsingEscape
        }
      }
    })
    val mfOpt: Option[ModelFileContext] =
      try {
        success = true
        Some(parser.modelFile())
      } catch {
        case t: Throwable => None
      }
    if (success)
      mfOpt.map(mf => new QueryBuilder().build(mf.model()))
    else
      None
  }
}