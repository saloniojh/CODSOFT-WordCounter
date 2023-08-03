import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WordCounter {
    private static final String STOP_WORDS = "a an the and or but in on at to from";

    public static void main(String[] args) {
        String text = getTextFromUser();
        if (text == null) {
            System.out.println("Invalid input. Exiting...");
            return;
        }

        String[] words = splitIntoWords(text);
        int totalWords = countWords(words);
        System.out.println("Total words: " + totalWords);

        int uniqueWords = countUniqueWords(words);
        System.out.println("Unique words: " + uniqueWords);

        Map<String, Integer> wordFrequencies = getWordFrequencies(words);
        System.out.println("Word frequencies:");
        for (Map.Entry<String, Integer> entry : wordFrequencies.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static String getTextFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter text or file path: ");
        String input = scanner.nextLine().trim();

        try {
            if (input.endsWith(".txt")) {
                return readFile(input);
            } else {
                return input;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        } finally {
            scanner.close(); // Close the scanner to avoid resource leak
        }
    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    private static String[] splitIntoWords(String text) {
        return text.split("[\\p{Punct}\\s]+");
    }

    private static int countWords(String[] words) {
        return words.length;
    }

    private static int countUniqueWords(String[] words) {
        Map<String, Integer> wordCountMap = new HashMap<>();
        for (String word : words) {
            wordCountMap.put(word.toLowerCase(), wordCountMap.getOrDefault(word.toLowerCase(), 0) + 1);
        }
        return wordCountMap.size();
    }
    private static Map<String, Integer> getWordFrequencies(String[] words) {
        Map<String, Integer> wordFrequencies = new HashMap<>();
        for (String word : words) {
            word = word.toLowerCase();
            if (!STOP_WORDS.contains(word)) {
                wordFrequencies.put(word, wordFrequencies.getOrDefault(word, 0) + 1);
            }
        }
        return wordFrequencies;
}}