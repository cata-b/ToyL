package model.values;

import model.types.IType;
import model.types.IntType;

public class IntValue implements IValue {
    private final int val;

    public IntValue(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    @Override
    public String toString() {
        return val + "";
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public IValue copy() {
        return new IntValue(val);
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof IntValue && ((IntValue) another).val == val;
    }

}
