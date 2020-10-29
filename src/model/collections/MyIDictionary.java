package model.collections;

import model.exceptions.EmptyCollectionException;
import model.exceptions.InvalidParameterException;

public interface MyIDictionary<K, V> {
    void clear();
    boolean contains(V value) throws InvalidParameterException;
    boolean containsKey(K key) throws InvalidParameterException;
    V get(K key) throws InvalidParameterException;
    boolean isEmpty();
    V put (K key, V value) throws InvalidParameterException;
    V remove(K key) throws InvalidParameterException;
    int size();
}
