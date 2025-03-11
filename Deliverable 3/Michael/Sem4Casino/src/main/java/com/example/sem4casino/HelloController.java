package com.example.sem4casino;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

import java.io.File;

public class HelloController {
    @FXML
    private Label welcomeText;

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


    @FXML
    public void initialize(){
        ImageView[] imageViews = {best_H1_1,best_H1_2,best_H1_3,best_H1_4,best_H1_5,best_H2_1,best_H2_2
                ,best_H2_3,best_H2_4,best_H2_5,riverCard1,riverCard2,riverCard3,riverCard4,riverCard5
                ,playerCard1,playerCard2, bot1Card1, bot1Card2, bot2Card1, bot2Card2, bot3Card1, bot3Card2
                ,bot4Card1, bot4Card2, bot5Card1, bot5Card2};

        String[] files = {"PNG-cards-1.3/2_of_clubs.png","PNG-cards-1.3/3_of_diamonds.png","PNG-cards-1.3/4_of_hearts.png"
                ,"PNG-cards-1.3/5_of_spades.png","PNG-cards-1.3/6_of_clubs.png","PNG-cards-1.3/7_of_diamonds.png","PNG-cards-1.3/8_of_hearts.png"
                ,"PNG-cards-1.3/9_of_spades.png","PNG-cards-1.3/10_of_clubs.png","PNG-cards-1.3/jack_of_diamonds.png","PNG-cards-1.3/2_of_clubs.png"
                ,"PNG-cards-1.3/3_of_diamonds.png","PNG-cards-1.3/4_of_hearts.png","PNG-cards-1.3/5_of_spades.png","PNG-cards-1.3/6_of_clubs.png"
                ,"PNG-cards-1.3/5_of_spades.png","PNG-cards-1.3/6_of_clubs.png","PNG-cards-1.3/7_of_diamonds.png","PNG-cards-1.3/8_of_hearts.png"
                ,"PNG-cards-1.3/9_of_spades.png","PNG-cards-1.3/10_of_clubs.png","PNG-cards-1.3/jack_of_diamonds.png","PNG-cards-1.3/2_of_clubs.png"
                ,"PNG-cards-1.3/3_of_diamonds.png","PNG-cards-1.3/4_of_hearts.png","PNG-cards-1.3/5_of_spades.png","PNG-cards-1.3/6_of_clubs.png"};

        for(int i=0;i< imageViews.length;i++)
        {
            setImage(imageViews[i],files[i]);
        }

        borderPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.widthProperty().addListener((obsW, oldW, newW) -> setTable(newScene));
                newScene.heightProperty().addListener((obsH, oldH, newH) -> setTable(newScene));
            }
        });
        setPlayers();
    }

    private void setImage(ImageView imageView, String filePath){
        File file = new File(filePath);
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }

    private void setTable(Scene scene){
        brownEllipse.setLayoutX((scene.getWidth()/2)-(borderPane.getLeft().getBoundsInParent().getWidth()));
        brownEllipse.setLayoutY(scene.getHeight()/3);
        greenEllipse.setLayoutX((scene.getWidth()/2)-(borderPane.getLeft().getBoundsInParent().getWidth()));
        greenEllipse.setLayoutY(scene.getHeight()/3);

        ImageView[] imageViews = {riverCard1,riverCard2,riverCard3,riverCard4,riverCard5};
        double displaceX = 0;

        for(int i=0;i<imageViews.length;i++) {
            imageViews[i].setLayoutX((scene.getWidth()/2)-(borderPane.getLeft().getBoundsInParent().getWidth())-135+displaceX);
            imageViews[i].setLayoutY((scene.getHeight()/3)-35);
            displaceX = displaceX+55;
        }

    }

    private void setPlayers(){
        double centerX = (greenEllipse.getLayoutX()*2)-33;
        double centerY = (greenEllipse.getLayoutY()*3)+65;
        double displaceX = greenEllipse.getRadiusX()-70;
        double displaceY = (greenEllipse.getLayoutY()/2)-15;;
        Circle[] botCircles = {botCircle2, botCircle3, botCircle4, botCircle5};

        playerCircle.setLayoutX(centerX);
        playerCircle.setLayoutY(centerY);

        centerY = (greenEllipse.getLayoutY()/2)-30;

        botCircle1.setLayoutX(centerX);
        botCircle1.setLayoutY(centerY);

        for(int i=0;i<4;i++){
            if(i<botCircles.length/2) {
                botCircles[i].setLayoutX(centerX+displaceX);
                botCircles[i].setLayoutY(centerY+displaceY);
                displaceY=displaceY+(greenEllipse.getLayoutY()+195);
                if(i == 1)
                {
                    displaceY=(greenEllipse.getLayoutY()/2)-15;
                }
            }
            else{
                botCircles[i].setLayoutX(centerX-displaceX);
                botCircles[i].setLayoutY(centerY+displaceY);
                displaceY=displaceY+(greenEllipse.getLayoutY()+195);
            }
        }
    }

    private void setPlayerCards(){
        ImageView[] cards = {playerCard1,playerCard2, bot1Card1, bot1Card2, bot2Card1, bot2Card2, bot3Card1, bot3Card2,bot4Card1, bot4Card2, bot5Card1, bot5Card2};
        cards[0].setLayoutX();
        cards[0].setLayoutY();
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}