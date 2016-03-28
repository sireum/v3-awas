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
		RULE_one = 17, RULE_fault = 18, RULE_type = 19, RULE_basicType = 20, RULE_intConstant = 21, 
		RULE_init = 22, RULE_mapEntry = 23, RULE_name = 24;
	public static final String[] ruleNames = {
		"modelFile", "model", "typeDecl", "componentDecl", "connectionDecl", "typeAliasDecl", 
		"enumDecl", "latticeDecl", "recordDecl", "field", "port", "flow", "property", 
		"constantDecl", "behaviour", "expression", "tuple", "one", "fault", "type", 
		"basicType", "intConstant", "init", "mapEntry", "name"
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
			setState(50);
			model();
			setState(51);
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
			setState(60);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(53);
				match(T__0);
				setState(57);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__14) | (1L << T__16) | (1L << T__18) | (1L << T__19))) != 0)) {
					{
					{
					setState(54);
					typeDecl();
					}
					}
					setState(59);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(69);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(62);
				match(T__1);
				setState(66);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ID) {
					{
					{
					setState(63);
					constantDecl();
					}
					}
					setState(68);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(78);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(71);
				match(T__2);
				setState(75);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ID) {
					{
					{
					setState(72);
					componentDecl();
					}
					}
					setState(77);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(87);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(80);
				match(T__3);
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ID) {
					{
					{
					setState(81);
					connectionDecl();
					}
					}
					setState(86);
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
			setState(93);
			switch (_input.LA(1)) {
			case T__14:
				_localctx = new AliasTypeDeclContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(89);
				typeAliasDecl();
				}
				break;
			case T__16:
				_localctx = new EnumTypeDeclContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(90);
				enumDecl();
				}
				break;
			case T__18:
				_localctx = new LatticeTypeDeclContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(91);
				latticeDecl();
				}
				break;
			case T__19:
				_localctx = new RecordTypeDeclContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(92);
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
			setState(95);
			name();
			setState(103);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(96);
				match(T__4);
				setState(100);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__20 || _la==T__21) {
					{
					{
					setState(97);
					port();
					}
					}
					setState(102);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(112);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(105);
				match(T__5);
				setState(109);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(106);
						flow();
						}
						} 
					}
					setState(111);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				}
				}
			}

			setState(116);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(114);
				match(T__6);
				setState(115);
				behaviour();
				}
			}

			setState(125);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(118);
				match(T__7);
				setState(122);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(119);
						property();
						}
						} 
					}
					setState(124);
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
			setState(127);
			((ConnectionDeclContext)_localctx).connName = name();
			setState(128);
			match(T__8);
			setState(129);
			((ConnectionDeclContext)_localctx).fromComponent = name();
			setState(130);
			match(T__9);
			setState(131);
			((ConnectionDeclContext)_localctx).fromPort = match(ID);
			setState(143);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(132);
				match(T__10);
				setState(133);
				((ConnectionDeclContext)_localctx).name = name();
				((ConnectionDeclContext)_localctx).fromE.add(((ConnectionDeclContext)_localctx).name);
				setState(138);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(134);
					match(T__11);
					setState(135);
					((ConnectionDeclContext)_localctx).name = name();
					((ConnectionDeclContext)_localctx).fromE.add(((ConnectionDeclContext)_localctx).name);
					}
					}
					setState(140);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(141);
				match(T__12);
				}
			}

			setState(145);
			match(T__13);
			setState(146);
			((ConnectionDeclContext)_localctx).toComponent = name();
			setState(147);
			match(T__9);
			setState(148);
			((ConnectionDeclContext)_localctx).toPort = match(ID);
			setState(160);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(149);
				match(T__10);
				setState(150);
				((ConnectionDeclContext)_localctx).name = name();
				((ConnectionDeclContext)_localctx).toE.add(((ConnectionDeclContext)_localctx).name);
				setState(155);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(151);
					match(T__11);
					setState(152);
					((ConnectionDeclContext)_localctx).name = name();
					((ConnectionDeclContext)_localctx).toE.add(((ConnectionDeclContext)_localctx).name);
					}
					}
					setState(157);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(158);
				match(T__12);
				}
			}

			setState(164);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(162);
				match(T__6);
				setState(163);
				behaviour();
				}
			}

			setState(173);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(166);
				match(T__7);
				setState(170);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(167);
						property();
						}
						} 
					}
					setState(172);
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
			setState(175);
			match(T__14);
			setState(176);
			name();
			setState(177);
			match(T__15);
			setState(178);
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
			setState(180);
			match(T__16);
			setState(181);
			((EnumDeclContext)_localctx).n = name();
			setState(191);
			_la = _input.LA(1);
			if (_la==T__17) {
				{
				setState(182);
				match(T__17);
				setState(183);
				((EnumDeclContext)_localctx).name = name();
				((EnumDeclContext)_localctx).supers.add(((EnumDeclContext)_localctx).name);
				setState(188);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(184);
					match(T__11);
					setState(185);
					((EnumDeclContext)_localctx).name = name();
					((EnumDeclContext)_localctx).supers.add(((EnumDeclContext)_localctx).name);
					}
					}
					setState(190);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(203);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(193);
				match(T__10);
				setState(194);
				((EnumDeclContext)_localctx).ID = match(ID);
				((EnumDeclContext)_localctx).elements.add(((EnumDeclContext)_localctx).ID);
				setState(199);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(195);
					match(T__11);
					setState(196);
					((EnumDeclContext)_localctx).ID = match(ID);
					((EnumDeclContext)_localctx).elements.add(((EnumDeclContext)_localctx).ID);
					}
					}
					setState(201);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(202);
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
			setState(205);
			match(T__18);
			setState(206);
			((LatticeDeclContext)_localctx).n = name();
			setState(216);
			_la = _input.LA(1);
			if (_la==T__17) {
				{
				setState(207);
				match(T__17);
				setState(208);
				((LatticeDeclContext)_localctx).name = name();
				((LatticeDeclContext)_localctx).supers.add(((LatticeDeclContext)_localctx).name);
				setState(213);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(209);
					match(T__11);
					setState(210);
					((LatticeDeclContext)_localctx).name = name();
					((LatticeDeclContext)_localctx).supers.add(((LatticeDeclContext)_localctx).name);
					}
					}
					setState(215);
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
			setState(218);
			match(T__19);
			setState(219);
			name();
			setState(221); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(220);
				field();
				}
				}
				setState(223); 
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
			setState(225);
			match(ID);
			setState(226);
			match(T__8);
			setState(227);
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
			setState(229);
			((PortContext)_localctx).mod = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==T__20 || _la==T__21) ) {
				((PortContext)_localctx).mod = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(230);
			match(ID);
			setState(233);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(231);
				match(T__8);
				setState(232);
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
			setState(235);
			((FlowContext)_localctx).id = match(ID);
			setState(236);
			match(T__8);
			setState(250);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(237);
				((FlowContext)_localctx).from = match(ID);
				setState(238);
				match(T__10);
				setState(239);
				((FlowContext)_localctx).name = name();
				((FlowContext)_localctx).fromE.add(((FlowContext)_localctx).name);
				setState(244);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(240);
					match(T__11);
					setState(241);
					((FlowContext)_localctx).name = name();
					((FlowContext)_localctx).fromE.add(((FlowContext)_localctx).name);
					}
					}
					setState(246);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(247);
				match(T__12);
				}
				break;
			case T__22:
				{
				setState(249);
				match(T__22);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(252);
			match(T__13);
			setState(266);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(253);
				((FlowContext)_localctx).to = match(ID);
				setState(254);
				match(T__10);
				setState(255);
				((FlowContext)_localctx).name = name();
				((FlowContext)_localctx).toE.add(((FlowContext)_localctx).name);
				setState(260);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(256);
					match(T__11);
					setState(257);
					((FlowContext)_localctx).name = name();
					((FlowContext)_localctx).toE.add(((FlowContext)_localctx).name);
					}
					}
					setState(262);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(263);
				match(T__12);
				}
				break;
			case T__22:
				{
				setState(265);
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
			setState(268);
			match(ID);
			setState(269);
			match(T__8);
			setState(270);
			type();
			setState(273);
			_la = _input.LA(1);
			if (_la==T__15) {
				{
				setState(271);
				match(T__15);
				setState(272);
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
			setState(275);
			name();
			setState(276);
			match(T__8);
			setState(277);
			type();
			setState(278);
			match(T__15);
			setState(279);
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
			setState(282); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(281);
					expression();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(284); 
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
			setState(286);
			((ExpressionContext)_localctx).key = tuple();
			setState(287);
			match(T__13);
			setState(288);
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
		public List<OneContext> one() {
			return getRuleContexts(OneContext.class);
		}
		public OneContext one(int i) {
			return getRuleContext(OneContext.class,i);
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
			setState(301);
			switch (_input.LA(1)) {
			case T__10:
			case T__22:
			case T__25:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(290);
				one();
				}
				break;
			case T__23:
				enterOuterAlt(_localctx, 2);
				{
				setState(291);
				match(T__23);
				setState(292);
				one();
				setState(295); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(293);
					match(T__11);
					setState(294);
					one();
					}
					}
					setState(297); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__11 );
				setState(299);
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
		enterRule(_localctx, 34, RULE_one);
		int _la;
		try {
			setState(317);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				_localctx = new NoFailureContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(303);
				match(T__25);
				}
				break;
			case 2:
				_localctx = new WildCardContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(304);
				match(T__22);
				}
				break;
			case 3:
				_localctx = new VariableContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(305);
				match(ID);
				}
				break;
			case 4:
				_localctx = new FaultRefContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(306);
				fault();
				}
				break;
			case 5:
				_localctx = new FaultSetContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(307);
				match(T__10);
				setState(308);
				fault();
				setState(311); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(309);
					match(T__11);
					setState(310);
					fault();
					}
					}
					setState(313); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__11 );
				setState(315);
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
		enterRule(_localctx, 36, RULE_fault);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(319);
			name();
			setState(320);
			match(T__9);
			setState(321);
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
		enterRule(_localctx, 38, RULE_type);
		try {
			setState(346);
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
				setState(323);
				basicType();
				}
				break;
			case T__26:
				_localctx = new OptionTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(324);
				match(T__26);
				setState(325);
				match(T__27);
				setState(326);
				type();
				setState(327);
				match(T__28);
				}
				break;
			case T__29:
				_localctx = new SetTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(329);
				match(T__29);
				setState(330);
				match(T__27);
				setState(331);
				type();
				setState(332);
				match(T__28);
				}
				break;
			case T__30:
				_localctx = new SeqTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(334);
				match(T__30);
				setState(335);
				match(T__27);
				setState(336);
				type();
				setState(337);
				match(T__28);
				}
				break;
			case T__31:
				_localctx = new MapTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(339);
				match(T__31);
				setState(340);
				match(T__27);
				setState(341);
				((MapTypeContext)_localctx).key = type();
				setState(342);
				match(T__11);
				setState(343);
				((MapTypeContext)_localctx).value = type();
				setState(344);
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
		enterRule(_localctx, 40, RULE_basicType);
		int _la;
		try {
			setState(363);
			switch (_input.LA(1)) {
			case T__32:
				_localctx = new BooleanTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(348);
				match(T__32);
				}
				break;
			case T__33:
				_localctx = new IntegerTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(349);
				match(T__33);
				setState(356);
				_la = _input.LA(1);
				if (_la==T__23) {
					{
					setState(350);
					match(T__23);
					setState(351);
					((IntegerTypeContext)_localctx).lo = intConstant();
					setState(352);
					match(T__11);
					setState(353);
					((IntegerTypeContext)_localctx).hi = intConstant();
					setState(354);
					match(T__24);
					}
				}

				}
				break;
			case T__34:
				_localctx = new RealTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(358);
				match(T__34);
				}
				break;
			case T__35:
				_localctx = new StringTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(359);
				match(T__35);
				}
				break;
			case T__36:
				_localctx = new ComponentTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(360);
				match(T__36);
				}
				break;
			case T__37:
				_localctx = new PortTypeContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(361);
				match(T__37);
				}
				break;
			case ID:
				_localctx = new NamedTypeContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(362);
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
		enterRule(_localctx, 42, RULE_intConstant);
		try {
			setState(368);
			switch (_input.LA(1)) {
			case INTEGER:
				_localctx = new LitIntConstantContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(365);
				match(INTEGER);
				}
				break;
			case ID:
				_localctx = new NamedIntConstantContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(366);
				name();
				}
				break;
			case T__22:
				_localctx = new ArbitraryIntConstantContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(367);
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
		enterRule(_localctx, 44, RULE_init);
		int _la;
		try {
			setState(462);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				_localctx = new TrueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(370);
				match(T__38);
				}
				break;
			case 2:
				_localctx = new FalseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(371);
				match(T__39);
				}
				break;
			case 3:
				_localctx = new IntegerContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(372);
				match(INTEGER);
				}
				break;
			case 4:
				_localctx = new RealContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(373);
				match(REAL);
				}
				break;
			case 5:
				_localctx = new StringContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(374);
				match(STRING);
				}
				break;
			case 6:
				_localctx = new RecordContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(375);
				name();
				setState(376);
				match(T__23);
				setState(377);
				match(ID);
				setState(378);
				match(T__15);
				setState(379);
				init();
				setState(386);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(380);
					match(T__11);
					setState(381);
					match(ID);
					setState(382);
					match(T__15);
					setState(383);
					init();
					}
					}
					setState(388);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(389);
				match(T__24);
				}
				break;
			case 7:
				_localctx = new NameRefContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(391);
				name();
				setState(394);
				_la = _input.LA(1);
				if (_la==T__9) {
					{
					setState(392);
					match(T__9);
					setState(393);
					match(ID);
					}
				}

				}
				break;
			case 8:
				_localctx = new NoneContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(396);
				match(T__40);
				setState(397);
				match(T__27);
				setState(398);
				type();
				setState(399);
				match(T__28);
				}
				break;
			case 9:
				_localctx = new SomeContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(401);
				match(T__41);
				setState(402);
				match(T__27);
				setState(403);
				type();
				setState(404);
				match(T__28);
				setState(405);
				match(T__23);
				setState(406);
				init();
				setState(407);
				match(T__24);
				}
				break;
			case 10:
				_localctx = new SetContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(409);
				match(T__29);
				setState(410);
				match(T__27);
				setState(411);
				type();
				setState(412);
				match(T__28);
				setState(413);
				match(T__23);
				setState(422);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << INTEGER) | (1L << REAL) | (1L << STRING) | (1L << ID))) != 0)) {
					{
					setState(414);
					init();
					setState(419);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__11) {
						{
						{
						setState(415);
						match(T__11);
						setState(416);
						init();
						}
						}
						setState(421);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(424);
				match(T__24);
				}
				break;
			case 11:
				_localctx = new SeqContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(426);
				match(T__30);
				setState(427);
				match(T__27);
				setState(428);
				type();
				setState(429);
				match(T__28);
				setState(430);
				match(T__23);
				setState(439);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << INTEGER) | (1L << REAL) | (1L << STRING) | (1L << ID))) != 0)) {
					{
					setState(431);
					init();
					setState(436);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__11) {
						{
						{
						setState(432);
						match(T__11);
						setState(433);
						init();
						}
						}
						setState(438);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(441);
				match(T__24);
				}
				break;
			case 12:
				_localctx = new MapContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(443);
				match(T__31);
				setState(444);
				match(T__27);
				setState(445);
				((MapContext)_localctx).key = type();
				setState(446);
				match(T__11);
				setState(447);
				((MapContext)_localctx).value = type();
				setState(448);
				match(T__28);
				setState(449);
				match(T__23);
				setState(458);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << INTEGER) | (1L << REAL) | (1L << STRING) | (1L << ID))) != 0)) {
					{
					setState(450);
					mapEntry();
					setState(455);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__11) {
						{
						{
						setState(451);
						match(T__11);
						setState(452);
						mapEntry();
						}
						}
						setState(457);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(460);
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
		enterRule(_localctx, 46, RULE_mapEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(464);
			((MapEntryContext)_localctx).key = init();
			setState(465);
			match(T__13);
			setState(466);
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
		enterRule(_localctx, 48, RULE_name);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(468);
			match(ID);
			setState(473);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__42) {
				{
				{
				setState(469);
				match(T__42);
				setState(470);
				match(ID);
				}
				}
				setState(475);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\65\u01df\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\3\2\3\2\3\2\3\3\3\3\7\3:\n\3\f\3\16\3=\13\3\5\3?\n\3\3\3\3"+
		"\3\7\3C\n\3\f\3\16\3F\13\3\5\3H\n\3\3\3\3\3\7\3L\n\3\f\3\16\3O\13\3\5"+
		"\3Q\n\3\3\3\3\3\7\3U\n\3\f\3\16\3X\13\3\5\3Z\n\3\3\4\3\4\3\4\3\4\5\4`"+
		"\n\4\3\5\3\5\3\5\7\5e\n\5\f\5\16\5h\13\5\5\5j\n\5\3\5\3\5\7\5n\n\5\f\5"+
		"\16\5q\13\5\5\5s\n\5\3\5\3\5\5\5w\n\5\3\5\3\5\7\5{\n\5\f\5\16\5~\13\5"+
		"\5\5\u0080\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6\u008b\n\6\f\6\16"+
		"\6\u008e\13\6\3\6\3\6\5\6\u0092\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7"+
		"\6\u009c\n\6\f\6\16\6\u009f\13\6\3\6\3\6\5\6\u00a3\n\6\3\6\3\6\5\6\u00a7"+
		"\n\6\3\6\3\6\7\6\u00ab\n\6\f\6\16\6\u00ae\13\6\5\6\u00b0\n\6\3\7\3\7\3"+
		"\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u00bd\n\b\f\b\16\b\u00c0\13\b\5"+
		"\b\u00c2\n\b\3\b\3\b\3\b\3\b\7\b\u00c8\n\b\f\b\16\b\u00cb\13\b\3\b\5\b"+
		"\u00ce\n\b\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u00d6\n\t\f\t\16\t\u00d9\13\t\5"+
		"\t\u00db\n\t\3\n\3\n\3\n\6\n\u00e0\n\n\r\n\16\n\u00e1\3\13\3\13\3\13\3"+
		"\13\3\f\3\f\3\f\3\f\5\f\u00ec\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00f5"+
		"\n\r\f\r\16\r\u00f8\13\r\3\r\3\r\3\r\5\r\u00fd\n\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\7\r\u0105\n\r\f\r\16\r\u0108\13\r\3\r\3\r\3\r\5\r\u010d\n\r\3\16"+
		"\3\16\3\16\3\16\3\16\5\16\u0114\n\16\3\17\3\17\3\17\3\17\3\17\3\17\3\20"+
		"\6\20\u011d\n\20\r\20\16\20\u011e\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3"+
		"\22\3\22\6\22\u012a\n\22\r\22\16\22\u012b\3\22\3\22\5\22\u0130\n\22\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\6\23\u013a\n\23\r\23\16\23\u013b"+
		"\3\23\3\23\5\23\u0140\n\23\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\5\25\u015d\n\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\5\26\u0167\n\26\3\26\3\26\3\26\3\26\3\26\5\26\u016e\n\26\3\27\3"+
		"\27\3\27\5\27\u0173\n\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\7\30\u0183\n\30\f\30\16\30\u0186\13\30\3\30"+
		"\3\30\3\30\3\30\3\30\5\30\u018d\n\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\7\30\u01a4\n\30\f\30\16\30\u01a7\13\30\5\30\u01a9\n\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\7\30\u01b5\n\30\f\30\16\30\u01b8\13"+
		"\30\5\30\u01ba\n\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\7\30\u01c8\n\30\f\30\16\30\u01cb\13\30\5\30\u01cd\n\30\3\30"+
		"\3\30\5\30\u01d1\n\30\3\31\3\31\3\31\3\31\3\32\3\32\3\32\7\32\u01da\n"+
		"\32\f\32\16\32\u01dd\13\32\3\32\2\2\33\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$&(*,.\60\62\2\3\3\2\27\30\u0214\2\64\3\2\2\2\4>\3\2\2\2\6_\3"+
		"\2\2\2\ba\3\2\2\2\n\u0081\3\2\2\2\f\u00b1\3\2\2\2\16\u00b6\3\2\2\2\20"+
		"\u00cf\3\2\2\2\22\u00dc\3\2\2\2\24\u00e3\3\2\2\2\26\u00e7\3\2\2\2\30\u00ed"+
		"\3\2\2\2\32\u010e\3\2\2\2\34\u0115\3\2\2\2\36\u011c\3\2\2\2 \u0120\3\2"+
		"\2\2\"\u012f\3\2\2\2$\u013f\3\2\2\2&\u0141\3\2\2\2(\u015c\3\2\2\2*\u016d"+
		"\3\2\2\2,\u0172\3\2\2\2.\u01d0\3\2\2\2\60\u01d2\3\2\2\2\62\u01d6\3\2\2"+
		"\2\64\65\5\4\3\2\65\66\7\2\2\3\66\3\3\2\2\2\67;\7\3\2\28:\5\6\4\298\3"+
		"\2\2\2:=\3\2\2\2;9\3\2\2\2;<\3\2\2\2<?\3\2\2\2=;\3\2\2\2>\67\3\2\2\2>"+
		"?\3\2\2\2?G\3\2\2\2@D\7\4\2\2AC\5\34\17\2BA\3\2\2\2CF\3\2\2\2DB\3\2\2"+
		"\2DE\3\2\2\2EH\3\2\2\2FD\3\2\2\2G@\3\2\2\2GH\3\2\2\2HP\3\2\2\2IM\7\5\2"+
		"\2JL\5\b\5\2KJ\3\2\2\2LO\3\2\2\2MK\3\2\2\2MN\3\2\2\2NQ\3\2\2\2OM\3\2\2"+
		"\2PI\3\2\2\2PQ\3\2\2\2QY\3\2\2\2RV\7\6\2\2SU\5\n\6\2TS\3\2\2\2UX\3\2\2"+
		"\2VT\3\2\2\2VW\3\2\2\2WZ\3\2\2\2XV\3\2\2\2YR\3\2\2\2YZ\3\2\2\2Z\5\3\2"+
		"\2\2[`\5\f\7\2\\`\5\16\b\2]`\5\20\t\2^`\5\22\n\2_[\3\2\2\2_\\\3\2\2\2"+
		"_]\3\2\2\2_^\3\2\2\2`\7\3\2\2\2ai\5\62\32\2bf\7\7\2\2ce\5\26\f\2dc\3\2"+
		"\2\2eh\3\2\2\2fd\3\2\2\2fg\3\2\2\2gj\3\2\2\2hf\3\2\2\2ib\3\2\2\2ij\3\2"+
		"\2\2jr\3\2\2\2ko\7\b\2\2ln\5\30\r\2ml\3\2\2\2nq\3\2\2\2om\3\2\2\2op\3"+
		"\2\2\2ps\3\2\2\2qo\3\2\2\2rk\3\2\2\2rs\3\2\2\2sv\3\2\2\2tu\7\t\2\2uw\5"+
		"\36\20\2vt\3\2\2\2vw\3\2\2\2w\177\3\2\2\2x|\7\n\2\2y{\5\32\16\2zy\3\2"+
		"\2\2{~\3\2\2\2|z\3\2\2\2|}\3\2\2\2}\u0080\3\2\2\2~|\3\2\2\2\177x\3\2\2"+
		"\2\177\u0080\3\2\2\2\u0080\t\3\2\2\2\u0081\u0082\5\62\32\2\u0082\u0083"+
		"\7\13\2\2\u0083\u0084\5\62\32\2\u0084\u0085\7\f\2\2\u0085\u0091\7\61\2"+
		"\2\u0086\u0087\7\r\2\2\u0087\u008c\5\62\32\2\u0088\u0089\7\16\2\2\u0089"+
		"\u008b\5\62\32\2\u008a\u0088\3\2\2\2\u008b\u008e\3\2\2\2\u008c\u008a\3"+
		"\2\2\2\u008c\u008d\3\2\2\2\u008d\u008f\3\2\2\2\u008e\u008c\3\2\2\2\u008f"+
		"\u0090\7\17\2\2\u0090\u0092\3\2\2\2\u0091\u0086\3\2\2\2\u0091\u0092\3"+
		"\2\2\2\u0092\u0093\3\2\2\2\u0093\u0094\7\20\2\2\u0094\u0095\5\62\32\2"+
		"\u0095\u0096\7\f\2\2\u0096\u00a2\7\61\2\2\u0097\u0098\7\r\2\2\u0098\u009d"+
		"\5\62\32\2\u0099\u009a\7\16\2\2\u009a\u009c\5\62\32\2\u009b\u0099\3\2"+
		"\2\2\u009c\u009f\3\2\2\2\u009d\u009b\3\2\2\2\u009d\u009e\3\2\2\2\u009e"+
		"\u00a0\3\2\2\2\u009f\u009d\3\2\2\2\u00a0\u00a1\7\17\2\2\u00a1\u00a3\3"+
		"\2\2\2\u00a2\u0097\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a6\3\2\2\2\u00a4"+
		"\u00a5\7\t\2\2\u00a5\u00a7\5\36\20\2\u00a6\u00a4\3\2\2\2\u00a6\u00a7\3"+
		"\2\2\2\u00a7\u00af\3\2\2\2\u00a8\u00ac\7\n\2\2\u00a9\u00ab\5\32\16\2\u00aa"+
		"\u00a9\3\2\2\2\u00ab\u00ae\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ac\u00ad\3\2"+
		"\2\2\u00ad\u00b0\3\2\2\2\u00ae\u00ac\3\2\2\2\u00af\u00a8\3\2\2\2\u00af"+
		"\u00b0\3\2\2\2\u00b0\13\3\2\2\2\u00b1\u00b2\7\21\2\2\u00b2\u00b3\5\62"+
		"\32\2\u00b3\u00b4\7\22\2\2\u00b4\u00b5\5(\25\2\u00b5\r\3\2\2\2\u00b6\u00b7"+
		"\7\23\2\2\u00b7\u00c1\5\62\32\2\u00b8\u00b9\7\24\2\2\u00b9\u00be\5\62"+
		"\32\2\u00ba\u00bb\7\16\2\2\u00bb\u00bd\5\62\32\2\u00bc\u00ba\3\2\2\2\u00bd"+
		"\u00c0\3\2\2\2\u00be\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00c2\3\2"+
		"\2\2\u00c0\u00be\3\2\2\2\u00c1\u00b8\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2"+
		"\u00cd\3\2\2\2\u00c3\u00c4\7\r\2\2\u00c4\u00c9\7\61\2\2\u00c5\u00c6\7"+
		"\16\2\2\u00c6\u00c8\7\61\2\2\u00c7\u00c5\3\2\2\2\u00c8\u00cb\3\2\2\2\u00c9"+
		"\u00c7\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00cc\3\2\2\2\u00cb\u00c9\3\2"+
		"\2\2\u00cc\u00ce\7\17\2\2\u00cd\u00c3\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce"+
		"\17\3\2\2\2\u00cf\u00d0\7\25\2\2\u00d0\u00da\5\62\32\2\u00d1\u00d2\7\24"+
		"\2\2\u00d2\u00d7\5\62\32\2\u00d3\u00d4\7\16\2\2\u00d4\u00d6\5\62\32\2"+
		"\u00d5\u00d3\3\2\2\2\u00d6\u00d9\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d7\u00d8"+
		"\3\2\2\2\u00d8\u00db\3\2\2\2\u00d9\u00d7\3\2\2\2\u00da\u00d1\3\2\2\2\u00da"+
		"\u00db\3\2\2\2\u00db\21\3\2\2\2\u00dc\u00dd\7\26\2\2\u00dd\u00df\5\62"+
		"\32\2\u00de\u00e0\5\24\13\2\u00df\u00de\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1"+
		"\u00df\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2\23\3\2\2\2\u00e3\u00e4\7\61\2"+
		"\2\u00e4\u00e5\7\13\2\2\u00e5\u00e6\5(\25\2\u00e6\25\3\2\2\2\u00e7\u00e8"+
		"\t\2\2\2\u00e8\u00eb\7\61\2\2\u00e9\u00ea\7\13\2\2\u00ea\u00ec\5\62\32"+
		"\2\u00eb\u00e9\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\27\3\2\2\2\u00ed\u00ee"+
		"\7\61\2\2\u00ee\u00fc\7\13\2\2\u00ef\u00f0\7\61\2\2\u00f0\u00f1\7\r\2"+
		"\2\u00f1\u00f6\5\62\32\2\u00f2\u00f3\7\16\2\2\u00f3\u00f5\5\62\32\2\u00f4"+
		"\u00f2\3\2\2\2\u00f5\u00f8\3\2\2\2\u00f6\u00f4\3\2\2\2\u00f6\u00f7\3\2"+
		"\2\2\u00f7\u00f9\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f9\u00fa\7\17\2\2\u00fa"+
		"\u00fd\3\2\2\2\u00fb\u00fd\7\31\2\2\u00fc\u00ef\3\2\2\2\u00fc\u00fb\3"+
		"\2\2\2\u00fd\u00fe\3\2\2\2\u00fe\u010c\7\20\2\2\u00ff\u0100\7\61\2\2\u0100"+
		"\u0101\7\r\2\2\u0101\u0106\5\62\32\2\u0102\u0103\7\16\2\2\u0103\u0105"+
		"\5\62\32\2\u0104\u0102\3\2\2\2\u0105\u0108\3\2\2\2\u0106\u0104\3\2\2\2"+
		"\u0106\u0107\3\2\2\2\u0107\u0109\3\2\2\2\u0108\u0106\3\2\2\2\u0109\u010a"+
		"\7\17\2\2\u010a\u010d\3\2\2\2\u010b\u010d\7\31\2\2\u010c\u00ff\3\2\2\2"+
		"\u010c\u010b\3\2\2\2\u010d\31\3\2\2\2\u010e\u010f\7\61\2\2\u010f\u0110"+
		"\7\13\2\2\u0110\u0113\5(\25\2\u0111\u0112\7\22\2\2\u0112\u0114\5.\30\2"+
		"\u0113\u0111\3\2\2\2\u0113\u0114\3\2\2\2\u0114\33\3\2\2\2\u0115\u0116"+
		"\5\62\32\2\u0116\u0117\7\13\2\2\u0117\u0118\5(\25\2\u0118\u0119\7\22\2"+
		"\2\u0119\u011a\5.\30\2\u011a\35\3\2\2\2\u011b\u011d\5 \21\2\u011c\u011b"+
		"\3\2\2\2\u011d\u011e\3\2\2\2\u011e\u011c\3\2\2\2\u011e\u011f\3\2\2\2\u011f"+
		"\37\3\2\2\2\u0120\u0121\5\"\22\2\u0121\u0122\7\20\2\2\u0122\u0123\5\""+
		"\22\2\u0123!\3\2\2\2\u0124\u0130\5$\23\2\u0125\u0126\7\32\2\2\u0126\u0129"+
		"\5$\23\2\u0127\u0128\7\16\2\2\u0128\u012a\5$\23\2\u0129\u0127\3\2\2\2"+
		"\u012a\u012b\3\2\2\2\u012b\u0129\3\2\2\2\u012b\u012c\3\2\2\2\u012c\u012d"+
		"\3\2\2\2\u012d\u012e\7\33\2\2\u012e\u0130\3\2\2\2\u012f\u0124\3\2\2\2"+
		"\u012f\u0125\3\2\2\2\u0130#\3\2\2\2\u0131\u0140\7\34\2\2\u0132\u0140\7"+
		"\31\2\2\u0133\u0140\7\61\2\2\u0134\u0140\5&\24\2\u0135\u0136\7\r\2\2\u0136"+
		"\u0139\5&\24\2\u0137\u0138\7\16\2\2\u0138\u013a\5&\24\2\u0139\u0137\3"+
		"\2\2\2\u013a\u013b\3\2\2\2\u013b\u0139\3\2\2\2\u013b\u013c\3\2\2\2\u013c"+
		"\u013d\3\2\2\2\u013d\u013e\7\17\2\2\u013e\u0140\3\2\2\2\u013f\u0131\3"+
		"\2\2\2\u013f\u0132\3\2\2\2\u013f\u0133\3\2\2\2\u013f\u0134\3\2\2\2\u013f"+
		"\u0135\3\2\2\2\u0140%\3\2\2\2\u0141\u0142\5\62\32\2\u0142\u0143\7\f\2"+
		"\2\u0143\u0144\7\61\2\2\u0144\'\3\2\2\2\u0145\u015d\5*\26\2\u0146\u0147"+
		"\7\35\2\2\u0147\u0148\7\36\2\2\u0148\u0149\5(\25\2\u0149\u014a\7\37\2"+
		"\2\u014a\u015d\3\2\2\2\u014b\u014c\7 \2\2\u014c\u014d\7\36\2\2\u014d\u014e"+
		"\5(\25\2\u014e\u014f\7\37\2\2\u014f\u015d\3\2\2\2\u0150\u0151\7!\2\2\u0151"+
		"\u0152\7\36\2\2\u0152\u0153\5(\25\2\u0153\u0154\7\37\2\2\u0154\u015d\3"+
		"\2\2\2\u0155\u0156\7\"\2\2\u0156\u0157\7\36\2\2\u0157\u0158\5(\25\2\u0158"+
		"\u0159\7\16\2\2\u0159\u015a\5(\25\2\u015a\u015b\7\37\2\2\u015b\u015d\3"+
		"\2\2\2\u015c\u0145\3\2\2\2\u015c\u0146\3\2\2\2\u015c\u014b\3\2\2\2\u015c"+
		"\u0150\3\2\2\2\u015c\u0155\3\2\2\2\u015d)\3\2\2\2\u015e\u016e\7#\2\2\u015f"+
		"\u0166\7$\2\2\u0160\u0161\7\32\2\2\u0161\u0162\5,\27\2\u0162\u0163\7\16"+
		"\2\2\u0163\u0164\5,\27\2\u0164\u0165\7\33\2\2\u0165\u0167\3\2\2\2\u0166"+
		"\u0160\3\2\2\2\u0166\u0167\3\2\2\2\u0167\u016e\3\2\2\2\u0168\u016e\7%"+
		"\2\2\u0169\u016e\7&\2\2\u016a\u016e\7\'\2\2\u016b\u016e\7(\2\2\u016c\u016e"+
		"\5\62\32\2\u016d\u015e\3\2\2\2\u016d\u015f\3\2\2\2\u016d\u0168\3\2\2\2"+
		"\u016d\u0169\3\2\2\2\u016d\u016a\3\2\2\2\u016d\u016b\3\2\2\2\u016d\u016c"+
		"\3\2\2\2\u016e+\3\2\2\2\u016f\u0173\7.\2\2\u0170\u0173\5\62\32\2\u0171"+
		"\u0173\7\31\2\2\u0172\u016f\3\2\2\2\u0172\u0170\3\2\2\2\u0172\u0171\3"+
		"\2\2\2\u0173-\3\2\2\2\u0174\u01d1\7)\2\2\u0175\u01d1\7*\2\2\u0176\u01d1"+
		"\7.\2\2\u0177\u01d1\7/\2\2\u0178\u01d1\7\60\2\2\u0179\u017a\5\62\32\2"+
		"\u017a\u017b\7\32\2\2\u017b\u017c\7\61\2\2\u017c\u017d\7\22\2\2\u017d"+
		"\u0184\5.\30\2\u017e\u017f\7\16\2\2\u017f\u0180\7\61\2\2\u0180\u0181\7"+
		"\22\2\2\u0181\u0183\5.\30\2\u0182\u017e\3\2\2\2\u0183\u0186\3\2\2\2\u0184"+
		"\u0182\3\2\2\2\u0184\u0185\3\2\2\2\u0185\u0187\3\2\2\2\u0186\u0184\3\2"+
		"\2\2\u0187\u0188\7\33\2\2\u0188\u01d1\3\2\2\2\u0189\u018c\5\62\32\2\u018a"+
		"\u018b\7\f\2\2\u018b\u018d\7\61\2\2\u018c\u018a\3\2\2\2\u018c\u018d\3"+
		"\2\2\2\u018d\u01d1\3\2\2\2\u018e\u018f\7+\2\2\u018f\u0190\7\36\2\2\u0190"+
		"\u0191\5(\25\2\u0191\u0192\7\37\2\2\u0192\u01d1\3\2\2\2\u0193\u0194\7"+
		",\2\2\u0194\u0195\7\36\2\2\u0195\u0196\5(\25\2\u0196\u0197\7\37\2\2\u0197"+
		"\u0198\7\32\2\2\u0198\u0199\5.\30\2\u0199\u019a\7\33\2\2\u019a\u01d1\3"+
		"\2\2\2\u019b\u019c\7 \2\2\u019c\u019d\7\36\2\2\u019d\u019e\5(\25\2\u019e"+
		"\u019f\7\37\2\2\u019f\u01a8\7\32\2\2\u01a0\u01a5\5.\30\2\u01a1\u01a2\7"+
		"\16\2\2\u01a2\u01a4\5.\30\2\u01a3\u01a1\3\2\2\2\u01a4\u01a7\3\2\2\2\u01a5"+
		"\u01a3\3\2\2\2\u01a5\u01a6\3\2\2\2\u01a6\u01a9\3\2\2\2\u01a7\u01a5\3\2"+
		"\2\2\u01a8\u01a0\3\2\2\2\u01a8\u01a9\3\2\2\2\u01a9\u01aa\3\2\2\2\u01aa"+
		"\u01ab\7\33\2\2\u01ab\u01d1\3\2\2\2\u01ac\u01ad\7!\2\2\u01ad\u01ae\7\36"+
		"\2\2\u01ae\u01af\5(\25\2\u01af\u01b0\7\37\2\2\u01b0\u01b9\7\32\2\2\u01b1"+
		"\u01b6\5.\30\2\u01b2\u01b3\7\16\2\2\u01b3\u01b5\5.\30\2\u01b4\u01b2\3"+
		"\2\2\2\u01b5\u01b8\3\2\2\2\u01b6\u01b4\3\2\2\2\u01b6\u01b7\3\2\2\2\u01b7"+
		"\u01ba\3\2\2\2\u01b8\u01b6\3\2\2\2\u01b9\u01b1\3\2\2\2\u01b9\u01ba\3\2"+
		"\2\2\u01ba\u01bb\3\2\2\2\u01bb\u01bc\7\33\2\2\u01bc\u01d1\3\2\2\2\u01bd"+
		"\u01be\7\"\2\2\u01be\u01bf\7\36\2\2\u01bf\u01c0\5(\25\2\u01c0\u01c1\7"+
		"\16\2\2\u01c1\u01c2\5(\25\2\u01c2\u01c3\7\37\2\2\u01c3\u01cc\7\32\2\2"+
		"\u01c4\u01c9\5\60\31\2\u01c5\u01c6\7\16\2\2\u01c6\u01c8\5\60\31\2\u01c7"+
		"\u01c5\3\2\2\2\u01c8\u01cb\3\2\2\2\u01c9\u01c7\3\2\2\2\u01c9\u01ca\3\2"+
		"\2\2\u01ca\u01cd\3\2\2\2\u01cb\u01c9\3\2\2\2\u01cc\u01c4\3\2\2\2\u01cc"+
		"\u01cd\3\2\2\2\u01cd\u01ce\3\2\2\2\u01ce\u01cf\7\33\2\2\u01cf\u01d1\3"+
		"\2\2\2\u01d0\u0174\3\2\2\2\u01d0\u0175\3\2\2\2\u01d0\u0176\3\2\2\2\u01d0"+
		"\u0177\3\2\2\2\u01d0\u0178\3\2\2\2\u01d0\u0179\3\2\2\2\u01d0\u0189\3\2"+
		"\2\2\u01d0\u018e\3\2\2\2\u01d0\u0193\3\2\2\2\u01d0\u019b\3\2\2\2\u01d0"+
		"\u01ac\3\2\2\2\u01d0\u01bd\3\2\2\2\u01d1/\3\2\2\2\u01d2\u01d3\5.\30\2"+
		"\u01d3\u01d4\7\20\2\2\u01d4\u01d5\5.\30\2\u01d5\61\3\2\2\2\u01d6\u01db"+
		"\7\61\2\2\u01d7\u01d8\7-\2\2\u01d8\u01da\7\61\2\2\u01d9\u01d7\3\2\2\2"+
		"\u01da\u01dd\3\2\2\2\u01db\u01d9\3\2\2\2\u01db\u01dc\3\2\2\2\u01dc\63"+
		"\3\2\2\2\u01dd\u01db\3\2\2\29;>DGMPVY_fiorv|\177\u008c\u0091\u009d\u00a2"+
		"\u00a6\u00ac\u00af\u00be\u00c1\u00c9\u00cd\u00d7\u00da\u00e1\u00eb\u00f6"+
		"\u00fc\u0106\u010c\u0113\u011e\u012b\u012f\u013b\u013f\u015c\u0166\u016d"+
		"\u0172\u0184\u018c\u01a5\u01a8\u01b6\u01b9\u01c9\u01cc\u01d0\u01db";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}