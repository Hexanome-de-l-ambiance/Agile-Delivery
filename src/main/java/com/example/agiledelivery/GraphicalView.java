package com.example.agiledelivery;

import com.example.model.*;

import javafx.collections.ObservableMap;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

public class GraphicalView extends Pane implements PropertyChangeListener, Visitor{

    private Carte carte;
    private Pane graph;
    private HashMap<Circle, Intersection> circleMap;

    public GraphicalView(Carte carte) {
        this.setPrefWidth(Window.graphicalViewScale * Window.PREFWIDTH);
        this.setPrefHeight(Window.PREFHEIGHT);

        this.carte = carte;
        graph = new Pane();
        graph.setPrefWidth(Window.graphicalViewScale * Window.PREFWIDTH);
        graph.setPrefHeight(Window.PREFHEIGHT);
        graph.setLayoutX(0);
        graph.setLayoutY(0);
        graph.setStyle("-fx-background-color: lightblue;");
        this.getChildren().add(graph);
        this.setStyle("-fx-background-color: yellow;");
        carte.addPropertyChangeListener(this);

        this.circleMap = new HashMap<>();
    }
    public Pane getGraph() {
        return graph;
    }

    public HashMap<Circle, Intersection> getCircleMap() {
        return circleMap;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String event = evt.getPropertyName();
        switch (event) {
            case Carte.RESET: graph.getChildren().clear(); break;
            case Carte.READ: display(carte); break;
            case Carte.UPDATE:
                ObservableMap<Integer, Tournee> listeTournees = (ObservableMap<Integer, Tournee>) evt.getNewValue();
                for(Tournee tournee : listeTournees.values())
                {
                    display(tournee);
                }
                break;
        }
    }

    @Override
    public void display(Carte carte) {
        double minLat = carte.getMinLat();
        double maxLat = carte.getMaxLat();
        double minLon = carte.getMinLon();
        double maxLon = carte.getMaxLon();

        double midLat = (minLat + maxLat) / 2;
        double midLon = (minLon + maxLon) / 2;

        double rangeLat = maxLat - minLat;
        double rangeLon = maxLon - minLon;

        double scaleX = graph.getWidth() / rangeLon;
        double scaleY = graph.getHeight() / rangeLat;
        double scale = Math.min(scaleX, scaleY);

        for (Intersection intersection : carte.getListeIntersections().values()) {
            double adjustedX = (intersection.getLongitude() - midLon) * scale + graph.getWidth() / 2;
            double adjustedY = -(intersection.getLatitude() - midLat) * scale + graph.getHeight() / 2;

            Circle circle = new Circle(adjustedX, adjustedY, 3);
            circleMap.put(circle, intersection);
            circle.setOnMouseEntered(event -> circle.setFill(Color.RED));
            circle.setOnMouseExited(event -> circle.setFill(Color.BLACK));

            graph.getChildren().add(circle); // Add to right pane


        }

        for (Segment segment : carte.getListeSegments().values()) {
            double adjustedX1 = (segment.getOrigin().getLongitude() - midLon) * scale + graph.getWidth() / 2;
            double adjustedY1 = -(segment.getOrigin().getLatitude() - midLat) * scale + graph.getHeight() / 2;
            double adjustedX2 = (segment.getDestination().getLongitude() - midLon) * scale + graph.getWidth() / 2;
            double adjustedY2 = -(segment.getDestination().getLatitude() - midLat) * scale + graph.getHeight() / 2;

            Line line = new Line(adjustedX1, adjustedY1, adjustedX2, adjustedY2);
            graph.getChildren().add(line); // Add to right pane
        }
    }

    @Override
    public void display(Tournee tournee) {
        double minLat = carte.getMinLat();
        double maxLat = carte.getMaxLat();
        double minLon = carte.getMinLon();
        double maxLon = carte.getMaxLon();

        double midLat = (minLat + maxLat) / 2;
        double midLon = (minLon + maxLon) / 2;

        double rangeLat = maxLat - minLat;
        double rangeLon = maxLon - minLon;

        double scaleX = graph.getWidth() / rangeLon;
        double scaleY = graph.getHeight() / rangeLat;
        double scale = Math.min(scaleX, scaleY);

        for(Chemin chemin : tournee.getListeChemins())
        {
            // Set a different color for each path
            double r = 1.0;
            double g = 0.0;
            double b = 0.0;
            for (Segment segment : chemin.getListeSegments()) {
                double adjustedX1 = (segment.getOrigin().getLongitude() - midLon) * scale + graph.getWidth() / 2;
                double adjustedY1 = -(segment.getOrigin().getLatitude() - midLat) * scale + graph.getHeight() / 2;
                double adjustedX2 = (segment.getDestination().getLongitude() - midLon) * scale + graph.getWidth() / 2;
                double adjustedY2 = -(segment.getDestination().getLatitude() - midLat) * scale + graph.getHeight() / 2;

                Line line = new Line(adjustedX1, adjustedY1, adjustedX2, adjustedY2);
                line.setStyle("-fx-stroke: rgb(" + r * 255 + "," + g * 255 + "," + b * 255 + ");");
                // change line width
                line.setStrokeWidth(3);
                graph.getChildren().add(line); // Add to right pane
            }

        }
    }
}