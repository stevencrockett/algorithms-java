package algorithms.sorting;

import java.util.Comparator;

/**
 * Generic implementation of merge sort, sorting items in ascending order.
 */
public final class MergeSort {

    private MergeSort() { }

    /**
     * If the size of the array is sufficiently small, the algorithm backs off to insertion
     * sort which is more efficient for small arrays. If the number of items is 7 or less,
     * insertion sort is used. Since arrays are zero-indexed, an offset is subtracted in advance
     * rather than subtracting the offset every time the array size is computed.
     */
    final static int SIZE_THRESHOLD = 7 - 1;


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
        final T[] aux = items.clone(); // create extra array to copy items between
        sort(aux, items, 0, items.length - 1, compareFunc);
    }


    /**
     * Recursive top-down merge sort. At each step the array is split into halves, where each
     * subarray is sorted separately, and then these sorted subarrays are merged.
     *
     * @param items The items to be sorted.
     * @param aux Auxiliary array used to copy elements to and from.
     * @param low Index of the lowest array element to be sorted.
     * @param high Index of the highest array element to be sorted.
     * @param compareFunc Function to compare the given items.
     * @param <T> Type of the items to be sorted.
     */
    private static <T> void sort(final T[] items, final T[] aux, final int low, final int high,
                                 final Comparator<T> compareFunc) {

        // if the array to be sorted is sufficiently small, backoff to insertion sort
        if (high - low <= SIZE_THRESHOLD) {
            InsertionSort.sort(aux, low, high, compareFunc);
            return;
        }

        final int mid = low + ((high - low) / 2); // compute index of middle element

        sort(aux, items, low, mid, compareFunc); // sort lower half
        sort(aux, items, mid + 1, high, compareFunc); // sort upper half

        // if the largest element of the lower half isn't greater than the smallest element of the
        // upper half, then the whole array must already be in sorted order, so we can end early.
        if (compareFunc.compare(items[mid], items[mid + 1]) < 0) {
            // since we are avoiding the merge, we need to manually copy into the auxiliary array
            System.arraycopy(items, low, aux, low, high - low + 1);
            return;
        }

        merge(items, aux, low, mid, high, compareFunc);
    }


    /**
     * Merge the two sorted subarrays, the bounds of which are indicated by the given indices.
     *
     * @param items The items to be sorted.
     * @param aux Auxiliary array used to copy elements to and from.
     * @param low Starting index of the lower subarray to be merged.
     * @param mid Middle index indicating the end of the lower subarray.
     * @param high End index of the upper subarray to be merged.
     * @param compareFunc Function to compare the given items.
     * @param <T> Type of the items to be sorted.
     */
    private static <T> void merge(final T[] items, final T[] aux, final int low, final int mid, final int high,
                                  final Comparator<T> compareFunc) {

        // maintain pointers to the next available element to be merged in each subarray
        int lowerPointer = low, upperPointer = mid + 1;

        for (int i = low;  i <= high; i++) {

            if (lowerPointer > mid) {
                // if we've exhausted elements in the lower half, trivially take from upper half
                aux[i] = items[upperPointer];
                upperPointer++;
            } else if (upperPointer > high) {
                // if we've exhausted elements in the upper half, trivially take from lower half
                aux[i] = items[lowerPointer];
                lowerPointer++;
            } else if (compareFunc.compare(items[lowerPointer], items[upperPointer]) > 0) {
                // item in lower half > item in the upper half
                aux[i] = items[upperPointer];
                upperPointer++;
            } else {
                // item in lower half <= item in the upper half
                aux[i] = items[lowerPointer];
                lowerPointer++;
            }

        }
    }

}
