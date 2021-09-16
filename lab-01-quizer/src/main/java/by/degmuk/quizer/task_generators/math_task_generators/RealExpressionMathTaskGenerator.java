package by.degmuk.quizer.task_generators.math_task_generators;

import by.degmuk.quizer.tasks.math_tasks.MathTask;
import by.degmuk.quizer.tasks.math_tasks.RealExpressionMathTask;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class RealExpressionMathTaskGenerator extends ExpressionTaskGenerator
        implements RealMathTaskGenerator {
    final int precision;

    public RealExpressionMathTaskGenerator(int precision, int minNumber,
                                           int maxNumber, boolean generateSum,
                                           boolean generateDifference,
                                           boolean generateMultiplication,
                                           boolean generateDivision) {
        super(minNumber, maxNumber, generateSum, generateDifference,
                generateMultiplication, generateDivision);
        this.precision = precision;
        if (maxNumber < minNumber || (!generateSum && !generateDifference &&
                !generateMultiplication && !generateDivision)) {
            throw new IllegalArgumentException();
        }
    }

    private double genNum() {
        return BigDecimal.valueOf(ThreadLocalRandom.current()
                .nextDouble(minNumber.doubleValue(), maxNumber.doubleValue()))
                .setScale(precision, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public double getMinNumber() {
        return minNumber.doubleValue();
    }

    @Override
    public double getMaxNumber() {
        return maxNumber.doubleValue();
    }

    private boolean validate(double num1, double num2, MathTask.Operator op) {
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
    public RealExpressionMathTask generate() {
        double num1, num2;
        MathTask.Operator op;
        do {
            num1 = genNum();
            num2 = genNum();
            op = genOperator();
        } while (!validate(num1, num2, op));
        return new RealExpressionMathTask(num1, num2, op, precision);
    }
}
