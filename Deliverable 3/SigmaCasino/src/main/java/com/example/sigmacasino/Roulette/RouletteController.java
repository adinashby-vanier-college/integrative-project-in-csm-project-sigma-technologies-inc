package com.example.sigmacasino.Roulette;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class RouletteController {

    @FXML
    private Label tracker;

    @FXML
    protected void onGameSelectionMenuItemClick(ActionEvent event) throws IOException {
        switchToScene(event, "/com/example/sigmacasino/UI/game-selector.fxml",null);
    }

    @FXML
    protected void onQuitMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stage.close();
    }


//    @FXML
//    protected void onReturnClick(ActionEvent event) {
//
//        //TO FIX LATER + MODIFY LINE 15 OF ROULETTE.FXML
//        /*
//        MenuItem menuItem = (MenuItem) event.getSource();
//
//        try {
//
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sigmacasino/UI/game-selector.fxml"));
//            BorderPane root = loader.load();
//
//            Stage stage = (Stage) menuItem.getParentMenu().getParentPopup().getScene().getWindow();
//
//            Scene newScene = new Scene(root);
//            stage.setScene(newScene);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//         */
//    }

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

    //Method for changing the FXML file of the stage
    private void switchToScene(Event event, String fxmlFile, Object controller ) throws IOException {
        System.out.println("Fxml: " + getClass().getResource((fxmlFile)));
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        if (controller != null) {
            loader.setController(controller);
        }
        Parent root = loader.load();
        Stage stage;
        if (event.getSource() instanceof Node) {
            // If the event source is a Node (ex: Button)
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } else {
            // If the event source is not a Node (ex:MenuItem)
            stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}