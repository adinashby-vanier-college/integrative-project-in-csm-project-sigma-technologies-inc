package com.example.sem4casino;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setMaximized(true);
        stage.setResizable(false);
        Parent root =FXMLLoader.load(getClass().getResource("pokerNewOptimizedCopy.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Poker!");
        stage.setScene(scene);
        stage.show();
        System.out.println(scene.getWidth());
        System.out.println(scene.getHeight());
    }

    public static void main(String[] args) {
        launch();
    }
}