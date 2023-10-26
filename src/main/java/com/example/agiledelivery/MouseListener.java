package com.example.agiledelivery;

import com.example.controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

import java.util.*;

/**
 * 
 */
public class MouseListener implements EventHandler<ActionEvent> {

    private Controller controller;
    private GraphicalView graphicalView;
    private Pane graph;

    private double mouseX, mouseY;
    public MouseListener(GraphicalView graphicalView, Controller controller) {
        this.controller = controller;
        this.graphicalView = graphicalView;
        this.graph = graphicalView.getGraph();
        graphicalView.setOnMousePressed(event -> {
            mouseX = event.getSceneX() - graph.getLayoutX();
            mouseY = event.getSceneY() - graph.getLayoutY();
        });

        graphicalView.setOnMouseDragged(event -> {
            double newX = event.getSceneX() - mouseX;
            double newY = event.getSceneY() - mouseY;
            graph.setLayoutX(newX);
            graph.setLayoutY(newY);
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
    }


    @Override
    public void handle(ActionEvent actionEvent) {

    }
}