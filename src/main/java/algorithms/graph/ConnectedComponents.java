package algorithms.graph;

import datastructures.graph.UndirectedGraph;

/**
 * Find the connected components in an undirected graph.
 */
public class ConnectedComponents {

    /**
     * Records which vertices have been visited already.
     */
    private final boolean[] isMarked;


    /**
     * Records which vertex belongs to which component.
     */
    private final int[] components;


    /**
     * Identifies which connected component the current vertices belong to.
     * Incremented when new connected component is found, so acts as a count too.
     */
    private int componentID = 0;


    /**
     * Explore the given graph and compute connected components.
     *
     * @param g The graph to find the connected components within.
     */
    public ConnectedComponents(final UndirectedGraph g) {
        final int vertexCount = g.vertexCount();
        isMarked = new boolean[vertexCount];
        components = new int[vertexCount];

        for (int i = 0; i < vertexCount; i++) {

            // run DFS on unprocessed vertices
            if (!isMarked[i]) {

                // DFS will find all vertices within a particular component
                DFS.search(g, i).forEachRemaining(vertex -> {
                    isMarked[vertex] = true;
                    components[vertex] = componentID; // all vertices have same component ID
                });

                // increment the component ID so the next component is uniquely identified
                componentID++;
            }
        }
    }


    /**
     * Query if the given vertices are connected in the graph (i.e. in the same connected component).
     *
     * @param vertex First vertex to check.
     * @param otherVertex Second vertex to check.
     * @return <CODE>true</CODE> if the vertices are connected; <CODE>false</CODE> otherwise.
     */
    public boolean connected(final int vertex, final int otherVertex) {
        // if vertices are connected they must be within the same connected component
        return components[vertex] == components[otherVertex];
    }


    /**
     * Get the number of connected components in the graph.
     *
     * @return The number of connected components.
     */
    public int count() {
        // component ID is incremented from zero for each new component found
        return componentID;
    }


    /**
     * Get the ID of the connected component that the given vertex belongs to.
     *
     * @param vertex Vertex to find the connected component of.
     * @return ID of the connected component corresponding to the vertex.
     */
    public int id(final int vertex) {
        return components[vertex];
    }

}
