package com.example.agiledelivery;

import com.example.controller.Controller;
import com.example.model.Intersection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 */
public class MouseListener implements EventHandler<ActionEvent> {

    private Controller controller;
    private GraphicalView graphicalView;
    private Pane graph;
    private double mouseX, mouseY;
    private boolean isDragged;
    public MouseListener(GraphicalView graphicalView, Controller controller) {
        this.controller = controller;
        this.graphicalView = graphicalView;
        this.graph = graphicalView.getGraph();
        setOnEvent();
    }

    private void setOnEvent(){
        graphicalView.setOnMousePressed(event -> {
            mouseX = event.getSceneX() - graph.getLayoutX();
            mouseY = event.getSceneY() - graph.getLayoutY();
            //System.out.println("Pressed");
        });

        graphicalView.setOnMouseReleased(event -> {
            if(isDragged);
            //System.out.println("Released");
            isDragged = false;
        });

        graphicalView.setOnMouseDragged(event -> {
            double newX = event.getSceneX() - mouseX;
            double newY = event.getSceneY() - mouseY;
            graph.setLayoutX(newX);
            graph.setLayoutY(newY);
            isDragged = true;
        });

        graphicalView.setOnScroll((ScrollEvent event) -> {
            double deltaY = event.getDeltaY();
            double scaleFactor = 1.1;

            if (deltaY < 0) {
                graph.setScaleX(graph.getScaleX() / scaleFactor);
                graph.setScaleY(graph.getScaleY() / scaleFactor);
            } else {
                graph.setScaleX(graph.getScaleX() * scaleFactor);
                graph.setScaleY(graph.getScaleY() * scaleFactor);
            }
        });

        HashMap<Circle, Intersection> circleMap = graphicalView.getCircleMap();
        for (Map.Entry<Circle, Intersection> entry : circleMap.entrySet()) {
            Circle key = entry.getKey();
            key.setOnMouseClicked(mouseEvent -> {
                controller.addDestination(entry.getValue());
            });
        }
    }
    @Override
    public void handle(ActionEvent actionEvent) {

    }
}