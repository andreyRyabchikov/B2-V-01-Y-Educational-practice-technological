package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MathProblem {
    private static final Logger logger = LogManager.getLogger(MathProblem.class);

    private final int num1;
    private final int num2;
    private final String operator;
    private final int correctAnswer;

    public MathProblem(int num1, int num2, String operator, int correctAnswer) {
        this.num1 = num1;
        this.num2 = num2;
        this.operator = operator;
        this.correctAnswer = correctAnswer;
        logger.debug("Создан новый MathProblem: {} {} {} = {}", num1, operator, num2, correctAnswer);
    }

    public String getQuestion() {
        return num1 + " " + operator + " " + num2 + " = ";
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}
