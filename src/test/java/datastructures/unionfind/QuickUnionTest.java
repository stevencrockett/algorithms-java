package datastructures.unionfind;

import org.junit.Before;


public class QuickUnionTest extends UnionFindTest {

    @Before
    public void initialise() {
        uf = new QuickUnion(VERTEX_COUNT);
    }

}