package datastructures.unionfind;

import org.junit.jupiter.api.BeforeEach;

public class WeightedQuickUnionTest extends UnionFindTest {

    @BeforeEach
    public void initialise() {
        uf = new WeightedQuickUnion(VERTEX_COUNT);
    }

}