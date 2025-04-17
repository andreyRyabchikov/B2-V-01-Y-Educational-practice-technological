import java.util.Random;

public class ProblemGenerator {
    private final Random random = new Random();
    private final int operation;
    private final int difficulty;

    public ProblemGenerator(int operation, int difficulty) {
        this.operation = operation;
        this.difficulty = difficulty;
    }

    public MathProblem generateProblem() {
        int maxValue = switch(difficulty) {
            case 1 -> 10;
            case 2 -> 50;
            case 3 -> 100;
            default -> 50;
        };

        return switch(operation) {
            case 1 -> generateAddition(maxValue);
            case 2 -> generateSubtraction(maxValue);
            case 3 -> generateMultiplication(maxValue);
            default -> throw new IllegalArgumentException("Неверная операция");
        };
    }

    private MathProblem generateAddition(int max) {
        int a = random.nextInt(max) + 1;
        int b = random.nextInt(max) + 1;
        return new MathProblem(a, b, "+", a + b);
    }

    private MathProblem generateSubtraction(int max) {
        int b = random.nextInt(max) + 1;
        int a = b + random.nextInt(max);
        return new MathProblem(a, b, "-", a - b);
    }

    private MathProblem generateMultiplication(int max) {
        int limit = (int) Math.sqrt(max);
        int a = random.nextInt(limit) + 1;
        int b = random.nextInt(limit) + 1;
        return new MathProblem(a, b, "×", a * b);
    }
}