package com.example.dsa_project;

import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class WordPrediction {
    private WordCompletionApplication App;
    private Scene wordPredictionScene;

    public WordPrediction(WordCompletionApplication app) {
        this.App = app;
    }
    public Scene getScene(){
        createWordPredictionScene();
        return wordPredictionScene;
    }
    private void createWordPredictionScene() {
        // Create components for Word Completion scene
        TextField textField = new TextField();
        TextArea textArea = new TextArea();
        Label label1 = new Label("Enter a word to get predicted next words:");
        Label label2 = new Label("Predicted next words:");
        Button backButton = new Button("Back");

        // Set action for back button
        backButton.setOnAction(e -> showHomePage());

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textArea.setText(App.predictor.suggestNextWords(newValue).toString());
        });

        // Create layout for Word Completion scene
        VBox wordPredictionLayout = new VBox(10);
        wordPredictionLayout.setPadding(new Insets(10, 10, 10, 10));
        wordPredictionLayout.getChildren().addAll(label1,textField,label2,textArea ,backButton);

        // Create Word Completion scene
        wordPredictionScene = new Scene(wordPredictionLayout, 450, 450);
    }
    public void showWordPredcitionScene() {
        App.setScene(this.getScene());
    }

    public void showHomePage() {
        App.setScene(App.homePage.getScene());
    }


}
