package model.expressions;

import model.collections.interfaces.MyIDictionary;
import model.collections.interfaces.MyIHeap;
import model.exceptions.ExpressionEvaluationException;
import model.exceptions.InvalidTypeException;
import model.exceptions.TypeCheckException;
import model.types.BoolType;
import model.types.IType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class RelationalExp implements IExp {
    private final @NotNull IExp left;
    private final @NotNull IExp right;
    private final @NotNull Operation op;

    public @NotNull Operation getOp() {
        return op;
    }

    public enum Operation {
        /** Smaller, < */
        sm((Integer a, Integer b) -> a < b, "<"),
        /** Smaller or equal, <= */
        smeq((Integer a, Integer b) -> a <= b, "<="),
        /** Equal, == */
        eq(Integer::equals, "=="),
        /** Not equal, != */
        neq((Integer a, Integer b) -> !a.equals(b), "!="),
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

    public RelationalExp(@NotNull IExp left, @NotNull IExp right, @NotNull Operation op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public IValue eval(@NotNull MyIDictionary<String, IValue> tbl, @NotNull MyIHeap heap) throws ExpressionEvaluationException {
        var leftVal = left.eval(tbl, heap);
        if (!leftVal.getType().equals(new IntType()))
            throw new InvalidTypeException("Relational expression operand has other type than " + new IntType() + ": " + leftVal.getType());

        var rightVal = right.eval(tbl, heap);
        if (!rightVal.getType().equals(new IntType()))
            throw new InvalidTypeException("Relational expression operand has other type than " + new IntType() + ": " + rightVal.getType());

        return op.apply((IntValue) leftVal, (IntValue) rightVal);
    }

    @Override
    public IExp copy() {
        return new RelationalExp(left.copy(), right.copy(), op);
    }

    @Override
    public IType typeCheck(@NotNull MyIDictionary<String, IType> typeEnvironment) throws TypeCheckException {
        var type1 = left.typeCheck(typeEnvironment);
        if (!type1.equals(new IntType()))
            throw new TypeCheckException("First operand of relational expression is not an integer");
        var type2 = right.typeCheck(typeEnvironment);
        if (!type2.equals(new IntType()))
            throw new TypeCheckException("Second operand of relational expression is not an integer");
        return new BoolType();
    }

    @Override
    public String toString() {
        return left + " " + op + " " + right;
    }

    public @NotNull IExp getLeft() {
        return left;
    }

    public @NotNull IExp getRight() {
        return right;
    }
}
