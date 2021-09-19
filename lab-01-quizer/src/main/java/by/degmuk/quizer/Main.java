package by.degmuk.quizer;

import by.degmuk.quizer.exceptions.QuizNotFinishedException;
import by.degmuk.quizer.task_generators.DITaskGenerator;
import by.degmuk.quizer.task_generators.GroupTaskGenerator;
import by.degmuk.quizer.task_generators.PoolTaskGenerator;
import by.degmuk.quizer.tasks.Task;
import by.degmuk.quizer.tasks.TextTask;
import by.degmuk.quizer.tasks.math_tasks.EquationTask;
import by.degmuk.quizer.tasks.math_tasks.ExpressionTask;
import by.degmuk.quizer.tasks.math_tasks.MathTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static Map<String, Quiz> getQuizMap() {
        Map<String, Quiz> quizMap = new HashMap<>();
        quizMap.put("Equation quiz", new Quiz(
                new EquationTask.Generator(-1000.0, 1000.0,
                        Set.of(MathTask.Operator.values()), 1), 10));
        quizMap.put("Expression quiz", new Quiz(
                new ExpressionTask.Generator(-1000.0, 1000.0,
                        Set.of(MathTask.Operator.values()), 0), 5));
        quizMap.put("Math quiz", new Quiz(new GroupTaskGenerator(
                new EquationTask.Generator(-1000.0, 1000.0,
                        Set.of(MathTask.Operator.values()), 0),
                new ExpressionTask.Generator(-1000.0, 1000.0,
                        Set.of(MathTask.Operator.values()), 0)), 10));
        quizMap.put("С подвохом", new Quiz(new GroupTaskGenerator(
                new PoolTaskGenerator(false, new TextTask("Сколько?", ""),
                        new TextTask(
                                "Какому русскому монарху посвящен памятник в " +
                                        "поэме А" +
                                        ".С.Пушкина \"Медный всадник\"?",
                                "Петр Первый"), new TextTask(
                        "Какая настоящая фамилия Максима " + "Горького?",
                        "Пешков"), new TextTask(
                        "Как звали девушку, в которую был влюблен " +
                                "главный герой романа А.С. Пушкина " +
                                "«Дубровский»?", "Маша Троекурова"),
                        new TextTask("Кому посвятил И.С.Тургенев свой " +
                                "наиболее известный роман " + "\"Отцы и " +
                                "дети\"?", "В.Г.Белинскому"),
                        new TextTask("Кем по профессии был А.П.Чехов?",
                                "Врач")), new DITaskGenerator()), 6));
        quizMap.put("Гонка \"Стальной Шар\"", new Quiz(
                new PoolTaskGenerator(false,
                        new TextTask("Johnny Joestar", "Tusk"),
                        new TextTask("Gyro Zeppeli", "Ball Breaker"),
                        new TextTask("Lucy Steel", "Ticket to Ride"),
                        new TextTask("Mountain Tim", "Oh! Lonesome Me"),
                        new TextTask("Dr. Ferdinand", "Scary Monsters"),
                        new TextTask("Hot Pants", "Cream Starter"),
                        new TextTask("Funny Valentine", "D4C"),
                        new TextTask("Sandman", "In a Silent Way"),
                        new TextTask("Pocoloco", "Hey Ya!"),
                        new TextTask("Boomboom Family", "Tomb of the Boom"),
                        new TextTask("Oyecomova",
                                "Boku no Rhythm wo Kiitekure"),
                        new TextTask("Pork Pie Hat Kid", "Wired"),
                        new TextTask("Ringo Roadagain", "Mandom"),
                        new TextTask("Blackmore", "Catch the Rainbow"),
                        new TextTask("Giant Tree", "Sugar Mountain"),
                        new TextTask("Eleven Men", "Tatoo You!"),
                        new TextTask("Mike O.", "Tubular Bells"),
                        new TextTask("Magent Magent", "20th Century Boy"),
                        new TextTask("Axl RO", "Civil War"),
                        new TextTask("D-I-S-C-O", "Chocolate Disco"),
                        new TextTask("Diego Brando (Alternate)", "ZA WARUDO")),
                10));
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
