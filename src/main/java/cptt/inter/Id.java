package cptt.inter;

import cptt.lexer.*;
import cptt.symbols.*;

public class Id extends Expr {
    public int offset;

    public Id(Word id, Type p, int b) {
        super(id, p);
        offset = b;
    }
}
