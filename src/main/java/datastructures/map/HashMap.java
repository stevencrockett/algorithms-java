package datastructures.map;

import java.util.Optional;

/**
 * Unordered map implemented as a hash table with linear probing.
 *
 * @param <Key> Type of the keys stored in the map which provide a hash code.
 * @param <Value> Type of the values stored in the map.
 */
public class HashMap<Key, Value> implements Map<Key, Value> {

    /**
     * Default capacity of the arrays if no initial size is specified.
     */
    private static final int DEFAULT_CAPACITY = 20;


    /**
     * Indicates how full the hash table is allowed to get before it gets resized.
     */
    private static final float MAX_LOAD_FACTOR = 0.8f;


    /**
     * Growth multiplier to calculate new array size when the array is full and needs to be grown.
     */
    private static final float GROWTH_RATE = 2.0f;


    /**
     * Array of keys accessed using a linear probing strategy. The key at index i will be associated
     * with the value at index i in the values array.
     */
    private Key[] keys;


    /**
     * Array of values parallel to the keys array. The key at index i in the keys array will be associated with the value at index i.
     */
    private Value[] values;


    /**
     * Current size of the underlying arrays for keys and values.
     */
    private int capacity;


    /**
     * Current number of key-value pairs in the hash table.
     */
    private int size = 0;


    /**
     * The maximum load allowed for the hash table before having to resize.
     * Based on the current size of the hash table and the max load factor.
     */
    private int currentMaxLoad;


    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public HashMap(final int initialCapacity) {
        keys = (Key[]) new Object[initialCapacity];
        values = (Value[]) new Object[initialCapacity];
        currentMaxLoad = (int) (initialCapacity * MAX_LOAD_FACTOR);
        capacity = initialCapacity;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void put(final Key key, final Value value) {

        // grow the map if we will exceed the maximum acceptable load
        if (size == currentMaxLoad) {
            grow();
        }

        int insertionPoint = hash(key) % capacity;
        Key currentKey = keys[insertionPoint];

        // follow items from insertion point until either:
        // 1. there is a free space to insert (i.e. we are adding a completely new key and value)
        // 2. the key is found (i.e. we just want to change the associated value)
        while (currentKey != null) {

            // terminate early if we've just updating a value in the map
            if (currentKey.equals(key)) {
                values[insertionPoint] = value;
                return;
            }

            insertionPoint = (insertionPoint + 1) % capacity;
            currentKey = keys[insertionPoint];
        }

        // add the new key-value pair to the map
        keys[insertionPoint] = key;
        values[insertionPoint] = value;

        size++;
    }

    /**
     * Grows the map, allowing more key-value pairs to be stored, or can be used to
     * reduce the load (leading to faster insertions and lookups).
     */
    @SuppressWarnings("unchecked")
    private void grow() {
        final int newCapacity = (int) (capacity * GROWTH_RATE);

        final Key[] newKeys = (Key[]) new Object[newCapacity];
        final Value[] newValues = (Value[]) new Object[newCapacity];

        // go through old arrays and reinsert items into the new larger arrays
        for (int i = 0; i < capacity; i++) {

            final Key currentKey = keys[i];

            if (currentKey == null) {
                continue;
            }

            int insertionPoint = hash(currentKey) % newCapacity;

            // follow items from insertion point until there is a free space to insert
            while (newKeys[insertionPoint] != null) {
                insertionPoint = (insertionPoint + 1) % newCapacity;
            }

            // insert the key-value pair into their new position in the larger arrays
            newKeys[insertionPoint] = currentKey;
            newValues[insertionPoint] = values[i];
        }

        // now all key-value pairs have been moved we can swap the arrays and update associated values
        keys = newKeys;
        values = newValues;

        capacity = newCapacity;
        currentMaxLoad = (int) (newCapacity * MAX_LOAD_FACTOR);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Value> get(final Key key) {

        final Optional<Integer> index = find(key); // get the index of the key if contained in the map

        if (index.isPresent()) {
            return Optional.of(values[index.get()]);
        }

        return Optional.empty();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final Key key) {

        final Optional<Integer> index = find(key); // get the index of the key if contained in the map

        // if key is not in the map there is nothing to delete
        if (!index.isPresent()) {
            return;
        }

        final int keyIndex = index.get();

        // delete key-value pair
        keys[keyIndex] = null;
        values[keyIndex] = null;
        size--;

        // with linear probing, all consecutive non-null entries after the deleted key and value
        // need to be shifted back one position their arrays, as long as they have been placed
        // after their initial hashed index e.g. a key hashed to index 9 but placed at index 10 can be moved back

        int lastPosition = keyIndex;
        int currentPosition = (lastPosition + 1) % capacity;
        Key currentKey = keys[currentPosition];

        while (currentKey != null
                && currentPosition != keyIndex
                && (hash(currentKey) % capacity) < currentPosition) {

            // shift back one position
            keys[lastPosition] = currentKey;
            keys[currentPosition] = null;

            values[lastPosition] = values[currentPosition];
            values[currentPosition] = null;


            lastPosition = currentPosition;
            currentPosition = (currentPosition + 1) % capacity;
            currentKey = keys[currentPosition];
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(final Key key) {
        return find(key).isPresent();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * Find the index of the key in the map's underlying array.
     *
     * @param key Key to find in the map.
     * @return Optional containing the index of the key if it is contained in the map.
     */
    private Optional<Integer> find(final Key key) {

        final int hashPoint = hash(key) % capacity;

        int insertionPoint = hashPoint;
        Key currentKey = keys[insertionPoint];

        // search through the array until either:
        // 1. we find a null value. (i.e. we would've have seen the key by now if it was present)
        // 2. we reach the initial position again (i.e. we've checked the whole array)
        do {

            if (key.equals(currentKey)) {
                return Optional.of(insertionPoint);
            }

            insertionPoint = (insertionPoint + 1) % capacity;
            currentKey = keys[insertionPoint];

        } while (currentKey != null && insertionPoint != hashPoint);

        return Optional.empty();
    }


    /**
     * Generate a positive number from a key's hash code.
     *
     * @param key The key to compute the hash code from.
     * @param <T> Type of the key.
     * @return Positive number generated from the key's hash code.
     */
    private static <T> int hash(final T key) {
        // use a bit mask to change signed bit to 0, resulting in a positive integer.
        return key.hashCode() & 0x7FFFFFFF;
    }

}
