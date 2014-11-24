package datastructures.unionfind;

import org.junit.Before;


public class WeightedQuickUnionPCTest extends UnionFindTest {

    @Before
    public void initialise() {
        uf = new WeightedQuickUnionPC(VERTEX_COUNT);
    }

}