package by.degmuk.quizer.tasks.math_tasks;

import by.degmuk.quizer.Result;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractMathTask implements MathTask {
    protected final Operator op;
    protected double num1;
    protected double num2;
    protected int precision;

    public AbstractMathTask(double num1, double num2, Operator op,
                            int precision) {
        this.num1 = num1;
        this.num2 = num2;
        this.op = op;
        this.precision = precision;
    }

    protected static double getPrecisionEps(int precision) {
        return Math.pow(10, -precision);
    }

    protected static String toStringWithPrecision(double number,
                                                  int precision) {
        return String.format("%." + precision + "f", number);
    }

    abstract Result validate(double answer);

    public Result validate(String answer) {
        try {
            return validate(Double.parseDouble(answer));
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }
    }

    public abstract static class Generator implements MathTask.Generator {
        private final ArrayList<Operator> operations;
        protected double minNumber;
        protected double maxNumber;
        protected int precision;

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }

        public Generator(double minNumber, double maxNumber,
                         boolean generateSum, boolean generateDifference,
                         boolean generateMultiplication,
                         boolean generateDivision, int precision) {
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.operations = new ArrayList<>();
            this.precision = precision;
            if (generateSum) {
                operations.add(Operator.ADD);
            }
            if (generateDifference) {
                operations.add(Operator.SUB);
            }
            if (generateMultiplication) {
                operations.add(Operator.MUL);
            }
            if (generateDivision) {
                operations.add(Operator.DIV);
            }
        }

        protected double roundToPrecision(double number) {
            return Double.parseDouble(toStringWithPrecision(number, precision));
        }

        protected double genNum(int precision) {
            return roundToPrecision(ThreadLocalRandom.current()
                    .nextDouble(minNumber, maxNumber));
        }


        protected final Operator genOperator() {
            if (operations.isEmpty()) {
                throw new RuntimeException();
            }
            return operations.get(
                    ThreadLocalRandom.current().nextInt(0, operations.size()));
        }
    }
}
