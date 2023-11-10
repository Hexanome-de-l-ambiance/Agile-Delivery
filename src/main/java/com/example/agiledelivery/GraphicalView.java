package com.example.agiledelivery;

import com.example.model.*;


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
import java.util.Map;
import java.util.Random;

public class GraphicalView extends Pane implements PropertyChangeListener, Visitor{

    private Carte carte;
    private HashMap<Circle, Intersection> circleMap;
    private HashSet<Pair<Long, Long>> hashSet = new HashSet<>();
    private MouseListener mouseListener;

    private final double DETECTION_RADIUS = 7.0;

    public GraphicalView(Carte carte, Pane mapPane) {
        this.setPrefWidth(mapPane.getPrefWidth());
        this.setPrefHeight(mapPane.getPrefHeight());
        this.carte = carte;
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.setStyle("-fx-background-color: lightblue;");
        carte.addPropertyChangeListener(this);
        this.circleMap = new HashMap<>();
    }
    public Pane getGraph() {
        return this;
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
            case Carte.RESET: this.getChildren().clear(); break;
            case Carte.READ: display(carte); break;
            case Carte.UPDATE:
                this.getChildren().clear();
                hashSet.clear();
                display(carte);
                HashMap<Integer, Tournee> listeTournees = (HashMap<Integer, Tournee>) evt.getNewValue();
                for(Map.Entry<Integer, Tournee> entry: listeTournees.entrySet()){
                    display(entry.getKey(), entry.getValue());
                }
                break;
            case Carte.SET_NB_COURIERS: this.getChildren().clear();display(carte);break;
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

        double scaleX = this.getWidth() / rangeLon;
        double scaleY = this.getHeight() / rangeLat;
        double scale = Math.min(scaleX, scaleY);

        for (Intersection intersection : carte.getListeIntersections().values()) {
            if(intersection == carte.getEntrepot()) continue;

            double adjustedX = (intersection.getLongitude() - midLon) * scale + this.getWidth() / 2;
            double adjustedY = -(intersection.getLatitude() - midLat) * scale + this.getHeight() / 2;

            Circle circle = new Circle(adjustedX, adjustedY, 3);
            Circle detectionCircle = new Circle(adjustedX, adjustedY, DETECTION_RADIUS);
            detectionCircle.setFill(Color.TRANSPARENT);
            detectionCircle.setOnMouseEntered(event -> circle.setFill(Color.RED));
            detectionCircle.setOnMouseExited(event -> circle.setFill(Color.BLACK));

            circleMap.put(detectionCircle, intersection);
            this.getChildren().add(circle); // Add to right pane
            this.getChildren().add(detectionCircle);
        }

        for (Segment segment : carte.getListeSegments().values()) {
            double adjustedX1 = (segment.getOrigin().getLongitude() - midLon) * scale + this.getWidth() / 2;
            double adjustedY1 = -(segment.getOrigin().getLatitude() - midLat) * scale + this.getHeight() / 2;
            double adjustedX2 = (segment.getDestination().getLongitude() - midLon) * scale + this.getWidth() / 2;
            double adjustedY2 = -(segment.getDestination().getLatitude() - midLat) * scale + this.getHeight() / 2;

            Line line = new Line(adjustedX1, adjustedY1, adjustedX2, adjustedY2);
            this.getChildren().add(line); // Add to right pane
        }
        Intersection entrepot = carte.getEntrepot();
        double adjustedX = (entrepot.getLongitude() - midLon) * scale + this.getWidth() / 2;
        double adjustedY = -(entrepot.getLatitude() - midLat) * scale + this.getHeight() / 2;

        Circle circle = new Circle(adjustedX, adjustedY, 5, Color.GREEN);
        circle.toFront();
        this.getChildren().add(circle); // Add to right pane

        mouseListener.setOnEvent();
    }

    @Override
    public void display(int numeroCoursier, Tournee tournee) {
        double minLat = carte.getMinLat();
        double maxLat = carte.getMaxLat();
        double minLon = carte.getMinLon();
        double maxLon = carte.getMaxLon();

        double midLat = (minLat + maxLat) / 2;
        double midLon = (minLon + maxLon) / 2;

        double rangeLat = maxLat - minLat;
        double rangeLon = maxLon - minLon;

        double scaleX = this.getWidth() / rangeLon;
        double scaleY = this.getHeight() / rangeLat;
        double scale = Math.min(scaleX, scaleY);

        for (Livraison livraison : tournee.getLivraisons()) {
            if(livraison.getDestination() == carte.getEntrepot()) continue;
            double adjustedX = (livraison.getDestination().getLongitude() - midLon) * scale + this.getWidth() / 2;
            double adjustedY = -(livraison.getDestination().getLatitude() - midLat) * scale + this.getHeight() / 2;

            Circle circle = new Circle(adjustedX, adjustedY, 4, Color.BLUE);
            this.getChildren().add(circle);
        }

        Random random = new Random();
        Color randomColor1 = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        Color randomColor2 = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        for(Chemin chemin : tournee.getListeChemins())
        {
            for (Segment segment : chemin.getListeSegments()) {
                double adjustedX1 = (segment.getOrigin().getLongitude() - midLon) * scale + this.getWidth() / 2;
                double adjustedY1 = -(segment.getOrigin().getLatitude() - midLat) * scale + this.getHeight() / 2;
                double adjustedX2 = (segment.getDestination().getLongitude() - midLon) * scale + this.getWidth() / 2;
                double adjustedY2 = -(segment.getDestination().getLatitude() - midLat) * scale + this.getHeight() / 2;

                double angle = Math.atan2((adjustedY2 - adjustedY1), (adjustedX2 - adjustedX1));

                // Create a Polygon to represent the arrowhead for the last segment
                if(segment == chemin.getListeSegments().get(chemin.getListeSegments().size() - 1)) {
                    Polygon arrowhead = new Polygon();
                    arrowhead.getPoints().addAll(
                            adjustedX2 - 15 * Math.cos(angle - Math.toRadians(30)), adjustedY2 - 15 * Math.sin(angle - Math.toRadians(30)),
                            adjustedX2, adjustedY2,
                            adjustedX2 - 15 * Math.cos(angle + Math.toRadians(30)), adjustedY2 - 15 * Math.sin(angle + Math.toRadians(30))
                    );
                    arrowhead.setFill(Color.BLUE);
                    this.getChildren().add(arrowhead);
                }
                Line line = new Line(adjustedX1, adjustedY1, adjustedX2, adjustedY2);

                line.setStrokeType(StrokeType.OUTSIDE); // Set the stroke type
                if(hashSet.contains(new Pair<>(segment.getOrigin().getId(), segment.getDestination().getId())) || hashSet.contains(new Pair<>(segment.getDestination().getId(), segment.getOrigin().getId()))) {
                    line.setStroke(Color.RED);
                }
                else applyCustomStroke(line, randomColor1, randomColor2);
                hashSet.add(new Pair<>(segment.getOrigin().getId(), segment.getDestination().getId()));
                this.getChildren().add(line);
            }
        }
    }

    private void applyCustomStroke(Line line, Color startColor, Color endColor) {
        line.setStroke(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, startColor), new Stop(1, endColor)));
    }
}