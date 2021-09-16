package by.degmuk.quizer.tasks.math_tasks;

import by.degmuk.quizer.Result;

public class IntegerExpressionMathTask extends ExpressionTask
        implements IntegerMathTask {

    public IntegerExpressionMathTask(int num1, int num2, Operator op) {
        super(num1, num2, op);
    }

    @Override
    protected Number getResult() {
        int val1 = num1.intValue();
        int val2 = num2.intValue();
        switch (op) {
            case ADD:
                return val1 + val2;
            case SUB:
                return val1 - val2;
            case MUL:
                return val1 * val2;
            case DIV:
                return val1 / val2;
        }
        throw new RuntimeException();
    }

    @Override
    protected String getNum1String() {
        return Integer.toString(num1.intValue());
    }

    @Override
    protected String getNum2String() {
        return Integer.toString(num2.intValue());
    }

    @Override
    public Result validate(int answer) {
        return super.validate(answer);
    }
}
