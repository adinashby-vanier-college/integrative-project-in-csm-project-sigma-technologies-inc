package com.example.sigmacasino.Menus;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

public class SettingsController {
    @FXML private CheckBox showUICheckBox;
    @FXML private Slider soundSlider;
    @FXML private CheckBox showAnimationsCheckBox;
    @FXML private ColorPicker backgroundColor;
    @FXML private Button applyButton;


    @FXML
    private void initialize() {
        soundSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Slider value changed from " + oldValue + " to " + newValue);
        });

        applyButton.setOnAction(event ->{
            
        });
    }

    public boolean getUICheckBoxValue(){
        return showUICheckBox.isSelected();
    }

    public double getSoundSliderValue(){
        return soundSlider.getValue();
    }

    public boolean getAnimationValue(){
        return showAnimationsCheckBox.isSelected();
    }

    public ObjectProperty<Color> getBackgroundColor() {
        return backgroundColor.valueProperty();
    }
}