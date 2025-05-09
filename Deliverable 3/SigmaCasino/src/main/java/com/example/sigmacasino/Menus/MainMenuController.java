package com.example.sigmacasino.Menus;

import com.example.sigmacasino.Auth.AuthManager;
import com.example.sigmacasino.Settings.SettingsManager;
import com.example.sigmacasino.SigmaCasinoMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
//jean valjean was here
public class MainMenuController {

    // Main menu
    @FXML private ImageView mainMenuIV;
    @FXML private Button playBtn;
    @FXML private Button loginButton;
    @FXML private Button registerButton;

    public void initialize() {

        File image = new File("src/main/resources/com/example/sigmacasino/Sprites/thumbails/menu.png");
        Image mainMenuI = new Image(image.toURI().toString());
        mainMenuIV.setImage(mainMenuI);
        SettingsManager.loadSettings();
        playBtn.setOnAction(event -> {
            try {
                AuthManager.current_username = "Guest";
                switchToScene(event, "/com/example/sigmacasino/UI/game-selector.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        loginButton.setOnAction(event -> {
            try {
                switchToScene(event, "/com/example/sigmacasino/UI/LoginPage.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        registerButton.setOnAction(event -> {
            try {
                switchToScene(event, "/com/example/sigmacasino/UI/SignUpPage.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

   @FXML
   protected void onPlayClick(ActionEvent event) {

        Node source = (Node) event.getSource();

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("src/main/resources/com/example/sigmacasino/UI/game-selector.fxml"));
            BorderPane root = loader.load();

            Stage stage = (Stage) source.getScene().getWindow();

            Scene newScene = new Scene(root);
            newScene.getStylesheets().add(getClass().getResource(SigmaCasinoMain.stylesPath).toExternalForm());
            stage.setScene(newScene);

        } catch (Exception e) {

            e.printStackTrace();

        }

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

    // About menu
    @FXML
    protected void onAboutClick(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(SigmaCasinoMain.class.getResource("/com/example/sigmacasino/UI/MainMenuAbout.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(getClass().getResource(SigmaCasinoMain.stylesPath).toExternalForm());
            stage.setTitle("About");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println("Error switching to about");
        }

    }

}