package model.statements;

import model.PrgState;
import model.collections.MyIStack;
import model.exceptions.InvalidTypeException;
import model.exceptions.MyException;
import model.exceptions.StatementExecutionException;
import model.expressions.IExp;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;
import org.jetbrains.annotations.NotNull;

public class IfStmt implements IStmt {
    private final IExp exp;
    private final IStmt thenS;
    private final IStmt elseS;

    public IfStmt(@NotNull IExp exp, @NotNull IStmt thenS, @NotNull IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS;
    }

    public IExp getExp() {
        return exp;
    }

    public IStmt getThenS() {
        return thenS;
    }

    public IStmt getElseS() {
        return elseS;
    }

    @Override
    public PrgState execute(@NotNull PrgState state) throws StatementExecutionException {
        IValue cond = exp.eval(state.getSymTable());
        if (!cond.getType().equals(new BoolType()))
            throw new InvalidTypeException("Conditional expression does not evaluate to a boolean");
        MyIStack<IStmt> stk = state.getExeStack();
        if (((BoolValue)cond).getVal())
            stk.push(thenS);
        else stk.push(elseS);
        return state;
    }

    @Override
    public IStmt copy() {
        return new IfStmt(exp.copy(), thenS.copy(), elseS.copy());
    }
    @Override
    public String toString(){
        return "(if (" + exp.toString() + ") then(" + thenS.toString() + ") else(" + elseS.toString() + "))";
    }
}
