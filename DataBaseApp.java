

package Final_Exceptions;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataBaseApp {
    public static void main(String[] args) {
        String[] messages = new String[]{"Фамилия", "Имя", "Отчество", "Дата рождения(dd.mm.yyyy)", "Номер телефона", "Пол(f или m)"};
        List<String> list = new ArrayList<>();
        Collections.addAll(list, messages);
        Collections.shuffle(list);

        System.out.println("Введите следующие данные через пробел: " + String.join("; ", list));
        Scanner scanner = new Scanner(System.in, "ibm866");
        String inputStr = scanner.nextLine();
        String[] inputArr = inputStr.split(" ");
        int numberCode = checkArray(inputArr, messages.length);
        printString(numberCode);
        if (numberCode == 0) recordToFile(list, inputArr);
        scanner.close();
    }

    public static int checkArray(String[] arr, int count){
        if (arr.length > count)
            return -2;
        else if (arr.length < count)
            return -1;
        else
            return 0;
    }

    public static void printString(int number){
        if (number == -1)
            System.out.println(number + "\nВы ввели меньше данных чем требуется");
        else if (number == -2)
            System.out.println(number + "\nВы ввели больше данных чем требуется");
    }

    public static void recordToFile(List<String> dataRequest, String[] input) {
        String[] record = new String[dataRequest.size()];
        for (int i = 0; i < input.length; i++) {
            if  (dataRequest.get(i).equals("Фамилия")) {
                record[0] = input[i];
                if (!input[i].matches("[\\D]+")) {
                    throw new RuntimeException("В фамилии число!");
                }
            } else if (dataRequest.get(i).equals("Имя")) {
                if (!input[i].matches("[\\D]+")) {
                    throw new RuntimeException("В имени число!");
                }
                record[1] = input[i];
            } else if (dataRequest.get(i).equals("Отчество")) {
                if (!input[i].matches("[\\D]+")) {
                    throw new RuntimeException("В отчестве число!");
                }
                record[2] = input[i];
            } else if (dataRequest.get(i).equals("Дата рождения(dd.mm.yyyy)")) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                dateFormat.setLenient(false);
                try {
                    dateFormat.parse(input[i]);
                } catch (ParseException e) {
                    throw new RuntimeException("дата введена некорректно");
                }
                record[3] = input[i];
            } else if (dataRequest.get(i).equals("Номер телефона")) {
                try {
                    Long.parseLong(input[i]);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("в номере телефона некорректный ввод");
                }
                record[4] = input[i];
            } else if (dataRequest.get(i).equals("Пол(f или m)")) {
                if (!input[i].equals("f") && !input[i].equals("m")) {
                    throw new RuntimeException("Пол может быть лишь f или m");
                }
                record[5] = input[i];
            }
        }

        try (FileWriter fw = new FileWriter(String.format("%s.txt", record[0]), true)) {
            fw.write(String.join(" ", record) + "\n");
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл или имя файла содержит недопустимые символы");
            e.printStackTrace();
        }
    }
}