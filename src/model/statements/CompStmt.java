package model.statements;

import model.PrgState;
import model.collections.MyIStack;
import model.exceptions.MyException;
import model.exceptions.StatementExecutionException;
import org.jetbrains.annotations.NotNull;

public class CompStmt implements IStmt {
    private final IStmt first;
    private final IStmt second;

    public CompStmt(@NotNull IStmt first, @NotNull IStmt second) {
        this.first = first;
        this.second = second;
    }

    public IStmt getFirst() {
        return first;
    }

    public IStmt getSecond() {
        return second;
    }

    @Override
    public PrgState execute(@NotNull PrgState state) {
        MyIStack<IStmt> stk = state.getExeStack();
        stk.push(second);
        stk.push(first);
        return state;
    }

    @Override
    public IStmt copy() {
        return new CompStmt(
                 first.copy(),
                 second.copy()
        );
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }
}
