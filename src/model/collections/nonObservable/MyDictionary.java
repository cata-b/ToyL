package model.collections.nonObservable;

import model.collections.interfaces.MyIDictionary;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    private HashMap<K, V> hashmap = new HashMap<>();

    @Override
    public synchronized void clear() {
        hashmap.clear();
    }

    @Override
    public boolean contains(@NotNull V value) {
        return hashmap.containsValue(value);
    }

    @Override
    public boolean containsKey(@NotNull K key) {
        return hashmap.containsKey(key);
    }

    @Override
    public V get(@NotNull K key) {
        return hashmap.get(key);
    }

    @Override
    public boolean isEmpty() {
        return hashmap.isEmpty();
    }

    @Override
    public synchronized V put(@NotNull K key, @NotNull V value) {
        return hashmap.put(key, value);
    }

    @Override
    public synchronized V remove(@NotNull K key) {
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
    public MyIDictionary<K, V> shallowCopy() {
        MyDictionary<K, V> copy = new MyDictionary<>();
        copy.setContent(getContent());
        return copy;
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
