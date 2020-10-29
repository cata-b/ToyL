package model.collections;

import model.exceptions.EmptyCollectionException;
import model.exceptions.InvalidParameterException;

import java.util.Hashtable;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private final Hashtable<K, V> hashtable = new Hashtable<>();

    @Override
    public void clear() {
        hashtable.clear();
    }

    @Override
    public boolean contains(V value) throws InvalidParameterException {
        if (value == null)
            throw new InvalidParameterException("Given value was null");
        return hashtable.contains(value);
    }

    @Override
    public boolean containsKey(K key) throws InvalidParameterException {
        if (key == null)
            throw new InvalidParameterException("Given key was null");
        return hashtable.containsKey(key);
    }

    @Override
    public V get(K key) throws InvalidParameterException {
        if (key == null)
            throw new InvalidParameterException("Given key was null");
        return hashtable.get(key);
    }

    @Override
    public boolean isEmpty() {
        return hashtable.isEmpty();
    }

    @Override
    public V put(K key, V value) throws InvalidParameterException {
        if (key == null || value == null)
            throw new InvalidParameterException("Given key/value was null");
        return hashtable.put(key, value);
    }

    @Override
    public V remove(K key) throws InvalidParameterException {
        if (key == null)
            throw new InvalidParameterException("Given key was null");
        return hashtable.remove(key);
    }

    @Override
    public int size() {
        return hashtable.size();
    }

    @Override
    public String toString() {
        return hashtable.toString();
    }
}
