package com.example.model;

import java.util.*;

/**
 * 
 */
public class Segment {

    /**
     * Default constructor
     */
    public Segment() {
    }

    public Segment(double x1, double y1, double x2, double y2) {
        this.start = new Intersection(x1, y1);
        this.end = new Intersection(x2, y2);
    }

    public Segment(Intersection start, Intersection end) {
        this.start = start;
        this.end = end;
    }

    private Intersection start;
    private Intersection end;

    public Intersection getStart() {
        return start;
    }

    public Intersection getEnd() {
        return end;
    }

}