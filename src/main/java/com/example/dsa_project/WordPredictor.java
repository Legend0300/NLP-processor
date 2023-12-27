package com.example.dsa_project;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordPredictor {

    private final HashMap<String, Map<String, Integer>> wordFrequencies;
    private static final Pattern WORD_PATTERN = Pattern.compile("\\b\\p{Alpha}+\\b");
    private static final Set<String> commonWords = new HashSet<>(Arrays.asList(
            "the", "and", "is", "a", "of", "in", "to", "that", "it", "for", "with", "as", "on", "at", "by", "an", "or",
            "but", "not", "from", "this", "have", "has", "was", "are", "were", "if", "will", "which", "you", "I", "we",
            "he", "she", "they", "it", "be", "can", "do", "does", "did", "your", "my", "his", "her", "our", "their",
            "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "i", "said", "had", "let", "when", "most"));

    public WordPredictor() {
        this.wordFrequencies = new HashMap<>();
    }

    public void analyzeDocument(String filePath) {
        try {
            PDDocument document = PDDocument.load(new File(filePath));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            document.close();
            tokenizeText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void tokenizeText(String text) {
        Matcher matcher = WORD_PATTERN.matcher(text);
        String previousWord = null;

        while (matcher.find()) {
            String currentWord = matcher.group().toLowerCase();
            if (!commonWords.contains(currentWord) && previousWord != null) {
                updateWordFrequencies(previousWord, currentWord);
            }
            previousWord = currentWord;
        }
    }

    private void updateWordFrequencies(String sourceWord, String targetWord) {
        wordFrequencies.computeIfAbsent(sourceWord, k -> new HashMap<>());
        wordFrequencies.get(sourceWord).merge(targetWord, 1, Integer::sum);
    }

    public List<String> suggestNextWords(String inputWord) {
        Map<String, Integer> nextWordFrequencies = wordFrequencies.getOrDefault(inputWord, new HashMap<>());
        PriorityQueue<Map.Entry<String, Integer>> maxHeap = createMaxHeap(nextWordFrequencies);

        return extractTopSuggestions(maxHeap);
    }

    private PriorityQueue<Map.Entry<String, Integer>> createMaxHeap(Map<String, Integer> wordFrequencies) {
        Comparator<Map.Entry<String, Integer>> byValue = Map.Entry.comparingByValue(Comparator.reverseOrder());
        PriorityQueue<Map.Entry<String, Integer>> maxHeap = new PriorityQueue<>(byValue);
        maxHeap.addAll(wordFrequencies.entrySet());
        return maxHeap;
    }

    private List<String> extractTopSuggestions(PriorityQueue<Map.Entry<String, Integer>> maxHeap) {
        List<String> suggestions = new ArrayList<>();
        int count = 0;
        while (!maxHeap.isEmpty() && count < 4) {
            suggestions.add(maxHeap.poll().getKey());
            count++;
        }
        return suggestions;
    }

    public String giveSuggestions(String inputWord, List<String> suggestions) {
        if (!suggestions.isEmpty()) {
            return String.join(" , ", suggestions);
        } else {
            return "no words found";
        }
    }

    public String printSuggestions(String inputWord, List<String> suggestions) {
        if (!suggestions.isEmpty()) {
            System.out.println("Suggestions for '" + inputWord + "':");
            suggestions.forEach(suggestion -> System.out.println("- " + suggestion));
            return String.join(" , ", suggestions);
        } else {
            System.out.println("No suggestions for '" + inputWord + "'.");
            return null;
        }
    }

}