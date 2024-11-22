package TVP.lab4;

import java.util.Stack;

public class Parser {
    public static boolean parse(String expr) {
        return evaluatePostfix(getPostfix(expr));
    }

    private static String getPostfix(String expression) {
        Stack<Character> operators = new Stack<>();
        StringBuilder postfix = new StringBuilder();

        //Обработка скобок
        expression = expression.replaceAll("\\s+", ""); // Удаляем пробелы

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == '0' || c == '1') {
                postfix.append(c);
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    postfix.append(operators.pop());
                }
                operators.pop(); // Удалить '('
            } else if (isOperator(c)) {
                while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                    postfix.append(operators.pop());
                }
                operators.push(c);
            }
        }

        while (!operators.isEmpty()) {
            postfix.append(operators.pop());
        }

        return postfix.toString();
    }

    private static boolean isOperator(char c) {
        return (c == '&' || c == '|' || c == '!' || c == '=');
    }

    private static int precedence(char op) {
        if (op == '!') return 4;
        if (op == '&') return 3;
        if (op == '|') return 2;
        if (op == '=') return 1;
        return 0;
    }

    private static boolean evaluatePostfix(String expression) {
        Stack<Boolean> operands = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                operands.push(c == '1');
            } else if (isOperator(c)) {
                boolean operand2 = operands.pop();
                boolean operand1 = false;
                if (c != '!') {
                    operand1 = operands.pop();
                }
                boolean result = switch (c) {
                    case '&' -> operand1 && operand2;
                    case '|' -> operand1 || operand2;
                    case '=' -> operand1 == operand2;
                    case '!' -> !operand2;
                    default -> false;
                };
                operands.push(result);
            }
        }
        if (operands.size() != 1) {
            throw new RuntimeException();
        }
        return operands.pop();
    }
}
