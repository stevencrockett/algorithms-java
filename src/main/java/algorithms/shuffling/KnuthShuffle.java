package algorithms.shuffling;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generic implementation of the Knuth (or Fisher-Yates) shuffle, to randomise
 * the order of items in a given array.
 */
public final class KnuthShuffle {

    private KnuthShuffle() { }

    /**
     * Randomise the order of items in the given array.
     *
     * @param items The items to be shuffled.
     * @param <T> Type of the items to be shuffled.
     */
    public static <T> void shuffle(final T[] items) {

        // on each iteration, the random item will be placed at index i, and so the shuffled part
        // of the array grows from the back of the array
        for (int i = items.length - 1; i > 0; i--) {

            // choose index from unshuffled part of the array
            final int index = ThreadLocalRandom.current().nextInt(i + 1);

            // move chosen item into the shuffled part of the array
            final T temp = items[i]; // copy last unshuffled item
            items[i] = items[index]; // store chosen item at the end of the subarray, joining the shuffled items
            items[index] = temp; // put copied item back into the unshuffled portion of the array
        }
    }

}
