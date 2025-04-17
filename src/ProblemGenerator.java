import java.util.Random;

public class ProblemGenerator {
    private final Random random = new Random();
    private final int operation;

    public ProblemGenerator(int operation) {
        this.operation = operation;
    }

    public MathProblem generateProblem() {
        return switch (operation) {
            case 1 -> generateAdditionProblem();
            case 2 -> generateSubtractionProblem();
            case 3 -> generateMultiplicationProblem();
            default -> throw new IllegalArgumentException("Неверная операция");
        };
    }

    private MathProblem generateAdditionProblem() {
        int num1 = random.nextInt(50) + 1;
        int num2 = random.nextInt(50) + 1;
        return new MathProblem(num1, num2, "+", num1 + num2);
    }

    private MathProblem generateSubtractionProblem() {
        int num2 = random.nextInt(50) + 1;
        int num1 = num2 + random.nextInt(50);
        return new MathProblem(num1, num2, "-", num1 - num2);
    }

    private MathProblem generateMultiplicationProblem() {
        int num1 = random.nextInt(10) + 1;
        int num2 = random.nextInt(10) + 1;
        return new MathProblem(num1, num2, "×", num1 * num2);
    }
}