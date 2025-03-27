package com.example.sigmacasino.Poker;

import io.lyuda.jcards.Card;
import io.lyuda.jcards.Deck;
import io.lyuda.jcards.game.Player;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PokerGame {
    private static BettingThread bettingThread;
    private final ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<Integer> playerChips = new ArrayList<>();
    private final ArrayList<Card> riverCards = new ArrayList<>();
    private final ArrayList<Float> playerRanks = new ArrayList<>();
    private final ArrayList<String> playerRankNames = new ArrayList<>();
    private static ArrayList<Label> dealerLabels = new ArrayList<>();
    private static ArrayList<Label> smallBlindLabels = new ArrayList<>();
    private static ArrayList<Label> bigBlindLabels = new ArrayList<>();
    private static ArrayList<ImageView> card1 = new ArrayList<>();
    private static ArrayList<ImageView> card2 = new ArrayList<>();
    private static ArrayList<Label> chipLabels = new ArrayList<>();
    private static ArrayList<Circle> playerTurnCircles = new ArrayList<>();
    private ArrayList<Boolean> playersFold = new ArrayList<>();
    private ArrayList<Integer> currentPlayerBets = new ArrayList<>();
    private Deck deck;
    private static int potSize;
    private static int smallBlindAmount;
    private static int bigBlindAmount;
    private static int dealerIndex;
    private static int smallBlindIndex;
    private static int bigBlindIndex;
    private int burnCards;
    private int botAmount;
    private int betFollow;
    private boolean earlyWin;
    private TextArea announcerTextArea;

    protected PokerGame(PokerController controller){
        for (ImageView imageView : controller.imageViews) {
            controller.setImage(imageView);
        }
        playersFold.clear();
        currentPlayerBets.clear();
        earlyWin= false;
        boolean newChips = false;
        if(playerChips.isEmpty())
        {
            newChips = true;
            int startingChips = Integer.parseInt(controller.getStartingChipsTextArea().getText());
            smallBlindAmount = startingChips/100;
            bigBlindAmount = startingChips/50;
        }

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
        playersFold.add(false);
        currentPlayerBets.add(0);
        if(newChips) {
            associatePlayerCards(controller,players.getFirst().getName());
            playerChips.add(Integer.parseInt(controller.getChipsPlayer().getText()));
            dealerLabels.add(controller.getDealerLabelPlayer());
            smallBlindLabels.add(controller.getSmallBlindLabelPlayer());
            bigBlindLabels.add(controller.getBigBlindLabelPlayer());
        }

        if(botAmount==4)
        {
            players.add(new Player("Bot "+botAmount));
            playersFold.add(false);
            currentPlayerBets.add(0);
            index=2;
            if(newChips) {
                associatePlayerCards(controller,players.get(1).getName());
                playerChips.add(Integer.parseInt(controller.getChipsBot4().getText()));
                dealerLabels.add(controller.getDealerLabelBot4());
                smallBlindLabels.add(controller.getSmallBlindLabelBot4());
                bigBlindLabels.add(controller.getBigBlindLabelBot4());
            }
        }
        else if (botAmount==5)
        {
            players.add(new Player("Bot "+botAmount));
            playersFold.add(false);
            currentPlayerBets.add(0);
            if(newChips) {
                associatePlayerCards(controller,players.get(1).getName());
                playerChips.add(Integer.parseInt(controller.getChipsBot5().getText()));
                dealerLabels.add(controller.getDealerLabelBot5());
                smallBlindLabels.add(controller.getSmallBlindLabelBot5());
                bigBlindLabels.add(controller.getBigBlindLabelBot5());
            }
            players.add(new Player("Bot "+(botAmount-1)));
            playersFold.add(false);
            currentPlayerBets.add(0);
            if(newChips) {
                associatePlayerCards(controller,players.get(2).getName());
                playerChips.add(Integer.parseInt(controller.getChipsBot4().getText()));
                dealerLabels.add(controller.getDealerLabelBot4());
                smallBlindLabels.add(controller.getSmallBlindLabelBot4());
                bigBlindLabels.add(controller.getBigBlindLabelBot4());
            }
            index=3;
        }
        int[] chips = {Integer.parseInt(controller.getChipsBot1().getText()),Integer.parseInt(controller.getChipsBot2().getText()),Integer.parseInt(controller.getChipsBot3().getText())};
        Label[] dealer = {controller.getDealerLabelBot1(),controller.getDealerLabelBot2(),controller.getDealerLabelBot3()};
        Label[] smallBlind = {controller.getSmallBlindLabelBot1(),controller.getSmallBlindLabelBot2(),controller.getSmallBlindLabelBot3()};
        Label[] bigBlind = {controller.getBigBlindLabelBot1(),controller.getBigBlindLabelBot2(),controller.getBigBlindLabelBot3()};
        for(int i=index,j=1;i<=botAmount;i++,j++)
        {
            players.add(new Player("Bot "+j));
            playersFold.add(false);
            currentPlayerBets.add(0);
            if(newChips) {
                playerChips.add(chips[j - 1]);
                dealerLabels.add(dealer[j - 1]);
                smallBlindLabels.add(smallBlind[j - 1]);
                bigBlindLabels.add(bigBlind[j - 1]);
                associatePlayerCards(controller,players.get(i).getName());
            }
        }
        if(newChips) {
            dealerIndex = 0;
            if (botAmount == 1) {
                smallBlindIndex = 0;
                bigBlindIndex = 1;
            } else {
                smallBlindIndex = 1;
                bigBlindIndex = 2;
            }
        }
        else {
            changeDealer(botAmount);
        }
        dealerLabels.get(dealerIndex).setVisible(true);
        smallBlindLabels.get(smallBlindIndex).setVisible(true);
        bigBlindLabels.get(bigBlindIndex).setVisible(true);
    }

    private void changeDealer(int botAmount){
        dealerIndex++;
        smallBlindIndex++;
        bigBlindIndex++;
        if(dealerIndex>botAmount)
        {
            dealerIndex=0;
        }
        if(smallBlindIndex>botAmount)
        {
            smallBlindIndex=0;
        }
        if(bigBlindIndex>botAmount)
        {
            bigBlindIndex=0;
        }
    }

    private void associatePlayerCards(PokerController controller,String player){
        switch(player){
            case "Player": card1.add(controller.getPlayerCard1());card2.add(controller.getPlayerCard2());chipLabels.add(controller.chips[0]); playerTurnCircles.add(controller.botTurns[0]);break;
            case "Bot 1": card1.add(controller.getBot1Card1()); card2.add(controller.getBot1Card2());chipLabels.add(controller.chips[1]); playerTurnCircles.add(controller.botTurns[1]);break;
            case "Bot 2": card1.add(controller.getBot2Card1()); card2.add(controller.getBot2Card2());chipLabels.add(controller.chips[2]); playerTurnCircles.add(controller.botTurns[2]);break;
            case "Bot 3": card1.add(controller.getBot3Card1()); card2.add(controller.getBot3Card2());chipLabels.add(controller.chips[3]); playerTurnCircles.add(controller.botTurns[3]);break;
            case "Bot 4": card1.add(controller.getBot4Card1()); card2.add(controller.getBot4Card2());chipLabels.add(controller.chips[4]); playerTurnCircles.add(controller.botTurns[4]);break;
            case "Bot 5": card1.add(controller.getBot5Card1()); card2.add(controller.getBot5Card2());chipLabels.add(controller.chips[5]); playerTurnCircles.add(controller.botTurns[5]);break;
        }
    }

    protected void playGame(PokerController controller){
        //Restricts users controls during the round
        restrictControls(true, controller);

        //Gives player Cards (runs on the JavafxThread)
        dealCards(controller);

        //Places small/big blinds into the pot
        placeBlinds(controller);

        //First Round of Betting
        playerBet(controller);

        if(earlyWin)
        {
            earlyWinner(controller);
            return;
        }

        //First River Flop
        dealFlop(controller);

        //Second Round of Betting
        playerBet(controller);

        if(earlyWin)
        {
            earlyWinner(controller);
            return;
        }

        //Second River Flop
        dealPostFlop(controller, true);

        //Third Round of Betting
        playerBet(controller);

        if(earlyWin)
        {
            earlyWinner(controller);
            return;
        }

        //Third River Flop
        dealPostFlop(controller, false);

        //Fourth Round of Betting
        playerBet(controller);

        if(earlyWin)
        {
            earlyWinner(controller);
            return;
        }

        //Check Cards Ranks
        rankings();
        playerRankNames();
        displayFinalRankings();

        //Winner
        getRoundWinner(controller);

        //Enables users controls after the round
        restrictControls(false, controller);

        //Ends Round
        endGame(controller);
    }

    private void earlyWinner(PokerController controller){
        int winnerIndex=0;
        for(int i=0;i<playersFold.size();i++)
        {
            if(!playersFold.get(i))
            {
                winnerIndex=i;
            }
        }
        String text;
        text = "\nEveryone has folded except for "+players.get(winnerIndex).getName()+"\n"+players.get(winnerIndex).getName()+" Wins!";
        String finalText = text;
        playerChips.set(winnerIndex, playerChips.get(winnerIndex)+potSize);
        Platform.runLater(() ->announcerTextArea.setText(announcerTextArea.getText()+ finalText));
        Platform.runLater(() -> updateChips(potSize, controller));
        restrictControls(false,controller);
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
        Platform.runLater(() -> {
            for (int i = 0; i < players.size(); i++) {
                card1.get(i).setImage(getImage(players.get(i).getHand().getCards().getFirst()));
                card2.get(i).setImage(getImage(players.get(i).getHand().getCards().getLast()));
                System.out.println(players.get(i).getName() + ": " + players.get(i).getHand());
            }
        });
        String text = "Dealer has dealt all the cards";
        Platform.runLater(() -> announcerTextArea.setText(text));
    }

    private Image getImage(Card card){
        String suit = card.getSuit().toString().toLowerCase();
        String rank = card.getRank().toString().toLowerCase();
        String imagePath = "src/main/resources/com/example/sigmacasino/Sprites/PNG-cards-1.3/" + rank + "_of_" + suit + ".png";
        File file = new File(imagePath);
        return new Image(file.toURI().toString());
    }

    private boolean didEveryoneFold(){
        int counter=0;
        for(int i=0;i<playersFold.size();i++){
            if(!playersFold.get(i))
            {
                counter++;
            }
        }
        return counter == 1;
    }

    private void playerBet(PokerController controller){
        bettingThread.pauseThread(); // Pause betting logic
        int value;
        int starter = bigBlindIndex + 1;
        if (starter >= players.size()) {
            starter = 0; // Ensure valid start index
        }
        boolean flag = false ;
        String text;

        try {
            System.out.println("Starter: " + starter);

            loop: for (int i = starter; ; i++) {
                if(!didEveryoneFold()) {
                    System.out.println("Current: " + i);
                    if (i == players.size()) {
                        System.out.println("In index reset");
                        i = 0; // Restart at the first player
                        flag = true; // Now we're in the second pass
                    }

                    // Ensure the loop stops after completing a full cycle
                    if (players.get(i).equals(players.get(starter)) && flag) {
                        System.out.println("In break");
                        break loop;
                    }
                    if (!playersFold.get(i)) {
                        playerTurnCircles.get(i).setVisible(true);
                        if (i == 0) { // Player's turn
                            Platform.runLater(() -> {
                                controller.getSecondsLabel().setVisible(true);
                                controller.getTimeRemainingLabel().setVisible(true);
                                controller.getPlayerTimeLimitLabel().setVisible(true);
                            });
                            long delay = 15;
                            System.out.println("\nPlayer is betting");
                            text = "\nPlayer's turn to bet...";
                            String finalText2 = text;
                            Platform.runLater(() -> announcerTextArea.setText(announcerTextArea.getText() + finalText2));
                            new Thread(() -> {
                                try {
                                    for (long j = delay; j >= 0; j--) {
                                        long finalJ = j;
                                        Platform.runLater(() -> controller.getPlayerTimeLimitLabel().setText(""+ finalJ));
                                        Thread.sleep(1000);
                                    }
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }).start();
                            Thread.sleep(delay*1000);
                            value = controller.getButtonValue();
                            switch (value) {
                                case 0:
                                    text = "\nPlayer has chosen to check";
                                    betFollow=betFollow-currentPlayerBets.get(i);
                                    playerChips.set(i, playerChips.get(i) - betFollow);
                                    currentPlayerBets.set(i, 0);
                                    break;
                                case -1:
                                    text = "\nPlayer has chosen to fold";
                                    playersFold.set(i, true);
                                    break;
                                default:
                                    text = "\nPlayer has chosen to raise by $" + controller.getRaiseText();
                                    betFollow = value;
                                    playerChips.set(i, playerChips.get(i) - betFollow);
                                    starter=i;
                                    break;
                            }

                            if (!playersFold.get(i)) {
                                Platform.runLater(() -> potSize = potSize + betFollow);
                            }
                            Platform.runLater(() -> updateChips(potSize, controller));
                            String finalText = text;
                            Platform.runLater(() -> announcerTextArea.setText(announcerTextArea.getText() + finalText));
                            Platform.runLater(() -> {
                                controller.getSecondsLabel().setVisible(false);
                                controller.getTimeRemainingLabel().setVisible(false);
                                controller.getPlayerTimeLimitLabel().setVisible(false);
                            });
                        } else { // Bot's turn
                            System.out.println("\n" + players.get(i).getName() + " is betting");
                            text = "\n" + players.get(i).getName() + "'s turn to bet...";
                            String finalText1 = text;
                            Platform.runLater(() -> announcerTextArea.setText(announcerTextArea.getText() + finalText1));
                            Thread.sleep(5000);
                            currentPlayerBets.set(i, 0);
                        }
                        playerTurnCircles.get(i).setVisible(false);
                    }
                }
                else{
                    earlyWin = true;
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        betFollow=0;
        bettingThread.resumeThread(); // Resume betting logic
    }

    private void getRoundWinner(PokerController controller){
        String text;
        int winnerAmount;
        float max = playerRanks.getFirst();
        ArrayList<Integer> winners = new ArrayList<>();
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
                winners.add(i);
            }
        }

        winnerAmount = potSize/winners.size();

        for(int i=0;i<winners.size();i++)
        {
            text = "\n"+players.get(winners.get(i)).getName()+" Wins!";
            String finalText = text;
            playerChips.set(winners.get(i), playerChips.get(winners.get(i))+winnerAmount);
            Platform.runLater(() ->announcerTextArea.setText(announcerTextArea.getText()+ finalText));
        }
        Platform.runLater(() -> updateChips(potSize, controller));
    }

    private void dealFlop(PokerController controller) {
        String text;
        String[] strings = {"first","second","third"};
        riverCards.clear();
        for(int i=0;i<3;i++)
        {
            riverCards.add(deck.deal());
            text = "\nThe "+strings[i]+" river card is a "+riverCards.get(i).getRank().toString().toLowerCase()+" of "+riverCards.get(i).getSuit().toString().toLowerCase();
            String finalText = text;
            Platform.runLater(() ->announcerTextArea.setText(announcerTextArea.getText()+ finalText));
            if(burnCards>0)
            {
                for(int j=0;j<burnCards;j++)
                {
                    Card card =deck.deal();
                    text = "\nThe dealer bruns a "+card.getRank().toString().toLowerCase()+" of "+card.getSuit().toString().toLowerCase();
                    String finalText1 = text;
                    Platform.runLater(() ->announcerTextArea.setText(announcerTextArea.getText()+ finalText1));
                }
            }
        }
        Platform.runLater(() -> {
            controller.getRiverCard1().setImage(getImage(riverCards.getFirst()));
            controller.getRiverCard2().setImage(getImage(riverCards.get(1)));
            controller.getRiverCard3().setImage(getImage(riverCards.get(2)));
        });
    }

    private void dealPostFlop(PokerController controller,boolean flop) {
        String text;
        riverCards.add(deck.deal());
        if (burnCards > 0) {
            for (int j = 0; j < burnCards; j++) {
                Card card =deck.deal();
                text = "\nThe dealer bruns a "+card.getRank().toString().toLowerCase()+" of "+card.getSuit().toString().toLowerCase();
                String finalText = text;
                Platform.runLater(() ->announcerTextArea.setText(announcerTextArea.getText()+ finalText));
            }
        }
        if(flop)
        {
            Platform.runLater(() ->controller.getRiverCard4().setImage(getImage(riverCards.get(3))));
            text = "\nThe fourth river card is a "+riverCards.get(3).getRank().toString().toLowerCase()+" of "+riverCards.get(3).getSuit().toString().toLowerCase();
            String finalText1 = text;
            Platform.runLater(() -> announcerTextArea.setText(announcerTextArea.getText()+ finalText1));
        }
        else
        {
            Platform.runLater(() ->controller.getRiverCard5().setImage(getImage(riverCards.get(4))));
            text = "\nThe fifth river card is a "+riverCards.get(4).getRank().toString().toLowerCase()+" of "+riverCards.get(4).getSuit().toString().toLowerCase();
            String finalText2 = text;
            Platform.runLater(() ->announcerTextArea.setText(announcerTextArea.getText()+ finalText2));
        }
    }

    private void placeBlinds(PokerController controller){
        currentPlayerBets.set(smallBlindIndex, smallBlindAmount);
        currentPlayerBets.set(bigBlindIndex, bigBlindAmount);
        betFollow=bigBlindAmount;
        potSize = smallBlindAmount+bigBlindAmount;
        playerChips.set(smallBlindIndex,playerChips.get(smallBlindIndex)-smallBlindAmount);
        playerChips.set(bigBlindIndex, playerChips.get(bigBlindIndex)-bigBlindAmount);
        Platform.runLater(() -> updateChips(potSize, controller));
    }

    private void updateChips(int pot, PokerController  controller){
        for(int i=0;i<players.size();i++)
        {
            chipLabels.get(i).setText(""+playerChips.get(i));
        }
        controller.getPot().setText(""+pot);
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
            Platform.runLater(() ->announcerTextArea.setText(announcerTextArea.getText()+text));
        }
    }

    protected static ArrayList<Integer> getPlayerChips() {
        return playerChips;
    }

    protected static ArrayList<Label> getdealerLabels(){
        return dealerLabels;
    }

    protected static ArrayList<Label> getSmallBlindLabels(){
        return smallBlindLabels;
    }

    protected static ArrayList<Label> getBigBlindLabels(){
        return bigBlindLabels;
    }

    protected static ArrayList<ImageView> getCard1() {
        return card1;
    }

    protected static ArrayList<ImageView> getCard2() {
        return card2;
    }

    protected static ArrayList<Label> getChipLabels() {
        return chipLabels;
    }

    protected static ArrayList<Circle> getPlayerTurnCircles() {
        return playerTurnCircles;
    }


    private void endGame(PokerController controller) {
        bettingThread.stopThread();// Gracefully stop the betting thread
        potSize=0;
        Platform.runLater(() -> {
            controller.getPot().setText(""+potSize);
            dealerLabels.get(dealerIndex).setVisible(false);
            smallBlindLabels.get(smallBlindIndex).setVisible(false);
            bigBlindLabels.get(bigBlindIndex).setVisible(false);
        });
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
