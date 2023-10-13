package datastructures.unionfind;

import org.junit.jupiter.api.BeforeEach;

public class QuickUnionTest extends UnionFindTest {

    @BeforeEach
    public void initialise() {
        uf = new QuickUnion(VERTEX_COUNT);
    }

}