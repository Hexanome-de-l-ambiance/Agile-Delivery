package com.example.model;

import java.util.*;

/**
 * 
 */
public class Chemin {

    private LinkedList<Segment> listeSegments;

    /**
     * Default constructor
     */
    public Chemin() {
        listeSegments = new LinkedList<>();
    }

    public Chemin(LinkedList<Segment> listeSegments) {
        this.listeSegments = listeSegments;
    }

    public LinkedList<Segment> getListeSegments() {
        return listeSegments;
    }

    /**
     * Add a segment in first position of the list of segments and check if its destination is the same as the origin of the first segment
     * @param segment
     * @return True if the segment was added, false otherwise
     */
    public Boolean addSegmentInFirstPosition(Segment segment) {
        if (listeSegments.isEmpty()) {
            listeSegments.add(segment);
            return true;
        } else {
            if (Objects.equals(listeSegments.getFirst().getOrigin().getId(), segment.getDestination().getId())) {
                listeSegments.addFirst(segment);
                return true;
            } else {
                return false;
            }
        }
    }
}