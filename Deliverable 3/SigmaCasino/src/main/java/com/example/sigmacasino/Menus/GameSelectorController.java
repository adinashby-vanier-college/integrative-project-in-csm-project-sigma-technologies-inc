package com.example.sigmacasino.Menus;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class GameSelectorController {

    @FXML private ImageView pokerIV;
    @FXML private ImageView blackjackIV;
    @FXML private ImageView rouletteIV;
    @FXML private ImageView dicesIV;

    @FXML private MenuItem returnMainMenu;



    public void initialize() {

        File poker = new File("out/production/integrative-project-in-csm-project-sigma-technologies-inc/com/example/sigmacasino/Sprites/thumbails/poker.png");
        File blackjack = new File("out/production/integrative-project-in-csm-project-sigma-technologies-inc/com/example/sigmacasino/Sprites/thumbails/blackjack.png");
        File roulette = new File("out/production/integrative-project-in-csm-project-sigma-technologies-inc/com/example/sigmacasino/Sprites/thumbails/roulette.png");
        //File dices = new File("out/production/integrative-project-in-csm-project-sigma-technologies-inc/com/example/sigmacasino/Sprites/thumbails/menu.png");



        Image pokerI = new Image(poker.toURI().toString());
        Image blackjackI = new Image(blackjack.toURI().toString());
        Image rouletteI = new Image(roulette.toURI().toString());
       // Image dicesI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/dices.png")));

        pokerIV.setImage(pokerI);
        blackjackIV.setImage(blackjackI);
        rouletteIV.setImage(rouletteI);
        //dicesIV.setImage(dicesI);

        pokerIV.setOnMouseClicked(event -> {
            try {
                switchToScene(event, "/com/example/sigmacasino/UI/poker.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        blackjackIV.setOnMouseClicked(event -> {
            try {
                switchToScene(event, "/com/example/sigmacasino/UI/blackjack.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        rouletteIV.setOnMouseClicked(event -> {
            try {
                switchToScene(event, "/com/example/sigmacasino/UI/roulette.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        returnMainMenu.setOnAction(event -> {
            try {
                switchToScene(event, "/com/example/sigmacasino/UI/main-menu.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

//    @FXML
//    protected void onRouletteClick(MouseEvent mouseEvent) {
//
//        Node source = (Node) mouseEvent.getSource();
//
//        try {
//
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sigmatechnologies/gamblingsimulator25/roulette.fxml"));
//            BorderPane root = loader.load();
//
//            Stage stage = (Stage) source.getScene().getWindow();
//
//            Scene newScene = new Scene(root);
//            stage.setScene(newScene);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//
//    }

    public void switchToScene(Event event, String fxmlFile) throws IOException {
        System.out.println("Fxml: "+getClass().getResource((fxmlFile)));

        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = null;
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