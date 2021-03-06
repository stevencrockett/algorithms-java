package datastructures.priorityqueue;

import java.util.Arrays;
import java.util.Optional;

/**
 * Implementation of a priority queue, using a binary heap. Acts as a max heap.
 *
 * @param <T> Type of the items to be stored in the priority queue which can be compared and ordered.
 */
public class PriorityQueue<T extends Comparable<T>> {

    /**
     * Default capacity of the heap if no initial size is specified.
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
     * The array itself, interpreted as a heap, which holds references to the items in the priority queue.
     */
    private T[] items;


    /**
     * Index of the next free element in the heap.
     */
    private int size = 0;


    /**
     * Size of the stack after which the stack array should be reduced in size to save memory.
     * Reduce the size of the underlying array by half when it is a quarter full.
     */
    private int lowerBound;


    public PriorityQueue() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public PriorityQueue(final int initialCapacity) {
        items = (T[]) new Comparable[initialCapacity];
        lowerBound = (int) (initialCapacity * SHRINK_LOWER_BOUND);
    }


    /**
     * Adds an item to the priority queue.
     *
     * @param item Item to add to the priority queue.
     */
    public void enqueue(final T item) {
        // check that the heap has enough space. if not, first increase size and copy
        if (size == items.length) {
            items = Arrays.copyOf(items, (int) (items.length * GROWTH_RATE));
        }

        items[size] = item; // append item to the end
        swim(size); // swim item up if needed to ensure heap order

        size++; // new element should be placed at the next free index
    }


    /**
     * Removes the highest valued item from the priority queue and returns it.
     *
     * @return Optional containing the item with highest value if present.
     */
    public Optional<T> dequeue() {

        if (size == 0) {
            return Optional.empty();
        }

        size--;

        final T maxItem = items[0]; // get value on top of the heap (with max value)
        items[0] = items[size]; // copy the last item into the head position
        items[size] = null; // allow GC to reclaim memory

        sink(0); // sink item if needed to ensure heap order

        // check if we need to reduce the size of the array
        if (size <= lowerBound) {
            items = Arrays.copyOf(items, (int) (items.length * 0.5f)); // reduce array size by half
            lowerBound = (int) (items.length * SHRINK_LOWER_BOUND); // compute new lower bound
        }

        return Optional.of(maxItem);
    }


    /**
     * Potentially moves the item at the given index up through the heap such that the item is
     * not greater than its parent, keeping in line with the heap order property.
     *
     * @param index Index of the item to try and move up the heap.
     */
    private void swim(int index) {

        int parent = (index - 1) / 2;
        while (index > 0 && items[index].compareTo(items[parent]) > 0) {

            final T temp = items[index];
            items[index] = items[parent];
            items[parent] = temp;

            index = parent;
            parent = (index - 1) / 2;
        }
    }


    /**
     * Potentially moves the item at the given index down through the heap such that the item is
     * not smaller than any of its children, keeping in line with the heap order property. If the item
     * is smaller than both its children, the the item will be swapped with the larger of the two.
     *
     * @param index Index of the item to try and move down the heap.
     */
    private void sink(int index) {

        int childIndex = (index * 2) + 1;
        while (childIndex < size) {

            // check if right child is strictly greater than the left child
            if (childIndex + 1 < size && items[childIndex].compareTo(items[childIndex + 1]) < 0) {
                childIndex++;
            }

            // if the parent isn't smaller than its largest child then we can stop early
            if (items[index].compareTo(items[childIndex]) > -1) {
                break;
            }

            // otherwise we swap the parent and child and continue down the heap
            final T temp = items[index];
            items[index] = items[childIndex];
            items[childIndex] = temp;

            index = childIndex;
            childIndex = (index * 2) + 1;
        }
    }


    /**
     * Counts the number of items in the priority queue.
     *
     * @return The number of items in the priority queue.
     */
    public int size() {
        return size;
    }


    /**
     * Tests if there are no items in the priority queue.
     *
     * @return <CODE>true</CODE> if there are no items in the priority queue; <CODE>false</CODE> otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }
}
