package com.sigmatechnologies.gamblingsimulator25;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;

public class MainMenuController {

    @FXML
    private ImageView mainMenuIV;

    public void initialize() {

        Image mainMenuI = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/sprites/menu.png")));
        mainMenuIV.setImage(mainMenuI);

    }

}