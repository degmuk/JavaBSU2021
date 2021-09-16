package by.degmuk.quizer.task_generators.math_task_generators;

import by.degmuk.quizer.tasks.math_tasks.EquationTask;

public abstract class EquationTaskGenerator extends AbstractMathTaskGenerator {
    public EquationTaskGenerator(Number minNumber, Number maxNumber,
                                 boolean generateSum,
                                 boolean generateDifference,
                                 boolean generateMultiplication,
                                 boolean generateDivision) {
        super(minNumber, maxNumber, generateSum, generateDifference,
                generateMultiplication, generateDivision);
    }

    @Override
    public abstract EquationTask generate();
}
