package queue;

public abstract class AbstractQueue implements Queue {
    protected int size = 0;
    public void enqueue(Object element) {
        assert element != null;
        enqueueImpl(element);
        size++;
    }
    protected abstract void enqueueImpl(Object element);

    public void clear() {
        size = 0;
        clearImpl();
    }
    protected abstract void clearImpl();
    public void dedup() {
        if (size < 2) {
            return;
        }
        Object element = dequeue();
        enqueue(element);
        int saveSize = size();
        for (int i = 1; i < saveSize; i++) {
            if (element.equals(element())) {
                dequeue();
            } else {
                element = dequeue();
                enqueue(element);
            }
        }
    }
    public Object dequeue() {
        assert !isEmpty();
        Object element = dequeueImpl();
        size--;
        return element;
    }
    protected abstract Object dequeueImpl();
    public Object element() {
        return elementImpl();
    }
    protected abstract Object elementImpl();

    //Pre: true
    //Post: return size && immutable(size)
    public int size() {
        return size;
    }

    //Pre: true
    //Post: return size == 0 && immutable(size)
    public boolean isEmpty() {
        return size == 0;
    }
}
