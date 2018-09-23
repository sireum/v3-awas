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

// Generated from /workspace-v3/sireum-v3/awas/jvm/src/main/resources/org/sireum/aq/parser/Antlr4AQ.g4 by ANTLR 4.7
package org.sireum.aq.parser;

// @formatter:off

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Antlr4AQParser}.
 */
public interface Antlr4AQListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link Antlr4AQParser#modelFile}.
     *
     * @param ctx the parse tree
     */
    void enterModelFile(Antlr4AQParser.ModelFileContext ctx);

    /**
     * Exit a parse tree produced by {@link Antlr4AQParser#modelFile}.
     *
     * @param ctx the parse tree
     */
    void exitModelFile(Antlr4AQParser.ModelFileContext ctx);

    /**
     * Enter a parse tree produced by {@link Antlr4AQParser#model}.
     *
     * @param ctx the parse tree
     */
    void enterModel(Antlr4AQParser.ModelContext ctx);

    /**
     * Exit a parse tree produced by {@link Antlr4AQParser#model}.
     *
     * @param ctx the parse tree
     */
    void exitModel(Antlr4AQParser.ModelContext ctx);

    /**
     * Enter a parse tree produced by {@link Antlr4AQParser#queryStmt}.
     *
     * @param ctx the parse tree
     */
    void enterQueryStmt(Antlr4AQParser.QueryStmtContext ctx);

    /**
     * Exit a parse tree produced by {@link Antlr4AQParser#queryStmt}.
     *
     * @param ctx the parse tree
     */
    void exitQueryStmt(Antlr4AQParser.QueryStmtContext ctx);

    /**
     * Enter a parse tree produced by the {@code PrimaryExpr}
     * labeled alternative in {@link Antlr4AQParser#expr}.
     *
     * @param ctx the parse tree
     */
    void enterPrimaryExpr(Antlr4AQParser.PrimaryExprContext ctx);

    /**
     * Exit a parse tree produced by the {@code PrimaryExpr}
     * labeled alternative in {@link Antlr4AQParser#expr}.
     *
     * @param ctx the parse tree
     */
    void exitPrimaryExpr(Antlr4AQParser.PrimaryExprContext ctx);

    /**
     * Enter a parse tree produced by the {@code Binary}
     * labeled alternative in {@link Antlr4AQParser#expr}.
     *
     * @param ctx the parse tree
     */
    void enterBinary(Antlr4AQParser.BinaryContext ctx);

    /**
     * Exit a parse tree produced by the {@code Binary}
     * labeled alternative in {@link Antlr4AQParser#expr}.
     *
     * @param ctx the parse tree
     */
    void exitBinary(Antlr4AQParser.BinaryContext ctx);

    /**
     * Enter a parse tree produced by the {@code NodeN}
     * labeled alternative in {@link Antlr4AQParser#pexpr}.
     *
     * @param ctx the parse tree
     */
    void enterNodeN(Antlr4AQParser.NodeNContext ctx);

    /**
     * Exit a parse tree produced by the {@code NodeN}
     * labeled alternative in {@link Antlr4AQParser#pexpr}.
     *
     * @param ctx the parse tree
     */
    void exitNodeN(Antlr4AQParser.NodeNContext ctx);

    /**
     * Enter a parse tree produced by the {@code Paren}
     * labeled alternative in {@link Antlr4AQParser#pexpr}.
     *
     * @param ctx the parse tree
     */
    void enterParen(Antlr4AQParser.ParenContext ctx);

    /**
     * Exit a parse tree produced by the {@code Paren}
     * labeled alternative in {@link Antlr4AQParser#pexpr}.
     *
     * @param ctx the parse tree
     */
    void exitParen(Antlr4AQParser.ParenContext ctx);

    /**
     * Enter a parse tree produced by the {@code NodeSet}
     * labeled alternative in {@link Antlr4AQParser#pexpr}.
     *
     * @param ctx the parse tree
     */
    void enterNodeSet(Antlr4AQParser.NodeSetContext ctx);

    /**
     * Exit a parse tree produced by the {@code NodeSet}
     * labeled alternative in {@link Antlr4AQParser#pexpr}.
     *
     * @param ctx the parse tree
     */
    void exitNodeSet(Antlr4AQParser.NodeSetContext ctx);

    /**
     * Enter a parse tree produced by the {@code Empty}
     * labeled alternative in {@link Antlr4AQParser#pexpr}.
     *
     * @param ctx the parse tree
     */
    void enterEmpty(Antlr4AQParser.EmptyContext ctx);

    /**
     * Exit a parse tree produced by the {@code Empty}
     * labeled alternative in {@link Antlr4AQParser#pexpr}.
     *
     * @param ctx the parse tree
     */
    void exitEmpty(Antlr4AQParser.EmptyContext ctx);

    /**
     * Enter a parse tree produced by the {@code QueryRes}
     * labeled alternative in {@link Antlr4AQParser#pexpr}.
     *
     * @param ctx the parse tree
     */
    void enterQueryRes(Antlr4AQParser.QueryResContext ctx);

    /**
     * Exit a parse tree produced by the {@code QueryRes}
     * labeled alternative in {@link Antlr4AQParser#pexpr}.
     *
     * @param ctx the parse tree
     */
    void exitQueryRes(Antlr4AQParser.QueryResContext ctx);

    /**
     * Enter a parse tree produced by {@link Antlr4AQParser#nodeNameError}.
     *
     * @param ctx the parse tree
     */
    void enterNodeNameError(Antlr4AQParser.NodeNameErrorContext ctx);

    /**
     * Exit a parse tree produced by {@link Antlr4AQParser#nodeNameError}.
     *
     * @param ctx the parse tree
     */
    void exitNodeNameError(Antlr4AQParser.NodeNameErrorContext ctx);

    /**
     * Enter a parse tree produced by {@link Antlr4AQParser#errorId}.
     *
     * @param ctx the parse tree
     */
    void enterErrorId(Antlr4AQParser.ErrorIdContext ctx);

    /**
     * Exit a parse tree produced by {@link Antlr4AQParser#errorId}.
     *
     * @param ctx the parse tree
     */
    void exitErrorId(Antlr4AQParser.ErrorIdContext ctx);

    /**
     * Enter a parse tree produced by {@link Antlr4AQParser#nodeName}.
     *
     * @param ctx the parse tree
     */
    void enterNodeName(Antlr4AQParser.NodeNameContext ctx);

    /**
     * Exit a parse tree produced by {@link Antlr4AQParser#nodeName}.
     *
     * @param ctx the parse tree
     */
    void exitNodeName(Antlr4AQParser.NodeNameContext ctx);
}