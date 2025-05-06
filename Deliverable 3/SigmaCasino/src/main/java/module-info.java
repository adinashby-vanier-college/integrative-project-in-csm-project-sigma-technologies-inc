module com.example.sigmacasino {
    requires javafx.controls;
    requires javafx.fxml;
    requires jcards;
    requires jdk.compiler;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.media;
    requires jdk.xml.dom;
    requires java.sql;
    requires com.google.gson;

    opens com.example.sigmacasino.Blackjack.controllers to javafx.fxml;
    opens com.example.sigmacasino to javafx.fxml;
    exports com.example.sigmacasino;
    opens com.example.sigmacasino.Poker to javafx.fxml;
    exports com.example.sigmacasino.Poker;
    opens com.example.sigmacasino.Menus to javafx.fxml;
    exports com.example.sigmacasino.Menus;
    opens com.example.sigmacasino.Roulette to javafx.fxml;
    exports com.example.sigmacasino.Roulette;
    opens com.example.sigmacasino.Blackjack.gameLogic to javafx.graphics;
    exports com.example.sigmacasino.Settings to com.google.gson;
}