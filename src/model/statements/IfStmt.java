package model.statements;

import model.PrgState;
import model.collections.MyIStack;
import model.exceptions.InvalidTypeException;
import model.exceptions.StatementExecutionException;
import model.expressions.IExp;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.IValue;
import org.jetbrains.annotations.NotNull;

public class IfStmt implements IStmt {
    private final @NotNull IExp exp;
    private final @NotNull IStmt thenS;
    private final @NotNull IStmt elseS;

    public IfStmt(@NotNull IExp exp, @NotNull IStmt thenS, IStmt elseS) {
        this.exp = exp;
        this.thenS = thenS;
        this.elseS = elseS == null ? new NopStmt() : elseS;
    }

    public @NotNull IExp getExp() {
        return exp;
    }

    public @NotNull IStmt getThenS() {
        return thenS;
    }

    public @NotNull IStmt getElseS() {
        return elseS;
    }

    @Override
    public PrgState execute(@NotNull PrgState state) throws StatementExecutionException {
        IValue cond = exp.eval(state.getSymTable(), state.getHeap());
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
