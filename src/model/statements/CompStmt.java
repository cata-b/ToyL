package model.statements;

import model.PrgState;
import model.collections.MyIStack;
import model.exceptions.MyException;
import model.exceptions.StatementExecutionException;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    public IStmt getFirst() {
        return first;
    }

    public void setFirst(IStmt statement) {
        first = statement;
    }

    public IStmt getSecond() {
        return second;
    }

    public void setSecond(IStmt statement) {
        second = statement;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (state == null)
            throw new StatementExecutionException("Program state was null.");
        if (first == null || second == null)
            throw new StatementExecutionException("One of the statements was null.");
        MyIStack<IStmt> stk = state.getExeStack();
        if (stk == null)
            throw new StatementExecutionException("Program execution stack was null");
        stk.push(second);
        stk.push(first);
        return state;
    }

    @Override
    public IStmt copy() {
        return new CompStmt(
                first != null ? first.copy() : null,
                second != null ? second.copy() : null
        );
    }

    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }
}
