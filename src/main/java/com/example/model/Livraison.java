package com.example.model;

import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.time.Duration;

/**
 * 
 */
public class Livraison {

    /**
     * Les états possibles d'une livraison : <code>EN_AVANCE</code>, <code>EN_RETARD</code>, <code>A_L_HEURE</code>
     */
    public enum Etat {
        INDETERMINE,
        EN_AVANCE,
        EN_RETARD,
        A_L_HEURE
    }

    /**
     * La vitesse de déplacement du livreur en mètre/min
     */
    public static final int VITESSE_DEPLACEMENT = 15000 / 60;

    /**
     * L'heure de début des tournées
     */
    public static final LocalTime DEBUT_TOURNEE = LocalTime.of(8,0,0);

    /**
     *
     */
    public static final LocalTime FIN_TOURNEE = LocalTime.of(12,0,0);


    /**
     * Durée de la réalisation d'une livraison
     */
    public static final Duration DUREE_LIVRAISON = Duration.ofMinutes(5);

    /**
     * Durée d'un créneau horaire
     */
    public static final Duration DUREE_CRENEAU_HORAIRE = Duration.ofHours(1);

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

    /**
     * L'état de la livraison
     */
    private Etat etat;

    public Livraison() {
        this.etat = Etat.INDETERMINE;
    }

    public Livraison(Intersection destination, LocalTime creneauHoraire) {
        this.destination = destination;
        this.crenauHoraire = creneauHoraire;
        this.heureLivraison = null;
        this.etat = Etat.INDETERMINE;
    }

    public LocalTime getCrenauHoraire() {
        return crenauHoraire;
    }

    public void setCrenauHoraire(LocalTime crenauHoraire) {
        this.crenauHoraire = crenauHoraire;
    }

    public Intersection getDestination() {
        if (destination == null) {
            throw new IllegalStateException("Destination is not set for this delivery.");
        }
        return destination;
    }
    public void setDestination(Intersection destination) {
        this.destination = destination;
    }

    public LocalTime getHeureLivraison() {
        return heureLivraison;
    }

    public void setHeureLivraison(LocalTime heureLivraison) {
        if(heureLivraison.isBefore(crenauHoraire)){
            etat = Etat.EN_AVANCE;
            this.heureLivraison = crenauHoraire;
        } else {
            this.heureLivraison = heureLivraison;
            if(heureLivraison.isAfter(crenauHoraire.plus(DUREE_CRENEAU_HORAIRE))) {
                this.etat = Etat.EN_RETARD;
            } else {
                this.etat = Etat.A_L_HEURE;
            }
        }
    }

    public Etat getEtat() { return etat; }

    public void setEtat(Etat etat) { this.etat = etat; }

}