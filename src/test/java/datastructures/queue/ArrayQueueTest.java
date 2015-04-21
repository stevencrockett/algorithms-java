package datastructures.queue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArrayQueueTest extends QueueTest {

    private static final int STARTING_SIZE = 10;

    @Before
    public void initialise() {
        queue = new ArrayQueue<>(STARTING_SIZE);
    }

    @Test
    public void testShrinkArray() {
        final int firstValue = 10;
        final int secondValue = 20;
        final int thirdValue = 30;
        queue.enqueue(firstValue);
        queue.dequeue(); // array should shrink here

        // verify behaviour is still correct after shrinking
        queue.enqueue(secondValue);
        queue.enqueue(thirdValue);

        Assert.assertEquals(secondValue, queue.dequeue().get().intValue());
        Assert.assertEquals(thirdValue, queue.dequeue().get().intValue());
    }

}
