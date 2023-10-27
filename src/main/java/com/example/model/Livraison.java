package com.example.model;

import java.util.*;

/**
 * 
 */
public class Livraison {
    /**
     *
     */
    private Intersection destination;
    /**
     *
     */
    private int heureDebut;


    /**
     * Default constructor
     */
    public Livraison(Intersection destination, int heureDebut) {
        this.destination = destination;
        this.heureDebut = heureDebut;
    }


    public int getHeureDebut() {
        return heureDebut;
    }

    public Intersection getDestination() {
        return destination;
    }
}