package com.example.sigmacasino.Blackjack.gameLogic;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Test extends Application {
    public static void main(String[] args) {
  launch(args);
    }

    private void testReset() {
        ObservableList<Node> cards = FXCollections.observableArrayList();
        Hand hand = new Hand(cards);
        hand.takeCard(new BlackJackCard(BlackJackCard.BSuit.SPADES, BlackJackCard.BRank.TEN));
        hand.reset();
        printResult("Reset hand", hand.valueProperty().get(), 0);
    }

    private void testBust() {
        ObservableList<Node> cards = FXCollections.observableArrayList();
        Hand hand = new Hand(cards);
        hand.takeCard(new BlackJackCard(BlackJackCard.BSuit.HEARTS, BlackJackCard.BRank.KING));
        hand.takeCard(new BlackJackCard(BlackJackCard.BSuit.CLUBS, BlackJackCard.BRank.NINE));
        hand.takeCard(new BlackJackCard(BlackJackCard.BSuit.DIAMONDS, BlackJackCard.BRank.FOUR));
        int value = hand.valueProperty().get();
        System.out.println("Bust test: " + value + " (Expected > 21)");
    }

    private void testBlackJack() {
        ObservableList<Node> cards = FXCollections.observableArrayList();
        Hand hand = new Hand(cards);
        hand.takeCard(new BlackJackCard(BlackJackCard.BSuit.HEARTS, BlackJackCard.BRank.ACE));
        hand.takeCard(new BlackJackCard(BlackJackCard.BSuit.SPADES, BlackJackCard.BRank.JACK));
        printResult("Natural Blackjack", hand.valueProperty().get(), 21);
    }

    private void testAceAdjusted() {
        ObservableList<Node> cards = FXCollections.observableArrayList();
        Hand hand = new Hand(cards);
        hand.takeCard(new BlackJackCard(BlackJackCard.BSuit.HEARTS, BlackJackCard.BRank.ACE));
        hand.takeCard(new BlackJackCard(BlackJackCard.BSuit.CLUBS, BlackJackCard.BRank.SIX));
        hand.takeCard(new BlackJackCard(BlackJackCard.BSuit.DIAMONDS, BlackJackCard.BRank.SEVEN));
        printResult("Ace adjusted to 1", hand.valueProperty().get(), 14);

    }

    private void testHandValueWithoutAces() {
        ObservableList<Node> cards = FXCollections.observableArrayList();
        Hand hand = new Hand(cards);
        hand.takeCard(new BlackJackCard(BlackJackCard.BSuit.HEARTS, BlackJackCard.BRank.TEN));
        hand.takeCard(new BlackJackCard(BlackJackCard.BSuit.SPADES, BlackJackCard.BRank.SEVEN));
        printResult("Hand without aces", hand.valueProperty().get(), 17);
    }

    private void testAceAsEleven() {
    }
    private void printResult(String testName, int actual, int expected) {
        String result = actual== expected ? "PASS" : "FAIL";
        System.out.printf("%s: got %d (expected %d)", testName, actual, expected, result);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.runLater(() -> {
            try{
            testReset();
            testAceAdjusted();
            testBust();
            testAceAsEleven();
            testBlackJack();
            testHandValueWithoutAces();} catch (Exception e) {
                e.printStackTrace();
            }
            Platform.exit();
        });
    }
}
