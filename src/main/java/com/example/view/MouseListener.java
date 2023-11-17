package com.example.view;

import com.example.controller.Controller;
import com.example.model.Intersection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


/**
 * Cette classe est responsable de la gestion des événements de souris, tels que les clics et les mouvements.
 */
public class MouseListener implements EventHandler<ActionEvent> {

    private Controller controller;
    private GraphicalView graphicalView;
    private TextualView textualView;
    private Pane graph;
    private double mouseX, mouseY;
    private boolean isDragged;

    private static final double MAX_SCALE = 5.0;
    private static final double MIN_SCALE = 0.5;

    private static final double MAP_BOUNDARY_LEFT = -100;
    private static final double MAP_BOUNDARY_RIGHT = 100;
    private static final double MAP_BOUNDARY_TOP = -100;
    private static final double MAP_BOUNDARY_BOTTOM = 100;
    private static final double PADDING = 10;

    private static final double SCALE_FACTOR = 5.0;
    private Circle lastClickedCircle;

    /**
     * Initialiser le listener de la souris.
     *
     * @param textualView    La vue textuelle associée
     * @param graphicalView  La vue graphique associée
     * @param controller     Le contrôleur associé
     */
    public MouseListener(TextualView textualView, GraphicalView graphicalView, Controller controller) {
        this.controller = controller;
        this.graphicalView = graphicalView;
        this.graph = graphicalView.getGraph();
        this.textualView = textualView;
        setOnEvent();
    }

    /**
     * Configurer les gestionnaires d'événements pour les interactions de la vue
     * Les événements configurés incluent le clic de la souris, le déplacement de la souris et le défilement de la souris.
     * En fonction des événements, cette méthode effectue des actions telles que la gestion du clic de souris,
     * le déplacement de la carte graphique et l'ajustement de l'échelle de la carte.
     */
    protected void setOnEvent(){
        graphicalView.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown()) {
                mouseX = event.getSceneX() - graph.getLayoutX();
                mouseY = event.getSceneY() - graph.getLayoutY();
            }if(event.isSecondaryButtonDown()){
                controller.unselectIntersection();
                if (lastClickedCircle != null) {
                    lastClickedCircle.setFill(Color.BLACK);
                    lastClickedCircle.setRadius(graphicalView.CIRCLE_RADIUS);
                }
                textualView.setCoordinatesPaneVisible(false);
                textualView.setTextLatitudeLabel("");
                textualView.setTextLongitudeLabel("");

            }
        });

        graphicalView.setOnMouseReleased(event -> {
                isDragged = false;
        });

        graphicalView.setOnMouseDragged(event -> {
            if(event.isPrimaryButtonDown()) {
                double newX = event.getSceneX() - mouseX;
                double newY = event.getSceneY() - mouseY;

                newX = Math.max(newX, MAP_BOUNDARY_LEFT * SCALE_FACTOR * graph.getScaleX() - PADDING);
                newX = Math.min(newX, MAP_BOUNDARY_RIGHT * SCALE_FACTOR * graph.getScaleX() + PADDING);
                newY = Math.max(newY, MAP_BOUNDARY_TOP * SCALE_FACTOR * graph.getScaleY() - PADDING);
                newY = Math.min(newY, MAP_BOUNDARY_BOTTOM * SCALE_FACTOR * graph.getScaleY() + PADDING);

                graph.setLayoutX(newX);
                graph.setLayoutY(newY);
                isDragged = true;
            }
        });



        graphicalView.setOnScroll((ScrollEvent event) -> {
            double deltaY = event.getDeltaY();
            double scaleFactor = 1.1;

            double newScaleX = graph.getScaleX();
            double newScaleY = graph.getScaleY();

            if (deltaY < 0) {
                newScaleX /= scaleFactor;
                newScaleY /= scaleFactor;
            } else {
                newScaleX *= scaleFactor;
                newScaleY *= scaleFactor;
            }

            newScaleX = Math.max(newScaleX, MIN_SCALE);
            newScaleY = Math.max(newScaleY, MIN_SCALE);
            newScaleX = Math.min(newScaleX, MAX_SCALE);
            newScaleY = Math.min(newScaleY, MAX_SCALE);

            graph.setScaleX(newScaleX);
            graph.setScaleY(newScaleY);
        });

        HashMap<Circle, Intersection> circleMap = graphicalView.getCircleMap();
        HashSet<Pair<Circle, Circle>> circlePairSet = graphicalView.getCirclePairSet();

        for (Map.Entry<Circle, Intersection> entry : circleMap.entrySet()) {
            Circle key = entry.getKey();
            key.setOnMouseReleased(mouseEvent -> {
                if (isDragged) {
                    mouseEvent.consume();
                } else {
                    Intersection selectedIntersection = entry.getValue();
                    controller.addDestination(selectedIntersection);
                    textualView.setCoordinatesPaneVisible(true);
                    textualView.setTextLongitudeLabel(" longitude : " + Math.round(entry.getValue().getLongitude() * 1000.0) / 1000.0);
                    textualView.setTextLatitudeLabel(" latitude : " + Math.round(entry.getValue().getLatitude() * 1000.0) / 1000.0);
                    textualView.showAlert("Veuillez choisir les options à gauche, et cliquez sur le bouton Ajouter une livraison. Cliquez droit pour supprimer cet ajout.");
                    for (Pair<Circle, Circle> circlePair : circlePairSet) {
                        if (circlePair.getKey().equals(key) && circlePair.getValue() != lastClickedCircle) {
                            Circle associatedCircle = circlePair.getValue();
                            associatedCircle.setFill(Color.RED);
                            associatedCircle.setRadius(graphicalView.CIRCLE_RADIUS * 2);

                            if (lastClickedCircle != null) {
                                lastClickedCircle.setFill(Color.BLACK);
                                lastClickedCircle.setRadius(graphicalView.CIRCLE_RADIUS);
                            }
                            lastClickedCircle = associatedCircle;
                            break;
                        }
                    }
                }
            });
        }
    }

    /**
     * Gérer les événements de souris.
     *
     * @param actionEvent L'événement de souris à gérer
     */
    @Override
    public void handle(ActionEvent actionEvent) {

    }
}