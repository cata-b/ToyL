package model.types;

import model.values.IValue;
import model.values.IntValue;

public class IntType implements IType {
    private static IntType instance = new IntType();

    @Override
    public boolean equals(Object another) {
        return (another instanceof IntType);
    }

    public String toString() {
        return "int";
    }

    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }
}
