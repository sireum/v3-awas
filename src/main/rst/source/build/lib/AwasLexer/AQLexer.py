from pygments.lexer import RegexLexer
from pygments.token import *

class AwasLexer(RegexLexer):
    name = 'Awas'
    aliases = ['Awas']
    filenames = ['*.aq']
    
    tokens = {
        'root': [
            (r' .*\n', Text),
            (r'//.*?$', Comment.Singleline),
            (words(('reach' , 'backward' , 'forward' , 'from' , 'to' , 'paths' , 'simple' , 'refined' , 'union' , 'intersect' , 'some' , 'all' , 'none' , 'with' ,'node' , 'port' , 'port-error' , 'flow' , 'flow-source' ,'flow-sink' , 'flow-path' , 'port-in' , 'port-out' , 'error'), suffix=r'\b'), Name.Builtin),
            (r'.*\n', Text),
        ]
    }


        
