package com.example.sigmacasino.Poker;
import com.example.sigmacasino.Calculator.Calculator;
import io.lyuda.jcards.Card;
import io.lyuda.jcards.Deck;

import java.util.*;
import java.util.Arrays;
import java.util.concurrent.*;

public class PokerCalculator extends Calculator {
    private static final int INITIAL_TRIALS = 50_000;  // Start with this number of trials
    private static final int MAX_TIME_MILLIS = 1_000; // 1 seconds
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

    static int[] getMoveDecision(int winRate, double currentBet, double potSize, double playerInvestment) {
        double callCost = currentBet - playerInvestment; // Adjust for what the player has already invested
        if (callCost < 0) callCost = 0; // Prevent negative values
        double effectivePotSize = potSize + callCost;
        double potOdds = callCost / effectivePotSize;

        int checkProbability = 0;
        int foldProbability = 0;
        int raiseProbability = 0;

        if (winRate > potOdds + 0.1) {
            // Strong hand, should raise
            raiseProbability = (int) (winRate * 100);
            checkProbability = 100 - raiseProbability;
        } else if (winRate > potOdds) {
            // Marginal hand, should check if possible, call if needed
            checkProbability = (int) ((1 - potOdds) * 100);
            foldProbability = 100 - checkProbability;
        } else {
            // Weak hand, should fold
            foldProbability = 100;
        }
        System.out.println(Arrays.toString(new int[]{checkProbability, foldProbability, raiseProbability}));
        return new int[]{checkProbability, foldProbability, raiseProbability};
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
}
