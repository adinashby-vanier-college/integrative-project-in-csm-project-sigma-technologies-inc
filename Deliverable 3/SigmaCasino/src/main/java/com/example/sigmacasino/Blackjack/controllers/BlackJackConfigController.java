package com.example.sigmacasino.Blackjack.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class BlackJackConfigController {
    @FXML
    private ColorPicker colorPicker;
    public ColorPicker colorPicker(){
        return colorPicker;
    }
    @FXML
    private CheckBox reshuffleBtn;
    @FXML
    private Button saveBtn;

    @FXML
    private void initialize() {

        colorPicker.setValue(Color.GREEN);

        saveBtn.setOnAction(event -> {
            Stage thisStage = (Stage) saveBtn.getScene().getWindow();
            thisStage.close();
        });
    }

    }


