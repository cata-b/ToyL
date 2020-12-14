package model.collections.interfaces;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface MyIDictionary<K, V> {

    /**
     * Removes all the entries from the dictionary
     */
    void clear();

    /**
     * @param value the value to look for
     * @return True if the value is a value in the dictionary
     */
    boolean contains(@NotNull V value);

    /**
     * @param key The key to look for
     * @return True if the key is in the dictionary
     */
    boolean containsKey(@NotNull K key);

    /**
     * @param key the key to get the value associated with
     * @return the value associated with the key, or null if there is no value mapped to the key
     */
    V get(@NotNull K key);

    /**
     * @return True if there are no entries in the dictionary
     */
    boolean isEmpty();

    /**
     * Adds a new entry in the dictionary
     * @param key the key part of the new entry
     * @param value the value part of the new entry
     * @return the old value, if a value was already mapped to this key
     */
    V put (@NotNull K key, @NotNull V value);

    /**
     * Removes an entry from the dictionary if present
     * @param key the key of the entry to remove
     * @return the value previously associated with the key or null if it didn't exist
     */
    V remove(@NotNull K key);

    /**
     * @return the number of entries in the dictionary
     */
    int size();

    /**
     * @return a Java Map with the same entries as this instance
     */
    Map<K, V> getContent();

    /**
     * Sets the entire content of the dictionary
     * @param content the content to set
     * @return the old content
     */
    Map<K, V> setContent(Map<K, V> content);

    MyIDictionary<K, V> shallowCopy();
}
