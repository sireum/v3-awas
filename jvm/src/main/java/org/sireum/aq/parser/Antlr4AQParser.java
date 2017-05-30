// Generated from /workspace-v3/sireum-v3/awas/jvm/src/main/resources/org/sireum/aq/parser/Antlr4AQ.g4 by ANTLR 4.7
package org.sireum.aq.parser;

// @formatter:off

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Antlr4AQParser extends Parser {
	public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
            T__9 = 10, T__10 = 11, T__11 = 12, T__12 = 13, T__13 = 14, T__14 = 15, T__15 = 16, T__16 = 17,
            T__17 = 18, T__18 = 19, INTEGER = 20, REAL = 21, STRING = 22, ID = 23, WS = 24, COMMENT = 25,
            LINE_COMMENT=26, ERROR_CHAR=27;
	public static final int
            RULE_modelFile = 0, RULE_model = 1, RULE_queryStmt = 2, RULE_expr = 3,
            RULE_pexpr = 4, RULE_nodeNameError = 5, RULE_errorId = 6, RULE_nodeName = 7;
    public static final String[] ruleNames = {
            "modelFile", "model", "queryStmt", "expr", "pexpr", "nodeNameError", "errorId",
            "nodeName"
    };
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\35f\4\2\t\2\4\3\t" +
                    "\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2\3\3\6\3" +
                    "\27\n\3\r\3\16\3\30\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3" +
                    "\5\3\5\3\5\3\5\7\5+\n\5\f\5\16\5.\13\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6" +
                    "\3\6\6\69\n\6\r\6\16\6:\3\6\3\6\3\6\3\6\3\6\5\6B\n\6\3\7\3\7\3\7\3\7\3" +
                    "\7\7\7I\n\7\f\7\16\7L\13\7\3\7\3\7\5\7P\n\7\3\b\3\b\3\b\7\bU\n\b\f\b\16" +
                    "\bX\13\b\3\t\3\t\3\t\7\t]\n\t\f\t\16\t`\13\t\3\t\3\t\5\td\n\t\3\t\2\3" +
                    "\b\n\2\4\6\b\n\f\16\20\2\5\3\2\5\6\3\2\7\b\3\2\22\25\2k\2\22\3\2\2\2\4" +
                    "\26\3\2\2\2\6\32\3\2\2\2\b\36\3\2\2\2\nA\3\2\2\2\fC\3\2\2\2\16Q\3\2\2" +
                    "\2\20Y\3\2\2\2\22\23\5\4\3\2\23\24\7\2\2\3\24\3\3\2\2\2\25\27\5\6\4\2" +
                    "\26\25\3\2\2\2\27\30\3\2\2\2\30\26\3\2\2\2\30\31\3\2\2\2\31\5\3\2\2\2" +
                    "\32\33\7\31\2\2\33\34\7\3\2\2\34\35\5\b\5\2\35\7\3\2\2\2\36\37\b\5\1\2" +
                    "\37 \5\n\6\2 ,\3\2\2\2!\"\f\5\2\2\"#\7\4\2\2#+\5\b\5\6$%\f\4\2\2%&\t\2" +
                    "\2\2&+\5\b\5\5\'(\f\3\2\2()\t\3\2\2)+\5\b\5\4*!\3\2\2\2*$\3\2\2\2*\'\3" +
                    "\2\2\2+.\3\2\2\2,*\3\2\2\2,-\3\2\2\2-\t\3\2\2\2.,\3\2\2\2/B\5\f\7\2\60" +
                    "\61\7\t\2\2\61\62\5\b\5\2\62\63\7\n\2\2\63B\3\2\2\2\64\65\7\13\2\2\65" +
                    "8\5\f\7\2\66\67\7\f\2\2\679\5\f\7\28\66\3\2\2\29:\3\2\2\2:8\3\2\2\2:;" +
                    "\3\2\2\2;<\3\2\2\2<=\7\r\2\2=B\3\2\2\2>B\7\16\2\2?@\7\17\2\2@B\7\31\2" +
                    "\2A/\3\2\2\2A\60\3\2\2\2A\64\3\2\2\2A>\3\2\2\2A?\3\2\2\2B\13\3\2\2\2C" +
                    "O\5\20\t\2DE\7\13\2\2EJ\5\16\b\2FG\7\f\2\2GI\5\16\b\2HF\3\2\2\2IL\3\2" +
                    "\2\2JH\3\2\2\2JK\3\2\2\2KM\3\2\2\2LJ\3\2\2\2MN\7\r\2\2NP\3\2\2\2OD\3\2" +
                    "\2\2OP\3\2\2\2P\r\3\2\2\2QV\7\31\2\2RS\7\20\2\2SU\7\31\2\2TR\3\2\2\2U" +
                    "X\3\2\2\2VT\3\2\2\2VW\3\2\2\2W\17\3\2\2\2XV\3\2\2\2Y^\7\31\2\2Z[\7\20" +
                    "\2\2[]\7\31\2\2\\Z\3\2\2\2]`\3\2\2\2^\\\3\2\2\2^_\3\2\2\2_c\3\2\2\2`^" +
                    "\3\2\2\2ab\7\21\2\2bd\t\4\2\2ca\3\2\2\2cd\3\2\2\2d\21\3\2\2\2\f\30*,:" +
                    "AJOV^c";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
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

    static {
        RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

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

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }

    public Antlr4AQParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
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

	public final ModelFileContext modelFile() throws RecognitionException {
		ModelFileContext _localctx = new ModelFileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_modelFile);
		try {
			enterOuterAlt(_localctx, 1);
            {
                setState(16);
                model();
                setState(17);
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

	public final ModelContext model() throws RecognitionException {
		ModelContext _localctx = new ModelContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_model);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
            {
                setState(20);
                _errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
                    {
                        setState(19);
                        queryStmt();
				}
                }
                setState(22);
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

	public final QueryStmtContext queryStmt() throws RecognitionException {
		QueryStmtContext _localctx = new QueryStmtContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_queryStmt);
		try {
			enterOuterAlt(_localctx, 1);
            {
                setState(24);
                ((QueryStmtContext)_localctx).id = match(ID);
                setState(25);
                match(T__0);
                setState(26);
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

                setState(29);
                pexpr();
			}
			_ctx.stop = _input.LT(-1);
                setState(42);
                _errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
                    {
                        setState(40);
                        _errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
						((BinaryContext)_localctx).l = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
                            setState(31);
                            if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                            setState(32);
                            ((BinaryContext)_localctx).op = match(T__1);
                            setState(33);
                            ((BinaryContext)_localctx).r = expr(4);
						}
						break;
					case 2:
						{
						_localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
						((BinaryContext)_localctx).l = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
                            setState(34);
                            if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                            setState(35);
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
                            setState(36);
                            ((BinaryContext)_localctx).r = expr(3);
						}
						break;
					case 3:
						{
						_localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
						((BinaryContext)_localctx).l = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
                            setState(37);
                            if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
                            setState(38);
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
                            setState(39);
                            ((BinaryContext)_localctx).r = expr(2);
						}
						break;
                    }
                    }
                }
                setState(44);
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

	public final PexprContext pexpr() throws RecognitionException {
		PexprContext _localctx = new PexprContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_pexpr);
		int _la;
        try {
            setState(63);
            _errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				_localctx = new NodeNContext(_localctx);
				enterOuterAlt(_localctx, 1);
            {
                setState(45);
                nodeNameError();
				}
				break;
			case T__6:
				_localctx = new ParenContext(_localctx);
				enterOuterAlt(_localctx, 2);
            {
                setState(46);
                match(T__6);
                setState(47);
                expr(0);
                setState(48);
                match(T__7);
				}
				break;
			case T__8:
				_localctx = new NodeSetContext(_localctx);
				enterOuterAlt(_localctx, 3);
            {
                setState(50);
                match(T__8);
                setState(51);
                nodeNameError();
                setState(54);
                _errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
                        {
                            setState(52);
                            match(T__9);
                            setState(53);
                            nodeNameError();
					}
                    }
                    setState(56);
                    _errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__9);
                setState(58);
                match(T__10);
				}
				break;
			case T__11:
				_localctx = new EmptyContext(_localctx);
				enterOuterAlt(_localctx, 4);
            {
                setState(60);
                match(T__11);
				}
				break;
			case T__12:
				_localctx = new QueryResContext(_localctx);
				enterOuterAlt(_localctx, 5);
            {
                setState(61);
                match(T__12);
                setState(62);
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

	public final NodeNameErrorContext nodeNameError() throws RecognitionException {
		NodeNameErrorContext _localctx = new NodeNameErrorContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_nodeNameError);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
            {
                setState(65);
                nodeName();
                setState(77);
                _errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1: {
                setState(66);
                match(T__8);
                setState(67);
                errorId();
                setState(72);
                _errHandler.sync(this);
				_la = _input.LA(1);
                while (_la == T__9) {
                    {
                        {
                            setState(68);
                            match(T__9);
                            setState(69);
                            errorId();
                        }
                    }
                    setState(74);
                    _errHandler.sync(this);
					_la = _input.LA(1);
                }
                setState(75);
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

    public final ErrorIdContext errorId() throws RecognitionException {
        ErrorIdContext _localctx = new ErrorIdContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_errorId);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(79);
                ((ErrorIdContext) _localctx).ID = match(ID);
                ((ErrorIdContext) _localctx).ids.add(((ErrorIdContext) _localctx).ID);
                setState(84);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == T__13) {
                    {
                        {
                            setState(80);
                            match(T__13);
                            setState(81);
                            ((ErrorIdContext) _localctx).ID = match(ID);
                            ((ErrorIdContext) _localctx).ids.add(((ErrorIdContext) _localctx).ID);
                        }
                    }
                    setState(86);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

	public final NodeNameContext nodeName() throws RecognitionException {
		NodeNameContext _localctx = new NodeNameContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_nodeName);
        int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
            {
                setState(87);
                ((NodeNameContext)_localctx).ID = match(ID);
			((NodeNameContext)_localctx).ids.add(((NodeNameContext)_localctx).ID);
                setState(92);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 8,_ctx);
                while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
                        {
                            setState(88);
                            match(T__13);
                            setState(89);
                            ((NodeNameContext)_localctx).ID = match(ID);
					((NodeNameContext)_localctx).ids.add(((NodeNameContext)_localctx).ID);
                        }
                    }
                }
                setState(94);
                    _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 8,_ctx);
            }
                setState(97);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 9, _ctx) ) {
                    case 1: {
                setState(95);
                        match(T__14);
                setState(96);
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

    public static class ModelFileContext extends ParserRuleContext {
        public ModelFileContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public ModelContext model() {
            return getRuleContext(ModelContext.class, 0);
        }

        public TerminalNode EOF() {
            return getToken(Antlr4AQParser.EOF, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_modelFile;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).enterModelFile(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).exitModelFile(this);
        }
    }

    public static class ModelContext extends ParserRuleContext {
        public ModelContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<QueryStmtContext> queryStmt() {
            return getRuleContexts(QueryStmtContext.class);
        }

        public QueryStmtContext queryStmt(int i) {
            return getRuleContext(QueryStmtContext.class, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_model;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).enterModel(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).exitModel(this);
        }
    }

    public static class QueryStmtContext extends ParserRuleContext {
        public Token id;

        public QueryStmtContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public ExprContext expr() {
            return getRuleContext(ExprContext.class, 0);
        }

        public TerminalNode ID() {
            return getToken(Antlr4AQParser.ID, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_queryStmt;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).enterQueryStmt(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).exitQueryStmt(this);
        }
    }

    public static class ExprContext extends ParserRuleContext {
        public ExprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public ExprContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_expr;
        }

        public void copyFrom(ExprContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class PrimaryExprContext extends ExprContext {
        public PrimaryExprContext(ExprContext ctx) {
            copyFrom(ctx);
        }

        public PexprContext pexpr() {
            return getRuleContext(PexprContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).enterPrimaryExpr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).exitPrimaryExpr(this);
        }
    }

    public static class BinaryContext extends ExprContext {
        public ExprContext l;
        public Token op;
        public ExprContext r;

        public BinaryContext(ExprContext ctx) {
            copyFrom(ctx);
        }

        public List<ExprContext> expr() {
            return getRuleContexts(ExprContext.class);
        }

        public ExprContext expr(int i) {
            return getRuleContext(ExprContext.class, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).enterBinary(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).exitBinary(this);
        }
    }

    public static class PexprContext extends ParserRuleContext {
        public PexprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public PexprContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_pexpr;
        }

        public void copyFrom(PexprContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class NodeSetContext extends PexprContext {
        public NodeSetContext(PexprContext ctx) {
            copyFrom(ctx);
        }

        public List<NodeNameErrorContext> nodeNameError() {
            return getRuleContexts(NodeNameErrorContext.class);
        }

        public NodeNameErrorContext nodeNameError(int i) {
            return getRuleContext(NodeNameErrorContext.class, i);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).enterNodeSet(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).exitNodeSet(this);
        }
    }

    public static class EmptyContext extends PexprContext {
        public EmptyContext(PexprContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).enterEmpty(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).exitEmpty(this);
        }
    }

    public static class NodeNContext extends PexprContext {
        public NodeNContext(PexprContext ctx) {
            copyFrom(ctx);
        }

        public NodeNameErrorContext nodeNameError() {
            return getRuleContext(NodeNameErrorContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).enterNodeN(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).exitNodeN(this);
        }
    }

    public static class QueryResContext extends PexprContext {
        public Token id;

        public QueryResContext(PexprContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode ID() {
            return getToken(Antlr4AQParser.ID, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).enterQueryRes(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).exitQueryRes(this);
        }
    }

    public static class ParenContext extends PexprContext {
        public ParenContext(PexprContext ctx) {
            copyFrom(ctx);
        }

        public ExprContext expr() {
            return getRuleContext(ExprContext.class, 0);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).enterParen(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).exitParen(this);
        }
    }

    public static class NodeNameErrorContext extends ParserRuleContext {
        public NodeNameErrorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public NodeNameContext nodeName() {
            return getRuleContext(NodeNameContext.class, 0);
        }

        public List<ErrorIdContext> errorId() {
            return getRuleContexts(ErrorIdContext.class);
        }

        public ErrorIdContext errorId(int i) {
            return getRuleContext(ErrorIdContext.class, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_nodeNameError;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).enterNodeNameError(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).exitNodeNameError(this);
        }
    }

    public static class ErrorIdContext extends ParserRuleContext {
        public Token ID;
        public List<Token> ids = new ArrayList<Token>();

        public ErrorIdContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<TerminalNode> ID() {
            return getTokens(Antlr4AQParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(Antlr4AQParser.ID, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_errorId;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).enterErrorId(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).exitErrorId(this);
        }
    }

    public static class NodeNameContext extends ParserRuleContext {
        public Token ID;
        public List<Token> ids = new ArrayList<Token>();
        public Token f;

        public NodeNameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<TerminalNode> ID() {
            return getTokens(Antlr4AQParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(Antlr4AQParser.ID, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_nodeName;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).enterNodeName(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AQListener) ((Antlr4AQListener) listener).exitNodeName(this);
        }
	}
}