package model.values;

import model.types.IType;
import model.types.BoolType;

public class BoolValue implements IValue{
    private final boolean val;

    public BoolValue(boolean val) {
        this.val = val;
    }

    public boolean getVal() {
        return val;
    }

    @Override
    public String toString() {
        return val + "";
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public IValue copy() {
        return new BoolValue(val);
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof BoolValue && ((BoolValue) another).val == val;
    }
}
