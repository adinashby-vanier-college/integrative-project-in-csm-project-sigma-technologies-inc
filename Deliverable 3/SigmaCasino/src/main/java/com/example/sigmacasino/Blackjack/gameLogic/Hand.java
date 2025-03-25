package com.example.sigmacasino.Blackjack.gameLogic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class Hand {

    private ObservableList<Node> cards;
    private SimpleIntegerProperty value = new SimpleIntegerProperty(0);
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
