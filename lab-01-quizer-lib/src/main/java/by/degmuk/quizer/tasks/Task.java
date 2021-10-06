package by.degmuk.quizer.tasks;

import by.degmuk.quizer.Result;

public interface Task {
    String getText();

    Result validate(String answer);

    interface Generator {
        Task generate();
    }
}
