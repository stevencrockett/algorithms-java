package algorithms.sorting;

import java.util.Comparator;

/**
 * Generic implementation of merge sort, sorting items in ascending order.
 */
public class MergeSort {

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
    private static <T> void sort(T[] items, T[] aux, int low, int high, Comparator<T> compareFunc) {

        // if it's a single element we don't need to sort it
        if (high <= low) {
            return;
        }

        final int mid = low + ((high - low) / 2); // compute index of middle element

        sort(aux, items, low, mid, compareFunc); // sort lower half
        sort(aux, items, mid + 1, high, compareFunc); // sort upper half

        // if the largest element of the lower half isn't greater than the smallest element of the
        // upper half, then the whole array must already be in sorted order, so we can end early.
        if (compareFunc.compare(items[mid], items[mid + 1]) < 0) {
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
    private static <T> void merge(T[] items, T[] aux, int low, int mid, int high, Comparator<T> compareFunc) {

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
            } else if (compareFunc.compare(items[lowerPointer], items[upperPointer]) < 0) {
                // item in lower half < item in the upper half
                aux[i] = items[lowerPointer];
                lowerPointer++;
            } else {
                // item in upper half <= item in the lower half
                aux[i] = items[upperPointer];
                upperPointer++;
            }

        }
    }



}
