package model.collections;

import model.exceptions.EmptyCollectionException;
import model.exceptions.IndexOutOfBoundsException;

public interface MyIList<T> {
    void add(T element);
    void add(int index, T element) throws IndexOutOfBoundsException;
    T remove(int index) throws IndexOutOfBoundsException;
    boolean remove(T element);
    T get(int index) throws IndexOutOfBoundsException;
    boolean contains(T element);
    boolean isEmpty();
    int indexOf(T element);
    int lastIndexOf(T element);
    int size();
    T set(int index, T element) throws IndexOutOfBoundsException;
    void clear();
}
