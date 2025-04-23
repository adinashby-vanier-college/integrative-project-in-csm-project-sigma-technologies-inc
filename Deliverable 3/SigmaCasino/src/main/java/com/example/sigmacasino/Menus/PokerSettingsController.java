package com.example.sigmacasino.Menus;

import com.example.sigmacasino.Poker.PokerController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class PokerSettingsController {
    @FXML private Label returnButton;

    @FXML
    public void initialize() throws IOException {
        returnButton.setOnMouseClicked(event -> {
            try {
                switchToScene(event, "/com/example/sigmacasino/UI/poker.fxml", new PokerController(),false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void switchToScene(Event event, String fxmlFile, Object controller, boolean maximize ) throws IOException {
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
        stage.show();
        if(maximize){
            stage.setMaximized(false);
            stage.setMaximized(true);
        }
    }
}
