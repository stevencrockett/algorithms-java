package datastructures.unionfind;

/**
 * Weighted quick-union with path compression. Modification of the weighted quick-union implementation
 * of the union-find data structure, where connected components are represented as trees of connected vertices.
 * For any union or find operation we need to find the root of the tree a vertex is contained within. Once
 * the root is found, all vertices on the path from the original vertex to the root are updated to point
 * directly to the root. This can result in much faster tree traversals for subsequent operations.
 *
 * Fast performance across union and find operations.
 */
public class WeightedQuickUnionPC implements UnionFind {

    /**
     * Connected vertices are arranged as trees. Each vertex represented by its array index
     * contains the id of its parent in the tree.
     */
    private final int[] id;

    /**
     * For each vertex i we store the size of the tree rooted at i.
     */
    private final int[] treeSizes;


    /**
     * Create union-find data structure to store information for the specified number of vertices.
     *
     * @param vertexCount Number of vertices.
     */
    public WeightedQuickUnionPC(final int vertexCount) {
        id = new int[vertexCount];
        treeSizes = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            id[i] = i;
            treeSizes[i] = 1;
        }
    }


    /**
     * Traverses the tree up to the root node from the specified vertex to find its connected component ID.
     * Updates vertices on this path to point directly to the found root.
     *
     * @param vertexIndex Index of the vertex whose connected component will be found.
     * @return The ID of the connected component.
     */
    private int root(int vertexIndex) {

        // start by finding the root of the tree
        int rootIndex = vertexIndex;

        // do pointer chasing until vertex index in the array is equal to its value, i.e. id[i] == i
        while (rootIndex != id[rootIndex]) {
            rootIndex = id[rootIndex];
        }

        // now root has been found, retrace the path and update vertices to point to the root directly
        while (vertexIndex != rootIndex) {
            final int nextIndex = id[vertexIndex];
            id[vertexIndex] = rootIndex;
            vertexIndex = nextIndex;
        }

        return rootIndex;
    }


    /**
     * {@inheritDoc}
     *
     * Takes O(log N) time per union operation for N vertices.
     */
    @Override
    public void union(final int vertexIndex, final int otherVertexIndex) {

        // find the root of the first component, and then set it as a child of the root of the second component
        final int firstRoot = root(vertexIndex);
        final int secondRoot = root(otherVertexIndex);

        // return early if vertices are already joined
        if (firstRoot == secondRoot) {
            return;
        }

        // join the trees, the root of the smaller tree pointing to the root of the larger tree
        if (treeSizes[firstRoot] < treeSizes[secondRoot]) {
            id[firstRoot] = secondRoot;
            treeSizes[secondRoot] += treeSizes[firstRoot];
        } else {
            id[secondRoot] = firstRoot;
            treeSizes[firstRoot] += treeSizes[secondRoot];
        }
    }

    /**
     * {@inheritDoc}
     * Takes O(log N) time per union operation for N vertices.
     */
    @Override
    public int find(final int vertexIndex) {
        return root(vertexIndex);
    }


    /**
     * {@inheritDoc}
     * Takes O(log N) time per union operation for N vertices.
     */
    @Override
    public boolean connected(final int vertexIndex, final int otherVertexIndex) {
        return root(vertexIndex) == root(otherVertexIndex);
    }
}
