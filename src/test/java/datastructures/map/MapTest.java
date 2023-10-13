package datastructures.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * Abstract test class for all Map implementations. Maps should be consistent in their
 * behaviour, regardless of the underlying implementation.
 * Tests for Map implementations should extend this class.
 */
public abstract class MapTest {

    protected Map<String, Integer> map;

    @Test
    public void testEmptyMapSize() {
        Assertions.assertEquals(0, map.size());
    }

    @Test
    public void testNonEmptyMapSize() {
        final String someKey = "London";
        final int someValue = 1;

        Assertions.assertEquals(0, map.size());

        map.put(someKey, someValue);
        Assertions.assertEquals(1, map.size());
    }

    @Test
    public void testMissingKey() {
        final String missingKey = "thing";

        final Optional<Integer> result = map.get(missingKey);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testMissingKeyContains() {
        final String missingKey = "thing";

        Assertions.assertFalse(map.contains(missingKey));
    }

    @Test
    public void testSinglePair() {
        final String someKey = "London";
        final int someValue = 1;

        map.put(someKey, someValue);

        final Optional<Integer> result = map.get(someKey);
        Assertions.assertEquals(someValue, result.get().intValue());
    }

    @Test
    public void testSinglePairContains() {
        final String someKey = "London";
        final int someValue = 1;

        map.put(someKey, someValue);

        Assertions.assertTrue(map.contains(someKey));
    }

    @Test
    public void testDoublePair() {
        final String firstKey = "London";
        final int firstValue = 1;
        map.put(firstKey, firstValue);

        final String secondKey = "Tokyo";
        final int secondValue = 2;
        map.put(secondKey, secondValue);

        Assertions.assertEquals(firstValue, map.get(firstKey).get().intValue());
        Assertions.assertEquals(secondValue, map.get(secondKey).get().intValue());
    }

    @Test
    public void testDoublePairContains() {
        final String firstKey = "London";
        final int firstValue = 1;
        map.put(firstKey, firstValue);

        final String secondKey = "Tokyo";
        final int secondValue = 2;
        map.put(secondKey, secondValue);

        Assertions.assertTrue(map.contains(firstKey));
        Assertions.assertTrue(map.contains(secondKey));
    }

    @Test
    public void testValueOverwrite() {
        final String someKey = "London";
        final int someValue = 1;

        map.put(someKey, someValue);

        final int newValue = 5;

        map.put(someKey, newValue);

        Assertions.assertEquals(newValue, map.get(someKey).get().intValue());
    }

    @Test
    public void testValueOverwriteSize() {
        final String someKey = "London";
        final int someValue = 1;

        map.put(someKey, someValue);
        Assertions.assertEquals(1, map.size());

        final int newValue = 5;

        map.put(someKey, newValue);
        Assertions.assertEquals(1, map.size());
    }

    @Test
    public void testDeleteSize() {
        final String someKey = "London";
        final int someValue = 1;
        map.put(someKey, someValue);

        final String otherKey = "Tokyo";
        final int otherValue = 2;
        map.put(otherKey, otherValue);

        map.delete(someKey);

        Assertions.assertEquals(1, map.size());
    }

    @Test
    public void testDeleteContains() {
        final String someKey = "London";
        final int someValue = 1;
        map.put(someKey, someValue);

        final String otherKey = "Tokyo";
        final int otherValue = 2;
        map.put(otherKey, otherValue);

        map.delete(someKey);

        Assertions.assertFalse(map.contains(someKey));
    }

    @Test
    public void testDeleteGet() {
        final String someKey = "London";
        final int someValue = 1;
        map.put(someKey, someValue);

        final String otherKey = "Tokyo";
        final int otherValue = 2;
        map.put(otherKey, otherValue);

        map.delete(someKey);

        final Optional<Integer> result = map.get(someKey);
        Assertions.assertFalse(result.isPresent());
    }

}
