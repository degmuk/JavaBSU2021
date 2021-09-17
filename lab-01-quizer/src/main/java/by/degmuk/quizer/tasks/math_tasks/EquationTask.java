package by.degmuk.quizer.tasks.math_tasks;

import by.degmuk.quizer.Result;

public class EquationTask extends AbstractMathTask {
    public EquationTask(double num1, double num2, Operator op, int precision) {
        super(num1, num2, op, precision);
    }

    @Override
    Result validate(double answer) {
        final double validationPrecision = 1e-9;
        double currentValidationPrecision =
                op == Operator.DIV || op == Operator.MUL ?
                        getPrecisionEps(precision / 2) * 0.5 :
                        validationPrecision;
        return Math.abs(op.perform(num1, answer) - num2) <
                currentValidationPrecision ? Result.OK : Result.WRONG;
    }

    @Override
    public String getText() {
        return toStringWithPrecision(num1, precision) + " " +
                op.getStringRepresentation() + " " + "x = " +
                toStringWithPrecision(num2, precision);
    }

    public static class Generator extends AbstractMathTask.Generator {
        public Generator(double minNumber, double maxNumber,
                         boolean generateSum, boolean generateDifference,
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
                    if (Math.abs(num1) < getPrecisionEps(precision)) {
                        return false;
                    }
                    break;
            }
            return true;
        }

        @Override
        public EquationTask generate() {
            double num1, num2;
            Operator op;
            do {
                num1 = genNum(precision);
                num2 = genNum(precision);
                op = genOperator();
            } while (!validate(num1, num2, op));
            return new EquationTask(num1, num2, op, precision);
        }

    }
}
