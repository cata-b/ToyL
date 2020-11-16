package model.statements;

import model.PrgState;
import model.collections.*;
import model.exceptions.InvalidTypeException;
import model.exceptions.MyException;
import model.exceptions.StatementExecutionException;
import model.expressions.IExp;
import model.values.IValue;
import model.types.*;
import org.jetbrains.annotations.NotNull;

public class AssignStmt implements IStmt {
    private final @NotNull String id;
    private final @NotNull IExp exp;

    public AssignStmt(@NotNull String id, @NotNull IExp exp) {
        this.id = id;
        this.exp = exp;
    }

    public @NotNull String getId() {
        return id;
    }

    public @NotNull IExp getExp() {
        return exp;
    }

    @Override
    public PrgState execute(@NotNull PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTbl = state.getSymTable();

        if (!symTbl.containsKey(id))
            throw new StatementExecutionException("the used variable" + id + " was not declared before");

        IValue val = exp.eval(symTbl, state.getHeap());
        IType typId = (symTbl.get(id)).getType();

        if (!(val.getType()).equals(typId))
            throw new InvalidTypeException("Declared type of variable " + id + " and type of the assigned expression do not match");

        symTbl.put(id, val);
        return state;
    }

    @Override
    public IStmt copy() {
        return new AssignStmt(id, exp.copy());
    }

    @Override
    public String toString() {
        return id + " = " + exp.toString();
    }
}
