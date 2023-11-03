package com.example.agiledelivery;

import com.example.model.*;

import javafx.collections.ObservableMap;
import javafx.scene.layout.Pane;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;
import javafx.util.Pair;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class GraphicalView extends Pane implements PropertyChangeListener, Visitor{

    private Carte carte;
    private Pane graph;
    private HashMap<Circle, Intersection> circleMap;
    private HashSet<Pair<Long, Long>> hashSet = new HashSet<>();
    private MouseListener mouseListener;

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

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String event = evt.getPropertyName();
        switch (event) {
            case Carte.RESET: graph.getChildren().clear(); break;
            case Carte.READ: display(carte); break;
            case Carte.UPDATE:
                graph.getChildren().clear();
                hashSet.clear();
                display(carte);
                ObservableMap<Integer, Tournee> listeTournees = (ObservableMap<Integer, Tournee>) evt.getNewValue();
                for(Tournee tournee : listeTournees.values())
                {
                    display(tournee);
                }
                break;
            case Carte.SET_NB_COURIERS: graph.getChildren().clear();display(carte);break;
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
        mouseListener.setOnEvent();
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

        Random random = new Random();
        Color randomColor1 = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        Color randomColor2 = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        for(Chemin chemin : tournee.getListeChemins())
        {
            for (Segment segment : chemin.getListeSegments()) {
                double adjustedX1 = (segment.getOrigin().getLongitude() - midLon) * scale + graph.getWidth() / 2;
                double adjustedY1 = -(segment.getOrigin().getLatitude() - midLat) * scale + graph.getHeight() / 2;
                double adjustedX2 = (segment.getDestination().getLongitude() - midLon) * scale + graph.getWidth() / 2;
                double adjustedY2 = -(segment.getDestination().getLatitude() - midLat) * scale + graph.getHeight() / 2;

                double angle = Math.atan2((adjustedY2 - adjustedY1), (adjustedX2 - adjustedX1));

                // Create a Polygon to represent the arrowhead
                Polygon arrowhead = new Polygon();
                arrowhead.getPoints().addAll(
                        adjustedX2 - 10 * Math.cos(angle - Math.toRadians(30)), adjustedY2 - 10 * Math.sin(angle - Math.toRadians(30)),
                        adjustedX2, adjustedY2,
                        adjustedX2 - 10 * Math.cos(angle + Math.toRadians(30)), adjustedY2 - 10 * Math.sin(angle + Math.toRadians(30))
                );
                arrowhead.setFill(Color.BLACK);

                Line line = new Line(adjustedX1, adjustedY1, adjustedX2, adjustedY2);

                line.setStrokeType(StrokeType.OUTSIDE); // Set the stroke type
                if(hashSet.contains(new Pair<>(segment.getOrigin().getId(), segment.getDestination().getId())) || hashSet.contains(new Pair<>(segment.getDestination().getId(), segment.getOrigin().getId()))) {
                    line.setStroke(Color.RED);
                }
                else applyCustomStroke(line, randomColor1, randomColor2);
                hashSet.add(new Pair<>(segment.getOrigin().getId(), segment.getDestination().getId()));
                graph.getChildren().add(line);
                graph.getChildren().add(arrowhead);
            }
        }
    }

    private void applyCustomStroke(Line line, Color startColor, Color endColor) {
        line.setStroke(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, startColor), new Stop(1, endColor)));
    }
}