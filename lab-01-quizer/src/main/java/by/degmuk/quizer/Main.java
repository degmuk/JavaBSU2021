package by.degmuk.quizer;

import by.degmuk.quizer.exceptions.QuizNotFinishedException;
import by.degmuk.quizer.tasks.Task;
import by.degmuk.quizer.tasks.math_tasks.EquationTask;
import by.degmuk.quizer.tasks.math_tasks.ExpressionTask;

import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Map<String, Quiz> getQuizMap() {
        var quizMap = Map.of("Equation quiz", new Quiz(
                new EquationTask.Generator(-1000.0, 1000.0, true, true, true,
                        true, 1), 10), "Expression quiz", new Quiz(
                new ExpressionTask.Generator(-1000.0, 1000.0, true, true, true,
                        true, 0), 5));
        return quizMap;
    }

    public static void main(String[] args) {
        Map<String, Quiz> quizMap = getQuizMap();
        System.out.println("Введите название теста...");
        Scanner in = new Scanner(System.in);
        String testName = in.nextLine();
        while (!quizMap.containsKey(testName)) {
            System.out.println("Введите название существующего теста...");
            testName = in.nextLine();
        }
        Quiz quiz = quizMap.get(testName);
        while (!quiz.isFinished()) {
            Result answerResult;
            Task task = quiz.nextTask();
            do {
                System.out.println(task.getText());
                String answer = in.nextLine();
                answerResult = quiz.provideAnswer(answer);
                System.out.println(answerResult);
            } while (answerResult == Result.INCORRECT_INPUT);
        }
        try {
            System.out.println(quiz.getMark());
        } catch (QuizNotFinishedException e) {
            e.printStackTrace();
        }
    }
}
