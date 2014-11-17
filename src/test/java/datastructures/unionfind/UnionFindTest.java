package datastructures.unionfind;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.IntStream;

/**
 * Abstract test class for all union-find implementations. Union-find data structures should be
 * consistent in their behaviour, regardless of the underlying implementation.
 * Tests for union-find implementations should extend this class.
 */
public abstract class UnionFindTest {

    protected UnionFind uf;

    protected final static int VERTEX_COUNT = 100;

    @Before
    public abstract void initialise();

    @Test
    public void testNoUnionComponents() {
        IntStream.range(0, VERTEX_COUNT).forEach(i -> Assert.assertEquals(i, uf.find(i)));
    }

    @Test
    public void testConnectedAfterUnion() {
        final int firstVertexID = 0;
        final int secondVertexID = 1;

        uf.union(firstVertexID, secondVertexID);

        Assert.assertTrue(uf.connected(firstVertexID, secondVertexID));
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

        Assert.assertTrue(uf.connected(firstVertexID, fourthVertexID));
    }

    @Test
    public void testFindAfterUnion() {
        final int firstVertexID = 0;
        final int secondVertexID = 1;

        uf.union(firstVertexID, secondVertexID);

        Assert.assertEquals(uf.find(firstVertexID), uf.find(secondVertexID));
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

        Assert.assertEquals(uf.find(firstVertexID), uf.find(fourthVertexID));
    }



}
