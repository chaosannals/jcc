lexer grammar XMLLexer;

OPEN: '<' -> pushMode(INSIDE);
COMMENT: '<!--' .*? '-->' -> skip;
ENTITYREF : '&' [a-z]+ ';' ;
TEXT : ~('<'|'&')+;

mode INSIDE ;
CLOSE : '>' -> popModel;
SLASH_CLOSE : '/>' ->popModel;
EQUALS : '=' ;
STRING : '"' .*? '"';
SlashName : '/' Name ;
Name : ALPHA (ALPHA|DIGIT)* ;
S : [ \t\r\n] -> skip;

fragment
ALPHA : [a-zA-Z] ;

fragment
DIGIT : [0-9] ;