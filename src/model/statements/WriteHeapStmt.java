package model.statements;

import model.PrgState;
import model.exceptions.MyException;
import model.exceptions.StatementExecutionException;
import model.expressions.IExp;
import model.types.RefType;
import model.values.RefValue;
import org.jetbrains.annotations.NotNull;

public class WriteHeapStmt implements IStmt {
    private final @NotNull String id;
    private final @NotNull IExp expression;

    public WriteHeapStmt(@NotNull String id, @NotNull IExp expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public PrgState execute(@NotNull PrgState state) throws MyException {
        var symTable = state.getSymTable();
        var heap = state.getHeap();

        if (!symTable.containsKey(id))
            throw new StatementExecutionException("Variable " + id + " not declared");
        var value = symTable.get(id);
        if (!(value.getType() instanceof RefType))
            throw new StatementExecutionException("Type of " + id + " is not reference type");
        var valueConv = (RefValue)value;

        if (!heap.containsKey(valueConv.getAddress()))
            throw new StatementExecutionException("Address is not in the heap");

        var expResult = expression.eval(symTable, heap);
        if (!expResult.getType().equals(valueConv.getLocationType()))
            throw new StatementExecutionException("Result of the expression has a different type than the type referenced");
        heap.put(valueConv.getAddress(), expResult);
        return state;
    }

    @Override
    public IStmt copy() {
        return new WriteHeapStmt(id, expression.copy());
    }

    @Override
    public String toString() {
        return "wH(" + id + ", " + expression + ")";
    }
}
