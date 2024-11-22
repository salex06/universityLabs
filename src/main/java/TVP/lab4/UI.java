package TVP.lab4;

import java.io.PrintStream;
import java.util.Scanner;

public class UI {
    private final Scanner scanner;
    private final PrintStream printStream;

    public UI() {
        scanner = new Scanner(System.in);
        printStream = new PrintStream(System.out);
    }

    public void run() {
        try {
            printStream.print("Введите выражение: ");
            String expr = scanner.nextLine();
            boolean result = Parser.parse(expr);
            printStream.print("Результат выражения: " + result);
        } catch (RuntimeException e) {
            printStream.print("Некорректное логическое выражение!");
        }
    }
}
