package com.sigmatechnologies.gamblingsimulator25;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

public class MainMenuController {

    @FXML
    private ImageView mainMenuIV;

    public void initialize() {

        Image mainMenuI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/menu.png")));
        mainMenuIV.setImage(mainMenuI);

    }

    @FXML
    protected void onPlayClick(ActionEvent event) {

        Node source = (Node) event.getSource();

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sigmatechnologies/gamblingsimulator25/game-selector.fxml"));
            BorderPane root = loader.load();

            Stage stage = (Stage) source.getScene().getWindow();

            Scene newScene = new Scene(root);
            stage.setScene(newScene);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @FXML
    protected void onAboutClick(ActionEvent event) {

        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("About");
        about.setHeaderText("About");
        about.setContentText("W.I.P.");
        about.showAndWait();

    }

    @FXML
    protected void onCloseClick(ActionEvent event) {

        Platform.exit();

    }

}