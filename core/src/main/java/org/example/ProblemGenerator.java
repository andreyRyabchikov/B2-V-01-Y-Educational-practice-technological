package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class ProblemGenerator {
    private static final Logger logger = LogManager.getLogger(ProblemGenerator.class);

    public static final int ADDITION = 1;
    public static final int SUBTRACTION = 2;
    public static final int MULTIPLICATION = 3;
    public static final int DIVISION = 4;

    public static final int DIFFICULTY_EASY = 1;
    public static final int DIFFICULTY_MEDIUM = 2;
    public static final int DIFFICULTY_HARD = 3;

    private Random random = new Random();
    private final int operation;
    private final int difficulty;

    public ProblemGenerator(int operation, int difficulty) {
        if (operation < 1 || operation > 4) {
            throw new IllegalArgumentException("Неверная операция: " + operation);
        }
        if (difficulty < 1 || difficulty > 3) {
            throw new IllegalArgumentException("Неверный уровень сложности: " + difficulty);
        }
        this.operation = operation;
        this.difficulty = difficulty;
        logger.info("Инициализирован ProblemGenerator с операцией {} и уровнем сложности {}", operation, difficulty);
    }

    ProblemGenerator(int operation, int difficulty, Random random) {
        this(operation, difficulty);
        this.random = random;
    }

    public MathProblem generateProblem() {
        int maxValue = switch(difficulty) {
            case DIFFICULTY_EASY -> 10;
            case DIFFICULTY_MEDIUM -> 50;
            case DIFFICULTY_HARD -> 100;
            default -> throw new IllegalStateException("Недопустимый уровень сложности: " + difficulty);
        };
        logger.debug("Максимальное значение для генерации: {}", maxValue);

        return switch(operation) {
            case ADDITION -> generateAddition(maxValue);
            case SUBTRACTION -> generateSubtraction(maxValue);
            case MULTIPLICATION -> generateMultiplication(maxValue);
            case DIVISION -> generateDivision(maxValue);
            default -> throw new IllegalArgumentException("Неверная операция: " + operation);
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
        int a = b + random.nextInt(max - b + 1);
        logger.debug("Генерация вычитания: {} - {}", a, b);
        return new MathProblem(a, b, "-", a - b);
    }

    private MathProblem generateMultiplication(int max) {
        int a = random.nextInt(max) + 1;
        int maxB = max / a;
        int b = random.nextInt(maxB) + 1;
        logger.debug("Генерация умножения: {} × {}", a, b);
        return new MathProblem(a, b, "×", a * b);
    }

    private MathProblem generateDivision(int max) {
        int b = random.nextInt(max) + 1;
        int maxK = max / b;
        int k = random.nextInt(maxK) + 1;
        int a = b * k;
        logger.debug("Генерация деления: {} ÷ {}", a, b);
        return new MathProblem(a, b, "÷", k);
    }
}