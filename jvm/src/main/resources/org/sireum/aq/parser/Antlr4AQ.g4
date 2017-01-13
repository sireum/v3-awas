grammar Antlr4AQ;

@header {
// @formatter:off
}

modelFile
  : model EOF
  ;

model
: queryStmt+
;

queryStmt
: queryName '=' queryExpr
;

queryName
: ID
;

queryExpr
: expr (operator expr)*
;

expr
: nodeNameError
| '{' nodeNameError '}'
| '*'
| queryName
;

nodeNameError
: nodeName ('{' ID+ '}')?
;

nodeName
  : ID ( '.' ID )* (':' ('in' | 'out' | 'source' | 'sink'))?
  ;

operator
: 'union'
| 'intersect'
| '->'
| '<-'
| '-'
;

filter
:
':'('source' | 'sink' | 'both')
;

INTEGER
  : '0' | [1-9] [0-9]*
  ;

REAL
  : ( '0' | [1-9] [0-9]* ) '.' [0-9]+
  ;

STRING
  : '\"' ( ~["] | '"' '"' )* '\"'
  ;

ID
  : [a-zA-Z$_] [a-zA-Z0-9$_]*
  | '`' ~[\r\n\t\u000C`]+ '`'
  ;

WS
  : [ \r\n\t\u000C]+ -> skip
  ;

COMMENT
  : '/*' .*? '*/' -> skip
  ;

LINE_COMMENT
  : '//' ~[\r\n]* -> skip
  ;

ERROR_CHAR
  : .
  ;

