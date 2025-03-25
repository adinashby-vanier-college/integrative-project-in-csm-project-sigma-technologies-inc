package com.example.sigmacasino.Blackjack.gameLogic;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.File;
import java.util.Stack;

public class BlackJackCard extends Parent {


    enum BSuit{
        HEARTS, DIAMONDS, CLUBS, SPADES

    };
    enum BRank{
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);
        final int value;
        private BRank(int value){
            this.value = value;

        };

    }
    public final BSuit Bsuit;
    public final BRank Brank;
    public final int value;
    public BlackJackCard(BSuit suit, BRank rank){
        this.Bsuit = suit;
        this.Brank = rank;
        this.value = rank.value;
        Rectangle cardView = new Rectangle(68, 99);

        cardView.setFill(Color.WHITE);
        String imagePath = "src/main/resources/com/example/sigmacasino/Sprites/PNG-cards-1.3/" + rank + "_of_" + suit + ".png";
        File file = new File(imagePath);
        cardView.setFill(new ImagePattern(new Image(file.toURI().toString())));
        //Text text = new Text(toString());
        //text.setWrappingWidth(60);
        getChildren().add(new StackPane(cardView));
    }

    public String toString(){
        return Brank.toString() + " of " + Bsuit.toString();
    }
}
