package algorithms.sorting;

import java.util.Comparator;

/**
 * Generic implementation of bubble sort, sorting items in ascending order.
 */
public final class BubbleSort {

    private BubbleSort() { }

    /**
     * Sort the given items in ascending order.
     * Items implementing comparable can be easily sorted.
     *
     * @param items The items to be sorted.
     * @param <T> Type of the items to be sorted.
     */
    public static <T extends Comparable<T>> void sort(final T[] items) {
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
    public static <T> void sort(final T[] items, final Comparator<T> compareFunc) {

        final int itemCount = items.length;

        // need to sort all but one item. once everything is sorted, the final
        // item will clearly be in the correct position
        for (int i = 0, iterations = itemCount - 1; i < iterations; i++) {

            // after the ith iteration, the last i items in the array must be in sorted
            // order, so we can avoid checking these positions
            for (int j = 0, end = iterations - i; j < end; j++) {

                // compare adjacent items in the array and swap if out of order
                if (compareFunc.compare(items[j], items[j + 1]) > 0) {
                    final T temp = items[j];
                    items[j] = items[j + 1];
                    items[j + 1] = temp;
                }
            }

        }

    }
}
