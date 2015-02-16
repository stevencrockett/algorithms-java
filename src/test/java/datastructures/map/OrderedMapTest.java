package datastructures.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

/**
 * Abstract test class for all Ordered Map implementations. Ordered Maps should be consistent
 * in their behaviour, regardless of the underlying implementation.
 * Tests for Ordered Map implementations should extend this class.
 */
public abstract class OrderedMapTest extends MapTest {

    protected OrderedMap<String, Integer> orderedMap;

    @Test
    public void testMinimum() {
        final String firstKey = "London";
        final int firstValue = 1;

        final String secondKey = "Zurich";
        final int secondValue = 2;

        final String thirdKey = "Boston";
        final int thirdValue = 3;

        orderedMap.put(firstKey, firstValue);
        orderedMap.put(secondKey, secondValue);
        orderedMap.put(thirdKey, thirdValue);

        final Optional<String> minimum = orderedMap.min();

        Assert.assertTrue(minimum.isPresent());
        Assert.assertEquals(thirdKey, minimum.get());
    }

    @Test
    public void testMaximum() {
        final String firstKey = "London";
        final int firstValue = 1;

        final String secondKey = "Zurich";
        final int secondValue = 2;

        final String thirdKey = "Boston";
        final int thirdValue = 3;

        orderedMap.put(firstKey, firstValue);
        orderedMap.put(secondKey, secondValue);
        orderedMap.put(thirdKey, thirdValue);

        final Optional<String> maximum = orderedMap.max();

        Assert.assertTrue(maximum.isPresent());
        Assert.assertEquals(secondKey, maximum.get());
    }

    @Test
    public void testPredecessor() {
        final String firstKey = "London";
        final int firstValue = 1;

        final String secondKey = "Zurich";
        final int secondValue = 2;

        final String thirdKey = "Boston";
        final int thirdValue = 3;

        orderedMap.put(firstKey, firstValue);
        orderedMap.put(secondKey, secondValue);
        orderedMap.put(thirdKey, thirdValue);

        final Optional<String> firstPredecessor = orderedMap.predecessor(secondKey);
        Assert.assertTrue(firstPredecessor.isPresent());
        Assert.assertEquals(firstKey, firstPredecessor.get());

        final Optional<String> secondPredecessor = orderedMap.predecessor(firstKey);
        Assert.assertTrue(secondPredecessor.isPresent());
        Assert.assertEquals(thirdKey, secondPredecessor.get());
    }

    @Test
    public void testNoPredecessor() {
        final String firstKey = "London";
        final int firstValue = 1;

        final String secondKey = "Zurich";
        final int secondValue = 2;

        final String thirdKey = "Boston";
        final int thirdValue = 3;

        orderedMap.put(firstKey, firstValue);
        orderedMap.put(secondKey, secondValue);
        orderedMap.put(thirdKey, thirdValue);

        final Optional<String> predecessor = orderedMap.predecessor(thirdKey);
        Assert.assertFalse(predecessor.isPresent());
    }

    @Test
    public void testSuccessor() {
        final String firstKey = "London";
        final int firstValue = 1;

        final String secondKey = "Zurich";
        final int secondValue = 2;

        final String thirdKey = "Boston";
        final int thirdValue = 3;

        orderedMap.put(firstKey, firstValue);
        orderedMap.put(secondKey, secondValue);
        orderedMap.put(thirdKey, thirdValue);

        final Optional<String> firstSuccessor = orderedMap.successor(thirdKey);
        Assert.assertTrue(firstSuccessor.isPresent());
        Assert.assertEquals(firstKey, firstSuccessor.get());

        final Optional<String> secondSuccessor = orderedMap.successor(firstKey);
        Assert.assertTrue(secondSuccessor.isPresent());
        Assert.assertEquals(secondKey, secondSuccessor.get());
    }

    @Test
    public void testNoSuccessor() {
        final String firstKey = "London";
        final int firstValue = 1;

        final String secondKey = "Zurich";
        final int secondValue = 2;

        final String thirdKey = "Boston";
        final int thirdValue = 3;

        orderedMap.put(firstKey, firstValue);
        orderedMap.put(secondKey, secondValue);
        orderedMap.put(thirdKey, thirdValue);

        final Optional<String> successor = orderedMap.successor(secondKey);
        Assert.assertFalse(successor.isPresent());
    }

}
