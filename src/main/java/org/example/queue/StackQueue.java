package org.example.queue;

import java.util.Stack;

public class StackQueue<E> implements Queue<E> {

    private Stack<E> container;

    public StackQueue(int capacity) {
        container = new Stack<>();
    }

    @Override
    public void push(E entity) {
        container.push(entity);
    }

    @Override
    public E pop() {
        return container.pop();
    }

    @Override
    public E peek() {
        return container.peek();
    }

    @Override
    public Boolean empty() {
        return container.empty();
    }
}
