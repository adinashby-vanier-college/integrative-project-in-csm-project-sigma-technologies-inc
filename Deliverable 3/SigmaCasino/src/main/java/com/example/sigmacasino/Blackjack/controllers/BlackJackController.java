package com.example.sigmacasino.Blackjack.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;


public class BlackJackController {
    @FXML
    private Button exitBtn;
    @FXML
    private Button hitBtn;
    @FXML
    private Button standBtn;
    @FXML
    private Button doubleBtn;
    @FXML
    private TextField betField;
    @FXML
    private TextField dealerPts;
    @FXML
    private TextField playerPts;
    @FXML
    private ImageView dealCard1;
    @FXML
    private ImageView dealCard2;
    @FXML
    private ImageView dealCard3;
    @FXML
    private ImageView playerCard1;
    @FXML
    private ImageView PlayerCard2;
    @FXML
    private ImageView playerCard3;




    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize()  throws IOException {
        System.out.println("BlackJack successfully initialized");
        exitBtn.setOnAction(event -> {
            try {
                switchToScene(event, "/com/example/sigmacasino/UI/game-selector.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void switchToScene(ActionEvent event, String fxmlFile) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxmlFile));
        if (event.getSource() instanceof Node) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } else {
            stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        }
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    }



