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
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38,
            T__38 = 39, T__39 = 40, T__40 = 41, T__41 = 42, T__42 = 43, T__43 = 44, T__44 = 45,
            T__45 = 46, T__46 = 47, INTEGER = 48, REAL = 49, STRING = 50, ID = 51, WS = 52, COMMENT = 53,
            LINE_COMMENT = 54, ERROR_CHAR = 55;
    public static final int
		RULE_modelFile = 0, RULE_model = 1, RULE_queryStmt = 2, RULE_expr = 3, 
		RULE_reachExprs = 4, RULE_withExpr = 5, RULE_regExpr = 6, RULE_primaryRExpr = 7, 
		RULE_filter = 8, RULE_pexpr = 9, RULE_nodeNameError = 10, RULE_errorId = 11, 
		RULE_nodeName = 12;
	public static final String[] ruleNames = {
		"modelFile", "model", "queryStmt", "expr", "reachExprs", "withExpr", "regExpr", 
		"primaryRExpr", "filter", "pexpr", "nodeNameError", "errorId", "nodeName"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'='", "'-'", "'union'", "'intersect'", "'reach'", "':'", "'forward'", 
		"'backward'", "'from'", "'to'", "'paths'", "'with'", "'some'", "'all'", 
		"'none'", "'('", "')'", "'*'", "'+'", "'?'", "','", "'|'", "'_'", "'node'", 
		"'NODE'", "'port'", "'PORT'", "'in-port'", "'IN-PORT'", "'out-port'",
            "'OUT-PORT'", "'error'", "'ERROR'", "'porterror'", "'PORTERROR'", "'flows'",
            "'FLOWS'", "'flow-source'", "'FLOW-SOURCE'", "'flow-sink'", "'FLOW-SINK'",
            "'flow-path'", "'FLOW-PATH'", "'{'", "'}'", "'''", "'.'"
    };
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null,
            "INTEGER", "REAL", "STRING", "ID", "WS", "COMMENT", "LINE_COMMENT", "ERROR_CHAR"
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
			setState(26);
			model();
			setState(27);
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
			setState(30); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(29);
				queryStmt();
				}
				}
				setState(32); 
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
			setState(34);
			((QueryStmtContext)_localctx).id = match(ID);
			setState(35);
			match(T__0);
			setState(36);
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
	public static class FilterExprContext extends ExprContext {
		public ExprContext l;
		public FilterContext op;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public FilterContext filter() {
			return getRuleContext(FilterContext.class,0);
		}
		public FilterExprContext(ExprContext ctx) { copyFrom(ctx); }
	}
	public static class PrimaryExprContext extends ExprContext {
		public PexprContext pexpr() {
			return getRuleContext(PexprContext.class,0);
		}
		public PrimaryExprContext(ExprContext ctx) { copyFrom(ctx); }
	}
	public static class ReachExContext extends ExprContext {
		public ReachExprsContext reachExprs() {
			return getRuleContext(ReachExprsContext.class,0);
		}
		public ReachExContext(ExprContext ctx) { copyFrom(ctx); }
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
			setState(42);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__15:
			case T__17:
                case T__43:
                case T__45:
                case ID:
				{
				_localctx = new PrimaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(39);
				pexpr();
				}
				break;
			case T__4:
				{
				_localctx = new ReachExContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(40);
				match(T__4);
				setState(41);
				reachExprs();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(55);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(53);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
						((BinaryContext)_localctx).l = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(44);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(45);
						((BinaryContext)_localctx).op = match(T__1);
						setState(46);
						((BinaryContext)_localctx).r = expr(5);
						}
						break;
					case 2:
						{
						_localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
						((BinaryContext)_localctx).l = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(47);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(48);
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
						setState(49);
						((BinaryContext)_localctx).r = expr(4);
						}
						break;
					case 3:
						{
						_localctx = new FilterExprContext(new ExprContext(_parentctx, _parentState));
						((FilterExprContext)_localctx).l = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(50);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(51);
						match(T__5);
						setState(52);
						((FilterExprContext)_localctx).op = filter();
						}
						break;
					}
					} 
				}
				setState(57);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
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

	public static class ReachExprsContext extends ParserRuleContext {
		public ReachExprsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reachExprs; }
	 
		public ReachExprsContext() { }
		public void copyFrom(ReachExprsContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PathContext extends ReachExprsContext {
		public ExprContext s;
		public ExprContext t;
		public WithExprContext we;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public WithExprContext withExpr() {
			return getRuleContext(WithExprContext.class,0);
		}
		public PathContext(ReachExprsContext ctx) { copyFrom(ctx); }
	}
	public static class BackwardContext extends ReachExprsContext {
		public ExprContext e;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public BackwardContext(ReachExprsContext ctx) { copyFrom(ctx); }
	}
	public static class ChopContext extends ReachExprsContext {
		public ExprContext s;
		public ExprContext t;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ChopContext(ReachExprsContext ctx) { copyFrom(ctx); }
	}
	public static class ForwardContext extends ReachExprsContext {
		public ExprContext e;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ForwardContext(ReachExprsContext ctx) { copyFrom(ctx); }
	}

	public final ReachExprsContext reachExprs() throws RecognitionException {
		ReachExprsContext _localctx = new ReachExprsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_reachExprs);
		try {
			setState(76);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
				_localctx = new ForwardContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(58);
				match(T__6);
				setState(59);
				((ForwardContext)_localctx).e = expr(0);
				}
				break;
			case T__7:
				_localctx = new BackwardContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(60);
				match(T__7);
				setState(61);
				((BackwardContext)_localctx).e = expr(0);
				}
				break;
			case T__8:
				_localctx = new ChopContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(62);
				match(T__8);
				setState(63);
				((ChopContext)_localctx).s = expr(0);
				setState(64);
				match(T__9);
				setState(65);
				((ChopContext)_localctx).t = expr(0);
				}
				break;
			case T__10:
				_localctx = new PathContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(67);
				match(T__10);
				setState(68);
				match(T__8);
				setState(69);
				((PathContext)_localctx).s = expr(0);
				setState(70);
				match(T__9);
				setState(71);
				((PathContext)_localctx).t = expr(0);
				setState(74);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
				case 1:
					{
					setState(72);
					match(T__11);
					setState(73);
					((PathContext)_localctx).we = withExpr();
					}
					break;
				}
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

	public static class WithExprContext extends ParserRuleContext {
		public WithExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_withExpr; }
	 
		public WithExprContext() { }
		public void copyFrom(WithExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class RegExConstraintContext extends WithExprContext {
		public RegExprContext regExpr() {
			return getRuleContext(RegExprContext.class,0);
		}
		public RegExConstraintContext(WithExprContext ctx) { copyFrom(ctx); }
	}
	public static class SimpleConstraintContext extends WithExprContext {
		public Token op;
		public ExprContext e;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public SimpleConstraintContext(WithExprContext ctx) { copyFrom(ctx); }
	}

	public final WithExprContext withExpr() throws RecognitionException {
		WithExprContext _localctx = new WithExprContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_withExpr);
		int _la;
		try {
			setState(84);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__12:
			case T__13:
			case T__14:
				_localctx = new SimpleConstraintContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(78);
				((SimpleConstraintContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__12) | (1L << T__13) | (1L << T__14))) != 0)) ) {
					((SimpleConstraintContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(79);
				match(T__15);
				setState(80);
				((SimpleConstraintContext)_localctx).e = expr(0);
				setState(81);
				match(T__16);
				}
				break;
			case T__15:
			case T__22:
			case ID:
				_localctx = new RegExConstraintContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(83);
				regExpr(0);
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

	public static class RegExprContext extends ParserRuleContext {
		public RegExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_regExpr; }
	 
		public RegExprContext() { }
		public void copyFrom(RegExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class RegExPrimaryContext extends RegExprContext {
		public PrimaryRExprContext primaryRExpr() {
			return getRuleContext(PrimaryRExprContext.class,0);
		}
		public RegExPrimaryContext(RegExprContext ctx) { copyFrom(ctx); }
	}
	public static class RegExUnionContext extends RegExprContext {
		public RegExprContext l;
		public Token op;
		public RegExprContext r;
		public List<RegExprContext> regExpr() {
			return getRuleContexts(RegExprContext.class);
		}
		public RegExprContext regExpr(int i) {
			return getRuleContext(RegExprContext.class,i);
		}
		public RegExUnionContext(RegExprContext ctx) { copyFrom(ctx); }
	}
	public static class RegExUnaryContext extends RegExprContext {
		public RegExprContext e;
		public Token op;
		public RegExprContext regExpr() {
			return getRuleContext(RegExprContext.class,0);
		}
		public RegExUnaryContext(RegExprContext ctx) { copyFrom(ctx); }
	}
	public static class RegExConcatContext extends RegExprContext {
		public RegExprContext l;
		public Token op;
		public RegExprContext r;
		public List<RegExprContext> regExpr() {
			return getRuleContexts(RegExprContext.class);
		}
		public RegExprContext regExpr(int i) {
			return getRuleContext(RegExprContext.class,i);
		}
		public RegExConcatContext(RegExprContext ctx) { copyFrom(ctx); }
	}

	public final RegExprContext regExpr() throws RecognitionException {
		return regExpr(0);
	}

	private RegExprContext regExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		RegExprContext _localctx = new RegExprContext(_ctx, _parentState);
		RegExprContext _prevctx = _localctx;
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_regExpr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new RegExPrimaryContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(87);
			primaryRExpr();
			}
			_ctx.stop = _input.LT(-1);
			setState(99);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(97);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
					case 1:
						{
						_localctx = new RegExConcatContext(new RegExprContext(_parentctx, _parentState));
						((RegExConcatContext)_localctx).l = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_regExpr);
						setState(89);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(90);
						((RegExConcatContext)_localctx).op = match(T__20);
						setState(91);
						((RegExConcatContext)_localctx).r = regExpr(4);
						}
						break;
					case 2:
						{
						_localctx = new RegExUnionContext(new RegExprContext(_parentctx, _parentState));
						((RegExUnionContext)_localctx).l = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_regExpr);
						setState(92);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(93);
						((RegExUnionContext)_localctx).op = match(T__21);
						setState(94);
						((RegExUnionContext)_localctx).r = regExpr(3);
						}
						break;
					case 3:
						{
						_localctx = new RegExUnaryContext(new RegExprContext(_parentctx, _parentState));
						((RegExUnaryContext)_localctx).e = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_regExpr);
						setState(95);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(96);
						((RegExUnaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << T__19))) != 0)) ) {
							((RegExUnaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
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
				setState(101);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
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

	public static class PrimaryRExprContext extends ParserRuleContext {
		public PrimaryRExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primaryRExpr; }
	 
		public PrimaryRExprContext() { }
		public void copyFrom(PrimaryRExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class RegExParenContext extends PrimaryRExprContext {
		public RegExprContext regExpr() {
			return getRuleContext(RegExprContext.class,0);
		}
		public RegExParenContext(PrimaryRExprContext ctx) { copyFrom(ctx); }
	}
	public static class IdContext extends PrimaryRExprContext {
		public NodeNameErrorContext nodeNameError() {
			return getRuleContext(NodeNameErrorContext.class,0);
		}
		public IdContext(PrimaryRExprContext ctx) { copyFrom(ctx); }
	}
	public static class AnyContext extends PrimaryRExprContext {
		public AnyContext(PrimaryRExprContext ctx) { copyFrom(ctx); }
	}

	public final PrimaryRExprContext primaryRExpr() throws RecognitionException {
		PrimaryRExprContext _localctx = new PrimaryRExprContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_primaryRExpr);
		try {
			setState(108);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				_localctx = new IdContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(102);
				nodeNameError();
				}
				break;
			case T__15:
				_localctx = new RegExParenContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(103);
				match(T__15);
				setState(104);
				regExpr(0);
				setState(105);
				match(T__16);
				}
				break;
			case T__22:
				_localctx = new AnyContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(107);
				match(T__22);
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

	public static class FilterContext extends ParserRuleContext {
		public FilterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_filter; }
	}

	public final FilterContext filter() throws RecognitionException {
		FilterContext _localctx = new FilterContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_filter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			_la = _input.LA(1);
                if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << T__42))) != 0))) {
                    _errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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
		enterRule(_localctx, 18, RULE_pexpr);
		int _la;
		try {
			setState(130);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				_localctx = new NodeNContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(112);
				nodeNameError();
				}
				break;
			case T__15:
				_localctx = new ParenContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(113);
				match(T__15);
				setState(114);
				expr(0);
				setState(115);
				match(T__16);
				}
				break;
                case T__43:
                    _localctx = new NodeSetContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(117);
                    match(T__43);
                    setState(118);
				nodeNameError();
				setState(121); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(119);
					match(T__20);
					setState(120);
					nodeNameError();
					}
					}
					setState(123); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__20 );
				setState(125);
                    match(T__44);
                }
				break;
			case T__17:
				_localctx = new EmptyContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(127);
				match(T__17);
				}
				break;
                case T__45:
                    _localctx = new QueryResContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(128);
                    match(T__45);
                    setState(129);
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
		public NodeNameContext nodeName() {
			return getRuleContext(NodeNameContext.class,0);
		}
		public List<ErrorIdContext> errorId() {
			return getRuleContexts(ErrorIdContext.class);
		}
		public ErrorIdContext errorId(int i) {
			return getRuleContext(ErrorIdContext.class,i);
		}
		public NodeNameErrorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nodeNameError; }
	}

	public final NodeNameErrorContext nodeNameError() throws RecognitionException {
		NodeNameErrorContext _localctx = new NodeNameErrorContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_nodeNameError);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			nodeName();
			setState(144);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(133);
                    match(T__43);
                    setState(134);
				errorId();
				setState(139);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__20) {
					{
					{
					setState(135);
					match(T__20);
					setState(136);
					errorId();
					}
					}
					setState(141);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(142);
                    match(T__44);
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
		public List<TerminalNode> ID() { return getTokens(Antlr4AQParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(Antlr4AQParser.ID, i);
		}
		public ErrorIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_errorId; }
	}

	public final ErrorIdContext errorId() throws RecognitionException {
		ErrorIdContext _localctx = new ErrorIdContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_errorId);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			((ErrorIdContext)_localctx).ID = match(ID);
			((ErrorIdContext)_localctx).ids.add(((ErrorIdContext)_localctx).ID);
			setState(151);
			_errHandler.sync(this);
			_la = _input.LA(1);
                while (_la == T__46) {
                    {
				{
				setState(147);
                    match(T__46);
                    setState(148);
				((ErrorIdContext)_localctx).ID = match(ID);
				((ErrorIdContext)_localctx).ids.add(((ErrorIdContext)_localctx).ID);
				}
				}
				setState(153);
				_errHandler.sync(this);
				_la = _input.LA(1);
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
		enterRule(_localctx, 24, RULE_nodeName);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			((NodeNameContext)_localctx).ID = match(ID);
			((NodeNameContext)_localctx).ids.add(((NodeNameContext)_localctx).ID);
			setState(159);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(155);
                        match(T__46);
                        setState(156);
					((NodeNameContext)_localctx).ID = match(ID);
					((NodeNameContext)_localctx).ids.add(((NodeNameContext)_localctx).ID);
					}
					} 
				}
				setState(161);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
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
		case 6:
			return regExpr_sempred((RegExprContext)_localctx, predIndex);
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
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean regExpr_sempred(RegExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 3);
		case 4:
			return precpred(_ctx, 2);
		case 5:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\39\u00a5\4\2\t\2\4" +
                    "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t" +
                    "\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\2\3\3\6\3!\n\3\r\3\16\3\"\3\4" +
                    "\3\4\3\4\3\4\3\5\3\5\3\5\3\5\5\5-\n\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5" +
                    "\3\5\7\58\n\5\f\5\16\5;\13\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3" +
                    "\6\3\6\3\6\3\6\3\6\3\6\5\6M\n\6\5\6O\n\6\3\7\3\7\3\7\3\7\3\7\3\7\5\7W" +
                    "\n\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\7\bd\n\b\f\b\16\bg\13" +
                    "\b\3\t\3\t\3\t\3\t\3\t\3\t\5\to\n\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\6\13|\n\13\r\13\16\13}\3\13\3\13\3\13\3\13\3\13\5\13"+
		"\u0085\n\13\3\f\3\f\3\f\3\f\3\f\7\f\u008c\n\f\f\f\16\f\u008f\13\f\3\f"+
		"\3\f\5\f\u0093\n\f\3\r\3\r\3\r\7\r\u0098\n\r\f\r\16\r\u009b\13\r\3\16"+
		"\3\16\3\16\7\16\u00a0\n\16\f\16\16\16\u00a3\13\16\3\16\2\4\b\16\17\2\4"+
                    "\6\b\n\f\16\20\22\24\26\30\32\2\6\3\2\5\6\3\2\17\21\3\2\24\26\3\2\32-" +
                    "\2\u00af\2\34\3\2\2\2\4 \3\2\2\2\6$\3\2\2\2\b,\3\2\2\2\nN\3\2\2\2\fV\3"+
		"\2\2\2\16X\3\2\2\2\20n\3\2\2\2\22p\3\2\2\2\24\u0084\3\2\2\2\26\u0086\3"+
		"\2\2\2\30\u0094\3\2\2\2\32\u009c\3\2\2\2\34\35\5\4\3\2\35\36\7\2\2\3\36"+
		"\3\3\2\2\2\37!\5\6\4\2 \37\3\2\2\2!\"\3\2\2\2\" \3\2\2\2\"#\3\2\2\2#\5"+
                    "\3\2\2\2$%\7\65\2\2%&\7\3\2\2&\'\5\b\5\2\'\7\3\2\2\2()\b\5\1\2)-\5\24" +
                    "\13\2*+\7\7\2\2+-\5\n\6\2,(\3\2\2\2,*\3\2\2\2-9\3\2\2\2./\f\6\2\2/\60" +
                    "\7\4\2\2\608\5\b\5\7\61\62\f\5\2\2\62\63\t\2\2\2\638\5\b\5\6\64\65\f\3" +
                    "\2\2\65\66\7\b\2\2\668\5\22\n\2\67.\3\2\2\2\67\61\3\2\2\2\67\64\3\2\2" +
                    "\28;\3\2\2\29\67\3\2\2\29:\3\2\2\2:\t\3\2\2\2;9\3\2\2\2<=\7\t\2\2=O\5" +
                    "\b\5\2>?\7\n\2\2?O\5\b\5\2@A\7\13\2\2AB\5\b\5\2BC\7\f\2\2CD\5\b\5\2DO" +
                    "\3\2\2\2EF\7\r\2\2FG\7\13\2\2GH\5\b\5\2HI\7\f\2\2IL\5\b\5\2JK\7\16\2\2" +
                    "KM\5\f\7\2LJ\3\2\2\2LM\3\2\2\2MO\3\2\2\2N<\3\2\2\2N>\3\2\2\2N@\3\2\2\2" +
                    "NE\3\2\2\2O\13\3\2\2\2PQ\t\3\2\2QR\7\22\2\2RS\5\b\5\2ST\7\23\2\2TW\3\2" +
                    "\2\2UW\5\16\b\2VP\3\2\2\2VU\3\2\2\2W\r\3\2\2\2XY\b\b\1\2YZ\5\20\t\2Ze" +
                    "\3\2\2\2[\\\f\5\2\2\\]\7\27\2\2]d\5\16\b\6^_\f\4\2\2_`\7\30\2\2`d\5\16" +
                    "\b\5ab\f\6\2\2bd\t\4\2\2c[\3\2\2\2c^\3\2\2\2ca\3\2\2\2dg\3\2\2\2ec\3\2" +
                    "\2\2ef\3\2\2\2f\17\3\2\2\2ge\3\2\2\2ho\5\26\f\2ij\7\22\2\2jk\5\16\b\2" +
                    "kl\7\23\2\2lo\3\2\2\2mo\7\31\2\2nh\3\2\2\2ni\3\2\2\2nm\3\2\2\2o\21\3\2" +
                    "\2\2pq\t\5\2\2q\23\3\2\2\2r\u0085\5\26\f\2st\7\22\2\2tu\5\b\5\2uv\7\23" +
                    "\2\2v\u0085\3\2\2\2wx\7.\2\2x{\5\26\f\2yz\7\27\2\2z|\5\26\f\2{y\3\2\2" +
                    "\2|}\3\2\2\2}{\3\2\2\2}~\3\2\2\2~\177\3\2\2\2\177\u0080\7/\2\2\u0080\u0085" +
                    "\3\2\2\2\u0081\u0085\7\24\2\2\u0082\u0083\7\60\2\2\u0083\u0085\7\65\2" +
                    "\2\u0084r\3\2\2\2\u0084s\3\2\2\2\u0084w\3\2\2\2\u0084\u0081\3\2\2\2\u0084" +
                    "\u0082\3\2\2\2\u0085\25\3\2\2\2\u0086\u0092\5\32\16\2\u0087\u0088\7.\2" +
                    "\2\u0088\u008d\5\30\r\2\u0089\u008a\7\27\2\2\u008a\u008c\5\30\r\2\u008b" +
                    "\u0089\3\2\2\2\u008c\u008f\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008e\3\2" +
                    "\2\2\u008e\u0090\3\2\2\2\u008f\u008d\3\2\2\2\u0090\u0091\7/\2\2\u0091" +
                    "\u0093\3\2\2\2\u0092\u0087\3\2\2\2\u0092\u0093\3\2\2\2\u0093\27\3\2\2" +
                    "\2\u0094\u0099\7\65\2\2\u0095\u0096\7\61\2\2\u0096\u0098\7\65\2\2\u0097" +
                    "\u0095\3\2\2\2\u0098\u009b\3\2\2\2\u0099\u0097\3\2\2\2\u0099\u009a\3\2" +
                    "\2\2\u009a\31\3\2\2\2\u009b\u0099\3\2\2\2\u009c\u00a1\7\65\2\2\u009d\u009e" +
                    "\7\61\2\2\u009e\u00a0\7\65\2\2\u009f\u009d\3\2\2\2\u00a0\u00a3\3\2\2\2" +
                    "\u00a1\u009f\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\33\3\2\2\2\u00a3\u00a1" +
                    "\3\2\2\2\22\",\679LNVcen}\u0084\u008d\u0092\u0099\u00a1";
    public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}