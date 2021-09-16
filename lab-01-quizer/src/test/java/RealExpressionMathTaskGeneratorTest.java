import by.degmuk.quizer.Result;
import by.degmuk.quizer.task_generators.TaskGenerator;
import by.degmuk.quizer.task_generators.math_task_generators.ExpressionTaskGenerator;
import by.degmuk.quizer.task_generators.math_task_generators.MathTaskGenerator;
import by.degmuk.quizer.task_generators.math_task_generators.RealExpressionMathTaskGenerator;
import by.degmuk.quizer.task_generators.math_task_generators.RealMathTaskGenerator;
import by.degmuk.quizer.tasks.math_tasks.RealExpressionMathTask;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class RealExpressionMathTaskGeneratorTest {
    @Test
    void compileabilityTest() throws IllegalAccessException {
        RealExpressionMathTaskGenerator generator =
                new RealExpressionMathTaskGenerator(4,42, 228, true, true, true,
                        true);
        RealMathTaskGenerator realMathTaskGenerator = generator;
        assertEquals(realMathTaskGenerator.generate().getClass(),
                RealExpressionMathTask.class);
        ExpressionTaskGenerator expressionTaskGenerator = generator;
        assertEquals(expressionTaskGenerator.generate().getClass(),
                RealExpressionMathTask.class);
        MathTaskGenerator mathTaskGenerator = generator;
        assertEquals(mathTaskGenerator.generate().getClass(),
                RealExpressionMathTask.class);
        TaskGenerator taskGenerator = generator;
        assertEquals(taskGenerator.generate().getClass(),
                RealExpressionMathTask.class);
    }

    @Test
    void constructionTest() {
        assertDoesNotThrow(
                () -> new RealExpressionMathTaskGenerator(4,42, 228, true, true,
                        true, true));
        assertDoesNotThrow(
                () -> new RealExpressionMathTaskGenerator(4,42, 228, false, false,
                        false, true));
        assertThrows(IllegalArgumentException.class,
                () -> new RealExpressionMathTaskGenerator(4,228, 42, true, true,
                        true, true));
        assertThrows(IllegalArgumentException.class,
                () -> new RealExpressionMathTaskGenerator(4,42, 228, false, false,
                        false, false));
    }

    double[] getNums(RealExpressionMathTask task) {
        Scanner scanner = new Scanner(task.getText());
        double[] nums = new double[2];
        nums[0] = scanner.nextDouble();
        scanner.next();
        nums[1] = scanner.nextDouble();
        return nums;
    }

    @Test
    void gettersTest() throws IllegalAccessException {
        {
            RealExpressionMathTaskGenerator generator =
                    new RealExpressionMathTaskGenerator(4,42, 228, true, true,
                            true, true);
            assertEquals(generator.getMinNumber(), 42);
            assertEquals(generator.getMaxNumber(), 228);
        }
        {
            RealExpressionMathTaskGenerator generator =
                    new RealExpressionMathTaskGenerator(4,42, 228, false, false,
                            false, true);
            assertEquals(generator.getMinNumber(), 42);
            assertEquals(generator.getMaxNumber(), 228);
        }
    }

    @Test
    void generationTest() throws IllegalAccessException {
        RealExpressionMathTaskGenerator generator =
                new RealExpressionMathTaskGenerator(4,-42, 228, true, true, true,
                        true);
        for (double i = 0; i < 1000; ++i) {
            RealExpressionMathTask task = generator.generate();
            double[] nums = getNums(task);
            double num1 = nums[0];
            double num2 = nums[1];
            assertTrue(generator.getMinNumber() <= num1 &&
                    num1 <= generator.getMaxNumber());
            assertTrue(generator.getMinNumber() <= num2 &&
                    num2 <= generator.getMaxNumber());
        }
    }

    @Test
    void sumTest() throws IllegalAccessException {
        RealExpressionMathTaskGenerator generator =
                new RealExpressionMathTaskGenerator(4,-42, 228, true, false,
                        false, false);
        for (double i = 0; i < 1000; ++i) {
            RealExpressionMathTask task = generator.generate();
            double[] nums = getNums(task);
            double num1 = nums[0];
            double num2 = nums[1];
            assertEquals(Result.INCORRECT_INPUT, task.validate("--1"));
            assertEquals(Result.WRONG, task.validate(num1 + num2 + 42));
            assertEquals(Result.OK, task.validate(num1 + num2));
        }
    }

    @Test
    void differenceTest() throws IllegalAccessException {
        RealExpressionMathTaskGenerator generator =
                new RealExpressionMathTaskGenerator(4,-42, 228, false, true,
                        false, false);
        for (double i = 0; i < 1000; ++i) {
            RealExpressionMathTask task = generator.generate();
            double[] nums = getNums(task);
            double num1 = nums[0];
            double num2 = nums[1];
            assertEquals(Result.INCORRECT_INPUT, task.validate("--1"));
            assertEquals(Result.WRONG, task.validate(num1 - num2 + 42));
            assertEquals(Result.OK, task.validate(num1 - num2));
        }
    }

    @Test
    void multiplicationTest() throws IllegalAccessException {
        RealExpressionMathTaskGenerator generator =
                new RealExpressionMathTaskGenerator(4,-42, 228, false, false,
                        true, false);
        for (double i = 0; i < 1000; ++i) {
            RealExpressionMathTask task = generator.generate();
            double[] nums = getNums(task);
            double num1 = nums[0];
            double num2 = nums[1];
            assertEquals(Result.INCORRECT_INPUT, task.validate("--1"));
            assertEquals(Result.WRONG, task.validate(num1 * num2 + 42));
            assertEquals(Result.OK, task.validate(num1 * num2));
        }
    }

    @Test
    void divisionTest() throws IllegalAccessException {
        RealExpressionMathTaskGenerator generator =
                new RealExpressionMathTaskGenerator(4,-42, 228, false, false,
                        false, true);
        for (double i = 0; i < 1000; ++i) {
            RealExpressionMathTask task = generator.generate();
            double[] nums = getNums(task);
            double num1 = nums[0];
            double num2 = nums[1];
            assertNotEquals(0, num2);
            assertEquals(Result.INCORRECT_INPUT, task.validate("--1"));
            assertEquals(Result.WRONG, task.validate(num1 / num2 + 42));
            assertEquals(Result.OK, task.validate(num1 / num2));
        }
    }
}
