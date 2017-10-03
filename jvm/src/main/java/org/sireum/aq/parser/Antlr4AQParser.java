// Generated from /workspace-v3/sireum-v3/awas/jvm/src/main/resources/org/sireum/aq/parser/Antlr4AQ.g4 by ANTLR 4.7
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
    static {
        RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION);
    }

    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
            T__9 = 10, T__10 = 11, T__11 = 12, T__12 = 13, T__13 = 14, T__14 = 15, T__15 = 16, T__16 = 17,
            T__17 = 18, T__18 = 19, T__19 = 20, T__20 = 21, T__21 = 22, T__22 = 23, T__23 = 24,
            T__24 = 25, T__25 = 26, T__26 = 27, INTEGER = 28, REAL = 29, STRING = 30, ID = 31, WS = 32,
            COMMENT = 33, LINE_COMMENT = 34, ERROR_CHAR = 35;
    public static final int
            RULE_modelFile = 0, RULE_model = 1, RULE_queryStmt = 2, RULE_expr = 3,
            RULE_filter = 4, RULE_pexpr = 5, RULE_nodeNameError = 6, RULE_errorId = 7,
            RULE_nodeName = 8;
    public static final String[] ruleNames = {
            "modelFile", "model", "queryStmt", "expr", "filter", "pexpr", "nodeNameError",
            "errorId", "nodeName"
    };

    private static final String[] _LITERAL_NAMES = {
            null, "'='", "'-'", "'union'", "'intersect'", "'->'", "'~>'", "':'", "'node'",
            "'NODE'", "'port'", "'PORT'", "'in-port'", "'IN-PORT'", "'out-port'",
            "'OUT-PORT'", "'error'", "'ERROR'", "'porterror'", "'PORTERROR'", "'('",
            "')'", "'{'", "','", "'}'", "'*'", "'''", "'.'"
    };
    private static final String[] _SYMBOLIC_NAMES = {
            null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, "INTEGER", "REAL", "STRING", "ID", "WS", "COMMENT",
            "LINE_COMMENT", "ERROR_CHAR"
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
    public ATN getATN() {
        return _ATN;
    }

    public Antlr4AQParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class ModelFileContext extends ParserRuleContext {
        public ModelContext model() {
            return getRuleContext(ModelContext.class, 0);
        }

        public TerminalNode EOF() {
            return getToken(Antlr4AQParser.EOF, 0);
        }

        public ModelFileContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_modelFile;
        }
    }

    public final ModelFileContext modelFile() throws RecognitionException {
        ModelFileContext _localctx = new ModelFileContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_modelFile);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(18);
                model();
                setState(19);
                match(EOF);
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

    public static class ModelContext extends ParserRuleContext {
        public List<QueryStmtContext> queryStmt() {
            return getRuleContexts(QueryStmtContext.class);
        }

        public QueryStmtContext queryStmt(int i) {
            return getRuleContext(QueryStmtContext.class, i);
        }

        public ModelContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_model;
        }
    }

    public final ModelContext model() throws RecognitionException {
        ModelContext _localctx = new ModelContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_model);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(22);
                _errHandler.sync(this);
                _la = _input.LA(1);
                do {
                    {
                        {
                            setState(21);
                            queryStmt();
                        }
                    }
                    setState(24);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                } while (_la == ID);
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

    public static class QueryStmtContext extends ParserRuleContext {
        public Token id;

        public ExprContext expr() {
            return getRuleContext(ExprContext.class, 0);
        }

        public TerminalNode ID() {
            return getToken(Antlr4AQParser.ID, 0);
        }

        public QueryStmtContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_queryStmt;
        }
    }

    public final QueryStmtContext queryStmt() throws RecognitionException {
        QueryStmtContext _localctx = new QueryStmtContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_queryStmt);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(26);
                ((QueryStmtContext) _localctx).id = match(ID);
                setState(27);
                match(T__0);
                setState(28);
                expr(0);
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

    public static class ExprContext extends ParserRuleContext {
        public ExprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_expr;
        }

        public ExprContext() {
        }

        public void copyFrom(ExprContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class FilterExprContext extends ExprContext {
        public ExprContext l;
        public FilterContext op;

        public ExprContext expr() {
            return getRuleContext(ExprContext.class, 0);
        }

        public FilterContext filter() {
            return getRuleContext(FilterContext.class, 0);
        }

        public FilterExprContext(ExprContext ctx) {
            copyFrom(ctx);
        }
    }

    public static class PrimaryExprContext extends ExprContext {
        public PexprContext pexpr() {
            return getRuleContext(PexprContext.class, 0);
        }

        public PrimaryExprContext(ExprContext ctx) {
            copyFrom(ctx);
        }
    }

    public static class BinaryContext extends ExprContext {
        public ExprContext l;
        public Token op;
        public ExprContext r;

        public List<ExprContext> expr() {
            return getRuleContexts(ExprContext.class);
        }

        public ExprContext expr(int i) {
            return getRuleContext(ExprContext.class, i);
        }

        public BinaryContext(ExprContext ctx) {
            copyFrom(ctx);
        }
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

                    setState(31);
                    pexpr();
                }
                _ctx.stop = _input.LT(-1);
                setState(47);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        if (_parseListeners != null) triggerExitRuleEvent();
                        _prevctx = _localctx;
                        {
                            setState(45);
                            _errHandler.sync(this);
                            switch (getInterpreter().adaptivePredict(_input, 1, _ctx)) {
                                case 1: {
                                    _localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
                                    ((BinaryContext) _localctx).l = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                                    setState(33);
                                    if (!(precpred(_ctx, 4)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 4)");
                                    setState(34);
                                    ((BinaryContext) _localctx).op = match(T__1);
                                    setState(35);
                                    ((BinaryContext) _localctx).r = expr(5);
                                }
                                break;
                                case 2: {
                                    _localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
                                    ((BinaryContext) _localctx).l = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                                    setState(36);
                                    if (!(precpred(_ctx, 3)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 3)");
                                    setState(37);
                                    ((BinaryContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == T__2 || _la == T__3)) {
                                        ((BinaryContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(38);
                                    ((BinaryContext) _localctx).r = expr(4);
                                }
                                break;
                                case 3: {
                                    _localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
                                    ((BinaryContext) _localctx).l = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                                    setState(39);
                                    if (!(precpred(_ctx, 2)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 2)");
                                    setState(40);
                                    ((BinaryContext) _localctx).op = _input.LT(1);
                                    _la = _input.LA(1);
                                    if (!(_la == T__4 || _la == T__5)) {
                                        ((BinaryContext) _localctx).op = (Token) _errHandler.recoverInline(this);
                                    } else {
                                        if (_input.LA(1) == Token.EOF) matchedEOF = true;
                                        _errHandler.reportMatch(this);
                                        consume();
                                    }
                                    setState(41);
                                    ((BinaryContext) _localctx).r = expr(3);
                                }
                                break;
                                case 4: {
                                    _localctx = new FilterExprContext(new ExprContext(_parentctx, _parentState));
                                    ((FilterExprContext) _localctx).l = _prevctx;
                                    pushNewRecursionContext(_localctx, _startState, RULE_expr);
                                    setState(42);
                                    if (!(precpred(_ctx, 1)))
                                        throw new FailedPredicateException(this, "precpred(_ctx, 1)");
                                    setState(43);
                                    match(T__6);
                                    setState(44);
                                    ((FilterExprContext) _localctx).op = filter();
                                }
                                break;
                            }
                        }
                    }
                    setState(49);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            unrollRecursionContexts(_parentctx);
        }
        return _localctx;
    }

    public static class FilterContext extends ParserRuleContext {
        public FilterContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_filter;
        }
    }

    public final FilterContext filter() throws RecognitionException {
        FilterContext _localctx = new FilterContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_filter);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(50);
                _la = _input.LA(1);
                if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18))) != 0))) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
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

    public static class PexprContext extends ParserRuleContext {
        public PexprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_pexpr;
        }

        public PexprContext() {
        }

        public void copyFrom(PexprContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class NodeSetContext extends PexprContext {
        public List<NodeNameErrorContext> nodeNameError() {
            return getRuleContexts(NodeNameErrorContext.class);
        }

        public NodeNameErrorContext nodeNameError(int i) {
            return getRuleContext(NodeNameErrorContext.class, i);
        }

        public NodeSetContext(PexprContext ctx) {
            copyFrom(ctx);
        }
    }

    public static class EmptyContext extends PexprContext {
        public EmptyContext(PexprContext ctx) {
            copyFrom(ctx);
        }
    }

    public static class NodeNContext extends PexprContext {
        public NodeNameErrorContext nodeNameError() {
            return getRuleContext(NodeNameErrorContext.class, 0);
        }

        public NodeNContext(PexprContext ctx) {
            copyFrom(ctx);
        }
    }

    public static class QueryResContext extends PexprContext {
        public Token id;

        public TerminalNode ID() {
            return getToken(Antlr4AQParser.ID, 0);
        }

        public QueryResContext(PexprContext ctx) {
            copyFrom(ctx);
        }
    }

    public static class ParenContext extends PexprContext {
        public ExprContext expr() {
            return getRuleContext(ExprContext.class, 0);
        }

        public ParenContext(PexprContext ctx) {
            copyFrom(ctx);
        }
    }

    public final PexprContext pexpr() throws RecognitionException {
        PexprContext _localctx = new PexprContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_pexpr);
        int _la;
        try {
            setState(70);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case ID:
                    _localctx = new NodeNContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(52);
                    nodeNameError();
                }
                break;
                case T__19:
                    _localctx = new ParenContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(53);
                    match(T__19);
                    setState(54);
                    expr(0);
                    setState(55);
                    match(T__20);
                }
                break;
                case T__21:
                    _localctx = new NodeSetContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                {
                    setState(57);
                    match(T__21);
                    setState(58);
                    nodeNameError();
                    setState(61);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    do {
                        {
                            {
                                setState(59);
                                match(T__22);
                                setState(60);
                                nodeNameError();
                            }
                        }
                        setState(63);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                    } while (_la == T__22);
                    setState(65);
                    match(T__23);
                }
                break;
                case T__24:
                    _localctx = new EmptyContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                {
                    setState(67);
                    match(T__24);
                }
                break;
                case T__25:
                    _localctx = new QueryResContext(_localctx);
                    enterOuterAlt(_localctx, 5);
                {
                    setState(68);
                    match(T__25);
                    setState(69);
                    ((QueryResContext) _localctx).id = match(ID);
                }
                break;
                default:
                    throw new NoViableAltException(this);
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

    public static class NodeNameErrorContext extends ParserRuleContext {
        public NodeNameContext nodeName() {
            return getRuleContext(NodeNameContext.class, 0);
        }

        public List<ErrorIdContext> errorId() {
            return getRuleContexts(ErrorIdContext.class);
        }

        public ErrorIdContext errorId(int i) {
            return getRuleContext(ErrorIdContext.class, i);
        }

        public NodeNameErrorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_nodeNameError;
        }
    }

	public final NodeNameErrorContext nodeNameError() throws RecognitionException {
		NodeNameErrorContext _localctx = new NodeNameErrorContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_nodeNameError);
        int _la;
        try {
			enterOuterAlt(_localctx, 1);
            {
                setState(72);
                nodeName();
                setState(84);
                _errHandler.sync(this);
                switch (getInterpreter().adaptivePredict(_input, 6, _ctx)) {
                    case 1: {
                        setState(73);
                        match(T__21);
                        setState(74);
                        errorId();
                        setState(79);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == T__22) {
                            {
                                {
                                    setState(75);
                                    match(T__22);
                                    setState(76);
                                    errorId();
                                }
                            }
                            setState(81);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                        setState(82);
                        match(T__23);
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

    public static class ErrorIdContext extends ParserRuleContext {
        public Token ID;
        public List<Token> ids = new ArrayList<Token>();

        public List<TerminalNode> ID() {
            return getTokens(Antlr4AQParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(Antlr4AQParser.ID, i);
        }

        public ErrorIdContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_errorId;
        }
    }

    public final ErrorIdContext errorId() throws RecognitionException {
        ErrorIdContext _localctx = new ErrorIdContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_errorId);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(86);
                ((ErrorIdContext) _localctx).ID = match(ID);
                ((ErrorIdContext) _localctx).ids.add(((ErrorIdContext) _localctx).ID);
                setState(91);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == T__26) {
                    {
                        {
                            setState(87);
                            match(T__26);
                            setState(88);
                            ((ErrorIdContext) _localctx).ID = match(ID);
                            ((ErrorIdContext) _localctx).ids.add(((ErrorIdContext) _localctx).ID);
                        }
                    }
                    setState(93);
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

    public static class NodeNameContext extends ParserRuleContext {
        public Token ID;
        public List<Token> ids = new ArrayList<Token>();

        public List<TerminalNode> ID() {
            return getTokens(Antlr4AQParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(Antlr4AQParser.ID, i);
        }

        public NodeNameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_nodeName;
        }
    }

	public final NodeNameContext nodeName() throws RecognitionException {
		NodeNameContext _localctx = new NodeNameContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_nodeName);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(94);
                ((NodeNameContext) _localctx).ID = match(ID);
                ((NodeNameContext) _localctx).ids.add(((NodeNameContext) _localctx).ID);
                setState(99);
                _errHandler.sync(this);
                _alt = getInterpreter().adaptivePredict(_input, 8, _ctx);
                while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                    if (_alt == 1) {
                        {
                            {
                                setState(95);
                                match(T__26);
                                setState(96);
                                ((NodeNameContext) _localctx).ID = match(ID);
                                ((NodeNameContext) _localctx).ids.add(((NodeNameContext) _localctx).ID);
                            }
                        }
                    }
                    setState(101);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 8, _ctx);
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
            return precpred(_ctx, 4);
            case 1:
                return precpred(_ctx, 3);
            case 2:
                return precpred(_ctx, 2);
            case 3:
                return precpred(_ctx, 1);
        }
		return true;
    }

    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3%i\4\2\t\2\4\3\t\3" +
                    "\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2\3\2\3" +
                    "\3\6\3\31\n\3\r\3\16\3\32\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5" +
                    "\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5\60\n\5\f\5\16\5\63\13\5\3\6\3\6\3" +
                    "\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\6\7@\n\7\r\7\16\7A\3\7\3\7\3\7\3\7" +
                    "\3\7\5\7I\n\7\3\b\3\b\3\b\3\b\3\b\7\bP\n\b\f\b\16\bS\13\b\3\b\3\b\5\b" +
                    "W\n\b\3\t\3\t\3\t\7\t\\\n\t\f\t\16\t_\13\t\3\n\3\n\3\n\7\nd\n\n\f\n\16" +
                    "\ng\13\n\3\n\2\3\b\13\2\4\6\b\n\f\16\20\22\2\5\3\2\5\6\3\2\7\b\3\2\n\25" +
                    "\2m\2\24\3\2\2\2\4\30\3\2\2\2\6\34\3\2\2\2\b \3\2\2\2\n\64\3\2\2\2\fH" +
                    "\3\2\2\2\16J\3\2\2\2\20X\3\2\2\2\22`\3\2\2\2\24\25\5\4\3\2\25\26\7\2\2" +
                    "\3\26\3\3\2\2\2\27\31\5\6\4\2\30\27\3\2\2\2\31\32\3\2\2\2\32\30\3\2\2" +
                    "\2\32\33\3\2\2\2\33\5\3\2\2\2\34\35\7!\2\2\35\36\7\3\2\2\36\37\5\b\5\2" +
                    "\37\7\3\2\2\2 !\b\5\1\2!\"\5\f\7\2\"\61\3\2\2\2#$\f\6\2\2$%\7\4\2\2%\60" +
                    "\5\b\5\7&\'\f\5\2\2\'(\t\2\2\2(\60\5\b\5\6)*\f\4\2\2*+\t\3\2\2+\60\5\b" +
                    "\5\5,-\f\3\2\2-.\7\t\2\2.\60\5\n\6\2/#\3\2\2\2/&\3\2\2\2/)\3\2\2\2/,\3" +
                    "\2\2\2\60\63\3\2\2\2\61/\3\2\2\2\61\62\3\2\2\2\62\t\3\2\2\2\63\61\3\2" +
                    "\2\2\64\65\t\4\2\2\65\13\3\2\2\2\66I\5\16\b\2\678\7\26\2\289\5\b\5\29" +
                    ":\7\27\2\2:I\3\2\2\2;<\7\30\2\2<?\5\16\b\2=>\7\31\2\2>@\5\16\b\2?=\3\2" +
                    "\2\2@A\3\2\2\2A?\3\2\2\2AB\3\2\2\2BC\3\2\2\2CD\7\32\2\2DI\3\2\2\2EI\7" +
                    "\33\2\2FG\7\34\2\2GI\7!\2\2H\66\3\2\2\2H\67\3\2\2\2H;\3\2\2\2HE\3\2\2" +
                    "\2HF\3\2\2\2I\r\3\2\2\2JV\5\22\n\2KL\7\30\2\2LQ\5\20\t\2MN\7\31\2\2NP" +
                    "\5\20\t\2OM\3\2\2\2PS\3\2\2\2QO\3\2\2\2QR\3\2\2\2RT\3\2\2\2SQ\3\2\2\2" +
                    "TU\7\32\2\2UW\3\2\2\2VK\3\2\2\2VW\3\2\2\2W\17\3\2\2\2X]\7!\2\2YZ\7\35" +
                    "\2\2Z\\\7!\2\2[Y\3\2\2\2\\_\3\2\2\2][\3\2\2\2]^\3\2\2\2^\21\3\2\2\2_]" +
                    "\3\2\2\2`e\7!\2\2ab\7\35\2\2bd\7!\2\2ca\3\2\2\2dg\3\2\2\2ec\3\2\2\2ef" +
                    "\3\2\2\2f\23\3\2\2\2ge\3\2\2\2\13\32/\61AHQV]e";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
	}
}