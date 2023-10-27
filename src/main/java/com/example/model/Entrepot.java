package com.example.model;

import java.util.*;

/**
 * 
 */
public class Entrepot extends Intersection {

    /**
     * Default constructor
     */
    public Entrepot() {
    }
    public Entrepot(Intersection intersection){
        super(intersection.getId(), intersection.getLatitude(), intersection.getLongitude());
    }

}