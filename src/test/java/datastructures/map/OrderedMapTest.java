package datastructures.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

        Assertions.assertTrue(minimum.isPresent());
        Assertions.assertEquals(thirdKey, minimum.get());
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

        Assertions.assertTrue(maximum.isPresent());
        Assertions.assertEquals(secondKey, maximum.get());
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
        Assertions.assertTrue(firstPredecessor.isPresent());
        Assertions.assertEquals(firstKey, firstPredecessor.get());

        final Optional<String> secondPredecessor = orderedMap.predecessor(firstKey);
        Assertions.assertTrue(secondPredecessor.isPresent());
        Assertions.assertEquals(thirdKey, secondPredecessor.get());
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
        Assertions.assertFalse(predecessor.isPresent());
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
        Assertions.assertTrue(firstSuccessor.isPresent());
        Assertions.assertEquals(firstKey, firstSuccessor.get());

        final Optional<String> secondSuccessor = orderedMap.successor(firstKey);
        Assertions.assertTrue(secondSuccessor.isPresent());
        Assertions.assertEquals(secondKey, secondSuccessor.get());
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
        Assertions.assertFalse(successor.isPresent());
    }

}
