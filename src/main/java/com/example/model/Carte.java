package com.example.model;

import com.example.agiledelivery.GraphicalView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class Carte {
    private double minLat;
    private double maxLat;
    private double minLon;
    private double maxLon;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private ObservableMap<Long, Intersection> listeIntersection = FXCollections.observableHashMap();
    private ObservableMap<Integer, Segment> listeSegments = FXCollections.observableHashMap();
    private SimpleIntegerProperty idProperty = new SimpleIntegerProperty(1);

    public void addIntersection(Long id, double latitude, double longitude) {
        Intersection newIntersection = new Intersection(id, latitude, longitude);
        listeIntersection.put(id, newIntersection);
    }

    public void addSegment(Long ori, Long dest, double length, String name) {
        Intersection origin = listeIntersection.get(ori);
        Intersection destination = listeIntersection.get(dest);
        Segment newSegment = new Segment(origin, destination, length, name);
        listeSegments.put(idProperty.get(), newSegment);
        idProperty.set(idProperty.get() + 1);
    }

    public ObservableMap<Long, Intersection> getListeIntersections() {
        return listeIntersection;
    }

    public ObservableMap<Integer, Segment> getListeSegments() {
        return listeSegments;
    }

    public int getId() {
        return idProperty.get();
    }

    public double getMinLat() {
        return minLat;
    }

    public double getMaxLat() {
        return maxLat;
    }

    public double getMinLon() {
        return minLon;
    }

    public double getMaxLon() {
        return maxLon;
    }

    public SimpleIntegerProperty idProperty() {
        return idProperty;
    }

    public void reset() {
        listeIntersection.clear();
        listeSegments.clear();
        idProperty.set(1);
        firePropertyChange("reset", null, null);
    }

    public void readEnd(){
        minLat = Double.MAX_VALUE;
        maxLat = Double.MIN_VALUE;
        minLon = Double.MAX_VALUE;
        maxLon = Double.MIN_VALUE;

        for (Intersection intersection : listeIntersection.values()) {
            minLat = Math.min(minLat, intersection.getLatitude());
            maxLat = Math.max(maxLat, intersection.getLatitude());
            minLon = Math.min(minLon, intersection.getLongitude());
            maxLon = Math.max(maxLon, intersection.getLongitude());
        }

        firePropertyChange("read", null, null);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
    private void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        support.firePropertyChange(propertyName, oldValue, newValue);
    }


}