package com.example.sigmacasino.Poker;

import com.example.sigmacasino.Calculator.Calculator;
import io.lyuda.jcards.Card;
import io.lyuda.jcards.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PokerCalculator extends Calculator {
    private static final int INITIAL_TRIALS = 50_000;  // Start with this number of trials
    private static final int MAX_TIME_MILLIS = 1_000; // 1 seconds
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    private final List<Card> playerHand;
    private final List<Card> communityCards;
    private final ArrayList<Card> discardCards = new ArrayList<>();
    private final int numOpponents;

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

    static int[] getMoveDecision(double winRatePercent, double currentBet, double potSize, double playerInvestment) {
        System.out.println("\n=== NEW DECISION CALCULATION ===");
        System.out.printf("Input params - WinRate: %.2f%%, CurrentBet: %.1f, PotSize: %.1f, PlayerInvestment: %.1f\n",
                winRatePercent, currentBet, potSize, playerInvestment);

        // Convert win rate percentage to decimal (0-1)
        double winRate = Math.max(0, Math.min(100, winRatePercent)) / 100.0;
        System.out.printf("Converted WinRate: %.2f\n", winRate);

        // Calculate pot odds
        double callCost = Math.max(currentBet - playerInvestment, 0);
        double effectivePotSize = potSize + callCost;
        double potOdds = (callCost == 0) ? 0 : callCost / effectivePotSize;
        System.out.printf("Calculated - CallCost: %.1f, EffectivePot: %.1f, PotOdds: %.2f\n",
                callCost, effectivePotSize, potOdds);

        // Base probabilities
        int checkProb = 0;
        int foldProb = 0;
        int raiseProb = 0;
        String decisionCase = "";

        // Decision logic
        if (callCost == 0) {
            decisionCase = "NO BET TO CALL";
            System.out.println("Case: " + decisionCase);

            // More likely to check with lower win rate
            checkProb = 60 + (int)((1 - winRate) * 30);  // 60-90% check
            raiseProb = 100 - checkProb;
            System.out.printf("Initial probs - Check: %d, Raise: %d\n", checkProb, raiseProb);

            // Ensure one is always at least 10% higher
            if (Math.abs(checkProb - raiseProb) < 10) {
                System.out.println("Adjusting for minimum 10% difference");
                if (winRate > 0.5) {
                    raiseProb += 10;
                    checkProb -= 10;
                } else {
                    checkProb += 10;
                    raiseProb -= 10;
                }
                System.out.printf("Adjusted probs - Check: %d, Raise: %d\n", checkProb, raiseProb);
            }
        } else {
            // There is a bet to call
            if (winRate < potOdds - 0.15) {
                decisionCase = "VERY UNFAVORABLE";
                System.out.println("Case: " + decisionCase);

                foldProb = 70 + (int)(Math.random() * 20);  // 70-90% fold
                checkProb = (int)((1 - winRate) * (100 - foldProb) * 0.7);
                raiseProb = 100 - foldProb - checkProb;
                System.out.printf("Initial probs - Check: %d, Fold: %d, Raise: %d\n", checkProb, foldProb, raiseProb);

            } else if (winRate < potOdds) {
                decisionCase = "SLIGHTLY UNFAVORABLE";
                System.out.println("Case: " + decisionCase);

                foldProb = 30 + (int)(Math.random() * 30);  // 30-60% fold
                checkProb = (int)((winRate * 0.7) * (100 - foldProb));
                raiseProb = 100 - foldProb - checkProb;
                System.out.printf("Initial probs - Check: %d, Fold: %d, Raise: %d\n", checkProb, foldProb, raiseProb);

            } else if (winRate < potOdds + 0.2) {
                decisionCase = "MARGINAL SITUATION";
                System.out.println("Case: " + decisionCase);

                checkProb = 60 + (int)(Math.random() * 30);  // 60-90% check
                raiseProb = (int)((winRate - potOdds) * (100 - checkProb) * 2);
                foldProb = 100 - checkProb - raiseProb;
                System.out.printf("Initial probs - Check: %d, Fold: %d, Raise: %d\n", checkProb, foldProb, raiseProb);

            } else {
                decisionCase = "STRONG HAND";
                System.out.println("Case: " + decisionCase);

                raiseProb = 50 + (int)((winRate - potOdds) * 40);  // 50-90% raise
                checkProb = (int)((1 - (winRate - potOdds)) * (100 - raiseProb) * 0.6);
                foldProb = 100 - raiseProb - checkProb;
                System.out.printf("Initial probs - Check: %d, Fold: %d, Raise: %d\n", checkProb, foldProb, raiseProb);
            }

            // Ensure one action is clearly dominant (at least 15% difference)
            System.out.println("Checking for dominant action...");
            int maxProb = Math.max(checkProb, Math.max(foldProb, raiseProb));
            if (maxProb == checkProb) {
                System.out.println("Check is currently dominant");
                if (checkProb - Math.max(foldProb, raiseProb) < 15) {
                    System.out.println("Adjusting to strengthen check");
                    checkProb += 15;
                    if (foldProb > raiseProb) foldProb -= 15;
                    else raiseProb -= 15;
                }
            } else if (maxProb == foldProb) {
                System.out.println("Fold is currently dominant");
                if (foldProb - Math.max(checkProb, raiseProb) < 15) {
                    System.out.println("Adjusting to strengthen fold");
                    foldProb += 15;
                    if (checkProb > raiseProb) checkProb -= 15;
                    else raiseProb -= 15;
                }
            } else {
                System.out.println("Raise is currently dominant");
                if (raiseProb - Math.max(checkProb, foldProb) < 15) {
                    System.out.println("Adjusting to strengthen raise");
                    raiseProb += 15;
                    if (checkProb > foldProb) checkProb -= 15;
                    else foldProb -= 15;
                }
            }
            System.out.printf("Post-dominance probs - Check: %d, Fold: %d, Raise: %d\n", checkProb, foldProb, raiseProb);
        }

        // Final adjustments
        System.out.println("Making final adjustments...");
        int total = checkProb + foldProb + raiseProb;
        System.out.printf("Current total: %d\n", total);

        if (total != 100) {
            int diff = 100 - total;
            System.out.printf("Adjusting by %d points\n", diff);

            if (checkProb >= foldProb && checkProb >= raiseProb) {
                System.out.println("Adding difference to check");
                checkProb += diff;
            } else if (foldProb >= checkProb && foldProb >= raiseProb) {
                System.out.println("Adding difference to fold");
                foldProb += diff;
            } else {
                System.out.println("Adding difference to raise");
                raiseProb += diff;
            }
        }

        // Final clamp
        checkProb = Math.max(0, Math.min(100, checkProb));
        foldProb = Math.max(0, Math.min(100, foldProb));
        raiseProb = Math.max(0, Math.min(100, raiseProb));
        System.out.printf("Final clamped probs - Check: %d, Fold: %d, Raise: %d\n", checkProb, foldProb, raiseProb);

        System.out.printf("Final Decision (%s): Check=%d%%, Fold=%d%%, Raise=%d%%\n",
                decisionCase, checkProb, foldProb, raiseProb);

        return new int[]{checkProb, foldProb, raiseProb};
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
