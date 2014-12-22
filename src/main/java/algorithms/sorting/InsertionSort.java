package algorithms.sorting;

import java.util.Comparator;

/**
 * Generic implementation of insertion sort, sorting items in ascending order.
 */
public class InsertionSort {

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

        // move pointer forward each time, considering the next item which needs to be placed in sorted order
        for (int i = 1, itemCount = items.length; i < itemCount; i++) {

            // iterate backwards, checking that the new item is in the correct
            // position among the items previously sorted.
            for (int j = i - 1; j >= 0; j--) {

                // if the previous item is greater than the new item (in the higher index), swap them
                if (compareFunc.compare(items[j], items[j+1]) > 0) {
                    final T temp = items[j];
                    items[j] = items[j + 1];
                    items[j + 1] = temp;
                } else {
                    // due to insertion sort, we have the guarantee that all the items seen so far
                    // are in sorted order, so we can terminate the backwards iteration early
                    break;
                }

            }

        }
    }
}
