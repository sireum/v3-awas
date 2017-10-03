// Generated from /workspace-v3/sireum-v3/awas/jvm/src/main/resources/org/sireum/aq/parser/Antlr4AQ.g4 by ANTLR 4.7
package org.sireum.aq.parser;

// @formatter:off

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

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
            T__24 = 25, T__25 = 26, T__26 = 27, INTEGER = 28, REAL = 29, STRING = 30, ID = 31, WS = 32,
            COMMENT = 33, LINE_COMMENT = 34, ERROR_CHAR = 35;
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
            "T__25", "T__26", "INTEGER", "REAL", "STRING", "ID", "WS", "COMMENT",
            "LINE_COMMENT", "ERROR_CHAR"
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


    public Antlr4AQLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
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
    public ATN getATN() {
        return _ATN;
    }

    public static final String _serializedATN =
            "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2%\u0125\b\1\4\2\t" +
                    "\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13" +
                    "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22" +
                    "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31" +
                    "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!" +
                    "\t!\4\"\t\"\4#\t#\4$\t$\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3" +
                    "\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\t" +
                    "\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f" +
                    "\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16" +
                    "\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20" +
                    "\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\22" +
                    "\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23" +
                    "\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\26" +
                    "\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35" +
                    "\3\35\3\35\7\35\u00cf\n\35\f\35\16\35\u00d2\13\35\5\35\u00d4\n\35\3\36" +
                    "\3\36\3\36\7\36\u00d9\n\36\f\36\16\36\u00dc\13\36\5\36\u00de\n\36\3\36" +
                    "\3\36\6\36\u00e2\n\36\r\36\16\36\u00e3\3\37\3\37\3\37\3\37\3\37\3\37\7" +
                    "\37\u00ec\n\37\f\37\16\37\u00ef\13\37\3\37\3\37\3\37\3 \3 \7 \u00f6\n" +
                    " \f \16 \u00f9\13 \3 \3 \6 \u00fd\n \r \16 \u00fe\3 \5 \u0102\n \3!\6" +
                    "!\u0105\n!\r!\16!\u0106\3!\3!\3\"\3\"\3\"\3\"\7\"\u010f\n\"\f\"\16\"\u0112" +
                    "\13\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\7#\u011d\n#\f#\16#\u0120\13#\3#" +
                    "\3#\3$\3$\3\u0110\2%\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27" +
                    "\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33" +
                    "\65\34\67\359\36;\37= ?!A\"C#E$G%\3\2\n\3\2\63;\3\2\62;\3\2$$\6\2&&C\\" +
                    "aac|\7\2&&\62;C\\aac|\5\2\13\f\16\17bb\5\2\13\f\16\17\"\"\4\2\f\f\17\17" +
                    "\2\u0131\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2" +
                    "\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27" +
                    "\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2" +
                    "\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2" +
                    "\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2" +
                    "\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2" +
                    "\2G\3\2\2\2\3I\3\2\2\2\5K\3\2\2\2\7M\3\2\2\2\tS\3\2\2\2\13]\3\2\2\2\r" +
                    "`\3\2\2\2\17c\3\2\2\2\21e\3\2\2\2\23j\3\2\2\2\25o\3\2\2\2\27t\3\2\2\2" +
                    "\31y\3\2\2\2\33\u0081\3\2\2\2\35\u0089\3\2\2\2\37\u0092\3\2\2\2!\u009b" +
                    "\3\2\2\2#\u00a1\3\2\2\2%\u00a7\3\2\2\2\'\u00b1\3\2\2\2)\u00bb\3\2\2\2" +
                    "+\u00bd\3\2\2\2-\u00bf\3\2\2\2/\u00c1\3\2\2\2\61\u00c3\3\2\2\2\63\u00c5" +
                    "\3\2\2\2\65\u00c7\3\2\2\2\67\u00c9\3\2\2\29\u00d3\3\2\2\2;\u00dd\3\2\2" +
                    "\2=\u00e5\3\2\2\2?\u0101\3\2\2\2A\u0104\3\2\2\2C\u010a\3\2\2\2E\u0118" +
                    "\3\2\2\2G\u0123\3\2\2\2IJ\7?\2\2J\4\3\2\2\2KL\7/\2\2L\6\3\2\2\2MN\7w\2" +
                    "\2NO\7p\2\2OP\7k\2\2PQ\7q\2\2QR\7p\2\2R\b\3\2\2\2ST\7k\2\2TU\7p\2\2UV" +
                    "\7v\2\2VW\7g\2\2WX\7t\2\2XY\7u\2\2YZ\7g\2\2Z[\7e\2\2[\\\7v\2\2\\\n\3\2" +
                    "\2\2]^\7/\2\2^_\7@\2\2_\f\3\2\2\2`a\7\u0080\2\2ab\7@\2\2b\16\3\2\2\2c" +
                    "d\7<\2\2d\20\3\2\2\2ef\7p\2\2fg\7q\2\2gh\7f\2\2hi\7g\2\2i\22\3\2\2\2j" +
                    "k\7P\2\2kl\7Q\2\2lm\7F\2\2mn\7G\2\2n\24\3\2\2\2op\7r\2\2pq\7q\2\2qr\7" +
                    "t\2\2rs\7v\2\2s\26\3\2\2\2tu\7R\2\2uv\7Q\2\2vw\7T\2\2wx\7V\2\2x\30\3\2" +
                    "\2\2yz\7k\2\2z{\7p\2\2{|\7/\2\2|}\7r\2\2}~\7q\2\2~\177\7t\2\2\177\u0080" +
                    "\7v\2\2\u0080\32\3\2\2\2\u0081\u0082\7K\2\2\u0082\u0083\7P\2\2\u0083\u0084" +
                    "\7/\2\2\u0084\u0085\7R\2\2\u0085\u0086\7Q\2\2\u0086\u0087\7T\2\2\u0087" +
                    "\u0088\7V\2\2\u0088\34\3\2\2\2\u0089\u008a\7q\2\2\u008a\u008b\7w\2\2\u008b" +
                    "\u008c\7v\2\2\u008c\u008d\7/\2\2\u008d\u008e\7r\2\2\u008e\u008f\7q\2\2" +
                    "\u008f\u0090\7t\2\2\u0090\u0091\7v\2\2\u0091\36\3\2\2\2\u0092\u0093\7" +
                    "Q\2\2\u0093\u0094\7W\2\2\u0094\u0095\7V\2\2\u0095\u0096\7/\2\2\u0096\u0097" +
                    "\7R\2\2\u0097\u0098\7Q\2\2\u0098\u0099\7T\2\2\u0099\u009a\7V\2\2\u009a" +
                    " \3\2\2\2\u009b\u009c\7g\2\2\u009c\u009d\7t\2\2\u009d\u009e\7t\2\2\u009e" +
                    "\u009f\7q\2\2\u009f\u00a0\7t\2\2\u00a0\"\3\2\2\2\u00a1\u00a2\7G\2\2\u00a2" +
                    "\u00a3\7T\2\2\u00a3\u00a4\7T\2\2\u00a4\u00a5\7Q\2\2\u00a5\u00a6\7T\2\2" +
                    "\u00a6$\3\2\2\2\u00a7\u00a8\7r\2\2\u00a8\u00a9\7q\2\2\u00a9\u00aa\7t\2" +
                    "\2\u00aa\u00ab\7v\2\2\u00ab\u00ac\7g\2\2\u00ac\u00ad\7t\2\2\u00ad\u00ae" +
                    "\7t\2\2\u00ae\u00af\7q\2\2\u00af\u00b0\7t\2\2\u00b0&\3\2\2\2\u00b1\u00b2" +
                    "\7R\2\2\u00b2\u00b3\7Q\2\2\u00b3\u00b4\7T\2\2\u00b4\u00b5\7V\2\2\u00b5" +
                    "\u00b6\7G\2\2\u00b6\u00b7\7T\2\2\u00b7\u00b8\7T\2\2\u00b8\u00b9\7Q\2\2" +
                    "\u00b9\u00ba\7T\2\2\u00ba(\3\2\2\2\u00bb\u00bc\7*\2\2\u00bc*\3\2\2\2\u00bd" +
                    "\u00be\7+\2\2\u00be,\3\2\2\2\u00bf\u00c0\7}\2\2\u00c0.\3\2\2\2\u00c1\u00c2" +
                    "\7.\2\2\u00c2\60\3\2\2\2\u00c3\u00c4\7\177\2\2\u00c4\62\3\2\2\2\u00c5" +
                    "\u00c6\7,\2\2\u00c6\64\3\2\2\2\u00c7\u00c8\7)\2\2\u00c8\66\3\2\2\2\u00c9" +
                    "\u00ca\7\60\2\2\u00ca8\3\2\2\2\u00cb\u00d4\7\62\2\2\u00cc\u00d0\t\2\2" +
                    "\2\u00cd\u00cf\t\3\2\2\u00ce\u00cd\3\2\2\2\u00cf\u00d2\3\2\2\2\u00d0\u00ce" +
                    "\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d4\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d3" +
                    "\u00cb\3\2\2\2\u00d3\u00cc\3\2\2\2\u00d4:\3\2\2\2\u00d5\u00de\7\62\2\2" +
                    "\u00d6\u00da\t\2\2\2\u00d7\u00d9\t\3\2\2\u00d8\u00d7\3\2\2\2\u00d9\u00dc" +
                    "\3\2\2\2\u00da\u00d8\3\2\2\2\u00da\u00db\3\2\2\2\u00db\u00de\3\2\2\2\u00dc" +
                    "\u00da\3\2\2\2\u00dd\u00d5\3\2\2\2\u00dd\u00d6\3\2\2\2\u00de\u00df\3\2" +
                    "\2\2\u00df\u00e1\7\60\2\2\u00e0\u00e2\t\3\2\2\u00e1\u00e0\3\2\2\2\u00e2" +
                    "\u00e3\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4<\3\2\2\2" +
                    "\u00e5\u00e6\7^\2\2\u00e6\u00e7\7$\2\2\u00e7\u00ed\3\2\2\2\u00e8\u00ec" +
                    "\n\4\2\2\u00e9\u00ea\7$\2\2\u00ea\u00ec\7$\2\2\u00eb\u00e8\3\2\2\2\u00eb" +
                    "\u00e9\3\2\2\2\u00ec\u00ef\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ed\u00ee\3\2" +
                    "\2\2\u00ee\u00f0\3\2\2\2\u00ef\u00ed\3\2\2\2\u00f0\u00f1\7^\2\2\u00f1" +
                    "\u00f2\7$\2\2\u00f2>\3\2\2\2\u00f3\u00f7\t\5\2\2\u00f4\u00f6\t\6\2\2\u00f5" +
                    "\u00f4\3\2\2\2\u00f6\u00f9\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f7\u00f8\3\2" +
                    "\2\2\u00f8\u0102\3\2\2\2\u00f9\u00f7\3\2\2\2\u00fa\u00fc\7b\2\2\u00fb" +
                    "\u00fd\n\7\2\2\u00fc\u00fb\3\2\2\2\u00fd\u00fe\3\2\2\2\u00fe\u00fc\3\2" +
                    "\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0100\3\2\2\2\u0100\u0102\7b\2\2\u0101" +
                    "\u00f3\3\2\2\2\u0101\u00fa\3\2\2\2\u0102@\3\2\2\2\u0103\u0105\t\b\2\2" +
                    "\u0104\u0103\3\2\2\2\u0105\u0106\3\2\2\2\u0106\u0104\3\2\2\2\u0106\u0107" +
                    "\3\2\2\2\u0107\u0108\3\2\2\2\u0108\u0109\b!\2\2\u0109B\3\2\2\2\u010a\u010b" +
                    "\7\61\2\2\u010b\u010c\7,\2\2\u010c\u0110\3\2\2\2\u010d\u010f\13\2\2\2" +
                    "\u010e\u010d\3\2\2\2\u010f\u0112\3\2\2\2\u0110\u0111\3\2\2\2\u0110\u010e" +
                    "\3\2\2\2\u0111\u0113\3\2\2\2\u0112\u0110\3\2\2\2\u0113\u0114\7,\2\2\u0114" +
                    "\u0115\7\61\2\2\u0115\u0116\3\2\2\2\u0116\u0117\b\"\2\2\u0117D\3\2\2\2" +
                    "\u0118\u0119\7\61\2\2\u0119\u011a\7\61\2\2\u011a\u011e\3\2\2\2\u011b\u011d" +
                    "\n\t\2\2\u011c\u011b\3\2\2\2\u011d\u0120\3\2\2\2\u011e\u011c\3\2\2\2\u011e" +
                    "\u011f\3\2\2\2\u011f\u0121\3\2\2\2\u0120\u011e\3\2\2\2\u0121\u0122\b#" +
                    "\2\2\u0122F\3\2\2\2\u0123\u0124\13\2\2\2\u0124H\3\2\2\2\20\2\u00d0\u00d3" +
                    "\u00da\u00dd\u00e3\u00eb\u00ed\u00f7\u00fe\u0101\u0106\u0110\u011e\3\b" +
                    "\2\2";
    public static final ATN _ATN =
            new ATNDeserializer().deserialize(_serializedATN.toCharArray());

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }
}