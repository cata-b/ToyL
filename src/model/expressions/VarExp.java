package model.expressions;

import model.collections.MyIDictionary;
import model.collections.MyIHeap;
import model.exceptions.ExpressionEvaluationException;
import model.exceptions.TypeCheckException;
import model.types.IType;
import model.values.IValue;
import org.jetbrains.annotations.NotNull;

public class VarExp implements IExp {
    private final @NotNull String id;

    public VarExp(@NotNull String id) {
        this.id = id;
    }

    public @NotNull String getId() {
        return id;
    }

    @Override
    public IValue eval(@NotNull MyIDictionary<String, IValue> tbl, @NotNull MyIHeap heap) {
        return tbl.get(id);
    }

    @Override
    public IExp copy() {
        return new VarExp(id);
    }

    @Override
    public IType typeCheck(@NotNull MyIDictionary<String, IType> typeEnvironment) {
        return typeEnvironment.get(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
