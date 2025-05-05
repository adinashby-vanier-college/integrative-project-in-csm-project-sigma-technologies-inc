package com.example.sigmacasino.Roulette;

import com.example.sigmacasino.SigmaCasinoMain;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.sigmacasino.Calculator.CryptoRandom;
import java.io.IOException;

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

    @FXML
    protected void onAboutClick(ActionEvent event) {

        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("About");
        about.setHeaderText("Roulette");
        about.setContentText("Welcome to American Roulette! Essentially you have to bet which " +
                             "slot a ball will fall in in a spinning roulette wheel. There are" +
                             " 38 numbered slots, half-red, half-black, including a 0 and 00 w" +
                             "hich are both green. You can bet a monetary amount on numbers, c" +
                             "olours, or odds/evens and ranges 1-18. You lose if the ball does" +
                             " not land in your bet slot.");
        about.showAndWait();

    }

    @FXML
    protected void onTipsClick(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(SigmaCasinoMain.class.getResource("/com/example/sigmacasino/UI/RouletteTipsAndInfo.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Tips and Info");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println("Error switching to tips and info");
        }
        
//        Alert about = new Alert(Alert.AlertType.INFORMATION);
//        about.setTitle("About");
//        about.setHeaderText("Tips and Information");
//        about.setContentText("Warnings: Hot/Cold number trackers and the concept of cranking or hot streaks for certain" +
//                             " numbers is a gambler's fallacy, since all the roles are independent from each other and " +
//                             "in no way affect each other's probabilities.\n\nThe house edge in American Roulette (which " +
//                             "is slightly higher than in European Roulette due to the 00 (2.70%-1.35%, lower when using" +
//                             " En Prison/La Partage rules) coming to a whopping 5.26% (7.89% if you play suboptimally) " +
//                             "of bet amount which they keep on average.\n\nHere is the best play: avoid American Roulet" +
//                             "te in favour of European Roulette if you can, if not: if your goal is to not lose any mon" +
//                             "ey (payout of 1:1) bet evenly for all options (ex: red/black), for a balanced playstyle: " +
//                             "6 numbers and a property (ex: 13-18 and Red), avoid the top line bet (0, 00, 1, 2, 3) and" +
//                             " single numbers (very low odds), remember the house always wins and hot streaks aren't a " +
//                             "thing...");
//        about.showAndWait();
//
    }

    @FXML
    protected void onSpinClick(ActionEvent event) {
        int number = CryptoRandom.GenerateRandomRangeInt(1,37);
        String temp;

        if (number == 37) {
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

    public void onBetsClick(ActionEvent actionEvent) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sigmacasino/UI/bet.fxml"));
            BorderPane root = loader.load();

            Stage secondStage = new Stage();
            secondStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            Scene secondScene = new Scene(root);
            secondStage.setTitle("Bets");
            secondStage.setScene(secondScene);
            secondStage.setResizable(false);
            secondStage.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}