import java.util.Scanner;

/*
 * Математический тренажёр для учеников 3-4 классов
 *
 * Основная задача проекта:
 * Развитие и закрепление базовых арифметических навыков через интерактивные упражнения
 *
 * Состав проекта:
 * - MathQuiz:         Главный класс, управляющий потоком программы
 * - MathProblem:      Хранит данные о математической задаче (числа, оператор, ответ)
 * - ScoreManager:     Отвечает за подсчёт и отображение результатов
 * - ProblemGenerator: Генерирует случайные задачи выбранного типа
 *
 * Основной функционал:
 * 1. Интерактивный выбор типа заданий
 * 2. Генерация адаптированных примеров:
 *    - Сложение: числа до 50
 *    - Вычитание: гарантированно положительный результат
 *    - Умножение: таблица до 10×10
 * 3. Интеллектуальная система подсчёта очков
 * 4. Мотивационная обратная связь
 * 5. Простой текстовый интерфейс с подсказками
 *
 * Как использовать:
 * 1. Скомпилировать: javac MathQuiz.java
 * 2. Запустить:      java MathQuiz
 * 3. Следовать инструкциям на экране
 */
public class MathQuiz {
    private static final int TOTAL_QUESTIONS = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("🌞 Добро пожаловать в математический тренажёр! 🌞");
        int operation = selectOperation(scanner);

        ScoreManager scoreManager = new ScoreManager(TOTAL_QUESTIONS);
        processQuiz(scanner, operation, scoreManager);

        scanner.close();
    }

    private static int selectOperation(Scanner scanner) {
        System.out.println("\nВыбери операцию:");
        System.out.println("1 - Сложение");
        System.out.println("2 - Вычитание");
        System.out.println("3 - Умножение");
        System.out.print("➤ Твой выбор: ");
        return scanner.nextInt();
    }

    private static void processQuiz(Scanner scanner, int operation, ScoreManager scoreManager) {
        System.out.println("\n✎ Реши " + TOTAL_QUESTIONS + " примеров:");
        ProblemGenerator generator = new ProblemGenerator(operation);

        for (int i = 1; i <= TOTAL_QUESTIONS; i++) {
            MathProblem problem = generator.generateProblem();
            askQuestion(i, problem, scanner, scoreManager);
        }

        scoreManager.printFinalResult();
    }

    private static void askQuestion(int questionNumber, MathProblem problem,
                                    Scanner scanner, ScoreManager scoreManager) {
        System.out.print("Пример " + questionNumber + ": " + problem.getQuestion());
        int userAnswer = scanner.nextInt();

        if (userAnswer == problem.getCorrectAnswer()) {
            System.out.println("✅ Верно!\n");
            scoreManager.incrementCorrectAnswers();
        } else {
            System.out.println("❌ Неверно. Правильный ответ: " + problem.getCorrectAnswer() + "\n");
        }
    }
}