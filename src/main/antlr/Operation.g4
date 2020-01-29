grammar Operation;

MUL: '*';
DIV: '/';
ADD: '+';
SUB: '-';
EQUAL: '=';
PL: '(';
PR: ')';
ID: [a-zA-Z]+;
INT: [0-9]+;
NL: '\r'? '\n';
WS: [ \t] -> skip;

program: statement+;

statement
    : expression NL? # printExpress
    | ID EQUAL expression NL? # assign
    | NL? # blank
    ;

expression
    : expression op=(MUL|DIV) expression # mulDiv
    | expression op=(ADD|SUB) expression # addSub
    | INT # integer
    | ID # identifer
    | PL expression PR # parens
    ;