package aoc3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    private static char[][] input;

    public static void main(String[] args) throws Exception {
        input = readInput();
        long result = 0;

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (isSpecialCharacter(input[i][j])) {
                    System.out.println(String.format("processing special character %s [%s][%s]", input[i][j], i, j));

                    // search for digit in surrounding
                    if (i > 0 && j > 0) {
                        result += findAndReplaceNumber(i - 1, j - 1);
                    }
                    if (i > 0) {
                        result += findAndReplaceNumber(i - 1, j);
                    }
                    if (i > 0 && input[i - 1].length > j + 1) {
                        result += findAndReplaceNumber(i - 1, j + 1);
                    }
                    if (input[i].length > j + 1) {
                        result += findAndReplaceNumber(i, j + 1);
                    }
                    if (input.length > i + 1 && input[i + 1].length > j + 1) {
                        result += findAndReplaceNumber(i + 1, j + 1);
                    }
                    if (input.length > i + 1) {
                        result += findAndReplaceNumber(i + 1, j);
                    }
                    if (input.length > i + 1 && j > 0) {
                        result += findAndReplaceNumber(i + 1, j - 1);
                    }
                    if (j > 0) {
                        result += findAndReplaceNumber(i, j - 1);
                    }
                }
            }
        }

        System.out.println("Result = " + result);
    }

    private static char[][] readInput() throws Exception {
        // load the data from file
        List<String> lines = Files.readAllLines(Path.of("src/main/resources/input_3_1.txt"));

        if (lines == null || lines.isEmpty())
            throw new Exception("Empty input !");

        char[][] result = new char[lines.get(0).length()][lines.size()];

        // convert lines to array
        for (int i = 0; i < lines.size(); i++) {
            result[i] = lines.get(i).toCharArray();
        }

        return result;
    }

    private static boolean isSpecialCharacter(char c) {
        return !Character.isDigit(c) && c != '.';
    }

    private static int findAndReplaceNumber(int i, int j) throws Exception {
        if (!Character.isDigit(input[i][j]))
            return 0;

        StringBuilder result = new StringBuilder();

        // is there something in LEFT ?
        while (Character.isDigit(input[i][j])) {
            result.append(input[i][j]);
            input[i][j] = '.';
            if (j == 0)
                break; // can not move more to left
            j--;
        }

        if (!result.isEmpty()) {
            result = result.reverse();
        }

        // is there something in the RIGHT ?

        // pointer push back where it was at the begining
        j = j + result.length() + 1;
        while (Character.isDigit(input[i][j])) {
            result.append(input[i][j]);
            input[i][j] = '.';
            j++;
            if (input[i].length == j)
                break; // can not move more to right
        }

        if (result.isEmpty())
            return 0;
        int foundedNumber = Integer.parseInt(result, 0, result.length(), 10);
        System.out.println("Found number: " + foundedNumber);
        return foundedNumber;
    }
}
