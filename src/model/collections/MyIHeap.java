package model.collections;

import model.values.IValue;

import java.util.Map;

public interface MyIHeap extends MyIDictionary<Integer, IValue> {
    /**
     * @return a guaranteed original new address for adding a value to the heap
     */
    int getNewAddress();
}
