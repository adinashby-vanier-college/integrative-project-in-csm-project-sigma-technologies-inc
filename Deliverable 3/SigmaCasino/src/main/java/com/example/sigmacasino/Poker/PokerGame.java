package com.example.sigmacasino.Poker;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.Deck;
import io.lyuda.jcards.game.Player;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PokerGame {
    private final ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Integer> playerChips = new ArrayList<>();
    private final ArrayList<Card> riverCards = new ArrayList<>();
    private final ArrayList<Float> playerRanks = new ArrayList<>();
    private final ArrayList<String> playerRankNames = new ArrayList<>();
    private Deck deck;
    private int potSize;
    private int dealerIndex;
    private int smallBlindIndex;
    private int bigBlindIndex;
    private int burnCards;
    private int botAmount;

    public PokerGame(PokerController controller){
        botAmount=controller.getSpinnerBots().getValue();
        String burnAmount=controller.getChoiceBoxBruntCards().getValue();
        switch(burnAmount)
        {
            case "No Burn Cards": burnCards=0;break;
            case "1 Burn Card": burnCards=1;break;
            case "2 Burn Cards": burnCards=2;break;
        }
        players.add(new Player("Player"));
        if(botAmount==4)
        {
            players.add(new Player("Bot "+botAmount));
            botAmount=3;

        }
        else if (botAmount==5)
        {
            players.add(new Player("Bot "+botAmount));
            players.add(new Player("Bot "+(botAmount-1)));
            botAmount=3;
        }
        for(int i=1;i<=botAmount;i++)
        {
            players.add(new Player("Bot "+i));
        }

        dealerIndex=0;
        if (botAmount<2)
        {
            smallBlindIndex=0;
            bigBlindIndex=1;
        }
        else{
            smallBlindIndex=1;
            bigBlindIndex=2;
        }
    }

    public void playGame(PokerController controller){

        //Gives player Cards
        dealCards();

        //First Round of Betting
        playerBet(controller);

        //First River Flop
        dealFlop();

        //Second Round of Betting
        playerBet(controller);

        //Second River Flop
        dealPostFlop();

        //Third Round of Betting
        playerBet(controller);

        //Third River Flop
        dealPostFlop();

        //Fourth Round of Betting
        playerBet(controller);

        //Check Cards Ranks
        rankings();
        playerRankNames();
    }

    private void dealCards(){
        deck = new Deck();
        deck.shuffle();
        for(int j=0;j<2;j++) {
            for (Player player : players) {
                player.addCard(deck.deal());
            }
        }
        for(int i=0;i<players.size();i++)
        {
            System.out.println(players.get(i).getName()+": "+players.get(i).getHand());
        }
    }

    private void playerBet(PokerController controller){
        try {
            boolean flag = false;
            while (flag) {
                System.out.println(flag);
                // Wait for user choice
                CompletableFuture<Boolean> userChoice = waitForUserChoice(controller.getCheckButton(), controller.getFoldButton(), controller.getRaiseButton());
                flag = userChoice.get();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CompletableFuture<Boolean> waitForUserChoice(Button checkButton, Button foldButton, Button raiseButton) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        checkButton.setOnAction(e -> future.complete(true));
        foldButton.setOnAction(e -> future.complete(true));
        raiseButton.setOnAction(e -> future.complete(true));

        return future;
    }

    private void dealFlop() {
        riverCards.clear();
        for(int i=0;i<3;i++)
        {
            riverCards.add(deck.deal());
            if(burnCards>0)
            {
                for(int j=0;j<burnCards;j++)
                {
                    deck.deal();
                }
            }
        }
    }

    private void dealPostFlop() {
        riverCards.add(deck.deal());
        if (burnCards > 0) {
            for (int j = 0; j < burnCards; j++) {
                deck.deal();
            }
        }
    }

    private void rankings() {
        System.out.println("\n"+riverCards);
        for(int i=0;i<players.size();i++)
        {
            playerRanks.add(bestHand(new ArrayList<Card>(List.of(new Card[]{players.get(i).getHand().getCards().getFirst(), players.get(i).getHand().getCards().getLast(), riverCards.get(0), riverCards.get(1), riverCards.get(2), riverCards.get(3), riverCards.get(4)}))));
        }
        System.out.println("\n"+playerRanks);
    }

    private static float bestHand(ArrayList<Card> allCards) {
        Card[] hand = new Card[5];
        float bestRank = HandRanks.HIGH_CARD.getValue();

        // Length of total Cards (7)
        int n = 7;

        // Alpha-Beta-like Pruning in this context
        // Alpha: Best rank found so far
        // Beta: We would only proceed if we find a better hand than the current best
        float alpha = bestRank;

        // All possible hands P(7,5)
        for (int i = 0; i < n - 4; i++) {

            // We can prune this branch if we already have a better hand
            if (bestRank > alpha) break;

            for (int j = i + 1; j < n - 3; j++) {
                if (bestRank > alpha) break;

                for (int k = j + 1; k < n - 2; k++) {
                    if (bestRank > alpha) break;

                    for (int l = k + 1; l < n - 1; l++) {
                        if (bestRank > alpha) break;

                        for (int m = l + 1; m < n; m++) {
                            hand[0] = allCards.get(i);
                            hand[1] = allCards.get(j);
                            hand[2] = allCards.get(k);
                            hand[3] = allCards.get(l);
                            hand[4] = allCards.get(m);

                            // Evaluate the hand rank
                            float calRank = HandRanks.calculateRank(hand);

                            // Prune if the current hand is not better
                            if (calRank > bestRank) {
                                bestRank = calRank;
                                alpha = bestRank;  // Update alpha to the new best rank
                            }
                        }
                    }
                }
            }
        }
        return bestRank;
    }

    private void playerRankNames() {
        for (Float playerRank : playerRanks) {
            switch (playerRank.intValue()) {
                case 10 -> playerRankNames.add("Royal Flush");
                case 9 -> playerRankNames.add("Straight Flush");
                case 8 -> playerRankNames.add("Four of a Kind");
                case 7 -> playerRankNames.add("Full House");
                case 6 -> playerRankNames.add("Flush");
                case 5 -> playerRankNames.add("Straight");
                case 4 -> playerRankNames.add("Three of a Kind");
                case 3 -> playerRankNames.add("Two Pair");
                case 2 -> playerRankNames.add("One Pair");
                case 1 -> playerRankNames.add("High Card");
            }
        }
        System.out.println("\n"+playerRankNames);
    }

    public ArrayList<Integer> getPlayerChips() {
        return playerChips;
    }

    public void setPlayerChips(ArrayList<Integer> playerChips) {
        this.playerChips = playerChips;
    }
}
