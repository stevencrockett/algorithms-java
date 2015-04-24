package datastructures.graph;

import java.util.Iterator;

/**
 * A graph, consisting vertices and edges which connect them.
 * Vertices are represented by indices typically from 0.
 */
public interface Graph {

    /**
     * Add an edge between the specified vertices to the graph.
     *
     * @param vertex First vertex in the pair to add an edge between.
     * @param otherVertex Second vertex in the pair to add an edge between.
     */
    void addEdge(int vertex, int otherVertex);


    /**
     * Provide an iterator over the indices of vertices adjacent to the specified vertex.
     *
     * @param vertex Vertex to get the neighbours of.
     * @return An iterator over vertices adjacent to the specified vertex.
     */
    Iterator<Integer> adj(int vertex);


    /**
     * Count the number of vertices in the graph.
     *
     * @return The number of vertices contained in the graph.
     */
    int vertexCount();

}
