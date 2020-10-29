package model.expressions;

import model.collections.MyIDictionary;
import model.exceptions.ExpressionEvaluationException;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class LogicExp implements IExp {
    private IExp e1;
    private IExp e2;
    int op; // 1 - 'and', 2 - 'or'

    public LogicExp(IExp e1, IExp e2, int op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl) throws ExpressionEvaluationException {
        if (op < 1 || op > 2)
            throw new ExpressionEvaluationException("Invalid operator");
        IValue val1 = e1.eval(tbl);
        if (!val1.getType().equals(new BoolType()))
            throw new ExpressionEvaluationException("Invalid logic expression operand type: " + val1.getType());
        IValue val2 = e2.eval(tbl);
        if (!val2.getType().equals(new BoolType()))
            throw new ExpressionEvaluationException("Invalid logic expression operand type: " + val1.getType());
        if (op == 1) // and
            return new BoolValue(((BoolValue)val1).getVal() && ((BoolValue)val2).getVal());
        return new BoolValue(((BoolValue)val1).getVal() || ((BoolValue)val2).getVal()); // 2 - or
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
        return e1.toString() + (op == 1 ? " and " : " or ") + e2.toString();
    }

}
