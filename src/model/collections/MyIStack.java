package model.collections;

import model.exceptions.EmptyCollectionException;

public interface MyIStack<T> {
    /**
     * Adds an element at the top of the stack
     * @param element the element to add
     */
    void push(T element);

    /**
     * Pops an element from the top of the stack
     * @return the removed element
     * @throws EmptyCollectionException if the stack is empty
     */
    T pop() throws EmptyCollectionException;

    /**
     * @return the element at the top of the stack, without removing it
     * @throws EmptyCollectionException if the stack is empty
     */
    T peek() throws EmptyCollectionException;

    /**
     * @return True if the stack is empty
     */
    boolean empty();

    /**
     * Empties the stack
     */
    void clear();
}
