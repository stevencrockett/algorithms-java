package datastructures.stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Abstract test class for all Stack implementations. Stacks should be consistent in their
 * behaviour, regardless of the underlying implementation.
 * Tests for Stack implementations should extend this class.
 */
public abstract class StackTest {

    protected Stack<Integer> stack;

    @BeforeEach
    public abstract void initialise();

    @Test
    public void testEmptyStackSize() {
        Assertions.assertEquals(0, stack.size());
    }

    @Test
    public void testEmptyStackIsEmpty() {
        Assertions.assertTrue(stack.isEmpty());
    }

    @Test
    public void testNonEmptyStackSize() {
        final int someValue = 1;

        Assertions.assertEquals(0, stack.size());

        stack.push(someValue);
        Assertions.assertEquals(1, stack.size());

        stack.push(someValue);
        Assertions.assertEquals(2, stack.size());
    }

    @Test
    public void testNonEmptyStackIsNonEmpty() {
        Assertions.assertTrue(stack.isEmpty());

        stack.push(1);
        Assertions.assertFalse(stack.isEmpty());
    }

    @Test
    public void testEmptyPop() {
        final Optional<Integer> result = stack.pop();
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void testSinglePushPop() {
        final int pushedValue = 10;
        stack.push(pushedValue);

        final Optional<Integer> result = stack.pop();
        Assertions.assertEquals(pushedValue, result.get().intValue());
    }

    @Test
    public void testDoublePushPop() {
        final int firstValue = 10;
        final int secondValue = 20;
        stack.push(firstValue);
        stack.push(secondValue);

        Assertions.assertEquals(secondValue, stack.pop().get().intValue());
        Assertions.assertEquals(firstValue, stack.pop().get().intValue());
    }

    @Test
    public void testManyPushPop() {
        final int limit = 100;

        for (int i = 0; i < limit; i++) {
            stack.push(i);
        }

        for (int i = limit - 1; i >= 0; i--) {
            Assertions.assertEquals(i, stack.pop().get().intValue());
        }
    }

    @Test
    public void testPeekEmptyStack() {
        final Optional<Integer> noValue = stack.peek();
        Assertions.assertFalse(noValue.isPresent());
    }

    @Test
    public void testPeekStack() {
        final int pushedValue = 50;
        stack.push(pushedValue);

        final Optional<Integer> value = stack.peek();
        Assertions.assertEquals(pushedValue, value.get().intValue());
    }

    @Test
    public void testStackIterator() {

        final int numItems = 10;

        IntStream.range(0, numItems).forEach(stack::push);

        int expectedItem = numItems - 1;

        for (Integer i : stack) {
            Assertions.assertEquals(expectedItem, stack.pop().get().intValue());
            expectedItem--;
        }
    }

}
