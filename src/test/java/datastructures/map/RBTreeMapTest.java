package datastructures.map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RBTreeMapTest extends OrderedMapTest {

    private RBTreeMap<String, Integer> treeMap;

    @Before
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

        Assert.assertTrue(treeMap.contains(firstKey));
        Assert.assertFalse(treeMap.contains(secondKey));
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

        Assert.assertTrue(treeMap.contains(firstKey));
        Assert.assertTrue(treeMap.contains(thirdKey));
        Assert.assertFalse(treeMap.contains(secondKey));
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

        Assert.assertTrue(treeMap.contains(firstKey));
        Assert.assertTrue(treeMap.contains(thirdKey));
        Assert.assertTrue(treeMap.contains(fourthKey));
        Assert.assertFalse(treeMap.contains(secondKey));
    }

}
