package com.example.controller;

import com.example.model.Carte;
import com.example.model.Intersection;
import com.example.model.Livraison;
import com.example.xml.CustomXMLParsingException;
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
        if(carte.calculerTournees()){
            c.setEtatCourant(c.etatTourneeCalculee);
        };
    }

    public void deleteDelivery(ListeDeCommandes l, int numeroCoursier, Livraison livraison, Controller c, Carte carte){
        l.addCommande(new CommandeSupprimerLivraison(livraison, numeroCoursier, carte));
    };


    public void modifierCoursiers(Controller c, Carte carte, int nombre) {
        carte.setNbCoursiers(nombre);
    }

    public void loadTour(Controller c, Carte carte, Stage stage) {
        try{
            XMLOpener.getInstance().loadTour(stage, carte);
            c.setEtatCourant(c.etatDemandeAjoutee);
        } catch (CustomXMLParsingException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveTour(Controller c, Carte carte, Stage stage) {
        try {
            XMLOpener.getInstance().saveTour(stage, carte);
        } catch (CustomXMLParsingException e) {
            throw new RuntimeException(e);
        }
    }

    public void undo(ListeDeCommandes l){
        l.undo();
    }

    public void redo(ListeDeCommandes l){
        l.redo();
    }

    public void reset(Controller c, Carte carte){
        carte.resetTournee();
        c.setEtatCourant(c.etatCarteChargee);
    }
}
