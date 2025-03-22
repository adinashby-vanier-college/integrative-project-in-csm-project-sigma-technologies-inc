package com.example.sigmacasino.Poker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GTP extends Application {
    private int currentPlayer = 1; // Assume 2 players for now

    @Override
    public void start(Stage primaryStage) {
        Button checkButton = new Button("Check");
        Button foldButton = new Button("Fold");
        Button raiseButton = new Button("Raise");

        checkButton.setOnAction(e -> handleAction("Check"));
        foldButton.setOnAction(e -> handleAction("Fold"));
        raiseButton.setOnAction(e -> handleAction("Raise"));

        VBox layout = new VBox(10, checkButton, foldButton, raiseButton);
        primaryStage.setScene(new Scene(layout, 300, 200));
        primaryStage.show();
    }

    private void handleAction(String action) {
        System.out.println("Player " + currentPlayer + " chooses to " + action);

        // Move to the next turn
        nextTurn();
        System.out.println("Moved On");
    }

    private void nextTurn() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1; // Switch players
        System.out.println("Now it's Player " + currentPlayer + "'s turn.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
