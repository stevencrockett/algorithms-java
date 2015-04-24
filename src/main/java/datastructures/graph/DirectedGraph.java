package datastructures.graph;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Directed graphs using adjacency lists.
 */
public class DirectedGraph implements Graph {

    /**
     * The number of vertices in the graph.
     */
    private final int vertexCount;


    /**
     * Array of lists, with each index corresponding to a unique vertex.
     * At each index, vertices reachable from the corresponding vertex are held in the list.
     */
    private final LinkedList<Integer>[] outNeighbours;


    /**
     *  Create a graph with a set number of vertices.
     *
     * @param vertexCount The number of vertices contained in the graph.
     */
    @SuppressWarnings("unchecked")
    public DirectedGraph(final int vertexCount) {
        this.vertexCount = vertexCount;

        // create and initialise lists
        outNeighbours = new LinkedList[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            outNeighbours[i] = new LinkedList<>();
        }
    }


    /**
     * Add an arc (directed edge) between the specified vertices.
     *
     * @param vertex Vertex to add an arc from.
     * @param otherVertex Vertex to add an arc to.
     */
    @Override
    public void addEdge(final int vertex, final int otherVertex) {
        // otherVertex can now be reached from vertex using the new arc
        outNeighbours[vertex].add(otherVertex);
    }


    /**
     * Provide an iterator over the indices of vertices which are out-neighbours of the specified vertex.
     *
     * @param vertex Vertex to get the out-neighbours of.
     * @return An iterator over out-neighbours of the specified vertex.
     */
    @Override
    public Iterator<Integer> adj(final int vertex) {
        return outNeighbours[vertex].iterator();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int vertexCount() {
        return vertexCount;
    }

}
