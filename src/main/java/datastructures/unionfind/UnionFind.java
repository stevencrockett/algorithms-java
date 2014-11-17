package datastructures.unionfind;

/**
 * A union-find data structure, allowing dynamic connectivity problems to be solved efficiently.
 * The data structure models connections between vertices on an undirected graph, allowing
 * new connections between vertices to be established (the union operation), and to find out which
 * set (or connected component) a particular vertex belongs in (the find operation).
 */
public interface UnionFind {

    /**
     * Adds a connection in the graph between the two specified vertices.
     *
     * @param vertexIndex Index of the first vertex to be connected.
     * @param otherVertexIndex Index of the other vertex to be connected.
     */
    public void union(final int vertexIndex, final int otherVertexIndex);


    /**
     * Returns the ID of the connected component that the specified vertex is within.
     * Vertices within the same connected component will have the same ID.
     *
     * @param vertexIndex Index of the vertex whose connected component will be found.
     * @return The ID of the connected component.
     */
    public int find(final int vertexIndex);


    /**
     * Checks if the two specified vertices are connected.
     *
     * @param vertexIndex Index of the first vertex.
     * @param otherVertexIndex Index of the other vertex.
     * @return <CODE>true</CODE> if the two vertices are connected; <CODE>false</CODE> otherwise.
     */
    public boolean connected(final int vertexIndex, final int otherVertexIndex);

}
