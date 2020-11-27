package model.expressions;

import model.collections.MyIDictionary;
import model.collections.MyIHeap;
import model.exceptions.DivisionByZeroException;
import model.exceptions.ExpressionEvaluationException;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class ArithExp implements IExp {
    private final @NotNull IExp e1;
    private final @NotNull IExp e2;
    private final @NotNull Operation op;

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

    public @NotNull IExp getE1() {
        return e1;
    }

    public @NotNull IExp getE2() {
        return e2;
    }

    public ArithExp(@NotNull Operation op, @NotNull IExp e1, @NotNull IExp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public IValue eval(@NotNull MyIDictionary<String, IValue> tbl, @NotNull MyIHeap heap) throws ExpressionEvaluationException {
        IValue v1, v2;
        v1 = e1.eval(tbl, heap);
        if (!v1.getType().equals(new IntType()))
            throw new ExpressionEvaluationException("first operand is not an integer");

        v2 = e2.eval(tbl, heap);
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
                e1.copy(),
                e2.copy()
        );
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }
}
