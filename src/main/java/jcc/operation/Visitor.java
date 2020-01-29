package jcc.operation;

import java.util.*;
import jcc.grammar.*;
import jcc.grammar.OperationParser.*;

/**
 * 
 */
public class Visitor extends OperationBaseVisitor<Integer> {
    private Map<String, Integer> variables = new HashMap<>();

    /**
     * 
     */
    @Override
    public Integer visitInteger(IntegerContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }

    @Override
    public Integer visitAssign(AssignContext ctx) {
        String id = ctx.ID().getText();
        int value = visit(ctx.expression());
        variables.put(id, value);
        return value;
    }

    @Override
    public Integer visitPrintExpress(PrintExpressContext ctx) {
        Integer value = visit(ctx.expression());
        System.out.println(value);
        return value;
    }

    @Override
    public Integer visitIdentifer(IdentiferContext ctx) {
        String id = ctx.ID().getText();
        if (variables.containsKey(id)) {
            return variables.get(id);
        }
        return 0;
    }

    @Override
    public Integer visitMulDiv(MulDivContext ctx) {
        int left = visit(ctx.expression(0));
        int right = visit(ctx.expression(1));
        if (ctx.op.getType() == OperationLexer.MUL) {
            return left * right;
        }
        return left / right;
    }

    @Override
    public Integer visitAddSub(AddSubContext ctx) {
        int left = visit(ctx.expression(0));
        int right = visit(ctx.expression(1));
        if (ctx.op.getType() == OperationLexer.ADD) {
            return left + right;
        }
        return left - right;
    }

    @Override
    public Integer visitParens(ParensContext ctx) {
        return visit(ctx.expression());
    }
}