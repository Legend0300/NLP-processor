﻿# DSA Project: NLP Processor

## Introduction

This DSA (Data Structures and Algorithms) project is an NLP (Natural Language Processing) processor designed to predict and auto-complete words. The project reads a PDF file, processes the text, and provides functionalities such as word prediction and auto-completion.

## Functionality

### WordCompleter Class

The `WordCompleter` class is responsible for building a trie data structure from the input text and offering word completion suggestions.

#### TrieNode

- The `TrieNode` class represents a node in the trie.
- It uses a `Map<Character, TrieNode>` to store children nodes.
- It has a boolean flag `isEndOfWord` to indicate the end of a word.

#### WordCompleter Class Features

- **Insertion**: The class allows inserting words into the trie.
- **Building Trie from Text**: The `buildTrieFromText` method processes the input text and constructs the trie.
- **Word Completion**: The `complete` method suggests word completions based on a given prefix.
- **Most Frequent Suggestions**: The `suggestMostFrequent` method provides suggestions for the most frequent words starting with a prefix.
- **Document Analysis**: The `analyzeDocument` method analyzes a PDF document, extracting text and updating the trie.

### WordPredictor Class

The `WordPredictor` class focuses on predicting the next word and providing suggestions based on word frequencies.

#### Features

- **Document Analysis**: The `analyzeDocument` method processes a PDF document and tokenizes the text.
- **Update Word Frequencies**: The class updates word frequencies based on word pairs.
- **Suggest Next Words**: The `suggestNextWords` method suggests the next words based on the input word.
- **Give Suggestions**: The `giveSuggestions` method formats and returns suggestions.
- **Print Suggestions**: The `printSuggestions` method prints suggestions to the console.

## Data Structures Used

- **Trie**: The `WordCompleter` class utilizes a trie data structure for efficient word insertion and retrieval.
- **HashMap**: Word frequencies are stored and updated using `HashMap` for quick access.

## Example Usage

```java

## How to Run

1. **Set up your Java development environment.**
   - Ensure you have Java installed on your machine. You can download it from [here](https://www.oracle.com/java/technologies/javase-downloads.html).

2. **Ensure the required dependencies (Apache PDFBox) are added to the project.**
   - Add the Apache PDFBox library to your project. You can find it [here](https://pdfbox.apache.org/download.cgi).

3. **Run the main method in either WordCompleter or WordPredictor for demonstration.**
   - Execute the `main` method in either the `WordCompleter` or `WordPredictor` class to demonstrate the NLP Processor.

## Contribution

Feel free to contribute to this project by providing improvements, bug reports, or additional features.

**Enjoy experimenting with this NLP Processor!**
