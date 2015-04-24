package datastructures.graph;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

public class UndirectedGraphTest {

    private UndirectedGraph graph;

    private static final int VERTEX_COUNT = 10;

    @Before
    public void initialise() {
        graph = new UndirectedGraph(VERTEX_COUNT);
    }

    @Test
    public void testGraphSize() {
        Assert.assertEquals(VERTEX_COUNT, graph.vertexCount());
    }

    @Test
    public void testUnconnectedGraph() {
        // check adjacent vertices for each vertex
        for (int i = 0; i < VERTEX_COUNT; i++) {
            // vertex should have no adjacent vertices, so fail immediately
            if (graph.adj(i).hasNext()) {
                Assert.fail();
            }
        }
    }

    @Test
    public void testSingleConnection() {
        graph.addEdge(0, 1);

        int adjCount = 0;
        Iterator<Integer> adjacent = graph.adj(0);

        while (adjacent.hasNext()) {
            final int adj = adjacent.next();
            // vertex 1 should be the only adjacent vertex
            if (adj != 1) {
                Assert.fail();
            }
            adjCount++;
        }

        if (adjCount != 1) {
            Assert.fail();
        }

        adjCount = 0;
        adjacent = graph.adj(1);
        while (adjacent.hasNext()) {
            final int adj = adjacent.next();
            // vertex 0 should be the only adjacent vertex
            if (adj != 0) {
                Assert.fail();
            }
            adjCount++;
        }

        if (adjCount != 1) {
            Assert.fail();
        }
    }

}
