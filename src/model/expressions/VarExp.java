package model.expressions;

import model.collections.MyIDictionary;
import model.exceptions.ExpressionEvaluationException;
import model.exceptions.InvalidParameterException;
import model.values.IValue;
import org.jetbrains.annotations.NotNull;

public class VarExp implements IExp {
    private String id;

    public VarExp(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public IValue eval(@NotNull MyIDictionary<String, IValue> tbl) throws ExpressionEvaluationException {
        try {
            return tbl.get(id);
        } catch (InvalidParameterException e) {
            throw new ExpressionEvaluationException("Exception occurred when trying to get value for id" + id + ": " + e);
        }
    }

    @Override
    public IExp copy() {
        return new VarExp(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
