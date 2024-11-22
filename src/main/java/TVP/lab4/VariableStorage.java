package TVP.lab4;

import java.util.HashMap;
import java.util.Map;

public class VariableStorage {
    private static final Map<String, Boolean> storage = new HashMap<>();

    public static Boolean getValue(String variable){
        return storage.getOrDefault(variable, null);
    }

    public static void setValue(String variable, Boolean value){
        storage.put(variable, value);
    }

    public static boolean updateValue(String variable, Boolean value){
        if(!storage.containsKey(variable)){
            return false;
        }
        storage.put(variable, value);
        return true;
    }

    public static String getAllData(){
        StringBuilder data = new StringBuilder();
        for(Map.Entry<String, Boolean> entry : storage.entrySet()){
            data.append(entry.getKey()).append(" - ").append(entry.getValue()).append('\n');
        }
        return data.toString();
    }
}
