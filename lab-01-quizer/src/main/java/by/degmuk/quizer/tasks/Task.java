package by.degmuk.quizer.tasks;

public interface Task {
    String getText();

    Result validate(String answer);

    enum Result {
        OK, WRONG, INCORRECT_INPUT
    }
}
