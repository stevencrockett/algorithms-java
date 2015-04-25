package datastructures.map;

import java.util.Optional;

/**
 * An ordered map, which internally stores key in order, such that it is easy to
 * find the min key, max key etc. contained in the map.
 */
public interface OrderedMap<Key extends Comparable<Key>, Value> extends Map<Key, Value> {

    /**
     * Find the smallest key in the map.
     *
     * @return Optional containing the smallest key in the map if it exists.
     */
    Optional<Key> min();


    /**
     * Find the largest key in the map.
     *
     * @return Optional containing the largest key in the map if it exists.
     */
    Optional<Key> max();


    /**
     * Find the next key smaller than the given key.
     *
     * @param key Key to find the next key smaller than.
     * @return Optional containing the next smallest key in the map if it exists.
     */
    Optional<Key> predecessor(Key key);


    /**
     * Find the next key greater than the given key.
     *
     * @param key Key to find the next key greater than.
     * @return Optional containing the next largest key in the map if it exists.
     */
    Optional<Key> successor(Key key);

}
