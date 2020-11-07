package model.expressions;

import model.collections.MyIDictionary;
import model.exceptions.ExpressionEvaluationException;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class LogicExp implements IExp {
    private final IExp e1;
    private final IExp e2;
    Operation op;

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

    public LogicExp(IExp e1, IExp e2, Operation op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public IValue eval(@NotNull MyIDictionary<String, IValue> tbl) throws ExpressionEvaluationException {
        IValue val1 = e1.eval(tbl);
        if (!val1.getType().equals(new BoolType()))
            throw new ExpressionEvaluationException("Invalid logic expression operand type: " + val1.getType());
        IValue val2 = e2.eval(tbl);
        if (!val2.getType().equals(new BoolType()))
            throw new ExpressionEvaluationException("Invalid logic expression operand type: " + val1.getType());
        return op.apply((BoolValue) val1, (BoolValue) val2);
    }

    @Override
    public IExp copy() {
        return new LogicExp(
                e1 != null ? e1.copy() : null,
                e2 != null ? e2.copy() : null,
                op
        );
    }

    @Override
    public String toString() {
        return e1.toString() + op + e2.toString();
    }

}
