package by.degmuk.quizer.task_generators.math_task_generators;

import by.degmuk.quizer.tasks.math_tasks.IntegerEquationMathTask;
import by.degmuk.quizer.tasks.math_tasks.IntegerExpressionMathTask;
import by.degmuk.quizer.tasks.math_tasks.MathTask;

import java.util.concurrent.ThreadLocalRandom;

public class IntegerExpressionMathTaskGenerator extends ExpressionTaskGenerator
        implements IntegerMathTaskGenerator {
    public IntegerExpressionMathTaskGenerator(int minNumber, int maxNumber,
                                              boolean generateSum,
                                              boolean generateDifference,
                                              boolean generateMultiplication,
                                              boolean generateDivision) {
        super(minNumber, maxNumber, generateSum, generateDifference,
                generateMultiplication, generateDivision);
        if (maxNumber < minNumber || (!generateSum && !generateDifference &&
                !generateMultiplication && !generateDivision)) {
            throw new IllegalArgumentException();
        }
    }

    private int genNum() {
        return ThreadLocalRandom.current()
                .nextInt(minNumber.intValue(), maxNumber.intValue() + 1);
    }

    private boolean validate(int num1, int num2, MathTask.Operator op) {
        switch (op) {
            case ADD:
            case SUB:
            case MUL:
                break;
            case DIV:
                if (num2 == 0) {
                    return false;
                }
                break;
        }
        return true;
    }

    @Override
    public int getMinNumber() {
        return minNumber.intValue();
    }

    @Override
    public int getMaxNumber() {
        return maxNumber.intValue();
    }

    @Override
    public IntegerExpressionMathTask generate() {
        int num1, num2;
        MathTask.Operator op;
        do {
            num1 = genNum();
            num2 = genNum();
            op = genOperator();
        } while (!validate(num1, num2, op));
        return new IntegerExpressionMathTask(num1, num2, op);
    }
}
