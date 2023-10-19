module com.example.agiledelivery {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.agiledelivery to javafx.fxml;
    exports com.example.agiledelivery;
}