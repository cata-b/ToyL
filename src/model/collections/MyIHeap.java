package model.collections;

import java.util.Map;

public interface MyIHeap<K, V> extends MyIDictionary<K, V> {
    /**
     * @return a guaranteed original new address for adding a value to the heap
     */
    int getNewAddress();
}
