package datastructures.queue;

import java.util.Optional;

/**
 * A queue, allowing elements to be enqueued and dequeued in FIFO order.
 *
 * @param <T> Type of the items to be stored in the queue.
 */
public interface Queue<T> extends Iterable<T> {

    /**
     * Adds an item to the back of the queue.
     *
     * @param item Object to be added.
     */
    void enqueue(final T item);


    /**
     * Removes the item from the front of the queue and returns it.
     * Queue may be empty, in which case the Optional is empty.
     *
     * @return Optional containing the item at the front of the queue if present.
     */
    Optional<T> dequeue();


    /**
     * Counts the number of items in the queue.
     *
     * @return The number of items in the queue.
     */
    int size();


    /**
     * Tests if there are no items in the queue.
     *
     * @return <CODE>true</CODE> if there are no items in the queue; <CODE>false</CODE> otherwise.
     */
    boolean isEmpty();
}
