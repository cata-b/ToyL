package model.statements;

import model.PrgState;
import model.collections.MyIDictionary;
import model.exceptions.EmptyCollectionException;
import model.exceptions.MyException;
import model.exceptions.StatementExecutionException;
import model.types.IType;
import model.values.IValue;

public class VarDeclStmt implements IStmt {
    private String id;
    private IType type;

    public VarDeclStmt(String id, IType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IType getType() {
        return type;
    }

    public void setType(IType type) {
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (id == null)
            throw new StatementExecutionException("Null id");
        if (type == null)
            throw new StatementExecutionException("Null type");
        if (state == null)
            throw new StatementExecutionException("Null program state");
        MyIDictionary<String, IValue> tbl = state.getSymTable();
        if (tbl == null)
            throw new StatementExecutionException("Null symbol table");
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