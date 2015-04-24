package algorithms.graph;

import datastructures.graph.DirectedGraph;
import datastructures.graph.UndirectedGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;

public class BFSTest {

    private static final int VERTEX_COUNT = 5;

    private static UndirectedGraph generateUndirectedGraph() {
        final UndirectedGraph g = new UndirectedGraph(VERTEX_COUNT);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        return g;
    }

    private static DirectedGraph generateDirectedGraph() {
        final DirectedGraph g = new DirectedGraph(VERTEX_COUNT);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        return g;
    }

    @Test
    public void testUndirectedPathExists() {
        final UndirectedGraph g = generateUndirectedGraph();
        final int rootVertex = 0; // perform search from vertex 0
        final BFS bfs = new BFS(g, rootVertex);

        Assert.assertTrue(bfs.hasPathTo(1));
        Assert.assertTrue(bfs.hasPathTo(2));
        Assert.assertTrue(bfs.hasPathTo(3));

        // vertex 4 is disconnected in the graph
        Assert.assertFalse(bfs.hasPathTo(4));
    }

    @Test
    public void testUndirectedPath() {
        final UndirectedGraph g = generateUndirectedGraph();
        final int rootVertex = 0; // perform search from vertex 0
        final BFS bfs = new BFS(g, rootVertex);

        Assert.assertFalse(bfs.pathTo(4).isPresent());

        // verify the path from vertex 0 to vertex 3
        final Optional<Iterator<Integer>> path = bfs.pathTo(3);

        Assert.assertTrue(path.isPresent());

        final LinkedList<Integer> actualPath = new LinkedList<>();
        path.get().forEachRemaining(actualPath::add);

        final LinkedList<Integer> expectedPath = new LinkedList<>();
        expectedPath.add(0);
        expectedPath.add(1);
        expectedPath.add(3);

        Assert.assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testUndirectedSearch() {
        final UndirectedGraph g = generateUndirectedGraph();
        final int rootVertex = 0; // perform search from vertex 0

        final LinkedList<Integer> actualOrder = new LinkedList<>();
        BFS.search(g, rootVertex).forEachRemaining(actualOrder::add);

        final LinkedList<Integer> expectedOrder = new LinkedList<>();
        expectedOrder.add(0);
        expectedOrder.add(1);
        expectedOrder.add(2);
        expectedOrder.add(3);

        Assert.assertEquals(expectedOrder, actualOrder);
    }


    @Test
    public void testDirectedPathExists() {
        final DirectedGraph g = generateDirectedGraph();
        final int rootVertex = 0; // perform search from vertex 0
        final BFS bfs = new BFS(g, rootVertex);

        Assert.assertTrue(bfs.hasPathTo(1));
        Assert.assertTrue(bfs.hasPathTo(2));
        Assert.assertTrue(bfs.hasPathTo(3));

        // vertex 4 is disconnected in the graph
        Assert.assertFalse(bfs.hasPathTo(4));
    }

    @Test
    public void testDirectedPath() {
        final DirectedGraph g = generateDirectedGraph();
        final int rootVertex = 0; // perform search from vertex 0
        final BFS bfs = new BFS(g, rootVertex);

        Assert.assertFalse(bfs.pathTo(4).isPresent());

        // verify the path from vertex 0 to vertex 3
        final Optional<Iterator<Integer>> path = bfs.pathTo(3);

        Assert.assertTrue(path.isPresent());

        final LinkedList<Integer> actualPath = new LinkedList<>();
        path.get().forEachRemaining(actualPath::add);

        final LinkedList<Integer> expectedPath = new LinkedList<>();
        expectedPath.add(0);
        expectedPath.add(1);
        expectedPath.add(3);

        Assert.assertEquals(expectedPath, actualPath);
    }

    @Test
    public void testDirectedSearch() {
        final DirectedGraph g = generateDirectedGraph();
        final int rootVertex = 0; // perform search from vertex 0

        final LinkedList<Integer> actualOrder = new LinkedList<>();
        BFS.search(g, rootVertex).forEachRemaining(actualOrder::add);

        final LinkedList<Integer> expectedOrder = new LinkedList<>();
        expectedOrder.add(0);
        expectedOrder.add(1);
        expectedOrder.add(2);
        expectedOrder.add(3);

        Assert.assertEquals(expectedOrder, actualOrder);
    }

}
