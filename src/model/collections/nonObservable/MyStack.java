package model.collections.nonObservable;

import model.collections.interfaces.MyIStack;
import model.exceptions.EmptyCollectionException;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MyStack<T> implements MyIStack<T> {
    private final Deque<T> stack = new LinkedList<>();

    private void checkStackEmpty() throws EmptyCollectionException {
        if (stack.isEmpty())
            throw new EmptyCollectionException("Stack is empty");
    }

    @Override
    public void push(T element) {
        stack.push(element);
    }

    @Override
    public T pop() throws EmptyCollectionException {
        checkStackEmpty();
        return stack.pop();
    }

    @Override
    public T peek() throws EmptyCollectionException {
        checkStackEmpty();
        return stack.peek();
    }

    @Override
    public boolean empty() {
        return stack.isEmpty();
    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public List<T> getContent() {
        return new ArrayList<>(stack);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        stack.forEach(item -> result
                .append(item.toString())
                .append(System.lineSeparator()));
        return result.substring(0, Math.max(result.length() - 1, 0));
    }
}
