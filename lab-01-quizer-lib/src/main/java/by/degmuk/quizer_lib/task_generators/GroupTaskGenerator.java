package by.degmuk.quizer_lib.task_generators;

import by.degmuk.quizer_lib.tasks.Task;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class GroupTaskGenerator implements Task.Generator {
    private final Task.Generator[] generators;

    public GroupTaskGenerator(Task.Generator... generators) {
        this(Arrays.stream(generators).toList());
    }

    public GroupTaskGenerator(Collection<Task.Generator> generatorList) {
        this.generators = generatorList.toArray(new Task.Generator[0]);
    }

    private Task.Generator getRandomGenerator() {
        return generators[ThreadLocalRandom.current()
                .nextInt(0, generators.length)];
    }

    @Override
    public Task generate() {
        return getRandomGenerator().generate();
    }
}
