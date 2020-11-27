package model.values;

import model.types.IType;

public interface IValue {
    /**
     * @return an IType instance that represents the type of the current value
     */
    IType getType();

    /**
     * @return a deep copy of the current value
     */
    IValue copy();
}
