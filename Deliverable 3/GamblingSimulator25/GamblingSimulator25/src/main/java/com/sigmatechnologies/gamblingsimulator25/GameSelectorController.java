package com.sigmatechnologies.gamblingsimulator25;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;

public class GameSelectorController {

    @FXML
    private ImageView pokerIV;
    @FXML
    private ImageView blackjackIV;
    @FXML
    private ImageView rouletteIV;
    @FXML
    private ImageView dicesIV;

    public void initialize() {

        Image pokerI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/poker.png")));
        Image blackjackI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/blackjack.png")));
        Image rouletteI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/roulette.png")));
        Image dicesI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/dices.png")));

        pokerIV.setImage(pokerI);
        blackjackIV.setImage(blackjackI);
        rouletteIV.setImage(rouletteI);
        dicesIV.setImage(dicesI);

    }

}