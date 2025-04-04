package com.sigmatechnologies.gamblingsimulator25;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Window;

import java.util.Objects;

public class GameSelectorController {

    @FXML
    private ImageView pokerIV;
    @FXML
    private ImageView blackjackIV;
    @FXML
    private ImageView rouletteIV;
    @FXML
    private ImageView dicesIV;

    public void initialize() {

        Image pokerI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/poker.png")));
        Image blackjackI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/blackjack.png")));
        Image rouletteI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/roulette.png")));
        Image dicesI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/dices.png")));

        pokerIV.setImage(pokerI);
        blackjackIV.setImage(blackjackI);
        rouletteIV.setImage(rouletteI);
        dicesIV.setImage(dicesI);

    }

    @FXML
    protected void onRouletteClick(MouseEvent mouseEvent) {

        Node source = (Node) mouseEvent.getSource();

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sigmatechnologies/gamblingsimulator25/roulette.fxml"));
            BorderPane root = loader.load();

            Stage stage = (Stage) source.getScene().getWindow();

            Scene newScene = new Scene(root);
            stage.setScene(newScene);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @FXML
    protected void onCloseClick(ActionEvent event) {

        Platform.exit();

    }

    @FXML
    protected void onAboutClick(ActionEvent event) {

        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("About");
        about.setHeaderText("Game Selector");
        about.setContentText("W.I.P.");
        about.showAndWait();

    }

    @FXML
    protected void onReturnClick(ActionEvent event) {

        MenuItem menuItem = (MenuItem) event.getSource();
        Window window = menuItem.getParentPopup().getOwnerWindow();
        Scene scene = ((Stage) window).getScene();

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sigmatechnologies/gamblingsimulator25/main-menu.fxml"));
            BorderPane root = loader.load();

            Stage stage = (Stage) scene.getWindow();

            Scene newScene = new Scene(root);
            stage.setScene(newScene);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}