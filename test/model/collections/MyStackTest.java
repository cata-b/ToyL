package model.collections;

import model.collections.nonObservable.MyStack;

public class MyStackTest implements MyIStackTest<MyStack<Object>> {
    private MyStack<Object> stack;
    private Object dummyObj;

    @Override
    public MyStack<Object> getStack() {
        if (stack == null)
            stack = new MyStack<>();
        return stack;
    }

    @Override
    public Object getObject() {
        if (dummyObj == null)
            dummyObj = new Object();
        return dummyObj;
    }
}
