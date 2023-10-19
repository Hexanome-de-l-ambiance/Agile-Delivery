module com.example.agiledelivery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens com.example.agiledelivery to javafx.fxml;
    exports com.example.agiledelivery;
}