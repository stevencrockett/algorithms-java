package datastructures.stack;

import java.util.Iterator;
import java.util.Optional;

/**
 * Stack implementation backed by a linked list. Pushed items are inserted/removed from the front of the list
 * for push/pop operations respectively.
 */
public class ListStack<T> implements Stack<T> {

    /**
     * Pointer to the start of the linked list which contains the items.
     */
    private Node firstItem;


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
     * Current number of items on the stack.
     */
    private int size = 0;


    public ListStack() {
        firstItem = null;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void push(final T item) {

        // create new node in the list that points to the existing list
        final Node newItem = new Node(item, firstItem);

        //set the new node as the start of the list
        firstItem = newItem;
        size++;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> pop() {
        // first check if stack is empty
        if (size == 0) {
            return Optional.empty();
        }

        // retrieve the item from the first node in the list
        final T item = firstItem.item;

        // the next node should now become the start of the list
        firstItem = firstItem.next;
        size--;

        return Optional.of(item);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> peek() {
        if (size > 0) {
            return Optional.of(firstItem.item);
        } else {
            return Optional.empty();
        }
    }


    /**
     * {@inheritDoc}
     * This methods takes constant time.
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
        return new ListStackIterator();
    }


    /**
     * An iterator that iterates through the stack, starting at the top.
     */
    private class ListStackIterator implements Iterator<T> {

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
