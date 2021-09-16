package by.degmuk.quizer.tasks.math_tasks;

import by.degmuk.quizer.Result;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RealEquationMathTask extends EquationTask implements RealMathTask {
    int precision;

    public RealEquationMathTask(double num1, double num2, Operator op,
                                int precision) {
        super(num1, num2, op);
        this.precision = precision;
    }

    @Override
    protected Number getResult() {
        double val1 = num1.doubleValue();
        double val2 = num2.doubleValue();
        switch (op) {
            case ADD:
                return val2 - val1;
            case SUB:
                return val1 - val2;
            case MUL:
                return val2 / val1;
            case DIV:
                return val1 / val2;
        }
        throw new RuntimeException();
    }

    @Override
    public Result validate(double answer) {
        return Math.log10(Math.abs(getResult().doubleValue() - answer)) <
                -precision ? Result.OK : Result.WRONG;
    }

    @Override
    protected String getNum1String() {
        return BigDecimal.valueOf(num1.doubleValue())
                .setScale(precision, RoundingMode.HALF_UP).toString();
    }

    @Override
    protected String getNum2String() {
        return BigDecimal.valueOf(num2.doubleValue())
                .setScale(precision, RoundingMode.HALF_UP).toString();
    }
}
