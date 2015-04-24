package datastructures.graph;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Undirected graphs using adjacency lists.
 */
public class UndirectedGraph implements Graph {

    /**
     * The number of vertices in the graph.
     */
    private final int vertexCount;


    /**
     * Array of lists, with each index corresponding to a unique vertex.
     * At each index, neighbours of the corresponding vertex are held in the list.
     */
    private final LinkedList<Integer>[] neighbours;


    /**
     *  Create a graph with a set number of vertices.
     *
     * @param vertexCount The number of vertices contained in the graph.
     */
    @SuppressWarnings("unchecked")
    public UndirectedGraph(final int vertexCount) {
        this.vertexCount = vertexCount;

        // create and initialise lists
        neighbours = new LinkedList[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            neighbours[i] = new LinkedList<>();
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void addEdge(final int vertex, final int otherVertex) {
        // add the new neighbour to the adjacency list of each vertex
        neighbours[vertex].add(otherVertex);
        neighbours[otherVertex].add(vertex);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Integer> adj(final int vertex) {
        return neighbours[vertex].iterator();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int vertexCount() {
        return vertexCount;
    }

}
