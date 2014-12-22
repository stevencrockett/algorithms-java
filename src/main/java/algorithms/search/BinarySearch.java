package algorithms.search;

import java.util.Comparator;
import java.util.Optional;

/**
 * Generic implementation of binary search.
 */
public class BinarySearch {

    /**
     * Search for the key within the given array of items.
     *
     * @param items The items to search within.
     * @param key The item to search for in the array.
     * @param <T> Type of the set of items and the key to be searched for.
     * @return Optional containing the index of the key if found; empty otherwise.
     */
    public static <T extends Comparable<T>> Optional<Integer> search(final T[] items, final T key) {
        return search(items, key, Comparable::compareTo);
    }

    /**
     * Search for the key within the given array of items.
     *
     * @param items The items to search within.
     * @param key The item to search for in the array.
     * @param compareFunc Function to compare the key with the items.
     * @param <T> Type of the set of items and the key to be searched for.
     * @return Optional containing the index of the key if found; empty otherwise.
     */
    public static <T> Optional<Integer> search(final T[] items, final T key, final Comparator<T> compareFunc) {
        return binarySearch(items, key, compareFunc);
    }


    /**
     * Iteratively search for the key within the given array of items.
     *
     * @param items The items to search within.
     * @param key The item to search for in the array.
     * @param compareFunc Function to compare the key with the items.
     * @param <T> Type of the set of items and the key to be searched for.
     * @return Optional containing the index of the key if found; empty otherwise.
     */
    private static <T> Optional<Integer> binarySearch(final T[] items, final T key,
                                                      final Comparator<T> compareFunc) {

        int low = 0; // index of the smallest element to consider
        int high = items.length; // index of the largest element to consider

        while (low <= high) {
            final int mid = low + ((high - low) / 2); // compute index of middle element
            final T midItem = items[mid];

            final int compareResult = compareFunc.compare(key, midItem);
            if (compareResult > 0) {
                low = mid + 1; // update search to upper half of array
            } else if (compareResult < 0) {
                high = mid - 1; // update search to lower half of array
            } else {
                return Optional.of(mid); // key has been found
            }
        }

        return Optional.empty(); // key must not be in the array
    }


//    /**
//     * Recursively search for the key within the given array of items.
//     *
//     * @param items The items to search within.
//     * @param key The item to search for in the array.
//     * @param low Index of the smallest element to consider.
//     * @param high Index of the largest element to consider.
//     * @param compareFunc Function to compare the key with the items.
//     * @param <T> Type of the set of items and the key to be searched for.
//     * @return Optional containing the index of the key if found; empty otherwise.
//     */
//    private static <T> Optional<Integer> binarySearch(final T[] items, final T key,
//                                                      final int low, final int high,
//                                                      final Comparator<T> compareFunc) {
//
//        if (low > high) return Optional.empty(); // key must not be in the array
//
//        final int mid = low + ((high - low) / 2); // compute index of middle element
//        final T midItem = items[mid];
//
//        final int compareResult = compareFunc.compare(key, midItem);
//        if (compareResult > 0) {
//            return binarySearch(items, key, mid + 1, high, compareFunc); // search upper half of array
//        } else if (compareResult < 0) {
//            return binarySearch(items, key, low, mid - 1, compareFunc); // search lower half of array
//        } else {
//            return Optional.of(mid); // key has been found
//        }
//    }

}
