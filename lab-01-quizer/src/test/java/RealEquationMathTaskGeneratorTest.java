import by.degmuk.quizer.Result;
import by.degmuk.quizer.task_generators.TaskGenerator;
import by.degmuk.quizer.task_generators.math_task_generators.*;
import by.degmuk.quizer.tasks.math_tasks.RealEquationMathTask;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RealEquationMathTaskGeneratorTest {
    @Test
    void compileabilityTest() throws IllegalAccessException {
        RealEquationMathTaskGenerator generator =
                new RealEquationMathTaskGenerator(4, 42, 228, true, true, true,
                        true);
        RealMathTaskGenerator realMathTaskGenerator = generator;
        assertEquals(realMathTaskGenerator.generate().getClass(),
                RealEquationMathTask.class);
        EquationTaskGenerator equationTaskGenerator = generator;
        assertEquals(equationTaskGenerator.generate().getClass(),
                RealEquationMathTask.class);
        MathTaskGenerator mathTaskGenerator = generator;
        assertEquals(mathTaskGenerator.generate().getClass(),
                RealEquationMathTask.class);
        TaskGenerator taskGenerator = generator;
        assertEquals(taskGenerator.generate().getClass(),
                RealEquationMathTask.class);
    }

    @Test
    void constructionTest() {
        assertDoesNotThrow(
                () -> new RealEquationMathTaskGenerator(4, 42, 228, true, true,
                        true, true));
        assertDoesNotThrow(
                () -> new RealEquationMathTaskGenerator(4, 42, 228, false,
                        false, false, true));
        assertThrows(IllegalArgumentException.class,
                () -> new RealEquationMathTaskGenerator(4, 228, 42, true, true,
                        true, true));
        assertThrows(IllegalArgumentException.class,
                () -> new RealEquationMathTaskGenerator(4,42, 228, false,
                        false, false, false));
    }

    double[] getNums(RealEquationMathTask task) {
        Scanner scanner = new Scanner(task.getText());
        double[] nums = new double[2];
        nums[0] = scanner.nextDouble();
        scanner.next();
        scanner.next();
        scanner.next();
        nums[1] = scanner.nextDouble();
        return nums;
    }

    @Test
    void gettersTest() throws IllegalAccessException {
        {
            RealEquationMathTaskGenerator generator =
                    new RealEquationMathTaskGenerator(4,42, 228, true, true,
                            true, true);
            assertEquals(generator.getMinNumber(), 42);
            assertEquals(generator.getMaxNumber(), 228);
        }
        {
            RealEquationMathTaskGenerator generator =
                    new RealEquationMathTaskGenerator(4,42, 228, false, false,
                            false, true);
            assertEquals(generator.getMinNumber(), 42);
            assertEquals(generator.getMaxNumber(), 228);
        }
    }

    @Test
    void generationTest() throws IllegalAccessException {
        RealEquationMathTaskGenerator generator =
                new RealEquationMathTaskGenerator(4,-42, 228, true, true, true,
                        true);
        for (int i = 0; i < 1000; ++i) {
            RealEquationMathTask task = generator.generate();
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
        RealEquationMathTaskGenerator generator =
                new RealEquationMathTaskGenerator(4,-42, 228, true, false,
                        false, false);
        for (int i = 0; i < 1000; ++i) {
            RealEquationMathTask task = generator.generate();
            double[] nums = getNums(task);
            double num1 = nums[0];
            double num2 = nums[1];
            assertEquals(Result.INCORRECT_INPUT, task.validate("--1"));
            assertEquals(Result.WRONG, task.validate(num2 - num1 + 42));
            assertEquals(Result.OK, task.validate(num2 - num1));
        }
    }

    @Test
    void differenceTest() throws IllegalAccessException {
        RealEquationMathTaskGenerator generator =
                new RealEquationMathTaskGenerator(4,-42, 228, false, true,
                        false, false);
        for (int i = 0; i < 1000; ++i) {
            RealEquationMathTask task = generator.generate();
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
        RealEquationMathTaskGenerator generator =
                new RealEquationMathTaskGenerator(4,-42, 228, false, false,
                        true, false);
        for (int i = 0; i < 1000; ++i) {
            RealEquationMathTask task = generator.generate();
            double[] nums = getNums(task);
            double num1 = nums[0];
            double num2 = nums[1];
            assertNotEquals(0, num1);
            assertEquals(Result.INCORRECT_INPUT, task.validate("--1"));
            assertEquals(Result.WRONG, task.validate(num2 / num1 + 42));
            assertEquals(Result.OK, task.validate(num2 / num1));
        }
    }

    @Test
    void divisionTest() throws IllegalAccessException {
        RealEquationMathTaskGenerator generator =
                new RealEquationMathTaskGenerator(4,-42, 228, false, false,
                        false, true);
        for (int i = 0; i < 1000; ++i) {
            RealEquationMathTask task = generator.generate();
            double[] nums = getNums(task);
            double num1 = nums[0];
            double num2 = nums[1];
            assertNotEquals(0, num1);
            assertNotEquals(0, num2);
            assertEquals(Result.INCORRECT_INPUT, task.validate("--1"));
            assertEquals(Result.WRONG, task.validate(num1 / num2 + 42));
            assertEquals(Result.OK, task.validate(num1 / num2));
        }
    }
}
