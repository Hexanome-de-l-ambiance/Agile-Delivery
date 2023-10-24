package com.example.agiledelivery;

import com.example.controller.Controller;
import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Segment;
import com.example.xml.XMLOpener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;


public class Window extends Application {
    private Pane leftPane;
    private Pane rightPane;
    private Controller controller;
    private Button loadMapButton;
    private final int PREFWIDTH = 1920;
    private final int PREFHEIGHT = 1080;


    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller(primaryStage);

        leftPane = new Pane();
        rightPane = new Pane();

        initializeButtons();

        HBox mainLayout = new HBox();
        mainLayout.getChildren().addAll(leftPane, rightPane);

        leftPane.setPrefWidth(0.2 * PREFWIDTH);
        rightPane.setPrefWidth(0.8 * PREFWIDTH);



        Scene scene = new Scene(mainLayout, PREFWIDTH, PREFHEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void initializeButtons() {
        loadMapButton = new Button("Charger une carte");
        loadMapButton.setOnAction(event -> {
            try {
                controller.load();
                displayMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        leftPane.getChildren().add(loadMapButton); // Add the button to the left pane
    }

    public void displayMap() {
        rightPane.getChildren().clear();

        Carte carte = controller.getCarte();
        if (carte == null) {
            System.out.println("Carte null");
            return;
        }

        carte = controller.getCarte();

        double minLat = Double.MAX_VALUE;
        double maxLat = Double.MIN_VALUE;
        double minLon = Double.MAX_VALUE;
        double maxLon = Double.MIN_VALUE;

        // 1. Trouver les valeurs min et max
        for (Intersection intersection : carte.getListeIntersections().values()) {
            minLat = Math.min(minLat, intersection.getLatitude());
            maxLat = Math.max(maxLat, intersection.getLatitude());
            minLon = Math.min(minLon, intersection.getLongitude());
            maxLon = Math.max(maxLon, intersection.getLongitude());
        }

        // 2. Determiner le centre et le scale
        double midLat = (minLat + maxLat) / 2;
        double midLon = (minLon + maxLon) / 2;

        double rangeLat = maxLat - minLat;
        double rangeLon = maxLon - minLon;

        double paneWidth = 1200;
        double paneHeight = 900;

        double scaleX = paneWidth / rangeLon;
        double scaleY = paneHeight / rangeLat;
        double scale = Math.min(scaleX, scaleY);

        // 3. Afficher les intersections
        for (Intersection intersection : carte.getListeIntersections().values()) {
            double adjustedX = (intersection.getLongitude() - midLon) * scale + paneWidth / 2;
            double adjustedY = -(intersection.getLatitude() - midLat) * scale + paneHeight / 2;

            Circle circle = new Circle(adjustedX, adjustedY, 3);
            rightPane.getChildren().add(circle); // Add to right pane
        }

        // 4. Afficher les segments
        for (Segment segment : carte.getListeSegments().values()) {
            double adjustedX1 = (segment.getOrigin().getLongitude() - midLon) * scale + paneWidth / 2;
            double adjustedY1 = -(segment.getOrigin().getLatitude() - midLat) * scale + paneHeight / 2;
            double adjustedX2 = (segment.getDestination().getLongitude() - midLon) * scale + paneWidth / 2;
            double adjustedY2 = -(segment.getDestination().getLatitude() - midLat) * scale + paneHeight / 2;

            Line line = new Line(adjustedX1, adjustedY1, adjustedX2, adjustedY2);
            rightPane.getChildren().add(line); // Add to right pane
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}