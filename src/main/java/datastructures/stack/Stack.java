package datastructures.stack;

import java.util.Optional;

/**
 * A stack, allowing elements to be pushed on and popped off the stack in LIFO order.
 *
 * @param <T> Type of the items to be stored on the stack.
 */
public interface Stack<T> extends Iterable<T> {

    /**
     * Adds an item on to the top of the stack.
     *
     * @param item Object to be pushed.
     */
    void push(final T item);


    /**
     * Removes the item from the top of the stack and returns it.
     * Stack may be empty, in which case the Optional is empty.
     *
     * @return Optional containing the item on top of the stack if present.
     */
    Optional<T> pop();


    /**
     * Get the item currently on top of the stack without removing it.
     * Stack may be empty, in which case the Optional is empty.
     *
     * @return Optional containing the item on top of the stack if present.
     */
    Optional<T> peek();


    /**
     * Counts the number of items in the stack.
     *
     * @return The number of items in the stack.
     */
    int size();


    /**
     * Tests if there are no items on the stack.
     *
     * @return <CODE>true</CODE> if there are no items on the stack; <CODE>false</CODE> otherwise.
     */
    boolean isEmpty();

}
