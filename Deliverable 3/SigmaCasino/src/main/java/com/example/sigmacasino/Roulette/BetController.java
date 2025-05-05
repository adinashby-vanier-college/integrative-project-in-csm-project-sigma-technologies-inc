package com.example.sigmacasino.Roulette;

import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.lang.Math;
import java.util.Collections;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BetController {

    @FXML
    private TextField field00;
    @FXML
    private TextField field0;
    @FXML
    private TextField field1;
    @FXML
    private TextField field2;
    @FXML
    private TextField field3;
    @FXML
    private TextField field4;
    @FXML
    private TextField field5;
    @FXML
    private TextField field6;
    @FXML
    private TextField field7;
    @FXML
    private TextField field8;
    @FXML
    private TextField field9;
    @FXML
    private TextField field10;
    @FXML
    private TextField field11;
    @FXML
    private TextField field12;
    @FXML
    private TextField field13;
    @FXML
    private TextField field14;
    @FXML
    private TextField field15;
    @FXML
    private TextField field16;
    @FXML
    private TextField field17;
    @FXML
    private TextField field18;
    @FXML
    private TextField field19;
    @FXML
    private TextField field20;
    @FXML
    private TextField field21;
    @FXML
    private TextField field22;
    @FXML
    private TextField field23;
    @FXML
    private TextField field24;
    @FXML
    private TextField field25;
    @FXML
    private TextField field26;
    @FXML
    private TextField field27;
    @FXML
    private TextField field28;
    @FXML
    private TextField field29;
    @FXML
    private TextField field30;
    @FXML
    private TextField field31;
    @FXML
    private TextField field32;
    @FXML
    private TextField field33;
    @FXML
    private TextField field34;
    @FXML
    private TextField field35;
    @FXML
    private TextField field36;
    @FXML
    private TextField field37;

    public static ArrayList<Integer> bets = new ArrayList<>(38);

    public void onBetClick(ActionEvent actionEvent) {

        bets.addAll(Collections.nCopies(38, 0));

        try {

            for (int i = 0; i <= 37; i++) {

                switch (i) {

                    case 0:
                        if (!field0.getText().matches("\\d+") || field0.getText().isBlank()) break;
                        bets.set(0, Math.abs(Integer.parseInt(field0.getText())));
                        break;

                    case 1:
                        if (!field1.getText().matches("\\d+") || field1.getText().isBlank()) break;
                        bets.set(1, Math.abs(Integer.parseInt(field1.getText())));
                        break;

                    case 2:
                        if (!field2.getText().matches("\\d+") || field2.getText().isBlank()) break;
                        bets.set(2, Math.abs(Integer.parseInt(field2.getText())));
                        break;

                    case 3:
                        if (!field3.getText().matches("\\d+") || field3.getText().isBlank()) break;
                        bets.set(3, Math.abs(Integer.parseInt(field3.getText())));
                        break;

                    case 4:
                        if (!field4.getText().matches("\\d+") || field4.getText().isBlank()) break;
                        bets.set(4, Math.abs(Integer.parseInt(field4.getText())));
                        break;

                    case 5:
                        if (!field5.getText().matches("\\d+") || field5.getText().isBlank()) break;
                        bets.set(5, Math.abs(Integer.parseInt(field5.getText())));
                        break;

                    case 6:
                        if (!field6.getText().matches("\\d+") || field6.getText().isBlank()) break;
                        bets.set(6, Math.abs(Integer.parseInt(field6.getText())));
                        break;

                    case 7:
                        if (!field7.getText().matches("\\d+") || field7.getText().isBlank()) break;
                        bets.set(7, Math.abs(Integer.parseInt(field7.getText())));
                        break;

                    case 8:
                        if (!field8.getText().matches("\\d+") || field8.getText().isBlank()) break;
                        bets.set(8, Math.abs(Integer.parseInt(field8.getText())));
                        break;

                    case 9:
                        if (!field9.getText().matches("\\d+") || field9.getText().isBlank()) break;
                        bets.set(9, Math.abs(Integer.parseInt(field9.getText())));
                        break;

                    case 10:
                        if (!field10.getText().matches("\\d+") || field10.getText().isBlank()) break;
                        bets.set(10, Math.abs(Integer.parseInt(field10.getText())));
                        break;

                    case 11:
                        if (!field11.getText().matches("\\d+") || field11.getText().isBlank()) break;
                        bets.set(11, Math.abs(Integer.parseInt(field11.getText())));
                        break;

                    case 12:
                        if (!field12.getText().matches("\\d+") || field12.getText().isBlank()) break;
                        bets.set(12, Math.abs(Integer.parseInt(field12.getText())));
                        break;

                    case 13:
                        if (!field13.getText().matches("\\d+") || field13.getText().isBlank()) break;
                        bets.set(13, Math.abs(Integer.parseInt(field13.getText())));
                        break;

                    case 14:
                        if (!field14.getText().matches("\\d+") || field14.getText().isBlank()) break;
                        bets.set(14, Math.abs(Integer.parseInt(field14.getText())));
                        break;

                    case 15:
                        if (!field15.getText().matches("\\d+") || field15.getText().isBlank()) break;
                        bets.set(15, Math.abs(Integer.parseInt(field15.getText())));
                        break;

                    case 16:
                        if (!field16.getText().matches("\\d+") || field16.getText().isBlank()) break;
                        bets.set(16, Math.abs(Integer.parseInt(field16.getText())));
                        break;

                    case 17:
                        if (!field17.getText().matches("\\d+") || field17.getText().isBlank()) break;
                        bets.set(17, Math.abs(Integer.parseInt(field17.getText())));
                        break;

                    case 18:
                        if (!field18.getText().matches("\\d+") || field18.getText().isBlank()) break;
                        bets.set(18, Math.abs(Integer.parseInt(field18.getText())));
                        break;

                    case 19:
                        if (!field19.getText().matches("\\d+") || field19.getText().isBlank()) break;
                        bets.set(19, Math.abs(Integer.parseInt(field19.getText())));
                        break;

                    case 20:
                        if (!field20.getText().matches("\\d+") || field20.getText().isBlank()) break;
                        bets.set(20, Math.abs(Integer.parseInt(field20.getText())));
                        break;

                    case 21:
                        if (!field21.getText().matches("\\d+") || field21.getText().isBlank()) break;
                        bets.set(21, Math.abs(Integer.parseInt(field21.getText())));
                        break;

                    case 22:
                        if (!field22.getText().matches("\\d+") || field22.getText().isBlank()) break;
                        bets.set(22, Math.abs(Integer.parseInt(field22.getText())));
                        break;

                    case 23:
                        if (!field23.getText().matches("\\d+") || field23.getText().isBlank()) break;
                        bets.set(23, Math.abs(Integer.parseInt(field23.getText())));
                        break;

                    case 24:
                        if (!field24.getText().matches("\\d+") || field24.getText().isBlank()) break;
                        bets.set(24, Math.abs(Integer.parseInt(field24.getText())));
                        break;

                    case 25:
                        if (!field25.getText().matches("\\d+") || field25.getText().isBlank()) break;
                        bets.set(25, Math.abs(Integer.parseInt(field25.getText())));
                        break;

                    case 26:
                        if (!field26.getText().matches("\\d+") || field26.getText().isBlank()) break;
                        bets.set(26, Math.abs(Integer.parseInt(field26.getText())));
                        break;

                    case 27:
                        if (!field27.getText().matches("\\d+") || field27.getText().isBlank()) break;
                        bets.set(27, Math.abs(Integer.parseInt(field27.getText())));
                        break;

                    case 28:
                        if (!field28.getText().matches("\\d+") || field28.getText().isBlank()) break;
                        bets.set(28, Math.abs(Integer.parseInt(field28.getText())));
                        break;

                    case 29:
                        if (!field29.getText().matches("\\d+") || field29.getText().isBlank()) break;
                        bets.set(29, Math.abs(Integer.parseInt(field29.getText())));
                        break;

                    case 30:
                        if (!field30.getText().matches("\\d+") || field30.getText().isBlank()) break;
                        bets.set(30, Math.abs(Integer.parseInt(field30.getText())));
                        break;

                    case 31:
                        if (!field31.getText().matches("\\d+") || field31.getText().isBlank()) break;
                        bets.set(31, Math.abs(Integer.parseInt(field31.getText())));
                        break;

                    case 32:
                        if (!field32.getText().matches("\\d+") || field32.getText().isBlank()) break;
                        bets.set(32, Math.abs(Integer.parseInt(field32.getText())));
                        break;

                    case 33:
                        if (!field33.getText().matches("\\d+") || field33.getText().isBlank()) break;
                        bets.set(33, Math.abs(Integer.parseInt(field33.getText())));
                        break;

                    case 34:
                        if (!field34.getText().matches("\\d+") || field34.getText().isBlank()) break;
                        bets.set(34, Math.abs(Integer.parseInt(field34.getText())));
                        break;

                    case 35:
                        if (!field35.getText().matches("\\d+") || field35.getText().isBlank()) break;
                        bets.set(35, Math.abs(Integer.parseInt(field35.getText())));
                        break;

                    case 36:
                        if (!field36.getText().matches("\\d+") || field36.getText().isBlank()) break;
                        bets.set(36, Math.abs(Integer.parseInt(field36.getText())));
                        break;

                    case 37:
                        if (!field00.getText().matches("\\d+") || field00.getText().isBlank()) break;
                        bets.set(37, Math.abs(Integer.parseInt(field00.getText())));
                        System.out.println(bets.get(37));
                        break;

                }

            }

        } catch (NumberFormatException e) {

            e.printStackTrace();

        }

    }

}