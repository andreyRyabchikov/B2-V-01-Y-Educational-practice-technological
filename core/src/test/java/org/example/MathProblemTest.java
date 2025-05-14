package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MathProblemTest {

    // Тест основных операций через параметризованный тест
    @ParameterizedTest
    @CsvSource({
            "5, 3, +, 8, '5 + 3 = '",
            "10, 4, -, 6, '10 - 4 = '",
            "7, 2, ×, 14, '7 × 2 = '",
            "20, 5, ÷, 4, '20 ÷ 5 = '",
            "0, 5, +, 5, '0 + 5 = '",
            "-3, 2, -, -5, '-3 - 2 = '",
            "15, 0, +, 15, '15 + 0 = '"
    })
    void testMathProblem(int num1, int num2, String operator, int answer, String expectedQuestion) {
        // Arrange
        MathProblem problem = new MathProblem(num1, num2, operator, answer);

        // Act & Assert
        assertEquals(expectedQuestion, problem.getQuestion(), "Некорректный формат вопроса");
        assertEquals(answer, problem.getCorrectAnswer(), "Неправильный ответ");
    }

    // Тест специальных случаев
    @Test
    void testDivisionWithLargeNumbers() {
        // Arrange
        MathProblem problem = new MathProblem(1000000, 500000, "÷", 2);

        // Act & Assert
        assertEquals("1000000 ÷ 500000 = ", problem.getQuestion());
        assertEquals(2, problem.getCorrectAnswer());
    }

    @Test
    void testMultiplicationWithNegativeNumbers() {
        // Arrange
        MathProblem problem = new MathProblem(-4, -5, "×", 20);

        // Act & Assert
        assertEquals("-4 × -5 = ", problem.getQuestion());
        assertEquals(20, problem.getCorrectAnswer());
    }

    // Тест нестандартных операторов
    @Test
    void testCustomOperator() {
        // Arrange
        MathProblem problem = new MathProblem(3, 4, "**", 81);

        // Act & Assert
        assertEquals("3 ** 4 = ", problem.getQuestion());
        assertEquals(81, problem.getCorrectAnswer());
    }
}