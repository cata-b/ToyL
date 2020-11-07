package model.statements;

import model.PrgState;
import model.collections.MyIDictionary;
import model.exceptions.MyException;
import model.exceptions.StatementExecutionException;
import model.types.IType;
import model.values.IValue;
import org.jetbrains.annotations.NotNull;

public class VarDeclStmt implements IStmt {
    private final String id;
    private final IType type;

    public VarDeclStmt(@NotNull String id, @NotNull IType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public IType getType() {
        return type;
    }

    @Override
    public PrgState execute(@NotNull PrgState state) throws MyException {
        MyIDictionary<String, IValue> tbl = state.getSymTable();
        if (tbl.containsKey(id))
            throw new StatementExecutionException("Variable already declared: " + id);
        tbl.put(id, type.defaultValue());
        return state;
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