module com.example.sem4casino {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sem4casino to javafx.fxml;
    exports com.example.sem4casino;
}