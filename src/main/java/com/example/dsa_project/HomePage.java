package com.example.dsa_project;

import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.Scene;



public class HomePage {
    private Label selectedFileLabel;
    private WordCompletionApplication App;
    private Scene homeScene;
    Button wordCompletingButton;
    Button wordPredictionButton;
    public  HomePage(WordCompletionApplication app) {
        this.App = app;
        this.selectedFileLabel = new Label("No file selected");  // Initialize the label
        createMainScene();
    }

    public Scene getScene() {
        return homeScene;
    }

    private void createMainScene() {
        // Create buttons
         wordCompletingButton = new Button("Word Completion");
         wordPredictionButton = new Button("Word Prediction");
        Label label1 = new Label("WORD PREDICTION AND COMPLETION APP.");
        Label label2 = new Label("Please select an option below.");
        Font boldfont = Font.font(label1.getFont().getFamily(), FontWeight.BOLD, label1.getFont().getSize());
        label1.setFont(boldfont);

        Button chooseFileButton = new Button("Choose PDF File");
        chooseFileButton.setOnAction(e -> choosePDFFile());


        // Set actions for buttons
        wordCompletingButton.setOnAction(e -> App.showWordCompletionPage());
        wordPredictionButton.setOnAction(e -> App.showWordPredictionPage());

        // Create layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(50, 10, 10, 100));
        layout.getChildren().addAll(label1, label2,wordCompletingButton, wordPredictionButton,chooseFileButton,selectedFileLabel);

        // Create main scene
        homeScene = new Scene(layout, 450, 450);

}
    public void showWordPredcitionScene() {
        App.setScene(this.getScene());
    }

    public void showHomePage() {
        App.setScene(App.homePage.getScene());
    }

    private void choosePDFFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            selectedFileLabel.setText("Selected PDF File: " + filePath);
            App.predictor.analyzeDocument(filePath);
            App.completer.analyzeDocument(filePath);
            
            System.out.println("ANALYZATION COMPLETED.");
        } else {
            selectedFileLabel.setText("No file selected");
        }
    }
}
