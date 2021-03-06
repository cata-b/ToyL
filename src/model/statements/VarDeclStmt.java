package model.statements;

import model.PrgState;
import model.collections.interfaces.MyIDictionary;
import model.exceptions.MyException;
import model.exceptions.StatementExecutionException;
import model.exceptions.TypeCheckException;
import model.types.IType;
import model.values.IValue;
import org.jetbrains.annotations.NotNull;

public class VarDeclStmt implements IStmt {
    private final @NotNull String id;
    private final @NotNull IType type;

    public VarDeclStmt(@NotNull String id, @NotNull IType type) {
        this.id = id;
        this.type = type;
    }

    public @NotNull String getId() {
        return id;
    }

    public @NotNull IType getType() {
        return type;
    }

    @Override
    public PrgState execute(@NotNull PrgState state) throws MyException {
        MyIDictionary<String, IValue> tbl = state.getSymTable();
        if (tbl.containsKey(id))
            throw new StatementExecutionException("Variable already declared: " + id);
        tbl.put(id, type.defaultValue());
        return null;
    }

    @Override
    public @NotNull MyIDictionary<String, IType> typeCheck(@NotNull MyIDictionary<String, IType> typeEnvironment) throws TypeCheckException {
        typeEnvironment.put(id, type);
        return typeEnvironment;
    }

    @Override
    public IStmt copy() {
        return new VarDeclStmt(id, type);
    }

    @Override
    public String toString() {
        return type + " " + id;
    }

}