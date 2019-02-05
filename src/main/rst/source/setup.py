from setuptools import setup
 
__author__ = 'thari@ksu.edu'
 
setup(
    name='Awas Query Pygments Lexer',
    version='0.0.1',
    description=__doc__,
    author=__author__,
    packages=['AwasLexer'],
    entry_points='''[pygments.lexers]
awaslexer = AwasLexer:AwasLexer
'''
)
