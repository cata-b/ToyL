package model.statements;

import model.PrgState;
import model.exceptions.MyException;
import org.jetbrains.annotations.NotNull;

public interface IStmt {
    PrgState execute(@NotNull PrgState state) throws MyException;
    IStmt copy();
}
