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
		RULE_one = 17, RULE_type = 18, RULE_basicType = 19, RULE_intConstant = 20, 
		RULE_init = 21, RULE_mapEntry = 22, RULE_name = 23;
	public static final String[] ruleNames = {
		"modelFile", "model", "typeDecl", "componentDecl", "connectionDecl", "typeAliasDecl", 
		"enumDecl", "latticeDecl", "recordDecl", "field", "port", "flow", "property", 
		"constantDecl", "behaviour", "expression", "tuple", "one", "type", "basicType", 
		"intConstant", "init", "mapEntry", "name"
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
			setState(48);
			model();
			setState(49);
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
			setState(58);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(51);
				match(T__0);
				setState(55);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__14) | (1L << T__16) | (1L << T__18) | (1L << T__19))) != 0)) {
					{
					{
					setState(52);
					typeDecl();
					}
					}
					setState(57);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(67);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(60);
				match(T__1);
				setState(64);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ID) {
					{
					{
					setState(61);
					constantDecl();
					}
					}
					setState(66);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(76);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(69);
				match(T__2);
				setState(73);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ID) {
					{
					{
					setState(70);
					componentDecl();
					}
					}
					setState(75);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(85);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(78);
				match(T__3);
				setState(82);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ID) {
					{
					{
					setState(79);
					connectionDecl();
					}
					}
					setState(84);
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
			setState(91);
			switch (_input.LA(1)) {
			case T__14:
				_localctx = new AliasTypeDeclContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(87);
				typeAliasDecl();
				}
				break;
			case T__16:
				_localctx = new EnumTypeDeclContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(88);
				enumDecl();
				}
				break;
			case T__18:
				_localctx = new LatticeTypeDeclContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(89);
				latticeDecl();
				}
				break;
			case T__19:
				_localctx = new RecordTypeDeclContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(90);
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
			setState(93);
			name();
			setState(101);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(94);
				match(T__4);
				setState(98);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__20 || _la==T__21) {
					{
					{
					setState(95);
					port();
					}
					}
					setState(100);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(110);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(103);
				match(T__5);
				setState(107);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(104);
						flow();
						}
						} 
					}
					setState(109);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				}
				}
			}

			setState(114);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(112);
				match(T__6);
				setState(113);
				behaviour();
				}
			}

			setState(123);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(116);
				match(T__7);
				setState(120);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(117);
						property();
						}
						} 
					}
					setState(122);
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
			setState(125);
			((ConnectionDeclContext)_localctx).connName = name();
			setState(126);
			match(T__8);
			setState(127);
			((ConnectionDeclContext)_localctx).fromComponent = name();
			setState(128);
			match(T__9);
			setState(129);
			((ConnectionDeclContext)_localctx).fromPort = match(ID);
			setState(141);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(130);
				match(T__10);
				setState(131);
				((ConnectionDeclContext)_localctx).name = name();
				((ConnectionDeclContext)_localctx).fromE.add(((ConnectionDeclContext)_localctx).name);
				setState(136);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(132);
					match(T__11);
					setState(133);
					((ConnectionDeclContext)_localctx).name = name();
					((ConnectionDeclContext)_localctx).fromE.add(((ConnectionDeclContext)_localctx).name);
					}
					}
					setState(138);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(139);
				match(T__12);
				}
			}

			setState(143);
			match(T__13);
			setState(144);
			((ConnectionDeclContext)_localctx).toComponent = name();
			setState(145);
			match(T__9);
			setState(146);
			((ConnectionDeclContext)_localctx).toPort = match(ID);
			setState(158);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(147);
				match(T__10);
				setState(148);
				((ConnectionDeclContext)_localctx).name = name();
				((ConnectionDeclContext)_localctx).toE.add(((ConnectionDeclContext)_localctx).name);
				setState(153);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(149);
					match(T__11);
					setState(150);
					((ConnectionDeclContext)_localctx).name = name();
					((ConnectionDeclContext)_localctx).toE.add(((ConnectionDeclContext)_localctx).name);
					}
					}
					setState(155);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(156);
				match(T__12);
				}
			}

			setState(162);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(160);
				match(T__6);
				setState(161);
				behaviour();
				}
			}

			setState(171);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(164);
				match(T__7);
				setState(168);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(165);
						property();
						}
						} 
					}
					setState(170);
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
			setState(173);
			match(T__14);
			setState(174);
			name();
			setState(175);
			match(T__15);
			setState(176);
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
			setState(178);
			match(T__16);
			setState(179);
			((EnumDeclContext)_localctx).n = name();
			setState(189);
			_la = _input.LA(1);
			if (_la==T__17) {
				{
				setState(180);
				match(T__17);
				setState(181);
				((EnumDeclContext)_localctx).name = name();
				((EnumDeclContext)_localctx).supers.add(((EnumDeclContext)_localctx).name);
				setState(186);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(182);
					match(T__11);
					setState(183);
					((EnumDeclContext)_localctx).name = name();
					((EnumDeclContext)_localctx).supers.add(((EnumDeclContext)_localctx).name);
					}
					}
					setState(188);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(201);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(191);
				match(T__10);
				setState(192);
				((EnumDeclContext)_localctx).ID = match(ID);
				((EnumDeclContext)_localctx).elements.add(((EnumDeclContext)_localctx).ID);
				setState(197);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(193);
					match(T__11);
					setState(194);
					((EnumDeclContext)_localctx).ID = match(ID);
					((EnumDeclContext)_localctx).elements.add(((EnumDeclContext)_localctx).ID);
					}
					}
					setState(199);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(200);
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
			setState(203);
			match(T__18);
			setState(204);
			((LatticeDeclContext)_localctx).n = name();
			setState(214);
			_la = _input.LA(1);
			if (_la==T__17) {
				{
				setState(205);
				match(T__17);
				setState(206);
				((LatticeDeclContext)_localctx).name = name();
				((LatticeDeclContext)_localctx).supers.add(((LatticeDeclContext)_localctx).name);
				setState(211);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(207);
					match(T__11);
					setState(208);
					((LatticeDeclContext)_localctx).name = name();
					((LatticeDeclContext)_localctx).supers.add(((LatticeDeclContext)_localctx).name);
					}
					}
					setState(213);
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
			setState(216);
			match(T__19);
			setState(217);
			name();
			setState(219); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(218);
				field();
				}
				}
				setState(221); 
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
			setState(223);
			match(ID);
			setState(224);
			match(T__8);
			setState(225);
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
			setState(227);
			((PortContext)_localctx).mod = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==T__20 || _la==T__21) ) {
				((PortContext)_localctx).mod = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(228);
			match(ID);
			setState(231);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(229);
				match(T__8);
				setState(230);
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
			setState(233);
			((FlowContext)_localctx).id = match(ID);
			setState(234);
			match(T__8);
			setState(248);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(235);
				((FlowContext)_localctx).from = match(ID);
				setState(236);
				match(T__10);
				setState(237);
				((FlowContext)_localctx).name = name();
				((FlowContext)_localctx).fromE.add(((FlowContext)_localctx).name);
				setState(242);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(238);
					match(T__11);
					setState(239);
					((FlowContext)_localctx).name = name();
					((FlowContext)_localctx).fromE.add(((FlowContext)_localctx).name);
					}
					}
					setState(244);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(245);
				match(T__12);
				}
				break;
			case T__22:
				{
				setState(247);
				match(T__22);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(250);
			match(T__13);
			setState(264);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(251);
				((FlowContext)_localctx).to = match(ID);
				setState(252);
				match(T__10);
				setState(253);
				((FlowContext)_localctx).name = name();
				((FlowContext)_localctx).toE.add(((FlowContext)_localctx).name);
				setState(258);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(254);
					match(T__11);
					setState(255);
					((FlowContext)_localctx).name = name();
					((FlowContext)_localctx).toE.add(((FlowContext)_localctx).name);
					}
					}
					setState(260);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(261);
				match(T__12);
				}
				break;
			case T__22:
				{
				setState(263);
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
			setState(266);
			match(ID);
			setState(267);
			match(T__8);
			setState(268);
			type();
			setState(271);
			_la = _input.LA(1);
			if (_la==T__15) {
				{
				setState(269);
				match(T__15);
				setState(270);
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
			setState(273);
			name();
			setState(274);
			match(T__8);
			setState(275);
			type();
			setState(276);
			match(T__15);
			setState(277);
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
			setState(280); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(279);
					expression();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(282); 
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
			setState(284);
			((ExpressionContext)_localctx).key = tuple();
			setState(285);
			match(T__13);
			setState(286);
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
			setState(299);
			switch (_input.LA(1)) {
			case T__10:
			case T__22:
			case T__25:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(288);
				one();
				}
				break;
			case T__23:
				enterOuterAlt(_localctx, 2);
				{
				setState(289);
				match(T__23);
				setState(290);
				one();
				setState(293); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(291);
					match(T__11);
					setState(292);
					one();
					}
					}
					setState(295); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__11 );
				setState(297);
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
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode ID() { return getToken(Antlr4AwasParser.ID, 0); }
		public FaultRefContext(OneContext ctx) { copyFrom(ctx); }
	}
	public static class FaultSetContext extends OneContext {
		public List<OneContext> one() {
			return getRuleContexts(OneContext.class);
		}
		public OneContext one(int i) {
			return getRuleContext(OneContext.class,i);
		}
		public FaultSetContext(OneContext ctx) { copyFrom(ctx); }
	}

	public final OneContext one() throws RecognitionException {
		OneContext _localctx = new OneContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_one);
		int _la;
		try {
			setState(318);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				_localctx = new NoFailureContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(301);
				match(T__25);
				}
				break;
			case 2:
				_localctx = new WildCardContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(302);
				match(T__22);
				}
				break;
			case 3:
				_localctx = new VariableContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(303);
				match(ID);
				}
				break;
			case 4:
				_localctx = new FaultRefContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(304);
				name();
				setState(305);
				match(T__9);
				setState(306);
				match(ID);
				}
				break;
			case 5:
				_localctx = new FaultSetContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(308);
				match(T__10);
				setState(309);
				one();
				setState(312); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(310);
					match(T__11);
					setState(311);
					one();
					}
					}
					setState(314); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__11 );
				setState(316);
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
		enterRule(_localctx, 36, RULE_type);
		try {
			setState(343);
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
				setState(320);
				basicType();
				}
				break;
			case T__26:
				_localctx = new OptionTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(321);
				match(T__26);
				setState(322);
				match(T__27);
				setState(323);
				type();
				setState(324);
				match(T__28);
				}
				break;
			case T__29:
				_localctx = new SetTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(326);
				match(T__29);
				setState(327);
				match(T__27);
				setState(328);
				type();
				setState(329);
				match(T__28);
				}
				break;
			case T__30:
				_localctx = new SeqTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(331);
				match(T__30);
				setState(332);
				match(T__27);
				setState(333);
				type();
				setState(334);
				match(T__28);
				}
				break;
			case T__31:
				_localctx = new MapTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(336);
				match(T__31);
				setState(337);
				match(T__27);
				setState(338);
				((MapTypeContext)_localctx).key = type();
				setState(339);
				match(T__11);
				setState(340);
				((MapTypeContext)_localctx).value = type();
				setState(341);
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
		enterRule(_localctx, 38, RULE_basicType);
		int _la;
		try {
			setState(360);
			switch (_input.LA(1)) {
			case T__32:
				_localctx = new BooleanTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(345);
				match(T__32);
				}
				break;
			case T__33:
				_localctx = new IntegerTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(346);
				match(T__33);
				setState(353);
				_la = _input.LA(1);
				if (_la==T__23) {
					{
					setState(347);
					match(T__23);
					setState(348);
					((IntegerTypeContext)_localctx).lo = intConstant();
					setState(349);
					match(T__11);
					setState(350);
					((IntegerTypeContext)_localctx).hi = intConstant();
					setState(351);
					match(T__24);
					}
				}

				}
				break;
			case T__34:
				_localctx = new RealTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(355);
				match(T__34);
				}
				break;
			case T__35:
				_localctx = new StringTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(356);
				match(T__35);
				}
				break;
			case T__36:
				_localctx = new ComponentTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(357);
				match(T__36);
				}
				break;
			case T__37:
				_localctx = new PortTypeContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(358);
				match(T__37);
				}
				break;
			case ID:
				_localctx = new NamedTypeContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(359);
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
		enterRule(_localctx, 40, RULE_intConstant);
		try {
			setState(365);
			switch (_input.LA(1)) {
			case INTEGER:
				_localctx = new LitIntConstantContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(362);
				match(INTEGER);
				}
				break;
			case ID:
				_localctx = new NamedIntConstantContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(363);
				name();
				}
				break;
			case T__22:
				_localctx = new ArbitraryIntConstantContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(364);
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
		enterRule(_localctx, 42, RULE_init);
		int _la;
		try {
			setState(459);
			switch ( getInterpreter().adaptivePredict(_input,53,_ctx) ) {
			case 1:
				_localctx = new TrueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(367);
				match(T__38);
				}
				break;
			case 2:
				_localctx = new FalseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(368);
				match(T__39);
				}
				break;
			case 3:
				_localctx = new IntegerContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(369);
				match(INTEGER);
				}
				break;
			case 4:
				_localctx = new RealContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(370);
				match(REAL);
				}
				break;
			case 5:
				_localctx = new StringContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(371);
				match(STRING);
				}
				break;
			case 6:
				_localctx = new RecordContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(372);
				name();
				setState(373);
				match(T__23);
				setState(374);
				match(ID);
				setState(375);
				match(T__15);
				setState(376);
				init();
				setState(383);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__11) {
					{
					{
					setState(377);
					match(T__11);
					setState(378);
					match(ID);
					setState(379);
					match(T__15);
					setState(380);
					init();
					}
					}
					setState(385);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(386);
				match(T__24);
				}
				break;
			case 7:
				_localctx = new NameRefContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(388);
				name();
				setState(391);
				_la = _input.LA(1);
				if (_la==T__9) {
					{
					setState(389);
					match(T__9);
					setState(390);
					match(ID);
					}
				}

				}
				break;
			case 8:
				_localctx = new NoneContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(393);
				match(T__40);
				setState(394);
				match(T__27);
				setState(395);
				type();
				setState(396);
				match(T__28);
				}
				break;
			case 9:
				_localctx = new SomeContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(398);
				match(T__41);
				setState(399);
				match(T__27);
				setState(400);
				type();
				setState(401);
				match(T__28);
				setState(402);
				match(T__23);
				setState(403);
				init();
				setState(404);
				match(T__24);
				}
				break;
			case 10:
				_localctx = new SetContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(406);
				match(T__29);
				setState(407);
				match(T__27);
				setState(408);
				type();
				setState(409);
				match(T__28);
				setState(410);
				match(T__23);
				setState(419);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << INTEGER) | (1L << REAL) | (1L << STRING) | (1L << ID))) != 0)) {
					{
					setState(411);
					init();
					setState(416);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__11) {
						{
						{
						setState(412);
						match(T__11);
						setState(413);
						init();
						}
						}
						setState(418);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(421);
				match(T__24);
				}
				break;
			case 11:
				_localctx = new SeqContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(423);
				match(T__30);
				setState(424);
				match(T__27);
				setState(425);
				type();
				setState(426);
				match(T__28);
				setState(427);
				match(T__23);
				setState(436);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << INTEGER) | (1L << REAL) | (1L << STRING) | (1L << ID))) != 0)) {
					{
					setState(428);
					init();
					setState(433);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__11) {
						{
						{
						setState(429);
						match(T__11);
						setState(430);
						init();
						}
						}
						setState(435);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(438);
				match(T__24);
				}
				break;
			case 12:
				_localctx = new MapContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(440);
				match(T__31);
				setState(441);
				match(T__27);
				setState(442);
				((MapContext)_localctx).key = type();
				setState(443);
				match(T__11);
				setState(444);
				((MapContext)_localctx).value = type();
				setState(445);
				match(T__28);
				setState(446);
				match(T__23);
				setState(455);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__38) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << INTEGER) | (1L << REAL) | (1L << STRING) | (1L << ID))) != 0)) {
					{
					setState(447);
					mapEntry();
					setState(452);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__11) {
						{
						{
						setState(448);
						match(T__11);
						setState(449);
						mapEntry();
						}
						}
						setState(454);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(457);
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
		enterRule(_localctx, 44, RULE_mapEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(461);
			((MapEntryContext)_localctx).key = init();
			setState(462);
			match(T__13);
			setState(463);
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
		enterRule(_localctx, 46, RULE_name);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(465);
			match(ID);
			setState(470);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__42) {
				{
				{
				setState(466);
				match(T__42);
				setState(467);
				match(ID);
				}
				}
				setState(472);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\65\u01dc\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\3\2\3\2\3\2\3\3\3\3\7\38\n\3\f\3\16\3;\13\3\5\3=\n\3\3\3\3\3\7\3A\n\3"+
		"\f\3\16\3D\13\3\5\3F\n\3\3\3\3\3\7\3J\n\3\f\3\16\3M\13\3\5\3O\n\3\3\3"+
		"\3\3\7\3S\n\3\f\3\16\3V\13\3\5\3X\n\3\3\4\3\4\3\4\3\4\5\4^\n\4\3\5\3\5"+
		"\3\5\7\5c\n\5\f\5\16\5f\13\5\5\5h\n\5\3\5\3\5\7\5l\n\5\f\5\16\5o\13\5"+
		"\5\5q\n\5\3\5\3\5\5\5u\n\5\3\5\3\5\7\5y\n\5\f\5\16\5|\13\5\5\5~\n\5\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6\u0089\n\6\f\6\16\6\u008c\13\6\3"+
		"\6\3\6\5\6\u0090\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6\u009a\n\6\f\6"+
		"\16\6\u009d\13\6\3\6\3\6\5\6\u00a1\n\6\3\6\3\6\5\6\u00a5\n\6\3\6\3\6\7"+
		"\6\u00a9\n\6\f\6\16\6\u00ac\13\6\5\6\u00ae\n\6\3\7\3\7\3\7\3\7\3\7\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\7\b\u00bb\n\b\f\b\16\b\u00be\13\b\5\b\u00c0\n\b\3"+
		"\b\3\b\3\b\3\b\7\b\u00c6\n\b\f\b\16\b\u00c9\13\b\3\b\5\b\u00cc\n\b\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\7\t\u00d4\n\t\f\t\16\t\u00d7\13\t\5\t\u00d9\n\t\3"+
		"\n\3\n\3\n\6\n\u00de\n\n\r\n\16\n\u00df\3\13\3\13\3\13\3\13\3\f\3\f\3"+
		"\f\3\f\5\f\u00ea\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00f3\n\r\f\r\16"+
		"\r\u00f6\13\r\3\r\3\r\3\r\5\r\u00fb\n\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u0103"+
		"\n\r\f\r\16\r\u0106\13\r\3\r\3\r\3\r\5\r\u010b\n\r\3\16\3\16\3\16\3\16"+
		"\3\16\5\16\u0112\n\16\3\17\3\17\3\17\3\17\3\17\3\17\3\20\6\20\u011b\n"+
		"\20\r\20\16\20\u011c\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\6\22"+
		"\u0128\n\22\r\22\16\22\u0129\3\22\3\22\5\22\u012e\n\22\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\6\23\u013b\n\23\r\23\16\23\u013c"+
		"\3\23\3\23\5\23\u0141\n\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\5\24\u015a\n\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u0164\n"+
		"\25\3\25\3\25\3\25\3\25\3\25\5\25\u016b\n\25\3\26\3\26\3\26\5\26\u0170"+
		"\n\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\7\27\u0180\n\27\f\27\16\27\u0183\13\27\3\27\3\27\3\27\3\27\3\27"+
		"\5\27\u018a\n\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\7\27\u01a1\n\27\f\27"+
		"\16\27\u01a4\13\27\5\27\u01a6\n\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\7\27\u01b2\n\27\f\27\16\27\u01b5\13\27\5\27\u01b7\n\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\7\27\u01c5"+
		"\n\27\f\27\16\27\u01c8\13\27\5\27\u01ca\n\27\3\27\3\27\5\27\u01ce\n\27"+
		"\3\30\3\30\3\30\3\30\3\31\3\31\3\31\7\31\u01d7\n\31\f\31\16\31\u01da\13"+
		"\31\3\31\2\2\32\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\2\3"+
		"\3\2\27\30\u0212\2\62\3\2\2\2\4<\3\2\2\2\6]\3\2\2\2\b_\3\2\2\2\n\177\3"+
		"\2\2\2\f\u00af\3\2\2\2\16\u00b4\3\2\2\2\20\u00cd\3\2\2\2\22\u00da\3\2"+
		"\2\2\24\u00e1\3\2\2\2\26\u00e5\3\2\2\2\30\u00eb\3\2\2\2\32\u010c\3\2\2"+
		"\2\34\u0113\3\2\2\2\36\u011a\3\2\2\2 \u011e\3\2\2\2\"\u012d\3\2\2\2$\u0140"+
		"\3\2\2\2&\u0159\3\2\2\2(\u016a\3\2\2\2*\u016f\3\2\2\2,\u01cd\3\2\2\2."+
		"\u01cf\3\2\2\2\60\u01d3\3\2\2\2\62\63\5\4\3\2\63\64\7\2\2\3\64\3\3\2\2"+
		"\2\659\7\3\2\2\668\5\6\4\2\67\66\3\2\2\28;\3\2\2\29\67\3\2\2\29:\3\2\2"+
		"\2:=\3\2\2\2;9\3\2\2\2<\65\3\2\2\2<=\3\2\2\2=E\3\2\2\2>B\7\4\2\2?A\5\34"+
		"\17\2@?\3\2\2\2AD\3\2\2\2B@\3\2\2\2BC\3\2\2\2CF\3\2\2\2DB\3\2\2\2E>\3"+
		"\2\2\2EF\3\2\2\2FN\3\2\2\2GK\7\5\2\2HJ\5\b\5\2IH\3\2\2\2JM\3\2\2\2KI\3"+
		"\2\2\2KL\3\2\2\2LO\3\2\2\2MK\3\2\2\2NG\3\2\2\2NO\3\2\2\2OW\3\2\2\2PT\7"+
		"\6\2\2QS\5\n\6\2RQ\3\2\2\2SV\3\2\2\2TR\3\2\2\2TU\3\2\2\2UX\3\2\2\2VT\3"+
		"\2\2\2WP\3\2\2\2WX\3\2\2\2X\5\3\2\2\2Y^\5\f\7\2Z^\5\16\b\2[^\5\20\t\2"+
		"\\^\5\22\n\2]Y\3\2\2\2]Z\3\2\2\2][\3\2\2\2]\\\3\2\2\2^\7\3\2\2\2_g\5\60"+
		"\31\2`d\7\7\2\2ac\5\26\f\2ba\3\2\2\2cf\3\2\2\2db\3\2\2\2de\3\2\2\2eh\3"+
		"\2\2\2fd\3\2\2\2g`\3\2\2\2gh\3\2\2\2hp\3\2\2\2im\7\b\2\2jl\5\30\r\2kj"+
		"\3\2\2\2lo\3\2\2\2mk\3\2\2\2mn\3\2\2\2nq\3\2\2\2om\3\2\2\2pi\3\2\2\2p"+
		"q\3\2\2\2qt\3\2\2\2rs\7\t\2\2su\5\36\20\2tr\3\2\2\2tu\3\2\2\2u}\3\2\2"+
		"\2vz\7\n\2\2wy\5\32\16\2xw\3\2\2\2y|\3\2\2\2zx\3\2\2\2z{\3\2\2\2{~\3\2"+
		"\2\2|z\3\2\2\2}v\3\2\2\2}~\3\2\2\2~\t\3\2\2\2\177\u0080\5\60\31\2\u0080"+
		"\u0081\7\13\2\2\u0081\u0082\5\60\31\2\u0082\u0083\7\f\2\2\u0083\u008f"+
		"\7\61\2\2\u0084\u0085\7\r\2\2\u0085\u008a\5\60\31\2\u0086\u0087\7\16\2"+
		"\2\u0087\u0089\5\60\31\2\u0088\u0086\3\2\2\2\u0089\u008c\3\2\2\2\u008a"+
		"\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u008d\3\2\2\2\u008c\u008a\3\2"+
		"\2\2\u008d\u008e\7\17\2\2\u008e\u0090\3\2\2\2\u008f\u0084\3\2\2\2\u008f"+
		"\u0090\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u0092\7\20\2\2\u0092\u0093\5"+
		"\60\31\2\u0093\u0094\7\f\2\2\u0094\u00a0\7\61\2\2\u0095\u0096\7\r\2\2"+
		"\u0096\u009b\5\60\31\2\u0097\u0098\7\16\2\2\u0098\u009a\5\60\31\2\u0099"+
		"\u0097\3\2\2\2\u009a\u009d\3\2\2\2\u009b\u0099\3\2\2\2\u009b\u009c\3\2"+
		"\2\2\u009c\u009e\3\2\2\2\u009d\u009b\3\2\2\2\u009e\u009f\7\17\2\2\u009f"+
		"\u00a1\3\2\2\2\u00a0\u0095\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a4\3\2"+
		"\2\2\u00a2\u00a3\7\t\2\2\u00a3\u00a5\5\36\20\2\u00a4\u00a2\3\2\2\2\u00a4"+
		"\u00a5\3\2\2\2\u00a5\u00ad\3\2\2\2\u00a6\u00aa\7\n\2\2\u00a7\u00a9\5\32"+
		"\16\2\u00a8\u00a7\3\2\2\2\u00a9\u00ac\3\2\2\2\u00aa\u00a8\3\2\2\2\u00aa"+
		"\u00ab\3\2\2\2\u00ab\u00ae\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ad\u00a6\3\2"+
		"\2\2\u00ad\u00ae\3\2\2\2\u00ae\13\3\2\2\2\u00af\u00b0\7\21\2\2\u00b0\u00b1"+
		"\5\60\31\2\u00b1\u00b2\7\22\2\2\u00b2\u00b3\5&\24\2\u00b3\r\3\2\2\2\u00b4"+
		"\u00b5\7\23\2\2\u00b5\u00bf\5\60\31\2\u00b6\u00b7\7\24\2\2\u00b7\u00bc"+
		"\5\60\31\2\u00b8\u00b9\7\16\2\2\u00b9\u00bb\5\60\31\2\u00ba\u00b8\3\2"+
		"\2\2\u00bb\u00be\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd"+
		"\u00c0\3\2\2\2\u00be\u00bc\3\2\2\2\u00bf\u00b6\3\2\2\2\u00bf\u00c0\3\2"+
		"\2\2\u00c0\u00cb\3\2\2\2\u00c1\u00c2\7\r\2\2\u00c2\u00c7\7\61\2\2\u00c3"+
		"\u00c4\7\16\2\2\u00c4\u00c6\7\61\2\2\u00c5\u00c3\3\2\2\2\u00c6\u00c9\3"+
		"\2\2\2\u00c7\u00c5\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00ca\3\2\2\2\u00c9"+
		"\u00c7\3\2\2\2\u00ca\u00cc\7\17\2\2\u00cb\u00c1\3\2\2\2\u00cb\u00cc\3"+
		"\2\2\2\u00cc\17\3\2\2\2\u00cd\u00ce\7\25\2\2\u00ce\u00d8\5\60\31\2\u00cf"+
		"\u00d0\7\24\2\2\u00d0\u00d5\5\60\31\2\u00d1\u00d2\7\16\2\2\u00d2\u00d4"+
		"\5\60\31\2\u00d3\u00d1\3\2\2\2\u00d4\u00d7\3\2\2\2\u00d5\u00d3\3\2\2\2"+
		"\u00d5\u00d6\3\2\2\2\u00d6\u00d9\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d8\u00cf"+
		"\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\21\3\2\2\2\u00da\u00db\7\26\2\2\u00db"+
		"\u00dd\5\60\31\2\u00dc\u00de\5\24\13\2\u00dd\u00dc\3\2\2\2\u00de\u00df"+
		"\3\2\2\2\u00df\u00dd\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0\23\3\2\2\2\u00e1"+
		"\u00e2\7\61\2\2\u00e2\u00e3\7\13\2\2\u00e3\u00e4\5&\24\2\u00e4\25\3\2"+
		"\2\2\u00e5\u00e6\t\2\2\2\u00e6\u00e9\7\61\2\2\u00e7\u00e8\7\13\2\2\u00e8"+
		"\u00ea\5\60\31\2\u00e9\u00e7\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea\27\3\2"+
		"\2\2\u00eb\u00ec\7\61\2\2\u00ec\u00fa\7\13\2\2\u00ed\u00ee\7\61\2\2\u00ee"+
		"\u00ef\7\r\2\2\u00ef\u00f4\5\60\31\2\u00f0\u00f1\7\16\2\2\u00f1\u00f3"+
		"\5\60\31\2\u00f2\u00f0\3\2\2\2\u00f3\u00f6\3\2\2\2\u00f4\u00f2\3\2\2\2"+
		"\u00f4\u00f5\3\2\2\2\u00f5\u00f7\3\2\2\2\u00f6\u00f4\3\2\2\2\u00f7\u00f8"+
		"\7\17\2\2\u00f8\u00fb\3\2\2\2\u00f9\u00fb\7\31\2\2\u00fa\u00ed\3\2\2\2"+
		"\u00fa\u00f9\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\u010a\7\20\2\2\u00fd\u00fe"+
		"\7\61\2\2\u00fe\u00ff\7\r\2\2\u00ff\u0104\5\60\31\2\u0100\u0101\7\16\2"+
		"\2\u0101\u0103\5\60\31\2\u0102\u0100\3\2\2\2\u0103\u0106\3\2\2\2\u0104"+
		"\u0102\3\2\2\2\u0104\u0105\3\2\2\2\u0105\u0107\3\2\2\2\u0106\u0104\3\2"+
		"\2\2\u0107\u0108\7\17\2\2\u0108\u010b\3\2\2\2\u0109\u010b\7\31\2\2\u010a"+
		"\u00fd\3\2\2\2\u010a\u0109\3\2\2\2\u010b\31\3\2\2\2\u010c\u010d\7\61\2"+
		"\2\u010d\u010e\7\13\2\2\u010e\u0111\5&\24\2\u010f\u0110\7\22\2\2\u0110"+
		"\u0112\5,\27\2\u0111\u010f\3\2\2\2\u0111\u0112\3\2\2\2\u0112\33\3\2\2"+
		"\2\u0113\u0114\5\60\31\2\u0114\u0115\7\13\2\2\u0115\u0116\5&\24\2\u0116"+
		"\u0117\7\22\2\2\u0117\u0118\5,\27\2\u0118\35\3\2\2\2\u0119\u011b\5 \21"+
		"\2\u011a\u0119\3\2\2\2\u011b\u011c\3\2\2\2\u011c\u011a\3\2\2\2\u011c\u011d"+
		"\3\2\2\2\u011d\37\3\2\2\2\u011e\u011f\5\"\22\2\u011f\u0120\7\20\2\2\u0120"+
		"\u0121\5\"\22\2\u0121!\3\2\2\2\u0122\u012e\5$\23\2\u0123\u0124\7\32\2"+
		"\2\u0124\u0127\5$\23\2\u0125\u0126\7\16\2\2\u0126\u0128\5$\23\2\u0127"+
		"\u0125\3\2\2\2\u0128\u0129\3\2\2\2\u0129\u0127\3\2\2\2\u0129\u012a\3\2"+
		"\2\2\u012a\u012b\3\2\2\2\u012b\u012c\7\33\2\2\u012c\u012e\3\2\2\2\u012d"+
		"\u0122\3\2\2\2\u012d\u0123\3\2\2\2\u012e#\3\2\2\2\u012f\u0141\7\34\2\2"+
		"\u0130\u0141\7\31\2\2\u0131\u0141\7\61\2\2\u0132\u0133\5\60\31\2\u0133"+
		"\u0134\7\f\2\2\u0134\u0135\7\61\2\2\u0135\u0141\3\2\2\2\u0136\u0137\7"+
		"\r\2\2\u0137\u013a\5$\23\2\u0138\u0139\7\16\2\2\u0139\u013b\5$\23\2\u013a"+
		"\u0138\3\2\2\2\u013b\u013c\3\2\2\2\u013c\u013a\3\2\2\2\u013c\u013d\3\2"+
		"\2\2\u013d\u013e\3\2\2\2\u013e\u013f\7\17\2\2\u013f\u0141\3\2\2\2\u0140"+
		"\u012f\3\2\2\2\u0140\u0130\3\2\2\2\u0140\u0131\3\2\2\2\u0140\u0132\3\2"+
		"\2\2\u0140\u0136\3\2\2\2\u0141%\3\2\2\2\u0142\u015a\5(\25\2\u0143\u0144"+
		"\7\35\2\2\u0144\u0145\7\36\2\2\u0145\u0146\5&\24\2\u0146\u0147\7\37\2"+
		"\2\u0147\u015a\3\2\2\2\u0148\u0149\7 \2\2\u0149\u014a\7\36\2\2\u014a\u014b"+
		"\5&\24\2\u014b\u014c\7\37\2\2\u014c\u015a\3\2\2\2\u014d\u014e\7!\2\2\u014e"+
		"\u014f\7\36\2\2\u014f\u0150\5&\24\2\u0150\u0151\7\37\2\2\u0151\u015a\3"+
		"\2\2\2\u0152\u0153\7\"\2\2\u0153\u0154\7\36\2\2\u0154\u0155\5&\24\2\u0155"+
		"\u0156\7\16\2\2\u0156\u0157\5&\24\2\u0157\u0158\7\37\2\2\u0158\u015a\3"+
		"\2\2\2\u0159\u0142\3\2\2\2\u0159\u0143\3\2\2\2\u0159\u0148\3\2\2\2\u0159"+
		"\u014d\3\2\2\2\u0159\u0152\3\2\2\2\u015a\'\3\2\2\2\u015b\u016b\7#\2\2"+
		"\u015c\u0163\7$\2\2\u015d\u015e\7\32\2\2\u015e\u015f\5*\26\2\u015f\u0160"+
		"\7\16\2\2\u0160\u0161\5*\26\2\u0161\u0162\7\33\2\2\u0162\u0164\3\2\2\2"+
		"\u0163\u015d\3\2\2\2\u0163\u0164\3\2\2\2\u0164\u016b\3\2\2\2\u0165\u016b"+
		"\7%\2\2\u0166\u016b\7&\2\2\u0167\u016b\7\'\2\2\u0168\u016b\7(\2\2\u0169"+
		"\u016b\5\60\31\2\u016a\u015b\3\2\2\2\u016a\u015c\3\2\2\2\u016a\u0165\3"+
		"\2\2\2\u016a\u0166\3\2\2\2\u016a\u0167\3\2\2\2\u016a\u0168\3\2\2\2\u016a"+
		"\u0169\3\2\2\2\u016b)\3\2\2\2\u016c\u0170\7.\2\2\u016d\u0170\5\60\31\2"+
		"\u016e\u0170\7\31\2\2\u016f\u016c\3\2\2\2\u016f\u016d\3\2\2\2\u016f\u016e"+
		"\3\2\2\2\u0170+\3\2\2\2\u0171\u01ce\7)\2\2\u0172\u01ce\7*\2\2\u0173\u01ce"+
		"\7.\2\2\u0174\u01ce\7/\2\2\u0175\u01ce\7\60\2\2\u0176\u0177\5\60\31\2"+
		"\u0177\u0178\7\32\2\2\u0178\u0179\7\61\2\2\u0179\u017a\7\22\2\2\u017a"+
		"\u0181\5,\27\2\u017b\u017c\7\16\2\2\u017c\u017d\7\61\2\2\u017d\u017e\7"+
		"\22\2\2\u017e\u0180\5,\27\2\u017f\u017b\3\2\2\2\u0180\u0183\3\2\2\2\u0181"+
		"\u017f\3\2\2\2\u0181\u0182\3\2\2\2\u0182\u0184\3\2\2\2\u0183\u0181\3\2"+
		"\2\2\u0184\u0185\7\33\2\2\u0185\u01ce\3\2\2\2\u0186\u0189\5\60\31\2\u0187"+
		"\u0188\7\f\2\2\u0188\u018a\7\61\2\2\u0189\u0187\3\2\2\2\u0189\u018a\3"+
		"\2\2\2\u018a\u01ce\3\2\2\2\u018b\u018c\7+\2\2\u018c\u018d\7\36\2\2\u018d"+
		"\u018e\5&\24\2\u018e\u018f\7\37\2\2\u018f\u01ce\3\2\2\2\u0190\u0191\7"+
		",\2\2\u0191\u0192\7\36\2\2\u0192\u0193\5&\24\2\u0193\u0194\7\37\2\2\u0194"+
		"\u0195\7\32\2\2\u0195\u0196\5,\27\2\u0196\u0197\7\33\2\2\u0197\u01ce\3"+
		"\2\2\2\u0198\u0199\7 \2\2\u0199\u019a\7\36\2\2\u019a\u019b\5&\24\2\u019b"+
		"\u019c\7\37\2\2\u019c\u01a5\7\32\2\2\u019d\u01a2\5,\27\2\u019e\u019f\7"+
		"\16\2\2\u019f\u01a1\5,\27\2\u01a0\u019e\3\2\2\2\u01a1\u01a4\3\2\2\2\u01a2"+
		"\u01a0\3\2\2\2\u01a2\u01a3\3\2\2\2\u01a3\u01a6\3\2\2\2\u01a4\u01a2\3\2"+
		"\2\2\u01a5\u019d\3\2\2\2\u01a5\u01a6\3\2\2\2\u01a6\u01a7\3\2\2\2\u01a7"+
		"\u01a8\7\33\2\2\u01a8\u01ce\3\2\2\2\u01a9\u01aa\7!\2\2\u01aa\u01ab\7\36"+
		"\2\2\u01ab\u01ac\5&\24\2\u01ac\u01ad\7\37\2\2\u01ad\u01b6\7\32\2\2\u01ae"+
		"\u01b3\5,\27\2\u01af\u01b0\7\16\2\2\u01b0\u01b2\5,\27\2\u01b1\u01af\3"+
		"\2\2\2\u01b2\u01b5\3\2\2\2\u01b3\u01b1\3\2\2\2\u01b3\u01b4\3\2\2\2\u01b4"+
		"\u01b7\3\2\2\2\u01b5\u01b3\3\2\2\2\u01b6\u01ae\3\2\2\2\u01b6\u01b7\3\2"+
		"\2\2\u01b7\u01b8\3\2\2\2\u01b8\u01b9\7\33\2\2\u01b9\u01ce\3\2\2\2\u01ba"+
		"\u01bb\7\"\2\2\u01bb\u01bc\7\36\2\2\u01bc\u01bd\5&\24\2\u01bd\u01be\7"+
		"\16\2\2\u01be\u01bf\5&\24\2\u01bf\u01c0\7\37\2\2\u01c0\u01c9\7\32\2\2"+
		"\u01c1\u01c6\5.\30\2\u01c2\u01c3\7\16\2\2\u01c3\u01c5\5.\30\2\u01c4\u01c2"+
		"\3\2\2\2\u01c5\u01c8\3\2\2\2\u01c6\u01c4\3\2\2\2\u01c6\u01c7\3\2\2\2\u01c7"+
		"\u01ca\3\2\2\2\u01c8\u01c6\3\2\2\2\u01c9\u01c1\3\2\2\2\u01c9\u01ca\3\2"+
		"\2\2\u01ca\u01cb\3\2\2\2\u01cb\u01cc\7\33\2\2\u01cc\u01ce\3\2\2\2\u01cd"+
		"\u0171\3\2\2\2\u01cd\u0172\3\2\2\2\u01cd\u0173\3\2\2\2\u01cd\u0174\3\2"+
		"\2\2\u01cd\u0175\3\2\2\2\u01cd\u0176\3\2\2\2\u01cd\u0186\3\2\2\2\u01cd"+
		"\u018b\3\2\2\2\u01cd\u0190\3\2\2\2\u01cd\u0198\3\2\2\2\u01cd\u01a9\3\2"+
		"\2\2\u01cd\u01ba\3\2\2\2\u01ce-\3\2\2\2\u01cf\u01d0\5,\27\2\u01d0\u01d1"+
		"\7\20\2\2\u01d1\u01d2\5,\27\2\u01d2/\3\2\2\2\u01d3\u01d8\7\61\2\2\u01d4"+
		"\u01d5\7-\2\2\u01d5\u01d7\7\61\2\2\u01d6\u01d4\3\2\2\2\u01d7\u01da\3\2"+
		"\2\2\u01d8\u01d6\3\2\2\2\u01d8\u01d9\3\2\2\2\u01d9\61\3\2\2\2\u01da\u01d8"+
		"\3\2\2\299<BEKNTW]dgmptz}\u008a\u008f\u009b\u00a0\u00a4\u00aa\u00ad\u00bc"+
		"\u00bf\u00c7\u00cb\u00d5\u00d8\u00df\u00e9\u00f4\u00fa\u0104\u010a\u0111"+
		"\u011c\u0129\u012d\u013c\u0140\u0159\u0163\u016a\u016f\u0181\u0189\u01a2"+
		"\u01a5\u01b3\u01b6\u01c6\u01c9\u01cd\u01d8";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}