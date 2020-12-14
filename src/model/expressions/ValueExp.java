package model.expressions;

import model.collections.interfaces.MyIDictionary;
import model.collections.interfaces.MyIHeap;
import model.types.IType;
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
    public IType typeCheck(@NotNull MyIDictionary<String, IType> typeEnvironment) {
        return val.getType();
    }

    @Override
    public String toString() {
        return val.toString();
    }
}
