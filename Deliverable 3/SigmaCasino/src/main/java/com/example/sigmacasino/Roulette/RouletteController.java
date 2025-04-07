package com.example.sigmacasino.Roulette;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.util.Random;

public class RouletteController {

    @FXML
    private Label tracker;

    @FXML
    protected void onReturnClick(ActionEvent event) {

        //TO FIX LATER + MODIFY LINE 15 OF ROULETTE.FXML
        /*
        MenuItem menuItem = (MenuItem) event.getSource();

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sigmacasino/UI/game-selector.fxml"));
            BorderPane root = loader.load();

            Stage stage = (Stage) menuItem.getParentMenu().getParentPopup().getScene().getWindow();

            Scene newScene = new Scene(root);
            stage.setScene(newScene);

        } catch (Exception e) {

            e.printStackTrace();

        }
         */
    }

    @FXML
    protected void onAboutClick(ActionEvent event) {

        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("About");
        about.setHeaderText("Roulette");
        about.setContentText("W.I.P.");
        about.showAndWait();

    }

    @FXML
    protected void onTipsClick(ActionEvent event) {

        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("About");
        about.setHeaderText("Tips and Information");
        about.setContentText("W.I.P.");
        about.showAndWait();

    }

    @FXML
    protected void onSpinClick(ActionEvent event) {

        //RNG TO BE REPLACED BY CUSTOM RNG WRAPPER
        Random rng = new Random();
        int number = rng.nextInt(38);
        if (number == 37) {
            number = 0;
        } else if (number == 38) {
            number = -1;
        }

        String temp;
        if (number == -1) {
            temp = "00";
        } else {
            temp = String.valueOf(number);
        }
        String list = tracker.getText();
        tracker.setText(temp + ", " + list);

    }

}