package model.collections.nonObservable;

import model.collections.interfaces.MyIHeap;
import model.values.IValue;

import java.util.concurrent.atomic.AtomicInteger;

public class MyHeap extends MyDictionary<Integer, IValue> implements MyIHeap {
    private static final AtomicInteger lastAddress = new AtomicInteger(0); // default RefValues point to 0

    @Override
    public int getNewAddress() {
        return lastAddress.addAndGet(1);
    }
}
