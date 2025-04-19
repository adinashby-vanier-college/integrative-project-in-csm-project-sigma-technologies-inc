package com.example.sigmacasino.Menus;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PokerRulesController {
    @FXML private Hyperlink pokerHandHyperlink;
    @FXML private Hyperlink inDepthRulesHyperLink;

    @FXML
    private void initialize() {

        //Initializes HyperLinks
        pokerHandHyperlink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.cardschat.com/poker/strategy/poker-hands/"));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        inDepthRulesHyperLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://upswingpoker.com/poker-rules/texas-holdem-rules/"));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });
    }
}
