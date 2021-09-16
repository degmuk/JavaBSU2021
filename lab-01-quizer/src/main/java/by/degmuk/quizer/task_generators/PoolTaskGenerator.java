package by.degmuk.quizer.task_generators;

import by.degmuk.quizer.tasks.Task;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class PoolTaskGenerator implements Task.Generator {
    Task[] tasks;

    PoolTaskGenerator(boolean allowDuplicate, Task... tasks) {
        this(allowDuplicate, new LinkedList<Task>(
                Arrays.stream(tasks).collect(Collectors.toList())));
    }

    PoolTaskGenerator(boolean allowDuplicate, List<Task> tasks) {
        if (allowDuplicate) {
            this.tasks = (Task[]) new HashSet<Task>(tasks).toArray();
        } else {
            this.tasks = (Task[]) tasks.toArray();
        }
    }

    @Override
    public Task generate() {
        return tasks[ThreadLocalRandom.current().nextInt(0, tasks.length)];
    }
}
