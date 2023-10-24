package com.example.model;

import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * 
 */
public class Carte extends PropertyChangeSupport {

    /**
     * Default constructor
     */
    private HashMap<Long, Intersection> listeIntersection;
    private HashMap<Integer, Segment> listeSegments;

    private int id = 1;
    public Carte() {
        super(new Object());
        this.listeIntersection = new HashMap<>();
        this.listeSegments = new HashMap<>();
    }

    public void addIntersection(Long id, double latitude, double longitude) {
        Intersection newIntersection = new Intersection(id, latitude, longitude);
        listeIntersection.put(id, newIntersection);

        firePropertyChange("IntersectionAdded", null, newIntersection);
    }

    public void addSegment(Long ori, Long dest, double length, String name) {
        Intersection origin = listeIntersection.get(ori);
        Intersection destination = listeIntersection.get(dest);
        Segment newSegment = new Segment(origin, destination, length, name);
        listeSegments.put(this.id, newSegment);
        this.id++;

        firePropertyChange("segmentAdded", null, newSegment);
    }

    public HashMap<Long, Intersection> getListeIntersections() {
        return listeIntersection;
    }

    public HashMap<Integer, Segment> getListeSegments() {
        return listeSegments;
    }

    public int getId() {
        return id;
    }

    public void Info() {
        for (Map.Entry<Long, Intersection> entry : listeIntersection.entrySet()) {
            Long key = entry.getKey();
            Intersection intersection = entry.getValue();
            System.out.println("Intersection key: " + key);
            System.out.println("Intersection ID: " + intersection.getId());
            System.out.println("Latitude: " + intersection.getLatitude());
            System.out.println("Longitude: " + intersection.getLongitude());
        }
        for (Map.Entry<Integer, Segment> entry : listeSegments.entrySet()) {
            Integer key = entry.getKey();
            Segment segment = entry.getValue();
            System.out.println("Segment key: " + key);
            System.out.println("Origin: " + segment.getOrigin().getId());
            System.out.println("Destination: " + segment.getDestination().getId());
            System.out.println("Length: " + segment.getLength());
            System.out.println("Name: " + segment.getName());
        }
    }


}