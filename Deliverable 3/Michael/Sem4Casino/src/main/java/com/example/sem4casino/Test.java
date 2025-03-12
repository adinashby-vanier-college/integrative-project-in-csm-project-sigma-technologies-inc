package com.example.sem4casino;
import io.lyuda.jcards.Card;
import io.lyuda.jcards.Deck;
import io.lyuda.jcards.Rank;
import io.lyuda.jcards.Suit;
import io.lyuda.jcards.game.Player;

import java.util.ArrayList;
import java.util.HashMap;


public class Test {
    static int playerAmount = 4;

    public static void main(String[] args) {


        ArrayList<Player> players = new ArrayList<>();
        for(int i=0;i<playerAmount;i++)
        {
            players.add(new Player("Bot"+(i+1)));
        }
        Deck deck = new Deck();
        deck.shuffle();
        for(int j=0;j<playerAmount;j++) {
            for (int i = 0; i < 2; i++) {
                players.get(j).addCard(deck.deal());
            }
        }
        for(int i=0;i<playerAmount;i++) {
            System.out.println(players.get(i).getHand());
        }
    }
}