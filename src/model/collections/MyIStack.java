package model.collections;

import model.exceptions.EmptyCollectionException;

public interface MyIStack<T> {
    void push(T element);
    T pop() throws EmptyCollectionException;
    T peek() throws EmptyCollectionException;
    boolean empty();
}
