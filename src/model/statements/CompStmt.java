package model.statements;

import model.PrgState;
import model.collections.interfaces.MyIDictionary;
import model.collections.interfaces.MyIStack;
import model.exceptions.TypeCheckException;
import model.types.IType;
import org.jetbrains.annotations.NotNull;

public class CompStmt implements IStmt {
    private final @NotNull IStmt first;
    private final @NotNull IStmt second;

    public CompStmt(@NotNull IStmt first, @NotNull IStmt second) {
        this.first = first;
        this.second = second;
    }

    public @NotNull IStmt getFirst() {
        return first;
    }

    public @NotNull IStmt getSecond() {
        return second;
    }

    @Override
    public PrgState execute(@NotNull PrgState state) {
        MyIStack<IStmt> stk = state.getExeStack();
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public @NotNull MyIDictionary<String, IType> typeCheck(@NotNull MyIDictionary<String, IType> typeEnvironment) throws TypeCheckException {
        return second.typeCheck(first.typeCheck(typeEnvironment));
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
