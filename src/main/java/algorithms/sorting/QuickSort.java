package algorithms.sorting;

import algorithms.shuffling.KnuthShuffle;

import java.util.Comparator;

/**
 * Generic implementation of quick sort, sorting items in ascending order.
 */
public final class QuickSort {

    private QuickSort() { }

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
        // first, shuffle the items as a probabalistic guarantee against worst-case running time
        KnuthShuffle.shuffle(items);
        sort(items, 0, items.length - 1, compareFunc);
    }


    /**
     * Recursive quicksort. At each step the array is partitioned by putting a single element in its
     * final sorted position within the array. This partitioned element defines a subarray on either
     * side of the the element, both of which are sorted recursively.
     *
     * @param items The items to be sorted.
     * @param low Index of the lowest array element to be sorted.
     * @param high Index of the highest array element to be sorted.
     * @param compareFunc Function to compare the given items.
     * @param <T> Type of the items to be sorted.
     */
    private static <T> void sort(final T[] items, final int low, final int high, final Comparator<T> compareFunc) {

        // no need to sort if subarray doesn't contain at least 2 elements
        if (high - low < 1) return;

        final int point = partition(items, low, high, compareFunc); // find partition point

        sort(items, low, point - 1, compareFunc); // sort subarray left of partition
        sort(items, point + 1, high, compareFunc); // sort subarray right of partition
    }


    /**
     * Selects a single element (the pivot) in the subarray - defined by the upper and lower bounds - and
     * places it in sorted order within the subarray, returning the index at which the pivot was placed.
     * The lowest element in the subarray is always chosen as the pivot element.
     *
     * @param items The items to be sorted.
     * @param low Index of the lowest array element to be sorted.
     * @param high Index of the highest array element to be sorted.
     * @param compareFunc Function to compare the given items.
     * @param <T> Type of the items to be sorted.
     * @return The index at which the chosen pivot was placed after being put in its sorted position.
     */
    private static <T> int partition(final T[] items, final int low, final int high, final Comparator<T> compareFunc) {

        final T pivot = items[low]; // choose leftmost element as the pivot

        // maintain left and right pointers which scan across the array from either direction
        int l = low + 1, r = high;

        while (true) {

            // move the left pointer along if the item is less than the pivot
            while (compareFunc.compare(items[l], pivot) < 0) {
                if (l == r) break; // stop if we reach the right pointer (implicitly avoids going out of bounds too)
                l++;
            }

            // move the right pointer across array while the items are greater than the pivot
            while (compareFunc.compare(items[r], pivot) > 0) {
                r--;
                // since the leftmost element is the pivot (which can never be greater than
                // itself), there is no chance to go out of bounds
            }

            if (l >= r) break; // terminate if pointers have crossed

            // swap elements at left and right pointers so that the smaller element is on the left side
            final T temp = items[l];
            items[l] = items[r];
            items[r] = temp;
        }

        // finally, place pivot value in the correct position and return the index of the pivot
        final T temp = items[r];
        items[r] = pivot;
        items[low] = temp;

        return r;
    }

}
