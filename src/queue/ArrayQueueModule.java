package queue;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

// Model: a[head]..a[size]
// Inv: size >= 0 && size <= a.length && forall i=1..size - 1: a[i] != null
// Let: immutable(k): forall i=1..k - 1
public class ArrayQueueModule {
    private static int size = 0;
    private static int head = 0;

    private static Object[] elements = new Object[5];


    //Pre: true
    //Post: if (size == elements.length) { elements'.length = elements.length * 2 } && immutable(size)
    private static void ensureCapacity() {
        if (size == elements.length) {
            Object[] newElements = new Object[elements.length * 2];
            System.arraycopy(elements, head, newElements, 0, elements.length - head);
            System.arraycopy(elements, 0, newElements, elements.length - head, head);
            elements = newElements;
            head = 0;
        }
    }

    //Pre: element != null
    //Post: elements'[i] = element && size' = size + 1
    public static void enqueue(Object element) {
        assert element != null;
        ensureCapacity();
        elements[(head + size) % elements.length] = element;
        size++;
    }

    //Pre: size > 0
    //Post: elements'[head] = null && head' = head + 1 && size' = size - 1
    public static Object dequeue() {
        assert !isEmpty();
        Object element = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;

        return element;
    }

    //Pre: size > 0
    //Post: return elements[head] && immutable(size)
    public static Object element() {
        return elements[head];
    }

    //Pre: true
    //Post: forall i = [0, 4]: elements'[i] = null && elements'.length = 5 && head' = 0 && size' = 0
    public static void clear() {
        elements = new Object[5];
        head = 0;
        size = 0;
    }

    //Pre: true && predicate != null
    //Post: if (predicate.test(elements[i])) { cnt' = cnt + 1 } && return cnt
    // && immutable(size)
    public static int countIf(Predicate<Object> predicate) {
        int cnt = 0;
        for (int i = 0; i < size; i++) {
            if (predicate.test(elements[(head + i) % elements.length])) {
                cnt++;
            }
        }
        return cnt;
    }
    //Pre: true
    //Post: return size && immutable(size)

    public static int size() {
        return size;
    }

    //Pre: true
    //Post: return size == 0 && immutable(size)
    public static boolean isEmpty() {
        return size == 0;
    }
}
