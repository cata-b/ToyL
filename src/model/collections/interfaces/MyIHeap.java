package model.collections.interfaces;

import model.values.IValue;

public interface MyIHeap extends MyIDictionary<Integer, IValue> {
    /**
     * @return a guaranteed original new address for adding a value to the heap
     */
    int getNewAddress();
}
