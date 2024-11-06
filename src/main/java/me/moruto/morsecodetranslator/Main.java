package me.moruto.morsecodetranslator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<String, String> dictionary = loadDictionary();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1: English to Morse\n2: Morse to English");
        String choice = scanner.nextLine().trim();

        while (true) {
            System.out.print("Enter text: ");
            String input = scanner.nextLine().trim().toUpperCase();
            System.out.println("Translated: " + (choice.equals("1") ? toMorse(input) : toEnglish(input)));
        }
    }

    private static String toMorse(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) result.append(dictionary.getOrDefault(String.valueOf(c), "?")).append(" ");
        return result.toString().trim();
    }

    private static String toEnglish(String input) {
        StringBuilder result = new StringBuilder();
        for (String word : input.split(" / "))
            for (String morse : word.split(" "))
                result.append(dictionary.entrySet().stream().filter(e -> e.getValue().equals(morse)).map(Map.Entry::getKey).findFirst().orElse("?"));
        return result.toString();
    }

    private static Map<String, String> loadDictionary() {
        Map<String, String> dict = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/dictionary.txt")))) {
            br.lines().forEach(line -> {
                String[] parts = line.split("\\|");
                dict.put(parts[0], parts[1]);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dict;
    }
}
