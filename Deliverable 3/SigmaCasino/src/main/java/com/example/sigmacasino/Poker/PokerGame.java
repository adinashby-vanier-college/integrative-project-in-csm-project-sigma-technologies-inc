package com.example.sigmacasino.Poker;

import com.example.sigmacasino.Audio.AudioManager;
import com.example.sigmacasino.Settings.SettingsManager;
import io.lyuda.jcards.Card;
import io.lyuda.jcards.Deck;
import io.lyuda.jcards.game.Player;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.sigmacasino.Poker.HandRanks.bestHand;

public class PokerGame {

    //Initiating Variables
    private final ArrayList<Player> players = new ArrayList<>();
    private static final ArrayList<Integer> playerChips = new ArrayList<>();
    private final ArrayList<Card> riverCards = new ArrayList<>();
    private final ArrayList<Float> playerRanks = new ArrayList<>();
    private final ArrayList<String> playerRankNames = new ArrayList<>();
    private static final ArrayList<Label> dealerLabels = new ArrayList<>();
    private static final ArrayList<Label> smallBlindLabels = new ArrayList<>();
    private static final ArrayList<Label> bigBlindLabels = new ArrayList<>();
    private static final ArrayList<ImageView> card1 = new ArrayList<>();
    private static final ArrayList<ImageView> card2 = new ArrayList<>();
    private static final ArrayList<Label> chipLabels = new ArrayList<>();
    private static final ArrayList<Circle> playerTurnCircles = new ArrayList<>();
    private final ArrayList<Boolean> playersFold = new ArrayList<>();
    private final ArrayList<Integer> currentPlayerBets = new ArrayList<>();
    private final ArrayList<Circle> playerCirlesGUI = new ArrayList<>();
    private Deck deck;
    private static int potSize;
    private static int smallBlindAmount;
    private static int bigBlindAmount;
    private static int dealerIndex;
    private static int smallBlindIndex;
    private static int bigBlindIndex;
    private static int playerWinRate;
    private static int botWinRate;
    private int burnCards;
    private final int botAmount;
    private int betFollow;
    private boolean earlyWin;
    private final TextArea announcerTextArea;

    //Sets all the variables up before the game/round starts
    //Also sets the order of all the player (important for dealer, big blind and small blind)
    PokerGame(PokerController controller){

        //Sets default card images
        for (ImageView imageView : controller.imageViews) {
            controller.setDefaultImage(imageView);
        }

        //Sound fx
        AudioManager.loadSound("Shuffle Start", "target/classes/com/example/sigmacasino/Audio/PokerCardShuffleFlip.mp3");
        AudioManager.loadSound("Card Flip", "target/classes/com/example/sigmacasino/Audio/CardFlip.mp3");
        AudioManager.loadSound("Check", "target/classes/com/example/sigmacasino/Audio/CheckSound.mp3");
        AudioManager.loadSound("Fold", "target/classes/com/example/sigmacasino/Audio/FoldSound.mp3");
        AudioManager.loadSound("Raise", "target/classes/com/example/sigmacasino/Audio/RaiseSound.mp3");
        AudioManager.loadSound("Win","target/classes/com/example/sigmacasino/Audio/PokerWin.mp3");

        //Set sound volume
        AudioManager.setVolume("Shuffle Start", SettingsManager.settings.volume);
        AudioManager.setVolume("Card Flip", SettingsManager.settings.volume);
        AudioManager.setVolume("Check", SettingsManager.settings.volume);
        AudioManager.setVolume("Fold", SettingsManager.settings.volume);
        AudioManager.setVolume("Raise", SettingsManager.settings.volume);
        AudioManager.setVolume("Win",SettingsManager.settings.volume);

        //Changes UI on the JavaFX Thread
        Platform.runLater(() -> {
            controller.getCheckProbabilityLabel().setText("");
            controller.getFoldProbabilityLabel().setText("");
            controller.getRaiseProbabilityLabel().setText("");
            controller.getWinPercentageLabel().setText("");
        });
        clearGraph(controller);
        playersFold.clear();
        currentPlayerBets.clear();
        playerCirlesGUI.clear();
        playerWinRate=0;
        earlyWin= false;
        boolean newChips = false;
        if(playerChips.isEmpty())
        {
            newChips = true;
            int startingChips = Integer.parseInt(controller.getStartingChipsTextArea().getText());
            smallBlindAmount = startingChips/100;
            bigBlindAmount = startingChips/50;
        }
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
        playerCirlesGUI.add(controller.getPlayerCircle());
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
            playerCirlesGUI.add(controller.getBotCircle4());
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
            playerCirlesGUI.add(controller.getBotCircle5());
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
            playerCirlesGUI.add(controller.getBotCircle4());
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
        Circle[] circles = {controller.getBotCircle1(),controller.getBotCircle2(),controller.getBotCircle3()};
        for(int i=index,j=1;i<=botAmount;i++,j++)
        {
            players.add(new Player("Bot "+j));
            playersFold.add(false);
            currentPlayerBets.add(0);
            playerCirlesGUI.add(circles[j-1]);
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

        //Resets player circle color
        for (Circle circle : playerCirlesGUI) {
            circle.setFill(Color.rgb(30,144,255));
        }
    }

    //Changes the dealer,big blind and small blind every round
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

    //Associated the front end player controls to the back end player
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

    //Protocol of the game/Path to be followed
    void playGame(PokerController controller){
        //Restricts users controls during the round
        restrictControls(true, controller);

        //Plays sound
        AudioManager.playSound("Shuffle Start");

        //Gives player Cards (runs on the JavafxThread)
        dealCards(controller);
        updateGraph(0,controller);

        //Places small/big blinds into the pot
        placeBlinds(controller);

        //First Round of Betting
        playerBet(controller,true);

        if(earlyWin)
        {
            earlyWinner(controller);
            return;
        }

        //First River Flop
        AudioManager.playSound("Card Flip");
        dealFlop(controller);
        updateGraph(1,controller);
        displayBestPlayerHand(controller,players.getFirst(),riverCards);

        //Second Round of Betting
        playerBet(controller,false);

        if(earlyWin)
        {
            earlyWinner(controller);
            return;
        }

        //Second River Flop
        AudioManager.playSound("Card Flip");
        dealPostFlop(controller, true);
        updateGraph(2,controller);
        displayBestPlayerHand(controller,players.getFirst(),riverCards);

        //Third Round of Betting
        playerBet(controller,false);

        if(earlyWin)
        {
            earlyWinner(controller);
            return;
        }

        //Third River Flop
        AudioManager.playSound("Card Flip");
        dealPostFlop(controller, false);
        updateGraph(3,controller);
        displayBestPlayerHand(controller,players.getFirst(),riverCards);

        //Fourth Round of Betting
        playerBet(controller,false);

        if(earlyWin)
        {
            earlyWinner(controller);
            return;
        }

        //Check Cards Ranks
        AudioManager.playSound("Card Flip");
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

    //Resets the graph for the next round
    private void clearGraph(PokerController controller){
        Platform.runLater(() -> controller.series.getData().clear());
    }

    //All players except for one have folded game ends early
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
        text = "Everyone has folded except for "+players.get(winnerIndex).getName()+"\n"+players.get(winnerIndex).getName()+" Wins!\n";
        String finalText = text;
        playerChips.set(winnerIndex, playerChips.get(winnerIndex)+potSize);
        Platform.runLater(() ->announcerTextArea.setText(finalText+announcerTextArea.getText()));
        Platform.runLater(() -> updateChips(potSize, controller));
        if(players.get(winnerIndex).getName().equals("Player"))
        {
            AudioManager.playSound("Win");
        }
        restrictControls(false,controller);
        endGame(controller);
    }

    //Updates graph with a new win rate percentage
    private void updateGraph(int round, PokerController controller) {
        // Run simulation in background
        new Thread(() -> {
            PokerCalculator simulator = new PokerCalculator(
                    players.getFirst().getHand().getCards(),
                    riverCards,
                    players.size() - 1
            );

            playerWinRate = (int) (simulator.runSimulation() * 100);
            System.out.println("Round " + round + " Win Chance: " + playerWinRate + "%");

            // Update UI on JavaFX thread
            Platform.runLater(() -> {

                //Updates win percent label
                controller.getWinPercentageLabel().setText(playerWinRate + "%");

                // Clear previous data for this round if it exists
                for (XYChart.Data<Number, Number> data : controller.series.getData()) {
                    if (data.getXValue().intValue() == round) {
                        controller.series.getData().remove(data);
                        break;
                    }
                }

                // Add new data point
                controller.series.getData().add(new XYChart.Data<>(round, playerWinRate));

                // Auto-range if needed
                controller.getLineChart().requestLayout();
            });
        }).start();
    }

    //Restrict the users controls to avoid fatal errors
    private void restrictControls(boolean restrict, PokerController controller)
    {
        if(restrict)
        {
            announcerTextArea.clear();
            controller.getChoiceBoxBruntCards().setDisable(true);
            controller.getSpinnerBots().setDisable(true);
            controller.getStartingChipsTextArea().setEditable(false);
            controller.getStartRoundCheckBox().setDisable(true);
            controller.getGameSelect().disableProperty().set(true);
            controller.getPokerRulesMenuItem().disableProperty().set(true);
            controller.getSettingsMenuItem().disableProperty().set(true);
        }
        else {
            controller.getChoiceBoxBruntCards().setDisable(false);
            controller.getSpinnerBots().setDisable(false);
            controller.getStartingChipsTextArea().setEditable(true);
            controller.getStartRoundCheckBox().setDisable(false);
            controller.getGameSelect().disableProperty().set(false);
            controller.getPokerRulesMenuItem().disableProperty().set(false);
            controller.getSettingsMenuItem().disableProperty().set(false);
        }
    }

    //Deals all cards to the players
    private void dealCards(PokerController controller){
        deck = new Deck();
        deck.shuffle();
        riverCards.clear();
        for(int j=0;j<2;j++) {
            for (Player player : players) {
                player.addCard(deck.deal());
            }
        }

        //Displays cards on the GUI
        flipCardImage(card1.getFirst(),getImage(players.getFirst().getHand().getCards().getFirst()));
        flipCardImage(card2.getFirst(),getImage(players.getFirst().getHand().getCards().getLast()));
        String text = "Dealer has dealt all the cards\n";
        Platform.runLater(() -> announcerTextArea.setText(text+announcerTextArea.getText()));
    }

    //Gets the image of a given card
    private Image getImage(Card card){
        String suit = card.getSuit().toString().toLowerCase();
        String rank = card.getRank().toString().toLowerCase();
        String imagePath = "src/main/resources/com/example/sigmacasino/Sprites/PNG-cards-1.3/" + rank + "_of_" + suit + ".png";
        File file = new File(imagePath);
        return new Image(file.toURI().toString());
    }

    //Checks to see if everyone has folded
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

    //Betting phase for all players
    private void playerBet(PokerController controller, boolean isFirstRound){
        System.out.println("Bet Follow: "+betFollow);

        //Finds starter player
        int value;
        int starter = bigBlindIndex + 1;
        if (starter >= players.size()) {
            starter = 0; // Ensure valid start index
        }
        boolean flag = false; //Used in order to tell the loop to break at a certain player
        String text;

        try {
            System.out.println("Starter: " + starter);

            for (int i = starter; ; i++) {
                if(!didEveryoneFold()) {
                    System.out.println("Current: " + i);
                    if (i == players.size()) {
                        System.out.println("In index reset");
                        i = 0; //Restart at the first player
                        flag = true; //Now we're in the second pass
                    }

                    //Ensure the loop stops after completing a full cycle
                    if (players.get(i).equals(players.get(starter)) && flag) {
                        System.out.println("In break");
                        break;
                    }
                    if (!playersFold.get(i)) {
                        playerTurnCircles.get(i).setVisible(true);
                        if (i == 0) { // Player's turn
                            
                            //Updates check, fold, raise percentages
                            int[] results = PokerCalculator.getMoveDecision(playerWinRate,betFollow,potSize,currentPlayerBets.get(i),isFirstRound);
                            Platform.runLater(() -> {
                                controller.getCheckProbabilityLabel().setText(results[0] + "%");
                                controller.getFoldProbabilityLabel().setText(results[1] + "%");
                                controller.getRaiseProbabilityLabel().setText(results[2] + "%");
                            });
                            
                            //Updates time label
                            Platform.runLater(() -> {
                                controller.getSecondsLabel().setVisible(true);
                                controller.getTimeRemainingLabel().setVisible(true);
                                controller.getPlayerTimeLimitLabel().setVisible(true);
                            });
                            long delay = 15;
                            System.out.println("\nPlayer is betting");
                            text = "Player's turn to bet...\n";
                            String finalText2 = text;
                            Platform.runLater(() -> announcerTextArea.setText(finalText2+announcerTextArea.getText()));
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
                            int playerFollow = betFollow - currentPlayerBets.get(i);
                            switch (value) {
                                case 0: // Check/Call
                                    System.out.println("\nPlayer Follow: "+playerFollow);
                                    if (playerFollow == 0) {
                                        AudioManager.playSound("Check");
                                        text = "Player has chosen to check\n";
                                    } else {
                                        AudioManager.playSound("Raise");
                                        text = "Player has chosen to call $" + playerFollow+"\n";
                                    }

                                    // Ensure player doesn't bet more than they have
                                    playerFollow = Math.min(playerFollow, playerChips.get(i));
                                    playerChips.set(i, playerChips.get(i) - playerFollow);
                                    currentPlayerBets.set(i, currentPlayerBets.get(i) + playerFollow);
                                    potSize += playerFollow;
                                    break;

                                case -1: // Fold
                                    AudioManager.playSound("Fold");
                                    text = "Player has chosen to fold\n";
                                    playersFold.set(i, true);
                                    playerCirlesGUI.get(i).setFill(Color.GRAY);
                                    break;

                                default: // Raise
                                    AudioManager.playSound("Raise");
                                    int totalBet = playerFollow + value;

                                    // Ensure player doesn't bet more than they have
                                    totalBet = Math.min(totalBet, playerChips.get(i));
                                    playerChips.set(i, playerChips.get(i) - totalBet);
                                    currentPlayerBets.set(i, currentPlayerBets.get(i) + totalBet);
                                    betFollow += value; // Update the highest bet
                                    System.out.println("\nBet Follow: "+betFollow);
                                    potSize += totalBet;
                                    starter = i; // New raiser becomes the new "starter" for betting round
                                    text = "Player has chosen to raise by $" + value+"\n";
                                    break;
                            }
                            //Updating UI
                            String finalText = text;
                            Platform.runLater(() -> {
                                updateChips(potSize, controller);
                                announcerTextArea.setText(finalText + announcerTextArea.getText());
                                controller.getSecondsLabel().setVisible(false);
                                controller.getTimeRemainingLabel().setVisible(false);
                                controller.getPlayerTimeLimitLabel().setVisible(false);
                                controller.getRaiseTextArea().setText("0");
                                controller.getCheckProbabilityLabel().setText("");
                                controller.getFoldProbabilityLabel().setText("");
                                controller.getRaiseProbabilityLabel().setText("");
                            });
                        } else { // Bot's turn
                            botWinRate=0;
                            System.out.println("\n" + players.get(i).getName() + " is betting");
                            text = players.get(i).getName() + "'s turn to bet...\n";
                            String finalText1 = text;
                            Platform.runLater(() -> announcerTextArea.setText(finalText1 + announcerTextArea.getText()));

                            // Run simulation in background
                            int finalI = i;
                            PokerCalculator simulator = new PokerCalculator(
                                    players.get(finalI).getHand().getCards(),
                                    riverCards,
                                    players.size() - 1
                            );
                            int winRate = (int) (simulator.runSimulation() * 100);
                            System.out.println("\n\nWin Rate: " + winRate+"\n\n");
                            setBotWinRate(winRate);

                            //Bot decision
                            int[] results = PokerCalculator.getMoveDecision(botWinRate, betFollow, potSize, currentPlayerBets.get(i),isFirstRound);
                            int max = results[0];
                            int index = 0;
                            for(int j=1;j<results.length;j++)
                            {
                                if(results[j]>max)
                                {
                                    max=results[j];
                                    index =j;
                                }
                            }
                            int botFollow = betFollow - currentPlayerBets.get(i);
                            System.out.println("\nBot Follow: "+botFollow);
                            Thread.sleep(5000);
                            switch(index){
                                case 0: // Check/Call
                                    if (botFollow == 0) {
                                        AudioManager.playSound("Check");
                                        text = players.get(i).getName() + " has chosen to check\n";
                                    } else {
                                        AudioManager.playSound("Raise");
                                        text = players.get(i).getName() + " has chosen to call $" + botFollow+"\n";
                                    }
                                    botFollow = Math.min(botFollow, playerChips.get(i));
                                    playerChips.set(i, playerChips.get(i) - botFollow);
                                    currentPlayerBets.set(i, currentPlayerBets.get(i) + botFollow);
                                    potSize += botFollow;
                                    break;

                                case 1: // Fold
                                    AudioManager.playSound("Fold");
                                    text = players.get(i).getName() + " has chosen to fold\n";
                                    playersFold.set(i, true);
                                    break;

                                case 2: // Raise
                                    AudioManager.playSound("Raise");
                                    if(playerChips.get(i)!=0) {
                                        int raiseAmount = ThreadLocalRandom.current().nextInt(0, (playerChips.get(i) / 4));
                                        botFollow = betFollow - currentPlayerBets.get(i);
                                        int totalBet = botFollow + raiseAmount;

                                        totalBet = Math.min(totalBet, playerChips.get(i));
                                        playerChips.set(i, playerChips.get(i) - totalBet);
                                        currentPlayerBets.set(i, currentPlayerBets.get(i) + totalBet);
                                        betFollow += raiseAmount;
                                        System.out.println("\nBet Follow: "+betFollow);
                                        potSize += totalBet;
                                        starter = i;
                                        text = players.get(i).getName() + " has chosen to raise by $" + raiseAmount+"\n";
                                    }
                                    else{
                                        text = players.get(i).getName() + " has chosen to check\n";
                                    }
                                    break;
                            }
                            if(playersFold.get(i))
                            {
                                playerCirlesGUI.get(i).setFill(Color.GRAY);
                            }

                            //Updates UI
                            String finalText = text;
                            Platform.runLater(() -> {
                                updateChips(potSize, controller);
                                announcerTextArea.setText(finalText + announcerTextArea.getText());
                            });
                            botWinRate=0;
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
        System.out.println("Pot size:"+potSize);

        //Resets values for a new betting phase
        currentPlayerBets.replaceAll(ignored -> 0);
        betFollow=0;
        System.out.println("Current Bets: "+currentPlayerBets);
    }

    //Shows the player their best possible hand
    private void displayBestPlayerHand(PokerController controller, Player player, ArrayList<Card> communityCards){
        ArrayList<Card> allCards = new ArrayList<>();
        ImageView[] cardDisplays = {controller.getBestHandC1(),controller.getBestHandC2(),controller.getBestHandC3(),controller.getBestHandC4(),controller.getBestHandC5()};
        allCards.add(player.getHand().getCards().get(0));
        allCards.add(player.getHand().getCards().get(1));
        allCards.addAll(communityCards);
        System.out.println("All Cards (Inside first): "+allCards);
        Platform.runLater(() -> {

            controller.getCurrentBestHandName().setText(getPlayerRankName(HandRanks.bestHand(allCards)));
            Image[] images =HandRanks.getBestHandImages(allCards);
            for(int i =0;i<cardDisplays.length;i++){
                cardDisplays[i].setImage(images[i]);
            }
        });
    }

    //Gets the winner of the round
    private void getRoundWinner(PokerController controller){
        String text;
        int winnerAmount;
        float max = playerRanks.getFirst();  // Get the first player's rank
        ArrayList<Integer> winners = new ArrayList<>();

        // Find the maximum rank
        for (int i = 1; i < players.size(); i++) {
            if ((playerRanks.get(i) > max) && !playersFold.get(i)) {
                max = playerRanks.get(i);
            }
        }

        // Add all players with the maximum rank to the winners list
        for (int i = 0; i < players.size(); i++) {
            if ((playerRanks.get(i) == max) && !playersFold.get(i)) {
                winners.add(i);
            }
        }

        // If there are multiple winners, determine the highest individual hand rank
        if (winners.size() > 1) {
            max = HandRanks.getIndividualHandRank(players.get(winners.getFirst()));  // Start with the first winner's hand rank
            ArrayList<Integer> realWinners = new ArrayList<>();

            // Check for individual hand ranks to break ties
            for (int winnerIndex : winners) {
                float handRank = HandRanks.getIndividualHandRank(players.get(winnerIndex));

                if (handRank > max) {
                    max = handRank;
                    realWinners.clear();  // Clear previous winners and add the new one
                    realWinners.add(winnerIndex);
                } else if (handRank == max) {
                    realWinners.add(winnerIndex);
                }
            }

            winners.clear(); //Clears in order to add the "proper" winner(s)
            winners.addAll(realWinners);  // Update winners list with final winners
            System.out.println("Winners: " + winners);
        }

        // Calculate winner amount
        winnerAmount = potSize / winners.size();

        // Distribute the pot to each winner
        for (Integer winner : winners) {
            if(players.get(winner).getName().equals("Player"))
            {
                AudioManager.playSound("Win");
            }
            text = players.get(winner).getName() + " Wins!\n";
            String finalText = text;
            playerChips.set(winner, playerChips.get(winner) + winnerAmount);
            Platform.runLater(() -> announcerTextArea.setText(finalText + announcerTextArea.getText()));
        }

        // Update the chips display after the round
        Platform.runLater(() -> updateChips(potSize, controller));
    }

    //Deals the community cards (first round)
    private void dealFlop(PokerController controller) {
        String text;
        String[] strings = {"first","second","third"};
        riverCards.clear();
        for(int i=0;i<3;i++)
        {
            riverCards.add(deck.deal());
            text = "The "+strings[i]+" river card is a "+riverCards.get(i).getRank().toString().toLowerCase()+" of "+riverCards.get(i).getSuit().toString().toLowerCase()+"\n";
            String finalText = text;
            Platform.runLater(() ->announcerTextArea.setText(finalText + announcerTextArea.getText()));
            if(burnCards>0)
            {
                for(int j=0;j<burnCards;j++)
                {
                    deck.deal();
                    Platform.runLater(() ->announcerTextArea.setText("A card was burned\n" + announcerTextArea.getText()));
                }
            }
        }

        //Card change GUI
        flipCardImage(controller.getRiverCard1(),getImage(riverCards.getFirst()));
        flipCardImage(controller.getRiverCard2(),getImage(riverCards.get(1)));
        flipCardImage(controller.getRiverCard3(),getImage(riverCards.get(2)));

    }

    //Deals the community cards (After first rounds)
    private void dealPostFlop(PokerController controller,boolean flop) {
        String text;
        riverCards.add(deck.deal());
        if (burnCards > 0) {
            for (int j = 0; j < burnCards; j++) {
                deck.deal();
                Platform.runLater(() ->announcerTextArea.setText("A card was burned\n" + announcerTextArea.getText()));
            }
        }
        if(flop) //Second round
        {
            flipCardImage(controller.getRiverCard4(),getImage(riverCards.get(3)));
            text = "The fourth river card is a "+riverCards.get(3).getRank().toString().toLowerCase()+" of "+riverCards.get(3).getSuit().toString().toLowerCase() + "\n";
            String finalText1 = text;
            Platform.runLater(() -> announcerTextArea.setText(finalText1 + announcerTextArea.getText()));
        }
        else //Third round
        {
            flipCardImage(controller.getRiverCard5(),getImage(riverCards.get(4)));
            text = "The fifth river card is a "+riverCards.get(4).getRank().toString().toLowerCase()+" of "+riverCards.get(4).getSuit().toString().toLowerCase()+"\n";
            String finalText2 = text;
            Platform.runLater(() ->announcerTextArea.setText(finalText2+announcerTextArea.getText()));
        }
    }

    //Automatically places the big blind and small binds minimum chips into the pot
    private void placeBlinds(PokerController controller){
        currentPlayerBets.set(smallBlindIndex, smallBlindAmount);
        currentPlayerBets.set(bigBlindIndex, bigBlindAmount);
        System.out.println("Current Bets: "+currentPlayerBets);
        betFollow=bigBlindAmount;
        potSize = smallBlindAmount+bigBlindAmount;
        playerChips.set(smallBlindIndex,playerChips.get(smallBlindIndex)-smallBlindAmount);
        playerChips.set(bigBlindIndex, playerChips.get(bigBlindIndex)-bigBlindAmount);
        Platform.runLater(() -> updateChips(potSize, controller));
    }

    //Updates all the players chips
    private void updateChips(int pot, PokerController  controller){
        for(int i=0;i<players.size();i++)
        {
            chipLabels.get(i).setText(""+playerChips.get(i));
        }
        controller.getPot().setText(""+pot);
    }

    //Gets all the player rankings
    private void rankings() {
        for (int i = 1; i < players.size(); i++) {
            flipCardImage(card1.get(i),getImage(players.get(i).getHand().getCards().getFirst()));
            flipCardImage(card2.get(i),getImage(players.get(i).getHand().getCards().getLast()));
            System.out.println(players.get(i).getName() + ": " + players.get(i).getHand());
        }
        for (Player player : players) {
            playerRanks.add(bestHand(new ArrayList<Card>(List.of(new Card[]{player.getHand().getCards().getFirst(), player.getHand().getCards().getLast(), riverCards.get(0), riverCards.get(1), riverCards.get(2), riverCards.get(3), riverCards.get(4)}))));
            System.out.println(playerRanks);
        }
    }

    //Using the player rank values the name of the player rank is displayed using the Announcer
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

    //Used in the UI Best Hand Rank
    private String getPlayerRankName(float playerRank) {
        int rank = (int) playerRank;
        switch (rank) {
            case 10:
                return "Royal Flush";
            case 9:
                return "Straight Flush";
            case 8:
                return "Four of a Kind";
            case 7:
                return "Full House";
            case 6:
                return "Flush";
            case 5:
                return "Straight";
            case 4:
                return "Three of a Kind";
            case 3:
                return "Two Pair";
            case 2:
                return "One Pair";
            case 1:
                return "High Card";
            default:
                return "Unknown Rank";
        }
    }

    //Shows the player rankings on the GUI
    private void displayFinalRankings(){
        for(int i=0;i<players.size();i++)
        {
            String text = players.get(i).getName()+" has "+ playerRankNames.get(i)+"\n";
            Platform.runLater(() ->announcerTextArea.setText(text + announcerTextArea.getText()));
        }
    }

    //Clearing up all variables and resetting the game for the next round
    private void endGame(PokerController controller) {
        potSize=0;
        for(int i=0;i<players.size();i++)
        {
            if(playerChips.get(i)==0)
            {
                playerChips.set(i,Integer.parseInt(controller.getStartingChipsTextArea().getText()));
            }
        }
        Platform.runLater(() -> {
            updateChips(potSize, controller);
            controller.getPot().setText(""+potSize);
            dealerLabels.get(dealerIndex).setVisible(false);
            smallBlindLabels.get(smallBlindIndex).setVisible(false);
            bigBlindLabels.get(bigBlindIndex).setVisible(false);
        });
    }

    //Card animations
    private void flipCardImage(ImageView imageView, Image image){
        if(SettingsManager.settings.showAnimations) {
            Platform.runLater(() -> {
                RotateTransition rotateOut = new RotateTransition(Duration.millis(220), imageView);
                rotateOut.setAxis(Rotate.Y_AXIS);
                rotateOut.setFromAngle(0);
                rotateOut.setToAngle(90);
                rotateOut.setOnFinished(event -> {
                    imageView.setImage(image);
                    RotateTransition rotateIn = new RotateTransition(Duration.millis(220), imageView);
                    rotateIn.setAxis(Rotate.Y_AXIS);
                    rotateIn.setFromAngle(90);
                    rotateIn.setToAngle(0);
                    rotateIn.play();
                });
                rotateOut.play();
            });
        } else{
            imageView.setImage(image);
        }
    }

    //Getters
    static ArrayList<Integer> getPlayerChips() {
        return playerChips;
    }

    static ArrayList<Label> getDealerLabels(){
        return dealerLabels;
    }

    static ArrayList<Label> getSmallBlindLabels(){
        return smallBlindLabels;
    }

    static ArrayList<Label> getBigBlindLabels(){
        return bigBlindLabels;
    }

    static ArrayList<ImageView> getCard1() {
        return card1;
    }

    static ArrayList<ImageView> getCard2() {
        return card2;
    }

    static ArrayList<Label> getChipLabels() {
        return chipLabels;
    }

    static ArrayList<Circle> getPlayerTurnCircles() {
        return playerTurnCircles;
    }

    private static void setBotWinRate(int winRate){
        botWinRate=winRate;
    }

    
}
