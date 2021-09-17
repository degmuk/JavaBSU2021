package by.degmuk.quizer.exceptions;

public class QuizNotFinishedException extends Exception {
    public QuizNotFinishedException() {
        super("Getting mark of unfinished quiz");
    }
}
