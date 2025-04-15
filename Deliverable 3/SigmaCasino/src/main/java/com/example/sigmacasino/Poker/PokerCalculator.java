package com.example.sigmacasino.Poker;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PokerCalculator{
    private static final int INITIAL_TRIALS = 50_000;  // Start with this number of trials
    private static final int MAX_TIME_MILLIS = 1_000; // Around 1 sec max for simulation to finish
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    private final List<Card> playerHand;
    private final List<Card> communityCards;
    private final ArrayList<Card> discardCards = new ArrayList<>();
    private final int numOpponents;

    //Setting up all discarded cards and the number of bots
    public PokerCalculator(List<Card> playerHand, List<Card> communityCards, int numOpponents) {
        discardCards.add(playerHand.get(0));
        discardCards.add(playerHand.get(1));
        this.playerHand = playerHand;
        this.communityCards = communityCards;
        this.numOpponents = numOpponents;
    }

    //Simulating possible hands to find out the win rate
    double runSimulation() {

        //Runs the tests in parallel
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        long startTime = System.currentTimeMillis();

        int totalTrials = 0;  // Track actual trials
        int wins = 0; //Win amount

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

        //Stops simulation
        executor.shutdown();
        double winRate = (double) wins / totalTrials;
        System.out.println("Total Trials Run: " + totalTrials);
        return winRate;
    }

    //Depending on the criteria the algorithm provides the best possible move
    static int[] getMoveDecision(double winRatePercent, double currentBet, double potSize, double playerInvestment, boolean isFirstRound) {
        System.out.println("\n=== NEW DECISION CALCULATION ===");
        System.out.printf("Input params - WinRate: %.2f%%, CurrentBet: %.1f, PotSize: %.1f, PlayerInvestment: %.1f\n",
                winRatePercent, currentBet, potSize, playerInvestment);

        //Convert win rate percentage (0-100%) to decimal (0.0-1.0)
        double winRate = Math.max(0, Math.min(100, winRatePercent)) / 100.0;
        System.out.printf("Converted WinRate: %.2f\n", winRate);

        //Calculate the cost to call and pot odds (to see if it is worth is for the player to continue playing)
        double callCost = Math.max(currentBet - playerInvestment, 0);
        double effectivePotSize = potSize + callCost;
        double potOdds = (callCost == 0) ? 0 : callCost / effectivePotSize; //Doesn't throw an error if dividing by 0
        System.out.printf("Calculated - CallCost: %.1f, EffectivePot: %.1f, PotOdds: %.2f\n",
                callCost, effectivePotSize, potOdds);

        // Decision probabilities
        int checkProb = 0;
        int foldProb = 0;
        int raiseProb = 0;
        String decisionCase = "";


        //Case 1: No bet to call (check or raise)
        if (callCost == 0) {
            decisionCase = "NO BET TO CALL";
            System.out.println("Case: " + decisionCase);

            //Default: more likely to check unless win rate is high
            checkProb = 60 + (int)((1 - winRate) * 30);  // 60-90% check
            raiseProb = 100 - checkProb;

            //If win rate > 50%, boost raise chance
            if (winRate > 0.5) {
                raiseProb += (int)((winRate - 0.5) * 40);  // boost based on how strong the hand is
                checkProb = 100 - raiseProb;
            }

            //Ensures there's a clear difference between choices
            if (Math.abs(checkProb - raiseProb) < 10) {
                System.out.println("Adjusting for minimum 10% difference");
                if (winRate > 0.5) {
                    raiseProb += 10;
                    checkProb -= 10;
                } else {
                    checkProb += 10;
                    raiseProb -= 10;
                }
            }

            System.out.printf("Adjusted probs - Check: %d, Raise: %d\n", checkProb, raiseProb);

        } else {
            //Case 2: There is a bet to call
            if (winRate < potOdds - 0.15) {
                decisionCase = "VERY UNFAVORABLE";
                System.out.println("Case: " + decisionCase);

                foldProb = 70 + (int)(Math.random() * 20);  // 70-90% fold

                //First round: encourage check by reducing fold
                if (isFirstRound) {
                    System.out.println("First round: reducing fold probability");
                    foldProb -= 40;
                }

                checkProb = (int)((1 - winRate) * (100 - foldProb) * 0.7);
                raiseProb = 100 - foldProb - checkProb;

                //Add bluffing if win rate is very low
                if (winRate < 0.35 && Math.random() < 0.34) {
                    int bluffAmount = 2 + (int)(Math.random() * 6);  // 2% to 8%
                    System.out.printf("BLUFFING! Boosting raise by %d%%\n", bluffAmount);
                    raiseProb += bluffAmount;
                    foldProb -= bluffAmount;
                }

            } else if (winRate < potOdds) {
                //Not worth playing
                decisionCase = "SLIGHTLY UNFAVORABLE";
                System.out.println("Case: " + decisionCase);

                foldProb = 30 + (int)(Math.random() * 30);  // 30-60% fold

                if (isFirstRound) {
                    System.out.println("First round: reducing fold probability");
                    foldProb -= 35;
                }

                checkProb = (int)((winRate * 0.7) * (100 - foldProb));
                raiseProb = 100 - foldProb - checkProb;

            } else if (winRate < potOdds + 0.2) {
                //Ok hand
                decisionCase = "MARGINAL SITUATION";
                System.out.println("Case: " + decisionCase);

                checkProb = 60 + (int)(Math.random() * 30);  // 60-90% check
                raiseProb = (int)((winRate - potOdds) * (100 - checkProb) * 2);

                // Favor raise if win rate > 50%
                if (winRate > 0.5) {
                    raiseProb += (int)((winRate - 0.5) * 30);
                }

                foldProb = 100 - checkProb - raiseProb;

            } else {
                //Good Hand
                decisionCase = "STRONG HAND";
                System.out.println("Case: " + decisionCase);

                raiseProb = 50 + (int)((winRate - potOdds) * 40);  // 50-90% raise

                // Strong win rate gets extra raise boost
                if (winRate > 0.5) {
                    raiseProb += (int)((winRate - 0.5) * 30);
                }

                checkProb = (int)((1 - (winRate - potOdds)) * (100 - raiseProb) * 0.6);
                foldProb = 100 - raiseProb - checkProb;
            }

            //Ensure one action is clearly favored by >=15% in order to get a proper decision
            System.out.println("Checking for dominant action...");
            int maxProb = Math.max(checkProb, Math.max(foldProb, raiseProb));
            if (maxProb == checkProb && checkProb - Math.max(foldProb, raiseProb) < 15) {
                System.out.println("Adjusting to strengthen check");
                checkProb += 15;
                if (foldProb > raiseProb) foldProb -= 15;
                else raiseProb -= 15;
            } else if (maxProb == foldProb && foldProb - Math.max(checkProb, raiseProb) < 15) {
                System.out.println("Adjusting to strengthen fold");
                foldProb += 15;
                if (checkProb > raiseProb) checkProb -= 15;
                else raiseProb -= 15;
            } else if (maxProb == raiseProb && raiseProb - Math.max(checkProb, foldProb) < 15) {
                System.out.println("Adjusting to strengthen raise");
                raiseProb += 15;
                if (checkProb > foldProb) checkProb -= 15;
                else foldProb -= 15;
            }
            System.out.printf("Post-dominance probs - Check: %d, Fold: %d, Raise: %d\n", checkProb, foldProb, raiseProb);
        }

        //Normalize the percentages to total to 100
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

        //Clamp probabilities to valid range
        checkProb = Math.max(0, Math.min(100, checkProb));
        foldProb = Math.max(0, Math.min(100, foldProb));
        raiseProb = Math.max(0, Math.min(100, raiseProb));
        System.out.printf("Final clamped probs - Check: %d, Fold: %d, Raise: %d\n", checkProb, foldProb, raiseProb);

        System.out.printf("Final Decision (%s): Check=%d%%, Fold=%d%%, Raise=%d%%\n",
                decisionCase, checkProb, foldProb, raiseProb);

        return new int[]{checkProb, foldProb, raiseProb};
    }

    //Logics behind the simulations
    private int simulateGames(int numTrials) {
        int wins = 0;

        for (int i = 0; i < numTrials; i++) {
            Deck deck = new Deck();  // Create a fresh deck for every trial
            deck.shuffle();

            ArrayList<Card> fullCommunity = new ArrayList<>(communityCards);
            while (fullCommunity.size() < 5) {
                if (deck.deal()==null) break; //Prevent out-of-cards error
                Card card = deck.deal();
                while (discardCards.contains(card) && !(deck.deal()==null)) {
                    card = deck.deal();
                }
                fullCommunity.add(card);
            }

            ArrayList<List<Card>> opponentHands = new ArrayList<>();
            for (int j = 0; j < numOpponents; j++) {
                if (deck.deal() == null) break; //Prevent over-dealing
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

    //Makes a hand for each simulated bot
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
