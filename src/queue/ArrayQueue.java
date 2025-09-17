package queue;

import java.util.function.Predicate;

// Model: a[head]..a[size] && a[0] = null
// Inv: size >= 0 && size <= a.length && forall i=1..size - 1: a[i] != null
// Let: immutable(k): forall i=1..k - 1
public class ArrayQueue extends AbstractQueue {
    private int head = 0;
    private Object[] elements = new Object[5];

    //Pre: true
    //Post: if (size == elements.length) { elements'.length = elements.length * 2 } && immutable(size)
    private void ensureCapacity() {
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

    @Override
    protected void enqueueImpl(Object element) {
        ensureCapacity();
        elements[(head + size) % elements.length] = element;
    }

    //Pre: size > 0
    //Post: return elements[head] && elements'[head] = null && head' = head + 1 && size' = size - 1
    @Override
    protected Object dequeueImpl() {
        Object element = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;

        return element;
    }

    //Pre: size > 0
    //Post: return elements[head] && immutable(size)
    @Override
    protected Object elementImpl() {
        return elements[head];
    }

    //Pre: true
    //Post: forall i = [0, 4]: elements'[i] = null && elements'.length = 5 && head' = 0 && size' = 0
    @Override
    protected void clearImpl() {
        head = 0;
        elements = new Object[5];
    }

    //Pre: true && predicate != null
    //Post: if (predicate.test(elements[i)) { cnt' = cnt + 1 } && return cnt
    // && immutable(size)
    public int countIf(Predicate<Object> predicate) {
        int cnt = 0;
        for (int i = 0; i < size; i++) {
            if (predicate.test(elements[(head + i) % elements.length])) {
                cnt++;
            }
        }
        return cnt;
    }
}
