package TVP.lab3;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class FiniteStateMachine {
    private static final String FINAL_STATE = "B";
    private final Map<Map.Entry<String, Character>, String> transitions;
    private final Scanner scanner;
    private String currState;

    public FiniteStateMachine() {
        currState = "S";
        scanner = new Scanner(System.in);
        transitions = new HashMap<>();
        transitions.put(Map.entry("S", 'g'), "A");
        transitions.put(Map.entry("A", 'h'), "B");
        transitions.put(Map.entry("B", 'h'), "CH");
        transitions.put(Map.entry("CH", 'h'), "I");
        transitions.put(Map.entry("I", 'g'), "J");
        transitions.put(Map.entry("J", 'g'), "K");
        transitions.put(Map.entry("K", 'h'), "B");
        transitions.put(Map.entry("CH", 'g'), "D");
        transitions.put(Map.entry("D", 'h'), "E");
        transitions.put(Map.entry("E", 'g'), "G");
        transitions.put(Map.entry("G", 'g'), "B");
    }

    public void run() {
        System.out.println("Выполните ввод: ");
        String toParse = scanner.nextLine();
        if (toParse.isBlank()) {
            System.out.println("Некорректный ввод");
            return;
        }
        char noTransitionValue = ' ';
        boolean _isNoTransition = false;
        for (int i = 0; i < toParse.length(); i++) {
            char value = toParse.charAt(i);
            if (value != 'g' && value != 'h') {
                System.out.println("Некорректный ввод!");
                return;
            }

            if (!transitions.containsKey(Map.entry(currState, value))) {
                _isNoTransition = true;
                noTransitionValue = value;
                break;
            } else {
                currState = transitions.get(Map.entry(currState, value));
            }
        }
        if (_isNoTransition) {
            System.out.println("Строка символов не входит в язык, т.к. нет перехода из " + currState + " через " + noTransitionValue);
        } else if (!Objects.equals(currState, FINAL_STATE)) {
            System.out.println("Строка символов не входит в язык, т.к. не достигнуто конечное состояние");
        } else {
            System.out.println("Строка символов входит в язык");
        }
    }
}
