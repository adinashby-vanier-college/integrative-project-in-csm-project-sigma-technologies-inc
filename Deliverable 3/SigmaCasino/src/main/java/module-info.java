module com.example.sigmacasino {
    requires javafx.controls;
    requires javafx.fxml;
    requires jcards;


    opens com.example.sigmacasino to javafx.fxml;
    exports com.example.sigmacasino;
    opens com.example.sigmacasino.Poker to javafx.fxml;
    exports com.example.sigmacasino.Poker;
    opens com.example.sigmacasino.Menus to javafx.fxml;
    exports com.example.sigmacasino.Menus;
}