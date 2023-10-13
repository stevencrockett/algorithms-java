package datastructures.queue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Abstract test class for all Queue implementations. Queues should be consistent in their
 * behaviour, regardless of the underlying implementation.
 * Tests for Queue implementations should extend this class.
 */
public abstract class QueueTest {

    protected Queue<Integer> queue;

    @Test
    public void testEmptyQueueSize() {
        Assertions.assertEquals(0, queue.size());
    }

    @Test
    public void testEmptyQueueIsEmpty() {
        Assertions.assertTrue(queue.isEmpty());
    }

    @Test
    public void testNonEmptyQueueSize() {
        final int someValue = 1;

        Assertions.assertEquals(0, queue.size());

        queue.enqueue(someValue);
        Assertions.assertEquals(1, queue.size());

        queue.enqueue(someValue);
        Assertions.assertEquals(2, queue.size());
    }

    @Test
    public void testNonEmptyQueueIsNonEmpty() {
        Assertions.assertTrue(queue.isEmpty());

        queue.enqueue(1);
        Assertions.assertFalse(queue.isEmpty());
    }

    @Test
    public void testEmptyDequeue() {
        final Optional<Integer> result = queue.dequeue();
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testSingleEnqueueDequeue() {
        final int enqueuedValue = 10;
        queue.enqueue(enqueuedValue);

        final Optional<Integer> result = queue.dequeue();
        Assertions.assertEquals(enqueuedValue, result.get().intValue());
    }

    @Test
    public void testDoubleEnqueueDequeue() {
        final int firstValue = 10;
        final int secondValue = 20;
        queue.enqueue(firstValue);
        queue.enqueue(secondValue);

        Assertions.assertEquals(firstValue, queue.dequeue().get().intValue());
        Assertions.assertEquals(secondValue, queue.dequeue().get().intValue());
    }

    @Test
    public void testManyEnqueueDequeue() {
        final int limit = 100;

        for (int i = 0; i < limit; i++) {
            queue.enqueue(i);
        }

        for (int i = 0; i < limit; i++) {
            Assertions.assertEquals(i, queue.dequeue().get().intValue());
        }
    }

    @Test
    public void testQueueIterator() {

        final int numItems = 10;

        IntStream.range(0, numItems).forEach(queue::enqueue);

        IntStream.range(0, numItems).forEach(expectedItem ->
                Assertions.assertEquals(expectedItem, queue.dequeue().get().intValue()));
    }

}
