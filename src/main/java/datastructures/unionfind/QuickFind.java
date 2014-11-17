package datastructures.unionfind;

/**
 * Union-find implementation backed by an array.
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
     */
    @Override
    public int find(final int vertexIndex) {
        return id[vertexIndex];
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean connected(final int vertexIndex, final int otherVertexIndex) {
        return id[vertexIndex] == id[otherVertexIndex];
    }
}
