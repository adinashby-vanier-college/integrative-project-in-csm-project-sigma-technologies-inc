package com.example.sigmacasino.Menus;

import com.example.sigmacasino.Auth.AuthManager;
import com.example.sigmacasino.Blackjack.controllers.BlackJackController;
import com.example.sigmacasino.Poker.PokerController;
import com.example.sigmacasino.SigmaCasinoMain;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class GameSelectorController {

    // Game selection menu
    @FXML private ImageView pokerIV;
    @FXML private ImageView blackjackIV;
    @FXML private ImageView rouletteIV;
//    @FXML private ImageView minesIV;
//    @FXML private ImageView dicesIV;
    @FXML private MenuItem quitMenu;
    @FXML private Label playerName;

    @FXML private MenuItem returnMainMenu;

    public void initialize() {
        playerName.setText(AuthManager.current_username);

        File poker = new File("src/main/resources/com/example/sigmacasino/Sprites/thumbails/poker.png");
        File blackjack = new File("src/main/resources/com/example/sigmacasino/Sprites/thumbails/blackjack.png");
        File roulette = new File("src/main/resources/com/example/sigmacasino/Sprites/thumbails/roulette.png");
//        File mines = new File("src/main/resources/com/example/sigmacasino/Sprites/thumbails/mines.png");
//        File dices = new File("src/main/resources/com/example/sigmacasino/Sprites/thumbails/dices.png");

        Image pokerI = new Image(poker.toURI().toString());
        Image blackjackI = new Image(blackjack.toURI().toString());
        Image rouletteI = new Image(roulette.toURI().toString());
//        Image minesI = new Image(mines.toURI().toString());
//        Image dicesI = new Image(dices.toURI().toString());

        pokerIV.setImage(pokerI);
        blackjackIV.setImage(blackjackI);
        rouletteIV.setImage(rouletteI);
//        minesIV.setImage(minesI);
//        dicesIV.setImage(dicesI);

        pokerIV.setOnMouseClicked(event -> {
            try {
                switchToScene(event, "/com/example/sigmacasino/UI/poker.fxml", new PokerController(),true,0,0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        blackjackIV.setOnMouseClicked(event -> {
            try {
                switchToScene(event, "/com/example/sigmacasino/UI/blackjack.fxml", new BlackJackController(),false,580,930);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        rouletteIV.setOnMouseClicked(event -> {
            try {
                switchToScene(event, "/com/example/sigmacasino/UI/roulette.fxml", null,false,440,620);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        returnMainMenu.setOnAction(event -> {
            try {
                switchToScene(event, "/com/example/sigmacasino/UI/main-menu.fxml",null,false,440,620);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        quitMenu.setOnAction(event -> {
            try {
                Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
                stage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

    @FXML
    protected void onPlayClick(ActionEvent event) {

        Node source = (Node) event.getSource();

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sigmacasino/UI/game-selector.fxml"));
            BorderPane root = loader.load();

            Stage stage = (Stage) source.getScene().getWindow();

            Scene newScene = new Scene(root);
            newScene.getStylesheets().add(getClass().getResource(SigmaCasinoMain.stylesPath).toExternalForm());
            stage.setScene(newScene);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public void switchToScene(Event event, String fxmlFile, Object controller, boolean maximize , int height, int width ) throws IOException {
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
            scene.getStylesheets().add(getClass().getResource(SigmaCasinoMain.stylesPath).toExternalForm());
            stage.setScene(scene);
            stage.show();
            if(maximize){
                stage.setMaximized(true);
            }else {
                stage.setMaximized(false);
                stage.setHeight(height);
                stage.setWidth(width);
            }
    }

    // Options menu
    @FXML
    protected void onOptionsClick(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sigmacasino/UI/settings.fxml"));
            BorderPane root = loader.load();

            Stage secondStage = new Stage();
            secondStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            Scene secondScene = new Scene(root);
            secondScene.getStylesheets().add(getClass().getResource(SigmaCasinoMain.stylesPath).toExternalForm());
            secondStage.setTitle("Settings");
            secondStage.setScene(secondScene);
            secondStage.setResizable(false);
            secondStage.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}