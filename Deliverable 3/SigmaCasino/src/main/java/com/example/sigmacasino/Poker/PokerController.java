package com.example.sigmacasino.Poker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class PokerController {


    @FXML public Spinner<Integer> SpinnerBots;
    @FXML public ChoiceBox<String> ChoiceBoxBruntCards;
    @FXML public Circle bot2Turn;
    @FXML public Circle bot1Turn;
    @FXML public Circle bot4Turn;
    @FXML public Circle bot5Turn;
    @FXML public Circle playerTurn;
    @FXML public Circle bot3Turn;


    @FXML public CheckBox startRound;

    @FXML public Button checkButton;
    @FXML public Button foldButton;
    @FXML public Button raiseButton;

    @FXML public TextField startingChips;
    @FXML public TextField raiseText;

    @FXML private ImageView best_H1_1;
    @FXML private ImageView best_H1_2;
    @FXML private ImageView best_H1_3;
    @FXML private ImageView best_H1_4;
    @FXML private ImageView best_H1_5;
    @FXML private ImageView best_H2_1;
    @FXML private ImageView best_H2_2;
    @FXML private ImageView best_H2_3;
    @FXML private ImageView best_H2_4;
    @FXML private ImageView best_H2_5;
    @FXML private ImageView riverCard1;
    @FXML private ImageView riverCard2;
    @FXML private ImageView riverCard3;
    @FXML private ImageView riverCard4;
    @FXML private ImageView riverCard5;
    @FXML private ImageView playerCard1;
    @FXML private ImageView playerCard2;
    @FXML private ImageView bot1Card1;
    @FXML private ImageView bot1Card2;
    @FXML private ImageView bot2Card1;
    @FXML private ImageView bot2Card2;
    @FXML private ImageView bot3Card1;
    @FXML private ImageView bot3Card2;
    @FXML private ImageView bot4Card1;
    @FXML private ImageView bot4Card2;
    @FXML private ImageView bot5Card1;
    @FXML private ImageView bot5Card2;

    @FXML private Ellipse brownEllipse;
    @FXML private Ellipse greenEllipse;
    @FXML private Circle playerCircle;
    @FXML private Circle botCircle1;
    @FXML private Circle botCircle2;
    @FXML private Circle botCircle3;
    @FXML private Circle botCircle4;
    @FXML private Circle botCircle5;
    @FXML private MenuItem menuQuit;

    @FXML private Label namePlayer;
    @FXML private Label nameBot1;
    @FXML private Label nameBot2;
    @FXML private Label nameBot3;
    @FXML private Label nameBot4;
    @FXML private Label nameBot5;

    @FXML private Label chipsPlayer;
    @FXML private Label chipsBot1;
    @FXML private Label chipsBot2;
    @FXML private Label chipsBot3;
    @FXML private Label chipsBot4;
    @FXML private Label chipsBot5;

    @FXML
    public void initialize() throws IOException {
            ImageView[] imageViews = {best_H1_1, best_H1_2, best_H1_3, best_H1_4, best_H1_5, best_H2_1, best_H2_2
                    , best_H2_3, best_H2_4, best_H2_5, riverCard1, riverCard2, riverCard3, riverCard4, riverCard5
                    , playerCard1, playerCard2, bot1Card1, bot1Card2, bot2Card1, bot2Card2, bot3Card1, bot3Card2
                    , bot4Card1, bot4Card2, bot5Card1, bot5Card2};

            //Hides turn circles
            Circle[] botTurns = {bot1Turn, bot2Turn, bot3Turn, bot4Turn, bot5Turn};
            for (int i = 0; i < botTurns.length; i++) {
                botTurns[i].setVisible(false);
            }
            playerTurn.setVisible(true);

            //Hides extra bots
            onBotNumberChange();

            //Sets burnt cards value
            ChoiceBoxBruntCards.setValue("No Card");

            //Presets images (remove later)
            for (int i = 0; i < imageViews.length; i++) {
                setImage(imageViews[i]);
            }

            //Add and remove bots
            SpinnerBots.valueProperty().addListener((obs, oldValue, newValue) -> {
                onBotNumberChange();
            });

            startRound.setOnAction(actionEvent -> {
                    if(startRound.isSelected()) {
                        PokerGame game = new PokerGame(this);
                        game.playGame(this);
                        startRound.setSelected(false);
                    }
            });
    }

    private void setImage(ImageView imageView){
        File file = new File("src/main/resources/com/example/sigmacasino/Sprites/PNG-cards-1.3/back_of_card.png");
        System.out.println(file.toURI());
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
    }


    protected void onBotNumberChange() {
        int bots = SpinnerBots.getValue();
        Circle[] circles = {botCircle1,botCircle2,botCircle3,botCircle4,botCircle5};
        Label[] names = {nameBot1,nameBot2,nameBot3,nameBot4,nameBot5};
        Label[] chips = {chipsPlayer,chipsBot1,chipsBot2,chipsBot3,chipsBot4,chipsBot5};
        ImageView[] card1 = {bot1Card1,bot2Card1,bot3Card1,bot4Card1,bot5Card1};
        ImageView[] card2 = {bot1Card2,bot2Card2,bot3Card2,bot4Card2,bot5Card2};

        for(int i=0;i<bots;i++) {
            circles[i].setVisible(true);
            card1[i].setVisible(true);
            card2[i].setVisible(true);
            names[i].setVisible(true);
            chips[i+1].setVisible(true);
        }

        for(int i=bots;i<circles.length;i++)
        {
            circles[i].setVisible(false);
            card1[i].setVisible(false);
            card2[i].setVisible(false);
            names[i].setVisible(false);
            chips[i+1].setVisible(false);
        }

    }

    public void switchToScene(ActionEvent event, String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
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

    public Spinner<Integer> getSpinnerBots(){
        return SpinnerBots;
    }
    public ChoiceBox<String> getChoiceBoxBruntCards(){
        return ChoiceBoxBruntCards;
    }
    public Button getCheckButton(){
        return checkButton;
    }
    public Button getFoldButton(){
        return foldButton;
    }
    public Button getRaiseButton(){
        return raiseButton;
    }
    public TextField getStartingChipsTextArea(){
        return startingChips;
    }
    public TextField getRaiseTextArea(){
        return raiseText;
    }


}