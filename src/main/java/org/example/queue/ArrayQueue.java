package org.example.queue;

import java.util.ArrayList;

public class ArrayQueue<E> implements Queue<E> {

    private final ArrayList<E> container;

    public ArrayQueue(int capacity) {
        container = new ArrayList<>(capacity);
    }

    @Override
    public void push(E entity) {
        container.add(container.size(), entity);
    }

    @Override
    public E pop() {
        if (empty()) {
            return null;
        } else {
            return container.remove(container.size() - 1);
        }
    }

    @Override
    public E peek() {
        if (empty()) {
            return null;
        } else {
            return container.get(0);
        }
    }

    @Override
    public Boolean empty() {
        return container.isEmpty();
    }
}
