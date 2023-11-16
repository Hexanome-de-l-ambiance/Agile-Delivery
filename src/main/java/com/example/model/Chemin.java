package com.example.model;

import java.time.Duration;
import java.util.*;

/**
 * Représente un chemin composé de segments reliant des intersections.
 */
public class Chemin {

    /**
     * La liste des segments que suit le chemin
     */
    private LinkedList<Segment> listeSegments;

    /**
     * La longueur du chemin
     */
    private double longueur;

    /**
     * La durée de parcours du chemin
     */
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