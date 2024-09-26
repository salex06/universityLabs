package TVP.lab2;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FiniteStateMachine {
    Map<Map.Entry<Character, Integer>, Character>  transitions;
    Scanner scanner;
    Character currState;
    boolean _isNoTransition = false;
    boolean _isInTheEnd = false;
    public FiniteStateMachine(){
        currState = 'S';
        scanner = new Scanner(System.in);
        transitions = new HashMap<>();
        transitions.put(Map.entry('S', 1), 'B');
        transitions.put(Map.entry('S', 3), 'A');
        transitions.put(Map.entry('S', 2), 'C');
        transitions.put(Map.entry('B', 1), 'B');
        transitions.put(Map.entry('B', 2), 'Z');
        transitions.put(Map.entry('A', 3), 'A');
        transitions.put(Map.entry('A', 1), 'Z');
        transitions.put(Map.entry('C', 3), 'C');
        transitions.put(Map.entry('C', 1), 'Z');
        transitions.put(Map.entry('Z', 2), 'C');
        transitions.put(Map.entry('Z', 3), 'B');
        transitions.put(Map.entry('Z', 1), 'A');
    }

    public void run() {
        System.out.println("Выполните ввод: ");
        String toParse = scanner.nextLine();
        Matcher matcher;
        Pattern pattern = Pattern.compile("^[1-3]+$");
        matcher = pattern.matcher(toParse);
        int noTransitionValue = 0;
        if (matcher.matches()) {
            Character nextState = currState;
            for (int i = 0; i < toParse.length(); i++) {
                int value = Integer.parseInt(String.valueOf(toParse.charAt(i)));

                if (!transitions.containsKey(Map.entry(currState, value))) {
                    _isNoTransition = true;
                    noTransitionValue = value;
                    break;
                } else {
                    nextState = transitions.get(Map.entry(currState, value));
                    currState = nextState;
                }
            }
            if (_isNoTransition == true) {
                System.out.println("Строка символов не входит в язык, т.к. нет перехода из " + currState + " шагом " + noTransitionValue);
            }
            else if (currState != 'Z') {
                System.out.println("Строка символов не входит в язык, т.к. не достигнуто конечное состояние");
            } else if (currState == 'Z' && _isNoTransition == false) {
                System.out.println("Строка символов входит в язык");
            }
        }
        else {
            System.out.println("Некорректный ввод!");
        }
    }
}