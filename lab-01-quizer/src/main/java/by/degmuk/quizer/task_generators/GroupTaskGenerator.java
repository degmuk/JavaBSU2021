package by.degmuk.quizer.task_generators;

import by.degmuk.quizer.tasks.Task;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GroupTaskGenerator implements Task.Generator {
    private final Task.Generator[] generators;

    GroupTaskGenerator(Task.Generator... generators) {
        this.generators = (Task.Generator[]) Arrays.stream(generators).toArray();
    }

    GroupTaskGenerator(List<Task.Generator> generatorList) {
        this.generators = new Task.Generator[generatorList.size()];
        int ind = 0;
        for (Task.Generator i : generatorList) {
            this.generators[ind] = i;
            ++ind;
        }
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
