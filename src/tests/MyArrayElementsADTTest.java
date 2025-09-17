package tests;

import queue.ArrayQueueADT;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyArrayElementsADTTest {

    private ArrayQueueADT queue;

    @Before
    public void setUp() {
        queue = new ArrayQueueADT();
    }

    @Test
    public void testIsEmptyInitially() {
        assertTrue(ArrayQueueADT.isEmpty(queue));
        assertEquals(0, ArrayQueueADT.size(queue));
        assertNull(ArrayQueueADT.element(queue));
    }

    @Test
    public void testEnqueueAndElement() {
        ArrayQueueADT.enqueue(queue, "A");

        assertFalse(ArrayQueueADT.isEmpty(queue));
        assertEquals(1, ArrayQueueADT.size(queue));
        assertEquals("A", ArrayQueueADT.element(queue));
    }

    @Test
    public void testEnqueueMultipleAndDequeueOrder() {
        ArrayQueueADT.enqueue(queue, "A");
        ArrayQueueADT.enqueue(queue, "B");
        ArrayQueueADT.enqueue(queue, "C");

        assertEquals("A", ArrayQueueADT.dequeue(queue));
        assertEquals("B", ArrayQueueADT.dequeue(queue));
        assertEquals("C", ArrayQueueADT.dequeue(queue));

        assertTrue(ArrayQueueADT.isEmpty(queue));
    }

    @Test(expected = AssertionError.class)
    public void testDequeueFromEmpty_throwsAssertion() {
        ArrayQueueADT.dequeue(queue);
    }

    @Test
    public void testClearResetsQueue() {
        ArrayQueueADT.enqueue(queue, "A");
        ArrayQueueADT.enqueue(queue, "B");
        ArrayQueueADT.enqueue(queue, "C");

        ArrayQueueADT.clear(queue);

        assertTrue(ArrayQueueADT.isEmpty(queue));
        assertEquals(0, ArrayQueueADT.size(queue));
        assertNull(ArrayQueueADT.element(queue));
    }

    @Test
    public void testCountIfDoesNotModifyQueue() {
        ArrayQueueADT.enqueue(queue, 1);
        ArrayQueueADT.enqueue(queue, 2);
        ArrayQueueADT.enqueue(queue, 3);
        ArrayQueueADT.enqueue(queue, 4);

        int evens = ArrayQueueADT.countIf(queue, x -> ((Integer) x) % 2 == 0);
        int greaterThanTwo = ArrayQueueADT.countIf(queue, x -> ((Integer) x) > 2);

        assertEquals(2, evens);          // 2 и 4
        assertEquals(2, greaterThanTwo); // 3 и 4
        assertEquals(4, ArrayQueueADT.size(queue));
    }

    @Test
    public void testEnsureCapacityAndOrderAfterResize() {
        for (int i = 0; i < 8; i++) {
            ArrayQueueADT.enqueue(queue, i);
        }

        for (int i = 0; i < 8; i++) {
            Object v = ArrayQueueADT.dequeue(queue);
            assertEquals(i, v);
        }

        assertTrue(ArrayQueueADT.isEmpty(queue));
    }
}
