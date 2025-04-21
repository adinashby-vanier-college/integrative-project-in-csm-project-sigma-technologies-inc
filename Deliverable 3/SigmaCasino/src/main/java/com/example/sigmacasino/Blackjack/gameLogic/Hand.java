package com.example.sigmacasino.Blackjack.gameLogic;

import javafx.animation.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.util.Duration;

public class Hand {

    private final ObservableList<Node> cards;
    private final SimpleIntegerProperty value = new SimpleIntegerProperty(0);
    private int numOfAces = 0;
    public Hand(ObservableList<Node> cards){
        this.cards = cards;
    }
    public void takeCard(BlackJackCard card){
        cards.add(card);
        if(card.Brank == BlackJackCard.BRank.ACE){
            numOfAces++;
        }
        if(value.get() + card.value > 21 && numOfAces > 0){
            value.set(value.get() + card.value - 10);
            numOfAces--;
        }
        else{
            value.set(value.get() + card.value);
        }
        animateCard(card);
    }
    public BlackJackCard getUpCard() {
        if (cards.size() > 0 && cards.get(0) instanceof BlackJackCard) {
            return (BlackJackCard) cards.get(0);
        }
        return null;
    }

    private void animateCard(BlackJackCard card) {
        card.setTranslateX(-200);
        card.setTranslateY(-200);
        card.setOpacity(0);

        TranslateTransition slide = new TranslateTransition(Duration.millis(400), card);
        slide.setToX(0);
        slide.setToY(0);
        slide.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition fade = new FadeTransition(Duration.millis(400), card);
        fade.setFromValue(0);
        fade.setToValue(1);

        RotateTransition rotate = new RotateTransition(Duration.millis(250), card);
        rotate.setByAngle(7);

        ParallelTransition animation = new ParallelTransition(slide, fade, rotate);
        animation.play();
    }


    public void reset(){
    cards.clear();
    value.set(0);
    numOfAces = 0;
    }
    public SimpleIntegerProperty valueProperty(){
        return value;
    }


}
