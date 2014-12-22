package datastructures.queue;

import org.junit.Before;

public class ArrayQueueTest extends QueueTest {

    private static final int STARTING_SIZE = 10;

    @Before
    public void initialise() {
        queue = new ArrayQueue<>(STARTING_SIZE);
    }

}
