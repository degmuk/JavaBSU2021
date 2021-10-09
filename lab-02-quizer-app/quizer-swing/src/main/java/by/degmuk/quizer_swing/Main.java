package by.degmuk.quizer_swing;

import by.degmuk.quizer_lib.Quiz;
import by.degmuk.quizer_lib.Result;
import by.degmuk.quizer_lib.exceptions.QuizNotFinishedException;
import by.degmuk.quizer_lib.task_generators.DITaskGenerator;
import by.degmuk.quizer_lib.task_generators.GroupTaskGenerator;
import by.degmuk.quizer_lib.task_generators.PoolTaskGenerator;
import by.degmuk.quizer_lib.tasks.TextTask;
import by.degmuk.quizer_lib.tasks.math_tasks.EquationTask;
import by.degmuk.quizer_lib.tasks.math_tasks.ExpressionTask;
import by.degmuk.quizer_lib.tasks.math_tasks.MathTask;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {
    private final JFrame frame = new JFrame("Quizer GUI");
    private Quiz quiz = null;

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

    private static void createAndShowGUI() {
        Main main = new Main();
        main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.frame.setContentPane(main.getTaskChooserPane());

        main.frame.setMinimumSize(new Dimension(800, 600));

        main.frame.pack();
        main.frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private Container getQuizPane() {
        Container pane = new Container();
        pane.setLayout(new GridLayout(5, 3));
        JTextArea label = new JTextArea();
        label.setLineWrap(true);

        pane.add(Box.createVerticalGlue());
        pane.add(Box.createVerticalGlue());
        pane.add(Box.createVerticalGlue());

        pane.add(Box.createHorizontalGlue());
        pane.add(label);
        pane.add(Box.createHorizontalGlue());

        pane.add(Box.createVerticalGlue());
        pane.add(Box.createVerticalGlue());
        pane.add(Box.createVerticalGlue());

        label.setText(quiz.nextTask().getText());

        JTextField answerInputField = new JTextField();
        answerInputField.setMaximumSize(new Dimension(50, 20));
        answerInputField.addActionListener(actionEvent -> {
            if (answerInputField.getText().isEmpty()) {
                return;
            }
            answerInputField.setRequestFocusEnabled(true);
            if (quiz.provideAnswer(answerInputField.getText()) ==
                    Result.INCORRECT_INPUT) {
                JOptionPane.showMessageDialog(null, "Введите корректный ввод");
            } else {
                if (!quiz.isFinished()) {
                    label.setText(quiz.nextTask().getText());
                    answerInputField.setText("");
                } else {
                    try {
                        JOptionPane.showMessageDialog(null,
                                "Ваша отметка " + quiz.getMark() + " из 100");
                    } catch (QuizNotFinishedException e) {
                        e.printStackTrace();
                    }
                    frame.setContentPane(getTaskChooserPane());
                    frame.setVisible(true);
                    frame.repaint();
                }
            }
        });

        pane.add(Box.createHorizontalGlue());
        pane.add(answerInputField);
        pane.add(Box.createHorizontalGlue());

        return pane;
    }

    private Container getTaskChooserPane() {
        Container pane = new Container();
        pane.setLayout(new GridLayout(5, 3));

        JLabel label = new JLabel("Введите название теста");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        pane.add(Box.createVerticalGlue());
        pane.add(Box.createVerticalGlue());
        pane.add(Box.createVerticalGlue());

        pane.add(Box.createHorizontalGlue());
        pane.add(label);
        pane.add(Box.createHorizontalGlue());

        pane.add(Box.createVerticalGlue());
        pane.add(Box.createVerticalGlue());
        pane.add(Box.createVerticalGlue());

        JTextField testNameInputField = new JTextField();
        testNameInputField.setMaximumSize(new Dimension(50, 20));
        testNameInputField.setMinimumSize(new Dimension(50, 20));
        testNameInputField.setPreferredSize(new Dimension(50, 20));
        testNameInputField.addActionListener(actionEvent -> {
            if (testNameInputField.getText().isEmpty()) {
                return;
            }
            String testName = testNameInputField.getText();
            testNameInputField.setText("");
            testNameInputField.setRequestFocusEnabled(true);
            Map<String, Quiz> quizMap = getQuizMap();
            if (!quizMap.containsKey(testName)) {
                JOptionPane.showMessageDialog(null,
                        "Введите название существующего теста");
            } else {
                this.quiz = quizMap.get(testName);
                frame.setContentPane(getQuizPane());
                frame.setVisible(true);
                frame.repaint();
            }
        });

        pane.add(Box.createHorizontalGlue());
        pane.add(testNameInputField);
        pane.add(Box.createHorizontalGlue());

        return pane;
    }
}
