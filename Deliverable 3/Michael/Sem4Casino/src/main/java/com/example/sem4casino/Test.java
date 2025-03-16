package com.example.sem4casino;
import io.lyuda.jcards.Card;
import io.lyuda.jcards.Deck;
import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import io.lyuda.jcards.game.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;



public class Test {
    static int playerAmount = 4;

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        Deck deck = new Deck();
        deck.shuffle();

        ArrayList<Card> cards = new ArrayList<Card>();
        for(int i=0;i<7;i++)
        {
            cards.add(deck.deal());
        }
        System.out.println(cards);
        System.out.println(bestHand(cards));

        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Execution time: " + duration / 1_000_000.0 + " ms");
//        ArrayList<Player> players = new ArrayList<>();
//        for(int i=0;i<playerAmount;i++)
//        {
//            players.add(new Player("Bot"+(i+1)));
//        }
//        Deck deck = new Deck();
//        deck.shuffle();
//        for(int j=0;j<playerAmount;j++) {
//            for (int i = 0; i < 2; i++) {
//                players.get(j).addCard(deck.deal());
//            }
//        }
//        for(int i=0;i<playerAmount;i++) {
//            System.out.println(players.get(i).getHand());
//        }
    }

   public static float bestHand(ArrayList<Card> allCards){
        Card[] hand = new Card[5];
        float bestRank = HandRanks.HIGH_CARD.getValue();

        //length of total Cards (7)
        int n = 7;

        //All possible hands P(7,5)
        for (int i = 0; i < n - 4; i++) {
            for (int j = i + 1; j < n - 3; j++) {
                for (int k = j + 1; k < n - 2; k++) {
                    for (int l = k + 1; l < n - 1; l++) {
                        for (int m = l + 1; m < n; m++) {
                            hand[0]=allCards.get(i);
                            hand[1]=allCards.get(j);
                            hand[2]=allCards.get(k);
                            hand[3]=allCards.get(l);
                            hand[4]=allCards.get(m);
                            float calRank = HandRanks.calculateRank(hand);
                            if(calRank > bestRank){
                                bestRank = calRank;
                            }
                        }
                    }
                }
            }
        }
        return bestRank;
   }

}

