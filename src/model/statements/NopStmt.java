package model.statements;

import model.PrgState;
import model.exceptions.MyException;

public class NopStmt implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return state;
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
