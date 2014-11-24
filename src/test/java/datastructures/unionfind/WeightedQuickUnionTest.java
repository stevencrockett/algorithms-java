package datastructures.unionfind;

import org.junit.Before;


public class WeightedQuickUnionTest extends UnionFindTest {

    @Before
    public void initialise() {
        uf = new WeightedQuickUnion(VERTEX_COUNT);
    }

}