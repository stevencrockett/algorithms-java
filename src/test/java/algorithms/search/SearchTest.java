package algorithms.search;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class SearchTest {

    @Test
    public void testBinarySearchIsPresent() {

        final int itemCount = 1000; // number of items to search within

        // items will be an ascending sequence of positive integers, with a random starting point
        final int start = 1 + ThreadLocalRandom.current().nextInt(100);

        // generate the items
        final Integer[] items = IntStream.range(start, start + itemCount)
                .mapToObj(Integer::valueOf).toArray(Integer[]::new);


        // find item known to be in the array to search for
        final int knownIndex = ThreadLocalRandom.current().nextInt(itemCount);
        final Integer knownItem = items[knownIndex];

        final Optional<Integer> result = BinarySearch.search(items, knownItem);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(knownIndex, result.get().intValue());
    }

    @Test
    public void testBinarySearchNotPresent() {

        final int itemCount = 1000; // number of items to search within

        // items will be an ascending sequence of positive integers, with a random starting point
        final int start = 1 + ThreadLocalRandom.current().nextInt(100);

        // generate the items
        final Integer[] items = IntStream.range(start, start + itemCount)
                .mapToObj(Integer::valueOf).toArray(Integer[]::new);


        // find item known to not be in the array to search for
        final Integer knownItem = 0;

        final Optional<Integer> result = BinarySearch.search(items, knownItem);

        Assert.assertFalse(result.isPresent());
    }

}
