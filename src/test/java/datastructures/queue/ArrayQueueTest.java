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

    @Test
    public void testSmallArray() {

        // when dequeuing from a queue with a backing array of size 1, check that the
        // backing array doesn't get shrunk to size 0 (so no new elements can be enqueued).

        // create queue with array of size 1
        final ArrayQueue<Integer> smallQueue = new ArrayQueue<>(1);

        // add and remove single element
        final int someInt = 2;
        smallQueue.enqueue(someInt);
        smallQueue.dequeue();

        // check that we can still use the queue normally
        final int otherInt = 5;
        smallQueue.enqueue(otherInt);
        Assert.assertEquals(otherInt, smallQueue.dequeue().get().intValue());
    }

}
