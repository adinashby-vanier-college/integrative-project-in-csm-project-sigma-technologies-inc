package com.example.sem4casino;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;

import java.util.ArrayList;
import java.util.Arrays;

public enum HandRanks{
    HIGH_CARD(1),
    ONE_PAIR(2),
    TWO_PAIR(3),
    THREE_OF_A_KIND(4),
    STRAIGHT(5),
    FLUSH(6),
    FULL_HOUSE(7),
    FOUR_OF_A_KIND(8),
    STRAIGHT_FLUSH(9),
    ROYAL_FLUSH(10);

    private final int value;

    HandRanks(int value) {
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

    public static HandRanks calculateRank(Card[] hand){
        ArrayList<Rank> cardNames = new ArrayList<>(Arrays.asList(
                Rank.ACE, Rank.KING, Rank.QUEEN, Rank.JACK, Rank.TEN,
                Rank.NINE, Rank.EIGHT, Rank.SEVEN, Rank.SIX, Rank.FIVE,
                Rank.FOUR, Rank.THREE, Rank.TWO
        ));
        int[] cardNamesCount = new int[13];
        ArrayList<Suit> suits = new ArrayList<>(Arrays.asList(Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS, Suit.SPADES));
        int[] suitsCount = new int[4];
        HandRanks rank = HIGH_CARD;
        int pairs =0;
        int threeOfAKind = 0;

        //Sorts Cards
        for(int i=0;i<hand.length;i++) {
            cardNamesCount[cardNames.indexOf(hand[i].getRank())]++;
        }

        for(int i=0;i<cardNamesCount.length;i++){
            switch (cardNamesCount[i]){
                case 2: pairs++;break;
                case 3: threeOfAKind++;break;
                case 4: rank = FOUR_OF_A_KIND;break;
            }
        }
        if(pairs==1){
            rank = ONE_PAIR;
        } else if (pairs==2 && threeOfAKind==1) {
            rank = FULL_HOUSE;
        } else if(pairs==2){
            rank = TWO_PAIR;
        } else if (threeOfAKind==1) {
            rank = THREE_OF_A_KIND;
        }
        return rank;
    }
}
