package by.degmuk.quizer.task_generators.math_task_generators;

import by.degmuk.quizer.tasks.math_tasks.IntegerMathTask;

public interface IntegerMathTaskGenerator extends MathTaskGenerator {
    int getMinNumber();

    int getMaxNumber();

    @Override
    IntegerMathTask generate();
}
