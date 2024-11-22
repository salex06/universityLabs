package TVP.lab4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CodeParser implements Parser {
    private static final String VAR_KEYWORD = "VAR";
    private static final int MAX_ID_LENGTH = 9;
    private static final String VARS_TYPE = "LOGICAL";
    private static final String BEGIN_KEYWORD = "BEGIN";

    @Override
    public boolean parse(String data) {
        data = data.replaceAll("\\s+", "");
        Map.Entry<Integer, List<String>> parsedVars = getVariables(data);
        List<String> variables = parsedVars.getValue();
        int pointerIndex = parsedVars.getKey();
        pointerIndex = checkVariablesType(pointerIndex, data);
        if (!(data.charAt(pointerIndex) == ';')) {
            throw new RuntimeException("Ожидается ;");
        }
        pointerIndex += 1;
        loadVariables(variables);
        parseAssignments(pointerIndex, data, variables);
        return true;
    }


    private Map.Entry<Integer, List<String>> getVariables(String data) {
        List<String> variables = new ArrayList<>();
        if (!data.startsWith(VAR_KEYWORD)) {
            throw new RuntimeException("Отсутствует ключевое слово " + VAR_KEYWORD);
        }
        int i = VAR_KEYWORD.length();
        StringBuilder current;
        while (data.charAt(i) != ':') {
            current = new StringBuilder();
            while (data.charAt(i) != ':' && data.charAt(i) != ',') {
                if(!Character.isLetter(data.charAt(i))){
                    throw new RuntimeException("Название переменной может содержать только буквы!");
                }
                current.append(data.charAt(i));
                i++;
            }
            String variable = current.toString();
            if (variable.length() > MAX_ID_LENGTH) {
                throw new RuntimeException(
                        "Длина идентификатора " + variable + " равна: " + variable.length()
                                + "(максимум: " + MAX_ID_LENGTH + ")");
            }
            variables.add(variable);
            if(data.charAt(i) == ':') continue;
            i++;
        }
        return Map.entry(i, variables);
    }

    private int checkVariablesType(int pointerIndex, String data) {
        if (!data.startsWith(VARS_TYPE, pointerIndex + 1)) {
            throw new RuntimeException("Неверный тип данных, требуется: " + VARS_TYPE);
        }
        return pointerIndex + VARS_TYPE.length() + 1;
    }

    private void loadVariables(List<String> variables) {
        for(String var: variables) {
            VariableStorage.setValue(var, false);
        }
    }

    private void parseAssignments(int pointerIndex, String data, List<String> variables){
        if(!data.startsWith(BEGIN_KEYWORD, pointerIndex)){
            throw new RuntimeException("Отсутствует ключевое слово " + BEGIN_KEYWORD);
        }
        pointerIndex += BEGIN_KEYWORD.length();
        while(pointerIndex < data.length() && !data.startsWith("END", pointerIndex)){
            StringBuilder var = new StringBuilder();
            StringBuilder expr = new StringBuilder();
            boolean wasEquiv = false;
            while(data.charAt(pointerIndex) != ';'){
                if(data.charAt(pointerIndex) == '=' && !wasEquiv){
                    wasEquiv = true;
                }else if(wasEquiv){
                    expr.append(data.charAt(pointerIndex));
                }else{
                    var.append(data.charAt(pointerIndex));
                }
                pointerIndex++;
            }
            if(!wasEquiv){
                throw new RuntimeException("Ожидается присваивание переменной " + var);
            }
            String expression = replaceVariables(expr, variables);
            boolean updateResult = VariableStorage.updateValue(var.toString(), new ExpressionParser().parse(expression));
            if(!updateResult){
                throw new RuntimeException("Переменная не объявлена: " + var);
            }
            pointerIndex++;
        }
        if(!data.endsWith("END")){
            throw new RuntimeException("Ожидается ключевое слово END");
        }
    }

    private String replaceVariables(StringBuilder expr, List<String> variables) {
        String expression = expr.toString();
        for(String var: variables){
            String val = (VariableStorage.getValue(var) ? "1" : "0");
            expression = expression.replaceAll(var, val);
        }
        return expression;
    }
}
