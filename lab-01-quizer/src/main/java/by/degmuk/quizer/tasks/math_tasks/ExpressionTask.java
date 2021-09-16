package by.degmuk.quizer.tasks.math_tasks;

import by.degmuk.quizer.Result;

public class ExpressionTask extends AbstractMathTask {
    public ExpressionTask(double num1, double num2, Operator op,
                          int precision) {
        super(num1, num2, op, precision);
    }

    public Result validate(double answer) {
        return Math.abs(op.perform(num1, num2) - answer) <
                getPrecisionEps(precision) ? Result.OK : Result.WRONG;
    }


    @Override
    public String getText() {
        return toStringWithPrecision(num1, precision) + " " +
                op.getStringRepresentation() + " " +
                toStringWithPrecision(num2, precision) + " = ?";
    }

    public static class Generator extends AbstractMathTask.Generator {
        public Generator(int minNumber, int maxNumber, boolean generateSum,
                         boolean generateDifference,
                         boolean generateMultiplication,
                         boolean generateDivision, int precision) {
            super(minNumber, maxNumber, generateSum, generateDifference,
                    generateMultiplication, generateDivision, precision);
            if (maxNumber < minNumber || (!generateSum && !generateDifference &&
                    !generateMultiplication && !generateDivision)) {
                throw new IllegalArgumentException();
            }
        }

        private boolean validate(double num1, double num2, Operator op) {
            switch (op) {
                case ADD:
                case SUB:
                case MUL:
                    break;
                case DIV:
                    if (Math.abs(num2) < getPrecisionEps(precision)) {
                        return false;
                    }
                    break;
            }
            return true;
        }

        @Override
        public ExpressionTask generate() {
            double num1, num2;
            Operator op;
            do {
                num1 = genNum();
                num2 = genNum();
                op = genOperator();
            } while (!validate(num1, num2, op));
            return new ExpressionTask(num1, num2, op, precision);
        }
    }
}
