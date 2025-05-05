package com.example.sigmacasino.Roulette;

import com.example.sigmacasino.Calculator.CryptoRandom;
import com.example.sigmacasino.SigmaCasinoMain;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class RouletteController {

    @FXML
    private Label tracker;
    @FXML
    private TextField winTextField;
    @FXML
    private Canvas canvas;

    // Return
    @FXML
    protected void onGameSelectionMenuItemClick(ActionEvent event) throws IOException {
        switchToScene(event, "/com/example/sigmacasino/UI/game-selector.fxml",null);
    }

    // Quit
    @FXML
    protected void onQuitMenuItemClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stage.close();
    }

    // Tutorials
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

    // Tutorials
    @FXML
    protected void onTipsClick(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(SigmaCasinoMain.class.getResource("/com/example/sigmacasino/UI/RouletteTipsAndInfo.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(getClass().getResource(SigmaCasinoMain.stylesPath).toExternalForm());
            stage.setTitle("Tips and Info");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println("Error switching to tips and info");
        }

    }

    @FXML
    protected void onSpinClick(ActionEvent event) {
        int number = CryptoRandom.GenerateRandomRangeInt(0,37);
        String temp;

        // 37 returns 00 -> 0-36 + 00 -> 38 slots
        if (number == 37) {
            temp = "00";
        } else {
            temp = String.valueOf(number);
        }

        String list = tracker.getText();
        tracker.setText(temp + ", " + list);

        calculateWin(number);
        playAnimation(number);

    }

    // Win amount calculation
    protected void calculateWin(int number) {

        if (!BetController.bets.isEmpty()) {

            int sum = 35 * BetController.bets.get(number);
            winTextField.setText(String.valueOf(sum));

        }

    }

    // Animation player
    private double angle = 0;
    private double speed = 30;
    final double deceleration = 0.5;

    protected void playAnimation(int number) {

        angle = Math.random()*360;
        speed = 20 + Math.random()*10;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image image = new Image(getClass().getResource("/com/example/sigmacasino/Sprites/wheel.png").toExternalForm(), 200, 200, true, true);


        final Timeline[] timeline = new Timeline[1];
        timeline[0] = new Timeline();
        KeyFrame keyframe = new KeyFrame(Duration.millis(16), e -> {

            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            drawRotatedImage(gc, image, angle, canvas.getWidth() / 2, canvas.getHeight() / 2);
            angle += speed;
            speed = Math.max(speed - deceleration, 0);
            if (speed == 0) timeline[0].stop();

        });
        timeline[0].getKeyFrames().add(keyframe);
        timeline[0].setCycleCount(Timeline.INDEFINITE);
        timeline[0].play();

    }

    private void drawRotatedImage(GraphicsContext gc, Image image, double angle, double px, double py) {
        gc.save();
        rotate(gc, angle, px, py);
        gc.drawImage(image, px - image.getWidth() / 2, py - image.getHeight() / 2);
        gc.restore();
    }

    private void rotate(GraphicsContext gc, double angle, double px, double py) {
        gc.translate(px, py);
        gc.rotate(angle);
        gc.translate(-px, -py);
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
        scene.getStylesheets().add(getClass().getResource(SigmaCasinoMain.stylesPath).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    // Betting window open
    @FXML
    public void onBetsClick(ActionEvent actionEvent) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sigmacasino/UI/bet.fxml"));
            BorderPane root = loader.load();

            Stage secondStage = new Stage();
            secondStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            Scene secondScene = new Scene(root);
            secondScene.getStylesheets().add(getClass().getResource(SigmaCasinoMain.stylesPath).toExternalForm());
            secondStage.setTitle("Bets");
            secondStage.setScene(secondScene);
            secondStage.setResizable(false);
            secondStage.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}