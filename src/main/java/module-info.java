module com.example.agiledelivery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.desktop;


    opens com.example.agiledelivery to javafx.fxml;
    exports com.example.agiledelivery;
    exports com.example.controller;
}