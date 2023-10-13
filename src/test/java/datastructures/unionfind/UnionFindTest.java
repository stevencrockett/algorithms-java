package datastructures.unionfind;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

/**
 * Abstract test class for all union-find implementations. Union-find data structures should be
 * consistent in their behaviour, regardless of the underlying implementation.
 * Tests for union-find implementations should extend this class.
 */
public abstract class UnionFindTest {

    protected UnionFind uf;

    protected final static int VERTEX_COUNT = 100;

    @BeforeEach
    public abstract void initialise();

    @Test
    public void testNoUnionComponents() {
        IntStream.range(0, VERTEX_COUNT).forEach(i -> Assertions.assertEquals(i, uf.find(i)));
    }

    @Test
    public void testConnectedAfterUnion() {
        final int firstVertexID = 0;
        final int secondVertexID = 1;

        uf.union(firstVertexID, secondVertexID);

        Assertions.assertTrue(uf.connected(firstVertexID, secondVertexID));
    }

    @Test
    public void testConnectedAfterUnions() {
        final int firstVertexID = 0;
        final int secondVertexID = 1;
        final int thirdVertexID = 2;
        final int fourthVertexID = 3;

        uf.union(firstVertexID, secondVertexID);
        uf.union(thirdVertexID, fourthVertexID);
        uf.union(secondVertexID, thirdVertexID);

        Assertions.assertTrue(uf.connected(firstVertexID, fourthVertexID));
    }

    @Test
    public void testFindAfterUnion() {
        final int firstVertexID = 0;
        final int secondVertexID = 1;

        uf.union(firstVertexID, secondVertexID);

        Assertions.assertEquals(uf.find(firstVertexID), uf.find(secondVertexID));
    }

    @Test
    public void testFindAfterUnions() {
        final int firstVertexID = 0;
        final int secondVertexID = 1;
        final int thirdVertexID = 2;
        final int fourthVertexID = 3;

        uf.union(firstVertexID, secondVertexID);
        uf.union(thirdVertexID, fourthVertexID);
        uf.union(secondVertexID, thirdVertexID);

        Assertions.assertEquals(uf.find(firstVertexID), uf.find(fourthVertexID));
    }



}
