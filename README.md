# Data Structures and Algorithms in Java

## Getting Started

Welcome to the VS Code Java world. Below is a guideline to help you get started with writing Java code in Visual Studio Code.

### Folder Structure

The workspace contains two folders by default:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

The compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

### Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## Purpose of this Repository

This repository serves as a source file for various data structures and algorithms implemented in Java. It is intended for practice and learning purposes. Feel free to explore the code and use it as a reference for your own projects or to deepen your understanding of Java programming, data structures, and algorithms.

## Table of Contents

- [LinkedList Implementation](src/LinkedList.java)
  - Simple implementation of a singly linked list with various operations.

- [WordCompleter](src/com/example/dsa_project/WordCompleter.java)
  - Implementation of a word completer using Trie data structure. It includes methods for insertion, building a Trie from text, word completion, and suggestions based on word frequencies.

- [WordPredictor](src/com/example/dsa_project/WordPredictor.java)
  - Implementation of a word predictor using word frequencies. It includes methods for analyzing a document, suggesting next words based on input, and printing suggestions.

## Contribution

If you have suggestions for improvements, bug reports, or would like to contribute additional implementations, feel free to [contribute](CONTRIBUTING.md).

## Examples

### WordCompleter Example

```java
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
