lexer grammar OperationLexer;

MUL: '*';
DIV: '/';
ADD: '+';
SUB: '-';

ID: [a-zA-Z]+;
INT: [0-9]+;
NL: '\r'? '\n';
WS: [ \t] -> skip;