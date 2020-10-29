package model.expressions;

import model.collections.MyIDictionary;
import model.values.IValue;

public class ValueExp implements IExp {
    private IValue val;

    public ValueExp(IValue val) {
        this.val = val;
    }

    public void setVal(IValue val) {
        this.val = val;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl) {
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
