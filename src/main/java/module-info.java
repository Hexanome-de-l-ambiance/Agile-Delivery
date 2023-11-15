module com.example.agiledelivery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.desktop;


    opens com.example.agiledelivery to javafx.fxml;
    opens com.example.model;
    exports com.example.agiledelivery;
    exports com.example.controller;
}