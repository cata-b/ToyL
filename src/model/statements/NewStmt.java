package model.statements;

import model.PrgState;
import model.exceptions.MyException;
import model.exceptions.StatementExecutionException;
import model.expressions.IExp;
import model.types.RefType;
import model.values.RefValue;
import org.jetbrains.annotations.NotNull;

public class NewStmt implements IStmt {
    private final @NotNull String id;
    private final @NotNull IExp value;

    public NewStmt(@NotNull String id, @NotNull IExp value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public PrgState execute(@NotNull PrgState state) throws MyException {
        var symTable = state.getSymTable();
        var heap = state.getHeap();

        if (!symTable.containsKey(id))
            throw new StatementExecutionException("Variable " + id + " is not declared");

        var refValue = symTable.get(id);
        if (!(refValue.getType() instanceof RefType))
            throw new StatementExecutionException("Variable " + id + " used in a new statement is not a reference type");
        var refValueConv = (RefValue)refValue;

        var expResult = value.eval(symTable, heap);
        if (!expResult.getType().equals(refValueConv.getLocationType()))
            throw new StatementExecutionException("Reference location type and expression type do not match");

        var address = heap.getNewAddress();
        heap.put(address, expResult);
        symTable.put(id, new RefValue(address, refValueConv.getLocationType()));
        return state;
    }

    @Override
    public IStmt copy() {
        return new NewStmt(id, value.copy());
    }

    @Override
    public String toString() {
        return "new(" + id + ", " + value + ")";
    }
}
