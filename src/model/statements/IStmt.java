package model.statements;

import model.PrgState;
import model.collections.interfaces.MyIDictionary;
import model.exceptions.MyException;
import model.exceptions.TypeCheckException;
import model.types.IType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface IStmt {
    static IStmt connectStmts(List<IStmt> iStmts) {
        if (iStmts.size() == 0)
            return new NopStmt();
        if (iStmts.size() == 1)
            return iStmts.get(0);
        var rv = new ArrayList<>(iStmts);
        Collections.reverse(rv);
        return rv.stream().skip(2)
                .reduce(new CompStmt(iStmts.get(iStmts.size() - 2), iStmts.get(iStmts.size() - 1)),
                        (a, b) -> new CompStmt(b, a));
    }
    /**
     * Executes the statement
     * @param state the program state to modify
     * @return a new program state if a new thread was created, or null otherwise
     * @throws MyException for various reasons, depending on the statement
     */
    PrgState execute(@NotNull PrgState state) throws MyException;
    @NotNull MyIDictionary<String, IType> typeCheck(@NotNull MyIDictionary<String, IType> typeEnvironment) throws TypeCheckException;
    IStmt copy();
}
