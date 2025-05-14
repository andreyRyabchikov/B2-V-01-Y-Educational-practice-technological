package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ScoreManagerTest {
    private ScoreManager scoreManager;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        scoreManager = new ScoreManager(10);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Инициализация с правильным количеством вопросов")
    void constructor_ShouldSetTotalQuestions() {
        ScoreManager sm = new ScoreManager(15);
        assertEquals(15, getTotalQuestions(sm));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10})
    @DisplayName("Добавление правильных ответов увеличивает счетчик")
    void addCorrect_ShouldIncrementCounters(int times) {
        for (int i = 0; i < times; i++) {
            scoreManager.addCorrect();
        }
        assertEquals(times, scoreManager.getCorrectAnswers());
    }

    @Test
    @DisplayName("Серия правильных ответов учитывается")
    void addCorrect_ShouldTrackStreak() {
        scoreManager.addCorrect();
        scoreManager.addCorrect();
        assertEquals(2, getCurrentStreak(scoreManager));
    }

    @Test
    @DisplayName("Максимальная серия обновляется")
    void addCorrect_ShouldUpdateMaxStreak() {
        scoreManager.addCorrect();
        scoreManager.addCorrect();
        assertEquals(2, getMaxStreak(scoreManager));

        scoreManager = new ScoreManager(10);
        scoreManager.addCorrect();
        scoreManager.addCorrect();
        scoreManager.addCorrect();
        assertEquals(3, getMaxStreak(scoreManager));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 0",
            "2, 0",
            "3, 3",
            "5, 5"
    })
    @DisplayName("Бонус за серию рассчитывается правильно")
    void streakBonus_ShouldReturnCorrectValue(int streakLength, int expectedBonus) {
        for (int i = 0; i < streakLength; i++) {
            scoreManager.addCorrect();
        }
        assertEquals(expectedBonus, getCurrentBonus(scoreManager));
    }

    @Test
    @DisplayName("Печать результатов при идеальном результате")
    void printFinalResult_ShouldShowPerfectMessage() {
        ScoreManager perfectScore = new ScoreManager(3);
        perfectScore.addCorrect();
        perfectScore.addCorrect();
        perfectScore.addCorrect();

        perfectScore.printFinalResult();

        String output = outContent.toString();
        assertTrue(output.contains("★ Максимальная серия: 3"));
        assertTrue(output.contains("🎉 Идеальный результат!"));
    }

    // Вспомогательные методы для доступа к приватным полям
    private int getTotalQuestions(ScoreManager sm) {
        try {
            var field = ScoreManager.class.getDeclaredField("totalQuestions");
            field.setAccessible(true);
            return (int) field.get(sm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getCurrentStreak(ScoreManager sm) {
        try {
            var field = ScoreManager.class.getDeclaredField("streak");
            field.setAccessible(true);
            return (int) field.get(sm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getMaxStreak(ScoreManager sm) {
        try {
            var field = ScoreManager.class.getDeclaredField("maxStreak");
            field.setAccessible(true);
            return (int) field.get(sm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getCurrentBonus(ScoreManager sm) {
        try {
            var method = ScoreManager.class.getDeclaredMethod("streakBonus");
            method.setAccessible(true);
            return (int) method.invoke(sm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}