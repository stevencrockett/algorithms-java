package datastructures.map;

import java.util.Optional;

/**
 * A map (or symbol table, dictionary etc.), allowing keys to be associated with
 * values, such that a value can be obtained given a key.
 *
 * @param <Key> Type of the keys stored in the map which can be compared and ordered.
 * @param <Value> Type of the values stored in the map.
 */
public interface Map<Key, Value> {

    /**
     * Associate the given key with the given value.
     *
     * @param key Key to associate with the given value.
     * @param value Value that the key should map to.
     */
    public void put(Key key, Value value);


    /**
     * Get the value associated with the given key.
     *
     * @param key Key to find in the map.
     * @return Optional containing the value associated with the key if the key is contained in the map.
     */
    public Optional<Value> get(Key key);


    /**
     * Remove the key and its associated value from the map.
     *
     * @param key Key to find in the map.
     */
    public void delete(Key key);


    /**
     * Check if the given key is contained in the map (i.e. it has an associated value).
     *
     * @param key Key to find in the map.
     * @return <CODE>true</CODE> if the key is present; <CODE>false</CODE> otherwise.
     */
    public boolean contains(Key key);


    /**
     * Counts the number of key-value pairs stored in the map.
     *
     * @return The number of key-value pairs in the map.
     */
    public int size();

}
