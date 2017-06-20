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
	public static final int
            T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8, T__8 = 9,
            T__9 = 10, T__10 = 11, T__11 = 12, T__12 = 13, T__13 = 14, T__14 = 15, T__15 = 16, T__16 = 17,
            T__17 = 18, T__18 = 19, INTEGER = 20, REAL = 21, STRING = 22, ID = 23, WS = 24, COMMENT = 25,
            LINE_COMMENT = 26, ERROR_CHAR = 27;
    public static final String[] ruleNames = {
            "T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8",
            "T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16",
            "T__17", "T__18", "INTEGER", "REAL", "STRING", "ID", "WS", "COMMENT",
            "LINE_COMMENT", "ERROR_CHAR"
    };
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\35\u00d2\b\1\4\2" +
                    "\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4" +
                    "\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22" +
                    "\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31" +
                    "\t\31\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3" +
                    "\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7" +
                    "\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17" +
                    "\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23" +
                    "\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\7\25|\n\25\f\25\16" +
                    "\25\177\13\25\5\25\u0081\n\25\3\26\3\26\3\26\7\26\u0086\n\26\f\26\16\26" +
                    "\u0089\13\26\5\26\u008b\n\26\3\26\3\26\6\26\u008f\n\26\r\26\16\26\u0090" +
                    "\3\27\3\27\3\27\3\27\3\27\3\27\7\27\u0099\n\27\f\27\16\27\u009c\13\27" +
                    "\3\27\3\27\3\27\3\30\3\30\7\30\u00a3\n\30\f\30\16\30\u00a6\13\30\3\30" +
                    "\3\30\6\30\u00aa\n\30\r\30\16\30\u00ab\3\30\5\30\u00af\n\30\3\31\6\31" +
                    "\u00b2\n\31\r\31\16\31\u00b3\3\31\3\31\3\32\3\32\3\32\3\32\7\32\u00bc" +
                    "\n\32\f\32\16\32\u00bf\13\32\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3" +
                    "\33\7\33\u00ca\n\33\f\33\16\33\u00cd\13\33\3\33\3\33\3\34\3\34\3\u00bd" +
                    "\2\35\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35" +
                    "\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\35\3\2" +
                    "\n\3\2\63;\3\2\62;\3\2$$\6\2&&C\\aac|\7\2&&\62;C\\aac|\5\2\13\f\16\17" +
                    "bb\5\2\13\f\16\17\"\"\4\2\f\f\17\17\2\u00de\2\3\3\2\2\2\2\5\3\2\2\2\2" +
                    "\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2" +
                    "\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2" +
                    "\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2" +
                    "\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2" +
                    "\2\2\65\3\2\2\2\2\67\3\2\2\2\39\3\2\2\2\5;\3\2\2\2\7=\3\2\2\2\tC\3\2\2" +
                    "\2\13M\3\2\2\2\rP\3\2\2\2\17S\3\2\2\2\21U\3\2\2\2\23W\3\2\2\2\25Y\3\2" +
                    "\2\2\27[\3\2\2\2\31]\3\2\2\2\33_\3\2\2\2\35a\3\2\2\2\37c\3\2\2\2!e\3\2" +
                    "\2\2#h\3\2\2\2%l\3\2\2\2\'s\3\2\2\2)\u0080\3\2\2\2+\u008a\3\2\2\2-\u0092" +
                    "\3\2\2\2/\u00ae\3\2\2\2\61\u00b1\3\2\2\2\63\u00b7\3\2\2\2\65\u00c5\3\2" +
                    "\2\2\67\u00d0\3\2\2\29:\7?\2\2:\4\3\2\2\2;<\7/\2\2<\6\3\2\2\2=>\7w\2\2" +
                    ">?\7p\2\2?@\7k\2\2@A\7q\2\2AB\7p\2\2B\b\3\2\2\2CD\7k\2\2DE\7p\2\2EF\7" +
                    "v\2\2FG\7g\2\2GH\7t\2\2HI\7u\2\2IJ\7g\2\2JK\7e\2\2KL\7v\2\2L\n\3\2\2\2" +
                    "MN\7/\2\2NO\7@\2\2O\f\3\2\2\2PQ\7\u0080\2\2QR\7@\2\2R\16\3\2\2\2ST\7*" +
                    "\2\2T\20\3\2\2\2UV\7+\2\2V\22\3\2\2\2WX\7}\2\2X\24\3\2\2\2YZ\7.\2\2Z\26" +
                    "\3\2\2\2[\\\7\177\2\2\\\30\3\2\2\2]^\7,\2\2^\32\3\2\2\2_`\7)\2\2`\34\3" +
                    "\2\2\2ab\7\60\2\2b\36\3\2\2\2cd\7<\2\2d \3\2\2\2ef\7k\2\2fg\7p\2\2g\"" +
                    "\3\2\2\2hi\7q\2\2ij\7w\2\2jk\7v\2\2k$\3\2\2\2lm\7u\2\2mn\7q\2\2no\7w\2" +
                    "\2op\7t\2\2pq\7e\2\2qr\7g\2\2r&\3\2\2\2st\7u\2\2tu\7k\2\2uv\7p\2\2vw\7" +
                    "m\2\2w(\3\2\2\2x\u0081\7\62\2\2y}\t\2\2\2z|\t\3\2\2{z\3\2\2\2|\177\3\2" +
                    "\2\2}{\3\2\2\2}~\3\2\2\2~\u0081\3\2\2\2\177}\3\2\2\2\u0080x\3\2\2\2\u0080" +
                    "y\3\2\2\2\u0081*\3\2\2\2\u0082\u008b\7\62\2\2\u0083\u0087\t\2\2\2\u0084" +
                    "\u0086\t\3\2\2\u0085\u0084\3\2\2\2\u0086\u0089\3\2\2\2\u0087\u0085\3\2" +
                    "\2\2\u0087\u0088\3\2\2\2\u0088\u008b\3\2\2\2\u0089\u0087\3\2\2\2\u008a" +
                    "\u0082\3\2\2\2\u008a\u0083\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008e\7\60" +
                    "\2\2\u008d\u008f\t\3\2\2\u008e\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090" +
                    "\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091,\3\2\2\2\u0092\u0093\7^\2\2\u0093" +
                    "\u0094\7$\2\2\u0094\u009a\3\2\2\2\u0095\u0099\n\4\2\2\u0096\u0097\7$\2" +
                    "\2\u0097\u0099\7$\2\2\u0098\u0095\3\2\2\2\u0098\u0096\3\2\2\2\u0099\u009c" +
                    "\3\2\2\2\u009a\u0098\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u009d\3\2\2\2\u009c" +
                    "\u009a\3\2\2\2\u009d\u009e\7^\2\2\u009e\u009f\7$\2\2\u009f.\3\2\2\2\u00a0" +
                    "\u00a4\t\5\2\2\u00a1\u00a3\t\6\2\2\u00a2\u00a1\3\2\2\2\u00a3\u00a6\3\2" +
                    "\2\2\u00a4\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00af\3\2\2\2\u00a6" +
                    "\u00a4\3\2\2\2\u00a7\u00a9\7b\2\2\u00a8\u00aa\n\7\2\2\u00a9\u00a8\3\2" +
                    "\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac" +
                    "\u00ad\3\2\2\2\u00ad\u00af\7b\2\2\u00ae\u00a0\3\2\2\2\u00ae\u00a7\3\2" +
                    "\2\2\u00af\60\3\2\2\2\u00b0\u00b2\t\b\2\2\u00b1\u00b0\3\2\2\2\u00b2\u00b3" +
                    "\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5" +
                    "\u00b6\b\31\2\2\u00b6\62\3\2\2\2\u00b7\u00b8\7\61\2\2\u00b8\u00b9\7,\2" +
                    "\2\u00b9\u00bd\3\2\2\2\u00ba\u00bc\13\2\2\2\u00bb\u00ba\3\2\2\2\u00bc" +
                    "\u00bf\3\2\2\2\u00bd\u00be\3\2\2\2\u00bd\u00bb\3\2\2\2\u00be\u00c0\3\2" +
                    "\2\2\u00bf\u00bd\3\2\2\2\u00c0\u00c1\7,\2\2\u00c1\u00c2\7\61\2\2\u00c2" +
                    "\u00c3\3\2\2\2\u00c3\u00c4\b\32\2\2\u00c4\64\3\2\2\2\u00c5\u00c6\7\61" +
                    "\2\2\u00c6\u00c7\7\61\2\2\u00c7\u00cb\3\2\2\2\u00c8\u00ca\n\t\2\2\u00c9" +
                    "\u00c8\3\2\2\2\u00ca\u00cd\3\2\2\2\u00cb\u00c9\3\2\2\2\u00cb\u00cc\3\2" +
                    "\2\2\u00cc\u00ce\3\2\2\2\u00cd\u00cb\3\2\2\2\u00ce\u00cf\b\33\2\2\u00cf" +
                    "\66\3\2\2\2\u00d0\u00d1\13\2\2\2\u00d18\3\2\2\2\20\2}\u0080\u0087\u008a" +
                    "\u0090\u0098\u009a\u00a4\u00ab\u00ae\u00b3\u00bd\u00cb\3\b\2\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
            new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = {
            null, "'='", "'-'", "'union'", "'intersect'", "'->'", "'~>'", "'('", "')'",
            "'{'", "','", "'}'", "'*'", "'''", "'.'", "':'", "'in'", "'out'", "'source'",
            "'sink'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, "INTEGER", "REAL", "STRING", 
		"ID", "WS", "COMMENT", "LINE_COMMENT", "ERROR_CHAR"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
    public static String[] channelNames = {
            "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };
    public static String[] modeNames = {
            "DEFAULT_MODE"
    };

    static {
        RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION);
    }

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

    public Antlr4AQLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
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
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public String[] getChannelNames() {
        return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }
}