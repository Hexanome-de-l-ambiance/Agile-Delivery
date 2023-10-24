package com.example.agiledelivery;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Segment;
import com.example.xml.XMLOpener;
import com.example.xml.XMLfilter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 640, 480);


        stage.setTitle("Hello world");
        stage.setScene(scene);
        Carte carte = new Carte();
        stage.show();
        try{
            XMLOpener.getInstance().readFile(stage, carte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        carte.Info();
    }

    public static void drawSegmentExample(Canvas canvas)
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(javafx.scene.paint.Color.RED);
        gc.setLineWidth(2);
        Intersection i1 = new Intersection(1L, 45.5, 46.5);
        Intersection i2 = new Intersection(2L, 50.5, 60.5);
        Segment segment = new Segment(i1, i2, 200, "12");

        gc.strokeLine(segment.getOrigin().getLongitude(), segment.getOrigin().getLatitude(), segment.getDestination().getLongitude(), segment.getDestination().getLatitude());
    }

    public static void main(String[] args) {
        launch();
    }
}