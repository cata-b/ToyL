package model.statements;

import model.PrgState;
import model.exceptions.EmptyCollectionException;
import model.exceptions.MyException;
import model.exceptions.StatementExecutionException;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    IStmt copy();
}
