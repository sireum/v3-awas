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
| l=expr op=('->' | '~>') r=expr                 #Binary
| l=expr ':' op=filter                           #FilterExpr
;

filter
: 'node' | 'NODE'                                #Node
| 'port' | 'PORT'                                #Port
| 'in-port' | 'IN-PORT'                          #In
| 'out-port' | 'OUT-PORT'                        #Out
| 'error' | 'ERROR'                              #Error
| 'porterror' | 'PORTERROR'                      #PortError
;

pexpr
: nodeNameError                                  #NodeN
| '(' expr ')'                                   #Paren
| '{' nodeNameError(','  nodeNameError)+ '}'     #NodeSet
| '*'                                            #Empty
| '\''id=ID                                     #QueryRes
;

nodeNameError
: nodeName ('{' errorId(','  errorId)* '}')?
;

errorId
: ids+=ID ( '.' ids+=ID )*
;

nodeName
  : ids+=ID ( '.' ids+=ID )* //(':' f=('in' | 'out'))?
  ;

INTEGER
  : '0' | [1-9] [0-9]*
  ;

REAL
  : ( '0' | [1-9] [0-9]* ) '.' [0-9]+
  ;

STRING
  : '\\"' ( ~["] | '"' '"' )* '\\"'
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

