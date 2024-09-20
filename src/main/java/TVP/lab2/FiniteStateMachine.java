package TVP.lab2;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FiniteStateMachine {
    Map<Map.Entry<Character, Integer>, Character>  transitions;
    Scanner scanner;
    Character currState;
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

    public void run(){
        String toParse = scanner.nextLine();
        Character nextState = currState;
        boolean result = true;
        for(int i  = 0; i < toParse.length(); i++){
            int value = Integer.parseInt(String.valueOf(toParse.charAt(i)));
            if(!transitions.containsKey(Map.entry(currState, value))){
                result = false;
            }else {
                nextState = transitions.get(Map.entry(currState, value));
                if (nextState == null) {
                    result = false;
                }
                currState = nextState;
            }
        }
        if(!result || currState != 'Z'){
            System.out.println("A string of characters is NOT included in the language");
        }else if(currState == 'Z' && result){
            System.out.println("A string of characters is included in the language");
        }
    }
}
