import java.util.Scanner;

/*
 * Улучшенный математический тренажёр с:
 * - Обработкой некорректного ввода
 * - Уровнями сложности
 * - Таймером выполнения
 * - Расширенной статистикой
 * - Повторными попытками
 */
public class MathQuiz {
    private static final int TOTAL_QUESTIONS = 5;
    private static long startTime;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("🌞 Добро пожаловать в математический тренажёр! 🌞");
        do {
            startTime = System.currentTimeMillis();
            runQuizSession(scanner);
        } while (askForRepeat(scanner));

        scanner.close();
    }

    private static void runQuizSession(Scanner scanner) {
        int operation = selectOperation(scanner);
        int difficulty = selectDifficulty(scanner);
        ScoreManager scoreManager = new ScoreManager(TOTAL_QUESTIONS);

        processQuiz(scanner, operation, difficulty, scoreManager);
        showExtendedResults(scoreManager);
    }

    private static int selectOperation(Scanner scanner) {
        while(true) {
            System.out.println("\nВыбери операцию:");
            System.out.println("1 - Сложение");
            System.out.println("2 - Вычитание");
            System.out.println("3 - Умножение");
            System.out.print("➤ Твой выбор: ");

            if(scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if(choice >= 1 && choice <= 3) return choice;
            }
            scanner.nextLine();
            System.out.println("❌ Ошибка! Введи число от 1 до 3");
        }
    }

    private static int selectDifficulty(Scanner scanner) {
        while(true) {
            System.out.println("\nВыбери сложность:");
            System.out.println("1 - Лёгкий (числа до 10)");
            System.out.println("2 - Средний (числа до 50)");
            System.out.println("3 - Сложный (числа до 100)");
            System.out.print("➤ Твой выбор: ");

            if(scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                if(choice >= 1 && choice <= 3) return choice;
            }
            scanner.nextLine();
            System.out.println("❌ Ошибка! Введи число от 1 до 3");
        }
    }

    private static void processQuiz(Scanner scanner, int operation,
                                    int difficulty, ScoreManager scoreManager) {
        ProblemGenerator generator = new ProblemGenerator(operation, difficulty);
        System.out.println("\n✎ Реши " + TOTAL_QUESTIONS + " примеров:");

        for(int i = 1; i <= TOTAL_QUESTIONS; i++) {
            MathProblem problem = generator.generateProblem();
            askQuestion(i, problem, scanner, scoreManager);
        }
    }

    private static void askQuestion(int num, MathProblem problem,
                                    Scanner scanner, ScoreManager scoreManager) {
        while(true) {
            System.out.print("Пример " + num + ": " + problem.getQuestion());

            if(scanner.hasNextInt()) {
                int answer = scanner.nextInt();
                checkAnswer(problem, answer, scoreManager);
                return;
            }

            scanner.nextLine();
            System.out.println("⚠ Это не число! Попробуй ещё раз");
        }
    }

    private static void checkAnswer(MathProblem problem, int userAnswer,
                                    ScoreManager scoreManager) {
        if(userAnswer == problem.getCorrectAnswer()) {
            System.out.println("✅ Верно! (+" + scoreManager.addCorrect() + " баллов)");
        } else {
            System.out.println("❌ Неверно. Правильный ответ: " + problem.getCorrectAnswer());
        }
    }

    private static void showExtendedResults(ScoreManager scoreManager) {
        long timeSpent = (System.currentTimeMillis() - startTime) / 1000;
        System.out.println("\n🏁 Результаты:");
        System.out.println("Правильных ответов: " + scoreManager.getCorrectAnswers() + "/" + TOTAL_QUESTIONS);
        System.out.println("Затраченное время: " + timeSpent + " сек");
        System.out.println("Среднее время на вопрос: " + timeSpent/TOTAL_QUESTIONS + " сек");
        scoreManager.printFinalResult();
    }

    private static boolean askForRepeat(Scanner scanner) {
        System.out.print("\nПопробовать ещё раз? (да/нет): ");
        scanner.nextLine();
        return scanner.nextLine().equalsIgnoreCase("да");
    }
}