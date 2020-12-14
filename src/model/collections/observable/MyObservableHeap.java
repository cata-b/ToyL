package model.collections.observable;

import model.collections.interfaces.MyIHeap;
import model.collections.nonObservable.MyHeap;
import model.values.IValue;

import java.util.concurrent.atomic.AtomicInteger;

public class MyObservableHeap extends MyObservableDictionary<Integer, IValue> implements MyIHeap {
    private static final AtomicInteger lastAddress = new AtomicInteger(0);
    @Override
    public int getNewAddress() {
        return lastAddress.addAndGet(1);
    }
}
