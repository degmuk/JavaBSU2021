package by.degmuk.quizer;

import by.degmuk.quizer.task_generators.TaskGenerator;
import by.degmuk.quizer.tasks.Task;

import java.util.Map;
import java.util.Scanner;

class Quiz {
    private final TaskGenerator generator;
    private int taskCount;
    private int taskOk = 0;
    private int taskWrong = 0;
    private int taskIncorrect = 0;
    private Task currentTask = null;

    public Quiz(TaskGenerator generator, int taskCount) {
        this.generator = generator;
        if (taskCount <= 0) {
            throw new RuntimeException();
        }
        this.taskCount = taskCount;
    }

    public Task nextTask() {
        if (isFinished()) {
            throw new RuntimeException();
        }
        currentTask = generator.generate();
        return currentTask;
    }

    public Task.Result provideAnswer(String answer) {
        if (currentTask == null) {
            throw new RuntimeException();
        }
        --taskCount;
        Task.Result result = currentTask.validate(answer);
        switch (result) {
            case OK:
                ++taskOk;
                break;
            case WRONG:
                ++taskWrong;
                break;
            case INCORRECT_INPUT:
                ++taskIncorrect;
                break;
        }
        return result;
    }

    public boolean isFinished() {
        return taskCount == 0;
    }

    public int getCorrectAnswerNumber() {
        return taskOk;
    }

    public int getWrongAnswerNumber() {
        return taskWrong;
    }

    public int getIncorrectInputNumber() {
        return taskIncorrect;
    }

    public double getMark() {
        if (!isFinished()) {
            throw new RuntimeException();
        }
        return 100.0 * (double) taskOk /
                (taskOk + taskIncorrect + taskWrong + taskCount);
    }
}

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
