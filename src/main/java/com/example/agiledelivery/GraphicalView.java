package com.example.agiledelivery;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Segment;
import com.example.model.Visitor;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GraphicalView extends Pane implements PropertyChangeListener, Visitor{

    private Carte carte;

    public GraphicalView(Carte carte) {
        this.carte = carte;
        carte.addPropertyChangeListener(this);
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("reset".equals(evt.getPropertyName())) {
            getChildren().clear();
        } else if ("read".equals(evt.getPropertyName())){
            display(carte);
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

        // 3. Afficher les intersections
        for (Intersection intersection : carte.getListeIntersections().values()) {
            double adjustedX = (intersection.getLongitude() - midLon) * scale + this.getWidth() / 2;
            double adjustedY = -(intersection.getLatitude() - midLat) * scale + this.getHeight() / 2;

            Circle circle = new Circle(adjustedX, adjustedY, 3);
            this.getChildren().add(circle); // Add to right pane
        }

        // 4. Afficher les segments
        for (Segment segment : carte.getListeSegments().values()) {
            double adjustedX1 = (segment.getOrigin().getLongitude() - midLon) * scale + this.getWidth() / 2;
            double adjustedY1 = -(segment.getOrigin().getLatitude() - midLat) * scale + this.getHeight() / 2;
            double adjustedX2 = (segment.getDestination().getLongitude() - midLon) * scale + this.getWidth() / 2;
            double adjustedY2 = -(segment.getDestination().getLatitude() - midLat) * scale + this.getHeight() / 2;

            Line line = new Line(adjustedX1, adjustedY1, adjustedX2, adjustedY2);
            this.getChildren().add(line); // Add to right pane
        }
    }
}