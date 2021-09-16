package by.degmuk.quizer.tasks.math_tasks;

import by.degmuk.quizer.tasks.Task;

public interface IntegerMathTask extends MathTask {
    Task.Result validate(int answer);

    default Task.Result validate(String answer) {
        try {
            return validate(Integer.parseInt(answer));
        } catch (NumberFormatException e) {
            return Task.Result.INCORRECT_INPUT;
        }
    }
}
