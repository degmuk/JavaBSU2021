package by.degmuk.quizer;

import by.degmuk.quizer.task_generators.TaskGenerator;
import by.degmuk.quizer.tasks.Task;

import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Map<String, Quiz> getQuizMap() {
        return null;
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
            System.out.println(quiz.nextTask().getText());
            String answer = in.nextLine();
            System.out.println(quiz.provideAnswer(answer));
        }
        System.out.println(quiz.getMark());
    }
}
