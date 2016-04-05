// Generated from /Volumes/User/hariharan/Documents/workspace-sireumV3/sireum-v3/awas/jvm/src/main/resources/org/sireum/awas/parser/Antlr4Awas.g4 by ANTLR 4.5.1
package org.sireum.awas.parser;

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
public class Antlr4AwasParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, INTEGER=44, REAL=45, 
		STRING=46, ID=47, WS=48, COMMENT=49, LINE_COMMENT=50, ERROR_CHAR=51;
	public static final int
		RULE_modelFile = 0, RULE_model = 1, RULE_typeDecl = 2, RULE_componentDecl = 3, 
		RULE_connectionDecl = 4, RULE_typeAliasDecl = 5, RULE_enumDecl = 6, RULE_latticeDecl = 7, 
		RULE_recordDecl = 8, RULE_field = 9, RULE_port = 10, RULE_flow = 11, RULE_property = 12, 
		RULE_constantDecl = 13, RULE_behaviour = 14, RULE_expression = 15, RULE_tuple = 16, 
		RULE_faultPort = 17, RULE_one = 18, RULE_fault = 19, RULE_type = 20, RULE_basicType = 21, 
		RULE_intConstant = 22, RULE_init = 23, RULE_mapEntry = 24, RULE_name = 25;
	public static final String[] ruleNames = {
		"modelFile", "model", "typeDecl", "componentDecl", "connectionDecl", "typeAliasDecl", 
		"enumDecl", "latticeDecl", "recordDecl", "field", "port", "flow", "property", 
		"constantDecl", "behaviour", "expression", "tuple", "faultPort", "one", 
		"fault", "type", "basicType", "intConstant", "init", "mapEntry", "name"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'types'", "'constants'", "'components'", "'connections'", "'ports'", 
		"'flows'", "'behaviour'", "'properties'", "':'", "'.'", "'{'", "','", 
		"'}'", "'->'", "'alias'", "'='", "'enum'", "'extends'", "'lattice'", "'record'", 
		"'in'", "'out'", "'_'", "'('", "')'", "'*'", "'Option'", "'['", "']'", 
		"'Set'", "'Seq'", "'Map'", "'Boolean'", "'Integer'", "'Real'", "'String'", 
		"'Component'", "'Port'", "'true'", "'false'", "'None'", "'Some'", "'::'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
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
	public String getGrammarFileName() { return "Antlr4Awas.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public Antlr4AwasParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ModelFileContext extends ParserRuleContext {
		public ModelContext model() {
			return getRuleContext(ModelContext.class,0);
		}
		public TerminalNode EOF() { return getToken(Antlr4AwasParser.EOF, 0); }
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
			setState(52);
			model();
			setState(53);
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
		public List<TypeDeclContext> typeDecl() {
			return getRuleContexts(TypeDeclContext.class);
		}
		public TypeDeclContext typeDecl(int i) {
			return getRuleContext(TypeDeclContext.class,i);
		}
		public List<ConstantDeclContext> constantDecl() {
			return getRuleContexts(ConstantDeclContext.class);
		}
		public ConstantDeclContext constantDecl(int i) {
			return getRuleContext(ConstantDeclContext.class,i);
		}
		public List<ComponentDeclContext> componentDecl() {
			return getRuleContexts(ComponentDeclContext.class);
		}
		public ComponentDeclContext componentDecl(int i) {
			return getRuleContext(ComponentDeclContext.class,i);
		}
		public List<ConnectionDeclContext> connectionDecl() {
			return getRuleContexts(ConnectionDeclContext.class);
		}
		public ConnectionDeclContext connectionDecl(int i) {
			return getRuleContext(ConnectionDeclContext.class,i);
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
			setState(62);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(55);
				match(T__0);
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__14) | (1L << T__16) | (1L << T__18) | (1L << T__19))) != 0)) {
					{
					{
					setState(56);
					typeDecl();
					}
					}
					setState(61);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(71);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(64);
				match(T__1);
				setState(68);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ID) {
					{
					{
					setState(65);
					constantDecl();
					}
					}
					setState(70);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(80);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(73);
				match(T__2);
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ID) {
					{
					{
					setState(74);
					componentDecl();
					}
					}
					setState(79);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(89);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(82);
				match(T__3);
				setState(86);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ID) {
					{
					{
					setState(83);
					connectionDecl();
					}
					}
					setState(88);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
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

	public static class TypeDeclContext extends ParserRuleContext {
		public TypeDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeDecl; }
	 
		public TypeDeclContext() { }
		public void copyFrom(TypeDeclContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class EnumTypeDeclContext extends TypeDeclContext {
		public EnumDeclContext enumDecl() {
			return getRuleContext(EnumDeclContext.class,0);
		}
		public EnumTypeDeclContext(TypeDeclContext ctx) { copyFrom(ctx); }
	}
	public static class LatticeTypeDeclContext extends TypeDeclContext {
		public LatticeDeclContext latticeDecl() {
			return getRuleContext(LatticeDeclContext.class,0);
		}
		public LatticeTypeDeclContext(TypeDeclContext ctx) { copyFrom(ctx); }
	}
	public static class AliasTypeDeclContext extends TypeDeclContext {
		public TypeAliasDeclContext typeAliasDecl() {
			return getRuleContext(TypeAliasDeclContext.class,0);
		}
		public AliasTypeDeclContext(TypeDeclContext ctx) { copyFrom(ctx); }
	}
	public static class RecordTypeDeclContext extends TypeDeclContext {
		public RecordDeclContext recordDecl() {
			return getRuleContext(RecordDeclContext.class,0);
		}
		public RecordTypeDeclContext(TypeDeclContext ctx) { copyFrom(ctx); }
	}

	public final TypeDeclContext typeDecl() throws RecognitionException {
		TypeDeclContext _localctx = new TypeDeclContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_typeDecl);
		try {
			setState(95);
			switch (_input.LA(1)) {
			case T__14:
				_localctx = new AliasTypeDeclContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(91);
				typeAliasDecl();
				}
				break;
			case T__16:
				_localctx = new EnumTypeDeclContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(92);
				enumDecl();
				}
				break;
			case T__18:
				_localctx = new LatticeTypeDeclContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(93);
				latticeDecl();
				}
				break;
			case T__19:
				_localctx = new RecordTypeDeclContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(94);
				recordDecl();
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

	public static class ComponentDeclContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public BehaviourContext behaviour() {
			return getRuleContext(BehaviourContext.class,0);
		}
		public List<PortContext> port() {
			return getRuleContexts(PortContext.class);
		}
		public PortContext port(int i) {
			return getRuleContext(PortContext.class,i);
		}
		public List<FlowContext> flow() {
			return getRuleContexts(FlowContext.class);
		}
		public FlowContext flow(int i) {
			return getRuleContext(FlowContext.class,i);
		}
		public List<PropertyContext> property() {
			return getRuleContexts(PropertyContext.class);
		}
		public PropertyContext property(int i) {
			return getRuleContext(PropertyContext.class,i);
		}
		public ComponentDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_componentDecl; }
	}

	public final ComponentDeclContext componentDecl() throws RecognitionException {
		ComponentDeclContext _localctx = new ComponentDeclContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_componentDecl);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			name();
			setState(105);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(98);
				match(T__4);
				setState(102);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__20 || _la==T__21) {
					{
					{
					setState(99);
					port();
					}
					}
					setState(104);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(114);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(107);
				match(T__5);
				setState(111);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(108);
						flow();
						}
						} 
					}
					setState(113);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				}
				}
			}

			setState(118);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(116);
				match(T__6);
				setState(117);
				behaviour();
				}
			}

			setState(127);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(120);
				match(T__7);
				setState(124);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(121);
						property();
						}
						} 
					}
					setState(126);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				}
				}
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

	public static class ConnectionDeclContext extends ParserRuleContext {
		public NameContext connName;
		public NameContext fromComponent;
		public Token fromPort;
		public NameContext name;
		public List<NameContext> fromE = new ArrayList<NameContext>();
		public NameContext toComponent;
		public Token toPort;
		public List<NameContext> toE = new ArrayList<NameContext>();
		public List<NameContext> name() {
			return getRuleContexts(NameContext.class);
		}
		public NameContext name(int i) {
			return getRuleContext(NameContext.class,i);
		}
		public List<TerminalNode> ID() { return getTokens(Antlr4AwasParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(Antlr4AwasParser.ID, i);
		}
		public BehaviourContext behaviour() {
			return getRuleContext(BehaviourContext.class,0);
		}
		public List<PropertyContext> property() {
			return getRuleContexts(PropertyContext.class);
		}
		public PropertyContext property(int i) {
			return getRuleContext(PropertyContext.class,i);
		}
		public ConnectionDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_connectionDecl; }
	}

	public final ConnectionDeclContext connectionDecl() throws RecognitionException {
		ConnectionDeclContext _localctx = new ConnectionDeclContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_connectionDecl);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			((ConnectionDeclContext)_localctx).connName = name();
			setState(130);
			match(T__8);
			setState(131);
			((ConnectionDeclContext)_localctx).fromComponent = name();
			setState(132);
			match(T__9);
			setState(133);
			((ConnectionDeclContext)_localctx).fromPort = match(ID);
			setState(145);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(134);
				match(T__10);
				setState(135);
				((ConnectionDeclContext)_localctx).name = name();
				((ConnectionDeclContext)_localctx).fromE.add(((ConnectionDeclContext)_localctx).name);
				setState(140);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(136);
					match(T__11);
					setState(137);
					((ConnectionDeclContext)_localctx).name = name();
					((ConnectionDeclContext)_localctx).fromE.add(((ConnectionDeclContext)_localctx).name);
					}
					}
					setState(142);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(143);
				match(T__12);
				}
			}

			setState(147);
			match(T__13);
			setState(148);
			((ConnectionDeclContext)_localctx).toComponent = name();
			setState(149);
			match(T__9);
			setState(150);
			((ConnectionDeclContext)_localctx).toPort = match(ID);
			setState(162);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(151);
				match(T__10);
				setState(152);
				((ConnectionDeclContext)_localctx).name = name();
				((ConnectionDeclContext)_localctx).toE.add(((ConnectionDeclContext)_localctx).name);
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(153);
					match(T__11);
					setState(154);
					((ConnectionDeclContext)_localctx).name = name();
					((ConnectionDeclContext)_localctx).toE.add(((ConnectionDeclContext)_localctx).name);
					}
					}
					setState(159);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(160);
				match(T__12);
				}
			}

			setState(166);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(164);
				match(T__6);
				setState(165);
				behaviour();
				}
			}

			setState(175);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(168);
				match(T__7);
				setState(172);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(169);
						property();
						}
						} 
					}
					setState(174);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				}
				}
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

	public static class TypeAliasDeclContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypeAliasDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeAliasDecl; }
	}

	public final TypeAliasDeclContext typeAliasDecl() throws RecognitionException {
		TypeAliasDeclContext _localctx = new TypeAliasDeclContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_typeAliasDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(177);
			match(T__14);
			setState(178);
			name();
			setState(179);
			match(T__15);
			setState(180);
			type();
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

	public static class EnumDeclContext extends ParserRuleContext {
		public NameContext n;
		public NameContext name;
		public List<NameContext> supers = new ArrayList<NameContext>();
		public Token ID;
		public List<Token> elements = new ArrayList<Token>();
		public List<NameContext> name() {
			return getRuleContexts(NameContext.class);
		}
		public NameContext name(int i) {
			return getRuleContext(NameContext.class,i);
		}
		public List<TerminalNode> ID() { return getTokens(Antlr4AwasParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(Antlr4AwasParser.ID, i);
		}
		public EnumDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumDecl; }
	}

	public final EnumDeclContext enumDecl() throws RecognitionException {
		EnumDeclContext _localctx = new EnumDeclContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_enumDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			match(T__16);
			setState(183);
			((EnumDeclContext)_localctx).n = name();
			setState(193);
			_la = _input.LA(1);
			if (_la==T__17) {
				{
				setState(184);
				match(T__17);
				setState(185);
				((EnumDeclContext)_localctx).name = name();
				((EnumDeclContext)_localctx).supers.add(((EnumDeclContext)_localctx).name);
				setState(190);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(186);
					match(T__11);
					setState(187);
					((EnumDeclContext)_localctx).name = name();
					((EnumDeclContext)_localctx).supers.add(((EnumDeclContext)_localctx).name);
					}
					}
					setState(192);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(205);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(195);
				match(T__10);
				setState(196);
				((EnumDeclContext)_localctx).ID = match(ID);
				((EnumDeclContext)_localctx).elements.add(((EnumDeclContext)_localctx).ID);
				setState(201);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(197);
					match(T__11);
					setState(198);
					((EnumDeclContext)_localctx).ID = match(ID);
					((EnumDeclContext)_localctx).elements.add(((EnumDeclContext)_localctx).ID);
					}
					}
					setState(203);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(204);
				match(T__12);
				}
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

	public static class LatticeDeclContext extends ParserRuleContext {
		public NameContext n;
		public NameContext name;
		public List<NameContext> supers = new ArrayList<NameContext>();
		public List<NameContext> name() {
			return getRuleContexts(NameContext.class);
		}
		public NameContext name(int i) {
			return getRuleContext(NameContext.class,i);
		}
		public LatticeDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_latticeDecl; }
	}

	public final LatticeDeclContext latticeDecl() throws RecognitionException {
		LatticeDeclContext _localctx = new LatticeDeclContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_latticeDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			match(T__18);
			setState(208);
			((LatticeDeclContext)_localctx).n = name();
			setState(218);
			_la = _input.LA(1);
			if (_la==T__17) {
				{
				setState(209);
				match(T__17);
				setState(210);
				((LatticeDeclContext)_localctx).name = name();
				((LatticeDeclContext)_localctx).supers.add(((LatticeDeclContext)_localctx).name);
				setState(215);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(211);
					match(T__11);
					setState(212);
					((LatticeDeclContext)_localctx).name = name();
					((LatticeDeclContext)_localctx).supers.add(((LatticeDeclContext)_localctx).name);
					}
					}
					setState(217);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
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

	public static class RecordDeclContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public List<FieldContext> field() {
			return getRuleContexts(FieldContext.class);
		}
		public FieldContext field(int i) {
			return getRuleContext(FieldContext.class,i);
		}
		public RecordDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_recordDecl; }
	}

	public final RecordDeclContext recordDecl() throws RecognitionException {
		RecordDeclContext _localctx = new RecordDeclContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_recordDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(220);
			match(T__19);
			setState(221);
			name();
			setState(223); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(222);
				field();
				}
				}
				setState(225); 
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

	public static class FieldContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(Antlr4AwasParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public FieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_field; }
	}

	public final FieldContext field() throws RecognitionException {
		FieldContext _localctx = new FieldContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_field);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(227);
			match(ID);
			setState(228);
			match(T__8);
			setState(229);
			type();
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

	public static class PortContext extends ParserRuleContext {
		public Token mod;
		public TerminalNode ID() { return getToken(Antlr4AwasParser.ID, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public PortContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_port; }
	}

	public final PortContext port() throws RecognitionException {
		PortContext _localctx = new PortContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_port);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			((PortContext)_localctx).mod = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==T__20 || _la==T__21) ) {
				((PortContext)_localctx).mod = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(232);
			match(ID);
			setState(235);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(233);
				match(T__8);
				setState(234);
				name();
				}
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

	public static class FlowContext extends ParserRuleContext {
		public Token id;
		public Token from;
		public NameContext name;
		public List<NameContext> fromE = new ArrayList<NameContext>();
		public Token to;
		public List<NameContext> toE = new ArrayList<NameContext>();
		public List<TerminalNode> ID() { return getTokens(Antlr4AwasParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(Antlr4AwasParser.ID, i);
		}
		public List<NameContext> name() {
			return getRuleContexts(NameContext.class);
		}
		public NameContext name(int i) {
			return getRuleContext(NameContext.class,i);
		}
		public FlowContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_flow; }
	}

	public final FlowContext flow() throws RecognitionException {
		FlowContext _localctx = new FlowContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_flow);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			((FlowContext)_localctx).id = match(ID);
			setState(238);
			match(T__8);
			setState(252);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(239);
				((FlowContext)_localctx).from = match(ID);
				setState(240);
				match(T__10);
				setState(241);
				((FlowContext)_localctx).name = name();
				((FlowContext)_localctx).fromE.add(((FlowContext)_localctx).name);
				setState(246);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(242);
					match(T__11);
					setState(243);
					((FlowContext)_localctx).name = name();
					((FlowContext)_localctx).fromE.add(((FlowContext)_localctx).name);
					}
					}
					setState(248);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(249);
				match(T__12);
				}
				break;
			case T__22:
				{
				setState(251);
				match(T__22);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(254);
			match(T__13);
			setState(268);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(255);
				((FlowContext)_localctx).to = match(ID);
				setState(256);
				match(T__10);
				setState(257);
				((FlowContext)_localctx).name = name();
				((FlowContext)_localctx).toE.add(((FlowContext)_localctx).name);
				setState(262);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(258);
					match(T__11);
					setState(259);
					((FlowContext)_localctx).name = name();
					((FlowContext)_localctx).toE.add(((FlowContext)_localctx).name);
					}
					}
					setState(264);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(265);
				match(T__12);
				}
				break;
			case T__22:
				{
				setState(267);
				match(T__22);
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class PropertyContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(Antlr4AwasParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public InitContext init() {
			return getRuleContext(InitContext.class,0);
		}
		public PropertyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_property; }
	}

	public final PropertyContext property() throws RecognitionException {
		PropertyContext _localctx = new PropertyContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_property);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			match(ID);
			setState(271);
			match(T__8);
			setState(272);
			type();
			setState(275);
			_la = _input.LA(1);
			if (_la==T__15) {
				{
				setState(273);
				match(T__15);
				setState(274);
				init();
				}
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

	public static class ConstantDeclContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public InitContext init() {
			return getRuleContext(InitContext.class,0);
		}
		public ConstantDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantDecl; }
	}

	public final ConstantDeclContext constantDecl() throws RecognitionException {
		ConstantDeclContext _localctx = new ConstantDeclContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_constantDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(277);
			name();
			setState(278);
			match(T__8);
			setState(279);
			type();
			setState(280);
			match(T__15);
			setState(281);
			init();
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

	public static class BehaviourContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public BehaviourContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_behaviour; }
	}

	public final BehaviourContext behaviour() throws RecognitionException {
		BehaviourContext _localctx = new BehaviourContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_behaviour);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(284); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(283);
					expression();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(286); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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

	public static class ExpressionContext extends ParserRuleContext {
		public TupleContext key;
		public TupleContext value;
		public List<TupleContext> tuple() {
			return getRuleContexts(TupleContext.class);
		}
		public TupleContext tuple(int i) {
			return getRuleContext(TupleContext.class,i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			((ExpressionContext)_localctx).key = tuple();
			setState(289);
			match(T__13);
			setState(290);
			((ExpressionContext)_localctx).value = tuple();
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

	public static class TupleContext extends ParserRuleContext {
		public List<FaultPortContext> faultPort() {
			return getRuleContexts(FaultPortContext.class);
		}
		public FaultPortContext faultPort(int i) {
			return getRuleContext(FaultPortContext.class,i);
		}
		public TupleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tuple; }
	}

	public final TupleContext tuple() throws RecognitionException {
		TupleContext _localctx = new TupleContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_tuple);
		int _la;
		try {
			setState(303);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(292);
				faultPort();
				}
				break;
			case T__23:
				enterOuterAlt(_localctx, 2);
				{
				setState(293);
				match(T__23);
				setState(294);
				faultPort();
				setState(297); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(295);
					match(T__11);
					setState(296);
					faultPort();
					}
					}
					setState(299); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__11 );
				setState(301);
				match(T__24);
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

	public static class FaultPortContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(Antlr4AwasParser.ID, 0); }
		public OneContext one() {
			return getRuleContext(OneContext.class,0);
		}
		public FaultPortContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_faultPort; }
	}

	public final FaultPortContext faultPort() throws RecognitionException {
		FaultPortContext _localctx = new FaultPortContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_faultPort);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(305);
			match(ID);
			setState(306);
			match(T__9);
			setState(307);
			one();
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

	public static class OneContext extends ParserRuleContext {
		public OneContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_one; }
	 
		public OneContext() { }
		public void copyFrom(OneContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NoFailureContext extends OneContext {
		public NoFailureContext(OneContext ctx) { copyFrom(ctx); }
	}
	public static class VariableContext extends OneContext {
		public TerminalNode ID() { return getToken(Antlr4AwasParser.ID, 0); }
		public VariableContext(OneContext ctx) { copyFrom(ctx); }
	}
	public static class WildCardContext extends OneContext {
		public WildCardContext(OneContext ctx) { copyFrom(ctx); }
	}
	public static class FaultRefContext extends OneContext {
		public FaultContext fault() {
			return getRuleContext(FaultContext.class,0);
		}
		public FaultRefContext(OneContext ctx) { copyFrom(ctx); }
	}
	public static class FaultSetContext extends OneContext {
		public List<FaultContext> fault() {
			return getRuleContexts(FaultContext.class);
		}
		public FaultContext fault(int i) {
			return getRuleContext(FaultContext.class,i);
		}
		public FaultSetContext(OneContext ctx) { copyFrom(ctx); }
	}

	public final OneContext one() throws RecognitionException {
		OneContext _localctx = new OneContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_one);
		int _la;
		try {
			setState(323);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				_localctx = new NoFailureContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(309);
				match(T__25);
				}
				break;
			case 2:
				_localctx = new WildCardContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(310);
				match(T__22);
				}
				break;
			case 3:
				_localctx = new VariableContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(311);
				match(ID);
				}
				break;
			case 4:
				_localctx = new FaultRefContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(312);
				fault();
				}
				break;
			case 5:
				_localctx = new FaultSetContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(313);
				match(T__10);
				setState(314);
				fault();
				setState(317); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(315);
					match(T__11);
					setState(316);
					fault();
					}
					}
					setState(319); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__11 );
				setState(321);
				match(T__12);
				}
				break;
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

	public static class FaultContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode ID() { return getToken(Antlr4AwasParser.ID, 0); }
		public FaultContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fault; }
	}

	public final FaultContext fault() throws RecognitionException {
		FaultContext _localctx = new FaultContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_fault);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(325);
			name();
			setState(326);
			match(T__9);
			setState(327);
			match(ID);
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

	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	 
		public TypeContext() { }
		public void copyFrom(TypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BaseTypeContext extends TypeContext {
		public BasicTypeContext basicType() {
			return getRuleContext(BasicTypeContext.class,0);
		}
		public BaseTypeContext(TypeContext ctx) { copyFrom(ctx); }
	}
	public static class SetTypeContext extends TypeContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public SetTypeContext(TypeContext ctx) { copyFrom(ctx); }
	}
	public static class SeqTypeContext extends TypeContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public SeqTypeContext(TypeContext ctx) { copyFrom(ctx); }
	}
	public static class OptionTypeContext extends TypeContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public OptionTypeContext(TypeContext ctx) { copyFrom(ctx); }
	}
	public static class MapTypeContext extends TypeContext {
		public TypeContext key;
		public TypeContext value;
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public MapTypeContext(TypeContext ctx) { copyFrom(ctx); }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_type);
		try {
			setState(352);
			switch (_input.LA(1)) {
			case T__32:
			case T__33:
			case T__34:
			case T__35:
			case T__36:
			case T__37:
			case ID:
				_localctx = new BaseTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(329);
				basicType();
				}
				break;
			case T__26:
				_localctx = new OptionTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(330);
				match(T__26);
				setState(331);
				match(T__27);
				setState(332);
				type();
				setState(333);
				match(T__28);
				}
				break;
			case T__29:
				_localctx = new SetTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(335);
				match(T__29);
				setState(336);
				match(T__27);
				setState(337);
				type();
				setState(338);
				match(T__28);
				}
				break;
			case T__30:
				_localctx = new SeqTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(340);
				match(T__30);
				setState(341);
				match(T__27);
				setState(342);
				type();
				setState(343);
				match(T__28);
				}
				break;
			case T__31:
				_localctx = new MapTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(345);
				match(T__31);
				setState(346);
				match(T__27);
				setState(347);
				((MapTypeContext)_localctx).key = type();
				setState(348);
				match(T__11);
				setState(349);
				((MapTypeContext)_localctx).value = type();
				setState(350);
				match(T__28);
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

	public static class BasicTypeContext extends ParserRuleContext {
		public BasicTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_basicType; }
	 
		public BasicTypeContext() { }
		public void copyFrom(BasicTypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IntegerTypeContext extends BasicTypeContext {
		public IntConstantContext lo;
		public IntConstantContext hi;
		public List<IntConstantContext> intConstant() {
			return getRuleContexts(IntConstantContext.class);
		}
		public IntConstantContext intConstant(int i) {
			return getRuleContext(IntConstantContext.class,i);
		}
		public IntegerTypeContext(BasicTypeContext ctx) { copyFrom(ctx); }
	}
	public static class ComponentTypeContext extends BasicTypeContext {
		public ComponentTypeContext(BasicTypeContext ctx) { copyFrom(ctx); }
	}
	public static class StringTypeContext extends BasicTypeContext {
		public StringTypeContext(BasicTypeContext ctx) { copyFrom(ctx); }
	}
	public static class NamedTypeContext extends BasicTypeContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public NamedTypeContext(BasicTypeContext ctx) { copyFrom(ctx); }
	}
	public static class BooleanTypeContext extends BasicTypeContext {
		public BooleanTypeContext(BasicTypeContext ctx) { copyFrom(ctx); }
	}
	public static class RealTypeContext extends BasicTypeContext {
		public RealTypeContext(BasicTypeContext ctx) { copyFrom(ctx); }
	}
	public static class PortTypeContext extends BasicTypeContext {
		public PortTypeContext(BasicTypeContext ctx) { copyFrom(ctx); }
	}

	public final BasicTypeContext basicType() throws RecognitionException {
		BasicTypeContext _localctx = new BasicTypeContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_basicType);
		int _la;
		try {
			setState(369);
			switch (_input.LA(1)) {
			case T__32:
				_localctx = new BooleanTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(354);
				match(T__32);
				}
				break;
			case T__33:
				_localctx = new IntegerTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(355);
				match(T__33);
				setState(362);
				_la = _input.LA(1);
				if (_la==T__23) {
					{
					setState(356);
					match(T__23);
					setState(357);
					((IntegerTypeContext)_localctx).lo = intConstant();
					setState(358);
					match(T__11);
					setState(359);
					((IntegerTypeContext)_localctx).hi = intConstant();
					setState(360);
					match(T__24);
					}
				}

				}
				break;
			case T__34:
				_localctx = new RealTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(364);
				match(T__34);
				}
				break;
			case T__35:
				_localctx = new StringTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(365);
				match(T__35);
				}
				break;
			case T__36:
				_localctx = new ComponentTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(366);
				match(T__36);
				}
				break;
			case T__37:
				_localctx = new PortTypeContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(367);
				match(T__37);
				}
				break;
			case ID:
				_localctx = new NamedTypeContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(368);
				name();
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

	public static class IntConstantContext extends ParserRuleContext {
		public IntConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intConstant; }
	 
		public IntConstantContext() { }
		public void copyFrom(IntConstantContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LitIntConstantContext extends IntConstantContext {
		public TerminalNode INTEGER() { return getToken(Antlr4AwasParser.INTEGER, 0); }
		public LitIntConstantContext(IntConstantContext ctx) { copyFrom(ctx); }
	}
	public static class ArbitraryIntConstantContext extends IntConstantContext {
		public ArbitraryIntConstantContext(IntConstantContext ctx) { copyFrom(ctx); }
	}
	public static class NamedIntConstantContext extends IntConstantContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public NamedIntConstantContext(IntConstantContext ctx) { copyFrom(ctx); }
	}

	public final IntConstantContext intConstant() throws RecognitionException {
		IntConstantContext _localctx = new IntConstantContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_intConstant);
		try {
			setState(374);
			switch (_input.LA(1)) {
			case INTEGER:
				_localctx = new LitIntConstantContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(371);
				match(INTEGER);
				}
				break;
			case ID:
				_localctx = new NamedIntConstantContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(372);
				name();
				}
				break;
			case T__22:
				_localctx = new ArbitraryIntConstantContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(373);
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

	public static class InitContext extends ParserRuleContext {
		public InitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_init; }
	 
		public InitContext() { }
		public void copyFrom(InitContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IntegerContext extends InitContext {
		public TerminalNode INTEGER() { return getToken(Antlr4AwasParser.INTEGER, 0); }
		public IntegerContext(InitContext ctx) { copyFrom(ctx); }
	}
	public static class NameRefContext extends InitContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode ID() { return getToken(Antlr4AwasParser.ID, 0); }
		public NameRefContext(InitContext ctx) { copyFrom(ctx); }
	}
	public static class SomeContext extends InitContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public InitContext init() {
			return getRuleContext(InitContext.class,0);
		}
		public SomeContext(InitContext ctx) { copyFrom(ctx); }
	}
	public static class SetContext extends InitContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<InitContext> init() {
			return getRuleContexts(InitContext.class);
		}
		public InitContext init(int i) {
			return getRuleContext(InitContext.class,i);
		}
		public SetContext(InitContext ctx) { copyFrom(ctx); }
	}
	public static class RealContext extends InitContext {
		public TerminalNode REAL() { return getToken(Antlr4AwasParser.REAL, 0); }
		public RealContext(InitContext ctx) { copyFrom(ctx); }
	}
	public static class TrueContext extends InitContext {
		public TrueContext(InitContext ctx) { copyFrom(ctx); }
	}
	public static class RecordContext extends InitContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public List<TerminalNode> ID() { return getTokens(Antlr4AwasParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(Antlr4AwasParser.ID, i);
		}
		public List<InitContext> init() {
			return getRuleContexts(InitContext.class);
		}
		public InitContext init(int i) {
			return getRuleContext(InitContext.class,i);
		}
		public RecordContext(InitContext ctx) { copyFrom(ctx); }
	}
	public static class FalseContext extends InitContext {
		public FalseContext(InitContext ctx) { copyFrom(ctx); }
	}
	public static class StringContext extends InitContext {
		public TerminalNode STRING() { return getToken(Antlr4AwasParser.STRING, 0); }
		public StringContext(InitContext ctx) { copyFrom(ctx); }
	}
	public static class NoneContext extends InitContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public NoneContext(InitContext ctx) { copyFrom(ctx); }
	}
	public static class MapContext extends InitContext {
		public TypeContext key;
		public TypeContext value;
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public List<MapEntryContext> mapEntry() {
			return getRuleContexts(MapEntryContext.class);
		}
		public MapEntryContext mapEntry(int i) {
			return getRuleContext(MapEntryContext.class,i);
		}
		public MapContext(InitContext ctx) { copyFrom(ctx); }
	}
	public static class SeqContext extends InitContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<InitContext> init() {
			return getRuleContexts(InitContext.class);
		}
		public InitContext init(int i) {
			return getRuleContext(InitContext.class,i);
		}
		public SeqContext(InitContext ctx) { copyFrom(ctx); }
	}

	public final InitContext init() throws RecognitionException {
		InitContext _localctx = new InitContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_init);
		int _la;
		try {
			setState(468);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				_localctx = new TrueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(376);
				match(T__38);
				}
				break;
			case 2:
				_localctx = new FalseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(377);
				match(T__39);
				}
				break;
			case 3:
				_localctx = new IntegerContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(378);
				match(INTEGER);
				}
				break;
			case 4:
				_localctx = new RealContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(379);
				match(REAL);
				}
				break;
			case 5:
				_localctx = new StringContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(380);
				match(STRING);
				}
				break;
			case 6:
				_localctx = new RecordContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(381);
				name();
				setState(382);
				match(T__23);
				setState(383);
				match(ID);
				setState(384);
				match(T__15);
				setState(385);
				init();
				setState(392);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(386);
					match(T__11);
					setState(387);
					match(ID);
					setState(388);
					match(T__15);
					setState(389);
					init();
					}
					}
					setState(394);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(395);
				match(T__24);
				}
				break;
			case 7:
				_localctx = new NameRefContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(397);
				name();
				setState(400);
				_la = _input.LA(1);
				if (_la==T__9) {
					{
					setState(398);
					match(T__9);
					setState(399);
					match(ID);
					}
				}

				}
				break;
			case 8:
				_localctx = new NoneContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(402);
				match(T__40);
				setState(403);
				match(T__27);
				setState(404);
				type();
				setState(405);
				match(T__28);
				}
				break;
			case 9:
				_localctx = new SomeContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(407);
				match(T__41);
				setState(408);
				match(T__27);
				setState(409);
				type();
				setState(410);
				match(T__28);
				setState(411);
				match(T__23);
				setState(412);
				init();
				setState(413);
				match(T__24);
				}
				break;
			case 10:
				_localctx = new SetContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(415);
				match(T__29);
				setState(416);
				match(T__27);
				setState(417);
				type();
				setState(418);
				match(T__28);
				setState(419);
				match(T__23);
				setState(428);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << INTEGER) | (1L << REAL) | (1L << STRING) | (1L << ID))) != 0)) {
					{
					setState(420);
					init();
					setState(425);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__11) {
						{
						{
						setState(421);
						match(T__11);
						setState(422);
						init();
						}
						}
						setState(427);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(430);
				match(T__24);
				}
				break;
			case 11:
				_localctx = new SeqContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(432);
				match(T__30);
				setState(433);
				match(T__27);
				setState(434);
				type();
				setState(435);
				match(T__28);
				setState(436);
				match(T__23);
				setState(445);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << INTEGER) | (1L << REAL) | (1L << STRING) | (1L << ID))) != 0)) {
					{
					setState(437);
					init();
					setState(442);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__11) {
						{
						{
						setState(438);
						match(T__11);
						setState(439);
						init();
						}
						}
						setState(444);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(447);
				match(T__24);
				}
				break;
			case 12:
				_localctx = new MapContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(449);
				match(T__31);
				setState(450);
				match(T__27);
				setState(451);
				((MapContext)_localctx).key = type();
				setState(452);
				match(T__11);
				setState(453);
				((MapContext)_localctx).value = type();
				setState(454);
				match(T__28);
				setState(455);
				match(T__23);
				setState(464);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << INTEGER) | (1L << REAL) | (1L << STRING) | (1L << ID))) != 0)) {
					{
					setState(456);
					mapEntry();
					setState(461);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__11) {
						{
						{
						setState(457);
						match(T__11);
						setState(458);
						mapEntry();
						}
						}
						setState(463);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(466);
				match(T__24);
				}
				break;
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

	public static class MapEntryContext extends ParserRuleContext {
		public InitContext key;
		public InitContext value;
		public List<InitContext> init() {
			return getRuleContexts(InitContext.class);
		}
		public InitContext init(int i) {
			return getRuleContext(InitContext.class,i);
		}
		public MapEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mapEntry; }
	}

	public final MapEntryContext mapEntry() throws RecognitionException {
		MapEntryContext _localctx = new MapEntryContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_mapEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(470);
			((MapEntryContext)_localctx).key = init();
			setState(471);
			match(T__13);
			setState(472);
			((MapEntryContext)_localctx).value = init();
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

	public static class NameContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(Antlr4AwasParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(Antlr4AwasParser.ID, i);
		}
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_name);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(474);
			match(ID);
			setState(479);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__42) {
				{
				{
				setState(475);
				match(T__42);
				setState(476);
				match(ID);
				}
				}
				setState(481);
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

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\65\u01e5\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\3\2\3\2\3\2\3\3\3\3\7\3<\n\3\f\3\16\3?\13\3\5\3A"+
		"\n\3\3\3\3\3\7\3E\n\3\f\3\16\3H\13\3\5\3J\n\3\3\3\3\3\7\3N\n\3\f\3\16"+
		"\3Q\13\3\5\3S\n\3\3\3\3\3\7\3W\n\3\f\3\16\3Z\13\3\5\3\\\n\3\3\4\3\4\3"+
		"\4\3\4\5\4b\n\4\3\5\3\5\3\5\7\5g\n\5\f\5\16\5j\13\5\5\5l\n\5\3\5\3\5\7"+
		"\5p\n\5\f\5\16\5s\13\5\5\5u\n\5\3\5\3\5\5\5y\n\5\3\5\3\5\7\5}\n\5\f\5"+
		"\16\5\u0080\13\5\5\5\u0082\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6"+
		"\u008d\n\6\f\6\16\6\u0090\13\6\3\6\3\6\5\6\u0094\n\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\7\6\u009e\n\6\f\6\16\6\u00a1\13\6\3\6\3\6\5\6\u00a5\n\6"+
		"\3\6\3\6\5\6\u00a9\n\6\3\6\3\6\7\6\u00ad\n\6\f\6\16\6\u00b0\13\6\5\6\u00b2"+
		"\n\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u00bf\n\b\f\b\16"+
		"\b\u00c2\13\b\5\b\u00c4\n\b\3\b\3\b\3\b\3\b\7\b\u00ca\n\b\f\b\16\b\u00cd"+
		"\13\b\3\b\5\b\u00d0\n\b\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u00d8\n\t\f\t\16\t"+
		"\u00db\13\t\5\t\u00dd\n\t\3\n\3\n\3\n\6\n\u00e2\n\n\r\n\16\n\u00e3\3\13"+
		"\3\13\3\13\3\13\3\f\3\f\3\f\3\f\5\f\u00ee\n\f\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\7\r\u00f7\n\r\f\r\16\r\u00fa\13\r\3\r\3\r\3\r\5\r\u00ff\n\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\7\r\u0107\n\r\f\r\16\r\u010a\13\r\3\r\3\r\3\r\5\r\u010f"+
		"\n\r\3\16\3\16\3\16\3\16\3\16\5\16\u0116\n\16\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\20\6\20\u011f\n\20\r\20\16\20\u0120\3\21\3\21\3\21\3\21\3\22\3"+
		"\22\3\22\3\22\3\22\6\22\u012c\n\22\r\22\16\22\u012d\3\22\3\22\5\22\u0132"+
		"\n\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\6\24"+
		"\u0140\n\24\r\24\16\24\u0141\3\24\3\24\5\24\u0146\n\24\3\25\3\25\3\25"+
		"\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u0163\n\26\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u016d\n\27\3\27\3\27\3\27\3\27"+
		"\3\27\5\27\u0174\n\27\3\30\3\30\3\30\5\30\u0179\n\30\3\31\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\7\31\u0189\n\31"+
		"\f\31\16\31\u018c\13\31\3\31\3\31\3\31\3\31\3\31\5\31\u0193\n\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\7\31\u01aa\n\31\f\31\16\31\u01ad\13\31"+
		"\5\31\u01af\n\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\7\31"+
		"\u01bb\n\31\f\31\16\31\u01be\13\31\5\31\u01c0\n\31\3\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\7\31\u01ce\n\31\f\31\16\31\u01d1"+
		"\13\31\5\31\u01d3\n\31\3\31\3\31\5\31\u01d7\n\31\3\32\3\32\3\32\3\32\3"+
		"\33\3\33\3\33\7\33\u01e0\n\33\f\33\16\33\u01e3\13\33\3\33\2\2\34\2\4\6"+
		"\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\2\3\3\2\27\30\u0219"+
		"\2\66\3\2\2\2\4@\3\2\2\2\6a\3\2\2\2\bc\3\2\2\2\n\u0083\3\2\2\2\f\u00b3"+
		"\3\2\2\2\16\u00b8\3\2\2\2\20\u00d1\3\2\2\2\22\u00de\3\2\2\2\24\u00e5\3"+
		"\2\2\2\26\u00e9\3\2\2\2\30\u00ef\3\2\2\2\32\u0110\3\2\2\2\34\u0117\3\2"+
		"\2\2\36\u011e\3\2\2\2 \u0122\3\2\2\2\"\u0131\3\2\2\2$\u0133\3\2\2\2&\u0145"+
		"\3\2\2\2(\u0147\3\2\2\2*\u0162\3\2\2\2,\u0173\3\2\2\2.\u0178\3\2\2\2\60"+
		"\u01d6\3\2\2\2\62\u01d8\3\2\2\2\64\u01dc\3\2\2\2\66\67\5\4\3\2\678\7\2"+
		"\2\38\3\3\2\2\29=\7\3\2\2:<\5\6\4\2;:\3\2\2\2<?\3\2\2\2=;\3\2\2\2=>\3"+
		"\2\2\2>A\3\2\2\2?=\3\2\2\2@9\3\2\2\2@A\3\2\2\2AI\3\2\2\2BF\7\4\2\2CE\5"+
		"\34\17\2DC\3\2\2\2EH\3\2\2\2FD\3\2\2\2FG\3\2\2\2GJ\3\2\2\2HF\3\2\2\2I"+
		"B\3\2\2\2IJ\3\2\2\2JR\3\2\2\2KO\7\5\2\2LN\5\b\5\2ML\3\2\2\2NQ\3\2\2\2"+
		"OM\3\2\2\2OP\3\2\2\2PS\3\2\2\2QO\3\2\2\2RK\3\2\2\2RS\3\2\2\2S[\3\2\2\2"+
		"TX\7\6\2\2UW\5\n\6\2VU\3\2\2\2WZ\3\2\2\2XV\3\2\2\2XY\3\2\2\2Y\\\3\2\2"+
		"\2ZX\3\2\2\2[T\3\2\2\2[\\\3\2\2\2\\\5\3\2\2\2]b\5\f\7\2^b\5\16\b\2_b\5"+
		"\20\t\2`b\5\22\n\2a]\3\2\2\2a^\3\2\2\2a_\3\2\2\2a`\3\2\2\2b\7\3\2\2\2"+
		"ck\5\64\33\2dh\7\7\2\2eg\5\26\f\2fe\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2"+
		"\2\2il\3\2\2\2jh\3\2\2\2kd\3\2\2\2kl\3\2\2\2lt\3\2\2\2mq\7\b\2\2np\5\30"+
		"\r\2on\3\2\2\2ps\3\2\2\2qo\3\2\2\2qr\3\2\2\2ru\3\2\2\2sq\3\2\2\2tm\3\2"+
		"\2\2tu\3\2\2\2ux\3\2\2\2vw\7\t\2\2wy\5\36\20\2xv\3\2\2\2xy\3\2\2\2y\u0081"+
		"\3\2\2\2z~\7\n\2\2{}\5\32\16\2|{\3\2\2\2}\u0080\3\2\2\2~|\3\2\2\2~\177"+
		"\3\2\2\2\177\u0082\3\2\2\2\u0080~\3\2\2\2\u0081z\3\2\2\2\u0081\u0082\3"+
		"\2\2\2\u0082\t\3\2\2\2\u0083\u0084\5\64\33\2\u0084\u0085\7\13\2\2\u0085"+
		"\u0086\5\64\33\2\u0086\u0087\7\f\2\2\u0087\u0093\7\61\2\2\u0088\u0089"+
		"\7\r\2\2\u0089\u008e\5\64\33\2\u008a\u008b\7\16\2\2\u008b\u008d\5\64\33"+
		"\2\u008c\u008a\3\2\2\2\u008d\u0090\3\2\2\2\u008e\u008c\3\2\2\2\u008e\u008f"+
		"\3\2\2\2\u008f\u0091\3\2\2\2\u0090\u008e\3\2\2\2\u0091\u0092\7\17\2\2"+
		"\u0092\u0094\3\2\2\2\u0093\u0088\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u0095"+
		"\3\2\2\2\u0095\u0096\7\20\2\2\u0096\u0097\5\64\33\2\u0097\u0098\7\f\2"+
		"\2\u0098\u00a4\7\61\2\2\u0099\u009a\7\r\2\2\u009a\u009f\5\64\33\2\u009b"+
		"\u009c\7\16\2\2\u009c\u009e\5\64\33\2\u009d\u009b\3\2\2\2\u009e\u00a1"+
		"\3\2\2\2\u009f\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u00a2\3\2\2\2\u00a1"+
		"\u009f\3\2\2\2\u00a2\u00a3\7\17\2\2\u00a3\u00a5\3\2\2\2\u00a4\u0099\3"+
		"\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a8\3\2\2\2\u00a6\u00a7\7\t\2\2\u00a7"+
		"\u00a9\5\36\20\2\u00a8\u00a6\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00b1\3"+
		"\2\2\2\u00aa\u00ae\7\n\2\2\u00ab\u00ad\5\32\16\2\u00ac\u00ab\3\2\2\2\u00ad"+
		"\u00b0\3\2\2\2\u00ae\u00ac\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b2\3\2"+
		"\2\2\u00b0\u00ae\3\2\2\2\u00b1\u00aa\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2"+
		"\13\3\2\2\2\u00b3\u00b4\7\21\2\2\u00b4\u00b5\5\64\33\2\u00b5\u00b6\7\22"+
		"\2\2\u00b6\u00b7\5*\26\2\u00b7\r\3\2\2\2\u00b8\u00b9\7\23\2\2\u00b9\u00c3"+
		"\5\64\33\2\u00ba\u00bb\7\24\2\2\u00bb\u00c0\5\64\33\2\u00bc\u00bd\7\16"+
		"\2\2\u00bd\u00bf\5\64\33\2\u00be\u00bc\3\2\2\2\u00bf\u00c2\3\2\2\2\u00c0"+
		"\u00be\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1\u00c4\3\2\2\2\u00c2\u00c0\3\2"+
		"\2\2\u00c3\u00ba\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00cf\3\2\2\2\u00c5"+
		"\u00c6\7\r\2\2\u00c6\u00cb\7\61\2\2\u00c7\u00c8\7\16\2\2\u00c8\u00ca\7"+
		"\61\2\2\u00c9\u00c7\3\2\2\2\u00ca\u00cd\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cb"+
		"\u00cc\3\2\2\2\u00cc\u00ce\3\2\2\2\u00cd\u00cb\3\2\2\2\u00ce\u00d0\7\17"+
		"\2\2\u00cf\u00c5\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\17\3\2\2\2\u00d1\u00d2"+
		"\7\25\2\2\u00d2\u00dc\5\64\33\2\u00d3\u00d4\7\24\2\2\u00d4\u00d9\5\64"+
		"\33\2\u00d5\u00d6\7\16\2\2\u00d6\u00d8\5\64\33\2\u00d7\u00d5\3\2\2\2\u00d8"+
		"\u00db\3\2\2\2\u00d9\u00d7\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00dd\3\2"+
		"\2\2\u00db\u00d9\3\2\2\2\u00dc\u00d3\3\2\2\2\u00dc\u00dd\3\2\2\2\u00dd"+
		"\21\3\2\2\2\u00de\u00df\7\26\2\2\u00df\u00e1\5\64\33\2\u00e0\u00e2\5\24"+
		"\13\2\u00e1\u00e0\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e3"+
		"\u00e4\3\2\2\2\u00e4\23\3\2\2\2\u00e5\u00e6\7\61\2\2\u00e6\u00e7\7\13"+
		"\2\2\u00e7\u00e8\5*\26\2\u00e8\25\3\2\2\2\u00e9\u00ea\t\2\2\2\u00ea\u00ed"+
		"\7\61\2\2\u00eb\u00ec\7\13\2\2\u00ec\u00ee\5\64\33\2\u00ed\u00eb\3\2\2"+
		"\2\u00ed\u00ee\3\2\2\2\u00ee\27\3\2\2\2\u00ef\u00f0\7\61\2\2\u00f0\u00fe"+
		"\7\13\2\2\u00f1\u00f2\7\61\2\2\u00f2\u00f3\7\r\2\2\u00f3\u00f8\5\64\33"+
		"\2\u00f4\u00f5\7\16\2\2\u00f5\u00f7\5\64\33\2\u00f6\u00f4\3\2\2\2\u00f7"+
		"\u00fa\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\u00fb\3\2"+
		"\2\2\u00fa\u00f8\3\2\2\2\u00fb\u00fc\7\17\2\2\u00fc\u00ff\3\2\2\2\u00fd"+
		"\u00ff\7\31\2\2\u00fe\u00f1\3\2\2\2\u00fe\u00fd\3\2\2\2\u00ff\u0100\3"+
		"\2\2\2\u0100\u010e\7\20\2\2\u0101\u0102\7\61\2\2\u0102\u0103\7\r\2\2\u0103"+
		"\u0108\5\64\33\2\u0104\u0105\7\16\2\2\u0105\u0107\5\64\33\2\u0106\u0104"+
		"\3\2\2\2\u0107\u010a\3\2\2\2\u0108\u0106\3\2\2\2\u0108\u0109\3\2\2\2\u0109"+
		"\u010b\3\2\2\2\u010a\u0108\3\2\2\2\u010b\u010c\7\17\2\2\u010c\u010f\3"+
		"\2\2\2\u010d\u010f\7\31\2\2\u010e\u0101\3\2\2\2\u010e\u010d\3\2\2\2\u010f"+
		"\31\3\2\2\2\u0110\u0111\7\61\2\2\u0111\u0112\7\13\2\2\u0112\u0115\5*\26"+
		"\2\u0113\u0114\7\22\2\2\u0114\u0116\5\60\31\2\u0115\u0113\3\2\2\2\u0115"+
		"\u0116\3\2\2\2\u0116\33\3\2\2\2\u0117\u0118\5\64\33\2\u0118\u0119\7\13"+
		"\2\2\u0119\u011a\5*\26\2\u011a\u011b\7\22\2\2\u011b\u011c\5\60\31\2\u011c"+
		"\35\3\2\2\2\u011d\u011f\5 \21\2\u011e\u011d\3\2\2\2\u011f\u0120\3\2\2"+
		"\2\u0120\u011e\3\2\2\2\u0120\u0121\3\2\2\2\u0121\37\3\2\2\2\u0122\u0123"+
		"\5\"\22\2\u0123\u0124\7\20\2\2\u0124\u0125\5\"\22\2\u0125!\3\2\2\2\u0126"+
		"\u0132\5$\23\2\u0127\u0128\7\32\2\2\u0128\u012b\5$\23\2\u0129\u012a\7"+
		"\16\2\2\u012a\u012c\5$\23\2\u012b\u0129\3\2\2\2\u012c\u012d\3\2\2\2\u012d"+
		"\u012b\3\2\2\2\u012d\u012e\3\2\2\2\u012e\u012f\3\2\2\2\u012f\u0130\7\33"+
		"\2\2\u0130\u0132\3\2\2\2\u0131\u0126\3\2\2\2\u0131\u0127\3\2\2\2\u0132"+
		"#\3\2\2\2\u0133\u0134\7\61\2\2\u0134\u0135\7\f\2\2\u0135\u0136\5&\24\2"+
		"\u0136%\3\2\2\2\u0137\u0146\7\34\2\2\u0138\u0146\7\31\2\2\u0139\u0146"+
		"\7\61\2\2\u013a\u0146\5(\25\2\u013b\u013c\7\r\2\2\u013c\u013f\5(\25\2"+
		"\u013d\u013e\7\16\2\2\u013e\u0140\5(\25\2\u013f\u013d\3\2\2\2\u0140\u0141"+
		"\3\2\2\2\u0141\u013f\3\2\2\2\u0141\u0142\3\2\2\2\u0142\u0143\3\2\2\2\u0143"+
		"\u0144\7\17\2\2\u0144\u0146\3\2\2\2\u0145\u0137\3\2\2\2\u0145\u0138\3"+
		"\2\2\2\u0145\u0139\3\2\2\2\u0145\u013a\3\2\2\2\u0145\u013b\3\2\2\2\u0146"+
		"\'\3\2\2\2\u0147\u0148\5\64\33\2\u0148\u0149\7\f\2\2\u0149\u014a\7\61"+
		"\2\2\u014a)\3\2\2\2\u014b\u0163\5,\27\2\u014c\u014d\7\35\2\2\u014d\u014e"+
		"\7\36\2\2\u014e\u014f\5*\26\2\u014f\u0150\7\37\2\2\u0150\u0163\3\2\2\2"+
		"\u0151\u0152\7 \2\2\u0152\u0153\7\36\2\2\u0153\u0154\5*\26\2\u0154\u0155"+
		"\7\37\2\2\u0155\u0163\3\2\2\2\u0156\u0157\7!\2\2\u0157\u0158\7\36\2\2"+
		"\u0158\u0159\5*\26\2\u0159\u015a\7\37\2\2\u015a\u0163\3\2\2\2\u015b\u015c"+
		"\7\"\2\2\u015c\u015d\7\36\2\2\u015d\u015e\5*\26\2\u015e\u015f\7\16\2\2"+
		"\u015f\u0160\5*\26\2\u0160\u0161\7\37\2\2\u0161\u0163\3\2\2\2\u0162\u014b"+
		"\3\2\2\2\u0162\u014c\3\2\2\2\u0162\u0151\3\2\2\2\u0162\u0156\3\2\2\2\u0162"+
		"\u015b\3\2\2\2\u0163+\3\2\2\2\u0164\u0174\7#\2\2\u0165\u016c\7$\2\2\u0166"+
		"\u0167\7\32\2\2\u0167\u0168\5.\30\2\u0168\u0169\7\16\2\2\u0169\u016a\5"+
		".\30\2\u016a\u016b\7\33\2\2\u016b\u016d\3\2\2\2\u016c\u0166\3\2\2\2\u016c"+
		"\u016d\3\2\2\2\u016d\u0174\3\2\2\2\u016e\u0174\7%\2\2\u016f\u0174\7&\2"+
		"\2\u0170\u0174\7\'\2\2\u0171\u0174\7(\2\2\u0172\u0174\5\64\33\2\u0173"+
		"\u0164\3\2\2\2\u0173\u0165\3\2\2\2\u0173\u016e\3\2\2\2\u0173\u016f\3\2"+
		"\2\2\u0173\u0170\3\2\2\2\u0173\u0171\3\2\2\2\u0173\u0172\3\2\2\2\u0174"+
		"-\3\2\2\2\u0175\u0179\7.\2\2\u0176\u0179\5\64\33\2\u0177\u0179\7\31\2"+
		"\2\u0178\u0175\3\2\2\2\u0178\u0176\3\2\2\2\u0178\u0177\3\2\2\2\u0179/"+
		"\3\2\2\2\u017a\u01d7\7)\2\2\u017b\u01d7\7*\2\2\u017c\u01d7\7.\2\2\u017d"+
		"\u01d7\7/\2\2\u017e\u01d7\7\60\2\2\u017f\u0180\5\64\33\2\u0180\u0181\7"+
		"\32\2\2\u0181\u0182\7\61\2\2\u0182\u0183\7\22\2\2\u0183\u018a\5\60\31"+
		"\2\u0184\u0185\7\16\2\2\u0185\u0186\7\61\2\2\u0186\u0187\7\22\2\2\u0187"+
		"\u0189\5\60\31\2\u0188\u0184\3\2\2\2\u0189\u018c\3\2\2\2\u018a\u0188\3"+
		"\2\2\2\u018a\u018b\3\2\2\2\u018b\u018d\3\2\2\2\u018c\u018a\3\2\2\2\u018d"+
		"\u018e\7\33\2\2\u018e\u01d7\3\2\2\2\u018f\u0192\5\64\33\2\u0190\u0191"+
		"\7\f\2\2\u0191\u0193\7\61\2\2\u0192\u0190\3\2\2\2\u0192\u0193\3\2\2\2"+
		"\u0193\u01d7\3\2\2\2\u0194\u0195\7+\2\2\u0195\u0196\7\36\2\2\u0196\u0197"+
		"\5*\26\2\u0197\u0198\7\37\2\2\u0198\u01d7\3\2\2\2\u0199\u019a\7,\2\2\u019a"+
		"\u019b\7\36\2\2\u019b\u019c\5*\26\2\u019c\u019d\7\37\2\2\u019d\u019e\7"+
		"\32\2\2\u019e\u019f\5\60\31\2\u019f\u01a0\7\33\2\2\u01a0\u01d7\3\2\2\2"+
		"\u01a1\u01a2\7 \2\2\u01a2\u01a3\7\36\2\2\u01a3\u01a4\5*\26\2\u01a4\u01a5"+
		"\7\37\2\2\u01a5\u01ae\7\32\2\2\u01a6\u01ab\5\60\31\2\u01a7\u01a8\7\16"+
		"\2\2\u01a8\u01aa\5\60\31\2\u01a9\u01a7\3\2\2\2\u01aa\u01ad\3\2\2\2\u01ab"+
		"\u01a9\3\2\2\2\u01ab\u01ac\3\2\2\2\u01ac\u01af\3\2\2\2\u01ad\u01ab\3\2"+
		"\2\2\u01ae\u01a6\3\2\2\2\u01ae\u01af\3\2\2\2\u01af\u01b0\3\2\2\2\u01b0"+
		"\u01b1\7\33\2\2\u01b1\u01d7\3\2\2\2\u01b2\u01b3\7!\2\2\u01b3\u01b4\7\36"+
		"\2\2\u01b4\u01b5\5*\26\2\u01b5\u01b6\7\37\2\2\u01b6\u01bf\7\32\2\2\u01b7"+
		"\u01bc\5\60\31\2\u01b8\u01b9\7\16\2\2\u01b9\u01bb\5\60\31\2\u01ba\u01b8"+
		"\3\2\2\2\u01bb\u01be\3\2\2\2\u01bc\u01ba\3\2\2\2\u01bc\u01bd\3\2\2\2\u01bd"+
		"\u01c0\3\2\2\2\u01be\u01bc\3\2\2\2\u01bf\u01b7\3\2\2\2\u01bf\u01c0\3\2"+
		"\2\2\u01c0\u01c1\3\2\2\2\u01c1\u01c2\7\33\2\2\u01c2\u01d7\3\2\2\2\u01c3"+
		"\u01c4\7\"\2\2\u01c4\u01c5\7\36\2\2\u01c5\u01c6\5*\26\2\u01c6\u01c7\7"+
		"\16\2\2\u01c7\u01c8\5*\26\2\u01c8\u01c9\7\37\2\2\u01c9\u01d2\7\32\2\2"+
		"\u01ca\u01cf\5\62\32\2\u01cb\u01cc\7\16\2\2\u01cc\u01ce\5\62\32\2\u01cd"+
		"\u01cb\3\2\2\2\u01ce\u01d1\3\2\2\2\u01cf\u01cd\3\2\2\2\u01cf\u01d0\3\2"+
		"\2\2\u01d0\u01d3\3\2\2\2\u01d1\u01cf\3\2\2\2\u01d2\u01ca\3\2\2\2\u01d2"+
		"\u01d3\3\2\2\2\u01d3\u01d4\3\2\2\2\u01d4\u01d5\7\33\2\2\u01d5\u01d7\3"+
		"\2\2\2\u01d6\u017a\3\2\2\2\u01d6\u017b\3\2\2\2\u01d6\u017c\3\2\2\2\u01d6"+
		"\u017d\3\2\2\2\u01d6\u017e\3\2\2\2\u01d6\u017f\3\2\2\2\u01d6\u018f\3\2"+
		"\2\2\u01d6\u0194\3\2\2\2\u01d6\u0199\3\2\2\2\u01d6\u01a1\3\2\2\2\u01d6"+
		"\u01b2\3\2\2\2\u01d6\u01c3\3\2\2\2\u01d7\61\3\2\2\2\u01d8\u01d9\5\60\31"+
		"\2\u01d9\u01da\7\20\2\2\u01da\u01db\5\60\31\2\u01db\63\3\2\2\2\u01dc\u01e1"+
		"\7\61\2\2\u01dd\u01de\7-\2\2\u01de\u01e0\7\61\2\2\u01df\u01dd\3\2\2\2"+
		"\u01e0\u01e3\3\2\2\2\u01e1\u01df\3\2\2\2\u01e1\u01e2\3\2\2\2\u01e2\65"+
		"\3\2\2\2\u01e3\u01e1\3\2\2\29=@FIORX[ahkqtx~\u0081\u008e\u0093\u009f\u00a4"+
		"\u00a8\u00ae\u00b1\u00c0\u00c3\u00cb\u00cf\u00d9\u00dc\u00e3\u00ed\u00f8"+
		"\u00fe\u0108\u010e\u0115\u0120\u012d\u0131\u0141\u0145\u0162\u016c\u0173"+
		"\u0178\u018a\u0192\u01ab\u01ae\u01bc\u01bf\u01cf\u01d2\u01d6\u01e1";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}