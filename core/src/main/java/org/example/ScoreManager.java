package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScoreManager {
    private static final Logger logger = LogManager.getLogger(ScoreManager.class);

    private int correctAnswers;
    int streak;
     int maxStreak;
    private final int totalQuestions;

    public ScoreManager(int totalQuestions) {
        this.totalQuestions = totalQuestions;
        logger.info("Инициализирован ScoreManager с общим количеством вопросов: {}", totalQuestions);
    }

    public int addCorrect() {
        correctAnswers++;
        streak++;
        if(streak > maxStreak) {
            maxStreak = streak;
            logger.debug("Новый рекорд серии: {}", maxStreak);
        }
        logger.debug("Правильный ответ добавлен. Текущая серия: {}, Всего правильных: {}", streak, correctAnswers);
        return streakBonus();
    }

    private int streakBonus() {
        return streak >= 3 ? streak : 0;
    }

    public void printFinalResult() {
        logger.info("Печать финального результата. Правильных ответов: {}, Максимальная серия: {}", correctAnswers, maxStreak);

        System.out.println("\n★ Максимальная серия: " + maxStreak);
        if(correctAnswers == totalQuestions) {
            System.out.println("🎉 Идеальный результат!");
        } else if(correctAnswers >= totalQuestions * 0.8) {
            System.out.println("👍 Отличная работа!");
        }
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }
}
