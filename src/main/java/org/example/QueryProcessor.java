package org.example;

import java.io.*;
import java.util.Objects;

public class QueryProcessor {
    public static void main(String[] args) {
        try {
            // Чтение входных данных из файла
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(QueryProcessor.class.getClassLoader().getResourceAsStream("input.txt"))));
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

            // Чтение n и q
            String[] inputParams = reader.readLine().split(" ");
            int n = Integer.parseInt(inputParams[0]);
            int q = Integer.parseInt(inputParams[1]);

            // Чтение строки S
            String s = reader.readLine();

            // Обработка каждого запроса
            long startTime = System.currentTimeMillis(); // Засекаем начало выполнения

            for (int i = 0; i < q; i++) {
                String[] query = reader.readLine().split(" ");
                int l = Integer.parseInt(query[0]);
                int r = Integer.parseInt(query[1]);
                int k = Integer.parseInt(query[2]);

                // Решение запроса
                int result = processQuery(s.substring(l - 1, r), k);

                // Запись результата в файл
                writer.write(result + "\n");
            }

            long endTime = System.currentTimeMillis(); // Засекаем конец выполнения
            long executionTime = endTime - startTime;
            //System.out.println("Execution Time: " + executionTime + " ms");

            // Закрытие файловых потоков
            reader.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int processQuery(String substring, int k) {
        int countA = 0;
        int countB = 0;

        for (int i = 0; i < substring.length(); i++) {
            char c = substring.charAt(i);
            if (c == 'A') {
                countA++;
            } else {
                countB++;
            }
        }

        // Логика обработки запроса
        if (k < countA) {
            return findMatch(substring, 'A', countA - k);
        } else if (k < countA + countB) {
            return findMatch(substring, 'B', k - countA + 1);
        }
        if (substring.charAt(k - 1) == 'A') {
            return findMatch(substring, 'B', countA);
        } else {
            return findMatch(substring, 'A', countB);
        }

    }

    private static int findMatch(String substring, char target, int count) {
        int found = 0;

        for (int i = 0; i < substring.length(); i++) {
            if (substring.charAt(i) == target) {
                found++;
                if (found == count) {
                    return i + 1; // Позиция в строке (отсчет с 1)
                }
            }
        }

        return -1;
    }
}
