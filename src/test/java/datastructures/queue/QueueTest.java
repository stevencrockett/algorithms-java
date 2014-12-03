package datastructures.queue;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

/**
 * Abstract test class for all Queue implementations. Queues should be consistent in their
 * behaviour, regardless of the underlying implementation.
 * Tests for Queue implementations should extend this class.
 */
public abstract class QueueTest {

    protected Queue<Integer> queue;

    @Test
    public void testEmptyQueueSize() {
        Assert.assertEquals(0, queue.size());
    }

    @Test
    public void testEmptyQueueIsEmpty() {
        Assert.assertTrue(queue.isEmpty());
    }

    @Test
    public void testNonEmptyQueueSize() {
        final int someValue = 1;

        Assert.assertEquals(0, queue.size());

        queue.enqueue(someValue);
        Assert.assertEquals(1, queue.size());

        queue.enqueue(someValue);
        Assert.assertEquals(2, queue.size());
    }

    @Test
    public void testNonEmptyQueueIsNonEmpty() {
        Assert.assertTrue(queue.isEmpty());

        queue.enqueue(1);
        Assert.assertFalse(queue.isEmpty());
    }

    @Test
    public void testEmptyDequeue() {
        final Optional<Integer> result = queue.dequeue();
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void testSingleEnqueueDequeue() {
        final int enqueuedValue = 10;
        queue.enqueue(enqueuedValue);

        final Optional<Integer> result = queue.dequeue();
        Assert.assertEquals(enqueuedValue, result.get().intValue());
    }

    @Test
    public void testDoubleEnqueueDequeue() {
        final int firstValue = 10;
        final int secondValue = 20;
        queue.enqueue(firstValue);
        queue.enqueue(secondValue);

        Assert.assertEquals(firstValue, queue.dequeue().get().intValue());
        Assert.assertEquals(secondValue, queue.dequeue().get().intValue());
    }

    @Test
    public void testManyEnqueueDequeue() {
        final int limit = 100;

        for (int i = 0; i < limit; i++) {
            queue.enqueue(i);
        }

        for (int i = 0; i < limit; i++) {
            Assert.assertEquals(i, queue.dequeue().get().intValue());
        }
    }

}
