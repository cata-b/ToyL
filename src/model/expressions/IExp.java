package model.expressions;

import model.collections.MyIDictionary;
import model.exceptions.ExpressionEvaluationException;
import model.values.IValue;

public interface IExp {
    IValue eval(MyIDictionary<String, IValue> tbl) throws ExpressionEvaluationException;
    IExp copy();
}
