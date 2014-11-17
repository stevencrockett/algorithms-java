package datastructures.unionfind;

import org.junit.Before;


public class QuickFindTest extends UnionFindTest {

    @Before
    public void initialise() {
        uf = new QuickFind(VERTEX_COUNT);
    }

}
