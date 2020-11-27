package model.collections;

import model.exceptions.EmptyCollectionException;
import model.exceptions.IndexOutOfBoundsException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    private ArrayList<T> list;

    private void validateIndex(int index, int max) throws IndexOutOfBoundsException {
        if (index < 0 || index >= max)
            throw new IndexOutOfBoundsException("Index out of bounds: " + index + ", (0, " + (list.size() - 1) + ").");
    }

    public MyList() {
        list = new ArrayList<>();
    }

    @Override
    public synchronized void add(T element) {
        list.add(element);
    }

    @Override
    public synchronized void add(int index, T element) throws IndexOutOfBoundsException {
        validateIndex(index, list.size() + 1);
        list.add(index, element);
    }

    @Override
    public synchronized T remove(int index) throws IndexOutOfBoundsException {
        validateIndex(index, list.size());
        return list.remove(index);
    }

    @Override
    public synchronized boolean remove(T element) {
        return list.remove(element);
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        validateIndex(index, list.size());
        return list.get(index);
    }

    @Override
    public boolean contains(T element) {
        return list.contains(element);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int indexOf(T element) {
        return list.indexOf(element);
    }

    @Override
    public int lastIndexOf(T element) {
        return list.lastIndexOf(element);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public synchronized T set(int index, T element) throws IndexOutOfBoundsException {
        validateIndex(index, list.size());
        return list.set(index, element);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public List<T> getContent() {
        return list;
    }

    @Override
    public void setContent(List<T> content) {
        list = new ArrayList<T>(content);
    }

    @Override
    public String toString() {
        var result = new StringBuilder();
        list.forEach(item -> result.append(item).append(System.lineSeparator()));
        return result.substring(0, Math.max(result.length() - 1, 0));
    }
}
