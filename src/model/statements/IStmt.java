package model.statements;

import model.PrgState;
import model.exceptions.MyException;
import org.jetbrains.annotations.NotNull;

public interface IStmt {
    /**
     * Executes the statement
     * @param state the program state to modify
     * @return a new program state if a new thread was created, or null otherwise
     * @throws MyException for various reasons, depending on the statement
     */
    PrgState execute(@NotNull PrgState state) throws MyException;
    IStmt copy();
}
