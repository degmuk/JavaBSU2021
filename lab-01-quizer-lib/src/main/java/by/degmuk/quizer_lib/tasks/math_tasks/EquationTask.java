package by.degmuk.quizer_lib.tasks.math_tasks;

import by.degmuk.quizer_lib.Result;

import java.util.Set;

public class EquationTask extends AbstractMathTask {
    public EquationTask(double num1, double num2, Operator op, int precision) {
        super(num1, num2, op, precision);
    }

    @Override
    Result validate(double answer) {
        final double validationPrecision =
                op == Operator.MUL || op == Operator.DIV ?
                        getPrecisionEps(precision) * 0.5 : 1e-9;
        return Math.abs((op.perform(num1, answer) - num2) / num2) <
                validationPrecision ? Result.OK : Result.WRONG;
    }

    @Override
    public String getText() {
        return toStringWithPrecision(num1, precision) + " " +
                op.getStringRepresentation() + " " + "x = " +
                toStringWithPrecision(num2, precision);
    }

    public static class Generator extends AbstractMathTask.Generator {
        public Generator(double minNumber, double maxNumber,
                         Set<Operator> toGenerate, int precision) {
            super(minNumber, maxNumber, toGenerate, precision);
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
