package by.degmuk.quizer.tasks.math_tasks;

public interface RealMathTask extends MathTask {
    Result validate(double answer);

    default Result validate(String answer) {
        try {
            return validate(Double.parseDouble(answer));
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }
    }
}
