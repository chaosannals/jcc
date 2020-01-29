package jcc;

import java.io.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import jcc.grammar.*;

public class App {

    public static void main(String[] args) throws IOException {
        System.out.println("Start Operation");
        InputStream code = new ByteArrayInputStream("123 + 456".getBytes());
        CharStream input = CharStreams.fromStream(code);
        OperationLexer lexer = new OperationLexer(input);
        OperationParser parser = new OperationParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.program();
        System.out.println(tree.toString());
    }
}
