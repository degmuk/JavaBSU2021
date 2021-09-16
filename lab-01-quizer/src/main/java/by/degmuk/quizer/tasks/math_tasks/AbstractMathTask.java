package by.degmuk.quizer.tasks.math_tasks;

import by.degmuk.quizer.Result;

public abstract class AbstractMathTask implements MathTask {
    protected final Operator op;
    protected Number num1;
    protected Number num2;

    public AbstractMathTask(Number num1, Number num2, Operator op) {
        this.num1 = num1;
        this.num2 = num2;
        this.op = op;
    }

    protected abstract Number getResult();

    protected Result validate(Number answer) {
        return getResult().equals(answer) ? Result.OK : Result.WRONG;
    }

    protected abstract String getNum1String();

    protected abstract String getNum2String();


    protected String operatorChar() {
        switch (op) {
            case ADD:
                return "+";
            case SUB:
                return "-";
            case MUL:
                return "*";
            case DIV:
                return "/";
        }
        throw new RuntimeException();
    }

}
