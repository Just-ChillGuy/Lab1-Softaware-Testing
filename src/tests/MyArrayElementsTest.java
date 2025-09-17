package tests;

import queue.ArrayQueue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class MyArrayElementsTest {

    private ArrayQueue queue;

    @Before
    public void setUp() {
        queue = new ArrayQueue();
    }

    @Test
    public void testIsEmptyInitially() {
        // Arrange & Act & Assert
        assertTrue("Новая очередь должна быть пустой", queue.isEmpty());
        assertEquals("Размер новой очереди = 0", 0, queue.size());
        assertNull("element() на пустой очереди должен возвращать null (по текущей реализации)", queue.element());
    }

    @Test
    public void testEnqueueAndElement() {
        // Arrange
        queue.enqueue("A");
        // Act & Assert
        assertFalse("После enqueue очередь не пуста", queue.isEmpty());
        assertEquals("size после одного enqueue = 1", 1, queue.size());
        assertEquals("element() возвращает первый элемент", "A", queue.element());
    }

    @Test
    public void testEnqueueMultipleAndDequeueOrder() {

        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");


        assertEquals("Первый dequeue = A", "A", queue.dequeue());
        assertEquals("Второй dequeue = B", "B", queue.dequeue());
        assertEquals("Третий dequeue = C", "C", queue.dequeue());


        assertTrue("Очередь должна быть пуста", queue.isEmpty());
        assertEquals("size = 0 после удаления всех элементов", 0, queue.size());
    }

    @Test(expected = AssertionError.class)
    public void testDequeueFromEmpty_throwsAssertion() {

        queue.dequeue();
    }

    @Test
    public void testClearResetsQueue() {

        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        queue.clear();

        assertTrue("После clear очередь пуста", queue.isEmpty());
        assertEquals("Размер после clear = 0", 0, queue.size());
        assertNull("element() после clear возвращает null", queue.element());
    }

    @Test
    public void testCountIfDoesNotModifyQueue() {

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);

        int evens = queue.countIf(x -> ((Integer) x) % 2 == 0);
        int greaterThanTwo = queue.countIf(x -> ((Integer) x) > 2);

        assertEquals("Чётных элементов должно быть 2 (2 и 4)", 2, evens);
        assertEquals("Элементов > 2 должно быть 2 (3 и 4)", 2, greaterThanTwo);
        assertEquals("size не должен измениться после countIf", 4, queue.size());
    }

    @Test
    public void testDedupRemovesConsecutiveDuplicates() {
        queue.enqueue("A");
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("B");
        queue.enqueue("A");

        queue.dedup();

        List<Object> out = new ArrayList<>();
        while (!queue.isEmpty()) {
            out.add(queue.dequeue());
        }

        assertEquals("После dedup последовательность должна быть [A, B, A]",
                Arrays.asList("A", "B", "A"), out);
    }

    @Test
    public void testDedupLeavesNonConsecutiveDuplicates() {

        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("A");

        queue.dedup();

        List<Object> out = new ArrayList<>();
        while (!queue.isEmpty()) {
            out.add(queue.dequeue());
        }

        assertEquals("dedup не должен удалять не подряд идущие дубликаты",
                Arrays.asList("A", "B", "A"), out);
    }

    @Test
    public void testDedupAllSameElementsReducesToOne() {

        queue.enqueue("X");
        queue.enqueue("X");
        queue.enqueue("X");
        queue.enqueue("X");

        queue.dedup();

        assertEquals("После dedup из всех одинаковых элементов останется один", 1, queue.size());
        assertEquals("Единственный элемент должен быть 'X'", "X", queue.element());
    }

    @Test
    public void testEnsureCapacityAndOrderAfterResize() {

        for (int i = 0; i < 8; i++) {
            queue.enqueue(i);
        }

        for (int i = 0; i < 8; i++) {
            Object v = queue.dequeue();
            assertEquals("Элемент после resize должен быть в FIFO-порядке", i, v);
        }

        assertTrue("После удаления всех элементов очередь пуста", queue.isEmpty());
    }
}
