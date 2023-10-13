package datastructures.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HashMapTest extends MapTest {

    private static final int STARTING_CAPACITY = 20;

    @BeforeEach
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
            Assertions.assertTrue(map.contains(String.valueOf(i)));
        }
    }
}
