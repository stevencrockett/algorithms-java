package algorithms.sorting;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

public class SortingTest {

    private Double[] items;

    private static final int ITEM_COUNT = 10000;

    @Before
    public void initialise() {
        do {
            items = ThreadLocalRandom.current().doubles()
                    .limit(ITEM_COUNT)
                    .mapToObj(Double::valueOf)
                    .toArray(Double[]::new);
        } while (isSorted(items));

    }

    @Test
    public void testSelectionSort() {
        SelectionSort.sort(items);
        Assert.assertTrue(isSorted(items));
    }

    @Test
    public void testBubbleSort() {
        BubbleSort.sort(items);
        Assert.assertTrue(isSorted(items));
    }

    @Test
    public void testInsertionSort() {
        InsertionSort.sort(items);
        Assert.assertTrue(isSorted(items));
    }

    @Test
    public void testShellSort() {
        ShellSort.sort(items);
        Assert.assertTrue(isSorted(items));
    }

    @Test
    public void testMergeSort() {
        MergeSort.sort(items);
        Assert.assertTrue(isSorted(items));
    }

    @Test
    public void testQuickSort() {
        QuickSort.sort(items);
        Assert.assertTrue(isSorted(items));
    }


    private boolean isSorted(final Double[] items) {
        // check that items are in ascending order
        for (int i = 0, iterations = items.length - 1; i < iterations; i++) {
            if (items[i].compareTo(items[i+1]) > 0) {
                return false;
            }
        }

        return true;
    }
}
