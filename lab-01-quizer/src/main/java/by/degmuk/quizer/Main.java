package by.degmuk.quizer;

import by.degmuk.quizer.exceptions.QuizNotFinishedException;
import by.degmuk.quizer.task_generators.DITaskGenerator;
import by.degmuk.quizer.task_generators.GroupTaskGenerator;
import by.degmuk.quizer.task_generators.PoolTaskGenerator;
import by.degmuk.quizer.tasks.Task;
import by.degmuk.quizer.tasks.TextTask;
import by.degmuk.quizer.tasks.math_tasks.EquationTask;
import by.degmuk.quizer.tasks.math_tasks.ExpressionTask;

import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Map<String, Quiz> getQuizMap() {
        return Map.of("Equation quiz", new Quiz(
                        new EquationTask.Generator(-1000.0, 1000.0, true,
                                true, true,
                                true, 1), 10), "Expression quiz", new Quiz(
                        new ExpressionTask.Generator(-1000.0, 1000.0, true,
                                true, true,
                                true, 0), 5), "Math quiz", new Quiz(
                        new GroupTaskGenerator(
                                new EquationTask.Generator(-1000.0, 1000.0,
                                        true, true,
                                        true, true, 0),
                                new ExpressionTask.Generator(-1000.0, 1000.0,
                                        true,
                                        true, true, true, 0)), 10), "С " +
                        "подвохом",
                new Quiz(new GroupTaskGenerator(new PoolTaskGenerator(false,
                        new TextTask(
                                "Какому русскому монарху посвящен памятник в " +
                                        "поэме А" +
                                        ".С.Пушкина \"Медный всадник\"?",
                                "Петр Первый"), new TextTask(
                        "Какая настоящая фамилия Максима " + "Горького?",
                        "Пешков"), new TextTask(
                        "Как звали девушку, в которую был влюблен главный " +
                                "герой романа А.С. Пушкина «Дубровский»?",
                        "Маша Троекурова"), new TextTask(
                        "Кому посвятил И.С.Тургенев свой " +
                                "наиболее известный роман \"Отцы и дети\"?",
                        "В.Г.Белинскому"),
                        new TextTask("Кем по профессии был А.П.Чехов?",
                                "Врач")), new DITaskGenerator()), 1000));
    }

    public static void main(String[] args) {
        Map<String, Quiz> quizMap = getQuizMap();
        System.out.println("Введите название теста...");
        Scanner in = new Scanner(System.in);
        String testName;//= in.nextLine();
        // testName = in.nextLine();
        testName = "С подвохом";
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
