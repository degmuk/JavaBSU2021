package by.degmuk.quizer.task_generators;

import by.degmuk.quizer.tasks.Task;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GroupTaskGenerator implements TaskGenerator {
    private final TaskGenerator[] generators;

    GroupTaskGenerator(TaskGenerator... generators) {
        this.generators = (TaskGenerator[]) Arrays.stream(generators).toArray();
    }

    GroupTaskGenerator(List<TaskGenerator> generatorList) {
        this.generators = new TaskGenerator[generatorList.size()];
        int ind = 0;
        for (TaskGenerator i : generatorList) {
            this.generators[ind] = i;
            ++ind;
        }
    }

    private TaskGenerator getRandomGenerator() {
        return generators[ThreadLocalRandom.current()
                .nextInt(0, generators.length)];
    }

    @Override
    public Task generate() {
        return getRandomGenerator().generate();
    }
}
