package datastructures.unionfind;

/**
 * Union-find implementation backed by an array. Connected components are represented as trees of connected vertices.
 * Each entry in the array - corresponding to a vertex - stores the index of its parent.
 *
 * Similar performance for union and find operations, but can be slow if the trees become too deep.
 */
public class QuickUnion implements UnionFind {

    /**
     * Connected vertices are arranged as trees. Each vertex represented by its array index
     * contains the id of its parent in the tree.
     */
    private final int[] id;


    /**
     * Create union-find data structure to store information for the specified number of vertices.
     *
     * @param vertexCount Number of vertices.
     */
    public QuickUnion(final int vertexCount) {
        id = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            id[i] = i;
        }
    }


    /**
     * Traverses the tree up to the root node from the specified vertex to find its connected component ID.
     *
     * @param vertexIndex Index of the vertex whose connected component will be found.
     * @return The ID of the connected component.
     */
    private int root(int vertexIndex) {
        // do pointer chasing until vertex index in the array is equal to its value, i.e. id[i] == i
        while (vertexIndex != id[vertexIndex]) {
            vertexIndex = id[vertexIndex];
        }

        return vertexIndex;
    }


    /**
     * {@inheritDoc}
     * Takes O(N) time per union operation for N vertices.
     */
    @Override
    public void union(final int vertexIndex, final int otherVertexIndex) {

        // find the root of the first component, and then set it as a child of the root of the second component
        final int firstRoot = root(vertexIndex);
        final int secondRoot = root(otherVertexIndex);
        id[firstRoot] = secondRoot;
    }

    /**
     * {@inheritDoc}
     * Takes O(N) time per union operation for N vertices.
     */
    @Override
    public int find(final int vertexIndex) {
        return root(vertexIndex);
    }


    /**
     * {@inheritDoc}
     * Takes O(N) time per union operation for N vertices.
     */
    @Override
    public boolean connected(final int vertexIndex, final int otherVertexIndex) {
        return root(vertexIndex) == root(otherVertexIndex);
    }
}
