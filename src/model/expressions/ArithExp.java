package model.expressions;

import model.collections.MyIDictionary;
import model.exceptions.DivisionByZeroException;
import model.exceptions.ExpressionEvaluationException;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class ArithExp implements IExp {
    private IExp e1;
    private IExp e2;
    private int op; // 1 - '+', 2 - '-', 3 - '*', 4 - '/'


    public IExp getE1() {
        return e1;
    }

    public void setE1(IExp e1) {
        this.e1 = e1;
    }

    public IExp getE2() {
        return e2;
    }

    public void setE2(IExp e2) {
        this.e2 = e2;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    public ArithExp(int op, IExp e1, IExp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    public ArithExp(char op, IExp e1, IExp e2) {
        this.e1 = e1;
        this.e2 = e2;
        if (op == '+')
            this.op = 1;
        else if (op == '-')
            this.op = 2;
        else if (op == '*')
            this.op = 3;
        else if (op == '/')
            this.op = 4;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl) throws ExpressionEvaluationException {
        if (e1 == null || e2 == null)
            throw new ExpressionEvaluationException("Null operand");
        if (op <= 0 || op > 4)
            throw new ExpressionEvaluationException("Invalid operator");

        IValue v1, v2;
        v1 = e1.eval(tbl);
        if (!v1.getType().equals(new IntType()))
            throw new ExpressionEvaluationException("first operand is not an integer");

        v2 = e2.eval(tbl);
        if (!v2.getType().equals(new IntType()))
            throw new ExpressionEvaluationException("second operand is not an integer");

        IntValue i1 = (IntValue) v1;
        IntValue i2 = (IntValue) v2;
        int n1, n2;
        n1 = i1.getVal();
        n2 = i2.getVal();
        if (op == 1)
            return new IntValue(n1 + n2);
        if (op == 2)
            return new IntValue(n1 - n2);
        if (op == 3)
            return new IntValue(n1 * n2);
        if (op == 4) {
            if (n2 == 0)
                throw new DivisionByZeroException("division by zero");
            return new IntValue(n1 / n2);
        }
        return null;
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
        char operator = 0;
        if (op == 1)
            operator = '+';
        else if (op == 2)
            operator = '-';
        else if (op == 3)
            operator = '*';
        else if (op == 4)
            operator = '/';
        return e1.toString() + " " + operator + " " + e2.toString();
    }
}
