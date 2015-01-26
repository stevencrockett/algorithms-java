package algorithms.sorting;

import java.util.Comparator;

/**
 * Generic implementation of heap sort, sorting items in ascending order.
 */
public final class HeapSort {

    private HeapSort() { }

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
        int size = items.length;

        // first turn the array into a heap
        for (int i = (size - 1) / 2; i >= 0; i--) {
            sink(items, size, i, compareFunc);
        }

        // on each iteration, move the current max value in the heap to the sorted part of the array
        // the heap portion of the array gets progressively smaller as the array becomes sorted
        while (size > 0) {

            size--; // update size of heap portion

            // swap first (max) element in the heap with last element, moving it to the sorted portion of the array
            final T max = items[0];
            items[0] = items[size];
            items[size] = max;

            // ensure heap order is maintained for the remaining heap portion of the array
            sink(items, size, 0, compareFunc);
        }
    }


    /**
     * Potentially moves the item at the given index down through the heap such that the item is
     * not smaller than any of its children, keeping in line with the heap order property. If the item
     * is smaller than both its children, the the item will be swapped with the larger of the two.
     *
     * The end index of the heap in the array is specified, allowing only a portion at the
     * start of the array to be interpreted as the heap.
     *
     * @param items The items to be sorted, of which some portion is interpreted as a binary heap.
     * @param size Size of the heap portion of the array. The first size elements are interpreted
     *             as a binary heap.
     * @param index Index of the item to try and move down the heap.
     * @param compareFunc Function to compare the given items.
     * @param <T> Type of the items to be sorted.
     */
    private static <T> void sink(final T[] items, final int size, int index, final Comparator<T> compareFunc) {

        int childIndex = (index * 2) + 1;
        while (childIndex < size) {

            // check if right child is strictly greater than the left child
            if (childIndex + 1 < size && compareFunc.compare(items[childIndex], items[childIndex + 1]) < 0) {
                childIndex++;
            }

            // if the parent isn't smaller than its largest child then we can stop early
            if (compareFunc.compare(items[index], items[childIndex]) > -1) {
                break;
            }

            // otherwise we swap the parent and child and continue down the heap
            final T temp = items[index];
            items[index] = items[childIndex];
            items[childIndex] = temp;

            index = childIndex;
            childIndex = (index * 2) + 1;
        }
    }

}
