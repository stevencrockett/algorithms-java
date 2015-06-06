package datastructures.stack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArrayStackTest extends StackTest {

    private static final int STARTING_SIZE = 10;

    @Before
    public void initialise() {
        stack = new ArrayStack<>(STARTING_SIZE);
    }

    @Test
    public void testShrinkArray() {
        final int firstValue = 10;
        final int secondValue = 20;
        final int thirdValue = 30;
        stack.push(firstValue);
        stack.pop(); // array should shrink here

        // verify behaviour is still correct after shrinking
        stack.push(secondValue);
        stack.push(thirdValue);

        Assert.assertEquals(thirdValue, stack.pop().get().intValue());
        Assert.assertEquals(secondValue, stack.pop().get().intValue());
    }

    @Test
    public void testSmallArray() {

        // when poping from a stack with a backing array of size 1, check that the
        // backing array doesn't get shrunk to size 0 (so no new elements can be pushed).

        // create stack with array of size 1
        final ArrayStack<Integer> smallStack = new ArrayStack<>(1);

        // add and remove single element
        final int someInt = 2;
        smallStack.push(someInt);
        smallStack.pop();

        // check that we can still use the stack normally
        final int otherInt = 5;
        smallStack.push(otherInt);
        Assert.assertEquals(otherInt, smallStack.pop().get().intValue());
    }

}
