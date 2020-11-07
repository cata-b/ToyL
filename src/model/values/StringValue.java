package model.values;

import model.types.IType;
import model.types.IntType;
import model.types.StringType;

public class StringValue implements IValue {
    private final String val;

    public StringValue(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    @Override
    public String toString() {
        return "\"" + val + "\"";
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue copy() {
        return new StringValue(val);
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof StringValue && ((StringValue) another).val.equals(val);
    }

    @Override
    public int hashCode() {
        return val.hashCode();
    }
}
