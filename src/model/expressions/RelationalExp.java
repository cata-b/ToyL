package model.expressions;

import model.collections.MyIDictionary;
import model.exceptions.ExpressionEvaluationException;
import model.exceptions.InvalidTypeException;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class RelationalExp implements IExp {
    private final IExp left;
    private final IExp right;
    private final Operation op;

    public Operation getOp() {
        return op;
    }

    public enum Operation {
        /** Smaller, < */
        sm((Integer a, Integer b) -> a < b, "<"),
        /** Smaller or equal, <= */
        smeq((Integer a, Integer b) -> a <= b, "<="),
        /** Equal, == */
        eq((Integer a, Integer b) -> a == b, "=="),
        /** Not equal, != */
        neq((Integer a, Integer b) -> a != b, "!="),
        /** Greater, > */
        gt((Integer a, Integer b) -> a > b, ">"),
        /** Greater or equal, >= */
        gteq((Integer a, Integer b) -> a >= b, ">=");

        private final BiFunction<Integer, Integer, Boolean> operation;
        private final String symbol;

        Operation(BiFunction<Integer, Integer, Boolean> operation, String symbol) {
            this.operation = operation;
            this.symbol = symbol;
        }

        BoolValue apply(IntValue a, IntValue b) {
            return new BoolValue(operation.apply(a.getVal(), b.getVal()));
        }

        @Override
        public String toString() {
            return symbol;
        }
    }

    public RelationalExp(@NotNull IExp left, @NotNull IExp right, Operation op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public IValue eval(@NotNull MyIDictionary<String, IValue> tbl) throws ExpressionEvaluationException {
        var leftVal = left.eval(tbl);
        if (!leftVal.getType().equals(new IntType()))
            throw new InvalidTypeException("Relational expression operand has other type than " + new IntType() + ": " + leftVal.getType());

        var rightVal = right.eval(tbl);
        if (!rightVal.getType().equals(new IntType()))
            throw new InvalidTypeException("Relational expression operand has other type than " + new IntType() + ": " + rightVal.getType());

        return op.apply((IntValue) leftVal, (IntValue) rightVal);
    }

    @Override
    public IExp copy() {
        return new RelationalExp(left.copy(), right.copy(), op);
    }

    @Override
    public String toString() {
        return left + " " + op + " " + right;
    }

    public IExp getLeft() {
        return left;
    }

    public IExp getRight() {
        return right;
    }
}
