package datastructures.map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HashMapTest extends MapTest {

    private static final int STARTING_CAPACITY = 20;

    @Before
    public void initialise() {
        map = new HashMap<>(STARTING_CAPACITY);
    }

    @Test
    public void testMapGrowth() {
        final int limit = STARTING_CAPACITY + 10;
        for (int i = 0; i < limit; i++) {
            map.put(String.valueOf(i), i);
        }

        for (int i = 0; i < limit; i++) {
            Assert.assertTrue(map.contains(String.valueOf(i)));
        }
    }
}
