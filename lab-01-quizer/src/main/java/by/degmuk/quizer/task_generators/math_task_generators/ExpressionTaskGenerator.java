package by.degmuk.quizer.task_generators.math_task_generators;

import by.degmuk.quizer.tasks.math_tasks.ExpressionTask;

public abstract class ExpressionTaskGenerator
        extends AbstractMathTaskGenerator {
    public ExpressionTaskGenerator(int minNumber, int maxNumber,
                                   boolean generateSum,
                                   boolean generateDifference,
                                   boolean generateMultiplication,
                                   boolean generateDivision) {
        super(minNumber, maxNumber, generateSum, generateDifference,
                generateMultiplication, generateDivision);
    }

    @Override
    public abstract ExpressionTask generate();
}
