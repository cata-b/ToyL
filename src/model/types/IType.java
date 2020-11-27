package model.types;

import model.values.IValue;

public interface IType {
    /**
     * @return the default value for the current type instance wrapped in a corresponding IValue
     */
    IValue defaultValue();
}
