package org.example.queue;

public interface Queue<E> {

    void push(E entity);

    E pop();

    E peek();

    Boolean empty();
}
