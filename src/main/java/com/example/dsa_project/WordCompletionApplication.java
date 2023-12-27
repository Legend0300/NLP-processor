package com.example.dsa_project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class WordCompletionApplication extends Application {
    public Stage stage;
    public HomePage homePage;
    public WordPrediction wordPrediction;
    public WordCompletion wordCompletion;
    WordPredictor predictor = new WordPredictor();
    WordCompleter completer = new WordCompleter(6);

    public static void main(String[] args) {
        launch();
    }
    public void showHomePage (){
        stage.setScene(homePage.getScene());
    }
    public void showWordPredictionPage (){
        stage.setScene(wordPrediction.getScene());
    }
    public void showWordCompletionPage(){
        stage.setScene(wordCompletion.getScene());
    }
    public void setScene (Scene scene){
        this.stage.setScene(scene);
    }


    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        stage.setTitle("WORD COMPLETION AND PREDICTION.");
        homePage = new HomePage(this);
        wordPrediction = new WordPrediction(this);
        wordCompletion = new WordCompletion(this);

        stage.getIcons().add(new Image("C:\\Users\\Legend\\Desktop\\NLP Processor\\NLP-processor\\fa12c5b9-e57d-44cf-9ed3-e3c45e0f2e3d.jpg"));

        stage.setScene(homePage.getScene());
        stage.show();
    }
}
