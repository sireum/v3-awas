grammar Antlr4AQ;
//this is deprecated, kept just for understanding the grammar
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
| 'reach' reachExprs                             #ReachEx
//| l=expr op=('->' | '~>') r=expr                 #Binary
| l=expr ':' op=filter                           #FilterExpr
;

reachExprs
: 'forward' e=expr                                            #Forward
| 'backward' e=expr                                           #Backward
| 'from' s=expr 'to' t=expr                                   #Chop
| 'paths' 'from' s=expr 'to' t=expr ('with' we=withExpr)?     #Path
;

withExpr
: op=('some' | 'all' | 'none') '(' e=expr ')'                 #SimpleConstraint
| regExpr                                                     #RegExConstraint
;

regExpr
: e=regExpr op=('*' | '+' | '?')                              #RegExUnary
| l=regExpr op=',' r=regExpr                                  #RegExConcat
| l=regExpr op='|' r=regExpr                                  #RegExUnion
| primaryRExpr                                                #RegExPrimary
;

primaryRExpr
: nodeNameError                                               #Id
| '(' regExpr ')'                                             #RegExParen
| '_'                                                         #Any
;

filter
: 'node' | 'NODE'                                #Node
| 'port' | 'PORT'                                #Port
| 'in-port' | 'IN-PORT'                          #In
| 'out-port' | 'OUT-PORT'                        #Out
| 'error' | 'ERROR'                              #Error
| 'porterror' | 'PORTERROR'                      #PortError
| 'flows' | 'FLOWS'                              #Flows
| 'flow-source' | 'FLOW-SOURCE'                  #Source
| 'flow-sink' | 'FLOW-SINK'                      #Sink
| 'flow-path' | 'FLOW-PATH'                      #Path
;

pexpr
: nodeNameError                                  #NodeN
| '(' expr ')'                                   #Paren
| '{' nodeNameError(','  nodeNameError)+ '}'     #NodeSet
| '*'                                            #Empty
| '\''id=ID                                      #QueryRes
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

