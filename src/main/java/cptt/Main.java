package cptt;
import java.io.*;
import cptt.lexer.*;
import cptt.parser.*;

public class Main {

    public static void main(String[] args) throws IOException{
        System.out.println("Compiler Start");
        Lexer lex = new Lexer();
        Parser parser = new Parser(lex);
        parser.program();
        System.out.write('\n');
        System.out.println("Compiler End");
    }
}
