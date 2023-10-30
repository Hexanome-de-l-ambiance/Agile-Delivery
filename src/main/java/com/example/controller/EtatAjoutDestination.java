package com.example.controller;

import com.example.model.Carte;
import com.example.model.Intersection;
import javafx.stage.Stage;

public class EtatAjoutDestination implements Etat{
    Intersection intersection;

    @Override
    public void addIntersection(Controller c, Intersection intersection) {
        this.intersection = intersection;
    }

    @Override
    public void addDelivery(ListeDeCommandes l, int numeroCoursier, Controller c, Carte carte) {
        l.addCommande(new CommandeAjouterLivraison(intersection, numeroCoursier, carte));
        c.setEtatCourant(c.etatDemandeAjoutee);
    }

    public void undo(ListeDeCommandes l){
        l.undo();
    }
}
