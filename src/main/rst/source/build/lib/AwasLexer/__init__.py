import re
from pygments.lexer import RegexLexer, words, include, bygroups, default
from pygments.token import *

class AwasLexer(RegexLexer):
    name = 'Awas'
    aliases = ['Awas']
    filenames = ['*.aq']

    flags = re.MULTILINE | re.DOTALL
    
    iden = r'[a-zA-Z_][a-zA-Z0-9_\.]*'
    
    text_tuple = (r'[^\S\n]+', Text)
    tokens = {
        'root': [
            text_tuple,
            include('query')
        ],

        'idenList': [
            (r'(,) (\s*)', Punctuation,  'idenList'),
            (iden, Name.Class, 'idenList'),
            (iden, Name.Class),
            default('#pop')
        ],
        
        'nodeNameError': [
            (r'('+iden+r')({)', bygroups(Name.Class, Punctuation), ('nodeNameError','idenList')),
#            include('idenList'),
            (r'}', Punctuation, '#pop')
#            (iden, Name.Class)
            
        ],
            

        
        'reach_keywords':[
            (r'(forward) (\s*)', Keyword.Reserved, 'expr'),
            (r'(backward) (\s*)', Keyword.Reserved, 'expr' ),
            (r'from \s*', Keyword.Reserved, 'expr'),
            (r'(paths) (\s*)', Keyword.Reserved, 'reach_keywords'),
            (r'(simple) (\s*)', Operator.Word, 'reach_keywords'),
            (r'(refined) (\s*)', Operator.Word, 'reach_keywords')
            
       #     (r'(simple) (\s*) (paths) (\s*) (from)', bygroups(Keyword.Reserved, Keyword.Reserved, Keyword.Reserved), 'expr'),
       #     (r'(refined) (\s*) (simple) (\s*) (paths) (\s*) (from)', bygroups(Keyword.Reserved, Text, Keyword.Reserved, Text, Keyword.Reserved, Text, Keyword.Reserved, Text, Keyword.Reserved), 'expr')
         ],
        'reach': [
            (r'(reach) (\s*)',  Keyword.Reserved, 'reach_keywords') 
        ],
        'expr': [
#            (r'(reach) (\s*)', Keyword.Reserved, 'reach_keywords')
            include('reach'),
            include('nodeNameError'),
            (iden, Name.Class),
            (r'\(', Punctuation, 'expr'),
            (r'\)', Punctuation, ('#pop','expr')),
            (r'(\s*) (to) (\s*)', Keyword.Reserved, 'expr'),
            (r'(\s*) (union) (\s*)', Operator.Word, 'expr'),
            (r'(\s*) (intersect) (\s*)', Operator.Word, 'expr'),
            (r'(\s*) (-) (\s*)', Keyword.Reserved, 'expr'),
            (r'(:)', Keyword.Reserved, 'filter_keywords'),
            (r'(\s*)(with)(\s*)(?:(some)|(all)|(none))(\()', bygroups(Text, Keyword.Reserved, Text, Keyword.Reserved, Keyword.Reserved,Keyword.Reserved,Punctuation), 'expr')

        ],
        
        'filter_keywords' : [
            (r'node', Keyword.Reserved),
            (r'port\-error', Keyword.Reserved),
            (r'port', Keyword.Reserved),
            (r'in\-port', Keyword.Reserved),
            (r'out\-port', Keyword.Reserved),
            (r'error', Keyword.Reserved),
            (r'source', Keyword.Reserved),
            (r'sink', Keyword.Reserved),
            include('expr'),
            default('#pop')
        ],
    
        'query':[
#            (term_iden, Keyword.Reserved),
            (r'('+ iden + r')(\s+)(=)(\s+)', bygroups(Name.Variable,Text, Operator, Text), 'expr')
        ]


            

        # 'query': [
        #     (r'\s+ name \s+ = \s+', bygroups(Text, Name.Decorator, Text, Token.Operator, Text), 'expr') 
        # ],
        # 'expr': [
        #     (r'(\w\.)*\w', Name.Decorator),
        #     include('reach')
        # ],

        # 'name': [
        #     (r'((\w\.)*\w)', Name.Decorator)
        # ],

        
    }


        
