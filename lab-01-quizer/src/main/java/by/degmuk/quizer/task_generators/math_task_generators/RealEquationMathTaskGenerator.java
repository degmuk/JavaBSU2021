package by.degmuk.quizer.task_generators.math_task_generators;


import by.degmuk.quizer.tasks.math_tasks.RealEquationMathTask;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class RealEquationMathTaskGenerator extends EquationTaskGenerator
        implements RealMathTaskGenerator {
    private final int precision;

    public RealEquationMathTaskGenerator(int precision, double minNumber,
                                         double maxNumber, boolean generateSum,
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

    @Override
    public RealEquationMathTask generate() {
        return new RealEquationMathTask(genNum(), genNum(), genOperator(),
                precision);
    }
}
