package datastructures.queue;

import org.junit.jupiter.api.BeforeEach;

public class ListQueueTest extends QueueTest {

    @BeforeEach
    public void initialise() {
        queue = new ListQueue<>();
    }
}
