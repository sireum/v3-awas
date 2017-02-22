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

// Generated from /Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/src/main/resources/org/sireum/aq/parser/Antlr4AQ.g4 by ANTLR 4.6
package org.sireum.aq.parser;

// @formatter:off

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Antlr4AQParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, INTEGER=20, REAL=21, STRING=22, ID=23, WS=24, COMMENT=25, 
		LINE_COMMENT=26, ERROR_CHAR=27;
	public static final int
		RULE_modelFile = 0, RULE_model = 1, RULE_queryStmt = 2, RULE_expr = 3, 
		RULE_pexpr = 4, RULE_nodeNameError = 5, RULE_nodeName = 6;
	public static final String[] ruleNames = {
		"modelFile", "model", "queryStmt", "expr", "pexpr", "nodeNameError", "nodeName"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'='", "'-'", "'union'", "'intersect'", "'->'", "'<-'", "'('", "')'", 
		"'{'", "','", "'}'", "'*'", "'''", "'.'", "':'", "'in'", "'out'", "'source'", 
		"'sink'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, "INTEGER", "REAL", "STRING", 
		"ID", "WS", "COMMENT", "LINE_COMMENT", "ERROR_CHAR"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Antlr4AQ.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public Antlr4AQParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ModelFileContext extends ParserRuleContext {
		public ModelContext model() {
			return getRuleContext(ModelContext.class,0);
		}
		public TerminalNode EOF() { return getToken(Antlr4AQParser.EOF, 0); }
		public ModelFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modelFile; }
	}

	public final ModelFileContext modelFile() throws RecognitionException {
		ModelFileContext _localctx = new ModelFileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_modelFile);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(14);
			model();
			setState(15);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModelContext extends ParserRuleContext {
		public List<QueryStmtContext> queryStmt() {
			return getRuleContexts(QueryStmtContext.class);
		}
		public QueryStmtContext queryStmt(int i) {
			return getRuleContext(QueryStmtContext.class,i);
		}
		public ModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_model; }
	}

	public final ModelContext model() throws RecognitionException {
		ModelContext _localctx = new ModelContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_model);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(17);
				queryStmt();
				}
				}
				setState(20); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==ID );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QueryStmtContext extends ParserRuleContext {
		public Token id;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode ID() { return getToken(Antlr4AQParser.ID, 0); }
		public QueryStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryStmt; }
	}

	public final QueryStmtContext queryStmt() throws RecognitionException {
		QueryStmtContext _localctx = new QueryStmtContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_queryStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22);
			((QueryStmtContext)_localctx).id = match(ID);
			setState(23);
			match(T__0);
			setState(24);
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PrimaryExprContext extends ExprContext {
		public PexprContext pexpr() {
			return getRuleContext(PexprContext.class,0);
		}
		public PrimaryExprContext(ExprContext ctx) { copyFrom(ctx); }
	}
	public static class BinaryContext extends ExprContext {
		public ExprContext l;
		public Token op;
		public ExprContext r;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public BinaryContext(ExprContext ctx) { copyFrom(ctx); }
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new PrimaryExprContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(27);
			pexpr();
			}
			_ctx.stop = _input.LT(-1);
			setState(40);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(38);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
						((BinaryContext)_localctx).l = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(29);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(30);
						((BinaryContext)_localctx).op = match(T__1);
						setState(31);
						((BinaryContext)_localctx).r = expr(4);
						}
						break;
					case 2:
						{
						_localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
						((BinaryContext)_localctx).l = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(32);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(33);
						((BinaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__2 || _la==T__3) ) {
							((BinaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(34);
						((BinaryContext)_localctx).r = expr(3);
						}
						break;
					case 3:
						{
						_localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
						((BinaryContext)_localctx).l = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(35);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(36);
						((BinaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__4 || _la==T__5) ) {
							((BinaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(37);
						((BinaryContext)_localctx).r = expr(2);
						}
						break;
					}
					} 
				}
				setState(42);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PexprContext extends ParserRuleContext {
		public PexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pexpr; }
	 
		public PexprContext() { }
		public void copyFrom(PexprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NodeSetContext extends PexprContext {
		public List<NodeNameErrorContext> nodeNameError() {
			return getRuleContexts(NodeNameErrorContext.class);
		}
		public NodeNameErrorContext nodeNameError(int i) {
			return getRuleContext(NodeNameErrorContext.class,i);
		}
		public NodeSetContext(PexprContext ctx) { copyFrom(ctx); }
	}
	public static class EmptyContext extends PexprContext {
		public EmptyContext(PexprContext ctx) { copyFrom(ctx); }
	}
	public static class NodeNContext extends PexprContext {
		public NodeNameErrorContext nodeNameError() {
			return getRuleContext(NodeNameErrorContext.class,0);
		}
		public NodeNContext(PexprContext ctx) { copyFrom(ctx); }
	}
	public static class QueryResContext extends PexprContext {
		public Token id;
		public TerminalNode ID() { return getToken(Antlr4AQParser.ID, 0); }
		public QueryResContext(PexprContext ctx) { copyFrom(ctx); }
	}
	public static class ParenContext extends PexprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ParenContext(PexprContext ctx) { copyFrom(ctx); }
	}

	public final PexprContext pexpr() throws RecognitionException {
		PexprContext _localctx = new PexprContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_pexpr);
		int _la;
		try {
			setState(61);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				_localctx = new NodeNContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(43);
				nodeNameError();
				}
				break;
			case T__6:
				_localctx = new ParenContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(44);
				match(T__6);
				setState(45);
				expr(0);
				setState(46);
				match(T__7);
				}
				break;
			case T__8:
				_localctx = new NodeSetContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(48);
				match(T__8);
				setState(49);
				nodeNameError();
				setState(52); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(50);
					match(T__9);
					setState(51);
					nodeNameError();
					}
					}
					setState(54); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__9 );
				setState(56);
				match(T__10);
				}
				break;
			case T__11:
				_localctx = new EmptyContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(58);
				match(T__11);
				}
				break;
			case T__12:
				_localctx = new QueryResContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(59);
				match(T__12);
				setState(60);
				((QueryResContext)_localctx).id = match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NodeNameErrorContext extends ParserRuleContext {
		public Token ID;
		public List<Token> ids = new ArrayList<Token>();
		public NodeNameContext nodeName() {
			return getRuleContext(NodeNameContext.class,0);
		}
		public List<TerminalNode> ID() { return getTokens(Antlr4AQParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(Antlr4AQParser.ID, i);
		}
		public NodeNameErrorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nodeNameError; }
	}

	public final NodeNameErrorContext nodeNameError() throws RecognitionException {
		NodeNameErrorContext _localctx = new NodeNameErrorContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_nodeNameError);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			nodeName();
			setState(71);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(64);
				match(T__8);
				setState(66); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(65);
					((NodeNameErrorContext)_localctx).ID = match(ID);
					((NodeNameErrorContext)_localctx).ids.add(((NodeNameErrorContext)_localctx).ID);
					}
					}
					setState(68); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ID );
				setState(70);
				match(T__10);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NodeNameContext extends ParserRuleContext {
		public Token ID;
		public List<Token> ids = new ArrayList<Token>();
		public Token f;
		public List<TerminalNode> ID() { return getTokens(Antlr4AQParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(Antlr4AQParser.ID, i);
		}
		public NodeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nodeName; }
	}

	public final NodeNameContext nodeName() throws RecognitionException {
		NodeNameContext _localctx = new NodeNameContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_nodeName);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			((NodeNameContext)_localctx).ID = match(ID);
			((NodeNameContext)_localctx).ids.add(((NodeNameContext)_localctx).ID);
			setState(78);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(74);
					match(T__13);
					setState(75);
					((NodeNameContext)_localctx).ID = match(ID);
					((NodeNameContext)_localctx).ids.add(((NodeNameContext)_localctx).ID);
					}
					} 
				}
				setState(80);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			}
			setState(83);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(81);
				match(T__14);
				setState(82);
				((NodeNameContext)_localctx).f = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18))) != 0)) ) {
					((NodeNameContext)_localctx).f = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 3:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 3);
		case 1:
			return precpred(_ctx, 2);
		case 2:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\35X\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\3\2\3\2\3\3\6\3\25\n\3"+
		"\r\3\16\3\26\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\7\5)\n\5\f\5\16\5,\13\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\6\6"+
		"\67\n\6\r\6\16\68\3\6\3\6\3\6\3\6\3\6\5\6@\n\6\3\7\3\7\3\7\6\7E\n\7\r"+
		"\7\16\7F\3\7\5\7J\n\7\3\b\3\b\3\b\7\bO\n\b\f\b\16\bR\13\b\3\b\3\b\5\b"+
		"V\n\b\3\b\2\3\b\t\2\4\6\b\n\f\16\2\5\3\2\5\6\3\2\7\b\3\2\22\25]\2\20\3"+
		"\2\2\2\4\24\3\2\2\2\6\30\3\2\2\2\b\34\3\2\2\2\n?\3\2\2\2\fA\3\2\2\2\16"+
		"K\3\2\2\2\20\21\5\4\3\2\21\22\7\2\2\3\22\3\3\2\2\2\23\25\5\6\4\2\24\23"+
		"\3\2\2\2\25\26\3\2\2\2\26\24\3\2\2\2\26\27\3\2\2\2\27\5\3\2\2\2\30\31"+
		"\7\31\2\2\31\32\7\3\2\2\32\33\5\b\5\2\33\7\3\2\2\2\34\35\b\5\1\2\35\36"+
		"\5\n\6\2\36*\3\2\2\2\37 \f\5\2\2 !\7\4\2\2!)\5\b\5\6\"#\f\4\2\2#$\t\2"+
		"\2\2$)\5\b\5\5%&\f\3\2\2&\'\t\3\2\2\')\5\b\5\4(\37\3\2\2\2(\"\3\2\2\2"+
		"(%\3\2\2\2),\3\2\2\2*(\3\2\2\2*+\3\2\2\2+\t\3\2\2\2,*\3\2\2\2-@\5\f\7"+
		"\2./\7\t\2\2/\60\5\b\5\2\60\61\7\n\2\2\61@\3\2\2\2\62\63\7\13\2\2\63\66"+
		"\5\f\7\2\64\65\7\f\2\2\65\67\5\f\7\2\66\64\3\2\2\2\678\3\2\2\28\66\3\2"+
		"\2\289\3\2\2\29:\3\2\2\2:;\7\r\2\2;@\3\2\2\2<@\7\16\2\2=>\7\17\2\2>@\7"+
		"\31\2\2?-\3\2\2\2?.\3\2\2\2?\62\3\2\2\2?<\3\2\2\2?=\3\2\2\2@\13\3\2\2"+
		"\2AI\5\16\b\2BD\7\13\2\2CE\7\31\2\2DC\3\2\2\2EF\3\2\2\2FD\3\2\2\2FG\3"+
		"\2\2\2GH\3\2\2\2HJ\7\r\2\2IB\3\2\2\2IJ\3\2\2\2J\r\3\2\2\2KP\7\31\2\2L"+
		"M\7\20\2\2MO\7\31\2\2NL\3\2\2\2OR\3\2\2\2PN\3\2\2\2PQ\3\2\2\2QU\3\2\2"+
		"\2RP\3\2\2\2ST\7\21\2\2TV\t\4\2\2US\3\2\2\2UV\3\2\2\2V\17\3\2\2\2\13\26"+
		"(*8?FIPU";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}