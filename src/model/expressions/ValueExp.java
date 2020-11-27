package model.expressions;

import model.collections.MyIDictionary;
import model.collections.MyIHeap;
import model.values.IValue;
import org.jetbrains.annotations.NotNull;

public class ValueExp implements IExp {
    private final @NotNull IValue val;

    public ValueExp(@NotNull IValue val) {
        this.val = val;
    }

    @Override
    public IValue eval(@NotNull MyIDictionary<String, IValue> tbl, @NotNull MyIHeap heap) {
        return val;
    }

    @Override
    public IExp copy() {
        return new ValueExp(val.copy());
    }

    @Override
    public String toString() {
        return val.toString();
    }
}
