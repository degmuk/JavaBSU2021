package by.degmuk.quizer.task_generators;

import by.degmuk.quizer.tasks.Task;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class PoolTaskGenerator implements Task.Generator {
    Task[] tasks;

    public PoolTaskGenerator(boolean allowDuplicate, Task... tasks) {
        this(allowDuplicate, new LinkedList<Task>(
                Arrays.stream(tasks).collect(Collectors.toList())));
    }

    public PoolTaskGenerator(boolean allowDuplicate, List<Task> tasks) {
        if (allowDuplicate) {
            this.tasks = tasks.toArray(new Task[0]);
        } else {
            var uniqueTasks = Set.of(tasks.stream().toArray());
            this.tasks = new Task[uniqueTasks.size()];
            uniqueTasks.toArray(this.tasks);
        }
    }

    @Override
    public Task generate() {
        return tasks[ThreadLocalRandom.current().nextInt(0, tasks.length)];
    }
}
