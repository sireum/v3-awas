// Generated from /workspace-v3/sireum-v3/awas/jvm/src/main/resources/org/sireum/awas/parser/Antlr4Awas.g4 by ANTLR 4.7
package org.sireum.awas.parser;

// @formatter:off

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Antlr4AwasParser extends Parser {
	public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
            T__9 = 10, T__10 = 11, T__11 = 12, T__12 = 13, T__13 = 14, T__14 = 15, T__15 = 16, T__16 = 17,
            T__17 = 18, T__18 = 19, T__19 = 20, T__20 = 21, T__21 = 22, T__22 = 23, T__23 = 24,
            T__24 = 25, T__25 = 26, T__26 = 27, T__27 = 28, T__28 = 29, T__29 = 30, T__30 = 31,
            T__31 = 32, T__32 = 33, T__33 = 34, T__34 = 35, T__35 = 36, T__36 = 37, T__37 = 38,
            T__38 = 39, T__39 = 40, T__40 = 41, T__41 = 42, T__42 = 43, T__43 = 44, T__44 = 45,
            T__45 = 46, T__46 = 47, T__47 = 48, T__48 = 49, T__49 = 50, INTEGER = 51, REAL = 52,
            STRING=53, ID=54, WS=55, COMMENT=56, LINE_COMMENT=57, ERROR_CHAR=58;
	public static final int
            RULE_modelFile = 0, RULE_model = 1, RULE_typeDecl = 2, RULE_behaviorDecl = 3,
            RULE_componentDecl = 4, RULE_connectionDecl = 5, RULE_typeAliasDecl = 6,
            RULE_enumDecl = 7, RULE_latticeDecl = 8, RULE_recordDecl = 9, RULE_field = 10,
            RULE_port = 11, RULE_propagation = 12, RULE_flow = 13, RULE_property = 14,
            RULE_constantDecl = 15, RULE_transition = 16, RULE_transExpr = 17, RULE_behaviour = 18,
            RULE_expression = 19, RULE_idGroup = 20, RULE_tuple = 21, RULE_faultPort = 22,
            RULE_one = 23, RULE_fault = 24, RULE_type = 25, RULE_basicType = 26, RULE_intConstant = 27,
            RULE_init = 28, RULE_mapEntry = 29, RULE_name = 30, RULE_states = 31,
            RULE_events = 32;
	public static final String[] ruleNames = {
            "modelFile", "model", "typeDecl", "behaviorDecl", "componentDecl", "connectionDecl",
            "typeAliasDecl", "enumDecl", "latticeDecl", "recordDecl", "field", "port",
            "propagation", "flow", "property", "constantDecl", "transition", "transExpr",
            "behaviour", "expression", "idGroup", "tuple", "faultPort", "one", "fault",
            "type", "basicType", "intConstant", "init", "mapEntry", "name", "states",
            "events"
	};
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3<\u0266\4\2\t\2\4" +
                    "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t" +
                    "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22" +
                    "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31" +
                    "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!" +
                    "\t!\4\"\t\"\3\2\3\2\3\2\3\3\3\3\7\3J\n\3\f\3\16\3M\13\3\5\3O\n\3\3\3\3" +
                    "\3\7\3S\n\3\f\3\16\3V\13\3\5\3X\n\3\3\3\3\3\7\3\\\n\3\f\3\16\3_\13\3\5" +
                    "\3a\n\3\3\3\3\3\7\3e\n\3\f\3\16\3h\13\3\5\3j\n\3\3\3\3\3\7\3n\n\3\f\3" +
                    "\16\3q\13\3\5\3s\n\3\3\4\3\4\3\4\3\4\5\4y\n\4\3\5\3\5\3\5\3\5\5\5\177" +
                    "\n\5\3\6\3\6\3\6\3\6\3\6\7\6\u0086\n\6\f\6\16\6\u0089\13\6\5\6\u008b\n" +
                    "\6\3\6\3\6\7\6\u008f\n\6\f\6\16\6\u0092\13\6\5\6\u0094\n\6\3\6\3\6\7\6" +
                    "\u0098\n\6\f\6\16\6\u009b\13\6\5\6\u009d\n\6\3\6\3\6\7\6\u00a1\n\6\f\6" +
                    "\16\6\u00a4\13\6\5\6\u00a6\n\6\3\6\3\6\5\6\u00aa\n\6\3\6\3\6\5\6\u00ae" +
                    "\n\6\3\6\3\6\7\6\u00b2\n\6\f\6\16\6\u00b5\13\6\5\6\u00b7\n\6\3\7\3\7\3" +
                    "\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7\u00c2\n\7\f\7\16\7\u00c5\13\7\3\7\3\7\5" +
                    "\7\u00c9\n\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7\u00d3\n\7\f\7\16\7\u00d6" +
                    "\13\7\3\7\3\7\5\7\u00da\n\7\3\7\3\7\5\7\u00de\n\7\3\7\3\7\7\7\u00e2\n" +
                    "\7\f\7\16\7\u00e5\13\7\5\7\u00e7\n\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3" +
                    "\t\3\t\3\t\7\t\u00f4\n\t\f\t\16\t\u00f7\13\t\5\t\u00f9\n\t\3\t\3\t\3\t" +
                    "\3\t\7\t\u00ff\n\t\f\t\16\t\u0102\13\t\3\t\5\t\u0105\n\t\3\n\3\n\3\n\3" +
                    "\n\3\n\3\n\7\n\u010d\n\n\f\n\16\n\u0110\13\n\5\n\u0112\n\n\3\13\3\13\3" +
                    "\13\6\13\u0117\n\13\r\13\16\13\u0118\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\5" +
                    "\r\u0123\n\r\3\16\3\16\3\16\3\16\3\16\3\16\7\16\u012b\n\16\f\16\16\16" +
                    "\u012e\13\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\7\17\u0139\n" +
                    "\17\f\17\16\17\u013c\13\17\3\17\3\17\5\17\u0140\n\17\3\17\5\17\u0143\n" +
                    "\17\3\17\3\17\3\17\3\17\3\17\3\17\7\17\u014b\n\17\f\17\16\17\u014e\13" +
                    "\17\3\17\3\17\5\17\u0152\n\17\3\17\5\17\u0155\n\17\3\20\3\20\3\20\3\20" +
                    "\3\20\5\20\u015c\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\22\6\22\u0165\n" +
                    "\22\r\22\16\22\u0166\3\23\3\23\3\23\3\23\5\23\u016d\n\23\3\23\3\23\3\23" +
                    "\3\23\3\24\6\24\u0174\n\24\r\24\16\24\u0175\3\25\3\25\5\25\u017a\n\25" +
                    "\3\25\3\25\3\25\3\25\3\25\5\25\u0181\n\25\3\25\3\25\5\25\u0185\n\25\3" +
                    "\26\3\26\3\26\3\26\3\26\6\26\u018c\n\26\r\26\16\26\u018d\3\26\5\26\u0191" +
                    "\n\26\3\27\3\27\3\27\3\27\3\27\6\27\u0198\n\27\r\27\16\27\u0199\3\27\3" +
                    "\27\5\27\u019e\n\27\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\6\31" +
                    "\u01a9\n\31\r\31\16\31\u01aa\3\31\3\31\5\31\u01af\n\31\3\32\3\32\3\33" +
                    "\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33" +
                    "\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u01ca\n\33\3\34\3\34\3\34" +
                    "\3\34\3\34\3\34\3\34\3\34\5\34\u01d4\n\34\3\34\3\34\3\34\3\34\3\34\5\34" +
                    "\u01db\n\34\3\35\3\35\3\35\5\35\u01e0\n\35\3\36\3\36\3\36\3\36\3\36\3" +
                    "\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\7\36\u01f0\n\36\f\36\16\36" +
                    "\u01f3\13\36\3\36\3\36\3\36\3\36\3\36\5\36\u01fa\n\36\3\36\3\36\3\36\3" +
                    "\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3" +
                    "\36\3\36\3\36\3\36\7\36\u0211\n\36\f\36\16\36\u0214\13\36\5\36\u0216\n" +
                    "\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\7\36\u0222\n\36" +
                    "\f\36\16\36\u0225\13\36\5\36\u0227\n\36\3\36\3\36\3\36\3\36\3\36\3\36" +
                    "\3\36\3\36\3\36\3\36\3\36\3\36\7\36\u0235\n\36\f\36\16\36\u0238\13\36" +
                    "\5\36\u023a\n\36\3\36\3\36\5\36\u023e\n\36\3\37\3\37\3\37\3\37\3 \3 \3" +
                    " \7 \u0247\n \f \16 \u024a\13 \3!\3!\3!\3!\3!\3!\7!\u0252\n!\f!\16!\u0255" +
                    "\13!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\7\"\u025f\n\"\f\"\16\"\u0262\13\"\3" +
                    "\"\3\"\3\"\2\2#\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62" +
                    "\64\668:<>@B\2\3\3\2\32\33\2\u02a4\2D\3\2\2\2\4N\3\2\2\2\6x\3\2\2\2\b" +
                    "z\3\2\2\2\n\u0080\3\2\2\2\f\u00b8\3\2\2\2\16\u00e8\3\2\2\2\20\u00ed\3" +
                    "\2\2\2\22\u0106\3\2\2\2\24\u0113\3\2\2\2\26\u011a\3\2\2\2\30\u011e\3\2" +
                    "\2\2\32\u0124\3\2\2\2\34\u0131\3\2\2\2\36\u0156\3\2\2\2 \u015d\3\2\2\2" +
                    "\"\u0164\3\2\2\2$\u0168\3\2\2\2&\u0173\3\2\2\2(\u0179\3\2\2\2*\u0190\3" +
                    "\2\2\2,\u019d\3\2\2\2.\u019f\3\2\2\2\60\u01ae\3\2\2\2\62\u01b0\3\2\2\2" +
                    "\64\u01c9\3\2\2\2\66\u01da\3\2\2\28\u01df\3\2\2\2:\u023d\3\2\2\2<\u023f" +
                    "\3\2\2\2>\u0243\3\2\2\2@\u024b\3\2\2\2B\u0258\3\2\2\2DE\5\4\3\2EF\7\2" +
                    "\2\3F\3\3\2\2\2GK\7\3\2\2HJ\5\6\4\2IH\3\2\2\2JM\3\2\2\2KI\3\2\2\2KL\3" +
                    "\2\2\2LO\3\2\2\2MK\3\2\2\2NG\3\2\2\2NO\3\2\2\2OW\3\2\2\2PT\7\4\2\2QS\5" +
                    "\b\5\2RQ\3\2\2\2SV\3\2\2\2TR\3\2\2\2TU\3\2\2\2UX\3\2\2\2VT\3\2\2\2WP\3" +
                    "\2\2\2WX\3\2\2\2X`\3\2\2\2Y]\7\5\2\2Z\\\5 \21\2[Z\3\2\2\2\\_\3\2\2\2]" +
                    "[\3\2\2\2]^\3\2\2\2^a\3\2\2\2_]\3\2\2\2`Y\3\2\2\2`a\3\2\2\2ai\3\2\2\2" +
                    "bf\7\6\2\2ce\5\n\6\2dc\3\2\2\2eh\3\2\2\2fd\3\2\2\2fg\3\2\2\2gj\3\2\2\2" +
                    "hf\3\2\2\2ib\3\2\2\2ij\3\2\2\2jr\3\2\2\2ko\7\7\2\2ln\5\f\7\2ml\3\2\2\2" +
                    "nq\3\2\2\2om\3\2\2\2op\3\2\2\2ps\3\2\2\2qo\3\2\2\2rk\3\2\2\2rs\3\2\2\2" +
                    "s\5\3\2\2\2ty\5\16\b\2uy\5\20\t\2vy\5\22\n\2wy\5\24\13\2xt\3\2\2\2xu\3" +
                    "\2\2\2xv\3\2\2\2xw\3\2\2\2y\7\3\2\2\2z{\78\2\2{|\7\b\2\2|~\5@!\2}\177" +
                    "\5B\"\2~}\3\2\2\2~\177\3\2\2\2\177\t\3\2\2\2\u0080\u008a\78\2\2\u0081" +
                    "\u0082\7\t\2\2\u0082\u0087\5> \2\u0083\u0084\7\n\2\2\u0084\u0086\5> \2" +
                    "\u0085\u0083\3\2\2\2\u0086\u0089\3\2\2\2\u0087\u0085\3\2\2\2\u0087\u0088" +
                    "\3\2\2\2\u0088\u008b\3\2\2\2\u0089\u0087\3\2\2\2\u008a\u0081\3\2\2\2\u008a" +
                    "\u008b\3\2\2\2\u008b\u0093\3\2\2\2\u008c\u0090\7\13\2\2\u008d\u008f\5" +
                    "\30\r\2\u008e\u008d\3\2\2\2\u008f\u0092\3\2\2\2\u0090\u008e\3\2\2\2\u0090" +
                    "\u0091\3\2\2\2\u0091\u0094\3\2\2\2\u0092\u0090\3\2\2\2\u0093\u008c\3\2" +
                    "\2\2\u0093\u0094\3\2\2\2\u0094\u009c\3\2\2\2\u0095\u0099\7\f\2\2\u0096" +
                    "\u0098\5\32\16\2\u0097\u0096\3\2\2\2\u0098\u009b\3\2\2\2\u0099\u0097\3" +
                    "\2\2\2\u0099\u009a\3\2\2\2\u009a\u009d\3\2\2\2\u009b\u0099\3\2\2\2\u009c" +
                    "\u0095\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u00a5\3\2\2\2\u009e\u00a2\7\r" +
                    "\2\2\u009f\u00a1\5\34\17\2\u00a0\u009f\3\2\2\2\u00a1\u00a4\3\2\2\2\u00a2" +
                    "\u00a0\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a6\3\2\2\2\u00a4\u00a2\3\2" +
                    "\2\2\u00a5\u009e\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7" +
                    "\u00a8\7\16\2\2\u00a8\u00aa\5\"\22\2\u00a9\u00a7\3\2\2\2\u00a9\u00aa\3" +
                    "\2\2\2\u00aa\u00ad\3\2\2\2\u00ab\u00ac\7\4\2\2\u00ac\u00ae\5&\24\2\u00ad" +
                    "\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00b6\3\2\2\2\u00af\u00b3\7\17" +
                    "\2\2\u00b0\u00b2\5\36\20\2\u00b1\u00b0\3\2\2\2\u00b2\u00b5\3\2\2\2\u00b3" +
                    "\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00b7\3\2\2\2\u00b5\u00b3\3\2" +
                    "\2\2\u00b6\u00af\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7\13\3\2\2\2\u00b8\u00b9" +
                    "\78\2\2\u00b9\u00ba\7\b\2\2\u00ba\u00bb\5> \2\u00bb\u00bc\7\20\2\2\u00bc" +
                    "\u00c8\78\2\2\u00bd\u00be\7\21\2\2\u00be\u00c3\5> \2\u00bf\u00c0\7\n\2" +
                    "\2\u00c0\u00c2\5> \2\u00c1\u00bf\3\2\2\2\u00c2\u00c5\3\2\2\2\u00c3\u00c1" +
                    "\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c6\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c6" +
                    "\u00c7\7\22\2\2\u00c7\u00c9\3\2\2\2\u00c8\u00bd\3\2\2\2\u00c8\u00c9\3" +
                    "\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00cb\7\23\2\2\u00cb\u00cc\5> \2\u00cc" +
                    "\u00cd\7\20\2\2\u00cd\u00d9\78\2\2\u00ce\u00cf\7\21\2\2\u00cf\u00d4\5" +
                    "> \2\u00d0\u00d1\7\n\2\2\u00d1\u00d3\5> \2\u00d2\u00d0\3\2\2\2\u00d3\u00d6" +
                    "\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5\u00d7\3\2\2\2\u00d6" +
                    "\u00d4\3\2\2\2\u00d7\u00d8\7\22\2\2\u00d8\u00da\3\2\2\2\u00d9\u00ce\3" +
                    "\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00dd\3\2\2\2\u00db\u00dc\7\4\2\2\u00dc" +
                    "\u00de\5&\24\2\u00dd\u00db\3\2\2\2\u00dd\u00de\3\2\2\2\u00de\u00e6\3\2" +
                    "\2\2\u00df\u00e3\7\17\2\2\u00e0\u00e2\5\36\20\2\u00e1\u00e0\3\2\2\2\u00e2" +
                    "\u00e5\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4\u00e7\3\2" +
                    "\2\2\u00e5\u00e3\3\2\2\2\u00e6\u00df\3\2\2\2\u00e6\u00e7\3\2\2\2\u00e7" +
                    "\r\3\2\2\2\u00e8\u00e9\7\24\2\2\u00e9\u00ea\78\2\2\u00ea\u00eb\7\25\2" +
                    "\2\u00eb\u00ec\5\64\33\2\u00ec\17\3\2\2\2\u00ed\u00ee\7\26\2\2\u00ee\u00f8" +
                    "\78\2\2\u00ef\u00f0\7\27\2\2\u00f0\u00f5\5> \2\u00f1\u00f2\7\n\2\2\u00f2" +
                    "\u00f4\5> \2\u00f3\u00f1\3\2\2\2\u00f4\u00f7\3\2\2\2\u00f5\u00f3\3\2\2" +
                    "\2\u00f5\u00f6\3\2\2\2\u00f6\u00f9\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f8\u00ef" +
                    "\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\u0104\3\2\2\2\u00fa\u00fb\7\21\2\2" +
                    "\u00fb\u0100\78\2\2\u00fc\u00fd\7\n\2\2\u00fd\u00ff\78\2\2\u00fe\u00fc" +
                    "\3\2\2\2\u00ff\u0102\3\2\2\2\u0100\u00fe\3\2\2\2\u0100\u0101\3\2\2\2\u0101" +
                    "\u0103\3\2\2\2\u0102\u0100\3\2\2\2\u0103\u0105\7\22\2\2\u0104\u00fa\3" +
                    "\2\2\2\u0104\u0105\3\2\2\2\u0105\21\3\2\2\2\u0106\u0107\7\30\2\2\u0107" +
                    "\u0111\78\2\2\u0108\u0109\7\27\2\2\u0109\u010e\5> \2\u010a\u010b\7\n\2" +
                    "\2\u010b\u010d\5> \2\u010c\u010a\3\2\2\2\u010d\u0110\3\2\2\2\u010e\u010c" +
                    "\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0112\3\2\2\2\u0110\u010e\3\2\2\2\u0111" +
                    "\u0108\3\2\2\2\u0111\u0112\3\2\2\2\u0112\23\3\2\2\2\u0113\u0114\7\31\2" +
                    "\2\u0114\u0116\78\2\2\u0115\u0117\5\26\f\2\u0116\u0115\3\2\2\2\u0117\u0118" +
                    "\3\2\2\2\u0118\u0116\3\2\2\2\u0118\u0119\3\2\2\2\u0119\25\3\2\2\2\u011a" +
                    "\u011b\78\2\2\u011b\u011c\7\b\2\2\u011c\u011d\5\64\33\2\u011d\27\3\2\2" +
                    "\2\u011e\u011f\t\2\2\2\u011f\u0122\78\2\2\u0120\u0121\7\b\2\2\u0121\u0123" +
                    "\5> \2\u0122\u0120\3\2\2\2\u0122\u0123\3\2\2\2\u0123\31\3\2\2\2\u0124" +
                    "\u0125\78\2\2\u0125\u0126\7\25\2\2\u0126\u0127\7\21\2\2\u0127\u012c\5" +
                    "> \2\u0128\u0129\7\n\2\2\u0129\u012b\5> \2\u012a\u0128\3\2\2\2\u012b\u012e" +
                    "\3\2\2\2\u012c\u012a\3\2\2\2\u012c\u012d\3\2\2\2\u012d\u012f\3\2\2\2\u012e" +
                    "\u012c\3\2\2\2\u012f\u0130\7\22\2\2\u0130\33\3\2\2\2\u0131\u0132\78\2" +
                    "\2\u0132\u0142\7\b\2\2\u0133\u013f\78\2\2\u0134\u0135\7\21\2\2\u0135\u013a" +
                    "\5> \2\u0136\u0137\7\n\2\2\u0137\u0139\5> \2\u0138\u0136\3\2\2\2\u0139" +
                    "\u013c\3\2\2\2\u013a\u0138\3\2\2\2\u013a\u013b\3\2\2\2\u013b\u013d\3\2" +
                    "\2\2\u013c\u013a\3\2\2\2\u013d\u013e\7\22\2\2\u013e\u0140\3\2\2\2\u013f" +
                    "\u0134\3\2\2\2\u013f\u0140\3\2\2\2\u0140\u0143\3\2\2\2\u0141\u0143\7\34" +
                    "\2\2\u0142\u0133\3\2\2\2\u0142\u0141\3\2\2\2\u0143\u0144\3\2\2\2\u0144" +
                    "\u0154\7\23\2\2\u0145\u0151\78\2\2\u0146\u0147\7\21\2\2\u0147\u014c\5" +
                    "> \2\u0148\u0149\7\n\2\2\u0149\u014b\5> \2\u014a\u0148\3\2\2\2\u014b\u014e" +
                    "\3\2\2\2\u014c\u014a\3\2\2\2\u014c\u014d\3\2\2\2\u014d\u014f\3\2\2\2\u014e" +
                    "\u014c\3\2\2\2\u014f\u0150\7\22\2\2\u0150\u0152\3\2\2\2\u0151\u0146\3" +
                    "\2\2\2\u0151\u0152\3\2\2\2\u0152\u0155\3\2\2\2\u0153\u0155\7\34\2\2\u0154" +
                    "\u0145\3\2\2\2\u0154\u0153\3\2\2\2\u0155\35\3\2\2\2\u0156\u0157\78\2\2" +
                    "\u0157\u0158\7\b\2\2\u0158\u015b\5\64\33\2\u0159\u015a\7\25\2\2\u015a" +
                    "\u015c\5:\36\2\u015b\u0159\3\2\2\2\u015b\u015c\3\2\2\2\u015c\37\3\2\2" +
                    "\2\u015d\u015e\5> \2\u015e\u015f\7\b\2\2\u015f\u0160\5\64\33\2\u0160\u0161" +
                    "\7\25\2\2\u0161\u0162\5:\36\2\u0162!\3\2\2\2\u0163\u0165\5$\23\2\u0164" +
                    "\u0163\3\2\2\2\u0165\u0166\3\2\2\2\u0166\u0164\3\2\2\2\u0166\u0167\3\2" +
                    "\2\2\u0167#\3\2\2\2\u0168\u0169\5*\26\2\u0169\u016c\7\35\2\2\u016a\u016d" +
                    "\5,\27\2\u016b\u016d\5*\26\2\u016c\u016a\3\2\2\2\u016c\u016b\3\2\2\2\u016d" +
                    "\u016e\3\2\2\2\u016e\u016f\7\36\2\2\u016f\u0170\3\2\2\2\u0170\u0171\5" +
                    "*\26\2\u0171%\3\2\2\2\u0172\u0174\5(\25\2\u0173\u0172\3\2\2\2\u0174\u0175" +
                    "\3\2\2\2\u0175\u0173\3\2\2\2\u0175\u0176\3\2\2\2\u0176\'\3\2\2\2\u0177" +
                    "\u017a\5,\27\2\u0178\u017a\7\34\2\2\u0179\u0177\3\2\2\2\u0179\u0178\3" +
                    "\2\2\2\u017a\u0180\3\2\2\2\u017b\u0181\7\23\2\2\u017c\u017d\7\35\2\2\u017d" +
                    "\u017e\5*\26\2\u017e\u017f\7\36\2\2\u017f\u0181\3\2\2\2\u0180\u017b\3" +
                    "\2\2\2\u0180\u017c\3\2\2\2\u0181\u0184\3\2\2\2\u0182\u0185\5,\27\2\u0183" +
                    "\u0185\7\34\2\2\u0184\u0182\3\2\2\2\u0184\u0183\3\2\2\2\u0185)\3\2\2\2" +
                    "\u0186\u0191\78\2\2\u0187\u0188\7\37\2\2\u0188\u018b\78\2\2\u0189\u018a" +
                    "\7\n\2\2\u018a\u018c\78\2\2\u018b\u0189\3\2\2\2\u018c\u018d\3\2\2\2\u018d" +
                    "\u018b\3\2\2\2\u018d\u018e\3\2\2\2\u018e\u018f\3\2\2\2\u018f\u0191\7 " +
                    "\2\2\u0190\u0186\3\2\2\2\u0190\u0187\3\2\2\2\u0191+\3\2\2\2\u0192\u019e" +
                    "\5.\30\2\u0193\u0194\7\37\2\2\u0194\u0197\5.\30\2\u0195\u0196\7\n\2\2" +
                    "\u0196\u0198\5.\30\2\u0197\u0195\3\2\2\2\u0198\u0199\3\2\2\2\u0199\u0197" +
                    "\3\2\2\2\u0199\u019a\3\2\2\2\u019a\u019b\3\2\2\2\u019b\u019c\7 \2\2\u019c" +
                    "\u019e\3\2\2\2\u019d\u0192\3\2\2\2\u019d\u0193\3\2\2\2\u019e-\3\2\2\2" +
                    "\u019f\u01a0\78\2\2\u01a0\u01a1\7\b\2\2\u01a1\u01a2\5\60\31\2\u01a2/\3" +
                    "\2\2\2\u01a3\u01af\5\62\32\2\u01a4\u01a5\7\21\2\2\u01a5\u01a8\5\62\32" +
                    "\2\u01a6\u01a7\7\n\2\2\u01a7\u01a9\5\62\32\2\u01a8\u01a6\3\2\2\2\u01a9" +
                    "\u01aa\3\2\2\2\u01aa\u01a8\3\2\2\2\u01aa\u01ab\3\2\2\2\u01ab\u01ac\3\2" +
                    "\2\2\u01ac\u01ad\7\22\2\2\u01ad\u01af\3\2\2\2\u01ae\u01a3\3\2\2\2\u01ae" +
                    "\u01a4\3\2\2\2\u01af\61\3\2\2\2\u01b0\u01b1\5> \2\u01b1\63\3\2\2\2\u01b2" +
                    "\u01ca\5\66\34\2\u01b3\u01b4\7!\2\2\u01b4\u01b5\7\"\2\2\u01b5\u01b6\5" +
                    "\64\33\2\u01b6\u01b7\7#\2\2\u01b7\u01ca\3\2\2\2\u01b8\u01b9\7$\2\2\u01b9" +
                    "\u01ba\7\"\2\2\u01ba\u01bb\5\64\33\2\u01bb\u01bc\7#\2\2\u01bc\u01ca\3" +
                    "\2\2\2\u01bd\u01be\7%\2\2\u01be\u01bf\7\"\2\2\u01bf\u01c0\5\64\33\2\u01c0" +
                    "\u01c1\7#\2\2\u01c1\u01ca\3\2\2\2\u01c2\u01c3\7&\2\2\u01c3\u01c4\7\"\2" +
                    "\2\u01c4\u01c5\5\64\33\2\u01c5\u01c6\7\n\2\2\u01c6\u01c7\5\64\33\2\u01c7" +
                    "\u01c8\7#\2\2\u01c8\u01ca\3\2\2\2\u01c9\u01b2\3\2\2\2\u01c9\u01b3\3\2" +
                    "\2\2\u01c9\u01b8\3\2\2\2\u01c9\u01bd\3\2\2\2\u01c9\u01c2\3\2\2\2\u01ca" +
                    "\65\3\2\2\2\u01cb\u01db\7\'\2\2\u01cc\u01d3\7(\2\2\u01cd\u01ce\7\37\2" +
                    "\2\u01ce\u01cf\58\35\2\u01cf\u01d0\7\n\2\2\u01d0\u01d1\58\35\2\u01d1\u01d2" +
                    "\7 \2\2\u01d2\u01d4\3\2\2\2\u01d3\u01cd\3\2\2\2\u01d3\u01d4\3\2\2\2\u01d4" +
                    "\u01db\3\2\2\2\u01d5\u01db\7)\2\2\u01d6\u01db\7*\2\2\u01d7\u01db\7+\2" +
                    "\2\u01d8\u01db\7,\2\2\u01d9\u01db\5> \2\u01da\u01cb\3\2\2\2\u01da\u01cc" +
                    "\3\2\2\2\u01da\u01d5\3\2\2\2\u01da\u01d6\3\2\2\2\u01da\u01d7\3\2\2\2\u01da" +
                    "\u01d8\3\2\2\2\u01da\u01d9\3\2\2\2\u01db\67\3\2\2\2\u01dc\u01e0\7\65\2" +
                    "\2\u01dd\u01e0\5> \2\u01de\u01e0\7-\2\2\u01df\u01dc\3\2\2\2\u01df\u01dd" +
                    "\3\2\2\2\u01df\u01de\3\2\2\2\u01e09\3\2\2\2\u01e1\u023e\7.\2\2\u01e2\u023e" +
                    "\7/\2\2\u01e3\u023e\7\65\2\2\u01e4\u023e\7\66\2\2\u01e5\u023e\7\67\2\2" +
                    "\u01e6\u01e7\5> \2\u01e7\u01e8\7\37\2\2\u01e8\u01e9\78\2\2\u01e9\u01ea" +
                    "\7\25\2\2\u01ea\u01f1\5:\36\2\u01eb\u01ec\7\n\2\2\u01ec\u01ed\78\2\2\u01ed" +
                    "\u01ee\7\25\2\2\u01ee\u01f0\5:\36\2\u01ef\u01eb\3\2\2\2\u01f0\u01f3\3" +
                    "\2\2\2\u01f1\u01ef\3\2\2\2\u01f1\u01f2\3\2\2\2\u01f2\u01f4\3\2\2\2\u01f3" +
                    "\u01f1\3\2\2\2\u01f4\u01f5\7 \2\2\u01f5\u023e\3\2\2\2\u01f6\u01f9\5> " +
                    "\2\u01f7\u01f8\7\20\2\2\u01f8\u01fa\78\2\2\u01f9\u01f7\3\2\2\2\u01f9\u01fa" +
                    "\3\2\2\2\u01fa\u023e\3\2\2\2\u01fb\u01fc\7\60\2\2\u01fc\u01fd\7\"\2\2" +
                    "\u01fd\u01fe\5\64\33\2\u01fe\u01ff\7#\2\2\u01ff\u023e\3\2\2\2\u0200\u0201" +
                    "\7\61\2\2\u0201\u0202\7\"\2\2\u0202\u0203\5\64\33\2\u0203\u0204\7#\2\2" +
                    "\u0204\u0205\7\37\2\2\u0205\u0206\5:\36\2\u0206\u0207\7 \2\2\u0207\u023e" +
                    "\3\2\2\2\u0208\u0209\7$\2\2\u0209\u020a\7\"\2\2\u020a\u020b\5\64\33\2" +
                    "\u020b\u020c\7#\2\2\u020c\u0215\7\37\2\2\u020d\u0212\5:\36\2\u020e\u020f" +
                    "\7\n\2\2\u020f\u0211\5:\36\2\u0210\u020e\3\2\2\2\u0211\u0214\3\2\2\2\u0212" +
                    "\u0210\3\2\2\2\u0212\u0213\3\2\2\2\u0213\u0216\3\2\2\2\u0214\u0212\3\2" +
                    "\2\2\u0215\u020d\3\2\2\2\u0215\u0216\3\2\2\2\u0216\u0217\3\2\2\2\u0217" +
                    "\u0218\7 \2\2\u0218\u023e\3\2\2\2\u0219\u021a\7%\2\2\u021a\u021b\7\"\2" +
                    "\2\u021b\u021c\5\64\33\2\u021c\u021d\7#\2\2\u021d\u0226\7\37\2\2\u021e" +
                    "\u0223\5:\36\2\u021f\u0220\7\n\2\2\u0220\u0222\5:\36\2\u0221\u021f\3\2" +
                    "\2\2\u0222\u0225\3\2\2\2\u0223\u0221\3\2\2\2\u0223\u0224\3\2\2\2\u0224" +
                    "\u0227\3\2\2\2\u0225\u0223\3\2\2\2\u0226\u021e\3\2\2\2\u0226\u0227\3\2" +
                    "\2\2\u0227\u0228\3\2\2\2\u0228\u0229\7 \2\2\u0229\u023e\3\2\2\2\u022a" +
                    "\u022b\7&\2\2\u022b\u022c\7\"\2\2\u022c\u022d\5\64\33\2\u022d\u022e\7" +
                    "\n\2\2\u022e\u022f\5\64\33\2\u022f\u0230\7#\2\2\u0230\u0239\7\37\2\2\u0231" +
                    "\u0236\5<\37\2\u0232\u0233\7\n\2\2\u0233\u0235\5<\37\2\u0234\u0232\3\2" +
                    "\2\2\u0235\u0238\3\2\2\2\u0236\u0234\3\2\2\2\u0236\u0237\3\2\2\2\u0237" +
                    "\u023a\3\2\2\2\u0238\u0236\3\2\2\2\u0239\u0231\3\2\2\2\u0239\u023a\3\2" +
                    "\2\2\u023a\u023b\3\2\2\2\u023b\u023c\7 \2\2\u023c\u023e\3\2\2\2\u023d" +
                    "\u01e1\3\2\2\2\u023d\u01e2\3\2\2\2\u023d\u01e3\3\2\2\2\u023d\u01e4\3\2" +
                    "\2\2\u023d\u01e5\3\2\2\2\u023d\u01e6\3\2\2\2\u023d\u01f6\3\2\2\2\u023d" +
                    "\u01fb\3\2\2\2\u023d\u0200\3\2\2\2\u023d\u0208\3\2\2\2\u023d\u0219\3\2" +
                    "\2\2\u023d\u022a\3\2\2\2\u023e;\3\2\2\2\u023f\u0240\5:\36\2\u0240\u0241" +
                    "\7\23\2\2\u0241\u0242\5:\36\2\u0242=\3\2\2\2\u0243\u0248\78\2\2\u0244" +
                    "\u0245\7\62\2\2\u0245\u0247\78\2\2\u0246\u0244\3\2\2\2\u0247\u024a\3\2" +
                    "\2\2\u0248\u0246\3\2\2\2\u0248\u0249\3\2\2\2\u0249?\3\2\2\2\u024a\u0248" +
                    "\3\2\2\2\u024b\u024c\7\63\2\2\u024c\u024d\7\25\2\2\u024d\u024e\7\"\2\2" +
                    "\u024e\u0253\78\2\2\u024f\u0250\7\n\2\2\u0250\u0252\78\2\2\u0251\u024f" +
                    "\3\2\2\2\u0252\u0255\3\2\2\2\u0253\u0251\3\2\2\2\u0253\u0254\3\2\2\2\u0254" +
                    "\u0256\3\2\2\2\u0255\u0253\3\2\2\2\u0256\u0257\7#\2\2\u0257A\3\2\2\2\u0258" +
                    "\u0259\7\64\2\2\u0259\u025a\7\25\2\2\u025a\u025b\7\21\2\2\u025b\u0260" +
                    "\78\2\2\u025c\u025d\7\n\2\2\u025d\u025f\78\2\2\u025e\u025c\3\2\2\2\u025f" +
                    "\u0262\3\2\2\2\u0260\u025e\3\2\2\2\u0260\u0261\3\2\2\2\u0261\u0263\3\2" +
                    "\2\2\u0262\u0260\3\2\2\2\u0263\u0264\7\22\2\2\u0264C\3\2\2\2MKNTW]`fi" +
                    "orx~\u0087\u008a\u0090\u0093\u0099\u009c\u00a2\u00a5\u00a9\u00ad\u00b3" +
                    "\u00b6\u00c3\u00c8\u00d4\u00d9\u00dd\u00e3\u00e6\u00f5\u00f8\u0100\u0104" +
                    "\u010e\u0111\u0118\u0122\u012c\u013a\u013f\u0142\u014c\u0151\u0154\u015b" +
                    "\u0166\u016c\u0175\u0179\u0180\u0184\u018d\u0190\u0199\u019d\u01aa\u01ae" +
                    "\u01c9\u01d3\u01da\u01df\u01f1\u01f9\u0212\u0215\u0223\u0226\u0236\u0239" +
                    "\u023d\u0248\u0253\u0260";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = {
            null, "'types'", "'behavior'", "'constants'", "'components'", "'connections'",
            "':'", "'with'", "','", "'ports'", "'propagations'", "'flows'", "'transitions'",
            "'properties'", "'.'", "'{'", "'}'", "'->'", "'alias'", "'='", "'enum'",
            "'extends'", "'lattice'", "'record'", "'in'", "'out'", "'*'", "'-['",
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

    public Antlr4AwasParser(TokenStream input) {
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
	public String getGrammarFileName() { return "Antlr4Awas.g4"; }

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
			setState(66);
			model();
			setState(67);
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
			setState(76);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(69);
				match(T__0);
				setState(73);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__19) | (1L << T__21) | (1L << T__22))) != 0)) {
					{
					{
					setState(70);
					typeDecl();
					}
					}
					setState(75);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(78);
				match(T__1);
				setState(82);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ID) {
					{
					{
					setState(79);
					behaviorDecl();
					}
					}
					setState(84);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(94);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(87);
				match(T__2);
				setState(91);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ID) {
					{
					{
					setState(88);
					constantDecl();
					}
					}
					setState(93);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(103);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(96);
				match(T__3);
				setState(100);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ID) {
					{
					{
					setState(97);
					componentDecl();
					}
					}
					setState(102);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(112);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(105);
				match(T__4);
				setState(109);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ID) {
					{
					{
					setState(106);
					connectionDecl();
					}
					}
					setState(111);
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

	public final TypeDeclContext typeDecl() throws RecognitionException {
		TypeDeclContext _localctx = new TypeDeclContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_typeDecl);
		try {
			setState(118);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__17:
				_localctx = new AliasTypeDeclContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(114);
				typeAliasDecl();
				}
				break;
			case T__19:
				_localctx = new EnumTypeDeclContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(115);
				enumDecl();
				}
				break;
			case T__21:
				_localctx = new LatticeTypeDeclContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(116);
				latticeDecl();
				}
				break;
			case T__22:
				_localctx = new RecordTypeDeclContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(117);
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

	public final BehaviorDeclContext behaviorDecl() throws RecognitionException {
		BehaviorDeclContext _localctx = new BehaviorDeclContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_behaviorDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			((BehaviorDeclContext)_localctx).smName = match(ID);
			setState(121);
			match(T__5);
			setState(122);
			states();
			setState(124);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__49) {
				{
				setState(123);
				events();
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

	public final ComponentDeclContext componentDecl() throws RecognitionException {
		ComponentDeclContext _localctx = new ComponentDeclContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_componentDecl);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			((ComponentDeclContext)_localctx).compName = match(ID);
			setState(136);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(127);
				match(T__6);
				setState(128);
				((ComponentDeclContext)_localctx).name = name();
				((ComponentDeclContext)_localctx).with.add(((ComponentDeclContext)_localctx).name);
				setState(133);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__7) {
					{
					{
					setState(129);
					match(T__7);
					setState(130);
					((ComponentDeclContext)_localctx).name = name();
					((ComponentDeclContext)_localctx).with.add(((ComponentDeclContext)_localctx).name);
					}
					}
					setState(135);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(145);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(138);
				match(T__8);
				setState(142);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__23 || _la==T__24) {
					{
					{
					setState(139);
					port();
					}
					}
					setState(144);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(154);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9) {
				{
				setState(147);
				match(T__9);
				setState(151);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(148);
						propagation();
                        }
                        }
                    }
					setState(153);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
				}
				}
			}

			setState(163);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(156);
				match(T__10);
				setState(160);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(157);
						flow();
                        }
                        }
                    }
					setState(162);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
				}
				}
			}

			setState(167);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11) {
				{
				setState(165);
				match(T__11);
				setState(166);
				transition();
				}
			}

			setState(171);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(169);
				match(T__1);
				setState(170);
				behaviour();
				}
			}

			setState(180);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__12) {
				{
				setState(173);
				match(T__12);
				setState(177);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(174);
						property();
                        }
                        }
                    }
					setState(179);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
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

	public final ConnectionDeclContext connectionDecl() throws RecognitionException {
		ConnectionDeclContext _localctx = new ConnectionDeclContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_connectionDecl);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			((ConnectionDeclContext)_localctx).connName = match(ID);
			setState(183);
			match(T__5);
			setState(184);
			((ConnectionDeclContext)_localctx).fromComponent = name();
			setState(185);
			match(T__13);
			setState(186);
			((ConnectionDeclContext)_localctx).fromPort = match(ID);
			setState(198);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__14) {
				{
				setState(187);
				match(T__14);
				setState(188);
				((ConnectionDeclContext)_localctx).name = name();
				((ConnectionDeclContext)_localctx).fromE.add(((ConnectionDeclContext)_localctx).name);
				setState(193);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__7) {
					{
					{
					setState(189);
					match(T__7);
					setState(190);
					((ConnectionDeclContext)_localctx).name = name();
					((ConnectionDeclContext)_localctx).fromE.add(((ConnectionDeclContext)_localctx).name);
					}
					}
					setState(195);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(196);
				match(T__15);
				}
			}

			setState(200);
			match(T__16);
			setState(201);
			((ConnectionDeclContext)_localctx).toComponent = name();
			setState(202);
			match(T__13);
			setState(203);
			((ConnectionDeclContext)_localctx).toPort = match(ID);
			setState(215);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__14) {
				{
				setState(204);
				match(T__14);
				setState(205);
				((ConnectionDeclContext)_localctx).name = name();
				((ConnectionDeclContext)_localctx).toE.add(((ConnectionDeclContext)_localctx).name);
				setState(210);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__7) {
					{
					{
					setState(206);
					match(T__7);
					setState(207);
					((ConnectionDeclContext)_localctx).name = name();
					((ConnectionDeclContext)_localctx).toE.add(((ConnectionDeclContext)_localctx).name);
					}
					}
					setState(212);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(213);
				match(T__15);
				}
			}

			setState(219);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(217);
				match(T__1);
				setState(218);
				behaviour();
				}
			}

			setState(228);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__12) {
				{
				setState(221);
				match(T__12);
				setState(225);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(222);
						property();
                        }
                        }
                    }
					setState(227);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
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

	public final TypeAliasDeclContext typeAliasDecl() throws RecognitionException {
		TypeAliasDeclContext _localctx = new TypeAliasDeclContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_typeAliasDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(230);
			match(T__17);
			setState(231);
			match(ID);
			setState(232);
			match(T__18);
			setState(233);
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

	public final EnumDeclContext enumDecl() throws RecognitionException {
		EnumDeclContext _localctx = new EnumDeclContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_enumDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			match(T__19);
			setState(236);
			((EnumDeclContext)_localctx).n = match(ID);
			setState(246);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__20) {
				{
				setState(237);
				match(T__20);
				setState(238);
				((EnumDeclContext)_localctx).name = name();
				((EnumDeclContext)_localctx).supers.add(((EnumDeclContext)_localctx).name);
				setState(243);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__7) {
					{
					{
					setState(239);
					match(T__7);
					setState(240);
					((EnumDeclContext)_localctx).name = name();
					((EnumDeclContext)_localctx).supers.add(((EnumDeclContext)_localctx).name);
					}
					}
					setState(245);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(258);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__14) {
				{
				setState(248);
				match(T__14);
				setState(249);
				((EnumDeclContext)_localctx).ID = match(ID);
				((EnumDeclContext)_localctx).elements.add(((EnumDeclContext)_localctx).ID);
				setState(254);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__7) {
					{
					{
					setState(250);
					match(T__7);
					setState(251);
					((EnumDeclContext)_localctx).ID = match(ID);
					((EnumDeclContext)_localctx).elements.add(((EnumDeclContext)_localctx).ID);
					}
					}
					setState(256);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(257);
				match(T__15);
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

	public final LatticeDeclContext latticeDecl() throws RecognitionException {
		LatticeDeclContext _localctx = new LatticeDeclContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_latticeDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(260);
			match(T__21);
			setState(261);
			((LatticeDeclContext)_localctx).n = match(ID);
			setState(271);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__20) {
				{
				setState(262);
				match(T__20);
				setState(263);
				((LatticeDeclContext)_localctx).name = name();
				((LatticeDeclContext)_localctx).supers.add(((LatticeDeclContext)_localctx).name);
				setState(268);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__7) {
					{
					{
					setState(264);
					match(T__7);
					setState(265);
					((LatticeDeclContext)_localctx).name = name();
					((LatticeDeclContext)_localctx).supers.add(((LatticeDeclContext)_localctx).name);
					}
					}
					setState(270);
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

	public final RecordDeclContext recordDecl() throws RecognitionException {
		RecordDeclContext _localctx = new RecordDeclContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_recordDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273);
			match(T__22);
			setState(274);
			match(ID);
                setState(276);
                _errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(275);
				field();
				}
                }
                setState(278);
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

	public final FieldContext field() throws RecognitionException {
		FieldContext _localctx = new FieldContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_field);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(280);
			match(ID);
			setState(281);
			match(T__5);
			setState(282);
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

	public final PortContext port() throws RecognitionException {
		PortContext _localctx = new PortContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_port);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			((PortContext)_localctx).mod = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==T__23 || _la==T__24) ) {
				((PortContext)_localctx).mod = (Token)_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(285);
			match(ID);
			setState(288);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(286);
				match(T__5);
				setState(287);
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

	public final PropagationContext propagation() throws RecognitionException {
		PropagationContext _localctx = new PropagationContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_propagation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(290);
			((PropagationContext)_localctx).id = match(ID);
			setState(291);
			match(T__18);
			setState(292);
			match(T__14);
			setState(293);
			((PropagationContext)_localctx).name = name();
			((PropagationContext)_localctx).errorT.add(((PropagationContext)_localctx).name);
			setState(298);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(294);
				match(T__7);
				setState(295);
				((PropagationContext)_localctx).name = name();
				((PropagationContext)_localctx).errorT.add(((PropagationContext)_localctx).name);
				}
				}
				setState(300);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(301);
			match(T__15);
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

	public final FlowContext flow() throws RecognitionException {
		FlowContext _localctx = new FlowContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_flow);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(303);
			((FlowContext)_localctx).id = match(ID);
			setState(304);
			match(T__5);
			setState(320);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(305);
				((FlowContext)_localctx).from = match(ID);
				setState(317);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__14) {
					{
					setState(306);
					match(T__14);
					setState(307);
					((FlowContext)_localctx).name = name();
					((FlowContext)_localctx).fromE.add(((FlowContext)_localctx).name);
					setState(312);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__7) {
						{
						{
						setState(308);
						match(T__7);
						setState(309);
						((FlowContext)_localctx).name = name();
						((FlowContext)_localctx).fromE.add(((FlowContext)_localctx).name);
						}
						}
						setState(314);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(315);
					match(T__15);
					}
				}

				}
				break;
			case T__25:
				{
				setState(319);
				match(T__25);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(322);
			match(T__16);
			setState(338);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				{
				setState(323);
				((FlowContext)_localctx).to = match(ID);
				setState(335);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__14) {
					{
					setState(324);
					match(T__14);
					setState(325);
					((FlowContext)_localctx).name = name();
					((FlowContext)_localctx).toE.add(((FlowContext)_localctx).name);
					setState(330);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__7) {
						{
						{
						setState(326);
						match(T__7);
						setState(327);
						((FlowContext)_localctx).name = name();
						((FlowContext)_localctx).toE.add(((FlowContext)_localctx).name);
						}
						}
						setState(332);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(333);
					match(T__15);
					}
				}

				}
				break;
			case T__25:
				{
				setState(337);
				match(T__25);
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

	public final PropertyContext property() throws RecognitionException {
		PropertyContext _localctx = new PropertyContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_property);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(340);
			match(ID);
			setState(341);
			match(T__5);
			setState(342);
			type();
			setState(345);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__18) {
				{
				setState(343);
				match(T__18);
				setState(344);
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

	public final ConstantDeclContext constantDecl() throws RecognitionException {
		ConstantDeclContext _localctx = new ConstantDeclContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_constantDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(347);
			name();
			setState(348);
			match(T__5);
			setState(349);
			type();
			setState(350);
			match(T__18);
			setState(351);
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

	public final TransitionContext transition() throws RecognitionException {
		TransitionContext _localctx = new TransitionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_transition);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
            {
                setState(354);
                _errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(353);
					transExpr();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
                }
                setState(356);
                _errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
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

	public final TransExprContext transExpr() throws RecognitionException {
		TransExprContext _localctx = new TransExprContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_transExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(358);
			((TransExprContext)_localctx).fromState = idGroup();
			{
			setState(359);
			match(T__26);
			setState(362);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				{
				setState(360);
				((TransExprContext)_localctx).propCond = tuple();
				}
				break;
			case 2:
				{
				setState(361);
				((TransExprContext)_localctx).triggers = idGroup();
				}
				break;
			}
			setState(364);
			match(T__27);
			}
			setState(366);
			((TransExprContext)_localctx).toState = idGroup();
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

	public final BehaviourContext behaviour() throws RecognitionException {
		BehaviourContext _localctx = new BehaviourContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_behaviour);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
            {
                setState(369);
                _errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(368);
					expression();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
                }
                setState(371);
                _errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,49,_ctx);
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

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(375);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__28:
			case ID:
				{
				setState(373);
				((ExpressionContext)_localctx).key = tuple();
				}
				break;
			case T__25:
				{
				setState(374);
				match(T__25);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(382);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__16:
				{
				setState(377);
				match(T__16);
				}
				break;
			case T__26:
				{
				{
				setState(378);
				match(T__26);
				setState(379);
				((ExpressionContext)_localctx).st = idGroup();
				setState(380);
				match(T__27);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(386);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__28:
			case ID:
				{
				setState(384);
				((ExpressionContext)_localctx).value = tuple();
				}
				break;
			case T__25:
				{
				setState(385);
				match(T__25);
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

	public final IdGroupContext idGroup() throws RecognitionException {
		IdGroupContext _localctx = new IdGroupContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_idGroup);
		int _la;
		try {
			setState(398);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(388);
				((IdGroupContext)_localctx).ID = match(ID);
				((IdGroupContext)_localctx).ids.add(((IdGroupContext)_localctx).ID);
				}
				break;
			case T__28:
				enterOuterAlt(_localctx, 2);
				{
				setState(389);
				match(T__28);
				setState(390);
				((IdGroupContext)_localctx).ID = match(ID);
				((IdGroupContext)_localctx).ids.add(((IdGroupContext)_localctx).ID);
                    setState(393);
                    _errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(391);
					match(T__7);
					setState(392);
					((IdGroupContext)_localctx).ID = match(ID);
					((IdGroupContext)_localctx).ids.add(((IdGroupContext)_localctx).ID);
					}
                    }
                    setState(395);
                    _errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__7 );
				setState(397);
				match(T__29);
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

	public final TupleContext tuple() throws RecognitionException {
		TupleContext _localctx = new TupleContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_tuple);
		int _la;
		try {
			setState(411);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(400);
				faultPort();
				}
				break;
			case T__28:
				enterOuterAlt(_localctx, 2);
				{
				setState(401);
				match(T__28);
				setState(402);
				faultPort();
                    setState(405);
                    _errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(403);
					match(T__7);
					setState(404);
					faultPort();
					}
                    }
                    setState(407);
                    _errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__7 );
				setState(409);
				match(T__29);
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

	public final FaultPortContext faultPort() throws RecognitionException {
		FaultPortContext _localctx = new FaultPortContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_faultPort);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(413);
			match(ID);
			setState(414);
			match(T__5);
			setState(415);
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

	public final OneContext one() throws RecognitionException {
		OneContext _localctx = new OneContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_one);
		int _la;
		try {
			setState(428);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				_localctx = new FaultRefContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(417);
				fault();
				}
				break;
			case T__14:
				_localctx = new FaultSetContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(418);
				match(T__14);
				setState(419);
				fault();
                    setState(422);
                    _errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(420);
					match(T__7);
					setState(421);
					fault();
					}
                    }
                    setState(424);
                    _errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__7 );
				setState(426);
				match(T__15);
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

	public final FaultContext fault() throws RecognitionException {
		FaultContext _localctx = new FaultContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_fault);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(430);
			name();
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

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_type);
		try {
			setState(455);
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
				setState(432);
				basicType();
				}
				break;
			case T__30:
				_localctx = new OptionTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(433);
				match(T__30);
				setState(434);
				match(T__31);
				setState(435);
				type();
				setState(436);
				match(T__32);
				}
				break;
			case T__33:
				_localctx = new SetTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(438);
				match(T__33);
				setState(439);
				match(T__31);
				setState(440);
				type();
				setState(441);
				match(T__32);
				}
				break;
			case T__34:
				_localctx = new SeqTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(443);
				match(T__34);
				setState(444);
				match(T__31);
				setState(445);
				type();
				setState(446);
				match(T__32);
				}
				break;
			case T__35:
				_localctx = new MapTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(448);
				match(T__35);
				setState(449);
				match(T__31);
				setState(450);
				((MapTypeContext)_localctx).key = type();
				setState(451);
				match(T__7);
				setState(452);
				((MapTypeContext)_localctx).value = type();
				setState(453);
				match(T__32);
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

	public final BasicTypeContext basicType() throws RecognitionException {
		BasicTypeContext _localctx = new BasicTypeContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_basicType);
		int _la;
		try {
			setState(472);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__36:
				_localctx = new BooleanTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(457);
				match(T__36);
				}
				break;
			case T__37:
				_localctx = new IntegerTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(458);
				match(T__37);
				setState(465);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__28) {
					{
					setState(459);
					match(T__28);
					setState(460);
					((IntegerTypeContext)_localctx).lo = intConstant();
					setState(461);
					match(T__7);
					setState(462);
					((IntegerTypeContext)_localctx).hi = intConstant();
					setState(463);
					match(T__29);
					}
				}

				}
				break;
			case T__38:
				_localctx = new RealTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(467);
				match(T__38);
				}
				break;
			case T__39:
				_localctx = new StringTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(468);
				match(T__39);
				}
				break;
			case T__40:
				_localctx = new ComponentTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(469);
				match(T__40);
				}
				break;
			case T__41:
				_localctx = new PortTypeContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(470);
				match(T__41);
				}
				break;
			case ID:
				_localctx = new NamedTypeContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(471);
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

	public final IntConstantContext intConstant() throws RecognitionException {
		IntConstantContext _localctx = new IntConstantContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_intConstant);
		try {
			setState(477);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTEGER:
				_localctx = new LitIntConstantContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(474);
				match(INTEGER);
				}
				break;
			case ID:
				_localctx = new NamedIntConstantContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(475);
				name();
				}
				break;
			case T__42:
				_localctx = new ArbitraryIntConstantContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(476);
				match(T__42);
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

	public final InitContext init() throws RecognitionException {
		InitContext _localctx = new InitContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_init);
		int _la;
		try {
			setState(571);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,71,_ctx) ) {
			case 1:
				_localctx = new TrueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(479);
				match(T__43);
				}
				break;
			case 2:
				_localctx = new FalseContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(480);
				match(T__44);
				}
				break;
			case 3:
				_localctx = new IntegerContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(481);
				match(INTEGER);
				}
				break;
			case 4:
				_localctx = new RealContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(482);
				match(REAL);
				}
				break;
			case 5:
				_localctx = new StringContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(483);
				match(STRING);
				}
				break;
			case 6:
				_localctx = new RecordContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(484);
				name();
				setState(485);
				match(T__28);
				setState(486);
				match(ID);
				setState(487);
				match(T__18);
				setState(488);
				init();
				setState(495);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__7) {
					{
					{
					setState(489);
					match(T__7);
					setState(490);
					match(ID);
					setState(491);
					match(T__18);
					setState(492);
					init();
					}
					}
					setState(497);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(498);
				match(T__29);
				}
				break;
			case 7:
				_localctx = new NameRefContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(500);
				name();
				setState(503);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__13) {
					{
					setState(501);
					match(T__13);
					setState(502);
					match(ID);
					}
				}

				}
				break;
			case 8:
				_localctx = new NoneContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(505);
				match(T__45);
				setState(506);
				match(T__31);
				setState(507);
				type();
				setState(508);
				match(T__32);
				}
				break;
			case 9:
				_localctx = new SomeContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(510);
				match(T__46);
				setState(511);
				match(T__31);
				setState(512);
				type();
				setState(513);
				match(T__32);
				setState(514);
				match(T__28);
				setState(515);
				init();
				setState(516);
				match(T__29);
				}
				break;
			case 10:
				_localctx = new SetContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(518);
				match(T__33);
				setState(519);
				match(T__31);
				setState(520);
				type();
				setState(521);
				match(T__32);
				setState(522);
				match(T__28);
				setState(531);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << INTEGER) | (1L << REAL) | (1L << STRING) | (1L << ID))) != 0)) {
					{
					setState(523);
					init();
					setState(528);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__7) {
						{
						{
						setState(524);
						match(T__7);
						setState(525);
						init();
						}
						}
						setState(530);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(533);
				match(T__29);
				}
				break;
			case 11:
				_localctx = new SeqContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(535);
				match(T__34);
				setState(536);
				match(T__31);
				setState(537);
				type();
				setState(538);
				match(T__32);
				setState(539);
				match(T__28);
				setState(548);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << INTEGER) | (1L << REAL) | (1L << STRING) | (1L << ID))) != 0)) {
					{
					setState(540);
					init();
					setState(545);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__7) {
						{
						{
						setState(541);
						match(T__7);
						setState(542);
						init();
						}
						}
						setState(547);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(550);
				match(T__29);
				}
				break;
			case 12:
				_localctx = new MapContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(552);
				match(T__35);
				setState(553);
				match(T__31);
				setState(554);
				((MapContext)_localctx).key = type();
				setState(555);
				match(T__7);
				setState(556);
				((MapContext)_localctx).value = type();
				setState(557);
				match(T__32);
				setState(558);
				match(T__28);
				setState(567);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << INTEGER) | (1L << REAL) | (1L << STRING) | (1L << ID))) != 0)) {
					{
					setState(559);
					mapEntry();
					setState(564);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__7) {
						{
						{
						setState(560);
						match(T__7);
						setState(561);
						mapEntry();
						}
						}
						setState(566);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(569);
				match(T__29);
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

	public final MapEntryContext mapEntry() throws RecognitionException {
		MapEntryContext _localctx = new MapEntryContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_mapEntry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(573);
			((MapEntryContext)_localctx).key = init();
			setState(574);
			match(T__16);
			setState(575);
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

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_name);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(577);
			match(ID);
			setState(582);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__47) {
				{
				{
				setState(578);
				match(T__47);
				setState(579);
				match(ID);
				}
				}
				setState(584);
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

	public final StatesContext states() throws RecognitionException {
		StatesContext _localctx = new StatesContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_states);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(585);
			match(T__48);
			setState(586);
			match(T__18);
			setState(587);
			match(T__31);
			setState(588);
			((StatesContext)_localctx).ID = match(ID);
			((StatesContext)_localctx).state.add(((StatesContext)_localctx).ID);
			setState(593);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(589);
				match(T__7);
				setState(590);
				((StatesContext)_localctx).ID = match(ID);
				((StatesContext)_localctx).state.add(((StatesContext)_localctx).ID);
				}
				}
				setState(595);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(596);
			match(T__32);
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

	public final EventsContext events() throws RecognitionException {
		EventsContext _localctx = new EventsContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_events);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(598);
			match(T__49);
			setState(599);
			match(T__18);
			setState(600);
			match(T__14);
			setState(601);
			((EventsContext)_localctx).ID = match(ID);
			((EventsContext)_localctx).event.add(((EventsContext)_localctx).ID);
			setState(606);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7) {
				{
				{
				setState(602);
				match(T__7);
				setState(603);
				((EventsContext)_localctx).ID = match(ID);
				((EventsContext)_localctx).event.add(((EventsContext)_localctx).ID);
				}
				}
				setState(608);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(609);
			match(T__15);
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

    public static class ModelFileContext extends ParserRuleContext {
        public ModelFileContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public ModelContext model() {
            return getRuleContext(ModelContext.class, 0);
        }

        public TerminalNode EOF() {
            return getToken(Antlr4AwasParser.EOF, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_modelFile;
        }
    }

    public static class ModelContext extends ParserRuleContext {
        public ModelContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

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

        @Override
        public int getRuleIndex() {
            return RULE_model;
        }
    }

    public static class TypeDeclContext extends ParserRuleContext {
        public TypeDeclContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TypeDeclContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_typeDecl;
        }

        public void copyFrom(TypeDeclContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class EnumTypeDeclContext extends TypeDeclContext {
        public EnumTypeDeclContext(TypeDeclContext ctx) {
            copyFrom(ctx);
        }

        public EnumDeclContext enumDecl() {
            return getRuleContext(EnumDeclContext.class, 0);
        }
    }

    public static class LatticeTypeDeclContext extends TypeDeclContext {
        public LatticeTypeDeclContext(TypeDeclContext ctx) {
            copyFrom(ctx);
        }

        public LatticeDeclContext latticeDecl() {
            return getRuleContext(LatticeDeclContext.class, 0);
        }
    }

    public static class AliasTypeDeclContext extends TypeDeclContext {
        public AliasTypeDeclContext(TypeDeclContext ctx) {
            copyFrom(ctx);
        }

        public TypeAliasDeclContext typeAliasDecl() {
            return getRuleContext(TypeAliasDeclContext.class, 0);
        }
    }

    public static class RecordTypeDeclContext extends TypeDeclContext {
        public RecordTypeDeclContext(TypeDeclContext ctx) {
            copyFrom(ctx);
        }

        public RecordDeclContext recordDecl() {
            return getRuleContext(RecordDeclContext.class, 0);
        }
    }

    public static class BehaviorDeclContext extends ParserRuleContext {
        public Token smName;

        public BehaviorDeclContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public StatesContext states() {
            return getRuleContext(StatesContext.class, 0);
        }

        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public EventsContext events() {
            return getRuleContext(EventsContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_behaviorDecl;
        }
    }

    public static class ComponentDeclContext extends ParserRuleContext {
        public Token compName;
        public NameContext name;
        public List<NameContext> with = new ArrayList<NameContext>();

        public ComponentDeclContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

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

        @Override
        public int getRuleIndex() {
            return RULE_componentDecl;
        }
    }

    public static class ConnectionDeclContext extends ParserRuleContext {
        public Token connName;
        public NameContext fromComponent;
        public Token fromPort;
        public NameContext name;
        public List<NameContext> fromE = new ArrayList<NameContext>();
        public NameContext toComponent;
        public Token toPort;
        public List<NameContext> toE = new ArrayList<NameContext>();

        public ConnectionDeclContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

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

        public List<PropertyContext> property() {
            return getRuleContexts(PropertyContext.class);
        }

        public PropertyContext property(int i) {
            return getRuleContext(PropertyContext.class, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_connectionDecl;
        }
    }

    public static class TypeAliasDeclContext extends ParserRuleContext {
        public TypeAliasDeclContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_typeAliasDecl;
        }
    }

    public static class EnumDeclContext extends ParserRuleContext {
        public Token n;
        public NameContext name;
        public List<NameContext> supers = new ArrayList<NameContext>();
        public Token ID;
        public List<Token> elements = new ArrayList<Token>();

        public EnumDeclContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

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

        @Override
        public int getRuleIndex() {
            return RULE_enumDecl;
        }
    }

    public static class LatticeDeclContext extends ParserRuleContext {
        public Token n;
        public NameContext name;
        public List<NameContext> supers = new ArrayList<NameContext>();

        public LatticeDeclContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public List<NameContext> name() {
            return getRuleContexts(NameContext.class);
        }

        public NameContext name(int i) {
            return getRuleContext(NameContext.class, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_latticeDecl;
        }
    }

    public static class RecordDeclContext extends ParserRuleContext {
        public RecordDeclContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public List<FieldContext> field() {
            return getRuleContexts(FieldContext.class);
        }

        public FieldContext field(int i) {
            return getRuleContext(FieldContext.class, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_recordDecl;
        }
    }

    public static class FieldContext extends ParserRuleContext {
        public FieldContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_field;
        }
    }

    public static class PortContext extends ParserRuleContext {
        public Token mod;

        public PortContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_port;
        }
    }

    public static class PropagationContext extends ParserRuleContext {
        public Token id;
        public NameContext name;
        public List<NameContext> errorT = new ArrayList<NameContext>();

        public PropagationContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public List<NameContext> name() {
            return getRuleContexts(NameContext.class);
        }

        public NameContext name(int i) {
            return getRuleContext(NameContext.class, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_propagation;
        }
    }

    public static class FlowContext extends ParserRuleContext {
        public Token id;
        public Token from;
        public NameContext name;
        public List<NameContext> fromE = new ArrayList<NameContext>();
        public Token to;
        public List<NameContext> toE = new ArrayList<NameContext>();

        public FlowContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

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

        @Override
        public int getRuleIndex() {
            return RULE_flow;
        }
    }

    public static class PropertyContext extends ParserRuleContext {
        public PropertyContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }

        public InitContext init() {
            return getRuleContext(InitContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_property;
        }
    }

    public static class ConstantDeclContext extends ParserRuleContext {
        public ConstantDeclContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }

        public InitContext init() {
            return getRuleContext(InitContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_constantDecl;
        }
    }

    public static class TransitionContext extends ParserRuleContext {
        public TransitionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<TransExprContext> transExpr() {
            return getRuleContexts(TransExprContext.class);
        }

        public TransExprContext transExpr(int i) {
            return getRuleContext(TransExprContext.class, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_transition;
        }
    }

    public static class TransExprContext extends ParserRuleContext {
        public IdGroupContext fromState;
        public TupleContext propCond;
        public IdGroupContext triggers;
        public IdGroupContext toState;

        public TransExprContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<IdGroupContext> idGroup() {
            return getRuleContexts(IdGroupContext.class);
        }

        public IdGroupContext idGroup(int i) {
            return getRuleContext(IdGroupContext.class, i);
        }

        public TupleContext tuple() {
            return getRuleContext(TupleContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_transExpr;
        }
    }

    public static class BehaviourContext extends ParserRuleContext {
        public BehaviourContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<ExpressionContext> expression() {
            return getRuleContexts(ExpressionContext.class);
        }

        public ExpressionContext expression(int i) {
            return getRuleContext(ExpressionContext.class, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_behaviour;
        }
    }

    public static class ExpressionContext extends ParserRuleContext {
        public TupleContext key;
        public IdGroupContext st;
        public TupleContext value;

        public ExpressionContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<TupleContext> tuple() {
            return getRuleContexts(TupleContext.class);
        }

        public TupleContext tuple(int i) {
            return getRuleContext(TupleContext.class, i);
        }

        public IdGroupContext idGroup() {
            return getRuleContext(IdGroupContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_expression;
        }
    }

    public static class IdGroupContext extends ParserRuleContext {
        public Token ID;
        public List<Token> ids = new ArrayList<Token>();

        public IdGroupContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<TerminalNode> ID() {
            return getTokens(Antlr4AwasParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(Antlr4AwasParser.ID, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_idGroup;
        }
    }

    public static class TupleContext extends ParserRuleContext {
        public TupleContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<FaultPortContext> faultPort() {
            return getRuleContexts(FaultPortContext.class);
        }

        public FaultPortContext faultPort(int i) {
            return getRuleContext(FaultPortContext.class, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_tuple;
        }
    }

    public static class FaultPortContext extends ParserRuleContext {
        public FaultPortContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }

        public OneContext one() {
            return getRuleContext(OneContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_faultPort;
        }
    }

    public static class OneContext extends ParserRuleContext {
        public OneContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public OneContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_one;
        }

        public void copyFrom(OneContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class FaultRefContext extends OneContext {
        public FaultRefContext(OneContext ctx) {
            copyFrom(ctx);
        }

        public FaultContext fault() {
            return getRuleContext(FaultContext.class, 0);
        }
    }

    public static class FaultSetContext extends OneContext {
        public FaultSetContext(OneContext ctx) {
            copyFrom(ctx);
        }

        public List<FaultContext> fault() {
            return getRuleContexts(FaultContext.class);
        }

        public FaultContext fault(int i) {
            return getRuleContext(FaultContext.class, i);
        }
    }

    public static class FaultContext extends ParserRuleContext {
        public FaultContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_fault;
        }
    }

    public static class TypeContext extends ParserRuleContext {
        public TypeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TypeContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_type;
        }

        public void copyFrom(TypeContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class BaseTypeContext extends TypeContext {
        public BaseTypeContext(TypeContext ctx) {
            copyFrom(ctx);
        }

        public BasicTypeContext basicType() {
            return getRuleContext(BasicTypeContext.class, 0);
        }
    }

    public static class SetTypeContext extends TypeContext {
        public SetTypeContext(TypeContext ctx) {
            copyFrom(ctx);
        }

        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }
    }

    public static class SeqTypeContext extends TypeContext {
        public SeqTypeContext(TypeContext ctx) {
            copyFrom(ctx);
        }

        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }
    }

    public static class OptionTypeContext extends TypeContext {
        public OptionTypeContext(TypeContext ctx) {
            copyFrom(ctx);
        }

        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }
    }

    public static class MapTypeContext extends TypeContext {
        public TypeContext key;
        public TypeContext value;

        public MapTypeContext(TypeContext ctx) {
            copyFrom(ctx);
        }

        public List<TypeContext> type() {
            return getRuleContexts(TypeContext.class);
        }

        public TypeContext type(int i) {
            return getRuleContext(TypeContext.class, i);
        }
    }

    public static class BasicTypeContext extends ParserRuleContext {
        public BasicTypeContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public BasicTypeContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_basicType;
        }

        public void copyFrom(BasicTypeContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class IntegerTypeContext extends BasicTypeContext {
        public IntConstantContext lo;
        public IntConstantContext hi;

        public IntegerTypeContext(BasicTypeContext ctx) {
            copyFrom(ctx);
        }

        public List<IntConstantContext> intConstant() {
            return getRuleContexts(IntConstantContext.class);
        }

        public IntConstantContext intConstant(int i) {
            return getRuleContext(IntConstantContext.class, i);
        }
    }

    public static class ComponentTypeContext extends BasicTypeContext {
        public ComponentTypeContext(BasicTypeContext ctx) {
            copyFrom(ctx);
        }
    }

    public static class StringTypeContext extends BasicTypeContext {
        public StringTypeContext(BasicTypeContext ctx) {
            copyFrom(ctx);
        }
    }

    public static class NamedTypeContext extends BasicTypeContext {
        public NamedTypeContext(BasicTypeContext ctx) {
            copyFrom(ctx);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }
    }

    public static class BooleanTypeContext extends BasicTypeContext {
        public BooleanTypeContext(BasicTypeContext ctx) {
            copyFrom(ctx);
        }
    }

    public static class RealTypeContext extends BasicTypeContext {
        public RealTypeContext(BasicTypeContext ctx) {
            copyFrom(ctx);
        }
    }

    public static class PortTypeContext extends BasicTypeContext {
        public PortTypeContext(BasicTypeContext ctx) {
            copyFrom(ctx);
        }
    }

    public static class IntConstantContext extends ParserRuleContext {
        public IntConstantContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public IntConstantContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_intConstant;
        }

        public void copyFrom(IntConstantContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class LitIntConstantContext extends IntConstantContext {
        public LitIntConstantContext(IntConstantContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode INTEGER() {
            return getToken(Antlr4AwasParser.INTEGER, 0);
        }
    }

    public static class ArbitraryIntConstantContext extends IntConstantContext {
        public ArbitraryIntConstantContext(IntConstantContext ctx) {
            copyFrom(ctx);
        }
    }

    public static class NamedIntConstantContext extends IntConstantContext {
        public NamedIntConstantContext(IntConstantContext ctx) {
            copyFrom(ctx);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }
    }

    public static class InitContext extends ParserRuleContext {
        public InitContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public InitContext() {
        }

        @Override
        public int getRuleIndex() {
            return RULE_init;
        }

        public void copyFrom(InitContext ctx) {
            super.copyFrom(ctx);
        }
    }

    public static class IntegerContext extends InitContext {
        public IntegerContext(InitContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode INTEGER() {
            return getToken(Antlr4AwasParser.INTEGER, 0);
        }
    }

    public static class NameRefContext extends InitContext {
        public NameRefContext(InitContext ctx) {
            copyFrom(ctx);
        }

        public NameContext name() {
            return getRuleContext(NameContext.class, 0);
        }

        public TerminalNode ID() {
            return getToken(Antlr4AwasParser.ID, 0);
        }
    }

    public static class SomeContext extends InitContext {
        public SomeContext(InitContext ctx) {
            copyFrom(ctx);
        }

        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }

        public InitContext init() {
            return getRuleContext(InitContext.class, 0);
        }
    }

    public static class SetContext extends InitContext {
        public SetContext(InitContext ctx) {
            copyFrom(ctx);
        }

        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }

        public List<InitContext> init() {
            return getRuleContexts(InitContext.class);
        }

        public InitContext init(int i) {
            return getRuleContext(InitContext.class, i);
        }
    }

    public static class RealContext extends InitContext {
        public RealContext(InitContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode REAL() {
            return getToken(Antlr4AwasParser.REAL, 0);
        }
    }

    public static class TrueContext extends InitContext {
        public TrueContext(InitContext ctx) {
            copyFrom(ctx);
        }
    }

    public static class RecordContext extends InitContext {
        public RecordContext(InitContext ctx) {
            copyFrom(ctx);
        }

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
    }

    public static class FalseContext extends InitContext {
        public FalseContext(InitContext ctx) {
            copyFrom(ctx);
        }
    }

    public static class StringContext extends InitContext {
        public StringContext(InitContext ctx) {
            copyFrom(ctx);
        }

        public TerminalNode STRING() {
            return getToken(Antlr4AwasParser.STRING, 0);
        }
    }

    public static class NoneContext extends InitContext {
        public NoneContext(InitContext ctx) {
            copyFrom(ctx);
        }

        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }
    }

    public static class MapContext extends InitContext {
        public TypeContext key;
        public TypeContext value;

        public MapContext(InitContext ctx) {
            copyFrom(ctx);
        }

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
    }

    public static class SeqContext extends InitContext {
        public SeqContext(InitContext ctx) {
            copyFrom(ctx);
        }

        public TypeContext type() {
            return getRuleContext(TypeContext.class, 0);
        }

        public List<InitContext> init() {
            return getRuleContexts(InitContext.class);
        }

        public InitContext init(int i) {
            return getRuleContext(InitContext.class, i);
        }
    }

    public static class MapEntryContext extends ParserRuleContext {
        public InitContext key;
        public InitContext value;

        public MapEntryContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<InitContext> init() {
            return getRuleContexts(InitContext.class);
        }

        public InitContext init(int i) {
            return getRuleContext(InitContext.class, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_mapEntry;
        }
    }

    public static class NameContext extends ParserRuleContext {
        public NameContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<TerminalNode> ID() {
            return getTokens(Antlr4AwasParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(Antlr4AwasParser.ID, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_name;
        }
    }

    public static class StatesContext extends ParserRuleContext {
        public Token ID;
        public List<Token> state = new ArrayList<Token>();

        public StatesContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<TerminalNode> ID() {
            return getTokens(Antlr4AwasParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(Antlr4AwasParser.ID, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_states;
        }
    }

    public static class EventsContext extends ParserRuleContext {
        public Token ID;
        public List<Token> event = new ArrayList<Token>();

        public EventsContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public List<TerminalNode> ID() {
            return getTokens(Antlr4AwasParser.ID);
        }

        public TerminalNode ID(int i) {
            return getToken(Antlr4AwasParser.ID, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_events; }
    }
}