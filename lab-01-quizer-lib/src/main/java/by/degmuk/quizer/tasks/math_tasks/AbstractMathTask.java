package by.degmuk.quizer.tasks.math_tasks;

import by.degmuk.quizer.Result;

import java.util.ArrayList;
import java.util.Set;
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

        public Generator(double minNumber, double maxNumber,
                         Set<Operator> toGenerate, int precision) {
            if (maxNumber < minNumber || toGenerate.isEmpty()) {
                throw new IllegalArgumentException();
            }
            this.minNumber = minNumber;
            this.maxNumber = maxNumber;
            this.operations = new ArrayList<>();
            this.precision = precision;
            operations.addAll(toGenerate);
        }

        @Override
        public double getMinNumber() {
            return minNumber;
        }

        @Override
        public double getMaxNumber() {
            return maxNumber;
        }

        protected double roundToPrecision(double number, int precision) {
            return Double.parseDouble(toStringWithPrecision(number, precision));
        }

        protected double genNum(int precision) {
            return roundToPrecision(ThreadLocalRandom.current()
                    .nextDouble(minNumber, maxNumber), precision);
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
