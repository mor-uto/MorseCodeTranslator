package me.moruto.morsecodetranslator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<String, String> dictionary = new HashMap<String, String>() {{
        put("A", ".-");
        put("B", "-...");
        put("C", "-.-.");
        put("D", "-..");
        put("E", ".");
        put("F", "..-.");
        put("G", "--.");
        put("H", "....");
        put("I", "..");
        put("J", ".---");
        put("K", "-.-");
        put("L", ".-..");
        put("M", "--");
        put("N", "-.");
        put("O", "---");
        put("P", ".--.");
        put("Q", "--.-");
        put("R", ".-.");
        put("S", "...");
        put("T", "-");
        put("U", "..-");
        put("V", "...-");
        put("W", ".--");
        put("X", "-..-");
        put("Y", "-.--");
        put("Z", "--..");
        // Add spaces for readability in translation output
        put(" ", " / ");
    }};

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
}
