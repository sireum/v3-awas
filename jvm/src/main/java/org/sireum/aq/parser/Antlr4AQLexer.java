/*
 * // #Sireum
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
 *
 */

// Generated from /workspace-v3/sireum-v3/awas/jvm/src/main/resources/org/sireum/aq/parser/Antlr4AQ.g4 by ANTLR 4.7
package org.sireum.aq.parser;

// @formatter:off

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Antlr4AQLexer extends Lexer {
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
            T__45 = 46, T__46 = 47, INTEGER = 48, REAL = 49, STRING = 50, ID = 51, WS = 52, COMMENT = 53,
            LINE_COMMENT = 54, ERROR_CHAR = 55;
    public static String[] channelNames = {
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };

    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

    public static final String[] ruleNames = {
            "T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8",
            "T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16",
            "T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24",
            "T__25", "T__26", "T__27", "T__28", "T__29", "T__30", "T__31", "T__32",
            "T__33", "T__34", "T__35", "T__36", "T__37", "T__38", "T__39", "T__40",
            "T__41", "T__42", "T__43", "T__44", "T__45", "T__46", "INTEGER", "REAL",
            "STRING", "ID", "WS", "COMMENT", "LINE_COMMENT", "ERROR_CHAR"
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


    public Antlr4AQLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    @Override
    public String getGrammarFileName() {
        return "Antlr4AQ.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public String[] getChannelNames() {
        return channelNames;
    }

    @Override
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\29\u01d3\b\1\4\2\t" +
                    "\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
                    "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22" +
                    "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31" +
                    "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!" +
                    "\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4" +
                    ",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t" +
                    "\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3" +
                    "\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6" +
                    "\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3" +
                    "\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f" +
                    "\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\20" +
                    "\3\20\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25" +
                    "\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32" +
                    "\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\35\3\35" +
                    "\3\35\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36" +
                    "\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3 " +
                    "\3 \3!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3#\3#" +
                    "\3#\3#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3%\3%\3%\3%\3%\3%\3&\3&\3&\3&\3&" +
                    "\3&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(" +
                    "\3(\3(\3(\3(\3(\3(\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3*\3*\3*\3*\3*\3*\3*" +
                    "\3*\3*\3*\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3,\3,\3,\3,\3,\3,\3,\3,\3,\3," +
                    "\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\61\7\61\u017d\n\61\f\61\16\61" +
                    "\u0180\13\61\5\61\u0182\n\61\3\62\3\62\3\62\7\62\u0187\n\62\f\62\16\62" +
                    "\u018a\13\62\5\62\u018c\n\62\3\62\3\62\6\62\u0190\n\62\r\62\16\62\u0191" +
                    "\3\63\3\63\3\63\3\63\3\63\3\63\7\63\u019a\n\63\f\63\16\63\u019d\13\63" +
                    "\3\63\3\63\3\63\3\64\3\64\7\64\u01a4\n\64\f\64\16\64\u01a7\13\64\3\64" +
                    "\3\64\6\64\u01ab\n\64\r\64\16\64\u01ac\3\64\5\64\u01b0\n\64\3\65\6\65" +
                    "\u01b3\n\65\r\65\16\65\u01b4\3\65\3\65\3\66\3\66\3\66\3\66\7\66\u01bd" +
                    "\n\66\f\66\16\66\u01c0\13\66\3\66\3\66\3\66\3\66\3\66\3\67\3\67\3\67\3" +
                    "\67\7\67\u01cb\n\67\f\67\16\67\u01ce\13\67\3\67\3\67\38\38\3\u01be\29" +
                    "\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20" +
                    "\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37" +
                    "= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o" +
                    "9\3\2\n\3\2\63;\3\2\62;\3\2$$\6\2&&C\\aac|\7\2&&\62;C\\aac|\5\2\13\f\16" +
                    "\17bb\5\2\13\f\16\17\"\"\4\2\f\f\17\17\2\u01df\2\3\3\2\2\2\2\5\3\2\2\2" +
                    "\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3" +
                    "\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2" +
                    "\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2" +
                    "\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2" +
                    "\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2" +
                    "\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2" +
                    "\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y" +
                    "\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2" +
                    "\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\3q\3\2\2\2" +
                    "\5s\3\2\2\2\7u\3\2\2\2\t{\3\2\2\2\13\u0085\3\2\2\2\r\u008b\3\2\2\2\17" +
                    "\u008d\3\2\2\2\21\u0095\3\2\2\2\23\u009e\3\2\2\2\25\u00a3\3\2\2\2\27\u00a6" +
                    "\3\2\2\2\31\u00ac\3\2\2\2\33\u00b1\3\2\2\2\35\u00b6\3\2\2\2\37\u00ba\3" +
                    "\2\2\2!\u00bf\3\2\2\2#\u00c1\3\2\2\2%\u00c3\3\2\2\2\'\u00c5\3\2\2\2)\u00c7" +
                    "\3\2\2\2+\u00c9\3\2\2\2-\u00cb\3\2\2\2/\u00cd\3\2\2\2\61\u00cf\3\2\2\2" +
                    "\63\u00d4\3\2\2\2\65\u00d9\3\2\2\2\67\u00de\3\2\2\29\u00e3\3\2\2\2;\u00eb" +
                    "\3\2\2\2=\u00f3\3\2\2\2?\u00fc\3\2\2\2A\u0105\3\2\2\2C\u010b\3\2\2\2E" +
                    "\u0111\3\2\2\2G\u011b\3\2\2\2I\u0125\3\2\2\2K\u012b\3\2\2\2M\u0131\3\2" +
                    "\2\2O\u013d\3\2\2\2Q\u0149\3\2\2\2S\u0153\3\2\2\2U\u015d\3\2\2\2W\u0167" +
                    "\3\2\2\2Y\u0171\3\2\2\2[\u0173\3\2\2\2]\u0175\3\2\2\2_\u0177\3\2\2\2a" +
                    "\u0181\3\2\2\2c\u018b\3\2\2\2e\u0193\3\2\2\2g\u01af\3\2\2\2i\u01b2\3\2" +
                    "\2\2k\u01b8\3\2\2\2m\u01c6\3\2\2\2o\u01d1\3\2\2\2qr\7?\2\2r\4\3\2\2\2" +
                    "st\7/\2\2t\6\3\2\2\2uv\7w\2\2vw\7p\2\2wx\7k\2\2xy\7q\2\2yz\7p\2\2z\b\3" +
                    "\2\2\2{|\7k\2\2|}\7p\2\2}~\7v\2\2~\177\7g\2\2\177\u0080\7t\2\2\u0080\u0081" +
                    "\7u\2\2\u0081\u0082\7g\2\2\u0082\u0083\7e\2\2\u0083\u0084\7v\2\2\u0084" +
                    "\n\3\2\2\2\u0085\u0086\7t\2\2\u0086\u0087\7g\2\2\u0087\u0088\7c\2\2\u0088" +
                    "\u0089\7e\2\2\u0089\u008a\7j\2\2\u008a\f\3\2\2\2\u008b\u008c\7<\2\2\u008c" +
                    "\16\3\2\2\2\u008d\u008e\7h\2\2\u008e\u008f\7q\2\2\u008f\u0090\7t\2\2\u0090" +
                    "\u0091\7y\2\2\u0091\u0092\7c\2\2\u0092\u0093\7t\2\2\u0093\u0094\7f\2\2" +
                    "\u0094\20\3\2\2\2\u0095\u0096\7d\2\2\u0096\u0097\7c\2\2\u0097\u0098\7" +
                    "e\2\2\u0098\u0099\7m\2\2\u0099\u009a\7y\2\2\u009a\u009b\7c\2\2\u009b\u009c" +
                    "\7t\2\2\u009c\u009d\7f\2\2\u009d\22\3\2\2\2\u009e\u009f\7h\2\2\u009f\u00a0" +
                    "\7t\2\2\u00a0\u00a1\7q\2\2\u00a1\u00a2\7o\2\2\u00a2\24\3\2\2\2\u00a3\u00a4" +
                    "\7v\2\2\u00a4\u00a5\7q\2\2\u00a5\26\3\2\2\2\u00a6\u00a7\7r\2\2\u00a7\u00a8" +
                    "\7c\2\2\u00a8\u00a9\7v\2\2\u00a9\u00aa\7j\2\2\u00aa\u00ab\7u\2\2\u00ab" +
                    "\30\3\2\2\2\u00ac\u00ad\7y\2\2\u00ad\u00ae\7k\2\2\u00ae\u00af\7v\2\2\u00af" +
                    "\u00b0\7j\2\2\u00b0\32\3\2\2\2\u00b1\u00b2\7u\2\2\u00b2\u00b3\7q\2\2\u00b3" +
                    "\u00b4\7o\2\2\u00b4\u00b5\7g\2\2\u00b5\34\3\2\2\2\u00b6\u00b7\7c\2\2\u00b7" +
                    "\u00b8\7n\2\2\u00b8\u00b9\7n\2\2\u00b9\36\3\2\2\2\u00ba\u00bb\7p\2\2\u00bb" +
                    "\u00bc\7q\2\2\u00bc\u00bd\7p\2\2\u00bd\u00be\7g\2\2\u00be \3\2\2\2\u00bf" +
                    "\u00c0\7*\2\2\u00c0\"\3\2\2\2\u00c1\u00c2\7+\2\2\u00c2$\3\2\2\2\u00c3" +
                    "\u00c4\7,\2\2\u00c4&\3\2\2\2\u00c5\u00c6\7-\2\2\u00c6(\3\2\2\2\u00c7\u00c8" +
                    "\7A\2\2\u00c8*\3\2\2\2\u00c9\u00ca\7.\2\2\u00ca,\3\2\2\2\u00cb\u00cc\7" +
                    "~\2\2\u00cc.\3\2\2\2\u00cd\u00ce\7a\2\2\u00ce\60\3\2\2\2\u00cf\u00d0\7" +
                    "p\2\2\u00d0\u00d1\7q\2\2\u00d1\u00d2\7f\2\2\u00d2\u00d3\7g\2\2\u00d3\62" +
                    "\3\2\2\2\u00d4\u00d5\7P\2\2\u00d5\u00d6\7Q\2\2\u00d6\u00d7\7F\2\2\u00d7" +
                    "\u00d8\7G\2\2\u00d8\64\3\2\2\2\u00d9\u00da\7r\2\2\u00da\u00db\7q\2\2\u00db" +
                    "\u00dc\7t\2\2\u00dc\u00dd\7v\2\2\u00dd\66\3\2\2\2\u00de\u00df\7R\2\2\u00df" +
                    "\u00e0\7Q\2\2\u00e0\u00e1\7T\2\2\u00e1\u00e2\7V\2\2\u00e28\3\2\2\2\u00e3" +
                    "\u00e4\7k\2\2\u00e4\u00e5\7p\2\2\u00e5\u00e6\7/\2\2\u00e6\u00e7\7r\2\2" +
                    "\u00e7\u00e8\7q\2\2\u00e8\u00e9\7t\2\2\u00e9\u00ea\7v\2\2\u00ea:\3\2\2" +
                    "\2\u00eb\u00ec\7K\2\2\u00ec\u00ed\7P\2\2\u00ed\u00ee\7/\2\2\u00ee\u00ef" +
                    "\7R\2\2\u00ef\u00f0\7Q\2\2\u00f0\u00f1\7T\2\2\u00f1\u00f2\7V\2\2\u00f2" +
                    "<\3\2\2\2\u00f3\u00f4\7q\2\2\u00f4\u00f5\7w\2\2\u00f5\u00f6\7v\2\2\u00f6" +
                    "\u00f7\7/\2\2\u00f7\u00f8\7r\2\2\u00f8\u00f9\7q\2\2\u00f9\u00fa\7t\2\2" +
                    "\u00fa\u00fb\7v\2\2\u00fb>\3\2\2\2\u00fc\u00fd\7Q\2\2\u00fd\u00fe\7W\2" +
                    "\2\u00fe\u00ff\7V\2\2\u00ff\u0100\7/\2\2\u0100\u0101\7R\2\2\u0101\u0102" +
                    "\7Q\2\2\u0102\u0103\7T\2\2\u0103\u0104\7V\2\2\u0104@\3\2\2\2\u0105\u0106" +
                    "\7g\2\2\u0106\u0107\7t\2\2\u0107\u0108\7t\2\2\u0108\u0109\7q\2\2\u0109" +
                    "\u010a\7t\2\2\u010aB\3\2\2\2\u010b\u010c\7G\2\2\u010c\u010d\7T\2\2\u010d" +
                    "\u010e\7T\2\2\u010e\u010f\7Q\2\2\u010f\u0110\7T\2\2\u0110D\3\2\2\2\u0111" +
                    "\u0112\7r\2\2\u0112\u0113\7q\2\2\u0113\u0114\7t\2\2\u0114\u0115\7v\2\2" +
                    "\u0115\u0116\7g\2\2\u0116\u0117\7t\2\2\u0117\u0118\7t\2\2\u0118\u0119" +
                    "\7q\2\2\u0119\u011a\7t\2\2\u011aF\3\2\2\2\u011b\u011c\7R\2\2\u011c\u011d" +
                    "\7Q\2\2\u011d\u011e\7T\2\2\u011e\u011f\7V\2\2\u011f\u0120\7G\2\2\u0120" +
                    "\u0121\7T\2\2\u0121\u0122\7T\2\2\u0122\u0123\7Q\2\2\u0123\u0124\7T\2\2" +
                    "\u0124H\3\2\2\2\u0125\u0126\7h\2\2\u0126\u0127\7n\2\2\u0127\u0128\7q\2" +
                    "\2\u0128\u0129\7y\2\2\u0129\u012a\7u\2\2\u012aJ\3\2\2\2\u012b\u012c\7" +
                    "H\2\2\u012c\u012d\7N\2\2\u012d\u012e\7Q\2\2\u012e\u012f\7Y\2\2\u012f\u0130" +
                    "\7U\2\2\u0130L\3\2\2\2\u0131\u0132\7h\2\2\u0132\u0133\7n\2\2\u0133\u0134" +
                    "\7q\2\2\u0134\u0135\7y\2\2\u0135\u0136\7/\2\2\u0136\u0137\7u\2\2\u0137" +
                    "\u0138\7q\2\2\u0138\u0139\7w\2\2\u0139\u013a\7t\2\2\u013a\u013b\7e\2\2" +
                    "\u013b\u013c\7g\2\2\u013cN\3\2\2\2\u013d\u013e\7H\2\2\u013e\u013f\7N\2" +
                    "\2\u013f\u0140\7Q\2\2\u0140\u0141\7Y\2\2\u0141\u0142\7/\2\2\u0142\u0143" +
                    "\7U\2\2\u0143\u0144\7Q\2\2\u0144\u0145\7W\2\2\u0145\u0146\7T\2\2\u0146" +
                    "\u0147\7E\2\2\u0147\u0148\7G\2\2\u0148P\3\2\2\2\u0149\u014a\7h\2\2\u014a" +
                    "\u014b\7n\2\2\u014b\u014c\7q\2\2\u014c\u014d\7y\2\2\u014d\u014e\7/\2\2" +
                    "\u014e\u014f\7u\2\2\u014f\u0150\7k\2\2\u0150\u0151\7p\2\2\u0151\u0152" +
                    "\7m\2\2\u0152R\3\2\2\2\u0153\u0154\7H\2\2\u0154\u0155\7N\2\2\u0155\u0156" +
                    "\7Q\2\2\u0156\u0157\7Y\2\2\u0157\u0158\7/\2\2\u0158\u0159\7U\2\2\u0159" +
                    "\u015a\7K\2\2\u015a\u015b\7P\2\2\u015b\u015c\7M\2\2\u015cT\3\2\2\2\u015d" +
                    "\u015e\7h\2\2\u015e\u015f\7n\2\2\u015f\u0160\7q\2\2\u0160\u0161\7y\2\2" +
                    "\u0161\u0162\7/\2\2\u0162\u0163\7r\2\2\u0163\u0164\7c\2\2\u0164\u0165" +
                    "\7v\2\2\u0165\u0166\7j\2\2\u0166V\3\2\2\2\u0167\u0168\7H\2\2\u0168\u0169" +
                    "\7N\2\2\u0169\u016a\7Q\2\2\u016a\u016b\7Y\2\2\u016b\u016c\7/\2\2\u016c" +
                    "\u016d\7R\2\2\u016d\u016e\7C\2\2\u016e\u016f\7V\2\2\u016f\u0170\7J\2\2" +
                    "\u0170X\3\2\2\2\u0171\u0172\7}\2\2\u0172Z\3\2\2\2\u0173\u0174\7\177\2" +
                    "\2\u0174\\\3\2\2\2\u0175\u0176\7)\2\2\u0176^\3\2\2\2\u0177\u0178\7\60" +
                    "\2\2\u0178`\3\2\2\2\u0179\u0182\7\62\2\2\u017a\u017e\t\2\2\2\u017b\u017d" +
                    "\t\3\2\2\u017c\u017b\3\2\2\2\u017d\u0180\3\2\2\2\u017e\u017c\3\2\2\2\u017e" +
                    "\u017f\3\2\2\2\u017f\u0182\3\2\2\2\u0180\u017e\3\2\2\2\u0181\u0179\3\2" +
                    "\2\2\u0181\u017a\3\2\2\2\u0182b\3\2\2\2\u0183\u018c\7\62\2\2\u0184\u0188" +
                    "\t\2\2\2\u0185\u0187\t\3\2\2\u0186\u0185\3\2\2\2\u0187\u018a\3\2\2\2\u0188" +
                    "\u0186\3\2\2\2\u0188\u0189\3\2\2\2\u0189\u018c\3\2\2\2\u018a\u0188\3\2" +
                    "\2\2\u018b\u0183\3\2\2\2\u018b\u0184\3\2\2\2\u018c\u018d\3\2\2\2\u018d" +
                    "\u018f\7\60\2\2\u018e\u0190\t\3\2\2\u018f\u018e\3\2\2\2\u0190\u0191\3" +
                    "\2\2\2\u0191\u018f\3\2\2\2\u0191\u0192\3\2\2\2\u0192d\3\2\2\2\u0193\u0194" +
                    "\7^\2\2\u0194\u0195\7$\2\2\u0195\u019b\3\2\2\2\u0196\u019a\n\4\2\2\u0197" +
                    "\u0198\7$\2\2\u0198\u019a\7$\2\2\u0199\u0196\3\2\2\2\u0199\u0197\3\2\2" +
                    "\2\u019a\u019d\3\2\2\2\u019b\u0199\3\2\2\2\u019b\u019c\3\2\2\2\u019c\u019e" +
                    "\3\2\2\2\u019d\u019b\3\2\2\2\u019e\u019f\7^\2\2\u019f\u01a0\7$\2\2\u01a0" +
                    "f\3\2\2\2\u01a1\u01a5\t\5\2\2\u01a2\u01a4\t\6\2\2\u01a3\u01a2\3\2\2\2" +
                    "\u01a4\u01a7\3\2\2\2\u01a5\u01a3\3\2\2\2\u01a5\u01a6\3\2\2\2\u01a6\u01b0" +
                    "\3\2\2\2\u01a7\u01a5\3\2\2\2\u01a8\u01aa\7b\2\2\u01a9\u01ab\n\7\2\2\u01aa" +
                    "\u01a9\3\2\2\2\u01ab\u01ac\3\2\2\2\u01ac\u01aa\3\2\2\2\u01ac\u01ad\3\2" +
                    "\2\2\u01ad\u01ae\3\2\2\2\u01ae\u01b0\7b\2\2\u01af\u01a1\3\2\2\2\u01af" +
                    "\u01a8\3\2\2\2\u01b0h\3\2\2\2\u01b1\u01b3\t\b\2\2\u01b2\u01b1\3\2\2\2" +
                    "\u01b3\u01b4\3\2\2\2\u01b4\u01b2\3\2\2\2\u01b4\u01b5\3\2\2\2\u01b5\u01b6" +
                    "\3\2\2\2\u01b6\u01b7\b\65\2\2\u01b7j\3\2\2\2\u01b8\u01b9\7\61\2\2\u01b9" +
                    "\u01ba\7,\2\2\u01ba\u01be\3\2\2\2\u01bb\u01bd\13\2\2\2\u01bc\u01bb\3\2" +
                    "\2\2\u01bd\u01c0\3\2\2\2\u01be\u01bf\3\2\2\2\u01be\u01bc\3\2\2\2\u01bf" +
                    "\u01c1\3\2\2\2\u01c0\u01be\3\2\2\2\u01c1\u01c2\7,\2\2\u01c2\u01c3\7\61" +
                    "\2\2\u01c3\u01c4\3\2\2\2\u01c4\u01c5\b\66\2\2\u01c5l\3\2\2\2\u01c6\u01c7" +
                    "\7\61\2\2\u01c7\u01c8\7\61\2\2\u01c8\u01cc\3\2\2\2\u01c9\u01cb\n\t\2\2" +
                    "\u01ca\u01c9\3\2\2\2\u01cb\u01ce\3\2\2\2\u01cc\u01ca\3\2\2\2\u01cc\u01cd" +
                    "\3\2\2\2\u01cd\u01cf\3\2\2\2\u01ce\u01cc\3\2\2\2\u01cf\u01d0\b\67\2\2" +
                    "\u01d0n\3\2\2\2\u01d1\u01d2\13\2\2\2\u01d2p\3\2\2\2\20\2\u017e\u0181\u0188" +
                    "\u018b\u0191\u0199\u019b\u01a5\u01ac\u01af\u01b4\u01be\u01cc\3\b\2\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}