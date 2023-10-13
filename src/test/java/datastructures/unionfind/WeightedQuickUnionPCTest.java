package datastructures.unionfind;

import org.junit.jupiter.api.BeforeEach;

public class WeightedQuickUnionPCTest extends UnionFindTest {

    @BeforeEach
    public void initialise() {
        uf = new WeightedQuickUnionPC(VERTEX_COUNT);
    }

}