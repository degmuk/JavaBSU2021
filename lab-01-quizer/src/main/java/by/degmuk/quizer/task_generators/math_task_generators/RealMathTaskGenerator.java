package by.degmuk.quizer.task_generators.math_task_generators;

import by.degmuk.quizer.tasks.math_tasks.RealMathTask;

public interface RealMathTaskGenerator extends MathTaskGenerator {
    double getMinNumber();

    double getMaxNumber();

    @Override
    RealMathTask generate();
}
