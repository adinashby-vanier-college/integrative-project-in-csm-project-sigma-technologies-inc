package com.example.sigmacasino.Poker;

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sigmacasino/UI/poker.fxml"));
        Parent root = fxmlLoader.load();
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