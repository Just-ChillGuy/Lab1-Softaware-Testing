package tests;

import queue.ArrayQueueModule;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MyArrayElementsModuleTest {

    @Before
    public void setUp() {
        ArrayQueueModule.clear();
    }

    @Test
    public void testIsEmptyInitially() {
        assertTrue(ArrayQueueModule.isEmpty());
        assertEquals(0, ArrayQueueModule.size());
        assertNull(ArrayQueueModule.element());
    }

    @Test
    public void testEnqueueAndElement() {
        ArrayQueueModule.enqueue("A");

        assertFalse(ArrayQueueModule.isEmpty());
        assertEquals(1, ArrayQueueModule.size());
        assertEquals("A", ArrayQueueModule.element());
    }

    @Test
    public void testEnqueueMultipleAndDequeueOrder() {
        ArrayQueueModule.enqueue("A");
        ArrayQueueModule.enqueue("B");
        ArrayQueueModule.enqueue("C");

        assertEquals("A", ArrayQueueModule.dequeue());
        assertEquals("B", ArrayQueueModule.dequeue());
        assertEquals("C", ArrayQueueModule.dequeue());

        assertTrue(ArrayQueueModule.isEmpty());
    }

    @Test(expected = AssertionError.class)
    public void testDequeueFromEmpty_throwsAssertion() {
        ArrayQueueModule.dequeue();
    }

    @Test
    public void testClearResetsQueue() {
        ArrayQueueModule.enqueue("A");
        ArrayQueueModule.enqueue("B");
        ArrayQueueModule.enqueue("C");

        ArrayQueueModule.clear();

        assertTrue(ArrayQueueModule.isEmpty());
        assertEquals(0, ArrayQueueModule.size());
        assertNull(ArrayQueueModule.element());
    }

    @Test
    public void testCountIfDoesNotModifyQueue() {
        ArrayQueueModule.enqueue(1);
        ArrayQueueModule.enqueue(2);
        ArrayQueueModule.enqueue(3);
        ArrayQueueModule.enqueue(4);

        int evens = ArrayQueueModule.countIf(x -> ((Integer) x) % 2 == 0);
        int greaterThanTwo = ArrayQueueModule.countIf(x -> ((Integer) x) > 2);

        assertEquals(2, evens);          // 2 и 4
        assertEquals(2, greaterThanTwo); // 3 и 4
        assertEquals(4, ArrayQueueModule.size());
    }

    @Test
    public void testEnsureCapacityAndOrderAfterResize() {
        for (int i = 0; i < 8; i++) {
            ArrayQueueModule.enqueue(i);
        }

        List<Object> out = new ArrayList<>();
        while (!ArrayQueueModule.isEmpty()) {
            out.add(ArrayQueueModule.dequeue());
        }

        assertEquals(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7), out);
        assertTrue(ArrayQueueModule.isEmpty());
    }
}
