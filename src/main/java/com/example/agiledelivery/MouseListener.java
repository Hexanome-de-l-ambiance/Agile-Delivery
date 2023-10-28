package com.example.agiledelivery;

import com.example.controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

public class MouseListener implements EventHandler<ActionEvent> {

    private Controller controller;
    private GraphicalView graphicalView;
    private Pane graph;
    private double mouseX, mouseY;
    private boolean isDragged;

    private static final double BASE_BOUNDARY_PADDING = 50; // Base padding value

    public MouseListener(GraphicalView graphicalView, Controller controller) {
        this.controller = controller;
        this.graphicalView = graphicalView;
        this.graph = graphicalView.getGraph();
        setOnEvent();
    }

    private void setOnEvent() {
        graphicalView.setOnMousePressed(event -> {
            mouseX = event.getSceneX() - graph.getLayoutX();
            mouseY = event.getSceneY() - graph.getLayoutY();
        });

        graphicalView.setOnMouseReleased(event -> {
            if (isDragged);
            isDragged = false;
        });

        graphicalView.setOnMouseDragged(event -> {
            double newX = event.getSceneX() - mouseX;
            double newY = event.getSceneY() - mouseY;

            // Calculate scaled boundary padding
            double scaledBoundaryPaddingX = BASE_BOUNDARY_PADDING * graph.getScaleX();
            double scaledBoundaryPaddingY = BASE_BOUNDARY_PADDING * graph.getScaleY();

            // Boundary checks with scaled padding
            if (newX > scaledBoundaryPaddingX) newX = scaledBoundaryPaddingX;
            if (newY > scaledBoundaryPaddingY) newY = scaledBoundaryPaddingY;
            if (newX < -graph.getWidth() + graphicalView.getWidth() - scaledBoundaryPaddingX) newX = -graph.getWidth() + graphicalView.getWidth() - scaledBoundaryPaddingX;
            if (newY < -graph.getHeight() + graphicalView.getHeight() - scaledBoundaryPaddingX) newY = -graph.getHeight() + graphicalView.getHeight() - scaledBoundaryPaddingY;

            graph.setLayoutX(newX);
            graph.setLayoutY(newY);
            isDragged = true;
        });

        graphicalView.setOnScroll((ScrollEvent event) -> {
            double deltaY = event.getDeltaY();
            double scaleFactor = 1.05;

            double minScale = 0.1;
            double maxScale = 5.0;
            if (deltaY < 0 && graph.getScaleX() < minScale) {
                return;
            }
            if (deltaY > 0 && graph.getScaleX() > maxScale) {
                return;
            }

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
