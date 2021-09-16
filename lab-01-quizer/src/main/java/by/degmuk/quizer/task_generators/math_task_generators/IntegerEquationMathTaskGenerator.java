package by.degmuk.quizer.task_generators.math_task_generators;

import by.degmuk.quizer.tasks.math_tasks.IntegerEquationMathTask;
import by.degmuk.quizer.tasks.math_tasks.MathTask;

import java.util.concurrent.ThreadLocalRandom;

public class IntegerEquationMathTaskGenerator extends EquationTaskGenerator
        implements IntegerMathTaskGenerator {
    public IntegerEquationMathTaskGenerator(int minNumber, int maxNumber,
                                            boolean generateSum,
                                            boolean generateDifference,
                                            boolean generateMultiplication,
                                            boolean generateDivision)
            throws IllegalAccessException {
        super(minNumber, maxNumber, generateSum, generateDifference,
                generateMultiplication, generateDivision);
        if (maxNumber < minNumber || (!generateSum && !generateDifference &&
                !generateMultiplication && !generateDivision)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int getMinNumber() {
        return minNumber.intValue();
    }

    @Override
    public int getMaxNumber() {
        return maxNumber.intValue();
    }

    private boolean validate(int num1, int num2, MathTask.Operator op) {
        switch (op) {
            case ADD:
            case SUB:
                break;
            case MUL:
                if (num1 == 0 || num2 % num1 != 0) {
                    return false;
                }
                break;
            case DIV:
                if (num1 == 0 || num2 == 0 || num1 % num2 != 0) {
                    return false;
                }
                break;
        }
        return true;
    }

    @Override
    public IntegerEquationMathTask generate() {
        int num1, num2;
        MathTask.Operator op;
        do {
            num1 = genNum();
            num2 = genNum();
            op = genOperator();
        } while (!validate(num1, num2, op));
        return new IntegerEquationMathTask(num1, num2, op);
    }

    private int genNum() {
        return ThreadLocalRandom.current()
                .nextInt(minNumber.intValue(), maxNumber.intValue() + 1);
    }
}
