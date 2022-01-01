package by.degmuk.quizer_lib.tasks;

import by.degmuk.quizer_lib.Result;

public interface Task {
    String getText();

    Result validate(String answer);

    interface Generator {
        Task generate();
    }
}
