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
        carte.calculerTournees();
    }

    public void deleteDelivery(ListeDeCommandes l, int numeroCoursier, Livraison livraison, Controller c, Carte carte){
        l.addCommande(new CommandeSupprimerLivraison(livraison, numeroCoursier, carte));

    };
    @Override
    public void modiferCoursiers(Controller c, Carte carte, int nombre) {
        carte.setNbCoursiers(nombre);
    }
    @Override
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
}
