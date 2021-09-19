package by.degmuk.quizer.tasks.math_tasks;

import by.degmuk.quizer.Result;

import java.util.Set;

public class ExpressionTask extends AbstractMathTask {
    public ExpressionTask(double num1, double num2, Operator op,
                          int precision) {
        super(num1, num2, op, precision);
    }

    public Result validate(double answer) {
        final double validationPrecision =
                op == Operator.DIV ? getPrecisionEps(precision) * 0.5 : 1e-9;
        final double okResult = op.perform(num1, num2);
        return Math.abs((okResult - answer) / okResult) < validationPrecision ?
                Result.OK : Result.WRONG;
    }


    @Override
    public String getText() {
        return toStringWithPrecision(num1, precision) + " " +
                op.getStringRepresentation() + " " +
                toStringWithPrecision(num2, precision) + " = ?";
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
                op = genOperator();
                if (op == Operator.MUL) {
                    num1 = genNum(precision / 2);
                    num2 = genNum(precision / 2);
                } else {
                    num1 = genNum(precision);
                    num2 = genNum(precision);
                }
            } while (!validate(num1, num2, op));
            return new ExpressionTask(num1, num2, op, precision);
        }
    }
}
