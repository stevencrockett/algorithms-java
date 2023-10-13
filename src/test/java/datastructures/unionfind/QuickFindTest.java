package datastructures.unionfind;

import org.junit.jupiter.api.BeforeEach;

public class QuickFindTest extends UnionFindTest {

    @BeforeEach
    public void initialise() {
        uf = new QuickFind(VERTEX_COUNT);
    }

}
