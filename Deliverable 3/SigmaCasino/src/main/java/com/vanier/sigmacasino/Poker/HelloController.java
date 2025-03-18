package com.vanier.sigmacasino.Poker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML public Spinner SpinnerBots;
    @FXML public ChoiceBox ChoiceBoxBruntCards;
    @FXML public Circle bot2Turn;
    @FXML public Circle bot1Turn;
    @FXML public Circle bot4Turn;
    @FXML public Circle bot5Turn;
    @FXML public Circle playerTurn;
    @FXML public Circle bot3Turn;


    @FXML private BorderPane borderPane;

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


    @FXML
    public void initialize() throws IOException {
            ImageView[] imageViews = {best_H1_1, best_H1_2, best_H1_3, best_H1_4, best_H1_5, best_H2_1, best_H2_2
                    , best_H2_3, best_H2_4, best_H2_5, riverCard1, riverCard2, riverCard3, riverCard4, riverCard5
                    , playerCard1, playerCard2, bot1Card1, bot1Card2, bot2Card1, bot2Card2, bot3Card1, bot3Card2
                    , bot4Card1, bot4Card2, bot5Card1, bot5Card2};

            String[] files = {"PNG-cards-1.3/2_of_clubs.png", "PNG-cards-1.3/3_of_diamonds.png", "PNG-cards-1.3/4_of_hearts.png"
                    , "PNG-cards-1.3/5_of_spades.png", "PNG-cards-1.3/6_of_clubs.png", "PNG-cards-1.3/7_of_diamonds.png", "PNG-cards-1.3/8_of_hearts.png"
                    , "PNG-cards-1.3/9_of_spades.png", "PNG-cards-1.3/10_of_clubs.png", "PNG-cards-1.3/jack_of_diamonds.png", "PNG-cards-1.3/2_of_clubs.png"
                    , "PNG-cards-1.3/3_of_diamonds.png", "PNG-cards-1.3/4_of_hearts.png", "PNG-cards-1.3/5_of_spades.png", "PNG-cards-1.3/6_of_clubs.png"
                    , "PNG-cards-1.3/5_of_spades.png", "PNG-cards-1.3/6_of_clubs.png", "PNG-cards-1.3/7_of_diamonds.png", "PNG-cards-1.3/8_of_hearts.png"
                    , "PNG-cards-1.3/9_of_spades.png", "PNG-cards-1.3/10_of_clubs.png", "PNG-cards-1.3/jack_of_diamonds.png", "PNG-cards-1.3/2_of_clubs.png"
                    , "PNG-cards-1.3/3_of_diamonds.png", "PNG-cards-1.3/4_of_hearts.png", "PNG-cards-1.3/5_of_spades.png", "PNG-cards-1.3/6_of_clubs.png"};

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
                setImage(imageViews[i], files[i]);
            }

            //Add and remove bots
            SpinnerBots.valueProperty().addListener((obs, oldValue, newValue) -> {
                onBotNumberChange();
            });




            //Remove later (Testing)
            menuQuit.setOnAction(event -> {
                try {
                    switchToScene(event, "poker-view.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });



    }

    private void setImage(ImageView imageView, String filePath){
        System.out.println("In");
        File file = new File("Deliverable 3/Michael/Sem4Casino/" +filePath);
        System.out.println(file.toURI());
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }

    @FXML
    protected void onBotNumberChange() {
        int bots = (int) SpinnerBots.getValue();
        Circle[] circles = {botCircle1,botCircle2,botCircle3,botCircle4,botCircle5};
        ImageView[] card1 = {bot1Card1,bot2Card1,bot3Card1,bot4Card1,bot5Card1};
        ImageView[] card2 = {bot1Card2,bot2Card2,bot3Card2,bot4Card2,bot5Card2};

        for(int i=0;i<bots;i++) {
            circles[i].setVisible(true);
            card1[i].setVisible(true);
            card2[i].setVisible(true);
        }

        for(int i=bots;i<circles.length;i++)
        {
            circles[i].setVisible(false);
            card1[i].setVisible(false);
            card2[i].setVisible(false);
        }

    }

    public void switchToScene(ActionEvent event, String fxmlFile) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxmlFile));
        if (event.getSource() instanceof Node) {
            // If the event source is a Node (ex: Button)
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } else {
            // If the event source is not a Node (ex:MenuItem)
            stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        }
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}