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

    public WordCompleter(int numberOfSuggestions) {
        this.root = new TrieNode();
        this.wordFrequencies = new HashMap<>();
        this.numberOfSuggestions = numberOfSuggestions;
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
        String[] words = text.toLowerCase().split("\\b\\P{Alpha}+\\b");
        for (String word : words) {
            insert(word);
            wordFrequencies.merge(word, 1, Integer::sum);
        }
    }

    public String complete(String prefix) {
        TrieNode node = searchNode(prefix);
        StringBuilder result = new StringBuilder(prefix);

        while (node != null && !node.isEndOfWord) {
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

    public void analyzeDocument(String filePath) {
        try {
            PDDocument document = PDDocument.load(new File(filePath));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            document.close();

            buildTrieFromText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}