package com.example.sigmacasino.Blackjack.gameLogic;
import com.example.sigmacasino.Blackjack.gameLogic.BlackJackCard.BRank;
import com.example.sigmacasino.Blackjack.gameLogic.BlackJackCard.BSuit;
import com.example.sigmacasino.Calculator.CryptoRandom;
public class Deck {

    private final BlackJackCard[] cards = new BlackJackCard[52];
    public Deck(){
        refill();
    }

    public final void refill() {
        int i = 0;
        for( BSuit suit : BSuit.values()){
            for(BRank rank : BRank.values()){
                cards[i++] = new BlackJackCard(suit, rank);
            }
        }
    }
    public BlackJackCard drawCard(){
        BlackJackCard card = null;
        while(card == null){
            int index = (int)(CryptoRandom.GenerateRandomRangeFloat(0,0.999f)*cards.length);
            card = cards[index];
            cards[index] = null;
        }
    return card;}
}
