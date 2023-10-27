package com.example.model;

import java.util.*;
import com.example.model.Intersection;

/**
 * 
 */
public class Segment {

    /**
     * Default constructor
     */
    private Intersection origin;
    private Intersection destination;
    private double length;
    private String name;
    public Segment() {
    }

    public Segment(Intersection origin, Intersection destination, double length, String name) {
        this.origin = origin;
        this.destination = destination;
        this.length = length;
        this.name = name;
    }

    public Segment(double originLatitude, double originLongitude, double destinationLatitude, double destinationLongitude) {
        this.origin = new Intersection(null, originLatitude, originLongitude);
        this.destination = new Intersection(null, destinationLatitude, destinationLongitude);
    }

    public Intersection getOrigin() {
        return origin;
    }

    public void setOrigin(Intersection origin) {
        this.origin = origin;
    }

    public Intersection getDestination() {
        return destination;
    }

    public void setDestination(Intersection destination) {
        this.destination = destination;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}