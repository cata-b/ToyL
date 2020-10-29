package model.statements;

import model.PrgState;
import model.collections.MyIStack;
import model.exceptions.EmptyCollectionException;
import model.exceptions.InvalidTypeException;
import model.exceptions.MyException;
import model.exceptions.StatementExecutionException;
import model.expressions.IExp;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;

public class IfStmt implements IStmt {
    private IExp exp;
    private IStmt thenS, elseS;

    public IfStmt(IExp exp, IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    public IExp getExp() {
        return exp;
    }

    public void setExp(IExp exp) {
        this.exp = exp;
    }

    public IStmt getThenS() {
        return thenS;
    }

    public void setThenS(IStmt thenS) {
        this.thenS = thenS;
    }

    public IStmt getElseS() {
        return elseS;
    }

    public void setElseS(IStmt elseS) {
        this.elseS = elseS;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (exp == null)
            throw new StatementExecutionException("Expression was null");
        if (thenS == null)
            throw new StatementExecutionException("Then branch was null");
        IValue cond = exp.eval(state.getSymTable());
        if (!cond.getType().equals(new BoolType()))
            throw new InvalidTypeException("Conditional expression does not evaluate to a boolean");
        MyIStack<IStmt> stk = state.getExeStack();
        if (stk == null)
            throw new StatementExecutionException("Program execution stack was null");
        if (((BoolValue)cond).getVal())
            stk.push(thenS);
        else stk.push(elseS);
        return state;
    }

    @Override
    public IStmt copy() {
        return null;
    }
    @Override
    public String toString(){
        return "(If (" + exp.toString() + ") Then(" + thenS.toString() + ") Else(" + elseS.toString() + "))";
    }
}
