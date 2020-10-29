package model.expressions;

import model.collections.MyIDictionary;
import model.exceptions.EmptyCollectionException;
import model.exceptions.ExpressionEvaluationException;
import model.exceptions.InvalidParameterException;
import model.values.IValue;

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
    public IValue eval(MyIDictionary<String, IValue> tbl) throws ExpressionEvaluationException {
        if (tbl == null)
            throw new ExpressionEvaluationException("Null symbol table");
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
