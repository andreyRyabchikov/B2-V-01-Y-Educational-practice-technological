package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.example.ProblemGenerator.*;

class ProblemGeneratorTest {

    // Тесты конструктора остаются без изменений
    @ParameterizedTest
    @ValueSource(ints = {0, 5, -1})
    void constructor_ShouldThrowForInvalidOperations(int invalidOperation) {
        assertThrows(IllegalArgumentException.class,
                () -> new ProblemGenerator(invalidOperation, DIFFICULTY_EASY));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 4, -1})
    void constructor_ShouldThrowForInvalidDifficulty(int invalidDifficulty) {
        assertThrows(IllegalArgumentException.class,
                () -> new ProblemGenerator(ADDITION, invalidDifficulty));
    }

    // Тесты генерации задач с использованием TestableRandom
    static class TestableRandom extends Random {
        private final int[] values;
        private int index = 0;

        TestableRandom(int... values) {
            this.values = values;
        }

        @Override
        public int nextInt(int bound) {
            return values[index++ % values.length];
        }
    }

    @Test
    void generateAddition_ShouldCreateValidProblem() {
        ProblemGenerator generator = new ProblemGenerator(
                ADDITION, DIFFICULTY_EASY, new TestableRandom(3, 7));

        MathProblem problem = generator.generateProblem();

        assertAll("Проверка сложения",
                () -> assertEquals(4, problem.getNum1()),
                () -> assertEquals(8, problem.getNum2()),
                () -> assertEquals("+", problem.getOperator()),
                () -> assertEquals(12, problem.getCorrectAnswer())
        );
    }

    @Test
    void generateSubtraction_ShouldProduceNonNegativeResult() {
        ProblemGenerator generator = new ProblemGenerator(
                SUBTRACTION, DIFFICULTY_MEDIUM, new TestableRandom(9, 15));

        MathProblem problem = generator.generateProblem();

        assertAll("Проверка вычитания",
                () -> assertEquals(25, problem.getNum1()),
                () -> assertEquals(10, problem.getNum2()),
                () -> assertEquals("-", problem.getOperator()),
                () -> assertEquals(15, problem.getCorrectAnswer())
        );
    }

    @Test
    void generateMultiplication_ShouldNotExceedMax() {
        ProblemGenerator generator = new ProblemGenerator(
                MULTIPLICATION, DIFFICULTY_HARD, new TestableRandom(24, 3));

        MathProblem problem = generator.generateProblem();

        assertAll("Проверка умножения",
                () -> assertEquals(25, problem.getNum1()),
                () -> assertEquals(4, problem.getNum2()),
                () -> assertEquals("×", problem.getOperator()),
                () -> assertEquals(100, problem.getCorrectAnswer())
        );
    }

    @Test
    void generateDivision_ShouldReturnWholeNumber() {
        ProblemGenerator generator = new ProblemGenerator(
                DIVISION, DIFFICULTY_EASY, new TestableRandom(2, 1));

        MathProblem problem = generator.generateProblem();

        assertAll("Проверка деления",
                () -> assertEquals(6, problem.getNum1()),
                () -> assertEquals(3, problem.getNum2()),
                () -> assertEquals("÷", problem.getOperator()),
                () -> assertEquals(2, problem.getCorrectAnswer())
        );
    }

    // Остальные тесты остаются без изменений
    @ParameterizedTest
    @CsvSource({
            "ADDITION, DIFFICULTY_EASY, 10",
            "SUBTRACTION, DIFFICULTY_MEDIUM, 50",
            "MULTIPLICATION, DIFFICULTY_HARD, 100",
            "DIVISION, DIFFICULTY_EASY, 10"
    })

    @RepeatedTest(50)
    void subtractionResults_ShouldAlwaysBeNonNegative() {
        ProblemGenerator generator = new ProblemGenerator(SUBTRACTION, DIFFICULTY_HARD);
        MathProblem problem = generator.generateProblem();
        assertTrue(problem.getCorrectAnswer() >= 0);
    }

    @RepeatedTest(50)
    void divisionResults_ShouldBeWholeNumbers() {
        ProblemGenerator generator = new ProblemGenerator(DIVISION, DIFFICULTY_MEDIUM);
        MathProblem problem = generator.generateProblem();
        assertEquals(0, problem.getNum1() % problem.getNum2());
    }
}