public class CalculatorModel {
    public int calculate(String equation){
        int result = 0;
        String[] numbersString = equation.split("\\+");
        int[] numbers = new int[numbersString.length];

        for (int i = 0; i<numbersString.length;i++){
            numbers[i] = Integer.parseInt(numbersString[i]);
        }
        for (int i = 0; i<numbers.length;i++){
            result += numbers[i];
        }
        return result;


    }

}
