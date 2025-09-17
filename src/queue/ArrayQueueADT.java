package queue;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

// Model: a[head]..a[size]
// Inv: size >= 0 && size <= a.length && forall i=1..size - 1: a[i] != null
// Let: immutable(k): forall i=1..k - 1
public class ArrayQueueADT {
    private int size = 0;
    private Object[] elements;
    private int head = 0;

    public ArrayQueueADT() {
        elements = new Object[5];
    }

    public static ArrayQueueADT create() {
        ArrayQueueADT elements = new ArrayQueueADT();
        elements.elements = new Object[10];
        return elements;
    }

    //Pre: true && elements != null
    //Post: if (size == elements.length) { elements'.length = elements.length * 2 } && immutable(size)
    private static void ensureCapacity(ArrayQueueADT elements) {
        if (elements.size == elements.elements.length) {
            Object[] newElements = new Object[elements.elements.length * 2];
            System.arraycopy(elements.elements, elements.head, newElements, 0, elements.elements.length
                    - elements.head);
            System.arraycopy(elements.elements, 0, newElements,
                    elements.elements.length - elements.head, elements.head);
            elements.elements = newElements;
            elements.head = 0;
        }
    }

    //Pre: element != null && elements != null
    //Post: elements'[i] = element && size' = size + 1
    public static void enqueue(ArrayQueueADT elements, Object element) {
        assert element != null;
        ensureCapacity(elements);
        elements.elements[(elements.head + elements.size) % elements.elements.length] = element;
        elements.size++;
    }

    //Pre: size > 0 && elements != null
    //Post: elements'[head] = null && head' = head + 1 && size' = size - 1
    public static Object dequeue(ArrayQueueADT elements) {
        assert !isEmpty(elements);
        Object element = elements.elements[elements.head];
        elements.elements[elements.head] = null;
        elements.head = (elements.head + 1) % elements.elements.length;
        elements.size--;

        return element;
    }

    //Pre: size > 0 && elements != null
    //Post: return elements[head] && immutable(size)
    public static Object element(ArrayQueueADT elements) {
        return elements.elements[elements.head];
    }

    //Pre: true && elements != null
    //Post: forall i = [0, 4]: elements'[i] = null && elements'.length = 5 && head' = 0 && size' = 0
    public static void clear(ArrayQueueADT elements) {
        elements.elements = new Object[5];
        elements.head = 0;
        elements.size = 0;
    }

    //Pre: true && elements != null && predicate != null
    //Post: if (predicate.test(elements[i])) { cnt' = cnt + 1 } && return cnt
    // && immutable(size)
    public static int countIf(ArrayQueueADT elements, Predicate<Object> predicate) {
        int cnt = 0;
        for (int i = 0; i < elements.size; i++) {
            if (predicate.test(elements.elements[(elements.head + i) % elements.elements.length])) {
                cnt++;
            }
        }
        return cnt;
    }

    //Pre: true && elements != null
    //Post: return size && immutable(size)
    public static int size(ArrayQueueADT elements) {
        return elements.size;
    }

    //Pre: true && elements != null
    //Post: return size == 0 && immutable(size)
    public static boolean isEmpty(ArrayQueueADT elements) {
        return elements.size == 0;
    }
}
