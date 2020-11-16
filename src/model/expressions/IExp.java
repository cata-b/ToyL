package model.expressions;

import model.collections.MyIDictionary;
import model.collections.MyIHeap;
import model.exceptions.ExpressionEvaluationException;
import model.values.IValue;
import org.jetbrains.annotations.NotNull;

public interface IExp {
    IValue eval(@NotNull MyIDictionary<String, IValue> tbl, @NotNull MyIHeap<Integer, IValue> heap) throws ExpressionEvaluationException;
    IExp copy();
}
