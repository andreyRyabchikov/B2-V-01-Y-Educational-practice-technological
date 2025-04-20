package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class ProblemGenerator {
    private static final Logger logger = LogManager.getLogger(ProblemGenerator.class);

    private final Random random = new Random();
    private final int operation;
    private final int difficulty;

    public ProblemGenerator(int operation, int difficulty) {
        this.operation = operation;
        this.difficulty = difficulty;
        logger.info("Инициализирован ProblemGenerator с операцией {} и уровнем сложности {}", operation, difficulty);
    }

    public MathProblem generateProblem() {
        int maxValue = switch(difficulty) {
            case 1 -> 10;
            case 2 -> 50;
            case 3 -> 100;
            default -> 50;
        };
        logger.debug("Максимальное значение для генерации: {}", maxValue);

        return switch(operation) {
            case 1 -> generateAddition(maxValue);
            case 2 -> generateSubtraction(maxValue);
            case 3 -> generateMultiplication(maxValue);
            default -> {
                logger.error("Неверная операция: {}", operation);
                throw new IllegalArgumentException("Неверная операция");
            }
        };
    }

    private MathProblem generateAddition(int max) {
        int a = random.nextInt(max) + 1;
        int b = random.nextInt(max) + 1;
        logger.debug("Генерация сложения: {} + {}", a, b);
        return new MathProblem(a, b, "+", a + b);
    }

    private MathProblem generateSubtraction(int max) {
        int b = random.nextInt(max) + 1;
        int a = b + random.nextInt(max);
        logger.debug("Генерация вычитания: {} - {}", a, b);
        return new MathProblem(a, b, "-", a - b);
    }

    private MathProblem generateMultiplication(int max) {
        int limit = (int) Math.sqrt(max);
        int a = random.nextInt(limit) + 1;
        int b = random.nextInt(limit) + 1;
        logger.debug("Генерация умножения: {} × {}", a, b);
        return new MathProblem(a, b, "×", a * b);
    }
}
