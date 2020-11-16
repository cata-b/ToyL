package model.values;

import model.types.IType;
import model.types.RefType;
import org.jetbrains.annotations.NotNull;

public class RefValue implements IValue {
    private final int address;
    private final IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public IValue copy() {
        return new RefValue(address, locationType);
    }

    public int getAddress() {
        return address;
    }

    public IType getLocationType() {
        return locationType;
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof RefValue && ((RefValue)another).address == address;
    }

    @Override
    public String toString() {
        return address + "," + locationType;
    }

}
