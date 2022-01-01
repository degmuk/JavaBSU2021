package by.degmuk.quizer_lib.exceptions;

public class QuizNotFinishedException extends Exception {
    public QuizNotFinishedException() {
        super("Getting mark of unfinished quiz");
    }
}
