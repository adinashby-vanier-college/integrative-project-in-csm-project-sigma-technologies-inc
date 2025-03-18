module com.vanier.sigmacasino {
    requires javafx.controls;
    requires javafx.fxml;
    requires jcards;


    opens com.vanier.sigmacasino to javafx.fxml;
    exports com.vanier.sigmacasino;
    exports com.vanier.sigmacasino.Poker;
}