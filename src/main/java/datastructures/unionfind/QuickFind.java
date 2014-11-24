package datastructures.unionfind;

/**
 * Union-find implementation backed by an array. Connected components are represented as IDs in the array.
 * Each entry in the array - corresponding to a vertex - stores the ID of its connected component.
 *
 * Optimised for efficient find operations, while unions take longer.
 */
public class QuickFind implements UnionFind {

    /**
     * Each vertex represented by its array index contains the id of its connected component.
     */
    private final int[] id;


    /**
     * Create union-find data structure to store information for the specified number of vertices.
     *
     * @param vertexCount Number of vertices.
     */
    public QuickFind(final int vertexCount) {
        id = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            id[i] = i;
        }
    }


    /**
     * {@inheritDoc}
     * Takes O(N) time per union operation for N vertices.
     */
    @Override
    public void union(final int vertexIndex, final int otherVertexIndex) {
        // get connected component IDs
        final int componentID = id[vertexIndex];
        final int otherComponentID = id[otherVertexIndex];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == otherComponentID) {
                id[i] = componentID;
            }
        }
    }

    /**
     * {@inheritDoc}
     * Takes O(1) time per find operation i.e. constant time.
     */
    @Override
    public int find(final int vertexIndex) {
        return id[vertexIndex];
    }


    /**
     * {@inheritDoc}
     * Takes O(1) time per connected operation i.e. constant time.
     */
    @Override
    public boolean connected(final int vertexIndex, final int otherVertexIndex) {
        return id[vertexIndex] == id[otherVertexIndex];
    }
}
