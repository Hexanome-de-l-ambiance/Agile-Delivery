module com.example.agiledelivery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.desktop;


    opens com.example.view to javafx.fxml;
    opens com.example.model;
    exports com.example.view;
    exports com.example.controller;
    exports com.example.tsp;
    exports com.example.xml;
    exports com.example.utils;
}