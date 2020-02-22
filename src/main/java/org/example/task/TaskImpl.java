package org.example.task;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 *  利用CompletableFuture实现
 */
public class TaskImpl implements Task {

    private CompletableFuture completableFuture;

    private List<Task> dependecyTasks;

    public TaskImpl(List<Task> dependecyTasks) {
        this.completableFuture = new CompletableFuture();
    }

    @Override
    public void execute() {
        if (dependecyTasks != null && !dependecyTasks.isEmpty()) {
            CompletableFuture[] completableFutures = dependecyTasks.stream()
                    .map(Task::getFuture)
                    .toArray(CompletableFuture[]::new);
            CompletableFuture.allOf(completableFutures).join();
        }
        completableFuture.complete("");
    }

    @Override
    public CompletableFuture getFuture() {
        return null;
    }
}
