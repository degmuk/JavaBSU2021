import by.degmuk.quizer.Result;
import by.degmuk.quizer.tasks.Task;
import by.degmuk.quizer.tasks.math_tasks.MathTask;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class IntegerEquationMathTaskGeneratorTest {
    @Test
    void compileabilityTest() throws IllegalAccessException {
        IntegerEquationMathTaskGenerator generator =
                new IntegerEquationMathTaskGenerator(42, 228, true, true, true,
                        true);
        IntegerMathTaskGenerator integerMathTaskGenerator = generator;
        assertEquals(integerMathTaskGenerator.generate().getClass(),
                IntegerEquationMathTask.class);
        EquationTaskGenerator equationTaskGenerator = generator;
        assertEquals(equationTaskGenerator.generate().getClass(),
                IntegerEquationMathTask.class);
        MathTask.Generator mathTaskGenerator = generator;
        assertEquals(mathTaskGenerator.generate().getClass(),
                IntegerEquationMathTask.class);
        Task.Generator taskGenerator = generator;
        assertEquals(taskGenerator.generate().getClass(),
                IntegerEquationMathTask.class);
    }

    @Test
    void constructionTest() {
        assertDoesNotThrow(
                () -> new IntegerEquationMathTaskGenerator(42, 228, true, true,
                        true, true));
        assertDoesNotThrow(
                () -> new IntegerEquationMathTaskGenerator(42, 228, false,
                        false, false, true));
        assertDoesNotThrow(
                () -> new IntegerEquationMathTaskGenerator(Integer.MIN_VALUE,
                        Integer.MAX_VALUE, true, true, true, true));
        assertThrows(IllegalArgumentException.class,
                () -> new IntegerEquationMathTaskGenerator(228, 42, true, true,
                        true, true));
        assertThrows(IllegalArgumentException.class,
                () -> new IntegerEquationMathTaskGenerator(Integer.MAX_VALUE,
                        Integer.MIN_VALUE, true, true, true, true));
        assertThrows(IllegalArgumentException.class,
                () -> new IntegerEquationMathTaskGenerator(42, 228, false,
                        false, false, false));
    }

    int[] getNums(IntegerEquationMathTask task) {
        Scanner scanner = new Scanner(task.getText());
        int[] nums = new int[2];
        nums[0] = scanner.nextInt();
        scanner.next();
        scanner.next();
        scanner.next();
        nums[1] = scanner.nextInt();
        return nums;
    }

    @Test
    void gettersTest() throws IllegalAccessException {
        {
            IntegerEquationMathTaskGenerator generator =
                    new IntegerEquationMathTaskGenerator(42, 228, true, true,
                            true, true);
            assertEquals(generator.getMinNumber(), 42);
            assertEquals(generator.getMaxNumber(), 228);
        }
        {
            IntegerEquationMathTaskGenerator generator =
                    new IntegerEquationMathTaskGenerator(42, 228, false, false,
                            false, true);
            assertEquals(generator.getMinNumber(), 42);
            assertEquals(generator.getMaxNumber(), 228);
        }
        {
            IntegerEquationMathTaskGenerator generator =
                    new IntegerEquationMathTaskGenerator(Integer.MIN_VALUE,
                            Integer.MAX_VALUE, true, true, true, true);
            assertEquals(generator.getMinNumber(), Integer.MIN_VALUE);
            assertEquals(generator.getMaxNumber(), Integer.MAX_VALUE);
        }
    }

    @Test
    void generationTest() throws IllegalAccessException {
        IntegerEquationMathTaskGenerator generator =
                new IntegerEquationMathTaskGenerator(-42, 228, true, true, true,
                        true);
        for (int i = 0; i < 1000; ++i) {
            IntegerEquationMathTask task = generator.generate();
            int[] nums = getNums(task);
            int num1 = nums[0];
            int num2 = nums[1];
            assertTrue(generator.getMinNumber() <= num1 &&
                    num1 <= generator.getMaxNumber());
            assertTrue(generator.getMinNumber() <= num2 &&
                    num2 <= generator.getMaxNumber());
        }
    }

    @Test
    void sumTest() throws IllegalAccessException {
        IntegerEquationMathTaskGenerator generator =
                new IntegerEquationMathTaskGenerator(-42, 228, true, false,
                        false, false);
        for (int i = 0; i < 1000; ++i) {
            IntegerEquationMathTask task = generator.generate();
            int[] nums = getNums(task);
            int num1 = nums[0];
            int num2 = nums[1];
            assertEquals(Result.INCORRECT_INPUT, task.validate("--1"));
            assertEquals(Result.WRONG, task.validate(num2 - num1 + 42));
            assertEquals(Result.OK, task.validate(num2 - num1));
        }
    }

    @Test
    void differenceTest() throws IllegalAccessException {
        IntegerEquationMathTaskGenerator generator =
                new IntegerEquationMathTaskGenerator(-42, 228, false, true,
                        false, false);
        for (int i = 0; i < 1000; ++i) {
            IntegerEquationMathTask task = generator.generate();
            int[] nums = getNums(task);
            int num1 = nums[0];
            int num2 = nums[1];
            assertEquals(Result.INCORRECT_INPUT, task.validate("--1"));
            assertEquals(Result.WRONG, task.validate(num1 - num2 + 42));
            assertEquals(Result.OK, task.validate(num1 - num2));
        }
    }

    @Test
    void multiplicationTest() throws IllegalAccessException {
        IntegerEquationMathTaskGenerator generator =
                new IntegerEquationMathTaskGenerator(-42, 228, false, false,
                        true, false);
        for (int i = 0; i < 1000; ++i) {
            IntegerEquationMathTask task = generator.generate();
            int[] nums = getNums(task);
            int num1 = nums[0];
            int num2 = nums[1];
            assertNotEquals(0, num1);
            assertEquals(0, num2 % num1);
            assertEquals(Result.INCORRECT_INPUT, task.validate("--1"));
            assertEquals(Result.WRONG, task.validate(num2 / num1 + 42));
            assertEquals(Result.OK, task.validate(num2 / num1));
        }
    }

    @Test
    void divisionTest() throws IllegalAccessException {
        IntegerEquationMathTaskGenerator generator =
                new IntegerEquationMathTaskGenerator(-42, 228, false, false,
                        false, true);
        for (int i = 0; i < 1000; ++i) {
            IntegerEquationMathTask task = generator.generate();
            int[] nums = getNums(task);
            int num1 = nums[0];
            int num2 = nums[1];
            assertNotEquals(0, num1);
            assertNotEquals(0, num2);
            assertEquals(0, num1 % num2);
            assertEquals(Result.INCORRECT_INPUT, task.validate("--1"));
            assertEquals(Result.WRONG, task.validate(num1 / num2 + 42));
            assertEquals(Result.OK, task.validate(num1 / num2));
        }
    }
}
