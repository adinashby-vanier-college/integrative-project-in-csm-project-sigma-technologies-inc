package com.example.sigmacasino.Poker;
import com.example.sigmacasino.Calculator.Calculator;
import io.lyuda.jcards.Card;
import io.lyuda.jcards.Deck;

import java.util.*;
import java.util.concurrent.*;

public class PokerCalculator extends Calculator {
    private static final int INITIAL_TRIALS = 50_000;  // Start with this number
    private static final int MAX_TIME_MILLIS = 1_000; // 15 seconds
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    private List<Card> playerHand;
    private List<Card> communityCards;
    private ArrayList<Card> discardCards = new ArrayList<>();
    private int numOpponents;

    public PokerCalculator(List<Card> playerHand, List<Card> communityCards, int numOpponents) {
        discardCards.add(playerHand.get(0));
        discardCards.add(playerHand.get(1));
        this.playerHand = playerHand;
        this.communityCards = communityCards;
        this.numOpponents = numOpponents;
    }

    double runSimulation() {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        long startTime = System.currentTimeMillis();

        int totalTrials = 0;  // Track actual trials
        int wins = 0;

        while (System.currentTimeMillis() - startTime < MAX_TIME_MILLIS) {
            List<Future<Integer>> futures = new ArrayList<>();
            int trialsPerThread = INITIAL_TRIALS / NUM_THREADS;

            // Submit parallel tasks
            for (int i = 0; i < NUM_THREADS; i++) {
                futures.add(executor.submit(() -> simulateGames(trialsPerThread)));
            }

            // Collect results
            for (Future<Integer> future : futures) {
                try {
                    int result = future.get();
                    wins += result;
                    totalTrials += trialsPerThread;  // Track real trials
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        executor.shutdown();
        double winRate = (double) wins / totalTrials;
        System.out.println("Total Trials Run: " + totalTrials);
        return winRate;
    }


    private int simulateGames(int numTrials) {
        int wins = 0;

        for (int i = 0; i < numTrials; i++) {
            Deck deck = new Deck();  // Create a fresh deck for every trial
            deck.shuffle();

            ArrayList<Card> fullCommunity = new ArrayList<>(communityCards);
            while (fullCommunity.size() < 5) {
                if (deck.deal()==null) break; // Prevent out-of-cards error
                Card card = deck.deal();
                while (discardCards.contains(card) && !(deck.deal()==null)) {
                    card = deck.deal();
                }
                fullCommunity.add(card);
            }

            ArrayList<List<Card>> opponentHands = new ArrayList<>();
            for (int j = 0; j < numOpponents; j++) {
                if (deck.deal() == null) break; // Prevent over-dealing
                opponentHands.add(makeHand(deck));
            }

            float playerRank = HandRanks.bestHand(new ArrayList<>(playerHand) {{ addAll(fullCommunity); }});
            float bestOpponentRank = 0;

            for (List<Card> oppHand : opponentHands) {
                bestOpponentRank = Math.max(bestOpponentRank, HandRanks.bestHand(new ArrayList<>(oppHand) {{ addAll(fullCommunity); }}));
            }

            if (playerRank > bestOpponentRank) {
                wins++;
            }
        }

        return wins;
    }

    private ArrayList<Card> makeHand(Deck deck){
        ArrayList<Card> hand = new ArrayList<>();
        for(int i=0;i<2;i++) {
            Card card = deck.deal();
            for (int w = 0; w < 2; w++) {
                if (discardCards.contains(card)) {
                    card = deck.deal();
                }
            }
            hand.add(card);
        }
        return hand;
    }

//    public static void main(String[] args) {
//        System.out.println("Number of Threads: "+NUM_THREADS);
//        for(int i=0;i<1;i++) {
//            long startTime = System.currentTimeMillis();
//            Deck deck = new Deck();
//            deck.shuffle();
//            List<Card> playerHand = Arrays.asList(deck.deal(), deck.deal());
//            List<Card> communityCards = Arrays.asList(deck.deal(), deck.deal(), deck.deal(),deck.deal(),deck.deal());
//
//            System.out.println("Player Hand: " + playerHand);
//            System.out.println("Community Cards: " + communityCards);
//
//            PokerCalculator simulator = new PokerCalculator(playerHand, communityCards, 5);
//            double winRate = simulator.runSimulation();
//            System.out.println("Estimated Win Probability: " + (winRate * 100) + "%");
//            long endTime = System.currentTimeMillis();  // End timing
//            double elapsedTime = (endTime - startTime) / 1000.0;  // Convert to seconds
//            System.out.println("Execution Time: " + elapsedTime + " seconds\n");  // Print execution time
//        }
//    }
}
