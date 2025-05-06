package com.example.sigmacasino.Poker;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import io.lyuda.jcards.game.Player;

import java.util.ArrayList;

public class Test {

    static ArrayList<Card> playerHand1;
    static ArrayList<Card> playerHand2;
    static ArrayList<Card> playerHand3;
    static ArrayList<Card> communityCards;

    public static void main(String[] args) {
        new Test();
        sampleIndividualRank();
        sampleFullHandRank();
        sampleWinRate();
    }

    public Test(){
        playerHand1 = new ArrayList<>();
        playerHand2 = new ArrayList<>();
        playerHand3 = new ArrayList<>();

        communityCards = new ArrayList<>();

        playerHand1.add(new Card(Rank.SEVEN, Suit.HEARTS));
        playerHand1.add(new Card(Rank.NINE, Suit.CLUBS));

        playerHand2.add(new Card(Rank.JACK, Suit.SPADES));
        playerHand2.add(new Card(Rank.TWO, Suit.DIAMONDS));

        playerHand3.add(new Card(Rank.ACE, Suit.SPADES));
        playerHand3.add(new Card(Rank.ACE, Suit.CLUBS));

        communityCards.add(new Card(Rank.ACE, Suit.HEARTS));
        communityCards.add(new Card(Rank.TEN, Suit.SPADES));
        communityCards.add(new Card(Rank.TEN, Suit.CLUBS));
        communityCards.add(new Card(Rank.SEVEN, Suit.DIAMONDS));
        communityCards.add(new Card(Rank.KING, Suit.CLUBS));
    }

    //Hand rank testing
    //Individual ranks
    static void sampleIndividualRank(){
        System.out.println("Individual Player Rank Testing:\n");
        Player playerHand1 = new Player("Player 1");
        Player playerHand2 = new Player("Player 2");
        Player playerHand3 = new Player("Player 3");

        playerHand1.addCard(new Card(Rank.ACE, Suit.DIAMONDS));
        playerHand1.addCard(new Card(Rank.ACE,Suit.HEARTS));

        playerHand2.addCard(new Card(Rank.TWO, Suit.CLUBS));
        playerHand2.addCard(new Card(Rank.EIGHT,Suit.SPADES));

        playerHand3.addCard(new Card(Rank.KING, Suit.DIAMONDS));
        playerHand3.addCard(new Card(Rank.ACE,Suit.HEARTS));

        System.out.println("Player 1 Hand: "+playerHand1.getHand().toString());
        System.out.println("Player 2 Hand: "+playerHand2.getHand().toString());
        System.out.println("Player 3 Hand: "+playerHand3.getHand().toString());

        System.out.println("\nPlayer 1: "+HandRanks.getIndividualHandRank(playerHand1)+"\nExpected: 1313");
        System.out.println("\nPlayer 2: "+HandRanks.getIndividualHandRank(playerHand2)+"\nExpected: 701");
        System.out.println("\nPlayer 3: "+HandRanks.getIndividualHandRank(playerHand3)+"\nExpected: 1312\n");

    }

    //Full Hand Ranking
    static void sampleFullHandRank(){
        System.out.println("\nFinal Player Rank Testing:\n");


        System.out.println("Player 1 Hand: "+playerHand1);
        System.out.println("Player 2 Hand: "+playerHand2);
        System.out.println("Player 3 Hand: "+playerHand3);
        System.out.println("\nCommunity Cards: "+communityCards);

        ArrayList<Card> hand = new ArrayList<>();

        hand.addAll(playerHand1);
        hand.addAll(communityCards);
        System.out.println("\nPlayer 1: "+HandRanks.bestHand(hand)+"\nExpected: 3.0906");
        hand.clear();

        hand.addAll(playerHand2);
        hand.addAll(communityCards);
        System.out.println("\nPlayer 2: "+HandRanks.bestHand(hand)+"\nExpected: 2.09");
        hand.clear();

        hand.addAll(playerHand3);
        hand.addAll(communityCards);
        System.out.println("\nPlayer 3: "+HandRanks.bestHand(hand)+"\nExpected: 7.1309");
        hand.clear();
    }

    //Sample win rates
    static void sampleWinRate(){
        System.out.println("\nWin Rate Testing:");
       PokerCalculator simulator1 = new PokerCalculator(playerHand1,communityCards.subList(0,2),3);
       PokerCalculator simulator2 = new PokerCalculator(playerHand2,communityCards.subList(0,3),3);
       PokerCalculator simulator3 = new PokerCalculator(playerHand3,communityCards.subList(0,4),3);
       int w1 = (int) (simulator1.runSimulation()*100);
       int w2 = (int) (simulator2.runSimulation()*100);
       int w3 = (int) (simulator3.runSimulation()*100);

       System.out.println("Win rate player 1 (3 community cards): "+w1+"%"); //Low
       System.out.println("Win rate player 2 (4 community cards): "+w2+"%"); //Very Low
       System.out.println("Win rate player 3 (5 community cards): "+w3+"%"); //Very High

    }
}
