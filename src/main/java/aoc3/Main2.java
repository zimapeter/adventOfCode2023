package aoc3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Main2 {

    private static char[][] input;
    private static final char GEAR_SPECIAL_CHARACTER = '*';

    public static void main(String[] args) throws Exception {
        input = readInput();
        long result = 0;

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] == GEAR_SPECIAL_CHARACTER) {
                    System.out.println(String.format("processing special character %s [%s][%s]", input[i][j], i, j));

                    Map<LookAround, Integer> tmpResultMap = new HashMap<>();

                    // search for digit in surrounding
                    for (LookAround lookAround : LookAround.values()) {
                        if (lookAround.getCheck().test(i, j, input)) {
                            tmpResultMap.put(lookAround, findNumber(i + lookAround.getI_modification(),
                                    j + lookAround.getJ_modification()));
                        }
                    }

                    if (tmpResultMap.get(LookAround.UP) != null) {
                        tmpResultMap.put(LookAround.UP_LEFT, null);
                        tmpResultMap.put(LookAround.UP_RIGHT, null);
                    }
                    if (tmpResultMap.get(LookAround.DOWN) != null) {
                        tmpResultMap.put(LookAround.DOWN_LEFT, null);
                        tmpResultMap.put(LookAround.DOWN_RIGHT, null);
                    }

                    int tmpResult = 1;
                    byte notNullCounter = 0;
                    Set<Entry<LookAround, Integer>> entrySet = tmpResultMap.entrySet();
                    for (Entry<LookAround, Integer> entry : entrySet) {
                        if (entry.getValue() != null) {
                            if (notNullCounter > 2)
                                break;

                            tmpResult = tmpResult * entry.getValue();
                            notNullCounter++;
                        }
                    }
                    if (notNullCounter == 2) {
                        System.out.println("Has 2 correct values: " + tmpResult);
                        result += tmpResult;
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

    private static Integer findNumber(int i, int j) throws Exception {
        if (!Character.isDigit(input[i][j]))
            return null;

        StringBuilder result = new StringBuilder();

        // is there something in LEFT ?
        while (Character.isDigit(input[i][j])) {
            result.append(input[i][j]);
            j--;
            if (j == -1)
                break; // can not move more to left
        }

        if (!result.isEmpty()) {
            result = result.reverse();
        }

        // is there something in the RIGHT ?

        // pointer push back where it was at the begining
        j = j + result.length() + 1;
        while (input[i].length > j && Character.isDigit(input[i][j])) {
            result.append(input[i][j]);
            j++;
        }

        if (result.isEmpty())
            return null;
        int foundedNumber = Integer.parseInt(result, 0, result.length(), 10);
        System.out.println("Found number: " + foundedNumber);
        return foundedNumber;
    }
}
