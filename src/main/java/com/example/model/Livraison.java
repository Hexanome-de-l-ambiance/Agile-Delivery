package com.example.model;

import java.time.temporal.Temporal;
import java.util.*;
/**
 * 
 */
public class Livraison {

    /**
     * L'heure de début des tournées
     */
    public static final Integer HEURE_DEBUT = 8;

    /**
     * Durée de la réalisation d'une livraison en minutes
     */
    public static final Integer DUREE_LIVRAISON = 5;

    /**
     * Durée d'un créneau horaire en heures
     */
    public static final Integer DUREE_CRENEAU_HORAIRE = 1;

    /**
     * Default constructor
     */
    public Livraison() {
    }

    public Livraison(Intersection destination, int heureDebut) {
        this.destination = destination;
        this.heureDebut = heureDebut;
    }

    /**
     * L'heure de début de la livraison.
     */
    private int heureDebut;

    /**
     * Le lieu de livraison
     */
    private Intersection destination;


    public Intersection getDestination() {
        return destination;
    }


}