package model.expressions;

import model.collections.MyIDictionary;
import model.exceptions.ExpressionEvaluationException;
import model.values.IValue;
import org.jetbrains.annotations.NotNull;

public interface IExp {
    IValue eval(@NotNull MyIDictionary<String, IValue> tbl) throws ExpressionEvaluationException;
    IExp copy();
}
