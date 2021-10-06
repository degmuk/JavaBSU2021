package by.degmuk.quizer.task_generators;

import by.degmuk.quizer.tasks.Task;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PoolTaskGenerator implements Task.Generator {
    ArrayList<Task> tasks = new ArrayList<>();
    boolean allowDuplicate;

    public PoolTaskGenerator(boolean allowDuplicate, Task... tasks) {
        this(allowDuplicate, new LinkedList<Task>(
                Arrays.stream(tasks).collect(Collectors.toList())));
    }

    public PoolTaskGenerator(boolean allowDuplicate, Collection<Task> tasks) {
        this.allowDuplicate = allowDuplicate;
        if (allowDuplicate) {
            this.tasks.addAll(tasks);
        } else {
            this.tasks.addAll(tasks.stream()
                    .collect(Collectors.groupingBy(Function.identity(),
                            Collectors.counting()))
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getValue() == 1)
                    .map(Map.Entry::getKey).collect(Collectors.toList()));
        }
    }

    @Override
    public Task generate() {
        if (tasks.size() == 0) {
            throw new RuntimeException(
                    "Calling generate of empty PoolTaskGenerator");
        }
        int toReturn = ThreadLocalRandom.current().nextInt(0, tasks.size());
        if (allowDuplicate) {
            return tasks.get(toReturn);
        } else {
            return tasks.remove(toReturn);
        }
    }
}
