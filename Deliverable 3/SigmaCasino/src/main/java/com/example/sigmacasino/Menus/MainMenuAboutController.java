package com.example.sigmacasino.Menus;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainMenuAboutController {

    @FXML
    private Hyperlink jCards;

    @FXML
    private void initialize() {
        //Initializes HyperLinks
        jCards.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/lyudaio/jcards"));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });
    }
}
