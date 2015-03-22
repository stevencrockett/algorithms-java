package datastructures.queue;

import java.util.Iterator;
import java.util.Optional;

/**
 * Queue implementation backed by a linked list. Pointers to the front and back of
 * the queue are maintained to support enqueue/dequeue operations.
 *
 * @param <T> Type of the items to be stored in the queue.
 */
public class ListQueue<T> implements Queue<T> {

    /**
     * Pointer to the start of the linked list which contains the items.
     */
    private Node firstItem;


    /**
     * Pointer to the end of the linked list which contains the items.
     */
    private Node lastItem;


    /**
     * A node represents an individual element in the linked list, containing the item itself and
     * a pointer to the next node.
     */
    private class Node {
        public T item;
        public Node next;

        public Node(final T item, final Node next) {
            this.item = item;
            this.next = next;
        }
    }


    /**
     * Current number of items in the queue.
     */
    private int size = 0;


    public ListQueue() {
        firstItem = null;
        lastItem = null;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void enqueue(final T item) {
        final Node newNode = new Node(item, null);

        if (size > 0) {
            lastItem.next = newNode; // point the previous last node to the new last node
            lastItem = newNode; // update pointer to the back of the queue
        } else {
            // if the queue is empty then the new item is both first and last.
            firstItem = newNode;
            lastItem = newNode;
        }

        size++;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> dequeue() {

        if (size == 0) {
            return Optional.empty();
        }

        final T item = firstItem.item; // record first item
        firstItem = firstItem.next; // move pointer along to the next item in the queue
        size--;
        return Optional.of(item);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new ListQueueIterator();
    }


    /**
     * An iterator that iterates through the queue, starting at the front.
     */
    private class ListQueueIterator implements Iterator<T> {

        Node currentItem = firstItem;

        @Override
        public boolean hasNext() {
            return currentItem.next != null;
        }

        @Override
        public T next() {
            final T item = currentItem.item;
            currentItem = currentItem.next;
            return item;
        }
    }
}
