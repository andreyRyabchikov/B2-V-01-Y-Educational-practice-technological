public class ScoreManager {
    private int correctAnswers;
    private int streak;
    private int maxStreak;
    private final int totalQuestions;

    public ScoreManager(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int addCorrect() {
        correctAnswers++;
        streak++;
        if(streak > maxStreak) maxStreak = streak;
        return streakBonus();
    }

    private int streakBonus() {
        return streak >= 3 ? streak : 0;
    }

    public void printFinalResult() {
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