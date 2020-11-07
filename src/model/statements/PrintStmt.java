package model.statements;

import model.PrgState;
import model.exceptions.StatementExecutionException;
import model.expressions.IExp;
import org.jetbrains.annotations.NotNull;

public class PrintStmt implements IStmt {
    private final IExp exp;

    public PrintStmt(@NotNull IExp exp) {
        this.exp = exp;
    }

    public IExp getExp() {
        return exp;
    }

    @Override
    public PrgState execute(@NotNull PrgState state) throws StatementExecutionException {
        state.getOut().add(exp.eval(state.getSymTable()));
        return state;
    }

    @Override
    public IStmt copy() {
        return new PrintStmt(exp.copy());
    }

    @Override
    public String toString() {
        return "print(" + exp.toString() + ")";
    }

}
