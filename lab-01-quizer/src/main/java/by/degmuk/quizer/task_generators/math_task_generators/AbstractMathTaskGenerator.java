package by.degmuk.quizer.task_generators.math_task_generators;

import by.degmuk.quizer.tasks.math_tasks.ExpressionTask;
import by.degmuk.quizer.tasks.math_tasks.MathTask;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractMathTaskGenerator implements MathTaskGenerator {
    protected Number minNumber;
    protected Number maxNumber;
    private ArrayList<ExpressionTask.Operator> operations;

    public AbstractMathTaskGenerator(Number minNumber, Number maxNumber,
                                     boolean generateSum,
                                     boolean generateDifference,
                                     boolean generateMultiplication,
                                     boolean generateDivision) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        this.operations = new ArrayList<MathTask.Operator>();
        if (generateSum) {
            operations.add(ExpressionTask.Operator.ADD);
        }
        if (generateDifference) {
            operations.add(ExpressionTask.Operator.SUB);
        }
        if (generateMultiplication) {
            operations.add(ExpressionTask.Operator.MUL);
        }
        if (generateDivision) {
            operations.add(ExpressionTask.Operator.DIV);
        }
    }

    protected final ExpressionTask.Operator genOperator() {
        if (operations.isEmpty()) {
            throw new RuntimeException();
        }
        return operations
                .get(ThreadLocalRandom.current().nextInt(0, operations.size()));
    }
}
