package com.example.sigmacasino.Menus;

import com.example.sigmacasino.Settings.SettingsManager;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

import java.io.Console;
import java.util.Set;

public class SettingsController {
    @FXML private CheckBox showUICheckBox;
    @FXML private Slider soundSlider;
    @FXML private CheckBox showAnimationsCheckBox;
    @FXML private Button applyButton;


    @FXML
    private void initialize()
    {
        SettingsManager.loadSettings();
        showUICheckBox.setSelected(SettingsManager.settings.isShowUIStats );
        soundSlider.setValue(SettingsManager.settings.volume);
        showAnimationsCheckBox.setSelected(SettingsManager.settings.showAnimations);

        applyButton.setOnAction(event ->{
            SettingsManager.settings.isShowUIStats = showUICheckBox.isSelected();
            SettingsManager.settings.volume = (float) soundSlider.getValue();
            SettingsManager.settings.showAnimations = showAnimationsCheckBox.isSelected();

            SettingsManager.saveSettings();
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
}