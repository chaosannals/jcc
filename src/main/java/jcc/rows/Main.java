package jcc.rows;

import java.io.*;
import org.antlr.v4.runtime.*;
import jcc.grammar.*;

class Main {
    public static void main(String[] arguments) throws IOException {
        String code = "";
        InputStream stream = new ByteArrayInputStream(code.getBytes());
        CharStream input = CharStreams.fromStream(stream);
        RowsLexer lexer = new RowsLexer(input);
        int col = Integer.valueOf("123");
        RowsParser parser = new RowsParser(new CommonTokenStream(lexer), col);
        parser.file();
    }
}