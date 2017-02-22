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
: id=ID '=' expr
;

expr
: pexpr                                          #PrimaryExpr
| l=expr op='-' r=expr                           #Binary
| l=expr op=('union' | 'intersect') r=expr       #Binary
| l=expr op=('->' | '<-') r=expr                 #Binary
;


pexpr
: nodeNameError                                  #NodeN
| '(' expr ')'                                   #Paren
| '{' nodeNameError(','  nodeNameError)+ '}'     #NodeSet
| '*'                                            #Empty
| '\''id=ID                                     #QueryRes
;

nodeNameError
: nodeName ('{' ids+=ID+ '}')?
;

nodeName
  : ids+=ID ( '.' ids+=ID )* (':' f=('in' | 'out' | 'source' | 'sink'))?
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

