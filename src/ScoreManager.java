public class ScoreManager {
    private int correctAnswers;
    private final int totalQuestions;

    public ScoreManager(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public void incrementCorrectAnswers() {
        correctAnswers++;
    }

    public void printFinalResult() {
        System.out.println("🏁 Твой результат: " + correctAnswers + " из " + totalQuestions);
        if (correctAnswers == totalQuestions) {
            System.out.println("🎉 Отлично! Ты гений математики!");
        } else if (correctAnswers >= totalQuestions / 2) {
            System.out.println("👍 Хорошо! Ты молодец!");
        } else {
            System.out.println("💪 Не сдавайся! Ты сможешь лучше!");
        }
    }
}