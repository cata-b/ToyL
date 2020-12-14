package model.expressions;

import model.collections.interfaces.MyIDictionary;
import model.collections.interfaces.MyIHeap;
import model.exceptions.ExpressionEvaluationException;
import model.exceptions.TypeCheckException;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class LogicExp implements IExp {
    private final @NotNull IExp e1;
    private final @NotNull IExp e2;
    private final @NotNull Operation op;

    public enum Operation {
        and((Boolean a, Boolean b) -> a && b, "and"),
        or((Boolean a, Boolean b) -> a || b, "or");

        private final BiFunction<Boolean, Boolean, Boolean> operation;
        private final String symbol;

        Operation(BiFunction<Boolean, Boolean, Boolean> operation, String symbol) {
            this.operation = operation;
            this.symbol = symbol;
        }

        BoolValue apply(BoolValue a, BoolValue b) {
            return new BoolValue(operation.apply(a.getVal(), b.getVal()));
        }

        @Override
        public String toString() {
            return symbol;
        }
    }

    public LogicExp(@NotNull IExp e1, @NotNull IExp e2, @NotNull Operation op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public IValue eval(@NotNull MyIDictionary<String, IValue> tbl, @NotNull MyIHeap heap) throws ExpressionEvaluationException {
        IValue val1 = e1.eval(tbl, heap);
        if (!val1.getType().equals(new BoolType()))
            throw new ExpressionEvaluationException("Invalid logic expression operand type: " + val1.getType());
        IValue val2 = e2.eval(tbl, heap);
        if (!val2.getType().equals(new BoolType()))
            throw new ExpressionEvaluationException("Invalid logic expression operand type: " + val1.getType());
        return op.apply((BoolValue) val1, (BoolValue) val2);
    }

    @Override
    public IExp copy() {
        return new LogicExp(
                e1.copy(),
                e2.copy(),
                op
        );
    }

    @Override
    public IType typeCheck(@NotNull MyIDictionary<String, IType> typeEnvironment) throws TypeCheckException {
        var type1 = e1.typeCheck(typeEnvironment);
        if (!type1.equals(new BoolType()))
            throw new TypeCheckException("First operand of logic expression is not a boolean");
        var type2 = e2.typeCheck(typeEnvironment);
        if (!type2.equals(new BoolType()))
            throw new TypeCheckException("First operand of logic expression is not a boolean");
        return new BoolType();
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }

}
