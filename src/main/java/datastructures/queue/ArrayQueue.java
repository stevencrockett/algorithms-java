package datastructures.queue;

import java.util.Optional;

/**
 * Queue implementation backed by an array. The array is grown and contracted
 * depending on how full the queue is, causing elements to be copied.
 */
public class ArrayQueue<T> implements Queue<T> {

    /**
     * Default capacity of the queue if no initial size is specified.
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
     * The array itself which holds references to the items in the queue.
     */
    private T[] items;


    /**
     * Current number of items in the queue.
     */
    private int size = 0;


    /**
     * Size of the queue after which the queue array should be reduced in size to save memory.
     * Reduce the size of the underlying array by half when it is a quarter full.
     */
    private int lowerBound;


    /**
     * Index that represents the front of the queue.
     */
    private int frontIndex;


    /**
     * Index of the back of the queue.
     */
    private int backIndex;


    public ArrayQueue() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayQueue(final int initialCapacity) {
        items = (T[]) new Object[initialCapacity];
        lowerBound = (int) (initialCapacity * SHRINK_LOWER_BOUND);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void enqueue(T item) {

        if (size > 0) {
            // if the array is already at capacity, first grow the array to make space
            if (size == items.length) {

                final int capacity = items.length;

                // copy queue into a larger array, the head of the queue starting at index 0.
                final T[] newArray = (T[]) new Object[(int) (items.length * GROWTH_RATE)];
                for (int i = 0; i < size; i++) {
                    newArray[i] = items[(frontIndex + i) % items.length];
                }
                items = newArray;

                // update indices
                frontIndex = 0;
                backIndex = size - 1;
            }

            // move backpointer along to the next location in the array where the new item will go
            backIndex = (backIndex + 1) % items.length;
            items[backIndex] = item;

        } else {
            // if the queue is empty then the front index == back index.
            items[frontIndex] = item;
        }

        size++;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> dequeue() {
        if (size > 0) {
            // get the head of the queue
            final T item = items[frontIndex];
            items[frontIndex] = null; // allow GC to reclaim memory
            frontIndex = (frontIndex + 1) % items.length;
            size--;

            // if the array is sufficiently empty, shrink the array to reduce memory
            if (size <= lowerBound) {
                final int capacity = items.length;
                final int newCapacity = (int) (capacity * 0.5f);

                // copy queue into a smaller array, the head of the queue starting at index 0.
                final T[] newArray = (T[]) new Object[newCapacity];
                for (int i = 0; i < size; i++) {
                    newArray[i] = items[(frontIndex + i) % capacity];
                }
                items = newArray;

                // update indices
                frontIndex = 0;
                backIndex = size - 1;

                lowerBound = (int) (newCapacity * SHRINK_LOWER_BOUND); // compute new lower bound
            }

            return Optional.of(item);
        } else {
            return Optional.empty();
        }
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


}
