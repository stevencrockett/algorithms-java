package datastructures.priorityqueue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.IntStream;

public class PriorityQueueTest {

    private static final int STARTING_SIZE = 10;

    private PriorityQueue<Integer> heap;

    @BeforeEach
    public void initialise() {
        heap = new PriorityQueue<>(STARTING_SIZE);
    }

    @Test
    public void testEmptyHeapSize() {
        Assertions.assertEquals(0, heap.size());
    }

    @Test
    public void testEmptyHeapIsEmpty() {
        Assertions.assertTrue(heap.isEmpty());
    }

    @Test
    public void testNonEmptyHeapSize() {
        final int someValue = 1;

        Assertions.assertEquals(0, heap.size());

        heap.enqueue(someValue);
        Assertions.assertEquals(1, heap.size());

        heap.enqueue(someValue);
        Assertions.assertEquals(2, heap.size());
    }

    @Test
    public void testNonEmptyHeapIsNonEmpty() {
        Assertions.assertTrue(heap.isEmpty());

        heap.enqueue(1);
        Assertions.assertFalse(heap.isEmpty());
    }

    @Test
    public void testEmptyDequeue() {
        final Optional<Integer> result = heap.dequeue();
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testSingleEnqueueDequeue() {
        final int enqueuedValue = 10;
        heap.enqueue(enqueuedValue);

        final Optional<Integer> result = heap.dequeue();
        Assertions.assertEquals(enqueuedValue, result.get().intValue());
    }

    @Test
    public void testTripleEnqueueDequeueOrderReturned() {
        final int firstValue = 13;
        final int secondValue = 23;
        final int thirdValue = 1;
        heap.enqueue(firstValue);
        heap.enqueue(secondValue);
        heap.enqueue(thirdValue);

        Assertions.assertEquals(secondValue, heap.dequeue().get().intValue());
        Assertions.assertEquals(firstValue, heap.dequeue().get().intValue());
        Assertions.assertEquals(thirdValue, heap.dequeue().get().intValue());
    }

    @Test
    public void testManyEnqueueDequeue() {
        final int limit = 100;

        // add elements out of order
        IntStream.rangeClosed(25, limit).forEach(heap::enqueue);
        IntStream.range(0, 25).forEach(heap::enqueue);

        for (int i = limit; i >= 0; i--) {
            Assertions.assertEquals(i, heap.dequeue().get().intValue());
        }
    }


}
