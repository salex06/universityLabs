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
            printStream.print("Введите код программы: ");
            String data = scanner.nextLine();
            boolean result = new CodeParser().parse(data);
            if(result){
                printStream.println("Программа отработала корректно!");
                printStream.println(VariableStorage.getAllData());
            }
        } catch (RuntimeException e) {
            printStream.print(e.getMessage());
        }
    }
}
