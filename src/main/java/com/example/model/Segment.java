package com.example.model;

/**
 * 
 */
public class Segment {

    /**
     * L'intersection d'origine du segment
     */
    private Intersection origin;

    /**
     * L'intersection de destination du segment
     */
    private Intersection destination;

    /**
     * La longueur du segment
     */
    private double length;

    /**
     * Le nom du segment
     */
    private String name;

    /**
     * Default constructor
     */
    public Segment(Intersection origin, Intersection destination, double length, String name) {
        this.origin = origin;
        this.destination = destination;
        this.length = length;
        this.name = name;
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