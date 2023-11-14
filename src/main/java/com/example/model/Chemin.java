package com.example.model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

/**
 * 
 */
public class Chemin {

    private LinkedList<Segment> listeSegments;
    private double longueur;
    private Duration duree;

    /**
     * Default constructor
     */
    public Chemin() {
        longueur = 0;
        duree = Duration.ZERO;
        listeSegments = new LinkedList<>();
    }
    public LinkedList<Segment> getListeSegments() {
        return listeSegments;
    }

    public Intersection getOrigin()
    {
        return listeSegments.getFirst().getOrigin();
    }

    public Intersection getDestination()
    {
        return listeSegments.getLast().getDestination();
    }

    public Duration getDuree() {
        return duree;
    }

    public double getLongueur() { return longueur; }

    /**
     * Add a segment in first position of the list of segments and check if its destination is the same as the origin of the first segment
     * @param segment the segment to add
     */
    public void addSegmentInFirstPosition(Segment segment) {
        if (listeSegments.isEmpty() || listeSegments.getFirst().getOrigin() == segment.getDestination()) {
            listeSegments.addFirst(segment);
            longueur += segment.getLength();
            duree = Duration.ofMinutes((long)(longueur / Livraison.VITESSE_DEPLACEMENT));
        }
    }
}