package com.example.controller;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;

import java.time.LocalTime;
/**
 * La classe implémentant l'interface représente l'état pour l'ajout d'une destination avant le calcul des tournées.
 */
public class EtatAjoutDestination implements Etat{
    Intersection intersection;

    public void addIntersection(Controller c, Intersection intersection) {
        this.intersection = intersection;
    }

    public void addDelivery(ListeDeCommandes l, LocalTime heure, int numeroCoursier, Controller c, Carte carte) {

        l.addCommande(new CommandeAjouterLivraison(new Livraison(intersection, heure), numeroCoursier, carte));
        c.setEtatCourant(c.etatCarteChargee);
    }

    public void unselectIntersection(Controller c){c.setEtatCourant(c.etatCarteChargee);}

}
