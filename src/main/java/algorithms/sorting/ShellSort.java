package algorithms.sorting;

import java.util.*;

/**
 * Generic implementation of shell sort, sorting items in ascending order.
 */
public class ShellSort {

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

        // create sequence of increments at which to perform insertion sort
        // (an increment of 1 is normal insertion sort)
        final int[] increments = generateIncrementSequence(itemCount);

        Arrays.stream(increments).forEach(inc -> {

            // move pointer forward each time, considering the next item which needs to be placed in sorted order
            for (int i = inc; i < itemCount; i ++) {

                // iterate backwards, checking that the new item is in the correct
                // position among the items previously sorted.
                for (int j = i - inc; j >= 0; j -= inc) {

                    // index of the element which the element at the jth index is compared with
                    final int otherIndex = j + inc;

                    // if the previous item is greater than the new item (in the higher index), swap them
                    if (compareFunc.compare(items[j], items[otherIndex]) > 0) {
                        final T temp = items[j];
                        items[j] = items[otherIndex];
                        items[otherIndex] = temp;
                    } else {
                        // due to insertion sort, we have the guarantee that all the items seen so far
                        // are in sorted order, so we can terminate the backwards iteration early
                        break;
                    }

                }


            }

        });
    }


    /**
     * Create the sequence of increments used during shell sort.
     * This increment sequence is given by Robert Sedgewick.
     *
     * @param itemCount The number of items that need to be sorted.
     * @return Array containing the decreasing sequence of increments to be used in shell sort.
     */
    private static int[] generateIncrementSequence(final int itemCount) {

        final List<Integer> incrementSequence = new ArrayList<>();

        // accumulate the increments in reverse order, starting from the smallest
        int k = 0;
        while (true) {
            final int firstResult = 9 * ((int)Math.pow(4, k) - (int)Math.pow(2, k)) + 1;
            if (firstResult < itemCount) {
                incrementSequence.add(firstResult);
            } else {
                break;
            }

            final int secondResult = (int)Math.pow(4, k + 2) - (6 * (int)Math.pow(2, k + 1)) + 1;
            if (secondResult < itemCount) {
                incrementSequence.add(secondResult);
            } else {
                break;
            }

            k++;
        }

        Collections.reverse(incrementSequence); // reverse sequence to descending order

        return incrementSequence.stream().mapToInt(Integer::intValue).toArray();
    }

}
