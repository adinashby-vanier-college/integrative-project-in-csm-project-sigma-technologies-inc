module com.example.sem4casino {
    requires javafx.controls;
    requires javafx.fxml;
    requires jcards;


    opens com.example.sem4casino to javafx.fxml;
    exports com.example.sem4casino;
}