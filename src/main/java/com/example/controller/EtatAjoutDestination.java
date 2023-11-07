package com.example.controller;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;
import javafx.stage.Stage;

import java.time.LocalTime;

public class EtatAjoutDestination implements Etat{
    Intersection intersection;

    @Override
    public void addIntersection(Controller c, Intersection intersection) {
        this.intersection = intersection;
    }

    @Override
    public void addDelivery(ListeDeCommandes l, LocalTime heure, int numeroCoursier, Controller c, Carte carte) {

        l.addCommande(new CommandeAjouterLivraison(new Livraison(intersection, heure), numeroCoursier, carte));
        c.setEtatCourant(c.etatDemandeAjoutee);
    }

}
