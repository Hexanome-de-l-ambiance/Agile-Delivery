package com.example.model;

import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.time.Duration;

/**
 * 
 */
public class Livraison {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * La vitesse de déplacement du livreur en mètre/min
     */
    public static final int VITESSE_DEPLACEMENT = 15000 / 60;

    /**
     * L'heure de début des tournées
     */
    public static final LocalTime DEBUT_TOURNEE = LocalTime.parse("08:00", formatter);

    /**
     * Durée de la réalisation d'une livraison
     */
    public static final Duration DUREE_LIVRAISON = Duration.ofMinutes(5);

    /**
     * Durée d'un créneau horaire
     */
    public static final Duration DUREE_CRENEAU_HORAIRE = Duration.ofHours(1);

    /**
     * Le nomrbe de créneaux horaires
     */
    public static final Integer NOMBRE_CRENEAUX_HORAIRE = 4;

    /**
     * Default constructor
     */
    public Livraison() {
    }

    public Livraison(Intersection destination, LocalTime creneauHoraire) {
        this.destination = destination;
        this.crenauHoraire = creneauHoraire;
    }

    public LocalTime getCrenauHoraire() {
        return crenauHoraire;
    }

    /**
     * L'heure du début du créneau horaire de livraison
     */
    private LocalTime crenauHoraire;

    /**
     * L'heure de livraison calculée
     */
    private LocalTime heureLivraison;

    /**
     * Le lieu de livraison
     */
    private Intersection destination;

    public Intersection getDestination() {
        return destination;
    }

    public LocalTime getHeureLivraison() {
        return heureLivraison;
    }

    public void setDestination(Intersection destination) {
        this.destination = destination;
    }
    public void setHeureLivraison(LocalTime heureLivraison) {
        this.heureLivraison = heureLivraison;
    }

}