package me.moruto.morsecodetranslator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<String, String> dictionary = loadDictionary();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose mode:");
        System.out.println("1: English to Morse");
        System.out.println("2: Morse to English");

        String choice = scanner.nextLine().trim();

        if (!choice.equals("1") && !choice.equals("2")) {
            throw new IllegalArgumentException("Invalid choice. Please enter 1 or 2.");
        }

        System.out.println("Enter your text: ");
        String input = scanner.nextLine().trim().toUpperCase();

        if (choice.equals("1")) {
            System.out.println("Translated: " + translateToMorse(input));
            return;
        }

        System.out.println("Translated: " + translateToEnglish(input));
    }

    private static String translateToMorse(String input) {
        StringBuilder result = new StringBuilder();
        for (char character : input.toCharArray()) {
            String morseCode = dictionary.getOrDefault(String.valueOf(character), "?");
            result.append(morseCode).append(" ");
        }
        return result.toString().trim();
    }

    private static String translateToEnglish(String morseInput) {
        StringBuilder result = new StringBuilder();
        String[] morseWords = morseInput.split(" / ");
        for (String morseWord : morseWords) {
            for (String morseChar : morseWord.split(" ")) {
                result.append(dictionary.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(morseChar))
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .orElse("?"));
            }
            result.append(" ");
        }
        return result.toString().trim();
    }

    private static Map<String, String> loadDictionary() {
        Map<String, String> dictionary = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/dictionary.txt")))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\|");
                dictionary.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dictionary;
    }
}
