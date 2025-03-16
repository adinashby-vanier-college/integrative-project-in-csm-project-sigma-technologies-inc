module com.sigmatechnologies.gamblingsimulator25 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jcards;


    opens com.sigmatechnologies.gamblingsimulator25 to javafx.fxml;
    exports com.sigmatechnologies.gamblingsimulator25;
}