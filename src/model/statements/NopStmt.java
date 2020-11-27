package model.statements;

import model.PrgState;
import model.exceptions.MyException;
import org.jetbrains.annotations.NotNull;

public class NopStmt implements IStmt {

    @Override
    public PrgState execute(@NotNull PrgState state) throws MyException {
        return null;
    }

    @Override
    public IStmt copy() {
        return new NopStmt();
    }

    @Override
    public String toString() {
        return "nop";
    }
}
