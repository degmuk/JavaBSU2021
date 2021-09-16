package by.degmuk.quizer.task_generators.math_task_generators;

import by.degmuk.quizer.task_generators.TaskGenerator;
import by.degmuk.quizer.tasks.math_tasks.MathTask;

public interface MathTaskGenerator extends TaskGenerator {
    @Override
    MathTask generate();
}
