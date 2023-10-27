package com.example.controller;

import com.example.model.Carte;
import com.example.model.Tournee;
import com.example.xml.CustomXMLParsingException;
import com.example.xml.XMLOpener;
import javafx.stage.Stage;

/**
 *
 */
public class EtatInitial implements Etat {

    @Override
    public void loadMap(Controller c, Carte carte, ListeDeCommandes l, Stage stage){
        try{
            XMLOpener.getInstance().readFile(stage, carte);
            c.setEtatCourant(c.etatCarteChargee);
            Tournee tournee = new Tournee();
            tournee.addLivraison(carte.getListeIntersections().get(2129259178L));
            tournee.addLivraison(carte.getListeIntersections().get(25175791L));
            tournee.addLivraison(carte.getListeIntersections().get(26086130L));
            tournee.addLivraison(carte.getListeIntersections().get(26086123L));
            tournee.addLivraison(carte.getListeIntersections().get(565375197L));
            tournee.addLivraison(carte.getListeIntersections().get(55475025L));
            tournee.addLivraison(carte.getListeIntersections().get(2117622721L));

            tournee.calculerTournee(carte);
            carte.addTournee(0, tournee);

        } catch (CustomXMLParsingException e) {
            throw new RuntimeException(e);
        }
    }
}