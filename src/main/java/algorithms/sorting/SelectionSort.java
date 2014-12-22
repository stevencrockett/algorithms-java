package algorithms.sorting;

import java.util.Comparator;

/**
 * Generic implementation of selection sort, sorting items in ascending order.
 */
public class SelectionSort {

    /**
     * Sort the given items in ascending order.
     * Items implementing comparable can be easily sorted.
     *
     * @param items The items to be sorted.
     */
    public static void sort(Comparable[] items) {
        sort(items, Comparable::compareTo);
    }

    /**
     * Sort the given items in ascending order.
     * For items not implementing comparable, a function needs to be given in
     * order to compare the items and provide an ordering.
     *
     * @param items The items to be sorted.
     * @param compareFunc Function to compare the given items.
     * @param <T> Type of the items to be sorted.
     */
    public static <T> void sort(T[] items, Comparator<T> compareFunc) {

        final int itemCount = items.length;

        // for each index, find the smallest remaining element and place it at the index
        for (int i = 0, iterations = itemCount - 1; i < iterations; i++) {

            int currentMinIndex = i;
            T currentMin = items[i];

            // iterate forward to the end of the array, finding the min value
            for (int j = i + 1; j < itemCount; j++) {
                if (compareFunc.compare(currentMin, items[j]) > 0) {
                    currentMinIndex = j;
                    currentMin = items[j];
                }
            }

            // finally, place the min value at the smallest unsorted index.
            T temp = items[i];
            items[i] = items[currentMinIndex];
            items[currentMinIndex] = temp;

        }

    }
}
