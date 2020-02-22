package org.example.task;

import java.util.concurrent.CompletableFuture;

public interface Task {

    void execute();

    CompletableFuture getFuture();
}
