package com.example.sigmacasino.Roulette;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RouletteController {

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

    

}