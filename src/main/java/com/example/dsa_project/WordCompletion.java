package com.example.dsa_project;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WordCompletion {
    WordCompletionApplication App;
    private Scene wordCompletionScene;

    public WordCompletion(WordCompletionApplication app) {
        this.App = app;
    }
    public Scene getScene() {
        createWordCompletionScene();
        return wordCompletionScene;
    }

    private void createWordCompletionScene() {
        // Create components for Word Completion scene
        TextField textField = new TextField();
        TextArea textArea = new TextArea();
        Label label1 = new Label("Enter a few letters to get completed words:");
        Label label2 = new Label("Completed words:");
        Button backButton = new Button("Back");

        // Set action for back button
        backButton.setOnAction(e -> showHomePage());

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textArea.setText(App.completer.returnMostFrequent(newValue));
        });

        // Create layout for Word Completion scene
        VBox wordCompletionLayout = new VBox(10);
        wordCompletionLayout.setPadding(new Insets(10, 10, 10, 10));
        wordCompletionLayout.getChildren().addAll(label1,textField, label2 ,textArea,backButton);

        // Create Word Completion scene
        wordCompletionScene = new Scene(wordCompletionLayout, 450, 450);
    }
    public void showWordCompletionScene() {
        App.setScene(this.getScene());
    }

    public void showHomePage() {
        App.setScene(App.homePage.getScene());
    }

}

