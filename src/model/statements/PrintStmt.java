package model.statements;

import model.PrgState;
import model.collections.MyIList;
import model.exceptions.MyException;
import model.exceptions.StatementExecutionException;
import model.expressions.IExp;
import model.values.IValue;

public class PrintStmt implements IStmt {
    private IExp exp;

    public PrintStmt(IExp exp) {
        this.exp = exp;
    }

    public IExp getExp() {
        return exp;
    }

    public void setExp(IExp exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (state == null)
            throw new StatementExecutionException("Program state was null");
        if (exp == null)
            throw new StatementExecutionException("Given expression was null");
        MyIList<IValue> out = state.getOut();
        if (out == null)
            throw new StatementExecutionException("Program out list was null");
        out.add(exp.eval(state.getSymTable()));
        return state;
    }

    @Override
    public IStmt copy() {
        return new PrintStmt(exp != null ? exp.copy() : null);
    }

    @Override
    public String toString() {
        return "Print(" + exp.toString() + ")";
    }

}
