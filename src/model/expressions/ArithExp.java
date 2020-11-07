package model.expressions;

import model.collections.MyIDictionary;
import model.exceptions.DivisionByZeroException;
import model.exceptions.ExpressionEvaluationException;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class ArithExp implements IExp {
    private final IExp e1;
    private final IExp e2;
    private final Operation op;

    public enum Operation {
        add(Integer::sum, "+"),
        sub((Integer a, Integer b) -> a - b, "-"),
        mul((Integer a, Integer b) -> a * b, "*"),
        div((Integer a, Integer b) -> a / b, "/");

        private final BiFunction<Integer, Integer, Integer> operation;
        private final String symbol;

        Operation(BiFunction<Integer, Integer, Integer> operation, String symbol) {
            this.operation = operation;
            this.symbol = symbol;
        }

        IntValue apply(IntValue a, IntValue b) throws DivisionByZeroException {
            if (this.equals(div) && b.getVal() == 0)
                throw new DivisionByZeroException("Division by zero");
            return new IntValue(operation.apply(a.getVal(), b.getVal()));
        }

        @Override
        public String toString() {
            return symbol;
        }
    }

    public IExp getE1() {
        return e1;
    }

    public IExp getE2() {
        return e2;
    }

    public ArithExp(Operation op, IExp e1, IExp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public IValue eval(@NotNull MyIDictionary<String, IValue> tbl) throws ExpressionEvaluationException {
        if (e1 == null || e2 == null)
            throw new ExpressionEvaluationException("Null operand");

        IValue v1, v2;
        v1 = e1.eval(tbl);
        if (!v1.getType().equals(new IntType()))
            throw new ExpressionEvaluationException("first operand is not an integer");

        v2 = e2.eval(tbl);
        if (!v2.getType().equals(new IntType()))
            throw new ExpressionEvaluationException("second operand is not an integer");

        IntValue i1 = (IntValue) v1;
        IntValue i2 = (IntValue) v2;
        return op.apply(i1, i2);
    }

    @Override
    public IExp copy() {
        return new ArithExp(
                op,
                e1 != null ? e1.copy() : null,
                e2 != null ? e2.copy() : null
        );
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }
}
