package com.example.controller;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;
import com.example.xml.XMLOpener;
import javafx.stage.Stage;

import java.util.ArrayList;

public class EtatDemandeAjoutee implements Etat {

    /**
    * Default constructor
    */
    public EtatDemandeAjoutee() {
    }


    public void addIntersection(Controller c, Intersection intersection){
        c.setEtatCourant(c.etatAjoutDestination);
        c.etatAjoutDestination.addIntersection(c, intersection);
    }

    public void calculerLivraisons(Controller c, Carte carte){
        carte.calculerTournees();
    }
    @Override
    public void modiferCoursiers(Controller c, Carte carte, int nombre) {
        carte.setNbCoursiers(nombre);
    }
    public void undo(ListeDeCommandes l){
        l.undo();
    }

    public void redo(ListeDeCommandes l){
        l.redo();
    }
}
