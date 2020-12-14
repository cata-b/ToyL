package model.statements;

import model.PrgState;
import model.collections.interfaces.MyIDictionary;
import model.types.IType;
import org.jetbrains.annotations.NotNull;

public class NopStmt implements IStmt {

    @Override
    public PrgState execute(@NotNull PrgState state) {
        return null;
    }

    @Override
    public @NotNull MyIDictionary<String, IType> typeCheck(@NotNull MyIDictionary<String, IType> typeEnvironment) {
        return typeEnvironment;
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
