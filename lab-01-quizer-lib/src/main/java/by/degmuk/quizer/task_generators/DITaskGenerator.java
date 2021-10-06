package by.degmuk.quizer.task_generators;

import by.degmuk.quizer.tasks.Task;
import by.degmuk.quizer.tasks.TextTask;

public class DITaskGenerator implements Task.Generator {
    private int current = 1000;
    @Override
    public TextTask generate() {
        var result = new TextTask(current + " минус семь?",
                Integer.toString(current - 7));
        current -= 7;
        return result;
    }
}
