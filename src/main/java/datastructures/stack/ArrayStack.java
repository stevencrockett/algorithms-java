package datastructures.stack;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

/**
 * Stack implementation backed by an array. The array is grown and contracted
 * depending on how full the stack is, causing elements to be copied.
 *
 * @param <T> Type of the items to be stored on the stack.
 */
public class ArrayStack<T> implements Stack<T>, Iterable<T> {

    /**
     * Default capacity of the stack if no initial size is specified.
     */
    private static final int DEFAULT_CAPACITY = 10;


    /**
     * Growth multiplier to calculate new array size when the array is full and needs to be grown.
     */
    private static final float GROWTH_RATE = 2.0f;


    /**
     * Proportion of array that needs to be full in order to shrink the array.
     */
    private static final float SHRINK_LOWER_BOUND = 0.25f;


    /**
     * The array itself which holds references to the items on the stack.
     */
    private T[] items;


    /**
     * Current number of items on the stack.
     */
    private int size = 0;


    /**
     * Size of the stack after which the stack array should be reduced in size to save memory.
     * Reduce the size of the underlying array by half when it is a quarter full.
     */
    private int lowerBound;


    public ArrayStack() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ArrayStack(final int initialCapacity) {
        items = (T[]) new Object[initialCapacity];
        lowerBound = (int) (initialCapacity * SHRINK_LOWER_BOUND);
    }


    /**
     * {@inheritDoc}
     * If the underlying array is full, the stack is copied over to a larger array
     * to accommodate the pushed item.
     */
    @Override
    public void push(final T item) {
        // check that stack has enough space. if not, first increase size and copy
        if (size == items.length) {
            items = Arrays.copyOf(items, (int) (items.length * GROWTH_RATE));
        }

        items[size] = item;
        size++;
    }


    /**
     * {@inheritDoc}
     * If the underlying array is sufficiently empty, the stack is copied over
     * to a smaller array to reduce memory consumption.
     */
    @Override
    public Optional<T> pop() {
        // first check if stack is empty
        if (size == 0) {
            return Optional.empty();
        }

        size--;
        final T item = items[size]; // get reference to item on top of the stack
        items[size] = null; // allow GC to reclaim memory

        // check if we need to reduce the size of the array
        if (size < lowerBound) {
            items = Arrays.copyOf(items, 1 + (int) (items.length * 0.5f)); // reduce array size by roughly half
            lowerBound = (int) (items.length * SHRINK_LOWER_BOUND); // compute new lower bound
        }

        return Optional.of(item);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> peek() {

        if (size == 0) {
            return Optional.empty();
        }

        return Optional.of(items[size - 1]);
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
        return new ArrayStackIterator();
    }


    /**
     * An iterator that iterates through the stack, starting at the top.
     */
    private class ArrayStackIterator implements Iterator<T> {

        int currentIndex = size;

        @Override
        public boolean hasNext() {
            return currentIndex > 0;
        }

        @Override
        public T next() {
            currentIndex--;
            return items[currentIndex];
        }
    }
}
