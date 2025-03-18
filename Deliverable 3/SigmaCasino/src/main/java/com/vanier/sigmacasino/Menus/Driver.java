package java.com.vanier.sigmacasino.Menus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Driver extends Application {

    public static void main(String[] args) {launch(args);}

    public void start(Stage mainMenu) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sigmatechnologies/gamblingsimulator25/main-menu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        mainMenu.setTitle("Gambling Simulator 25 (W.I.P.)");
        mainMenu.setScene(scene);
        mainMenu.show();

    }

}