package com.example.sigmacasino.Poker;

import com.example.sigmacasino.SigmaCasinoMain;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PokerController {
    @FXML private Spinner<Integer> SpinnerBots;
    @FXML private ChoiceBox<String> ChoiceBoxBruntCards;

    @FXML private TextArea announcerTextArea;

    @FXML private Circle bot2Turn;
    @FXML private Circle bot1Turn;
    @FXML private Circle bot4Turn;
    @FXML private Circle bot5Turn;
    @FXML private Circle playerTurn;
    @FXML private Circle bot3Turn;

    @FXML private MenuItem gameSelect;
    @FXML private MenuItem menuQuit;


    @FXML private CheckBox startRound;
    @FXML private CheckBox hideChips;

    @FXML private Button checkButton;
    @FXML private Button foldButton;
    @FXML private Button raiseButton;

    @FXML private TextField startingChips;
    @FXML private TextField potText;
    @FXML private TextField raiseText;

    @FXML private ImageView riverCard1;
    @FXML private ImageView riverCard2;
    @FXML private ImageView riverCard3;
    @FXML private ImageView riverCard4;
    @FXML private ImageView riverCard5;

    @FXML private ImageView playerCard1;
    @FXML private ImageView playerCard2;
    @FXML private ImageView bot1Card1;
    @FXML private ImageView bot1Card2;
    @FXML private ImageView bot2Card1;
    @FXML private ImageView bot2Card2;
    @FXML private ImageView bot3Card1;
    @FXML private ImageView bot3Card2;
    @FXML private ImageView bot4Card1;
    @FXML private ImageView bot4Card2;
    @FXML private ImageView bot5Card1;
    @FXML private ImageView bot5Card2;

    @FXML private Ellipse brownEllipse;
    @FXML private Ellipse greenEllipse;
    @FXML private Circle playerCircle;
    @FXML private Circle botCircle1;
    @FXML private Circle botCircle2;
    @FXML private Circle botCircle3;
    @FXML private Circle botCircle4;
    @FXML private Circle botCircle5;

    @FXML private Label namePlayer;
    @FXML private Label nameBot1;
    @FXML private Label nameBot2;
    @FXML private Label nameBot3;
    @FXML private Label nameBot4;
    @FXML private Label nameBot5;

    @FXML private Label chipsPlayer;
    @FXML private Label chipsBot1;
    @FXML private Label chipsBot2;
    @FXML private Label chipsBot3;
    @FXML private Label chipsBot4;
    @FXML private Label chipsBot5;

    @FXML private CheckBox checkBox;
    @FXML private CheckBox foldBox;
    @FXML private CheckBox raiseBox;

    @FXML private Label smallBlindLabelPlayer;
    @FXML private Label smallBlindLabelBot1;
    @FXML private Label smallBlindLabelBot2;
    @FXML private Label smallBlindLabelBot3;
    @FXML private Label smallBlindLabelBot4;
    @FXML private Label smallBlindLabelBot5;

    @FXML private Label bigBlindLabelPlayer;
    @FXML private Label bigBlindLabelBot1;
    @FXML private Label bigBlindLabelBot2;
    @FXML private Label bigBlindLabelBot3;
    @FXML private Label bigBlindLabelBot4;
    @FXML private Label bigBlindLabelBot5;


    @FXML private Label dealerLabelPlayer;
    @FXML private Label dealerLabelBot1;
    @FXML private Label dealerLabelBot2;
    @FXML private Label dealerLabelBot3;
    @FXML private Label dealerLabelBot4;
    @FXML private Label dealerLabelBot5;

    @FXML private Label playerTimeLimitLabel;
    @FXML private Label timeRemainingLabel;
    @FXML private Label secondsLabel;
    @FXML private Label winPercentageLabel;
    @FXML private Label checkProbabilityLabel;
    @FXML private Label foldProbabilityLabel;
    @FXML private Label raiseProbabilityLabel;


    @FXML private LineChart<Number, Number> lineChart;
    @FXML private NumberAxis xAxis;
    @FXML private NumberAxis yAxis;

    @FXML private ImageView bestHandC1;
    @FXML private ImageView bestHandC2;
    @FXML private ImageView bestHandC3;
    @FXML private ImageView bestHandC4;
    @FXML private ImageView bestHandC5;
    @FXML private Label currentBestHandName;

    @FXML private MenuItem pokerRulesMenuItem;
    @FXML private MenuItem settingsMenuItem;

    XYChart.Series<Number, Number> series;
    ImageView[] imageViews;
    Circle[] botTurns;
    Label[] chips;
    Label[] dealerLabels;
    Label[] smallBlindLabels;
    Label[] bigBlindLabels;

    @FXML
    public void initialize() throws IOException {
        //Clears all PokerGame assets
        PokerGame.getPlayerChips().clear();
        PokerGame.getDealerLabels().clear();
        PokerGame.getSmallBlindLabels().clear();
        PokerGame.getBigBlindLabels().clear();
        PokerGame.getCard1().clear();
        PokerGame.getCard2().clear();
        PokerGame.getChipLabels().clear();
        PokerGame.getPlayerTurnCircles().clear();

        //Initializing all variables
        imageViews = new ImageView[]{bestHandC1, bestHandC2, bestHandC3, bestHandC4, bestHandC5, riverCard1, riverCard2, riverCard3, riverCard4, riverCard5
                , playerCard1, playerCard2, bot1Card1, bot1Card2, bot2Card1, bot2Card2, bot3Card1, bot3Card2
                , bot4Card1, bot4Card2, bot5Card1, bot5Card2};

        dealerLabels = new Label[]{dealerLabelPlayer,dealerLabelBot1,dealerLabelBot2,dealerLabelBot3,dealerLabelBot4,dealerLabelBot5};
        smallBlindLabels = new Label[]{smallBlindLabelPlayer,smallBlindLabelBot1,smallBlindLabelBot2,smallBlindLabelBot3,smallBlindLabelBot4,smallBlindLabelBot5};
        bigBlindLabels = new Label[]{bigBlindLabelPlayer,bigBlindLabelBot1,bigBlindLabelBot2,bigBlindLabelBot3,bigBlindLabelBot4,bigBlindLabelBot5};
        botTurns = new Circle[]{playerTurn, bot1Turn, bot2Turn, bot3Turn, bot4Turn, bot5Turn};
        chips = new Label[]{chipsPlayer, chipsBot1, chipsBot2, chipsBot3, chipsBot4, chipsBot5};

        getSecondsLabel().setVisible(false);
        getTimeRemainingLabel().setVisible(false);
        getPlayerTimeLimitLabel().setVisible(false);


        //Initializes Graph
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(3);
        xAxis.setTickUnit(1);

        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);
        yAxis.setTickUnit(10);

        series = new XYChart.Series<>();
        series.setName("Percentage Data");
        lineChart.getData().add(series);

        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                return String.format("%.0f%%", object.doubleValue());
            }
        });

        //Hides dealer labels
        for (Label dealerLabel : dealerLabels) {
            dealerLabel.setVisible(false);
        }

        //Hides blind labels
        for (Label blindLabel : smallBlindLabels) {
            blindLabel.setVisible(false);
        }
        for (Label blindLabel : bigBlindLabels) {
            blindLabel.setVisible(false);
        }

        //Hides turn circles
        for (Circle botTurn : botTurns) {
            botTurn.setVisible(false);
        }

        //Hides extra bots
        onBotNumberChange();

        //Sets burnt cards value
        ChoiceBoxBruntCards.setValue("No Card");

        //Presets images (remove later)
        for (ImageView imageView : imageViews) {
            setDefaultImage(imageView);
        }

        //Initial starting chips
        startingChips.setText("1000");

        //Initial pot
        potText.setText("0");

        //Initial raise
        raiseText.setText("0");

        //Sets player chips
        for (Label chip : chips) {
            chip.setText(startingChips.getText());
        }

        //Sets the low bound for the starting chips
        startingChips.textProperty().addListener((observable, oldValue, newValue) -> {
            int value;
            try{
                value = Integer.parseInt(newValue);
                if(value<=1000)
                {
                    throw new Exception();
                }
            } catch (Exception e) {
                value = Integer.parseInt(oldValue);
            }
            startingChips.setText(String.valueOf(value));
            for (Label chip : chips) {
                chip.setText(""+value);
            }
            PokerGame.getPlayerChips().clear();
            PokerGame.getDealerLabels().clear();
            PokerGame.getSmallBlindLabels().clear();
            PokerGame.getBigBlindLabels().clear();
            PokerGame.getCard1().clear();
            PokerGame.getCard2().clear();
            PokerGame.getChipLabels().clear();
            PokerGame.getPlayerTurnCircles().clear();
        });

        //Add and remove bots
        SpinnerBots.valueProperty().addListener((obs, oldValue, newValue) -> {
            onBotNumberChange();
            PokerGame.getPlayerChips().clear();
            PokerGame.getDealerLabels().clear();
            PokerGame.getSmallBlindLabels().clear();
            PokerGame.getBigBlindLabels().clear();
            PokerGame.getCard1().clear();
            PokerGame.getCard2().clear();
            PokerGame.getChipLabels().clear();
            PokerGame.getPlayerTurnCircles().clear();
        });

        //Starts poker game/round
        startRound.setOnAction(actionEvent -> {
            if(startRound.isSelected()) {
                for (Thread t : Thread.getAllStackTraces().keySet()) {
                    System.out.println("Thread Name: " + t.getName() + " | State: " + t.getState());
                }
                new Thread(()->{
                    PokerGame game = new PokerGame(this);
                    game.playGame(this);
                    startRound.setSelected(false);
                }).start();
            }
        });

        //Retrieves the amount of chips that the player has raised
        raiseText.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                if(!newValue.isEmpty()) {
                    int value = Integer.parseInt(newValue);
                    if (value < 0) {
                        throw new Exception();
                    } else if (value > Integer.parseInt(chipsPlayer.getText())) {
                        raiseText.setText(chipsPlayer.getText());
                    }
                }
            } catch (Exception e) {
                raiseText.setText(oldValue);
            }
        });

        //Goes back to game selection menu
        gameSelect.setOnAction(event -> {
            try {
                //Closes unnecessary threads
                ArrayList<String> openThreads = new ArrayList<>(List.of(new String[]{"Monitor Ctrl-Break", "Notification Thread", "InvokeLaterDispatcher", "Cleaner-0", "JavaFX Application Thread", "Common-Cleaner",
                        "Attach Listener", "Prism Font Disposer", "Reference Handler", "QuantumRenderer-0", "Signal Dispatcher", "main", "Finalizer", "JavaFX-Launcher"}));
                for (Thread t : Thread.getAllStackTraces().keySet()) {
                    if (!openThreads.contains(t.getName())) {
                        System.out.println("Found Thread-1 with ID: " + t.getId());
                        t.interrupt();
                        break;
                    }
                }
                switchToScene(event, "/com/example/sigmacasino/UI/game-selector.fxml",null,true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //Closes application
        menuQuit.setOnAction(event -> {
            try {
                //Closes unnecessary threads
                ArrayList<String> openThreads = new ArrayList<>(List.of(new String[]{"Monitor Ctrl-Break", "Notification Thread", "InvokeLaterDispatcher", "Cleaner-0", "JavaFX Application Thread", "Common-Cleaner",
                        "Attach Listener", "Prism Font Disposer", "Reference Handler", "QuantumRenderer-0", "Signal Dispatcher", "main", "Finalizer", "JavaFX-Launcher"}));
                for (Thread t : Thread.getAllStackTraces().keySet()) {
                    if (!openThreads.contains(t.getName())) {
                        System.out.println("Found Thread-1 with ID: " + t.getId());
                        t.interrupt();
                        break;
                    }
                }
                Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
                stage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        //Shows the poker rules menu
        pokerRulesMenuItem.setOnAction(event ->{
            try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(SigmaCasinoMain.class.getResource("/com/example/sigmacasino/UI/PokerRules.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("Rules/Guide");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.showAndWait();
            } catch (Exception e) {
                System.out.println("Error switching to rules");
            }
        });

        //Shows the poker settings menu
        settingsMenuItem.setOnAction(event -> {
            try {
                switchToScene(event, "/com/example/sigmacasino/UI/settings.fxml",null,false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //Check, Fold and Raise event handlers
        checkBox.setOnAction(event -> {
            if(checkBox.isSelected()) {
                foldBox.setSelected(false);
                raiseBox.setSelected(false);
            }
        });

        foldBox.setOnAction(event -> {
            if(foldBox.isSelected()) {
                checkBox.setSelected(false);
                raiseBox.setSelected(false);
            }
        });

        raiseBox.setOnAction(event -> {
            if(raiseBox.isSelected()) {
                foldBox.setSelected(false);
                checkBox.setSelected(false);
            }
        });

    }

    //Sets the initial image of the cards
     void setDefaultImage(ImageView imageView){
        File file = new File("src/main/resources/com/example/sigmacasino/Sprites/PNG-cards-1.3/back_of_card.png");
        //System.out.println(file.toURI());
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
    }

    //Changes UI components (Player visuals) depending on how many bots there are
     void onBotNumberChange() {
        int bots = SpinnerBots.getValue();
        Circle[] circles = {botCircle1,botCircle2,botCircle3,botCircle4,botCircle5};
        Label[] names = {nameBot1,nameBot2,nameBot3,nameBot4,nameBot5};
        Label[] chips = {chipsPlayer,chipsBot1,chipsBot2,chipsBot3,chipsBot4,chipsBot5};
        ImageView[] card1 = {bot1Card1,bot2Card1,bot3Card1,bot4Card1,bot5Card1};
        ImageView[] card2 = {bot1Card2,bot2Card2,bot3Card2,bot4Card2,bot5Card2};

        for(int i=0;i<bots;i++) {
            circles[i].setVisible(true);
            card1[i].setVisible(true);
            card2[i].setVisible(true);
            names[i].setVisible(true);
            chips[i+1].setVisible(true);
        }

        for(int i=bots;i<circles.length;i++)
        {
            circles[i].setVisible(false);
            card1[i].setVisible(false);
            card2[i].setVisible(false);
            names[i].setVisible(false);
            chips[i+1].setVisible(false);
        }

    }

    //Method for changing the FXML file of the stage
    private void switchToScene(Event event, String fxmlFile, Object controller, boolean minimize ) throws IOException {
        System.out.println("Fxml: " + getClass().getResource((fxmlFile)));
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        if (controller != null) {
            loader.setController(controller);
        }
        Parent root = loader.load();
        Stage stage;
        if (event.getSource() instanceof Node) {
            // If the event source is a Node (ex: Button)
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } else {
            // If the event source is not a Node (ex:MenuItem)
            stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        if(minimize)
        {
            stage.setMaximized(false);
            stage.setHeight(440);
            stage.setWidth(620);
        }
        stage.show();
    }

    //Sets a value to the checkbox that is selected
     int getButtonValue() {
        int value;
        if (checkBox.isSelected()) {
            value = 0;
        } else if (foldBox.isSelected()) {
            value = -1;
        } else if (raiseBox.isSelected()) {
            System.out.println(raiseText.getText());
            try{
                value = Integer.parseInt(raiseText.getText());
            }
            catch(Exception e){
                value = 0;
            }
        } else {
            value = 0;
        }
        checkBox.setSelected(false);
        foldBox.setSelected(false);
        raiseBox.setSelected(false);
        return value;
    }

    //Getters (package access in order to not be used publicly)
    CheckBox getStartRoundCheckBox() {return startRound;}

    Spinner<Integer> getSpinnerBots(){
        return SpinnerBots;
    }

    ChoiceBox<String> getChoiceBoxBruntCards(){
        return ChoiceBoxBruntCards;
    }

    TextField getStartingChipsTextArea(){
        return startingChips;
    }

    TextField getRaiseTextArea(){
        return raiseText;
    }

    ImageView getPlayerCard1() {
        return playerCard1;
    }

    ImageView getPlayerCard2() {
        return playerCard2;
    }

    ImageView getBot1Card1() {
        return bot1Card1;
    }

    ImageView getBot1Card2() {
        return bot1Card2;
    }

    ImageView getBot2Card1() {
        return bot2Card1;
    }

    ImageView getBot2Card2() {
        return bot2Card2;
    }

    ImageView getBot3Card1() {
        return bot3Card1;
    }

    ImageView getBot3Card2() {
        return bot3Card2;
    }

    ImageView getBot4Card1() {
        return bot4Card1;
    }

    ImageView getBot4Card2() {
        return bot4Card2;
    }

    ImageView getBot5Card1() {
        return bot5Card1;
    }

    ImageView getBot5Card2() {
        return bot5Card2;
    }

    ImageView getRiverCard1() {
        return riverCard1;
    }

    ImageView getRiverCard2() {
        return riverCard2;
    }

    ImageView getRiverCard3() {
        return riverCard3;
    }

    ImageView getRiverCard4() {
        return riverCard4;
    }

    ImageView getRiverCard5() {
        return riverCard5;
    }

    TextArea getAnnouncerTextArea() {return announcerTextArea;}

    Label getChipsPlayer() {
        return chipsPlayer;
    }

    Label getChipsBot1() {
        return chipsBot1;
    }

    Label getChipsBot2() {
        return chipsBot2;
    }

    Label getChipsBot3() {
        return chipsBot3;
    }

    Label getChipsBot4() {
        return chipsBot4;
    }

    Label getChipsBot5() {
        return chipsBot5;
    }

    Label getSmallBlindLabelPlayer() {
        return smallBlindLabelPlayer;
    }

    Label getSmallBlindLabelBot1() {
        return smallBlindLabelBot1;
    }

    Label getSmallBlindLabelBot2() {
        return smallBlindLabelBot2;
    }

    Label getSmallBlindLabelBot3() {
        return smallBlindLabelBot3;
    }

    Label getSmallBlindLabelBot4() {
        return smallBlindLabelBot4;
    }

    Label getSmallBlindLabelBot5() {
        return smallBlindLabelBot5;
    }

    Label getBigBlindLabelPlayer() {
        return bigBlindLabelPlayer;
    }

    Label getBigBlindLabelBot1() {
        return bigBlindLabelBot1;
    }

    Label getBigBlindLabelBot2() {
        return bigBlindLabelBot2;
    }

    Label getBigBlindLabelBot3() {
        return bigBlindLabelBot3;
    }

    Label getBigBlindLabelBot4() {
        return bigBlindLabelBot4;
    }

    Label getBigBlindLabelBot5() {
        return bigBlindLabelBot5;
    }

    Label getDealerLabelPlayer() {
        return dealerLabelPlayer;
    }

    Label getDealerLabelBot1() {
        return dealerLabelBot1;
    }

    Label getDealerLabelBot2() {
        return dealerLabelBot2;
    }

    Label getDealerLabelBot3() {
        return dealerLabelBot3;
    }

    Label getDealerLabelBot4() {
        return dealerLabelBot4;
    }

    Label getDealerLabelBot5() {
        return dealerLabelBot5;
    }

    TextField getPot() {
        return potText;
    }

    Label getPlayerTimeLimitLabel() {
        return playerTimeLimitLabel;
    }

    Label getTimeRemainingLabel() {
        return timeRemainingLabel;
    }

    Label getSecondsLabel() {
        return secondsLabel;
    }

    LineChart<Number, Number> getLineChart(){
        return lineChart;
    }

    Label getWinPercentageLabel(){
        return winPercentageLabel;
    }

    Label getCheckProbabilityLabel() {
        return checkProbabilityLabel;
    }

    Label getFoldProbabilityLabel() {
        return foldProbabilityLabel;
    }

    Label getRaiseProbabilityLabel() {
        return raiseProbabilityLabel;
    }

    Circle getPlayerCircle() {
        return playerCircle;
    }

    Circle getBotCircle1() {
        return botCircle1;
    }

    Circle getBotCircle2() {
        return botCircle2;
    }

    Circle getBotCircle3() {
        return botCircle3;
    }

    Circle getBotCircle4() {
        return botCircle4;
    }

    Circle getBotCircle5() {
        return botCircle5;
    }

    MenuItem getGameSelect(){ return gameSelect; }

    ImageView getBestHandC1() {
        return bestHandC1;
    }

    ImageView getBestHandC2() {
        return bestHandC2;
    }

    ImageView getBestHandC3() {
        return bestHandC3;
    }

    ImageView getBestHandC4() {
        return bestHandC4;
    }

    ImageView getBestHandC5() {
        return bestHandC5;
    }

    Label getCurrentBestHandName() {return currentBestHandName;}

    MenuItem getPokerRulesMenuItem() { return pokerRulesMenuItem; }

    MenuItem getSettingsMenuItem() { return settingsMenuItem; }
}