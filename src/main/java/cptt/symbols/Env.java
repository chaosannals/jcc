package cptt.symbols;

import java.util.*;
import cptt.lexer.*;
import cptt.inter.*;

public class Env {
    private HashMap<Token, Id> table;
    protected Env prev;

    public Env(Env n) {
        table = new HashMap<>();
        prev = n;
    }

    public void put(Token w, Id i) {
        table.put(w, i);
    }

    public Id get(Token w) {
        for (Env e = this; e != null; e = e.prev) {
            Id found = (Id) (e.table.get(w));
            if (found != null)
                return found;
        }
        return null;
    }
}
