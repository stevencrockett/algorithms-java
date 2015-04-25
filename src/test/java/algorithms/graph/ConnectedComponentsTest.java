package algorithms.graph;

import datastructures.graph.UndirectedGraph;
import org.junit.Assert;
import org.junit.Test;

public class ConnectedComponentsTest {

    private static final int VERTEX_COUNT = 6;

    private static UndirectedGraph generateUndirectedGraph() {
        final UndirectedGraph g = new UndirectedGraph(VERTEX_COUNT);

        // create 3 components
        g.addEdge(0, 1);
        g.addEdge(0, 2);

        g.addEdge(3, 4); // second component

        // last component is vertex 5 by itself
        return g;
    }

    @Test
    public void testComponentCount() {
        final ConnectedComponents cc = new ConnectedComponents(generateUndirectedGraph());

        final int expectedCount = 3;

        final int actualCount = cc.count();

        Assert.assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testVerticesAreConnected() {
        final ConnectedComponents cc = new ConnectedComponents(generateUndirectedGraph());

        Assert.assertTrue(cc.connected(0, 2));
        Assert.assertTrue(cc.connected(3, 4));

        Assert.assertFalse(cc.connected(1, 5));
        Assert.assertFalse(cc.connected(4, 5));
    }

    @Test
    public void testComponentIds() {
        final ConnectedComponents cc = new ConnectedComponents(generateUndirectedGraph());

        Assert.assertTrue(cc.id(0) == cc.id(2));
        Assert.assertTrue(cc.id(3) == cc.id(4));

        Assert.assertTrue(cc.id(1) != cc.id(5));
        Assert.assertTrue(cc.id(4) != cc.id(5));
    }

}
