package by.degmuk.quizer.tasks.math_tasks;

import by.degmuk.quizer.Result;

public interface IntegerMathTask extends MathTask {
    Result validate(int answer);

    default Result validate(String answer) {
        try {
            return validate(Integer.parseInt(answer));
        } catch (NumberFormatException e) {
            return Result.INCORRECT_INPUT;
        }
    }
}
