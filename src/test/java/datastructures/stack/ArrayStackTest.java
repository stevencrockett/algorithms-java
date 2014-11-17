package datastructures.stack;

import org.junit.Before;


public class ArrayStackTest extends StackTest {

    private static final int STARTING_SIZE = 10;

    @Before
    public void initialise() {
        stack = new ArrayStack<>(STARTING_SIZE);
    }


}
