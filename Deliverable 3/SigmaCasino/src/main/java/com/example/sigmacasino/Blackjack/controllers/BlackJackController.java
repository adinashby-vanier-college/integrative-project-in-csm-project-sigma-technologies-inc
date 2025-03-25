package com.example.sigmacasino.Blackjack.controllers;

import com.example.sigmacasino.Blackjack.gameLogic.Deck;
import com.example.sigmacasino.Blackjack.gameLogic.Hand;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class BlackJackController {


    private Deck deck = new Deck();
    private Hand dealer, player;
    private Text message = new Text();
    private SimpleBooleanProperty isPlayable = new SimpleBooleanProperty(false);


    private Parent createContent(){
        playBtn.disableProperty().bind(isPlayable);
        hitBtn.disableProperty().bind(isPlayable.not());
        standBtn.disableProperty().bind(isPlayable.not());
        dealerPts.textProperty().bind(new SimpleStringProperty("Dealer: ").concat(dealer.valueProperty().asString()));
        playerPts.textProperty().bind(new SimpleStringProperty("Player: ").concat(player.valueProperty().asString()));
        player.valueProperty().addListener((obs, old, newValue) -> {
            if(newValue.intValue() >=21){
                endGame();
            }
        });
        dealer.valueProperty().addListener((obs, old, newValue) -> {
            if(newValue.intValue() >=21){
                endGame();
            }
        });
        playBtn.setOnAction((ActionEvent event) -> {
            startNewGame();
        });
        hitBtn.setOnAction((ActionEvent event) -> {
            player.takeCard(deck.drawCard());
        });
        standBtn.setOnAction((ActionEvent event) -> {
            while(dealer.valueProperty().get()<17){
                dealer.takeCard(deck.drawCard());
            }
            endGame();
        });
   return root; }
    private void startNewGame(){

    }
    private void endGame(){

    }


    @FXML
    private Button playBtn;
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
    @FXML
    private MenuItem closeMenu;



    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize()  throws IOException {
        System.out.println("BlackJack successfully initialized");
        closeMenu.setOnAction(event -> {
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



