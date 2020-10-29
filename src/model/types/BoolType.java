package model.types;

import model.values.BoolValue;
import model.values.IValue;

public class BoolType implements IType {
    private static BoolType instance = new BoolType();

    @Override
    public boolean equals(Object another) {
        return (another instanceof BoolType);
    }

    public String toString() {
        return "bool";
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }
}
