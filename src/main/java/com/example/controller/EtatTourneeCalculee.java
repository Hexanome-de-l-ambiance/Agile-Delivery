package com.example.controller;

import com.example.model.Carte;
import com.example.model.Tournee;
import com.example.model.Intersection;
import com.example.model.Livraison;
import com.example.xml.CustomXMLParsingException;
import com.example.xml.XMLOpener;
import javafx.stage.Stage;

public class EtatTourneeCalculee implements Etat {

    /**
    * Default constructor
    */
    public EtatTourneeCalculee() {

    }
    public void addIntersection(Controller c, Intersection intersection){
        c.setEtatCourant(c.etatAjoutDestination2);
        c.etatAjoutDestination2.addIntersection(c, intersection);
    }

    public void deleteDelivery(ListeDeCommandes l, int numeroCoursier, Livraison livraison, int index, Controller c, Carte carte){
        l.addCommande(new CommandeSupprimerLivraisonApresCalcul(livraison, numeroCoursier, index, carte));
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

    @Override
    public void saveTour(Controller c, Carte carte, Stage stage) {
        try {
            XMLOpener.getInstance().saveTour(stage, carte);
        } catch (CustomXMLParsingException e) {
            throw new RuntimeException(e);
        }
    }

    public void genererFeuilleRoute(Controller c, Carte carte){
        for (Tournee tournee : carte.getListeTournees().values())
        {
           tournee.genererFeuilleDeRouteHTML("feuilleDeRoute" + tournee.getCoursier() + ".html");
        }
    }

}
