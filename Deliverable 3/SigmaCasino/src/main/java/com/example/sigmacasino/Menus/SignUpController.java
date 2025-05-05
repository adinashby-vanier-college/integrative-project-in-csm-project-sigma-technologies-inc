package com.example.sigmacasino.Menus;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.example.sigmacasino.Auth.AuthManager;

import java.io.IOException;

public class SignUpController {

    @FXML private Label goBackButton;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private Button signUpButton;
    @FXML private Label usernameError;
    @FXML private Label passwordError;


    @FXML
    private void initialize() {
        goBackButton.setOnMouseClicked(event -> {
            try {
                switchToScene(event, "/com/example/sigmacasino/UI/main-menu.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        signUpButton.setOnAction(event ->{

            if(usernameTextField.getText().length()<5){
                usernameError.setText("Username must be at least 5 characters long");
                usernameError.setVisible(true);
                return;
            }
            if(passwordTextField.getText().length()<7){
                passwordError.setVisible(true);
                return;
            }

            boolean sucess = AuthManager.register(usernameTextField.getText(), passwordTextField.getText());
            if(sucess)
            {
                try {
                    switchToScene(event, "/com/example/sigmacasino/UI/game-selector.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else
            {
                usernameError.setText("This username already exists");
                usernameError.setVisible(true);
            }
        });
    }

    public void switchToScene(MouseEvent event, String fxmlFile) throws IOException {
        System.out.println("Fxml: "+getClass().getResource((fxmlFile)));

        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = null;
        if (event.getSource() instanceof Node) {
            // If the event source is a Node (ex: Button)
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } else {
            // If the event source is not a Node (ex:MenuItem)
            stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public void switchToScene(ActionEvent event, String fxmlFile) throws IOException {
        System.out.println("Fxml: "+getClass().getResource((fxmlFile)));

        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = null;
        if (event.getSource() instanceof Node) {
            // If the event source is a Node (ex: Button)
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } else {
            // If the event source is not a Node (ex:MenuItem)
            stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
