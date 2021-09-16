package by.degmuk.quizer.tasks.math_tasks;

public abstract class ExpressionTask extends AbstractMathTask {
    public ExpressionTask(Number num1, Number num2, Operator op) {
        super(num1, num2, op);
    }

    @Override
    public String getText() {
        return getNum1String() + " " + operatorChar() + " " + getNum2String() +
                " = ?";
    }
}
