package com.example.sigmacasino.Blackjack.controllers;

import com.example.sigmacasino.Blackjack.gameLogic.Deck;
import com.example.sigmacasino.Blackjack.gameLogic.Hand;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class BlackJackController {


    private final Deck deck = new Deck();
    private Hand dealer, player;

    private final SimpleBooleanProperty isPlayable = new SimpleBooleanProperty(false);



    private void startNewGame(){

        isPlayable.set(true);
        resultText.setText("");
        resultText.setVisible(false);
        deck.refill();
        dealer.reset();
        player.reset();

        dealer.takeCard(deck.drawCard());
        dealer.takeCard(deck.drawCard());
        player.takeCard(deck.drawCard());
        player.takeCard(deck.drawCard());
        checkForBlackJack();

    }
//Checks for instant win during card dealing
    private void checkForBlackJack() {
        int dealerValue = dealer.valueProperty().get();
        int playerValue = player.valueProperty().get();
        if(dealerValue == 21 && playerValue == 21){
            resultText.setText("Tie: Both players instantly have Blackjack!");
            resultText.setVisible(true);
            betField.clear();
            currentBet[0] = 0;
        }
        else if (playerValue == 21) {
            resultText.setText("Blackjack! PLAYER wins " + currentBet[0] + "$");
        } else if (dealerValue == 21) {
            resultText.setText("Dealer has Blackjack! DEALER wins.");
        } else {
            //base case
        }
    }

    private void endGame(){
        isPlayable.set(false);
        while (dealer.valueProperty().get() < 17) {
            dealer.takeCard(deck.drawCard());
        }
        int dealerValue = dealer.valueProperty().get();
        int playerValue = player.valueProperty().get();
        String winner = "Point totals: dealer: " +dealerValue+ " player: " + playerValue;
        if (playerValue > 21) {
            resultText.setText("PLAYER busted! DEALER wins " + currentBet[0] + "$");
            resultText.setVisible(true);
            betField.clear();
            currentBet[0] = 0;
            return; //stop
        }
        if(playerValue == 21 && dealerValue != 21) {
            winner = "PLAYER";
        }
        else if(dealerValue == 21 && playerValue != 21){
            winner = "DEALER";
        }
        else if(playerValue >21){
            winner = "DEALER";
        }
        else if(dealerValue >21){
            winner = "PLAYER";
        }
        else if(playerValue > dealerValue){
            winner = "PLAYER";
        }
        else if(dealerValue > playerValue){
            winner = "DEALER";
        }
        else{
            resultText.setText("Tie: no money has been won");
            resultText.setVisible(true);
            betField.clear();
            currentBet[0] = 0;
        }

        if(resultText.getText().equals("DEALER") || resultText.getText().equals("PLAYER")){
            resultText.setText(winner + " won " + currentBet[0] + "$");
            resultText.setVisible(true);
            betField.clear();
            currentBet[0] = 0;
        }

        resultText.setText(winner + " won " + currentBet[0] + "$");
        resultText.setVisible(true);
        betField.clear();
        currentBet[0] = 0;

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
    @FXML
    private HBox playerCards;
    @FXML
    private HBox dealerCards;
    @FXML
    private Text resultText;
    @FXML
    private Button addFive;
    @FXML
    private Button removeFive;
    @FXML
            private MenuItem optionsMenu;
    @FXML
            private Rectangle tableBackground;


    int[] currentBet = {0};
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize()  throws IOException {

        currentBet[0] = 0;
        resultText.setVisible(false);
        System.out.println("BlackJack successfully initialized");
        closeMenu.setOnAction(event -> {
            try {
                switchToScene(event, "/com/example/sigmacasino/UI/game-selector.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        dealer = new Hand(dealerCards.getChildren());
        player = new Hand(playerCards.getChildren());



        playBtn.disableProperty().bind(isPlayable);
        hitBtn.disableProperty().bind(isPlayable.not());
        standBtn.disableProperty().bind(isPlayable.not());
        dealerPts.textProperty().bind(new SimpleStringProperty("").concat(dealer.valueProperty().asString()));
        playerPts.textProperty().bind(new SimpleStringProperty("").concat(player.valueProperty().asString()));
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
            if(player.valueProperty().get()==21){
                dealer.takeCard(deck.drawCard());
            }
            endGame();
        });
        addFive.setOnAction((ActionEvent event) -> {
            currentBet[0] = currentBet[0] + 5;
            betField.setText(Integer.toString(currentBet[0]));
        });
        optionsMenu.setOnAction(event -> {
            openOptions();
        });

        removeFive.setOnAction((ActionEvent event) -> {
            if (currentBet[0] >= 5) {
                currentBet[0] = currentBet[0] - 5;
            }
            if (currentBet[0] == 0) {
                Background originalBackground = removeFive.getBackground();
                removeFive.setTextFill(Color.RED);
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                    removeFive.setTextFill(Color.BLACK);
                }));
                timeline.setCycleCount(1);
                timeline.play();
            }
            betField.setText(Integer.toString(currentBet[0]));

        });
    }

    private void openOptions() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sigmacasino/UI/blackjackConfig.fxml"));
                Parent root = loader.load();

                BlackJackConfigController optionsController = loader.getController();
                tableBackground.fillProperty().bind(optionsController.colorPicker().valueProperty());



                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    private void setBet(){

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



