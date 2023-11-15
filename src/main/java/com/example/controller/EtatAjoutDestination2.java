package com.example.controller;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;

import java.time.LocalTime;

public class EtatAjoutDestination2 implements Etat{
    Intersection intersection;
    public void addIntersection(Controller c, Intersection intersection) {
        this.intersection = intersection;
    }


    public void addDelivery(ListeDeCommandes l, LocalTime heure, int numeroCoursier, int index, Controller c, Carte carte) {
        l.addCommande(new CommandeAjouterLivraisonApresCalcul(new Livraison(intersection, heure), index, numeroCoursier, carte));
        c.setEtatCourant(c.etatTourneeCalculee);
    }
    public void unselectIntersection(Controller c){c.setEtatCourant(c.etatTourneeCalculee);}

}
