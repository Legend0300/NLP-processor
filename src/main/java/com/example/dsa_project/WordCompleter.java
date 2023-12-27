package com.example.dsa_project;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.*;

class TrieNode {
    Map<Character, TrieNode> children;
    boolean isEndOfWord;

    TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
    }
}

public class WordCompleter {

    private final TrieNode root;
    private final Map<String, Integer> wordFrequencies;
    private int numberOfSuggestions; // New field
    private StringBuilder currentInput; // New field

    public WordCompleter(int numberOfSuggestions) {
        this.root = new TrieNode();
        this.wordFrequencies = new HashMap<>();
        this.numberOfSuggestions = numberOfSuggestions;
        this.currentInput = new StringBuilder();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current.children.putIfAbsent(ch, new TrieNode());
            current = current.children.get(ch);
        }
        current.isEndOfWord = true;
    }

    public void buildTrieFromText(String text) {
        if (text == null || text.trim().isEmpty()) {
            System.out.println("Input text is empty or null.");
            return;
        }

        String[] words = text.toLowerCase().split("\\b\\P{Alpha}+\\b");
        for (String word : words) {
            insert(word);
            wordFrequencies.merge(word, 1, Integer::sum);
        }
    }

    public String complete(String prefix) {
        TrieNode node = searchNode(prefix);
        if (node == null) {
            return prefix;
        }

        StringBuilder result = new StringBuilder(prefix);

        while (!node.isEndOfWord) {
            char nextChar = getNextChar(node);
            result.append(nextChar);
            node = node.children.get(nextChar);
        }

        return result.toString();
    }

    private TrieNode searchNode(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            if (current.children.containsKey(ch)) {
                current = current.children.get(ch);
            } else {
                return null;
            }
        }
        return current;
    }

    private char getNextChar(TrieNode node) {
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            return entry.getKey();
        }
        return 0; // This should not happen in a well-formed Trie
    }

    public List<String> suggestMostFrequent(String prefix) {
        TrieNode node = searchNode(prefix);

        List<String> suggestions = new ArrayList<>();

        if (node != null && !node.isEndOfWord) {
            List<Map.Entry<String, Integer>> sortedSuggestions = getSortedSuggestions(prefix);

            int count = 0;
            for (Map.Entry<String, Integer> entry : sortedSuggestions) {
                suggestions.add(entry.getKey());
                count++;
                if (count >= numberOfSuggestions) {
                    break;
                }
            }
        }

        return suggestions;
    }

    public String returnMostFrequent(String prefix) {
        TrieNode node = searchNode(prefix);

        if (prefix == null || prefix.trim().isEmpty()) {
            return "Input text is empty or null.";
        }

        StringBuilder suggestions = new StringBuilder();

        if (node != null && !node.isEndOfWord) {
            List<Map.Entry<String, Integer>> sortedSuggestions = getSortedSuggestions(prefix);

            int count = 0;
            for (Map.Entry<String, Integer> entry : sortedSuggestions) {
                suggestions.append(entry.getKey()).append(" , ");
                count++;
                if (count >= numberOfSuggestions) {
                    break;
                }
            }
        }

        removeTrailingCommaAndSpace(suggestions);

        return suggestions.toString();
    }

    private List<Map.Entry<String, Integer>> getSortedSuggestions(String prefix) {
        Map<String, Integer> prefixSuggestions = new HashMap<>();

        // Collect all suggestions that start with the given prefix and their frequencies
        for (Map.Entry<String, Integer> entry : wordFrequencies.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                prefixSuggestions.put(entry.getKey(), entry.getValue());
            }
        }

        // Sort suggestions by frequency in descending order
        List<Map.Entry<String, Integer>> sortedSuggestions = new ArrayList<>(prefixSuggestions.entrySet());
        sortedSuggestions.sort((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()));

        return sortedSuggestions;
    }

    private void removeTrailingCommaAndSpace(StringBuilder suggestions) {
        if (suggestions.length() > 2) {
            suggestions.setLength(suggestions.length() - 2); // Remove the trailing comma and space
        }
    }

    public void printWordFrequencies() {
        System.out.println("Word frequencies:");
        for (Map.Entry<String, Integer> entry : wordFrequencies.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public boolean analyzeDocument(String filePath) {
        try {
            PDDocument document = PDDocument.load(new File(filePath));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            document.close();
            buildTrieFromText(text);
            return true;
        } catch (IOException e) {
            System.out.println("Error reading the document.");
            e.printStackTrace();
            return false;
        }
    }

    public void handleKeyPress(char key) {
        if (key == ' ') {
            handleSpace();
        } else {
            currentInput.append(key);
        }
    }

    private void handleBackspace() {
        if (currentInput.length() > 0) {
            currentInput.deleteCharAt(currentInput.length() - 1);
        }
    }

    private void handleSpace() {
        if (currentInput.length() > 0) {
            // Process the current input and reset for the next word
            String inputWord = currentInput.toString();
            currentInput.setLength(0); // Clear the input buffer

            // Example: Analyze the current word
            List<String> suggestions = suggestMostFrequent(inputWord);
            System.out.println("Suggestions for '" + inputWord + "': " + suggestions);
        }
    }

    public static void main(String[] args) {
        int numberOfSuggestions = 4;
        WordCompleter completer = new WordCompleter(numberOfSuggestions);

        // Example: Analyze words from a PDF file
        completer.analyzeDocument("D:\\Coding\\NLP Processor\\src\\Rich Dad Poor Dad What the Rich Teach Their Kids About Money—That the Poor and Middle Class Do Not by Robert T. Kiyosaki (z-lib.org).epub.pdf");

        // Example input
        String inputPrefix = "app";
        List<String> suggestions = completer.suggestMostFrequent(inputPrefix);

        System.out.println("Suggestions for '" + inputPrefix + "': " + suggestions);

        // Example: Simulate key presses
        for (char key : "apple".toCharArray()) {
            completer.handleKeyPress(key);
        }

        // Handle the space at the end
        completer.handleKeyPress(' ');

        // Process the current input
        completer.handleSpace();
    }
}
