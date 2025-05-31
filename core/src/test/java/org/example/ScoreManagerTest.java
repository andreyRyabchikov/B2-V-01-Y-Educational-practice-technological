package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        assertEquals(15, getPrivateField(sm, "totalQuestions"));
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
        assertEquals(2, getPrivateField(scoreManager, "streak"));
    }

    @Test
    @DisplayName("Максимальная серия обновляется")
    void addCorrect_ShouldUpdateMaxStreak() {
        scoreManager.addCorrect();
        scoreManager.addCorrect();
        assertEquals(2, getPrivateField(scoreManager, "maxStreak"));

        scoreManager = new ScoreManager(10);
        scoreManager.addCorrect();
        scoreManager.addCorrect();
        scoreManager.addCorrect();
        assertEquals(3, getPrivateField(scoreManager, "maxStreak"));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 0",
            "2, 0",
            "3, 3",
            "5, 5"
    })
    @DisplayName("Бонус за серию рассчитывается правильно")
    void streakBonus_ShouldReturnCorrectValue(int streakLength, int expectedBonus) throws Exception {
        setPrivateField(scoreManager, "streak", streakLength);
        assertEquals(expectedBonus, invokePrivateMethod(scoreManager, "streakBonus"));
    }

    @Test
    @DisplayName("Печать результатов при идеальном результате")
    void printFinalResult_ShouldShowPerfectMessage() {
        // Используем Mockito только для проверки вывода
        PrintStream mockPrintStream = mock(PrintStream.class);
        System.setOut(mockPrintStream);

        ScoreManager perfectScore = new ScoreManager(3);
        setPrivateField(perfectScore, "correctAnswers", 3);
        setPrivateField(perfectScore, "maxStreak", 3);

        perfectScore.printFinalResult();

        verify(mockPrintStream).println(contains("★ Максимальная серия: 3"));
        verify(mockPrintStream).println(contains("🎉 Идеальный результат!"));
    }

    // Вспомогательные методы
    private int getPrivateField(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (int) field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setPrivateField(Object obj, String fieldName, int value) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int invokePrivateMethod(Object obj, String methodName) {
        try {
            var method = obj.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            return (int) method.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}