package webProgramming.lab1;

import java.util.Arrays;

public class Solve{
    private int[] data;
    private final InputOutputTool iot;

    public Solve() {
        iot = new InputOutputTool();
    }

    public void run(){
        try {
            readNumbers();
            displayHappyNumbers();
        }catch(IndexOutOfBoundsException | NumberFormatException e){
            iot.showMessage(e.getMessage() + " Повторите ввод!\n");
            run();
        }catch (Exception e){
            iot.showMessage(e.getMessage() + " Фатальная ошибка!\n");
        }
    }

    private void readNumbers(){
        data = iot.readNumbers();
    }

    private void displayHappyNumbers() {
        int[] temp = new int[data.length];
        int tempInd = 0;
        for (int i : data) {
            if (checkNumber(i)) {
                temp[tempInd++] = i;
            }
        }
        iot.showNumbers(Arrays.copyOf(temp, tempInd));
    }

    private boolean checkNumber(int number) {
        /*
        Число называется счастливым, если сумма квадратов его цифр кратна 4
         */
        int copyOfNumber = number;
        int sum = 0;
        while (copyOfNumber > 0) {
            int currFigure = copyOfNumber % 10;
            sum += currFigure * currFigure;
            copyOfNumber /= 10;
        }
        return (sum % 4 == 0 && number != 0);
    }

}
