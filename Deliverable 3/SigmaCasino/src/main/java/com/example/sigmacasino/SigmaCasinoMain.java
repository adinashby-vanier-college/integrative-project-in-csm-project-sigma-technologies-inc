package com.example.sigmacasino;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SigmaCasinoMain extends Application {

    public static String stylesPath = "/com/example/sigmacasino/Styles/global_dark.css";

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(SigmaCasinoMain.class.getResource("/com/example/sigmacasino/UI/main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource(stylesPath).toExternalForm());
        stage.setTitle("Sigma Casino");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}