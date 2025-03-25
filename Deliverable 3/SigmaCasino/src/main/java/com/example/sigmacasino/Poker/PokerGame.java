package com.example.sigmacasino.Poker;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.Deck;
import io.lyuda.jcards.game.Player;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PokerGame {
    private static BettingThread bettingThread;
    private final ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Integer> playerChips = new ArrayList<>();
    private final ArrayList<Card> riverCards = new ArrayList<>();
    private final ArrayList<Float> playerRanks = new ArrayList<>();
    private final ArrayList<String> playerRankNames = new ArrayList<>();
    private ArrayList<ImageView> card1 = new ArrayList<>();
    private ArrayList<ImageView> card2 = new ArrayList<>();
    private Deck deck;
    private int potSize;
    private int dealerIndex;
    private int smallBlindIndex;
    private int bigBlindIndex;
    private int burnCards;
    private int botAmount;
    private TextArea announcerTextArea;

    protected PokerGame(PokerController controller){
        bettingThread = new BettingThread();
        bettingThread.start();
        announcerTextArea = controller.getAnnouncerTextArea();
        int index = 1;
        botAmount=controller.getSpinnerBots().getValue();
        String burnAmount=controller.getChoiceBoxBruntCards().getValue();
        switch(burnAmount)
        {
            case "No Cards": burnCards=0;break;
            case "1 Card": burnCards=1;break;
            case "2 Cards": burnCards=2;break;
        }


        players.add(new Player("Player"));
        associatePlayerCards(controller,players.getFirst().getName(),0);
        playerChips.add(Integer.parseInt(controller.getChipsPlayer().getText()));

        if(botAmount==4)
        {
            players.add(new Player("Bot "+botAmount));
            index=2;
            associatePlayerCards(controller,players.get(1).getName(),1);
            playerChips.add(Integer.parseInt(controller.getChipsBot4().getText()));
        }
        else if (botAmount==5)
        {
            players.add(new Player("Bot "+botAmount));
            associatePlayerCards(controller,players.get(1).getName(),1);
            playerChips.add(Integer.parseInt(controller.getChipsBot5().getText()));
            players.add(new Player("Bot "+(botAmount-1)));
            associatePlayerCards(controller,players.get(2).getName(),2);
            playerChips.add(Integer.parseInt(controller.getChipsBot4().getText()));
            index=3;
        }
        int[] chips = {Integer.parseInt(controller.getChipsBot1().getText()),Integer.parseInt(controller.getChipsBot2().getText()),Integer.parseInt(controller.getChipsBot3().getText())};
        for(int i=index,j=1;i<=botAmount;i++,j++)
        {
            players.add(new Player("Bot "+j));
            playerChips.add(chips[j-1]);
            associatePlayerCards(controller,players.get(i).getName(),i);
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

    private void associatePlayerCards(PokerController controller,String player, int index){
        switch(player){
            case "Player": card1.add(controller.getPlayerCard1());card2.add(controller.getPlayerCard2());break;
            case "Bot 1": card1.add(controller.getBot1Card1()); card2.add(controller.getBot1Card2()); break;
            case "Bot 2": card1.add(controller.getBot2Card1()); card2.add(controller.getBot2Card2()); break;
            case "Bot 3": card1.add(controller.getBot3Card1()); card2.add(controller.getBot3Card2()); break;
            case "Bot 4": card1.add(controller.getBot4Card1()); card2.add(controller.getBot4Card2()); break;
            case "Bot 5": card1.add(controller.getBot5Card1()); card2.add(controller.getBot5Card2()); break;
        }
    }

    protected void playGame(PokerController controller){
        //Restricts users controls during the round
        restrictControls(true,controller);

        //Gives player Cards
        dealCards(controller);

        //First Round of Betting
        playerBet(controller);

        //First River Flop
        dealFlop(controller);

        //Second Round of Betting
        playerBet(controller);

        //Second River Flop
        dealPostFlop(controller,true);

        //Third Round of Betting
        playerBet(controller);

        //Third River Flop
        dealPostFlop(controller,false);

        //Fourth Round of Betting
        playerBet(controller);

        //Check Cards Ranks
        rankings();
        playerRankNames();
        displayFinalRankings();

        //Winner
        getRoundWinner();

        //Enables users controls after the round
        restrictControls(false,controller);

        //Ends Round
        endGame(controller);
    }

    private void restrictControls(boolean restrict, PokerController controller)
    {
        if(restrict)
        {
            controller.getChoiceBoxBruntCards().setDisable(true);
            controller.getSpinnerBots().setDisable(true);
            controller.getStartingChipsTextArea().setEditable(false);
            controller.getStartRoundCheckBox().setDisable(true);
            controller.getHideChipsCheckBox().setDisable(true);
        }
        else {
            controller.getChoiceBoxBruntCards().setDisable(false);
            controller.getSpinnerBots().setDisable(false);
            controller.getStartingChipsTextArea().setEditable(true);
            controller.getStartRoundCheckBox().setDisable(false);
            controller.getHideChipsCheckBox().setDisable(false);
        }
    }

    private void dealCards(PokerController controller){
        deck = new Deck();
        deck.shuffle();
        for(int j=0;j<2;j++) {
            for (Player player : players) {
                player.addCard(deck.deal());
            }
        }
        for(int i=0;i<players.size();i++)
        {
            card1.get(i).setImage(getImage(players.get(i).getHand().getCards().getFirst()));
            card2.get(i).setImage(getImage(players.get(i).getHand().getCards().getLast()));
            System.out.println(players.get(i).getName()+": "+players.get(i).getHand());
        }
        String text = "Dealer has dealt all the cards";
        announcerTextArea.setText(text);
    }

    private Image getImage(Card card){
        String suit = card.getSuit().toString().toLowerCase();
        String rank = card.getRank().toString().toLowerCase();
        String imagePath = "src/main/resources/com/example/sigmacasino/Sprites/PNG-cards-1.3/" + rank + "_of_" + suit + ".png";
        File file = new File(imagePath);
        return new Image(file.toURI().toString());
    }


    private void playerBet(PokerController controller){
        bettingThread.pauseThread(); // Pause betting logic
        int value = -1;
        // Simulate waiting for user input (Check, Fold, Raise)
        // In real code, this should be event-driven (e.g., waiting for button clicks)
        try {
            String text = "\nPlayer's turn to bet...";
            announcerTextArea.setText(announcerTextArea.getText()+text);
            Thread.sleep(5000); // Simulating player thinking time
            value = controller.getButtonValue();
            text = "\nPlayer has chosen to ";
            text = switch (value) {
                case 0 -> text + "check";
                case -1 -> text + "fold";
                default -> text + "raise by $" + controller.getRaiseText();
            };
            announcerTextArea.setText(announcerTextArea.getText()+text);
            for(int i=1;i<players.size();i++)
            {
                text = "\n"+players.get(i).getName()+"'s turn to bet...";
                announcerTextArea.setText(announcerTextArea.getText()+text);
                Thread.sleep(5000);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        bettingThread.resumeThread(); // Resume betting logic
    }

    private void getRoundWinner(){
        String text;
        float max = playerRanks.getFirst();
        ArrayList<Player> winners = new ArrayList<>();
        for (int i=1;i<players.size();i++)
        {
            if(playerRanks.get(i)>max){
                max = playerRanks.get(i);
            }
        }
        for(int i=0;i<players.size();i++)
        {
            if(playerRanks.get(i)== max)
            {
                winners.add(players.get(i));
            }
        }
        for(int i=0;i<winners.size();i++)
        {
            text = "\n"+winners.get(i).getName()+" Wins!";
            announcerTextArea.setText(announcerTextArea.getText()+text);
        }
    }

    private void dealFlop(PokerController controller) {
        String text;
        String[] strings = {"first","second","third"};
        riverCards.clear();
        for(int i=0;i<3;i++)
        {
            riverCards.add(deck.deal());
            text = "\nThe "+strings[i]+" river card is a "+riverCards.get(i).getRank().toString().toLowerCase()+" of "+riverCards.get(i).getSuit().toString().toLowerCase();
            announcerTextArea.setText(announcerTextArea.getText()+text);
            if(burnCards>0)
            {
                for(int j=0;j<burnCards;j++)
                {
                    Card card =deck.deal();
                    text = "\nThe dealer bruns a "+card.getRank().toString().toLowerCase()+" of "+card.getSuit().toString().toLowerCase();
                    announcerTextArea.setText(announcerTextArea.getText()+text);
                }
            }
        }
        controller.getRiverCard1().setImage(getImage(riverCards.getFirst()));
        controller.getRiverCard2().setImage(getImage(riverCards.get(1)));
        controller.getRiverCard3().setImage(getImage(riverCards.get(2)));
    }

    private void dealPostFlop(PokerController controller,boolean flop) {
        String text;
        System.out.println("Breaks?");
        riverCards.add(deck.deal());
        if (burnCards > 0) {
            for (int j = 0; j < burnCards; j++) {
                Card card =deck.deal();
                text = "\nThe dealer bruns a "+card.getRank().toString().toLowerCase()+" of "+card.getSuit().toString().toLowerCase();
                announcerTextArea.setText(announcerTextArea.getText()+text);
            }
        }
        if(flop)
        {
            controller.getRiverCard4().setImage(getImage(riverCards.get(3)));
            text = "\nThe fourth river card is a "+riverCards.get(3).getRank().toString().toLowerCase()+" of "+riverCards.get(3).getSuit().toString().toLowerCase();
            announcerTextArea.setText(announcerTextArea.getText()+text);
        }
        else
        {
            controller.getRiverCard5().setImage(getImage(riverCards.get(4)));
            text = "\nThe fifth river card is a "+riverCards.get(4).getRank().toString().toLowerCase()+" of "+riverCards.get(4).getSuit().toString().toLowerCase();
            announcerTextArea.setText(announcerTextArea.getText()+text);
        }
    }

    private void rankings() {
        for(int i=0;i<players.size();i++)
        {
            playerRanks.add(bestHand(new ArrayList<Card>(List.of(new Card[]{players.get(i).getHand().getCards().getFirst(), players.get(i).getHand().getCards().getLast(), riverCards.get(0), riverCards.get(1), riverCards.get(2), riverCards.get(3), riverCards.get(4)}))));
            System.out.println(playerRanks);
        }
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

    private void displayFinalRankings(){
        for(int i=0;i<players.size();i++)
        {
            String text = "\n"+players.get(i).getName()+" has "+ playerRankNames.get(i);
            announcerTextArea.setText(announcerTextArea.getText()+text);
        }
    }

    protected ArrayList<Integer> getPlayerChips() {
        return playerChips;
    }

    private void setPlayerChips(ArrayList<Integer> playerChips) {
        this.playerChips = playerChips;
    }

    private void endGame(PokerController controller) {
        bettingThread.stopThread(); // Gracefully stop the betting thread

        for (ImageView imageView : controller.imageViews) {
            controller.setImage(imageView);
        }
    }

    private class BettingThread extends Thread {
        private volatile boolean running = true;
        private volatile boolean paused = false;

        public void pauseThread() {
            paused = true;
        }

        public void resumeThread() {
            synchronized (this) {
                paused = false;
                notify(); // Wake up the thread
            }
        }

        public void stopThread() {
            running = false; // Signal thread to stop
            resumeThread();  // In case it's paused, wake it up so it can exit
        }

        @Override
        public void run() {
            while (running) { // Run while the thread is active
                synchronized (this) {
                    while (paused && running) { // Pause logic
                        try {
                            wait(); // Wait until resumed or stopped
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }

                if (!running) break; // Exit if stopped

                System.out.println("Betting phase ongoing...");
                try {
                    Thread.sleep(1000); // Simulate betting process
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Betting thread has stopped.");
        }
    }
}
