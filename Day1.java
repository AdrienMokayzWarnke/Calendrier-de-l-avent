package com.mokayz;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day1 {

    public static FileReader file;

    static {
        try {
            file = new FileReader("C:/Users/Mokayz/IdeaProjects/Calendrier de l'avent/src/com/mokayz/inputDay1.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final Map<String, String> stringToInt = new HashMap<>();

    static {
        stringToInt.put("one", "1");
        stringToInt.put("two", "2");
        stringToInt.put("three", "3");
        stringToInt.put("four", "4");
        stringToInt.put("five", "5");
        stringToInt.put("six", "6");
        stringToInt.put("seven", "7");
        stringToInt.put("eight", "8");
        stringToInt.put("nine", "9");
    }

    private static String findNb(String substring, boolean problem2) {
        try {
            Integer.parseInt(substring);
            return substring;
        } catch (NumberFormatException ex) {
            if (problem2) {
                return stringToInt.get(substring);
            }

            return null;
        }
    }

    private static int firstMatchF(String str, boolean problem2) {
        for (int i = 0; i < str.length(); i++) {
            for (int j = i; j < str.length(); j++) {
                String maybeMatch = findNb(str.substring(i, j + 1), problem2);
                if (maybeMatch != null) {
                    return Integer.parseInt(maybeMatch);
                }
            }
        }

        throw new IllegalStateException("Didn't find a match. Invalid string.");
    }

    private static int firstMatchB(String str, boolean problem2) {
        for (int i = str.length() - 1; i >= 0; i--) {
            for (int j = i; j >= 0; j--) {
                String maybeMatch = findNb(str.substring(j, i + 1), problem2);
                if (maybeMatch != null) {
                    return Integer.parseInt(maybeMatch);
                }
            }
        }

        throw new IllegalStateException("Didn't find a match. Invalid string.");
    }

    private static int getDigitFromString(String str, boolean problem2) {
        int first = firstMatchF(str, problem2);
        int last = firstMatchB(str, problem2);
        return Integer.parseInt(String.format("%d%d", first, last));
    }

    public static int solution(boolean problem2) throws IOException {
        BufferedReader br = new BufferedReader(file);

        int i = br.lines()
                .filter(line -> !line.isBlank())
                .mapToInt(line -> getDigitFromString(line, problem2))
                .sum();

        System.out.println(i);

        br.close();

        return 0;
    }

}