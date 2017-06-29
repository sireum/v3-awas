// Generated from /workspace-v3/sireum-v3/awas/jvm/src/main/resources/org/sireum/awas/parser/Antlr4Awas.g4 by ANTLR 4.7
package org.sireum.awas.parser;

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
public class Antlr4AwasParser extends Parser {
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
            T__24 = 25, T__25 = 26, T__26 = 27, T__27 = 28, T__28 = 29, T__29 = 30, T__30 = 31,
            T__31 = 32, T__32 = 33, T__33 = 34, T__34 = 35, T__35 = 36, T__36 = 37, T__37 = 38,
            T__38 = 39, T__39 = 40, T__40 = 41, T__41 = 42, T__42 = 43, T__43 = 44, T__44 = 45,
            T__45 = 46, T__46 = 47, T__47 = 48, T__48 = 49, T__49 = 50, INTEGER = 51, REAL = 52,
            STRING = 53, ID = 54, WS = 55, COMMENT = 56, LINE_COMMENT = 57, ERROR_CHAR = 58;
    public static final int
            RULE_modelFile = 0, RULE_model = 1, RULE_typeDecl = 2, RULE_behaviorDecl = 3,
            RULE_componentDecl = 4, RULE_connectionDecl = 5, RULE_typeAliasDecl = 6,
            RULE_enumDecl = 7, RULE_latticeDecl = 8, RULE_recordDecl = 9, RULE_field = 10,
            RULE_port = 11, RULE_propagation = 12, RULE_flow = 13, RULE_flowc = 14,
            RULE_property = 15, RULE_constantDecl = 16, RULE_transition = 17, RULE_transExpr = 18,
            RULE_behaviour = 19, RULE_expression = 20, RULE_idGroup = 21, RULE_tuple = 22,
            RULE_faultPort = 23, RULE_one = 24, RULE_fault = 25, RULE_type = 26, RULE_basicType = 27,
            RULE_intConstant = 28, RULE_init = 29, RULE_mapEntry = 30, RULE_name = 31,
            RULE_states = 32, RULE_events = 33;
    public static final String[] ruleNames = {
            "modelFile", "model", "typeDecl", "behaviorDecl", "componentDecl", "connectionDecl",
            "typeAliasDecl", "enumDecl", "latticeDecl", "recordDecl", "field", "port",
            "propagation", "flow", "flowc", "property", "constantDecl", "transition",
            "transExpr", "behaviour", "expression", "idGroup", "tuple", "faultPort",
            "one", "fault", "type", "basicType", "intConstant", "init", "mapEntry",
            "name", "states", "events"
    };

    private static final String[] _LITERAL_NAMES = {
            null, "'types'", "'behavior'", "'constants'", "'components'", "'connections'",
            "':'", "'with'", "','", "'ports'", "'propagations'", "'flows'", "'transitions'",
            "'properties'", "'.'", "'->'", "'alias'", "'='", "'enum'", "'extends'",
            "'{'", "'}'", "'lattice'", "'record'", "'in'", "'out'", "'*'", "'-['",
            "']->'", "'('", "')'", "'Option'", "'['", "']'", "'Set'", "'Seq'", "'Map'",
            "'Boolean'", "'Integer'", "'Real'", "'String'", "'Component'", "'Port'",
            "'_'", "'true'", "'false'", "'None'", "'Some'", "'::'", "'states'", "'events'"
    };
    private static final String[] _SYMBOLIC_NAMES = {
            null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, "INTEGER", "REAL", "STRING", "ID", "WS", "COMMENT",
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
	public String getGrammarFileName() { return "Antlr4Awas.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
    public ATN getATN() {
        return _ATN;
    }

    public Antlr4AwasParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    public static class ModelFileContext extends ParserRuleContext {
        public ModelContext model() {
            return getRuleContext(ModelContext.class, 0);
        }

        public TerminalNode EOF() {
            return getToken(Antlr4AwasParser.EOF, 0);
        }

        public ModelFileContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_modelFile;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterModelFile(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitModelFile(this);
        }
    }

    public final ModelFileContext modelFile() throws RecognitionException {
        ModelFileContext _localctx = new ModelFileContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_modelFile);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(68);
                model();
                setState(69);
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
        public List<TypeDeclContext> typeDecl() {
            return getRuleContexts(TypeDeclContext.class);
        }

        public TypeDeclContext typeDecl(int i) {
            return getRuleContext(TypeDeclContext.class, i);
        }

        public List<BehaviorDeclContext> behaviorDecl() {
            return getRuleContexts(BehaviorDeclContext.class);
        }

        public BehaviorDeclContext behaviorDecl(int i) {
            return getRuleContext(BehaviorDeclContext.class, i);
        }

        public List<ConstantDeclContext> constantDecl() {
            return getRuleContexts(ConstantDeclContext.class);
        }

        public ConstantDeclContext constantDecl(int i) {
            return getRuleContext(ConstantDeclContext.class, i);
        }

        public List<ComponentDeclContext> componentDecl() {
            return getRuleContexts(ComponentDeclContext.class);
        }

        public ComponentDeclContext componentDecl(int i) {
            return getRuleContext(ComponentDeclContext.class, i);
        }

        public List<ConnectionDeclContext> connectionDecl() {
            return getRuleContexts(ConnectionDeclContext.class);
        }

        public ConnectionDeclContext connectionDecl(int i) {
            return getRuleContext(ConnectionDeclContext.class, i);
        }

        public ModelContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_model;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterModel(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitModel(this);
        }
    }

    public final ModelContext model() throws RecognitionException {
        ModelContext _localctx = new ModelContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_model);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(78);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__0) {
                    {
                        setState(71);
                        match(T__0);
                        setState(75);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << T__17) | (1L << T__21) | (1L << T__22))) != 0)) {
                            {
                                {
                                    setState(72);
                                    typeDecl();
                                }
                            }
                            setState(77);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                    }
                }

                setState(87);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__1) {
                    {
                        setState(80);
                        match(T__1);
                        setState(84);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == ID) {
                            {
                                {
                                    setState(81);
                                    behaviorDecl();
                                }
                            }
                            setState(86);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                    }
                }

                setState(96);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__2) {
                    {
                        setState(89);
                        match(T__2);
                        setState(93);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == ID) {
                            {
                                {
                                    setState(90);
                                    constantDecl();
                                }
                            }
                            setState(95);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                    }
                }

                setState(105);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__3) {
                    {
                        setState(98);
                        match(T__3);
                        setState(102);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == ID) {
                            {
                                {
                                    setState(99);
                                    componentDecl();
                                }
                            }
                            setState(104);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                    }
                }

                setState(114);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__4) {
                    {
                        setState(107);
                        match(T__4);
                        setState(111);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == ID) {
                            {
                                {
                                    setState(108);
                                    connectionDecl();
                                }
                            }
                            setState(113);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                    }
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

    public static class TypeDeclContext extends ParserRuleContext {
        public TypeDeclContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_typeDecl;
        }

        public TypeDeclContext() {
        }

        public void copyFrom(TypeDeclContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class EnumTypeDeclContext extends TypeDeclContext {
        public EnumDeclContext enumDecl() {
            return getRuleContext(EnumDeclContext.class, 0);
        }

        public EnumTypeDeclContext(TypeDeclContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterEnumTypeDecl(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitEnumTypeDecl(this);
        }
    }

    public static class LatticeTypeDeclContext extends TypeDeclContext {
        public LatticeDeclContext latticeDecl() {
            return getRuleContext(LatticeDeclContext.class, 0);
        }

        public LatticeTypeDeclContext(TypeDeclContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterLatticeTypeDecl(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitLatticeTypeDecl(this);
        }
    }

    public static class AliasTypeDeclContext extends TypeDeclContext {
        public TypeAliasDeclContext typeAliasDecl() {
            return getRuleContext(TypeAliasDeclContext.class, 0);
        }

        public AliasTypeDeclContext(TypeDeclContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterAliasTypeDecl(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitAliasTypeDecl(this);
        }
    }

    public static class RecordTypeDeclContext extends TypeDeclContext {
        public RecordDeclContext recordDecl() {
            return getRuleContext(RecordDeclContext.class, 0);
        }

        public RecordTypeDeclContext(TypeDeclContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterRecordTypeDecl(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitRecordTypeDecl(this);
        }
    }

    public final TypeDeclContext typeDecl() throws RecognitionException {
        TypeDeclContext _localctx = new TypeDeclContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_typeDecl);
        try {
            setState(120);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case T__15:
                    _localctx = new AliasTypeDeclContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(116);
                    typeAliasDecl();
                }
                break;
                case T__17:
                    _localctx = new EnumTypeDeclContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(117);
                    enumDecl();
                }
                break;
                case T__21:
                    _localctx = new LatticeTypeDeclContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                {
                    setState(118);
                    latticeDecl();
                }
                break;
                case T__22:
                    _localctx = new RecordTypeDeclContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                {
                    setState(119);
                    recordDecl();
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

    public static class BehaviorDeclContext extends ParserRuleContext {
        public Token smName;

        public StatesContext states() {
            return getRuleContext(StatesContext.class, 0);
        }

        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public EventsContext events() {
            return getRuleContext(EventsContext.class, 0);
        }

        public BehaviorDeclContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_behaviorDecl;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterBehaviorDecl(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitBehaviorDecl(this);
        }
    }

    public final BehaviorDeclContext behaviorDecl() throws RecognitionException {
        BehaviorDeclContext _localctx = new BehaviorDeclContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_behaviorDecl);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(122);
                ((BehaviorDeclContext) _localctx).smName = match(ID);
                setState(123);
                match(T__5);
                setState(124);
                states();
                setState(126);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__49) {
                    {
                        setState(125);
                        events();
                    }
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

    public static class ComponentDeclContext extends ParserRuleContext {
        public Token compName;
        public NameContext name;
        public List<NameContext> with = new ArrayList<NameContext>();

        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public TransitionContext transition() {
            return getRuleContext(TransitionContext.class, 0);
        }

        public BehaviourContext behaviour() {
            return getRuleContext(BehaviourContext.class, 0);
        }

        public List<NameContext> name() {
            return getRuleContexts(NameContext.class);
        }

        public NameContext name(int i) {
            return getRuleContext(NameContext.class, i);
        }

        public List<PortContext> port() {
            return getRuleContexts(PortContext.class);
        }

        public PortContext port(int i) {
            return getRuleContext(PortContext.class, i);
        }

        public List<PropagationContext> propagation() {
            return getRuleContexts(PropagationContext.class);
        }

        public PropagationContext propagation(int i) {
            return getRuleContext(PropagationContext.class, i);
        }

        public List<FlowContext> flow() {
            return getRuleContexts(FlowContext.class);
        }

        public FlowContext flow(int i) {
            return getRuleContext(FlowContext.class, i);
        }

        public List<PropertyContext> property() {
            return getRuleContexts(PropertyContext.class);
        }

        public PropertyContext property(int i) {
            return getRuleContext(PropertyContext.class, i);
        }

        public ComponentDeclContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_componentDecl;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterComponentDecl(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitComponentDecl(this);
        }
    }

    public final ComponentDeclContext componentDecl() throws RecognitionException {
        ComponentDeclContext _localctx = new ComponentDeclContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_componentDecl);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(128);
                ((ComponentDeclContext) _localctx).compName = match(ID);
                setState(138);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__6) {
                    {
                        setState(129);
                        match(T__6);
                        setState(130);
                        ((ComponentDeclContext) _localctx).name = name();
                        ((ComponentDeclContext) _localctx).with.add(((ComponentDeclContext) _localctx).name);
                        setState(135);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == T__7) {
                            {
                                {
                                    setState(131);
                                    match(T__7);
                                    setState(132);
                                    ((ComponentDeclContext) _localctx).name = name();
                                    ((ComponentDeclContext) _localctx).with.add(((ComponentDeclContext) _localctx).name);
                                }
                            }
                            setState(137);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                    }
                }

                setState(147);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__8) {
                    {
                        setState(140);
                        match(T__8);
                        setState(144);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == T__23 || _la == T__24) {
                            {
                                {
                                    setState(141);
                                    port();
                                }
                            }
                            setState(146);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                    }
                }

                setState(156);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__9) {
                    {
                        setState(149);
                        match(T__9);
                        setState(153);
                        _errHandler.sync(this);
                        _alt = getInterpreter().adaptivePredict(_input, 16, _ctx);
                        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                            if (_alt == 1) {
                                {
                                    {
                                        setState(150);
                                        propagation();
                                    }
                                }
                            }
                            setState(155);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 16, _ctx);
                        }
                    }
                }

                setState(165);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__10) {
                    {
                        setState(158);
                        match(T__10);
                        setState(162);
                        _errHandler.sync(this);
                        _alt = getInterpreter().adaptivePredict(_input, 18, _ctx);
                        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                            if (_alt == 1) {
                                {
                                    {
                                        setState(159);
                                        flow();
                                    }
                                }
                            }
                            setState(164);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 18, _ctx);
                        }
                    }
                }

                setState(169);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__11) {
                    {
                        setState(167);
                        match(T__11);
                        setState(168);
                        transition();
                    }
                }

                setState(173);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__1) {
                    {
                        setState(171);
                        match(T__1);
                        setState(172);
                        behaviour();
                    }
                }

                setState(182);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__12) {
                    {
                        setState(175);
                        match(T__12);
                        setState(179);
                        _errHandler.sync(this);
                        _alt = getInterpreter().adaptivePredict(_input, 22, _ctx);
                        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                            if (_alt == 1) {
                                {
                                    {
                                        setState(176);
                                        property();
                                    }
                                }
                            }
                            setState(181);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 22, _ctx);
                        }
                    }
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

    public static class ConnectionDeclContext extends ParserRuleContext {
        public Token connName;
        public NameContext fromComponent;
        public Token fromPort;
        public NameContext toComponent;
        public Token toPort;

        public List<TerminalNode> ID() {
            return getTokens(Antlr4AwasParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(Antlr4AwasParser.ID, i);
        }

        public List<NameContext> name() {
            return getRuleContexts(NameContext.class);
        }

        public NameContext name(int i) {
            return getRuleContext(NameContext.class, i);
        }

        public BehaviourContext behaviour() {
            return getRuleContext(BehaviourContext.class, 0);
        }

        public List<FlowcContext> flowc() {
            return getRuleContexts(FlowcContext.class);
        }

        public FlowcContext flowc(int i) {
            return getRuleContext(FlowcContext.class, i);
        }

        public List<PropertyContext> property() {
            return getRuleContexts(PropertyContext.class);
        }

        public PropertyContext property(int i) {
            return getRuleContext(PropertyContext.class, i);
        }

        public ConnectionDeclContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_connectionDecl;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterConnectionDecl(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitConnectionDecl(this);
        }
    }

    public final ConnectionDeclContext connectionDecl() throws RecognitionException {
        ConnectionDeclContext _localctx = new ConnectionDeclContext(_ctx, getState());
        enterRule(_localctx, 10, RULE_connectionDecl);
        int _la;
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(184);
                ((ConnectionDeclContext) _localctx).connName = match(ID);
                setState(185);
                match(T__5);
                setState(186);
                ((ConnectionDeclContext) _localctx).fromComponent = name();
                setState(187);
                match(T__13);
                setState(188);
                ((ConnectionDeclContext) _localctx).fromPort = match(ID);
                setState(189);
                match(T__14);
                setState(190);
                ((ConnectionDeclContext) _localctx).toComponent = name();
                setState(191);
                match(T__13);
                setState(192);
                ((ConnectionDeclContext) _localctx).toPort = match(ID);
                setState(200);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__10) {
                    {
                        setState(193);
                        match(T__10);
                        setState(197);
                        _errHandler.sync(this);
                        _alt = getInterpreter().adaptivePredict(_input, 24, _ctx);
                        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                            if (_alt == 1) {
                                {
                                    {
                                        setState(194);
                                        flowc();
                                    }
                                }
                            }
                            setState(199);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 24, _ctx);
                        }
                    }
                }

                setState(204);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__1) {
                    {
                        setState(202);
                        match(T__1);
                        setState(203);
                        behaviour();
                    }
                }

                setState(213);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__12) {
                    {
                        setState(206);
                        match(T__12);
                        setState(210);
                        _errHandler.sync(this);
                        _alt = getInterpreter().adaptivePredict(_input, 27, _ctx);
                        while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER) {
                            if (_alt == 1) {
                                {
                                    {
                                        setState(207);
                                        property();
                                    }
                                }
                            }
                            setState(212);
                            _errHandler.sync(this);
                            _alt = getInterpreter().adaptivePredict(_input, 27, _ctx);
                        }
                    }
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

    public static class TypeAliasDeclContext extends ParserRuleContext {
        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }

        public TypeAliasDeclContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_typeAliasDecl;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterTypeAliasDecl(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitTypeAliasDecl(this);
        }
    }

    public final TypeAliasDeclContext typeAliasDecl() throws RecognitionException {
        TypeAliasDeclContext _localctx = new TypeAliasDeclContext(_ctx, getState());
        enterRule(_localctx, 12, RULE_typeAliasDecl);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(215);
                match(T__15);
                setState(216);
                match(ID);
                setState(217);
                match(T__16);
                setState(218);
                type();
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

    public static class EnumDeclContext extends ParserRuleContext {
        public Token n;
        public NameContext name;
        public List<NameContext> supers = new ArrayList<NameContext>();
        public Token ID;
        public List<Token> elements = new ArrayList<Token>();

        public List<TerminalNode> ID() {
            return getTokens(Antlr4AwasParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(Antlr4AwasParser.ID, i);
        }

        public List<NameContext> name() {
            return getRuleContexts(NameContext.class);
        }

        public NameContext name(int i) {
            return getRuleContext(NameContext.class, i);
        }

        public EnumDeclContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_enumDecl;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterEnumDecl(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitEnumDecl(this);
        }
    }

    public final EnumDeclContext enumDecl() throws RecognitionException {
        EnumDeclContext _localctx = new EnumDeclContext(_ctx, getState());
        enterRule(_localctx, 14, RULE_enumDecl);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(220);
                match(T__17);
                setState(221);
                ((EnumDeclContext) _localctx).n = match(ID);
                setState(231);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__18) {
                    {
                        setState(222);
                        match(T__18);
                        setState(223);
                        ((EnumDeclContext) _localctx).name = name();
                        ((EnumDeclContext) _localctx).supers.add(((EnumDeclContext) _localctx).name);
                        setState(228);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == T__7) {
                            {
                                {
                                    setState(224);
                                    match(T__7);
                                    setState(225);
                                    ((EnumDeclContext) _localctx).name = name();
                                    ((EnumDeclContext) _localctx).supers.add(((EnumDeclContext) _localctx).name);
                                }
                            }
                            setState(230);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                    }
                }

                setState(243);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__19) {
                    {
                        setState(233);
                        match(T__19);
                        setState(234);
                        ((EnumDeclContext) _localctx).ID = match(ID);
                        ((EnumDeclContext) _localctx).elements.add(((EnumDeclContext) _localctx).ID);
                        setState(239);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == T__7) {
                            {
                                {
                                    setState(235);
                                    match(T__7);
                                    setState(236);
                                    ((EnumDeclContext) _localctx).ID = match(ID);
                                    ((EnumDeclContext) _localctx).elements.add(((EnumDeclContext) _localctx).ID);
                                }
                            }
                            setState(241);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                        setState(242);
                        match(T__20);
                    }
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

    public static class LatticeDeclContext extends ParserRuleContext {
        public Token n;
        public NameContext name;
        public List<NameContext> supers = new ArrayList<NameContext>();

        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public List<NameContext> name() {
            return getRuleContexts(NameContext.class);
        }

        public NameContext name(int i) {
            return getRuleContext(NameContext.class, i);
        }

        public LatticeDeclContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_latticeDecl;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterLatticeDecl(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitLatticeDecl(this);
        }
    }

    public final LatticeDeclContext latticeDecl() throws RecognitionException {
        LatticeDeclContext _localctx = new LatticeDeclContext(_ctx, getState());
        enterRule(_localctx, 16, RULE_latticeDecl);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(245);
                match(T__21);
                setState(246);
                ((LatticeDeclContext) _localctx).n = match(ID);
                setState(256);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__18) {
                    {
                        setState(247);
                        match(T__18);
                        setState(248);
                        ((LatticeDeclContext) _localctx).name = name();
                        ((LatticeDeclContext) _localctx).supers.add(((LatticeDeclContext) _localctx).name);
                        setState(253);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        while (_la == T__7) {
                            {
                                {
                                    setState(249);
                                    match(T__7);
                                    setState(250);
                                    ((LatticeDeclContext) _localctx).name = name();
                                    ((LatticeDeclContext) _localctx).supers.add(((LatticeDeclContext) _localctx).name);
                                }
                            }
                            setState(255);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                        }
                    }
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

    public static class RecordDeclContext extends ParserRuleContext {
        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public List<FieldContext> field() {
            return getRuleContexts(FieldContext.class);
        }

        public FieldContext field(int i) {
            return getRuleContext(FieldContext.class, i);
        }

        public RecordDeclContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_recordDecl;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterRecordDecl(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitRecordDecl(this);
        }
    }

    public final RecordDeclContext recordDecl() throws RecognitionException {
        RecordDeclContext _localctx = new RecordDeclContext(_ctx, getState());
        enterRule(_localctx, 18, RULE_recordDecl);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(258);
                match(T__22);
                setState(259);
                match(ID);
                setState(261);
                _errHandler.sync(this);
                _la = _input.LA(1);
                do {
                    {
                        {
                            setState(260);
                            field();
                        }
                    }
                    setState(263);
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

    public static class FieldContext extends ParserRuleContext {
        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }

        public FieldContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_field;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterField(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitField(this);
        }
    }

    public final FieldContext field() throws RecognitionException {
        FieldContext _localctx = new FieldContext(_ctx, getState());
        enterRule(_localctx, 20, RULE_field);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(265);
                match(ID);
                setState(266);
                match(T__5);
                setState(267);
                type();
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

    public static class PortContext extends ParserRuleContext {
        public Token mod;

        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public PortContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_port;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterPort(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitPort(this);
        }
    }

    public final PortContext port() throws RecognitionException {
        PortContext _localctx = new PortContext(_ctx, getState());
        enterRule(_localctx, 22, RULE_port);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(269);
                ((PortContext) _localctx).mod = _input.LT(1);
                _la = _input.LA(1);
                if (!(_la == T__23 || _la == T__24)) {
                    ((PortContext) _localctx).mod = (Token) _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF) matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
                setState(270);
                match(ID);
                setState(273);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__5) {
                    {
                        setState(271);
                        match(T__5);
                        setState(272);
                        name();
                    }
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

    public static class PropagationContext extends ParserRuleContext {
        public Token id;
        public FaultContext fault;
        public List<FaultContext> errorT = new ArrayList<FaultContext>();

        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public List<FaultContext> fault() {
            return getRuleContexts(FaultContext.class);
        }

        public FaultContext fault(int i) {
            return getRuleContext(FaultContext.class, i);
        }

        public PropagationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_propagation;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterPropagation(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitPropagation(this);
        }
    }

    public final PropagationContext propagation() throws RecognitionException {
        PropagationContext _localctx = new PropagationContext(_ctx, getState());
        enterRule(_localctx, 24, RULE_propagation);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(275);
                ((PropagationContext) _localctx).id = match(ID);
                setState(276);
                match(T__16);
                setState(277);
                match(T__19);
                setState(278);
                ((PropagationContext) _localctx).fault = fault();
                ((PropagationContext) _localctx).errorT.add(((PropagationContext) _localctx).fault);
                setState(283);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == T__7) {
                    {
                        {
                            setState(279);
                            match(T__7);
                            setState(280);
                            ((PropagationContext) _localctx).fault = fault();
                            ((PropagationContext) _localctx).errorT.add(((PropagationContext) _localctx).fault);
                        }
                    }
                    setState(285);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(286);
                match(T__20);
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

    public static class FlowContext extends ParserRuleContext {
        public Token id;
        public Token from;
        public FaultContext fault;
        public List<FaultContext> fromE = new ArrayList<FaultContext>();
        public Token to;
        public List<FaultContext> toE = new ArrayList<FaultContext>();

        public List<TerminalNode> ID() {
            return getTokens(Antlr4AwasParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(Antlr4AwasParser.ID, i);
        }

        public List<FaultContext> fault() {
            return getRuleContexts(FaultContext.class);
        }

        public FaultContext fault(int i) {
            return getRuleContext(FaultContext.class, i);
        }

        public FlowContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_flow;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterFlow(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitFlow(this);
        }
    }

    public final FlowContext flow() throws RecognitionException {
        FlowContext _localctx = new FlowContext(_ctx, getState());
        enterRule(_localctx, 26, RULE_flow);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(288);
                ((FlowContext) _localctx).id = match(ID);
                setState(289);
                match(T__5);
                setState(305);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case ID: {
                        setState(290);
                        ((FlowContext) _localctx).from = match(ID);
                        setState(302);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if (_la == T__19) {
                            {
                                setState(291);
                                match(T__19);
                                setState(292);
                                ((FlowContext) _localctx).fault = fault();
                                ((FlowContext) _localctx).fromE.add(((FlowContext) _localctx).fault);
                                setState(297);
                                _errHandler.sync(this);
                                _la = _input.LA(1);
                                while (_la == T__7) {
                                    {
                                        {
                                            setState(293);
                                            match(T__7);
                                            setState(294);
                                            ((FlowContext) _localctx).fault = fault();
                                            ((FlowContext) _localctx).fromE.add(((FlowContext) _localctx).fault);
                                        }
                                    }
                                    setState(299);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                }
                                setState(300);
                                match(T__20);
                            }
                        }

                    }
                    break;
                    case T__25: {
                        setState(304);
                        match(T__25);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(307);
                match(T__14);
                setState(323);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case ID: {
                        setState(308);
                        ((FlowContext) _localctx).to = match(ID);
                        setState(320);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if (_la == T__19) {
                            {
                                setState(309);
                                match(T__19);
                                setState(310);
                                ((FlowContext) _localctx).fault = fault();
                                ((FlowContext) _localctx).toE.add(((FlowContext) _localctx).fault);
                                setState(315);
                                _errHandler.sync(this);
                                _la = _input.LA(1);
                                while (_la == T__7) {
                                    {
                                        {
                                            setState(311);
                                            match(T__7);
                                            setState(312);
                                            ((FlowContext) _localctx).fault = fault();
                                            ((FlowContext) _localctx).toE.add(((FlowContext) _localctx).fault);
                                        }
                                    }
                                    setState(317);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                }
                                setState(318);
                                match(T__20);
                            }
                        }

                    }
                    break;
                    case T__25: {
                        setState(322);
                        match(T__25);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
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

    public static class FlowcContext extends ParserRuleContext {
        public Token id;
        public FaultContext fault;
        public List<FaultContext> fromE = new ArrayList<FaultContext>();
        public List<FaultContext> toE = new ArrayList<FaultContext>();

        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public List<FaultContext> fault() {
            return getRuleContexts(FaultContext.class);
        }

        public FaultContext fault(int i) {
            return getRuleContext(FaultContext.class, i);
        }

        public FlowcContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_flowc;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterFlowc(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitFlowc(this);
        }
    }

    public final FlowcContext flowc() throws RecognitionException {
        FlowcContext _localctx = new FlowcContext(_ctx, getState());
        enterRule(_localctx, 28, RULE_flowc);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(325);
                ((FlowcContext) _localctx).id = match(ID);
                setState(326);
                match(T__5);
                setState(341);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case T__14:
                    case T__19: {
                        setState(338);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if (_la == T__19) {
                            {
                                setState(327);
                                match(T__19);
                                setState(328);
                                ((FlowcContext) _localctx).fault = fault();
                                ((FlowcContext) _localctx).fromE.add(((FlowcContext) _localctx).fault);
                                setState(333);
                                _errHandler.sync(this);
                                _la = _input.LA(1);
                                while (_la == T__7) {
                                    {
                                        {
                                            setState(329);
                                            match(T__7);
                                            setState(330);
                                            ((FlowcContext) _localctx).fault = fault();
                                            ((FlowcContext) _localctx).fromE.add(((FlowcContext) _localctx).fault);
                                        }
                                    }
                                    setState(335);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                }
                                setState(336);
                                match(T__20);
                            }
                        }

                    }
                    break;
                    case T__25: {
                        setState(340);
                        match(T__25);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(343);
                match(T__14);
                setState(358);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case EOF:
                    case T__1:
                    case T__12:
                    case T__19:
                    case ID: {
                        setState(355);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                        if (_la == T__19) {
                            {
                                setState(344);
                                match(T__19);
                                setState(345);
                                ((FlowcContext) _localctx).fault = fault();
                                ((FlowcContext) _localctx).toE.add(((FlowcContext) _localctx).fault);
                                setState(350);
                                _errHandler.sync(this);
                                _la = _input.LA(1);
                                while (_la == T__7) {
                                    {
                                        {
                                            setState(346);
                                            match(T__7);
                                            setState(347);
                                            ((FlowcContext) _localctx).fault = fault();
                                            ((FlowcContext) _localctx).toE.add(((FlowcContext) _localctx).fault);
                                        }
                                    }
                                    setState(352);
                                    _errHandler.sync(this);
                                    _la = _input.LA(1);
                                }
                                setState(353);
                                match(T__20);
                            }
                        }

                    }
                    break;
                    case T__25: {
                        setState(357);
                        match(T__25);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
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

    public static class PropertyContext extends ParserRuleContext {
        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }

        public InitContext init() {
            return getRuleContext(InitContext.class, 0);
        }

        public PropertyContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_property;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterProperty(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitProperty(this);
        }
    }

    public final PropertyContext property() throws RecognitionException {
        PropertyContext _localctx = new PropertyContext(_ctx, getState());
        enterRule(_localctx, 30, RULE_property);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(360);
                match(ID);
                setState(361);
                match(T__5);
                setState(362);
                type();
                setState(365);
                _errHandler.sync(this);
                _la = _input.LA(1);
                if (_la == T__16) {
                    {
                        setState(363);
                        match(T__16);
                        setState(364);
                        init();
                    }
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

    public static class ConstantDeclContext extends ParserRuleContext {
        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }

        public InitContext init() {
            return getRuleContext(InitContext.class, 0);
        }

        public ConstantDeclContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_constantDecl;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterConstantDecl(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitConstantDecl(this);
        }
    }

    public final ConstantDeclContext constantDecl() throws RecognitionException {
        ConstantDeclContext _localctx = new ConstantDeclContext(_ctx, getState());
        enterRule(_localctx, 32, RULE_constantDecl);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(367);
                name();
                setState(368);
                match(T__5);
                setState(369);
                type();
                setState(370);
                match(T__16);
                setState(371);
                init();
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

    public static class TransitionContext extends ParserRuleContext {
        public List<TransExprContext> transExpr() {
            return getRuleContexts(TransExprContext.class);
        }

        public TransExprContext transExpr(int i) {
            return getRuleContext(TransExprContext.class, i);
        }

        public TransitionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_transition;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterTransition(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitTransition(this);
        }
    }

    public final TransitionContext transition() throws RecognitionException {
        TransitionContext _localctx = new TransitionContext(_ctx, getState());
        enterRule(_localctx, 34, RULE_transition);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(374);
                _errHandler.sync(this);
                _alt = 1;
                do {
                    switch (_alt) {
                        case 1: {
                            {
                                setState(373);
                                transExpr();
                            }
                        }
                        break;
                        default:
                            throw new NoViableAltException(this);
                    }
                    setState(376);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 51, _ctx);
                } while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
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

    public static class TransExprContext extends ParserRuleContext {
        public IdGroupContext fromState;
        public TupleContext propCond;
        public IdGroupContext triggers;
        public IdGroupContext toState;

        public List<IdGroupContext> idGroup() {
            return getRuleContexts(IdGroupContext.class);
        }

        public IdGroupContext idGroup(int i) {
            return getRuleContext(IdGroupContext.class, i);
        }

        public TupleContext tuple() {
            return getRuleContext(TupleContext.class, 0);
        }

        public TransExprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_transExpr;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterTransExpr(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitTransExpr(this);
        }
    }

    public final TransExprContext transExpr() throws RecognitionException {
        TransExprContext _localctx = new TransExprContext(_ctx, getState());
        enterRule(_localctx, 36, RULE_transExpr);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(378);
                ((TransExprContext) _localctx).fromState = idGroup();
                {
                    setState(379);
                    match(T__26);
                    setState(382);
                    _errHandler.sync(this);
                    switch (getInterpreter().adaptivePredict(_input, 52, _ctx)) {
                        case 1: {
                            setState(380);
                            ((TransExprContext) _localctx).propCond = tuple();
                        }
                        break;
                        case 2: {
                            setState(381);
                            ((TransExprContext) _localctx).triggers = idGroup();
                        }
                        break;
                    }
                    setState(384);
                    match(T__27);
                }
                setState(386);
                ((TransExprContext) _localctx).toState = idGroup();
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

    public static class BehaviourContext extends ParserRuleContext {
        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        public BehaviourContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_behaviour;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterBehaviour(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitBehaviour(this);
        }
    }

    public final BehaviourContext behaviour() throws RecognitionException {
        BehaviourContext _localctx = new BehaviourContext(_ctx, getState());
        enterRule(_localctx, 38, RULE_behaviour);
        try {
            int _alt;
            enterOuterAlt(_localctx, 1);
            {
                setState(389);
                _errHandler.sync(this);
                _alt = 1;
                do {
                    switch (_alt) {
                        case 1: {
                            {
                                setState(388);
                                expression();
                            }
                        }
                        break;
                        default:
                            throw new NoViableAltException(this);
                    }
                    setState(391);
                    _errHandler.sync(this);
                    _alt = getInterpreter().adaptivePredict(_input, 53, _ctx);
                } while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
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

    public static class ExpressionContext extends ParserRuleContext {
        public TupleContext key;
        public IdGroupContext st;
        public TupleContext value;

        public List<TupleContext> tuple() {
            return getRuleContexts(TupleContext.class);
        }

        public TupleContext tuple(int i) {
            return getRuleContext(TupleContext.class, i);
        }

        public IdGroupContext idGroup() {
            return getRuleContext(IdGroupContext.class, 0);
        }

        public ExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_expression;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterExpression(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitExpression(this);
        }
    }

    public final ExpressionContext expression() throws RecognitionException {
        ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
        enterRule(_localctx, 40, RULE_expression);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(395);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case T__28:
                    case ID: {
                        setState(393);
                        ((ExpressionContext) _localctx).key = tuple();
                    }
                    break;
                    case T__25: {
                        setState(394);
                        match(T__25);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(402);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case T__14: {
                        setState(397);
                        match(T__14);
                    }
                    break;
                    case T__26: {
                        {
                            setState(398);
                            match(T__26);
                            setState(399);
                            ((ExpressionContext) _localctx).st = idGroup();
                            setState(400);
                            match(T__27);
                        }
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
                }
                setState(406);
                _errHandler.sync(this);
                switch (_input.LA(1)) {
                    case T__28:
                    case ID: {
                        setState(404);
                        ((ExpressionContext) _localctx).value = tuple();
                    }
                    break;
                    case T__25: {
                        setState(405);
                        match(T__25);
                    }
                    break;
                    default:
                        throw new NoViableAltException(this);
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

    public static class IdGroupContext extends ParserRuleContext {
        public Token ID;
        public List<Token> ids = new ArrayList<Token>();

        public List<TerminalNode> ID() {
            return getTokens(Antlr4AwasParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(Antlr4AwasParser.ID, i);
        }

        public IdGroupContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_idGroup;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterIdGroup(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitIdGroup(this);
        }
    }

    public final IdGroupContext idGroup() throws RecognitionException {
        IdGroupContext _localctx = new IdGroupContext(_ctx, getState());
        enterRule(_localctx, 42, RULE_idGroup);
        int _la;
        try {
            setState(418);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case ID:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(408);
                    ((IdGroupContext) _localctx).ID = match(ID);
                    ((IdGroupContext) _localctx).ids.add(((IdGroupContext) _localctx).ID);
                }
                break;
                case T__28:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(409);
                    match(T__28);
                    setState(410);
                    ((IdGroupContext) _localctx).ID = match(ID);
                    ((IdGroupContext) _localctx).ids.add(((IdGroupContext) _localctx).ID);
                    setState(413);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    do {
                        {
                            {
                                setState(411);
                                match(T__7);
                                setState(412);
                                ((IdGroupContext) _localctx).ID = match(ID);
                                ((IdGroupContext) _localctx).ids.add(((IdGroupContext) _localctx).ID);
                            }
                        }
                        setState(415);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                    } while (_la == T__7);
                    setState(417);
                    match(T__29);
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

    public static class TupleContext extends ParserRuleContext {
        public List<FaultPortContext> faultPort() {
            return getRuleContexts(FaultPortContext.class);
        }

        public FaultPortContext faultPort(int i) {
            return getRuleContext(FaultPortContext.class, i);
        }

        public TupleContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_tuple;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterTuple(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitTuple(this);
        }
    }

    public final TupleContext tuple() throws RecognitionException {
        TupleContext _localctx = new TupleContext(_ctx, getState());
        enterRule(_localctx, 44, RULE_tuple);
        int _la;
        try {
            setState(431);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case ID:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(420);
                    faultPort();
                }
                break;
                case T__28:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(421);
                    match(T__28);
                    setState(422);
                    faultPort();
                    setState(425);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    do {
                        {
                            {
                                setState(423);
                                match(T__7);
                                setState(424);
                                faultPort();
                            }
                        }
                        setState(427);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                    } while (_la == T__7);
                    setState(429);
                    match(T__29);
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

    public static class FaultPortContext extends ParserRuleContext {
        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public OneContext one() {
            return getRuleContext(OneContext.class, 0);
        }

        public FaultPortContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_faultPort;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterFaultPort(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitFaultPort(this);
        }
    }

    public final FaultPortContext faultPort() throws RecognitionException {
        FaultPortContext _localctx = new FaultPortContext(_ctx, getState());
        enterRule(_localctx, 46, RULE_faultPort);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(433);
                match(ID);
                setState(434);
                match(T__5);
                setState(435);
                one();
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

    public static class OneContext extends ParserRuleContext {
        public OneContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_one;
        }

        public OneContext() {
        }

        public void copyFrom(OneContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class FaultRefContext extends OneContext {
        public FaultContext fault() {
            return getRuleContext(FaultContext.class, 0);
        }

        public FaultRefContext(OneContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterFaultRef(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitFaultRef(this);
        }
    }

    public static class FaultSetContext extends OneContext {
        public List<FaultContext> fault() {
            return getRuleContexts(FaultContext.class);
        }

        public FaultContext fault(int i) {
            return getRuleContext(FaultContext.class, i);
        }

        public FaultSetContext(OneContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterFaultSet(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitFaultSet(this);
        }
    }

    public final OneContext one() throws RecognitionException {
        OneContext _localctx = new OneContext(_ctx, getState());
        enterRule(_localctx, 48, RULE_one);
        int _la;
        try {
            setState(448);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case ID:
                    _localctx = new FaultRefContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(437);
                    fault();
                }
                break;
                case T__19:
                    _localctx = new FaultSetContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(438);
                    match(T__19);
                    setState(439);
                    fault();
                    setState(442);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    do {
                        {
                            {
                                setState(440);
                                match(T__7);
                                setState(441);
                                fault();
                            }
                        }
                        setState(444);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                    } while (_la == T__7);
                    setState(446);
                    match(T__20);
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

    public static class FaultContext extends ParserRuleContext {
        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public FaultContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_fault;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterFault(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitFault(this);
        }
    }

    public final FaultContext fault() throws RecognitionException {
        FaultContext _localctx = new FaultContext(_ctx, getState());
        enterRule(_localctx, 50, RULE_fault);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(450);
                name();
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

    public static class TypeContext extends ParserRuleContext {
        public TypeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_type;
        }

        public TypeContext() {
        }

        public void copyFrom(TypeContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class BaseTypeContext extends TypeContext {
        public BasicTypeContext basicType() {
            return getRuleContext(BasicTypeContext.class, 0);
        }

        public BaseTypeContext(TypeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterBaseType(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitBaseType(this);
        }
    }

    public static class SetTypeContext extends TypeContext {
        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }

        public SetTypeContext(TypeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterSetType(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitSetType(this);
        }
    }

    public static class SeqTypeContext extends TypeContext {
        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }

        public SeqTypeContext(TypeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterSeqType(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitSeqType(this);
        }
    }

    public static class OptionTypeContext extends TypeContext {
        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }

        public OptionTypeContext(TypeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterOptionType(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitOptionType(this);
        }
    }

    public static class MapTypeContext extends TypeContext {
        public TypeContext key;
        public TypeContext value;

        public List<TypeContext> type() {
            return getRuleContexts(TypeContext.class);
        }

        public TypeContext type(int i) {
            return getRuleContext(TypeContext.class, i);
        }

        public MapTypeContext(TypeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterMapType(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitMapType(this);
        }
    }

    public final TypeContext type() throws RecognitionException {
        TypeContext _localctx = new TypeContext(_ctx, getState());
        enterRule(_localctx, 52, RULE_type);
        try {
            setState(475);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case T__36:
                case T__37:
                case T__38:
                case T__39:
                case T__40:
                case T__41:
                case ID:
                    _localctx = new BaseTypeContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(452);
                    basicType();
                }
                break;
                case T__30:
                    _localctx = new OptionTypeContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(453);
                    match(T__30);
                    setState(454);
                    match(T__31);
                    setState(455);
                    type();
                    setState(456);
                    match(T__32);
                }
                break;
                case T__33:
                    _localctx = new SetTypeContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                {
                    setState(458);
                    match(T__33);
                    setState(459);
                    match(T__31);
                    setState(460);
                    type();
                    setState(461);
                    match(T__32);
                }
                break;
                case T__34:
                    _localctx = new SeqTypeContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                {
                    setState(463);
                    match(T__34);
                    setState(464);
                    match(T__31);
                    setState(465);
                    type();
                    setState(466);
                    match(T__32);
                }
                break;
                case T__35:
                    _localctx = new MapTypeContext(_localctx);
                    enterOuterAlt(_localctx, 5);
                {
                    setState(468);
                    match(T__35);
                    setState(469);
                    match(T__31);
                    setState(470);
                    ((MapTypeContext) _localctx).key = type();
                    setState(471);
                    match(T__7);
                    setState(472);
                    ((MapTypeContext) _localctx).value = type();
                    setState(473);
                    match(T__32);
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

    public static class BasicTypeContext extends ParserRuleContext {
        public BasicTypeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_basicType;
        }

        public BasicTypeContext() {
        }

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
            return getRuleContext(IntConstantContext.class, i);
        }

        public IntegerTypeContext(BasicTypeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterIntegerType(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitIntegerType(this);
        }
    }

    public static class ComponentTypeContext extends BasicTypeContext {
        public ComponentTypeContext(BasicTypeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterComponentType(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitComponentType(this);
        }
    }

    public static class StringTypeContext extends BasicTypeContext {
        public StringTypeContext(BasicTypeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterStringType(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitStringType(this);
        }
    }

    public static class NamedTypeContext extends BasicTypeContext {
        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public NamedTypeContext(BasicTypeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterNamedType(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitNamedType(this);
        }
    }

    public static class BooleanTypeContext extends BasicTypeContext {
        public BooleanTypeContext(BasicTypeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterBooleanType(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitBooleanType(this);
        }
    }

    public static class RealTypeContext extends BasicTypeContext {
        public RealTypeContext(BasicTypeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterRealType(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitRealType(this);
        }
    }

    public static class PortTypeContext extends BasicTypeContext {
        public PortTypeContext(BasicTypeContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterPortType(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitPortType(this);
        }
    }

    public final BasicTypeContext basicType() throws RecognitionException {
        BasicTypeContext _localctx = new BasicTypeContext(_ctx, getState());
        enterRule(_localctx, 54, RULE_basicType);
        int _la;
        try {
            setState(492);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case T__36:
                    _localctx = new BooleanTypeContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(477);
                    match(T__36);
                }
                break;
                case T__37:
                    _localctx = new IntegerTypeContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(478);
                    match(T__37);
                    setState(485);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == T__28) {
                        {
                            setState(479);
                            match(T__28);
                            setState(480);
                            ((IntegerTypeContext) _localctx).lo = intConstant();
                            setState(481);
                            match(T__7);
                            setState(482);
                            ((IntegerTypeContext) _localctx).hi = intConstant();
                            setState(483);
                            match(T__29);
                        }
                    }

                }
                break;
                case T__38:
                    _localctx = new RealTypeContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                {
                    setState(487);
                    match(T__38);
                }
                break;
                case T__39:
                    _localctx = new StringTypeContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                {
                    setState(488);
                    match(T__39);
                }
                break;
                case T__40:
                    _localctx = new ComponentTypeContext(_localctx);
                    enterOuterAlt(_localctx, 5);
                {
                    setState(489);
                    match(T__40);
                }
                break;
                case T__41:
                    _localctx = new PortTypeContext(_localctx);
                    enterOuterAlt(_localctx, 6);
                {
                    setState(490);
                    match(T__41);
                }
                break;
                case ID:
                    _localctx = new NamedTypeContext(_localctx);
                    enterOuterAlt(_localctx, 7);
                {
                    setState(491);
                    name();
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

    public static class IntConstantContext extends ParserRuleContext {
        public IntConstantContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_intConstant;
        }

        public IntConstantContext() {
        }

        public void copyFrom(IntConstantContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class LitIntConstantContext extends IntConstantContext {
        public TerminalNode INTEGER() {
            return getToken(Antlr4AwasParser.INTEGER, 0);
        }

        public LitIntConstantContext(IntConstantContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterLitIntConstant(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitLitIntConstant(this);
        }
    }

    public static class ArbitraryIntConstantContext extends IntConstantContext {
        public ArbitraryIntConstantContext(IntConstantContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterArbitraryIntConstant(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitArbitraryIntConstant(this);
        }
    }

    public static class NamedIntConstantContext extends IntConstantContext {
        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public NamedIntConstantContext(IntConstantContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterNamedIntConstant(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitNamedIntConstant(this);
        }
    }

    public final IntConstantContext intConstant() throws RecognitionException {
        IntConstantContext _localctx = new IntConstantContext(_ctx, getState());
        enterRule(_localctx, 56, RULE_intConstant);
        try {
            setState(497);
            _errHandler.sync(this);
            switch (_input.LA(1)) {
                case INTEGER:
                    _localctx = new LitIntConstantContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(494);
                    match(INTEGER);
                }
                break;
                case ID:
                    _localctx = new NamedIntConstantContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(495);
                    name();
                }
                break;
                case T__42:
                    _localctx = new ArbitraryIntConstantContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                {
                    setState(496);
                    match(T__42);
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

    public static class InitContext extends ParserRuleContext {
        public InitContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_init;
        }

        public InitContext() {
        }

        public void copyFrom(InitContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class IntegerContext extends InitContext {
        public TerminalNode INTEGER() {
            return getToken(Antlr4AwasParser.INTEGER, 0);
        }

        public IntegerContext(InitContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterInteger(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitInteger(this);
        }
    }

    public static class NameRefContext extends InitContext {
        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public NameRefContext(InitContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterNameRef(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitNameRef(this);
        }
    }

    public static class SomeContext extends InitContext {
        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }

        public InitContext init() {
            return getRuleContext(InitContext.class, 0);
        }

        public SomeContext(InitContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterSome(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitSome(this);
        }
    }

    public static class SetContext extends InitContext {
        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }

        public List<InitContext> init() {
            return getRuleContexts(InitContext.class);
        }

        public InitContext init(int i) {
            return getRuleContext(InitContext.class, i);
        }

        public SetContext(InitContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterSet(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitSet(this);
        }
    }

    public static class RealContext extends InitContext {
        public TerminalNode REAL() {
            return getToken(Antlr4AwasParser.REAL, 0);
        }

        public RealContext(InitContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterReal(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitReal(this);
        }
    }

    public static class TrueContext extends InitContext {
        public TrueContext(InitContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterTrue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitTrue(this);
        }
    }

    public static class RecordContext extends InitContext {
        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public List<TerminalNode> ID() {
            return getTokens(Antlr4AwasParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(Antlr4AwasParser.ID, i);
        }

        public List<InitContext> init() {
            return getRuleContexts(InitContext.class);
        }

        public InitContext init(int i) {
            return getRuleContext(InitContext.class, i);
        }

        public RecordContext(InitContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterRecord(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitRecord(this);
        }
    }

    public static class FalseContext extends InitContext {
        public FalseContext(InitContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterFalse(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitFalse(this);
        }
    }

    public static class StringContext extends InitContext {
        public TerminalNode STRING() {
            return getToken(Antlr4AwasParser.STRING, 0);
        }

        public StringContext(InitContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterString(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitString(this);
        }
    }

    public static class NoneContext extends InitContext {
        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }

        public NoneContext(InitContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterNone(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitNone(this);
        }
    }

    public static class MapContext extends InitContext {
        public TypeContext key;
        public TypeContext value;

        public List<TypeContext> type() {
            return getRuleContexts(TypeContext.class);
        }

        public TypeContext type(int i) {
            return getRuleContext(TypeContext.class, i);
        }

        public List<MapEntryContext> mapEntry() {
            return getRuleContexts(MapEntryContext.class);
        }

        public MapEntryContext mapEntry(int i) {
            return getRuleContext(MapEntryContext.class, i);
        }

        public MapContext(InitContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterMap(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitMap(this);
        }
    }

    public static class SeqContext extends InitContext {
        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }

        public List<InitContext> init() {
            return getRuleContexts(InitContext.class);
        }

        public InitContext init(int i) {
            return getRuleContext(InitContext.class, i);
        }

        public SeqContext(InitContext ctx) {
            copyFrom(ctx);
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterSeq(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitSeq(this);
        }
    }

    public final InitContext init() throws RecognitionException {
        InitContext _localctx = new InitContext(_ctx, getState());
        enterRule(_localctx, 58, RULE_init);
        int _la;
        try {
            setState(591);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 75, _ctx)) {
                case 1:
                    _localctx = new TrueContext(_localctx);
                    enterOuterAlt(_localctx, 1);
                {
                    setState(499);
                    match(T__43);
                }
                break;
                case 2:
                    _localctx = new FalseContext(_localctx);
                    enterOuterAlt(_localctx, 2);
                {
                    setState(500);
                    match(T__44);
                }
                break;
                case 3:
                    _localctx = new IntegerContext(_localctx);
                    enterOuterAlt(_localctx, 3);
                {
                    setState(501);
                    match(INTEGER);
                }
                break;
                case 4:
                    _localctx = new RealContext(_localctx);
                    enterOuterAlt(_localctx, 4);
                {
                    setState(502);
                    match(REAL);
                }
                break;
                case 5:
                    _localctx = new StringContext(_localctx);
                    enterOuterAlt(_localctx, 5);
                {
                    setState(503);
                    match(STRING);
                }
                break;
                case 6:
                    _localctx = new RecordContext(_localctx);
                    enterOuterAlt(_localctx, 6);
                {
                    setState(504);
                    name();
                    setState(505);
                    match(T__28);
                    setState(506);
                    match(ID);
                    setState(507);
                    match(T__16);
                    setState(508);
                    init();
                    setState(515);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    while (_la == T__7) {
                        {
                            {
                                setState(509);
                                match(T__7);
                                setState(510);
                                match(ID);
                                setState(511);
                                match(T__16);
                                setState(512);
                                init();
                            }
                        }
                        setState(517);
                        _errHandler.sync(this);
                        _la = _input.LA(1);
                    }
                    setState(518);
                    match(T__29);
                }
                break;
                case 7:
                    _localctx = new NameRefContext(_localctx);
                    enterOuterAlt(_localctx, 7);
                {
                    setState(520);
                    name();
                    setState(523);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if (_la == T__13) {
                        {
                            setState(521);
                            match(T__13);
                            setState(522);
                            match(ID);
                        }
                    }

                }
                break;
                case 8:
                    _localctx = new NoneContext(_localctx);
                    enterOuterAlt(_localctx, 8);
                {
                    setState(525);
                    match(T__45);
                    setState(526);
                    match(T__31);
                    setState(527);
                    type();
                    setState(528);
                    match(T__32);
                }
                break;
                case 9:
                    _localctx = new SomeContext(_localctx);
                    enterOuterAlt(_localctx, 9);
                {
                    setState(530);
                    match(T__46);
                    setState(531);
                    match(T__31);
                    setState(532);
                    type();
                    setState(533);
                    match(T__32);
                    setState(534);
                    match(T__28);
                    setState(535);
                    init();
                    setState(536);
                    match(T__29);
                }
                break;
                case 10:
                    _localctx = new SetContext(_localctx);
                    enterOuterAlt(_localctx, 10);
                {
                    setState(538);
                    match(T__33);
                    setState(539);
                    match(T__31);
                    setState(540);
                    type();
                    setState(541);
                    match(T__32);
                    setState(542);
                    match(T__28);
                    setState(551);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << INTEGER) | (1L << REAL) | (1L << STRING) | (1L << ID))) != 0)) {
                        {
                            setState(543);
                            init();
                            setState(548);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                            while (_la == T__7) {
                                {
                                    {
                                        setState(544);
                                        match(T__7);
                                        setState(545);
                                        init();
                                    }
                                }
                                setState(550);
                                _errHandler.sync(this);
                                _la = _input.LA(1);
                            }
                        }
                    }

                    setState(553);
                    match(T__29);
                }
                break;
                case 11:
                    _localctx = new SeqContext(_localctx);
                    enterOuterAlt(_localctx, 11);
                {
                    setState(555);
                    match(T__34);
                    setState(556);
                    match(T__31);
                    setState(557);
                    type();
                    setState(558);
                    match(T__32);
                    setState(559);
                    match(T__28);
                    setState(568);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << INTEGER) | (1L << REAL) | (1L << STRING) | (1L << ID))) != 0)) {
                        {
                            setState(560);
                            init();
                            setState(565);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                            while (_la == T__7) {
                                {
                                    {
                                        setState(561);
                                        match(T__7);
                                        setState(562);
                                        init();
                                    }
                                }
                                setState(567);
                                _errHandler.sync(this);
                                _la = _input.LA(1);
                            }
                        }
                    }

                    setState(570);
                    match(T__29);
                }
                break;
                case 12:
                    _localctx = new MapContext(_localctx);
                    enterOuterAlt(_localctx, 12);
                {
                    setState(572);
                    match(T__35);
                    setState(573);
                    match(T__31);
                    setState(574);
                    ((MapContext) _localctx).key = type();
                    setState(575);
                    match(T__7);
                    setState(576);
                    ((MapContext) _localctx).value = type();
                    setState(577);
                    match(T__32);
                    setState(578);
                    match(T__28);
                    setState(587);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                    if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << INTEGER) | (1L << REAL) | (1L << STRING) | (1L << ID))) != 0)) {
                        {
                            setState(579);
                            mapEntry();
                            setState(584);
                            _errHandler.sync(this);
                            _la = _input.LA(1);
                            while (_la == T__7) {
                                {
                                    {
                                        setState(580);
                                        match(T__7);
                                        setState(581);
                                        mapEntry();
                                    }
                                }
                                setState(586);
                                _errHandler.sync(this);
                                _la = _input.LA(1);
                            }
                        }
                    }

                    setState(589);
                    match(T__29);
                }
                break;
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

    public static class MapEntryContext extends ParserRuleContext {
        public InitContext key;
        public InitContext value;

        public List<InitContext> init() {
            return getRuleContexts(InitContext.class);
        }

        public InitContext init(int i) {
            return getRuleContext(InitContext.class, i);
        }

        public MapEntryContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_mapEntry;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterMapEntry(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitMapEntry(this);
        }
    }

    public final MapEntryContext mapEntry() throws RecognitionException {
        MapEntryContext _localctx = new MapEntryContext(_ctx, getState());
        enterRule(_localctx, 60, RULE_mapEntry);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(593);
                ((MapEntryContext) _localctx).key = init();
                setState(594);
                match(T__14);
                setState(595);
                ((MapEntryContext) _localctx).value = init();
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

    public static class NameContext extends ParserRuleContext {
        public List<TerminalNode> ID() {
            return getTokens(Antlr4AwasParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(Antlr4AwasParser.ID, i);
        }

        public NameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_name;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterName(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitName(this);
        }
    }

    public final NameContext name() throws RecognitionException {
        NameContext _localctx = new NameContext(_ctx, getState());
        enterRule(_localctx, 62, RULE_name);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(597);
                match(ID);
                setState(602);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == T__47) {
                    {
                        {
                            setState(598);
                            match(T__47);
                            setState(599);
                            match(ID);
                        }
                    }
                    setState(604);
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

    public static class StatesContext extends ParserRuleContext {
        public Token ID;
        public List<Token> state = new ArrayList<Token>();

        public List<TerminalNode> ID() {
            return getTokens(Antlr4AwasParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(Antlr4AwasParser.ID, i);
        }

        public StatesContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_states;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterStates(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitStates(this);
        }
    }

    public final StatesContext states() throws RecognitionException {
        StatesContext _localctx = new StatesContext(_ctx, getState());
        enterRule(_localctx, 64, RULE_states);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(605);
                match(T__48);
                setState(606);
                match(T__16);
                setState(607);
                match(T__31);
                setState(608);
                ((StatesContext) _localctx).ID = match(ID);
                ((StatesContext) _localctx).state.add(((StatesContext) _localctx).ID);
                setState(613);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == T__7) {
                    {
                        {
                            setState(609);
                            match(T__7);
                            setState(610);
                            ((StatesContext) _localctx).ID = match(ID);
                            ((StatesContext) _localctx).state.add(((StatesContext) _localctx).ID);
                        }
                    }
                    setState(615);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(616);
                match(T__32);
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

    public static class EventsContext extends ParserRuleContext {
        public Token ID;
        public List<Token> event = new ArrayList<Token>();

        public List<TerminalNode> ID() {
            return getTokens(Antlr4AwasParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(Antlr4AwasParser.ID, i);
        }

        public EventsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        @Override
        public int getRuleIndex() {
            return RULE_events;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).enterEvents(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Antlr4AwasListener) ((Antlr4AwasListener) listener).exitEvents(this);
        }
    }

    public final EventsContext events() throws RecognitionException {
        EventsContext _localctx = new EventsContext(_ctx, getState());
        enterRule(_localctx, 66, RULE_events);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(618);
                match(T__49);
                setState(619);
                match(T__16);
                setState(620);
                match(T__19);
                setState(621);
                ((EventsContext) _localctx).ID = match(ID);
                ((EventsContext) _localctx).event.add(((EventsContext) _localctx).ID);
                setState(626);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while (_la == T__7) {
                    {
                        {
                            setState(622);
                            match(T__7);
                            setState(623);
                            ((EventsContext) _localctx).ID = match(ID);
                            ((EventsContext) _localctx).event.add(((EventsContext) _localctx).ID);
                        }
                    }
                    setState(628);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(629);
                match(T__20);
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

    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3<\u027a\4\2\t\2\4" +
                    "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t" +
                    "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22" +
                    "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31" +
                    "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!" +
                    "\t!\4\"\t\"\4#\t#\3\2\3\2\3\2\3\3\3\3\7\3L\n\3\f\3\16\3O\13\3\5\3Q\n\3" +
                    "\3\3\3\3\7\3U\n\3\f\3\16\3X\13\3\5\3Z\n\3\3\3\3\3\7\3^\n\3\f\3\16\3a\13" +
                    "\3\5\3c\n\3\3\3\3\3\7\3g\n\3\f\3\16\3j\13\3\5\3l\n\3\3\3\3\3\7\3p\n\3" +
                    "\f\3\16\3s\13\3\5\3u\n\3\3\4\3\4\3\4\3\4\5\4{\n\4\3\5\3\5\3\5\3\5\5\5" +
                    "\u0081\n\5\3\6\3\6\3\6\3\6\3\6\7\6\u0088\n\6\f\6\16\6\u008b\13\6\5\6\u008d" +
                    "\n\6\3\6\3\6\7\6\u0091\n\6\f\6\16\6\u0094\13\6\5\6\u0096\n\6\3\6\3\6\7" +
                    "\6\u009a\n\6\f\6\16\6\u009d\13\6\5\6\u009f\n\6\3\6\3\6\7\6\u00a3\n\6\f" +
                    "\6\16\6\u00a6\13\6\5\6\u00a8\n\6\3\6\3\6\5\6\u00ac\n\6\3\6\3\6\5\6\u00b0" +
                    "\n\6\3\6\3\6\7\6\u00b4\n\6\f\6\16\6\u00b7\13\6\5\6\u00b9\n\6\3\7\3\7\3" +
                    "\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7\u00c6\n\7\f\7\16\7\u00c9\13\7\5" +
                    "\7\u00cb\n\7\3\7\3\7\5\7\u00cf\n\7\3\7\3\7\7\7\u00d3\n\7\f\7\16\7\u00d6" +
                    "\13\7\5\7\u00d8\n\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\7\t\u00e5" +
                    "\n\t\f\t\16\t\u00e8\13\t\5\t\u00ea\n\t\3\t\3\t\3\t\3\t\7\t\u00f0\n\t\f" +
                    "\t\16\t\u00f3\13\t\3\t\5\t\u00f6\n\t\3\n\3\n\3\n\3\n\3\n\3\n\7\n\u00fe" +
                    "\n\n\f\n\16\n\u0101\13\n\5\n\u0103\n\n\3\13\3\13\3\13\6\13\u0108\n\13" +
                    "\r\13\16\13\u0109\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\5\r\u0114\n\r\3\16\3" +
                    "\16\3\16\3\16\3\16\3\16\7\16\u011c\n\16\f\16\16\16\u011f\13\16\3\16\3" +
                    "\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\7\17\u012a\n\17\f\17\16\17\u012d" +
                    "\13\17\3\17\3\17\5\17\u0131\n\17\3\17\5\17\u0134\n\17\3\17\3\17\3\17\3" +
                    "\17\3\17\3\17\7\17\u013c\n\17\f\17\16\17\u013f\13\17\3\17\3\17\5\17\u0143" +
                    "\n\17\3\17\5\17\u0146\n\17\3\20\3\20\3\20\3\20\3\20\3\20\7\20\u014e\n" +
                    "\20\f\20\16\20\u0151\13\20\3\20\3\20\5\20\u0155\n\20\3\20\5\20\u0158\n" +
                    "\20\3\20\3\20\3\20\3\20\3\20\7\20\u015f\n\20\f\20\16\20\u0162\13\20\3" +
                    "\20\3\20\5\20\u0166\n\20\3\20\5\20\u0169\n\20\3\21\3\21\3\21\3\21\3\21" +
                    "\5\21\u0170\n\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23\6\23\u0179\n\23\r" +
                    "\23\16\23\u017a\3\24\3\24\3\24\3\24\5\24\u0181\n\24\3\24\3\24\3\24\3\24" +
                    "\3\25\6\25\u0188\n\25\r\25\16\25\u0189\3\26\3\26\5\26\u018e\n\26\3\26" +
                    "\3\26\3\26\3\26\3\26\5\26\u0195\n\26\3\26\3\26\5\26\u0199\n\26\3\27\3" +
                    "\27\3\27\3\27\3\27\6\27\u01a0\n\27\r\27\16\27\u01a1\3\27\5\27\u01a5\n" +
                    "\27\3\30\3\30\3\30\3\30\3\30\6\30\u01ac\n\30\r\30\16\30\u01ad\3\30\3\30" +
                    "\5\30\u01b2\n\30\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\6\32\u01bd" +
                    "\n\32\r\32\16\32\u01be\3\32\3\32\5\32\u01c3\n\32\3\33\3\33\3\34\3\34\3" +
                    "\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3" +
                    "\34\3\34\3\34\3\34\3\34\3\34\3\34\5\34\u01de\n\34\3\35\3\35\3\35\3\35" +
                    "\3\35\3\35\3\35\3\35\5\35\u01e8\n\35\3\35\3\35\3\35\3\35\3\35\5\35\u01ef" +
                    "\n\35\3\36\3\36\3\36\5\36\u01f4\n\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37" +
                    "\3\37\3\37\3\37\3\37\3\37\3\37\3\37\7\37\u0204\n\37\f\37\16\37\u0207\13" +
                    "\37\3\37\3\37\3\37\3\37\3\37\5\37\u020e\n\37\3\37\3\37\3\37\3\37\3\37" +
                    "\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37" +
                    "\3\37\3\37\7\37\u0225\n\37\f\37\16\37\u0228\13\37\5\37\u022a\n\37\3\37" +
                    "\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\7\37\u0236\n\37\f\37\16" +
                    "\37\u0239\13\37\5\37\u023b\n\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37" +
                    "\3\37\3\37\3\37\3\37\7\37\u0249\n\37\f\37\16\37\u024c\13\37\5\37\u024e" +
                    "\n\37\3\37\3\37\5\37\u0252\n\37\3 \3 \3 \3 \3!\3!\3!\7!\u025b\n!\f!\16" +
                    "!\u025e\13!\3\"\3\"\3\"\3\"\3\"\3\"\7\"\u0266\n\"\f\"\16\"\u0269\13\"" +
                    "\3\"\3\"\3#\3#\3#\3#\3#\3#\7#\u0273\n#\f#\16#\u0276\13#\3#\3#\3#\2\2$" +
                    "\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BD\2" +
                    "\3\3\2\32\33\2\u02bb\2F\3\2\2\2\4P\3\2\2\2\6z\3\2\2\2\b|\3\2\2\2\n\u0082" +
                    "\3\2\2\2\f\u00ba\3\2\2\2\16\u00d9\3\2\2\2\20\u00de\3\2\2\2\22\u00f7\3" +
                    "\2\2\2\24\u0104\3\2\2\2\26\u010b\3\2\2\2\30\u010f\3\2\2\2\32\u0115\3\2" +
                    "\2\2\34\u0122\3\2\2\2\36\u0147\3\2\2\2 \u016a\3\2\2\2\"\u0171\3\2\2\2" +
                    "$\u0178\3\2\2\2&\u017c\3\2\2\2(\u0187\3\2\2\2*\u018d\3\2\2\2,\u01a4\3" +
                    "\2\2\2.\u01b1\3\2\2\2\60\u01b3\3\2\2\2\62\u01c2\3\2\2\2\64\u01c4\3\2\2" +
                    "\2\66\u01dd\3\2\2\28\u01ee\3\2\2\2:\u01f3\3\2\2\2<\u0251\3\2\2\2>\u0253" +
                    "\3\2\2\2@\u0257\3\2\2\2B\u025f\3\2\2\2D\u026c\3\2\2\2FG\5\4\3\2GH\7\2" +
                    "\2\3H\3\3\2\2\2IM\7\3\2\2JL\5\6\4\2KJ\3\2\2\2LO\3\2\2\2MK\3\2\2\2MN\3" +
                    "\2\2\2NQ\3\2\2\2OM\3\2\2\2PI\3\2\2\2PQ\3\2\2\2QY\3\2\2\2RV\7\4\2\2SU\5" +
                    "\b\5\2TS\3\2\2\2UX\3\2\2\2VT\3\2\2\2VW\3\2\2\2WZ\3\2\2\2XV\3\2\2\2YR\3" +
                    "\2\2\2YZ\3\2\2\2Zb\3\2\2\2[_\7\5\2\2\\^\5\"\22\2]\\\3\2\2\2^a\3\2\2\2" +
                    "_]\3\2\2\2_`\3\2\2\2`c\3\2\2\2a_\3\2\2\2b[\3\2\2\2bc\3\2\2\2ck\3\2\2\2" +
                    "dh\7\6\2\2eg\5\n\6\2fe\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2\2\2il\3\2\2\2" +
                    "jh\3\2\2\2kd\3\2\2\2kl\3\2\2\2lt\3\2\2\2mq\7\7\2\2np\5\f\7\2on\3\2\2\2" +
                    "ps\3\2\2\2qo\3\2\2\2qr\3\2\2\2ru\3\2\2\2sq\3\2\2\2tm\3\2\2\2tu\3\2\2\2" +
                    "u\5\3\2\2\2v{\5\16\b\2w{\5\20\t\2x{\5\22\n\2y{\5\24\13\2zv\3\2\2\2zw\3" +
                    "\2\2\2zx\3\2\2\2zy\3\2\2\2{\7\3\2\2\2|}\78\2\2}~\7\b\2\2~\u0080\5B\"\2" +
                    "\177\u0081\5D#\2\u0080\177\3\2\2\2\u0080\u0081\3\2\2\2\u0081\t\3\2\2\2" +
                    "\u0082\u008c\78\2\2\u0083\u0084\7\t\2\2\u0084\u0089\5@!\2\u0085\u0086" +
                    "\7\n\2\2\u0086\u0088\5@!\2\u0087\u0085\3\2\2\2\u0088\u008b\3\2\2\2\u0089" +
                    "\u0087\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u008d\3\2\2\2\u008b\u0089\3\2" +
                    "\2\2\u008c\u0083\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u0095\3\2\2\2\u008e" +
                    "\u0092\7\13\2\2\u008f\u0091\5\30\r\2\u0090\u008f\3\2\2\2\u0091\u0094\3" +
                    "\2\2\2\u0092\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093\u0096\3\2\2\2\u0094" +
                    "\u0092\3\2\2\2\u0095\u008e\3\2\2\2\u0095\u0096\3\2\2\2\u0096\u009e\3\2" +
                    "\2\2\u0097\u009b\7\f\2\2\u0098\u009a\5\32\16\2\u0099\u0098\3\2\2\2\u009a" +
                    "\u009d\3\2\2\2\u009b\u0099\3\2\2\2\u009b\u009c\3\2\2\2\u009c\u009f\3\2" +
                    "\2\2\u009d\u009b\3\2\2\2\u009e\u0097\3\2\2\2\u009e\u009f\3\2\2\2\u009f" +
                    "\u00a7\3\2\2\2\u00a0\u00a4\7\r\2\2\u00a1\u00a3\5\34\17\2\u00a2\u00a1\3" +
                    "\2\2\2\u00a3\u00a6\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5" +
                    "\u00a8\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a7\u00a0\3\2\2\2\u00a7\u00a8\3\2" +
                    "\2\2\u00a8\u00ab\3\2\2\2\u00a9\u00aa\7\16\2\2\u00aa\u00ac\5$\23\2\u00ab" +
                    "\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ae\7\4" +
                    "\2\2\u00ae\u00b0\5(\25\2\u00af\u00ad\3\2\2\2\u00af\u00b0\3\2\2\2\u00b0" +
                    "\u00b8\3\2\2\2\u00b1\u00b5\7\17\2\2\u00b2\u00b4\5 \21\2\u00b3\u00b2\3" +
                    "\2\2\2\u00b4\u00b7\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6" +
                    "\u00b9\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b8\u00b1\3\2\2\2\u00b8\u00b9\3\2" +
                    "\2\2\u00b9\13\3\2\2\2\u00ba\u00bb\78\2\2\u00bb\u00bc\7\b\2\2\u00bc\u00bd" +
                    "\5@!\2\u00bd\u00be\7\20\2\2\u00be\u00bf\78\2\2\u00bf\u00c0\7\21\2\2\u00c0" +
                    "\u00c1\5@!\2\u00c1\u00c2\7\20\2\2\u00c2\u00ca\78\2\2\u00c3\u00c7\7\r\2" +
                    "\2\u00c4\u00c6\5\36\20\2\u00c5\u00c4\3\2\2\2\u00c6\u00c9\3\2\2\2\u00c7" +
                    "\u00c5\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00cb\3\2\2\2\u00c9\u00c7\3\2" +
                    "\2\2\u00ca\u00c3\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00ce\3\2\2\2\u00cc" +
                    "\u00cd\7\4\2\2\u00cd\u00cf\5(\25\2\u00ce\u00cc\3\2\2\2\u00ce\u00cf\3\2" +
                    "\2\2\u00cf\u00d7\3\2\2\2\u00d0\u00d4\7\17\2\2\u00d1\u00d3\5 \21\2\u00d2" +
                    "\u00d1\3\2\2\2\u00d3\u00d6\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d4\u00d5\3\2" +
                    "\2\2\u00d5\u00d8\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d7\u00d0\3\2\2\2\u00d7" +
                    "\u00d8\3\2\2\2\u00d8\r\3\2\2\2\u00d9\u00da\7\22\2\2\u00da\u00db\78\2\2" +
                    "\u00db\u00dc\7\23\2\2\u00dc\u00dd\5\66\34\2\u00dd\17\3\2\2\2\u00de\u00df" +
                    "\7\24\2\2\u00df\u00e9\78\2\2\u00e0\u00e1\7\25\2\2\u00e1\u00e6\5@!\2\u00e2" +
                    "\u00e3\7\n\2\2\u00e3\u00e5\5@!\2\u00e4\u00e2\3\2\2\2\u00e5\u00e8\3\2\2" +
                    "\2\u00e6\u00e4\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7\u00ea\3\2\2\2\u00e8\u00e6" +
                    "\3\2\2\2\u00e9\u00e0\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea\u00f5\3\2\2\2\u00eb" +
                    "\u00ec\7\26\2\2\u00ec\u00f1\78\2\2\u00ed\u00ee\7\n\2\2\u00ee\u00f0\78" +
                    "\2\2\u00ef\u00ed\3\2\2\2\u00f0\u00f3\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f1" +
                    "\u00f2\3\2\2\2\u00f2\u00f4\3\2\2\2\u00f3\u00f1\3\2\2\2\u00f4\u00f6\7\27" +
                    "\2\2\u00f5\u00eb\3\2\2\2\u00f5\u00f6\3\2\2\2\u00f6\21\3\2\2\2\u00f7\u00f8" +
                    "\7\30\2\2\u00f8\u0102\78\2\2\u00f9\u00fa\7\25\2\2\u00fa\u00ff\5@!\2\u00fb" +
                    "\u00fc\7\n\2\2\u00fc\u00fe\5@!\2\u00fd\u00fb\3\2\2\2\u00fe\u0101\3\2\2" +
                    "\2\u00ff\u00fd\3\2\2\2\u00ff\u0100\3\2\2\2\u0100\u0103\3\2\2\2\u0101\u00ff" +
                    "\3\2\2\2\u0102\u00f9\3\2\2\2\u0102\u0103\3\2\2\2\u0103\23\3\2\2\2\u0104" +
                    "\u0105\7\31\2\2\u0105\u0107\78\2\2\u0106\u0108\5\26\f\2\u0107\u0106\3" +
                    "\2\2\2\u0108\u0109\3\2\2\2\u0109\u0107\3\2\2\2\u0109\u010a\3\2\2\2\u010a" +
                    "\25\3\2\2\2\u010b\u010c\78\2\2\u010c\u010d\7\b\2\2\u010d\u010e\5\66\34" +
                    "\2\u010e\27\3\2\2\2\u010f\u0110\t\2\2\2\u0110\u0113\78\2\2\u0111\u0112" +
                    "\7\b\2\2\u0112\u0114\5@!\2\u0113\u0111\3\2\2\2\u0113\u0114\3\2\2\2\u0114" +
                    "\31\3\2\2\2\u0115\u0116\78\2\2\u0116\u0117\7\23\2\2\u0117\u0118\7\26\2" +
                    "\2\u0118\u011d\5\64\33\2\u0119\u011a\7\n\2\2\u011a\u011c\5\64\33\2\u011b" +
                    "\u0119\3\2\2\2\u011c\u011f\3\2\2\2\u011d\u011b\3\2\2\2\u011d\u011e\3\2" +
                    "\2\2\u011e\u0120\3\2\2\2\u011f\u011d\3\2\2\2\u0120\u0121\7\27\2\2\u0121" +
                    "\33\3\2\2\2\u0122\u0123\78\2\2\u0123\u0133\7\b\2\2\u0124\u0130\78\2\2" +
                    "\u0125\u0126\7\26\2\2\u0126\u012b\5\64\33\2\u0127\u0128\7\n\2\2\u0128" +
                    "\u012a\5\64\33\2\u0129\u0127\3\2\2\2\u012a\u012d\3\2\2\2\u012b\u0129\3" +
                    "\2\2\2\u012b\u012c\3\2\2\2\u012c\u012e\3\2\2\2\u012d\u012b\3\2\2\2\u012e" +
                    "\u012f\7\27\2\2\u012f\u0131\3\2\2\2\u0130\u0125\3\2\2\2\u0130\u0131\3" +
                    "\2\2\2\u0131\u0134\3\2\2\2\u0132\u0134\7\34\2\2\u0133\u0124\3\2\2\2\u0133" +
                    "\u0132\3\2\2\2\u0134\u0135\3\2\2\2\u0135\u0145\7\21\2\2\u0136\u0142\7" +
                    "8\2\2\u0137\u0138\7\26\2\2\u0138\u013d\5\64\33\2\u0139\u013a\7\n\2\2\u013a" +
                    "\u013c\5\64\33\2\u013b\u0139\3\2\2\2\u013c\u013f\3\2\2\2\u013d\u013b\3" +
                    "\2\2\2\u013d\u013e\3\2\2\2\u013e\u0140\3\2\2\2\u013f\u013d\3\2\2\2\u0140" +
                    "\u0141\7\27\2\2\u0141\u0143\3\2\2\2\u0142\u0137\3\2\2\2\u0142\u0143\3" +
                    "\2\2\2\u0143\u0146\3\2\2\2\u0144\u0146\7\34\2\2\u0145\u0136\3\2\2\2\u0145" +
                    "\u0144\3\2\2\2\u0146\35\3\2\2\2\u0147\u0148\78\2\2\u0148\u0157\7\b\2\2" +
                    "\u0149\u014a\7\26\2\2\u014a\u014f\5\64\33\2\u014b\u014c\7\n\2\2\u014c" +
                    "\u014e\5\64\33\2\u014d\u014b\3\2\2\2\u014e\u0151\3\2\2\2\u014f\u014d\3" +
                    "\2\2\2\u014f\u0150\3\2\2\2\u0150\u0152\3\2\2\2\u0151\u014f\3\2\2\2\u0152" +
                    "\u0153\7\27\2\2\u0153\u0155\3\2\2\2\u0154\u0149\3\2\2\2\u0154\u0155\3" +
                    "\2\2\2\u0155\u0158\3\2\2\2\u0156\u0158\7\34\2\2\u0157\u0154\3\2\2\2\u0157" +
                    "\u0156\3\2\2\2\u0158\u0159\3\2\2\2\u0159\u0168\7\21\2\2\u015a\u015b\7" +
                    "\26\2\2\u015b\u0160\5\64\33\2\u015c\u015d\7\n\2\2\u015d\u015f\5\64\33" +
                    "\2\u015e\u015c\3\2\2\2\u015f\u0162\3\2\2\2\u0160\u015e\3\2\2\2\u0160\u0161" +
                    "\3\2\2\2\u0161\u0163\3\2\2\2\u0162\u0160\3\2\2\2\u0163\u0164\7\27\2\2" +
                    "\u0164\u0166\3\2\2\2\u0165\u015a\3\2\2\2\u0165\u0166\3\2\2\2\u0166\u0169" +
                    "\3\2\2\2\u0167\u0169\7\34\2\2\u0168\u0165\3\2\2\2\u0168\u0167\3\2\2\2" +
                    "\u0169\37\3\2\2\2\u016a\u016b\78\2\2\u016b\u016c\7\b\2\2\u016c\u016f\5" +
                    "\66\34\2\u016d\u016e\7\23\2\2\u016e\u0170\5<\37\2\u016f\u016d\3\2\2\2" +
                    "\u016f\u0170\3\2\2\2\u0170!\3\2\2\2\u0171\u0172\5@!\2\u0172\u0173\7\b" +
                    "\2\2\u0173\u0174\5\66\34\2\u0174\u0175\7\23\2\2\u0175\u0176\5<\37\2\u0176" +
                    "#\3\2\2\2\u0177\u0179\5&\24\2\u0178\u0177\3\2\2\2\u0179\u017a\3\2\2\2" +
                    "\u017a\u0178\3\2\2\2\u017a\u017b\3\2\2\2\u017b%\3\2\2\2\u017c\u017d\5" +
                    ",\27\2\u017d\u0180\7\35\2\2\u017e\u0181\5.\30\2\u017f\u0181\5,\27\2\u0180" +
                    "\u017e\3\2\2\2\u0180\u017f\3\2\2\2\u0181\u0182\3\2\2\2\u0182\u0183\7\36" +
                    "\2\2\u0183\u0184\3\2\2\2\u0184\u0185\5,\27\2\u0185\'\3\2\2\2\u0186\u0188" +
                    "\5*\26\2\u0187\u0186\3\2\2\2\u0188\u0189\3\2\2\2\u0189\u0187\3\2\2\2\u0189" +
                    "\u018a\3\2\2\2\u018a)\3\2\2\2\u018b\u018e\5.\30\2\u018c\u018e\7\34\2\2" +
                    "\u018d\u018b\3\2\2\2\u018d\u018c\3\2\2\2\u018e\u0194\3\2\2\2\u018f\u0195" +
                    "\7\21\2\2\u0190\u0191\7\35\2\2\u0191\u0192\5,\27\2\u0192\u0193\7\36\2" +
                    "\2\u0193\u0195\3\2\2\2\u0194\u018f\3\2\2\2\u0194\u0190\3\2\2\2\u0195\u0198" +
                    "\3\2\2\2\u0196\u0199\5.\30\2\u0197\u0199\7\34\2\2\u0198\u0196\3\2\2\2" +
                    "\u0198\u0197\3\2\2\2\u0199+\3\2\2\2\u019a\u01a5\78\2\2\u019b\u019c\7\37" +
                    "\2\2\u019c\u019f\78\2\2\u019d\u019e\7\n\2\2\u019e\u01a0\78\2\2\u019f\u019d" +
                    "\3\2\2\2\u01a0\u01a1\3\2\2\2\u01a1\u019f\3\2\2\2\u01a1\u01a2\3\2\2\2\u01a2" +
                    "\u01a3\3\2\2\2\u01a3\u01a5\7 \2\2\u01a4\u019a\3\2\2\2\u01a4\u019b\3\2" +
                    "\2\2\u01a5-\3\2\2\2\u01a6\u01b2\5\60\31\2\u01a7\u01a8\7\37\2\2\u01a8\u01ab" +
                    "\5\60\31\2\u01a9\u01aa\7\n\2\2\u01aa\u01ac\5\60\31\2\u01ab\u01a9\3\2\2" +
                    "\2\u01ac\u01ad\3\2\2\2\u01ad\u01ab\3\2\2\2\u01ad\u01ae\3\2\2\2\u01ae\u01af" +
                    "\3\2\2\2\u01af\u01b0\7 \2\2\u01b0\u01b2\3\2\2\2\u01b1\u01a6\3\2\2\2\u01b1" +
                    "\u01a7\3\2\2\2\u01b2/\3\2\2\2\u01b3\u01b4\78\2\2\u01b4\u01b5\7\b\2\2\u01b5" +
                    "\u01b6\5\62\32\2\u01b6\61\3\2\2\2\u01b7\u01c3\5\64\33\2\u01b8\u01b9\7" +
                    "\26\2\2\u01b9\u01bc\5\64\33\2\u01ba\u01bb\7\n\2\2\u01bb\u01bd\5\64\33" +
                    "\2\u01bc\u01ba\3\2\2\2\u01bd\u01be\3\2\2\2\u01be\u01bc\3\2\2\2\u01be\u01bf" +
                    "\3\2\2\2\u01bf\u01c0\3\2\2\2\u01c0\u01c1\7\27\2\2\u01c1\u01c3\3\2\2\2" +
                    "\u01c2\u01b7\3\2\2\2\u01c2\u01b8\3\2\2\2\u01c3\63\3\2\2\2\u01c4\u01c5" +
                    "\5@!\2\u01c5\65\3\2\2\2\u01c6\u01de\58\35\2\u01c7\u01c8\7!\2\2\u01c8\u01c9" +
                    "\7\"\2\2\u01c9\u01ca\5\66\34\2\u01ca\u01cb\7#\2\2\u01cb\u01de\3\2\2\2" +
                    "\u01cc\u01cd\7$\2\2\u01cd\u01ce\7\"\2\2\u01ce\u01cf\5\66\34\2\u01cf\u01d0" +
                    "\7#\2\2\u01d0\u01de\3\2\2\2\u01d1\u01d2\7%\2\2\u01d2\u01d3\7\"\2\2\u01d3" +
                    "\u01d4\5\66\34\2\u01d4\u01d5\7#\2\2\u01d5\u01de\3\2\2\2\u01d6\u01d7\7" +
                    "&\2\2\u01d7\u01d8\7\"\2\2\u01d8\u01d9\5\66\34\2\u01d9\u01da\7\n\2\2\u01da" +
                    "\u01db\5\66\34\2\u01db\u01dc\7#\2\2\u01dc\u01de\3\2\2\2\u01dd\u01c6\3" +
                    "\2\2\2\u01dd\u01c7\3\2\2\2\u01dd\u01cc\3\2\2\2\u01dd\u01d1\3\2\2\2\u01dd" +
                    "\u01d6\3\2\2\2\u01de\67\3\2\2\2\u01df\u01ef\7\'\2\2\u01e0\u01e7\7(\2\2" +
                    "\u01e1\u01e2\7\37\2\2\u01e2\u01e3\5:\36\2\u01e3\u01e4\7\n\2\2\u01e4\u01e5" +
                    "\5:\36\2\u01e5\u01e6\7 \2\2\u01e6\u01e8\3\2\2\2\u01e7\u01e1\3\2\2\2\u01e7" +
                    "\u01e8\3\2\2\2\u01e8\u01ef\3\2\2\2\u01e9\u01ef\7)\2\2\u01ea\u01ef\7*\2" +
                    "\2\u01eb\u01ef\7+\2\2\u01ec\u01ef\7,\2\2\u01ed\u01ef\5@!\2\u01ee\u01df" +
                    "\3\2\2\2\u01ee\u01e0\3\2\2\2\u01ee\u01e9\3\2\2\2\u01ee\u01ea\3\2\2\2\u01ee" +
                    "\u01eb\3\2\2\2\u01ee\u01ec\3\2\2\2\u01ee\u01ed\3\2\2\2\u01ef9\3\2\2\2" +
                    "\u01f0\u01f4\7\65\2\2\u01f1\u01f4\5@!\2\u01f2\u01f4\7-\2\2\u01f3\u01f0" +
                    "\3\2\2\2\u01f3\u01f1\3\2\2\2\u01f3\u01f2\3\2\2\2\u01f4;\3\2\2\2\u01f5" +
                    "\u0252\7.\2\2\u01f6\u0252\7/\2\2\u01f7\u0252\7\65\2\2\u01f8\u0252\7\66" +
                    "\2\2\u01f9\u0252\7\67\2\2\u01fa\u01fb\5@!\2\u01fb\u01fc\7\37\2\2\u01fc" +
                    "\u01fd\78\2\2\u01fd\u01fe\7\23\2\2\u01fe\u0205\5<\37\2\u01ff\u0200\7\n" +
                    "\2\2\u0200\u0201\78\2\2\u0201\u0202\7\23\2\2\u0202\u0204\5<\37\2\u0203" +
                    "\u01ff\3\2\2\2\u0204\u0207\3\2\2\2\u0205\u0203\3\2\2\2\u0205\u0206\3\2" +
                    "\2\2\u0206\u0208\3\2\2\2\u0207\u0205\3\2\2\2\u0208\u0209\7 \2\2\u0209" +
                    "\u0252\3\2\2\2\u020a\u020d\5@!\2\u020b\u020c\7\20\2\2\u020c\u020e\78\2" +
                    "\2\u020d\u020b\3\2\2\2\u020d\u020e\3\2\2\2\u020e\u0252\3\2\2\2\u020f\u0210" +
                    "\7\60\2\2\u0210\u0211\7\"\2\2\u0211\u0212\5\66\34\2\u0212\u0213\7#\2\2" +
                    "\u0213\u0252\3\2\2\2\u0214\u0215\7\61\2\2\u0215\u0216\7\"\2\2\u0216\u0217" +
                    "\5\66\34\2\u0217\u0218\7#\2\2\u0218\u0219\7\37\2\2\u0219\u021a\5<\37\2" +
                    "\u021a\u021b\7 \2\2\u021b\u0252\3\2\2\2\u021c\u021d\7$\2\2\u021d\u021e" +
                    "\7\"\2\2\u021e\u021f\5\66\34\2\u021f\u0220\7#\2\2\u0220\u0229\7\37\2\2" +
                    "\u0221\u0226\5<\37\2\u0222\u0223\7\n\2\2\u0223\u0225\5<\37\2\u0224\u0222" +
                    "\3\2\2\2\u0225\u0228\3\2\2\2\u0226\u0224\3\2\2\2\u0226\u0227\3\2\2\2\u0227" +
                    "\u022a\3\2\2\2\u0228\u0226\3\2\2\2\u0229\u0221\3\2\2\2\u0229\u022a\3\2" +
                    "\2\2\u022a\u022b\3\2\2\2\u022b\u022c\7 \2\2\u022c\u0252\3\2\2\2\u022d" +
                    "\u022e\7%\2\2\u022e\u022f\7\"\2\2\u022f\u0230\5\66\34\2\u0230\u0231\7" +
                    "#\2\2\u0231\u023a\7\37\2\2\u0232\u0237\5<\37\2\u0233\u0234\7\n\2\2\u0234" +
                    "\u0236\5<\37\2\u0235\u0233\3\2\2\2\u0236\u0239\3\2\2\2\u0237\u0235\3\2" +
                    "\2\2\u0237\u0238\3\2\2\2\u0238\u023b\3\2\2\2\u0239\u0237\3\2\2\2\u023a" +
                    "\u0232\3\2\2\2\u023a\u023b\3\2\2\2\u023b\u023c\3\2\2\2\u023c\u023d\7 " +
                    "\2\2\u023d\u0252\3\2\2\2\u023e\u023f\7&\2\2\u023f\u0240\7\"\2\2\u0240" +
                    "\u0241\5\66\34\2\u0241\u0242\7\n\2\2\u0242\u0243\5\66\34\2\u0243\u0244" +
                    "\7#\2\2\u0244\u024d\7\37\2\2\u0245\u024a\5> \2\u0246\u0247\7\n\2\2\u0247" +
                    "\u0249\5> \2\u0248\u0246\3\2\2\2\u0249\u024c\3\2\2\2\u024a\u0248\3\2\2" +
                    "\2\u024a\u024b\3\2\2\2\u024b\u024e\3\2\2\2\u024c\u024a\3\2\2\2\u024d\u0245" +
                    "\3\2\2\2\u024d\u024e\3\2\2\2\u024e\u024f\3\2\2\2\u024f\u0250\7 \2\2\u0250" +
                    "\u0252\3\2\2\2\u0251\u01f5\3\2\2\2\u0251\u01f6\3\2\2\2\u0251\u01f7\3\2" +
                    "\2\2\u0251\u01f8\3\2\2\2\u0251\u01f9\3\2\2\2\u0251\u01fa\3\2\2\2\u0251" +
                    "\u020a\3\2\2\2\u0251\u020f\3\2\2\2\u0251\u0214\3\2\2\2\u0251\u021c\3\2" +
                    "\2\2\u0251\u022d\3\2\2\2\u0251\u023e\3\2\2\2\u0252=\3\2\2\2\u0253\u0254" +
                    "\5<\37\2\u0254\u0255\7\21\2\2\u0255\u0256\5<\37\2\u0256?\3\2\2\2\u0257" +
                    "\u025c\78\2\2\u0258\u0259\7\62\2\2\u0259\u025b\78\2\2\u025a\u0258\3\2" +
                    "\2\2\u025b\u025e\3\2\2\2\u025c\u025a\3\2\2\2\u025c\u025d\3\2\2\2\u025d" +
                    "A\3\2\2\2\u025e\u025c\3\2\2\2\u025f\u0260\7\63\2\2\u0260\u0261\7\23\2" +
                    "\2\u0261\u0262\7\"\2\2\u0262\u0267\78\2\2\u0263\u0264\7\n\2\2\u0264\u0266" +
                    "\78\2\2\u0265\u0263\3\2\2\2\u0266\u0269\3\2\2\2\u0267\u0265\3\2\2\2\u0267" +
                    "\u0268\3\2\2\2\u0268\u026a\3\2\2\2\u0269\u0267\3\2\2\2\u026a\u026b\7#" +
                    "\2\2\u026bC\3\2\2\2\u026c\u026d\7\64\2\2\u026d\u026e\7\23\2\2\u026e\u026f" +
                    "\7\26\2\2\u026f\u0274\78\2\2\u0270\u0271\7\n\2\2\u0271\u0273\78\2\2\u0272" +
                    "\u0270\3\2\2\2\u0273\u0276\3\2\2\2\u0274\u0272\3\2\2\2\u0274\u0275\3\2" +
                    "\2\2\u0275\u0277\3\2\2\2\u0276\u0274\3\2\2\2\u0277\u0278\7\27\2\2\u0278" +
                    "E\3\2\2\2QMPVY_bhkqtz\u0080\u0089\u008c\u0092\u0095\u009b\u009e\u00a4" +
                    "\u00a7\u00ab\u00af\u00b5\u00b8\u00c7\u00ca\u00ce\u00d4\u00d7\u00e6\u00e9" +
                    "\u00f1\u00f5\u00ff\u0102\u0109\u0113\u011d\u012b\u0130\u0133\u013d\u0142" +
                    "\u0145\u014f\u0154\u0157\u0160\u0165\u0168\u016f\u017a\u0180\u0189\u018d" +
                    "\u0194\u0198\u01a1\u01a4\u01ad\u01b1\u01be\u01c2\u01dd\u01e7\u01ee\u01f3" +
                    "\u0205\u020d\u0226\u0229\u0237\u023a\u024a\u024d\u0251\u025c\u0267\u0274";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}