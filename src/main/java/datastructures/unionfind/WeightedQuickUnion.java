package datastructures.unionfind;

/**
 * Modification of the quick-union implementation of the union-find data structure, where
 * connected components are represented as trees of connected vertices.
 * The size of each tree is stored, and this information is used to limit the overall depth of any tree, making
 * it faster to find the root of any tree, resulting in faster union and find operations.
 *
 * Fast performance across union and find operations.
 */
public class WeightedQuickUnion implements UnionFind {

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
    public WeightedQuickUnion(final int vertexCount) {
        id = new int[vertexCount];
        treeSizes = new int[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            id[i] = i;
            treeSizes[i] = 1;
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
