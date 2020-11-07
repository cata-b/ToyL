package model.collections;

import model.exceptions.InvalidParameterException;

import java.util.HashMap;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private final HashMap<K, V> hashmap = new HashMap<>();

    @Override
    public void clear() {
        hashmap.clear();
    }

    @Override
    public boolean contains(V value) throws InvalidParameterException {
        if (value == null)
            throw new InvalidParameterException("Given value was null");
        return hashmap.containsValue(value);
    }

    @Override
    public boolean containsKey(K key) throws InvalidParameterException {
        if (key == null)
            throw new InvalidParameterException("Given key was null");
        return hashmap.containsKey(key);
    }

    @Override
    public V get(K key) throws InvalidParameterException {
        if (key == null)
            throw new InvalidParameterException("Given key was null");
        return hashmap.get(key);
    }

    @Override
    public boolean isEmpty() {
        return hashmap.isEmpty();
    }

    @Override
    public V put(K key, V value) throws InvalidParameterException {
        if (key == null || value == null)
            throw new InvalidParameterException("Given key/value was null");
        return hashmap.put(key, value);
    }

    @Override
    public V remove(K key) throws InvalidParameterException {
        if (key == null)
            throw new InvalidParameterException("Given key was null");
        return hashmap.remove(key);
    }

    @Override
    public int size() {
        return hashmap.size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        hashmap.forEach((key, value) -> result
                .append(key)
                .append(" --> ")
                .append(value)
                .append(System.lineSeparator()));
        return result.substring(0, Math.max(result.length() - 1, 0));
    }
}
