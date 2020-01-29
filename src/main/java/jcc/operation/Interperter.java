package jcc.operation;

import java.io.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import jcc.grammar.*;

/**
 * 
 */
class Interpreter {
    /**
     * @throws IOException
     * 
     */
    public Object interpret(String code) throws IOException {
        InputStream stream = new ByteArrayInputStream(code.getBytes());
        CharStream input = CharStreams.fromStream(stream);
        OperationLexer lexer = new OperationLexer(input);
        OperationParser parser = new OperationParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.program();
        Visitor visitor = new Visitor();
        return visitor.visit(tree);
    }

    /**
     * 
     * @param argments
     * @throws IOException
     */
    public static void main(String[] argments) throws IOException {
        Interpreter interperter = new Interpreter();
        interperter.interpret("123 + 110");
    }
}