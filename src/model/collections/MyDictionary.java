package model.collections;

import model.exceptions.InvalidParameterException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private HashMap<K, V> hashmap = new HashMap<>();

    @Override
    public synchronized void clear() {
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
    public synchronized V put(K key, V value) throws InvalidParameterException {
        if (key == null || value == null)
            throw new InvalidParameterException("Given key/value was null");
        return hashmap.put(key, value);
    }

    @Override
    public synchronized V remove(K key) throws InvalidParameterException {
        if (key == null)
            throw new InvalidParameterException("Given key was null");
        return hashmap.remove(key);
    }

    @Override
    public int size() {
        return hashmap.size();
    }

    @Override
    public Map<K, V> getContent() {
        return hashmap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public synchronized Map<K, V> setContent(Map<K, V> content) {
        var old_map = hashmap;
        hashmap = new HashMap<>();
        hashmap.putAll(content);
        return old_map;
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
