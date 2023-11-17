package com.example.view;

import com.example.model.*;


import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;
import javafx.util.Pair;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

/**
 * La vue graphique
 */
public class GraphicalView extends Pane implements PropertyChangeListener, Visitor{

    private Carte carte;

    private Pane graph;

    private HashMap<Circle, Intersection> circleMap;

    private HashSet<Pair<Circle, Circle>> circlePairSet = new HashSet<>();

    private HashSet<Pair<Long, Long>> hashSet = new HashSet<>();

    private MouseListener mouseListener;

    private ArrayList<Color> colors = new ArrayList<>(Arrays.asList(Color.BLUE, Color.GREEN, Color.PURPLE, Color.ORANGE, Color.RED, Color.FUCHSIA, Color.SIENNA));

    protected final double DETECTION_RADIUS = 7.0;
    protected final double CIRCLE_RADIUS = 2.0;

    /**
     * Initialiser le view
     *
     * @param carte     La carte à afficher
     * @param mapPane   Le conteneur de la carte
     */
    public GraphicalView(Carte carte, Pane mapPane) {
        this.setPrefWidth(mapPane.getPrefWidth());
        this.setPrefHeight(mapPane.getPrefHeight());
        this.carte = carte;
        graph = new Pane();
        graph.setLayoutX(5);
        graph.setLayoutY(5);
        graph.setPrefWidth(this.getPrefWidth());
        graph.setPrefHeight(this.getPrefHeight());
        graph.setStyle("-fx-background-color: lightblue;");
        this.getChildren().add(graph);
        this.setStyle("-fx-background-color: lightblue;");
        carte.addPropertyChangeListener(this);
        this.circleMap = new HashMap<>();
        for (ReadOnlyDoubleProperty readOnlyDoubleProperty : Arrays.asList(widthProperty(), heightProperty())) {
            readOnlyDoubleProperty.addListener((obs, oldVal, newVal) -> {
                if (oldVal.longValue() != 0.0 && !graph.getChildren().isEmpty()){
                   handleResize();
                }
            });
        }
    }
    public Pane getGraph() {
        return graph;
    }

    public HashMap<Circle, Intersection> getCircleMap() {
        return circleMap;
    }

    public HashSet<Pair<Circle, Circle>> getCirclePairSet() {
        return circlePairSet;
    }

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    /**
     * Gérer les changements dans les propriétés de l'événement de PropertyChange en effectuant différentes actions basées sur l'événement.
     * @param evt L'événement de changement de propriété
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String event = evt.getPropertyName();
        switch (event) {
            case Carte.RESET: graph.getChildren().clear(); break;
            case Carte.READ: display(carte); break;
            case Carte.ADD, Carte.REMOVE, Carte.UPDATE:{
                graph.getChildren().clear();
                hashSet.clear();
                display(carte);
                HashMap<Integer, Tournee> listeTournees = (HashMap<Integer, Tournee>) evt.getNewValue();
                for(Map.Entry<Integer, Tournee> entry: listeTournees.entrySet()){
                    display(entry.getKey(), entry.getValue());
                }
                break;
            }
            case Carte.SET_NB_COURIERS, Carte.RESET_TOURS: graph.getChildren().clear();display(carte);break;
        }

    }

    /**
     * Afficher la carte sur le view en fonction des tournées fournies.
     * @param carte La carte à afficher
     */
    @Override
    public void display(Carte carte) {
        if(carte.getEntrepot() == null) { return; }
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
            if(intersection == carte.getEntrepot()) continue;

            double adjustedX = (intersection.getLongitude() - midLon) * scale + graph.getWidth() / 2;
            double adjustedY = -(intersection.getLatitude() - midLat) * scale + graph.getHeight() / 2;

            Circle circle = new Circle(adjustedX, adjustedY, CIRCLE_RADIUS, Color.BLACK);
            Circle detectionCircle = new Circle(adjustedX, adjustedY, DETECTION_RADIUS);
            detectionCircle.setFill(Color.TRANSPARENT);
            detectionCircle.setOnMouseEntered(event -> circle.setFill(Color.RED));
            detectionCircle.setOnMouseExited(event -> {
                if (circle.getRadius() == CIRCLE_RADIUS) {
                    circle.setFill(Color.BLACK);
                }
            });

            circleMap.put(detectionCircle, intersection);
            circlePairSet.add(new Pair<>(detectionCircle, circle));
            graph.getChildren().add(circle);
            graph.getChildren().add(detectionCircle);
        }

        for (Segment segment : carte.getListeSegments().values()) {
            double adjustedX1 = (segment.getOrigin().getLongitude() - midLon) * scale + graph.getWidth() / 2;
            double adjustedY1 = -(segment.getOrigin().getLatitude() - midLat) * scale + graph.getHeight() / 2;
            double adjustedX2 = (segment.getDestination().getLongitude() - midLon) * scale + graph.getWidth() / 2;
            double adjustedY2 = -(segment.getDestination().getLatitude() - midLat) * scale + graph.getHeight() / 2;

            Line line = new Line(adjustedX1, adjustedY1, adjustedX2, adjustedY2);
            graph.getChildren().add(line);
        }
        Intersection entrepot = carte.getEntrepot();
        double adjustedX = (entrepot.getLongitude() - midLon) * scale + graph.getWidth() / 2;
        double adjustedY = -(entrepot.getLatitude() - midLat) * scale + graph.getHeight() / 2;

        Circle circle = new Circle(adjustedX, adjustedY, 5, Color.GREEN);
        circle.toFront();
        graph.getChildren().add(circle);

        mouseListener.setOnEvent();
    }

    /**
     * Afficher une tournée spécifique pour un numéro de coursier donné.
     *
     * @param numeroCoursier Le numéro du coursier
     * @param tournee        La tournée à afficher
     */
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

        double scaleX = graph.getWidth() / rangeLon;
        double scaleY = graph.getHeight() / rangeLat;
        double scale = Math.min(scaleX, scaleY);
        Color color = colors.get((numeroCoursier-1) % colors.size());

        for (Livraison livraison : tournee.getListeLivraisons()) {
            if(livraison.getDestination() == carte.getEntrepot()) continue;
            double adjustedX = (livraison.getDestination().getLongitude() - midLon) * scale + graph.getWidth() / 2;
            double adjustedY = -(livraison.getDestination().getLatitude() - midLat) * scale + graph.getHeight() / 2;

            Circle circle = new Circle(adjustedX, adjustedY, 6);

            switch (livraison.getEtat()) {
                case INDETERMINE -> circle.setFill(Color.BLUE);
                case EN_AVANCE -> circle.setFill(Color.GREEN);
                case A_L_HEURE -> circle.setFill(Color.YELLOW);
                case EN_RETARD -> circle.setFill(Color.RED);
            }



            circle.toFront();
            graph.getChildren().add(circle);
        }

        int i = 1;
        for(Chemin chemin : tournee.getListeChemins())
        {
            for (Segment segment : chemin.getListeSegments()) {
                double adjustedX1 = (segment.getOrigin().getLongitude() - midLon) * scale + graph.getWidth() / 2;
                double adjustedY1 = -(segment.getOrigin().getLatitude() - midLat) * scale + graph.getHeight() / 2;
                double adjustedX2 = (segment.getDestination().getLongitude() - midLon) * scale + graph.getWidth() / 2;
                double adjustedY2 = -(segment.getDestination().getLatitude() - midLat) * scale + graph.getHeight() / 2;

                double angle = Math.atan2((adjustedY2 - adjustedY1), (adjustedX2 - adjustedX1));

                // Create a Polygon to represent the arrowhead for the last segment
                if(segment == chemin.getListeSegments().get(chemin.getListeSegments().size() - 1) && segment.getDestination() != carte.getEntrepot()) {
                    Polygon arrowhead = new Polygon();
                    arrowhead.getPoints().addAll(
                            adjustedX2 - 15 * Math.cos(angle - Math.toRadians(30)), adjustedY2 - 15 * Math.sin(angle - Math.toRadians(30)),
                            adjustedX2, adjustedY2,
                            adjustedX2 - 15 * Math.cos(angle + Math.toRadians(30)), adjustedY2 - 15 * Math.sin(angle + Math.toRadians(30))
                    );
                    arrowhead.setFill(Color.BLACK);
                    arrowhead.toFront();
                    graph.getChildren().add(arrowhead);

                    double adjustedX = (segment.getDestination().getLongitude() - midLon) * scale + graph.getWidth() / 2 ;
                    double adjustedY = -(segment.getDestination().getLatitude() - midLat) * scale + graph.getHeight() / 2;
                    javafx.scene.text.Text text = new javafx.scene.text.Text("" + i++);
                    text.setX(adjustedX - 5);
                    text.setY(adjustedY - 15);
                    text.setStroke(color);
                    text.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: white; -fx-stroke-width: 1px;");
                    text.toFront();
                    graph.getChildren().add(text);
                }
                Line line = new Line(adjustedX1, adjustedY1, adjustedX2, adjustedY2);

                line.setStrokeType(StrokeType.OUTSIDE); // Set the stroke type
                line.setStrokeWidth(2); // Set the stroke width
                line.setStroke(color);
                if(hashSet.contains(new Pair<>(segment.getOrigin().getId(), segment.getDestination().getId())) || hashSet.contains(new Pair<>(segment.getDestination().getId(), segment.getOrigin().getId()))) {
                    line.getStrokeDashArray().addAll((double)(numeroCoursier%3), 8d);
                }
                else {
                    hashSet.add(new Pair<>(segment.getOrigin().getId(), segment.getDestination().getId()));
                }
                graph.getChildren().add(line);
            }
        }
    }

    private void handleResize() {
        Platform.runLater(() -> {
            graph.getChildren().clear();
            hashSet.clear();
            display(carte);
            HashMap<Integer, Tournee> listeTournees = carte.getListeTournees();
            for (Map.Entry<Integer, Tournee> entry : listeTournees.entrySet()) {
                display(entry.getKey(), entry.getValue());
            }
        });
    }

}