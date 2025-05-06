package com.example.sigmacasino.Menus;

import com.example.sigmacasino.Auth.AuthManager;
import com.example.sigmacasino.SigmaCasinoMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {


    @FXML private Label goBackButton;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordTextField;
    @FXML private Button logInButton;

    @FXML
    private void initialize() {

        goBackButton.setOnMouseClicked(event -> {
            try {
                switchToScene(event, "/com/example/sigmacasino/UI/main-menu.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        logInButton.setOnAction(event ->{
            boolean sucess = AuthManager.login(usernameTextField.getText(), passwordTextField.getText());
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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Credential");
                alert.setContentText("The username entered does not exist or the password for this user is incorrect.");
                alert.showAndWait();
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
        scene.getStylesheets().add(getClass().getResource(SigmaCasinoMain.stylesPath).toExternalForm());
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
        scene.getStylesheets().add(getClass().getResource(SigmaCasinoMain.stylesPath).toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
