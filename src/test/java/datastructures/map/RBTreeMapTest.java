package datastructures.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RBTreeMapTest extends OrderedMapTest {

    private RBTreeMap<String, Integer> treeMap;

    @BeforeEach
    public void initialise() {
        treeMap = new RBTreeMap<>();
        orderedMap = treeMap;
        map = treeMap;
    }

    @Test
    public void testDeleteNoChildren() {
        final String firstKey = "a";
        final String secondKey = "b";

        final int someValue = 1;

        treeMap.put(firstKey, someValue);
        treeMap.put(secondKey, someValue);

        treeMap.delete(secondKey);

        Assertions.assertTrue(treeMap.contains(firstKey));
        Assertions.assertFalse(treeMap.contains(secondKey));
    }

    @Test
    public void testDeleteSingleChild() {
        final String firstKey = "a";
        final String secondKey = "b";
        final String thirdKey = "c";

        final int someValue = 1;

        treeMap.put(firstKey, someValue);
        treeMap.put(secondKey, someValue);
        treeMap.put(thirdKey, someValue);

        treeMap.delete(secondKey);

        Assertions.assertTrue(treeMap.contains(firstKey));
        Assertions.assertTrue(treeMap.contains(thirdKey));
        Assertions.assertFalse(treeMap.contains(secondKey));
    }

    @Test
    public void testDeleteBothChildren() {
        final String firstKey = "a";
        final String secondKey = "c";
        final String thirdKey = "b";
        final String fourthKey = "d";

        final int someValue = 1;

        treeMap.put(firstKey, someValue);
        treeMap.put(secondKey, someValue);
        treeMap.put(thirdKey, someValue);
        treeMap.put(fourthKey, someValue);

        // node with key "c" will have nodes with "b" and "d" as left and right children respectively

        treeMap.delete(secondKey);

        Assertions.assertTrue(treeMap.contains(firstKey));
        Assertions.assertTrue(treeMap.contains(thirdKey));
        Assertions.assertTrue(treeMap.contains(fourthKey));
        Assertions.assertFalse(treeMap.contains(secondKey));
    }

}
