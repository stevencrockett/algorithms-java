package datastructures.map;

import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertEquals(0, map.size());
    }

    @Test
    public void testNonEmptyMapSize() {
        final String someKey = "London";
        final int someValue = 1;

        Assert.assertEquals(0, map.size());

        map.put(someKey, someValue);
        Assert.assertEquals(1, map.size());
    }

    @Test
    public void testMissingKey() {
        final String missingKey = "thing";

        final Optional<Integer> result = map.get(missingKey);
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void testMissingKeyContains() {
        final String missingKey = "thing";

        Assert.assertFalse(map.contains(missingKey));
    }

    @Test
    public void testSinglePair() {
        final String someKey = "London";
        final int someValue = 1;

        map.put(someKey, someValue);

        final Optional<Integer> result = map.get(someKey);
        Assert.assertEquals(someValue, result.get().intValue());
    }

    @Test
    public void testSinglePairContains() {
        final String someKey = "London";
        final int someValue = 1;

        map.put(someKey, someValue);

        Assert.assertTrue(map.contains(someKey));
    }

    @Test
    public void testDoublePair() {
        final String firstKey = "London";
        final int firstValue = 1;
        map.put(firstKey, firstValue);

        final String secondKey = "Tokyo";
        final int secondValue = 2;
        map.put(secondKey, secondValue);

        Assert.assertEquals(firstValue, map.get(firstKey).get().intValue());
        Assert.assertEquals(secondValue, map.get(secondKey).get().intValue());
    }

    @Test
    public void testDoublePairContains() {
        final String firstKey = "London";
        final int firstValue = 1;
        map.put(firstKey, firstValue);

        final String secondKey = "Tokyo";
        final int secondValue = 2;
        map.put(secondKey, secondValue);

        Assert.assertTrue(map.contains(firstKey));
        Assert.assertTrue(map.contains(secondKey));
    }

    @Test
    public void testValueOverwrite() {
        final String someKey = "London";
        final int someValue = 1;

        map.put(someKey, someValue);

        final int newValue = 5;

        map.put(someKey, newValue);

        Assert.assertEquals(newValue, map.get(someKey).get().intValue());
    }

    @Test
    public void testValueOverwriteSize() {
        final String someKey = "London";
        final int someValue = 1;

        map.put(someKey, someValue);
        Assert.assertEquals(1, map.size());

        final int newValue = 5;

        map.put(someKey, newValue);
        Assert.assertEquals(1, map.size());
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

        Assert.assertEquals(1, map.size());
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

        Assert.assertFalse(map.contains(someKey));
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
        Assert.assertFalse(result.isPresent());
    }

}
